/*
 * Copyright 2013 ~ 2014 Mocomsys(dhkim, Solution TF), Inc.  All Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 * Please contact Mocomsys, Inc., NURITKUM SQUARE R&D TOWER, 11F DMC 1605,
 * Sangam-Dong, Mapo-Gu, Seoul, 121-795 Korea or visit mocomsys.com
 * if you need additional information or have any questions.
*/
package pep.per.mint.database.mapper.co;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import pep.per.mint.common.data.basic.*;
import pep.per.mint.common.data.basic.System;
import pep.per.mint.common.data.basic.monitor.ProblemClass;



/**
 * @author Solution TF
 *
 */
public interface CommonMapper {

	/**
	 * [임시버전-20200520]
	 * - NH농협 보안취약점 대응수준으로 작성된 부분으로, 향후 AccessControl 표준 정리후 보완 필요
	 * - TODO : AccessControl 내용 보완
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public List<Map> getRestrictAccessAppList(@Param("userId") String userId) throws Exception;


	/**
	 * 사이트별 특성( 인터페이스 추가 속성 )
	 * @return
	 * @throws Exception
	 */
	public List<InterfaceAdditionalAttribute> getInterfaceAdditionalAttributeList() throws Exception;


	/**
	 * for test discriminator
	 * @return
	 * @throws Exception
	 */
	public List<Map> getSendReceiveSystemList() throws Exception;



	//------------------------------------------------------------
	// 설계서 CO-01 팝업 영역
	//------------------------------------------------------------
	/**
	 * <pre>
	 * 시스템 리스트 조회
	 * REST-R01-CO-01-00-001
	 * </pre>
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<System>	getSystemList(Map params) throws Exception;

	/**
	 * <pre>
	 * 서버 리스트 조회
	 * REST-R01-CO-01-00-002
	 * </pre>
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<Server> getServerList(Map params) throws Exception;

	/**
	 * <pre>
	 * 업무 리스트 조회
	 * REST-R01-CO-01-00-003
	 * </pre>
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<Business> getBusinessList(Map params) throws Exception;

	/**
	 * <pre>
	 * 사용자 리스트 조회
	 * REST-R01-CO-01-00-004
	 * </pre>
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<User> getUserList(Map params) throws Exception;

	/**
	 * <pre>
	 * 사용자 조회
	 * REST-R02-CO-01-00-004
	 * </pre>
	 * @return
	 * @throws Exception
	 */
	public User getUser(@Param("userId") String userId, @Param("usePassword") String usePassword) throws Exception;

	/**
	 * 삼성전기내에서반 사용될 예정
	 * 권한정의가 되지 않아 솔루션담당자인지 솔루션담당자라면 어떤 솔루션을 담당하는지 조회하여
	 * 사용자조회시 사용자정보의 롤에 정보를 담아도 전달한다.
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public List<String> getAdminChannelList(@Param("userId") String userId) throws Exception;

	/**
	 * <pre>
	 * 인터페이스 패턴조회
	 * REST-R01-CO-01-00-006
	 * </pre>
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<InterfacePattern> getInterfacePatternList(Map params) throws Exception;

	/**
	 * <pre>
	 * 인터페이스 리스트 조회
	 * REST-R01-CO-01-00-008
	 * </pre>
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<Interface> getInterfaceList(Map params) throws Exception;

	/**
	 * <pre>
	 * 인터페이스 상세 조회
	 * REST-R02-CO-01-00-008
	 * </pre>
	 * @param interfaceId
	 * @return
	 * @throws Exception
	 */
	public Interface getInterfaceDetail(@Param("interfaceId") String interfaceId)throws Exception;

	/**
	 * <pre>
	 * 인터페이스 상세-요건리스트 조회
	 * REST-R03-CO-01-00-008
	 * </pre>
	 * @param interfaceId
	 * @return
	 * @throws Exception
	 */
	public List<Requirement> getInterfaceDetailRequiremnets(@Param("interfaceId") String interfaceId) throws Exception;

	/**
	 * <pre>
	 * 인터페이스 존재유무 체크(삼성전기용)
	 * REST-R04-CO-01-00-008
	 * </pre>
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public String existInterface(Map params) throws Exception;



	//------------------------------------------------------------
	// 설계서 CO-02 코드 영역
	//------------------------------------------------------------

	/**
	 * <pre>
	 * 등록된 모든 시스템 코드 정보를 얻어온다.
	 * API ID : REST-R01-CO-02-00-001 시스템코드검색
	 * </pre>
	 * @return
	 * @throws Exception
	 */
	public List<System> getSystemCdList() throws Exception;



	/**
	 * <pre>
	 * 등록된 프로바이더 시스템 코드 정보를 얻어온다.
	 * API ID : REST-R02-CO-02-00-001 시스템코드검색
	 * </pre>
	 * @return
	 * @throws Exception
	 */
	public List<System> getProviderCdList() throws Exception;


