package pep.per.mint.front.controller.op;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

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
import org.springframework.web.client.RestTemplate;

import pep.per.mint.common.data.basic.ComMessage;
import pep.per.mint.common.data.basic.User;

import pep.per.mint.common.util.Util;
import pep.per.mint.database.service.co.CommonService;
import pep.per.mint.database.service.op.DashboardService;
import pep.per.mint.front.exception.ControllerException;

import pep.per.mint.front.util.MessageSourceUtil;



/**
 * <blockquote>
 *
 * <pre>
 * <B> ISM 대시보드 CRUD  서비스 제공 RESTful Controller</B>
 * <B>REST Method</B>
 * <table border="0" style="border-style:Groove;width:885px;">
 * <tr>
 * <td style="padding:10px;background-color:silver;">API ID</td>
 * <td style="padding:10px;background-color:silver;">API NM</td>
 * <td style="padding:10px;background-color:silver;">METHOD</td>
 * <td style="padding:10px;background-color:silver;">URI</td>
 * </tr>
 * <tr>
 * <td BGCOLOR="#e5f1ff" style="padding:3px;color:black;">REST-R01-OP-02-00-601</td>
 * <td BGCOLOR="#e5f1ff" style="padding:3px;color:black;">메인대시보드 통합 조회(ISM)</td>
 * <td BGCOLOR="#e5f1ff" style="padding:3px;color:black;">{@link #getMainDashboardInfo(HttpSession, ComMessage, Locale) getMainDashboardInfo}</td>
 * <td BGCOLOR="#e5f1ff" style="padding:3px;color:black;">/op/dashboard/maindashboards</td>
 * </tr>
 * </table>
 * </pre>
 *
 * <blockquote>
 *
 * @author isjang </pre>
 *</blockquote>
 */
@Controller
@RequestMapping("/op")
@SuppressWarnings({ "rawtypes", "unchecked" })
public class DashboardControllerISM {

	private static final Logger logger = LoggerFactory.getLogger(DashboardControllerISM.class);

	@Autowired
	CommonService commonService;

	/**
	 * The Dashboard service.
	 */
	@Autowired
	DashboardService dashboardService;

	/**
	 * The Message source.
	 * 비지니스처리중 프론트까지 전달할 메시지들을 참조할 수 있는 다국어지원용 번들 객체
	 */
	@Autowired
	ReloadableResourceBundleMessageSource messageSource;

	/**
	 * 서블리컨텍스트 관련정보 참조를 위한 객체
	 * 예를 들어 servletContext를 이용하여 웹어플리케이션이
	 * 배포퇸 컨텍스트 루트위치 등을 얻어올 수 있다.
	 */
	@Autowired
	private ServletContext servletContext;


	public static boolean isTest = false;


	@Autowired
	RestTemplate restTemplate;



