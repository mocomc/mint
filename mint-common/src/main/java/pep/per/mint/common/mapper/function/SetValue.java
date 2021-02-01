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
 * @author Solution TF
 *
 */
public class SetValue implements Function {

	/**
	 * 
	 */
	public SetValue() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see pep.per.mint.common.mapper.function.Function#call(java.util.List, java.util.Map)
	 */
	@Override
	public Serializable call(List<Serializable> params, Map params2) throws Exception {
		if(params2 == null || params2.isEmpty()) return null;
		Object value = params2.values().iterator().next();
		return (Serializable)value;
	}

}
