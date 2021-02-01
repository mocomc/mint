/**
 * 
 */
package pep.per.mint.common.data.basic.main;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import pep.per.mint.common.data.CacheableObject;

/**
 * @author INSEONG
 *
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")
public class DevelopmentProceed extends CacheableObject{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1377951484678813495L;

	@JsonProperty("devPCnt")
	int devPCnt;
	
	@JsonProperty("devDCnt")
	int devDCnt;
	
	@JsonProperty("devCCnt")
	int devCCnt;
	
	@JsonProperty("testDCnt")
	int testDCnt;
	
	@JsonProperty("testCCnt")
	int testCCnt;
	
	@JsonProperty("realApprovalCCnt")
	int realApprovalCCnt;
	
	@JsonProperty("realDCnt")
	int realDCnt;
	
	@JsonProperty("realCCnt")
	int realCCnt;
	
	@JsonProperty("devRCnt")
	int devRCnt;
	
	@JsonProperty("devCnt")
	int devCnt;
	
	@JsonProperty("testCnt")
	int testCnt;
	
	@JsonProperty("realCnt")
	int realCnt;
	
	@JsonProperty("tCnt")
	int tCnt;
	
	@JsonProperty("tRCnt")
	int tRCnt;
	
	@JsonProperty("approvalCCnt")
	int approvalCCnt;
	
	@JsonProperty("reviewCCnt")
	int reviewCCnt;

	
	public int getDevPCnt() {
		return devPCnt;
	}

	public void setDevPCnt(int devPCnt) {
		this.devPCnt = devPCnt;
	}

	public int getDevDCnt() {
		return devDCnt;
	}

	public void setDevDCnt(int devDCnt) {
		this.devDCnt = devDCnt;
	}

	public int getDevCCnt() {
		return devCCnt;
	}

	public void setDevCCnt(int devCCnt) {
		this.devCCnt = devCCnt;
	}

	public int getTestDCnt() {
		return testDCnt;
	}

	public void setTestDCnt(int testDCnt) {
		this.testDCnt = testDCnt;
	}

	public int getTestCCnt() {
		return testCCnt;
	}

	public void setTestCCnt(int testCCnt) {
		this.testCCnt = testCCnt;
	}

	/**
	 * @return the realApprovalCCnt
	 */
	public int getRealApprovalCCnt() {
		return realApprovalCCnt;
	}

	/**
	 * @param realApprovalCCnt the realApprovalCCnt to set
	 */
	public void setRealApprovalCCnt(int realApprovalCCnt) {
		this.realApprovalCCnt = realApprovalCCnt;
	}

	public int getRealDCnt() {
		return realDCnt;
	}

	public void setRealDCnt(int realDCnt) {
		this.realDCnt = realDCnt;
	}

	public int getRealCCnt() {
		return realCCnt;
	}

	public void setRealCCnt(int realCCnt) {
		this.realCCnt = realCCnt;
	}

	public int getDevRCnt() {
		return devRCnt;
	}

	public void setDevRCnt(int devRCnt) {
		this.devRCnt = devRCnt;
	}

	public int getDevCnt() {
		return devCnt;
	}

	public void setDevCnt(int devCnt) {
		this.devCnt = devCnt;
	}

	public int getTestCnt() {
		return testCnt;
	}

	public void setTestCnt(int testCnt) {
		this.testCnt = testCnt;
	}

	public int getRealCnt() {
		return realCnt;
	}

	public void setRealCnt(int realCnt) {
		this.realCnt = realCnt;
	}

	public int gettCnt() {
		return tCnt;
	}

	public void settCnt(int tCnt) {
		this.tCnt = tCnt;
	}

	/**
	 * @return the tRCnt
	 */
	public int gettRCnt() {
		return tRCnt;
	}

	/**
	 * @param tRCnt the tRCnt to set
	 */
	public void settRCnt(int tRCnt) {
		this.tRCnt = tRCnt;
	}

	/**
	 * @return the approvalCCnt
	 */
	public int getApprovalCCnt() {
		return approvalCCnt;
	}

	/**
	 * @param approvalCCnt the approvalCCnt to set
	 */
	public void setApprovalCCnt(int approvalCCnt) {
		this.approvalCCnt = approvalCCnt;
	}

	/**
	 * @return the reviewCCnt
	 */
	public int getReviewCCnt() {
		return reviewCCnt;
	}

	/**
	 * @param reviewCCnt the reviewCCnt to set
	 */
	public void setReviewCCnt(int reviewCCnt) {
		this.reviewCCnt = reviewCCnt;
	}
	
}
