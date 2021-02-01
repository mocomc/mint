package pep.per.mint.common.data.basic;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import pep.per.mint.common.data.CacheableObject;

/**
 * Created by Solution TF on 15. 11. 16..
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")
public class Tooltip extends CacheableObject{

    @JsonProperty
    String appId = defaultStringValue;

    @JsonProperty
    String appNm = defaultStringValue;

    @JsonProperty
    String appComments = defaultStringValue;


    @JsonProperty
    String tooltipId = defaultStringValue;

    @JsonProperty
    String langId = defaultStringValue;

    @JsonProperty
    String targetId = defaultStringValue;

    @JsonProperty
    String filterId = defaultStringValue;

    @JsonProperty
    String subject = defaultStringValue;

    @JsonProperty
    String contents = defaultStringValue;

    @JsonProperty
    String delYn = "N";

    @JsonProperty
    String regDate = defaultStringValue;

    @JsonProperty
    String regId = defaultStringValue;

    @JsonProperty
    String modDate = defaultStringValue;

    @JsonProperty
    String modId = defaultStringValue;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getTooltipId() {
        return tooltipId;
    }

    public void setTooltipId(String tooltipId) {
        this.tooltipId = tooltipId;
    }

    public String getLangId() {
        return langId;
    }

    public void setLangId(String langId) {
        this.langId = langId;
    }

    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }

    public String getFilterId() {
        return filterId;
    }

    public void setFilterId(String filterId) {
        this.filterId = filterId;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getDelYn() {
        return delYn;
    }

    public void setDelYn(String delYn) {
        this.delYn = delYn;
    }

    public String getRegDate() {
        return regDate;
    }

    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }

    public String getRegId() {
        return regId;
    }

    public void setRegId(String regId) {
        this.regId = regId;
    }

    public String getModDate() {
        return modDate;
    }

    public void setModDate(String modDate) {
        this.modDate = modDate;
    }

    public String getModId() {
        return modId;
    }

    public void setModId(String modId) {
        this.modId = modId;
    }

    public String getAppNm() {
        return appNm;
    }

    public void setAppNm(String appNm) {
        this.appNm = appNm;
    }

    public String getAppComments() {
        return appComments;
    }

    public void setAppComments(String appComments) {
        this.appComments = appComments;
    }
}
