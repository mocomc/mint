package pep.per.mint.front.controller.op;

import java.util.ArrayList;
import java.util.HashMap;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import pep.per.mint.common.data.basic.ComMessage;
import pep.per.mint.common.data.basic.TrackingLog;
import pep.per.mint.common.data.basic.TrackingLogDetail;
import pep.per.mint.common.data.basic.User;
import pep.per.mint.common.util.Util;
import pep.per.mint.database.service.co.CommonService;
import pep.per.mint.database.service.op.TrackingService;
import pep.per.mint.front.exception.ControllerException;
import pep.per.mint.front.util.MessageSourceUtil;

/**
 * <blockquote>
 * <pre>
 * <B>모니터링 CRUD  서비스 제공 RESTful Controller</B>
 * -------------------------------------------------------------
 * 개발할 메소드 목록
 * -------------------------------------------------------------
 * OP-01-10-001	트레킹 로그 리스트 조회	REST-R01-OP-01-10
 * OP-01-10-002	트레킹 로그 상세 조회	REST-R02-OP-01-10
 *
 *
 * @author Solution Interface Portal </pre>
 *</blockquote>
 */
@Controller
@RequestMapping("/op")
public class TrackingController {
	private static final Logger logger = LoggerFactory.getLogger(TrackingController.class);

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
	TrackingService trackingService;

	@Autowired
	CommonService commonService;

	/**
	 * <pre>
	 * 트레킹 로그 리스트 조회
	 * API ID : REST-R01-OP-01-10
	 * </pre>
	 *
	 * @param httpSession 세션
	 * @param comMessage 요청응답통신객체
	 * @param locale 다국어지원을 위한 로케일
	 * @param request the request
	 * @return ComMessage 통신객체
	 * @throws Exception the exception
	 * @author Solution Interface Portal
	 * @throws ControllerException
     * @since version 4.0(2020.04)
     */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/tracking/log/list", params = "method=GET", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<Map<String,Object>, List<TrackingLog>> getTrackingLogList(
			HttpSession httpSession,
			@RequestBody ComMessage<Map<String,Object>, List<TrackingLog>> comMessage,
			Locale locale, HttpServletRequest request) throws Exception, ControllerException {
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

			List<TrackingLog> list = null;
			{

				int totalCount = params.get("total") == null ? 0 : (Integer)params.get("total");
				if( totalCount == 0 ) {
					totalCount = trackingService.getTrackingLogListTotalCount(params);
				}

				if(totalCount > 0) {
					list = trackingService.getTrackingLogList(params);
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
	 * API ID : REST-R02-OP-01-10
	 * </pre>
	 *
	 * @param httpSession 세션
	 * @param logKey1 the log key 1
	 * @param logKey2 the log key 2
	 * @param comMessage 요청응답통신객체
	 * @param locale 다국어지원을 위한 로케일
	 * @return ComMessage 통신객체
	 * @throws Exception the exception
	 * @author Solution Interface Portal
     * @since version 4.0(2020.04)
     */
	@RequestMapping(
			value="/tracking/log/detail/{integrationId}/{trackingDate}/{orgHostId}", params={"method=GET"}, method=RequestMethod.POST, headers="content-type=application/json")
	public @ResponseBody ComMessage<Map<String, Object>, List<TrackingLogDetail>> getTrackingLogDetail(
			HttpSession  httpSession,
			@PathVariable("integrationId") String integrationId,
			@PathVariable("trackingDate") String trackingDate,
			@PathVariable("orgHostId") String orgHostId,
			@RequestBody ComMessage<Map<String, Object>, List<TrackingLogDetail>> comMessage,
			Locale locale,
			HttpServletRequest request
	) throws Exception {

		//----------------------------------------------------------------------------
		//여긴 비지니스 코드만 작성해라.
		//----------------------------------------------------------------------------
		{
			comMessage.getRequestObject().put("integrationId", integrationId);
			comMessage.getRequestObject().put("trackingDate", trackingDate);
			comMessage.getRequestObject().put("orgHostId", orgHostId);

			List<TrackingLogDetail> list = trackingService.getTrackingLogDetail(comMessage.getRequestObject());


			for(TrackingLogDetail detail : list) {
				if(detail.getMsg() != null) {
					byte[] bdata = detail.getMsg().getBytes(1, (int) detail.getMsg().length());
					detail.setMsgToString(new String(bdata));
				}
				if(detail.getMsgToByte() != null) {
					logger.debug(new String(detail.getMsgToByte()));
					detail.setMsgToString(new String(detail.getMsgToByte(), "UTF-8"));
				}
			}

			//서비스 처리 종료시간을 얻어 CM에 세팅한다.
			String endTime = Util.getFormatedDate("yyyyMMddHHmmssSSS");
			comMessage.setEndTime(endTime);

			//통신메시지에 처리결과 코드/메시지를 등록한다.
			String errorCd = "";
			String errorMsg = "";
			if (list == null || list.size() == 0) {// 결과가 없을 경우 비지니스 예외 처리
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
