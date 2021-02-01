/*****************************************************************************
 * Program Name : mint.lifecycle.js
 * Description
 *   - 공통모듈
 *   - 공통적으로 사용되는 변수나 함수( Util 성격의 function ) 를 정의한다
 *   - Access 방법
 *     mint.lifecycle.{함수명};
 *     mint.lifecycle.isUsed();
 *
 ****************************************************************************/
var mint_lifecycle = function() {

};

/*****************************************************************************
 * mint_lifecycle variable list
 * (주의) 직접 Access 하지 말고 set/get function 으로 접근 바랍니다
 *****************************************************************************/
/**
 * Enviroment Info
 */
mint_common.prototype.env = mint.common.getCommonCode('environmentalValues');

/*****************************************************************************
 * mint_lifecycle function list
 *****************************************************************************/

/**
 * LifeCycle 사용여부
 */
mint_lifecycle.prototype.isUsed = function() {
	try {
		var env = mint.common.getEnvorinment();
		var isUsed = env['lifecycle.used'];
		if( !mint.common.isEmpty(isUsed) && isUsed[0] == 'Y' ) {
			return true;
		} else {
			return false;
		}
	} catch( e ) {
		mint.common.errorLog(e, {"fnName" : "mint.lifecycle.isUsed"});
	}
};

/**
 * LifeCycle 사용여부
 */
mint_lifecycle.prototype.isSimple = function() {
	try {
		var env = mint.common.getEnvorinment();
		var isSimple = env['lifecycle.simple'];
		if( !mint.common.isEmpty(isSimple) && isSimple[0] == 'Y' ) {
			return true;
		} else {
			return false;
		}
	} catch( e ) {
		mint.common.errorLog(e, {"fnName" : "mint.lifecycle.isSimple"});
	}
};

/**
 * LifeCycle - isDeveloper
 */
mint_lifecycle.prototype.isDeveloper = function( roleId ) {
	try {
		var env = mint.common.getEnvorinment();
		var role = env['lifecycle.role.developer'];

		if( mint.common.isEmpty(roleId) ) {
			roleId = mint.session.getRoleId();
		}

		return mint.common.isEmpty(roleId) || ( role.indexOf(roleId) < 0 ) ? false : true;
	} catch( e ) {
		mint.common.errorLog(e, {"fnName" : "mint.lifecycle.isDeveloper"});
	}
};

/**
 * LifeCycle - isTester
 */
mint_lifecycle.prototype.isTester = function( roleId ) {
	try {
		var env = mint.common.getEnvorinment();
		var role = env['lifecycle.role.tester'];

		if( mint.common.isEmpty(roleId) ) {
			roleId = mint.session.getRoleId();
		}

		return mint.common.isEmpty(roleId) || ( role.indexOf(roleId) < 0 ) ? false : true;
	} catch( e ) {
		mint.common.errorLog(e, {"fnName" : "mint.lifecycle.isTester"});
	}
};

/**
 * LifeCycle - isApplier
 */
mint_lifecycle.prototype.isApplier = function( roleId ) {
	try {
		var env = mint.common.getEnvorinment();
		var role = env['lifecycle.role.applier'];

		if( mint.common.isEmpty(roleId) ) {
			roleId = mint.session.getRoleId();
		}

		return mint.common.isEmpty(roleId) || ( role.indexOf(roleId) < 0 ) ? false : true;
	} catch( e ) {
		mint.common.errorLog(e, {"fnName" : "mint.lifecycle.isApplier"});
	}
};

/**
 * LifeCycle - isApprover
 */
mint_lifecycle.prototype.isApprover = function( roleId ) {
	try {
		var env = mint.common.getEnvorinment();
		var role = env['lifecycle.role.approver'];

		if( mint.common.isEmpty(roleId) ) {
			roleId = mint.session.getRoleId();
		}

		return mint.common.isEmpty(roleId) || ( role.indexOf(roleId) < 0 ) ? false : true;
	} catch( e ) {
		mint.common.errorLog(e, {"fnName" : "mint.lifecycle.isApprover"});
	}
};


/**
 * mint 객체에 lifecycle 추가<p>
 */
mint.addConstructor(function() {
	try {
	    if (typeof mint.lifecycle === "undefined") {
	        mint.lifecycle = new mint_lifecycle();
	    }
	} catch( e ) {
		console.log(e);
	}
});