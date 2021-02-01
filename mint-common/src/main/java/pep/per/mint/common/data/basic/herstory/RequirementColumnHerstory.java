/*
 * Copyright (c) 2013 ~ 2015. Mocomsys's guys(Sanghoon Lim, Deahun Ham, dhkim, Solution TF), Inc.  All Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 * [주의!]
 * 본 코드가 하자없이 완벽할거라 믿었다간 엄청난 욕을 먹고 그로 인한 스트레스로 병들거나 심하면 살기 싫어질 수도 있으니 부디 살얼음판을 걷듯이 주의하여 사용하기 바란다.
 * 사용상 받을지 모를 스트레스 및 기타 피해에 대한 책임은 사용자 본인에게 있음을 명시한다. 부디 행운을 빈다.
 * Please Don't contact Mocomsys, Inc., NURITKUM SQUARE R&D TOWER, 11F DMC 1605,
 * Sangam-Dong, Mapo-Gu, Seoul, 121-795 Korea or visit mocomsys.com if you need additional information or have any questions.
 */

package pep.per.mint.common.data.basic.herstory;

import com.fasterxml.jackson.annotation.JsonProperty;
import pep.per.mint.common.data.CacheableObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Solution TF on 15. 9. 9..
 */
public class RequirementColumnHerstory extends CacheableObject {



    @JsonProperty String modDate = defaultStringValue;
    @JsonProperty String version = defaultStringValue;
    @JsonProperty String requirementId = defaultStringValue;
    @JsonProperty String requirementNm = defaultStringValue;
    @JsonProperty String requirementNm2 = defaultStringValue;
    @JsonProperty boolean requirementNmChanged = false;
    @JsonProperty String status = defaultStringValue;
    @JsonProperty String status2 = defaultStringValue;
    @JsonProperty boolean statusChanged = false;
    @JsonProperty String statusNm = defaultStringValue;
    @JsonProperty String statusNm2 = defaultStringValue;
    @JsonProperty String comments = defaultStringValue;
    @JsonProperty String comments2 = defaultStringValue;
    @JsonProperty boolean commentsChanged = false;
    @JsonProperty String devExpYmd = defaultStringValue;
    @JsonProperty String devExpYmd2 = defaultStringValue;
    @JsonProperty boolean devExpYmdChanged = false;
    @JsonProperty String devFinYmd = defaultStringValue;
    @JsonProperty String devFinYmd2 = defaultStringValue;
    @JsonProperty boolean devFinYmdChanged = false;
    @JsonProperty String testExpYmd = defaultStringValue;
    @JsonProperty String testExpYmd2 = defaultStringValue;
    @JsonProperty boolean testExpYmdChanged = false;
    @JsonProperty String testFinYmd = defaultStringValue;
    @JsonProperty String testFinYmd2 = defaultStringValue;
    @JsonProperty boolean testFinYmdChanged = false;
    @JsonProperty String realExpYmd = defaultStringValue;
    @JsonProperty String realExpYmd2 = defaultStringValue;
    @JsonProperty boolean realExpYmdChanged = false;
    @JsonProperty String realFinYmd = defaultStringValue;
    @JsonProperty String realFinYmd2 = defaultStringValue;
    @JsonProperty boolean realFinYmdChanged = false;
    @JsonProperty String delYn = defaultStringValue;
    @JsonProperty String delYn2 = defaultStringValue;
    @JsonProperty boolean delYnChanged = false;
    @JsonProperty String businessId = defaultStringValue;
    @JsonProperty String businessId2 = defaultStringValue;
    @JsonProperty boolean businessIdChanged = false;
    @JsonProperty String businessNm = defaultStringValue;
    @JsonProperty String businessNm2 = defaultStringValue;
    @JsonProperty String interfaceId = defaultStringValue;
    @JsonProperty String interfaceId2 = defaultStringValue;
    @JsonProperty boolean interfaceIdChanged = false;
    @JsonProperty InterfaceColumnHerstory interfaceColumnHerstory;


    @JsonProperty List commentList;
    @JsonProperty List commentList2;


