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

/**
 * <blockquote>
 * List 타입의 입력 인수의 size() 값(배열 크기)을 돌려줍니다. 
 * <p>호출 예) Count.call(["1", "2", "3"])는 숫자 3을 돌려줍니다.
 * </blockquote>
 * @author Solution TF
 *
 */
public class Count implements Function {
 
	
	/**
	 * 
	 */
	public Count() {
		// TODO Auto-generated constructor stub
	}
 

	/* (non-Javadoc)
	 * @see pep.per.mint.common.mapper.Function#call(java.util.List)
	 */
	@Override
	public Serializable call(List<Serializable> params, Map params2) throws Exception {
		if(params == null) throw new Exception("Function:Count:예외:파라메터값이 NULL입니다.");
		return params.size();
	}

 
}
