package pep.per.mint.agent.util;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import pep.per.mint.common.data.basic.ApplicationInfo;
import pep.per.mint.common.data.basic.agent.ResourceUsageLog;
import pep.per.mint.common.msg.handler.MessageHandler;
import pep.per.mint.common.util.Util;


public class ApplicationInfoReadTest {
	private static String APSCK_HOME = "";

	static {
		if (System.getProperty("APP_HOME") == null) {
			APSCK_HOME = "";
		} else {
			APSCK_HOME = System.getProperty("APP_HOME");
		}
	}

	public static void main(String[] args) throws JsonGenerationException, JsonMappingException, IOException {


		ObjectMapper mapper = new ObjectMapper();

		TypeReference<List<ApplicationInfo>> mapType = new TypeReference<List<ApplicationInfo>>() {};


//		ArrayList<ApplicationInfo> ifList = mapper.readValue(new File(APSCK_HOME + File.separatorChar +"config"+ File.separatorChar +"SiteInfo.json"), mapType);

		Class clazz = ApplicationInfoReadTest.class;
	    InputStream inputStream = clazz.getResourceAsStream("/config/SiteInfo.json");
	    String data = NHUtil.readFromInputStream(inputStream);

		ArrayList<ApplicationInfo> ifList = mapper.readValue(data, mapType);

		for(ApplicationInfo appInfo  : ifList){
			System.out.println(appInfo.toString());
			appInfo.setRegDate(Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI));
			appInfo.setRegId("iip");
		}
	}


}
