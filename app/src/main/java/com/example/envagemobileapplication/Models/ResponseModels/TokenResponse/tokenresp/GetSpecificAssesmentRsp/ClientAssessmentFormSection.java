
package com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetSpecificAssesmentRsp;

import java.util.List;
 import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

 public class ClientAssessmentFormSection {

    @SerializedName("clientAssessmentFormSectionId")
    @Expose
    private Integer clientAssessmentFormSectionId;
    @SerializedName("clientAssessmentFormId")
    @Expose
    private Integer clientAssessmentFormId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("weightage")
    @Expose
    private Double weightage;
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
    @SerializedName("clientAssessmentFormQuestions")
    @Expose
    private List<ClientAssessmentFormQuestion> clientAssessmentFormQuestions;

    public Integer getClientAssessmentFormSectionId() {
        return clientAssessmentFormSectionId;
    }

    public void setClientAssessmentFormSectionId(Integer clientAssessmentFormSectionId) {
        this.clientAssessmentFormSectionId = clientAssessmentFormSectionId;
    }

    public Integer getClientAssessmentFormId() {
        return clientAssessmentFormId;
    }

    public void setClientAssessmentFormId(Integer clientAssessmentFormId) {
        this.clientAssessmentFormId = clientAssessmentFormId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getWeightage() {
        return weightage;
    }

    public void setWeightage(Double weightage) {
        this.weightage = weightage;
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

    public List<ClientAssessmentFormQuestion> getClientAssessmentFormQuestions() {
        return clientAssessmentFormQuestions;
    }

    public void setClientAssessmentFormQuestions(List<ClientAssessmentFormQuestion> clientAssessmentFormQuestions) {
        this.clientAssessmentFormQuestions = clientAssessmentFormQuestions;
    }

}
