<!DOCTYPE html>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

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
	<!--                 Cascading Style Sheet                            -->
	<!--                                                                  -->
	<!--                                                                  -->
	<!---------------------------------------------------------------------->
	<!-- ................................................................ -->
	<!-- favicon                                                          -->
	<!-- ................................................................ -->
	<link rel="shortcut icon" href="../../img/favicon.ico">

	<!-- ................................................................ -->
	<!-- Cascading Style Sheet :: init                                    -->
	<!-- ................................................................ -->
	<link rel="stylesheet" type="text/css" href="../../css/common/init.css">

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

	<!-- ................................................................ -->
	<!-- Cascading Style Sheet :: kendo                                   -->
	<!-- ................................................................ -->
	<link rel="stylesheet" type="text/css" href="../../css/external/kendo/css/kendo.common.min.css">
	<link rel="stylesheet" type="text/css" href="../../css/external/kendo/css/kendo.default.min.css">

	<!---------------------------------------------------------------------->
	<!-- Cascading Style Sheet :: common                                  -->
	<!---------------------------------------------------------------------->
	<link rel="stylesheet" type="text/css" href="../../css/common/common.css">



	<!---------------------------------------------------------------------->
	<!--                        Java Script                               -->
	<!--                                                                  -->
	<!--                                                                  -->
	<!---------------------------------------------------------------------->

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
	<script type="text/javascript" src="../../js/common/mint.label.jsp"></script>
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
	<div id="page-wrapper" style="margin:0;">
		<div id="site-title" class="text-center animated fadeInDown" style="display:none;">
			<div >
				<h1 class="logo-name">&nbsp;</h1>
				<h1><strong><lb class="lb-3054">IIP</lb></strong></h1>
				<h3><strong><lb class="lb-3053">Integrated Interface Portal</lb></strong></h3>
			</div>
		</div>
		<div id="default-title" class="middle-box text-center loginscreen animated fadeInDown" style="display:none;">
			<div >
				<h1 class="logo-name">IIP</h1>
				<h3><strong><lb class="lb-2">Integrated Interface Portal</lb></strong></h3>
			</div>
		</div>

		<div class="middle-box text-center loginscreen animated fadeInDown">
	 		<div class="form-group-original">
				<input id="id" type="text" class="form-control enterKeySearch" style="ime-mode:inactive;" placeholder="UserId" required="" value="">
			</div>
			<div class="form-group-original">
	 			<input id="password" type="password" class="form-control enterKeySearch" style="ime-mode:inactive;" placeholder="Password" required="" value="">
	 		</div>

			<button id="btnLogin" type="submit" class="btn btn-primary block full-width m-b">Login</button>
			<p class="m-t"> <small>Copyright &copy; 2016</small> </p>
		</div>
    </div>

	<div id="waitingLoad"  style='display:none' align="center">
	  <img id="waitingLoad-image" align="middle" src="../../img/loading.gif" alt="Loading..." />
	</div>
