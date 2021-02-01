package pep.per.mint.agent.util;

import static org.junit.Assert.*;

import java.util.HashMap;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class NHUtilTest {


	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void testGetMQVersion() {
		System.out.println(NHUtil.getMQVersion());
	}

	@Test
	public void testGetMTEVersion() {
		HashMap params = new HashMap();
		params.put("adtPath", "C:\\iip\\mte\\memra_v3.5.5.17.exe");
		System.out.println(NHUtil.getMTEVersion(params));
	}

	@Test
	public void testGetTmaxVersion() {
		HashMap params = new HashMap();
		params.put("adtPath", "C:\\iip\\mte\\memra_v3.5.5.17.exe");
		System.out.println(NHUtil.getTmaxVersion(params));
	}

}
