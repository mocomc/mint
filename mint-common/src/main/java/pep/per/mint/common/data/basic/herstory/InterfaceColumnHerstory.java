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

import java.util.List;
import java.util.Map;

/**
 * Created by Solution TF on 15. 9. 9..
 */
public class InterfaceColumnHerstory extends CacheableObject {


    @JsonProperty String modDate = defaultStringValue;
    @JsonProperty String version = defaultStringValue;
    
    @JsonProperty String integrationId = defaultStringValue;
    @JsonProperty String interfaceId = defaultStringValue;
    @JsonProperty String interfaceNm = defaultStringValue;
    @JsonProperty String channelId = defaultStringValue;
    @JsonProperty String channelNm = defaultStringValue;
    @JsonProperty String importance = defaultStringValue;
    @JsonProperty String maxDelayTime = defaultStringValue;
    @JsonProperty String maxDelayUnit = defaultStringValue;
    @JsonProperty String maxErrorTime = defaultStringValue;
    @JsonProperty String maxErrorUnit = defaultStringValue;
    @JsonProperty String refPatternId = defaultStringValue;
    @JsonProperty String dataPrDir = defaultStringValue;
    @JsonProperty String appPrMethod = defaultStringValue;
    @JsonProperty String dataPrMethod = defaultStringValue;
    @JsonProperty String dataFrequency = defaultStringValue;
    @JsonProperty String dataFrequencyInput = defaultStringValue;
    @JsonProperty String sizePerTran = defaultStringValue;
    @JsonProperty String cntPerFrequency = defaultStringValue;
    @JsonProperty String cntPerDay = defaultStringValue;
    @JsonProperty String qttPerDay = defaultStringValue;
    @JsonProperty String startResType = defaultStringValue;
    @JsonProperty String endResType = defaultStringValue;
    @JsonProperty String startNodeCnt = defaultStringValue;
    @JsonProperty String endNodeCnt = defaultStringValue;
    @JsonProperty String dataSeqYn = defaultStringValue;
    @JsonProperty String comments = defaultStringValue;
    @JsonProperty String delYn = defaultStringValue;
    @JsonProperty String importanceNm = defaultStringValue;
    @JsonProperty String maxDelayUnitNm = defaultStringValue;
    @JsonProperty String maxErrorUnitNm = defaultStringValue;
    @JsonProperty String dataPrDirNm = defaultStringValue;
    @JsonProperty String appPrMethodNm = defaultStringValue;
    @JsonProperty String dataPrMethodNm = defaultStringValue;
    @JsonProperty String dataFrequencyNm = defaultStringValue;
    @JsonProperty String startResTypeNm = defaultStringValue;
    @JsonProperty String endResTypeNm = defaultStringValue;
    @JsonProperty String asisInterfaceId = defaultStringValue;

