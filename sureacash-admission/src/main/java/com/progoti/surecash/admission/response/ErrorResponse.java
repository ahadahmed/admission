package com.progoti.surecash.admission.response;

/**
 * Created by Shaown on 26 Sep, 2017
 */
public class ErrorResponse {
    private String message;
    private String reason;
    private String description;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
