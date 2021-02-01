package com.mocomsys.iip.inhouse.excel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import pep.per.mint.common.data.basic.Business;
import pep.per.mint.common.data.basic.Channel;
import pep.per.mint.common.data.basic.CommonCode;
import pep.per.mint.common.data.basic.DataAccessRole;
import pep.per.mint.common.data.basic.Interface;
import pep.per.mint.common.data.basic.InterfaceAdditionalAttribute;
import pep.per.mint.common.data.basic.InterfaceMapping;
import pep.per.mint.common.data.basic.InterfaceProperties;
import pep.per.mint.common.data.basic.Organization;
import pep.per.mint.common.data.basic.RelUser;
import pep.per.mint.common.data.basic.Requirement;
import pep.per.mint.common.data.basic.User;
import pep.per.mint.common.util.LogUtil;
import pep.per.mint.common.util.Util;
import pep.per.mint.database.mapper.ut.ExcelUploadMapper;
import pep.per.mint.database.service.an.RequirementService;
import pep.per.mint.database.service.co.CommonService;
import pep.per.mint.database.service.inhouse.IExcelUploadService;
import pep.per.mint.database.service.su.DataAccessRoleService;
import pep.per.mint.database.service.ut.ExcelUploadService;


/**
 * @author TA
 *
 */


public class DefaultExcelUploadService implements IExcelUploadService {

	private static final Logger logger = LoggerFactory.getLogger(ExcelUploadService.class);

	private final String COL_DELIMITER = ",";
	private final String ROW_DELIMITER = "/";

	/**
	 * The Common service.
	 */
	@Autowired
	CommonService commonService;

	/**
	 * Requirement Service
	 */
	@Autowired
	RequirementService requirementService;

	@Autowired
	DataAccessRoleService dataAccessRoleService;

	@Autowired
	ExcelUploadMapper excelUploadMapper;



