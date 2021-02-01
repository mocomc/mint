package pep.per.mint.front.controller.op;

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
import pep.per.mint.common.data.basic.agent.CommandConsole;
import pep.per.mint.common.data.basic.agent.IIPAgentLog;
import pep.per.mint.common.data.basic.agent.ProcessStatusLog;
import pep.per.mint.common.data.basic.agent.QmgrLogs;
import pep.per.mint.common.data.basic.agent.ResourceUsageLog;
import pep.per.mint.common.util.Util;
import pep.per.mint.database.service.op.IIPAgentService;
import pep.per.mint.front.exception.ControllerException;
import pep.per.mint.front.util.FieldCheckUtil;
import pep.per.mint.front.util.MessageSourceUtil;

@Controller
@RequestMapping("/op")
@SuppressWarnings({ "rawtypes", "unchecked" })
public class IIPAgentController {

	private static final Logger logger = LoggerFactory.getLogger(IIPAgentController.class);

	/**
	 * The Message source.
	 */
	@Autowired
	ReloadableResourceBundleMessageSource messageSource;

	// 인터페이스요건관련 데이터 서비스를 구현한 객체
	/*@Autowired
	MonitorService monitorService;*/

	// 서블리컨텍스트 관련정보 참조를 위한 객체
	// 예를 들어 servletContext를 이용하여 웹어플리케이션이
	// 배포퇸 컨텍스트 루트위치 등을 얻어올 수 있다.
	@Autowired
	private ServletContext servletContext;

	@Autowired
	IIPAgentService iipAgentService;


