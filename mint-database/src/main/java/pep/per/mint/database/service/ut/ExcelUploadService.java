package pep.per.mint.database.service.ut;

import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pep.per.mint.common.data.basic.Business;
import pep.per.mint.common.data.basic.Channel;
import pep.per.mint.common.data.basic.CommonCode;
import pep.per.mint.common.data.basic.Interface;
import pep.per.mint.common.data.basic.InterfaceMapping;
import pep.per.mint.common.data.basic.RelUser;
import pep.per.mint.common.data.basic.Requirement;
import pep.per.mint.common.data.basic.RequirementAttatchFile;
import pep.per.mint.common.data.basic.RequirementComment;
import pep.per.mint.common.data.basic.User;
import pep.per.mint.common.util.LogUtil;
import pep.per.mint.common.util.Util;
import pep.per.mint.database.mapper.ut.ExcelUploadMapper;
import pep.per.mint.database.service.an.RequirementService;
import pep.per.mint.database.service.co.CommonService;


/**
 * @author TA
 *
 */

@Service
public class ExcelUploadService {
	
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
	ExcelUploadMapper excelUploadMapper;
	
	
	
	public List<Requirement> validate(List<String[]> list, Map<String,Object> params) throws Exception {
		
		//----------------------------------------------------------------------------
		// * 1. * 은 필수 입력 값임
		// * 2. ARIS 프로세스 없을 경우 공통으로 입력 <- 공통에 대한 필드정보 알려주면 보내드리겠습니다.
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
		
		//-----------------------------------------------------------------
		// 삼성전기전용 - default businessId
		//-----------------------------------------------------------------
		String businessId = "BZ00000001"; // 형통시연위해 임시fix시킴
		Map<String,String> searchMap = new HashMap<String, String>();
		searchMap.put("businessId", businessId);
		
		//-----------------------------------------------------------------
		// default businessId 정보가 시스템에 없으면 에러
		//-----------------------------------------------------------------
		List<Business> defaultBusinessList = commonService.getBusinessList(searchMap);
		if( defaultBusinessList == null || defaultBusinessList.size() == 0 ) {
			throw new Exception("[ 엑셀 업로드 실패 ] : ARIS 프로세스 정보 [" + businessId + "] 가 시스템에 존재하지 않습니다.");
		}		

		//-----------------------------------------------------------------
		// 검증을 위한 공통 코드 정보들 로드
		//-----------------------------------------------------------------		
		List<pep.per.mint.common.data.basic.System> checkSystemCdList = commonService.getSystemCdList();
		List<CommonCode> checkDataPrDir 		= commonService.getCommonCodeList("IM", "01");
		List<CommonCode> checkDataPrMethod 		= commonService.getCommonCodeList("IM", "12");
		List<CommonCode> checkAppPrMethod 		= commonService.getCommonCodeList("IM", "02");
		List<CommonCode> checkDataFrequency 	= commonService.getCommonCodeList("IM", "03");
		List<CommonCode> checkResourceCd 		= commonService.getCommonCodeList("IM", "04");
		List<CommonCode> checkImportance 		= commonService.getCommonCodeList("IM", "07");
		List<CommonCode> checkMaxDelay 			= commonService.getCommonCodeList("IM", "05");
		List<CommonCode> checkMaxError 			= commonService.getCommonCodeList("IM", "06");
		CommonCode checkRoleType0 				= commonService.getCommonCode("IM", "09", "0");		
		CommonCode checkRoleType1 				= commonService.getCommonCode("IM", "09", "1");
		CommonCode checkRoleType2 				= commonService.getCommonCode("IM", "09", "2");
		List<Channel> checkChannelList 			= commonService.getChannelList();
		List<CommonCode> checkChannelChangeCd 	= commonService.getCommonCodeList("AN", "06");
		
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
			//-----------------------------------------------------------------
			// 순번이 같으면 동일 인터페이스로 보고 시스템 정보만 추가하는 로직을 구현한다
			//-----------------------------------------------------------------			
			if( seq.equals(cols[0]) ) {
				isSeqGroup = true;
			} else {
				seq = cols[0];
			}
			
			Requirement _requirement = null;
			Business _business = null;
			Interface _interfacez = null;
			Channel _channel = null;
			List<pep.per.mint.common.data.basic.System> _systemList = null;
			List<Business> _businessList = null;
			List<RelUser> _relUsers = null;
			InterfaceMapping _interfaceMapping = null;
			
			pep.per.mint.common.data.basic.System _providerSystem = new pep.per.mint.common.data.basic.System();
			pep.per.mint.common.data.basic.System _consumerSystem = new pep.per.mint.common.data.basic.System();				
			
			Business _providerBusiness = new Business();
			Business _consumerBusiness = new Business();			
			
			
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
				_interfacez.setBusinessList(_businessList);
				
				// attach relUsers
				_interfacez.setRelUsers(_relUsers);
				
				// attach interfaceMapping
				_interfacez.setInterfaceMapping(_interfaceMapping);
				
			}
			
