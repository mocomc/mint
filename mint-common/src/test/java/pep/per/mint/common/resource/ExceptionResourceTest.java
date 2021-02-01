package pep.per.mint.common.resource;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import pep.per.mint.common.resource.ExceptionResource;

public class ExceptionResourceTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public final void testGetStringArg() {
		String msg = ExceptionResource.getString("PAR001","A",new Integer(100));
		System.out.println(msg);
	}

}
