/*
 * Copyright 2013 ~ 2014 Mocomsys(dhkim, Solution TF), Inc.  All Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 * Please contact Mocomsys, Inc., NURITKUM SQUARE R&D TOWER, 11F DMC 1605, 
 * Sangam-Dong, Mapo-Gu, Seoul, 121-795 Korea or visit mocomsys.com 
 * if you need additional information or have any questions.
*/
package pep.per.mint.common.message;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pep.per.mint.common.data.FieldPath;
import pep.per.mint.common.util.Util;

/**
 * @author Solution TF
 *
 */
public class PathUtilTest {
	
	static Logger logger = LoggerFactory.getLogger(PathUtilTest.class);
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * Test method for {@link pep.per.mint.common.message.PathUtil#getPath(java.lang.String)}.
	 */
	@Test
	public void testGetPath() {
		String pathValue = "test[4]";
		Path path = null;
		try {
			path = PathUtil.getPath(pathValue);
			logger.debug(Util.join("path:", Util.toJSONString(path)));
			
			
			logger.debug(Util.join("path:", PathUtil.getLastPathName("a.b.c[1].d[9]..")));
			
			logger.debug(Util.join("path:", PathUtil.getPathString(new FieldPath("a[5].b.c.d[0]"))));
			
		} catch (Exception e) {
			logger.error("",e);
		}
	}
	
	
	@Test
	public void testGetParentFieldPath(){
		FieldPath fieldPath = new FieldPath("a");
		try {
			FieldPath parentPath = PathUtil.getParentFieldPath(fieldPath);
			logger.debug(Util.join("parentPath:", parentPath));
			
			//logger.debug(Util.join("isRoot:", PathUtil.isRoot(parentPath)));//deprecated function
			
		} catch (Exception e) {
			logger.error("",e);
		}
	}

}
