<!DOCTYPE html>
<%@page import="pep.per.mint.common.util.Util"%>
<%@page import="pep.per.mint.common.data.basic.ComMessage"%>
<%@page import="pep.per.mint.front.controller.co.CommonController"%>
<%@page import="org.springframework.web.servlet.FrameworkServlet"%>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="org.springframework.web.context.WebApplicationContext"%>
<%@page import="pep.per.mint.common.data.basic.LoginInfo"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="cutCarriageReturn.jsp" %>


<%
	String userid = null;
	String toa = null;

	try {
		String ticket = (String) request.getParameter("ticket");

		if( ticket != null) {
			userid = ticket;
		}

	} catch (Exception e) {
		System.out.println("티켓검증 실패! 로그인 페이지로 이동필요");
		String redirectPage = "./view/main/login.jsp";
		response.sendRedirect( redirectPage );
		//복호화 실패 로그인 페이지로 이동
	}

%>
<html>

<head>

    <meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta http-equiv="Content-Type" content="text/html;charset=UTF-8" />
	<meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate" />
	<meta http-equiv="Pragma" content="no-cache" />
	<meta http-equiv="Expires" content="0" />
	<meta http-equiv="imagetoolbar" content="no" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge" />
	<meta name="Robots" content="All" />
	<meta name="Keywords" content="" />
	<meta name="Description" content="" />

	<!---------------------------------------------------------------------->
	<!-- JavaScript :: JQuery                                             -->
	<!---------------------------------------------------------------------->
	<script type="text/javascript" src="../../js/external/jquery/js/jquery.min.js"></script>

	<!---------------------------------------------------------------------->
	<!-- JavaScript :: Common                                             -->
	<!---------------------------------------------------------------------->
	<script type="text/javascript" src="../../js/common/mint.base.js"></script>
	<script type="text/javascript" src="../../js/common/mint.common.js"></script>
	<script type="text/javascript" src="../../js/common/mint.message.js"></script>
</head>

<body>
</body>
<script>
$(document).ready(function() {
<%
	//login service call
	if( userid != null && userid != "" ) {
		//REST Service Call
		WebApplicationContext wac = WebApplicationContextUtils.getWebApplicationContext(application, FrameworkServlet.SERVLET_CONTEXT_PREFIX+"MintFrontWebAppServlet");
		CommonController commonController = (CommonController) wac.getBean("commonController");
		try {
			LoginInfo loginInfo = new LoginInfo();
			loginInfo.setUserId(userid);
			loginInfo.setSso("true");

			ComMessage req = new ComMessage();
			req.setAppId("ssoLogin.jsp");
			req.setUserId("iip");
			req.setStartTime(Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI));
			req.setCheckSession(false);
			req.setRequestObject(loginInfo);

			ComMessage msg = commonController.login(request.getSession(), req, null, request);
			if(msg.getErrorCd().equals("0")) {
%>
				window.location='../main/index.jsp' + location.search;
<%
			} else {
%>
				mint.common.alert('SSO-ID 정보가 올바르지 않습니다.');
				window.location='../main/login.jsp' + location.search;
<%
			}
		} catch(Throwable e) {
			e.printStackTrace();
			String errorMsg = "HTTP 500 에러";
		}
	} else {
		//redirection to login.jsp
		String redirectPage = "./view/main/login.jsp";
		String queryString = request.getQueryString();
		if( queryString != null && queryString != "" ) {
			redirectPage = redirectPage + "?" + queryString;
		}
		response.sendRedirect( redirectPage );
	}
%>

});//end document.ready()
</script>

</html>