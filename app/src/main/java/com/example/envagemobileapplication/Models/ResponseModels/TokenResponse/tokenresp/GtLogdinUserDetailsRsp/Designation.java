
package com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GtLogdinUserDetailsRsp;

 import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

 public class Designation {

    @SerializedName("designationId")
    @Expose
    private Integer designationId;
    @SerializedName("departmentId")
    @Expose
    private Integer departmentId;
    @SerializedName("companyId")
    @Expose
    private Integer companyId;
    @SerializedName("designationName")
    @Expose
    private String designationName;
    @SerializedName("departmentName")
    @Expose
    private Object departmentName;
    @SerializedName("createdBy")
    @Expose
    private Integer createdBy;
    @SerializedName("createdByName")
    @Expose
    private String createdByName;
    @SerializedName("createdDate")
    @Expose
    private String createdDate;
    @SerializedName("modifiedBy")
    @Expose
    private Integer modifiedBy;
    @SerializedName("modifiedByName")
    @Expose
    private String modifiedByName;
    @SerializedName("modifiedDate")
    @Expose
    private String modifiedDate;
    @SerializedName("isActive")
    @Expose
    private Boolean isActive;
    @SerializedName("isDeleted")
    @Expose
    private Boolean isDeleted;

    public Integer getDesignationId() {
        return designationId;
    }

    public void setDesignationId(Integer designationId) {
        this.designationId = designationId;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getDesignationName() {
        return designationName;
    }

    public void setDesignationName(String designationName) {
        this.designationName = designationName;
    }

    public Object getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(Object departmentName) {
        this.departmentName = departmentName;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public String getCreatedByName() {
        return createdByName;
    }

    public void setCreatedByName(String createdByName) {
        this.createdByName = createdByName;
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

    public String getModifiedByName() {
        return modifiedByName;
    }

    public void setModifiedByName(String modifiedByName) {
        this.modifiedByName = modifiedByName;
    }

    public String getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(String modifiedDate) {
        this.modifiedDate = modifiedDate;
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

}
