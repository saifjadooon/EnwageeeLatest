
package com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetJobRequests;

 import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

 public class JobRequestBillingDetail {

    @SerializedName("jobRequestId")
    @Expose
    private Integer jobRequestId;
    @SerializedName("markup")
    @Expose
    private Object markup;
    @SerializedName("minPayRate")
    @Expose
    private Double minPayRate;
    @SerializedName("minBillRate")
    @Expose
    private Object minBillRate;
    @SerializedName("maxPayRate")
    @Expose
    private Double maxPayRate;
    @SerializedName("maxBillRate")
    @Expose
    private Object maxBillRate;
    @SerializedName("targetPayRate")
    @Expose
    private Double targetPayRate;
    @SerializedName("targetBillRate")
    @Expose
    private Object targetBillRate;
    @SerializedName("overtimeType")
    @Expose
    private String overtimeType;
    @SerializedName("overtimeMarkup")
    @Expose
    private Object overtimeMarkup;
    @SerializedName("overtimePayRate")
    @Expose
    private Double overtimePayRate;
    @SerializedName("overtimeBillRate")
    @Expose
    private Object overtimeBillRate;
    @SerializedName("doubletimeType")
    @Expose
    private String doubletimeType;
    @SerializedName("doubletimeMarkup")
    @Expose
    private Object doubletimeMarkup;
    @SerializedName("doubletimePayRate")
    @Expose
    private Double doubletimePayRate;
    @SerializedName("doubletimeBillRate")
    @Expose
    private Object doubletimeBillRate;
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
    private Integer modifiedBy;
    @SerializedName("modifiedDate")
    @Expose
    private String modifiedDate;
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

    public Object getMarkup() {
        return markup;
    }

    public void setMarkup(Object markup) {
        this.markup = markup;
    }

    public Double getMinPayRate() {
        return minPayRate;
    }

    public void setMinPayRate(Double minPayRate) {
        this.minPayRate = minPayRate;
    }

    public Object getMinBillRate() {
        return minBillRate;
    }

    public void setMinBillRate(Object minBillRate) {
        this.minBillRate = minBillRate;
    }

    public Double getMaxPayRate() {
        return maxPayRate;
    }

    public void setMaxPayRate(Double maxPayRate) {
        this.maxPayRate = maxPayRate;
    }

    public Object getMaxBillRate() {
        return maxBillRate;
    }

    public void setMaxBillRate(Object maxBillRate) {
        this.maxBillRate = maxBillRate;
    }

    public Double getTargetPayRate() {
        return targetPayRate;
    }

    public void setTargetPayRate(Double targetPayRate) {
        this.targetPayRate = targetPayRate;
    }

    public Object getTargetBillRate() {
        return targetBillRate;
    }

    public void setTargetBillRate(Object targetBillRate) {
        this.targetBillRate = targetBillRate;
    }

    public String getOvertimeType() {
        return overtimeType;
    }

    public void setOvertimeType(String overtimeType) {
        this.overtimeType = overtimeType;
    }

    public Object getOvertimeMarkup() {
        return overtimeMarkup;
    }

    public void setOvertimeMarkup(Object overtimeMarkup) {
        this.overtimeMarkup = overtimeMarkup;
    }

    public Double getOvertimePayRate() {
        return overtimePayRate;
    }

    public void setOvertimePayRate(Double overtimePayRate) {
        this.overtimePayRate = overtimePayRate;
    }

    public Object getOvertimeBillRate() {
        return overtimeBillRate;
    }

    public void setOvertimeBillRate(Object overtimeBillRate) {
        this.overtimeBillRate = overtimeBillRate;
    }

    public String getDoubletimeType() {
        return doubletimeType;
    }

    public void setDoubletimeType(String doubletimeType) {
        this.doubletimeType = doubletimeType;
    }

    public Object getDoubletimeMarkup() {
        return doubletimeMarkup;
    }

    public void setDoubletimeMarkup(Object doubletimeMarkup) {
        this.doubletimeMarkup = doubletimeMarkup;
    }

    public Double getDoubletimePayRate() {
        return doubletimePayRate;
    }

    public void setDoubletimePayRate(Double doubletimePayRate) {
        this.doubletimePayRate = doubletimePayRate;
    }

    public Object getDoubletimeBillRate() {
        return doubletimeBillRate;
    }

    public void setDoubletimeBillRate(Object doubletimeBillRate) {
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

    public Integer getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(Integer modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public String getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(String modifiedDate) {
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
