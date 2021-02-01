/**
 * Copyright 2018 Mocomsys Inc.  All Rights Reserved.
 */
package pep.per.mint.fetch.exception;

/**
 * <pre>
 * pep.per.mint.fetch.error
 * FetchError.java
 * </pre>
 * @author whoana
 * @date May 14, 2019
 */
public class FetchException extends Exception{

	public final static String ERROR_SCHEMA 	= "100"; //스키마 패치 에러
	public final static String ERROR_DATA 		= "200"; //데이터 패치 에러
	public final static String ERROR_TSU0301 	= "301"; //공통코드 패치 에러
	public final static String ERROR_TSU0302 	= "302"; //포털환경변수 패치 에러
	public final static String ERROR_ETC 		= "900"; //기타 에러

	String errorCd;
	/**
	 *
	 */
	public FetchException(String errorCd, Exception e) {
		super(e);
		this.errorCd = errorCd;
	}
	public String getErrorCd() {
		return errorCd;
	}
	public void setErrorCd(String errorCd) {
		this.errorCd = errorCd;
	}

}
