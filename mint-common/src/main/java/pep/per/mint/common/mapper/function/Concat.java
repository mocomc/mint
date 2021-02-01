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
 * 임의의 개수의 입력 인수를 문자열로 변환하고 이 문자열들을 연결하여 단일 문자열을 돌려줍니다. 
 * <p>호출 예) Concat.call(["I ", "love ", "you", "."])는 "I love you."를 돌려줍니다.
 * </blockquote>
 * 
 * @author Solution TF
 *
 */
public class Concat implements Function {

	/**
	 * 
	 */
	public Concat() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see pep.per.mint.common.mapper.function.Function#call(java.util.List, java.util.Map)
	 */
	@Override
	public Serializable call(List<Serializable> params, Map params2) throws Exception {
		StringBuffer buffer = new StringBuffer();
		for (Serializable value : params) {
			buffer.append(Util.toString(value));
		}
		return buffer.toString();
	}

}
