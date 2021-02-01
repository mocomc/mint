package pep.per.mint.front.controller.op;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import pep.per.mint.common.data.basic.ComMessage;
import pep.per.mint.common.data.basic.GSTRLog;
import pep.per.mint.common.data.basic.GSTRLogDetail;
import pep.per.mint.common.data.basic.User;
import pep.per.mint.common.util.Util;
import pep.per.mint.database.service.co.CommonService;
import pep.per.mint.database.service.op.GSSPMonitorService;
import pep.per.mint.front.exception.ControllerException;
import pep.per.mint.front.util.MessageSourceUtil;

/**
 * <blockquote>
 * <pre>
 * <B>모니터링 CRUD  서비스 제공 RESTful Controller</B>
 * -------------------------------------------------------------
 * 개발할 메소드 목록
 * -------------------------------------------------------------
 * OP-01-01-101	트레킹 로그 리스트 조회	REST-R01-OP-01-01-GSSP
 *
 *
 * @author Solution TF </pre>
 *</blockquote>
 */
@Controller
@RequestMapping("/op")
public class GSSPMonitorController {
	private static final Logger logger = LoggerFactory.getLogger(MonitorController.class);

	/**
	 * The Message source.
	 */
	// 비지니스처리중 프론트까지 전달할 메시지들을 참조할 수 있는 다국어지원용 번들 객체
	@Autowired
	ReloadableResourceBundleMessageSource messageSource;

	/**
	 * The Monitor service.
	 */
	// 인터페이스요건관련 데이터 서비스를 구현한 객체
	@Autowired
	GSSPMonitorService monitorService;

	// 서블리컨텍스트 관련정보 참조를 위한 객체
	// 예를 들어 servletContext를 이용하여 웹어플리케이션이
	// 배포퇸 컨텍스트 루트위치 등을 얻어올 수 있다.
	@Autowired
	private ServletContext servletContext;

	@Autowired
	CommonService commonService;

