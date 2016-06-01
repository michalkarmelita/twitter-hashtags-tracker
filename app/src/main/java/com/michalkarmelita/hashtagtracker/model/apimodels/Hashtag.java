
package com.michalkarmelita.hashtagtracker.model.apimodels;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Hashtag {

    @SerializedName("text")
    @Expose
    private String text;
    @SerializedName("indices")
    @Expose
    private List<Long> indices = new ArrayList<Long>();

    /**
     * 
     * @return
     *     The text
     */
    public String getText() {
        return text;
    }

    /**
     * 
     * @param text
     *     The text
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * 
     * @return
     *     The indices
     */
    public List<Long> getIndices() {
        return indices;
    }

    /**
     * 
     * @param indices
     *     The indices
     */
    public void setIndices(List<Long> indices) {
        this.indices = indices;
    }

}
