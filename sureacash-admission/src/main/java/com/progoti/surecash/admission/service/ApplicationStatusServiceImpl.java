package com.progoti.surecash.admission.service;

import com.progoti.surecash.admission.converter.UnitConverter;
import com.progoti.surecash.admission.domain.AdmissionSession;
import com.progoti.surecash.admission.domain.StudentApplicationHistory;
import com.progoti.surecash.admission.repository.StudentApplicationHistoryRepository;
import com.progoti.surecash.admission.repository.UnitRepository;
import com.progoti.surecash.admission.utility.SteamCollector;
import com.progoti.surecash.dto.UnitDto;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ApplicationStatusServiceImpl implements ApplicationStatusService {

    private UnitRepository unitRepository;
    private StudentApplicationHistoryRepository historyRepository;

    @Autowired
    public ApplicationStatusServiceImpl(UnitRepository unitRepository,
            StudentApplicationHistoryRepository historyRepository) {
        this.unitRepository = unitRepository;
        this.historyRepository = historyRepository;
    }

    @Override
    public void retrieveAvailableUnits(List<UnitDto> availableUnits, List<UnitDto> appliedUnits,
            String userName, String sessionYear, int universityId) {
        List<AdmissionSession> sessions = unitRepository
                .loadUnitsByUniversityAndSession(sessionYear, universityId);
        List<StudentApplicationHistory> histories = historyRepository.loadHistoryByUserName(userName);

        List<UnitDto> finalAvailableUnits = new ArrayList<>();
        List<UnitDto> finalAppliedUnits = new ArrayList<>();
        sessions.forEach(session -> {
            StudentApplicationHistory history = histories.stream()
                    .filter(h -> h.getUnit().getId() == session.getUnit().getId())
                    .collect(SteamCollector.firstOrDefault());
            if (history == null) {
                finalAvailableUnits.add(UnitConverter.toDto(session));
            } else {
                UnitDto unitDto = UnitConverter.toDto(session);
                unitDto.setHistoryId(history.getId());
                finalAppliedUnits.add(unitDto);
            }
        });
        appliedUnits.addAll(finalAppliedUnits);
        availableUnits.addAll(finalAvailableUnits);
    }

    @Override
    public void deleteApplication(int historyId) {
        historyRepository.delete(historyId);
    }
}
