/*
 * Copyright 2013 ~ 2014 Mocomsys(dhkim, Solution TF), Inc.  All Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 * Please contact Mocomsys, Inc., NURITKUM SQUARE R&D TOWER, 11F DMC 1605, 
 * Sangam-Dong, Mapo-Gu, Seoul, 121-795 Korea or visit mocomsys.com 
 * if you need additional information or have any questions.
*/
package pep.per.mint.database.service.tu;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import pep.per.mint.common.data.basic.*;
import pep.per.mint.common.data.basic.tutorial.Flower;
import pep.per.mint.common.util.Util;

/**
 * @author Solution TF
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
		//"file:./src/test/resources/com/mocomsys/mint/database/config/database-context-test.xml"})
		"classpath:/config/database-context.xml","classpath:/config/inhouse-context.xml"})
//"file:./src/test/resources/config/database-context-test.xml"})
public class TutorialServiceTest {

	
	
	private static final Logger logger = LoggerFactory.getLogger(TutorialServiceTest.class);
	
	@Autowired
	TutorialService tutorialService;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}
	
	
	@Test
	public void testGetFlowerList(){
		try {
			Map params = new HashMap();
			List<Flower> res = tutorialService.getFlowerList(params);
			if(res != null){
				logger.debug(Util.join("testGetFlowerList:",Util.toJSONString(res)));
			}
			
		} catch (Exception e) {
			if(logger != null)
				logger.error("testGetFlowerList",e);
			fail("Fail testGetFlowerList");
		}
	}
	
	
	
}