    @JsonProperty String integrationId2 = defaultStringValue;
    @JsonProperty String interfaceId2 = defaultStringValue;
    @JsonProperty String interfaceNm2 = defaultStringValue;
    @JsonProperty String channelId2 = defaultStringValue;
    @JsonProperty String channelNm2 = defaultStringValue;
    @JsonProperty String importance2 = defaultStringValue;
    @JsonProperty String maxDelayTime2 = defaultStringValue;
    @JsonProperty String maxDelayUnit2 = defaultStringValue;
    @JsonProperty String maxErrorTime2 = defaultStringValue;
    @JsonProperty String maxErrorUnit2 = defaultStringValue;
    @JsonProperty String refPatternId2 = defaultStringValue;
    @JsonProperty String dataPrDir2 = defaultStringValue;
    @JsonProperty String appPrMethod2 = defaultStringValue;
    @JsonProperty String dataPrMethod2 = defaultStringValue;
    @JsonProperty String dataFrequency2 = defaultStringValue;
    @JsonProperty String dataFrequencyInput2 = defaultStringValue;
    @JsonProperty String sizePerTran2 = defaultStringValue;
    @JsonProperty String cntPerFrequency2 = defaultStringValue;
    @JsonProperty String cntPerDay2 = defaultStringValue;
    @JsonProperty String qttPerDay2 = defaultStringValue;
    @JsonProperty String startResType2 = defaultStringValue;
    @JsonProperty String endResType2 = defaultStringValue;
    @JsonProperty String startNodeCnt2 = defaultStringValue;
    @JsonProperty String endNodeCnt2 = defaultStringValue;
    @JsonProperty String dataSeqYn2 = defaultStringValue;
    @JsonProperty String comments2 = defaultStringValue;
    @JsonProperty String delYn2 = defaultStringValue;
    @JsonProperty String importanceNm2 = defaultStringValue;
    @JsonProperty String maxDelayUnitNm2 = defaultStringValue;
    @JsonProperty String maxErrorUnitNm2 = defaultStringValue;
    @JsonProperty String dataPrDirNm2 = defaultStringValue;
    @JsonProperty String appPrMethodNm2 = defaultStringValue;
    @JsonProperty String dataPrMethodNm2 = defaultStringValue;
    @JsonProperty String dataFrequencyNm2 = defaultStringValue;
    @JsonProperty String startResTypeNm2 = defaultStringValue;
    @JsonProperty String endResTypeNm2 = defaultStringValue;
    @JsonProperty String asisInterfaceId2 = defaultStringValue;

    @JsonProperty boolean integrationIdChanged = false;
    @JsonProperty boolean interfaceIdChanged = false;
    @JsonProperty boolean channelIdChanged = false;
    @JsonProperty boolean importanceChanged = false;
    @JsonProperty boolean maxDelayTimeChanged = false;
    @JsonProperty boolean maxDelayUnitChanged = false;
    @JsonProperty boolean maxErrorTimeChanged = false;
    @JsonProperty boolean maxErrorUnitChanged = false;
    @JsonProperty boolean refPatternIdChanged = false;
    @JsonProperty boolean dataPrDirChanged = false;
    @JsonProperty boolean appPrMethodChanged = false;
    @JsonProperty boolean dataPrMethodChanged = false;
    @JsonProperty boolean dataFrequencyChanged = false;
    @JsonProperty boolean dataFrequencyInputChanged = false;
    @JsonProperty boolean sizePerTranChanged = false;
    @JsonProperty boolean cntPerFrequencyChanged = false;
    @JsonProperty boolean cntPerDayChanged = false;
    @JsonProperty boolean qttPerDayChanged = false;
    @JsonProperty boolean startResTypeChanged = false;
    @JsonProperty boolean endResTypeChanged = false;
    @JsonProperty boolean startNodeCntChanged = false;
    @JsonProperty boolean endNodeCntChanged = false;
    @JsonProperty boolean dataSeqYnChanged = false;
    @JsonProperty boolean commentsChanged = false;
    @JsonProperty boolean delYnChanged = false;
    @JsonProperty boolean asisInterfaceIdChanged = false;

    @JsonProperty List systemList;
    @JsonProperty List systemList2;

    @JsonProperty List businessList;
    @JsonProperty List businessList2;

    @JsonProperty List relUsers;
    @JsonProperty List relUsers2;
    
    @JsonProperty Map b2biInfo;
    @JsonProperty Map b2biInfo2;



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

    public String getInterfaceId() {
        return interfaceId;
    }

    public void setInterfaceId(String interfaceId) {
        this.interfaceId = interfaceId;
    }

    public String getInterfaceNm() {
        return interfaceNm;
    }

