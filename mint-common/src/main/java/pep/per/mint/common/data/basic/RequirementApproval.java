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

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import pep.per.mint.common.data.CacheableObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Solution TF on 15. 8. 21..
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")
public class RequirementApproval extends CacheableObject {


    @JsonProperty
    String requirementId = defaultStringValue;

    @JsonProperty
    int seq = 0;

    @JsonProperty
    String reqUserId = defaultStringValue;

    @JsonProperty
    String reqUserNm = defaultStringValue;

    @JsonProperty
    String reqDate = defaultStringValue;

    @JsonProperty
    String reqType = defaultStringValue;

    @JsonProperty
    String subject = defaultStringValue;

    @JsonProperty
    String reqComments = defaultStringValue;

    List<RequirementApprovalUser> requirementApprovalUsers = new ArrayList<RequirementApprovalUser>();

    @JsonProperty
    String delYn = "N";

    public String getRequirementId() {
        return requirementId;
    }

    public void setRequirementId(String requirementId) {
        this.requirementId = requirementId;
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


    public List<RequirementApprovalUser> getRequirementApprovalUsers() {
        return requirementApprovalUsers;
    }

    public void setRequirementApprovalUsers(List<RequirementApprovalUser> requirementApprovalUsers) {
        this.requirementApprovalUsers = requirementApprovalUsers;
    }

    public String getReqUserNm() {
        return reqUserNm;
    }

    public void setReqUserNm(String reqUserNm) {
        this.reqUserNm = reqUserNm;
    }
}