	/**
	 * <pre>
	 * 처리건수 조회(금일)
	 * API ID : REST-R01-OP-02-00
	 * </pre>
	 *
	 * @param httpSession 세션
	 * @param comMessage 요청응답통신객체
	 * @param locale 다국어지원을 위한 로케일
	 * @param request the request
	 * @return ComMessage 통신객체
	 * @throws Exception the exception
	 * @throws Exception the exception
	 * @author isjang
     * @since version 1.0(2017.03)
     */
	@RequestMapping(
			value="/dashboard/ism/throughput/daily",
			params={"method=GET"},
			method=RequestMethod.POST,
			headers="content-type=application/json")
	public @ResponseBody ComMessage< Map<String,Object>, List<Map> > getDailyThroughput(
			HttpSession  httpSession,
			@RequestBody ComMessage< Map<String,Object>, List<Map> > comMessage,
			Locale locale, HttpServletRequest request) throws Exception, ControllerException {

		//----------------------------------------------------------------------------
		//여긴 비지니스 코드만 작성해라.
		//----------------------------------------------------------------------------
		{
			String errorCd = "";
			String errorMsg = "";

			Map params = comMessage.getRequestObject();
			if(params == null){
				params = new HashMap();
			}

			//--------------------------------------------------
			// 로그인사용자 정보 셋팅
			//--------------------------------------------------
			boolean checkSession = comMessage.getCheckSession();
			if(checkSession) {
				{
					User user = (User) httpSession.getAttribute("user");
					params.put("userId", user.getUserId());
					params.put("isInterfaceAdmin", user.getRole().getIsInterfaceAdmin());
				}
			}else{
				params.put("userId", comMessage.getUserId());
				params.put("isInterfaceAdmin", "Y");
			}


			//--------------------------------------------------
			// Service Call
			//--------------------------------------------------
			List<Map> list= null;
			{
				//list = dashboardService.getDailyThroughput(params);
				list = isTest ? getDailyThroughput(params) : getDailyThroughputReal(params);
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
				if(list == null || list.size() == 0) {
					errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.retrieve.none", locale);
					errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.retrieve.none", null, locale);
				}else{//성공 처리결과
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

	/**
	 *
	 * @param params
	 * @return
	 * @author KYS
	 */
	private List<Map> getDailyThroughputReal(Map params) {
		List<Map> list = new ArrayList<Map>();

		Map resultMap = new HashMap<String, Object>();
		try{
			{
				String callUrl  ="";
				String processDate = (String)params.get("processDate");
				Map envMap =  commonService.getEnvironmentalValues();
				String webUrl = "";
				List statsList = (List)envMap.get("system.ism.statistics.url");
				if(statsList != null && statsList.size()>0){
					webUrl = (String) statsList.get(0);
				}else{
					throw new Exception(Util.join("대시보드 URL 이 없습니다."));
				}
				callUrl =webUrl +"/custom/dashboard/process/count";

				URL url = new URL(callUrl);

			    // 문자열로 URL 표현
				System.out.println("URL====== :" + url.toExternalForm());

			    // HTTP Connection 구하기
			    HttpURLConnection conn = (HttpURLConnection) url.openConnection();

			    // 요청 방식 설정 ( GET or POST or .. 별도로 설정하지 않으면 GET 방식 )
			    conn.setRequestMethod("GET");
			    // 연결 타임아웃 설정
			    conn.setConnectTimeout(3000); // 3초
			    // 읽기 타임아웃 설정
			    conn.setReadTimeout(3000); // 3초

			    String line;

			    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			    while ((line = reader.readLine()) != null) {
			    	System.out.println("\nline====== " + line.toString());
			    	resultMap = Util.jsonToMap(line);
			    }
			    reader.close();

			    if(resultMap != null) {
			    	Map map = new HashMap<String, Object>();

			    	map = Util.jsonToMap(Util.toJSONString(resultMap.get("A")));
			    	map.put("roleNm","전체");
					map.put("totalCnt", map.get("total"));
					map.put("successCnt", map.get("success"));
					map.put("errorCnt", map.get("error"));
					map.put("toDayAvgTime", map.get("procSpd")); // 금일
					map.put("allAvgTime", map.get("avgProcSpd")); // 전일

			    	list.add(map);


			    	map = Util.jsonToMap(Util.toJSONString(resultMap.get("O")));
			    	map.put("roleNm","온라인");
					map.put("totalCnt", map.get("total"));
					map.put("successCnt", map.get("success"));
					map.put("errorCnt", map.get("error"));
					map.put("toDayAvgTime", map.get("procSpd")); // 금일
					map.put("allAvgTime", map.get("avgProcSpd")); // 전일

			    	list.add(map);


			    	map = (Map)resultMap.get("B"); //Util.jsonToMap(Util.toJSONString(resultMap.get("B")));
			    	map.put("roleNm","배치");
					map.put("totalCnt", map.get("total"));
					map.put("successCnt", map.get("success"));
					map.put("errorCnt", map.get("error"));
					map.put("toDayAvgTime", map.get("procSpd")); // 금일
					map.put("allAvgTime", map.get("avgProcSpd")); // 전일

			    	list.add(map);

			    }

			}
		}catch(Throwable e){
			return list;
			//throw new Exception(e);
		}


		return list;
	}


	private List<Map> getDailyThroughput(Map params) {
		List<Map> list = new ArrayList();

		HashMap resultMap = new HashMap<String, Object>();

		resultMap.put("roleNm","전체");
		resultMap.put("totalCnt",1000);
		resultMap.put("successCnt",800);
		resultMap.put("errorCnt",800);
		resultMap.put("toDayAvgTime",3.5);
		resultMap.put("allAvgTime",3.5);

		list.add(resultMap);


		resultMap = new HashMap<String, Object>();

		resultMap.put("roleNm","Online");
		resultMap.put("totalCnt",500);
		resultMap.put("successCnt",400);
		resultMap.put("errorCnt",400);
		resultMap.put("toDayAvgTime",2.2);
		resultMap.put("allAvgTime",2.2);
		list.add(resultMap);


		resultMap = new HashMap<String, Object>();

		resultMap.put("roleNm","Batch");
		resultMap.put("totalCnt",500);
		resultMap.put("successCnt",400);
		resultMap.put("errorCnt",300);
		resultMap.put("toDayAvgTime",4.7);
		resultMap.put("allAvgTime",4.7);
		list.add(resultMap);



		return list;
	}

	/**
	 * <pre>
	 * 오류인터페이스
	 * API ID : REST-R02-OP-02-00
	 * </pre>
	 *
	 * @param httpSession 세션
	 * @param comMessage 요청응답통신객체
	 * @param locale 다국어지원을 위한 로케일
	 * @param request the request
	 * @return ComMessage 통신객체
	 * @throws Exception the exception
	 * @throws Exception the exception
	 * @author isjang
     * @since version 1.0(2017.03)
     */
	@RequestMapping(
			value="/dashboard/ism/top/error-list",
			params={"method=GET"},
			method=RequestMethod.POST,
			headers="content-type=application/json")
	public @ResponseBody ComMessage< Map<String,Object>, List<Map> > getErrorListTop(
			HttpSession  httpSession,
			@RequestBody ComMessage< Map<String,Object>, List<Map> > comMessage,
			Locale locale, HttpServletRequest request) throws Exception, ControllerException {

		//----------------------------------------------------------------------------
		//여긴 비지니스 코드만 작성해라.
		//----------------------------------------------------------------------------
		{
			String errorCd = "";
			String errorMsg = "";

			Map params = comMessage.getRequestObject();
			if(params == null){
				params = new HashMap();
			}

			//--------------------------------------------------
			// 로그인사용자 정보 셋팅
			//--------------------------------------------------
			boolean checkSession = comMessage.getCheckSession();
			if(checkSession) {
				{
					User user = (User) httpSession.getAttribute("user");
					params.put("userId", user.getUserId());
					params.put("isInterfaceAdmin", user.getRole().getIsInterfaceAdmin());
				}
			}else{
				params.put("userId", comMessage.getUserId());
				params.put("isInterfaceAdmin", "Y");
			}

			//--------------------------------------------------
			// Service Call
			//--------------------------------------------------
			List<Map> list= null;
			{
				//list = dashboardService.getErrorListTop(params);

				list = isTest ? getErrorListTop(params) : getErrorListTopReal(params);


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
				if(list == null || list.size() == 0) {
					errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.retrieve.none", locale);
					errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.retrieve.none", null, locale);
				}else{//성공 처리결과
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

	private List<Map> getErrorListTopReal(Map params) throws Exception, ControllerException{

		String BASIC_URL = null;
		Map envMap =  commonService.getEnvironmentalValues();
		List statsList = (List)envMap.get("system.ism.statistics.url");
		if(statsList != null && statsList.size()>0){
			BASIC_URL = (String) statsList.get(0);
		}else{
			throw new Exception(Util.join("대시보드 URL 이 없습니다."));
		}

		//2.URL 세팅
		String batchUrl = Util.join(BASIC_URL , "/custom/dashboard/error?type=SERVICE");  // 현재날짜로 호출

		//3.데이터 조회
		logger.debug("오류인터페이스조회:");
		List<Map> datas = restTemplate.getForObject(batchUrl, List.class);
		try{logger.debug(Util.join("오류인터페이스조회결과:\n", Util.toJSONString(datas))); }catch(Exception e){}

		List<Map> resultList = new ArrayList<Map>();

		//2데이터 처리
		if(datas != null && datas.size() > 0){

			for (Map data : datas) {

				//test 이후 빼도됨
				String[] columns = {"integrationServiceNm", "comp", "date", "code", "gid"};
				checkRequired("오류인터페이스조회:/custom/dashboard/error", columns , data);

				Map<String, Object> record = new HashMap<String, Object>();
				String interfaceNm = (String) data.get("integrationServiceNm");
				String interfaceId = (String) data.get("comp");
				String date = (String) data.get("date");
				//String time = (String) data.get("time");
				//String txTime = (String)data.get("txTime");
				String code = (String)data.get("code");
				String gid = (String)data.get("gid");
				String type = (String)data.get("type");

				record.put("interfaceNm",interfaceNm);
				record.put("integrationId",interfaceId);
				record.put("errorDec",code);
				record.put("date",date);
				record.put("gid",gid);
				record.put("type",type);
				//record.put("time",time);
				//record.put("txTime",txTime);
				resultList.add(record);

			}
		}else{
			logger.debug("오류인터페이스조회:결과가 존재하지 않음.");
		}
		return resultList;
	}


	private List<Map> getErrorListTop(Map params) {
		List<Map> list = new ArrayList();

		HashMap resultMap = new HashMap<String, Object>();

		resultMap.put("interfaceNm","통일접속");
		resultMap.put("integrationId","IR_SYM-NN-001");
		resultMap.put("errorDec","접속 오류");

		list.add(resultMap);


		resultMap = new HashMap<String, Object>();

		resultMap.put("interfaceNm","통일접속1");
		resultMap.put("integrationId","IR_SYM-NN-002");
		resultMap.put("errorDec","수신오류");

		list.add(resultMap);


		resultMap = new HashMap<String, Object>();

		resultMap.put("interfaceNm","통일접속2");
		resultMap.put("integrationId","IR_SYM-NN-003");
		resultMap.put("errorDec","파싱 오류");

		list.add(resultMap);


		resultMap = new HashMap<String, Object>();

		resultMap.put("interfaceNm","통일접속4");
		resultMap.put("integrationId","IR_SYM-NN-005");
		resultMap.put("errorDec","처리 실패");

		list.add(resultMap);



		resultMap = new HashMap<String, Object>();

		resultMap.put("interfaceNm","통일접속5");
		resultMap.put("integrationId","IR_SYM-HN-009");
		resultMap.put("errorDec","처리 실패");

		list.add(resultMap);

		return list;
	}

	/**
	 * <pre>
	 * 지연 인터페이스
	 * API ID : REST-R03-OP-02-00
	 * </pre>
	 *
	 * @param httpSession 세션
	 * @param comMessage 요청응답통신객체
	 * @param locale 다국어지원을 위한 로케일
	 * @param request the request
	 * @return ComMessage 통신객체
	 * @throws Exception the exception
	 * @throws Exception the exception
	 * @author isjang
     * @since version 1.0(2017.03)
     */
	@RequestMapping(
			value="/dashboard/ism/top/delay-list",
			params={"method=GET"},
			method=RequestMethod.POST,
			headers="content-type=application/json")
	public @ResponseBody ComMessage< Map<String,Object>, List<Map> > getDelayListTop(
			HttpSession  httpSession,
			@RequestBody ComMessage< Map<String,Object>, List<Map> > comMessage,
			Locale locale, HttpServletRequest request) throws Exception, ControllerException {

		//----------------------------------------------------------------------------
		//여긴 비지니스 코드만 작성해라.
		//----------------------------------------------------------------------------
		{
			String errorCd = "";
			String errorMsg = "";

			Map params = comMessage.getRequestObject();
			if(params == null){
				params = new HashMap();
			}

			//--------------------------------------------------
			// 로그인사용자 정보 셋팅
			//--------------------------------------------------
			boolean checkSession = comMessage.getCheckSession();
			if(checkSession) {
				{
					User user = (User) httpSession.getAttribute("user");
					params.put("userId", user.getUserId());
					params.put("isInterfaceAdmin", user.getRole().getIsInterfaceAdmin());
				}
			}else{
				params.put("userId", comMessage.getUserId());
				params.put("isInterfaceAdmin", "Y");
			}

			//--------------------------------------------------
			// Service Call
			//--------------------------------------------------
			List<Map> list= null;
			{
				//list = dashboardService.getDelayListTop(params);
				list = isTest ? getDelayListTop(params) : getDelayListTopReal(params);
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
				if(list == null || list.size() == 0) {
					errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.retrieve.none", locale);
					errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.retrieve.none", null, locale);
				}else{//성공 처리결과
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

	private List<Map> getDelayListTopReal(Map params) throws Exception, ControllerException{

		String BASIC_URL = null;
		Map envMap =  commonService.getEnvironmentalValues();
		List statsList = (List)envMap.get("system.ism.statistics.url");
		if(statsList != null && statsList.size()>0){
			BASIC_URL = (String) statsList.get(0);
		}else{
			throw new Exception(Util.join("대시보드 URL 이 없습니다."));
		}

		//2.URL 세팅
		String batchUrl = Util.join(BASIC_URL , "/custom/dashboard/online/delay");  // 현재날짜로 호출

		//3.데이터 조회
		logger.debug("지연인터페이스조회:");
		List<Map> datas = restTemplate.getForObject(batchUrl, List.class);
		try{logger.debug(Util.join("지연인터페이스조회결과:", Util.toJSONString(datas))); }catch(Exception e){}

		List<Map> resultList = new ArrayList<Map>();
		//4.데이터 처리
		if(datas != null && datas.size() > 0){

			for (Map data : datas) {
				//test 이후 빼도됨
				String[] columns = {"integrationServiceNm", "integrationServiceId", "globalId", "estTime"};
				checkRequired("지연인터페이스조회:/custom/dashboard/online/delay", columns , data);

				Map<String, Object> record = new HashMap<String, Object>();
				String interfaceNm = (String) data.get("integrationServiceNm");
				String interfaceId = (String) data.get("integrationServiceId");
				String estTime = (String)data.get("estTime");
				String procDate = (String)data.get("procDate");
				String globalId = (String)data.get("globalId");


				record.put("interfaceNm",interfaceNm);
				record.put("integrationId",interfaceId);
				record.put("procDate",procDate);
				record.put("globalId",globalId);
				record.put("elapsedTime",estTime);
				resultList.add(record);

			}
		}else{
			logger.debug("지연인터페이스조회:결과가 존재하지 않음.");
		}
		return resultList;
	}

	private void checkRequired(String msg, String [] params, Map data) throws Exception, ControllerException{
		// TODO Auto-generated method stub
		for (String key : params) {
			if(!data.containsKey(key)) throw new ControllerException("ISM-ERR",Util.join(msg, " response have no required field(",key,") value"));
		}
	}

	private List<Map> getDelayListTop(Map params) {
		List<Map> list = new ArrayList();

		HashMap resultMap = new HashMap<String, Object>();

		resultMap.put("interfaceNm","통일접속");
		resultMap.put("integrationId","IR_SYM-NN-001");
		resultMap.put("elapsedTime",35.0);

		list.add(resultMap);


		resultMap = new HashMap<String, Object>();

		resultMap.put("interfaceNm","통일접속1");
		resultMap.put("integrationId","IR_SYM-NN-002");
		resultMap.put("elapsedTime",21.3);

		list.add(resultMap);


		resultMap = new HashMap<String, Object>();

		resultMap.put("interfaceNm","통일접속2");
		resultMap.put("integrationId","IR_SYM-NN-003");
		resultMap.put("elapsedTime",17.3);

		list.add(resultMap);


		resultMap = new HashMap<String, Object>();

		resultMap.put("interfaceNm","통일접속4");
		resultMap.put("integrationId","IR_SYM-NN-005");
		resultMap.put("elapsedTime",15.3);

		list.add(resultMap);



		resultMap = new HashMap<String, Object>();

		resultMap.put("interfaceNm","통일접속5");
		resultMap.put("integrationId","IR_SYM-HN-009");
		resultMap.put("elapsedTime",10.3);

		list.add(resultMap);

		return list;
	}

	/**
	 * <pre>
	 * 실시간 처리건수 - 전체
	 * API ID : REST-R04-OP-02-00
	 * </pre>
	 *
	 * @param httpSession 세션
	 * @param comMessage 요청응답통신객체
	 * @param locale 다국어지원을 위한 로케일
	 * @param request the request
	 * @return ComMessage 통신객체
	 * @throws Exception the exception
	 * @throws Exception the exception
	 * @author isjang
     * @since version 1.0(2017.03)
     */
	@RequestMapping(
			value="/dashboard/ism/realtime/total-count",
			params={"method=GET"},
			method=RequestMethod.POST,
			headers="content-type=application/json")
	public @ResponseBody ComMessage< Map<String,Object>, List<Map> > getRealTimeTotalCount(
			HttpSession  httpSession,
			@RequestBody ComMessage< Map<String,Object>, List<Map> > comMessage,
			Locale locale, HttpServletRequest request) throws Exception, ControllerException {

		//----------------------------------------------------------------------------
		//여긴 비지니스 코드만 작성해라.
		//----------------------------------------------------------------------------
		{
			String errorCd = "";
			String errorMsg = "";

			Map params = comMessage.getRequestObject();
			logger.debug("whoana-log:" + Util.toJSONString(params));
			if(params == null){
				params = new HashMap();
			}


			//--------------------------------------------------
			// 로그인사용자 정보 셋팅
			//--------------------------------------------------
			boolean checkSession = comMessage.getCheckSession();
			if(checkSession) {
				{
					User user = (User) httpSession.getAttribute("user");
					params.put("userId", user.getUserId());
					params.put("isInterfaceAdmin", user.getRole().getIsInterfaceAdmin());
				}
			}else{
				params.put("userId", comMessage.getUserId());
				params.put("isInterfaceAdmin", "Y");
			}

			//--------------------------------------------------
			// Service Call
			//--------------------------------------------------
			List<Map> list= null;
			{
				//list = dashboardService.getRealTimeTotalCount(params);
				list = isTest ? getRealTimeTotalCount(params) : getRealTimeTotalCountReal(params);
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
				if(list == null || list.size() == 0) {
					errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.retrieve.none", locale);
					errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.retrieve.none", null, locale);
				}else{//성공 처리결과
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

	/**
	 *
	 * @param params
	 * @return
	 * @author KYS
	 * @throws ControllerException
	 */
	private List<Map> getRealTimeTotalCountReal(Map params) throws Exception, ControllerException{

		String BASIC_URL = null;
		Map envMap =  commonService.getEnvironmentalValues();
		List statsList = (List)envMap.get("system.ism.statistics.url");
		if(statsList != null && statsList.size()>0){
			BASIC_URL = (String) statsList.get(0);
		}else{
			throw new Exception(Util.join("대시보드 URL 이 없습니다."));
		}

		//2.URL 세팅
		String fromDate = (String)params.get("fromDate");
		String toDate = (String)params.get("toDate");
		String url = Util.join(BASIC_URL , "/custom/dashboard/process/curProcCount?fromDate=",fromDate, "&toDate=",toDate);  // 현재날짜로 호출

		//3.데이터 조회
		logger.debug("실시간처리현황조회시작:");
		Map datas = restTemplate.getForObject(url, Map.class);
		try{logger.debug(Util.join("실시간처리현황결과:", Util.toJSONString(datas))); }catch(Exception e){}

		List<Map> resultList = new ArrayList<Map>();
		//24시 기준 데이터 처리
		if(datas != null){


			//test 이후 빼도됨
			String[] columns = {"batchCnt", "onlineCnt"};
			checkRequired("실시간처리현황조회:/custom/dashboard/process/curProcCount", columns , datas);


			int onlineCnt = (Integer)datas.get("onlineCnt");
			int batchCnt = (Integer)datas.get("batchCnt");

			Map recordAll = new HashMap<String, Object>();
			recordAll.put("roleNm","전체");
			recordAll.put("cnt",onlineCnt+batchCnt);
			recordAll.put("flag","count");
			resultList.add(recordAll);

			Map recordOnline = new HashMap<String, Object>();
			recordOnline.put("roleNm","Online");
			recordOnline.put("cnt",onlineCnt);
			recordOnline.put("flag","count");
			resultList.add(recordOnline);


			Map recordBatch = new HashMap<String, Object>();
			recordBatch.put("roleNm","Batch");
			recordBatch.put("cnt",batchCnt);
			recordBatch.put("flag","count");
			resultList.add(recordBatch);


		}else{
			logger.debug("실시간처리현황조회:결과가 존재하지 않음.");
		}
		return resultList;
	}

	private List<Map> getRealTimeTotalCount(Map params) {
		List<Map> list = new ArrayList();
		Random random = new Random();
		int onlineCnt = random.nextInt(99999999);
		int batchCnt = random.nextInt(99999999);
		HashMap resultMap = new HashMap<String, Object>();
		resultMap.put("roleNm","전체");
		resultMap.put("cnt",onlineCnt+batchCnt);
		resultMap.put("flag","count");
		list.add(resultMap);

		resultMap = new HashMap<String, Object>();
		resultMap.put("roleNm","Online");
		resultMap.put("cnt",onlineCnt);
		resultMap.put("flag","count");
		list.add(resultMap);


		resultMap = new HashMap<String, Object>();
		resultMap.put("roleNm","Batch");
		resultMap.put("cnt",batchCnt);
		resultMap.put("flag","count");
		list.add(resultMap);

		return list;
	}

	/**
	 * <pre>
	 * 실시간-처리현황(관심)
	 * API ID : REST-R05-OP-02-00
	 * </pre>
	 *
	 * @param httpSession 세션
	 * @param comMessage 요청응답통신객체
	 * @param locale 다국어지원을 위한 로케일
	 * @param request the request
	 * @return ComMessage 통신객체
	 * @throws Exception the exception
	 * @throws Exception the exception
	 * @author isjang
     * @since version 1.0(2017.03)
     */
	@RequestMapping(
			value="/dashboard/ism/realtime/favorite-count",
			params={"method=GET"},
			method=RequestMethod.POST,
			headers="content-type=application/json")
	public @ResponseBody ComMessage< Map<String,Object>, List<Map> > getRealTimeFavoriteCount(
			HttpSession  httpSession,
			@RequestBody ComMessage< Map<String,Object>, List<Map> > comMessage,
			Locale locale, HttpServletRequest request) throws Exception, ControllerException {

		//----------------------------------------------------------------------------
		//여긴 비지니스 코드만 작성해라.
		//----------------------------------------------------------------------------
		{
			String errorCd = "";
			String errorMsg = "";

			Map params = comMessage.getRequestObject();
			if(params == null){
				params = new HashMap();
			}

			//--------------------------------------------------
			// 로그인사용자 정보 셋팅
			//--------------------------------------------------
			boolean checkSession = comMessage.getCheckSession();
			if(checkSession) {
				{
					User user = (User) httpSession.getAttribute("user");
					params.put("userId", user.getUserId());
					params.put("isInterfaceAdmin", user.getRole().getIsInterfaceAdmin());
				}
			}else{
				params.put("userId", comMessage.getUserId());
				params.put("isInterfaceAdmin", "Y");
			}

			User user = (User) httpSession.getAttribute("user");
			params.put("userId", user.getUserId());
			//--------------------------------------------------
			// Service Call
			//--------------------------------------------------
			List<Map> list= null;
			{
				//list = dashboardService.getRealTimeFavoriteCount(params);
				list = isTest ? getRealTimeFavoriteCount(params) : getRealTimeFavoriteCountReal(params);
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
				if(list == null || list.size() == 0) {
					errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.retrieve.none", locale);
					errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.retrieve.none", null, locale);
				}else{//성공 처리결과
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

	private List<Map> getRealTimeFavoriteCountReal(Map params) throws Exception, ControllerException {


		String BASIC_URL = null;
		Map envMap =  commonService.getEnvironmentalValues();
		List statsList = (List)envMap.get("system.ism.statistics.url");
		if(statsList != null && statsList.size()>0){
			BASIC_URL = (String) statsList.get(0);
		}else{
			throw new Exception(Util.join("대시보드 URL 이 없습니다."));
		}

		//2.URL 세팅
		//String fromDate = Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT).substring(0, 12) + "00";
		//String toDate = Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT).substring(0, 12) + "59";
		//2.URL 세팅
		String fromDate = (String)params.get("fromDate");
		String toDate = (String)params.get("toDate");
		String userId = (String)params.get("userId");
		String list = getFavoriteInterfaces(userId);

		List<Map> resultList = new ArrayList<Map>();

		if(Util.isEmpty(list)) {
			logger.debug("실시간처리현황(관심)조회:관심인터페이스가 존재하지 않아 처리하지 않음.");
			return resultList;
		}

		String url = Util.join(BASIC_URL , "/custom/dashboard/process/curProcCount?fromDate=",fromDate, "&toDate=",toDate, "&list=",list);  // 현재날짜로 호출

		//3.데이터 조회
		logger.debug("실시간처리현황(관심)조회시작:");
		List<Map> datas = restTemplate.getForObject(url, List.class);
		try{logger.debug(Util.join("실시간처리현황(관심)결과:", Util.toJSONString(datas))); }catch(Exception e){}

		//24시 기준 데이터 처리
		if(datas != null && datas.size() > 0){

			for (Map data : datas) {
				//test 이후 빼도됨
				String[] columns = {"interfaceNm", "interfaceId", "integrationId", "cnt"};
				checkRequired("지연인터페이스조회:/custom/dashboard/online/delay", columns , data);

				Map<String, Object> record = new HashMap<String, Object>();
				String interfaceNm = (String) data.get("interfaceNm");
				String interfaceId = (String) data.get("interfaceId");
				String integrationId = (String)data.get("integrationId");
				int cnt = (Integer)data.get("cnt");

				record.put("interfaceNm",interfaceNm);
				record.put("interfaceId",interfaceId);
				record.put("integrationId",integrationId);
				record.put("cnt", cnt);
				record.put("flag","");
				resultList.add(record);

			}


		}else{
			logger.debug("실시간처리현황(관심)조회:결과가 존재하지 않음.");
		}
		return resultList;

	}

	private String getFavoriteInterfaces(String userId) throws Exception {
		List<String> list = dashboardService.getFavoriteInterfaceList(userId);
		if(list == null || list.size() == 0) return null;
		String integrationIdList = list.get(0);
		for(int i=1; i < list.size(); i++) {
			integrationIdList = integrationIdList + "," + list.get(i);
			logger.debug("integrationIdList(" + i + "):"+ integrationIdList);
		}
		logger.debug("integrationIdList:"+ integrationIdList);
		return integrationIdList;
	}

	private List<Map> getRealTimeFavoriteCount(Map params) {
		List<Map> list = new ArrayList();
		Random random = new Random();
		int onlineCnt = random.nextInt(999);
		for(int i =0 ;i<5;i++){
			HashMap resultMap = new HashMap<String, Object>();
			resultMap.put("interfaceId",String.format("IM_SYS-HN_%04d", i));
			resultMap.put("interfaceNm",String.format("IM_SYS-HN_%04d", i));
			resultMap.put("integrationId",String.format("IM_SYS-HN_%04d", i));
			resultMap.put("cnt",i*onlineCnt*55);
			resultMap.put("flag","");
			list.add(resultMap);
		}

		return list;
	}

	/**
	 * <pre>
	 * 처리량 추이(전일/금일)
	 * API ID : REST-R08-OP-02-00
	 * </pre>
	 *
	 * @param httpSession 세션
	 * @param comMessage 요청응답통신객체
	 * @param locale 다국어지원을 위한 로케일
	 * @param request the request
	 * @return ComMessage 통신객체
	 * @throws Exception the exception
	 * @throws Exception the exception
	 * @author isjang
     * @since version 1.0(2017.03)
     */
	@RequestMapping(
			value="/dashboard/ism/stats/daily",
			params={"method=GET"},
			method=RequestMethod.POST,
			headers="content-type=application/json")
	public @ResponseBody ComMessage< Map<String,Object>, List<Map> > getCountStatsDaily(
			HttpSession  httpSession,
			@RequestBody ComMessage< Map<String,Object>, List<Map> > comMessage,
			Locale locale, HttpServletRequest request) throws Exception, ControllerException {

		//----------------------------------------------------------------------------
		//여긴 비지니스 코드만 작성해라.
		//----------------------------------------------------------------------------
		{
			String errorCd = "";
			String errorMsg = "";

			Map params = comMessage.getRequestObject();
			if(params == null){
				params = new HashMap();
			}

			//--------------------------------------------------
			// 로그인사용자 정보 셋팅
			//--------------------------------------------------
			boolean checkSession = comMessage.getCheckSession();
			if(checkSession) {
				{
					User user = (User) httpSession.getAttribute("user");
					params.put("userId", user.getUserId());
					params.put("isInterfaceAdmin", user.getRole().getIsInterfaceAdmin());
				}
			}else{
				params.put("userId", comMessage.getUserId());
				params.put("isInterfaceAdmin", "Y");
			}


			//--------------------------------------------------
			// Service Call
			//--------------------------------------------------
			List<Map> list = null;
			{
				//list = dashboardService.getCountStatsDaily(params);
				list = isTest ? getCountStatsDaily(params) : getCountStatsDailyReal(params);
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
				if(list == null || list.size() == 0) {
					errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.retrieve.none", locale);
					errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.retrieve.none", null, locale);
				}else{//성공 처리결과
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

	/**
	 * KYS
	 * @param params
	 * @return
	 * @throws Exception
	 * @throws ControllerException
	 */
	private List<Map> getCountStatsDailyReal(Map params) throws Exception, ControllerException {
		//1.기준일자 계산
		DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd"); // 사용자가 구하고자 하는 날짜의 포맷을 DateFormat으로 지정

		Calendar todayCal = Calendar.getInstance(); //  Calendar 클래스는 new로 생성하지 못하기 때문에 getInstance메소드로 객체를 생성
		String today = dateFormat.format(todayCal.getTime()); // 어제날짜

		Calendar yesterdayCal = Calendar.getInstance(); //  Calendar 클래스는 new로 생성하지 못하기 때문에 getInstance메소드로 객체를 생성
		yesterdayCal.add(Calendar.DATE, -1); // 하루전 날짜로 셋팅
		String yesterday = dateFormat.format(yesterdayCal.getTime()); // 어제날짜

		String BASIC_URL = null;
		Map envMap =  commonService.getEnvironmentalValues();
		List statsList = (List)envMap.get("system.ism.statistics.url");
		if(statsList != null && statsList.size()>0){
			BASIC_URL = (String) statsList.get(0);
		}else{
			throw new Exception(Util.join("대시보드 URL 이 없습니다."));
		}

		//2.URL 세팅
		String todayUrl = Util.join(BASIC_URL , "/custom/dashboard/process/hourProcCount?date=",today);  // 현재날짜로 호출
		String yesterdayUrl = Util.join(BASIC_URL , "/custom/dashboard/process/hourProcCount?processDate=", yesterday);  // 어제날짜로 호출

		//3.데이터 조회
		//3.1.오늘 데이터
		Map todayData = restTemplate.getForObject(todayUrl, Map.class);
		//3.2.어제 데이터
		Map yesterdayData = restTemplate.getForObject(yesterdayUrl, Map.class);

		List<Map> resultList = new ArrayList<Map>();
		//24시 기준 데이터 처리
		if(todayData != null && yesterdayData != null){

	    	for(int i = 0; i < 24; i ++){
	    		Map record = new HashMap<String, Object>();
	    		String hh = String.format("%02d", i);
	    		String [] cols = {hh};
	    		checkRequired("처리량추이(전일/금일)[/custom/dashboard/process/dayProcCount]:", cols, todayData);
	    		record.put("HOURS", hh);
	    		int tCount = 0;
	    		try{//레코드가 없거나 OutofIndex 예외발생시 또는 null 이면 그냥 0으로 세팅.
	    			Map todayRecord = (Map)todayData.get(hh);

	    			String [] cols2 = {"batchCnt","onlineCnt"};
		    		checkRequired("처리량추이(금일)[/custom/dashboard/process/dayProcCount]:", cols2, todayRecord);

	    			int batchCnt = (Integer)todayRecord.get("batchCnt");
	    			int onlineCnt = (Integer)todayRecord.get("onlineCnt");
	    			tCount = batchCnt + onlineCnt;
	    		}catch(Exception e){
	    			tCount = 0;
	    		}

	    		int yCount = 0;
	    		try{//레코드가 없거나 OutofIndex 예외발생시 또는 null 이면 그냥 0으로 세팅.
	    			Map yesterdayRecord = (Map)yesterdayData.get(hh);
	    			String [] cols2 = {"batchCnt","onlineCnt"};
		    		checkRequired("처리량추이(전일)[/custom/dashboard/process/dayProcCount]:", cols2, yesterdayRecord);

	    			int batchCnt = (Integer)yesterdayRecord.get("batchCnt");
	    			int onlineCnt = (Integer)yesterdayRecord.get("onlineCnt");
	    			yCount = batchCnt + onlineCnt;
	    		}catch(Exception e){
	    			yCount = 0;
	    		}
	    		record.put("D0", tCount);//today count
	    		record.put("D1", yCount);//yesterday count
	    		resultList.add(record);
	    	}
		}else{
			for(int i =0 ;i<24;i++){
				Map record = new HashMap<String, Object>();
				record.put("HOURS",String.format("%02d", i));
				record.put("D1",0);
				record.put("D0",0);
				resultList.add(record);
			}
		}
		return resultList;
	}


	private List<Map> getCountStatsDaily(Map params) {
		List<Map> list = new ArrayList();

		for(int i =0 ;i<24;i++){
			HashMap resultMap = new HashMap<String, Object>();
			resultMap.put("HOURS",String.format("%02d", i));
			resultMap.put("D1",i*1000);
			resultMap.put("D0",i*890);
			list.add(resultMap);
		}

		return list;
	}


	/**
	 * <pre>
	 * 처리량 추이(최근4개월)
	 * API ID : REST-R09-OP-02-00
	 * </pre>
	 *
	 * @param httpSession 세션
	 * @param comMessage 요청응답통신객체
	 * @param locale 다국어지원을 위한 로케일
	 * @param request the request
	 * @return ComMessage 통신객체
	 * @throws Exception the exception
	 * @throws Exception the exception
	 * @author isjang
     * @since version 1.0(2017.03)
     */
	@RequestMapping(
			value="/dashboard/ism/stats/monthly",
			params={"method=GET"},
			method=RequestMethod.POST,
			headers="content-type=application/json")
	public @ResponseBody ComMessage< Map<String,Object>, Map > getCountStatsMonthly(
			HttpSession  httpSession,
			@RequestBody ComMessage< Map<String,Object>, Map > comMessage,
			Locale locale, HttpServletRequest request) throws Exception, ControllerException {

		//----------------------------------------------------------------------------
		//여긴 비지니스 코드만 작성해라.
		//----------------------------------------------------------------------------
		{
			String errorCd = "";
			String errorMsg = "";

			Map params = comMessage.getRequestObject();
			if(params == null){
				params = new HashMap();
			}

			//--------------------------------------------------
			// 로그인사용자 정보 셋팅
			//--------------------------------------------------
			boolean checkSession = comMessage.getCheckSession();
			if(checkSession) {
				{
					User user = (User) httpSession.getAttribute("user");
					params.put("userId", user.getUserId());
					params.put("isInterfaceAdmin", user.getRole().getIsInterfaceAdmin());
				}
			}else{
				params.put("userId", comMessage.getUserId());
				params.put("isInterfaceAdmin", "Y");
			}


			//--------------------------------------------------
			// Service Call
			//--------------------------------------------------
			Map resultMap = null;
			{
				//resultMap = dashboardService.getCountStatsMonthly(params);
				resultMap = isTest ? getCountStatsMonthly(params) : getCountStatsMonthlyReal(params);
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
				//if(list == null || list.size() == 0) {
				if( resultMap == null || resultMap.size() == 0 ) {
					errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.retrieve.none", locale);
					errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.retrieve.none", null, locale);
				}else{//성공 처리결과
					errorCd = MessageSourceUtil.getErrorCd(messageSource, "success.cd.ok", locale);
					Object [] errorMsgParams = {resultMap.size()};
					errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "success.msg.retrieve.list.ok", errorMsgParams, locale);

					comMessage.setResponseObject(resultMap);
				}

				comMessage.setErrorCd(errorCd);
				comMessage.setErrorMsg(errorMsg);
			}

			return comMessage;
		}
	}

	private Map getCountStatsMonthlyReal(Map params)throws Exception, ControllerException{

		String BASIC_URL = null;
		Map envMap =  commonService.getEnvironmentalValues();
		List statsList = (List)envMap.get("system.ism.statistics.url");
		if(statsList != null && statsList.size()>0){
			BASIC_URL = (String) statsList.get(0);
		}else{
			throw new Exception(Util.join("대시보드 URL 이 없습니다."));
		}



		String url = Util.join(BASIC_URL , "/custom/dashboard/process/monthProcCount?months=4");  // 현재날짜로 호출

		//3.데이터 조회
		logger.debug("처리량추이(최근4개월)시작:");
		List datas = restTemplate.getForObject(url, List.class);
		try{logger.debug(Util.join("처리량추이(최근4개월)결과:", Util.toJSONString(datas))); }catch(Exception e){}

		Map resultMap = new LinkedHashMap();
		//24시 기준 데이터 처리
		if(datas != null){

			int m3Cnt = (Integer)datas.get(0);
			int m2Cnt = (Integer)datas.get(1);
			int m1Cnt = (Integer)datas.get(2);
			int tCnt = (Integer)datas.get(3);

			resultMap.put("M-3",m3Cnt);
			resultMap.put("M-2",m2Cnt);
			resultMap.put("M-1",m1Cnt);
			resultMap.put("T",tCnt);

		}else{
			resultMap.put("M-3",0);
			resultMap.put("M-2",0);
			resultMap.put("M-1",0);
			resultMap.put("T",0);
			logger.debug("처리량추이(최근4개월):결과가 존재하지 않음.");
		}
		return resultMap;

	}

	private Map getCountStatsMonthly(Map params) {
		LinkedHashMap resultMap = new LinkedHashMap<String, Object>();

		resultMap.put("M-3",2500000);
		resultMap.put("M-2",3200000);
		resultMap.put("M-1",1800000);
		resultMap.put("T",2200000);
		return resultMap;
	}

	/**
	 * <pre>
	 * 인터페이스 IF 최근 사용현황.
	 * API ID : REST-R10-OP-02-00-601
	 * </pre>
	 *
	 * @param httpSession 세션
	 * @param comMessage 요청응답통신객체
	 * @param locale 다국어지원을 위한 로케일
	 * @param request the request
	 * @return ComMessage 통신객체
	 * @throws Exception the exception
	 * @throws Exception the exception
	 * @author isjang
     * @since version 1.0(2017.03)
     */
	@RequestMapping(
			value="/dashboard/ism/stats/interface-used",
			params={"method=GET"},
			method=RequestMethod.POST,
			headers="content-type=application/json")
	public @ResponseBody ComMessage< Map<String,Object>, List<Map> > getCountStatsInterfaceUsed(
			HttpSession  httpSession,
			@RequestBody ComMessage< Map<String,Object>, List<Map> > comMessage,
			Locale locale, HttpServletRequest request) throws Exception, ControllerException {

		//----------------------------------------------------------------------------
		//여긴 비지니스 코드만 작성해라.
		//----------------------------------------------------------------------------
		{
			String errorCd = "";
			String errorMsg = "";

			Map params = comMessage.getRequestObject();
			if(params == null){
				params = new HashMap();
			}

			//--------------------------------------------------
			// 로그인사용자 정보 셋팅
			//--------------------------------------------------
			boolean checkSession = comMessage.getCheckSession();
			if(checkSession) {
				{
					User user = (User) httpSession.getAttribute("user");
					params.put("userId", user.getUserId());
					params.put("isInterfaceAdmin", user.getRole().getIsInterfaceAdmin());
				}
			}else{
				params.put("userId", comMessage.getUserId());
				params.put("isInterfaceAdmin", "Y");
			}


			//--------------------------------------------------
			// Service Call
			//--------------------------------------------------
			Map resultMap = null;
			List<Map> list = null;
			{
				//resultMap = dashboardService.getCountStatsYearly(params);

				list = isTest ? getCountStatsInterfaceUsed(params) : getCountStatsInterfaceUsedReal(params);

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
				if(list == null || list.size() == 0) {
					errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.retrieve.none", locale);
					errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.retrieve.none", null, locale);
				}else{//성공 처리결과
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


	private List<Map> getCountStatsInterfaceUsedReal(Map params) {
		List<Map> list = new ArrayList();
		Map resultMap = new HashMap<String, Object>();

		try {
			String callUrl  ="";
			Map envMap =  commonService.getEnvironmentalValues();
			String webUrl = "";
			List statsList = (List)envMap.get("system.ism.statistics.url");
			if(statsList != null && statsList.size()>0){
				webUrl = (String) statsList.get(0);
			}else{
				throw new Exception(Util.join("조회 URL 이 없습니다."));
			}
			callUrl = webUrl +"/custom/dashboard/process/rulecount";

			URL url = new URL(callUrl);

		    // 문자열로 URL 표현
			//System.out.println("URL====== :" + url.toExternalForm());
			logger.debug("\n[getCountStatsInterfaceUsed] URL :" + url.toExternalForm());

		    // HTTP Connection 구하기
		    HttpURLConnection conn = (HttpURLConnection) url.openConnection();

		    // 요청 방식 설정 ( GET or POST or .. 별도로 설정하지 않으면 GET 방식 )
		    conn.setRequestMethod("GET");
		    // 연결 타임아웃 설정
		    conn.setConnectTimeout(3000); // 3초
		    // 읽기 타임아웃 설정
		    conn.setReadTimeout(3000); // 3초

		    String line;

		    BufferedReader reader = null;
		    try {
			    reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			    while ((line = reader.readLine()) != null) {
			    	//System.out.println("\nline====== " + line.toString());
			    	resultMap = Util.jsonToMap(line);
			    }
		    }finally {
		    	if(reader != null) try{ reader.close();}catch(Exception e) {logger.error("reader close error:",e);}
		    }

		    if(resultMap != null) {
		    	Map map = new HashMap<String, Object>();
		    	logger.debug("\n[getCountStatsInterfaceUsed]======" + resultMap);
		    	logger.debug("\n[getCountStatsInterfaceUsed-A]======" + resultMap.get("A"));
		    	logger.debug("\n[getCountStatsInterfaceUsed-O]======" + resultMap.get("O"));
		    	logger.debug("\n[getCountStatsInterfaceUsed-B]======" + resultMap.get("B"));

		    	int totalCnt = 0;
		    	int useCnt = 0;
		    	int notUseCnt = 0;

		    	if(resultMap.get("A") != null) {
		    		map = Util.jsonToMap(Util.toJSONString(resultMap.get("A")));
		    		map.put("roleNm", "전체");
		    		if(map.get("rule") != null) totalCnt = (Integer)map.get("rule");
		    		else totalCnt = 0;
		    		if(map.get("proc") != null) useCnt = (Integer)map.get("proc");
		    		else useCnt = 0;
		    		notUseCnt = totalCnt-useCnt;
		    		map.put("totalCnt", totalCnt);
		    		map.put("useCnt", useCnt);
		    		map.put("notUseCnt", notUseCnt);

		    		list.add(map);
		    	}

		    	if(resultMap.get("O") != null) {
		    		map = Util.jsonToMap(Util.toJSONString(resultMap.get("O")));
		    		map.put("roleNm", "Online");
		    		if(map.get("rule") != null) totalCnt = (Integer)map.get("rule");
		    		else totalCnt = 0;
		    		if(map.get("proc") != null) useCnt = (Integer)map.get("proc");
		    		else useCnt = 0;
		    		notUseCnt = totalCnt-useCnt;
		    		map.put("totalCnt", totalCnt);
		    		map.put("useCnt", useCnt);
		    		map.put("notUseCnt", notUseCnt);

		    		list.add(map);
		    	}

		    	if(resultMap.get("O") != null) {
		    		map = Util.jsonToMap(Util.toJSONString(resultMap.get("B")));
		    		map.put("roleNm", "Batch");
		    		if(map.get("rule") != null) totalCnt = (Integer)map.get("rule");
		    		else totalCnt = 0;
		    		if(map.get("proc") != null) useCnt = (Integer)map.get("proc");
		    		else useCnt = 0;
		    		notUseCnt = totalCnt-useCnt;
		    		map.put("totalCnt", totalCnt);
		    		map.put("useCnt", useCnt);
		    		map.put("notUseCnt", notUseCnt);

		    		list.add(map);
		    	}
		    }
		}catch(Throwable e){
			return list;
		}

		return list;
	}


	private List<Map> getCountStatsInterfaceUsed(Map params) {
		List<Map> list = new ArrayList();

		HashMap resultMap = new HashMap<String, Object>();

		resultMap.put("roleNm","전체");
		resultMap.put("totalCnt",1000);
		resultMap.put("useCnt",800);
		resultMap.put("notUseCnt",800);

		list.add(resultMap);


		resultMap = new HashMap<String, Object>();

		resultMap.put("roleNm","Online");
		resultMap.put("totalCnt",500);
		resultMap.put("useCnt",400);
		resultMap.put("notUseCnt",400);

		list.add(resultMap);


		resultMap = new HashMap<String, Object>();

		resultMap.put("roleNm","Batch");
		resultMap.put("totalCnt",500);
		resultMap.put("useCnt",400);
		resultMap.put("notUseCnt",300);

		list.add(resultMap);



		return list;
	}

}

/**
 대시보드

인아웃 포멧을 정확하게 확정해라.
모두 GET 방식인건지 ?
파마메터는 모두 URL 로 전달하는 것인지 ?

1.처리건수(인터페이스) : getDailyThroughput : 고영선 완료

	전체 Online Batch
------------------------------------
전체	0	0	0
성공	0	0	0
오류	0	0	0
금일	0	0	0
평균 	0	0	0


2.오류인터페이스 : getErrorListTop : 미완료
	인터페이스명 인터페이스ID 오류내역
--------------------------------------------
	INTF1		INTF1		sql error
가장 최근 에러 인터페이스 5건 보여주는 서비스 필요
현재 서비스 없음


3.지연인터페이스: getDelayListTop : 미완료
	인터페이스명 인터페이스ID 처리시간
--------------------------------------------
	INTF1		INTF1		100
현재 응답 포멧이 없음.


4.실시간 - 처리현황(전체) : getRealTimeTotalCount : 개발불가

호출시마다 현재 시간의배치, 온라인 건수가 1건씩 오면 됨
예) {"online":1000,"batch":1000}
현재 분별 처리현항은 하루치가 모두 오고 온라인 배치를 나눠 호출하므로 데이터 가공이 불가함.

5.실시간- 처리현황(관심인터페이스) : getRealTimeFavoriteCount : 미개발 , 현재 서비스로 가능한가 검토
호출 인터페이스ID 의 건수
[{"interfaceId":"xxxxxx","cnt":100},
 {"interfaceId":"xxxxxx","cnt":100}
]
4번과 동일 : 호출시점의 데이터 1건 만 필요

6.처리량추이 (전일 /금일) : getCountStatsDaily : 개발은 했으나 논리적으로 맞지 않는다.
시간별 처리 건수 조회 로 처리하면 되나확인  필요
해당시간에 처리 건수가 없더라도 데이터를 주는지 확인해 보기
온라인과 배치건이 모두 일자가 동일하게 오는 것인지?
동일하지 않으면 전체를 맞게 구할 수 없음
하나의 서비스로 구현하는 건 어떨런지 ? 아니면 전체주는 옵션도 넣어 주던지 ?

7.처리량추가(최근4개월) :getCountStatsMonthly :
4달까지 나오도록 변경 필요, 원별 건수
요청 : ?type=B&param=month3 (요거 맞나?)
응답 : {"201701":1000,"201702":1000,"201703":1000,"201704":1000}

8.인터페이스 사용현황 :
이건 내용이 무엇인지
인터페이스 정보의 사용미사용에 따라 보여주면 되는 것인지?
실제 거래 된 내역이 있는지 판단하는 것인지 ?

*/
