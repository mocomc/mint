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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MySingleServiceClient {

	Logger logger = LoggerFactory.getLogger(MySingleServiceClient.class);

	private String esbServer = "";
	private String cid = "";
	private String cpw = "";
	private String ucid = "";
	private String ucpw = "";
	/**
	 * 마이싱글 웹서비스 클라이언트 객체를 생성한다. 모든 파라메터는 컨피그로부터 읽어와야 한다.
	 * @param esbServer			    마이싱글 서버 주소
	 * @param cid					마이싱글 권한 ID 
	 * @param cpw					마이싱글 권한 Password
	 * @param ucid					계정권한 결재 서비스용 ID
	 * @param ucpw					계정권한 결재 서비스용 Password
	 *
	 * @throws Exception			연결 실패 시 ESBFaultException 타입의 예외를 던진다.
	 */
	public MySingleServiceClient(String esbServer, String cid, String cpw, String ucid, String ucpw) throws Exception {

		this.esbServer = esbServer;
		this.cid = cid;
		this.cpw = cpw;
		this.ucid = ucid;
		this.ucpw = ucpw;
		// 여기에 연결 구현 코드를 삽입한다. userService와 approvalService 둘 다 연결해야 한다.
	}



	/**
	 *
	 * @param title					결재 제목
	 * @param htmlContent			결재 내용
	 * @param misId					해당 결재 요청 건에 발번된 유니크 ID (32byte)
	 * @param route					결재선 객체를 Array 타입으로 묶어 전달한다. 
	 * @throws Exception			다양한 타입의 예외를 던질 수 있다. 예외가 발생하지 않으면 성공.
	 */
	public void requestApproval(String title, String htmlContent, String misId, ApprovalRouteVO[] route) throws Exception {

		// 여기에 submitApproval 파라메터 준비 및 호출 코드를 삽입한다. 
		//throw new Exception("요건 상훈이가 만든거이다. 일부러 에러내본다. 2PC 되는가 확인해 보자(misId로 테이블에 값이 insert 되었는지 조회해봐라 들어가지 말아야 한다. rollback됐으거니):misId:" + misId);
	}

	/**
	 * reqComments 를 추가한 버전
	 * @param title					결재 제목
	 * @param htmlContent			결재 내용
	 * @param misId					해당 결재 요청 건에 발번된 유니크 ID (32byte)
	 * @param route					결재선 객체를 Array 타입으로 묶어 전달한다.
	 * @param reqComments
	 * @throws Exception
	 */
	public void requestApproval(String title, String htmlContent, String misId, ApprovalRouteVO[] route, String reqComments) throws Exception {
		logger.info("========================================");
		for (int i = 0; i < route.length; i++) {
			logger.info("->>>>>emp[" + i + "][activeCode:"+route[i].getActivityCode()+"]:" + route[i].getEmployeeNumber());
		}
		logger.info("========================================");
		// 여기에 submitApproval 파라메터 준비 및 호출 코드를 삽입한다.
		//throw new Exception("요건 상훈이가 만든거이다. 일부러 에러내본다. 2PC 되는가 확인해 보자(misId로 테이블에 값이 insert 되었는지 조회해봐라 들어가지 말아야 한다. rollback됐으거니):misId:" + misId);
	}

}
