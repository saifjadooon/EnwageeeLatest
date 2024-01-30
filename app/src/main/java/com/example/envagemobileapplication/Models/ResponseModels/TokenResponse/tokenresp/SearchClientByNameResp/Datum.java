
package com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.SearchClientByNameResp;

 import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

 public class Datum {

    @SerializedName("clientId")
    @Expose
    private Integer clientId;
    @SerializedName("companyId")
    @Expose
    private Integer companyId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("industryName")
    @Expose
    private Object industryName;
    @SerializedName("profilePicture")
    @Expose
    private String profilePicture;
    @SerializedName("groupName")
    @Expose
    private String groupName;

    public Integer getClientId() {
        return clientId;
    }

    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getIndustryName() {
        return industryName;
    }

    public void setIndustryName(Object industryName) {
        this.industryName = industryName;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

}
