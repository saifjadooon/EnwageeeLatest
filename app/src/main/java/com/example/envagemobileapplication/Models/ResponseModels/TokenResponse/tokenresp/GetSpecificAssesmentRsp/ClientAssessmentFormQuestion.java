
package com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetSpecificAssesmentRsp;

import java.util.List;
 import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

 public class ClientAssessmentFormQuestion {

    @SerializedName("clientAssessmentFormQuestionId")
    @Expose
    private Integer clientAssessmentFormQuestionId;
    @SerializedName("clientAssessmentFormSectionId")
    @Expose
    private Integer clientAssessmentFormSectionId;
    @SerializedName("content")
    @Expose
    private String content;
    @SerializedName("expectedAnswer")
    @Expose
    private Integer expectedAnswer;
    @SerializedName("selectedAnswer")
    @Expose
    private Object selectedAnswer;
    @SerializedName("score")
    @Expose
    private Double score;
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
    @SerializedName("clientAssessmentFormOptions")
    @Expose
    private List<ClientAssessmentFormOption> clientAssessmentFormOptions;

    public Integer getClientAssessmentFormQuestionId() {
        return clientAssessmentFormQuestionId;
    }

    public void setClientAssessmentFormQuestionId(Integer clientAssessmentFormQuestionId) {
        this.clientAssessmentFormQuestionId = clientAssessmentFormQuestionId;
    }

    public Integer getClientAssessmentFormSectionId() {
        return clientAssessmentFormSectionId;
    }

    public void setClientAssessmentFormSectionId(Integer clientAssessmentFormSectionId) {
        this.clientAssessmentFormSectionId = clientAssessmentFormSectionId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getExpectedAnswer() {
        return expectedAnswer;
    }

    public void setExpectedAnswer(Integer expectedAnswer) {
        this.expectedAnswer = expectedAnswer;
    }

    public Object getSelectedAnswer() {
        return selectedAnswer;
    }

    public void setSelectedAnswer(Object selectedAnswer) {
        this.selectedAnswer = selectedAnswer;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
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

    public List<ClientAssessmentFormOption> getClientAssessmentFormOptions() {
        return clientAssessmentFormOptions;
    }

    public void setClientAssessmentFormOptions(List<ClientAssessmentFormOption> clientAssessmentFormOptions) {
        this.clientAssessmentFormOptions = clientAssessmentFormOptions;
    }

}
