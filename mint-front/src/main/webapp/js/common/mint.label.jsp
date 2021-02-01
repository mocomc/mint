<%--
/*****************************************************************************
 * Program Name : mint.label.jsp
 * Description
 *   - UI 에서 사용하는 Label 정보를 조회한다
 *   - Label 정보는 mint_label javascript 객체에 생성하여 관리한다
 *
 *   - Access 방법
 *     mint.label.{함수명};
 *     mint.label.getLabel(key);

 * branch working
 * --------------------------
 * 20170721
 * rest call을 스프링빈 호출로 변경
 *
 ****************************************************************************/
 --%>
<%@page import="java.util.LinkedHashMap"%>
<%@page import="java.util.Set"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@page import="pep.per.mint.common.data.basic.ComMessage"%>
<%@page import="java.io.UnsupportedEncodingException"%>
<%@page import="java.io.OutputStream"%>
<%@page import="java.io.InputStreamReader"%>
<%@page import="java.io.BufferedReader"%>
<%@page import="java.io.InputStream"%>
<%@page import="java.net.SocketTimeoutException"%>
<%@page import="java.net.HttpURLConnection"%>
<%@page import="java.net.URL"%>
<%@page import="pep.per.mint.common.util.Util"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>

<!-- branch 추가코드  -->
<%@page import="pep.per.mint.database.service.su.AppManagementService"%>
<%@page import="org.springframework.web.context.WebApplicationContext"%>
<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="org.springframework.web.servlet.FrameworkServlet"%>
<!-- branch 추가코드  -->

