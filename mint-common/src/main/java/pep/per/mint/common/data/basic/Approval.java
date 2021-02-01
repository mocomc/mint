/*
 * Copyright (c) 2013 ~ 2015. Mocomsys's guys(Sanghoon Lim, Deahun Ham, dhkim, Solution TF), Inc.  All Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 * [주의!]
 * 본 코드가 하자없이 완벽할거라 믿었다간 엄청난 욕을 먹고 그로 인한 스트레스로 병들거나 심하면 살기 싫어질 수도 있으니 부디 살얼음판을 걷듯이 주의하여 사용하기 바란다.
 * 사용상 받을지 모를 스트레스 및 기타 피해에 대한 책임은 사용자 본인에게 있음을 명시한다. 부디 행운을 빈다.
 * Please Don't contact Mocomsys, Inc., NURITKUM SQUARE R&D TOWER, 11F DMC 1605,
 * Sangam-Dong, Mapo-Gu, Seoul, 121-795 Korea or visit mocomsys.com if you need additional information or have any questions.
 */

package pep.per.mint.common.data.basic;

//import com.fasterxml.jackson.annotation.JsonProperty;
//import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import pep.per.mint.common.data.CacheableObject;
import pep.per.mint.common.data.basic.monitor.ProblemLedger;
import pep.per.mint.common.data.basic.monitor.ProblemManagement;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Solution TF on 15. 9. 10..
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")
public class Approval extends CacheableObject {


    public static final String APPROVAL_ITEM_TYPE_REQUIREMENT = "0";
    public static final String APPROVAL_ITEM_TYPE_PROBLEM = "1";

    @JsonProperty
    String approvalItemId = defaultStringValue;

    @JsonProperty
    String approvalItemType = defaultStringValue;

    @JsonProperty
    int seq = 0;

    @JsonProperty
    String reqUserId = defaultStringValue;

    @JsonProperty
    String reqUserNm = defaultStringValue;

    @JsonProperty
    String misid = defaultStringValue;

    @JsonProperty
    String reqDate = defaultStringValue;

    @JsonProperty
    String reqType = defaultStringValue;

    @JsonProperty
    String subject = defaultStringValue;

    @JsonProperty
    String reqComments = defaultStringValue;

    List<ApprovalUser> approvalUsers = new ArrayList<ApprovalUser>();

    @JsonProperty
    String delYn = "N";

    @JsonProperty
    String approvalContents = defaultStringValue;

    @JsonProperty
    String linkKey = defaultStringValue;

    @JsonProperty
    Requirement requirement;

    @JsonProperty
    //ProblemManagement problemManagement;
    ProblemLedger problemLedger;


    public String getApprovalItemId() {
        return approvalItemId;
    }

    public void setApprovalItemId(String approvalItemId) {
        this.approvalItemId = approvalItemId;
    }

    public String getApprovalItemType() {
        return approvalItemType;
    }

    public void setApprovalItemType(String approvalItemType) {
        this.approvalItemType = approvalItemType;
    }

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public String getReqUserId() {
        return reqUserId;
    }

    public void setReqUserId(String reqUserId) {
        this.reqUserId = reqUserId;
    }

    public String getMisid() {
        return misid;
    }

    public void setMisid(String misid) {
        this.misid = misid;
    }

    public String getReqDate() {
        return reqDate;
    }

    public void setReqDate(String reqDate) {
        this.reqDate = reqDate;
    }

    public String getReqType() {
        return reqType;
    }

    public void setReqType(String reqType) {
        this.reqType = reqType;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getReqComments() {
        return reqComments;
    }

    public void setReqComments(String reqComments) {
        this.reqComments = reqComments;
    }

    public String getDelYn() {
        return delYn;
    }

    public void setDelYn(String delYn) {
        this.delYn = delYn;
    }

    public List<ApprovalUser> getApprovalUsers() {
        return approvalUsers;
    }

    public void setApprovalUsers(List<ApprovalUser> approvalUsers) {
        this.approvalUsers = approvalUsers;
    }

    public String getReqUserNm() {
        return reqUserNm;
    }

    public void setReqUserNm(String reqUserNm) {
        this.reqUserNm = reqUserNm;
    }

    public String getApprovalContents() {
        return approvalContents;
    }

    public void setApprovalContents(String approvalContents) {
        this.approvalContents = approvalContents;
    }

    public String getLinkKey() {
        return linkKey;
    }

    public void setLinkKey(String linkKey) {
        this.linkKey = linkKey;
    }

    public Requirement getRequirement() {
        return requirement;
    }

    public void setRequirement(Requirement requirement) {
        this.requirement = requirement;
    }
    /*
    public ProblemManagement getProblemManagement() {
        return problemManagement;
    }

    public void setProblemManagement(ProblemManagement problemManagement) {
        this.problemManagement = problemManagement;
    }
    */

	/**
	 * @return the problemLedger
	 */
	public ProblemLedger getProblemLedger() {
		return problemLedger;
	}

	/**
	 * @param problemLedger the problemLedger to set
	 */
	public void setProblemLedger(ProblemLedger problemLedger) {
		this.problemLedger = problemLedger;
	}
    
}