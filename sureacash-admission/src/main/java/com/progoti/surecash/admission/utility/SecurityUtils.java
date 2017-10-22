package com.progoti.surecash.admission.utility;

import com.progoti.surecash.admission.domain.UserDetailsImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {

    public static UserDetailsImpl getUserDetails() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserDetailsImpl user = null;
        if (auth != null) {
            Object principal = auth.getPrincipal();
            if (principal instanceof UserDetailsImpl) {
                user = (UserDetailsImpl) principal;
            }
        }
        return user;
    }

    public static String getUserName() {
        UserDetailsImpl userDetails = getUserDetails();
        if (userDetails != null) {
            return userDetails.getUsername();
        }
        return "";
    }

    public static int getUserId() {
        UserDetailsImpl userDetails = getUserDetails();
        if (userDetails != null && userDetails.getUser() != null) {
            return userDetails.getUser().getUserId();
        }
        return 0;
    }

    public static int getUniversityId() {
        UserDetailsImpl userDetails = getUserDetails();
        if (userDetails != null && userDetails.getUser() != null) {
            if (userDetails.getUser().getUniv() != null) {
                return userDetails.getUser().getUniv().getId();
            }
        }
        return 0;
    }
}
