<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	//--------------------------------------------
	// 500 status 를 표출하지 않도록 404 로 위장
	//--------------------------------------------
	response.setStatus(404);
%>
<html>
<head>
	<meta charset="UTF-8">
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
	<!--                 Cascading Style Sheet                            -->
	<!--                                                                  -->
	<!--                                                                  -->
	<!---------------------------------------------------------------------->


	<!-- ................................................................ -->
	<!-- Cascading Style Sheet :: init                                    -->
	<!-- ................................................................ -->
	<link rel="stylesheet" type="text/css" href="/mint/css/common/init.css">

	<!---------------------------------------------------------------------->
	<!-- Cascading Style Sheet :: bootstrap-inspina                       -->
	<!---------------------------------------------------------------------->
	<link rel="stylesheet" type="text/css" href="/mint/css/external/bootstrap-inspinia/css/bootstrap.css">
	<link rel="stylesheet" type="text/css" href="/mint/css/external/bootstrap-inspinia/font-awesome/css/font-awesome.css">
	<link rel="stylesheet" type="text/css" href="/mint/css/external/bootstrap-inspinia/css/animate.css">
	<link rel="stylesheet" type="text/css" href="/mint/css/external/bootstrap-inspinia/css/style.css">

	<!---------------------------------------------------------------------->
	<!-- Cascading Style Sheet :: common                                  -->
	<!---------------------------------------------------------------------->


    <title>Integration & Interface Portal</title>
</head>

<body class="gray-bg">
    <div class="middle-box text-center animated fadeInDown">
		<h2>IIP Service Failed</h2>
		<h3 class="font-bold">Unknown Error</h3>
		<div class="error-desc">
		    The server encountered something unexpected that didn't allow it to complete the request. We apologize.<br/>
		    You can go back to main page: <br/><a href="/mint/view/main/index.jsp" class="btn btn-primary m-t">Home</a>
		</div>
    </div>
</body>

</html>