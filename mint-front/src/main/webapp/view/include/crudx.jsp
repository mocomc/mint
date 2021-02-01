<%@page import="pep.per.mint.front.env.FrontEnvironments"%>
<%@page import="pep.per.mint.common.data.basic.DataAccessRole"%>
<%@page import="pep.per.mint.common.util.Util"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="pep.per.mint.common.data.basic.UserRole"%>
<%@page import="pep.per.mint.common.data.basic.User"%>
<%@page import="pep.per.mint.common.data.basic.LoginInfo"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%
	//-----------------------------------------------------------------------
	//[임시버전-20200520]
	//- NH농협 보안 취약점 대응 수준에서 보완
	//- App CRUD 권한 체크를 Server Side 에서 처리
	//- FAQ, Notice 만 처리하였으며, 인터페이스등록 화면은 내용 복잡성으로 우선 배제하였음
	//- TODO: IIP 전체 Architecture 관점에서 표준화 구현 필요
	//-----------------------------------------------------------------------
	response.setContentType("text/javascript; charset=utf-8");
	String appId = request.getParameter("appId");

	HttpSession sessions = request.getSession();
	Object obj = sessions.getAttribute("user");
	User user = null;
	if(obj != null )
		user = (User) obj;

	if( user != null ) {
		UserRole userRole = user.getRole();
		if(userRole != null) {
			//-----------------------------------------------------------------------
			// 화면 작업 완료후, 버튼 처리 부분 여기다가 옮길것
			//-----------------------------------------------------------------------
%>

<%
			//-----------------------------------------------------------------------
			// [화면별 Copy&Paste 로직 시작]
			//-----------------------------------------------------------------------
			if(appId.equals("SU-02-01-001")) {
				if( userRole.getIsInterfaceAdmin().equals("Y") ) {
%>
					<div style="float: right; padding: 0px 20px 0px 0px;">
				        <a class="btn btn-w-m btn-default btn-outline btn-search"><i class="fa fa-search"></i> <lb class="lb-10"></lb> </a>
						<a class="btn btn-w-m btn-primary btn-create"><i class="fa fa-check"></i> <lb class="lb-421"></lb> </a>
					</div>
					<script>
						//-----------------------------------------------------------------------
						// Description :: btn-search event
						//-----------------------------------------------------------------------
						$('.btn-search').on('click', function() {
							try {
								$.main.fn_searchNotice();
							} catch(e) {
								mint.common.errorLog(e, {"screenName" : screenName, "fnName" : "btn-search.event.click"});
							}
						});

						//-----------------------------------------------------------------------
						// Description :: btn-create event
						//-----------------------------------------------------------------------
						$('.btn-create').on('click', function() {
							try {
								$.main.noticeProcMode = "CREATE";
								mint.common.searchPopup('../sub-su/SU-02-01-002.html','SU-02-01-002');
							} catch(e) {
								mint.common.errorLog(e, {"screenName" : screenName, "fnName" : "btn-create.event.click"});
							}
						});
					</script>
<%
				} else {
%>
					<div style="float: right; padding: 0px 20px 0px 0px;">
				        <a class="btn btn-w-m btn-default btn-outline btn-search"><i class="fa fa-search"></i> <lb class="lb-10"></lb> </a>
					</div>
					<script>
						//-----------------------------------------------------------------------
						// Description :: btn-search event
						//-----------------------------------------------------------------------
						$('.btn-search').on('click', function() {
							try {
								$.main.fn_searchNotice();
							} catch(e) {
								mint.common.errorLog(e, {"screenName" : screenName, "fnName" : "btn-search.event.click"});
							}
						});
					</script>
<%
				}//end isInterfaceAdmin Check
			}// end SU-02-01-001(여기까지가 한셋으로 화면추가시 Copy&Paste 로 수정작업 처리)
			//-----------------------------------------------------------------------
			// [화면별 Copy&Paste 로직 끝]
			//-----------------------------------------------------------------------
%>


<%
			//-----------------------------------------------------------------------
			// [화면별 Copy&Paste 로직 시작]
			//-----------------------------------------------------------------------
			if(appId.equals("SU-02-01-003")) {
				if( userRole.getIsInterfaceAdmin().equals("Y") ) {
%>
	            	<button type="button" class="btn btn-danger btn-delete" ><i class="fa fa-check"></i>  <lb class="lb-343"></lb></button>
	                <button type="button" class="btn btn-primary btn-modify" ><i class="fa fa-check"></i>  <lb class="lb-342"></lb></button>
	                <button type="button" class="btn btn-warning btn-outline" data-dismiss="modal"><i class="fa fa-ban"></i>  <lb class="lb-298"></lb></button>

					<script>
						//-----------------------------------------------------------------------
						// Description :: btn-delete event
						//-----------------------------------------------------------------------
						$('.btn-delete').on('click', function() {
							try {
								if (mint.common.confirm('CI00003', null)) {
									var requestObject = {};
									requestObject.objectType = "Notice";
									requestObject.noticeId = $.main.noticeDetailData.noticeId;
									//update
									mint.callService(requestObject, screenName, "REST-D01-SU-02-01", '$.sub.fn_deleteNoticeResult', {errorCall : true, params : {noticeId : $.main.noticeDetailData.noticeId} });
								}
							} catch(e) {
								mint.common.errorLog(e, {"screenName" : screenName, "fnName" : "btn-delete.event.click"});
							}
						});

						//-----------------------------------------------------------------------
						// Description :: btn-create event
						//-----------------------------------------------------------------------
						$('.btn-modify').on('click', function() {
							try {
								$('#SU-02-01-003').modal('hide');
								$.main.noticeProcMode = "UPDATE";
								mint.common.searchPopup('../sub-su/SU-02-01-002.html','SU-02-01-002');
							} catch(e) {
								mint.common.errorLog(e, {"screenName" : screenName, "fnName" : "btn-modify.event.click"});
							}
						});
					</script>
<%
				} else {
%>
	                <button type="button" class="btn btn-warning btn-outline" data-dismiss="modal"><i class="fa fa-ban"></i> <lb class="lb-298"></lb></button>
					<script>
					</script>
<%
				}//end isInterfaceAdmin Check
			}// end SU-02-01-003(여기까지가 한셋으로 화면추가시 Copy&Paste 로 수정작업 처리)
			//-----------------------------------------------------------------------
			// [화면별 Copy&Paste 로직 끝]
			//-----------------------------------------------------------------------
%>


<%
			//-----------------------------------------------------------------------
			// [화면별 Copy&Paste 로직 시작]
			//-----------------------------------------------------------------------
			if(appId.equals("SU-02-02-001")) {
				if( userRole.getIsInterfaceAdmin().equals("Y") ) {
%>
					<div style="float: right; padding: 0px 20px 0px 0px;">
				        <a class="btn btn-w-m btn-default btn-outline btn-search"><i class="fa fa-search"></i> <lb class="lb-10"></lb> </a>
						<a class="btn btn-w-m btn-primary btn-create"><i class="fa fa-check"></i> <lb class="lb-421"></lb> </a>
					</div>
					<script>
						//-----------------------------------------------------------------------
						// Description :: btn-search event
						//-----------------------------------------------------------------------
						$('.btn-search').on('click', function() {
							try {
								$.main.fn_searchFAQ();
							} catch(e) {
								mint.common.errorLog(e, {"screenName" : screenName, "fnName" : "btn-search.event.click"});
							}
						});

						//-----------------------------------------------------------------------
						// Description :: btn-create event
						//-----------------------------------------------------------------------
						$('.btn-create').on('click', function() {
							try {
								$.main.faqProcMode = "CREATE";
								mint.common.searchPopup('../sub-su/SU-02-02-002.html','SU-02-02-002');
							} catch(e) {
								mint.common.errorLog(e, {"screenName" : screenName, "fnName" : "btn-create.event.click"});
							}
						});
					</script>
<%
				} else {
%>
					<div style="float: right; padding: 0px 20px 0px 0px;">
				        <a class="btn btn-w-m btn-default btn-outline btn-search"><i class="fa fa-search"></i> <lb class="lb-10"></lb> </a>
					</div>
					<script>
						//-----------------------------------------------------------------------
						// Description :: btn-search event
						//-----------------------------------------------------------------------
						$('.btn-search').on('click', function() {
							try {
								$.main.fn_searchFAQ();
							} catch(e) {
								mint.common.errorLog(e, {"screenName" : screenName, "fnName" : "btn-search.event.click"});
							}
						});
					</script>
<%
				}//end isInterfaceAdmin Check
			}// end SU-02-02-001(여기까지가 한셋으로 화면추가시 Copy&Paste 로 수정작업 처리)
			//-----------------------------------------------------------------------
			// [화면별 Copy&Paste 로직 끝]
			//-----------------------------------------------------------------------
%>


<%
			//-----------------------------------------------------------------------
			// [화면별 Copy&Paste 로직 시작]
			//-----------------------------------------------------------------------
			if(appId.equals("SU-02-02-003")) {
				if( userRole.getIsInterfaceAdmin().equals("Y") ) {
%>
	            	<button type="button" class="btn btn-danger btn-delete" ><i class="fa fa-check"></i>  <lb class="lb-343"></lb></button>
	                <button type="button" class="btn btn-primary btn-modify" ><i class="fa fa-check"></i>  <lb class="lb-342"></lb></button>
	                <button type="button" class="btn btn-warning btn-outline" data-dismiss="modal"><i class="fa fa-ban"></i>  <lb class="lb-298"></lb></button>

					<script>
						//-----------------------------------------------------------------------
						// Description :: btn-delete event
						//-----------------------------------------------------------------------
						$('.btn-delete').on('click', function() {
							try {
								if (mint.common.confirm('CI00003', null)) {
									var requestObject = {};
									requestObject.objectType = "FAQ";
									requestObject.faqId = $.main.faqDetailData.faqId;
									//update
									mint.callService(requestObject, screenName, "REST-D01-SU-02-02", '$.sub.fn_deleteFAQResult', {errorCall : true, params : {faqId : $.main.faqDetailData.faqId} });
								}
							} catch(e) {
								mint.common.errorLog(e, {"screenName" : screenName, "fnName" : "btn-delete.event.click"});
							}
						});

						//-----------------------------------------------------------------------
						// Description :: btn-create event
						//-----------------------------------------------------------------------
						$('.btn-modify').on('click', function() {
							try {
								$('#SU-02-02-003').modal('hide');
								$.main.faqProcMode = "UPDATE";
								mint.common.searchPopup('../sub-su/SU-02-02-002.html','SU-02-02-002');
							} catch(e) {
								mint.common.errorLog(e, {"screenName" : screenName, "fnName" : "btn-modify.event.click"});
							}
						});
					</script>
<%
				} else {
%>
	                <button type="button" class="btn btn-warning btn-outline" data-dismiss="modal"><i class="fa fa-ban"></i> <lb class="lb-298"></lb></button>
					<script>
					</script>
<%
				}//end isInterfaceAdmin Check
			}// end SU-02-02-003(여기까지가 한셋으로 화면추가시 Copy&Paste 로 수정작업 처리)
			//-----------------------------------------------------------------------
			// [화면별 Copy&Paste 로직 끝]
			//-----------------------------------------------------------------------
%>



<%
		}//end userRole Check
	}//end user Check
%>