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
import pep.per.mint.common.data.basic.SiteHomeInfo;
import pep.per.mint.common.data.basic.SiteInfo;
import pep.per.mint.common.data.basic.agent.ResourceUsageLog;
import pep.per.mint.common.msg.handler.MessageHandler;
import pep.per.mint.common.util.Util;


public class SiteInfoReadTest {
	private static String APSCK_HOME = "";

	static {
		if (System.getProperty("APP_HOME") == null) {
			APSCK_HOME = "";
		} else {
			APSCK_HOME = System.getProperty("APP_HOME");
		}
	}

	public static void main(String[] args) throws JsonGenerationException, JsonMappingException, IOException {


		String tmp = "Success[";



		ObjectMapper mapper = new ObjectMapper();

		TypeReference<SiteInfo> mapType = new TypeReference<SiteInfo>() {};


//		ArrayList<ApplicationInfo> ifList = mapper.readValue(new File(APSCK_HOME + File.separatorChar +"config"+ File.separatorChar +"SiteInfo.json"), mapType);

		Class clazz = SiteInfoReadTest.class;
	    InputStream inputStream = clazz.getResourceAsStream("/config/SiteInfo2.json");
	    String data = NHUtil.readFromInputStream(inputStream);

	    SiteInfo siteInfo = mapper.readValue(data, mapType);

		List<SiteHomeInfo> ifList = siteInfo.getSiteHomeInfoList();

		for(SiteHomeInfo appInfo  : ifList){
			System.out.println(appInfo.toString());
		}
	}


}