	/**
	 * <pre>
	 * 조건과 연관된 시스템 코드 정보를 얻어온다.
	 * API ID : REST-R03-CO-02-00-001 시스템코드검색
	 * </pre>
	 */
	public List<System> getSystemCdListByRelation(Map params) throws Exception;


	/**
	 * <pre>
	 * 등록된 모든 Root 시스템 코드 정보를 얻어온다.
	 * API ID : REST-R04-CO-02-00-001 시스템코드검색
	 * </pre>
	 * @return
	 * @throws Exception
	 */
	public List<System> getRootSystemCdList() throws Exception;

	/**
	 * <pre>
	 * 등록된 모든 Child 시스템 코드 정보를 얻어온다.
	 * API ID : REST-R05-CO-02-00-001 시스템코드검색
	 * </pre>
	 * @param params (parentSystemId)
	 * @return
	 * @throws Exception
	 */
	public List<System> getChildSystemCdList(Map params) throws Exception;



	/**
	 * <pre>
	 * 등록된 모든 서버 코드 정보를 얻어온다.
	 * API ID : REST-R01-CO-02-00-002 서버코드검색
	 * </pre>
	 * @return
	 * @throws Exception
	 */
	public List<Server> getServerCdList() throws Exception;


	/**
	 * <pre>
	 * 등록된 모든 업무 코드 정보를 얻어온다.
	 * API ID : REST-R01-CO-02-00-004 업무코드검색
	 * </pre>
	 * @return
	 * @throws Exception
	 */
	public List<Business> getBusinessCdList() throws Exception;


	/**
	 * <pre>
	 * 등록된 모든 최상위 업무 코드 정보를 얻어온다.
	 * API ID : REST-R02-CO-02-00-004 업무코드검색
	 * </pre>
	 * @return
	 */
	public List<Business> getBusinessRootCdList()throws Exception;


	/**
	 * <pre>
	 * 검색조건에 연관된 최상위 업무 코드 정보를 얻어온다.
	 * API ID : REST-R04-CO-02-00-004 업무코드검색
	 * </pre>
	 * @return
	 */
	public List<Business> getBusinessRootCdListByRelation(Map params)throws Exception;

	/**
	 *
	 * <pre>
	 * 입력으로 받은 부노업무ID를 기준으로 모든 하위의 자식 업무 코드 정보를 얻어온다.
	 * API ID : REST-R03-CO-02-00-004 업무코드검색
	 * </pre>
	 * @param parentBusinessId
	 * @return
	 */
	public List<Business> getChildBusinessCdList(@Param("parentBusinessId") String parentBusinessId)throws Exception;


	/**
	 * <pre>
	 * 등록된 모든 인터페이스 정보를 얻어온다.
	 * API ID : REST-R01-CO-02-00-004 인터페이스 코드검색
	 * </pre>
	 * @return
	 * @throws Exception
	 */
	public List<Interface> getInterfaceCdList() throws Exception;

	public List<Map> getInterfaceCdListV2() throws Exception;

	/**
	 * <pre>
	 * 검색조건과 연관된 인터페이스 정보를 얻어온다.
	 * API ID : REST-R02-CO-02-00-004 인터페이스 코드검색
	 * </pre>
	 * @return
	 * @throws Exception
	 */
	public List<Interface> getInterfaceCdListByRelation(Map params) throws Exception;




	/**
	 * <pre>
	 * 레벨1, 레벨2 로 분류한 공통코드 테이블의 코드 리스트를 얻어온다.
	 * REST-R01-CO-02-00-005 공통코드 조회
	 * </pre>
	 * @return
	 * @throws Exception
	 */
	public List<CommonCode> getCommonCodeList(@Param("level1") String level1, @Param("level2")  String level2) throws Exception;

	public CommonCode getCommonCode(@Param("level1") String level1, @Param("level2")  String level2, @Param("cd")String cd) throws Exception;

	/**
	 * <pre>
	 * 연계 Channel 리스트를 얻어온다.
	 * REST-R01-CO-02-00-006 인터페이스 채널검색
	 * </pre>
	 * @return
	 * @throws Exception
	 */
	public List<Channel> getChannelList() throws Exception;


	/**
	 * <pre>
	 * 연계 Channel 리스트를 얻어온다.
	 * REST-R02-CO-02-00-006 인터페이스 채널검색
	 * </pre>
	 * @return
	 * @throws Exception
	 */
	public List<Channel> getChannelListByRelation(Map params) throws Exception;


	/**
	 * <pre>
	 * 서비스 리스트를 얻어온다.
	 * REST-R01-CO-02-00-007 서비스 검색
	 * </pre>
	 * @return
	 * @throws Exception
	 */
	public List<String> getServiceList() throws Exception;

	public List<String> getServiceListByRelation(Map params) throws Exception;

	public List<String> getServiceDescList() throws Exception;

	public List<String> getServiceDescListByRelation(Map params) throws Exception;

	/**
	 * <pre>
	 * 인터페이스 TAG 리스트를 얻어온다.
	 * REST-R01-CO-02-00-008 인터페이스TAG 검색
	 * </pre>
	 * @return
	 * @throws Exception
	 */
	public List<InterfaceTag> getInterfaceTagList() throws Exception;

