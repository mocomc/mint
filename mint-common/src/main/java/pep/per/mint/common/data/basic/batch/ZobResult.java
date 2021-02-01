package pep.per.mint.common.data.basic.batch;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import pep.per.mint.common.data.CacheableObject;

/**
 * Created by Solution TF on 15. 11. 4..
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")
public class ZobResult extends CacheableObject{

    public final static String RESULT_CD_SUCCESS = "0";
    public final static String RESULT_CD_ERROR = "-1";

    public final static String PROCESS_STATUS_START = "0";
    public final static String PROCESS_STATUS_END = "1";

    @JsonProperty
    String processId = defaultStringValue;

    @JsonProperty
    String groupId = defaultStringValue;

    @JsonProperty
    String jobId = defaultStringValue;

    @JsonProperty
    String scheduleId = defaultStringValue;

    @JsonProperty
    int seq;

    @JsonProperty
    String jobNm = defaultStringValue;

    @JsonProperty
    String groupNm = defaultStringValue;

    @JsonProperty
    String startDate = defaultStringValue;

    @JsonProperty
    String endDate = defaultStringValue;

    @JsonProperty
    String scheduleValue = defaultStringValue;

    @JsonProperty
    String processStatus = defaultStringValue;

    @JsonProperty
    String resultCd = defaultStringValue;

    @JsonProperty
    String errorMsg = defaultStringValue;

    @JsonProperty
    String caller = defaultStringValue;

    public String getProcessId() {
        return processId;
    }

    public void setProcessId(String processId) {
        this.processId = processId;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(String scheduleId) {
        this.scheduleId = scheduleId;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getScheduleValue() {
        return scheduleValue;
    }

    public void setScheduleValue(String scheduleValue) {
        this.scheduleValue = scheduleValue;
    }

    public String getProcessStatus() {
        return processStatus;
    }

    public void setProcessStatus(String processStatus) {
        this.processStatus = processStatus;
    }

    public String getResultCd() {
        return resultCd;
    }

    public void setResultCd(String resultCd) {
        this.resultCd = resultCd;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }


    public String getCaller() {
        return caller;
    }

    public void setCaller(String caller) {
        this.caller = caller;
    }

    public String getGroupNm() {
        return groupNm;
    }

    public void setGroupNm(String groupNm) {
        this.groupNm = groupNm;
    }

    public String getJobNm() {
        return jobNm;
    }

    public void setJobNm(String jobNm) {
        this.jobNm = jobNm;
    }
}
