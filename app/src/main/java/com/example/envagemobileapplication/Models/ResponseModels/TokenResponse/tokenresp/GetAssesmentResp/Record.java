
package com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetAssesmentResp;

 import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

 public class Record {

    @SerializedName("assignedAssessmentFormId")
    @Expose
    private Integer assignedAssessmentFormId;
    @SerializedName("clientId")
    @Expose
    private Integer clientId;
    @SerializedName("jobId")
    @Expose
    private Integer jobId;
    @SerializedName("candidateId")
    @Expose
    private Integer candidateId;
    @SerializedName("clientAssessmentFormId")
    @Expose
    private Integer clientAssessmentFormId;
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
    @SerializedName("isDeleted")
    @Expose
    private Boolean isDeleted;
    @SerializedName("isActive")
    @Expose
    private Boolean isActive;
    @SerializedName("expiryTime")
    @Expose
    private String expiryTime;
    @SerializedName("isExpired")
    @Expose
    private Boolean isExpired;
    @SerializedName("totalMarks")
    @Expose
    private Double totalMarks;
    @SerializedName("achievedMarks")
    @Expose
    private Double achievedMarks;
    @SerializedName("passingCriteria")
    @Expose
    private Integer passingCriteria;
    @SerializedName("passed")
    @Expose
    private Boolean passed;
    @SerializedName("candidate")
    @Expose
    private Object candidate;
    @SerializedName("client")
    @Expose
    private Client client;
    @SerializedName("clientAssessmentForm")
    @Expose
    private ClientAssessmentForm clientAssessmentForm;
    @SerializedName("job")
    @Expose
    private Job job;

    public Integer getAssignedAssessmentFormId() {
        return assignedAssessmentFormId;
    }

    public void setAssignedAssessmentFormId(Integer assignedAssessmentFormId) {
        this.assignedAssessmentFormId = assignedAssessmentFormId;
    }

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public Integer getJobId() {
        return jobId;
    }

    public void setJobId(Integer jobId) {
        this.jobId = jobId;
    }

    public Integer getCandidateId() {
        return candidateId;
    }

    public void setCandidateId(Integer candidateId) {
        this.candidateId = candidateId;
    }

    public Integer getClientAssessmentFormId() {
        return clientAssessmentFormId;
    }

    public void setClientAssessmentFormId(Integer clientAssessmentFormId) {
        this.clientAssessmentFormId = clientAssessmentFormId;
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

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public String getExpiryTime() {
        return expiryTime;
    }

    public void setExpiryTime(String expiryTime) {
        this.expiryTime = expiryTime;
    }

    public Boolean getIsExpired() {
        return isExpired;
    }

    public void setIsExpired(Boolean isExpired) {
        this.isExpired = isExpired;
    }

    public Double getTotalMarks() {
        return totalMarks;
    }

    public void setTotalMarks(Double totalMarks) {
        this.totalMarks = totalMarks;
    }

    public Double getAchievedMarks() {
        return achievedMarks;
    }

    public void setAchievedMarks(Double achievedMarks) {
        this.achievedMarks = achievedMarks;
    }

    public Integer getPassingCriteria() {
        return passingCriteria;
    }

    public void setPassingCriteria(Integer passingCriteria) {
        this.passingCriteria = passingCriteria;
    }

    public Boolean getPassed() {
        return passed;
    }

    public void setPassed(Boolean passed) {
        this.passed = passed;
    }

    public Object getCandidate() {
        return candidate;
    }

    public void setCandidate(Object candidate) {
        this.candidate = candidate;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public ClientAssessmentForm getClientAssessmentForm() {
        return clientAssessmentForm;
    }

    public void setClientAssessmentForm(ClientAssessmentForm clientAssessmentForm) {
        this.clientAssessmentForm = clientAssessmentForm;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

}
