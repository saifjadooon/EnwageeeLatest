
package com.example.envagemobileapplication.Models.ResponseModels.TokenResponse.tokenresp.GetAllSmsResp;

import java.util.List;
 import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

 public class Data {

    @SerializedName("nextPageUrl")
    @Expose
    private String nextPageUrl;
    @SerializedName("previousPageUrl")
    @Expose
    private String previousPageUrl;
    @SerializedName("page")
    @Expose
    private Integer page;
    @SerializedName("pageSize")
    @Expose
    private Integer pageSize;
    @SerializedName("records")
    @Expose
    private List<Record> records;

    public String getNextPageUrl() {
        return nextPageUrl;
    }

    public void setNextPageUrl(String nextPageUrl) {
        this.nextPageUrl = nextPageUrl;
    }

    public String getPreviousPageUrl() {
        return previousPageUrl;
    }

    public void setPreviousPageUrl(String previousPageUrl) {
        this.previousPageUrl = previousPageUrl;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public List<Record> getRecords() {
        return records;
    }

    public void setRecords(List<Record> records) {
        this.records = records;
    }

}
