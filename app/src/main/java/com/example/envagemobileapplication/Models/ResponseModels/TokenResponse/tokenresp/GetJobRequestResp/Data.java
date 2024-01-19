
package com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetJobRequestResp;

import java.util.List;
 import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

 public class Data {

    @SerializedName("jobRequestId")
    @Expose
    private Integer jobRequestId;
    @SerializedName("companyId")
    @Expose
    private Integer companyId;
    @SerializedName("clientId")
    @Expose
    private Integer clientId;
    @SerializedName("clientName")
    @Expose
    private String clientName;
    @SerializedName("owner")
    @Expose
    private Integer owner;
    @SerializedName("ownerName")
    @Expose
    private String ownerName;
    @SerializedName("industryId")
    @Expose
    private Integer industryId;
    @SerializedName("jobType")
    @Expose
    private String jobType;
    @SerializedName("jobFrequency")
    @Expose
    private Object jobFrequency;
    @SerializedName("positionName")
    @Expose
    private String positionName;
    @SerializedName("jobStatusId")
    @Expose
    private Integer jobStatusId;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("colorCode")
    @Expose
    private String colorCode;
    @SerializedName("jobNature")
    @Expose
    private String jobNature;
    @SerializedName("address1")
    @Expose
    private String address1;
    @SerializedName("address2")
    @Expose
    private String address2;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("zipcode")
    @Expose
    private String zipcode;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("state")
    @Expose
    private String state;
    @SerializedName("location")
    @Expose
    private Object location;
    @SerializedName("headcount")
    @Expose
    private Integer headcount;
    @SerializedName("startDate")
    @Expose
    private String startDate;
    @SerializedName("endDate")
    @Expose
    private Object endDate;
    @SerializedName("currency")
    @Expose
    private String currency;
    @SerializedName("minimumSalary")
    @Expose
    private Double minimumSalary;
    @SerializedName("maximumSalary")
    @Expose
    private Double maximumSalary;
    @SerializedName("workingDays")
    @Expose
    private List<String> workingDays;
    @SerializedName("workingDaysNo")
    @Expose
    private Integer workingDaysNo;
    @SerializedName("estimatedHours")
    @Expose
    private Integer estimatedHours;
    @SerializedName("jobDescription")
    @Expose
    private String jobDescription;
    @SerializedName("packageDescription")
    @Expose
    private Object packageDescription;
    @SerializedName("isPublish")
    @Expose
    private Object isPublish;
    @SerializedName("attachmentName")
    @Expose
    private Object attachmentName;
    @SerializedName("attachmentPath")
    @Expose
    private Object attachmentPath;
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
    @SerializedName("remarks")
    @Expose
    private String remarks;
    @SerializedName("payrollPayGroupId")
    @Expose
    private Integer payrollPayGroupId;
    @SerializedName("payGroupName")
    @Expose
    private String payGroupName;
    @SerializedName("billingDetails")
    @Expose
    private BillingDetails billingDetails;

    public Integer getJobRequestId() {
        return jobRequestId;
    }

    public void setJobRequestId(Integer jobRequestId) {
        this.jobRequestId = jobRequestId;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public Integer getOwner() {
        return owner;
    }

    public void setOwner(Integer owner) {
        this.owner = owner;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public Integer getIndustryId() {
        return industryId;
    }

    public void setIndustryId(Integer industryId) {
        this.industryId = industryId;
    }

    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    public Object getJobFrequency() {
        return jobFrequency;
    }

    public void setJobFrequency(Object jobFrequency) {
        this.jobFrequency = jobFrequency;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public Integer getJobStatusId() {
        return jobStatusId;
    }

    public void setJobStatusId(Integer jobStatusId) {
        this.jobStatusId = jobStatusId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getColorCode() {
        return colorCode;
    }

    public void setColorCode(String colorCode) {
        this.colorCode = colorCode;
    }

    public String getJobNature() {
        return jobNature;
    }

    public void setJobNature(String jobNature) {
        this.jobNature = jobNature;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Object getLocation() {
        return location;
    }

    public void setLocation(Object location) {
        this.location = location;
    }

    public Integer getHeadcount() {
        return headcount;
    }

    public void setHeadcount(Integer headcount) {
        this.headcount = headcount;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public Object getEndDate() {
        return endDate;
    }

    public void setEndDate(Object endDate) {
        this.endDate = endDate;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Double getMinimumSalary() {
        return minimumSalary;
    }

    public void setMinimumSalary(Double minimumSalary) {
        this.minimumSalary = minimumSalary;
    }

    public Double getMaximumSalary() {
        return maximumSalary;
    }

    public void setMaximumSalary(Double maximumSalary) {
        this.maximumSalary = maximumSalary;
    }

    public List<String> getWorkingDays() {
        return workingDays;
    }

    public void setWorkingDays(List<String> workingDays) {
        this.workingDays = workingDays;
    }

    public Integer getWorkingDaysNo() {
        return workingDaysNo;
    }

    public void setWorkingDaysNo(Integer workingDaysNo) {
        this.workingDaysNo = workingDaysNo;
    }

    public Integer getEstimatedHours() {
        return estimatedHours;
    }

    public void setEstimatedHours(Integer estimatedHours) {
        this.estimatedHours = estimatedHours;
    }

    public String getJobDescription() {
        return jobDescription;
    }

    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
    }

    public Object getPackageDescription() {
        return packageDescription;
    }

    public void setPackageDescription(Object packageDescription) {
        this.packageDescription = packageDescription;
    }

    public Object getIsPublish() {
        return isPublish;
    }

    public void setIsPublish(Object isPublish) {
        this.isPublish = isPublish;
    }

    public Object getAttachmentName() {
        return attachmentName;
    }

    public void setAttachmentName(Object attachmentName) {
        this.attachmentName = attachmentName;
    }

    public Object getAttachmentPath() {
        return attachmentPath;
    }

    public void setAttachmentPath(Object attachmentPath) {
        this.attachmentPath = attachmentPath;
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

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Integer getPayrollPayGroupId() {
        return payrollPayGroupId;
    }

    public void setPayrollPayGroupId(Integer payrollPayGroupId) {
        this.payrollPayGroupId = payrollPayGroupId;
    }

    public String getPayGroupName() {
        return payGroupName;
    }

    public void setPayGroupName(String payGroupName) {
        this.payGroupName = payGroupName;
    }

    public BillingDetails getBillingDetails() {
        return billingDetails;
    }

    public void setBillingDetails(BillingDetails billingDetails) {
        this.billingDetails = billingDetails;
    }

}
