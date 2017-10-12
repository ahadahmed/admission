package com.progoti.surecash.admission.domain;

import com.google.common.base.MoreObjects;

public class User {

    private int userId;
    private String userName;
    private String password;
    private String email;
    private String role;
    private University univ;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public University getUniv() {
        return univ;
    }

    public void setUniv(University univ) {
        this.univ = univ;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("userId", userId)
                .add("userName", userName)
                .add("email", email)
                .add("role", role)
                .add("univ", univ)
                .toString();
    }
}
