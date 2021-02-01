<%--
/*****************************************************************************
 * Program Name : mint.xy.jsp
 * Description
 *   - mint.xy project
 ****************************************************************************/
 --%>

<%@page import="org.apache.xmlbeans.impl.util.Base64"%>
<%@page import="pep.per.mint.front.security.RSAKeyManager"%>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%
	response.setContentType("text/javascript; charset=utf-8");
	boolean flag = RSAKeyManager.isEncryptEnable();
%>

/**
 * mint.xy module
 */
var mint_xy = function() {

};


/**
 * xy
 */
mint_xy.prototype.getXYK = function() {
	return "<%= RSAKeyManager.getPublicKey() %>";
}

/**
 * xy
 */
mint_xy.prototype.getXYM = function() {
	return "<%= RSAKeyManager.getPublicModule() %>";
}

/**
 * xyEnableCheck
 */
mint_xy.prototype.isEnable = function() {
	return <%= RSAKeyManager.isEncryptEnable() %>;
}

/**
 * encrypt
 */
mint_xy.prototype.encrypt = function(data) {
	try {
		var m = b64tohex(this.getXYM());
		var k = b64tohex(this.getXYK());

		var rsa = forge.pki.setRsaPublicKey(new forge.jsbn.BigInteger(m,16), new forge.jsbn.BigInteger(k,16));
		var encryptData = rsa.encrypt(data, 'RSA-OAEP', { md: forge.md.md5.create(), mgf1: { md: forge.md.sha1.create() } } );

		if( mint.common.isEmpty(encryptData) ) {
			mint.common.alert('RSA Encrypt Fail');
			return '';
		} else {
			return hex2b64(forge.util.bytesToHex(encryptData));
		}
	} catch( e ) {
		mint.common.errorLog(e, {"fnName" : "mint.xy.encrypt"});
	}
/*
	try {
		var rsa = new RSAKey();
		rsa.setPublic( b64tohex(this.getXYM()), b64tohex(this.getXYK()) );
		var encryptData = rsa.encrypt(data);
		if( mint.common.isEmpty(encryptData) ) {
			mint.common.alert('RSA Encrypt Fail');
			return '';
		} else {
			return hex2b64(rsa.encrypt(data));
		}
	} catch( e ) {
		mint.common.errorLog(e, {"fnName" : "mint.xy.encrypt"});
	}
*/
}



/**
 * mint 객체에 추가한다
 */
mint.addConstructor(function() {
	try {
	    if (typeof mint.xy === "undefined") {
	        mint.xy = new mint_xy();
	    }
    } catch( e ) {

    }
});