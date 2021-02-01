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
import pep.per.mint.common.data.basic.SiteHomeInfo;
import pep.per.mint.common.data.basic.SiteInfo;
import pep.per.mint.common.util.Util;


public class SitemInfoWriteTest {
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

		TypeReference<SiteInfo> mapType = new TypeReference<SiteInfo>() {};

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




		ArrayList<SiteHomeInfo> ifList1 = new ArrayList<SiteHomeInfo>();
		SiteHomeInfo appInfo2 = new SiteHomeInfo();
		appInfo2.setSystemCd("PIPICM");
		appInfo2.setBinaryPath("/var/mqm/interface.sh");  // WMQ
		ifList1.add(appInfo2);


		SiteHomeInfo appInfoMte2 = new SiteHomeInfo();
		appInfoMte2.setSystemCd("MDMIDR");
		appInfoMte2.setBinaryPath("/var/mqm/EAI2/interface.sh");  // WMQ
		ifList1.add(appInfoMte2);


		SiteHomeInfo appInfoTmax2 = new SiteHomeInfo();
		appInfoTmax2.setSystemCd("HCHWHC");
		appInfoTmax2.setBinaryPath("/var/mqm/EAI3/interface.sh");  // WMQ
		ifList1.add(appInfoTmax2);



		SiteInfo siteInfo = new SiteInfo();
//		siteInfo.setApplicationInfoList(ifList);
		siteInfo.setSiteHomeInfoList(ifList1);

		mapper.writeValue(new File(APSCK_HOME + File.separatorChar + "config" + File.separatorChar + "SiteInfo1.json"),siteInfo);

		String jsonInString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(siteInfo);
		System.out.println(jsonInString);
		//

	}

}
