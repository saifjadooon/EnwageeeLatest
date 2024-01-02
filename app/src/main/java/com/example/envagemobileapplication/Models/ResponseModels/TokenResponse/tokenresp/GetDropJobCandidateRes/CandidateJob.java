
package com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetDropJobCandidateRes;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class CandidateJob {

    @SerializedName("candidateJobId")
    @Expose
    private Integer candidateJobId;
    @SerializedName("companyId")
    @Expose
    private Integer companyId;
    @SerializedName("candidateId")
    @Expose
    private Integer candidateId;
    @SerializedName("jobId")
    @Expose
    private Integer jobId;
    @SerializedName("positionName")
    @Expose
    private String positionName;
    @SerializedName("jobStatusId")
    @Expose
    private Object jobStatusId;
    @SerializedName("jobStatus")
    @Expose
    private String jobStatus;
    @SerializedName("colorCode")
    @Expose
    private String colorCode;
    @SerializedName("clientId")
    @Expose
    private Object clientId;
    @SerializedName("clientProfilePicture")
    @Expose
    private String clientProfilePicture;
    @SerializedName("clientName")
    @Expose
    private String clientName;
    @SerializedName("jobType")
    @Expose
    private String jobType;
    @SerializedName("country")
    @Expose
    private Object country;
    @SerializedName("zipcode")
    @Expose
    private Object zipcode;
    @SerializedName("city")
    @Expose
    private Object city;
    @SerializedName("state")
    @Expose
    private Object state;
    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("jobNature")
    @Expose
    private String jobNature;
    @SerializedName("statusId")
    @Expose
    private Integer statusId;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("statusColorCode")
    @Expose
    private String statusColorCode;
    @SerializedName("payRate")
    @Expose
    private Double payRate;
    @SerializedName("billRate")
    @Expose
    private Double billRate;
    @SerializedName("overtimePayRate")
    @Expose
    private Double overtimePayRate;
    @SerializedName("overtimeBillRate")
    @Expose
    private Double overtimeBillRate;
    @SerializedName("doublePayRate")
    @Expose
    private Double doublePayRate;
    @SerializedName("doubleBillRate")
    @Expose
    private Double doubleBillRate;
    @SerializedName("statusSequence")
    @Expose
    private Double statusSequence;
    @SerializedName("isDeleted")
    @Expose
    private Boolean isDeleted;
    @SerializedName("offerSent")
    @Expose
    private Boolean offerSent;
    @SerializedName("templateId")
    @Expose
    private Object templateId;
    @SerializedName("offerLetterLink")
    @Expose
    private Object offerLetterLink;
    @SerializedName("lastStatusId")
    @Expose
    private Integer lastStatusId;
    @SerializedName("lastStatus")
    @Expose
    private String lastStatus;
    @SerializedName("lastStatusColorCode")
    @Expose
    private String lastStatusColorCode;
    @SerializedName("joiningDate")
    @Expose
    private Object joiningDate;
    @SerializedName("offeredSalary")
    @Expose
    private Object offeredSalary;
    @SerializedName("jobGuid")
    @Expose
    private Object jobGuid;

    public Integer getCandidateJobId() {
        return candidateJobId;
    }

    public void setCandidateJobId(Integer candidateJobId) {
        this.candidateJobId = candidateJobId;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getCandidateId() {
        return candidateId;
    }

    public void setCandidateId(Integer candidateId) {
        this.candidateId = candidateId;
    }

    public Integer getJobId() {
        return jobId;
    }

    public void setJobId(Integer jobId) {
        this.jobId = jobId;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public Object getJobStatusId() {
        return jobStatusId;
    }

    public void setJobStatusId(Object jobStatusId) {
        this.jobStatusId = jobStatusId;
    }

    public String getJobStatus() {
        return jobStatus;
    }

    public void setJobStatus(String jobStatus) {
        this.jobStatus = jobStatus;
    }

    public String getColorCode() {
        return colorCode;
    }

    public void setColorCode(String colorCode) {
        this.colorCode = colorCode;
    }

    public Object getClientId() {
        return clientId;
    }

    public void setClientId(Object clientId) {
        this.clientId = clientId;
    }

    public String getClientProfilePicture() {
        return clientProfilePicture;
    }

    public void setClientProfilePicture(String clientProfilePicture) {
        this.clientProfilePicture = clientProfilePicture;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    public Object getCountry() {
        return country;
    }

    public void setCountry(Object country) {
        this.country = country;
    }

    public Object getZipcode() {
        return zipcode;
    }

    public void setZipcode(Object zipcode) {
        this.zipcode = zipcode;
    }

    public Object getCity() {
        return city;
    }

    public void setCity(Object city) {
        this.city = city;
    }

    public Object getState() {
        return state;
    }

    public void setState(Object state) {
        this.state = state;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getJobNature() {
        return jobNature;
    }

    public void setJobNature(String jobNature) {
        this.jobNature = jobNature;
    }

    public Integer getStatusId() {
        return statusId;
    }

    public void setStatusId(Integer statusId) {
        this.statusId = statusId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusColorCode() {
        return statusColorCode;
    }

    public void setStatusColorCode(String statusColorCode) {
        this.statusColorCode = statusColorCode;
    }

    public Double getPayRate() {
        return payRate;
    }

    public void setPayRate(Double payRate) {
        this.payRate = payRate;
    }

    public Double getBillRate() {
        return billRate;
    }

    public void setBillRate(Double billRate) {
        this.billRate = billRate;
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

    public Double getDoublePayRate() {
        return doublePayRate;
    }

    public void setDoublePayRate(Double doublePayRate) {
        this.doublePayRate = doublePayRate;
    }

    public Double getDoubleBillRate() {
        return doubleBillRate;
    }

    public void setDoubleBillRate(Double doubleBillRate) {
        this.doubleBillRate = doubleBillRate;
    }

    public Double getStatusSequence() {
        return statusSequence;
    }

    public void setStatusSequence(Double statusSequence) {
        this.statusSequence = statusSequence;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Boolean getOfferSent() {
        return offerSent;
    }

    public void setOfferSent(Boolean offerSent) {
        this.offerSent = offerSent;
    }

    public Object getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Object templateId) {
        this.templateId = templateId;
    }

    public Object getOfferLetterLink() {
        return offerLetterLink;
    }

    public void setOfferLetterLink(Object offerLetterLink) {
        this.offerLetterLink = offerLetterLink;
    }

    public Integer getLastStatusId() {
        return lastStatusId;
    }

    public void setLastStatusId(Integer lastStatusId) {
        this.lastStatusId = lastStatusId;
    }

    public String getLastStatus() {
        return lastStatus;
    }

    public void setLastStatus(String lastStatus) {
        this.lastStatus = lastStatus;
    }

    public String getLastStatusColorCode() {
        return lastStatusColorCode;
    }

    public void setLastStatusColorCode(String lastStatusColorCode) {
        this.lastStatusColorCode = lastStatusColorCode;
    }

    public Object getJoiningDate() {
        return joiningDate;
    }

    public void setJoiningDate(Object joiningDate) {
        this.joiningDate = joiningDate;
    }

    public Object getOfferedSalary() {
        return offeredSalary;
    }

    public void setOfferedSalary(Object offeredSalary) {
        this.offeredSalary = offeredSalary;
    }

    public Object getJobGuid() {
        return jobGuid;
    }

    public void setJobGuid(Object jobGuid) {
        this.jobGuid = jobGuid;
    }

}
