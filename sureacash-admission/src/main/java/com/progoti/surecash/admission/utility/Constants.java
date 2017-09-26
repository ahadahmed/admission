package com.progoti.surecash.admission.utility;

/**
 * Created by Shaown on 10:41 AM.
 */
public class Constants {
    public enum Quota {
        FREEDOM_FIGHTER, TRIBAL, DISABLED, POSSHO
    }
    public enum Board {
        Barisal, Chittagong, Comilla, Dhaka, Dinajpur, Jessore, Rajshahi, Sylhet, Madrasah, Technical
    }
    public enum Group {
        HUMANITIES, SCIENCE, BUSINESS_STUDIES, MUSIC, HOME_ECONOMICS
    }
    public enum ErrorMessage {
        INVALID_ACADEMIC_INFO("SSC & HSC information mismatch");
        public String value;
        ErrorMessage(String value) {
            this.value = value;
        }
    }

}
