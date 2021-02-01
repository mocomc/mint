/*
 * Copyright 2013 ~ 2014 Mocomsys(dhkim, Solution TF), Inc.  All Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 * Please contact Mocomsys, Inc., NURITKUM SQUARE R&D TOWER, 11F DMC 1605, 
 * Sangam-Dong, Mapo-Gu, Seoul, 121-795 Korea or visit mocomsys.com 
 * if you need additional information or have any questions.
*/
package pep.per.mint.common.mapper.function;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import pep.per.mint.common.util.Util;

/**
 * <blockquote>
 * List 타입의 입력 인수의 첫번째 값을 문자열로 변환하여 trim을 수행한 문자열을 돌려줍니다. 
 * <p>호출 예) trim.call(["this is fantastic program.     "])는 ""this is fantastic program."를 돌려줍니다.
 * </blockquote>
 * @author Solution TF
 *
 */
public class Trim implements Function {

	/**
	 * 
	 */
	public Trim() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see pep.per.mint.common.mapper.function.Function#call(java.util.List, java.util.Map)
	 */
	@Override
	public Serializable call(List<Serializable> params, Map params2) throws Exception {
		if(params == null || params.size() == 0 ) return null;
		return Util.toString(params.get(0)).trim();
	}

}
