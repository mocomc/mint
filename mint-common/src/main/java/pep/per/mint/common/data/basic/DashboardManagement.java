/*
 * Copyright 2013 ~ 2014 Mocomsys(dhkim, Solution TF), Inc.  All Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 * Please contact Mocomsys, Inc., NURITKUM SQUARE R&D TOWER, 11F DMC 1605,
 * Sangam-Dong, Mapo-Gu, Seoul, 121-795 Korea or visit mocomsys.com
 * if you need additional information or have any questions.
*/
package pep.per.mint.common.data.basic;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import pep.per.mint.common.data.CacheableObject;

/**
 * 연계채널 Data Object
 * @author Solution TF
 *
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")
public class DashboardManagement  extends CacheableObject{

	/**
	 *
	 */
	private static final long serialVersionUID = 8432269590768008927L;

	/*** 보드ID */
	@JsonProperty("boardId")
	String boardId = defaultStringValue;

	/*** 보드CD */
	@JsonProperty("boardCd")
	String boardCd = defaultStringValue;

	/*** 보드명 */
	@JsonProperty("boardNm")
	String boardNm = defaultStringValue;

	/*** 보드구분 */
	@JsonProperty("boardType")
	String boardType = defaultStringValue;

	/*** 사용구분 */
	@JsonProperty("useYn")
	String useYn = defaultStringValue;

	/*** 설명 */
	@JsonProperty("comments")
	String comments = defaultStringValue;

	/*** 삭제구분 */
	@JsonProperty("delYn")
	String delYn = "N";

	/*** 등록일 */
	@JsonProperty("regDate")
	String regDate = defaultStringValue;

	/*** 등록자 */
	@JsonProperty("regId")
	String regId = defaultStringValue;

	/*** 최종수정일 */
	@JsonProperty("modDate")
	String modDate = defaultStringValue;

	/*** 최종수정자 */
	@JsonProperty("modId")
	String modId = defaultStringValue;



	public String getBoardId() {
		return boardId;
	}

	public void setBoardId(String boardId) {
		this.boardId = boardId;
	}

	public String getBoardCd() {
		return boardCd;
	}

	public void setBoardCd(String boardCd) {
		this.boardCd = boardCd;
	}

	public String getBoardNm() {
		return boardNm;
	}

	public void setBoardNm(String boardNm) {
		this.boardNm = boardNm;
	}

	public String getBoardType() {
		return boardType;
	}

	public void setBoardType(String boardType) {
		this.boardType = boardType;
	}

	public String getUseYn() {
		return useYn;
	}

	public void setUseYn(String useYn) {
		this.useYn = useYn;
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

	public String getRegDate() {
		return regDate;
	}

	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}

	public String getRegId() {
		return regId;
	}

	public void setRegId(String regId) {
		this.regId = regId;
	}

	public String getModDate() {
		return modDate;
	}

	public void setModDate(String modDate) {
		this.modDate = modDate;
	}

	public String getModId() {
		return modId;
	}

	public void setModId(String modId) {
		this.modId = modId;
	}

}