	/**
	 * <pre>
	 * 트레킹 로그 리스트 조회
	 * API ID : REST-R01-OP-01-01
	 * </pre>
	 *
	 * @param httpSession 세션
	 * @param comMessage 요청응답통신객체
	 * @param locale 다국어지원을 위한 로케일
	 * @param request the request
	 * @return ComMessage 통신객체
	 * @throws Exception the exception
	 * @author Solution TF
	 * @throws ControllerException
     * @since version 1.0(2015.07)
     */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/gssp/tracking/logs", params = "method=GET", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<Map<String,Object>, List<GSTRLog>> getTrackingLogList(
			HttpSession httpSession,
			@RequestBody ComMessage<Map<String,Object>, List<GSTRLog>> comMessage,
			Locale locale, HttpServletRequest request) throws Exception, ControllerException {

		/**
		 * 그전에 테스트 유닛코드를 꼭 먼저 작성할것을 권한다.게으름 피우지 말고 꼭 작성하길 바란다. 내가 지켜보고 있다. 한가지
		 * 더, 경험상 코드 작성 순서는 아래 순서로 하면 효율적이더라. 더 좋은 방법이 있다면 달리 하던가.
		 * 1.pep.per.mint.database.service.an.RequirementService.method 작성
		 * 2.pep.per.mint.front.controller.an.RequirementController.method 작성
		 * 3.pep.per.mint.database.mapper.an.RequirementMapper.method 작성
		 * 4.pep.per.mint.database.mapper.an.RequirementMapper.xml 상의 맵핑 sql 작성
		 *
		 */
		// ----------------------------------------------------------------------------
		// 여긴 비지니스 코드만 작성해라.
		// ----------------------------------------------------------------------------
		{
			String errorCd = "";
			String errorMsg = "";

			Map params = comMessage.getRequestObject();
			if(params == null)
				params = new HashMap();

			//-------------------------------------------------
			//로그인 사용자 정보 셋팅
			//-------------------------------------------------
			User user = (User) httpSession.getAttribute("user");
			params.put("userId", user.getUserId());
			params.put("isInterfaceAdmin", user.getRole().getIsInterfaceAdmin());

			//--------------------------------------------------
			// calculating startIndex and endIndex value
			//--------------------------------------------------
			{
				int startIndex = 0;
				int endIndex = 0;
				int page     = params.get("page") == null ? 1 : (Integer)params.get("page");
				int perCount = params.get("perCount") == null ? 20 : (Integer)params.get("perCount");

				startIndex = (page - 1) * perCount + 1;
				endIndex = startIndex + perCount - 1;

				params.put("startIndex",startIndex);
				params.put("endIndex",endIndex);
				params.put("page",page);
				params.put("perCount",perCount);
			}

			List<GSTRLog> list = null;
			{

				int totalCount = params.get("total") == null ? 0 : (Integer)params.get("total");
				if( totalCount == 0 ) {
					totalCount = monitorService.getTrackingLogListTotalCount(params);
				}

				if(totalCount > 0) {
					list = monitorService.getTrackingLogList(params);
				} else {
					list = new ArrayList();
				}
				params.put("total",totalCount);
				comMessage.setResponseObject(list);
			}

			if (list == null || list.size() == 0) {
				errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.retrieve.none", locale);
				errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.retrieve.none", null, locale);
			} else {
				errorCd = MessageSourceUtil.getErrorCd(messageSource, "success.cd.ok", locale);
				Object [] errorMsgParams = {list.size()};
				errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "success.msg.retrieve.list.ok", errorMsgParams, locale);
				comMessage.setResponseObject(list);
			}

			comMessage.setErrorCd(errorCd);
			comMessage.setErrorMsg(errorMsg);
			comMessage.setEndTime(Util.getFormatedDate("yyyyMMddHHmmssSSS"));

			return comMessage;
		}
	}

	/**
	 * <pre>
	 * 트레킹 로그 상세 - 트레킹 로그 상세 정보 조회
	 * API ID : REST-R02-OP-01-01-001
	 * </pre>
	 *
	 * @param httpSession 세션
	 * @param logKey1 the log key 1
	 * @param logKey2 the log key 2
	 * @param comMessage 요청응답통신객체
	 * @param locale 다국어지원을 위한 로케일
	 * @return ComMessage 통신객체
	 * @throws Exception the exception
	 * @author Solution TF
     * @since version 1.0(2015.07)
     */
	@RequestMapping(
			value="/gssp/tracking/logs/{logKey1}/{logKey2}/{logKey3}/{logKey4}/{logKey5}/{logKey6}", params={"method=GET"}, method=RequestMethod.POST, headers="content-type=application/json")
	public @ResponseBody ComMessage<Map<String, Object>, List<GSTRLogDetail>> getTrackingLogDetail(
			HttpSession  httpSession,
			@PathVariable("logKey1") String logKey1,
			@PathVariable("logKey2") String logKey2,
			@PathVariable("logKey3") String logKey3,
			@PathVariable("logKey4") String logKey4,
			@PathVariable("logKey5") String logKey5,
			@PathVariable("logKey6") String logKey6,
			@RequestBody ComMessage<Map<String, Object>, List<GSTRLogDetail>> comMessage,
			Locale locale,
			HttpServletRequest request
	) throws Exception {

		//----------------------------------------------------------------------------
		//여긴 비지니스 코드만 작성해라.
		//----------------------------------------------------------------------------
		{
			comMessage.getRequestObject().put("logKey1", logKey1);
			comMessage.getRequestObject().put("logKey2", logKey2);
			comMessage.getRequestObject().put("logKey3", logKey3);
			comMessage.getRequestObject().put("logKey4", logKey4);
			comMessage.getRequestObject().put("logKey5", logKey5);
			comMessage.getRequestObject().put("logKey6", logKey6);

			List<GSTRLogDetail> list = monitorService.getTrackingLogDetail(comMessage.getRequestObject());


			//서비스 처리 종료시간을 얻어 CM에 세팅한다.
			String endTime = Util.getFormatedDate("yyyyMMddHHmmssSSS");
			comMessage.setEndTime(endTime);

			//통신메시지에 처리결과 코드/메시지를 등록한다.
			String errorCd = "";
			String errorMsg = "";
			if (list == null || list.size() == 0) {// 결과가 없을 경우 비지니스 예외 처리
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

			return comMessage;
		}
	}

}
