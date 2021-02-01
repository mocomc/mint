/*
 * Copyright (c) 2013 ~ 2015. Mocomsys's guys(Sanghoon Lim, Deahun Ham, dhkim, Solution TF), Inc.  All Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 * [주의!]
 * 본 코드가 하자없이 완벽할거라 믿었다간 엄청난 욕을 먹고 그로 인한 스트레스로 병들거나 심하면 살기 싫어질 수도 있으니 부디 살얼음판을 걷듯이 주의하여 사용하기 바란다.
 * 사용상 받을지 모를 스트레스 및 기타 피해에 대한 책임은 사용자 본인에게 있음을 명시한다. 부디 행운을 빈다.
 * Please Don't contact Mocomsys, Inc., NURITKUM SQUARE R&D TOWER, 11F DMC 1605,
 * Sangam-Dong, Mapo-Gu, Seoul, 121-795 Korea or visit mocomsys.com if you need additional information or have any questions.
 */
package pep.per.mint.database.service.co;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pep.per.mint.common.data.basic.*;
import pep.per.mint.common.data.basic.System;
import pep.per.mint.common.util.Util;

import java.io.File;
import java.util.*;

import static org.junit.Assert.fail;

/**
 * @author Solution TF
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
		//"file:./src/test/resources/com/mocomsys/mint/database/config/database-context-test.xml"})
		"classpath:/config/database-context.xml","classpath:/config/inhouse-context.xml"})
		//"file:./src/test/resources/config/database-context-test.xml"})
public class ApprovalServiceTest {

	private static final Logger logger = LoggerFactory.getLogger(ApprovalServiceTest.class);

	@Autowired
	ApprovalService approvalService;

	/**
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
	}


	/**
	 * 요건상태변경  테스트
	 */
	@Test
	public void testUpdateRequirementStatus() {

		try {

			String requirementId = "RQ00000044";
			String modId = "11826";
			String modDate = Util.getFormatedDate();
			String status = "C0";
			Approval requirementApproval = new Approval();
			requirementApproval.setApprovalItemId(requirementId);
			requirementApproval.setApprovalItemType("0");
			requirementApproval.setReqUserId(modId);

			StringBuffer misid = new StringBuffer();
			String prefix = Util.rightPad(Util.join("iip",requirementApproval.getReqUserId(), requirementApproval.getApprovalItemType()),15,"0");
			misid.append(prefix).append(Util.getFormatedDate("yyyyMMddHHmmssSSS"));//17
			requirementApproval.setMisid(misid.toString());

			ApprovalUser approvalUser = new ApprovalUser();
			approvalUser.setApprovalItemId(requirementId);
			approvalUser.setApprovalItemType("0");
			approvalUser.setRoleType("0");
			approvalUser.setAdmUserId("11825");
			List<ApprovalUser> approvalUsers = new ArrayList<ApprovalUser>();
			approvalUsers.add(approvalUser);
			requirementApproval.setApprovalUsers(approvalUsers);
			requirementApproval.setReqDate(modDate);
			requirementApproval.setReqType(status);

			int res = approvalService.requestApproval(requirementApproval);
			logger.debug("result:" + res);


		} catch (Exception e) {
			if(logger != null)
				logger.error("testUpdateRequirementStatus",e);
			fail("Fail testUpdateRequirementStatus");
		}
	}


	@Test
	public void testGetApprovalUserList(){

		try {


			List<ApprovalUser> list = approvalService.getApprovalUserList("PERPP0506","CN00000001");
			logger.debug(Util.join("result:" ,Util.toJSONString(list)));


		} catch (Exception e) {
			if(logger != null)
				logger.error("testGetApprovalUserList",e);
			fail("Fail testGetApprovalUserList");
		}
	}

	@Test
	public void testGetApprovalLineUserList(){

		try {


			List<ApprovalUser> list = approvalService.getApprovalLineUserList("CN00000001");
			logger.debug(Util.join("result:" ,Util.toJSONString(list)));


		} catch (Exception e) {
			if(logger != null)
				logger.error("testGetApprovalLineUserList",e);
			fail("Fail testGetApprovalLineUserList");
		}
	}


	@Test
	public void testRequestApproval(){
		try{


			Approval approval = (Approval) Util.readObjectFromJson(new File("data/test/REST-C01-CO-02-00-010.json"), Approval.class, "UTF-8");
			StringBuffer misid = new StringBuffer();
			String prefix = Util.rightPad(Util.join("iip", approval.getReqUserId(), approval.getApprovalItemType()), 15, "0");
			misid.append(prefix).append(Util.getFormatedDate("yyyyMMddHHmmssSSS"));//17
			approval.setMisid(misid.toString());

			approvalService.requestApproval(approval);

		}catch(Exception e){
			logger.error("testRequestApproval",e);
			fail("Fail testRequestApproval");
		}

	}
}
