/*
 * Copyright (c) 2013 ~ 2015. Mocomsys's guys(Sanghoon Lim, Deahun Ham, dhkim, Solution TF), Inc.  All Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 * [주의!]
 * 본 코드가 하자없이 완벽할거라 믿었다간 엄청난 욕을 먹고 그로 인한 스트레스로 병들거나 심하면 살기 싫어질 수도 있으니 부디 살얼음판을 걷듯이 주의하여 사용하기 바란다.
 * 사용상 받을지 모를 스트레스 및 기타 피해에 대한 책임은 사용자 본인에게 있음을 명시한다. 부디 행운을 빈다.
 * Please Don't contact Mocomsys, Inc., NURITKUM SQUARE R&D TOWER, 11F DMC 1605,
 * Sangam-Dong, Mapo-Gu, Seoul, 121-795 Korea or visit mocomsys.com if you need additional information or have any questions.
 */

package pep.per.mint.batch.job.op;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import pep.per.mint.common.util.Util;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.fail;


/**
 * @author Solution TF
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
		"classpath:/config/batch-context.xml"})
public class InterfaceLogErrorNHServiceTest {
	private static final Logger logger = LoggerFactory.getLogger(InterfaceLogErrorNHServiceTest.class);

	@Autowired
	InterfaceLogErrorNHService interfaceLogErrorNHService;

	/**
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
	}


	@Test
	public void testService() {

		try {
			Map params = new HashMap();
            Calendar fromCal = new GregorianCalendar();
            fromCal.add(Calendar.MINUTE, -5);
            long fromHour = fromCal.getTimeInMillis();
            params.put("fromDate", Util.getFormatedDate(fromHour, "yyyyMMddHHmm"));

            Calendar toCal = new GregorianCalendar();
            long toHour = toCal.getTimeInMillis();
            params.put("toDate", Util.getFormatedDate(toHour, "yyyyMMddHHmm"));


			interfaceLogErrorNHService.run(params);
			logger.debug(Util.join("testService success"));

		} catch (Exception e) {
			e.printStackTrace();
			if(logger != null)
				logger.error("testService",e);
			fail("Fail testService");
		}
	}


}
