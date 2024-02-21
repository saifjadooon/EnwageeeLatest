package com.example.envagemobileapplication.Models.RequestModels;


public class ChangePasswordRequest {
    private String previousPassword;
    private String newPassword;

    public ChangePasswordRequest(String previousPassword, String newPassword) {
        this.previousPassword = previousPassword;
        this.newPassword = newPassword;
    }

    public String getPreviousPassword() {
        return previousPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }
}