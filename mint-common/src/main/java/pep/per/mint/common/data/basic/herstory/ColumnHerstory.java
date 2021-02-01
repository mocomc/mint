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

import pep.per.mint.common.data.CacheableObject;

/**
 * Created by Solution TF on 15. 9. 4..
 */

public class ColumnHerstory extends CacheableObject{

    public final static String COLUMN_TYPE_STRING = "0";

    public final static String COLUMN_TYPE_NUMERIC = "1";

    public  final static String COLUMN_NAME_REQ_ID           = "requirementId";
    public  final static String COLUMN_NAME_REQ_NM           = "requirementNm";
    public  final static String COLUMN_NAME_REQ_STATUS       = "status";
    public  final static String COLUMN_NAME_REQ_BIZID        = "businessId";
    public  final static String COLUMN_NAME_REQ_COMMENTS     = "comments";
    public  final static String COLUMN_NAME_REQ_DEV_EXP_YMD  = "devExpYmd";
    public  final static String COLUMN_NAME_REQ_DEV_FIN_YMD  = "devFinYmd";
    public  final static String COLUMN_NAME_REQ_TEST_EXP_YMD = "testExpYmd";
    public  final static String COLUMN_NAME_REQ_TEST_FIN_YMD = "testFinYmd";
    public  final static String COLUMN_NAME_REQ_REAL_EXP_YMD = "realExpYmd";
    public  final static String COLUMN_NAME_REQ_REAL_FIN_YMD = "realFinYmd";
    public  final static String COLUMN_NAME_REQ_DEL_YN       = "delYn";
    public  final static String COLUMN_NAME_REQ_INTERFACE_ID = "interfaceId";

    String interfaceId;

    String requirementId;

    String modDate;

    String columnId;

    String version;

    String type;

    String value;

    String modId;

    public ColumnHerstory() {
        super();
    }

    public ColumnHerstory(String requirementId, String modDate, String columnId, String version, String type, String value, String modId) {
        this();
        this.requirementId = requirementId;
        this.modDate = modDate;
        this.columnId = columnId;
        this.version = version;
        this.type = type;
        this.value = value;
        this.modId = modId;
    }

    public ColumnHerstory(String requirementId, String interfaceId, String modDate, String columnId, String version, String type, String value, String modId) {
        this();
        this.requirementId = requirementId;
        this.interfaceId = interfaceId;
        this.modDate = modDate;
        this.columnId = columnId;
        this.version = version;
        this.type = type;
        this.value = value;
        this.modId = modId;
    }

    public String getInterfaceId() {
        return interfaceId;
    }

    public void setInterfaceId(String interfaceId) {
        this.interfaceId = interfaceId;
    }

    public String getRequirementId() {
        return requirementId;
    }

    public void setRequirementId(String requirementId) {
        this.requirementId = requirementId;
    }

    public String getModDate() {
        return modDate;
    }

    public void setModDate(String modDate) {
        this.modDate = modDate;
    }

    public String getColumnId() {
        return columnId;
    }

    public void setColumnId(String columnId) {
        this.columnId = columnId;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getModId() {
        return modId;
    }

    public void setModId(String modId) {
        this.modId = modId;
    }
}
