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
public class StatisticsDevelopmentStatus extends CacheableObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 501232401445756563L;

	
	@JsonProperty("criteriaId")
	String criteriaId;
	
	@JsonProperty("criteriaNm")
	String criteriaNm;
	
	@JsonProperty("criteriaDetailId")
	String criteriaDetailId;
	
	@JsonProperty("criteriaDetailNm")
	String criteriaDetailNm;
	
	@JsonProperty("nodeTypeCd")
	String nodeTypeCd;
	
	@JsonProperty("nodeTypeNm")
	String nodeTypeNm;
	
	@JsonProperty("child_Cnt")
	int child_Cnt = 0;
	
	// R:Requirment(의뢰)
	// D:Delay(지연)
	// C:Complete(완료)
	
	@JsonProperty("review_C")
	int review_C = 0;
	
	@JsonProperty("approval_C")
	int approval_C = 0;
	
	@JsonProperty("total_R_Cnt")
	int total_R_Cnt = 0;
	
	@JsonProperty("dev_R")
	int dev_R = 0;
	
	@JsonProperty("dev_D")
	int dev_D = 0;
	
	@JsonProperty("dev_C")
	int dev_C = 0;
	
	@JsonProperty("dev_Cnt")
	int dev_Cnt = 0;
	
	@JsonProperty("test_D")
	int test_D = 0;

	@JsonProperty("test_C")
	int test_C = 0;

	@JsonProperty("test_Cnt")
	int test_Cnt = 0;
	
	@JsonProperty("real_approval_C")
	int real_approval_C = 0;
	
	@JsonProperty("real_D")
	int real_D = 0;

	@JsonProperty("real_C")
	int real_C = 0;

	@JsonProperty("real_Cnt")
	int real_Cnt = 0;
	
	@JsonProperty("total_Cnt")
	int total_Cnt = 0;
	
	
	
	/**
	 * @return the criteriaId
	 */
	public String getCriteriaId() {
		return criteriaId;
	}

	/**
	 * @param criteriaId the criteriaId to set
	 */
	public void setCriteriaId(String criteriaId) {
		this.criteriaId = criteriaId;
	}

	/**
	 * @return the criteriaNm
	 */
	public String getCriteriaNm() {
		return criteriaNm;
	}

	/**
	 * @param criteriaNm the criteriaNm to set
	 */
	public void setCriteriaNm(String criteriaNm) {
		this.criteriaNm = criteriaNm;
	}

	/**
	 * @return the criteriaDetailId
	 */
	public String getCriteriaDetailId() {
		return criteriaDetailId;
	}

	/**
	 * @param criteriaDetailId the criteriaDetailId to set
	 */
	public void setCriteriaDetailId(String criteriaDetailId) {
		this.criteriaDetailId = criteriaDetailId;
	}

	/**
	 * @return the criteriaDetailNm
	 */
	public String getCriteriaDetailNm() {
		return criteriaDetailNm;
	}

	/**
	 * @param criteriaDetailNm the criteriaDetailNm to set
	 */
	public void setCriteriaDetailNm(String criteriaDetailNm) {
		this.criteriaDetailNm = criteriaDetailNm;
	}

	/**
	 * @return the nodeTypeCd
	 */
	public String getNodeTypeCd() {
		return nodeTypeCd;
	}

	/**
	 * @param nodeTypeCd the nodeTypeCd to set
	 */
	public void setNodeTypeCd(String nodeTypeCd) {
		this.nodeTypeCd = nodeTypeCd;
	}

	/**
	 * @return the nodeTypeNm
	 */
	public String getNodeTypeNm() {
		return nodeTypeNm;
	}

	/**
	 * @param nodeTypeNm the nodeTypeNm to set
	 */
	public void setNodeTypeNm(String nodeTypeNm) {
		this.nodeTypeNm = nodeTypeNm;
	}
	
	/**
	 * @return the child_Cnt
	 */
	public int getChild_Cnt() {
		return child_Cnt;
	}

	/**
	 * @param child_Cnt the child_Cnt to set
	 */
	public void setChild_Cnt(int child_Cnt) {
		this.child_Cnt = child_Cnt;
	}

	/**
	 * @return the review_C
	 */
	public int getReview_C() {
		return review_C;
	}

	/**
	 * @param review_C the review_C to set
	 */
	public void setReview_C(int review_C) {
		this.review_C = review_C;
	}

	/**
	 * @return the approval_C
	 */
	public int getApproval_C() {
		return approval_C;
	}

	/**
	 * @param approval_C the approval_C to set
	 */
	public void setApproval_C(int approval_C) {
		this.approval_C = approval_C;
	}

	/**
	 * @return the total_R_Cnt
	 */
	public int getTotal_R_Cnt() {
		return total_R_Cnt;
	}

	/**
	 * @param total_R_Cnt the total_R_Cnt to set
	 */
	public void setTotal_R_Cnt(int total_R_Cnt) {
		this.total_R_Cnt = total_R_Cnt;
	}

	/**
	 * @return the dev_R
	 */
	public int getDev_R() {
		return dev_R;
	}

	/**
	 * @param dev_R the dev_R to set
	 */
	public void setDev_R(int dev_R) {
		this.dev_R = dev_R;
	}

	/**
	 * @return the dev_D
	 */
	public int getDev_D() {
		return dev_D;
	}

	/**
	 * @param dev_D the dev_D to set
	 */
	public void setDev_D(int dev_D) {
		this.dev_D = dev_D;
	}

	/**
	 * @return the dev_C
	 */
	public int getDev_C() {
		return dev_C;
	}

	/**
	 * @param dev_C the dev_C to set
	 */
	public void setDev_C(int dev_C) {
		this.dev_C = dev_C;
	}

	/**
	 * @return the dev_Cnt
	 */
	public int getDev_Cnt() {
		return dev_Cnt;
	}

	/**
	 * @param dev_Cnt the dev_Cnt to set
	 */
	public void setDev_Cnt(int dev_Cnt) {
		this.dev_Cnt = dev_Cnt;
	}

	/**
	 * @return the test_D
	 */
	public int getTest_D() {
		return test_D;
	}

	/**
	 * @param test_D the test_D to set
	 */
	public void setTest_D(int test_D) {
		this.test_D = test_D;
	}

	/**
	 * @return the test_C
	 */
	public int getTest_C() {
		return test_C;
	}

	/**
	 * @param test_C the test_C to set
	 */
	public void setTest_C(int test_C) {
		this.test_C = test_C;
	}

	/**
	 * @return the test_Cnt
	 */
	public int getTest_Cnt() {
		return test_Cnt;
	}

	/**
	 * @param test_Cnt the test_Cnt to set
	 */
	public void setTest_Cnt(int test_Cnt) {
		this.test_Cnt = test_Cnt;
	}

	/**
	 * @return the real_approval_C
	 */
	public int getReal_approval_C() {
		return real_approval_C;
	}

	/**
	 * @param real_approval_C the real_approval_C to set
	 */
	public void setReal_approval_C(int real_approval_C) {
		this.real_approval_C = real_approval_C;
	}

	/**
	 * @return the real_D
	 */
	public int getReal_D() {
		return real_D;
	}

	/**
	 * @param real_D the real_D to set
	 */
	public void setReal_D(int real_D) {
		this.real_D = real_D;
	}

	/**
	 * @return the real_C
	 */
	public int getReal_C() {
		return real_C;
	}

	/**
	 * @param real_C the real_C to set
	 */
	public void setReal_C(int real_C) {
		this.real_C = real_C;
	}

	/**
	 * @return the real_Cnt
	 */
	public int getReal_Cnt() {
		return real_Cnt;
	}

	/**
	 * @param real_Cnt the real_Cnt to set
	 */
	public void setReal_Cnt(int real_Cnt) {
		this.real_Cnt = real_Cnt;
	}

	/**
	 * @return the total_Cnt
	 */
	public int getTotal_Cnt() {
		return total_Cnt;
	}

	/**
	 * @param total_Cnt the total_Cnt to set
	 */
	public void setTotal_Cnt(int total_Cnt) {
		this.total_Cnt = total_Cnt;
	}
	
	
}
