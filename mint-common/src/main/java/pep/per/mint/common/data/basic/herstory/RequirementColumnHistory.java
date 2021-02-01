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
import pep.per.mint.common.data.basic.Requirement;

/**
 * Created by Solution TF on 15. 9. 9..
 */
public class RequirementColumnHistory extends CacheableObject {



    /**
	 *
	 */
	private static final long serialVersionUID = 3354108338396046787L;


	@JsonProperty String modDate = defaultStringValue;
    @JsonProperty String version = defaultStringValue;
    @JsonProperty String requirementId = defaultStringValue;
    @JsonProperty Requirement requirement = null;
    @JsonProperty Requirement requirement2 = null;
    @JsonProperty boolean requirementNmChanged = false;
    @JsonProperty boolean statusChanged = false;
    @JsonProperty boolean commentsChanged = false;
    @JsonProperty boolean devExpYmdChanged = false;
    @JsonProperty boolean devFinYmdChanged = false;
    @JsonProperty boolean testExpYmdChanged = false;
    @JsonProperty boolean testFinYmdChanged = false;
    @JsonProperty boolean realExpYmdChanged = false;
    @JsonProperty boolean realFinYmdChanged = false;
    @JsonProperty boolean delYnChanged = false;
    @JsonProperty boolean businessIdChanged = false;
    @JsonProperty boolean interfaceIdChanged = false;
    @JsonProperty InterfaceColumnHistory interfaceColumnHerstory;


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

    public boolean isRequirementNmChanged() {
        return requirementNmChanged;
    }

    public void setRequirementNmChanged(boolean requirementNmChanged) {
        this.requirementNmChanged = requirementNmChanged;
    }

    public boolean isStatusChanged() {
        return statusChanged;
    }

    public void setStatusChanged(boolean statusChanged) {
        this.statusChanged = statusChanged;
    }


    public boolean isCommentsChanged() {
        return commentsChanged;
    }

    public void setCommentsChanged(boolean commentsChanged) {
        this.commentsChanged = commentsChanged;
    }


    public boolean isDevExpYmdChanged() {
        return devExpYmdChanged;
    }

    public void setDevExpYmdChanged(boolean devExpYmdChanged) {
        this.devExpYmdChanged = devExpYmdChanged;
    }


    public boolean isDevFinYmdChanged() {
        return devFinYmdChanged;
    }

    public Requirement getRequirement() {
		return requirement;
	}

	public void setRequirement(Requirement requirement) {
		this.requirement = requirement;
	}

	public Requirement getRequirement2() {
		return requirement2;
	}

	public void setRequirement2(Requirement requirement2) {
		this.requirement2 = requirement2;
	}

	public InterfaceColumnHistory getInterfaceColumnHerstory() {
		return interfaceColumnHerstory;
	}

	public void setInterfaceColumnHerstory(InterfaceColumnHistory interfaceColumnHerstory) {
		this.interfaceColumnHerstory = interfaceColumnHerstory;
	}

	public void setDevFinYmdChanged(boolean devFinYmdChanged) {
        this.devFinYmdChanged = devFinYmdChanged;
    }


    public boolean isTestExpYmdChanged() {
        return testExpYmdChanged;
    }

    public void setTestExpYmdChanged(boolean testExpYmdChanged) {
        this.testExpYmdChanged = testExpYmdChanged;
    }


    public boolean isTestFinYmdChanged() {
        return testFinYmdChanged;
    }

    public void setTestFinYmdChanged(boolean testFinYmdChanged) {
        this.testFinYmdChanged = testFinYmdChanged;
    }

    public boolean isRealExpYmdChanged() {
        return realExpYmdChanged;
    }

    public void setRealExpYmdChanged(boolean realExpYmdChanged) {
        this.realExpYmdChanged = realExpYmdChanged;
    }


    public boolean isRealFinYmdChanged() {
        return realFinYmdChanged;
    }

    public void setRealFinYmdChanged(boolean realFinYmdChanged) {
        this.realFinYmdChanged = realFinYmdChanged;
    }


    public boolean isDelYnChanged() {
        return delYnChanged;
    }

    public void setDelYnChanged(boolean delYnChanged) {
        this.delYnChanged = delYnChanged;
    }


    public boolean isBusinessIdChanged() {
        return businessIdChanged;
    }

    public void setBusinessIdChanged(boolean businessIdChanged) {
        this.businessIdChanged = businessIdChanged;
    }


    public boolean isInterfaceIdChanged() {
        return interfaceIdChanged;
    }

    public void setInterfaceIdChanged(boolean interfaceIdChanged) {
        this.interfaceIdChanged = interfaceIdChanged;
    }

    public InterfaceColumnHistory getInterfaceColumnHistory() {
        return interfaceColumnHerstory;
    }

    public void setInterfaceColumnHistory(InterfaceColumnHistory interfaceColumnHerstory) {
        this.interfaceColumnHerstory = interfaceColumnHerstory;
    }

}
