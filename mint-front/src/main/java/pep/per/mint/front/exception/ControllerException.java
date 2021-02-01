/**
 * Copyright 2013 ~ 2015 Mocomsys's guys(Minhwoa Bak, Sanghoon Lim, Deahun Ham, dhkim, Solution TF), Inc.  All Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 * [주의!]
 * 본 코드가 하자없이 완벽할거라 믿었다간 엄청난 욕을 먹고 그로 인한 스트레스로 병들거나 심하면 살기 싫어질 수도 있으니 
 * 부디 살얼음판을 걷듯이 주의하여 사용하기 바란다. 
 * 사용상 받을지 모를 스트레스 및 기타 피해에 대한 책임은 사용자 본인에게 있음을 명시한다. 부디 행운을 빈다.
 * Please Don't contact Mocomsys, Inc., NURITKUM SQUARE R&D TOWER, 11F DMC 1605, 
 * Sangam-Dong, Mapo-Gu, Seoul, 121-795 Korea or visit mocomsys.com 
 * if you need additional information or have any questions.
 */
package pep.per.mint.front.exception;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter; 
import java.io.StringWriter;

/**
 * @author Solution TF
 *
 */
public class ControllerException extends Throwable {
	
	String errorCd = "2099";
	 
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8993949515304759118L;
	/**
	 * 
	 */
	public ControllerException() {
		super();
	}
	
	public ControllerException(String errorCd, String message){
		super(message);
		this.errorCd = errorCd;
	}
	
	
	public ControllerException(String errorCd,  String message, Throwable cause){
		super(message, cause);
		this.errorCd = errorCd;
	}
	
	/**
	 * @return the errorCd
	 */
	public String getErrorCd() {
		return errorCd;
	}

	/**
	 * @param errorCd the reasonCd to set
	 */
	public void setErrorCd(String errorCd) {
		this.errorCd = errorCd;
	}
	
	public String getStackTraceMsg(){
		PrintWriter pw = null;
		try{
			
			StringWriter sw = new StringWriter();
			sw.toString();
			pw = new PrintWriter(sw);
			printStackTrace(pw);
			pw.flush();
			return sw.toString();
		}finally{
			if(pw != null) pw.close();
		}
	}
	
	
}
