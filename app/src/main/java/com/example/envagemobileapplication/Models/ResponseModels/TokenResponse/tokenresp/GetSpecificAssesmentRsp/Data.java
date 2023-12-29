
package com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetSpecificAssesmentRsp;

import java.util.List;
 import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

 public class Data {

    @SerializedName("clientAssessmentFormId")
    @Expose
    private Integer clientAssessmentFormId;
    @SerializedName("clientId")
    @Expose
    private Integer clientId;
    @SerializedName("jobId")
    @Expose
    private Object jobId;
    @SerializedName("companyId")
    @Expose
    private Integer companyId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("timer")
    @Expose
    private String timer;
    @SerializedName("passingCriteria")
    @Expose
    private Integer passingCriteria;
    @SerializedName("urlExpiryTime")
    @Expose
    private String urlExpiryTime;
    @SerializedName("showCompanyLogo")
    @Expose
    private Boolean showCompanyLogo;
    @SerializedName("showEnwageWatermark")
    @Expose
    private Boolean showEnwageWatermark;
    @SerializedName("isDeleted")
    @Expose
    private Boolean isDeleted;
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
    @SerializedName("clientAssessmentFormSections")
    @Expose
    private List<ClientAssessmentFormSection> clientAssessmentFormSections;

    public Integer getClientAssessmentFormId() {
        return clientAssessmentFormId;
    }

    public void setClientAssessmentFormId(Integer clientAssessmentFormId) {
        this.clientAssessmentFormId = clientAssessmentFormId;
    }

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public Object getJobId() {
        return jobId;
    }

    public void setJobId(Object jobId) {
        this.jobId = jobId;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTimer() {
        return timer;
    }

    public void setTimer(String timer) {
        this.timer = timer;
    }

    public Integer getPassingCriteria() {
        return passingCriteria;
    }

    public void setPassingCriteria(Integer passingCriteria) {
        this.passingCriteria = passingCriteria;
    }

    public String getUrlExpiryTime() {
        return urlExpiryTime;
    }

    public void setUrlExpiryTime(String urlExpiryTime) {
        this.urlExpiryTime = urlExpiryTime;
    }

    public Boolean getShowCompanyLogo() {
        return showCompanyLogo;
    }

    public void setShowCompanyLogo(Boolean showCompanyLogo) {
        this.showCompanyLogo = showCompanyLogo;
    }

    public Boolean getShowEnwageWatermark() {
        return showEnwageWatermark;
    }

    public void setShowEnwageWatermark(Boolean showEnwageWatermark) {
        this.showEnwageWatermark = showEnwageWatermark;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
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

    public List<ClientAssessmentFormSection> getClientAssessmentFormSections() {
        return clientAssessmentFormSections;
    }

    public void setClientAssessmentFormSections(List<ClientAssessmentFormSection> clientAssessmentFormSections) {
        this.clientAssessmentFormSections = clientAssessmentFormSections;
    }

}
