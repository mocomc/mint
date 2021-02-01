/*
 * Copyright 2013 ~ 2014 Mocomsys(dhkim, Solution TF), Inc.  All Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 * Please contact Mocomsys, Inc., NURITKUM SQUARE R&D TOWER, 11F DMC 1605,
 * Sangam-Dong, Mapo-Gu, Seoul, 121-795 Korea or visit mocomsys.com
 * if you need additional information or have any questions.
 */
package pep.per.mint.database.service.co;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pep.per.mint.common.data.basic.App;
import pep.per.mint.common.data.basic.Application;
import pep.per.mint.common.data.basic.Authority;
import pep.per.mint.common.data.basic.BasicInfo;
import pep.per.mint.common.data.basic.Business;
import pep.per.mint.common.data.basic.Channel;
import pep.per.mint.common.data.basic.CommonCode;
import pep.per.mint.common.data.basic.DataAccessRole;
import pep.per.mint.common.data.basic.Interface;
import pep.per.mint.common.data.basic.InterfaceAdditionalAttribute;
import pep.per.mint.common.data.basic.InterfacePattern;
import pep.per.mint.common.data.basic.InterfaceProperties;
import pep.per.mint.common.data.basic.InterfaceTag;
import pep.per.mint.common.data.basic.ItemModel;
import pep.per.mint.common.data.basic.Menu;
import pep.per.mint.common.data.basic.Organization;
import pep.per.mint.common.data.basic.Requirement;
import pep.per.mint.common.data.basic.Server;
import pep.per.mint.common.data.basic.Site;
import pep.per.mint.common.data.basic.System;
import pep.per.mint.common.data.basic.TreeModel;
import pep.per.mint.common.data.basic.User;
import pep.per.mint.common.data.basic.UserRole;
import pep.per.mint.common.data.basic.monitor.ProblemClass;
import pep.per.mint.common.util.Util;
import pep.per.mint.database.mapper.co.CommonMapper;

/**
 * 시스템,업무,공통코드 조회 등 포털시스템 공통조회용 서비스
 * @author Solution TF
 *
 */
@Service
public class CommonService {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	CommonMapper commonMapper;


	public Business getBusinessByCd(String cd) throws Exception{
		return commonMapper.getBusinessByCd(cd);
	}

	public pep.per.mint.common.data.basic.System getSystemByCd(String cd) throws Exception {
		return commonMapper.getSystemByCd(cd);
	}

	public Channel getChannelByCd(String cd) throws Exception {
		return commonMapper.getChannelByCd(cd);
	}

	public DataAccessRole getDataAccessRoleByCd(String cd) throws Exception {
		return commonMapper.getDataAccessRoleByCd(cd);
	}

	public InterfaceProperties getInterfacePropertyByCd(String cd, String type, String channelId) throws Exception {
		Map<String, String> params = new HashMap<String, String>();
		params.put("cd", cd);
		params.put("type", type);
		params.put("channelId", channelId);
		return commonMapper.getInterfacePropertyByCd(params);
	}

	/**
	 * for test discriminator
	 * @return
	 * @throws Exception
	 */
	public List<Map> getSendReceiveSystemList() throws Exception{
		return commonMapper.getSendReceiveSystemList();
	}

	/**
	 * <pre>
	 * 시스템 리스트, 시스템 상세, 서버리스트, 서버 상세를 한번에 조회하여 시스템 복합유형을
	 * 구성하여 리스트로 돌려준다.
	 * REST-R01-CO-01-00-001 시스템 조회
	 * </pre>
	 * @return
	 * @throws Exception
	 */
	public List<System> getSystemList(Map<String, Object> params)throws Exception{
		List<System> list = commonMapper.getSystemList(params);
		return list;
	}

	/**
	 * REST-R01-CO-01-00-002 서버 조회
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<Server> getServerList(Map params) throws Exception {
		List<Server> list = commonMapper.getServerList(params);
		return list;
	}

	/**
	 * REST-R01-CO-01-00-003 업무 조회
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<Business> getBusinessList(Map params) throws Exception{
		List<Business> list = commonMapper.getBusinessList(params);
		return list;
	}

	/**
	 * REST-R01-CO-01-00-004 사용자리스트조회
	 * @return
	 * @throws Exception
	 */
	public List<User> getUserList(Map params) throws Exception{
		List<User> list = commonMapper.getUserList(params);
		return list;
	}


	/**
	 * REST-R02-CO-01-00-004 사용자조회
	 * @return
	 * @throws Exception
	 */
	public User getUser(String userId) throws Exception{
		return getUser(userId, false);
	}

	/**
	 * REST-R02-CO-01-00-004 사용자조회
	 * @return
	 * @throws Exception
	 */
	public User getUser(String userId, boolean isUsePassword) throws Exception{
		String usePassword = isUsePassword ? "Y" : "N";
		User user = commonMapper.getUser(userId, usePassword);
		List<String> adminChannelList = commonMapper.getAdminChannelList(userId);
		if(adminChannelList == null || adminChannelList.size() == 0){

		}else{
			UserRole role = user.getRole();
			role.setIsChannelAdmin("Y");
			role.setChannelIdList(adminChannelList);
		}
		return user;
	}

	/**
	 * REST-R01-CO-01-00-006 인터페이스 패턴 조회
	 * @return
	 * @throws Exception
	 */
	public List<InterfacePattern> getInterfacePatternList(Map params) throws Exception{
		List<InterfacePattern> list = commonMapper.getInterfacePatternList(params);
		return list;
	}

	/**
	 * REST-R01-CO-01-00-008 인터페이스 리스트 조회
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<Interface> getInterfaceList(Map params) throws Exception{
		List<Interface> list = commonMapper.getInterfaceList(params);
		return list;
	}

	/**
	 * REST-R02-CO-01-00-008 인터페이스상세조회
	 * @param interfaceId
	 * @return
	 * @throws Exception
	 */
	public Interface getInterfaceDetail(String interfaceId)throws Exception{
		Interface intf = commonMapper.getInterfaceDetail(interfaceId);
		return intf;
	}

