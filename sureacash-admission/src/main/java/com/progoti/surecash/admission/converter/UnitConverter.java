package com.progoti.surecash.admission.converter;

import com.progoti.surecash.admission.domain.AdmissionSession;
import com.progoti.surecash.admission.domain.StudentApplicationHistory;
import com.progoti.surecash.admission.domain.Unit;
import com.progoti.surecash.admission.utility.Constants;
import com.progoti.surecash.dto.UnitDto;
import java.util.ArrayList;
import java.util.List;

public class UnitConverter {

    public static UnitDto toDto(Unit unit) {
        UnitDto dto = new UnitDto();
        if (unit != null) {
            dto.setUnitId(unit.getId());
            dto.setName(unit.getName());
            dto.setCode(unit.getCode());
            dto.setDescription(unit.getDescription());
            if (unit.getUniversity() != null) {
                dto.setUniversityName(unit.getUniversity().getName());
            }
        }
        return dto;
    }

    public static UnitDto toDto(AdmissionSession session) {
        UnitDto dto = toDto(session.getUnit());
        dto.setFormattedFees(Constants.DECIMAL_FORMAT.format(session.getFormPrice()));
        return dto;
    }

    public static List<UnitDto> toDtos(List<AdmissionSession> sessions) {
        List<UnitDto> dtos = new ArrayList<>();
        if (sessions == null) {
            return dtos;
        }
        sessions.forEach(session -> dtos.add(toDto(session)));
        return dtos;
    }

    public static UnitDto toDto(StudentApplicationHistory history) {
        UnitDto dto = toDto(history.getUnit());
        dto.setHistoryId(history.getId());
        return dto;
    }

    public static List<UnitDto> toDto(List<StudentApplicationHistory> histories) {
        List<UnitDto> dtos = new ArrayList<>();
        if (histories == null) {
            return dtos;
        }
        histories.forEach(history -> dtos.add(toDto(history)));
        return dtos;
    }
}
