package pep.per.mint.common.data.basic.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import pep.per.mint.common.data.CacheableObject;

import java.util.List;

/**
 * Created by Solution TF on 15. 12. 3..
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")
public class ApprovalAuthorityGroup extends CacheableObject{

    @JsonProperty
    String groupId = defaultStringValue;

    @JsonProperty
    String groupNm = defaultStringValue;

    @JsonProperty
    List<ApprovalAuthority> authorityList;

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

    @JsonProperty
    String operationType = defaultStringValue;

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupNm() {
        return groupNm;
    }

    public void setGroupNm(String groupNm) {
        this.groupNm = groupNm;
    }

    public List<ApprovalAuthority> getAuthorityList() {
        return authorityList;
    }

    public void setAuthorityList(List<ApprovalAuthority> authorityList) {
        this.authorityList = authorityList;
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

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }
}
