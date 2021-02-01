package pep.per.mint.agent.util;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import pep.per.mint.common.data.basic.ApplicationInfo;
import pep.per.mint.common.util.Util;


public class ApplicationInfoWriteTest {
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

		ArrayList<ApplicationInfo> ifList = new ArrayList<ApplicationInfo>();
		ApplicationInfo appInfo = new ApplicationInfo();
		appInfo.setHome("");
		appInfo.setType("00");  // WMQ
		ifList.add(appInfo);


		ApplicationInfo appInfoMte1 = new ApplicationInfo();
		appInfoMte1.setHome("c:\\mte\\meoa.exe");
		appInfoMte1.setType("01");  // WMQ
		ifList.add(appInfoMte1);


		ApplicationInfo appInfoTmax1 = new ApplicationInfo();
		appInfoTmax1.setHome("");
		appInfoTmax1.setType("02");  // WMQ
		ifList.add(appInfoTmax1);

		mapper.writeValue(new File(APSCK_HOME + File.separatorChar + "config" + File.separatorChar + "SiteInfo.json"),ifList);

		String jsonInString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(ifList);
		System.out.println(jsonInString);
		//

	}

}
