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

/**
 * Created by Solution TF on 15. 8. 22..
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")
public class ApprovalUser extends CacheableObject {


    public static final String ROLE_TYPE_REQUEST    = "0"; //기안자
    public static final String ROLE_TYPE_APPROVE    = "1"; //결재자
    public static final String ROLE_TYPE_AGREE      = "2"; //합의자
    public static final String ROLE_TYPE_CONSIDER   = "3"; //심의자
    public static final String ROLE_TYPE_CONFIRM    = "4"; //검수자(솔루션담당자,정)
    public static final String ROLE_TYPE_NOTIFY     = "9"; //통보자

    @JsonProperty
    String approvalItemId = defaultStringValue;

    @JsonProperty
    String approvalItemType = defaultStringValue;

    @JsonProperty
    int seq = 0;


    @JsonProperty
    int admUserSeq = 0;

    @JsonProperty
    String admUserId = defaultStringValue;

    @JsonProperty
    String admUserNm = defaultStringValue;

    @JsonProperty
    String roleType = defaultStringValue;

    String roleNm = defaultStringValue;

    @JsonProperty
    String companyNm = defaultStringValue;

    @JsonProperty
    String departmentNm = defaultStringValue;

    @JsonProperty
    String positionNm = defaultStringValue;

    @JsonProperty
    String delYn = "N";

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

    public int getAdmUserSeq() {
        return admUserSeq;
    }

    public void setAdmUserSeq(int admUserSeq) {
        this.admUserSeq = admUserSeq;
    }

    public String getAdmUserId() {
        return admUserId;
    }

    public void setAdmUserId(String admUserId) {
        this.admUserId = admUserId;
    }

    public String getRoleType() {
        return roleType;
    }

    public void setRoleType(String roleType) {
        this.roleType = roleType;
    }

    public String getDelYn() {
        return delYn;
    }

    public void setDelYn(String delYn) {
        this.delYn = delYn;
    }

    public String getRoleNm() {
        return roleNm;
    }

    public void setRoleNm(String roleNm) {
        this.roleNm = roleNm;
    }

    public String getAdmUserNm() {
        return admUserNm;
    }

    public void setAdmUserNm(String admUserNm) {
        this.admUserNm = admUserNm;
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
}
