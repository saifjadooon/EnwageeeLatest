
package com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetAllSmsResp;

 import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

 public class SubresourceUris {

    @SerializedName("media")
    @Expose
    private String media;
    @SerializedName("feedback")
    @Expose
    private String feedback;

    public String getMedia() {
        return media;
    }

    public void setMedia(String media) {
        this.media = media;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

}
