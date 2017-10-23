package com.progoti.surecash.admission.service;

import com.progoti.surecash.admission.converter.UnitConverter;
import com.progoti.surecash.admission.domain.AdmissionSession;
import com.progoti.surecash.admission.domain.StudentApplicationHistory;
import com.progoti.surecash.admission.domain.StudentInfo;
import com.progoti.surecash.admission.domain.UserDetailsImpl;
import com.progoti.surecash.admission.repository.StudentApplicationHistoryRepository;
import com.progoti.surecash.admission.repository.UnitRepository;
import com.progoti.surecash.admission.repository.UniversityRepository;
import com.progoti.surecash.admission.utility.Constants;
import com.progoti.surecash.admission.utility.SecurityUtils;
import com.progoti.surecash.admission.utility.SteamCollector;
import com.progoti.surecash.dto.UnitDto;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ApplicationStatusServiceImpl implements ApplicationStatusService {

    private UnitRepository unitRepository;
    private StudentApplicationHistoryRepository historyRepository;
    private UniversityRepository universityRepository;

    @Autowired
    public ApplicationStatusServiceImpl(UnitRepository unitRepository,
            StudentApplicationHistoryRepository historyRepository, UniversityRepository universityRepository) {
        this.unitRepository = unitRepository;
        this.historyRepository = historyRepository;
        this.universityRepository = universityRepository;
    }

    @Override
    public void retrieveAvailableUnits(List<UnitDto> availableUnits, List<UnitDto> appliedUnits,
            String userName, String sessionYear, int universityId) {
        UserDetailsImpl userDetails = SecurityUtils.getUserDetails();
        List<AdmissionSession> sessions = unitRepository
                .loadUnitsByUniversityAndSession(sessionYear, universityId);

//        List<StudentApplicationHistory> histories = historyRepository
//                .loadActiveHistoryByUserName(userName);

        List<StudentApplicationHistory> histories = historyRepository
                .findAllByStudentInfoAndUniversity(userDetails.getUser().getStudentId(), universityRepository.getOne(universityId));

        List<UnitDto> finalAvailableUnits = new ArrayList<>();
        List<UnitDto> finalAppliedUnits = new ArrayList<>();
        sessions.forEach(session -> {
            StudentApplicationHistory history = histories.stream()
                    .filter(h -> h.getUnit().getId() == session.getUnit().getId() && h.getActive())
                    .collect(SteamCollector.firstOrDefault());
            if (history == null) {
                finalAvailableUnits.add(UnitConverter.toDto(session));
            } else {
                UnitDto unitDto = UnitConverter.toDto(session);
                unitDto.setHistoryId(history.getId());
                unitDto.setPaid(history.getPaid() == null ? false : history.getPaid());
                finalAppliedUnits.add(unitDto);
            }
        });
        appliedUnits.addAll(finalAppliedUnits);
        availableUnits.addAll(finalAvailableUnits);
    }

    @Override
    public void applyUnit(int studentInfoId, String sessionYear, List<Integer> unitIds) {
        List<AdmissionSession> sessions = unitRepository.loadUnitsByUnitIds(sessionYear, unitIds);
        List<StudentApplicationHistory> histories = new ArrayList<>();
        for (AdmissionSession session : sessions) {
            StudentApplicationHistory history = new StudentApplicationHistory();
            int applicationId = Constants.applicationIdGenerator(session.getUnit().getId());
            history.setApplicationId(String.valueOf(applicationId));
            history.setPayableAmount(session.getFormPrice());
            history.setActive(true);
            history.setApplicationDate(new Date());
            history.setUnit(session.getUnit());
            history.setStudentInfo(new StudentInfo(studentInfoId));
            history.setPaid(false);
            histories.add(history);
        }
        historyRepository.save(histories);
    }

    @Override
    public void deleteApplication(int historyId) {
        StudentApplicationHistory history = historyRepository.findOne(historyId);
        if (history != null) {
            history.setActive(false);
        }
        historyRepository.save(history);
    }
}
