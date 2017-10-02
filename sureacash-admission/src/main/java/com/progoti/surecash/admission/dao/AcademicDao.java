package com.progoti.surecash.admission.dao;

import com.progoti.surecash.admission.domain.University;
import com.progoti.surecash.admission.request.AcademicInformationRequest;
import com.progoti.surecash.admission.response.StudentInfoResponse;
import com.progoti.surecash.admission.response.UnitInfo;
import com.progoti.surecash.admission.utility.Constants;

import java.util.List;

/**
 * Created by Shaown on 12:56 PM.
 */
public interface AcademicDao {
    StudentInfoResponse getStudentInfo(AcademicInformationRequest request);
    List<UnitInfo> getUnitInfoListFromUniversityAndSession(University university, Constants.AdmissionSession admissionSession);
}
