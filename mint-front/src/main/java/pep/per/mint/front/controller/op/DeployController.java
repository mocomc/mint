package pep.per.mint.front.controller.op;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import pep.per.mint.common.data.basic.ComMessage;
import pep.per.mint.common.data.basic.Deployment;
import pep.per.mint.common.util.Util;

//import pep.per.mint.database.service.op.DeployService;
//import pep.per.mint.front.env.FrontEnvironments;

import pep.per.mint.front.exception.ControllerException;
import pep.per.mint.front.util.FieldCheckUtil;
import pep.per.mint.front.util.MessageSourceUtil;
import pep.per.mint.inhouse.ism.DeployService;
import pep.per.mint.inhouse.ism.DeploymentInfo;

@Controller
@RequestMapping("/op")
@SuppressWarnings({ "rawtypes", "unchecked" })
public class DeployController {

	private static final Logger logger = LoggerFactory.getLogger(DeployController.class);

	@Autowired
	DeployService deployService;

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
	 * 인터페이스 정보 배포 리스트 조회[REST-R01-OP-04-01]
	 * </pre>
	 * @param httpSession
	 * @param comMessage
	 * @param locale
	 * @param request
	 * @return
	 * @throws Exception
	 * @throws ControllerException
	 */
	@RequestMapping(value = "/deploy/interfaces", params = "method=GET", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<Map<String,String>, List<Deployment>> getDeployInterfaceResults(
			HttpSession httpSession,
			@RequestBody ComMessage<Map<String,String>, List<Deployment>> comMessage,
			Locale locale,
			HttpServletRequest request
			)throws Exception, ControllerException {

		{
			String errorCd = "";
			String errorMsg = "";

			Map<String,String> params = comMessage.getRequestObject();

			//----------------------------------------------------------------------------
			//실행
			//----------------------------------------------------------------------------
			List<Deployment> deployments = null;
			try{

				{
					deployments = deployService.getDeployInterfaceResults(params);
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
					comMessage.setResponseObject(deployments);

					comMessage.setErrorCd(errorCd);
					comMessage.setErrorMsg(errorMsg);
					return comMessage;
				}
			}catch(Throwable e){
				errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.create.fail", locale);
				String errorDetail = e.getMessage();
				String[] errorMsgParams = {"DeployController.getDeployInterfaceResults",errorDetail};
				errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.create.fail", errorMsgParams, locale);
				throw new ControllerException(errorCd, errorMsg, e);
			}

		}

	}


	/**
	 * <pre>
	 * 인터페이스 배포 조회[REST-R02-OP-04-01]
	 * </pre>
	 * @param httpSession
	 * @param comMessage
	 * @param locale
	 * @param request
	 * @return
	 * @throws Exception
	 * @throws ControllerException
	 */
	@RequestMapping(value = "/deploy/interfaces/{interfaceId}/{reqDate}/{seq}", params = "method=GET", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<?, Deployment> getDeployInterfaceResult(
			HttpSession httpSession,
			@RequestBody ComMessage<?,Deployment> comMessage,
			@PathVariable("interfaceId") String interfaceId,
			@PathVariable("reqDate") String reqDate,
			@PathVariable("seq") int seq,
			Locale locale,
			HttpServletRequest request
			)throws Exception, ControllerException {

		{
			String errorCd = "";
			String errorMsg = "";

			//----------------------------------------------------------------------------
			//실행
			//----------------------------------------------------------------------------
			Deployment deployment = null;
			try{

				{
					deployment = deployService.getDeployInterfaceResult(reqDate, interfaceId, seq);
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
					comMessage.setResponseObject(deployment);

					comMessage.setErrorCd(errorCd);
					comMessage.setErrorMsg(errorMsg);
					return comMessage;
				}
			}catch(Throwable e){
				errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.create.fail", locale);
				String errorDetail = e.getMessage();
				String[] errorMsgParams = {"DeployController.deployInterface",errorDetail};
				errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.create.fail", errorMsgParams, locale);
				throw new ControllerException(errorCd, errorMsg, e);
			}

		}

	}


	/**
	 * <pre>
	 * 인터페이스 정보 배포[REST-C01-OP-04-01]
	 * </pre>
	 * @param httpSession
	 * @param comMessage
	 * @param locale
	 * @param request
	 * @return
	 * @throws Exception
	 * @throws ControllerException
	 */
	@RequestMapping(value = "/deploy/interfaces", params = "method=POST", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<Map<String,String>, Deployment> deployInterface(
			HttpSession httpSession,
			@RequestBody ComMessage<Map<String,String>, Deployment> comMessage,
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
				String []checkFields = {"interfaceId", "targetType"	};
				FieldCheckUtil.checkRequired("DeployController.deployInterface",checkFields , params, messageSource, locale);
				params.put("appId", comMessage.getAppId());
				params.put("userId", comMessage.getUserId());
			}


			//----------------------------------------------------------------------------
			//실행
			//----------------------------------------------------------------------------
			Deployment deployment = null;
			try{

				{
					deployment = deployService.deployInterface(params);
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
					comMessage.setResponseObject(deployment);

					comMessage.setErrorCd(errorCd);
					comMessage.setErrorMsg(errorMsg);
					return comMessage;
				}
			}catch(Throwable e){
				errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.create.fail", locale);
				String errorDetail = e.getMessage();
				String[] errorMsgParams = {"DeployController.deployInterface",errorDetail};
				errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.create.fail", errorMsgParams, locale);
				throw new ControllerException(errorCd, errorMsg, e);
			}

		}

	}


	/**
	 * <pre>
	 * 인터페이스 정보 배포 수신 테스트를 위한 가상 Endpoint [REST-S01-OP-04-01]
	 * </pre>
	 * @param httpSession
	 * @param comMessage
	 * @param locale
	 * @param request
	 * @return
	 * @throws Exception
	 * @throws ControllerException
	 */
	@RequestMapping(value = "/deploy/interfaces/response", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody Map<String, String> deployInterfaceResponse(
			HttpSession httpSession,
			@RequestBody DeploymentInfo comMessage,
			Locale locale,
			HttpServletRequest request
			)throws Exception, ControllerException {

		{
			String errorCd = "";
			String errorMsg = "";
			try{


				logger.debug("==========================================================================");
				logger.debug("== LOCAL TEST FOR INTERFACE DEPLOY (요청받은 데이터 정보)");
				logger.debug("--------------------------------------------------------------------------");
				logger.debug(Util.join("request.getContnetLength():", request.getContentLength()));
				logger.debug(Util.join("request.getContentType():", request.getContentType()));
				logger.debug(Util.join("request.getContextPath():", request.getContextPath()));
				logger.debug(Util.join("request.getAuthType():", request.getAuthType()));
				logger.debug("--------------------------------------------------------------------------");
				logger.debug(Util.join("request comMessage:", Util.toJSONString(comMessage)));
				logger.debug("--------------------------------------------------------------------------");

				Map<String, String> resonse = new HashMap<String,String>();
				resonse.put("errorCd", "0");
				return resonse;
			}catch(Throwable e){
				errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.create.fail", locale);
				String errorDetail = e.getMessage();
				String[] errorMsgParams = {"DeployController.deployInterfaceResponse",errorDetail};
				errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.create.fail", errorMsgParams, locale);
				throw new ControllerException(errorCd, errorMsg, e);
			}

		}

	}


}
