package pep.per.mint.common.data.basic.app;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import pep.per.mint.common.data.CacheableObject;

/**
 * Created by Solution TF on 15. 12. 30..
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")
public class AppMessage extends CacheableObject {


    @JsonProperty
    String msgId = defaultStringValue;

    @JsonProperty
    String langId = defaultStringValue;

    @JsonProperty
    String tag = defaultStringValue;

    @JsonProperty
    String msg = defaultStringValue;

    @JsonProperty("delYn")
    String delYn = "N";

    @JsonProperty("regDate")
    String regDate = defaultStringValue;

    @JsonProperty("regId")
    String regId = defaultStringValue;

    @JsonProperty("modDate")
    String modDate = defaultStringValue;

    @JsonProperty("modId")
    String modId = defaultStringValue;

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public String getLangId() {
        return langId;
    }

    public void setLangId(String langId) {
        this.langId = langId;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
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
}
