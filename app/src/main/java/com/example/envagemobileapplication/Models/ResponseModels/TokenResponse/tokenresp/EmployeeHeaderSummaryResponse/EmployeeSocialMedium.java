
package com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.EmployeeHeaderSummaryResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EmployeeSocialMedium {

    @SerializedName("employeeSocialMediaId")
    @Expose
    private Integer employeeSocialMediaId;
    @SerializedName("employeeId")
    @Expose
    private Integer employeeId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("logoPath")
    @Expose
    private Object logoPath;
    @SerializedName("url")
    @Expose
    private Object url;
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
    @SerializedName("isActive")
    @Expose
    private Boolean isActive;

    public Integer getEmployeeSocialMediaId() {
        return employeeSocialMediaId;
    }

    public void setEmployeeSocialMediaId(Integer employeeSocialMediaId) {
        this.employeeSocialMediaId = employeeSocialMediaId;
    }

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getLogoPath() {
        return logoPath;
    }

    public void setLogoPath(Object logoPath) {
        this.logoPath = logoPath;
    }

    public Object getUrl() {
        return url;
    }

    public void setUrl(Object url) {
        this.url = url;
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

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

}
