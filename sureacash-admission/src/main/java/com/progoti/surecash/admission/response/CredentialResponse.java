package com.progoti.surecash.admission.response;

/**
 * Created by Shaown on 4:14 PM.
 */
public class CredentialResponse {
    private String userName;
    private String password;

    public CredentialResponse(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