	@Override
	public List<Requirement> validate(List<String[]> list, List<String[]> headerlist, Map<String,Object> params) throws Exception {

		//----------------------------------------------------------------------------
		// * 1. * 은 필수 입력 값임
		// * 3. 연계 담당자 지정시 들어갈 수 있도록
		// *    - 연계 담당자를 지정하지 않더라도 지정된 솔루션 담당자는 자동으로 입력되어야 함
		// * 4. 담당자 포멧은 콤마(',') 기호로 구분하고 다중 등록시 '/' 기호로 구분하여 등록
		// *    - 포멧 : 시스템,사번
		// *    - 예) SCM,123456/TIM,333333/,444444
		// *    - 사번 앞에 시스템 코드가 없을 경우 없는 상태로 넣으면 됨
		// * 5. 시스템코드 3자리 체크 요망
		//----------------------------------------------------------------------------


		List<Requirement> _requirementList = new LinkedList<Requirement>();
		String seq = "";
		Map<String,String> searchMap = new HashMap<String, String>();

		//-----------------------------------------------------------------
		// 검증을 위한 공통 코드 정보들 로드
		//-----------------------------------------------------------------
		// TIM0101 테이블의 SYSTEM_ID(시스템ID), SYSTEM_NM(시스템명), SYSTEM_CD(시스템코드)를 시스템VO에 담아 LIST로 담아둠.
		List<pep.per.mint.common.data.basic.System> checkSystemCdList = commonService.getSystemCdList();
		// TSU0301 테이블(공통코드테이블)
		// Data처리흐름(단방향, 양방향)에 대한 정보를 담는다.
		List<CommonCode> checkDataPrDir 		= commonService.getCommonCodeList("IM", "01");
		// DATA처리방식(배치, 온라인, 요청, 온라인조회)에 대한 정보를 담는다.
		List<CommonCode> checkDataPrMethod 		= commonService.getCommonCodeList("IM", "12");
		// APP처리방식(동기, 비동기) 에 대한 정보를 담는다.
		List<CommonCode> checkAppPrMethod 		= commonService.getCommonCodeList("IM", "02");
		// 발생주기(초,분,스케줄,시,일,월,년,수시)에 대한 정보를 담는다.
		List<CommonCode> checkDataFrequency 	= commonService.getCommonCodeList("IM", "03");
		// 리소스(DB,FILE,RFC,Proxy,WS,API,HTTP) 에 대한 정보를 담는다.
		List<CommonCode> checkResourceCd 		= commonService.getCommonCodeList("IM", "04");
		// 중요도(상,중,하) 에 대한 정보를 담는다.
		List<CommonCode> checkImportance 		= commonService.getCommonCodeList("IM", "07");
		// 지연임계시간단위(초,분,시) 에 대한 정보를 담는다. 형통소스 사용여부는 추후판단후 삭제 혹은 보존함.
		List<CommonCode> checkMaxDelay 			= commonService.getCommonCodeList("IM", "05");
		// 장애임계시간단위(초,분,시) 에 대한 정보를 담는다. 형통소스 사용여부는 추후판단후 삭제 혹은 보존함.
		List<CommonCode> checkMaxError 			= commonService.getCommonCodeList("IM", "06");
		// 인터페이스담당자유형(송신, 연계, 수신)에 대한 정보를 각각 담는다.
		CommonCode checkRoleType0 				= commonService.getCommonCode("IM", "09", "0");
		CommonCode checkRoleType1 				= commonService.getCommonCode("IM", "09", "1");
		CommonCode checkRoleType2 				= commonService.getCommonCode("IM", "09", "2");
		// 연계채널(EAI,IIB,DB Link,Data Stage, SAP PO, Talend)과 해당 연계채널의 담당자 정보를 Channel VO에 담아 List.
		List<Channel> checkChannelList 			= commonService.getChannelList();
		// 채널변경사유코드(개발불가능, 데이터처리용량초과, 직접입력) 을 담는다.
		List<CommonCode> checkChannelChangeCd 	= commonService.getCommonCodeList("AN", "06");
		// 데이터접근권한(KICS,NPA,SPO,MOJ) 에 대한 정보를 각각 담는다.
		List<DataAccessRole> ckeckDataAccessRoleList = commonService.getDataAccessRoleList();
		// 인터페이스 특성 데이타 조회
		List<InterfaceAdditionalAttribute> ckeckInterfaceAdditionalAttribute = commonService.getInterfaceAdditionalAttributeList();
		// 기관정보를 담을 코드 추가


		//-----------------------------------------------------------------
		// 인터페이스ID가 같을 경우 동일 인터페이스로 판단 시스템정보만 추가하는 로직을 구현한다.(추후구현)
		//-----------------------------------------------------------------



		//-----------------------------------------------------------------
		// 엑셀 데이터간 Provider 기준 중복 데이터가 존재하는지 체크하기 위한 변수 설정
		//-----------------------------------------------------------------
		List<String> dupProviderInfoCheckList = new LinkedList<String>();

		params.put("checkSystemCdList", checkSystemCdList);

		//-----------------------------------------------------------------
		// Row Data
		//-----------------------------------------------------------------
		for( int i = 0; i < list.size(); i++ ) {
			String[] cols = list.get(i);
			boolean isSeqGroup = false;
			//String[] businessCdList = new String[3];	// 업무 대,중,소 분류의 엑셀컬럼데이터 존재유무를 구분하기 위한 변수
			Business[] businessList = new Business[3];	// 업무 대,중,소 분류의 검색된 값을 저장하기 위한 변수
			String[] organizationCdList = new String[3];	// 기관 대,중,소 분류의 엑셀컬럼데이터 존재유무를 구분하기 위한 변수
			String[] systemCdList = new String[3];	// 시스템 대,중,소 분류의 엑셀컬럼데이터 존재유무를 구분하기 위한 변수
			//-----------------------------------------------------------------
			// 순번이 같으면 동일 인터페이스로 보고 시스템 정보만 추가하는 로직을 구현한다
			//-----------------------------------------------------------------
			logger.debug("그룹여부 [" + seq +"]["+cols[0]+"]");
			if( seq.equals(cols[0]) ) {
				isSeqGroup = true;
			} else {
				seq = cols[0];
			}

			Requirement _requirement = null;
			Business _business = null;
			//Organization _organization = null;
			Interface _interfacez = null;
			Channel _channel = null;
			List<pep.per.mint.common.data.basic.System> _systemList = null;
			List<Business> _businessList = null;
			List<RelUser> _relUsers = null;
			InterfaceMapping _interfaceMapping = null;
			List<InterfaceProperties> _interfacePropertyList = null;
			List<DataAccessRole> _dataAccessRoleList = null;
			//List<InterfaceProperties> _interfaceProperty = null;


			pep.per.mint.common.data.basic.System _providerSystem = new pep.per.mint.common.data.basic.System();
			pep.per.mint.common.data.basic.System _consumerSystem = new pep.per.mint.common.data.basic.System();
			
			


			if( isSeqGroup ) {
				//-----------------------------------------------------------------
				// 순번이 동일하면(동일 인터페이스) 이전 정보를 불러온다
				//-----------------------------------------------------------------
				_requirement 		= _requirementList.get(_requirementList.size()-1);
				_business 			= _requirement.getBusiness();
				_interfacez 		= _requirement.getInterfaceInfo();
				_channel 			= _interfacez.getChannel();
				_systemList 		= _interfacez.getSystemList();
				_businessList 		= _interfacez.getBusinessList();
				_relUsers 			= _interfacez.getRelUsers();
				_interfaceMapping 	= _interfacez.getInterfaceMapping();
				_interfacePropertyList = _interfacez.getProperties();
				_dataAccessRoleList = _interfacez.getDataAccessRoleList();

			} else {
				//-----------------------------------------------------------------
				// 순번이 상이하면 요건 정보를 새로 생성한다
				//-----------------------------------------------------------------
				_requirement 		= new Requirement();
				_business 			= new Business();
				_interfacez 		= new Interface();
				_channel 			= new Channel();
				_systemList 		= new LinkedList<pep.per.mint.common.data.basic.System>();
				_businessList 		= new LinkedList<Business>();
				_relUsers 			= new LinkedList<RelUser>();
				_interfaceMapping 	= new InterfaceMapping();
				_interfacePropertyList = new LinkedList<InterfaceProperties>();
				_dataAccessRoleList = new LinkedList<DataAccessRole>();
				//_organization		= new Organization();

				//-----------------------------------------------------------------
				// requirementId 에 순번을 임시로 매칭시켜 저장한다.
				//-----------------------------------------------------------------
				_requirement.setRequirementId(seq);
				_requirement.setStatus("A1");
				_requirement.setDelYn("N");
				_requirement.setRegId(params.get("userId").toString());
				_requirement.setModId(params.get("userId").toString());
				_requirement.setRegDate(params.get("regDate").toString());
				_requirement.setModDate(params.get("regDate").toString());

				// attach business
				_requirement.setBusiness(_business);

				// attach interface
				_interfacez.setDelYn("N");
				_interfacez.setRegId(params.get("userId").toString());
				_interfacez.setModId(params.get("userId").toString());
				_interfacez.setRegDate(params.get("regDate").toString());
				_interfacez.setModDate(params.get("regDate").toString());

				_requirement.setInterfaceInfo(_interfacez);

				// attach channel
				_interfacez.setChannel(_channel);

				// attach systemList
				_interfacez.setSystemList(_systemList);

				// attach businessList
				//_interfacez.setBusinessList(_businessList);
				_interfacez.setBusinessList(_businessList);

				// attach relUsers
				_interfacez.setRelUsers(_relUsers);

				// attach interfaceMapping
				_interfacez.setInterfaceMapping(_interfaceMapping);

				// attach interfacePropertyList
				_interfacez.setProperties(_interfacePropertyList);

				// attch interfaceDataAccessRoleList
				_interfacez.setDataAccessRoleList(_dataAccessRoleList);

			}

			//-----------------------------------------------------------------
			// Col Data
			//-----------------------------------------------------------------
			for( int j = 0; j < cols.length; j++ ) {
				String[] header = headerlist.get(1);  // 엑셀헤더
				switch( j ) {

				case 0 :
					//-----------------------------------------------------------------
					// ★ 번호
					//-----------------------------------------------------------------
				{

				}
				break;
				case 1 :
					//-----------------------------------------------------------------
					// ★ 인터페이스기본-인터페이스ID
					//-----------------------------------------------------------------
					if( isSeqGroup ) break;
					{
						String colNm = "인터페이스기본-인터페이스 ID";
						if( Util.isEmpty(cols[j]) ) {
							throw new NullPointerException("[ 엑셀 업로드 실패 ] : " + (i+1) +" 번째 행, [" + colNm + "] 값이 공백입니다.");
						} else {
							_interfacez.setIntegrationId(cols[j].trim());
						}
					}

					break;
				case 2 :
					//-----------------------------------------------------------------
					// ★ 인터페이스기본-인터페이스명
					//-----------------------------------------------------------------
					if( isSeqGroup ) break;
					{
						String colNm = "인터페이스기본-인터페이스명";
						if( Util.isEmpty(cols[j]) ) {
							throw new NullPointerException("[ 엑셀 업로드 실패 ] : " + (i+1) +" 번째 행, [" + colNm + "] 값이 공백입니다.");
						} else {
							_interfacez.setInterfaceNm( cols[j].trim() );

							// requirement의 업무명. TAN0101의 REQUIREMENT_NM은 임시로 해당 업무명으로 셋팅.
							// 위의 REQUIREMENT_NM은 DB상 NULL허용이 아니기 떄문에
							// 현재는 임시로 해당 값은 인터페이스명으로 대체함. 추후 어떻게 갈것인지 방향 정의 필요.
							_requirement.setRequirementNm(cols[j].trim());
						}
					}
					break;
				case 3 :
					//-----------------------------------------------------------------
					// ★ 인터페이스기본-인터페이스설명
					//-----------------------------------------------------------------
					if( isSeqGroup ) break;
					{
						if( Util.isEmpty(cols[j]) ) {
							_requirement.setComments("");
						} else {
							_requirement.setComments( cols[j].trim() );
						}
					}
					break;
				case 4 :
					//-----------------------------------------------------------------
					// ★ 업무-업무대분류명(형통은 LEVEL2 까지 관리)
					//-----------------------------------------------------------------
				{
					String colNm = "업무-업무대분류명";
					if( Util.isEmpty(cols[j]) ) {
						throw new NullPointerException("[ 엑셀 업로드 실패 ] : " + (i+1) +" 번째 행, [" + colNm + "] 값이 공백입니다.");
					} else {

					}
				}
				break;
				case 5 :
					//-----------------------------------------------------------------
					// ★ 업무-업무대분류코드
					//-----------------------------------------------------------------
				{
					String colNm = "업무-업무대분류코드";
					businessList[0] = null;
					if( Util.isEmpty(cols[j]) ) {
						throw new NullPointerException("[ 엑셀 업로드 실패 ] : " + (i+1) +" 번째 행, [" + colNm + "] 값이 공백입니다.");
					} else {
						searchMap.put("businessCd", cols[j].trim());
						List<Business> checkBusinessList = commonService.getBusinessList(searchMap);
						if( checkBusinessList == null || checkBusinessList.size() == 0 ) {
							throw new Exception("[ 엑셀 업로드 실패 ] : 업무대분류 코드 [" + searchMap.get("businessCd").trim() + "] 가 시스템에 존재하지 않습니다.");
						} else {
							businessList[0] = checkBusinessList.get(0);
							//Business business = checkBusinessList.get(0);
							//business.setBusinessId(business.getBusinessId());
							//business.setBusinessCd(business.getBusinessCd());
							//business.setBusinessNm(business.getBusinessNm());
						}

					}
				}
				break;
				case 6 :
					//-----------------------------------------------------------------
					// ★ 업무-업무중분류명
					//-----------------------------------------------------------------
				{
					if( Util.isEmpty(cols[j]) ) {
					} else {
					}
				}
				break;
				case 7 :
					//-----------------------------------------------------------------
					// ★ 업무-업무중분류코드
					//-----------------------------------------------------------------
				{
					businessList[1] = null;
					if( Util.isEmpty(cols[j]) ) {
					} else {
						searchMap.put("businessCd", cols[j].trim());
						List<Business> checkBusinessList = commonService.getBusinessList(searchMap);
						if( checkBusinessList == null || checkBusinessList.size() == 0 ) {
							throw new Exception("[ 엑셀 업로드 실패 ] : 업무중분류 코드 [" + searchMap.get("businessCd").trim() + "] 가 시스템에 존재하지 않습니다.");
						} else {
							businessList[1] = checkBusinessList.get(0);
							//Business business = checkBusinessList.get(0);
							//business.setBusinessId(business.getBusinessId());
							//business.setBusinessCd(business.getBusinessCd());
							//business.setBusinessNm(business.getBusinessNm());
						}
					}

				}
				break;
				case 8 :
					//-----------------------------------------------------------------
					// ★ 업무-업무명
					//-----------------------------------------------------------------
				{
					if( Util.isEmpty(cols[j]) ) {
					} else {
					}
				}
				break;
				case 9 :
					//-----------------------------------------------------------------
					// ☆ 업무-업무코드
					//-----------------------------------------------------------------
				{
					businessList[2] = null;
					if( Util.isEmpty(cols[j]) ) {
					} else {
						searchMap.put("businessCd", cols[j].trim());
						List<Business> checkBusinessList = commonService.getBusinessList(searchMap);
						if( checkBusinessList == null || checkBusinessList.size() == 0 ) {
							throw new Exception("[ 엑셀 업로드 실패 ] : 업무중분류 코드 [" + searchMap.get("businessCd").trim() + "] 가 시스템에 존재하지 않습니다.");
						} else {
							businessList[2] = checkBusinessList.get(0);
						}
					}

					if( isSeqGroup ) break;

					if(businessList[0] != null && businessList[1] == null && businessList[2] == null) {
						_business.setBusinessId(businessList[0].getBusinessId());
						_business.setBusinessCd(businessList[0].getBusinessCd());
						_business.setBusinessNm(businessList[0].getBusinessNm());
					} else if (businessList[0] != null && businessList[1] != null && businessList[2] == null) {
						_business.setBusinessId(businessList[1].getBusinessId());
						_business.setBusinessCd(businessList[1].getBusinessCd());
						_business.setBusinessNm(businessList[1].getBusinessNm());
					} else if (businessList[0] != null && businessList[1] != null && businessList[2] != null) {
						_business.setBusinessId(businessList[2].getBusinessId());
						_business.setBusinessCd(businessList[2].getBusinessCd());
						_business.setBusinessNm(businessList[2].getBusinessNm());
					} else {

					}

					_business.setSeq(0);
					_business.setNodeType("0");
					_business.setDelYn("N");
					_business.setRegId(params.get("userId").toString());
					_business.setModId(params.get("userId").toString());
					_business.setRegDate(params.get("regDate").toString());
					_business.setModDate(params.get("regDate").toString());

					_businessList.add(_business);
				}
				break;
				case 10 :
					//-----------------------------------------------------------------
					// ★ 기관-송신기관대분류명
					//-----------------------------------------------------------------
				{
					String colNm = "기관-송신기관대분류명";
					if( Util.isEmpty(cols[j]) ) {
						throw new NullPointerException("[ 엑셀 업로드 실패 ] : " + (i+1) +" 번째 행, [" + colNm + "] 값이 공백입니다.");
					} else {
					}
				}
				break;
				case 11 :
					//-----------------------------------------------------------------
					// ☆ 기관-송신기관대분류코드
					//-----------------------------------------------------------------
				{
					String colNm = "기관-송신기관대분류코드";
					if( Util.isEmpty(cols[j]) || cols[j].trim().equals("N/A") ) {
						throw new NullPointerException("[ 엑셀 업로드 실패 ] : " + (i+1) +" 번째 행, [" + colNm + "] 값이 공백입니다.");
					} else {
						organizationCdList[0] = cols[j].trim();
						searchMap.put("organizationCd", cols[j].trim());
						List<Organization> checkOrganizationList = commonService.getOrganizationList(searchMap);
						if(checkOrganizationList == null || checkOrganizationList.size() == 0) {
							throw new Exception("[ 엑셀 업로드 실패 ] : 송신기관 정보(대분류) [" + cols[j].trim() + "] 가 시스템에 존재하지 않습니다.");
						} else {
							// 실제 기관은 시스템과 맵핑이 되어있기때문에 시스템이 제대로 등록이 된다면 기관은 별도로 등록할 필요는 없다.
							// 단, 추후 시스템이 소속된기관과 잘 맵핑이 되어있는지 validation체크하는 것을 구현할 것.
							// 위의 commonService.getOraganizationList의 결과는 해당 Organization VO안에 기관에 소속된 시스템리스트가 들어있으므로
							// 체크시 시스템에 대한 쿼리를 따로 수행할 필요가 없음.
							//Organization organization = checkOrganizationList.get(0);
							//_organization.setOrganizationId(organization.getOrganizationId());
							//_organization.setOrganizationCd(organization.getOrganizationCd());
							//_organization.setOrganizationNm(organization.getOrganizationNm());
						}
					}

					//_organization.setRegId(params.get("userId").toString());
					//_organization.setModId(params.get("userId").toString());
					//_organization.setRegDate(params.get("regDate").toString());
					//_organization.setModDate(params.get("regDate").toString());
				}
				break;
				case 12 :
					//-----------------------------------------------------------------
					// ★ 기관-송신기관중분류명
					//-----------------------------------------------------------------
				{
					if( Util.isEmpty(cols[j]) ) {
					} else {
					}
				}
				break;
				case 13 :
					//-----------------------------------------------------------------
					// ★ 기관-송신기관중분류코드
					//-----------------------------------------------------------------
				{
					if( Util.isEmpty(cols[j]) || cols[j].trim().equals("N/A") ) {
						organizationCdList[1] = null;
					} else {
						organizationCdList[1] = cols[j].trim();
						searchMap.put("organizationCd", cols[j].trim());
						List<Organization> checkOrganizationList = commonService.getOrganizationList(searchMap);
						if(checkOrganizationList == null || checkOrganizationList.size() == 0) {
							throw new Exception("[ 엑셀 업로드 실패 ] : 송신기관 정보(중분류) [" + cols[j].trim() + "] 가 시스템에 존재하지 않습니다.");
						} else {
							// 실제 기관은 시스템과 맵핑이 되어있기때문에 시스템이 제대로 등록이 된다면 기관은 별도로 등록할 필요는 없다.
							// 단, 추후 시스템이 소속된기관과 잘 맵핑이 되어있는지 validation체크하는 것을 구현할 것.
							// 위의 commonService.getOraganizationList의 결과는 해당 Organization VO안에 기관에 소속된 시스템리스트가 들어있으므로
							// 체크시 시스템에 대한 쿼리를 따로 수행할 필요가 없음.
							//Organization organization = checkOrganizationList.get(0);
							//_organization.setOrganizationId(organization.getOrganizationId());
							//_organization.setOrganizationCd(organization.getOrganizationCd());
							//_organization.setOrganizationNm(organization.getOrganizationNm());
						}
					}

					//_organization.setRegId(params.get("userId").toString());
					//_organization.setModId(params.get("userId").toString());
					//_organization.setRegDate(params.get("regDate").toString());
					//_organization.setModDate(params.get("regDate").toString());
				}
				break;
				case 14 :
					//-----------------------------------------------------------------
					// ★ 기관-송신기관명
					//-----------------------------------------------------------------
				{
					if( Util.isEmpty(cols[j]) ) {
					} else {
					}
				}
				break;
				case 15 :
					//-----------------------------------------------------------------
					// ★ 기관-송신기관코드
					//-----------------------------------------------------------------
				{
					if( Util.isEmpty(cols[j]) || cols[j].trim().equals("N/A") ) {
						organizationCdList[2] = null;
					} else {
						organizationCdList[2] = cols[j].trim();
						searchMap.put("organizationCd", cols[j].trim());
						List<Organization> checkOrganizationList = commonService.getOrganizationList(searchMap);
						if(checkOrganizationList == null || checkOrganizationList.size() == 0) {
							throw new Exception("[ 엑셀 업로드 실패 ] : 송신기관 정보(소분류) [" + cols[j].trim() + "] 가 시스템에 존재하지 않습니다.");
						} else {
							// 실제 기관은 시스템과 맵핑이 되어있기때문에 시스템이 제대로 등록이 된다면 기관은 별도로 등록할 필요는 없다.
							// 단, 추후 시스템이 소속된기관과 잘 맵핑이 되어있는지 validation체크하는 것을 구현할 것.
							// 위의 commonService.getOraganizationList의 결과는 해당 Organization VO안에 기관에 소속된 시스템리스트가 들어있으므로
							// 체크시 시스템에 대한 쿼리를 따로 수행할 필요가 없음.
							//Organization organization = checkOrganizationList.get(0);
							//_organization.setOrganizationId(organization.getOrganizationId());
							//_organization.setOrganizationCd(organization.getOrganizationCd());
							//_organization.setOrganizationNm(organization.getOrganizationNm());
						}
					}

					//_organization.setRegId(params.get("userId").toString());
					//_organization.setModId(params.get("userId").toString());
					//_organization.setRegDate(params.get("regDate").toString());
					//_organization.setModDate(params.get("regDate").toString());
				}
				break;
				case 16 :
					//-----------------------------------------------------------------
					// ★ 기관-수신기관대분류명
					//-----------------------------------------------------------------
				{
					String colNm = "기관-수신기관대분류명";
					if( Util.isEmpty(cols[j]) ) {
						throw new NullPointerException("[ 엑셀 업로드 실패 ] : " + (i+1) +" 번째 행, [" + colNm + "] 값이 공백입니다.");
					} else {
					}
				}
				break;
				case 17 :
					//-----------------------------------------------------------------
					// ☆ 기관-수신기관대분류코드
					//-----------------------------------------------------------------
				{
					String colNm = "기관-수신기관대분류코드";
					if( Util.isEmpty(cols[j]) || cols[j].trim().equals("N/A") ) {
						throw new NullPointerException("[ 엑셀 업로드 실패 ] : " + (i+1) +" 번째 행, [" + colNm + "] 값이 공백입니다.");
					} else {
						organizationCdList[0] = cols[j].trim();
						searchMap.put("organizationCd", cols[j].trim());
						List<Organization> checkOrganizationList = commonService.getOrganizationList(searchMap);
						if(checkOrganizationList == null || checkOrganizationList.size() == 0) {
							throw new Exception("[ 엑셀 업로드 실패 ] : 수신기관 정보(대분류) [" + cols[j].trim() + "] 가 시스템에 존재하지 않습니다.");
						} else {
							// 실제 기관은 시스템과 맵핑이 되어있기때문에 시스템이 제대로 등록이 된다면 기관은 별도로 등록할 필요는 없다.
							// 단, 추후 시스템이 소속된기관과 잘 맵핑이 되어있는지 validation체크하는 것을 구현할 것.
							// 위의 commonService.getOraganizationList의 결과는 해당 Organization VO안에 기관에 소속된 시스템리스트가 들어있으므로
							// 체크시 시스템에 대한 쿼리를 따로 수행할 필요가 없음.
							//Organization organization = checkOrganizationList.get(0);
							//_organization.setOrganizationId(organization.getOrganizationId());
							//_organization.setOrganizationCd(organization.getOrganizationCd());
							//_organization.setOrganizationNm(organization.getOrganizationNm());
						}
					}
				}
				break;
				case 18 :
					//-----------------------------------------------------------------
					// ★ 기관-수신기관중분류명
					//-----------------------------------------------------------------
				{
					if( Util.isEmpty(cols[j]) ) {
					} else {
					}
				}
				break;
				case 19 :
					//-----------------------------------------------------------------
					// ★ 기관-수신기관중분류코드
					//-----------------------------------------------------------------
				{
					if( Util.isEmpty(cols[j]) || cols[j].trim().equals("N/A") ) {
						organizationCdList[1] = null;
					} else {
						organizationCdList[1] = cols[j].trim();
						searchMap.put("organizationCd", cols[j].trim());
						List<Organization> checkOrganizationList = commonService.getOrganizationList(searchMap);
						if(checkOrganizationList == null || checkOrganizationList.size() == 0) {
							throw new Exception("[ 엑셀 업로드 실패 ] : 수신기관 정보(중분류) [" + cols[j].trim() + "] 가 시스템에 존재하지 않습니다.");
						} else {
						}
					}

				}
				break;
				case 20 :
					//-----------------------------------------------------------------
					// ★ 기관-수신기관명
					//-----------------------------------------------------------------
				{
					if( Util.isEmpty(cols[j]) ) {
					} else {
					}
				}
				break;
				case 21 :
					//-----------------------------------------------------------------
					// ★ 기관-수신기관코드
					//-----------------------------------------------------------------
				{
					if( Util.isEmpty(cols[j]) || cols[j].trim().equals("N/A") ) {
						organizationCdList[2] = null;
					} else {
						organizationCdList[2] = cols[j].trim();
						searchMap.put("organizationCd", cols[j].trim());
						List<Organization> checkOrganizationList = commonService.getOrganizationList(searchMap);
						if(checkOrganizationList == null || checkOrganizationList.size() == 0) {
							throw new Exception("[ 엑셀 업로드 실패 ] : 수신기관 정보(소분류) [" + cols[j].trim() + "] 가 시스템에 존재하지 않습니다.");
						} else {
						}
					}
				}
				break;
				case 22 :
					//-----------------------------------------------------------------
					// ☆ 시스템-송신시스템대분류명(형통은 LEVEL1까지 관리)
					//-----------------------------------------------------------------
					if( isSeqGroup ) break;
					{
						String colNm = "시스템-송신시스템대분류명";
						if( Util.isEmpty(cols[j]) ) {
							throw new NullPointerException("[ 엑셀 업로드 실패 ] : " + (i+1) +" 번째 행, [" + colNm + "] 값이 공백입니다.");
						} else {
						}
					}
					break;
				case 23 :
					//-----------------------------------------------------------------
					// ★ 시스템-송신시스템대분류코드
					//-----------------------------------------------------------------
					if( isSeqGroup ) break;
					{
						if( Util.isEmpty(cols[j]) ) {
						} else {
						}
					}
					//
					break;
				case 24 :
					//-----------------------------------------------------------------
					// ☆ 시스템-송신시스템중분류명
					//-----------------------------------------------------------------
					if( isSeqGroup ) break;
					{
						if( Util.isEmpty(cols[j]) ) {
						} else {
						}
					}
					break;
				case 25 :
					//-----------------------------------------------------------------
					// ☆ 시스템-송신시스템중분류코드
					//-----------------------------------------------------------------
					if( isSeqGroup ) break;
					{
						if( Util.isEmpty(cols[j]) ) {
						} else {
						}
					}
				case 26 :
					//-----------------------------------------------------------------
					// ☆ 시스템-송신시스템명
					//-----------------------------------------------------------------
					if( isSeqGroup ) break;
					{
						if( Util.isEmpty(cols[j]) ) {
						} else {
						}
					}
					break;
				case 27 :
					//-----------------------------------------------------------------
					// ☆ 시스템-송신시스템코드
					//-----------------------------------------------------------------
					if( isSeqGroup ) break;
					{
						String colNm = "시스템-송신시스템코드";
						if( Util.isEmpty(cols[j]) ) {
							throw new NullPointerException("[ 엑셀 업로드 실패 ] : " + (i+1) +" 번째 행, [" + colNm + "] 값이 공백입니다.");
						} else {
							String cd = "";
							String id = "";
							String nm = "";

							boolean matchFlag = false;
							for(pep.per.mint.common.data.basic.System system : checkSystemCdList) {
								if(system.getSystemCd().equalsIgnoreCase(cols[j].trim())) {
									cd = system.getSystemCd();
									id = system.getSystemId();
									nm = system.getSystemNm();
									matchFlag = true;
									break;
								}
							}

							if( matchFlag ) {
								_consumerSystem.setSystemCd( cd );
								_consumerSystem.setSystemId( id );
								_consumerSystem.setSystemNm( nm );

								_consumerSystem.setSeq(_systemList.size());
								_consumerSystem.setNodeType("0");
								_consumerSystem.setNodeTypeNm("송신");
								_consumerSystem.setDelYn("N");
								_consumerSystem.setRegId(params.get("userId").toString());
								_consumerSystem.setModId(params.get("userId").toString());
								_consumerSystem.setRegDate(params.get("regDate").toString());
								_consumerSystem.setModDate(params.get("regDate").toString());

								_systemList.add(_consumerSystem);
							} else {
								throw new Exception("[ 엑셀 업로드 실패 ] : " + (i+1) +" 번째 행, [" + colNm + "] 값이 올바르지 않습니다.");
							}
						}
					}
					break;
				case 28 :
					//-----------------------------------------------------------------
					// ☆ 시스템-수신시스템대분류명
					//-----------------------------------------------------------------
				{
					String colNm = "시스템-수신시스템대분류명";
					if( Util.isEmpty(cols[j]) ) {
						throw new NullPointerException("[ 엑셀 업로드 실패 ] : " + (i+1) +" 번째 행, [" + colNm + "] 값이 공백입니다.");
					} else {
					}
				}
				break;
				case 29 :
					//-----------------------------------------------------------------
					// ☆ 시스템-수신시스템대분류코드
					//-----------------------------------------------------------------
				{
					if( Util.isEmpty(cols[j]) ) {
					} else {
					}
				}
				break;
				case 30 :
					//-----------------------------------------------------------------
					// ☆ 시스템-수신시스템중분류명
					//-----------------------------------------------------------------
				{
					if( Util.isEmpty(cols[j]) ) {
					} else {
					}
				}
				break;
				case 31 :
					//-----------------------------------------------------------------
					// ☆ 시스템-수신시스템중분류코드
					//-----------------------------------------------------------------
				{
					if( Util.isEmpty(cols[j]) ) {
					} else {
					}
				}
				break;
				case 32 :
					//-----------------------------------------------------------------
					// ☆ 시스템-수신시스템명
					//-----------------------------------------------------------------
				{
					if( Util.isEmpty(cols[j]) ) {
					} else {
					}
				}
				break;
				case 33 :
					//-----------------------------------------------------------------
					// ☆ 시스템-수신시스템코드
					//-----------------------------------------------------------------
				{
					String colNm = "시스템-수신시스템코드";
					if( Util.isEmpty(cols[j]) ) {
						//if( isSeqGroup ) break;
						throw new NullPointerException("[ 엑셀 업로드 실패 ] : " + (i+1) +" 번째 행, [" + colNm + "] 값이 공백입니다.");
					} else {

						String cd = "";
						String id = "";
						String nm = "";

						boolean matchFlag = false;
						for(pep.per.mint.common.data.basic.System system : checkSystemCdList) {
							if(system.getSystemCd().equalsIgnoreCase(cols[j].trim())) {
								cd = system.getSystemCd();
								id = system.getSystemId();
								nm = system.getSystemNm();
								matchFlag = true;
								break;
							}
						}

						if( matchFlag ) {
							_providerSystem.setSystemCd( cd );
							_providerSystem.setSystemId( id );
							_providerSystem.setSystemNm( nm );

							_providerSystem.setSeq(_systemList.size());
							_providerSystem.setNodeType("2");
							_providerSystem.setNodeTypeNm("수신");
							_providerSystem.setDelYn("N");
							_providerSystem.setRegId(params.get("userId").toString());
							_providerSystem.setModId(params.get("userId").toString());
							_providerSystem.setRegDate(params.get("regDate").toString());
							_providerSystem.setModDate(params.get("regDate").toString());

							_systemList.add(_providerSystem);
						} else {
							throw new Exception("[ 엑셀 업로드 실패 ] : " + (i+1) +" 번째 행, [" + colNm + "] 값이 올바르지 않습니다.");
						}
					}
				}
				break;
				case 34 :
					//-----------------------------------------------------------------
					// ☆ 연계기본정보-연계채널
					//-----------------------------------------------------------------
					if( isSeqGroup ) break;
					{
						String colNm = "연계기본정보-연계채널";
						if( Util.isEmpty(cols[j]) ) {
							throw new NullPointerException("[ 엑셀 업로드 실패 ] : " + (i+1) +" 번째 행, [" + colNm + "] 값이 공백입니다.");
						} else {
							boolean matchFlag = false;
							Channel matchChannel = null;
							for(Channel ch : checkChannelList) {
								if(ch.getChannelNm().equals(cols[j].trim())) {
									//if(ch.getChannelNm().equals(chlNm)) {
									matchChannel = ch;
									matchFlag = true;
									break;
								}
							}

							if( matchFlag ) {
								_channel.setChannelId( matchChannel.getChannelId() );
								_channel.setChannelCd( matchChannel.getChannelCd() );
								_channel.setChannelNm( matchChannel.getChannelNm() );
							} else {
								throw new Exception("[ 엑셀 업로드 실패 ] : " + (i+1) +" 번째 행, [" + colNm + "] 값이 올바르지 않습니다.");
							}
						}
					}
					break;
				case 35 :
					//-----------------------------------------------------------------
					// ☆ 연계기본정보-DATA처리흐름
					//-----------------------------------------------------------------
					if( isSeqGroup ) break;
					{
						String colNm = "연계기본정보-DATA처리흐름";
						if( Util.isEmpty(cols[j]) ) {
							throw new NullPointerException("[ 엑셀 업로드 실패 ] : " + (i+1) +" 번째 행, [" + colNm + "] 값이 공백입니다.");
						} else {

							String cd = "";
							String nm = "";

							boolean matchFlag = false;
							for(CommonCode commonCode : checkDataPrDir) {
								if(commonCode.getNm().equals(cols[j].trim())) {
									cd = commonCode.getCd();
									nm = commonCode.getNm();
									matchFlag = true;
									break;
								}
							}

							if( matchFlag ) {
								_interfacez.setDataPrDir( cd );
								_interfacez.setDataPrDirNm( nm );
							} else {
								throw new Exception("[ 엑셀 업로드 실패 ] : " + (i+1) +" 번째 행, [" + colNm + "] 값이 올바르지 않습니다.");
							}
						}
					}
					break;
				case 36 :
					//-----------------------------------------------------------------
					// ☆ 연계기본정보-App처리방식
					//-----------------------------------------------------------------
					if( isSeqGroup ) break;
					{
						String colNm = "연계기본정보-App처리방식";
						if( Util.isEmpty(cols[j]) ) {
							throw new NullPointerException("[ 엑셀 업로드 실패 ] : " + (i+1) +" 번째 행, [" + colNm + "] 값이 공백입니다.");
						} else {

							String cd = "";
							String nm = "";

							boolean matchFlag = false;
							for(CommonCode commonCode : checkAppPrMethod) {
								if(commonCode.getNm().equals(cols[j].trim())) {
									cd = commonCode.getCd();
									nm = commonCode.getNm();
									matchFlag = true;
									break;
								}
							}

							if( matchFlag ) {
								_interfacez.setAppPrMethod( cd );
								_interfacez.setAppPrMethodNm( nm );
							} else {
								throw new Exception("[ 엑셀 업로드 실패 ] : " + (i+1) +" 번째 행, [" + colNm + "] 값이 올바르지 않습니다.");
							}
						}
					}
					break;
				case 37 :
					//-----------------------------------------------------------------
					// ☆ 연계기본정보-Data처리방식
					//-----------------------------------------------------------------
					if( isSeqGroup ) break;
					{
						String colNm = "연계기본정보-인터페이스유형";
						if( Util.isEmpty(cols[j]) ) {
							throw new NullPointerException("[ 엑셀 업로드 실패 ] : " + (i+1) +" 번째 행, [" + colNm + "] 값이 공백입니다.");
						} else {

							String cd = "";
							String nm = "";

							boolean matchFlag = false;
							for(CommonCode commonCode : checkDataPrMethod) {
								if(commonCode.getNm().equals(cols[j].trim())) {
									cd = commonCode.getCd();
									nm = commonCode.getNm();
									matchFlag = true;
									break;
								}
							}

							if( matchFlag ) {
								_interfacez.setDataPrMethod( cd );
								_interfacez.setDataPrMethodNm( nm );
							} else {
								throw new Exception("[ 엑셀 업로드 실패 ] : " + (i+1) +" 번째 행, [" + colNm + "] 값이 올바르지 않습니다.");
							}
						}
					}
					break;
				case 38 :
					//-----------------------------------------------------------------
					// ☆ 연계기본정보-중요도
					//-----------------------------------------------------------------
					if( isSeqGroup ) break;
					{
						String colNm = "관리정보-중요도";
						if( Util.isEmpty(cols[j]) ) {
							_interfacez.setImportance( "1" );
							_interfacez.setImportanceNm( "중" );
						} else {

							String cd = "";
							String nm = "";

							boolean matchFlag = false;
							for(CommonCode commonCode : checkImportance) {
								if(commonCode.getNm().equals(cols[j].trim())) {
									cd = commonCode.getCd();
									nm = commonCode.getNm();
									matchFlag = true;
									break;
								}
							}

							if( matchFlag ) {
								_interfacez.setImportance( cd );
								_interfacez.setImportanceNm( nm );
							} else {
								throw new Exception("[ 엑셀 업로드 실패 ] : " + (i+1) +" 번째 행, [" + colNm + "] 값이 올바르지 않습니다.");
							}
						}
					}
					break;
				case 39 :
					//-----------------------------------------------------------------
					// ☆ 연계기본정보-진행상태
					//-----------------------------------------------------------------
				{
					if( Util.isEmpty(cols[j]) ) {
					} else {
					}
				}
				break;
				case 40 :
					//-----------------------------------------------------------------
					// ☆ 연계기본정보-등록일자(sysdate자동생성)
					//-----------------------------------------------------------------
				{
					if( Util.isEmpty(cols[j]) ) {
					} else {
					}
				}
				break;
				case 41 :
					//-----------------------------------------------------------------
					// ☆ 서비스-송신리소스
					//-----------------------------------------------------------------

					if( isSeqGroup ) break;
					{
						String colNm = "송신 리소스";
						String consumerResourceCd = "";
						String consumerResourceNm = "";
						boolean consumerMatchFlag = false;
						for(CommonCode commonCode : checkResourceCd) {
							if(commonCode.getNm().equalsIgnoreCase(cols[j].trim())) {
								consumerResourceCd = commonCode.getCd();
								consumerResourceNm = commonCode.getNm();
								consumerMatchFlag = true;
								break;
							}
						}

						if( consumerMatchFlag ) {
							_consumerSystem.setResourceCd( consumerResourceCd );
							_consumerSystem.setResourceNm( consumerResourceNm );
						} else {
							throw new Exception("[ 엑셀 업로드 실패 ] : " + (i+1) +" 번째 행, [" + colNm+ "] 값이 올바르지 않습니다.");
						}
						//_consumerSystem.setResourceCd(cols[j].trim());
					}
					break;
				case 42 :
					//-----------------------------------------------------------------
					// ☆ 서비스-송신 연결정보
					//-----------------------------------------------------------------
					if( isSeqGroup ) break;
					{
						_consumerSystem.setService(cols[j].trim());
					}
					break;
				case 43 :
					//-----------------------------------------------------------------
					// ☆ 서비스-송신 서비스명
					//-----------------------------------------------------------------
					if( isSeqGroup ) break;
					{
						_consumerSystem.setServiceDesc(cols[j].trim());
					}
					break;
				case 44 :
					//-----------------------------------------------------------------
					// ☆ 서비스-수신 리소스
					//-----------------------------------------------------------------

					if( isSeqGroup ) break;
					{
						String colNm = "수신 리소스";
						String providerResourceCd = "";
						String providerResourceNm = "";
						boolean providerMatchFlag = false;
						for(CommonCode commonCode : checkResourceCd) {
							if(commonCode.getNm().equalsIgnoreCase(cols[j].trim())) {
								providerResourceCd = commonCode.getCd();
								providerResourceNm = commonCode.getNm();
								providerMatchFlag = true;
								break;
							}
						}

						if( providerMatchFlag ) {
							_providerSystem.setResourceCd( providerResourceCd );
							_providerSystem.setResourceNm( providerResourceNm );
						} else {
							throw new Exception("[ 엑셀 업로드 실패 ] : " + (i+1) +" 번째 행, [" + colNm+ "] 값이 올바르지 않습니다.");
						}
						//_consumerSystem.setResourceCd(cols[j].trim());
					}
					break;
				case 45 :
					//-----------------------------------------------------------------
					// ☆ 서비스-수신 연결정보
					//-----------------------------------------------------------------
				{
					_providerSystem.setService(cols[j].trim());
				}
				break;
				case 46 :
					//-----------------------------------------------------------------
					// ☆ 서비스-수신 서비스명
					//-----------------------------------------------------------------
				{
					_providerSystem.setServiceDesc(cols[j].trim());
				}
				break;
				case 47 :
					//-----------------------------------------------------------------
					// ☆ 스케줄-발생주기
					//-----------------------------------------------------------------
					if( isSeqGroup ) break;
					{
						String colNm = "스케줄-발생주기";
						if( Util.isEmpty(cols[j]) ) {
							throw new NullPointerException("[ 엑셀 업로드 실패 ] : " + (i+1) +" 번째 행, [" + colNm + "] 값이 공백입니다.");
						} else {

							String cd = "";
							String nm = "";

							boolean matchFlag = false;
							for(CommonCode commonCode : checkDataFrequency) {
								if(commonCode.getNm().equals(cols[j].trim())) {
									cd = commonCode.getCd();
									nm = commonCode.getNm();
									matchFlag = true;
									break;
								}
							}

							if( matchFlag ) {
								_interfacez.setDataFrequency( cd );
								_interfacez.setDataFrequencyNm( nm );
							} else {
								throw new Exception("[ 엑셀 업로드 실패 ] : " + (i+1) +" 번째 행, [" + colNm + "] 값이 올바르지 않습니다.");
							}
						}
					}
					break;
				case 48 :
					//-----------------------------------------------------------------
					// ☆ 스케줄-발생주기입력값
					//-----------------------------------------------------------------
					if( isSeqGroup ) break;
					{
						String colNm = "스케줄-발생주기입력값";
						if( Util.isEmpty(cols[j]) ) {
							_interfacez.setDataFrequencyInput("");
						} else {
							_interfacez.setDataFrequencyInput( cols[j].trim() );
						}
					}
					break;
				case 49 :
					//-----------------------------------------------------------------
					// ☆ 스케줄-발생조건
					//-----------------------------------------------------------------
					if( isSeqGroup ) break;
					{
						if( Util.isEmpty(cols[j]) ) {
						} else {
						}
					}
					break;
				case 50 :
					//-----------------------------------------------------------------
					// ☆ 담당자-데이터접근권한명
					//-----------------------------------------------------------------
					if( isSeqGroup ) break;
					{
						if( Util.isEmpty(cols[j]) ) {
						} else {
						}
					}
					break;
				case 51 :
					//-----------------------------------------------------------------
					// ☆ 담당자-데이터접근권한
					//-----------------------------------------------------------------
				{
					String colNm = "담당자-데이터접근권한";
					if( Util.isEmpty(cols[j]) ) {
						throw new NullPointerException("[ 엑셀 업로드 실패 ] : " + (i+1) +" 번째 행, [" + colNm + "] 값이 공백입니다.");
					} else {
						/*
						 * 1. 엑셀값 형식 체크한다.(','로 구분되어있으면 정상, 아니면 예외처리)
						 * 2. ','구분된 값을 분리한 뒤 값이 중복인지 확인(HashSet을 써서 중복처리함.)
						 * 3. 값이 DB에 존재하는지 validation 체크(존재하지 않으면 예외처리)
						 * 4-1. 신규인터페이스일경우 validation체크한 값을 등록
						 * 4-2. 기존인터페이스(순번이동일하면) 이전 interface등록 정보에 이미 값이 있는지 체크(있으면 skip, 없으면 등록)
						 */
						// TODO
						// 하나,그 이상 처리하게 보완필요
						//if(cols[j].trim().matches(".*,.*")) {
						if(cols[j] != null){
							String[] dataAccessRoles = cols[j].trim().split(",");
							if(dataAccessRoles.length>0){
								//중복된 값 필터를 위해 Set 사용
								HashSet<String> dataAccessRolesSet = new HashSet<String>();

								for(int k=0; k<dataAccessRoles.length; k++) {
									dataAccessRolesSet.add(dataAccessRoles[k].trim());
								}

								Iterator<String> iterator = dataAccessRolesSet.iterator();
								while(iterator.hasNext()) {
									String dataAccessRoleCd = iterator.next().trim();
									DataAccessRole matchDataAccessRole = null;
									boolean matchFlag = false;
									for(DataAccessRole dar : ckeckDataAccessRoleList) {
										if(dar.getRoleCd().equals(dataAccessRoleCd)) {
											matchDataAccessRole = dar;
											matchFlag = true;
											break;
										}
									}
									if(matchFlag) {
										if(isSeqGroup) {
											boolean existFlag = false;
											for(DataAccessRole dar : _dataAccessRoleList) {
												if(dar.getRoleCd().equals(matchDataAccessRole.getRoleCd().trim())) {
													existFlag = true;
													break;
												}
											}
											if(!existFlag) {
												_dataAccessRoleList.add(matchDataAccessRole);
											}
										} else {
											_dataAccessRoleList.add(matchDataAccessRole);
										}
									} else {
										throw new Exception("[ 엑셀 업로드 실패 ] : " + (i+1) +" 번째 행, [" + colNm + "] 값 " + dataAccessRoleCd + "는 등록되어있지 않은 값입니다.");
										//break;
									}
								}
							}else{
								throw new Exception("[ 엑셀 업로드 실패 ] : " + (i+1) +" 번째 행, [" + colNm + "]  구분데이타가 없습니다.");
							}

						}else{
							throw new Exception("[ 엑셀 업로드 실패 ] : " + (i+1) +" 번째 행, [" + colNm + "] NULL 데이타 입니다.");
						}
					}
				}
				break;
				case 52 :
					//-----------------------------------------------------------------
					// ☆ 담당자-송신담당자
					//-----------------------------------------------------------------
					if( isSeqGroup ) break;
					{
						if( Util.isEmpty(cols[j]) ) {
						} else {
						}
					}
					break;
				case 53 :
					//-----------------------------------------------------------------
					// ☆ 담당자-송신담당자ID
					//-----------------------------------------------------------------
					if( isSeqGroup ) break;
					{
						String colNm = "담당자-송신담당자";
						if( Util.isEmpty(cols[j]) ) {
							//throw new NullPointerException("[ 엑셀 업로드 실패 ] : " + (i+1) +" 번째 행, [" + colNm + "] 값이 공백입니다.");
						} else {
							params.put("roleType", checkRoleType0.getCd());
							params.put("roleTypeNm", checkRoleType0.getNm());
							try {
								parsingUserInfo(_relUsers, cols[j], params);
							} catch( Exception e ) {
								throw new Exception("[ 엑셀 업로드 실패 ] : " + (i+1) +" 번째 행, [" + colNm + "] 값이 올바르지 않습니다.");
							}
						}
					}
					break;
				case 54 :
					//-----------------------------------------------------------------
					// ☆ 담당자-수신담당자명
					//-----------------------------------------------------------------
					if( isSeqGroup ) break;
					{
						if( Util.isEmpty(cols[j]) ) {
						} else {
						}
					}
					break;
				case 55 :
					//-----------------------------------------------------------------
					// ☆ 담당자-수신담당자ID
					//-----------------------------------------------------------------
					if( isSeqGroup ) break;
					{
						String colNm = "담당자-수신담당자";
						if( Util.isEmpty(cols[j]) ) {
							//throw new NullPointerException("[ 엑셀 업로드 실패 ] : " + (i+1) +" 번째 행, [" + colNm + "] 값이 공백입니다.");
						} else {
							params.put("roleType", checkRoleType2.getCd());
							params.put("roleTypeNm", checkRoleType2.getNm());
							try {
								parsingUserInfo(_relUsers, cols[j], params);
							} catch( Exception e ) {
								throw new Exception("[ 엑셀 업로드 실패 ] : " + (i+1) +" 번째 행, [" + colNm + "]["+cols[j]+"] 값이 올바르지 않습니다.", e);
							}
						}
					}
					break;
				case 56 :
					//-----------------------------------------------------------------
					// ☆ 담당자-연계담당자명
					//-----------------------------------------------------------------
					if( isSeqGroup ) break;
					{
						if( Util.isEmpty(cols[j]) ) {
						} else {
						}
					}
					break;
				case 57 :
					//-----------------------------------------------------------------
					// ☆ 담당자-연계담당자
					//-----------------------------------------------------------------
					if( isSeqGroup ) break;
					{
						String colNm = "담당자-연계담당자";
						if( Util.isEmpty(cols[j]) ) {
							//throw new NullPointerException("[ 엑셀 업로드 실패 ] : " + (i+1) +" 번째 행, [" + colNm + "] 값이 공백입니다.");
						} else {
							params.put("roleType", checkRoleType1.getCd());
							params.put("roleTypeNm", checkRoleType1.getNm());

							try {
								parsingUserInfo(_relUsers, cols[j], params);
							} catch( Exception e ) {
								throw new Exception("[ 엑셀 업로드 실패 ] : " + (i+1) +" 번째 행, [" + colNm + "]["+cols[j]+"] 값이 올바르지 않습니다.");
							}
						}
					}
					break;
				case 58 :
					//-----------------------------------------------------------------
					// ☆ SITE특성화컬럼  -> 특성화컬럼명#1(SITE)
					//-----------------------------------------------------------------
					if( isSeqGroup ) break;
					{
						String colNm = "특성화컬럼-1번째";
						if( Util.isEmpty(cols[j]) ) {
						} else {

							for(InterfaceAdditionalAttribute interfaceAdditionalAttribute : ckeckInterfaceAdditionalAttribute){
								if(interfaceAdditionalAttribute.getAttrNm().equalsIgnoreCase(header[j])) {
									InterfaceProperties interfaceProp = new InterfaceProperties();
									interfaceProp.setAttrId(interfaceAdditionalAttribute.getAttrId());
									interfaceProp.setAttrValue(cols[j].trim());
									interfaceProp.setType(InterfaceProperties.TYPE_INTERFACE_ATTR);
									_interfacePropertyList.add(interfaceProp);
								}
							}
						}
					}
					break;
				case 59 :
					//-----------------------------------------------------------------
					// ☆ SITE특성화컬럼  -> 특성화컬럼명#2(SITE)
					//-----------------------------------------------------------------
					if( isSeqGroup ) break;
					{
						String colNm = "특성화컬럼-2번째";
						if( Util.isEmpty(cols[j]) ) {
						} else {

							for(InterfaceAdditionalAttribute interfaceAdditionalAttribute : ckeckInterfaceAdditionalAttribute){
								if(interfaceAdditionalAttribute.getAttrNm().equalsIgnoreCase(header[j])) {
									InterfaceProperties interfaceProp = new InterfaceProperties();
									interfaceProp.setAttrId(interfaceAdditionalAttribute.getAttrId());
									interfaceProp.setAttrValue(cols[j].trim());
									interfaceProp.setType(InterfaceProperties.TYPE_INTERFACE_ATTR);
									_interfacePropertyList.add(interfaceProp);
								}
							}
						}
					}
					break;
				case 60 :
					//-----------------------------------------------------------------
					// ☆  SITE특성화컬럼  -> 특성화컬럼명#3(SITE)
					//-----------------------------------------------------------------
					if( isSeqGroup ) break;
					{
						String colNm = "특성화컬럼-3번째";
						if( Util.isEmpty(cols[j]) ) {
						} else {

							for(InterfaceAdditionalAttribute interfaceAdditionalAttribute : ckeckInterfaceAdditionalAttribute){
								if(interfaceAdditionalAttribute.getAttrNm().equalsIgnoreCase(header[j])) {
									InterfaceProperties interfaceProp = new InterfaceProperties();
									interfaceProp.setAttrId(interfaceAdditionalAttribute.getAttrId());
									interfaceProp.setAttrValue(cols[j].trim());
									interfaceProp.setType(InterfaceProperties.TYPE_INTERFACE_ATTR);
									_interfacePropertyList.add(interfaceProp);
								}
							}
						}
					}
					break;
				case 61 :
					//-----------------------------------------------------------------
					// ☆  연계채널특성화컬럼 -> IIB -> 특성컬럼명#1(IIB)
					//-----------------------------------------------------------------
					if( isSeqGroup ) break;
					{
						String colNm = "특성컬럼-1번째";
						if( Util.isEmpty(cols[j]) ) {
						} else {

							for(Channel channel : checkChannelList){
								if(_channel.getChannelId().equals(channel.getChannelId())){
									for(Map chalAttr :  channel.getRelChlAttributes()){
										logger.debug(""+(String)chalAttr.get("attrNm"));
										if(((String)chalAttr.get("attrNm")).equalsIgnoreCase(header[j])) {
											InterfaceProperties interfaceProp = new InterfaceProperties();
											interfaceProp.setAttrId((String)chalAttr.get("attrId"));
											interfaceProp.setAttrValue(cols[j].trim());
											logger.debug(""+interfaceProp.toString());
											logger.debug(""+interfaceProp.toString());
											_interfacePropertyList.add(interfaceProp);
										}
									}
								}
							}
						}
					}
					break;
				case 62 :
					//-----------------------------------------------------------------
					// ☆  연계채널특성화컬럼 -> IIB -> 특성컬럼명#2(IIB)
					//-----------------------------------------------------------------
					if( isSeqGroup ) break;
					{
						String colNm = "특성컬럼-2번째";
						if( Util.isEmpty(cols[j]) ) {
						} else {

							for(Channel channel : checkChannelList){
								if(_channel.getChannelId().equals(channel.getChannelId())){
									for(Map chalAttr :  channel.getRelChlAttributes()){
										logger.debug(""+(String)chalAttr.get("attrNm"));
										if(((String)chalAttr.get("attrNm")).equalsIgnoreCase(header[j])) {
											InterfaceProperties interfaceProp = new InterfaceProperties();
											interfaceProp.setAttrId((String)chalAttr.get("attrId"));
											interfaceProp.setAttrValue(cols[j].trim());
											logger.debug(""+interfaceProp.toString());
											logger.debug(""+interfaceProp.toString());
											_interfacePropertyList.add(interfaceProp);
										}
									}
								}
							}
						}
					}
					break;
				case 63 :
					//-----------------------------------------------------------------
					// ☆ 연계채널특성화컬럼 -> IIB -> 특성컬럼명#3(IIB)
					//-----------------------------------------------------------------
					if( isSeqGroup ) break;
					{
						String colNm = "특성컬럼-2번째";
						if( Util.isEmpty(cols[j]) ) {
						} else {

							for(Channel channel : checkChannelList){
								if(_channel.getChannelId().equals(channel.getChannelId())){
									for(Map chalAttr :  channel.getRelChlAttributes()){
										logger.debug(""+(String)chalAttr.get("attrNm"));
										if(((String)chalAttr.get("attrNm")).equalsIgnoreCase(header[j])) {
											InterfaceProperties interfaceProp = new InterfaceProperties();
											interfaceProp.setAttrId((String)chalAttr.get("attrId"));
											interfaceProp.setAttrValue(cols[j].trim());
											logger.debug(""+interfaceProp.toString());
											logger.debug(""+interfaceProp.toString());
											_interfacePropertyList.add(interfaceProp);
										}
									}
								}
							}
						}
					}
					break;
				case 64 :
					//-----------------------------------------------------------------
					// ☆ 연계채널특성화컬럼 -> MI -> 특성컬럼명#1(IIB)
					//-----------------------------------------------------------------
					if( isSeqGroup ) break;
					{
						String colNm = "특성컬럼-1번째";
						if( Util.isEmpty(cols[j]) ) {
						} else {

							for(Channel channel : checkChannelList){
								if(_channel.getChannelId().equals(channel.getChannelId())){
									for(Map chalAttr :  channel.getRelChlAttributes()){
										logger.debug(""+(String)chalAttr.get("attrNm"));
										if(((String)chalAttr.get("attrNm")).equalsIgnoreCase(header[j])) {
											InterfaceProperties interfaceProp = new InterfaceProperties();
											interfaceProp.setAttrId((String)chalAttr.get("attrId"));
											interfaceProp.setAttrValue(cols[j].trim());
											logger.debug(""+interfaceProp.toString());
											logger.debug(""+interfaceProp.toString());
											_interfacePropertyList.add(interfaceProp);
										}
									}
								}
							}
						}
					}
					break;
				case 65 :
					//-----------------------------------------------------------------
					// ☆  연계채널특성화컬럼 -> MI -> 특성컬럼명#2(IIB)
					//-----------------------------------------------------------------
					if( isSeqGroup ) break;
					{
						String colNm = "특성컬럼-2번째";
						if( Util.isEmpty(cols[j]) ) {
						} else {

							for(Channel channel : checkChannelList){
								if(_channel.getChannelId().equals(channel.getChannelId())){
									for(Map chalAttr :  channel.getRelChlAttributes()){
										logger.debug(""+(String)chalAttr.get("attrNm"));
										if(((String)chalAttr.get("attrNm")).equalsIgnoreCase(header[j])) {
											InterfaceProperties interfaceProp = new InterfaceProperties();
											interfaceProp.setAttrId((String)chalAttr.get("attrId"));
											interfaceProp.setAttrValue(cols[j].trim());
											logger.debug(""+interfaceProp.toString());
											logger.debug(""+interfaceProp.toString());
											_interfacePropertyList.add(interfaceProp);
										}
									}
								}
							}
						}
					}
					break;
				case 66 :
					//-----------------------------------------------------------------
					// ☆  연계채널특성화컬럼 -> MI -> 특성컬럼명#3(IIB)
					//-----------------------------------------------------------------
					if( isSeqGroup ) break;
					{
						String colNm = "특성컬럼-2번째";
						if( Util.isEmpty(cols[j]) ) {
						} else {

							for(Channel channel : checkChannelList){
								if(_channel.getChannelId().equals(channel.getChannelId())){
									for(Map chalAttr :  channel.getRelChlAttributes()){
										logger.debug(""+(String)chalAttr.get("attrNm"));
										if(((String)chalAttr.get("attrNm")).equalsIgnoreCase(header[j])) {
											InterfaceProperties interfaceProp = new InterfaceProperties();
											interfaceProp.setAttrId((String)chalAttr.get("attrId"));
											interfaceProp.setAttrValue(cols[j].trim());
											logger.debug(""+interfaceProp.toString());
											logger.debug(""+interfaceProp.toString());
											_interfacePropertyList.add(interfaceProp);
										}
									}
								}
							}
						}
					}
					break;

				}


			}

			{
				//-----------------------------------------------------------------
				// (1) 운영반영예정일 > 테스트예정일 > 개발예정일 체크
				//-----------------------------------------------------------------

				//-----------------------------------------------------------------
				// (2) Consumer / Provider System 체크
				//-----------------------------------------------------------------
				int providerCnt = 0;
				int consumerCnt = 0;

				String providerSystemId    = "";
				String providerService     = "";;
				String providerServiceDesc = "";

				for(pep.per.mint.common.data.basic.System sys : _systemList) {

					if( sys.getNodeType().equals("0") ) {
						consumerCnt ++;
					} else if( sys.getNodeType().equals("2") ) {
						providerCnt ++;

						//-----------------------------------------------------------------
						// providerSystem 이 멀티로 입력이 가능하나, 삼성전기는 단일 Provider 만 존재
						// 한다고 하며, 단일 Provider 기준으로 중복 체크를 한다.
						//-----------------------------------------------------------------
						providerSystemId    = sys.getSystemId();
						providerService     = sys.getService();
						providerServiceDesc = sys.getServiceDesc();
					}
				}
				//System.out.println("consumerCnt=" + consumerCnt + " providerCnt=" + providerCnt );
				//-----------------------------------------------------------------
				// consumer : provider = 1 : N (O)
				// consumer : provider = N : 1 (O)
				// consumer : provider = N : M (X)
				//-----------------------------------------------------------------
				if( providerCnt > 1 && consumerCnt > 1 ) {
					throw new Exception("[ 엑셀 업로드 실패 ] : " + (i+1) +" 번째 행, Consumer / Provider 시스템 관계는 1:1, 1:N, N:1 만 가능합니다.");
				}

				//-----------------------------------------------------------------
				// (3) 인터페이스 중복 체크(어떻게 할 것인지 추후 논의)
				//-----------------------------------------------------------------
				/*
						Map<Object,Object> interfaceCheckMap = new HashMap<Object, Object>();
						String dupProviderInfoCheckString = Util.join("2", _channel.getChannelId(), providerSystemId, providerService);

						interfaceCheckMap.put("nodeType", "2");
						interfaceCheckMap.put("channelId",_channel.getChannelId());
						interfaceCheckMap.put("systemId",providerSystemId);
						interfaceCheckMap.put("service",providerService);
						//interfaceCheckMap.put("serviceDesc",providerServiceDesc);
						String interfaceId = commonService.existInterface(interfaceCheckMap);

						if( !Util.isEmpty(interfaceId) ) {
							throw new Exception("[ 엑셀 업로드 실패 ] : \n" + (i+1) +" 번째 행, [인터페이스 중복] Provider 시스템과 서비스가 동일한 인터페이스가 이미 존재합니다.");
						}
				 */

			}

			if( !_requirementList.contains(_requirement) ) {
				_requirementList.add(_requirement);
			}


			//if( ! isSeqGroup ) {
			//	_requirementList.add(_requirement);
			//}

		}//end for(row)

		return _requirementList;

	}

