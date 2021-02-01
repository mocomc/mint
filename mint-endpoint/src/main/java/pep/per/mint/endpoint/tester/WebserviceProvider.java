/**
 * Copyright 2020 Mocomsys Inc.  All Rights Reserved.
 */
package pep.per.mint.endpoint.tester;

import java.io.FileOutputStream;
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

import com.tmax.tibero.jdbc.data.charset.Charset;

import pep.per.mint.common.data.basic.ComMessage;

import pep.per.mint.common.util.Util;
import pep.per.mint.endpoint.service.deploy.ModelDeployService; 

/**
 * @author whoana
 * @since Sep 14, 2020
 */
@RequestMapping("/rt")
@Controller
public class WebserviceProvider {
	
	private static final Logger logger = LoggerFactory.getLogger(WebserviceProvider.class);

	@Autowired
	ReloadableResourceBundleMessageSource messageSource;
	
	private ServletContext servletContext;	
	 
	
	@RequestMapping(value = "/models/deploys/testers/ws/provider", method = RequestMethod.POST, headers = "content-type=application/xml")
	public @ResponseBody String deployToService(
		HttpSession httpSession,
		@RequestBody String xmlData,
		Locale locale, HttpServletRequest request) throws Exception, Exception {
		
		String modelName = "/Users/whoana/DEV/workspace/mint/mint-endpoint/deploy/"+request.getParameter("modelName") + ".xml";
		
		FileOutputStream fos = new FileOutputStream(modelName);
		byte [] data = xmlData.getBytes();
		fos.write(data);
		fos.flush();
		
		return "receive:ok";
	}
}


