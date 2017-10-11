package com.progoti.surecash.admission.service;

import com.progoti.surecash.dto.UnitDto;
import java.util.List;

public interface ApplicationStatusService {

    void retrieveAvailableUnits(List<UnitDto> availableUnits, List<UnitDto> appliedUnits,
            String userName, String sessionYear, int universityId);

    void deleteApplication(int historyId);
}
