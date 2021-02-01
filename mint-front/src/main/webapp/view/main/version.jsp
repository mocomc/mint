<%--
/*****************************************************************************
 * Program Name : version.jsp
 * Description
 *   - IIP 배포 버전 정보를  조회한다
 * To-do
 *  - 포털환경변수값을 보여주면 어떨까?
 ****************************************************************************/
 --%>
<%@page import="pep.per.mint.common.data.basic.VersionInfo"%>
<%@page import="pep.per.mint.common.data.basic.ComMessage"%>
<%@page import="pep.per.mint.common.util.Util"%>
<%@page import="pep.per.mint.front.exception.ControllerException"%>
<%@page import="pep.per.mint.front.controller.su.VersionController"%>
<%@page import="org.springframework.web.context.WebApplicationContext"%>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="org.springframework.web.servlet.FrameworkServlet"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<title> IIP VERSION INFORMATION </title>
<script type="text/javascript" src="../../js/external/jquery/js/jquery.min.js"></script>
<script type="text/javascript" src="../../js/external/jquery/js/jquery.rolling.js"></script>
</head>
<body>
	<div>--IIP 배포버전정보--------------------</div>
	<div id="contents"></div>
	<div id="errors"></div>
</body>
<%
	WebApplicationContext wac = WebApplicationContextUtils.getWebApplicationContext(application, FrameworkServlet.SERVLET_CONTEXT_PREFIX+"MintFrontWebAppServlet");
	VersionController vc = (VersionController) wac.getBean("versionController");
	try {
		ComMessage req = new ComMessage();
		req.setAppId("version.jsp");
		req.setUserId("iip");
		req.setStartTime(Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI));
		req.setCheckSession(false);
		ComMessage msg = vc.getVersionInfo(request.getSession(), req, null, request);
		VersionInfo versionInfo = (VersionInfo)msg.getResponseObject();
		String versionInfoJson = Util.toJSONString(versionInfo);
		System.out.println("version:" + versionInfoJson);
%>
<script>
	var versionInfo = JSON.parse('<%=versionInfoJson%>');
	var versionString = "<P>버전:" + versionInfo.version +
                        "<P>사이트코드:" + versionInfo.siteCode +
                        "<P>소스전달일:" + versionInfo.deployDate +
                        "<p>서버위치:" + versionInfo.serverAddress +
                        "<P>description:" + versionInfo.description;
	$('#contents').html(versionString);
</script>

<%
	}catch(Throwable e){
		e.printStackTrace();
		String errorMsg = "HTTP 500 에러";
%>
<script>
	$('#errors').html("error: + <%=errorMsg%>");
</script>
<%
	}
%>
</html>

