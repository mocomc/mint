/*
 * Copyright (c) 2013 ~ 2015. Mocomsys's guys(Sanghoon Lim, Deahun Ham, dhkim, Solution TF), Inc.  All Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 * [주의!]
 * 본 코드가 하자없이 완벽할거라 믿었다간 엄청난 욕을 먹고 그로 인한 스트레스로 병들거나 심하면 살기 싫어질 수도 있으니 부디 살얼음판을 걷듯이 주의하여 사용하기 바란다.
 * 사용상 받을지 모를 스트레스 및 기타 피해에 대한 책임은 사용자 본인에게 있음을 명시한다. 부디 행운을 빈다.
 * Please Don't contact Mocomsys, Inc., NURITKUM SQUARE R&D TOWER, 11F DMC 1605,
 * Sangam-Dong, Mapo-Gu, Seoul, 121-795 Korea or visit mocomsys.com if you need additional information or have any questions.
 */

package com.mocomsys.iip.inhouse.wrapper;

public class ApprovalRouteVO {

	private String employeeNumber = "";
	private String activityCode = "0";
	private int routeSequence = 0;

	/**
	 * 결재선 객체를 생성한다.
	 * @author redjeans
	 * @param employeeNumber		사용자 사번 (TSU0101.USER_ID)
	 * @param activityCode			액티비티 코드 (0:기안, 1:결재, 2:합의, 3:후결, 7:병렬결재, 4:병렬합의, 9:통보)
	 * @param routeSequence		결재선 순서 (무조건 상신자가 시작점으로 0이며 이후의 값은 결재선이 편집된 순서대로 1씩 증가시켜 할당한다.) 
	 */
	public ApprovalRouteVO(String employeeNumber, String activityCode, int routeSequence) {
		this.employeeNumber = employeeNumber;
		this.activityCode = activityCode;
		this.routeSequence = routeSequence;
	}

	public String getEmployeeNumber() {
		return employeeNumber;
	}

	public void setEmployeeNumber(String employeeNumber) {
		this.employeeNumber = employeeNumber;
	}

	public String getActivityCode() {
		return activityCode;
	}

	public void setActivityCode(String activityCode) {
		this.activityCode = activityCode;
	}

	public int getRouteSequence() {
		return routeSequence;
	}

	public void setRouteSequence(int routeSequence) {
		this.routeSequence = routeSequence;
	}
}
