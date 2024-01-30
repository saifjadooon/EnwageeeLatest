
package com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetAllSmsResp;

 import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

 public class Record {

    @SerializedName("body")
    @Expose
    private String body;
    @SerializedName("num_segments")
    @Expose
    private String numSegments;
    @SerializedName("direction")
    @Expose
    private String direction;
    @SerializedName("from")
    @Expose
    private String from;
    @SerializedName("to")
    @Expose
    private String to;
    @SerializedName("date_updated")
    @Expose
    private String dateUpdated;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("error_message")
    @Expose
    private Object errorMessage;
    @SerializedName("uri")
    @Expose
    private String uri;
    @SerializedName("account_sid")
    @Expose
    private String accountSid;
    @SerializedName("num_media")
    @Expose
    private String numMedia;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("messaging_service_sid")
    @Expose
    private Object messagingServiceSid;
    @SerializedName("sid")
    @Expose
    private String sid;
    @SerializedName("date_sent")
    @Expose
    private String dateSent;
    @SerializedName("date_created")
    @Expose
    private String dateCreated;
    @SerializedName("error_code")
    @Expose
    private Object errorCode;
    @SerializedName("price_unit")
    @Expose
    private String priceUnit;
    @SerializedName("api_version")
    @Expose
    private String apiVersion;
    @SerializedName("subresource_uris")
    @Expose
    private SubresourceUris subresourceUris;

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getNumSegments() {
        return numSegments;
    }

    public void setNumSegments(String numSegments) {
        this.numSegments = numSegments;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getDateUpdated() {
        return dateUpdated;
    }

    public void setDateUpdated(String dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Object getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(Object errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getAccountSid() {
        return accountSid;
    }

    public void setAccountSid(String accountSid) {
        this.accountSid = accountSid;
    }

    public String getNumMedia() {
        return numMedia;
    }

    public void setNumMedia(String numMedia) {
        this.numMedia = numMedia;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getMessagingServiceSid() {
        return messagingServiceSid;
    }

    public void setMessagingServiceSid(Object messagingServiceSid) {
        this.messagingServiceSid = messagingServiceSid;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getDateSent() {
        return dateSent;
    }

    public void setDateSent(String dateSent) {
        this.dateSent = dateSent;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Object getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Object errorCode) {
        this.errorCode = errorCode;
    }

    public String getPriceUnit() {
        return priceUnit;
    }

    public void setPriceUnit(String priceUnit) {
        this.priceUnit = priceUnit;
    }

    public String getApiVersion() {
        return apiVersion;
    }

    public void setApiVersion(String apiVersion) {
        this.apiVersion = apiVersion;
    }

    public SubresourceUris getSubresourceUris() {
        return subresourceUris;
    }

    public void setSubresourceUris(SubresourceUris subresourceUris) {
        this.subresourceUris = subresourceUris;
    }

}
