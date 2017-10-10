package com.progoti.surecash.admission.service;

import com.progoti.surecash.admission.domain.University;
import com.progoti.surecash.dto.AdminDashboardDto;

/**
 * Created by Shaown on 6:44 AM.
 */
public interface AdminService {
    AdminDashboardDto getDashboardStatus(University university);
}
