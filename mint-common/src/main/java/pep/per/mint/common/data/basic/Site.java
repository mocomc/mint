/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pep.per.mint.common.data.basic;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import pep.per.mint.common.data.CacheableObject;

/**
 *
 * @author whoana
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")
public class Site extends CacheableObject{

    
    @JsonProperty
    private String siteId = defaultStringValue;
    
    @JsonProperty
    private String siteNm = defaultStringValue;
    
    @JsonProperty
    private String siteCode = defaultStringValue;
    
    @JsonProperty
    private String snm = defaultStringValue;

    @JsonProperty
    private String contractYn = defaultStringValue;
    
    /*** 설명 */
    @JsonProperty
    //@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
    private String comments = defaultStringValue;

    /*** 삭제여부 */
    @JsonProperty
    //@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
    private String delYn = "N";

    /*** 등록일 */
    @JsonProperty
    //@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
    private String regDate = defaultStringValue;

    /*** 등록자 */
    @JsonProperty
    //@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
    private String regId = defaultStringValue;

    /*** 수정일 */
    @JsonProperty
    //@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
    private String modDate = defaultStringValue;

    /*** 수정자 */
    @JsonProperty
    //@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
    private String modId = defaultStringValue;

    /**
     * @return the siteId
     */
    public String getSiteId() {
        return siteId;
    }

    /**
     * @param siteId the siteId to set
     */
    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    /**
     * @return the siteNm
     */
    public String getSiteNm() {
        return siteNm;
    }

    /**
     * @param siteNm the siteNm to set
     */
    public void setSiteNm(String siteNm) {
        this.siteNm = siteNm;
    }

    /**
     * @return the siteCode
     */
    public String getSiteCode() {
        return siteCode;
    }

    /**
     * @param siteCode the siteCode to set
     */
    public void setSiteCode(String siteCode) {
        this.siteCode = siteCode;
    }

    /**
     * @return the snm
     */
    public String getSnm() {
        return snm;
    }

    /**
     * @param snm the snm to set
     */
    public void setSnm(String snm) {
        this.snm = snm;
    }

    /**
     * @return the contractYn
     */
    public String getContractYn() {
        return contractYn;
    }

    /**
     * @param contractYn the contractYn to set
     */
    public void setContractYn(String contractYn) {
        this.contractYn = contractYn;
    }

    /**
     * @return the comments
     */
    public String getComments() {
        return comments;
    }

    /**
     * @param comments the comments to set
     */
    public void setComments(String comments) {
        this.comments = comments;
    }

    /**
     * @return the delYn
     */
    public String getDelYn() {
        return delYn;
    }

    /**
     * @param delYn the delYn to set
     */
    public void setDelYn(String delYn) {
        this.delYn = delYn;
    }

    /**
     * @return the regDate
     */
    public String getRegDate() {
        return regDate;
    }

    /**
     * @param regDate the regDate to set
     */
    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }

    /**
     * @return the regId
     */
    public String getRegId() {
        return regId;
    }

    /**
     * @param regId the regId to set
     */
    public void setRegId(String regId) {
        this.regId = regId;
    }

    /**
     * @return the modDate
     */
    public String getModDate() {
        return modDate;
    }

    /**
     * @param modDate the modDate to set
     */
    public void setModDate(String modDate) {
        this.modDate = modDate;
    }

    /**
     * @return the modId
     */
    public String getModId() {
        return modId;
    }

    /**
     * @param modId the modId to set
     */
    public void setModId(String modId) {
        this.modId = modId;
    }
    
}
