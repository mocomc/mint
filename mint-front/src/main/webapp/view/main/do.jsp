<!DOCTYPE html>
<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@page import="org.springframework.web.servlet.FrameworkServlet"%>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="org.springframework.web.context.WebApplicationContext"%>
<%@page import="pep.per.mint.database.service.co.CommonService"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	//---------------------------------------------------------------
	//subject	: ssl 강제 redirection 처리를 위한 주소 검증
	//---------------------------------------------------------------

	// 화면에 display 할 첫 페이지 지정(sso, login, index 등)
	String redirectPage = "";
	WebApplicationContext wac = WebApplicationContextUtils.getWebApplicationContext(application, FrameworkServlet.SERVLET_CONTEXT_PREFIX+"MintFrontWebAppServlet");
	CommonService commonService = (CommonService) wac.getBean("commonService");

	try {
		Map<String,List<String>> environmentalValues = commonService.getEnvironmentalValues("system","init.login.url");

		if(environmentalValues == null || environmentalValues.size() == 0) {
			redirectPage = "./view/main/login.jsp";
		} else {
			List<String> env = environmentalValues.get("system.init.login.url");
			if (env != null && env.size() > 0) {
				redirectPage = env.get(0);
			}
		}
	} catch(Exception e) {
		redirectPage = "./view/main/login.jsp";
	}

	String queryString = request.getQueryString();
	if( queryString != null && queryString != "" ) {
		redirectPage = redirectPage + "?" + queryString;
	}
	response.sendRedirect( redirectPage );
%>