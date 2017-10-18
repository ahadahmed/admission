package com.progoti.surecash.admission.utility;

import org.apache.commons.codec.binary.Base64;

import java.security.SecureRandom;
import java.text.DecimalFormat;
import java.util.UUID;

/**
 * Created by Shaown on 10:41 AM.
 */
public class Constants {

    public static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#,##0.00");

    private static SecureRandom secureRandom = new SecureRandom();

    public enum Quota {
        FREEDOM_FIGHTER, TRIBAL, DISABLED, POSSHO, NONE
    }

    public enum RoleName {
        USER, ADMIN, SC_ADMIN, SUPER_ADMIN
    }

    public enum Board {
        Barisal,
        Chittagong,
        Comilla,
        Dhaka,
        Dinajpur,
        Jessore,
        Rajshahi,
        Sylhet,
        Madrasah,
        Technical
    }

    public enum Group {
        HUMANITIES("HUMANITIES"),
        SCIENCE("SCIENCE"),
        BUSINESS_STUDIES("BUSINESS STUDIES"),
        MUSIC("MUSIC"),
        HOME_ECONOMICS("HOME ECONOMICS");

        public String value;

        Group(String value) {
            this.value = value;
        }
    }

    public enum Transaction_Status {
        PROCESSED,
        ERROR,
        UNSUCCESSFUL,
        FAILED,
        RECONCILE
    }

    public enum ErrorMessage {
        INVALID_ACADEMIC_INFO("SSC & HSC information mismatch"),
        INSTITUTION_NOT_FOUND("Institution not found : STATUS_CODE_1009"),
        NO_DUE("Amount is greater than due amount. Due amount: 0.00"),
        INVALID_AMOUNT("Amount is less than due amount. Due amount: ?");

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

    public static int applicationIdGenerator(int unitId) {
        int maxDigit = 8;
        int counter = 0;
        int number = unitId;
        while (number != 0) {
            counter++;
            number = number / 10;
        }
        int trailingZeros = (int) Math.pow(10, (maxDigit - counter));
        long seed = System.currentTimeMillis() + secureRandom.nextInt(99999999);
        return (int) ((unitId * trailingZeros) + (seed % trailingZeros));
    }

    public static String userNameGenerator(Integer uniId) {
        int maxDigit = 8;
        if (uniId == null) {
            return UUID.randomUUID().toString().replaceAll("-", "").substring(0, maxDigit);
        } else {
            int counter = 0;
            int number = uniId;
            while (number != 0) {
                counter++;
                number = number / 10;
            }
            return String.valueOf(uniId) + UUID.randomUUID().toString().replaceAll("-", "")
                    .substring(0, maxDigit - counter);
        }
    }

    public static String getBase64ImageData(byte[] byteData){
        return Base64.encodeBase64String(byteData);
    }
}
