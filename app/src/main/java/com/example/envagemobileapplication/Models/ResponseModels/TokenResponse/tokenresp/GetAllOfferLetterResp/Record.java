
package com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetAllOfferLetterResp;

 import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

 public class Record {

    @SerializedName("candidateOfferLetterId")
    @Expose
    private Integer candidateOfferLetterId;
    @SerializedName("candidateId")
    @Expose
    private Integer candidateId;
    @SerializedName("offerLetterFrom")
    @Expose
    private String offerLetterFrom;
    @SerializedName("offerLetterTo")
    @Expose
    private Object offerLetterTo;
    @SerializedName("offerLetterCc")
    @Expose
    private Object offerLetterCc;
    @SerializedName("offerLetterBcc")
    @Expose
    private Object offerLetterBcc;
    @SerializedName("subject")
    @Expose
    private String subject;
    @SerializedName("bodyPath")
    @Expose
    private String bodyPath;
    @SerializedName("status")
    @Expose
    private String status;
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
    @SerializedName("attachement1")
    @Expose
    private Object attachement1;
    @SerializedName("attachement2")
    @Expose
    private Object attachement2;
    @SerializedName("attachement3")
    @Expose
    private Object attachement3;
    @SerializedName("isActive")
    @Expose
    private Boolean isActive;
    @SerializedName("isDeleted")
    @Expose
    private Boolean isDeleted;
    @SerializedName("jobId")
    @Expose
    private Integer jobId;
    @SerializedName("candidateName")
    @Expose
    private String candidateName;
    @SerializedName("userName")
    @Expose
    private String userName;
    @SerializedName("joiningDate")
    @Expose
    private String joiningDate;
    @SerializedName("offeredSalary")
    @Expose
    private Object offeredSalary;

    public Integer getCandidateOfferLetterId() {
        return candidateOfferLetterId;
    }

    public void setCandidateOfferLetterId(Integer candidateOfferLetterId) {
        this.candidateOfferLetterId = candidateOfferLetterId;
    }

    public Integer getCandidateId() {
        return candidateId;
    }

    public void setCandidateId(Integer candidateId) {
        this.candidateId = candidateId;
    }

    public String getOfferLetterFrom() {
        return offerLetterFrom;
    }

    public void setOfferLetterFrom(String offerLetterFrom) {
        this.offerLetterFrom = offerLetterFrom;
    }

    public Object getOfferLetterTo() {
        return offerLetterTo;
    }

    public void setOfferLetterTo(Object offerLetterTo) {
        this.offerLetterTo = offerLetterTo;
    }

    public Object getOfferLetterCc() {
        return offerLetterCc;
    }

    public void setOfferLetterCc(Object offerLetterCc) {
        this.offerLetterCc = offerLetterCc;
    }

    public Object getOfferLetterBcc() {
        return offerLetterBcc;
    }

    public void setOfferLetterBcc(Object offerLetterBcc) {
        this.offerLetterBcc = offerLetterBcc;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBodyPath() {
        return bodyPath;
    }

    public void setBodyPath(String bodyPath) {
        this.bodyPath = bodyPath;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public Object getAttachement1() {
        return attachement1;
    }

    public void setAttachement1(Object attachement1) {
        this.attachement1 = attachement1;
    }

    public Object getAttachement2() {
        return attachement2;
    }

    public void setAttachement2(Object attachement2) {
        this.attachement2 = attachement2;
    }

    public Object getAttachement3() {
        return attachement3;
    }

    public void setAttachement3(Object attachement3) {
        this.attachement3 = attachement3;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Integer getJobId() {
        return jobId;
    }

    public void setJobId(Integer jobId) {
        this.jobId = jobId;
    }

    public String getCandidateName() {
        return candidateName;
    }

    public void setCandidateName(String candidateName) {
        this.candidateName = candidateName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getJoiningDate() {
        return joiningDate;
    }

    public void setJoiningDate(String joiningDate) {
        this.joiningDate = joiningDate;
    }

    public Object getOfferedSalary() {
        return offeredSalary;
    }

    public void setOfferedSalary(Object offeredSalary) {
        this.offeredSalary = offeredSalary;
    }

}
