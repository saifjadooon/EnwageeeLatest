
package com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GtLogdinUserDetailsRsp;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class GetLoggedinUserDetails {

    @SerializedName("data")
    @Expose
    private Data data;
    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("message")
    @Expose
    private Object message;
    @SerializedName("traceId")
    @Expose
    private Object traceId;
    @SerializedName("spanId")
    @Expose
    private Object spanId;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

    public Object getTraceId() {
        return traceId;
    }

    public void setTraceId(Object traceId) {
        this.traceId = traceId;
    }

    public Object getSpanId() {
        return spanId;
    }

    public void setSpanId(Object spanId) {
        this.spanId = spanId;
    }

}
