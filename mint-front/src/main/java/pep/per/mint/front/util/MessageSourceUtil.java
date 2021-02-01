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
package pep.per.mint.front.util;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import pep.per.mint.common.util.Util;

/**
 * @author Solution TF
 *
 */
public class MessageSourceUtil {

	static Logger logger = LoggerFactory.getLogger(MessageSourceUtil.class);

	final static String ERROR_CD_UNEXPECTED = "2099";
	public static String getMessage(ReloadableResourceBundleMessageSource messageSource, String key, Object[] params, Locale locale){

		try{
			return messageSource.getMessage(key, params, locale);
		}catch(Exception e){
			e.printStackTrace();
			return "";
		}
	}

	public static String getErrorCd(ReloadableResourceBundleMessageSource messageSource, String key, Locale locale){

		try{
			return messageSource.getMessage(key, null, locale);
		}catch(Exception e){
			e.printStackTrace();
			return ERROR_CD_UNEXPECTED;
		}
	}

	public static String getErrorMsg(ReloadableResourceBundleMessageSource messageSource, String key, Object[] params, Locale locale){

		try{
			return messageSource.getMessage(key, params, locale);
		}catch(Exception e){
			e.printStackTrace();
			String errorMsg = "예상치 못한 문제가 발생하여 요청을 처리하지 못했습니다.(서비스:{1}, 에러내용:{2})";
			try{
				if(params != null){
					if(!Util.isEmpty(params[0]) && params.length > 0)  errorMsg.replace("{1}", params[0].toString());
					if(!Util.isEmpty(params[1]) && params.length > 1)  errorMsg.replace("{2}", params[1].toString());
				}
			}catch(Exception ex){
				ex.printStackTrace();
				errorMsg = "예상치 못한 문제가 발생하여 요청을 처리하지 못했습니다.";
			}
			return errorMsg;
		}
	}



}