	/**
	 * <pre>
	 * 리소스로그  업로드 [REST-S01-OP-08-01]
	 * </pre>
	 * @param httpSession
	 * @param comMessage
	 * @param locale
	 * @param request
	 * @return
	 * @throws Exception
	 * @throws ControllerException
	 */
	@RequestMapping(value = "/agents/services/resource-logs/{agentId}", params = "method=POST", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<?, ?> addResourceLogs(
			HttpSession httpSession,
			@RequestBody ComMessage<List<ResourceUsageLog>, ?> comMessage,
			@PathVariable("agentId") String agentId,
			Locale locale,
			HttpServletRequest request
			)throws Exception, ControllerException {

		{
			String errorCd = "";
			String errorMsg = "";

			List<ResourceUsageLog> logs = comMessage.getRequestObject();
			logger.debug("resource logs:"+Util.toJSONString(logs));
			//----------------------------------------------------------------------------
			// 필수필드 체크하기
			//----------------------------------------------------------------------------
			{

			}

			//----------------------------------------------------------------------------
			//등록ID, 등록시간 설정하기
			//----------------------------------------------------------------------------
			{

			}

			//----------------------------------------------------------------------------
			//실행
			//----------------------------------------------------------------------------
			try{
				int res = -1;
				{
					res = iipAgentService.addResourceLogs(logs);
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


					comMessage.setErrorCd(errorCd);
					comMessage.setErrorMsg(errorMsg);
					return comMessage;
				}
			}catch(Throwable e){
				errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.update.fail", locale);
				String errorDetail = e.getMessage();
				String[] errorMsgParams = {"IIPAgentController.addResourceLogs",errorDetail};
				errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.update.fail", errorMsgParams, locale);
				throw new ControllerException(errorCd, errorMsg, e);
			}
		}
	}

	/**
	 * <pre>
	 * 프로세스로그 업로드 [REST-S01-OP-08-02]
	 * </pre>
	 * @param httpSession
	 * @param comMessage
	 * @param locale
	 * @param request
	 * @return
	 * @throws Exception
	 * @throws ControllerException
	 */
	@RequestMapping(value = "/agents/services/process-logs/{agentId}", params = "method=POST", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<?, ?> addProcessLogs(
			HttpSession httpSession,
			@RequestBody ComMessage<List<ProcessStatusLog>, ?> comMessage,
			@PathVariable("agentId") String agentId,
			Locale locale,
			HttpServletRequest request
			)throws Exception, ControllerException {

		{
			String errorCd = "";
			String errorMsg = "";

			List<ProcessStatusLog> logs = comMessage.getRequestObject();
			logger.debug("process logs:"+Util.toJSONString(logs));
			//----------------------------------------------------------------------------
			// 필수필드 체크하기
			//----------------------------------------------------------------------------
			{

			}

			//----------------------------------------------------------------------------
			//등록ID, 등록시간 설정하기
			//----------------------------------------------------------------------------
			{

			}

			//----------------------------------------------------------------------------
			//실행
			//----------------------------------------------------------------------------
			try{
				int res = -1;
				{
					res = iipAgentService.addProcessLogs(logs);
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


					comMessage.setErrorCd(errorCd);
					comMessage.setErrorMsg(errorMsg);
					return comMessage;
				}
			}catch(Throwable e){
				errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.update.fail", locale);
				String errorDetail = e.getMessage();
				String[] errorMsgParams = {"IIPAgentController.addProcessLogs",errorDetail};
				errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.update.fail", errorMsgParams, locale);
				throw new ControllerException(errorCd, errorMsg, e);
			}
		}
	}

	/**
	 * <pre>
	 * 큐매니저,채널,큐 로그 업로드 [REST-S01-OP-08-03]
	 * </pre>
	 * @param httpSession
	 * @param comMessage
	 * @param locale
	 * @param request
	 * @return
	 * @throws Exception
	 * @throws ControllerException
	 */
	@RequestMapping(value = "/agents/services/qmgr-logs/{agentId}", params = "method=POST", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<?, ?> addQmgrLogs(
			HttpSession httpSession,
			@RequestBody ComMessage<List<QmgrLogs>, ?> comMessage,
			@PathVariable("agentId") String agentId,
			Locale locale,
			HttpServletRequest request
			)throws Exception, ControllerException {

		{
			String errorCd = "";
			String errorMsg = "";

			List<QmgrLogs> logs = comMessage.getRequestObject();
			logger.debug("process logs:"+Util.toJSONString(logs));
			//----------------------------------------------------------------------------
			// 필수필드 체크하기
			//----------------------------------------------------------------------------
			{

			}

			//----------------------------------------------------------------------------
			//등록ID, 등록시간 설정하기
			//----------------------------------------------------------------------------
			{

			}

			//----------------------------------------------------------------------------
			//실행
			//----------------------------------------------------------------------------
			try{
				int res = -1;
				{
					res = iipAgentService.addQmgrLogs(logs);
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


					comMessage.setErrorCd(errorCd);
					comMessage.setErrorMsg(errorMsg);
					return comMessage;
				}
			}catch(Throwable e){
				errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.update.fail", locale);
				String errorDetail = e.getMessage();
				String[] errorMsgParams = {"IIPAgentController.addProcessLogs",errorDetail};
				errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.update.fail", errorMsgParams, locale);
				throw new ControllerException(errorCd, errorMsg, e);
			}
		}
	}




	/**
	 * <pre>
	 * 미처리명령(MAX)처리조회 조회 [REST-S01-OP-08-04]
	 * </pre>
	 * @param httpSession
	 * @param comMessage
	 * @param locale
	 * @param request
	 * @return
	 * @throws Exception
	 * @throws ControllerException
	 */
	@RequestMapping(value = "/agents/services/cmds/{agentId}", params = "method=GET", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<Map<String,String>, List<CommandConsole>> getAgentNotExecuteCommands(
			HttpSession httpSession,
			@RequestBody ComMessage<Map<String,String>, List<CommandConsole>> comMessage,
			@PathVariable("agentId") String agentId,
			Locale locale,
			HttpServletRequest request
			)throws Exception, ControllerException {

		{
			String errorCd = "";
			String errorMsg = "";

			List<CommandConsole> list = null;
			Map<String,String> params = comMessage.getRequestObject();
			//----------------------------------------------------------------------------
			// 필수필드 체크하기
			//----------------------------------------------------------------------------
			{
				String[] fields = {"commandCd", "fromDate","toDate"};
				FieldCheckUtil.checkRequired("getAgentCommands", fields, params, messageSource, locale);
			}


			//----------------------------------------------------------------------------
			//실행
			//----------------------------------------------------------------------------
			try{
				int res = -1;
				{
					params.put("agentId", agentId);
					list = iipAgentService.getAgentNotExecuteCommands(params);
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

					comMessage.setResponseObject(list);
					comMessage.setErrorCd(errorCd);
					comMessage.setErrorMsg(errorMsg);
					return comMessage;
				}

			}catch(Throwable e){
				errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.retrieve.fail", locale);
				String errorDetail = e.getMessage();
				String[] errorMsgParams = {"IIPAgentController.getAgentCommands",errorDetail};
				errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.retrieve.fail", errorMsgParams, locale);
				throw new ControllerException(errorCd, errorMsg, e);
			}
		}
	}


	/**
	 * <pre>
	 * 에이전트 명령 발행 [REST-U02-OP-02-02]
	 * </pre>
	 * @param httpSession
	 * @param comMessage
	 * @param locale
	 * @param request
	 * @return
	 * @throws Exception
	 * @throws ControllerException
	 */
	@RequestMapping(value = "/agents/services/cmds/{agentId}", params = "method=POST", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<CommandConsole, CommandConsole> insertAgentCommand(
			HttpSession httpSession,
			@RequestBody ComMessage<CommandConsole, CommandConsole> comMessage,
			@PathVariable("agentId") String agentId,
			Locale locale,
			HttpServletRequest request
			)throws Exception, ControllerException {

		{
			String errorCd = "";
			String errorMsg = "";

			CommandConsole cc = comMessage.getRequestObject();
			//----------------------------------------------------------------------------
			// 필수필드 체크하기
			//----------------------------------------------------------------------------
			{
			}


			//----------------------------------------------------------------------------
			//실행
			//----------------------------------------------------------------------------
			try{
				int res = -1;
				{

					res = iipAgentService.insertAgentCommand(cc);
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

					comMessage.setResponseObject(cc);
					comMessage.setErrorCd(errorCd);
					comMessage.setErrorMsg(errorMsg);
					return comMessage;
				}

			}catch(Throwable e){
				errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.update.fail", locale);
				String errorDetail = e.getMessage();
				String[] errorMsgParams = {"IIPAgentController.updateAgentCommand",errorDetail};
				errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.update.fail", errorMsgParams, locale);
				throw new ControllerException(errorCd, errorMsg, e);
			}
		}
	}


	/**
	 * <pre>
	 * 에이전트 명령 일괄 발행 [REST-S02-OP-08-04]
	 * </pre>
	 * @param httpSession
	 * @param comMessage
	 * @param locale
	 * @param request
	 * @return
	 * @throws Exception
	 * @throws ControllerException
	 */
	@RequestMapping(value = "/agents/services/cmds", params = "method=POST", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<CommandConsole, CommandConsole> insertAllAgentCommand(
			HttpSession httpSession,
			@RequestBody ComMessage<CommandConsole, CommandConsole> comMessage,
			Locale locale,
			HttpServletRequest request
			)throws Exception, ControllerException {

		{
			String errorCd = "";
			String errorMsg = "";

			CommandConsole cc = comMessage.getRequestObject();
			//----------------------------------------------------------------------------
			// 필수필드 체크하기
			//----------------------------------------------------------------------------
			{
			}
			logger.debug(Util.join("insertAllAgentCommand CommandConsole param dump:\n", FieldCheckUtil.jsonDump(cc)));

			//----------------------------------------------------------------------------
			//실행
			//----------------------------------------------------------------------------
			try{
				int res = -1;
				{

					res = iipAgentService.insertAllAgentCommand(cc);
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

					comMessage.setResponseObject(cc);
					comMessage.setErrorCd(errorCd);
					comMessage.setErrorMsg(errorMsg);
					return comMessage;
				}

			}catch(Throwable e){
				errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.update.fail", locale);
				String errorDetail = e.getMessage();
				String[] errorMsgParams = {"IIPAgentController.updateAgentCommand",errorDetail};
				errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.update.fail", errorMsgParams, locale);
				throw new ControllerException(errorCd, errorMsg, e);
			}
		}
	}


	/**
	 * <pre>
	 * 에이전트 명령 UPDATE [REST-S02-OP-08-04]
	 * </pre>
	 * @param httpSession
	 * @param comMessage
	 * @param locale
	 * @param request
	 * @return
	 * @throws Exception
	 * @throws ControllerException
	 */
	@RequestMapping(value = "/agents/services/cmds/result/{agentId}", params = "method=PUT", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<CommandConsole, CommandConsole> updateAgentCommandResult(
			HttpSession httpSession,
			@RequestBody ComMessage<CommandConsole, CommandConsole> comMessage,
			@PathVariable("agentId") String agentId,
			Locale locale,
			HttpServletRequest request
			)throws Exception, ControllerException {

		{
			String errorCd = "";
			String errorMsg = "";

			CommandConsole cc = comMessage.getRequestObject();
			//----------------------------------------------------------------------------
			// 필수필드 체크하기
			//----------------------------------------------------------------------------
			{
			}


			//----------------------------------------------------------------------------
			//실행
			//----------------------------------------------------------------------------
			try{
				int res = -1;
				{

					res = iipAgentService.updateAgentCommandResult(cc);
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

					comMessage.setErrorCd(errorCd);
					comMessage.setErrorMsg(errorMsg);
					return comMessage;
				}

			}catch(Throwable e){
				errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.update.fail", locale);
				String errorDetail = e.getMessage();
				String[] errorMsgParams = {"IIPAgentController.updateAgentCommand",errorDetail};
				errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.update.fail", errorMsgParams, locale);
				throw new ControllerException(errorCd, errorMsg, e);
			}
		}
	}

	/**
	 * <pre>
	 * 에이전트 명령처리결과조회 조회 [REST-S03-OP-08-04]
	 * </pre>
	 * @param httpSession
	 * @param comMessage
	 * @param locale
	 * @param request
	 * @return
	 * @throws Exception
	 * @throws ControllerException
	 */
	@RequestMapping(value = "/agents/services/cmds/results", params = "method=GET", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<Map<String,String>, List<CommandConsole>> getAgentCommandResults(
			HttpSession httpSession,
			@RequestBody ComMessage<Map<String,String>, List<CommandConsole>> comMessage,
			Locale locale,
			HttpServletRequest request
			)throws Exception, ControllerException {

		{
			String errorCd = "";
			String errorMsg = "";

			List<CommandConsole> list = null;
			Map<String,String> params = comMessage.getRequestObject();
			//----------------------------------------------------------------------------
			// 필수필드 체크하기
			//----------------------------------------------------------------------------
			{
				String[] fields = {"fromDate","toDate"};
				FieldCheckUtil.checkRequired("getAgentCommandResults", fields, params, messageSource, locale);
			}


			//----------------------------------------------------------------------------
			//실행
			//----------------------------------------------------------------------------
			try{
				int res = -1;
				{
					list = iipAgentService.getAgentCommandResults(params);
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

					comMessage.setResponseObject(list);
					comMessage.setErrorCd(errorCd);
					comMessage.setErrorMsg(errorMsg);
					return comMessage;
				}

			}catch(Throwable e){
				errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.retrieve.fail", locale);
				String errorDetail = e.getMessage();
				String[] errorMsgParams = {"IIPAgentController.getAgentCommandResults",errorDetail};
				errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.retrieve.fail", errorMsgParams, locale);
				throw new ControllerException(errorCd, errorMsg, e);
			}
		}
	}


	/**
	 * <pre>
	 * IIP 에이전트 로그 INSERT [REST-S01-OP-08-05]
	 * </pre>
	 * @param httpSession
	 * @param comMessage
	 * @param locale
	 * @param request
	 * @return
	 * @throws Exception
	 * @throws ControllerException
	 */
	@RequestMapping(value = "/agents/services/log/add/{agentId}", params = "method=POST", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<IIPAgentLog, IIPAgentLog> insertIIPAgentLog(
			HttpSession httpSession,
			@RequestBody ComMessage<IIPAgentLog, IIPAgentLog> comMessage,
			@PathVariable("agentId") String agentId,
			Locale locale,
			HttpServletRequest request
			)throws Exception, ControllerException {

		{
			String errorCd = "";
			String errorMsg = "";

			IIPAgentLog log = comMessage.getRequestObject();
			//----------------------------------------------------------------------------
			// 필수필드 체크하기
			//----------------------------------------------------------------------------
			{
			}


			//----------------------------------------------------------------------------
			//실행
			//----------------------------------------------------------------------------
			try{
				int res = -1;
				{

					res = iipAgentService.insertIIPAgentLog(log);
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
					comMessage.setResponseObject(log);
					comMessage.setErrorCd(errorCd);
					comMessage.setErrorMsg(errorMsg);
					return comMessage;
				}

			}catch(Throwable e){
				errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.update.fail", locale);
				String errorDetail = e.getMessage();
				String[] errorMsgParams = {"IIPAgentController.insertIIPAgentLog",errorDetail};
				errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.update.fail", errorMsgParams, locale);
				throw new ControllerException(errorCd, errorMsg, e);
			}
		}
	}

	/**
	 * <pre>
	 * IIP 에이전트 로그 UPDATE [REST-S02-OP-08-05]
	 * </pre>
	 * @param httpSession
	 * @param comMessage
	 * @param locale
	 * @param request
	 * @return
	 * @throws Exception
	 * @throws ControllerException
	 */
	@RequestMapping(value = "/agents/services/log/update/{agentId}", params = "method=PUT", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<IIPAgentLog, IIPAgentLog> updateIIPAgentLog(
			HttpSession httpSession,
			@RequestBody ComMessage<IIPAgentLog, IIPAgentLog> comMessage,
			@PathVariable("agentId") String agentId,
			Locale locale,
			HttpServletRequest request
			)throws Exception, ControllerException {

		{
			String errorCd = "";
			String errorMsg = "";

			IIPAgentLog log = comMessage.getRequestObject();
			//----------------------------------------------------------------------------
			// 필수필드 체크하기
			//----------------------------------------------------------------------------
			{
			}


			//----------------------------------------------------------------------------
			//실행
			//----------------------------------------------------------------------------
			try{
				int res = -1;
				{

					res = iipAgentService.updateIIPAgentState(log);
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
					comMessage.setResponseObject(log);
					comMessage.setErrorCd(errorCd);
					comMessage.setErrorMsg(errorMsg);
					return comMessage;
				}

			}catch(Throwable e){
				errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.update.fail", locale);
				String errorDetail = e.getMessage();
				String[] errorMsgParams = {"IIPAgentController.updateIIPAgentLog",errorDetail};
				errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.update.fail", errorMsgParams, locale);
				throw new ControllerException(errorCd, errorMsg, e);
			}
		}
	}


	/**
	 * <pre>
	 * 에이전트 로그 조회 [REST-S03-OP-08-04]
	 * </pre>
	 * @param httpSession
	 * @param comMessage
	 * @param locale
	 * @param request
	 * @return
	 * @throws Exception
	 * @throws ControllerException
	 */
	@RequestMapping(value = "/agents/services/logs", params = "method=GET", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<Map<String,String>, List<IIPAgentLog>> getIIPAgentLogs(
			HttpSession httpSession,
			@RequestBody ComMessage<Map<String,String>, List<IIPAgentLog>> comMessage,
			Locale locale,
			HttpServletRequest request
			)throws Exception, ControllerException {

		{
			String errorCd = "";
			String errorMsg = "";

			List<IIPAgentLog> list = null;
			Map<String,String> params = comMessage.getRequestObject();
			//----------------------------------------------------------------------------
			// 필수필드 체크하기
			//----------------------------------------------------------------------------
			{
				String[] fields = {"fromDate","toDate"};
				FieldCheckUtil.checkRequired("getAgentCommandResults", fields, params, messageSource, locale);
			}


			//----------------------------------------------------------------------------
			//실행
			//----------------------------------------------------------------------------
			try{
				int res = -1;
				{
					list = iipAgentService.getIIPAgentLogs(params);
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

					comMessage.setResponseObject(list);
					comMessage.setErrorCd(errorCd);
					comMessage.setErrorMsg(errorMsg);
					return comMessage;
				}

			}catch(Throwable e){
				errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.retrieve.fail", locale);
				String errorDetail = e.getMessage();
				String[] errorMsgParams = {"IIPAgentController.getAgentCommandResults",errorDetail};
				errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.retrieve.fail", errorMsgParams, locale);
				throw new ControllerException(errorCd, errorMsg, e);
			}
		}
	}

}
