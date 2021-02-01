package pep.per.mint.common.data.basic.chat;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import pep.per.mint.common.data.CacheableObject;

/**
 * Created by Solution TF on 15. 12. 18..
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")
public class ReceiverInfo extends CacheableObject {

    @JsonProperty
    String msgId = defaultStringValue;

    @JsonProperty
    String receiverId = defaultStringValue;

    @JsonProperty
    String receiveYn = "N";


    @JsonProperty
    String confirmYn = "N";


    @JsonProperty
    String receiveDate = defaultStringValue;



    @JsonProperty
    String confirmDate = defaultStringValue;


    public ReceiverInfo(){super();}


    public ReceiverInfo(String msgId, String receiverId) {
        this();
        this.msgId = msgId;
        this.receiverId = receiverId;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public String getReceiveYn() {
        return receiveYn;
    }

    public void setReceiveYn(String receiveYn) {
        this.receiveYn = receiveYn;
    }

    public String getConfirmYn() {
        return confirmYn;
    }

    public void setConfirmYn(String confirmYn) {
        this.confirmYn = confirmYn;
    }

    public String getReceiveDate() {
        return receiveDate;
    }

    public void setReceiveDate(String receiveDate) {
        this.receiveDate = receiveDate;
    }

    public String getConfirmDate() {
        return confirmDate;
    }

    public void setConfirmDate(String confirmDate) {
        this.confirmDate = confirmDate;
    }
}