</body>
<script>
$(document).ready(function() {
	//=======================================================================
	//(1) function 및 variable scope 설정 :: (메인은 main, 팝업 및 서브는 sub)
	//=======================================================================
	screenName = "login";
	$.extend({
		main : {
			//=======================================================================
			// (2) Screen 에서 사용할 variable 정의
			//=======================================================================

			//=======================================================================
			// (3) Screen 에서 사용할 function 정의
			//=======================================================================

			//-----------------------------------------------------------------------
			// Description :: 화면 초기화
			//-----------------------------------------------------------------------
			fn_init : function() {
				try {
					$.main.init_Title();
					$("#id").focus();
				} catch( e ) {
					mint.common.errorLog(e, {"screenName" : screenName, "fnName" : "$.main.fn_init"});
				}
			},//end fn_init()

			//-----------------------------------------------------------------------
			// Description :: 화면 초기화 콜백
			//-----------------------------------------------------------------------
			fn_initCallback : function() {
				try {
					//TODO
				} catch( e ) {
					mint.common.errorLog(e, {"screenName" : screenName, "fnName" : "$.main.fn_initCallback"});
				}
			},//end fn_initCallback()

			//-----------------------------------------------------------------------
			// Description :: init_Title
			//-----------------------------------------------------------------------
			init_Title : function() {
				try {
					if( !mint.common.isEmpty( mint.label.getLabel('lb-3054') ) ) {
						$('#site-title').show();
					} else {
						$('#default-title').show();
					}
				} catch( e ) {
					mint.common.errorLog(e, {"screenName" : screenName, "fnName" : "$.main.init_Title"});
				}
			},//end fn_login()
			//-----------------------------------------------------------------------
			// Description :: Login
			//-----------------------------------------------------------------------
			fn_login : function(loginInfo, callback) {
				try {

					var userId   = $('#id');
					var password = $('#password');
					//-----------------------------------------------------------------------
					// field check
					//-----------------------------------------------------------------------
					{
						if( mint.common.isFieldEmpty( userId, true, "UserId", userId ) ) {
							return;
						}
						if( mint.common.isFieldEmpty( password, true, "Password", password ) ) {
							return;
						}
					}

					var requestObject = {
						objectType : "LoginInfo",
						userId : userId.val(),
						password : password.val()
					};
/*
					mint.callService(requestObject, "CO-01-00-016", 'REST-R02-CO-04-03',
						function(jsonData) {
							if ( !mint.common.isEmpty(jsonData) && jsonData.passwordChangeRequired === true ) {
								mint.common.setScreenParam('userId', userId.val());
								mint.common.searchPopup("../sub-co/CO-01-00-016.html","CO-01-00-016");
							} else {
								mint.callService(requestObject, 'CO-00-00-003', 'REST-S01-CO-00-00-003',
										function(jsonData) {
											var fn_callback = mint.common.fn_callback('$.main.fn_loginCallback');
											if( fn_callback ) {
												fn_callback();
											}
										}, {errorCall : false}
								);
							}
						},
						{ errorCall : false, params : { userId : $('#id').val() } }
					);
*/

					mint.callService(requestObject, 'CO-00-00-003', 'REST-S01-CO-00-00-003',
						function(jsonData) {
							if( !mint.common.isEmpty(jsonData) && jsonData.passwordChangeRequired === true ) {
								mint.common.setScreenParam('userId', userId.val());
								mint.common.searchPopup("../sub-co/CO-01-00-016.html","CO-01-00-016");
							} else {
								var fn_callback = mint.common.fn_callback('$.main.fn_loginCallback');
								if( fn_callback ) {
									fn_callback();
								}
							}
						}, {errorCall : false}
					);
				} catch( e ) {
					mint.common.errorLog(e, {"screenName" : screenName, "fnName" : "$.main.fn_login"});
				}
			},//end fn_login()
			//-----------------------------------------------------------------------
			// Description :: Login 콜백
			//   - 로그인이 완료되면 index page 로 이동한다
			//-----------------------------------------------------------------------
			fn_loginCallback : function() {
				try {
					window.location='../main/index.jsp' + location.search;
				} catch( e ) {
					mint.common.errorLog(e, {"screenName" : screenName, "fnName" : "$.main.fn_loginCallback"});
				}
			},//end fn_loginCallback()

			//-----------------------------------------------------------------------
			// Description :: 엔터키 이벤트
			//-----------------------------------------------------------------------
			fn_onKeyDown : function() {
				try {
					if(event.keyCode == 13) {
						$.main.fn_login();
			     	}
				} catch( e ) {
					mint.common.errorLog(e, {"screenName" : screenName, "fnName" : "$.main.fn_onKeyDown"});
				}
			}//end fn_onKeyDown()

		}// end main
	});// end extends

	//=======================================================================
	// (4) 이벤트 핸들러 정의
	//=======================================================================
	//-----------------------------------------------------------------------
	// Description :: EnterKey Event 설정
	//-----------------------------------------------------------------------
	$(".enterKeySearch").keydown($.main.fn_onKeyDown);
	//-----------------------------------------------------------------------
	// Description :: Login 버튼에 Click 이벤트 설정
	//-----------------------------------------------------------------------
	$('#btnLogin').click(function(e) {
		try {
			$.main.fn_login();
		} catch( e ) {
			mint.common.errorLog(e, {"screenName" : screenName, "fnName" : "btnLogin.click"});
		}
	});

	//=======================================================================
	// (5) 기타 Function 정의
	//=======================================================================

	//=======================================================================
	// (6) 화면 로딩시 실행할 항목 기술
	//=======================================================================
	$.main.fn_init();
	mint.label.attachLabel(null);
});//end document.ready()


</script>

</html>
