package pep.per.mint.front.controller.tu;

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

import pep.per.mint.common.data.basic.tutorial.Flower;
import pep.per.mint.common.data.basic.ComMessage;
import pep.per.mint.common.util.Util;
import pep.per.mint.database.service.tu.TutorialService;
import pep.per.mint.front.exception.ControllerException;
import pep.per.mint.front.util.MessageSourceUtil;
@Controller
@RequestMapping("/tu")
@SuppressWarnings({ "rawtypes" })
public class TutorialController {
	private static final Logger logger = LoggerFactory.getLogger(TutorialController.class);


	/**
	 * The Tutorial service.
     */
	@Autowired
	TutorialService tutorialService;
	
	/**
	 * The Message source.
	 */
	@Autowired
	ReloadableResourceBundleMessageSource messageSource;
	
    @Autowired
    private ServletContext servletContext;

	/**
	 * <pre>
	 * API ID :
	 * </pre>
	 * @param httpSession 세션
	 * @param userId the user id
	 * @param comMessage 요청응답통신객체
	 * @param locale 다국어지원을 위한 로케일
	 * @param request the request
	 * @return ComMessage 통신객체
	 * @throws Exception the exception
	 * @throws Exception the exception
     */
	@RequestMapping(
			value="/flowers", 
			params="method=GET", 
			method=RequestMethod.POST, 
			headers="content-type=application/json")
	public @ResponseBody ComMessage<Map, List<Flower>> getFlowerList( 
			HttpSession  httpSession, 
			@RequestBody ComMessage<Map, List<Flower>> comMessage,
			Locale locale,
			HttpServletRequest request
	) throws Exception, ControllerException {
		//----------------------------------------------------------------------------
		//여긴 비지니스 코드만 작성해라.
		//----------------------------------------------------------------------------
		{
			String errorCd = "";
			String errorMsg = "";
			List<Flower> list = null; 
			   
			Map params = comMessage.getRequestObject();
			if(params == null) params = new HashMap();
			//-----------------------------------------------
			// 필드 체크 
			//-----------------------------------------------					
			{
			}
			 
			 
			
			//-----------------------------------------------
			//list 를 얻어온다. 
			//-----------------------------------------------
			{
				list = tutorialService.getFlowerList(params);
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
			}
			return comMessage;
		}
	}

}
