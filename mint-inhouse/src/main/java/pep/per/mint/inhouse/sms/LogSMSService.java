package pep.per.mint.inhouse.sms;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import pep.per.mint.common.util.Util;
import pep.per.mint.database.service.co.CommonService;
import pep.per.mint.inhouse.sms.SendSMSService;


public class LogSMSService implements SendSMSService {

	Logger logger = LoggerFactory.getLogger(LogSMSService.class);

	static final String SU_MON_INTF = "0";
	static final String SU_MON_RESOURCE = "1";
	static final String SU_MON_MI_AGENT = "2";
	static final String SU_MON_MI_RUNNER = "3";
	static final String SU_MON_PROCESS = "4";
	static final String SU_MON_QMGR = "5";
	static final String SU_MON_CHANNEL = "6";
	static final String SU_MON_QUEUE = "7";
	static final String SU_MON_IIPAGENT = "8";
	static final String SU_MON_ETC = "9";
	static final String SU_MON_RESOURCE_CPU = "10";
	static final String SU_MON_RESOURCE_MEM = "11";
	static final String SU_MON_RESOURCE_DISK = "12";

	static final String STATUS_NORMAL = "1";
	static final String STATUS_ABNORMAL = "9";

	int checkTime = -10;
	String senderNumber = "01088889999";

	@Autowired
	CommonService commonService;

	public void init() throws Exception{
		checkTime = commonService.getEnvironmentalIntValue("batch","interface.error.sms.checktime",checkTime);
		senderNumber = commonService.getEnvironmentalValue("push","sms.sender",senderNumber);
	}

	@Override
	public String buildContents(Map<String, ?> params) throws Exception {
		String status = (String)params.get("Status");
		String regTime = (String)params.get("RegTime");
		String qmgrNm = (String)params.get("QmgrName");
		String objectType = (String)params.get("ObjectType");
		String objectName = (String)params.get("ObjectName");
		String info1 = (String)params.get("Info1");
		String hostName = (String)params.get("HostName");

		StringBuffer sb = new StringBuffer();

		SimpleDateFormat dt = new SimpleDateFormat(Util.DEFAULT_DATE_FORMAT_MI);
		Date date = dt.parse(regTime);
		SimpleDateFormat dt1 = new SimpleDateFormat("MM-dd HH:mm:ss");
		String newRegTime = dt1.format(date);

		sb.append("[").append(newRegTime).append("] ");
		if(SU_MON_INTF.equalsIgnoreCase(objectType)){ // 인터페이스
			sb.append(objectName).append(" ");
			sb.append("[").append(info1).append("] alert");
		} else if(SU_MON_IIPAGENT.equalsIgnoreCase(objectType)) { // IIP에이전트
			sb.append(hostName).append(" ");
			if(!(STATUS_ABNORMAL.equalsIgnoreCase(status))) {
				sb.append(objectName).append(" IIPAgent normal");
			} else {
				sb.append(objectName).append(" IIPAgent disconnect");
			}
		} else if(SU_MON_PROCESS.equalsIgnoreCase(objectType)) { // 프로세스
			sb.append(hostName).append(" ");
			if(!(STATUS_ABNORMAL.equalsIgnoreCase(status))) {
				sb.append(objectName).append(" normal");
			} else {
				sb.append(objectName).append(" stopped");
			}
		} else if(SU_MON_QMGR.equalsIgnoreCase(objectType)) { // 큐관리자
			if(!(STATUS_ABNORMAL.equalsIgnoreCase(status))) {
				sb.append(objectName).append(" Qmgr normal");
			} else {
				sb.append(objectName).append(" Qmgr disconnect");
			}
		} else if(SU_MON_QUEUE.equalsIgnoreCase(objectType)) { // 큐
			sb.append(qmgrNm).append(" ");
			sb.append(objectName).append(" ");
			if(!(STATUS_ABNORMAL.equalsIgnoreCase(status))) {
				sb.append("[").append(info1).append("] normal");
			} else {
				sb.append("[").append(info1).append("] depthOber");
			}
		} else if(SU_MON_CHANNEL.equalsIgnoreCase(objectType)) { // 채널
			sb.append(qmgrNm).append(" ");
			if(!(STATUS_ABNORMAL.equalsIgnoreCase(status))) {
				sb.append(objectName).append(" normal");
			} else {
				sb.append(objectName).append(" abnormal");
			}
		} else if(SU_MON_RESOURCE.equalsIgnoreCase(objectType)) { // 리소스(CPU,MEMORY,DISK)
			sb.append(hostName).append(" ");
			sb.append(objectName).append(" ");
			sb.append("[").append(info1).append("]");
			String info2 = (String)params.get("Info2");

			if(!(info2 == null || info2.length() <=0 )) {
				sb.append("[").append(info2).append("]");
			}
			if(!(STATUS_ABNORMAL.equalsIgnoreCase(status))) {
				sb.append(" normal");
			} else {
				sb.append(" over");
			}
		} else {
			logger.warn(Util.join("abnormal ObjectType", objectType));
			throw new Exception(Util.join("abnormal ObjectType", objectType));
		}

		return sb.toString();

	}

	@Override
	public String getSubject(Map<String, ?> params) {
		return "SMS Header";
	}



	@Override
	public void sendSMS(Map<String, Object> params) throws Exception {
		// TODO Auto-generated method stub
		//

		//
	}


	@Override
	public String getSendSMSAddress() {
		return senderNumber;
	}

	@Override
	public int getFromCheckTime() {
		return checkTime;
	}


	@Override
	public boolean checkSendSMS(Map<String, ?> params) throws Exception {
		return true;
	}

}
