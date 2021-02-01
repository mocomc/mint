/**
 *
 */
package pep.per.mint.front.controller.su;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import pep.per.mint.common.data.basic.ComMessage;
import pep.per.mint.common.data.basic.User;
import pep.per.mint.common.util.Util;
import pep.per.mint.database.service.co.CommonService;
import pep.per.mint.front.exception.ControllerException;
import pep.per.mint.front.util.MessageSourceUtil;

/**
 * <blockquote>
 *
 * <pre>
 * <B>통계 - 인터페이스 현황 CRUD 서비스 제공 RESTful Controller  :: ISM</B>
 * <B>REST Method</B>
 * <table border="0" style="border-style:Groove;width:885px;">
 * <tr>
 * <td style="padding:10px;background-color:silver;">API ID</td>
 * <td style="padding:10px;background-color:silver;">API NM</td>
 * <td style="padding:10px;background-color:silver;">METHOD</td>
 * <td style="padding:10px;background-color:silver;">URI</td>
 * </tr>
 * </table>
 * </pre>
 *
 * <blockquote>
 *
 * @author BILl
 */
@Controller
@RequestMapping("/su")
@SuppressWarnings({ "rawtypes", "unchecked" })
public class StatisticsControllerISM {

	private static final Logger logger = LoggerFactory.getLogger(StatisticsControllerISM.class);

	/**
	 * The Message source.
	 */
// 비지니스처리중 프론트까지 전달할 메시지들을 참조할 수 있는 다국어지원용 번들 객체
	@Autowired
	ReloadableResourceBundleMessageSource messageSource;


	/**
	 * The Channel Attribute service.
	 */
	@Autowired
	CommonService commonService;
	// 서블리컨텍스트 관련정보 참조를 위한 객체
	// 예를 들어 servletContext를 이용하여 웹어플리케이션이
	// 배포퇸 컨텍스트 루트위치 등을 얻어올 수 있다.
	@Autowired
	private ServletContext servletContext;



