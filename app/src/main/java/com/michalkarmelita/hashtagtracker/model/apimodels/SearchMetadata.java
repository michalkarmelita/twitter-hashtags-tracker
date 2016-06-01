
package com.michalkarmelita.hashtagtracker.model.apimodels;

import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class SearchMetadata {

    @SerializedName("max_id")
    @Expose
    private Long maxId;
    @SerializedName("since_id")
    @Expose
    private Long sinceId;
    @SerializedName("refresh_url")
    @Expose
    private String refreshUrl;
    @SerializedName("next_results")
    @Expose
    private String nextResults;
    @SerializedName("count")
    @Expose
    private Long count;
    @SerializedName("completed_in")
    @Expose
    private Float completedIn;
    @SerializedName("since_id_str")
    @Expose
    private String sinceIdStr;
    @SerializedName("query")
    @Expose
    private String query;
    @SerializedName("max_id_str")
    @Expose
    private String maxIdStr;

    /**
     * 
     * @return
     *     The maxId
     */
    public Long getMaxId() {
        return maxId;
    }

    /**
     * 
     * @param maxId
     *     The max_id
     */
    public void setMaxId(Long maxId) {
        this.maxId = maxId;
    }

    /**
     * 
     * @return
     *     The sinceId
     */
    public Long getSinceId() {
        return sinceId;
    }

    /**
     * 
     * @param sinceId
     *     The since_id
     */
    public void setSinceId(Long sinceId) {
        this.sinceId = sinceId;
    }

    /**
     * 
     * @return
     *     The refreshUrl
     */
    public String getRefreshUrl() {
        return refreshUrl;
    }

    /**
     * 
     * @param refreshUrl
     *     The refresh_url
     */
    public void setRefreshUrl(String refreshUrl) {
        this.refreshUrl = refreshUrl;
    }

    /**
     * 
     * @return
     *     The nextResults
     */
    public String getNextResults() {
        return nextResults;
    }

    /**
     * 
     * @param nextResults
     *     The next_results
     */
    public void setNextResults(String nextResults) {
        this.nextResults = nextResults;
    }

    /**
     * 
     * @return
     *     The count
     */
    public Long getCount() {
        return count;
    }

    /**
     * 
     * @param count
     *     The count
     */
    public void setCount(Long count) {
        this.count = count;
    }

    /**
     * 
     * @return
     *     The completedIn
     */
    public Float getCompletedIn() {
        return completedIn;
    }

    /**
     * 
     * @param completedIn
     *     The completed_in
     */
    public void setCompletedIn(Float completedIn) {
        this.completedIn = completedIn;
    }

    /**
     * 
     * @return
     *     The sinceIdStr
     */
    public String getSinceIdStr() {
        return sinceIdStr;
    }

    /**
     * 
     * @param sinceIdStr
     *     The since_id_str
     */
    public void setSinceIdStr(String sinceIdStr) {
        this.sinceIdStr = sinceIdStr;
    }

    /**
     * 
     * @return
     *     The query
     */
    public String getQuery() {
        return query;
    }

    /**
     * 
     * @param query
     *     The query
     */
    public void setQuery(String query) {
        this.query = query;
    }

    /**
     * 
     * @return
     *     The maxIdStr
     */
    public String getMaxIdStr() {
        return maxIdStr;
    }

    /**
     * 
     * @param maxIdStr
     *     The max_id_str
     */
    public void setMaxIdStr(String maxIdStr) {
        this.maxIdStr = maxIdStr;
    }

}