    @JsonProperty List attatchFileList;
    @JsonProperty List attatchFileList2;


    public String getModDate() {
        return modDate;
    }

    public void setModDate(String modDate) {
        this.modDate = modDate;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getRequirementId() {
        return requirementId;
    }

    public void setRequirementId(String requirementId) {
        this.requirementId = requirementId;
    }

    public String getRequirementNm() {
        return requirementNm;
    }

    public void setRequirementNm(String requirementNm) {
        this.requirementNm = requirementNm;
    }

    public String getRequirementNm2() {
        return requirementNm2;
    }

    public void setRequirementNm2(String requirementNm2) {
        this.requirementNm2 = requirementNm2;
    }

    public boolean isRequirementNmChanged() {
        return requirementNmChanged;
    }

    public void setRequirementNmChanged(boolean requirementNmChanged) {
        this.requirementNmChanged = requirementNmChanged;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus2() {
        return status2;
    }

    public void setStatus2(String status2) {
        this.status2 = status2;
    }

    public boolean isStatusChanged() {
        return statusChanged;
    }

    public void setStatusChanged(boolean statusChanged) {
        this.statusChanged = statusChanged;
    }

    public String getStatusNm() {
        return statusNm;
    }

    public void setStatusNm(String statusNm) {
        this.statusNm = statusNm;
    }

    public String getStatusNm2() {
        return statusNm2;
    }

    public void setStatusNm2(String statusNm2) {
        this.statusNm2 = statusNm2;
    }


    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getComments2() {
        return comments2;
    }

    public void setComments2(String comments2) {
        this.comments2 = comments2;
    }

    public boolean isCommentsChanged() {
        return commentsChanged;
    }

    public void setCommentsChanged(boolean commentsChanged) {
        this.commentsChanged = commentsChanged;
    }

    public String getDevExpYmd() {
        return devExpYmd;
    }

    public void setDevExpYmd(String devExpYmd) {
        this.devExpYmd = devExpYmd;
    }

    public String getDevExpYmd2() {
        return devExpYmd2;
    }

    public void setDevExpYmd2(String devExpYmd2) {
        this.devExpYmd2 = devExpYmd2;
    }

    public boolean isDevExpYmdChanged() {
        return devExpYmdChanged;
    }

    public void setDevExpYmdChanged(boolean devExpYmdChanged) {
        this.devExpYmdChanged = devExpYmdChanged;
    }

    public String getDevFinYmd() {
        return devFinYmd;
    }

    public void setDevFinYmd(String devFinYmd) {
        this.devFinYmd = devFinYmd;
    }

    public String getDevFinYmd2() {
        return devFinYmd2;
    }

    public void setDevFinYmd2(String devFinYmd2) {
        this.devFinYmd2 = devFinYmd2;
    }

    public boolean isDevFinYmdChanged() {
        return devFinYmdChanged;
    }

    public void setDevFinYmdChanged(boolean devFinYmdChanged) {
        this.devFinYmdChanged = devFinYmdChanged;
    }

    public String getTestExpYmd() {
        return testExpYmd;
    }

    public void setTestExpYmd(String testExpYmd) {
        this.testExpYmd = testExpYmd;
    }

    public String getTestExpYmd2() {
        return testExpYmd2;
    }

    public void setTestExpYmd2(String testExpYmd2) {
        this.testExpYmd2 = testExpYmd2;
    }

    public boolean isTestExpYmdChanged() {
        return testExpYmdChanged;
    }

    public void setTestExpYmdChanged(boolean testExpYmdChanged) {
        this.testExpYmdChanged = testExpYmdChanged;
    }

    public String getTestFinYmd() {
        return testFinYmd;
    }

    public void setTestFinYmd(String testFinYmd) {
        this.testFinYmd = testFinYmd;
    }

    public String getTestFinYmd2() {
        return testFinYmd2;
    }

    public void setTestFinYmd2(String testFinYmd2) {
        this.testFinYmd2 = testFinYmd2;
    }

    public boolean isTestFinYmdChanged() {
        return testFinYmdChanged;
    }

    public void setTestFinYmdChanged(boolean testFinYmdChanged) {
        this.testFinYmdChanged = testFinYmdChanged;
    }

    public String getRealExpYmd() {
        return realExpYmd;
    }

    public void setRealExpYmd(String realExpYmd) {
        this.realExpYmd = realExpYmd;
    }

    public String getRealExpYmd2() {
        return realExpYmd2;
    }

    public void setRealExpYmd2(String realExpYmd2) {
        this.realExpYmd2 = realExpYmd2;
    }

    public boolean isRealExpYmdChanged() {
        return realExpYmdChanged;
    }

    public void setRealExpYmdChanged(boolean realExpYmdChanged) {
        this.realExpYmdChanged = realExpYmdChanged;
    }

    public String getRealFinYmd() {
        return realFinYmd;
    }

    public void setRealFinYmd(String realFinYmd) {
        this.realFinYmd = realFinYmd;
    }

    public String getRealFinYmd2() {
        return realFinYmd2;
    }

    public void setRealFinYmd2(String realFinYmd2) {
        this.realFinYmd2 = realFinYmd2;
    }

    public boolean isRealFinYmdChanged() {
        return realFinYmdChanged;
    }

    public void setRealFinYmdChanged(boolean realFinYmdChanged) {
        this.realFinYmdChanged = realFinYmdChanged;
    }

    public String getDelYn() {
        return delYn;
    }

    public void setDelYn(String delYn) {
        this.delYn = delYn;
    }

    public String getDelYn2() {
        return delYn2;
    }

    public void setDelYn2(String delYn2) {
        this.delYn2 = delYn2;
    }

    public boolean isDelYnChanged() {
        return delYnChanged;
    }

    public void setDelYnChanged(boolean delYnChanged) {
        this.delYnChanged = delYnChanged;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public String getBusinessId2() {
        return businessId2;
    }

    public void setBusinessId2(String businessId2) {
        this.businessId2 = businessId2;
    }

    public boolean isBusinessIdChanged() {
        return businessIdChanged;
    }

    public void setBusinessIdChanged(boolean businessIdChanged) {
        this.businessIdChanged = businessIdChanged;
    }

    public String getBusinessNm() {
        return businessNm;
    }

    public void setBusinessNm(String businessNm) {
        this.businessNm = businessNm;
    }

    public String getBusinessNm2() {
        return businessNm2;
    }

    public void setBusinessNm2(String businessNm2) {
        this.businessNm2 = businessNm2;
    }


    public String getInterfaceId() {
        return interfaceId;
    }

    public void setInterfaceId(String interfaceId) {
        this.interfaceId = interfaceId;
    }

    public String getInterfaceId2() {
        return interfaceId2;
    }

    public void setInterfaceId2(String interfaceId2) {
        this.interfaceId2 = interfaceId2;
    }

    public boolean isInterfaceIdChanged() {
        return interfaceIdChanged;
    }

    public void setInterfaceIdChanged(boolean interfaceIdChanged) {
        this.interfaceIdChanged = interfaceIdChanged;
    }

    public InterfaceColumnHerstory getInterfaceColumnHerstory() {
        return interfaceColumnHerstory;
    }

    public void setInterfaceColumnHerstory(InterfaceColumnHerstory interfaceColumnHerstory) {
        this.interfaceColumnHerstory = interfaceColumnHerstory;
    }

    public List getCommentList() {
        return commentList;
    }

    public void setCommentList(List commentList) {
        this.commentList = commentList;
    }

    public List getCommentList2() {
        return commentList2;
    }

    public void setCommentList2(List commentList2) {
        this.commentList2 = commentList2;
    }

    public List getAttatchFileList() {
        return attatchFileList;
    }

    public void setAttatchFileList(List attatchFileList) {
        this.attatchFileList = attatchFileList;
    }

    public List getAttatchFileList2() {
        return attatchFileList2;
    }

    public void setAttatchFileList2(List attatchFileList2) {
        this.attatchFileList2 = attatchFileList2;
    }
}
