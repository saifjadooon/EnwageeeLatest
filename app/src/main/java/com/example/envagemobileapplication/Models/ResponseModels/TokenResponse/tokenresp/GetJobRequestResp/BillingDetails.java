
package com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetJobRequestResp;

 import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

 public class BillingDetails {

    @SerializedName("jobRequestId")
    @Expose
    private Integer jobRequestId;
    @SerializedName("markup")
    @Expose
    private Double markup;
    @SerializedName("minPayRate")
    @Expose
    private Double minPayRate;
    @SerializedName("minBillRate")
    @Expose
    private Double minBillRate;
    @SerializedName("maxPayRate")
    @Expose
    private Double maxPayRate;
    @SerializedName("maxBillRate")
    @Expose
    private Double maxBillRate;
    @SerializedName("targetPayRate")
    @Expose
    private Double targetPayRate;
    @SerializedName("targetBillRate")
    @Expose
    private Double targetBillRate;
    @SerializedName("overtimeType")
    @Expose
    private String overtimeType;
    @SerializedName("overtimeMarkup")
    @Expose
    private Double overtimeMarkup;
    @SerializedName("overtimePayRate")
    @Expose
    private Double overtimePayRate;
    @SerializedName("overtimeBillRate")
    @Expose
    private Double overtimeBillRate;
    @SerializedName("doubletimeType")
    @Expose
    private String doubletimeType;
    @SerializedName("doubletimeMarkup")
    @Expose
    private Double doubletimeMarkup;
    @SerializedName("doubletimePayRate")
    @Expose
    private Double doubletimePayRate;
    @SerializedName("doubletimeBillRate")
    @Expose
    private Double doubletimeBillRate;
    @SerializedName("frequency")
    @Expose
    private String frequency;
    @SerializedName("createdBy")
    @Expose
    private Integer createdBy;
    @SerializedName("createdDate")
    @Expose
    private String createdDate;
    @SerializedName("modifiedBy")
    @Expose
    private Object modifiedBy;
    @SerializedName("modifiedDate")
    @Expose
    private Object modifiedDate;
    @SerializedName("overtimeMultiplier")
    @Expose
    private Double overtimeMultiplier;
    @SerializedName("doubletimeMultiplier")
    @Expose
    private Double doubletimeMultiplier;

    public Integer getJobRequestId() {
        return jobRequestId;
    }

    public void setJobRequestId(Integer jobRequestId) {
        this.jobRequestId = jobRequestId;
    }

    public Double getMarkup() {
        return markup;
    }

    public void setMarkup(Double markup) {
        this.markup = markup;
    }

    public Double getMinPayRate() {
        return minPayRate;
    }

    public void setMinPayRate(Double minPayRate) {
        this.minPayRate = minPayRate;
    }

    public Double getMinBillRate() {
        return minBillRate;
    }

    public void setMinBillRate(Double minBillRate) {
        this.minBillRate = minBillRate;
    }

    public Double getMaxPayRate() {
        return maxPayRate;
    }

    public void setMaxPayRate(Double maxPayRate) {
        this.maxPayRate = maxPayRate;
    }

    public Double getMaxBillRate() {
        return maxBillRate;
    }

    public void setMaxBillRate(Double maxBillRate) {
        this.maxBillRate = maxBillRate;
    }

    public Double getTargetPayRate() {
        return targetPayRate;
    }

    public void setTargetPayRate(Double targetPayRate) {
        this.targetPayRate = targetPayRate;
    }

    public Double getTargetBillRate() {
        return targetBillRate;
    }

    public void setTargetBillRate(Double targetBillRate) {
        this.targetBillRate = targetBillRate;
    }

    public String getOvertimeType() {
        return overtimeType;
    }

    public void setOvertimeType(String overtimeType) {
        this.overtimeType = overtimeType;
    }

    public Double getOvertimeMarkup() {
        return overtimeMarkup;
    }

    public void setOvertimeMarkup(Double overtimeMarkup) {
        this.overtimeMarkup = overtimeMarkup;
    }

    public Double getOvertimePayRate() {
        return overtimePayRate;
    }

    public void setOvertimePayRate(Double overtimePayRate) {
        this.overtimePayRate = overtimePayRate;
    }

    public Double getOvertimeBillRate() {
        return overtimeBillRate;
    }

    public void setOvertimeBillRate(Double overtimeBillRate) {
        this.overtimeBillRate = overtimeBillRate;
    }

    public String getDoubletimeType() {
        return doubletimeType;
    }

    public void setDoubletimeType(String doubletimeType) {
        this.doubletimeType = doubletimeType;
    }

    public Double getDoubletimeMarkup() {
        return doubletimeMarkup;
    }

    public void setDoubletimeMarkup(Double doubletimeMarkup) {
        this.doubletimeMarkup = doubletimeMarkup;
    }

    public Double getDoubletimePayRate() {
        return doubletimePayRate;
    }

    public void setDoubletimePayRate(Double doubletimePayRate) {
        this.doubletimePayRate = doubletimePayRate;
    }

    public Double getDoubletimeBillRate() {
        return doubletimeBillRate;
    }

    public void setDoubletimeBillRate(Double doubletimeBillRate) {
        this.doubletimeBillRate = doubletimeBillRate;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public Object getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(Object modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Object getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Object modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Double getOvertimeMultiplier() {
        return overtimeMultiplier;
    }

    public void setOvertimeMultiplier(Double overtimeMultiplier) {
        this.overtimeMultiplier = overtimeMultiplier;
    }

    public Double getDoubletimeMultiplier() {
        return doubletimeMultiplier;
    }

    public void setDoubletimeMultiplier(Double doubletimeMultiplier) {
        this.doubletimeMultiplier = doubletimeMultiplier;
    }

}