	/**
	 * REST-R03-CO-01-00-008 이인터페이스상세-요건리스트
	 * @param interfaceId
	 * @return
	 * @throws Exception
	 */
	public List<Requirement> getInterfaceDetailRequiremnets(String interfaceId) throws Exception{
		List<Requirement> list = commonMapper.getInterfaceDetailRequiremnets(interfaceId);
		return list;
	}

	/**
	 * REST-R04-CO-01-00-008 인터페이스 존재유무 체크(삼성전기용)
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public String existInterface(Map params) throws Exception{
		String interfaceId = commonMapper.existInterface(params);
		return interfaceId;
	}


	/**
	 * <pre>
	 * 로그인시 필요한 모든 기초데이터를 조회하여 제공한다.
	 * REST-R01-CO-02-00-000 기초데이터조회
	 * </pre>
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public BasicInfo getBasicInfo(String userId) throws Exception{

		BasicInfo basicInfo = new BasicInfo();
		//--------------------------------
		//업무코드 리스트 세팅
		//-------------------------------
		//change :
		//date : 20150808
		//내용 :
		//삼성전기 업무 데이터가 1만건이 넘어서
		//BasicInfo에서 제외한다.
		//--------------------------------
		//change ;20150809 00:30
		// 내일 적용하기로 해서 주석 풀음. 씨바 또 날샜냐 ?
		//--------------------------------
		//change ;20150809 20:30
		//이제 진짜 뺀다.
		{
			//List<Business> businessCdList = getBusinessCdList();
			//basicInfo.setBusinessCdList(businessCdList);
		}
		//--------------------------------
		//공토코드 세팅
		//--------------------------------
		{
			Map<String,List<CommonCode>> cds = new HashMap<String, List<CommonCode>>();
			List<CommonCode> cdList = getCommonCodeList(null, null);
			for (CommonCode commonCode : cdList) {
				String key = Util.join(commonCode.getLevel1(),commonCode.getLevel2());
				List<CommonCode> container = cds.get(key);
				if(container == null) {
					container = new ArrayList<CommonCode>();
					cds.put(key, container);
				}
				container.add(commonCode);
			}
			basicInfo.setCds(cds);
		}
		//--------------------------------
		//채널리스트 세팅
		//--------------------------------
		{
			List<Channel> channelList = getChannelList();
			basicInfo.setChannelList(channelList);
		}

		//--------------------------------
		//메뉴 세팅
		//--------------------------------
		{
			//List<Menu> menuList = getMenuList(userId);
			//basicInfo.setMenuList(menuList);
		}
		boolean isExist = true;
		//--------------------------------
		//포털 환경변수값 세팅 (사이트별관리 코드 추가)
		//--------------------------------
		Map<String,List<String>> environmentalValues = getEnvironmentalValues();
		basicInfo.setEnvironmentalValues(environmentalValues);
		List attrList = environmentalValues.get("system.basicInfo.isExist");
		if(attrList != null && attrList.size()>0){
			String strExist = (String) attrList.get(0);
			try{
				isExist = Boolean.parseBoolean(strExist);
			}catch(Exception e){
			}
		}else{
			isExist = true;
		}

		if(isExist){
			//--------------------------------
			//인터페이스 코드 리스트 세팅
			//--------------------------------
			{
				List<Interface> interfaceCdList = getInterfaceCdList();
				basicInfo.setInterfaceCdList(interfaceCdList);
			}
			//--------------------------------
			//인터페이스 Tag 세팅
			//--------------------------------
			{
				//List<InterfaceTag> interfaceTagList = getInterfaceTagList();
				//basicInfo.setInterfaceTagList(interfaceTagList);
			}
			//--------------------------------
			//요건코드 리스트 세팅
			//--------------------------------
			{
				//List<Requirement> requirementCdList = getRequirementCdList();
				//basicInfo.setRequirementCdList(requirementCdList);
			}
			//--------------------------------
			//롤리스트 세팅
			//--------------------------------
			{

			}
			//--------------------------------
			//서비스 리스트 세팅
			//--------------------------------
			{
				List<String> serviceList = getServiceList();
				basicInfo.setServiceList(serviceList);
			}

			//--------------------------------
			//서비스명 리스트 세팅
			//--------------------------------
			{
				List<String> serviceDescList = getServiceDescList();
				basicInfo.setServiceDescList(serviceDescList);
			}

			//--------------------------------
			//시스템 코드 리스트 세팅
			//--------------------------------
			{
				List<System> systemCdList = getSystemCdList();
				basicInfo.setSystemCdList(systemCdList);
			}

			//--------------------------------
			//프로바이더 시스템 코드 리스트 세팅
			//--------------------------------
			{
				//List<System> providerCdList = getProviderCdList();
				//basicInfo.setProviderCdList(providerCdList);
			}

		}


		//--------------------------------
		//사용자정보 세팅
		//--------------------------------
		{
			if(Util.isEmpty(userId)) throw new Exception("RequiredParamException:요예외클래스나중에에만들어라");
			User user = getUser(userId);
			basicInfo.setUser(user);
		}



		//--------------------------------
		//사이트별 특성( 인터페이스 추가 속성 )
		//--------------------------------
		{
			basicInfo.setInterfaceAdditionalAttributeList( getInterfaceAdditionalAttributeList() );
		}

		//--------------------------------
		//사용자 리스트 세팅
		//--------------------------------
		//변경
		//--------------------------------
		//내역 : 주석처리
		//일자 : 20160107
		//사유 : 로그인이 느려져서 BasicInfo에서 제외함.
		/*{
			Map params = null;
			List<User> userList = getUserList(params);
			basicInfo.setUserList(userList);
		}*/