			//-----------------------------------------------------------------
			// Col Data
			//-----------------------------------------------------------------
			for( int j = 0; j < cols.length; j++ ) {
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
						// ☆ 업무정보-ARIS 프로세스						
						//-----------------------------------------------------------------
						if( isSeqGroup ) break;
						{
							if( Util.isEmpty(cols[j]) ) {
								_business.setBusinessId(defaultBusinessList.get(0).getBusinessId());
								_business.setBusinessCd(defaultBusinessList.get(0).getBusinessCd());
								_business.setBusinessNm(defaultBusinessList.get(0).getBusinessNm());
							} else {
								//-----------------------------------------------------------------
								// Business 정보는 대량 데이터 이므로 입력된 값으로 매번 디비 조회후 체크한다
								//-----------------------------------------------------------------
								searchMap.put("businessId", cols[j].trim());
								List<Business> checkBusinessList = commonService.getBusinessList(searchMap);
								if( checkBusinessList == null || checkBusinessList.size() == 0 ) {
									throw new Exception("[ 엑셀 업로드 실패 ] : ARIS 프로세스 정보 [" + cols[j].trim() + "] 가 시스템에 존재하지 않습니다.");
								} else {
									Business business = checkBusinessList.get(0);
									_business.setBusinessId(business.getBusinessId());
									_business.setBusinessCd(business.getBusinessCd());
									_business.setBusinessNm(business.getBusinessNm());
								}
							}
							
							_business.setRegId(params.get("userId").toString());
							_business.setModId(params.get("userId").toString());
							_business.setRegDate(params.get("regDate").toString());
							_business.setModDate(params.get("regDate").toString());
							
						}
						break;
					case 2 :
						//-----------------------------------------------------------------
						// ★ 업무정보-업무명     
						//-----------------------------------------------------------------
						if( isSeqGroup ) break;
						{
							String colNm = "업무정보-업무명";
							if( Util.isEmpty(cols[j]) ) {
								throw new NullPointerException("[ 엑셀 업로드 실패 ] : " + (i+1) +" 번째 행, [" + colNm + "] 값이 공백입니다.");
							} else {
								_requirement.setRequirementNm( cols[j].trim() );
							}
						}
						break;
					case 3 :
						//-----------------------------------------------------------------
						// ★ 인터페이스정보-인터페이스명
						//-----------------------------------------------------------------
						if( isSeqGroup ) break;
						{
							String colNm = "인터페이스정보-인터페이스명";
							if( Util.isEmpty(cols[j]) ) {
								throw new NullPointerException("[ 엑셀 업로드 실패 ] : " + (i+1) +" 번째 행, [" + colNm + "] 값이 공백입니다.");
							} else {
								_interfacez.setInterfaceNm( cols[j].trim() );
							}
						}
						break;
					case 4 :
						//-----------------------------------------------------------------
						// ★ 인터페이스정보-Data처리방향
						//-----------------------------------------------------------------
						if( isSeqGroup ) break;
						{
							String colNm = "인터페이스정보-Data처리방향";
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
					case 5 :
						//-----------------------------------------------------------------
						// ★ 인터페이스정보-Data처리방식
						//-----------------------------------------------------------------
						if( isSeqGroup ) break;
						{
							String colNm = "인터페이스정보-Data처리방식";
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
					case 6 :
						//-----------------------------------------------------------------
						// ★ 인터페이스정보-App처리방식
						//-----------------------------------------------------------------
						if( isSeqGroup ) break;
						{
							String colNm = "인터페이스정보-App처리방식";
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
					case 7 :
						//-----------------------------------------------------------------
						// ★ 인터페이스정보-데이터순차보장
						//-----------------------------------------------------------------
						if( isSeqGroup ) break;
						{
							String colNm = "인터페이스정보-데이터순차보장";
							if( Util.isEmpty(cols[j]) ) {
								throw new NullPointerException("[ 엑셀 업로드 실패 ] : " + (i+1) +" 번째 행, [" + colNm + "] 값이 공백입니다.");
							} else {
								if( cols[j].trim().equalsIgnoreCase("Yes") ) {
									_interfacez.setDataSeqYn("Y");
								} else if( cols[j].trim().equalsIgnoreCase("No") ) { 
									_interfacez.setDataSeqYn("N");
								} else {
									throw new Exception("[ 엑셀 업로드 실패 ] : " + (i+1) +" 번째 행, [" + colNm + "] 값이 올바르지 않습니다.");
								}
							}
						}
						break;
					case 8 :
						//-----------------------------------------------------------------
						// ★ 인터페이스정보-발생주기
						//-----------------------------------------------------------------
						if( isSeqGroup ) break;
						{
							String colNm = "인터페이스정보-발생주기";
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
					case 9 :
						//-----------------------------------------------------------------
						// ★ 인터페이스정보-발생주기상세
						//-----------------------------------------------------------------
						if( isSeqGroup ) break;
						{
							String colNm = "인터페이스정보-발생주기상세";
							if( Util.isEmpty(cols[j]) ) {
								_interfacez.setDataFrequencyInput("");
							} else {
								_interfacez.setDataFrequencyInput( cols[j].trim() );
							}
						}
						break;
					case 10 :
						//-----------------------------------------------------------------
						// ★ 인터페이스정보-건당사이즈
						//-----------------------------------------------------------------
						if( isSeqGroup ) break;
						{
							String colNm = "인터페이스정보-건당사이즈";
							if( Util.isEmpty(cols[j]) ) {
								_interfacez.setSizePerTran( 0 );
							} else {
								if( Util.isNumber( cols[j].trim() ) ) {
									_interfacez.setSizePerTran( Long.parseLong( cols[j].trim() ) );	
								} else {
									throw new NumberFormatException("[ 엑셀 업로드 실패 ] : " + (i+1) +" 번째 행, [" + colNm + "] 값이 올바르지 않습니다.");
								}
							}
						}
						break;
					case 11 :
						//-----------------------------------------------------------------
						// ★ 인터페이스정보-주기별건수
						//-----------------------------------------------------------------
						if( isSeqGroup ) break;
						{
							String colNm = "인터페이스정보-주기별건수";
							if( Util.isEmpty(cols[j]) ) {
								_interfacez.setCntPerFrequency( 0 );
							} else {
								if( Util.isNumber( cols[j].trim() ) ) {
									_interfacez.setCntPerFrequency( Integer.parseInt( cols[j].trim() ) );	
								} else {
									throw new NumberFormatException("[ 엑셀 업로드 실패 ] : " + (i+1) +" 번째 행, [" + colNm + "] 값이 올바르지 않습니다.");
								}
							}
						}
						break;
					case 12 :
						//-----------------------------------------------------------------
						// ★ 인터페이스정보-일일발생횟수
						//-----------------------------------------------------------------
						if( isSeqGroup ) break;
						{
							String colNm = "인터페이스정보-일일발생횟수";
							if( Util.isEmpty(cols[j]) ) {
								_interfacez.setCntPerDay( 0 );
							} else {
								if( Util.isNumber( cols[j].trim() ) ) {
									_interfacez.setCntPerDay( Integer.parseInt( cols[j].trim() ) );	
								} else {
									throw new NumberFormatException("[ 엑셀 업로드 실패 ] : " + (i+1) +" 번째 행, [" + colNm + "] 값이 올바르지 않습니다.");
								}
							}
						}
						break;
					case 13 :
						//-----------------------------------------------------------------
						// ☆ Provider-시스템명
						//-----------------------------------------------------------------
						{
							String colNm = "Provider-시스템명";
							if( Util.isEmpty(cols[j]) ) {
								//TODO : nothing
							} else {						
								//TODO : nothing
							}
						}
						break;
					case 14 :
						//-----------------------------------------------------------------
						// ★ Provider-시스템코드
						//-----------------------------------------------------------------
						{
							String colNm = "Provider-시스템코드";
							if( Util.isEmpty(cols[j]) ) {
								if( isSeqGroup ) break;
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
					case 15 :
						//-----------------------------------------------------------------
						// ★ Provider-리소스
						//-----------------------------------------------------------------
						{
							String colNm = "Provider-리소스";
							if( Util.isEmpty(cols[j]) ) {
								if( isSeqGroup ) break;
								throw new NullPointerException("[ 엑셀 업로드 실패 ] : " + (i+1) +" 번째 행, [" + colNm + "] 값이 공백입니다.");
							} else {
								
								String cd = "";
								String nm = "";
								
								boolean matchFlag = false;
								for(CommonCode commonCode : checkResourceCd) {
									if(commonCode.getNm().equalsIgnoreCase(cols[j].trim())) {
										cd = commonCode.getCd();
										nm = commonCode.getNm();
										matchFlag = true;
										break;
									}
								}
								
								if( matchFlag ) {
									_providerSystem.setResourceCd( cd );
									_providerSystem.setResourceNm( nm );
								} else {
									throw new Exception("[ 엑셀 업로드 실패 ] : " + (i+1) +" 번째 행, [" + colNm + "] 값이 올바르지 않습니다.");
								}								

							}
						}
						break;
					case 16 :
						//-----------------------------------------------------------------
						// ★ Provider-서비스
						//-----------------------------------------------------------------
						{
							String colNm = "Provider-서비스";
							if( Util.isEmpty(cols[j]) ) {
								if( isSeqGroup ) break;
								throw new NullPointerException("[ 엑셀 업로드 실패 ] : " + (i+1) +" 번째 행, [" + colNm + "] 값이 공백입니다.");
							} else {						
								_providerSystem.setService( cols[j].trim() );
							}
						}
						break;
					case 17 :
						//-----------------------------------------------------------------
						// ★ Provider-서비스명
						//-----------------------------------------------------------------
						{
							String colNm = "Provider-서비스명";
							if( Util.isEmpty(cols[j]) ) {
								if( isSeqGroup ) break;
								throw new NullPointerException("[ 엑셀 업로드 실패 ] : " + (i+1) +" 번째 행, [" + colNm + "] 값이 공백입니다.");
							} else {						
								_providerSystem.setServiceDesc( cols[j].trim() );
							}
						}
						break;
					case 18 :
						//-----------------------------------------------------------------
						// ☆ Provider-ARIS 프로세스
						//-----------------------------------------------------------------
						{
							
							if( Util.isEmpty(cols[j]) ) {
								if( isSeqGroup ) break;
								_providerBusiness.setBusinessId(defaultBusinessList.get(0).getBusinessId());
								_providerBusiness.setBusinessCd(defaultBusinessList.get(0).getBusinessCd());
								_providerBusiness.setBusinessNm(defaultBusinessList.get(0).getBusinessNm());
							} else {
								//-----------------------------------------------------------------
								// Business 정보는 대량 데이터 이므로 입력된 값으로 매번 디비 조회후 체크한다
								//-----------------------------------------------------------------
								searchMap.put("businessId", cols[j].trim());
								List<Business> checkBusinessList = commonService.getBusinessList(searchMap);
								if( checkBusinessList == null || checkBusinessList.size() == 0 ) {
									throw new Exception("[ 엑셀 업로드 실패 ] : ARIS 프로세스 정보 [" + cols[j].trim() + "] 가 시스템에 존재하지 않습니다.");
								} else {
									Business business = checkBusinessList.get(0);
									_providerBusiness.setBusinessId(business.getBusinessId());
									_providerBusiness.setBusinessCd(business.getBusinessCd());
									_providerBusiness.setBusinessNm(business.getBusinessNm());
								}
							}
							
							_providerBusiness.setSeq(_businessList.size());
							_providerBusiness.setNodeType("2");
							_providerBusiness.setDelYn("N");
							_providerBusiness.setRegId(params.get("userId").toString());
							_providerBusiness.setModId(params.get("userId").toString());
							_providerBusiness.setRegDate(params.get("regDate").toString());
							_providerBusiness.setModDate(params.get("regDate").toString());
							
							_businessList.add(_providerBusiness);
						}
						break;
					case 19 :
						//-----------------------------------------------------------------
						// ☆ Consumer-시스템명
						//-----------------------------------------------------------------
						{
							String colNm = "Consumer-시스템명";
							if( Util.isEmpty(cols[j]) ) {
								
							} else {						
								
							}
						}
						break;
					case 20 :
						//-----------------------------------------------------------------
						// ★ Consumer-시스템코드
						//-----------------------------------------------------------------
						{
							String colNm = "Consumer-시스템코드";
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
					case 21 :
						//-----------------------------------------------------------------
						// ★ Consumer-리소스
						//-----------------------------------------------------------------
						{
							String colNm = "Consumer-리소스";
							if( Util.isEmpty(cols[j]) ) {
								throw new NullPointerException("[ 엑셀 업로드 실패 ] : " + (i+1) +" 번째 행, [" + colNm + "] 값이 공백입니다.");
							} else {
								
								String cd = "";
								String nm = "";
								
								boolean matchFlag = false;
								for(CommonCode commonCode : checkResourceCd) {
									if(commonCode.getNm().equalsIgnoreCase(cols[j].trim())) {
										cd = commonCode.getCd();
										nm = commonCode.getNm();
										matchFlag = true;
										break;
									}
								}
								
								if( matchFlag ) {
									_consumerSystem.setResourceCd( cd );
									_consumerSystem.setResourceNm( nm );
								} else {
									throw new Exception("[ 엑셀 업로드 실패 ] : " + (i+1) +" 번째 행, [" + colNm + "] 값이 올바르지 않습니다.");
								}								
							}
						}
						break;
					case 22 :
						//-----------------------------------------------------------------
						// ★ Consumer-서비스
						//-----------------------------------------------------------------
						{
							String colNm = "Consumer-서비스";
							if( Util.isEmpty(cols[j]) ) {
								throw new NullPointerException("[ 엑셀 업로드 실패 ] : " + (i+1) +" 번째 행, [" + colNm + "] 값이 공백입니다.");
							} else {						
								_consumerSystem.setService( cols[j].trim() );
							}
						}
						break;
					case 23 :
						//-----------------------------------------------------------------
						// ★ Consumer-서비스명
						//-----------------------------------------------------------------
						{
							String colNm = "Consumer-서비스명";
							if( Util.isEmpty(cols[j]) ) {
								throw new NullPointerException("[ 엑셀 업로드 실패 ] : " + (i+1) +" 번째 행, [" + colNm + "] 값이 공백입니다.");
							} else {						
								_consumerSystem.setServiceDesc( cols[j].trim() );
							}
						}
						break;
					case 24 :
						//-----------------------------------------------------------------
						// ☆ Consumer-ARIS 프로세스
						//-----------------------------------------------------------------
						{
							
							if( Util.isEmpty(cols[j]) ) {
								_consumerBusiness.setBusinessId(defaultBusinessList.get(0).getBusinessId());
								_consumerBusiness.setBusinessCd(defaultBusinessList.get(0).getBusinessCd());
								_consumerBusiness.setBusinessNm(defaultBusinessList.get(0).getBusinessNm());
							} else {
								//-----------------------------------------------------------------
								// Business 정보는 대량 데이터 이므로 입력된 값으로 매번 디비 조회후 체크한다
								//-----------------------------------------------------------------
								searchMap.put("businessId", cols[j].trim());
								List<Business> checkBusinessList = commonService.getBusinessList(searchMap);
								if( checkBusinessList == null || checkBusinessList.size() == 0 ) {
									throw new Exception("[ 엑셀 업로드 실패 ] : ARIS 프로세스 정보 [" + cols[j].trim() + "] 가 시스템에 존재하지 않습니다.");
								} else {
									Business business = checkBusinessList.get(0);
									_consumerBusiness.setBusinessId(business.getBusinessId());
									_consumerBusiness.setBusinessCd(business.getBusinessCd());
									_consumerBusiness.setBusinessNm(business.getBusinessNm());
								}
							}
							
							_consumerBusiness.setSeq(_businessList.size());
							_consumerBusiness.setNodeType("0");
							_consumerBusiness.setDelYn("N");
							_consumerBusiness.setRegId(params.get("userId").toString());
							_consumerBusiness.setModId(params.get("userId").toString());
							_consumerBusiness.setRegDate(params.get("regDate").toString());
							_consumerBusiness.setModDate(params.get("regDate").toString());
							
							_businessList.add(_consumerBusiness);
							
						}
						break;
					case 25 :
						//-----------------------------------------------------------------
						// ☆ 장애영향도-중요도
						//-----------------------------------------------------------------
						if( isSeqGroup ) break;
						{
							String colNm = "장애영향도-중요도";
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
					case 26 :
						//-----------------------------------------------------------------
						// ☆ 장애영향도-지연임계치
						//-----------------------------------------------------------------
						if( isSeqGroup ) break;
						{
							String colNm = "장애영향도-지연임계치";
							if( Util.isEmpty(cols[j]) ) {
								_interfacez.setMaxDelayTime( "1" );
								_interfacez.setMaxDelayUnit( "1" );
								_interfacez.setMaxDelayUnitNm( "분" );								
							} else {
								
								//값, 단위를 분리한다.
								String value = cols[j].trim().substring(0, cols[j].trim().length()-1);
								String unit  = cols[j].trim().substring(cols[j].trim().length()-1);
								
								String cd = "";
								
								boolean matchFlag = false;
								for(CommonCode commonCode : checkMaxDelay) {
									if(commonCode.getNm().equals(unit)) {
										cd = commonCode.getCd();
										matchFlag = true;
										break;
									}
								}
								
								if( matchFlag ) {
									if( Util.isNumber(value) ) {
										_interfacez.setMaxDelayTime( value );
										_interfacez.setMaxDelayUnit( cd );
										_interfacez.setMaxDelayUnitNm( unit );										
									} else {
										throw new NumberFormatException("[ 엑셀 업로드 실패 ] : " + (i+1) +" 번째 행, [" + colNm + "] 값이 올바르지 않습니다.");
									}
								} else {
									throw new Exception("[ 엑셀 업로드 실패 ] : " + (i+1) +" 번째 행, [" + colNm + "] 값이 올바르지 않습니다.");
								}								
							}
						}						
						break;
					case 27 :
						//-----------------------------------------------------------------
						// ☆ 장애영향도-장애임계치
						//-----------------------------------------------------------------
						if( isSeqGroup ) break;
						{
							String colNm = "장애영향도-장애임계치";
							if( Util.isEmpty(cols[j]) ) {
								_interfacez.setMaxErrorTime( "1" );
								_interfacez.setMaxErrorUnit( "1" );
								_interfacez.setMaxErrorUnitNm( "시" );								
							} else {
								
								//값, 단위를 분리한다.
								String value = cols[j].trim().substring(0, cols[j].trim().length()-1);
								String unit  = cols[j].trim().substring(cols[j].trim().length()-1);
								
								String cd = "";
								
								boolean matchFlag = false;
								for(CommonCode commonCode : checkMaxError) {
									if(commonCode.getNm().equals(unit)) {
										cd = commonCode.getCd();
										matchFlag = true;
										break;
									}
								}
								
								if( matchFlag ) {
									if( Util.isNumber(value) ) {
										_interfacez.setMaxErrorTime( value );
										_interfacez.setMaxErrorUnit( cd );
										_interfacez.setMaxErrorUnitNm( unit );										
									} else {
										throw new NumberFormatException("[ 엑셀 업로드 실패 ] : " + (i+1) +" 번째 행, [" + colNm + "] 값이 올바르지 않습니다.");
									}
								} else {
									throw new Exception("[ 엑셀 업로드 실패 ] : " + (i+1) +" 번째 행, [" + colNm + "] 값이 올바르지 않습니다.");
								}								
							}
						}						
						break;
					case 28 :
						//-----------------------------------------------------------------
						// ★ 스케줄-개발예정일
						//-----------------------------------------------------------------
						if( isSeqGroup ) break;
						{
							String colNm = "스케줄-개발예정일";
							if( Util.isEmpty(cols[j]) ) {
								throw new NullPointerException("[ 엑셀 업로드 실패 ] : " + (i+1) +" 번째 행, [" + colNm + "] 값이 공백입니다.");
							} else {						
								if( Util.isNumber(cols[j].trim()) && ( cols[j].trim().length() == 8 ) ) {
									_requirement.setDevExpYmd( cols[j].trim() );
								} else {
									throw new Exception("[ 엑셀 업로드 실패 ] : " + (i+1) +" 번째 행, [" + colNm + "] 값이 올바르지 않습니다.");
								}
							}
						}	
						break;
					case 29 :
						//-----------------------------------------------------------------
						// ★ 스케줄-테스트예정일
						//-----------------------------------------------------------------
						if( isSeqGroup ) break;
						{
							String colNm = "스케줄-테스트예정일";
							if( Util.isEmpty(cols[j]) ) {
								throw new NullPointerException("[ 엑셀 업로드 실패 ] : " + (i+1) +" 번째 행, [" + colNm + "] 값이 공백입니다.");
							} else {
								if( Util.isNumber(cols[j].trim()) && ( cols[j].trim().length() == 8 ) ) {
									_requirement.setTestExpYmd( cols[j].trim() );	
								} else {
									throw new Exception("[ 엑셀 업로드 실패 ] : " + (i+1) +" 번째 행, [" + colNm + "] 값이 올바르지 않습니다.");
								}
							}
						}
						break;
					case 30 :
						//-----------------------------------------------------------------
						// ★ 스케줄-운영반영예정일
						//-----------------------------------------------------------------
						if( isSeqGroup ) break;
						{
							String colNm = "스케줄-운영반영예정일";
							if( Util.isEmpty(cols[j]) ) {
								throw new NullPointerException("[ 엑셀 업로드 실패 ] : " + (i+1) +" 번째 행, [" + colNm + "] 값이 공백입니다.");
							} else {
								if( Util.isNumber(cols[j].trim()) && ( cols[j].trim().length() == 8 ) ) {
									_requirement.setRealExpYmd( cols[j].trim() );	
								} else {
									throw new Exception("[ 엑셀 업로드 실패 ] : " + (i+1) +" 번째 행, [" + colNm + "] 값이 올바르지 않습니다.");
								}
							}
						}
						break;
					case 31 :
						//-----------------------------------------------------------------
						// ☆ 담당자-연계
						//-----------------------------------------------------------------
						if( isSeqGroup ) break;
						{
							String colNm = "담당자-연계";
							if( Util.isEmpty(cols[j]) ) {
								//TODO : nothing
							} else {
								params.put("roleType", checkRoleType1.getCd());
								params.put("roleTypeNm", checkRoleType1.getNm());
								
								try {
									parsingUserInfo(_relUsers, cols[j], params);
								} catch( Exception e ) {
									throw new Exception("[ 엑셀 업로드 실패 ] : " + (i+1) +" 번째 행, [" + colNm + "] 값이 올바르지 않습니다.");
								}	
							}
						}
						break;
					case 32 :
						//-----------------------------------------------------------------
						// ★ 담당자-Provider
						//-----------------------------------------------------------------
						if( isSeqGroup ) break;
						{
							String colNm = "담당자-Provider";
							if( Util.isEmpty(cols[j]) ) {
								throw new NullPointerException("[ 엑셀 업로드 실패 ] : " + (i+1) +" 번째 행, [" + colNm + "] 값이 공백입니다.");
							} else {
								params.put("roleType", checkRoleType2.getCd());
								params.put("roleTypeNm", checkRoleType2.getNm());
								
								try {
									parsingUserInfo(_relUsers, cols[j], params);
								} catch( Exception e ) {
									throw new Exception("[ 엑셀 업로드 실패 ] : " + (i+1) +" 번째 행, [" + colNm + "] 값이 올바르지 않습니다.", e);
								}							
							}
						}
						break;
					case 33 :
						//-----------------------------------------------------------------
						// ★ 담당자-Consumer
						//-----------------------------------------------------------------
						if( isSeqGroup ) break;
						{
							String colNm = "담당자-Consumer";
							if( Util.isEmpty(cols[j]) ) {
								throw new NullPointerException("[ 엑셀 업로드 실패 ] : " + (i+1) +" 번째 행, [" + colNm + "] 값이 공백입니다.");
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
					case 34 :
						//-----------------------------------------------------------------
						// ★ 솔루션-전사솔루션
						//-----------------------------------------------------------------
						if( isSeqGroup ) break;
						{
							String colNm = "솔루션-전사솔루션";
							if( Util.isEmpty(cols[j]) ) {
								throw new NullPointerException("[ 엑셀 업로드 실패 ] : " + (i+1) +" 번째 행, [" + colNm + "] 값이 공백입니다.");
							} else {
							
								boolean matchFlag = false;
								Channel matchChannel = null;
								for(Channel ch : checkChannelList) {
									if(ch.getChannelNm().equals(cols[j].trim())) {
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
								
								
								//-----------------------------------------------------------------
								// 전사솔루션 담당자 추가
								//-----------------------------------------------------------------
								{
									params.put("roleType", checkRoleType1.getCd());
									params.put("roleTypeNm", checkRoleType1.getNm());
									
									boolean isExist = false;
									List<RelUser> chRelUserList = matchChannel.getRelUsers();
									for(RelUser chRelUser : chRelUserList) {
										for(RelUser relUser : _relUsers) {
											if(chRelUser.getUser().getUserId().equals(relUser.getUser().getUserId()) && relUser.getRoleType().equals("1") ) {
												isExist = true;
												break;
											}
										}
										if( !isExist ) {
											try {
												validateUserInfo(_relUsers, null, chRelUser.getUser().getUserId(), params);
											} catch( Exception e ) {
												
											}
										}
									}
								}
							}
						}
						break;
					case 35 :
						//-----------------------------------------------------------------
						// ☆ 솔루션-변경사유
						//-----------------------------------------------------------------
						if( isSeqGroup ) break;
						{
							String colNm = "솔루션-변경사유";
							if( Util.isEmpty(cols[j]) ) {
								_interfacez.setChannelChangeCd("");
								_interfacez.setChannelChangeNm("");								
							} else {			
								
								String cd = "";
								String nm = "";
								
								boolean matchFlag = false;
								for(CommonCode commonCode : checkChannelChangeCd) {
									if(commonCode.getNm().equals(cols[j].trim())) {
										cd = commonCode.getCd();
										nm = commonCode.getNm();
										matchFlag = true;
										break;
									}
								}
								
								if( matchFlag ) {
									_interfacez.setChannelChangeCd( cd );
									_interfacez.setChannelChangeNm( nm );
								} else {
									throw new Exception("[ 엑셀 업로드 실패 ] : " + (i+1) +" 번째 행, [" + colNm + "] 값이 올바르지 않습니다.");
								}									

							}
						}
						break;
					case 36 :
						//-----------------------------------------------------------------
						// ☆ 솔루션-변경사유상세
						//-----------------------------------------------------------------
						if( isSeqGroup ) break;
						{
							String colNm = "솔루션-변경사유상세";
							if( Util.isEmpty(cols[j]) ) {
								_interfacez.setChannelChangeComments("");
							} else {						
								_interfacez.setChannelChangeComments( cols[j].trim() );
							}
						}
						break;
					case 37 :
						//-----------------------------------------------------------------
						// ☆ 솔루션-맵핑키
						//-----------------------------------------------------------------
						if( isSeqGroup ) break;
						{
							String colNm = "솔루션-맵핑키";
							if( Util.isEmpty(cols[j]) ) {
								//TODO : nothing
							} else {
								String channelId = _interfacez.getChannel().getChannelId();
								
								if( !Util.isEmpty(channelId) && !channelId.equals("CN00000001") ) {
									
									String asisInterfaceId = "";
									
									boolean matchFlag = false;
									List<Map> asisInterfaceList = commonService.getAsisInterfaceList(channelId);
									for(Map asisInterface : asisInterfaceList) {
										if(asisInterface.get("asisInterfaceId").equals(cols[j].trim())) {
											asisInterfaceId = asisInterface.get("asisInterfaceId").toString();
											matchFlag = true;
											break;
										}
									}
									
									if( matchFlag ) {
										_interfaceMapping.setAsisInterfaceId( asisInterfaceId );
										_interfaceMapping.setChannelId(channelId);
									} else {
										throw new Exception("[ 엑셀 업로드 실패 ] : " + (i+1) +" 번째 행, [" + colNm + "] 값이 올바르지 않습니다.");
									}										

								}
							}
						}
						break;
					case 38 :
						//-----------------------------------------------------------------
						// ☆ 부가정보-Comment
						//-----------------------------------------------------------------
						if( isSeqGroup ) break;
						{
							if( Util.isEmpty(cols[j]) ) {
								_requirement.setCommentList(null);
							} else {						
								List<RequirementComment> commentList = new LinkedList<RequirementComment>();
								RequirementComment comment = new RequirementComment();
								
								comment.setCommentId("0");
								comment.setComment( cols[j].trim() );
								comment.setRegId(params.get("userId").toString());
								comment.setModId(params.get("userId").toString());
								comment.setRegDate(params.get("regDate").toString());
								comment.setModDate(params.get("regDate").toString());
								
								commentList.add( comment );
								
								_requirement.setCommentList(commentList);
							}
						}
						break;
					case 39 :
						//-----------------------------------------------------------------
						// ☆ 부가정보-Description
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
					case 40 :
						//-----------------------------------------------------------------
						// ☆ 부가정보-인터페이스 ID(형통버전)
						//-----------------------------------------------------------------
						{
							String colNm = "부가정보-인터페이스 ID";
							if( Util.isEmpty(cols[j]) ) {
								throw new NullPointerException("[ 엑셀 업로드 실패 ] : " + (i+1) +" 번째 행, [" + colNm + "] 값이 공백입니다.");
							} else {						
								_interfacez.setIntegrationId(cols[j].trim());
							}
						}							
						break;
					case 41 :
						//-----------------------------------------------------------------
						// Dummy #2
						//-----------------------------------------------------------------							
						break;
					case 42 :
						//-----------------------------------------------------------------
						// Dummy #3
						//-----------------------------------------------------------------							
						break;
						
					default :
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
				System.out.println("consumerCnt=" + consumerCnt + " providerCnt=" + providerCnt );
				//-----------------------------------------------------------------
				// consumer : provider = 1 : N (O)
				// consumer : provider = N : 1 (O)
				// consumer : provider = N : M (X)
				//-----------------------------------------------------------------
				if( providerCnt > 1 && consumerCnt > 1 ) {
					throw new Exception("[ 엑셀 업로드 실패 ] : " + (i+1) +" 번째 행, Consumer / Provider 시스템 관계는 1:1, 1:N, N:1 만 가능합니다.");
				}
				
				//-----------------------------------------------------------------
				// (3) 인터페이스 중복 체크
				//-----------------------------------------------------------------
				Map<Object,Object> interfaceCheckMap = new HashMap<Object, Object>();
				String dupProviderInfoCheckString = Util.join("2", _channel.getChannelId(), providerSystemId, providerService);

				interfaceCheckMap.put("nodeType", "2");
				interfaceCheckMap.put("channelId",_channel.getChannelId());
				interfaceCheckMap.put("systemId",providerSystemId);
				interfaceCheckMap.put("service",providerService);
				//interfaceCheckMap.put("serviceDesc",providerServiceDesc);
				/*String interfaceId = commonService.existInterface(interfaceCheckMap); 1323행까지 형통 미사용 주석
				
				if( !Util.isEmpty(interfaceId) ) {
					throw new Exception("[ 엑셀 업로드 실패 ] : \n" + (i+1) +" 번째 행, [인터페이스 중복] Provider 시스템과 서비스가 동일한 인터페이스가 이미 존재합니다.");
				}*/
				
				//-----------------------------------------------------------------
				// 그룹데이터가 아닌경우 엑셀 내부 중복 검사를 한다.(형통 주석 1328행부터 1335행 까지)
				//-----------------------------------------------------------------
				/*if( ! isSeqGroup ) {
					if( dupProviderInfoCheckList.contains(dupProviderInfoCheckString) ) {
						int dupIndex = dupProviderInfoCheckList.indexOf(dupProviderInfoCheckString);
						throw new Exception("[ 엑셀 업로드 실패 ] : " + (i+1) +" 번째 행, [인터페이스 중복] " + (dupIndex + 1) + " 번째 행 과 인터페이스 정보(솔루션/시스템/서비스)가 동일 합니다");
					} else {
						dupProviderInfoCheckList.add(dupProviderInfoCheckString);
					}
				}*/
				
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
	public List<Map<String,Object>> getExcelUploadMasterInfo(Map<String,Object> params) throws Exception {
		return excelUploadMapper.getExcelUploadMasterInfo(params);
	}
	
	/**
	 * 
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<Map<String,Object>> getExcelUploadDetailInfo(Map<String,Object> params) throws Exception {
		return excelUploadMapper.getExcelUploadDetailInfo(params);
	}		
	
	/**
	 * 
	 * @param params
	 * @return
	 * @throws Exception
	 */
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
	@Transactional
	public int deleteExcelUpload(Map<String, Object> params) throws Exception {
		int resultCd = 0;
		
		resultCd = excelUploadMapper.deleteExcelUploadDetailInfo(params);
		resultCd = excelUploadMapper.deleteExcelUploadMasterInfo(params);
		
		return resultCd;
	}	

}
