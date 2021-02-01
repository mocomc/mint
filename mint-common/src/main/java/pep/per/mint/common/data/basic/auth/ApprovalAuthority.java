package pep.per.mint.common.data.basic.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import pep.per.mint.common.data.CacheableObject;

/**
 * Created by Solution TF on 15. 12. 3..
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")
public class ApprovalAuthority extends CacheableObject{

    @JsonProperty
    String groupId = defaultStringValue;

    @JsonProperty
    int seq;

    @JsonProperty
    String userId = defaultStringValue;

    @JsonProperty
    int orderNo;

    @JsonProperty
    String userNm = defaultStringValue;

    @JsonProperty
    String companyNm = defaultStringValue;

    @JsonProperty
    String departmentNm = defaultStringValue;

    @JsonProperty
    String positionNm = defaultStringValue;

    @JsonProperty
    String approvalRoleType = defaultStringValue;

    @JsonProperty
    String approvalRoleNm = defaultStringValue;

    @JsonProperty
    String operationType = defaultStringValue;


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


    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(int orderNo) {
        this.orderNo = orderNo;
    }

    public String getUserNm() {
        return userNm;
    }

    public void setUserNm(String userNm) {
        this.userNm = userNm;
    }

    public String getCompanyNm() {
        return companyNm;
    }

    public void setCompanyNm(String companyNm) {
        this.companyNm = companyNm;
    }

    public String getDepartmentNm() {
        return departmentNm;
    }

    public void setDepartmentNm(String departmentNm) {
        this.departmentNm = departmentNm;
    }

    public String getPositionNm() {
        return positionNm;
    }

    public void setPositionNm(String positionNm) {
        this.positionNm = positionNm;
    }

    public String getApprovalRoleType() {
        return approvalRoleType;
    }

    public void setApprovalRoleType(String approvalRoleType) {
        this.approvalRoleType = approvalRoleType;
    }

    public String getApprovalRoleNm() {
        return approvalRoleNm;
    }

    public void setApprovalRoleNm(String approvalRoleNm) {
        this.approvalRoleNm = approvalRoleNm;
    }

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }


    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
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
