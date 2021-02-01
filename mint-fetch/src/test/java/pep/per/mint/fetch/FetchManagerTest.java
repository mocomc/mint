/**
 * Copyright 2018 Mocomsys Inc.  All Rights Reserved.
 */
package pep.per.mint.fetch;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * <pre>
 * pep.per.mint.fetch
 * FetchManagerTest.java
 * </pre>
 * @author whoana
 * @date Apr 29, 2019
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes= FetchConfig.class)
public class FetchManagerTest {

	@Autowired
	FetchManager fetchManager;

	/**
	 * Test method for {@link pep.per.mint.fetch.FetchManager#start()}.
	 */
	@Test
	public void testInitialize() {
		try {
			fetchManager.start();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("fail");
		}
	}

}
