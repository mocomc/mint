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

<%@ include file="../include/includeFiles.html" %>
<%@ include file="../include/menuPath.html" %>
<title>Integrated Interface Portal</title>

</head>

<body>
    <div id="wrapper-mint">
        <%@ include file="../include/left.html" %>
        <div id="page-wrapper" class="gray-bg">
			<%@ include file="../include/header.html" %>
			<div id="main_contents"></div>
			<div id="main_contents_detail" style="display: none;"></div>
			<div class="hr-space" style="height:30px;"></div>
			<%@ include file="../include/footer.html" %>
    	</div>
    </div>
	<div>
		<a type="button" id="scroll-top-button" class="btn btn-warning btn-circle btn-lg"><i class="fa fa-arrow-up"></i></a>
	</div>
<script>
$(document).ready(function() {
	//=======================================================================
	//(1) function 및 variable scope 설정 :: (메인은 main, 팝업 및 서브는 sub)
	//=======================================================================
	screenName = "index";
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
					mint.init('$.main.fn_initCallback');
				} catch( e ) {
					mint.common.errorLog(e, {"screenName" : screenName, "fnName" : "$.main.fn_init"});
				}
			},//end fn_init()

			//-----------------------------------------------------------------------
			// Description :: 화면 초기화 콜백
			//  - 화면이 로딩되면, main 페이지를 보여준다
			//-----------------------------------------------------------------------
			fn_initCallback : function() {
				try {

					if( mint.common.isEmpty( location.search ) ) {
						mint.goMain();
					} else {
						$.main.fn_approvalLinkView( location.search.substr(1) );
					}

					//-----------------------------------------------------------------------
					// showComponents
					//-----------------------------------------------------------------------
					{
	            		var env = mint.common.getEnvorinment();
						//-----------------------------------------------------------------------
						// 다국어 선택 메뉴 표시 여부
						//-----------------------------------------------------------------------
	            		var langFlag = env['system.lang.multi.used'];
	            		if( !mint.common.isEmpty(langFlag) && langFlag[0] === 'Y' ) {
	            			$('#lang-multi-select-div').show();
	            		} else {
	            			$('#lang-multi-select-div').hide();
	            		}
						//-----------------------------------------------------------------------
						// 패스워드변경버튼 표시여부 TODO: 환경설정 체크 좀더 표준화된 방안 필요할듯(특히 Value)
						//-----------------------------------------------------------------------
	            		var pwdFlag = env['system.security.password.check'];
	            		if( !mint.common.isEmpty(pwdFlag) && pwdFlag[0].toLowerCase() === 'true' ) {
	            			$('.btn-changePswd').show();
	            		} else {
	            			$('.btn-changePswd').hide();
	            		}
					}

					$.left.fn_init(); // 초기 셋팅 이후 메뉴목록 정보 호출한다.

				} catch( e ) {
					mint.common.errorLog(e, {"screenName" : screenName, "fnName" : "$.main.fn_initCallback"});
				}
			},//end fn_initCallback()

			//-----------------------------------------------------------------------
			// Description :: 결재내용 url 에 매칭된 화면으로 이동
			//-----------------------------------------------------------------------
			fn_approvalLinkView : function( queryString ) {
				try {

					var linkKey  = "";
					var linkType = "";
					var params = queryString.split("&");

					linkKey = params[0];
					if( params.length == 2 ) {
						linkType = params[1];
					}

					var ApprovalLinkInfo = {
						objectType : 'Map',
						linkKey : linkKey
					};
					mint.callService( ApprovalLinkInfo, 'index', 'REST-R03-CO-02-00-010',
						function( jsonData ) {
							if( mint.common.isEmpty( jsonData ) ) {
								mint.common.alert('CW00004', null);
								mint.goMain();
							} else {
								var approvalItemId   = jsonData.approvalItemId;
								var approvalItemType = jsonData.approvalItemType;
								//-----------------------------------------------------------------------
								//  approvalItemType=0 :: 인터페이스 요건 상세 페이지 호출
								//  approvalItemType=1 :: 장애 대장 상세 페이지 호출
								//-----------------------------------------------------------------------
								if( approvalItemType == '0' ) {
									mint.common.setScreenParam('linkType', linkType);
									mint.common.setScreenParam('requirementId', approvalItemId);
									mint.move('../sub-an/AN-01-00-003',{CRUDPS:'R'});
								} else if( approvalItemType == '1' ) {
							        mint.common.setScreenParam("updateCheck", true);
							    	mint.common.setScreenParam("problemId", approvalItemId);
							    	mint.common.setScreenParam("channelId", "");
							    	mint.common.setScreenParam("isApproval", false);
							        mint.move('../sub-op/OP-03-02-002');
								} else {
									mint.common.alert('CW00004', null);
									mint.goMain();
								}
							}
						}, { errorCall : true }
					);

				} catch( e ) {
					mint.common.errorLog(e, {"screenName" : screenName, "fnName" : "$.main.fn_approvalLinkView"});
				}
			},//end fn_approvalLinkSearch()
		}// end main
	});// end extends

	//=======================================================================
	// (4) 이벤트 핸들러 정의
	//=======================================================================

	//-----------------------------------------------------------------------
	// Description :: 페이지 상단으로 스크롤 시키는 버튼 핸들링.
	//-----------------------------------------------------------------------
	$(document).scroll( function() {
		try {
			var scrollTop    = $(window).scrollTop();
			var windowHeight = $(window).height();

			$('#scroll-top-button').stop().hide().css('top', windowHeight + scrollTop - 50);

			if( scrollTop > 100 ) {
				$('#scroll-top-button').css('z-index','1100');
				$('#scroll-top-button').fadeIn();
			} else if( scrollTop < 100 ) {
				$('#scroll-top-button').css('z-index','100');
				$('#scroll-top-button').stop().fadeOut();
			}
		} catch( e ) {
			mint.common.errorLog(e, {"screenName" : screenName, "fnName" : "$.main.document.scroll.function()"});
		}
	});

	//-----------------------------------------------------------------------
	// Description :: 페이지 상단으로 스크롤 시키는 버튼 이벤트 처리.
	//-----------------------------------------------------------------------
	$('#scroll-top-button').click(function() {
		$("html, body").animate({scrollTop:0}, 'slow');
	});

	//=======================================================================
	// (5) 기타 Function 정의 ( 모듈 단위로 정의 할것 )
	//=======================================================================

	//=======================================================================
	// (6) 화면 로딩시 실행할 항목 기술
	//=======================================================================
	$.main.fn_init();
	mint.label.attachLabel(null);

});//end document.ready()

</script>
</body>
</html>
