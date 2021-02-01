package pep.per.mint.inhouse.sso.hdins;


import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import pep.per.mint.common.util.Util;
import pep.per.mint.database.service.co.CommonService;
import pep.per.mint.inhouse.sso.ILoginService;

public class LoginService implements ILoginService{

	static Logger logger = LoggerFactory.getLogger(LoginService.class);

	@Autowired
	@Qualifier(value="eucKrRestTemplate")
	RestTemplate restTemplate;

	@Autowired
	CommonService commonService;

	public final static String OK = "00000000";

	String systemId = null;

	String url = null;


	public void init() throws Exception{

		List<HttpMessageConverter<?>> httpMessageConverters = new ArrayList<HttpMessageConverter<?>>();
		HttpMessageConverter<?> stringHttpMessageConverter = new StringHttpMessageConverter(Charset.forName("EUC-KR"));
        httpMessageConverters.add(stringHttpMessageConverter);
        //MappingJackson2HttpMessageConverter jsonHttpMessageConverter = new MappingJackson2HttpMessageConverter();
        //httpMessageConverters.add(jsonHttpMessageConverter);
        //List<MediaType> list = jsonHttpMessageConverter.getSupportedMediaTypes();
        //for (MediaType mediaType : list) {
		//	logger.debug("===========================suppored mediaType:" + mediaType);
		//}

        restTemplate.setMessageConverters(httpMessageConverters);



        boolean ssoLogin = false;
        {
			Map<String, List<String>> environmentalValues = commonService.getEnvironmentalValues();
			List<String> values = environmentalValues.get("system.sso.login");
			if (values != null && values.size() > 0) {
				ssoLogin = Boolean.parseBoolean(values.get(0));
			}
        }

		if(ssoLogin) {
			{
				Map<String, List<String>> environmentalValues = commonService.getEnvironmentalValues();
				List<String> values = environmentalValues.get("system.sso.login.url");
				if (values != null && values.size() > 0) {
					url = values.get(0);
					logger.debug(Util.join("system.sso.login.url:",url));
				}else{
					//change-when : 170927
					//change-what :
					//system.sso.login = true 일 경우 환경변수가 존재하지 않으면 아예 어플리케이션으르 올리지 않도록 하였으나.
					//사이트별로 환경변수가 틀릴수 있으므로 에러로그만 남기는 것으로 대체한다.
					//throw new Exception("can't login to sso system because there is no environment values of system.sso.login.url");
					logger.error("can't login to sso system because there is no environment values of system.sso.login.url");
				}
			}

			{
				Map<String, List<String>> environmentalValues = commonService.getEnvironmentalValues();
				List<String> values = environmentalValues.get("system.sso.login.system.id");
				if (values != null && values.size() > 0) {
					systemId = values.get(0);
					logger.debug(Util.join("system.sso.login.system.id:",systemId));
				}else{
					//change-when : 170927
					//change-what :
					//system.sso.login = true 일 경우 환경변수가 존재하지 않으면 아예 어플리케이션으르 올리지 않도록 하였으나.
					//사이트별로 환경변수가 틀릴수 있으므로 에러로그만 남기는 것으로 대체한다.
					//throw new Exception("can't login to sso system because there is no environment values of system.sso.login.system.id");
					logger.error("can't login to sso system because there is no environment values of system.sso.login.system.id");
				}
			}
		}
	}


	/**
	 * <pre>
	 * <code>
	 * 현대해상 요청 스펙정리
	 * ========================================================
	 * 1.Request
	 * 1.1.개발,검증,운영이 다르므로 프로퍼티로 등록 가능해야함.
	 * 	system.sso.login.url = http://10.2.11.191:29001/OLC-COM_1-initial/ISM
	 * 1.2.패스워드 MD5 암호화 후 전송
	 * 1.3.content-type : application/json; charset=euc-kr
	 * 1.4.HTTP Header
	 * 	-.content-type 	 : application/json; charset=euc-kr
	 * 	-.interface-id 	 : IR_HI-EAI_0001
	 *  -.user-id		 : 로그인시 입력한 사용자 아이디 (euc-kr 로 인코드 필요)
	 *  -.global-id		 : 30자리 랜덤값 (ISM 대사용)
	 *  -.message-enabled: false (고정값)
	 * 1.5.HTTP Body
	 * {
	 * 	"userId"	:"로그인 시 입력한 사용자 아이디",
	 * 	"clientIp"	:"로그인 시 실제 아이피",
	 * 	"systemId"	:"000032",//고정값으로 등록요청후 발급된 시스템 아이디 , 변경가능하므로 환경변수로 지정 필요
	 * 				 system.sso.login.system.id = 000032
	 * 	"password"	:"로그인 시 입력한 계정 비밀번호(MD5암호화)"
	 *               => 현대해상 암호화가이드 제공 그대로 사용
	 * }
	 * 2.Response
	 * 응답메시지 중, 헤더의 response-message, response-code 를 로그인 결과 값으로 사용한다.
	 * response-code 값이 "00000000" 외에는 모두 비정상 처리한다.
	 * </code>
	 * </pre>
	 *
	 */
	@Override
	public Map<?, ?> login(Map<?, ?> params) throws Exception {

		//--------------------------------------------------------------------
		//http header setting
		//--------------------------------------------------------------------
		String userId = ((String)params.get("userId")).toUpperCase();
		String globalId = Util.leftPad(Util.join(userId, Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT)), 30, "0");
		String interfaceId = "IR_HI-EAI_0001";