	/**
	 * <pre>
	 * 요건코드 리스트를 얻어온다.
	 * REST-R01-CO-02-00-009 요건검색
	 * </pre>
	 * @return
	 * @throws Exception
	 */
	public List<Requirement> getRequirementCdList() throws Exception;



	/**
	 * <pre>
	 * App 리스트를 얻어온다.
	 * REST-R01-CO-02-00-010
	 * </pre>
	 * @return
	 * @throws Exception
	 */
	public List<App> getAppList() throws  Exception;


	public List<Requirement> getRequirementCdListByRelation(Map params) throws Exception;


	/**
	 * <pre>
	 * 데이터 접근권한 리스트를 얻어온다.
	 * REST-R01-CO-02-00-013 인터페이스 데이터접근권한 검색
	 * </pre>
	 * @return
	 * @throws Exception
	 */
	public List<DataAccessRole> getDataAccessRoleList() throws Exception;

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
	public List<Menu> getMenuList(@Param("userId") String userId) throws Exception;


	public List<Map<String,String>> getMenuPathList(@Param("menuId") String menuId) throws Exception;


	/**
	 * 포털환경설정값 조회
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,String>> getEnvironmentalValues() throws Exception;


	/**
	 * 채널별 ASIS 인터페이스 리스트를 얻어온다.
	 * @param channelId
	 * @return
	 * @throws Exception
	 */
	public List<Map> getAsisInterfaceList(@Param("channelId")String channelId) throws Exception;


	/**
	 * 장애 유형을 가져온다.
	 * @return
	 * @throws Exception
	 */
	public List<ProblemClass> getProblemCdList(Map param) throws Exception;


	/**
	 *
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public int insertLoginUser(Map params) throws Exception;

	/**
	 *
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public int updateLogoutUser(Map params) throws Exception;

	/**
	 *
	 * @return
	 * @throws Exception
	 */
	public int getTodayLoginUserCnt() throws Exception;

	/**
	 *
	 * @return
	 * @throws Exception
	 */
	public int getTotalLoginUserCnt() throws Exception;


	/**
	 *
	 * @return
	 * @throws Exception
	 */
	public Map getLoginUserInfo() throws Exception;


	///public List<RelUser> selectInterfaceUsers(@Param("interfaceId")String  interfaceId) throws Exception;

	//------------------------------------------------------------
	// 설계서 CO-01 팝업 영역
	//------------------------------------------------------------
	/**
	 * <pre>
	 * 기관 리스트 조회
	 * REST-R01-CO-02-00-012
	 * </pre>
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<Organization>	getOrganizationList(Map params) throws Exception;

	public List<Map>	getSystemToOrganization(Map params) throws Exception;

	public List<Organization>	getOrganizationOfSystemList(Map params) throws Exception;

	public List<Organization>	getOrganizationListByRelation(Map params) throws Exception;

	public List<Map>	getSystemOfOrganization(Map params) throws Exception;

	public Map getOrganizationToSystem(Map params) throws Exception;


	public System getParentSystem(String systemId) throws Exception;



	public String getInterfaceID(String idKey)throws Exception;



	public String getInterfaceIDMax(Map params)throws Exception;

	public String getInterfaceIDMaxHDINS(Map params)throws Exception;

	public String getInterfaceIDMaxSHL(Map params)throws Exception;

	public String generateIntegrationId(@Param("prefix")String prefix, @Param("startIndex")int startIndex, @Param("endIndex")int endIndex, @Param("seqLength")int seqLength)throws Exception;

	public void updateInterfaceIDMax(String idKey)throws Exception;

	public List<Menu> getApplicationMenuPath(Map params) throws Exception;

	public List<System> getSystemPathList(Map params) throws Exception;


	public List<Map<String, String>> getEnvironmentalValuesById(@Param("package")String packageId, @Param("attributeId")String attributeId)throws Exception;

	public List<String> getEnvironmentalValueList(@Param("package")String packageId, @Param("attributeId")String attributeId)throws Exception;

	public Business getBusinessByCd(String cd)throws Exception;


	public System getSystemByCd(String cd)throws Exception;


	public Channel getChannelByCd(String cd)throws Exception;


	public DataAccessRole getDataAccessRoleByCd(String cd)throws Exception;


	public InterfaceProperties getInterfacePropertyByCd(Map params)throws Exception;

    public Site getSite();

    public int insertCommon(CommonCode common) throws Exception;

    public int updateCommon(CommonCode common) throws Exception;

    public int deleteCommon(CommonCode common) throws Exception;

	public List<Service> getAppServiceList() throws Exception;

	public List<String> getEnvironmentalValue(@Param("package")String packageId, @Param("attributeId")String attributeId)throws Exception;

	public List<String> getServerAccessRole(@Param("serverId")String serverId)throws Exception;

	public List<System> getSystemOfServer(@Param("serverId")String serverId)throws Exception;

}
