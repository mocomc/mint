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
import pep.per.mint.common.data.basic.Interface;

/**
 * Created by Solution TF on 15. 9. 9..
 */
public class InterfaceColumnHistory extends CacheableObject {

    /**
	 *
	 */
	private static final long serialVersionUID = 3157691122010057113L;


	@JsonProperty String modDate = defaultStringValue;
    @JsonProperty String version = defaultStringValue;

    @JsonProperty Interface interface1 = null;
    @JsonProperty Interface interface2 = null;

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



    public String getModDate() {
        return modDate;
    }

    public void setModDate(String modDate) {
        this.modDate = modDate;
    }

    public String getVersion() {
        return version;
    }

    public Interface getInterface1() {
		return interface1;
	}

	public void setInterface1(Interface interface1) {
		this.interface1 = interface1;
	}

	public Interface getInterface2() {
		return interface2;
	}

	public void setInterface2(Interface interface2) {
		this.interface2 = interface2;
	}

	public void setVersion(String version) {
        this.version = version;
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

    public boolean isAsisInterfaceIdChanged() {
        return asisInterfaceIdChanged;
    }

    public void setAsisInterfaceIdChanged(boolean asisInterfaceIdChanged) {
        this.asisInterfaceIdChanged = asisInterfaceIdChanged;
    }


	public boolean isIntegrationIdChanged() {
		return integrationIdChanged;
	}

	public void setIntegrationIdChanged(boolean integrationIdChanged) {
		this.integrationIdChanged = integrationIdChanged;
	}
}
