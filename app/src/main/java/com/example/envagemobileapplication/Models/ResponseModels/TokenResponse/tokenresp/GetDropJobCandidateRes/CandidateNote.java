
package com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetDropJobCandidateRes;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class CandidateNote {

    @SerializedName("candidateNotesId")
    @Expose
    private Integer candidateNotesId;
    @SerializedName("candidateId")
    @Expose
    private Integer candidateId;
    @SerializedName("candidateName")
    @Expose
    private Object candidateName;
    @SerializedName("candidateProfileImage")
    @Expose
    private Object candidateProfileImage;
    @SerializedName("createdByProfilePic")
    @Expose
    private String createdByProfilePic;
    @SerializedName("positionName")
    @Expose
    private String positionName;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("createdByUserName")
    @Expose
    private Object createdByUserName;
    @SerializedName("createdBy")
    @Expose
    private Integer createdBy;
    @SerializedName("createdDate")
    @Expose
    private String createdDate;
    @SerializedName("modfiedBy")
    @Expose
    private Integer modfiedBy;
    @SerializedName("modifiedDate")
    @Expose
    private String modifiedDate;
    @SerializedName("isDeleted")
    @Expose
    private Boolean isDeleted;
    @SerializedName("reason")
    @Expose
    private String reason;
    @SerializedName("jobId")
    @Expose
    private Integer jobId;
    @SerializedName("isShared")
    @Expose
    private Boolean isShared;

    public Integer getCandidateNotesId() {
        return candidateNotesId;
    }

    public void setCandidateNotesId(Integer candidateNotesId) {
        this.candidateNotesId = candidateNotesId;
    }

    public Integer getCandidateId() {
        return candidateId;
    }

    public void setCandidateId(Integer candidateId) {
        this.candidateId = candidateId;
    }

    public Object getCandidateName() {
        return candidateName;
    }

    public void setCandidateName(Object candidateName) {
        this.candidateName = candidateName;
    }

    public Object getCandidateProfileImage() {
        return candidateProfileImage;
    }

    public void setCandidateProfileImage(Object candidateProfileImage) {
        this.candidateProfileImage = candidateProfileImage;
    }

    public String getCreatedByProfilePic() {
        return createdByProfilePic;
    }

    public void setCreatedByProfilePic(String createdByProfilePic) {
        this.createdByProfilePic = createdByProfilePic;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Object getCreatedByUserName() {
        return createdByUserName;
    }

    public void setCreatedByUserName(Object createdByUserName) {
        this.createdByUserName = createdByUserName;
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

    public Integer getModfiedBy() {
        return modfiedBy;
    }

    public void setModfiedBy(Integer modfiedBy) {
        this.modfiedBy = modfiedBy;
    }

    public String getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(String modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Integer getJobId() {
        return jobId;
    }

    public void setJobId(Integer jobId) {
        this.jobId = jobId;
    }

    public Boolean getIsShared() {
        return isShared;
    }

    public void setIsShared(Boolean isShared) {
        this.isShared = isShared;
    }

}
