package pep.per.mint.database.service.an;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import pep.per.mint.common.data.basic.runtime.InterfaceModel;
import pep.per.mint.common.data.basic.runtime.RequirementModel;
import pep.per.mint.common.util.Util;
import pep.per.mint.database.mapper.an.RequirementMapper;


/**
 * @author Solution TF
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
		//"file:./src/test/resources/com/mocomsys/mint/database/config/database-context-test.xml"})
		"classpath:/config/database-context.xml"})
		//"file:./src/test/resources/config/database-context-test.xml"})
public class RequirementServiceTest {
	private static final Logger logger = LoggerFactory.getLogger(RequirementServiceTest.class);

	@Autowired
	RequirementService requirementService;

	@Autowired
	RequirementHerstoryService requirementHerstoryService;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testGetRequirementList() {

		//리소스 코드 리스트 조회
		try {


			Map map = new HashMap<String,Object>();
			//map.put("channelId", "CN00000001");
			//map.put("status", "0");
			//map.put("systemNm", "인사");
			//map.put("businessId", "BZ00000070");
			//map.put("businessNm", "ID");
			//map.put("requirementId", "RQ00054634");
			//map.put("requirementNm", "고객");
			//map.put("service", "PRD01.c");
			//map.put("resourceNm", "DB");
			//map.put("tag", "고객");
			//조회접근권한세팅
			//map.put("userId", "iip");
			map.put("isInterfaceAdmin", "Y");
			map.put("delYn", "N");
			map.put("bizPathView", "Y");

			long elapsed = java.lang.System.currentTimeMillis();
			List<Requirement> list = requirementService.getRequirementList(map);
			elapsed = java.lang.System.currentTimeMillis() - elapsed;
			if(list != null){
				int i = 0;
				logger.debug("Requirement List =====================================================================================");
				for(Requirement requirement : list){
					logger.debug(Util.join("Requirement[",i ++,"]:",Util.toJSONString(requirement)));
				}
				logger.debug("----------------------------------------------------------------------------------------------------");
			}

			logger.debug("elapsed time:" + elapsed);
		} catch (Exception e) {
			if(logger != null)
				logger.error("testGetRequirementList",e);
			fail("Fail testGetRequirementList");
		}
	}

	@Test
	public void testSelectInterfacesByAttributes() {
		try{
			Map params = new HashMap<String,Object>();
			List<InterfaceProperties> attrs = new ArrayList<InterfaceProperties>();
			InterfaceProperties prop1 = new InterfaceProperties();
			prop1.setAttrId("CA00000001");
			prop1.setIdx(0);
			prop1.setChannelId("CN00000001");
			prop1.setAttrValue("없");
			attrs.add(prop1);

			InterfaceProperties prop2 = new InterfaceProperties();
			prop2.setAttrId("CA00000002");
			prop2.setIdx(0);
			prop2.setChannelId("CN00000001");
			prop2.setAttrValue("없");
			attrs.add(prop2);

			params.put("channelAttributes", attrs);
			List<String> nms = requirementService.selectInterfacesByAttributes(params);
			logger.debug("interfaceIds:" + Util.toJSONString(nms));
		}catch(Exception e){
			logger.error("",e);
		}

	}

	@Test
	public void testGetRequirementListByPage() {

		//리소스 코드 리스트 조회
		try {


			Map map = new HashMap<String,Object>();
			//map.put("channelId", "CN00000001");
			//map.put("status", "0");
			//map.put("systemNm", "인사");
			//map.put("businessId", "BZ00000070");
			//map.put("businessNm", "ID");
			//map.put("requirementId", "RQ00054634");
			//map.put("requirementNm", "고객");
			//map.put("service", "PRD01.c");
			//map.put("resourceNm", "DB");
			//map.put("tag", "고객");
			//조회접근권한세팅
			map.put("userId", "iipdev");
			map.put("isInterfaceAdmin", "N");
			map.put("delYn", "N");
			map.put("bizPathView", "Y");


			map.put("page",10);
			map.put("pageCount",0);
			map.put("totalCount",0);
			map.put("perCount",10);
			map.put("tailCount",0);


			map.put("startIndex", 101);
			map.put("endIndex", 110);

			int totalCount = requirementService.getRequirementTotalCount(map);
			logger.debug(Util.join("Requirement list total count:",totalCount));
			List<Requirement> list = requirementService.getRequirementListByPage(map);
			logger.debug(Util.join("Requirement list:",Util.toJSONString(list)));
			/*if(list != null){
				int i = 0;
				logger.debug("Requirement List =====================================================================================");
				for(Requirement requirement : list){
					logger.debug(Util.join("Requirement[",i ++,"]:",Util.toJSONString(requirement)));
				}
				logger.debug("----------------------------------------------------------------------------------------------------");
			}*/


		} catch (Exception e) {
			if(logger != null)
				logger.error("testGetRequirementListByPage",e);
			fail("Fail testGetRequirementListByPage");
		}
	}

	@Test
	public void testGetRequirementListByPageV2() {

		//리소스 코드 리스트 조회
		try {


			Map map = new HashMap<String,Object>();
			//map.put("channelId", "CN00000001");
			//map.put("status", "0");
			//map.put("systemNm", "인사");
			//map.put("businessId", "BZ00000070");
			//map.put("businessNm", "ID");
			//map.put("requirementId", "RQ00054634");
			//map.put("requirementNm", "고객");
			//map.put("service", "PRD01.c");
			//map.put("resourceNm", "DB");
			//map.put("tag", "고객");
			//조회접근권한세팅
			map.put("userId", "iip");
			map.put("isInterfaceAdmin", "Y");
			map.put("delYn", "N");
			map.put("bizPathView", "Y");


			map.put("page",10);
			map.put("pageCount",0);
			map.put("totalCount",0);
			map.put("perCount",20);
			map.put("tailCount",0);


			map.put("startIndex", 1);
			map.put("endIndex",20);
            map.put("dataPrMethod", "0");


			List<InterfaceProperties> attrs1 = new ArrayList<InterfaceProperties>();
			{
				InterfaceProperties prop1 = new InterfaceProperties();
				prop1.setAttrId("CA00000001");
				prop1.setIdx(0);
				prop1.setChannelId("CN00000001");
				prop1.setAttrValue("없");
				attrs1.add(prop1);

				InterfaceProperties prop2 = new InterfaceProperties();
				prop2.setAttrId("CA00000002");
				prop2.setIdx(0);
				prop2.setChannelId("CN00000001");
				prop2.setAttrValue("없");
				attrs1.add(prop2);
			}
			//map.put("channelAttributes", attrs1);



			List<InterfaceProperties> attrs2 = new ArrayList<InterfaceProperties>();
			{
				InterfaceProperties prop1 = new InterfaceProperties();
				prop1.setAttrId("CA00000001");
				prop1.setIdx(0);
				prop1.setChannelId("CN00000001");
				prop1.setAttrValue("없");
				attrs2.add(prop1);

				InterfaceProperties prop2 = new InterfaceProperties();
				prop2.setAttrId("CA00000002");
				prop2.setIdx(0);
				prop2.setChannelId("CN00000001");
				prop2.setAttrValue("없");
				attrs2.add(prop2);
			}
			//map.put("siteAttributes", attrs2);


			map.put("orderByField","INTEGRATION_ID DESC");

			//map.put("integrationId", "IM_CIS-CI_0002");

			int totalCount = requirementService.getRequirementTotalCount(map);
			//logger.debug(Util.join("Requirement list total count:",totalCount));
			List<Requirement> list = requirementService.getRequirementListByPageV2(map);
			logger.debug(Util.join("Requirement list:",Util.toJSONString(list)));
//			if(list != null){
//				int i = 0;
//				logger.debug("Requirement List =====================================================================================");
//				for(Requirement requirement : list){
//					logger.debug(Util.join("Requirement[",i ++,"]:",Util.toJSONString(requirement)));
//				}
//				logger.debug("----------------------------------------------------------------------------------------------------");
//			}


		} catch (Exception e) {
			if(logger != null)
				logger.error("testGetRequirementListByPage",e);
			fail("Fail testGetRequirementListByPage");
		}
	}


	/*@Test
	public void testInsertRequirementComment() {

		//리소스 코드 리스트 조회
		try {
			//String requirementId = "REQ_DIMS_0001";
			String requirementId = "RQ00054634";

			RequirementComment rc = new RequirementComment();
			rc.setRequirementId(requirementId);
			rc.setComment("1");
			rc.setRegDate("2015081000000000");
			rc.setRegId("iip");

			int res = requirementService.insertRequirementComment(rc);

			logger.debug(Util.join("res:",res, "\nRequirementComment:", Util.toJSONString(rc)));

		} catch (Exception e) {
			if(logger != null)
				logger.error("testInsertRequirementComment",e);
			fail("Fail testInsertRequirementComment");
		}
	}*/


	/*@Test
	public void testInsertRequirementAttatchFile() {

		//리소스 코드 리스트 조회
		try {
			//String requirementId = "REQ_DIMS_0001";
			String requirementId = "RQ00054634";

			RequirementAttatchFile raf = new RequirementAttatchFile();
			raf.setRequirementId(requirementId);
			raf.setFileNm("1");
			raf.setFilePath("1");
			raf.setRegDate("2015081000000000");
			raf.setRegId("iip");

			int res = requirementService.insertRequirementRequirementAttatchFile(raf);

			logger.debug(Util.join("res:",res, "\nRequirementAttatchFile:", Util.toJSONString(raf)));

		} catch (Exception e) {
			if(logger != null)
				logger.error("testInsertRequirementAttatchFile",e);
			fail("Fail testInsertRequirementAttatchFile");
		}
	}*/

	/*@Test
	public void testInsertInterfaceSystemMap() {

		try {
			String interfaceId = "IS136D0001";

			pep.per.mint.common.data.basic.System system = new pep.per.mint.common.data.basic.System();
			system.setSystemId("SS00000134");
			system.setSystemNm("123");
			system.setNodeType("2");
			system.setRegDate("201508100000000");
			system.setRegId("iip");

			int res = requirementService.insertInterfaceSystemMap(interfaceId, system);

			logger.debug(Util.join("res:",res, "\nInterfaceSystemMap:", Util.toJSONString(system)));

		} catch (Exception e) {
			if(logger != null)
				logger.error("testInsertInterfaceSystemMap",e);
			fail("Fail testInsertInterfaceSystemMap");
		}
	}*/


	/*@Test
	public void testInsertInterfaceBusinessMap() {

		try {
			String interfaceId = "IS136D0001";

			pep.per.mint.common.data.basic.Business business = new pep.per.mint.common.data.basic.Business();
			business.setBusinessId("BZ00000003");
			business.setBusinessNm("123");
			business.setNodeType("2");
			business.setRegDate("201508100000000");
			business.setRegId("iip");

			int res = requirementService.insertInterfaceBusinessMap(interfaceId, business);

			logger.debug(Util.join("res:",res, "\nInterfaceBusinessMap:", Util.toJSONString(business)));

		} catch (Exception e) {
			if(logger != null)
				logger.error("testInsertInterfaceBusinessMap",e);
			fail("Fail testInsertInterfaceBusinessMap");
		}
	}*/

	/*
	@Test
	public void testInsertInterfaceTagMap() {

		try {
			String interfaceId = "IS136D0001";

			InterfaceTag tag = new InterfaceTag();
			tag.setInterfaceId(interfaceId);
			tag.setTag("TAG00000003");
			tag.setRegDate("201508100000000");
			tag.setRegId("iip");

			int res = requirementService.insertInterfaceTag(interfaceId, tag);

			logger.debug(Util.join("res:",res, "\nInterfaceTagMap:", Util.toJSONString(tag)));

		} catch (Exception e) {
			if(logger != null)
				logger.error("testInsertInterfaceTagMap",e);
			fail("Fail testInsertInterfaceTagMap");
		}
	}*/


	/*@Test
	public void testInsertInterfaceRelUserMap() {

		try {
			String interfaceId = "IS136D0001";

			RelUser relUser = new RelUser();
			relUser.setRoleType("0");
			User user = new User();
			user.setUserId("iip");
			relUser.setUser(user);
			relUser.setRegDate("201508100000000");
			relUser.setRegId("iip");

			int res = requirementService.insertInterfaceRelUser(interfaceId, relUser);

			logger.debug(Util.join("res:",res, "\nInterfaceReluserMap:", Util.toJSONString(relUser)));

		} catch (Exception e) {
			if(logger != null)
				logger.error("testInsertInterfaceReluserMap",e);
			fail("Fail testInsertInterfaceReluserMap");
		}
	}*/





	/*@Test
	public void testSelectRequirementAttatchFileId() {

		//리소스 코드 리스트 조회
		try {
			//String requirementId = "REQ_DIMS_0001";
			String requirementId = "RQ00054634";
			String id = requirementService.selectRequirementAttatchFileId(requirementId);

				logger.debug(Util.join("RequirementAttatchFileId:",id));

		} catch (Exception e) {
			if(logger != null)
				logger.error("testSelectRequirementAttatchFileId",e);
			fail("Fail testSelectRequirementAttatchFileId");
		}
	}
*/
	@Test
	public void testGetRequirementDetail() {

		//리소스 코드 리스트 조회
		try {
			//String requirementId = "REQ_DIMS_0001";
			String requirementId = "RQ00000065";
			Map<String, String> params = new HashMap<String,String>();
			params.put("requirementId",requirementId);
			params.put("bizPathView", "N");
			Requirement requirementDetail = requirementService.getRequirementDetail(params);

			if(requirementDetail != null){
				int i = 0;
				logger.debug("Requirement Detail =====================================================================================");
				logger.debug(Util.join("Requirement[",i ++,"]:",Util.toJSONString(requirementDetail)));
				logger.debug("----------------------------------------------------------------------------------------------------");
			}
			else{
				logger.debug("WHAT");
			}

		} catch (Exception e) {
			if(logger != null)
				logger.error("testGetRequirementDetail",e);
			fail("Fail testGetRequirementDetail");
		}
	}

	@Test
	public void testGetRequirementDetailList() {


		//리소스 코드 리스트 조회
		try {
			//String requirementId = "REQ_DIMS_0001";
			//String requirementId = "RQ00000246";
			Map<String, Object> params = new HashMap<String,Object>();
            List<String> requirementIds = new ArrayList<String>();
            requirementIds.add("RQ00010189");
            params.put("requirementIds",requirementIds);
			params.put("bizPathView", "N");
			//params.put("all", "Y");
			List<Requirement> list = requirementService.getRequirementDetailList(params);

			if(list != null && list.size() > 0){
				int i = 0;
				logger.debug("Requirement Detail =====================================================================================");
				logger.debug(Util.join("Requirement[",i ++,"]:",Util.toJSONString(list)));
				logger.debug("----------------------------------------------------------------------------------------------------");
			}
			else{
				logger.debug("WHAT");
			}

		} catch (Exception e) {
			if(logger != null)
				logger.error("testGetRequirementDetail",e);
			fail("Fail testGetRequirementDetail");
		}
	}

	@Test
	public void testGetRequirementsPageInfo() {

		try {
			Map<String, Object> params = new HashMap<String,Object>();

            params.put("page",new Integer(1));

			params.put("perCount", new Integer(1000));

			PageInfo pageInfo = requirementService.getRequirementsPageInfo(params);

			if(pageInfo != null){

				logger.debug(Util.join("pageInfo:",Util.toJSONString(pageInfo)));
			}
			else{
				logger.debug("WHAT");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	@Autowired
	RequirementMapper requirementMapper;

	@Test
	public void testInsertInterface() {

		try {

			/*select
			#{interfaceIdInfo.solutionCd} || #{interfaceIdInfo.systemCd} || #{interfaceIdInfo.serviceType} || lpad(to_char(to_number(max(nvl(substr(a.INTERFACE_ID, 6),'0'))) + 1),4,'0')
			from TAN0201 a
			inner join (select * from TAN0213 where NODE_TYPE = #{interfaceIdInfo.nodeType}) b on a.INTERFACE_ID = b.INTERFACE_ID
			inner join TIM0002 c on a.CHANNEL_ID = c.CHANNEL_ID
			where 1 = 1
			and c.CHANNEL_ID     = #{interfaceIdInfo.channelId}
			and b.SYSTEM_ID 	   = #{interfaceIdInfo.systemId}
			and b.RESOURCE_TYPE  = #{interfaceIdInfo.resourceCd}
			*/
			Interface interfaze = (Interface) Util.readObjectFromJson(new File("data/test/REST-C01-AN-02-00.json"), Interface.class, "UTF-8");
 			Map mapInfo = new HashMap();
			mapInfo.put("solutionCd","X");
			mapInfo.put("systemCd","02A");
			mapInfo.put("serviceType","D");
			mapInfo.put("nodeType","0");
			mapInfo.put("channelId","CN000000001");
			mapInfo.put("systemId","SS00000199");
			mapInfo.put("resourceCd","0");

			int res = requirementMapper.insertInterface(interfaze);
			logger.debug(Util.join("insertInterface res:",res,", interfaceId:", interfaze.getInterfaceId()));


		} catch (Exception e) {
			if(logger != null)
				logger.error("testInsertRequirement",e);
			fail("Fail testInsertRequirement");
		}
	}

	/**
	 * <pre>
	 * 요건+모델 등록수정 테스트
	 * 절차 
	 * 1) newInterfaceId,  newInterfaceNm 값 신규값으로 세팅 
	 * 2) JUnit run 
	 * </pre>
	 */
	@Test
	public void testCreateAndUpdateRequirementModel() {
		try {
			String newInterfaceId = "interface-model-test-105";
			String newInterfaceNm = "interface-model-test-105";
			if(newInterfaceId == null || newInterfaceNm == null) {
				throw new RuntimeException("newInterfaceId 또는 newInterfaceNm 값을 먼저 세팅하고 테스트해 주세요.");
			}
			
			RequirementModel requirementModel = (RequirementModel) Util.readObjectFromJson(new File("src/test/data/an/RequirementModel.json"), RequirementModel.class, "UTF-8");
			//logger.debug(Util.join("requirementModel:",Util.toJSONPrettyString(requirementModel)));
			Requirement requirement = requirementModel.getRequirement();
			requirement.setRequirementNm(newInterfaceNm);
			
			Interface interfaze = requirement.getInterfaceInfo();
			interfaze.setIntegrationId(newInterfaceId);
			interfaze.setInterfaceNm(newInterfaceNm);
			interfaze.setInterfaceId(null);
			
			InterfaceModel interfaceModel = requirementModel.getInterfaceModel();
			interfaceModel.setName(newInterfaceNm);
			
			requirementModel.setRegDate(Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI));
			requirementModel.setRegId("iip");

			int res = requirementService.createRequirementModel(requirementModel);
			
			logger.debug("created model info:" + Util.toJSONPrettyString(requirementModel));
			
			requirementModel.getRequirement().setRequirementNm(newInterfaceNm + "모델수정");
			requirementModel.setModDate(Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI));
			requirementModel.setModId("iip");
			requirementModel.getInterfaceModel().setName(newInterfaceNm + "모델수정");
			res = requirementService.updateRequirementModel(requirementModel);
			
			logger.debug("updated model info:" + Util.toJSONPrettyString(requirementModel));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	/**
	 * 요건등록 테스트
	 */
	@Test
	public void testCreateRequirement() {

		try {

			Requirement requirement = (Requirement) Util.readObjectFromJson(new File("data/test/REST-C01-AN-01-00.json"), Requirement.class, "UTF-8");
			//logger.debug(Util.join("testInsertRequirement before:"," requirementId:", requirement.getRequirementId()));
			//String requirementId = requirementService.selectRequirementId();
			//requirement.setId(requirementId);
			//requirement.setRequirementId(requirementId);
			//if(requirement.getDevExpYmd() == null) logger.debug("getDevExpYmd is null");
			//if(Util.isEmpty(requirement.getDevExpYmd() ) )logger.debug("getDevExpYmd is empty");

			//for(int i = 0 ; i < 1 ; i ++ ){
				int res = requirementService.createRequirement(requirement);
				logger.debug(Util.join("testInsertRequirement res:",res,", requirementId:", requirement.getRequirementId(), ", id:", requirement.getId()));
			//}

		} catch (Exception e) {
			if(logger != null)
				logger.error("testInsertRequirement",e);
			fail("Fail testInsertRequirement");
		}
	}


	/**
	 * 요건등록 테스트
	 */
	@Test
	public void testUpdateRequirement() {

		try {

			Requirement requirement = (Requirement) Util.readObjectFromJson(new File("data/test/REST-C01-AN-01-00.json"), Requirement.class, "UTF-8");
			//logger.debug(Util.join("testInsertRequirement before:"," requirementId:", requirement.getRequirementId()));
			//String requirementId = requirementService.selectRequirementId();
			//requirement.setId(requirementId);
			//requirement.setRequirementId(requirementId);
			//if(requirement.getDevExpYmd() == null) logger.debug("getDevExpYmd is null");
			//if(Util.isEmpty(requirement.getDevExpYmd() ) )logger.debug("getDevExpYmd is empty");

			for(int i = 0 ; i < 1 ; i ++ ){
				int res = requirementService.updateRequirement(requirement);
				logger.debug(Util.join("testUpdateRequirement res:",res,", requirementId:", requirement.getRequirementId(), ", id:", requirement.getId()));
			}

		} catch (Exception e) {
			if(logger != null)
				logger.error("testUpdateRequirement",e);
			fail("Fail testUpdateRequirement");
		}
	}


	/**
	 * 요건삭제 테스트
	 */
	@Test
	public void testDeleteRequirement() {

		try {

			String requirementId = "RQ00002434";
			String modId = "iip";
			String modDate = Util.getFormatedDate();
			int res = requirementService.deleteRequirement(requirementId, modId, modDate);
			logger.debug("result:" + res);

		} catch (Exception e) {
			if(logger != null)
				logger.error("testDeleteRequirement",e);
			fail("Fail testDeleteRequirement");
		}
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
			RequirementApproval requirementApproval = new RequirementApproval();
			requirementApproval.setRequirementId(requirementId);
			requirementApproval.setReqUserId(modId);

			RequirementApprovalUser approvalUser = new RequirementApprovalUser();
			approvalUser.setRequirementId(requirementId);
			approvalUser.setRoleType("0");
			approvalUser.setAdmUserId("11825");
			List<RequirementApprovalUser> approvalUsers = new ArrayList<RequirementApprovalUser>();
			approvalUsers.add(approvalUser);
			requirementApproval.setRequirementApprovalUsers(approvalUsers);
			requirementApproval.setReqDate(modDate);
			requirementApproval.setReqType(status);

			int res = requirementService.updateRequirementStatus(status, modDate, modId, requirementId, requirementApproval);
			logger.debug("result:" + res);


		} catch (Exception e) {
			if(logger != null)
				logger.error("testUpdateRequirementStatus",e);
			fail("Fail testUpdateRequirementStatus");
		}
	}




	/**
	 * 요건상태변경  테스트
	 */
	@Test
	public void testUpdateRequirementDevelopmentStatus() {

		try {

			String requirementId = "RQ00000009";
			String modId = "11826";
			String status = "I0";
			int res = requirementService.updateRequirementDevelopmentStatus(status, "20150923190000", modId, requirementId, "20150923");
			logger.debug("result:" + res);


		} catch (Exception e) {
			if(logger != null)
				logger.error("testUpdateRequirementDevelopmentStatus",e);
			fail("Fail testUpdateRequirementDevelopmentStatus");
		}
	}


	/**
	 * 요건TODO리스트조회   테스트
	 */
	@Test
	public void testGetRequirementToDoList() {

		try {

			String userId = "11825";



			List<Map> list = requirementService.getRequirementApprovalTargetList(userId,"N");


			if(list != null){
				int i = 0;
				for(Map requirement : list){
					logger.debug(Util.join("Requirement[",i ++,"]:",Util.toJSONString(requirement)));
				}
			}

		} catch (Exception e) {
			if(logger != null)
				logger.error("testGetRequirementToDoList",e);
			fail("Fail testGetRequirementToDoList");
		}
	}

	/**
	 * 요건상태변경리스트조회   테스트
	 */
	@Test
	public void testGetRequirementChangeList() {

		try {

			String userId = "11830";



			List<Map> list = requirementService.getRequirementChangeList(userId,"N");


			if(list != null){
				int i = 0;
				for(Map item : list){
					logger.debug(Util.join("item[",i ++,"]:",Util.toJSONString(item)));
				}
			}

		} catch (Exception e) {
			if(logger != null)
				logger.error("testGetRequirementChangeList",e);
			fail("Fail testGetRequirementChangeList");
		}
	}

	/**
	 *
	 */
	@Test
	public void testGetRequirementDevelopmentList() {

		try {

			String userId = "11825";



			List<Map> list = requirementService.getRequirementDevelopmentList(userId,"Y");


			if(list != null){
				int i = 0;
				for(Map item : list){
					logger.debug(Util.join("item[",i ++,"]:",Util.toJSONString(item)));
				}
			}

		} catch (Exception e) {
			if(logger != null)
				logger.error("testGetRequirementDevelopmentList",e);
			fail("Fail testGetRequirementDevelopmentList");
		}
	}


	/**
	 */
	@Test
	public void testGetRequirementTestList() {

		try {

			String userId = "11825";



			List<Map> list = requirementService.getRequirementTestList(userId, "Y");


			if(list != null){
				int i = 0;
				for(Map item : list){
					logger.debug(Util.join("item[",i ++,"]:",Util.toJSONString(item)));
				}
			}

		} catch (Exception e) {
			if(logger != null)
				logger.error("testGetRequirementTestList",e);
			fail("Fail testGetRequirementTestList");
		}
	}


	/**
	 */
	@Test
	public void testGetRequirementRealList() {

		try {

			String userId = "11825";



			List<Map> list = requirementService.getRequirementRealList(userId,"Y");


			if(list != null){
				int i = 0;
				for(Map item : list){
					logger.debug(Util.join("item[",i ++,"]:",Util.toJSONString(item)));
				}
			}

		} catch (Exception e) {
			if(logger != null)
				logger.error("testGetRequirementRealList",e);
			fail("Fail testGetRequirementRealList");
		}
	}


	/**
	 */
	@Test
	public void testGetRequirementDTMList() {

		try {

			String userId = "11826";
			String isInterfaceAdmin = "N";


			List<Map> list = requirementService.getRequirementDTMList(userId, isInterfaceAdmin);


			if(list != null){
				int i = 0;
				for(Map item : list){
					logger.debug(Util.join("item[",i ++,"]:",Util.toJSONString(item)));
				}
			}

		} catch (Exception e) {
			if(logger != null)
				logger.error("getRequirementDTMList",e);
			fail("Fail getRequirementDTMList");
		}
	}




	/**
	 * 요건승인내역 조회
	 */
	@Test
	public void testGetRequirementApproval() {

		try {

			String requirementId = "RQ00000609";


			RequirementApproval requirementApproval = requirementService.getRequirementApproval(requirementId);


			if(requirementApproval != null){
				logger.debug(Util.join("requirementApproval:",Util.toJSONString(requirementApproval)));
			}

		} catch (Exception e) {
			if(logger != null)
				logger.error("testGetRequirementApproval",e);
			fail("Fail testGetRequirementApproval");
		}
	}



	/**
	 *  요건 히스토리조회
	 */
	/*@Test
	public void testGetRequirementHistoryList() {

		try {

			String requirementId = "RQ00000609";
			//int version = 0;
			//String modDate = "20150904173733";

			List<Map> historyList = requirementService.getRequirementHerstoryList(requirementId);


			for(Map history : historyList){
				logger.debug(Util.join("history:",Util.toJSONString(history)));
				String version = (String)history.get("version");
				RequirementColumnHerstory requirementColumnHistory = requirementService.getRequirementColumnHerstory(requirementId, version);
				logger.debug(Util.join("requirementColumnHistory:",Util.toJSONString(requirementColumnHistory)));



//				Map recentVersion = requirementService.selectInterfaceHerstoryRecentVersion("P002D0530", modDate);
//				String interfaceId = (String)recentVersion.get("interfaceId");
//				modDate = (String)recentVersion.get("modDate");
//				version = (String)recentVersion.get("version");
//				InterfaceColumnHerstory interfaceColumnHerstory = requirementService.getInterfaceColumnHerstory(interfaceId, modDate, version);
//				if( interfaceColumnHerstory !=null ){
//					logger.debug(Util.join("interfaceColumnHerstory:",Util.toJSONString(interfaceColumnHerstory)));
//				}
			}


		} catch (Exception e) {
			if(logger != null)
				logger.error("testGetRequirementHistoryList",e);
			fail("Fail testGetRequirementHistoryList");
		}
	}*/

	/*@Test
	public void testGetRequirementHistory() {

		try {

			String requirementId = "RQ00000535";
			RequirementColumnHerstory requirementColumnHistory = requirementService.getRequirementColumnHerstory(requirementId, "0");
			logger.debug(Util.join("requirementColumnHistory:",Util.toJSONString(requirementColumnHistory)));

		} catch (Exception e) {
			if(logger != null)
				logger.error("testGetRequirementHistoryList",e);
			fail("Fail testGetRequirementHistoryList");
		}
	}*/




	/**
	 * 요건등록 테스트
	 */
	@Test
	public void testInsertRequirementTemp() {

		try {

			Requirement requirement = (Requirement) Util.readObjectFromJson(new File("data/test/REST-C01-AN-01-00.json"), Requirement.class, "UTF-8");

			String requirementString = Util.toJSONString(requirement);

			RequirementTemp requirementTemp = new RequirementTemp();
			requirementTemp.setRequirementId(requirement.getRequirementId());
			requirementTemp.setContents(requirementString);
			requirementTemp.setRegDate(Util.getFormatedDate());
			requirementTemp.setRegId("iip");

			requirementService.insertRequirementTemp(requirementTemp);

		} catch (Exception e) {
			if(logger != null)
				logger.error("testInsertRequirementTemp",e);
			fail("Fail testInsertRequirementTemp");
		}
	}



	/**
	 * 요건수정 테스트
	 */
	@Test
	public void testUpdateRequirementTemp() {

		try {


			RequirementTemp requirementTemp = new RequirementTemp();
			requirementTemp.setRequirementId("RQ00000001");
			requirementTemp.setModDate(Util.getFormatedDate());
			requirementTemp.setModId("iip");
			requirementTemp.setApplyYn("Y");
			requirementTemp.setDelYn("N");

			requirementService.updateRequirementTemp(requirementTemp);

		} catch (Exception e) {
			if(logger != null)
				logger.error("testUpdateRequirementTemp",e);
			fail("Fail testUpdateRequirementTemp");
		}
	}

	@Test
	public void testGetRequirementTemp() {
		try {
			RequirementTemp temp = requirementService.getRequirementTemp("RQ00000001");
			Requirement requirement = Util.jsonToObject(temp.getContents(), Requirement.class);
			logger.debug(Util.join("requirement name:", requirement.getRequirementNm()));

			Interface interfaze = requirement.getInterfaceInfo();
			logger.debug(Util.join("interface name:", interfaze.getInterfaceNm()));
			logger.debug(Util.join("interface id:", interfaze.getIntegrationId()));

			List<System> systemList = interfaze.getSystemList();
			for (System system : systemList){
				logger.debug(Util.join("systemId:",system.getSystemId(),",system nm:", system.getSystemNm()));
			}

		} catch (Exception e) {
			if(logger != null) logger.error("testGetRequirementTemp",e);
			fail("Fail testGetRequirementTemp");
		}

	}


}