		HttpURLConnection conn = null;
		OutputStream os = null;
		InputStream is = null;
		ByteArrayOutputStream baos = null;

		//--------------------------------------------------------------------
		//http body setting
		//--------------------------------------------------------------------
		Map<String, String> body = null;
		String clientIp = (String)params.get("clientIp");
		String password = ((String)params.get("password")).toUpperCase();
		//password = Util.convert(password, "euc-kr");//인코딩이 필요하면 주석풀것
		String md5Password = Util.getMD5(password);
		//md5Password = new String(Base64.encodeBase64(md5Password.getBytes()));

		{
			body = new HashMap<String, String>();
			body.put("userId", userId);
			body.put("clientIp", clientIp);
			body.put("systemId", systemId);
			body.put("password", md5Password);
			//body.put("testOption", "1");//for test, 삭제가능
		}

			Map<String,String> res = new HashMap<String,String>();
			try {
				URL urlx = new URL(url); // 문자열로  URL 표현
				logger.debug("URL :" + url);
				//HTTP Connection 구하기
				conn = (HttpURLConnection) urlx.openConnection();

				//요청방식 설정 (GET or POST)
				conn.setRequestMethod("POST");
				conn.addRequestProperty("Content-Type", "application/json;charset=EUC-KR");
				conn.addRequestProperty("interface-id", interfaceId);
				conn.addRequestProperty("user-Id", userId);
				conn.addRequestProperty("global-id", globalId);
				conn.addRequestProperty("message-enabled", "false");

				//연결 타임아웃 설정
				conn.setConnectTimeout(30000);  //30초

				//읽기 타임아웃 설정
				conn.setReadTimeout(30000);

				conn.setDoInput(true);
				conn.setDoOutput(true);
				conn.connect();
				os = conn.getOutputStream();
				logger.debug("Header [" + interfaceId + "] [" + globalId + "]");
				String bodyStr = Util.toJSONString(body);
				logger.debug("Body :" + bodyStr);
				os.write(bodyStr.getBytes("MS949"));
				int httpResCode = conn.getResponseCode();
				String httpResMsg = conn.getResponseMessage();
				logger.info("[" + globalId + "] http response [" + httpResCode + "] " + httpResMsg);

				if(httpResCode == 200) {
					//success
					is = conn.getInputStream();
					byte[] buffer = new byte[1024];
					baos = new ByteArrayOutputStream();

					while(true) {
						int len = is.read(buffer);
						if(len < 0) {
							break;
						}
						baos.write(buffer, 0, len);
					}

					String readData = new String(baos.toByteArray(), "MS949");
					logger.info("ResponseBody" + readData);
					String responseCode = conn.getHeaderField("response-code");
					String responseMessage = conn.getHeaderField("response-message");
					String returnCode = "";
					logger.info("[" + globalId + "] http data response [" + responseCode + "] " + responseMessage);

					if (readData.contains("UZZ16001") || readData.contains("UZZ16007")) {
						returnCode = "UZZ16001";
					}
					else {
						returnCode = "UZZ11111";
					}

					logger.debug("returnCode:" + returnCode);
					logger.debug("responseCode:" + responseCode);

					if (OK.equals(responseCode)) {
						if (returnCode == "UZZ16001") {
							res.put("errorCd", COMMON_SUCCESS_CD);
						} else {
							int A = readData.indexOf("returnMsg");
							int B = readData.indexOf("lastLoginDt");
							String returnMsg = readData.substring(A, B);
							logger.debug("body ReturnMsg:" + returnMsg);
							res.put("errorMsg", returnMsg == null ? "" : returnMsg);
						}
					} else {
						String errorMsg10 = URLDecoder.decode(responseMessage, "euc-kr");
						String errorMsg = Util.join(errorMsg10);
						res.put("errorMsg", responseMessage == null ? "" : errorMsg);
						logger.debug("errorMsg:" + errorMsg);
					}

					} else {
						is = conn.getErrorStream();
						byte[] buffer = new byte[1024];
						baos = new ByteArrayOutputStream();

						while(true) {
							int len = is.read(buffer);
							if(len < 0) {
								break;
							}
							baos.write(buffer, 0, len);
						}
						String readData = new String(baos.toByteArray(), "MS949");
						logger.error("error response data :" + readData);
					}
			} catch (Exception ee) {
				logger.warn(ee.getMessage(), ee);

			} finally {
				if(baos != null) {
					try { baos.close(); } catch (Exception ee) {}
				}

				if(is != null) {
					try { is.close(); } catch (Exception ee) {}
					}

				if(os != null) {
					try { os.close(); }
						catch (Exception ee) {}
					}

				if(conn != null) {
					try { conn.disconnect(); } catch(Exception ee) {}
					}
				}

			return res;

			}
	}