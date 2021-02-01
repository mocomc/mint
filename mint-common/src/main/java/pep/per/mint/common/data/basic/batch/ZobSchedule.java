package pep.per.mint.common.data.basic.batch;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import pep.per.mint.common.data.CacheableObject;

/**
 * Created by Solution TF on 15. 11. 4..
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")
public class ZobSchedule extends CacheableObject{

	public final static String TYPE_CRONTAB = "0";
	public final static String TYPE_INTERVAL_IN_SEC = "1";
	public final static String TYPE_INTERVAL_IN_MIN = "2";
	public final static String TYPE_INTERVAL_IN_HOUR = "3";

    @JsonProperty
    Zob zob;

    @JsonProperty
    String scheduleId = defaultStringValue;

    @JsonProperty
    String scheduleNm = defaultStringValue;

    @JsonProperty
    int seq;

    @JsonProperty
    String type = defaultStringValue;

    @JsonProperty
    String typeNm = defaultStringValue;

    @JsonProperty
    String value = defaultStringValue;

    public Zob getZob() {
        return zob;
    }

    public void setZob(Zob zob) {
        this.zob = zob;
    }

    public String getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(String scheduleId) {
        this.scheduleId = scheduleId;
    }

    public String getScheduleNm() {
        return scheduleNm;
    }

    public void setScheduleNm(String scheduleNm) {
        this.scheduleNm = scheduleNm;
    }

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTypeNm() {
        return typeNm;
    }

    public void setTypeNm(String typeNm) {
        this.typeNm = typeNm;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
