
package com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.EmployeeHeaderSummaryResponse;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DaysAvailable {

    @SerializedName("employeeDaysAvailableId")
    @Expose
    private Integer employeeDaysAvailableId;
    @SerializedName("employeeId")
    @Expose
    private Integer employeeId;
    @SerializedName("preferenceShift")
    @Expose
    private List<String> preferenceShift;
    @SerializedName("pereferedDays")
    @Expose
    private List<String> pereferedDays;
    @SerializedName("timeFrom")
    @Expose
    private String timeFrom;
    @SerializedName("timeTo")
    @Expose
    private String timeTo;

    public Integer getEmployeeDaysAvailableId() {
        return employeeDaysAvailableId;
    }

    public void setEmployeeDaysAvailableId(Integer employeeDaysAvailableId) {
        this.employeeDaysAvailableId = employeeDaysAvailableId;
    }

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public List<String> getPreferenceShift() {
        return preferenceShift;
    }

    public void setPreferenceShift(List<String> preferenceShift) {
        this.preferenceShift = preferenceShift;
    }

    public List<String> getPereferedDays() {
        return pereferedDays;
    }

    public void setPereferedDays(List<String> pereferedDays) {
        this.pereferedDays = pereferedDays;
    }

    public String getTimeFrom() {
        return timeFrom;
    }

    public void setTimeFrom(String timeFrom) {
        this.timeFrom = timeFrom;
    }

    public String getTimeTo() {
        return timeTo;
    }

    public void setTimeTo(String timeTo) {
        this.timeTo = timeTo;
    }

}
