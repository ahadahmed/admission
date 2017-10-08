package com.progoti.surecash.admission.utility;

import java.security.SecureRandom;
import java.util.UUID;

/**
 * Created by Shaown on 10:41 AM.
 */
public class Constants {
    private static SecureRandom secureRandom = new SecureRandom();
    public enum Quota {
        FREEDOM_FIGHTER, TRIBAL, DISABLED, POSSHO, NONE
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
    public enum TranxCode {
        A2B("107"), P2B("1202");

        public String value;
        TranxCode(String value) {
            this.value = value;
        }
    }
    public enum AdmissionSession {
        SESSION_2017_2018("2017-2018");

        public String value;
        AdmissionSession(String value) {
            this.value = value;
        }
    }
    public static int applicationIdGenerator(int unitId){
        int maxDigit = 8;
        int counter = 0;
        int number = unitId;
        while(number != 0)
        {
            counter++;
            number = number/10;
        }
        int trailingZeros = (int)Math.pow(10, (maxDigit-counter));
        long seed = System.currentTimeMillis() + secureRandom.nextInt(99999999);
        return (int) ((unitId*trailingZeros) + (seed%trailingZeros));
    }

    public static String userNameGenerator(Integer uniId){
        int maxDigit = 8;
        if(uniId == null){
            return UUID.randomUUID().toString().replaceAll("-", "").substring(0, maxDigit);
        } else {
            int counter = 0;
            int number = uniId;
            while(number != 0)
            {
                counter++;
                number = number/10;
            }
            return String.valueOf(uniId)+ UUID.randomUUID().toString().replaceAll("-", "").substring(0, maxDigit-counter);
        }
    }
}
