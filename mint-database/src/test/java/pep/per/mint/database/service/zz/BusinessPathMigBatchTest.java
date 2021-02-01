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
package pep.per.mint.database.service.zz;

import static org.junit.Assert.*;

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

import pep.per.mint.common.util.Util;

/**
 * @author Solution TF
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
		//"file:./src/test/resources/com/mocomsys/mint/database/config/database-context-test.xml"})
		"classpath:/config/database-context.xml"})
		//"file:./src/test/resources/config/database-context-test.xml"})
public class BusinessPathMigBatchTest {

	Logger logger = LoggerFactory.getLogger(BusinessPathMigBatchTest.class);
	
	@Autowired
	BusinessPathMigBatch businessPathMigBatch;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * Test method for {@link pep.per.mint.database.service.zz.BusinessPathMigBatch#mig()}.
	 */
	@Test
	public void testMig() {
		businessPathMigBatch.mig();
	}
	
	
	@Test
	public void testSelectMap() throws Exception {
		List<Map<String, String>> res = businessPathMigBatch.selectMap();
		for (Map<String, String> map : res) {
			logger.debug(Util.toJSONString(map));
		}
	}
	

}
