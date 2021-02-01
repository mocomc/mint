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
<meta http-equiv="X-UA-Compatible" content="IE=10" />
<meta name="Robots" content="All" />
<meta name="Keywords" content="" />
<meta name="Description" content="" />    

<style>
	html { font: 10pt sans-serif; }
	.k-grid { border-top-width: 0; }
	.k-grid, .k-grid-content { height: auto !important; }
	.k-grid-content { overflow: visible !important; }
	.k-grid .k-grid-header th { border-top: 1px solid; }
	.k-grid-toolbar, .k-grid-pager > .k-link { display: none; }
</style>


<!-- ................................................................ -->
<!--                 Cascading Style Sheet                            -->
<!--                                                                  -->
<!--                                                                  -->
<!-- ................................................................ -->


<!-- ................................................................ -->
<!-- Cascading Style Sheet :: common                                  -->
<!-- ................................................................ -->
<link rel="stylesheet" type="text/css" href="../../css/common/common.css">

<!-- ................................................................ -->
<!-- Cascading Style Sheet :: bootstrap-inspinia                      -->
<!-- ................................................................ -->
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
<link rel="stylesheet" type="text/css" href="../../css/external/kendo/css/kendo.rtl.min.css">
<link rel="stylesheet" type="text/css" href="../../css/external/kendo/css/kendo.default.min.css">
<link rel="stylesheet" type="text/css" href="../../css/external/kendo/css/kendo.dataviz.min.css">
<link rel="stylesheet" type="text/css" href="../../css/external/kendo/css/kendo.dataviz.default.min.css">
<link rel="stylesheet" type="text/css" href="../../css/external/kendo/css/kendo.mobile.all.min.css">



<!-- ................................................................ -->
<!--                        Java Script                               -->
<!--                                                                  -->
<!--                                                                  -->
<!-- ................................................................ -->

<!-- ................................................................ -->
<!-- JavaScript :: JQuery                                             -->
<!-- ................................................................ -->
<script type="text/javascript" src="../../js/external/jquery/js/jquery.min.js"></script>
<script type="text/javascript" src="../../js/external/jquery/js/jquery.rolling.js"></script>


<!-- ................................................................ -->
<!-- JavaScript :: bootstrap-inspinia                                 -->
<!-- ................................................................ -->
<script type="text/javascript" src="../../js/external/bootstrap-inspinia/js/plugins/metisMenu/jquery.metisMenu.js"></script>
<script type="text/javascript" src="../../js/external/bootstrap-inspinia/js/plugins/slimscroll/jquery.slimscroll.min.js"></script>
<script type="text/javascript" src="../../js/external/bootstrap-inspinia/js/plugins/peity/jquery.peity.min.js"></script>
<script type="text/javascript" src="../../js/external/bootstrap-inspinia/js/plugins/pace/pace.min.js"></script>
<script type="text/javascript" src="../../js/external/bootstrap-inspinia/js/plugins/iCheck/icheck.min.js"></script>
<script type="text/javascript" src="../../js/external/bootstrap-inspinia/js/plugins/gritter/jquery.gritter.min.js"></script>
<script type="text/javascript" src="../../js/external/bootstrap-inspinia/js/plugins/chartJs/Chart.min.js"></script>
<script type="text/javascript" src="../../js/external/bootstrap-inspinia/js/bootstrap.min.js"></script>
<script type="text/javascript" src="../../js/external/bootstrap-inspinia/js/inspinia.js"></script>

<script type="text/javascript" src="../../js/external/bootstrap-inspinia/js/plugins/flot/jquery.flot.js"></script>
<script type="text/javascript" src="../../js/external/bootstrap-inspinia/js/plugins/flot/jquery.flot.spline.js"></script>
<script type="text/javascript" src="../../js/external/bootstrap-inspinia/js/plugins/flot/jquery.flot.pie.js"></script>
<script type="text/javascript" src="../../js/external/bootstrap-inspinia/js/plugins/sparkline/jquery.sparkline.min.js"></script>
<script type="text/javascript" src="../../js/external/bootstrap-inspinia/js/plugins/sweetalert/sweetalert.min.js"></script>
<script type="text/javascript" src="../../js/external/bootstrap-inspinia/js/plugins/flot/jquery.flot.time.js"></script>
<script type="text/javascript" src="../../js/external/bootstrap-inspinia/js/plugins/morris/raphael-2.1.0.min.js"></script>
<script type="text/javascript" src="../../js/external/bootstrap-inspinia/js/plugins/morris/morris.js"></script>


