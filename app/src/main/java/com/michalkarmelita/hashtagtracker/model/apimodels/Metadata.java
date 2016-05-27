
package com.michalkarmelita.hashtagtracker.model.apimodels;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Metadata {

    private String isoLanguageCode;
    private String resultType;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The isoLanguageCode
     */
    public String getIsoLanguageCode() {
        return isoLanguageCode;
    }

    /**
     * 
     * @param isoLanguageCode
     *     The iso_language_code
     */
    public void setIsoLanguageCode(String isoLanguageCode) {
        this.isoLanguageCode = isoLanguageCode;
    }

    /**
     * 
     * @return
     *     The resultType
     */
    public String getResultType() {
        return resultType;
    }

    /**
     * 
     * @param resultType
     *     The result_type
     */
    public void setResultType(String resultType) {
        this.resultType = resultType;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