	private void parsingUserInfo(List<RelUser> relUserList, String colsStr, Map<String,Object> params) throws Exception {

		if( colsStr.trim().indexOf(ROW_DELIMITER) == -1 ) {
			//-----------------------------------------------------------------
			// 싱글 데이터
			//-----------------------------------------------------------------

			if( colsStr.trim().indexOf(COL_DELIMITER) == -1 ) {
				//-----------------------------------------------------------------
				// 시스템정보(X),사용자정보(O)
				//-----------------------------------------------------------------
				validateUserInfo(relUserList, null, colsStr.trim(), params);
			} else {
				//-----------------------------------------------------------------
				// 시스템정보(O),사용자정보(O)
				//-----------------------------------------------------------------
				String [] colList = colsStr.trim().split(COL_DELIMITER);
				if( colList.length != 2 ) {
					//-----------------------------------------------------------------
					// 시스템정보(O),사용자정보(O) 외 파라메터 존재시 에러
					//-----------------------------------------------------------------
					throw new Exception("[ 엑셀 업로드 실패 ] : 값이 올바르지 않습니다.");
				} else {
					validateUserInfo(relUserList, colList[0], colList[1], params);
				}
			}

		} else {
			//-----------------------------------------------------------------
			// 멀티 데이터
			//-----------------------------------------------------------------
			String [] rowList = colsStr.trim().split(ROW_DELIMITER);

			for(String row : rowList) {
				if( row.indexOf(COL_DELIMITER) == -1 ) {
					//-----------------------------------------------------------------
					// 시스템정보(X),사용자정보(O)
					//-----------------------------------------------------------------
					validateUserInfo(relUserList, null, row, params);
				} else {
					//-----------------------------------------------------------------
					// 시스템정보(O),사용자정보(O)
					//-----------------------------------------------------------------
					String [] colList = row.split(COL_DELIMITER);
					if( colList.length != 2 ) {
						//-----------------------------------------------------------------
						// 시스템정보(O),사용자정보(O) 외 파라메터 존재시 에러
						//-----------------------------------------------------------------
						throw new Exception("[ 엑셀 업로드 실패 ] : 값이 올바르지 않습니다.");
					} else {
						validateUserInfo(relUserList, colList[0], colList[1], params);
					}
				} //end if
			} //end for
		} //end if
	}

