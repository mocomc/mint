package pep.per.mint.common.data.basic.batch;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import pep.per.mint.common.data.CacheableObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Solution TF on 15. 11. 4..
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")
public class ZobGroup extends CacheableObject{

    @JsonProperty
    String groupId = defaultStringValue;

    @JsonProperty
    String groupNm = defaultStringValue;

    @JsonProperty
    List<ZobSchedule> list = new ArrayList<ZobSchedule>();

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

    public List<ZobSchedule> getList() {
        return list;
    }

    public void setList(List<ZobSchedule> list) {
        this.list = list;
    }
}
