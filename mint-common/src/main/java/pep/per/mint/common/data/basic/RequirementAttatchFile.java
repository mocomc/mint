/*
 * Copyright 2013 ~ 2014 Mocomsys(dhkim, Solution TF), Inc.  All Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 * Please contact Mocomsys, Inc., NURITKUM SQUARE R&D TOWER, 11F DMC 1605, 
 * Sangam-Dong, Mapo-Gu, Seoul, 121-795 Korea or visit mocomsys.com 
 * if you need additional information or have any questions.
*/
package pep.per.mint.common.data.basic;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonProperty;

import pep.per.mint.common.data.CacheableObject;

/**
 * 인터페이스요건 첨부파일 Data Object
 * @author Solution TF
 *
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")
public class RequirementAttatchFile  extends CacheableObject{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4775994349206578281L;

	/*** 요건ID */
	@JsonProperty("requirementId")
	String requirementId = defaultStringValue;
	
	/*** 첨부파일ID */
	@JsonProperty("fileId")
	String fileId = defaultStringValue;
	
	/*** 파일명 */
	@JsonProperty("fileNm")
	String fileNm = defaultStringValue;
	
	/*** 업무코드 */
	@JsonProperty("filePath")
	String filePath = defaultStringValue;
	
	/*** 삭제여부 */
	@JsonProperty("delYn")
	String delYn = "N";
	
	/*** 등록일 */
	@JsonProperty("regDate")
	String regDate = defaultStringValue;
	
	/*** 등록자 */
	@JsonProperty("regId")
	String regId = defaultStringValue;
	
	/*** 수정일 */
	@JsonProperty("modDate")
	String modDate = defaultStringValue;
	
	/*** 수정자 */
	@JsonProperty("modId")
	String modId = defaultStringValue;

	/**
	 * @return the requirementId
	 */
	public String getRequirementId() {
		return requirementId;
	}

	/**
	 * @param requirementId the requirementId to set
	 */
	public void setRequirementId(String requirementId) {
		this.requirementId = requirementId;
	}

	/**
	 * @return the fileId
	 */
	public String getFileId() {
		return fileId;
	}

	/**
	 * @param fileId the fileId to set
	 */
	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	/**
	 * @return the fileNm
	 */
	public String getFileNm() {
		return fileNm;
	}

	/**
	 * @param fileNm the fileNm to set
	 */
	public void setFileNm(String fileNm) {
		this.fileNm = fileNm;
	}

	/**
	 * @return the filePath
	 */
	public String getFilePath() {
		return filePath;
	}

	/**
	 * @param filePath the filePath to set
	 */
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	/**
	 * @return the delYn
	 */
	public String getDelYn() {
		return delYn;
	}

	/**
	 * @param delYn the delYn to set
	 */
	public void setDelYn(String delYn) {
		this.delYn = delYn;
	}

	/**
	 * @return the regDate
	 */
	public String getRegDate() {
		return regDate;
	}

	/**
	 * @param regDate the regDate to set
	 */
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}

	/**
	 * @return the regId
	 */
	public String getRegId() {
		return regId;
	}

	/**
	 * @param regId the regId to set
	 */
	public void setRegId(String regId) {
		this.regId = regId;
	}

	/**
	 * @return the modDate
	 */
	public String getModDate() {
		return modDate;
	}

	/**
	 * @param modDate the modDate to set
	 */
	public void setModDate(String modDate) {
		this.modDate = modDate;
	}

	/**
	 * @return the modId
	 */
	public String getModId() {
		return modId;
	}

	/**
	 * @param modId the modId to set
	 */
	public void setModId(String modId) {
		this.modId = modId;
	}


}
