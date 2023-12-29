
package com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.getTwilioConfig;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

 public class Data {

    @SerializedName("accountSid")
    @Expose
    private String accountSid;
    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("senderId")
    @Expose
    private String senderId;
    @SerializedName("number")
    @Expose
    private String number;
    @SerializedName("callerId")
    @Expose
    private String callerId;
    @SerializedName("timeout")
    @Expose
    private String timeout;
    @SerializedName("recordCall")
    @Expose
    private Boolean recordCall;
    @SerializedName("enableSms")
    @Expose
    private Boolean enableSms;
    @SerializedName("isActive")
    @Expose
    private Boolean isActive;

    public String getAccountSid() {
        return accountSid;
    }

    public void setAccountSid(String accountSid) {
        this.accountSid = accountSid;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCallerId() {
        return callerId;
    }

    public void setCallerId(String callerId) {
        this.callerId = callerId;
    }

    public String getTimeout() {
        return timeout;
    }

    public void setTimeout(String timeout) {
        this.timeout = timeout;
    }

    public Boolean getRecordCall() {
        return recordCall;
    }

    public void setRecordCall(Boolean recordCall) {
        this.recordCall = recordCall;
    }

    public Boolean getEnableSms() {
        return enableSms;
    }

    public void setEnableSms(Boolean enableSms) {
        this.enableSms = enableSms;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

}
