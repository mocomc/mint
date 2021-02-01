package pep.per.mint.front.controller.op;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
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
import pep.per.mint.common.util.Util;
import pep.per.mint.front.exception.ControllerException;
import pep.per.mint.front.util.FieldCheckUtil;
import pep.per.mint.front.util.MessageSourceUtil;

@Controller
@RequestMapping("/op")
@SuppressWarnings({ "rawtypes", "unchecked" })
public class LogIFController {

	private static final Logger logger = LoggerFactory.getLogger(LogIFController.class);

	//비지니스처리중 프론트까지 전달할 메시지들을 참조할 수 있는 다국어지원용 번들 객체
	@Autowired
	ReloadableResourceBundleMessageSource messageSource;


	// 서블리컨텍스트 관련정보 참조를 위한 객체
	// 예를 들어 servletContext를 이용하여 웹어플리케이션이
	// 배포퇸 컨텍스트 루트위치 등을 얻어올 수 있다.
	@Autowired
	private ServletContext servletContext;


	/**
	 * <pre>
	 * ISM 온라인처리결과 [REST-R01-OP-01-01-601]
	 * </pre>
	 * @param httpSession
	 * @param comMessage
	 * @param locale
	 * @param request
	 * @return
	 * @throws Exception
	 * @throws ControllerException
	 */
	@RequestMapping(value = "/ism/custom/log/online", params = "method=POST", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<Map<String,String>, Map<String, Object>> getISMLog_Online(
			HttpSession httpSession,
			@RequestBody ComMessage<Map<String,String>, Map<String, Object>> comMessage,
			Locale locale,
			HttpServletRequest request
			)throws Exception, ControllerException {

		{
			String errorCd = "";
			String errorMsg = "";

			Map<String,String> params = comMessage.getRequestObject();

			//----------------------------------------------------------------------------
			// 필수필드 체크하기
			//----------------------------------------------------------------------------
			{
				String []checkFields = {"callUrl", "processDate"};
				FieldCheckUtil.checkRequired("LogIFController.getISMLog_Online",checkFields , params, messageSource, locale);
			}

			int page = Util.isEmpty(params.get("page")) ? 1:Integer.parseInt(params.get("page"));
			String processDate = Util.isEmpty(params.get("processDate")) ? "":params.get("processDate");
			String stTime = Util.isEmpty(params.get("stTime")) ? "0000":params.get("stTime");
			String edTime = Util.isEmpty(params.get("edTime")) ? "2359":params.get("edTime");
			String integrationServiceId = Util.isEmpty(params.get("integrationServiceId")) ? "":params.get("integrationServiceId");
			String error = Util.isEmpty(params.get("error")) ? "":params.get("error");
			String callUrl = Util.isEmpty(params.get("callUrl")) ? "":params.get("callUrl");
			String globalId = Util.isEmpty(params.get("globalId")) ? "":params.get("globalId");
			String modelId = Util.isEmpty(params.get("modelId")) ? "":params.get("modelId");
			String sendSystemId = Util.isEmpty(params.get("sendsystemId")) ? "":params.get("sendsystemId");
			String recvSystemId = Util.isEmpty(params.get("recvsystemId")) ? "":params.get("recvsystemId");
			String elapsedTime = Util.isEmpty(params.get("totalTime")) ? "":params.get("totalTime");
			String userId = Util.isEmpty(params.get("userId")) ? "":params.get("userId");

			//----------------------------------------------------------------------------
			//실행
			//----------------------------------------------------------------------------

			List<Map<String, Object>> content = new ArrayList<Map<String, Object>>();
			Map<String, Object> map = new HashMap();
			try{
				{

					if(processDate != null && !processDate.equals("")) {
						/* 외부에서 ISM 호출할때 사용
						callUrl += "?page="+page+"&processDate="+"20170227"+"&stTime="+stTime+"&edTime="+edTime+"&integrationServiceId="+integrationServiceId+"&globalId="+globalId+"&error="+error
								+"&modelId="+modelId+"&sendsystemNm="+sendsystemNm+"&recvsystemNm="+recvsystemNm+"&elapsedTime="+elapsedTime;*/
						// 현대해상 안에서 사용
						callUrl += "?page="+page+"&processDate="+processDate+"&stTime="+stTime+"&edTime="+edTime+"&integrationServiceId="+integrationServiceId+"&globalId="+globalId+"&error="+error
								+"&modelId="+modelId+"&sendSystemId="+sendSystemId+"&recvSystemId="+recvSystemId+"&elapsedTime="+elapsedTime+"&userId="+userId;
						URL url = new URL(callUrl);

					    // 문자열로 URL 표현
						logger.debug("URL :" + url.toExternalForm());

					    // HTTP Connection 구하기
					    HttpURLConnection conn = (HttpURLConnection) url.openConnection();

					    // 요청 방식 설정 ( GET or POST or .. 별도로 설정하지 않으면 GET 방식 )
					    conn.setRequestMethod("GET");
					    // 연결 타임아웃 설정
					    conn.setConnectTimeout(30000); // 30초
					    // 읽기 타임아웃 설정
					    conn.setReadTimeout(30000); // 30초

					    String line;

					    BufferedReader reader = null;
					    try {
						    reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
						    while ((line = reader.readLine()) != null) {
						      map = Util.jsonToMap(line);
						    }
					    }finally {
					    	if(reader != null) try{ reader.close();}catch(Exception e) {logger.error("reader close error:",e);}
					    }


					    if(map != null && !Util.isEmpty(map.get("list"))) {
					    	//content = (List<Map<String, Object>>) map.get("list");
					    	//content = <Map<String, Object>> map;
					    	logger.debug("\nmap====== " + map);
					    }

					}
				}

				//--------------------------------------------------
				// 서비스 처리 종료시간을 얻어 CM에 세팅한다.
				//--------------------------------------------------
				{
					comMessage.setEndTime(Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI));
				}

				//--------------------------------------------------
				// 통신메시지에 처리결과 코드/메시지를 등록한다.
				//--------------------------------------------------
				{


					errorCd = MessageSourceUtil.getErrorCd(messageSource, "success.cd.ok", locale);
					errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "success.msg.ok", null, locale);
					comMessage.setResponseObject(map);

					comMessage.setErrorCd(errorCd);
					comMessage.setErrorMsg(errorMsg);
					return comMessage;
				}
			}catch(Throwable e){
				errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.retrieve.none", locale);
				String errorDetail = e.getMessage();
				String[] errorMsgParams = {"LogIFController.getISMLog_Online",errorDetail};
				errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.retrieve.none", errorMsgParams, locale);
				throw new ControllerException(errorCd, errorMsg, e);
			}

		}

	}

	/**
	 * <pre>
	 * ISM 온라인처리결과 상세 [REST-R01-OP-01-01-602]
	 * </pre>
	 * @param httpSession
	 * @param comMessage
	 * @param locale
	 * @param request
	 * @return
	 * @throws Exception
	 * @throws ControllerException
	 */
	@RequestMapping(value = "/ism/log/online/detail", params = "method=POST", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<Map<String,String>, Map<String, Object>> getISMLogDetail_Online(
			HttpSession httpSession,
			@RequestBody ComMessage<Map<String,String>, Map<String, Object>> comMessage,
			Locale locale,
			HttpServletRequest request
			)throws Exception, ControllerException {

		{
			String errorCd = "";
			String errorMsg = "";

			Map<String,String> params = comMessage.getRequestObject();

			//----------------------------------------------------------------------------
			// 필수필드 체크하기
			//----------------------------------------------------------------------------
			{
				String []checkFields = {"callUrl", "processDate", "integrationServiceId", "globalId"};
				FieldCheckUtil.checkRequired("LogIFController.getISMLog_Online",checkFields , params, messageSource, locale);
			}

			String processDate = params.get("processDate");
			String globalId = params.get("globalId");
			String integrationServiceId = params.get("integrationServiceId");
			String callUrl = params.get("callUrl");
			//String resultCode = params.get("resultCode");


			//----------------------------------------------------------------------------
			//실행
			//----------------------------------------------------------------------------
			Map<String, Object> content = null;
			try{
				{

					if(processDate != null && !processDate.equals("")) {
						/* 내부에서 테스트할때 사용
						callUrl += "?processDate="+"20170227"+"&integrationServiceId="+integrationServiceId+"&globalId="+globalId;*/
						callUrl += "?processDate="+processDate+"&integrationServiceId="+integrationServiceId+"&globalId="+globalId;
						URL url = new URL(callUrl);

					    // 문자열로 URL 표현
					    logger.debug("URL :" + url.toExternalForm());

					    // HTTP Connection 구하기
					    HttpURLConnection conn = (HttpURLConnection) url.openConnection();

					    // 요청 방식 설정 ( GET or POST or .. 별도로 설정하지않으면 GET 방식 )
					    conn.setRequestMethod("GET");
					    // 연결 타임아웃 설정
					    conn.setConnectTimeout(30000); // 30초
					    // 읽기 타임아웃 설정
					    conn.setReadTimeout(30000); // 30초

						//Map<String, Object> map = new HashMap();

					    BufferedReader reader = null;
					    try {
					    	reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
					    	//map = Util.jsonToMap(reader.readLine());
					    	content = Util.jsonToMap(reader.readLine());
					    }finally {
					    	if(reader != null) try{ reader.close();}catch(Exception e) {logger.error("reader close error:",e);}
					    }
					    /*if(map != null && !Util.isEmpty(map.get("list"))) {
					    	content = (List<Map<String, Object>>) map.get("list");
					    	logger.debug("detail list" + map.get("list") );*/

					    logger.debug("\ncontent====== " + content);

					}
				}

				//--------------------------------------------------
				// 서비스 처리 종료시간을 얻어 CM에 세팅한다.
				//--------------------------------------------------
				{
					comMessage.setEndTime(Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI));
				}

				//--------------------------------------------------
				// 통신메시지에 처리결과 코드/메시지를 등록한다.
				//--------------------------------------------------
				{

					errorCd = MessageSourceUtil.getErrorCd(messageSource, "success.cd.ok", locale);
					errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "success.msg.ok", null, locale);
					comMessage.setResponseObject(content);

					comMessage.setErrorCd(errorCd);
					comMessage.setErrorMsg(errorMsg);
					return comMessage;
				}
			}catch(Throwable e){
				errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.retrieve.none", locale);
				String errorDetail = e.getMessage();
				String[] errorMsgParams = {"LogIFController.getISMLog_Online",errorDetail};
				errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.retrieve.none", errorMsgParams, locale);
				throw new ControllerException(errorCd, errorMsg, e);
			}

		}

	}


	/**
	 * <pre>
	 * ISM 배치 처리결과 [REST-R01-OP-01-01-603]
	 * </pre>
	 * @param httpSession
	 * @param comMessage
	 * @param locale
	 * @param request
	 * @return
	 * @throws Exception
	 * @throws ControllerException
	 */
	@RequestMapping(value = "/ism/log/batch", params = "method=POST", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<Map<String,String>, Map<String, Object>> getISMLog_Batch(
			HttpSession httpSession,
			@RequestBody ComMessage<Map<String,String>, Map<String, Object>> comMessage,
			Locale locale,
			HttpServletRequest request
			)throws Exception, ControllerException {

		{
			String errorCd = "";
			String errorMsg = "";

			Map<String,String> params = comMessage.getRequestObject();

			//----------------------------------------------------------------------------
			// 필수필드 체크하기
			//----------------------------------------------------------------------------
			{
				String []checkFields = {"callUrl", "processDate"};
				FieldCheckUtil.checkRequired("LogIFController.getISMLog_Batch",checkFields , params, messageSource, locale);
			}

			int page = Util.isEmpty(params.get("page")) ? 1:Integer.parseInt(params.get("page"));
			String processDate = Util.isEmpty(params.get("processDate")) ? "":params.get("processDate");
			String stTime = Util.isEmpty(params.get("stTime")) ? "0000":params.get("stTime");
			String edTime = Util.isEmpty(params.get("edTime")) ? "2359":params.get("edTime");
			String integrationServiceId = Util.isEmpty(params.get("integrationServiceId")) ? "":params.get("integrationServiceId");
			String result = Util.isEmpty(params.get("result")) ? "":params.get("result");
			String callUrl = Util.isEmpty(params.get("callUrl")) ? "":params.get("callUrl");
			String batchId = Util.isEmpty(params.get("batchId")) ? "":params.get("batchId" );
			String sendFileName = Util.isEmpty(params.get("sendFileName")) ? "":params.get("sendFileName");
			String Type = Util.isEmpty(params.get("Type")) ? "":params.get("Type");
			String sendsystemId = Util.isEmpty(params.get("sendsystemId")) ? "":params.get("sendsystemId");
			String recvsystemId = Util.isEmpty(params.get("recvsystemId")) ? "":params.get("recvsystemId");
			String totalTime = Util.isEmpty(params.get("totalTime")) ? "":params.get("totalTime");
			String eaiHost = Util.isEmpty(params.get("eaiHost")) ? "":params.get("eaiHost");

			//----------------------------------------------------------------------------
			//실행
			//----------------------------------------------------------------------------

			List<Map<String, Object>> content = new ArrayList<Map<String, Object>>();
			Map<String, Object> map = new HashMap();
			try{
				{

					if(processDate != null && !processDate.equals("")) {
						/* 내부에서 테스트시 사용
						callUrl += "?page="+page+"&processDate="+"20170302"+"&stTime="+stTime+"&edTime="+edTime+"&integrationServiceId="+integrationServiceId+"&batchId="+batchId+"&modelId="+modelId
								+"&result="+result+"&sendFileName="+sendFileNm+"&sendsystemNm="+sendsystemNm+"&recvsystemNm="+recvsystemNm+"&totalTime="+totalTime+"&batchType="+batchType;*/
						callUrl += "?page="+page+"&processDate="+processDate+"&stTime="+stTime+"&edTime="+edTime+"&integrationServiceId="+integrationServiceId+"&batchId="+batchId
								+"&result="+result+"&sendFileName="+sendFileName+"&Type="+Type+"&sendsystemId="+sendsystemId+"&recvsystemId="+recvsystemId+"&totalTime="+totalTime+"&eaiHost="+eaiHost;
						URL url = new URL(callUrl);

					    // 문자열로 URL 표현
						logger.debug("URL :" + url.toExternalForm());

					    // HTTP Connection 구하기
					    HttpURLConnection conn = (HttpURLConnection) url.openConnection();

					    // 요청 방식 설정 ( GET or POST or .. 별도로 설정하지 않으면 GET 방식 )
					    conn.setRequestMethod("GET");
					    // 연결 타임아웃 설정
					    conn.setConnectTimeout(30000); // 30초
					    // 읽기 타임아웃 설정
					    conn.setReadTimeout(30000); // 30초

					    String line;

					    BufferedReader reader = null;
					    try {
						    reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
						    while ((line = reader.readLine()) != null) {
						      map = Util.jsonToMap(line);
						    }
					    }finally {
					    	if(reader != null) try{ reader.close();}catch(Exception e) {logger.error("reader close error:",e);}
					    }

					    if(map != null && !Util.isEmpty(map.get("list"))) {
					    	//content = (List<Map<String, Object>>) map.get("list");
					    	//content = <Map<String, Object>> map;
					    	logger.debug("\nmap====== " + map);
					    }
					}
				}

				//--------------------------------------------------
				// 서비스 처리 종료시간을 얻어 CM에 세팅한다.
				//--------------------------------------------------
				{
					comMessage.setEndTime(Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI));
				}

				//--------------------------------------------------
				// 통신메시지에 처리결과 코드/메시지를 등록한다.
				//--------------------------------------------------
				{

					errorCd = MessageSourceUtil.getErrorCd(messageSource, "success.cd.ok", locale);
					errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "success.msg.ok", null, locale);
					comMessage.setResponseObject(map);

					comMessage.setErrorCd(errorCd);
					comMessage.setErrorMsg(errorMsg);
					return comMessage;
				}
			}catch(Throwable e){
				errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.retrieve.none", locale);
				String errorDetail = e.getMessage();
				String[] errorMsgParams = {"LogIFController.getISMLog_Batch",errorDetail};
				errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.retrieve.none", errorMsgParams, locale);
				throw new ControllerException(errorCd, errorMsg, e);
			}
		}
	}



	/**
	 * <pre>
	 * ISM 배치 처리결과 상세 [REST-R01-OP-01-01-604]
	 * </pre>
	 * @param httpSession
	 * @param comMessage
	 * @param locale
	 * @param request
	 * @return
	 * @throws Exception
	 * @throws ControllerException
	 */
	@RequestMapping(value = "/ism/log/batch/detail", params = "method=POST", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<Map<String,String>, List<Map<String, Object>>> getISMLogDetail_Batch(
			HttpSession httpSession,
			@RequestBody ComMessage<Map<String,String>, List<Map<String, Object>>> comMessage,
			Locale locale,
			HttpServletRequest request
			)throws Exception, ControllerException {

		{
			String errorCd = "";
			String errorMsg = "";

			Map<String,String> params = comMessage.getRequestObject();

			//----------------------------------------------------------------------------
			// 필수필드 체크하기
			//----------------------------------------------------------------------------
			{
				String []checkFields = {"callUrl", "processDate", "integrationServiceId", "batchId"};
				FieldCheckUtil.checkRequired("LogIFController.getISMLog_Batch",checkFields , params, messageSource, locale);
			}

			String processDate = params.get("processDate");
			String batchId = params.get("batchId");
			String integrationServiceId = params.get("integrationServiceId");
			String callUrl = params.get("callUrl");
			//String resultCode = params.get("resultCode");


			//----------------------------------------------------------------------------
			//실행
			//----------------------------------------------------------------------------
			List<Map<String, Object>> content = new ArrayList<Map<String, Object>>();
			try{
				{

					if(processDate != null && !processDate.equals("")) {
						/* 내부에서 테스트시 사용
						callUrl += "?processDate="+"20170302"+"&integrationServiceId="+integrationServiceId+"&batchId="+batchId;*/
						callUrl += "?processDate="+processDate+"&integrationServiceId="+integrationServiceId+"&batchId="+batchId;
						URL url = new URL(callUrl);

					    // 문자열로 URL 표현
					    logger.debug("URL :" + url.toExternalForm());

					    // HTTP Connection 구하기
					    HttpURLConnection conn = (HttpURLConnection) url.openConnection();

					    // 요청 방식 설정 ( GET or POST or .. 별도로 설정하지않으면 GET 방식 )
					    conn.setRequestMethod("GET");
					    // 연결 타임아웃 설정
					    conn.setConnectTimeout(30000); // 30초
					    // 읽기 타임아웃 설정
					    conn.setReadTimeout(30000); // 30초

						Map<String, Object> map = new HashMap();

					    BufferedReader reader = null;
					    try {
						    reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
						    map = Util.jsonToMap(reader.readLine());
					    }finally {
					    	if(reader != null) try{ reader.close();}catch(Exception e) {logger.error("reader close error:",e);}
					    }

					    if(map != null && !Util.isEmpty(map.get("list"))) {
					    	content = (List<Map<String, Object>>) map.get("list");

					    	logger.debug("detail list" + map.get("list") );
					    	logger.debug("\ncontent====== " + content);
					    }


					}
				}

				//--------------------------------------------------
				// 서비스 처리 종료시간을 얻어 CM에 세팅한다.
				//--------------------------------------------------
				{
					comMessage.setEndTime(Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI));
				}

				//--------------------------------------------------
				// 통신메시지에 처리결과 코드/메시지를 등록한다.
				//--------------------------------------------------
				{

					errorCd = MessageSourceUtil.getErrorCd(messageSource, "success.cd.ok", locale);
					errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "success.msg.ok", null, locale);
					comMessage.setResponseObject(content);

					comMessage.setErrorCd(errorCd);
					comMessage.setErrorMsg(errorMsg);
					return comMessage;
				}
			}catch(Throwable e){
				errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.retrieve.none", locale);
				String errorDetail = e.getMessage();
				String[] errorMsgParams = {"LogIFController.getISMLog_Batch",errorDetail};
				errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.retrieve.none", errorMsgParams, locale);
				throw new ControllerException(errorCd, errorMsg, e);
			}

		}

	}


	/**
	 * <pre>
	 * ISM 온라인 처리결과 전문보기 팝업 [REST-R01-OP-01-01-605]
	 * </pre>
	 * @param httpSession
	 * @param comMessage
	 * @param locale
	 * @param request
	 * @return
	 * @throws Exception
	 * @throws ControllerException
	 */
	@RequestMapping(value = "/ism/log/online/message", params = "method=POST", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<Map<String,String>, Map<String, Object>> getISMLogOnline_Popup(
			HttpSession httpSession,
			@RequestBody ComMessage<Map<String,	String>, Map<String, Object>> comMessage,
			Locale locale,
			HttpServletRequest request
			)throws Exception, ControllerException {

		{
			String errorCd = "";
			String errorMsg = "";

			Map<String,String> params = comMessage.getRequestObject();

			//----------------------------------------------------------------------------
			// 필수필드 체크하기
			//----------------------------------------------------------------------------
			{
				String []checkFields = {"callUrl", "processDate", "integrationServiceId", "stepIndex", "id", "globalId", "serverName"};
				FieldCheckUtil.checkRequired("LogIFController.getISMLogOnline_Popup",checkFields , params, messageSource, locale);
			}

			String processDate = params.get("processDate");
			String stepIndex = params.get("stepIndex");
			String integrationServiceId = params.get("integrationServiceId");
			String id = params.get("id");
			String globalId = params.get("globalId");
			String serverName = params.get("serverName");
			String callUrl = params.get("callUrl");
			//String resultCode = params.get("resultCode");


			//----------------------------------------------------------------------------
			//실행
			//----------------------------------------------------------------------------
			List<Map<String, Object>> content = new ArrayList<Map<String, Object>>();
			Map<String, Object> map = new HashMap();

			try{
				{
					if(processDate != null && !processDate.equals("")) {
						/* 내부에서 테스트시 사용
						callUrl += "?id="+id+"&processDate="+"20170302"+"&integrationServiceId="+integrationServiceId+"&stepIndex="+stepIndex;*/
						callUrl += "?id="+id+"&processDate="+processDate+"&integrationServiceId="+integrationServiceId+"&stepIndex="+stepIndex+"&globalId="+globalId+"&serverName="+serverName;
						URL url = new URL(callUrl);

					    // 문자열로 URL 표현
					    logger.debug("URL :" + url.toExternalForm());

					    // HTTP Connection 구하기
					    HttpURLConnection conn = (HttpURLConnection) url.openConnection();

					    // 요청 방식 설정 ( GET or POST or .. 별도로 설정하지않으면 GET 방식 )
					    conn.setRequestMethod("GET");
					    // 연결 타임아웃 설정
					    conn.setConnectTimeout(30000); // 30초
					    // 읽기 타임아웃 설정
					    conn.setReadTimeout(30000); // 30초

						//Map<String, Object> map = new HashMap();
					    /*BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
					    map = Util.jsonToMap(reader.readLine());
					    reader.close();*/

						BufferedInputStream in = null;
						ByteArrayOutputStream out = null;
						try {
							in = new BufferedInputStream(conn.getInputStream());
							int readByteSize;
							byte[] data = new byte[1024];
							out = new ByteArrayOutputStream();
							while((readByteSize = in.read(data)) != -1) {
								out.write(data, 0, readByteSize);
							}

							String ismdata1 = out.toString("MS949");
							logger.debug("responseStr======" + ismdata1);
							map.put("resStr", ismdata1);
						}finally {
							if(out != null) try{ out.close();}catch(Exception e) {logger.error("out close error:",e);}
							if(in != null) try{ in.close();}catch(Exception e) {logger.error("in close error:",e);}
						}
					}
				}


				//--------------------------------------------------
				// 서비스 처리 종료시간을 얻어 CM에 세팅한다.
				//--------------------------------------------------
				{
					comMessage.setEndTime(Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI));
				}

				//--------------------------------------------------
				// 통신메시지에 처리결과 코드/메시지를 등록한다.
				//--------------------------------------------------
				{

					errorCd = MessageSourceUtil.getErrorCd(messageSource, "success.cd.ok", locale);
					errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "success.msg.ok", null, locale);
					comMessage.setResponseObject(map);

					comMessage.setErrorCd(errorCd);
					comMessage.setErrorMsg(errorMsg);
					return comMessage;
				}
			}catch(Throwable e){
				errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.retrieve.none", locale);
				String errorDetail = e.getMessage();
				String[] errorMsgParams = {"LogIFController.getISMLogOnline_Popup",errorDetail};
				errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.retrieve.none", errorMsgParams, locale);
				throw new ControllerException(errorCd, errorMsg, e);
			}

		}

	}


     /**
	 * <pre>
	 * ISM 컴포넌트 [REST-R06-OP-01-01-600]
	 * </pre>
	 * @param httpSession
	 * @param comMessage
	 * @param locale
	 * @param request
	 * @return
	 * @throws Exception
	 * @throws ControllerException
	 **/
	@RequestMapping(value = "/ism/log/modelId", params = "method=GET", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<Map<String,String>, List<Map<String, Object>>> getISMLog_ModelId(
			HttpSession httpSession,
			@RequestBody ComMessage<Map<String,String>, List<Map<String, Object>>> comMessage,
			Locale locale,
			HttpServletRequest request
			)throws Exception, ControllerException {

		{
			String errorCd = "";
			String errorMsg = "";

			Map<String,String> params = comMessage.getRequestObject();

			//----------------------------------------------------------------------------
			// 필수필드 체크하기
			//----------------------------------------------------------------------------
			String modelUrl = params.get("modelUrl");

			//----------------------------------------------------------------------------
			//실행
			//----------------------------------------------------------------------------
			List<Map<String, Object>> content = new ArrayList<Map<String, Object>>();
			try{
				{
						URL url = new URL(modelUrl);

					    // 문자열로 URL 표현
					    logger.debug("URL :" + url.toExternalForm());

					    // HTTP Connection 구하기
					    HttpURLConnection conn = (HttpURLConnection) url.openConnection();

					    // 요청 방식 설정 ( GET or POST or .. 별도로 설정하지않으면 GET 방식 )
					    conn.setRequestMethod("GET");
					    // 연결 타임아웃 설정
					    conn.setConnectTimeout(30000); // 30초
					    // 읽기 타임아웃 설정
					    conn.setReadTimeout(30000); // 30초

					    String line;
					    BufferedReader reader = null;
					    try{
					    	reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
						    while ((line = reader.readLine()) != null) {
						      content = (List<Map<String, Object>>)Util.jsonToObject(line);
						    }
				        }finally{
				        	if(reader != null) try{ reader.close();}catch(Exception e) {logger.error("reader close error:",e);}
				        }
				}

				//--------------------------------------------------
				// 서비스 처리 종료시간을 얻어 CM에 세팅한다.
				//--------------------------------------------------
				{
					comMessage.setEndTime(Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI));
				}

				//--------------------------------------------------
				// 통신메시지에 처리결과 코드/메시지를 등록한다.
				//--------------------------------------------------
				{

					errorCd = MessageSourceUtil.getErrorCd(messageSource, "success.cd.ok", locale);
					errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "success.msg.ok", null, locale);
					comMessage.setResponseObject(content);

					comMessage.setErrorCd(errorCd);
					comMessage.setErrorMsg(errorMsg);
					return comMessage;
				}
			}catch(Throwable e){
				errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.retrieve.none", locale);
				String errorDetail = e.getMessage();
				String[] errorMsgParams = {"LogIFController.getISMLog_Batch",errorDetail};
				errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.retrieve.none", errorMsgParams, locale);
				throw new ControllerException(errorCd, errorMsg, e);
			}
		}
	}


    /**
	 * <pre>
	 * ISM 온라인처리결과 StackTrace 팝업 [REST-R01-OP-01-01-606]
	 * </pre>
	 * @param httpSession
	 * @param comMessage
	 * @param locale
	 * @param request
	 * @return
	 * @throws Exception
	 * @throws ControllerException
	 **/
	@RequestMapping(value = "/ism/log/online/stacktrace", params = "method=POST", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<Map<String,String>, Map<String, Object>> getISMLogOnline_Popup_Stack(
			HttpSession httpSession,
			@RequestBody ComMessage<Map<String,String>, Map<String, Object>> comMessage,
			Locale locale,
			HttpServletRequest request
			)throws Exception, ControllerException {

		{
			String errorCd = "";
			String errorMsg = "";

			Map<String,String> params = comMessage.getRequestObject();

			//----------------------------------------------------------------------------
			// 필수필드 체크하기
			//----------------------------------------------------------------------------
			{
				String []checkFields = {"callUrl", "processDate", "integrationServiceId", "stepIndex", "id"};
				FieldCheckUtil.checkRequired("LogIFController.getISMLogOnline_Popup_Stack",checkFields , params, messageSource, locale);
			}

			String processDate = params.get("processDate");
			String stepIndex = params.get("stepIndex");
			String integrationServiceId = params.get("integrationServiceId");
			String globalId = params.get("globalId");
			String callUrl = params.get("callUrl");

			//----------------------------------------------------------------------------
			//실행
			//----------------------------------------------------------------------------
			List<Map<String, Object>> content = new ArrayList<Map<String, Object>>();
			Map<String, Object> map = new HashMap();

			try{
				{
					if(processDate != null && !processDate.equals("")) {
						callUrl += "?processDate="+processDate+"&integrationServiceId="+integrationServiceId+"&stepIndex="+stepIndex+"&globalId="+globalId;
						URL url = new URL(callUrl);

					    // 문자열로 URL 표현
					    logger.debug("URL :" + url.toExternalForm());

					    // HTTP Connection 구하기
					    HttpURLConnection conn = (HttpURLConnection) url.openConnection();

					    // 요청 방식 설정 ( GET or POST or .. 별도로 설정하지않으면 GET 방식 )
					    conn.setRequestMethod("GET");
					    // 연결 타임아웃 설정
					    conn.setConnectTimeout(30000); // 30초
					    // 읽기 타임아웃 설정
					    conn.setReadTimeout(30000); // 30초
					    BufferedInputStream in = null;
					    ByteArrayOutputStream out = null;
						try {
						    in = new BufferedInputStream(conn.getInputStream());
							int readByteSize;
							byte[] data = new byte[1024];
							out = new ByteArrayOutputStream();
							while((readByteSize = in.read(data)) != -1) {
								out.write(data, 0, readByteSize);
							}

							String ismdata1 = out.toString("MS949");
							logger.debug("responseStr======" + ismdata1);
							map.put("resStr", ismdata1);
						}finally {
							if(out != null) try{ out.close();}catch(Exception e) {logger.error("out close error:",e);}
							if(in != null) try{ in.close();}catch(Exception e) {logger.error("in close error:",e);}
						}
					}
				}


				//--------------------------------------------------
				// 서비스 처리 종료시간을 얻어 CM에 세팅한다.
				//--------------------------------------------------
				{
					comMessage.setEndTime(Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI));
				}

				//--------------------------------------------------
				// 통신메시지에 처리결과 코드/메시지를 등록한다.
				//--------------------------------------------------
				{

					errorCd = MessageSourceUtil.getErrorCd(messageSource, "success.cd.ok", locale);
					errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "success.msg.ok", null, locale);
					comMessage.setResponseObject(map);

					comMessage.setErrorCd(errorCd);
					comMessage.setErrorMsg(errorMsg);
					return comMessage;
				}
			}catch(Throwable e){
				errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.retrieve.none", locale);
				String errorDetail = e.getMessage();
				String[] errorMsgParams = {"LogIFController.getISMLogOnline_Popup",errorDetail};
				errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.retrieve.none", errorMsgParams, locale);
				throw new ControllerException(errorCd, errorMsg, e);
			}

		}

	}


}
