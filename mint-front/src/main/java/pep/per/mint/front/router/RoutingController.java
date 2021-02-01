/**
 * Copyright 2020 Mocomsys Inc.  All Rights Reserved.
 */
package pep.per.mint.front.router;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import pep.per.mint.common.data.basic.Service;
import pep.per.mint.common.util.Util;
import pep.per.mint.database.service.co.CommonService;

/**
 * @author whoana
 * @since Aug 19, 2020
 */
@Controller
public class RoutingController {

	Logger logger = LoggerFactory.getLogger(getClass());
	
	public static final String NOT_FOUND_PAGE = "/view/main/404.jsp";
	
	@Autowired
	CommonService commonService;
	
	static Map<String, Service> routingMap = null;
	
	/**
	 * <pre>
	 * 라우터를 사용할 경우 제약사항 
	 * 	1) url을 통해 serviceId 를 넘겨준다.  
	 * 		예) http://127.0.0.1:8080/mint/router/REST-R01-AN-06-00
	 * 	2) PathVariable 을 사용해야하는 URL의 경우 패스에 사용한 값을 key=value 형태의 url 파라메터로 넘겨 주도록 한다.
	 * 		예) http://127.0.0.1:8080/mint/routers/REST-R01-CO-02-00-005?level1=IM&level2=01
	 * </pre> 
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/routers/{serviceId}", method=RequestMethod.POST)
	public String route(@PathVariable("serviceId") String serviceId, HttpServletRequest request) throws Exception {
		String url = Util.join("forward:",getUrl(serviceId));
		Enumeration names = request.getParameterNames();
		while (names.hasMoreElements()) {
			String key = (String)names.nextElement();
			url = url.replaceFirst("[{]"+key+"[}]", request.getParameter(key));
		}
		url = url + "&locale=" + request.getParameter("locale");
		logger.debug("service[" + serviceId + "] forward url:" + url); 
		return url;
	}
	 
	/**
	 * @param restId
	 * @return
	 * @throws Exception 
	 */
	private String getUrl(String restId) throws Exception {
		
		if(routingMap == null) {
			routingMap = new ConcurrentHashMap();
			List<Service> services = commonService.getAppServiceList();
			if(!Util.isEmpty(services)) {
				routingMap = new HashMap<String, Service>();
				for (Service service : services) {
					if(Service.SERVICE_TYPE_REST.equals(service.getServiceType())) {
						routingMap.put(service.getServiceCd(), service);
					}
				}
			}
		}

		Service service = routingMap.get(restId);
		return service == null || Util.isEmpty(service.getServiceUri()) ? NOT_FOUND_PAGE : service.getServiceUri();

	}
	

	
}