	/**
	 * <pre>
	 * 인터페이스 기간별 조회 totals(ISM)-시간별
	 * API ID : REST-R08-SU-03-02-601
	 * </pre>
	 *
	 * @param httpSession 세션
	 * @param comMessage 요청응답통신객체
	 * @param locale 다국어지원을 위한 로케일
	 * @param request the request
	 * @return ComMessage 통신객체
	 * @throws Exception the exception
	 * @throws Exception the exception
	 * @author INSEONG
     * @since version 1.0(2015.07)
     */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/statistics-period/totals/hdins/hour", params = "method=GET", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<Map<String,Object>, List<Map>> getStatisticsInterfacePeriodTotalsHdinsHour(
			HttpSession httpSession,
			@RequestBody ComMessage<Map<String,Object>, List<Map>> comMessage,
			Locale locale, HttpServletRequest request) throws Exception, ControllerException {

		// ----------------------------------------------------------------------------
		// 여긴 비지니스 코드만 작성해라.
		// ----------------------------------------------------------------------------
		{
			String errorCd = "";
			String errorMsg = "";

			Map params = comMessage.getRequestObject();
			if(params == null) params = new HashMap();

			boolean checkSession = comMessage.getCheckSession();
			if(checkSession) {
			//-------------------------------------------------
			//데이터액세스권한용 사용자정보세팅
			//-------------------------------------------------
			{
			User user = (User) httpSession.getAttribute("user");

			if(user == null) {
				errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.session.timeout", locale);
				String sessionInfo = Util.join("\nsession id:", httpSession.getId(), "\nCreationTime:",
						             new Date(httpSession.getCreationTime()), "\nLastAccessedTIme:", new Date(httpSession.getLastAccessedTime()),
						             new Date(httpSession.getLastAccessedTime()), "\nMaxInactiveIntercal(sec):", httpSession.getMaxInactiveInterval());
				Object[] errorMsgParams = {"StatisticsControllerISM.getStatisticsInterfacePeriodTotalsHdinsHour", sessionInfo};
				errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.session.timeout", errorMsgParams, locale);
				logger.debug(sessionInfo);
				throw new ControllerException(errorCd, errorMsg);
			}

			params.put("userId", user.getUserId());
			params.put("isInterfaceAdmin", user.getRole().getIsInterfaceAdmin());
				}
			} else {
				params.put("userId", comMessage.getUserId());
			}


			List<Map> list = null;
			Map<String, Object> map = new HashMap();
			//--------------------------------------------------
			//요건 리스트 조회 실행
			//--------------------------------------------------
			{

				map = getStatisticsInterfacePeriodTotals(params);
				list = convertStatHours(map);
			}
			//--------------------------------------------------
			// 서비스 처리 종료시간을 얻어 CM에 세팅한다.
			//--------------------------------------------------
			{
				comMessage.setEndTime(Util.getFormatedDate("yyyyMMddHHmmssSSS"));
			}
			//--------------------------------------------------
			// 통신메시지에 처리결과 코드/메시지를 등록한다.
			//--------------------------------------------------
			{
				if (list == null || list.size() <= 1) {// 결과가 없을 경우 비지니스 예외 처리
					//logger.debug(Util.join("default locale:", locale.toString(), ",", locale.getLanguage(), ",", locale.getCountry()));
					errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.retrieve.none", locale);
					errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.retrieve.none", null, locale);
				} else {// 성공 처리결과
					errorCd = MessageSourceUtil.getErrorCd(messageSource, "success.cd.ok", locale);
					Object [] errorMsgParams = {list.size()};
					errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "success.msg.retrieve.list.ok", errorMsgParams, locale);
					comMessage.setResponseObject(list);
				}
				comMessage.setErrorCd(errorCd);
				comMessage.setErrorMsg(errorMsg);
			}
			return comMessage;
		}
	}



	private List<Map> convertStatHours(Map<String, Object> map) {
		List<Map>  list = new ArrayList<Map>();
		List  stList = (List)map.get("list");
		for(Object  obj : stList){
			String startDate = (String)((Map)obj).get("startDate");
			String interfaceId = (String)((Map)obj).get("interfaceID");
			// 성공
			HashMap itm = new HashMap();
			itm.put("startDate", startDate);
			itm.put("interfaceId", interfaceId);
			itm.put("gubun", "성공횟수");

			for(int i=0; i< 24;i++){
				itm.put("HOUR"+i, ((List)((Map)obj).get("okCounts")).get(i) );
			}
			list.add(itm);

			// 오류
			HashMap itmE = new HashMap();
			itmE.put("startDate", startDate);
			itmE.put("interfaceId", interfaceId);
			itmE.put("gubun", "실패횟수");

			for(int i=0; i< 24;i++){
				itmE.put("HOUR"+i, ((List)((Map)obj).get("errorCounts")).get(i) );
			}
			list.add(itmE);

			// 전체
			HashMap itmT = new HashMap();
			itmT.put("startDate", startDate);
			itmT.put("interfaceId", interfaceId);
			itmT.put("gubun", "총건수");

			for(int i=0; i< 24;i++){
				itmT.put("HOUR"+i, ((List)((Map)obj).get("totalCounts")).get(i) );
			}
			list.add(itmT);
			// 평균처리시간
			HashMap itmA = new HashMap();
			itmA.put("startDate", startDate);
			itmA.put("interfaceId", interfaceId);
			itmA.put("gubun", "평균처리시간");

			for(int i=0; i< 24;i++){
				itmA.put("HOUR"+i, ((List)((Map)obj).get("averageVelocitys")).get(i) );
			}
			list.add(itmA);
		}

		return list;
	}



	private Map<String, Object> getStatisticsInterfacePeriodTotals(Map params)  throws Exception{

		//----------------------------------------------------------------------------
		//실행
		//----------------------------------------------------------------------------

		List<Map<String, Object>> content = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap();
		try{
			{
				String callUrl  ="";
				String scType = (String)params.get("scCriteriaType");
				Map envMap =  commonService.getEnvironmentalValues();
				String webUrl = "";
				List statsList = (List)envMap.get("system.ism.statistics.url");
				if(statsList != null && statsList.size()>0){
					webUrl = (String) statsList.get(0);
				}else{
					throw new Exception(Util.join("통계조회 URL 이 없습니다."));
				}
				if("1".equalsIgnoreCase(scType)){  // 온라인
					callUrl =webUrl +"/stts/online/hour";
				}else if("2".equalsIgnoreCase(scType)){  // 배치
					callUrl =webUrl +"/stts/batch/hour";
				}else if("3".equalsIgnoreCase(scType)){
					callUrl =webUrl +"/stts/defferd/hour";
				}

				String stTime =(String)params.get("scPeriodFrom");;
				String edTime =(String)params.get("scPeriodTo");;
				String integrationServiceId =(String)params.get("scCategoryDetailNm");;

					callUrl += "?stTime="+stTime+"&edTime="+edTime+"&integrationServiceId="+integrationServiceId;
					URL url = new URL(callUrl);


				    // 문자열로 URL 표현
					logger.debug("URL :" + url.toExternalForm());

				    // HTTP Connection 구하기
				    HttpURLConnection conn = (HttpURLConnection) url.openConnection();

				    // 요청 방식 설정 ( GET or POST or .. 별도로 설정하지 않으면 GET 방식 )
				    conn.setRequestMethod("GET");
				    // 연결 타임아웃 설정
				    conn.setConnectTimeout(3000); // 3초
				    // 읽기 타임아웃 설정
				    conn.setReadTimeout(60000); // 60초

				    String line;


				    BufferedReader reader = null;
				    try {
					    reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
					    while ((line = reader.readLine()) != null) {
					      map = Util.jsonToMap(line);
					    }
				    }finally {
				    	if(reader != null) try{ reader.close();}catch(Exception e) {logger.error("",e);}
				    }

				    if(map != null && !Util.isEmpty(map.get("list"))) {
				    	//content = (List<Map<String, Object>>) map.get("list");
				    	//content = <Map<String, Object>> map;
				    	logger.debug("\nmap====== " + map);
				    }

			}
		}catch(Throwable e){
			throw new Exception(e);
		}

		return map;
	}



	/**
	 * <pre>
	 * 인터페이스 기간별 조회 totals(HDINS)-일별
	 * API ID : REST-R08-SU-03-02-602
	 * </pre>
	 *
	 * @param httpSession 세션
	 * @param comMessage 요청응답통신객체
	 * @param locale 다국어지원을 위한 로케일
	 * @param request the request
	 * @return ComMessage 통신객체
	 * @throws Exception the exception
	 * @throws Exception the exception
	 * @author INSEONG
     * @since version 1.0(2015.07)
     */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/statistics-period/totals/hdins/day", params = "method=GET", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<Map<String,Object>, List<Map>> getStatisticsInterfacePeriodTotalsHdinsDay(
			HttpSession httpSession,
			@RequestBody ComMessage<Map<String,Object>, List<Map>> comMessage,
			Locale locale, HttpServletRequest request) throws Exception, ControllerException {

		// ----------------------------------------------------------------------------
		// 여긴 비지니스 코드만 작성해라.
		// ----------------------------------------------------------------------------
		{
			String errorCd = "";
			String errorMsg = "";

			Map params = comMessage.getRequestObject();
			if(params == null) params = new HashMap();

			boolean checkSession = comMessage.getCheckSession();
			if(checkSession) {
			//-------------------------------------------------
			//데이터액세스권한용 사용자정보세팅
			//-------------------------------------------------
			{
			User user = (User) httpSession.getAttribute("user");

			if(user == null) {
				errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.session.timeout", locale);
				String sessionInfo = Util.join("\nsession id:", httpSession.getId(), "\nCreationTime:",
						             new Date(httpSession.getCreationTime()), "\nLastAccessedTIme:", new Date(httpSession.getLastAccessedTime()),
						             new Date(httpSession.getLastAccessedTime()), "\nMaxInactiveIntercal(sec):", httpSession.getMaxInactiveInterval());
				Object[] errorMsgParams = {"StatisticsControllerISM.getStatisticsInterfacePeriodTotalsHdinsDay", sessionInfo};
				errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.session.timeout", errorMsgParams, locale);
				logger.debug(sessionInfo);
				throw new ControllerException(errorCd, errorMsg);
			}

			params.put("userId", user.getUserId());
			params.put("isInterfaceAdmin", user.getRole().getIsInterfaceAdmin());
				}
			} else {
				params.put("userId", comMessage.getUserId());
			}

			List<Map> list = null;
			Map<String, Object> map = new HashMap();

			//--------------------------------------------------
			//요건 리스트 조회 실행
			//--------------------------------------------------
			{

				list = getStatisticsInterfacePeriodTotalsDay(params);
			}
			//--------------------------------------------------
			// 서비스 처리 종료시간을 얻어 CM에 세팅한다.
			//--------------------------------------------------
			{
				comMessage.setEndTime(Util.getFormatedDate("yyyyMMddHHmmssSSS"));
			}
			//--------------------------------------------------
			// 통신메시지에 처리결과 코드/메시지를 등록한다.
			//--------------------------------------------------
			{
				if (list == null || list.size() <= 1) {// 결과가 없을 경우 비지니스 예외 처리
					//logger.debug(Util.join("default locale:", locale.toString(), ",", locale.getLanguage(), ",", locale.getCountry()));
					errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.retrieve.none", locale);
					errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.retrieve.none", null, locale);
				} else {// 성공 처리결과
					errorCd = MessageSourceUtil.getErrorCd(messageSource, "success.cd.ok", locale);
					Object [] errorMsgParams = {list.size()};
					errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "success.msg.retrieve.list.ok", errorMsgParams, locale);
					comMessage.setResponseObject(list);
				}
				comMessage.setErrorCd(errorCd);
				comMessage.setErrorMsg(errorMsg);
			}
			return comMessage;
		}
	}



	private List<Map> getStatisticsInterfacePeriodTotalsDay(Map params)  throws Exception{

		//----------------------------------------------------------------------------
		//실행
		//----------------------------------------------------------------------------
		List<Map> list = null;
		Map<String, Object> map = new HashMap();
		try{
			{
				Map envMap =  commonService.getEnvironmentalValues();
				String webUrl = "";
				List statsList = (List)envMap.get("system.ism.statistics.url");
				if(statsList != null && statsList.size()>0){
					webUrl = (String) statsList.get(0);
				}else{
					throw new Exception(Util.join("통계조회 URL 이 없습니다."));
				}



				String callUrl  ="";
				String scType = (String)params.get("scCriteriaType");
				logger.debug("scType"+scType );
				if("1".equalsIgnoreCase(scType)){  // 온라인
					callUrl =webUrl +"/stts/online/day";
				}else if("2".equalsIgnoreCase(scType)){  // 배치
					callUrl =webUrl +"/stts/batch/day";
				}else if("3".equalsIgnoreCase(scType)){
					callUrl =webUrl +"/stts/defferd/day";
				}

				//String stTime ="2017022700";
				//String edTime ="2017030123";
				//String integrationServiceId ="EAI";

				String stTime =(String)params.get("scPeriodFrom");;
				String edTime =(String)params.get("scPeriodTo");;
				String integrationServiceId =(String)params.get("scCategoryDetailNm");;

					callUrl += "?stTime="+stTime+"&edTime="+edTime+"&integrationServiceId="+integrationServiceId;
					URL url = new URL(callUrl);


				    // 문자열로 URL 표현
					logger.debug("URL :" + url.toExternalForm());

				    // HTTP Connection 구하기
				    HttpURLConnection conn = (HttpURLConnection) url.openConnection();

				    // 요청 방식 설정 ( GET or POST or .. 별도로 설정하지 않으면 GET 방식 )
				    conn.setRequestMethod("GET");
				    // 연결 타임아웃 설정
				    conn.setConnectTimeout(3000); // 3초
				    // 읽기 타임아웃 설정
				    conn.setReadTimeout(60000); // 60초

				    String line;


				    BufferedReader reader = null;
				    try {
					    reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
					    while ((line = reader.readLine()) != null) {
					      map = Util.jsonToMap(line);
					    }
				    }finally {
				    	if(reader != null) try{ reader.close();}catch(Exception e) {logger.error("",e);}
				    }

				    if(map != null && !Util.isEmpty(map.get("list"))) {
				    	list = (List<Map>) map.get("list");
				    	logger.debug("\nmap====== " + map);
				    }
			}
		}catch(Throwable e){
			throw new Exception(e);
		}
		return list;
	}

}
