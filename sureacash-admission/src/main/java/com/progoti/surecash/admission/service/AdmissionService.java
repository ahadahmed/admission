package com.progoti.surecash.admission.service;

import com.progoti.surecash.admission.domain.StudentInfo;
import com.progoti.surecash.admission.request.ApplicationFormRequest;
import com.progoti.surecash.admission.response.CredentialResponse;
import com.progoti.surecash.admission.response.ProfileResponse;

/**
 * Created by Shaown on 3:13 PM.
 */
public interface AdmissionService {
    CredentialResponse submitForm(ApplicationFormRequest request);
    ProfileResponse getStudentProfile(StudentInfo studentInfo);
}