    public void setInterfaceNm(String interfaceNm) {
        this.interfaceNm = interfaceNm;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getChannelNm() {
        return channelNm;
    }

    public void setChannelNm(String channelNm) {
        this.channelNm = channelNm;
    }

    public String getImportance() {
        return importance;
    }

    public void setImportance(String importance) {
        this.importance = importance;
    }

    public String getMaxDelayTime() {
        return maxDelayTime;
    }

    public void setMaxDelayTime(String maxDelayTime) {
        this.maxDelayTime = maxDelayTime;
    }

    public String getMaxDelayUnit() {
        return maxDelayUnit;
    }

    public void setMaxDelayUnit(String maxDelayUnit) {
        this.maxDelayUnit = maxDelayUnit;
    }

    public String getMaxErrorTime() {
        return maxErrorTime;
    }

    public void setMaxErrorTime(String maxErrorTime) {
        this.maxErrorTime = maxErrorTime;
    }

    public String getMaxErrorUnit() {
        return maxErrorUnit;
    }

    public void setMaxErrorUnit(String maxErrorUnit) {
        this.maxErrorUnit = maxErrorUnit;
    }

    public String getRefPatternId() {
        return refPatternId;
    }

    public void setRefPatternId(String refPatternId) {
        this.refPatternId = refPatternId;
    }

    public String getDataPrDir() {
        return dataPrDir;
    }

    public void setDataPrDir(String dataPrDir) {
        this.dataPrDir = dataPrDir;
    }

    public String getAppPrMethod() {
        return appPrMethod;
    }

    public void setAppPrMethod(String appPrMethod) {
        this.appPrMethod = appPrMethod;
    }

    public String getDataPrMethod() {
        return dataPrMethod;
    }

    public void setDataPrMethod(String dataPrMethod) {
        this.dataPrMethod = dataPrMethod;
    }

    public String getDataFrequency() {
        return dataFrequency;
    }

    public void setDataFrequency(String dataFrequency) {
        this.dataFrequency = dataFrequency;
    }

    public String getSizePerTran() {
        return sizePerTran;
    }

    public void setSizePerTran(String sizePerTran) {
        this.sizePerTran = sizePerTran;
    }

    public String getCntPerFrequency() {
        return cntPerFrequency;
    }

    public void setCntPerFrequency(String cntPerFrequency) {
        this.cntPerFrequency = cntPerFrequency;
    }

    public String getCntPerDay() {
        return cntPerDay;
    }

    public void setCntPerDay(String cntPerDay) {
        this.cntPerDay = cntPerDay;
    }

    public String getQttPerDay() {
        return qttPerDay;
    }

    public void setQttPerDay(String qttPerDay) {
        this.qttPerDay = qttPerDay;
    }

    public String getStartResType() {
        return startResType;
    }

    public void setStartResType(String startResType) {
        this.startResType = startResType;
    }

    public String getEndResType() {
        return endResType;
    }

    public void setEndResType(String endResType) {
        this.endResType = endResType;
    }

    public String getStartNodeCnt() {
        return startNodeCnt;
    }

    public void setStartNodeCnt(String startNodeCnt) {
        this.startNodeCnt = startNodeCnt;
    }

    public String getEndNodeCnt() {
        return endNodeCnt;
    }

    public void setEndNodeCnt(String endNodeCnt) {
        this.endNodeCnt = endNodeCnt;
    }

    public String getDataSeqYn() {
        return dataSeqYn;
    }

    public void setDataSeqYn(String dataSeqYn) {
        this.dataSeqYn = dataSeqYn;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getDelYn() {
        return delYn;
    }

    public void setDelYn(String delYn) {
        this.delYn = delYn;
    }

    public String getImportanceNm() {
        return importanceNm;
    }

    public void setImportanceNm(String importanceNm) {
        this.importanceNm = importanceNm;
    }

    public String getMaxDelayUnitNm() {
        return maxDelayUnitNm;
    }

    public void setMaxDelayUnitNm(String maxDelayUnitNm) {
        this.maxDelayUnitNm = maxDelayUnitNm;
    }

    public String getMaxErrorUnitNm() {
        return maxErrorUnitNm;
    }

    public void setMaxErrorUnitNm(String maxErrorUnitNm) {
        this.maxErrorUnitNm = maxErrorUnitNm;
    }

    public String getDataPrDirNm() {
        return dataPrDirNm;
    }

    public void setDataPrDirNm(String dataPrDirNm) {
        this.dataPrDirNm = dataPrDirNm;
    }

    public String getAppPrMethodNm() {
        return appPrMethodNm;
    }

    public void setAppPrMethodNm(String appPrMethodNm) {
        this.appPrMethodNm = appPrMethodNm;
    }

    public String getDataPrMethodNm() {
        return dataPrMethodNm;
    }

    public void setDataPrMethodNm(String dataPrMethodNm) {
        this.dataPrMethodNm = dataPrMethodNm;
    }

    public String getDataFrequencyNm() {
        return dataFrequencyNm;
    }

    public void setDataFrequencyNm(String dataFrequencyNm) {
        this.dataFrequencyNm = dataFrequencyNm;
    }

    public String getStartResTypeNm() {
        return startResTypeNm;
    }

    public void setStartResTypeNm(String startResTypeNm) {
        this.startResTypeNm = startResTypeNm;
    }

    public String getEndResTypeNm() {
        return endResTypeNm;
    }

    public void setEndResTypeNm(String endResTypeNm) {
        this.endResTypeNm = endResTypeNm;
    }

    public String getInterfaceId2() {
        return interfaceId2;
    }

    public void setInterfaceId2(String interfaceId2) {
        this.interfaceId2 = interfaceId2;
    }

    public String getInterfaceNm2() {
        return interfaceNm2;
    }

    public void setInterfaceNm2(String interfaceNm2) {
        this.interfaceNm2 = interfaceNm2;
    }

    public String getChannelId2() {
        return channelId2;
    }

    public void setChannelId2(String channelId2) {
        this.channelId2 = channelId2;
    }

    public String getChannelNm2() {
        return channelNm2;
    }

    public void setChannelNm2(String channelNm2) {
        this.channelNm2 = channelNm2;
    }

    public String getImportance2() {
        return importance2;
    }

    public void setImportance2(String importance2) {
        this.importance2 = importance2;
    }

    public String getMaxDelayTime2() {
        return maxDelayTime2;
    }

    public void setMaxDelayTime2(String maxDelayTime2) {
        this.maxDelayTime2 = maxDelayTime2;
    }

    public String getMaxDelayUnit2() {
        return maxDelayUnit2;
    }

    public void setMaxDelayUnit2(String maxDelayUnit2) {
        this.maxDelayUnit2 = maxDelayUnit2;
    }

    public String getMaxErrorTime2() {
        return maxErrorTime2;
    }

    public void setMaxErrorTime2(String maxErrorTime2) {
        this.maxErrorTime2 = maxErrorTime2;
    }

    public String getMaxErrorUnit2() {
        return maxErrorUnit2;
    }

    public void setMaxErrorUnit2(String maxErrorUnit2) {
        this.maxErrorUnit2 = maxErrorUnit2;
    }

    public String getRefPatternId2() {
        return refPatternId2;
    }

    public void setRefPatternId2(String refPatternId2) {
        this.refPatternId2 = refPatternId2;
    }

    public String getDataPrDir2() {
        return dataPrDir2;
    }

    public void setDataPrDir2(String dataPrDir2) {
        this.dataPrDir2 = dataPrDir2;
    }

    public String getAppPrMethod2() {
        return appPrMethod2;
    }

    public void setAppPrMethod2(String appPrMethod2) {
        this.appPrMethod2 = appPrMethod2;
    }

    public String getDataPrMethod2() {
        return dataPrMethod2;
    }

    public void setDataPrMethod2(String dataPrMethod2) {
        this.dataPrMethod2 = dataPrMethod2;
    }

    public String getDataFrequency2() {
        return dataFrequency2;
    }

    public void setDataFrequency2(String dataFrequency2) {
        this.dataFrequency2 = dataFrequency2;
    }

    public String getSizePerTran2() {
        return sizePerTran2;
    }

    public void setSizePerTran2(String sizePerTran2) {
        this.sizePerTran2 = sizePerTran2;
    }

    public String getCntPerFrequency2() {
        return cntPerFrequency2;
    }

    public void setCntPerFrequency2(String cntPerFrequency2) {
        this.cntPerFrequency2 = cntPerFrequency2;
    }

    public String getCntPerDay2() {
        return cntPerDay2;
    }

    public void setCntPerDay2(String cntPerDay2) {
        this.cntPerDay2 = cntPerDay2;
    }

    public String getQttPerDay2() {
        return qttPerDay2;
    }

    public void setQttPerDay2(String qttPerDay2) {
        this.qttPerDay2 = qttPerDay2;
    }

    public String getStartResType2() {
        return startResType2;
    }

    public void setStartResType2(String startResType2) {
        this.startResType2 = startResType2;
    }

    public String getEndResType2() {
        return endResType2;
    }

    public void setEndResType2(String endResType2) {
        this.endResType2 = endResType2;
    }

    public String getStartNodeCnt2() {
        return startNodeCnt2;
    }

    public void setStartNodeCnt2(String startNodeCnt2) {
        this.startNodeCnt2 = startNodeCnt2;
    }

    public String getEndNodeCnt2() {
        return endNodeCnt2;
    }

    public void setEndNodeCnt2(String endNodeCnt2) {
        this.endNodeCnt2 = endNodeCnt2;
    }

    public String getDataSeqYn2() {
        return dataSeqYn2;
    }

    public void setDataSeqYn2(String dataSeqYn2) {
        this.dataSeqYn2 = dataSeqYn2;
    }

    public String getComments2() {
        return comments2;
    }

    public void setComments2(String comments2) {
        this.comments2 = comments2;
    }

    public String getDelYn2() {
        return delYn2;
    }

    public void setDelYn2(String delYn2) {
        this.delYn2 = delYn2;
    }

    public String getImportanceNm2() {
        return importanceNm2;
    }

    public void setImportanceNm2(String importanceNm2) {
        this.importanceNm2 = importanceNm2;
    }

    public String getMaxDelayUnitNm2() {
        return maxDelayUnitNm2;
    }

    public void setMaxDelayUnitNm2(String maxDelayUnitNm2) {
        this.maxDelayUnitNm2 = maxDelayUnitNm2;
    }

    public String getMaxErrorUnitNm2() {
        return maxErrorUnitNm2;
    }

    public void setMaxErrorUnitNm2(String maxErrorUnitNm2) {
        this.maxErrorUnitNm2 = maxErrorUnitNm2;
    }

    public String getDataPrDirNm2() {
        return dataPrDirNm2;
    }

    public void setDataPrDirNm2(String dataPrDirNm2) {
        this.dataPrDirNm2 = dataPrDirNm2;
    }

    public String getAppPrMethodNm2() {
        return appPrMethodNm2;
    }

    public void setAppPrMethodNm2(String appPrMethodNm2) {
        this.appPrMethodNm2 = appPrMethodNm2;
    }

    public String getDataPrMethodNm2() {
        return dataPrMethodNm2;
    }

    public void setDataPrMethodNm2(String dataPrMethodNm2) {
        this.dataPrMethodNm2 = dataPrMethodNm2;
    }

    public String getDataFrequencyNm2() {
        return dataFrequencyNm2;
    }

    public void setDataFrequencyNm2(String dataFrequencyNm2) {
        this.dataFrequencyNm2 = dataFrequencyNm2;
    }

    public String getStartResTypeNm2() {
        return startResTypeNm2;
    }

    public void setStartResTypeNm2(String startResTypeNm2) {
        this.startResTypeNm2 = startResTypeNm2;
    }

    public String getEndResTypeNm2() {
        return endResTypeNm2;
    }

    public void setEndResTypeNm2(String endResTypeNm2) {
        this.endResTypeNm2 = endResTypeNm2;
    }

    public boolean isInterfaceIdChanged() {
        return interfaceIdChanged;
    }

    public void setInterfaceIdChanged(boolean interfaceIdChanged) {
        this.interfaceIdChanged = interfaceIdChanged;
    }

    public boolean isChannelIdChanged() {
        return channelIdChanged;
    }

    public void setChannelIdChanged(boolean channelIdChanged) {
        this.channelIdChanged = channelIdChanged;
    }

    public boolean isImportanceChanged() {
        return importanceChanged;
    }

    public void setImportanceChanged(boolean importanceChanged) {
        this.importanceChanged = importanceChanged;
    }

    public boolean isMaxDelayTimeChanged() {
        return maxDelayTimeChanged;
    }

    public void setMaxDelayTimeChanged(boolean maxDelayTimeChanged) {
        this.maxDelayTimeChanged = maxDelayTimeChanged;
    }

    public boolean isMaxDelayUnitChanged() {
        return maxDelayUnitChanged;
    }

    public void setMaxDelayUnitChanged(boolean maxDelayUnitChanged) {
        this.maxDelayUnitChanged = maxDelayUnitChanged;
    }

    public boolean isMaxErrorTimeChanged() {
        return maxErrorTimeChanged;
    }

    public void setMaxErrorTimeChanged(boolean maxErrorTimeChanged) {
        this.maxErrorTimeChanged = maxErrorTimeChanged;
    }

    public boolean isMaxErrorUnitChanged() {
        return maxErrorUnitChanged;
    }

    public void setMaxErrorUnitChanged(boolean maxErrorUnitChanged) {
        this.maxErrorUnitChanged = maxErrorUnitChanged;
    }

    public boolean isRefPatternIdChanged() {
        return refPatternIdChanged;
    }

    public void setRefPatternIdChanged(boolean refPatternIdChanged) {
        this.refPatternIdChanged = refPatternIdChanged;
    }

    public boolean isDataPrDirChanged() {
        return dataPrDirChanged;
    }

    public void setDataPrDirChanged(boolean dataPrDirChanged) {
        this.dataPrDirChanged = dataPrDirChanged;
    }

    public boolean isAppPrMethodChanged() {
        return appPrMethodChanged;
    }

    public void setAppPrMethodChanged(boolean appPrMethodChanged) {
        this.appPrMethodChanged = appPrMethodChanged;
    }

    public boolean isDataPrMethodChanged() {
        return dataPrMethodChanged;
    }

    public void setDataPrMethodChanged(boolean dataPrMethodChanged) {
        this.dataPrMethodChanged = dataPrMethodChanged;
    }

    public boolean isDataFrequencyChanged() {
        return dataFrequencyChanged;
    }

    public void setDataFrequencyChanged(boolean dataFrequencyChanged) {
        this.dataFrequencyChanged = dataFrequencyChanged;
    }

    public String getDataFrequencyInput() {
		return dataFrequencyInput;
	}

	public void setDataFrequencyInput(String dataFrequencyInput) {
		this.dataFrequencyInput = dataFrequencyInput;
	}

	public String getDataFrequencyInput2() {
		return dataFrequencyInput2;
	}

	public void setDataFrequencyInput2(String dataFrequencyInput2) {
		this.dataFrequencyInput2 = dataFrequencyInput2;
	}

	public boolean isDataFrequencyInputChanged() {
		return dataFrequencyInputChanged;
	}

	public void setDataFrequencyInputChanged(boolean dataFrequencyInputChanged) {
		this.dataFrequencyInputChanged = dataFrequencyInputChanged;
	}

	public boolean isSizePerTranChanged() {
        return sizePerTranChanged;
    }

    public void setSizePerTranChanged(boolean sizePerTranChanged) {
        this.sizePerTranChanged = sizePerTranChanged;
    }

    public boolean isCntPerFrequencyChanged() {
        return cntPerFrequencyChanged;
    }

    public void setCntPerFrequencyChanged(boolean cntPerFrequencyChanged) {
        this.cntPerFrequencyChanged = cntPerFrequencyChanged;
    }

    public boolean isCntPerDayChanged() {
        return cntPerDayChanged;
    }

    public void setCntPerDayChanged(boolean cntPerDayChanged) {
        this.cntPerDayChanged = cntPerDayChanged;
    }

    public boolean isQttPerDayChanged() {
        return qttPerDayChanged;
    }

    public void setQttPerDayChanged(boolean qttPerDayChanged) {
        this.qttPerDayChanged = qttPerDayChanged;
    }

    public boolean isStartResTypeChanged() {
        return startResTypeChanged;
    }

    public void setStartResTypeChanged(boolean startResTypeChanged) {
        this.startResTypeChanged = startResTypeChanged;
    }

    public boolean isEndResTypeChanged() {
        return endResTypeChanged;
    }

    public void setEndResTypeChanged(boolean endResTypeChanged) {
        this.endResTypeChanged = endResTypeChanged;
    }

    public boolean isStartNodeCntChanged() {
        return startNodeCntChanged;
    }

    public void setStartNodeCntChanged(boolean startNodeCntChanged) {
        this.startNodeCntChanged = startNodeCntChanged;
    }

    public boolean isEndNodeCntChanged() {
        return endNodeCntChanged;
    }

    public void setEndNodeCntChanged(boolean endNodeCntChanged) {
        this.endNodeCntChanged = endNodeCntChanged;
    }

    public boolean isDataSeqYnChanged() {
        return dataSeqYnChanged;
    }

    public void setDataSeqYnChanged(boolean dataSeqYnChanged) {
        this.dataSeqYnChanged = dataSeqYnChanged;
    }

    public boolean isCommentsChanged() {
        return commentsChanged;
    }

    public void setCommentsChanged(boolean commentsChanged) {
        this.commentsChanged = commentsChanged;
    }

    public boolean isDelYnChanged() {
        return delYnChanged;
    }

    public void setDelYnChanged(boolean delYnChanged) {
        this.delYnChanged = delYnChanged;
    }

    public List getSystemList() {
        return systemList;
    }

    public void setSystemList(List systemList) {
        this.systemList = systemList;
    }

    public List getSystemList2() {
        return systemList2;
    }

    public void setSystemList2(List systemList2) {
        this.systemList2 = systemList2;
    }

    public List getBusinessList() {
        return businessList;
    }

    public void setBusinessList(List businessList) {
        this.businessList = businessList;
    }

    public List getBusinessList2() {
        return businessList2;
    }

    public void setBusinessList2(List businessList2) {
        this.businessList2 = businessList2;
    }

    public List getRelUsers() {
        return relUsers;
    }

    public void setRelUsers(List relUsers) {
        this.relUsers = relUsers;
    }

    public List getRelUsers2() {
        return relUsers2;
    }

    public void setRelUsers2(List relUsers2) {
        this.relUsers2 = relUsers2;
    }

    public String getAsisInterfaceId() {
        return asisInterfaceId;
    }

    public void setAsisInterfaceId(String asisInterfaceId) {
        this.asisInterfaceId = asisInterfaceId;
    }

    public String getAsisInterfaceId2() {
        return asisInterfaceId2;
    }

    public void setAsisInterfaceId2(String asisInterfaceId2) {
        this.asisInterfaceId2 = asisInterfaceId2;
    }

    public boolean isAsisInterfaceIdChanged() {
        return asisInterfaceIdChanged;
    }

    public void setAsisInterfaceIdChanged(boolean asisInterfaceIdChanged) {
        this.asisInterfaceIdChanged = asisInterfaceIdChanged;
    }

	public String getIntegrationId() {
		return integrationId;
	}

	public void setIntegrationId(String integrationId) {
		this.integrationId = integrationId;
	}

	public String getIntegrationId2() {
		return integrationId2;
	}

	public void setIntegrationId2(String integrationId2) {
		this.integrationId2 = integrationId2;
	}

	public boolean isIntegrationIdChanged() {
		return integrationIdChanged;
	}

	public void setIntegrationIdChanged(boolean integrationIdChanged) {
		this.integrationIdChanged = integrationIdChanged;
	}

	public Map getB2biInfo() {
		return b2biInfo;
	}

	public void setB2biInfo(Map b2biInfo) {
		this.b2biInfo = b2biInfo;
	}

	public Map getB2biInfo2() {
		return b2biInfo2;
	}

	public void setB2biInfo2(Map b2biInfo2) {
		this.b2biInfo2 = b2biInfo2;
	}
}