<!-- ................................................................ -->
<!-- JavaScript :: Kendo                                              -->
<!-- ................................................................ -->
<script type="text/javascript" src="../../js/external/kendo/js/kendo.all.min.js"></script>
<script type="text/javascript" src="../../js/external/kendo/js/kendo.autocomplete.min.js"></script>
<script type="text/javascript" src="../../js/external/kendo/js/jszip.min.js"></script>


    
<title>Integrated Interface Portal-FrontLogViewer</title>
    
</head>

<body>
    <div id="wrapper-mint">
		<br>{프론트로그뷰어]
		<div id="grid"></div>
		<script>
			$(document).ready(function() {


				var FrontLog = kendo.data.Model.define({
					id: "logKey", // the identifier of the model
					fields: {
						"logKey"               : {type: "string"},
						"userId"               : {type: "string"},
						"appId"                : {type: "string"},
						"serviceId"            : {type: "string"},
						"reqDate"              : {type: "string"},
						"resDate"              : {type: "string"},
						"errCd"                : {type: "string"},
						"errMsg"               : {type: "string"},
						"reqLog"               : {type: "string"},
						"resLog"               : {type: "string"},
						"httpRemoteAddr"       : {type: "string"},
						"httpRemotePort"       : {type: "string"},
						"httpLocalAddr"        : {type: "string"},
						"httpLocalPort"        : {type: "string"},
						"httpServerName"       : {type: "string"},
						"httpServerPort"       : {type: "string"},
						"httpServletPath"      : {type: "string"},
						"httpRequestURI"       : {type: "string"},
						"httpMethod"           : {type: "string"},
						"httpContentType"      : {type: "string"},
						"httpCharacterEncoding": {type: "string"},
						"httpContextPath"      : {type: "string"}
					}
				});




				var comMessage = new Object();
					comMessage.objectType = "ComMessage";
					//comMessage.requestObject = {"fromDate":"20150825100000","toDate":"20150825200100"};
					comMessage.requestObject = {"fromMin":"10"};//10전꺼부터조회, 주석처리하면 1분전부터조회됨.
					comMessage.startTime = "20150815100000000";
					comMessage.endTime = null;
					comMessage.errorCd = "0";
					comMessage.errorMsg = "";
					comMessage.userId   = "iip"
					comMessage.appId = "whoana";
					comMessage.checkSession = false;



				//here your code!
				//-----------------------------------
				var myDataSource = new kendo.data.DataSource({
				  transport: {
					read: function(options) {
					  $.ajax({
						async : true,
						cache : false,
						type: 'POST',
						dataType: 'json',
						url: '/mint/en/frontlogs?method=GET&isTest=false',
						contentType : 'application/json; charset=utf-8',
						data: JSON.stringify(comMessage),
						success: function(result) {
						  options.success(result.responseObject);
						}
					  });
					},
					destroy: function (options) {

					}
				  },
				  batch: true,
				  schema: {
					model: FrontLog
				  }
				});

				//myDataSource.fetch(function() {
				//  var frontLogs = myDataSource.data();
				//  myDataSource.remove(frontLogs[0]);
				//  myDataSource.sync();
				//});

				//-----------------------------------
				//end your code



				$("#grid").kendoGrid({
					dataSource: myDataSource,
					sortable: true,
					scrollable: true,
					selectable: "cell",
					pageable: true,
					navigatable: true,
					columns: [
					 { field: "reqDate" ,   title:"요청일시", width:130},
					 { field: "resDate" ,   title:"응답일시", width:130},
					 { field: "userId" ,    title:"사용자", width:80},
					 { field: "appId" ,     title:"프론트AP", width:120},
					 { field: "serviceId" , title:"서비스", width:300},
					 { field: "errCd" ,     title:"에러코드", width:80},
					 { field: "errMsg" ,    title:"에러메시지", width:200},
					 { field: "reqLog" ,    title:"ComMessage", width:200}
				   ]
				});
			});
		</script>
		</div>

    </div>
</body>
</html>
