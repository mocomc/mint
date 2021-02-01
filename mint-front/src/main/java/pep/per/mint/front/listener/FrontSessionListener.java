package pep.per.mint.front.listener;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.FrameworkServlet;

import pep.per.mint.common.data.basic.User;
import pep.per.mint.common.util.Util;
import pep.per.mint.database.service.co.SecurityService;
import pep.per.mint.front.controller.co.CommonController;
import pep.per.mint.front.controller.co.SecurityController;
import pep.per.mint.front.service.FrontSecurityService;

public class FrontSessionListener implements HttpSessionListener {

	private static final Logger logger = LoggerFactory.getLogger(CommonController.class);

	SecurityService securityService;
	
	SecurityController securityController;
	
	FrontSecurityService frontSecurityService;

	@Override
	public void sessionCreated(HttpSessionEvent se) {

	}

	@Override
	public void sessionDestroyed(HttpSessionEvent se) {

		HttpSession httpSession = se.getSession();
		logger.debug(Util.join("HttpSession:", httpSession.getId(), " destroyed"));

		// 로그인 히스토리 사용시 로직

		if (httpSession.getAttribute("historyYn") != null && (Boolean) httpSession.getAttribute("historyYn")) {
			try {

				WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(
						httpSession.getServletContext(),
						FrameworkServlet.SERVLET_CONTEXT_PREFIX + "MintFrontWebAppServlet");
				securityService = (SecurityService) context.getBean("securityService");
				frontSecurityService = (FrontSecurityService) context.getBean("frontSecurityService");

				User user = (User) httpSession.getAttribute("user");

				if (user != null) {

					Map<String, Object> loginParams = new HashMap();

					loginParams.put("userId", user.getUserId());
					loginParams.put("sessionId", httpSession.getId());
					loginParams.put("loginDate", httpSession.getAttribute("loginDate"));
					loginParams.put("logoutDate", Util.getFormatedDate());

					logger.debug(loginParams.toString());

					securityService.updateLoginHistory(loginParams);
					
					if(httpSession.getAttribute("duplicationCheckYn")!=null && (Boolean) httpSession.getAttribute("duplicationCheckYn")){
						String checkKey = (String)httpSession.getAttribute("checkKey");
						frontSecurityService.removeSession(checkKey);
					}				

//					httpSession.invalidate();
				}

			} catch (Exception e) {
				logger.debug("로그아웃 이력 갱신 실패");
			}
		}

	}

}
