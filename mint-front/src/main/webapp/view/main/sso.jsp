<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
	<!-- ................................................................ -->
	<!-- favicon                                                          -->
	<!-- ................................................................ -->
	<link rel="shortcut icon" href="../../img/favicon.ico">

	<!---------------------------------------------------------------------->
	<!-- Cascading Style Sheet :: common                                  -->
	<!---------------------------------------------------------------------->
	<link rel="stylesheet" type="text/css" href="../../css/common/common.css">

	<!---------------------------------------------------------------------->
	<!-- Cascading Style Sheet :: bootstrap-inspina                       -->
	<!---------------------------------------------------------------------->
	<link rel="stylesheet" type="text/css" href="../../css/external/bootstrap-inspinia/css/bootstrap.css">
	<link rel="stylesheet" type="text/css" href="../../css/external/bootstrap-inspinia/font-awesome/css/font-awesome.css">
	<link rel="stylesheet" type="text/css" href="../../css/external/bootstrap-inspinia/css/animate.css">
	<link rel="stylesheet" type="text/css" href="../../css/external/bootstrap-inspinia/css/style.css">
	<link rel="stylesheet" type="text/css" href="../../css/external/bootstrap-inspinia/css/plugins/gritter/jquery.gritter.css" rel="stylesheet">
	<link rel="stylesheet" type="text/css" href="../../css/external/bootstrap-inspinia/css/plugins/iCheck/custom.css" rel="stylesheet" >
	<link rel="stylesheet" type="text/css" href="../../css/external/bootstrap-inspinia/css/plugins/sweetalert/sweetalert.css">
	<link rel="stylesheet" type="text/css" href="../../css/external/bootstrap-inspinia/css/plugins/morris/morris-0.4.3.min.css">

	<!---------------------------------------------------------------------->
	<!-- JavaScript :: JQuery                                             -->
	<!---------------------------------------------------------------------->
	<script type="text/javascript" src="../../js/external/jquery/js/jquery.min.js"></script>
	<script type="text/javascript" src="../../js/external/jquery/js/jquery.rolling.js"></script>

	<!---------------------------------------------------------------------->
	<!-- JavaScript :: Common                                             -->
	<!---------------------------------------------------------------------->
	<script type="text/javascript" src="../../js/common/mint.object.js"></script>
	<script type="text/javascript" src="../../js/common/jquery.mint.js"></script>
	<script type="text/javascript" src="../../js/common/mint.base.js"></script>
	<script type="text/javascript" src="../../js/common/mint.common.js"></script>
	<script type="text/javascript" src="../../js/common/mint.rest.js"></script>
	<script type="text/javascript" src="../../js/common/mint.ui.js"></script>
	<script type="text/javascript" src="../../js/common/mint.message.js"></script>
	<script type="text/javascript" src="../../js/common/mint.xy.jsp"></script>

	<!-- ................................................................ -->
	<!-- JavaScript :: jsbn                                               -->
	<!-- ................................................................ -->
	<script type="text/javascript" src="../../js/external/jsencrypt/jsbn/base64.js"></script>
	<script type="text/javascript" src="../../js/external/jsencrypt/jsbn/jsbn.js"></script>
	<script type="text/javascript" src="../../js/external/jsencrypt/jsbn/jsbn2.js"></script>
	<script type="text/javascript" src="../../js/external/jsencrypt/jsbn/prng4.js"></script>
	<script type="text/javascript" src="../../js/external/jsencrypt/jsbn/rng.js"></script>
	<script type="text/javascript" src="../../js/external/jsencrypt/jsbn/rsa.js"></script>
	<script type="text/javascript" src="../../js/external/jsencrypt/jsbn/rsa2.js"></script>
	<script type="text/javascript" src="../../js/external/jsencrypt/jsbn/rsa-async.js"></script>
	<script type="text/javascript" src="../../js/external/forge/forge.all.min.js"></script>

	<!---------------------------------------------------------------------->
	<!-- JavaScript :: bootstrap-inspina                                  -->
	<!---------------------------------------------------------------------->
	<script type="text/javascript" src="../../js/external/bootstrap-inspinia/js/plugins/metisMenu/jquery.metisMenu.js"></script>
	<script type="text/javascript" src="../../js/external/bootstrap-inspinia/js/plugins/slimscroll/jquery.slimscroll.min.js"></script>
	<script type="text/javascript" src="../../js/external/bootstrap-inspinia/js/plugins/peity/jquery.peity.min.js"></script>
	<script type="text/javascript" src="../../js/external/bootstrap-inspinia/js/plugins/pace/pace.min.js"></script>
	<script type="text/javascript" src="../../js/external/bootstrap-inspinia/js/plugins/iCheck/icheck.min.js"></script>
	<script type="text/javascript" src="../../js/external/bootstrap-inspinia/js/plugins/gritter/jquery.gritter.min.js"></script>
	<script type="text/javascript" src="../../js/external/bootstrap-inspinia/js/plugins/chartJs/Chart.min.js"></script>
	<script type="text/javascript" src="../../js/external/bootstrap-inspinia/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="../../js/external/bootstrap-inspinia/js/inspinia.js"></script>

    <title>Integration & Interface Portal</title>
</head>

<body class="gray-bg">
    <div class="middle-box text-center loginscreen animated fadeInDown">
        <div>
            <div>
                <h1 class="logo-name">IIP</h1>
            </div>
            <div>
            <h3>SSO Login</h3>
            <p>SSO 는 사이트에서 In-house 로 개발 됩니다</p>
            <p>본 페이지는 SSO 테스트 페이지 입니다</p>
            <br>
            <br>
            <form name="ssoForm" action="javascript:$('#btnLogin').click();" method="post">
	            <div class="form-group-original">
	                <input id="ticket" name="ticket" class="form-control enterKeySearch" style="ime-mode:inactive;" placeholder="SSO-ID" required="" value="">
	            </div>
            </form>
            <button id="btnLogin" type="submit" class="btn btn-primary block full-width m-b">Login</button>
            <p class="m-t"> <small>Copyright &copy; 2015</small> </p>
            </div>
        </div>
    </div>
	<div id="waitingLoad"  style='display:none' align="center">
	  <img id="waitingLoad-image" align="middle" src="../../img/loading.gif" alt="Loading..." />
	</div>
</body>
<script>
$(document).ready(function() {
	screenName = "sso.jsp";

	$('#btnLogin').click(function(e) {
		try {
			$.fn_login();
		} catch( e ) {
			mint.common.errorLog(e, {"screenName" : screenName, "fnName" : "$.btnLogin.click"});
		}
	});

	$(".enterKeySearch").keydown(function(e) {
		try {
			if(event.keyCode == 13) {
				$.fn_login();
	     	}
		} catch( e ) {
			mint.common.errorLog(e, {"screenName" : screenName, "fnName" : "$.enterKeySearch.keydown"});
		}
	});

	$.fn_login = function() {
		try {
			var userId = $('#ticket');
			if( mint.common.isFieldEmpty( userId, true, "SSO-ID", userId ) ) {
				return;
			}

			ssoForm.action='./ssoLogin.jsp' + location.search;
			ssoForm.submit();
		} catch( e ) {
			mint.common.errorLog(e, {"screenName" : screenName, "fnName" : "fn_login"});
		}
	}
	$("#ticket").focus();

});//end document.ready()
</script>

</html>