package pep.per.mint.front.controller.op;

import java.util.List;
import java.util.Locale;
import java.util.Map;

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
import pep.per.mint.database.service.op.KABDashboardService;
import pep.per.mint.front.exception.ControllerException;
import pep.per.mint.front.util.MessageSourceUtil;


@Controller
@RequestMapping("/op")
@SuppressWarnings({ "rawtypes", "unchecked" })
public class KABDashboardController {

	private static final Logger logger = LoggerFactory.getLogger(KABDashboardController.class);

	/**
	 * The Dashboard service.
	 */
	@Autowired
	KABDashboardService kabDashboardService;

	/**
	 * The Message source.
	 * 비지니스처리중 프론트까지 전달할 메시지들을 참조할 수 있는 다국어지원용 번들 객체
	 */
	@Autowired
	ReloadableResourceBundleMessageSource messageSource;

	/**
	 * <pre>
	 * 연계기관 통신상태 LIST
	 * API ID : REST-R11-OP-02-00-KAB
	 * </pre>
	 *
	 * @param httpSession 세션
	 * @param comMessage 요청응답통신객체
	 * @param locale 다국어지원을 위한 로케일
	 * @param request the request
	 * @return ComMessage 통신객체
	 * @throws Exception the exception
	 * @author kesowga
	 * @since version 1.0(2019.12)
	 */
	@RequestMapping(
			value="/dashboard/kab/connect-status",
			params={"method=GET"},
			method=RequestMethod.POST,
			headers="content-type=application/json")
	public @ResponseBody ComMessage<Map<String,Object>, List<Map>> getConnectStatus(
			HttpSession  httpSession,
			@RequestBody ComMessage< Map<String,Object>, List<Map>> comMessage,
			Locale locale, HttpServletRequest request) throws Exception, ControllerException {

		//----------------------------------------------------------------------------
		//여긴 비지니스 코드만 작성해라.
		//----------------------------------------------------------------------------
		{
			String errorCd = "";
			String errorMsg = "";
			//--------------------------------------------------
			// Service Call
			//--------------------------------------------------
			List<Map> list= null;
			{
				list = kabDashboardService.getConnectStatus();
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
	 * <pre>
	 * TIME OUT 카운트
	 * API ID : REST-R01-OP-02-00-KAB
	 * </pre>
	 *
	 * @param httpSession 세션
	 * @param comMessage 요청응답통신객체
	 * @param locale 다국어지원을 위한 로케일
	 * @param request the request
	 * @return ComMessage 통신객체
	 * @throws Exception the exception
	 * @author kesowga
     * @since version 1.0(2019.12)
     */
	@RequestMapping(
			value="/dashboard/kab/timeout-count",
			params={"method=GET"},
			method=RequestMethod.POST,
			headers="content-type=application/json")
	public @ResponseBody ComMessage<Map<String,Object>, Integer> getTimeoutCount(
			HttpSession  httpSession,
			@RequestBody ComMessage< Map<String,Object>, Integer> comMessage,
			Locale locale, HttpServletRequest request) throws Exception, ControllerException {

		//----------------------------------------------------------------------------
		//여긴 비지니스 코드만 작성해라.
		//----------------------------------------------------------------------------
		{
			String errorCd = "";
			String errorMsg = "";
			//--------------------------------------------------
			// Service Call
			//--------------------------------------------------
			Integer count = new Integer(0);
			{
				count = kabDashboardService.getTimeoutCount();
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
                errorCd = MessageSourceUtil.getErrorCd(messageSource, "success.cd.ok", locale);
                errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "success.msg.ok",null, locale);
                comMessage.setResponseObject(count);
				comMessage.setErrorCd(errorCd);
				comMessage.setErrorMsg(errorMsg);
			}

			return comMessage;
		}
	}
}
