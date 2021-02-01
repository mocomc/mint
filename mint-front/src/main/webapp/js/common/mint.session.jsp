<%--
/*****************************************************************************
 * Program Name : mint.session.jsp
 * Description
 *   - HttpSession 에서 로그인 사용자 정보를 얻어온다.
 *   - 로그인 사용자 정보는 mint_session javascript 객체에 생성하여 관리한다
 *
 *   - Access 방법
 *     mint.session.{함수명};
 *     mint.session.getUserId();
 *
 *   - function list
 *     mint.session.getUserId();         //사용자ID
 *     mint.session.getUserNm();         //사용자명
 *     mint.session.getCellPhone();      //핸드폰
 *     mint.session.getPhone();          //전화번호
 *     mint.session.getEmail();          //이메일
 *     mint.session.getRoleId();         //롤ID
 *     mint.session.getRoleNm();         //롤명
 *     mint.session.isInterfaceAdmin();  //인터페이스어드민 여부 ( Y/N )
 *
 *
 ****************************************************************************/
 --%>


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
	response.setContentType("text/javascript; charset=utf-8");

	HttpSession sessions = request.getSession();
	Object obj = sessions.getAttribute("user");
	User user = null;
	if(obj != null )
		user = (User) obj;

	if( user == null ) {
		// 사용자 정보가 존재하지 않는다.
%>
	mint.common.alert("CW00000");
	window.location = "../main/login.jsp";
<%
	} else {
%>
/**
 * 로그인 사용자 정보 모듈
 */
var mint_session = function() {

};


/**
 * 사용자 ID
 */
mint_session.prototype.getUserId = function() {
	return "<%= user.getUserId() %>";
}

/**
 * 사용자 명
 */
mint_session.prototype.getUserNm = function() {
	return "<%= user.getUserNm() %>";
}

/**
 * 핸드폰
 */
mint_session.prototype.getCellPhone = function() {
	return "<%= user.getCellPhone() %>";
}


/**
 * 전화번호
 */
mint_session.prototype.getPhone = function() {
	return "<%= user.getPhone() %>";
}

/**
 * 이메일
 */
mint_session.prototype.getEmail = function() {
	return "<%= user.getEmail() %>";
}

/**
 * Company Name
 */
mint_session.prototype.getCompanyNm = function() {
	return "<%= user.getCompanyNm() %>";
}

/**
 * Dept Name
 */
mint_session.prototype.getDepartmentNm = function() {
	return "<%= user.getDepartmentNm() %>";
}

/**
 * position Name
 */
mint_session.prototype.getPositionNm = function(){
	return "<%= user.getPositionNm() %>";
}
<%
		UserRole userRole = user.getRole();
		if(userRole != null) {
%>

/**
 * 롤ID
 */
mint_session.prototype.getRoleId = function() {
	return "<%= userRole.getRoleId() %>";
}

/**
 * 롤이름
 */
mint_session.prototype.getRoleNm = function() {
	return "<%= userRole.getRoleNm() %>";
}

/**
 * 어드민여부(Y/N)
 */
mint_session.prototype.isInterfaceAdmin = function() {
	return "<%= userRole.getIsInterfaceAdmin() %>";
}

/**
 * 인터페이스뷰어여부(Y/N)
 */
mint_session.prototype.isInterfaceViewer = function() {
	return "<%= userRole.getIsInterfaceViewer() %>";
}

/**
 * 솔루션담당자여부(Y/N)
 */
mint_session.prototype.isChannelAdmin = function() {
	return "<%= userRole.getIsChannelAdmin() %>";
}

/**
 * 솔루션ID리스트(isChannelAdmin=Y 일 경우 관련 솔루션 목록을 리턴한다)
 */
mint_session.prototype.getChannelIdList = function() {
	var channelIdList = <%= Util.toJSONString( userRole.getChannelIdList() ) %>;
	if( mint.common.isEmpty(channelIdList) ) {
		return [];
	} else {
		return channelIdList;
	}
}
<%
		}
%>
/**
 * DataAccessRole
 */
mint_session.prototype.getDataAccessRoleList = function() {
	try {
		var dataAccessRoleList = [];
		<%
			List<DataAccessRole> list = user.getDataAccessRoleList();
			for(DataAccessRole dataAccessRole : list) {
		%>
		dataAccessRoleList.push('<%= dataAccessRole.getRoleId() %>');
		<%
			}
		%>

		return dataAccessRoleList;
	} catch( e) {
		mint.common.errorLog(e , {"fnName" : "mint.session.getDataAccessRoleList"});
	}
};

/**
 * 이전 로그인 히스토리
 */
mint_session.prototype.getLoginHistory = function() {

	var loginHistory = <%= Util.toJSONString( user.getLoginHistory() ) %>;
	if( mint.common.isEmpty(loginHistory) ) {
		return {};
	} else {
		return loginHistory;
	}
}

/**
 * 로그인 히스토리 사용 여부(true/false)
 */
mint_session.prototype.useHistory = function() {
	return <%= FrontEnvironments.historyYn %>;
}

/**
 * 중복 로그인 히스토리
 */
mint_session.prototype.getDuplicationLoginHistory = function() {
	var duplicationCheckYn = <%= FrontEnvironments.duplicationLoginCheckYn %>;

	if(duplicationCheckYn != null && duplicationCheckYn){

		var duplicationDetect = <%= sessions.getAttribute("duplicationDetect") %>;

		if(duplicationDetect != null && duplicationDetect){

			var duplicationLoginHistory = <%= Util.toJSONString( sessions.getAttribute("duplicationLoginHistory") ) %>;
			if( mint.common.isEmpty(duplicationLoginHistory) ) {
				return {};
			} else {
				return duplicationLoginHistory;
			}
		}
	}

	return null;
}

/**
 * IP 제한 사용 여부(true/false)
 */
mint_session.prototype.useIpCheck = function() {
	return <%= FrontEnvironments.ipCheck %>;
}

/**
 * 패스워드 실패 제한 사용 여부(true/false)
 */
mint_session.prototype.usePasswordFailCheck = function() {
	return <%= FrontEnvironments.passwordFailCheck %>;
}

/**
 * 로그아웃
 */
mint_session.prototype.logOut = function() {
	try {
		var requestObject = {
			objectType : "LoginInfo",
			userId : '<%= user.getUserId() %>'
		};
		mint.callService(requestObject, 'mint-session', 'REST-S02-CO-00-00-003',
			function(jsonData) {
				window.location="../main/login.jsp"
			}, {errorCall : false}
		);
	} catch( e) {
		mint.common.errorLog(e , {"fnName" : "mint.session.logOut"});
	}
};

/**
 * mint 객체에 추가한다
 */
mint.addConstructor(function() {
	try {
	    if (typeof mint.session === "undefined") {
	        mint.session = new mint_session();
	    }
    } catch( e ) {

    }
});
<%
	}
%>