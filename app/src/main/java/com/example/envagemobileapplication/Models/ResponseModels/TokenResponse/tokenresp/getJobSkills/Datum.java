
package com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.getJobSkills;

 import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

 public class Datum {

    @SerializedName("skillId")
    @Expose
    private Integer skillId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("skillRating")
    @Expose
    private String skillRating;
    @SerializedName("isDeleted")
    @Expose
    private Boolean isDeleted;

    public Integer getSkillId() {
        return skillId;
    }

    public void setSkillId(Integer skillId) {
        this.skillId = skillId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSkillRating() {
        return skillRating;
    }

    public void setSkillRating(String skillRating) {
        this.skillRating = skillRating;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

}
