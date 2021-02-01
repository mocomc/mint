package pep.per.mint.agent.service;

import static org.junit.Assert.*;

import java.io.File;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ch.qos.logback.core.net.SyslogOutputStream;
import pep.per.mint.agent.exception.AgentException;

public class FileUploadServiceTest {
	File f  = new File(".");
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testUploadFil() {
		FileUploadService  fus = new FileUploadService();
		try {
			fus.uploadFil("C://work//bill.txt", "TEST");
		} catch (AgentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testGetFileContents() {
		FileUploadService  fus = new FileUploadService();
		try {
			byte[] t = fus.getFileContents("C:/WORK/IIP_V3/mint/mint-front/src/main/resources/config/datasource.properties");
			System.out.println(new String(t));
		} catch (AgentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	@Test
	public void testConfigFile() {
		FileUploadService  fus = new FileUploadService();
		try {

			List<String> intfList = fus.getConfigFile(f.getAbsolutePath() +"\\src\\test\\resources\\config\\testXML.xml", "JAVA");

			for(String ifid : intfList){
				System.out.println(ifid);
			}
			List<String> intfListw = fus.getConfigFile(f.getAbsolutePath() +"\\src\\test\\resources\\config\\testINI.icf", "API");
			for(String ifid : intfListw){
				System.out.println(ifid);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
