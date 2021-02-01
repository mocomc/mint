/**
 * Copyright 2018 mocomsys Inc. All Rights Reserved.
 */
package pep.per.mint.front.controller.co;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import pep.per.mint.common.data.basic.ComMessage;
import pep.per.mint.common.data.basic.TRLog;
import pep.per.mint.common.util.Util;
import pep.per.mint.front.exception.ControllerException;

/**
 * <pre>
 * pep.per.mint.front.controller.co
 * TestController.java
 * </pre>
 * @author whoana
 * @date Dec 11, 2019
 */
@Controller
@RequestMapping("/test")
@SuppressWarnings({ "rawtypes", "unchecked" })
public class TestController {
 
	/**
	 * The Message source.
	 */
	@Autowired
	ReloadableResourceBundleMessageSource messageSource;

    @Autowired
    private ServletContext servletContext;

	/**
	 * <pre>
	 * 오류 / 지연 현황 조회
	 *
	 * </pre>
	 * @param httpSession 세션
	 * @param comMessage 요청응답통신객체
	 * @param locale 다국어지원을 위한 로케일
	 * @param request the request
	 * @return ComMessage 통신객체
	 * @throws Exception the exception
	 * @throws Exception the exception
	 * @author Solution TF
     * @since version 1.0(2015.07)
     */
    @RequestMapping(value = "/testme", method = RequestMethod.POST, headers = "content-type=application/json")
	public @ResponseBody ComMessage<Map, Map> testme(
			HttpSession httpSession,
			@RequestBody ComMessage<Map, Map> comMessage,
			Locale locale, HttpServletRequest request) throws Exception, ControllerException {
    	
    	Map params = comMessage.getRequestObject();
    	System.out.println("params:" + Util.toJSONPrettyString(params));
    	comMessage.setResponseObject(params);
    	return comMessage; 
    }
     

}
