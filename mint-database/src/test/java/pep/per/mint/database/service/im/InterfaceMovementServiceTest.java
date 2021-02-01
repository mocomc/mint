package pep.per.mint.database.service.im;

import static org.junit.Assert.*;

import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import pep.per.mint.common.util.Util;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={ "classpath:/config/database-context.xml"})
public class InterfaceMovementServiceTest {

	Logger logger = LoggerFactory.getLogger(InterfaceMovementServiceTest.class);

	@Autowired
	InterfaceMovementService service;

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}
	@Test
	public void testExportInterfaces() throws Exception {
		try{
			int countPerPage = 1000;

			String exportPath = "/Users/whoana/DEV/workspace3/mint/mint-database/export";

			String zipPath = "/Users/whoana/DEV/workspace3/mint/mint-database";
			String zipFileName = Util.join("export-", Util.getFormatedDate() ,".zip");

			Map result = service.exportInterfaces(countPerPage, exportPath,"Y", zipPath, zipFileName);

			logger.debug(Util.join("result:", Util.toJSONString(result)));

		}catch(Exception e){
			logger.error("",e);
		}
	}



}
