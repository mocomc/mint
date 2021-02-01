/**
 * Copyright 2018 Mocomsys Inc.  All Rights Reserved.
 */
package pep.per.mint.batch.job;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pep.per.mint.batch.Variables;

/**
 * <pre>
 * pep.per.mint.batch.job
 * LogTester.java
 * </pre>
 * @author whoana
 * @date Mar 12, 2019
 */
public class LogTester {

	/**
	 *
	 */
	public LogTester() {
		// TODO Auto-generated constructor stub
	}

	Logger log = Variables.log;

	@Test
	public void test() {
		try {
		log.debug("If a object log is null what happen?");
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
