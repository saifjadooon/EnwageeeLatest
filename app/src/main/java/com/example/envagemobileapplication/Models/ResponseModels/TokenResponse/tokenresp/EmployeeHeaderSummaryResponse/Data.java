
package com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.EmployeeHeaderSummaryResponse;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("employeeInfo")
    @Expose
    private EmployeeInfo employeeInfo;
    @SerializedName("daysAvailable")
    @Expose
    private DaysAvailable daysAvailable;
    @SerializedName("employeeSocialMedia")
    @Expose
    private List<EmployeeSocialMedium> employeeSocialMedia;
    @SerializedName("employeeBadges")
    @Expose
    private List<Object> employeeBadges;
    @SerializedName("employeeSkills")
    @Expose
    private List<Object> employeeSkills;

    public EmployeeInfo getEmployeeInfo() {
        return employeeInfo;
    }

    public void setEmployeeInfo(EmployeeInfo employeeInfo) {
        this.employeeInfo = employeeInfo;
    }

    public DaysAvailable getDaysAvailable() {
        return daysAvailable;
    }

    public void setDaysAvailable(DaysAvailable daysAvailable) {
        this.daysAvailable = daysAvailable;
    }

    public List<EmployeeSocialMedium> getEmployeeSocialMedia() {
        return employeeSocialMedia;
    }

    public void setEmployeeSocialMedia(List<EmployeeSocialMedium> employeeSocialMedia) {
        this.employeeSocialMedia = employeeSocialMedia;
    }

    public List<Object> getEmployeeBadges() {
        return employeeBadges;
    }

    public void setEmployeeBadges(List<Object> employeeBadges) {
        this.employeeBadges = employeeBadges;
    }

    public List<Object> getEmployeeSkills() {
        return employeeSkills;
    }

    public void setEmployeeSkills(List<Object> employeeSkills) {
        this.employeeSkills = employeeSkills;
    }

}
