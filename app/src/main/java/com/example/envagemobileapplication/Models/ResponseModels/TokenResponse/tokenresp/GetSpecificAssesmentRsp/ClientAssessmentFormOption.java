
package com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetSpecificAssesmentRsp;

 import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

 public class ClientAssessmentFormOption {

    @SerializedName("clientAssessmentFormOptionsId")
    @Expose
    private Integer clientAssessmentFormOptionsId;
    @SerializedName("clientAssessmentFormQuestionId")
    @Expose
    private Integer clientAssessmentFormQuestionId;
    @SerializedName("optionIndex")
    @Expose
    private Object optionIndex;
    @SerializedName("content")
    @Expose
    private String content;
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

    public Integer getClientAssessmentFormOptionsId() {
        return clientAssessmentFormOptionsId;
    }

    public void setClientAssessmentFormOptionsId(Integer clientAssessmentFormOptionsId) {
        this.clientAssessmentFormOptionsId = clientAssessmentFormOptionsId;
    }

    public Integer getClientAssessmentFormQuestionId() {
        return clientAssessmentFormQuestionId;
    }

    public void setClientAssessmentFormQuestionId(Integer clientAssessmentFormQuestionId) {
        this.clientAssessmentFormQuestionId = clientAssessmentFormQuestionId;
    }

    public Object getOptionIndex() {
        return optionIndex;
    }

    public void setOptionIndex(Object optionIndex) {
        this.optionIndex = optionIndex;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

}