		return basicInfo;
	}


	BasicInfo cachedBasicInfo = new BasicInfo();


	public void cacheBasicInfo() throws Exception{

		{
			List<Business> businessCdList = getBusinessCdList();
			cachedBasicInfo.setBusinessCdList(businessCdList);
		}
		//--------------------------------
		//공토코드 세팅
		//--------------------------------
		{
			Map<String,List<CommonCode>> cds = new HashMap<String, List<CommonCode>>();
			List<CommonCode> cdList = getCommonCodeList(null, null);
			for (CommonCode commonCode : cdList) {
				String key = Util.join(commonCode.getLevel1(),commonCode.getLevel2());
				List<CommonCode> container = cds.get(key);
				if(container == null) {
					container = new ArrayList<CommonCode>();
					cds.put(key, container);
				}
				container.add(commonCode);
			}
			cachedBasicInfo.setCds(cds);
		}
		//--------------------------------
		//채널리스트 세팅
		//--------------------------------
		{
			List<Channel> channelList = getChannelList();
			cachedBasicInfo.setChannelList(channelList);
		}


		boolean isExist = true;
		//--------------------------------
		//포털 환경변수값 세팅 (사이트별관리 코드 추가)
		//--------------------------------
		Map<String,List<String>> environmentalValues = getEnvironmentalValues();
		cachedBasicInfo.setEnvironmentalValues(environmentalValues);
		List attrList = environmentalValues.get("system.basicInfo.isExist");
		if(attrList != null && attrList.size()>0){
			String strExist = (String) attrList.get(0);
			try{
				isExist = Boolean.parseBoolean(strExist);
			}catch(Exception e){
			}
		}else{
			isExist = true;
		}

		if(isExist){
			//--------------------------------
			//인터페이스 코드 리스트 세팅
			//--------------------------------
			{
				List<Interface> interfaceCdList = getInterfaceCdList();
				cachedBasicInfo.setInterfaceCdList(interfaceCdList);
			}
			//--------------------------------
			//인터페이스 Tag 세팅
			//--------------------------------
			{
				List<InterfaceTag> interfaceTagList = getInterfaceTagList();
				cachedBasicInfo.setInterfaceTagList(interfaceTagList);
			}
			//--------------------------------
			//요건코드 리스트 세팅
			//--------------------------------
			{
				List<Requirement> requirementCdList = getRequirementCdList();
				cachedBasicInfo.setRequirementCdList(requirementCdList);
			}
			//--------------------------------
			//롤리스트 세팅
			//--------------------------------
			{

			}
			//--------------------------------
			//서비스 리스트 세팅
			//--------------------------------
			{
				List<String> serviceList = getServiceList();
				cachedBasicInfo.setServiceList(serviceList);
			}

			//--------------------------------
			//서비스명 리스트 세팅
			//--------------------------------
			{
				List<String> serviceDescList = getServiceDescList();
				cachedBasicInfo.setServiceDescList(serviceDescList);
			}

			//--------------------------------
			//시스템 코드 리스트 세팅
			//--------------------------------
			{
				List<System> systemCdList = getSystemCdList();
				cachedBasicInfo.setSystemCdList(systemCdList);
			}

			//--------------------------------
			//프로바이더 시스템 코드 리스트 세팅
			//--------------------------------
			{
				//List<System> providerCdList = getProviderCdList();
				//cachedBasicInfo.setProviderCdList(providerCdList);
			}

		}
		//--------------------------------
		//사이트별 특성( 인터페이스 추가 속성 )
		//--------------------------------
		{
			cachedBasicInfo.setInterfaceAdditionalAttributeList( getInterfaceAdditionalAttributeList() );
		}
		//--------------------------------
		//사용자 리스트 세팅
		//--------------------------------
		//변경
		//--------------------------------
		//내역 : 주석처리
		//일자 : 20160107
		//사유 : 로그인이 느려져서 BasicInfo에서 제외함.
		/*{
			Map params = null;
			List<User> userList = getUserList(params);
			cachedBasicInfo.setUserList(userList);
		}*/

	}

	public BasicInfo getBasicInfoV2(String userId) throws Exception{
		//--------------------------------
		//메뉴 세팅
		//--------------------------------
		{
			List<Menu> menuList = getMenuList(userId);
			cachedBasicInfo.setMenuList(menuList);
		}
		//--------------------------------
		//사용자정보 세팅
		//--------------------------------
		{
			if(Util.isEmpty(userId)) throw new Exception("RequiredParamException:요예외클래스나중에에만들어라");
			User user = getUser(userId);
			cachedBasicInfo.setUser(user);
		}
		return cachedBasicInfo;
	}


	/**
	 * <pre>
	 * 연관검색 조건을 조회하여 제공한다.
	 * REST-R02-CO-02-00-000 연관검색조건조회
	 *
	 * </pre>
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public BasicInfo getSearchInfo(Map params) throws Exception{

		BasicInfo basicInfo = new BasicInfo();

		//--------------------------------
		//채널리스트 세팅
		//--------------------------------
		{
			List<Channel> channelList = getChannelListByRelation(params);
			basicInfo.setChannelList(channelList);
		}

		//--------------------------------
		//시스템 코드 리스트 세팅   ??  TODO ? deprecated
		//--------------------------------
		{
			//List<System> systemCdList = getSystemCdListByRelation(params);
			//			List<System> systemCdList = commonMapper.getRootSystemCdList();
			//			basicInfo.setSystemCdList(systemCdList);
		}

		//--------------------------------
		//루트비지니스 ??  TODO ? deprecated
		//--------------------------------
		{
			//List<Business> businessCdList = getBusinessRootCdListByRelation(params);
			//			List<Business> businessCdList = commonMapper.getBusinessRootCdList();
			//			basicInfo.setBusinessCdList(businessCdList);
		}

		//--------------------------------
		//인터페이스 코드 리스트 세팅
		//--------------------------------
		{
			List<Interface> interfaceCdList = getInterfaceCdListByRelation(params);
			basicInfo.setInterfaceCdList(interfaceCdList);
		}

		//--------------------------------
		//요건코드 리스트 세팅  ??  TODO ? deprecated
		//--------------------------------
		{
			//			List<Requirement> requirementCdList = getRequirementCdListByRelation(params);
			//			basicInfo.setRequirementCdList(requirementCdList);
		}

		//--------------------------------
		//서비스 리스트 세팅    TODO 삭제예정
		//--------------------------------
		{
			List<String> serviceList = getServiceListByRelation(params);
			basicInfo.setServiceList(serviceList);
		}

		//--------------------------------
		//서비스명 리스트 세팅 TODO 삭제예정.
		//--------------------------------
		{
			List<String> serviceDescList = getServiceDescListByRelation(params);
			basicInfo.setServiceDescList(serviceDescList);
		}

		//--------------------------------
		// 기관 리스트 세팅 TODO 삭제예정.  ??  TODO ? deprecated
		//--------------------------------
		{
			//			List<Organization>  organizationList = getOrganizationListByRelation(params);
			//			basicInfo.setOrganizationList(organizationList);
		}

		return basicInfo;
	}

	public List<InterfaceAdditionalAttribute> getInterfaceAdditionalAttributeList() throws Exception {
		return commonMapper.getInterfaceAdditionalAttributeList();
	}

	/**
	 * <pre>
	 * API REST-R01-CO-02-00-001 시스템코드검색 서비스
	 * </pre>
	 * @return
	 * @throws Exception
	 */
	public List<System> getSystemCdList() throws Exception{
		List<System> list = commonMapper.getSystemCdList();
		return list;
	}

	/**
	 * <pre>
	 * API REST-R02-CO-02-00-001 시스템코드검색 서비스
	 * </pre>
	 * @return
	 * @throws Exception
	 */
	public List<System> getProviderCdList() throws Exception{
		List<System> list = commonMapper.getProviderCdList();
		return list;
	}


	/**
	 * <pre>
	 * API REST-R03-CO-02-00-001 시스템코드검색 서비스
	 * </pre>
	 * @return
	 * @throws Exception
	 */
	public List<System> getSystemCdListByRelation(Map params) throws Exception{
		List<System> list = commonMapper.getSystemCdListByRelation(params);
		return list;
	}


	/**
	 * <pre>
	 * 등록된 모든 Root 시스템 코드 정보를 얻어온다.
	 * API ID : REST-R04-CO-02-00-001 시스템코드검색
	 * </pre>
	 * @return
	 * @throws Exception
	 */
	public List<System> getRootSystemCdList() throws Exception{
		List<System> list = commonMapper.getRootSystemCdList();
		return list;
	}

	/**
	 * <pre>
	 * 등록된 모든 Child 시스템 코드 정보를 얻어온다.
	 * API ID : REST-R05-CO-02-00-001 시스템코드검색
	 * </pre>
	 * @param params (parentSystemId)
	 * @return
	 * @throws Exception
	 */
	public List<System> getChildSystemCdList(Map params) throws Exception{
		List<System> list = commonMapper.getChildSystemCdList(params);
		return list;
	}



	/**
	 * <pre>
	 * API REST-R01-CO-02-00-002 서버코드검색 서비스
	 * </pre>
	 * @return
	 * @throws Exception
	 */
	public List<Server> getServerCdList() throws Exception{
		List<Server> list = commonMapper.getServerCdList();
		return list;
	}


	/**
	 * <pre>
	 * API REST-R01-CO-02-00-003 업무코드검색 서비스
	 * </pre>
	 * @return
	 * @throws Exception
	 */
	public List<Business> getBusinessCdList() throws Exception{
		List<Business> list = commonMapper.getBusinessCdList();
		return list;
	}

	/**
	 * <pre>
	 * API REST-R02-CO-02-00-003 최상위 업무코드검색 서비스
	 * </pre>
	 * @return
	 * @throws Exception
	 */
	public List<Business> getRootBusinessCdList() throws Exception{
		List<Business> list = commonMapper.getBusinessRootCdList();
		return list;
	}


	/**
	 * <pre>
	 * API REST-R04-CO-02-00-003 최상위 업무코드검색 서비스
	 * </pre>
	 * @return
	 * @throws Exception
	 */
	public List<Business> getBusinessRootCdListByRelation(Map params) throws Exception{
		List<Business> list = commonMapper.getBusinessRootCdListByRelation(params);
		return list;
	}



	/**
	 * <pre>
	 * API REST-R03-CO-02-00-003 자식 업무코드검색 서비스
	 * </pre>
	 * @param parentBusinessId
	 * @return
	 * @throws Exception
	 */
	public List<Business> getChildBusinessCdList(String parentBusinessId) throws Exception{
		List<Business> list = commonMapper.getChildBusinessCdList(parentBusinessId);
		return list;
	}

	/**
	 * <pre>
	 * REST-R01-CO-02-00-004 인터페이스 코드검색
	 * </pre>
	 * @return
	 * @throws Exception
	 */
	public List<Interface> getInterfaceCdList() throws Exception{
		List<Interface> list = commonMapper.getInterfaceCdList();
		return list;
	}

	public List<Map> getInterfaceCdListV2() throws Exception{
		List<Map> list = commonMapper.getInterfaceCdListV2();
		return list;
	}

	/**
	 * <pre>
	 * REST-R02-CO-02-00-004 인터페이스 코드검색
	 * </pre>
	 * @return
	 * @throws Exception
	 */
	public List<Interface> getInterfaceCdListByRelation(Map params) throws Exception{
		List<Interface> list = commonMapper.getInterfaceCdListByRelation(params);
		return list;
	}

	/**
	 *
	 * @param level1
	 * @param level2
	 * @return
	 * @throws Exception
	 */
	public List<CommonCode> getCommonCodeList(String level1, String level2) throws Exception{
		List<CommonCode> list = commonMapper.getCommonCodeList(level1, level2);
		return list;
	}

	public CommonCode getCommonCode(String level1, String level2, String cd) throws Exception{
		CommonCode list = commonMapper.getCommonCode(level1, level2, cd);
		return list;
	}

	/**
	 * REST-R01-CO-02-00-006 인터페이스 채널검색
	 * @return
	 * @throws Exception
	 */
	public List<Channel> getChannelList() throws Exception{
		List<Channel> list = commonMapper.getChannelList();
		return list;
	}


	/**
	 * REST-R02-CO-02-00-006 인터페이스 채널검색
	 * @return
	 * @throws Exception
	 */
	public List<Channel> getChannelListByRelation(Map params) throws Exception{
		List<Channel> list = commonMapper.getChannelListByRelation(params);
		return list;
	}


	/**
	 * REST-R01-CO-02-00-007 서비스 검색
	 * @return
	 * @throws Exception
	 */
	public List<String> getServiceList() throws Exception{
		List<String> list = commonMapper.getServiceList();
		return list;
	}

	public List<String> getServiceListByRelation(Map params) throws Exception{
		List<String> list = commonMapper.getServiceListByRelation(params);
		return list;
	}

	public List<String> getServiceDescList() throws Exception{
		List<String> list = commonMapper.getServiceDescList();
		return list;
	}

	public List<String> getServiceDescListByRelation(Map params) throws Exception{
		List<String> list = commonMapper.getServiceDescListByRelation(params);
		return list;
	}

	/**
	 * REST-R01-CO-02-00-008 인터페이스TAG 검색
	 * @return
	 * @throws Exception
	 */
	public List<InterfaceTag> getInterfaceTagList() throws Exception{
		List<InterfaceTag> list = commonMapper.getInterfaceTagList();
		return list;
	}

	/**
	 * REST-R01-CO-02-00-009 요건검색, 요건(ID,NM) 리스트 검색
	 * @return
	 * @throws Exception
	 */
	public List<Requirement> getRequirementCdList() throws Exception{
		List<Requirement> list = commonMapper.getRequirementCdList();
		return list;
	}

	/**
	 * REST-R01-CO-02-00-010 App 리스트 검색
	 * @return
	 * @throws Exception
	 */
	public List<App> getAppList() throws Exception{
		List<App> list = commonMapper.getAppList();
		return list;
	}


	public List<Requirement> getRequirementCdListByRelation(Map params) throws Exception{
		List<Requirement> list = commonMapper.getRequirementCdListByRelation(params);
		return list;
	}

	/**
	 * REST-R01-CO-02-00-013 데이터접근권한검색
	 * @return
	 * @throws Exception
	 */
	public List<DataAccessRole> getDataAccessRoleList() throws Exception{
		List<DataAccessRole> list = commonMapper.getDataAccessRoleList();
		return list;
	}



	//------------------------------------------------------------
	// 설계서 CO-03 기타 코드(메뉴/프로그램/권한/..) 영역
	//------------------------------------------------------------
	/**
	 * <pre>
	 * 입력받은 사용자ID의 권한에 허가된 메뉴리스트를 조회한다.
	 * REST-R01-CO-03-00-001 메뉴검색
	 * </pre>
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public List<Menu> getMenuList(String userId) throws Exception{
		List<Menu> list = commonMapper.getMenuList(userId);

		Map<String, Menu> menuMap = new LinkedHashMap<String, Menu>();


		for (Menu menu : list) {
			menuMap.put(menu.getMenuId(), menu);

			logger.debug(Util.join("user:",userId,"' menu:", menu.getMenuId(),":",menu.getMenuNm()));
		}



		List<Menu> roots = new ArrayList<Menu>();
		for (Menu menu : list) {
			if("Y".equals(menu.getRootYn())){
				String menuId = menu.getMenuId();
				List<Map<String, String>> pathList = getMenuPathList(menuId);
				for(Map<String, String> path : pathList){
					String pid = path.get("parent");
					String cid = path.get("child");

					Menu pMenu = menuMap.get(pid);

					if(pMenu == null ) {
						logger.debug(Util.join("유저[",userId,"]에게 부모메뉴[메뉴ID",pid, "]에 권한이 없어 하위 메뉴를 구성할수 없어 메뉴구성 건너뜀."));
						continue;
					}

					Menu cMenu = menuMap.get(cid);
					if(cMenu == null ) {
						logger.debug(Util.join("유저[",userId,"]에게 부모메뉴[메뉴ID",pid, "]에 추가할 하위메뉴[메뉴ID",cid, "]에 대한 권한이 없어 메뉴구성 건너뜀."));
						continue;
					}
					pMenu.addChild(cMenu);

				}
				roots.add(menu);
			}
		}


		return roots;
	}

	List<Map<String,String>> getMenuPathList(String menuId) throws Exception{
		return commonMapper.getMenuPathList(menuId);
	}


	/**
	 * [임시버전-20200520]
	 * - NH농협 보안취약점 대응수준으로 작성된 부분으로, 향후 AccessControl 표준 정리후 보완 필요
	 * - TODO : AccessControl 내용 보완
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> getRestrictAccessAppList(String userId) throws Exception{
		List<Map> items = commonMapper.getRestrictAccessAppList(userId);

		Map<String, String> restrictAccsssMap = new LinkedHashMap<String, String>();
		//TODO : 하드코딩 부분 보완
		String prefix="/mint/view";
		for(Map item : items) {
			restrictAccsssMap.put(prefix + item.get("appURI"), prefix + item.get("appURI"));
		}
		return restrictAccsssMap;
	}

	public Map<String, List<String>> getEnvironmentalValues() throws Exception {
		List<Map<String,String>> list = commonMapper.getEnvironmentalValues();

		Map<String, List<String>> environmentalValues = new LinkedHashMap<String, List<String>>();
		for(Map<String, String> environmentValue : list){
			String key = environmentValue.get("key");
			String val = environmentValue.get("value");

			List<String> values = environmentalValues.get(key);
			if(values == null){
				values = new ArrayList<String>();
				environmentalValues.put(key,values);
			}
			values.add(val);
		}
		return environmentalValues;
	}

	public Map<String, List<String>> getEnvironmentalValues(String packageId, String attributeId) throws Exception {
		List<Map<String,String>> list = commonMapper.getEnvironmentalValuesById(packageId, attributeId);

		Map<String, List<String>> environmentalValues = new LinkedHashMap<String, List<String>>();
		for(Map<String, String> environmentValue : list){
			String key = environmentValue.get("key");
			String val = environmentValue.get("value");

			List<String> values = environmentalValues.get(key);
			if(values == null){
				values = new ArrayList<String>();
				environmentalValues.put(key,values);
			}
			values.add(val);
		}
		return environmentalValues;
	}


	public List<String> getEnvironmentalValueList(String packageId,String attributeId)throws Exception{
		return commonMapper.getEnvironmentalValueList(packageId, attributeId);
	}

	/**
	 * 채널별 ASIS 인터페이스 리스트를 조회한다.
	 * @param channelId
	 * @return
	 * @throws Exception
	 */
	public List<Map> getAsisInterfaceList(String channelId)throws Exception{
		return commonMapper.getAsisInterfaceList(channelId);
	}

	/**
	 * 장애 유형
	 * @return
	 * @throws Exception
	 */
	public List<ProblemClass> getProblemCdList(Map param)throws Exception{
		return commonMapper.getProblemCdList(param);
	}

	/**
	 *
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public int insertLoginUser(Map params) throws Exception{
		return commonMapper.insertLoginUser(params);
	}

	/**
	 *
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public int updateLogoutUser(Map params) throws Exception{
		return commonMapper.updateLogoutUser(params);
	}

	/**
	 *
	 * @return
	 * @throws Exception
	 */
	public int getTodayLoginUserCnt() throws Exception{
		return commonMapper.getTodayLoginUserCnt();
	}

	public int getTotalLoginUserCnt() throws Exception{
		return commonMapper.getTotalLoginUserCnt();
	}

	/**
	 * 권한 획득 메소드
	 * 롤에 따른 권한을 얻어온다.
	 * 권한관련 Entity 설계가 미완성이므로
	 * 삼성전기이후 설계완료하여 개발 진행한다
	 * 삼성전기임시적용할 로직은 롤이 뷰어일경우 로그인을 하지못하도록 제한한다.
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public Authority getAuthority(String userId) throws Exception{
		User user = getUser(userId);
		UserRole userRole = user.getRole();
		return getAuthority(userRole);
	}

	/**
	 * 권한 획득 메소드
	 * 롤에 따른 권한을 얻어온다.
	 * 권한관련 Entity 설계가 미완성이므로
	 * 삼성전기이후 설계완료하여 개발 진행한다
	 * 삼성전기임시적용할 로직은 롤이 뷰어일경우 로그인을 하지못하도록 제한한다.
	 * @param userRole
	 * @return
	 * @throws Exception
	 */
	public Authority getAuthority(UserRole userRole) throws Exception{
		Authority authority = new Authority();
		//------------------------------------------------------------
		// 2017.03.20 dhkim 주석처리.
		// 향후 Authority 에 대해 검토후 로직을 재 구현하도록 한다.
		//------------------------------------------------------------
		/*
		if(userRole.getRoleId().equals("UR00000005")){
			authority.setLoginAuthority(false);
		}
		 */
		return authority;
	}



	/**
	 * REST-R01-CO-02-00-012 기관 조회
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<Organization> getOrganizationList(Map params) throws Exception{
		List<Organization> list = commonMapper.getOrganizationList(params);
		return list;
	}

	/**
	 *
	 * @return
	 * @throws Exception
	 */
	public TreeModel<pep.per.mint.common.data.basic.System> getSystemToOrganization(Map params) throws Exception{
		TreeModel<pep.per.mint.common.data.basic.System> treeModel = new TreeModel<pep.per.mint.common.data.basic.System>();
		treeModel.setText("Organization Tree");
		List<Map> list = commonMapper.getSystemToOrganization(params);
		Map<String, ItemModel<pep.per.mint.common.data.basic.System>> rootItemModelMap = new HashMap<String, ItemModel<pep.per.mint.common.data.basic.System>>();
		Map<String, ItemModel<pep.per.mint.common.data.basic.System>> itemModelMap = new HashMap<String, ItemModel<pep.per.mint.common.data.basic.System>>();



		for(Map map : list) {


			//logger.debug(Util.join("tree map item 처리 :", Util.toJSONString(map)));
			{
				String parentId = (String) map.get("ORG_ID");
				String parentCd = (String) map.get("ORG_CD");
				String parentNm = (String) map.get("ORG_NM");
				String parentGrpYn = "Y";
				String parentRootYn = "Y";
				String childId = (String) map.get("SYSTEM_ID");
				String childCd = (String) map.get("SYSTEM_CD");
				String childNm = (String) map.get("SYSTEM_NM");
				String childGrpYn = "N";
				int depth = 1;

				ItemModel<pep.per.mint.common.data.basic.System> parentItemModel = itemModelMap.get(parentId);

				if(!itemModelMap.containsKey(parentId)){

					//---------------------------
					// parent System 생성
					//---------------------------
					/*pep.per.mint.common.data.basic.System parent = new pep.per.mint.common.data.basic.System();
					parent.setSystemId(parentId);
					parent.setSystemCd(parentCd);
					parent.setSystemNm(parentNm);
					parent.setRootYn(parentRootYn);
					parent.setGrpYn(parentGrpYn);*/

					//---------------------------
					// parentItemModel 생성
					//---------------------------
					parentItemModel = new ItemModel<pep.per.mint.common.data.basic.System>();
					parentItemModel.setId(parentId);
					parentItemModel.setText(parentNm+" ["+parentCd+"]");
					//parentItemModel.setItem(parent);
					parentItemModel.setIsGroup(Util.isEmpty(childGrpYn) || "N".equals(childGrpYn) ? false : true);

					parentItemModel.setObjCode(parentCd);
					parentItemModel.setObjName(parentNm);

					//---------------------------
					// temp map에 parentItemModel 등록
					//---------------------------

					itemModelMap.put(parentId, parentItemModel);
					//---------------------------
					// root 처리
					//---------------------------
					if("Y".equals(parentRootYn)){
						if(!rootItemModelMap.containsKey(parentId)){
							rootItemModelMap.put(parentId,parentItemModel);
							treeModel.addRoot(parentItemModel);
							parentItemModel.setIsRoot(true);
						}
						parentItemModel.setSpriteCssClass("rootfolder");
					}else{
						if("Y".equals(parentItemModel.isGroup())){
							parentItemModel.setSpriteCssClass("folder");
						}else{
							parentItemModel.setSpriteCssClass("html");
						}
					}


				}



				ItemModel<pep.per.mint.common.data.basic.System> childItemModel = itemModelMap.get(childId);

				if(childItemModel == null) {

					//---------------------------
					// child System 생성
					//---------------------------
					/*pep.per.mint.common.data.basic.System child = new pep.per.mint.common.data.basic.System();
					child.setSystemId(childId);
					child.setSystemCd(childCd);
					child.setSystemNm(childNm);
					child.setRootYn("N");
					child.setGrpYn(childGrpYn);*/

					//---------------------------
					// childItemModel 생성
					//---------------------------
					childItemModel = new ItemModel<pep.per.mint.common.data.basic.System>();
					childItemModel.setId(childId);
					childItemModel.setParent(parentId);
					childItemModel.setText(childNm+" ["+childCd+"]");
					//childItemModel.setItem(child);
					childItemModel.setIsRoot(false);
					childItemModel.setIsGroup(Util.isEmpty(childGrpYn) || "N".equals(childGrpYn) ? false : true);

					childItemModel.setObjCode(childCd);
					childItemModel.setObjName(childNm);

					if(childItemModel.isGroup()){
						childItemModel.setSpriteCssClass("folder");
					}else{
						childItemModel.setSpriteCssClass("html");
					}

					// SystemAdd

					//---------------------------
					// temp map에 parentItemModel 등록
					//---------------------------
					itemModelMap.put(childId, childItemModel);
				}

				if(depth == 1){
					//---------------------------
					// 부모-자식처리
					//---------------------------
					parentItemModel.addItem(childItemModel);
					parentItemModel.setHasChild(true);

				}

			}
		}
		return treeModel;
	}


	/**
	 *
	 * @return
	 * @throws Exception
	 */
	public TreeModel<pep.per.mint.common.data.basic.System> getSystemOfOrganization(Map params) throws Exception{
		TreeModel<pep.per.mint.common.data.basic.System> treeModel = new TreeModel<pep.per.mint.common.data.basic.System>();
		treeModel.setText("System Tree");
		List<Map> list = commonMapper.getSystemOfOrganization(params);
		Map<String, ItemModel<pep.per.mint.common.data.basic.System>> rootItemModelMap = new HashMap<String, ItemModel<pep.per.mint.common.data.basic.System>>();
		Map<String, ItemModel<pep.per.mint.common.data.basic.System>> itemModelMap = new HashMap<String, ItemModel<pep.per.mint.common.data.basic.System>>();

		for(Map map : list) {


			//logger.debug(Util.join("tree map item 처리 :", Util.toJSONString(map)));
			{
				String parentId = (String) map.get("parentId");
				String parentCd = (String) map.get("parentCd");
				String parentNm = (String) map.get("parentNm");
				String parentGrpYn = (String) map.get("parentGrpYn");
				String parentRootYn = (String) map.get("parentRootYn");
				String childId = (String) map.get("childId");
				String childCd = (String) map.get("childCd");
				String childNm = (String) map.get("childNm");
				String childGrpYn = (String) map.get("childGrpYn");
				int depth = (Integer) map.get("depth");

				ItemModel<pep.per.mint.common.data.basic.System> parentItemModel = itemModelMap.get(parentId);

				if(!itemModelMap.containsKey(parentId)){

					//---------------------------
					// parent System 생성
					//---------------------------
					/*pep.per.mint.common.data.basic.System parent = new pep.per.mint.common.data.basic.System();
					parent.setSystemId(parentId);
					parent.setSystemCd(parentCd);
					parent.setSystemNm(parentNm);
					parent.setRootYn(parentRootYn);
					parent.setGrpYn(parentGrpYn);*/

					//---------------------------
					// parentItemModel 생성
					//---------------------------
					parentItemModel = new ItemModel<pep.per.mint.common.data.basic.System>();
					parentItemModel.setId(parentId);
					parentItemModel.setText(parentNm+" ["+parentCd+"]");
					//parentItemModel.setItem(parent);
					parentItemModel.setIsGroup(Util.isEmpty(childGrpYn) || "N".equals(childGrpYn) ? false : true);

					parentItemModel.setObjCode(parentCd);
					parentItemModel.setObjName(parentNm);

					//---------------------------
					// temp map에 parentItemModel 등록
					//---------------------------

					itemModelMap.put(parentId, parentItemModel);
					//---------------------------
					// root 처리
					//---------------------------
					if("Y".equals(parentRootYn)){
						if(!rootItemModelMap.containsKey(parentId)){
							rootItemModelMap.put(parentId,parentItemModel);
							treeModel.addRoot(parentItemModel);
							parentItemModel.setIsRoot(true);
						}
						parentItemModel.setSpriteCssClass("rootfolder");
					}else{
						if("Y".equals(parentItemModel.isGroup())){
							parentItemModel.setSpriteCssClass("folder");
						}else{
							parentItemModel.setSpriteCssClass("html");
						}
					}


				}



				ItemModel<pep.per.mint.common.data.basic.System> childItemModel = itemModelMap.get(childId);

				if(childItemModel == null) {

					//---------------------------
					// child System 생성
					//---------------------------
					/*pep.per.mint.common.data.basic.System child = new pep.per.mint.common.data.basic.System();
					child.setSystemId(childId);
					child.setSystemCd(childCd);
					child.setSystemNm(childNm);
					child.setRootYn("N");
					child.setGrpYn(childGrpYn);*/

					//---------------------------
					// childItemModel 생성
					//---------------------------
					childItemModel = new ItemModel<pep.per.mint.common.data.basic.System>();
					childItemModel.setId(childId);
					childItemModel.setParent(parentId);
					childItemModel.setText(childNm+" ["+childCd+"]");
					//childItemModel.setItem(child);
					childItemModel.setIsRoot(false);
					childItemModel.setIsGroup(Util.isEmpty(childGrpYn) || "N".equals(childGrpYn) ? false : true);

					childItemModel.setObjCode(childCd);
					childItemModel.setObjName(childNm);

					if(childItemModel.isGroup()){
						childItemModel.setSpriteCssClass("folder");
					}else{
						childItemModel.setSpriteCssClass("html");
					}

					//---------------------------
					// temp map에 parentItemModel 등록
					//---------------------------
					itemModelMap.put(childId, childItemModel);
				}

				if(depth == 1){
					//---------------------------
					// 부모-자식처리
					//---------------------------
					parentItemModel.addItem(childItemModel);
					parentItemModel.setHasChild(true);

				}

			}
		}
		return treeModel;
	}

	public Map<String, String> getOrganizationToSystem(Map params) throws Exception{
		return commonMapper.getOrganizationToSystem(params);
	}

	public List<Organization> getOrganizationOfSystemList(Map params) throws Exception{
		List<Organization> list = commonMapper.getOrganizationOfSystemList(params);
		return list;
	}


	public List<Organization> getOrganizationListByRelation(Map params) throws Exception{
		List<Organization> list = commonMapper.getOrganizationListByRelation(params);
		return list;
	}

	public System getParentSystem(String systemId) throws Exception {
		return commonMapper.getParentSystem(systemId);
	}

	public String getInterfaceID(String idKey) throws Exception {
		return commonMapper.getInterfaceID(idKey);
	}

	public String getInterfaceIDMax(Map params) throws Exception {
		return commonMapper.getInterfaceIDMax(params);
	}

	public String getInterfaceIDMaxHDINS(Map params) throws Exception {
		return commonMapper.getInterfaceIDMaxHDINS(params);
	}

	public String getInterfaceIDMaxSHL(Map params) throws Exception {
		return commonMapper.getInterfaceIDMaxSHL(params);
	}

	public void updateInterfaceIDMax(String idKey) throws Exception  {
		commonMapper.updateInterfaceIDMax(idKey);
	}

	public List<Menu> getApplicationMenuPath(Map params) throws Exception {
		return commonMapper.getApplicationMenuPath(params);
	}

	public List<System> getSystemPathList(Map params) throws Exception {
		return commonMapper.getSystemPathList(params);
	}

	public String generateIntegrationId(String prefix, int startIndex, int endIndex, int seqLength) throws Exception {
		return commonMapper.generateIntegrationId(prefix, startIndex, endIndex, seqLength);
	}

    public Site getSite() throws Exception {
        return commonMapper.getSite();
    }

    public int createCommon(CommonCode common) throws Exception {
    	return commonMapper.insertCommon(common);
	}

    public int updateCommon(CommonCode common) throws Exception {
    	return commonMapper.updateCommon(common);
    }

    public int deleteCommon(CommonCode common) throws Exception {
    	return commonMapper.deleteCommon(common);
    }

    public List<pep.per.mint.common.data.basic.Service> getAppServiceList() throws Exception {
    	List<pep.per.mint.common.data.basic.Service> list = commonMapper.getAppServiceList();
    	if(list != null) {
	    	for(pep.per.mint.common.data.basic.Service service : list) {
	    		String paramsString = service.getParamsString();
	    		if(!Util.isEmpty(paramsString)) {
	    			Map params = Util.jsonToMap(paramsString);
	    			service.setParams(params);
	    		}
	    	}
    	}
    	return list;
    }

    /**
     * 포털환경변수 String 값 가져오기
     * @param pkg
     * @param attributeId
     * @param defaultValue
     * @return
     */
	public String getEnvironmentalValue(String pkg, String attributeId, String defaultValue) {
		String value = null;

		try {
			List<String> values = commonMapper.getEnvironmentalValue(pkg, attributeId);
			if(values != null && values.size() > 0) {
				value = values.get(0);
			}
			value = Util.isEmpty(value) ? defaultValue : value ;
		}catch(Exception e) {
			logger.error("no problem",e);
		}
		return value;
	}

	/**
	 * 포털환경변수 boolean 값 가져오기
	 * @param pkg
	 * @param attributeId
	 * @param defaultValue
	 * @return
	 */
	public boolean getEnvironmentalBooleanValue(String pkg, String attributeId, boolean defaultValue) {
		String value = null;
		boolean res = defaultValue;
		try {
			List<String> values = commonMapper.getEnvironmentalValue(pkg, attributeId);
			if(values != null && values.size() > 0) {
				value = values.get(0);
			}
			res = Util.isEmpty(value) ? defaultValue : Boolean.parseBoolean(value) ;
		}catch(Exception e) {
			logger.error("no problem",e);
		}
		return res;
	}

	/**
	 * 포털환경변수 long 값 가져오기
	 * @param pkg
	 * @param attributeId
	 * @param defaultValue
	 * @return
	 */
	public long getEnvironmentalLongValue(String pkg, String attributeId, long defaultValue) {
		String value = null;
		long res = defaultValue;
		try {
			List<String> values = commonMapper.getEnvironmentalValue(pkg, attributeId);
			if(values != null && values.size() > 0) {
				value = values.get(0);
			}
			res = Util.isEmpty(value) ? defaultValue : Long.parseLong(value) ;
		}catch(Exception e) {
			logger.error("no problem",e);
		}
		return res;
	}

	/**
	 * 포털환경변수 int 값 가져오기
	 * @param pkg
	 * @param attributeId
	 * @param defaultValue
	 * @return
	 */
	public int getEnvironmentalIntValue(String pkg, String attributeId, int defaultValue) {
		String value = null;
		int res = defaultValue;
		try {
			List<String> values = commonMapper.getEnvironmentalValue(pkg, attributeId);
			if(values != null && values.size() > 0) {
				value = values.get(0);
			}
			res = Util.isEmpty(value) ? defaultValue : Integer.parseInt(value) ;
		}catch(Exception e) {
			logger.error("no problem",e);
		}
		return res;
	}

	/**
	 * @param serverId
	 * @return
	 * @throws Exception
	 */
	public List<String> getServerAccessRole(String serverId) throws Exception {
		return commonMapper.getServerAccessRole(serverId);
	}


	/**
	 * <pre>
	 * </pre>
	 * @return
	 * @throws Exception
	 */
	public List<System> getSystemOfServer(String serverId) throws Exception{
		List<System> list = commonMapper.getSystemOfServer(serverId);
		return list;
	}
}
