/**
 * 
 */
package pep.per.mint.common.data.basic.statistics;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import pep.per.mint.common.data.CacheableObject;

/**
 * 
 * 통계 - 인터페이스 현황 Data Object
 * 
 * @author INSEONG
 *
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")
public class SubjectStatus extends CacheableObject {

	@JsonProperty
	String groupId1 = defaultStringValue;

	@JsonProperty
	String groupNm1 = defaultStringValue;

	@JsonProperty
	String groupId2 = defaultStringValue;

	@JsonProperty
	String groupNm2 = defaultStringValue;


	@JsonProperty
	int checkTotalCount = 0;

	@JsonProperty
	int totalCount = 0;

	@JsonProperty
	int subjectTotalCount = 0;

	@JsonProperty
	int subjectIngCount = 0;

	@JsonProperty
	int subjectDelayCount = 0;

	@JsonProperty
	int applovalIngCount = 0;

	@JsonProperty
	int applovalDelayCount = 0;

	@JsonProperty
	int devIngCount = 0;

	@JsonProperty
	int devDelayCount = 0;

	@JsonProperty
	int testIngCount = 0;

	@JsonProperty
	int testDelayCount = 0;

	@JsonProperty
	int realFinishCount = 0;
	
	@JsonProperty
	int changeFinishCount = 0;
	
	@JsonProperty
	int rejectCount = 0;	

	@JsonProperty
	int delCount = 0;

	public String getGroupId1() {
		return groupId1;
	}

	public void setGroupId1(String groupId1) {
		this.groupId1 = groupId1;
	}

	public String getGroupNm1() {
		return groupNm1;
	}

	public void setGroupNm1(String groupNm1) {
		this.groupNm1 = groupNm1;
	}

	public String getGroupId2() {
		return groupId2;
	}

	public void setGroupId2(String groupId2) {
		this.groupId2 = groupId2;
	}

	public String getGroupNm2() {
		return groupNm2;
	}

	public void setGroupNm2(String groupNm2) {
		this.groupNm2 = groupNm2;
	}

	public int getCheckTotalCount() {
		return checkTotalCount;
	}

	public void setCheckTotalCount(int checkTotalCount) {
		this.checkTotalCount = checkTotalCount;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getSubjectTotalCount() {
		return subjectTotalCount;
	}

	public void setSubjectTotalCount(int subjectTotalCount) {
		this.subjectTotalCount = subjectTotalCount;
	}

	public int getSubjectIngCount() {
		return subjectIngCount;
	}

	public void setSubjectIngCount(int subjectIngCount) {
		this.subjectIngCount = subjectIngCount;
	}

	public int getSubjectDelayCount() {
		return subjectDelayCount;
	}

	public void setSubjectDelayCount(int subjectDelayCount) {
		this.subjectDelayCount = subjectDelayCount;
	}

	public int getApplovalIngCount() {
		return applovalIngCount;
	}

	public void setApplovalIngCount(int applovalIngCount) {
		this.applovalIngCount = applovalIngCount;
	}

	public int getApplovalDelayCount() {
		return applovalDelayCount;
	}

	public void setApplovalDelayCount(int applovalDelayCount) {
		this.applovalDelayCount = applovalDelayCount;
	}

	public int getDevIngCount() {
		return devIngCount;
	}

	public void setDevIngCount(int devIngCount) {
		this.devIngCount = devIngCount;
	}

	public int getDevDelayCount() {
		return devDelayCount;
	}

	public void setDevDelayCount(int devDelayCount) {
		this.devDelayCount = devDelayCount;
	}

	public int getTestIngCount() {
		return testIngCount;
	}

	public void setTestIngCount(int testIngCount) {
		this.testIngCount = testIngCount;
	}

	public int getTestDelayCount() {
		return testDelayCount;
	}

	public void setTestDelayCount(int testDelayCount) {
		this.testDelayCount = testDelayCount;
	}

	public int getRealFinishCount() {
		return realFinishCount;
	}

	public void setRealFinishCount(int realFinishCount) {
		this.realFinishCount = realFinishCount;
	}
	
	public int getChangeFinishCount() {
		return changeFinishCount;
	}

	public void setChangeFinishCount(int changeFinishCount) {
		this.changeFinishCount = changeFinishCount;
	}
	
	public int getRejectCount() {
		return rejectCount;
	}

	public void setRejectCount(int rejectCount) {
		this.rejectCount = rejectCount;
	}	

	public int getDelCount() {
		return delCount;
	}

	public void setDelCount(int delCount) {
		this.delCount = delCount;
	}
}