<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%
	response.setContentType("text/javascript; charset=utf-8");
	//WebApplicationContext wac = WebApplicationContextUtils.getWebApplicationContext(getServletContext(), FrameworkServlet.SERVLET_CONTEXT_PREFIX+"MintFrontWebAppServlet");
	//servlet 2.5 spec 에서 getServletContext() 을 지원하지 않아 소스 변경, getServletContext() 메소드는 서블릿 스펙 3.0 부터 지원함.
	//보통 servlet-api-xx.jar는 소스 배포시 WAS 서 제공한는 것을 배포하므로 배포 환경을 확인할 필요가 있다.
	//20170814, 삼성전자에서 발견
	WebApplicationContext wac = WebApplicationContextUtils.getWebApplicationContext(application, FrameworkServlet.SERVLET_CONTEXT_PREFIX+"MintFrontWebAppServlet");

	AppManagementService appManagementService = (AppManagementService) wac.getBean("appManagementService");
	Map responseObject = appManagementService.getMessagesByLangId( new HashMap());
	//System.out.println("resultMap:" + Util.toJSONString(responseObject));


	/*
	response.setContentType("text/javascript; charset=utf-8");
	String url ="http://localhost:" + request.getLocalPort() + "/mint/su/management/app/messages-by-lang?method=GET";

	//-----------------------------------------------------------------------------
	// ComMessage
	// TODO : LANG-ID 를 어디서 받아오지??
	//-----------------------------------------------------------------------------
	ComMessage<Map,Map> requestMessage = new ComMessage<Map,Map>();
	{
		Map<String,String> requestObject = new HashMap<String,String>();
		//requestObject.put("langId", "cn");

		//requestMessage.setObjectType("ComMessage");
		requestMessage.setRequestObject( requestObject );
		requestMessage.setUserId("guest");
		requestMessage.setStartTime("20151125120000");
		requestMessage.setCheckSession(false);
	}

	//-----------------------------------------------------------------------------
	// HTTP URL Connection
	//-----------------------------------------------------------------------------
	URL connectUrl = new URL(url);
	HttpURLConnection httpConn = (HttpURLConnection)connectUrl.openConnection();
	{
		httpConn.setDoInput(true);
		httpConn.setDoOutput(true);
		httpConn.setUseCaches(false);
		httpConn.setDefaultUseCaches(false);
		httpConn.setConnectTimeout(10000);
	    httpConn.setReadTimeout(10000);
	    httpConn.setAllowUserInteraction(false);
		httpConn.setRequestMethod("POST");
		httpConn.addRequestProperty("Content-Type", "application/json; charset=utf-8");
	}
    // 연결
    httpConn.connect();


	//-----------------------------------------------------------------------------
	// Send Data
	//-----------------------------------------------------------------------------
    OutputStream ot = httpConn.getOutputStream();
    byte[] sendByte = null;
    try {
    	sendByte = Util.toJSONString(requestMessage).getBytes("utf-8");
    } catch (UnsupportedEncodingException e) {
    	StringBuffer buf = new StringBuffer("Invalid encoding [")
    		.append( "utf-8" )
    		.append("]");
        throw new Exception(buf.toString(), e);
    }
    StringBuffer result = new StringBuffer();

	ot.write( sendByte );
	ot.flush();
	ot.close();

    // 응답 결과 확인
    //httpConn.setReadTimeout(20000);
    //System.out.println("Socket Read Timeout : " + httpConn.getReadTimeout());

    try {
        if( httpConn.getResponseCode() != HttpURLConnection.HTTP_OK ) {
        	System.out.println("httpConn_responseCode : " + httpConn.getResponseCode() + " URL : " +  url + " requestMessage : " + requestMessage);
        }
    } catch(SocketTimeoutException e) {
    	//System.out.println(e.getMessage());
    }

	//-----------------------------------------------------------------------------
	// Read Data
	//-----------------------------------------------------------------------------
    InputStream in = httpConn.getInputStream();
    BufferedReader br = new BufferedReader( new InputStreamReader(in, "utf-8"));

    String line = null;
    while( (line = br.readLine()) != null ) {
    	result.append( line );
    }

	//-----------------------------------------------------------------------------
	// I/O, Connection Close
	//-----------------------------------------------------------------------------
    br.close();
    in.close();

    httpConn.disconnect();

    //System.out.println("REQ[" + requestMessage + "] RES[" + result.toString() +"]");

	ComMessage<Map,Map> responseMessage = Util.jsonToObject(result.toString(), ComMessage.class);
	LinkedHashMap responseObject = (LinkedHashMap)responseMessage.getResponseObject();
	*/

	Set langIdKeys = null;
	if( responseObject != null ) {
		langIdKeys = responseObject.keySet();
		Object[] langIdArray = langIdKeys.toArray();
%>

/**
 * Label 정보 모듈
 */
var mint_label = function() {

};

/**
 * 화면 Label 이 담겨질 Map
 * langId 별로 Label 정보가 담겨진다
 */
mint_label.prototype.labelMap = new Map();

/**
 * labelMap 에 langId 에 해당 하는 Label 정보를 설정
 * 중복허용
 * @param key
 * @param value
 */
mint_label.prototype.setLabel = function(key, value) {
	try {
		this.labelMap.put(key,value);
	} catch( e ) {
		mint.common.errorLog(e, {"fnName" : "mint.label.setLabel"});
	}
};

/**
 * labelMap 에서 mint.lang 기준, Label 의 key 값에 해당하는 value 를 return
 * @param key
 * @returns
 */
mint_label.prototype.getLabel = function(key) {
	try {
		var langMap = this.labelMap.get(mint.lang);

		if( mint.common.isEmpty(langMap) ) {
			return "";
		} else {
			return langMap.get(key);
		}

	} catch( e ) {
		mint.common.errorLog(e, {"fnName" : "mint.label.getLabel"});
	}
};



/**
 * Label 정보를 변수에 keep 한다
 */
mint_label.prototype.init = function(options) {
<%
	for(Object langId : langIdArray) {
		Map labelMap = (Map)responseObject.get(langId);
		if( labelMap != null ) {
			Set labelIdKeys = labelMap.keySet();
			Object[] labelIdArray = labelIdKeys.toArray();
%>
			var <%= langId.toString() %> = new Map();
<%
            for(Object labelId : labelIdArray) {
%>			<%= langId %>.put('lb-<%= labelId.toString() %>' , '<%= labelMap.get(labelId) %>');
<%
            }//end for
%>
			mint.label.setLabel('<%= langId.toString() %>' , <%= langId.toString() %>);
<%
		}//end if
	}//end for
%>
}


/**
 * Label 을 화면에 붙인다
 */
mint_label.prototype.attachLabel = function(options) {
	switch( mint.lang ) {
<%
	for(Object langId : langIdArray) {
		Map labelMap = (Map)responseObject.get(langId);
		if( labelMap != null ) {
			Set labelIdKeys = labelMap.keySet();
			Object[] labelIdArray = labelIdKeys.toArray();
%>
			case '<%= langId.toString() %>' :
<%
            for(Object labelId : labelIdArray) {
%>				$('.lb-<%= labelId.toString() %>').text('<%= labelMap.get(labelId) %>');
<%
            }
%>
				break;
<%
		}//end if
	}//end for
%>
			default :
				break;
	}
}



/**
 * mint 객체에 추가한다
 */
mint.addConstructor(function() {
	try {
	    if (typeof mint.label === "undefined") {
	        mint.label = new mint_label();
	        mint.label.init();
	    }
    } catch( e ) {

    }
});
<%
	} else {
%>


<%
	}
%>
