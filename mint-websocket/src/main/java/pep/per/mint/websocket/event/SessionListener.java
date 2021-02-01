/**
 * Copyright 2018 Mocomsys Inc.  All Rights Reserved.
 */
package pep.per.mint.websocket.event;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.FrameworkServlet;
import org.springframework.web.socket.CloseStatus;

import pep.per.mint.common.util.Util;
import pep.per.mint.websocket.service.FrontChannelService;

/**
 * <pre>
 * pep.per.mint.websocket.event
 * SessionListener.java
 * </pre>
 * @author whoana
 * @date 2018. 7. 25.
 */
public class SessionListener implements HttpSessionListener{

	Logger logger = LoggerFactory.getLogger(SessionListener.class);

	@Override
	public void sessionCreated(HttpSessionEvent se) {
		HttpSession session = se.getSession();
		logger.debug(Util.join("HttpSession:",session.getId(), " created"));

		WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(session.getServletContext(),FrameworkServlet.SERVLET_CONTEXT_PREFIX+"MintFrontWebAppServlet");
		FrontChannelService frontChannelService = (FrontChannelService) context.getBean("frontChannelService");

	}


	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		HttpSession session = se.getSession();
		logger.debug(Util.join("HttpSession:",session.getId(), " destroyed"));

		WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(session.getServletContext(),FrameworkServlet.SERVLET_CONTEXT_PREFIX+"MintFrontWebAppServlet");
		FrontChannelService frontChannelService = (FrontChannelService) context.getBean("frontChannelService");
		frontChannelService.logout(session.getId(), new CloseStatus(4888,"HTTPSession and WebSocketSession closed"));

	}

}