	private void validateUserInfo(List<RelUser> relUserList, String systemKey, String userKey, Map<String,Object> params) throws Exception{

		List<pep.per.mint.common.data.basic.System> checkSystemCdList = (List<pep.per.mint.common.data.basic.System>) params.get("checkSystemCdList");
		//-----------------------------------------------------------------
		// 사용자 정보를 체크한다.
		//-----------------------------------------------------------------
		User checkUser = null;
		try {
			checkUser = commonService.getUser(userKey);
		} catch(Exception e) {
			throw new Exception("ExcelUploadController.validateUserInfo() :: 사용자 정보가 올바르지 않습니다.");
		}

		if( checkUser != null && !Util.isEmpty(checkUser.getUserId()) ) {
			RelUser relUser = new RelUser();
			relUser.setUser(checkUser);
			relUser.setRoleType(params.get("roleType").toString());
			relUser.setRoleTypeNm(params.get("roleTypeNm").toString());
			relUser.setSeq(relUserList.size());
			relUser.setDelYn("N");
			relUser.setRegDate(params.get("regDate").toString());
			relUser.setRegId(params.get("userId").toString());

			if( Util.isEmpty(systemKey) ) {
				relUser.setSystemId("");
				relUser.setSystemCd("");
				relUser.setSystemNm("");
			} else {
				boolean matchFlag  = false;

				for(pep.per.mint.common.data.basic.System system : checkSystemCdList) {
					if(system.getSystemCd().equalsIgnoreCase(systemKey.trim())) {
						relUser.setSystemId(system.getSystemId());
						relUser.setSystemCd(system.getSystemCd());
						relUser.setSystemNm(system.getSystemNm());
						matchFlag = true;
						break;
					}
				}

				if( !matchFlag ) {
					throw new Exception("ExcelUploadController.validateUserInfo() :: 시스템 정보가 올바르지 않습니다.");
				}
			}

			relUserList.add( relUser );

		} else {
			throw new Exception("ExcelUploadController.validateUserInfo() :: 사용자 정보가 올바르지 않습니다.");
		}

	}











