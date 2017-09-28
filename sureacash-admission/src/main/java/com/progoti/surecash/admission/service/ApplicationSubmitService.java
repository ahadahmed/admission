package com.progoti.surecash.admission.service;

import com.progoti.surecash.admission.request.ApplicationFormRequest;
import com.progoti.surecash.admission.response.CredentialResponse;

/**
 * Created by Shaown on 3:13 PM.
 */
public interface ApplicationSubmitService {
    CredentialResponse submitForm(ApplicationFormRequest request);
}
