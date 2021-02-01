package pep.per.mint.common.data.basic.chat;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import pep.per.mint.common.data.CacheableObject;
import pep.per.mint.common.util.Util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Solution TF on 15. 12. 18..
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")
public class ChatMessage extends CacheableObject{

    @JsonProperty
    String msgId = defaultStringValue;

    @JsonProperty
    String msgDate = Util.getFormatedDate();

    @JsonProperty
    String senderId = defaultStringValue;

    @JsonProperty
    String reservationDate = defaultStringValue;

    @JsonProperty
    String completeYn = "N";

    @JsonProperty
    String delYn = "N";

    @JsonProperty
    String msg = defaultStringValue;

    List<ReceiverInfo> receiverInfoList = new ArrayList<ReceiverInfo>();

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public String getMsgDate() {
        return msgDate;
    }

    public void setMsgDate(String msgDate) {
        this.msgDate = msgDate;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(String reservationDate) {
        this.reservationDate = reservationDate;
    }

    public String getCompleteYn() {
        return completeYn;
    }

    public void setCompleteYn(String completeYn) {
        this.completeYn = completeYn;
    }

    public String getDelYn() {
        return delYn;
    }

    public void setDelYn(String delYn) {
        this.delYn = delYn;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<ReceiverInfo> getReceiverInfoList() {
        return receiverInfoList;
    }

    public void setReceiverInfoList(List<ReceiverInfo> receiverInfoList) {
        this.receiverInfoList = receiverInfoList;
    }
}
