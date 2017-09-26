package com.progoti.surecash.admission.repository;

import com.progoti.surecash.admission.request.AcademicInformationRequest;
import com.progoti.surecash.admission.response.StudentInfoResponse;

/**
 * Created by Shaown on 12:56 PM.
 */
public interface AcademicRepository {
    StudentInfoResponse getStudentInfo(AcademicInformationRequest request);
}