	/**
	 *
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<Map<String,Object>> getExcelUploadMasterInfo(Map<String,Object> params) throws Exception {
		return excelUploadMapper.getExcelUploadMasterInfo(params);
	}

	/**
	 *
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<Map<String,Object>> getExcelUploadDetailInfo(Map<String,Object> params) throws Exception {
		return excelUploadMapper.getExcelUploadDetailInfo(params);
	}

	/**
	 *
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@Override
	@Transactional
	public int insertExcelUpload(Map<String, Object> master, List<String[]> detail) throws Exception {
		StringBuffer resultMsg = new StringBuffer();
		int resultCd = 0;
		try{

			if(logger.isDebugEnabled())
			{
				resultMsg = new StringBuffer();
				LogUtil.bar2(LogUtil.prefix(resultMsg, "ExcelUpload-DataBase저장 : ExcelUploadService.insertExcelUpload 처리시작"));
				try{ LogUtil.prefix(resultMsg, "params:",Util.toJSONString(master)); LogUtil.postbar(resultMsg);}catch(Exception e){}
			}

			//----------------------------------------------------------
			// 1.TAN0226 Table Insert
			//----------------------------------------------------------
			{
				if(logger.isDebugEnabled()) LogUtil.prefix(resultMsg,"Master Table(TAN0226) Insert :");
				resultCd = excelUploadMapper.insertExcelUploadMasterInfo(master);
				if(resultCd != 1) {
					if(logger.isDebugEnabled()) LogUtil.prefix(resultMsg,"ExcelUpload-DataBase저장 실패 : ExcelUploadService.insertExcelUpload.insertExcelUploadMaster:result:",resultCd);
					throw new Exception(Util.join("Exception:ExcelUploadService.insertExcelUpload:excelUploadMapper.insertExcelUploadMaster:resultCd:(", resultCd, ")"));
				}
				if(logger.isDebugEnabled()) LogUtil.prefix(resultMsg,"ExcelUpload-DataBase저장 OK:batchId:",master.get("batchId"));
			}

			//----------------------------------------------------------
			// 2.TAN0227 Table insert
			//----------------------------------------------------------
			{
				if(logger.isDebugEnabled()) LogUtil.prefix(resultMsg,"Detail Table(TAN0227) Insert :");

				int rowIndex = 1;
				for(String[] cols : detail) {
					Map<String,Object> detailMap = new HashMap<String,Object>();

					detailMap.put("batchId", master.get("batchId"));
					detailMap.put("rowIndex", Integer.valueOf(rowIndex++));
					detailMap.put("requirementId", "");
					detailMap.put("interfaceId", "");
					detailMap.put("resultCd", "0");
					detailMap.put("resultMsg", "");
					for(int i = 0; i < cols.length; i++ ) {
						detailMap.put("col" + i, cols[i]);
					}
					resultCd = excelUploadMapper.insertExcelUploadDetailInfo(detailMap);
					if(resultCd != 1) {
						if(logger.isDebugEnabled()) LogUtil.prefix(resultMsg,"ExcelUpload-DataBase저장 실패 : ExcelUploadService.insertExcelUpload.insertExcelUploadDetail:result:",resultCd);
						throw new Exception(Util.join("Exception:ExcelUploadService.insertExcelUpload:excelUploadMapper.insertExcelUploadDetail:resultCd:(", resultCd, ")"));
					}
				}

			}
			return resultCd;
		}finally{
			if(logger.isDebugEnabled()) {
				resultMsg.append(LogUtil.bar(LogUtil.prefix("ExcelUpload-DataBase저장 : ExcelUploadService.insertExcelUpload 처리종료")));
				logger.debug(resultMsg.toString());
			}
		}

	}

	/**
	 *
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@Override
	@Transactional
	public int createRequirement(List<Requirement> requirementList, Map<String,Object> params) throws Exception {

		int resultCd = 0;
		String seq = "";
		try{

			for(Requirement requirement : requirementList) {

				{
					seq = requirement.getRequirementId();
					params.put("batchId", params.get("batchId"));
					params.put("seq", seq);
				}

				resultCd = requirementService.createRequirement(requirement);
				if( resultCd == 1 ) {
					// 데이타 액세스 권환 추가 서비스 CALL
					Map param1 = new HashMap<String, Object>();
					List paramList = new ArrayList();
					int i=0;
					for(DataAccessRole accesRole : requirement.getInterfaceInfo().getDataAccessRoleList()){
						Map subParam = new HashMap();
						subParam.put("roleId", accesRole.getRoleId());
						subParam.put("interfaceId",requirement.getInterfaceInfo().getInterfaceId());
						subParam.put("seq", i++);
						paramList.add(subParam);
					}
					param1.put("list", paramList);

					dataAccessRoleService.createDataAccessRoleInterface(param1,   requirement.getInterfaceInfo().getInterfaceId());
					//----------------------------------------------------------
					// 1.TAN0226 Table Update
					//----------------------------------------------------------
					params.put("resultCd", "2");
					params.put("resultMsg", "");
					//----------------------------------------------------------
					// 2.TAN0227 Table Update
					//----------------------------------------------------------
					params.put("requirementId", requirement.getRequirementId());
					params.put("interfaceId", requirement.getInterfaceInfo().getInterfaceId());
				} else {
					//----------------------------------------------------------
					// 1.TAN0226 Table Update
					//----------------------------------------------------------
					params.put("resultCd", "-2");
					params.put("resultMsg", "요건/인터페이스 생성중 실패:그룹번호(" + seq  + "):result:(" + resultCd + ")");
					//----------------------------------------------------------
					// 2.TAN0227 Table Update
					//----------------------------------------------------------
					params.put("requirementId", "");
					params.put("interfaceId", "");
				}

				excelUploadMapper.updateExcelUploadMasterInfoStatus(params);
				excelUploadMapper.updateExcelUploadDetailInfo(params);

			}

		} catch(Exception e){
			//----------------------------------------------------------
			// 1.TAN0226 Table Update
			//----------------------------------------------------------
			params.put("resultCd", "-2");
			params.put("resultMsg", "요건/인터페이스 생성중 실패:그룹번호(" + seq  + "):result:(" + e.getMessage() + ")");
			//----------------------------------------------------------
			// 2.TAN0227 Table Update
			//----------------------------------------------------------
			params.put("requirementId", "");
			params.put("interfaceId", "");

			excelUploadMapper.updateExcelUploadMasterInfoStatus(params);
			excelUploadMapper.updateExcelUploadMasterInfoStatus(params);
			excelUploadMapper.updateExcelUploadDetailInfo(params);

			logger.error(e.getMessage());
		} finally {

		}

		return resultCd;
	}


	/**
	 *
	 * @param params
	 * @return
	 * @throws Exception
	 */
	@Override
	@Transactional
	public int deleteExcelUpload(Map<String, Object> params) throws Exception {
		int resultCd = 0;

		resultCd = excelUploadMapper.deleteExcelUploadDetailInfo(params);
		resultCd = excelUploadMapper.deleteExcelUploadMasterInfo(params);

		return resultCd;
	}

	@Override
	public List<Requirement> validate(List<String[]> list, Map<String, Object> params) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}


}
