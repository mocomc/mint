package pep.per.mint.inhouse.sms.nh;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.pnp.eai.EAIException;
import com.pnp.eai.entity.BFH;
import com.pnp.eai.entity.EAIMessage;
import com.pnp.eai.service.EaiAPI;
import com.pnp.eai.service.EaiAPIImpl;

import pep.per.mint.common.data.basic.sms.Sms;
import pep.per.mint.common.util.Util;
import pep.per.mint.database.service.co.CommonService;
import pep.per.mint.database.service.im.EventNotiService;
import pep.per.mint.database.service.su.SMSService;
import pep.per.mint.inhouse.sms.SendSMSService;


public class NHSMSService implements SendSMSService {

	Logger logger = LoggerFactory.getLogger(NHSMSService.class);


	static final String SU_MON_INTF 		= "0";
	static final String SU_MON_RESOURCE		= "1";
	static final String SU_MON_MI_AGENT 	= "2";
	static final String SU_MON_MI_RUNNER 	= "3";
	static final String SU_MON_PROCESS 		= "4";
	static final String SU_MON_QMGR 		= "5";
	static final String SU_MON_CHANNEL 		= "6";
	static final String SU_MON_QUEUE 		= "7";
	static final String SU_MON_IIPAGENT 	= "8";
	static final String SU_MON_ETC 			= "9";

	static final String SU_MON_RESOURCE_CPU	= "10";
	static final String SU_MON_RESOURCE_MEM	= "11";
	static final String SU_MON_RESOURCE_DISK= "12";

	static final String STATUS_NORMAL 		= "1";
	static final String STATUS_ABNORMAL	= "9";


	@Autowired
	CommonService commonService;

	@Autowired
	EventNotiService eventNotiService;

	@Autowired
	SMSService smsService;

	String sendSmsAddress="";

	String nhSmsUserID ="";

	String nhSmsServiceCd ="";

	String nhSmsJungsanCd ="";

	String nhSmsIntfId ="";

	String nhSmsServiceNm ="UMSP2511";

	HashMap<String, String> eventNotiQmgrMap  = new HashMap();
	HashMap<String, String> eventNotiServerMap  = new HashMap();

	String eventControlType ="";


	public void init() throws Exception{

		Map<String, List<String>> environmentalValues = commonService.getEnvironmentalValues();

		boolean useNhSms = false;
    	{
	    	String useNHSmsKey = "inhouse.sms.nh.use";
	    	List<String> values = environmentalValues.get(useNHSmsKey);
			if(!Util.isEmpty(values)){
				useNhSms = Boolean.parseBoolean((values.get(0)));
			}
    	}

    	if(useNhSms){
			{
			  	String userIdKey = "inhouse.sms.nh.userId";
				List<String> values = environmentalValues.get(userIdKey);
				if (values != null && values.size() > 0) {
					nhSmsUserID = values.get(0);
					logger.debug(Util.join(userIdKey,":",nhSmsUserID));
				}else{
					throw new Exception(Util.join("Can't initialize ", this.getClass(), " because there is no values of ", userIdKey));
				}
			}

			{
				String sendSenderSmsKey = "inhouse.sms.nh.address";
				List<String> values = environmentalValues.get(sendSenderSmsKey);
				if (values != null && values.size() > 0) {
					sendSmsAddress = values.get(0);
					logger.debug(Util.join(sendSenderSmsKey,":",sendSmsAddress));
				}else{
					throw new Exception(Util.join("Can't initialize ", this.getClass(), " because there is no values of ", sendSenderSmsKey));
				}
			}

			{
				String smsIntfIdKey = "inhouse.sms.nh.eai.intfId";
				List<String> values = environmentalValues.get(smsIntfIdKey);
				if (values != null && values.size() > 0) {
					nhSmsIntfId = values.get(0);
					logger.debug(Util.join(smsIntfIdKey,":",nhSmsIntfId));
				}else{
					throw new Exception(Util.join("Can't initialize ", this.getClass(), " because there is no values of ", smsIntfIdKey));
				}
			}
			{
				String smsServiceCdKey = "inhouse.sms.nh.serviceCd";
				List<String> values = environmentalValues.get(smsServiceCdKey);
				if (values != null && values.size() > 0) {
					nhSmsServiceCd = values.get(0);
					logger.debug(Util.join(smsServiceCdKey,":",nhSmsServiceCd));
				}else{
					throw new Exception(Util.join("Can't initialize ", this.getClass(), " because there is no values of ", smsServiceCdKey));
				}
			}
			{
				String smsJungsanCdKey = "inhouse.sms.nh.jungsanCd";
				List<String> values = environmentalValues.get(smsJungsanCdKey);
				if (values != null && values.size() > 0) {
					nhSmsJungsanCd = values.get(0);
					logger.debug(Util.join(smsJungsanCdKey,":",nhSmsJungsanCd));
				}else{
					throw new Exception(Util.join("Can't initialize ", this.getClass(), " because there is no values of ", smsJungsanCdKey));
				}
			}
			{
				String smsServiceNmKey = "inhouse.sms.nh.serviceNm";
				List<String> values = environmentalValues.get(smsServiceNmKey);
				if (values != null && values.size() > 0) {
					nhSmsServiceNm = values.get(0);
					logger.debug(Util.join(smsServiceNmKey,":",nhSmsServiceNm));
				}else{
					throw new Exception(Util.join("Can't initialize ", this.getClass(), " because there is no values of ", smsServiceNmKey));
				}
			}
			{
				List<Map> values = eventNotiService.getEventNotiQmgrList();
				if (values != null && values.size() > 0) {
					for(Map tmpMap : values){
						if(tmpMap.get("QMGR_ID") != null){
							eventNotiQmgrMap.put(tmpMap.get("QMGR_ID")+"", tmpMap.get("SMS_YN")+"");
						}
						if(tmpMap.get("SERVER_ID")  != null){
							eventNotiServerMap.put(tmpMap.get("SERVER_ID")+"", tmpMap.get("SMS_YN")+"");
						}
					}
					for(Entry map : eventNotiQmgrMap.entrySet()){
						logger.debug(map.getKey() +"   " + map.getValue());
					}
					for(Entry map : eventNotiServerMap.entrySet()){
						logger.debug(map.getKey() +"   " + map.getValue());
					}
				}else{
				}
			}

			{
				String smsControlTypeKey = "inhouse.sms.control.type.nh";
				List<String> values = environmentalValues.get(smsControlTypeKey);
				if (values != null && values.size() > 0) {
					eventControlType = values.get(0);
					logger.debug(Util.join(smsControlTypeKey,":",eventControlType));
				}else{
					throw new Exception(Util.join("Can't initialize ", this.getClass(), " because there is no values of ", eventControlType));
				}
			}
    	}
	}



	@Override
	public String buildContents(Map<String, ?> params) throws Exception {

		String status 			= (String)params.get("Status");
		String regTime 			= (String)params.get("RegTime");
		String qmgrNm 			= (String)params.get("QmgrName");
		String objectType 		= (String)params.get("ObjectType");
		String objectName 		= (String)params.get("ObjectName");
		String info1	 		= (String)params.get("Info1");
		String hostname	 		= (String)params.get("HostName");

		StringBuffer sb = new StringBuffer();

		SimpleDateFormat dt = new SimpleDateFormat(Util.DEFAULT_DATE_FORMAT_MI);
		Date date = dt.parse(regTime);
		SimpleDateFormat dt1 = new SimpleDateFormat("MM-dd HH:mm:ss");
		String newRegTime =dt1.format(date);

		sb.append("[").append(newRegTime).append("] ");
		if(SU_MON_INTF.equalsIgnoreCase(objectType)){  	// 인터페이스 공지
			sb.append(objectName).append(" ");
			sb.append("[").append(info1).append("] alert");
		}else if(SU_MON_IIPAGENT.equalsIgnoreCase(objectType)){  // 에이전트
			sb.append(hostname).append(" ");
			if(!(STATUS_ABNORMAL.equalsIgnoreCase(status))){
				sb.append(objectName).append(" IIPAgent normal");
			}else{
				sb.append(objectName).append(" IIPAgent disconnect");
			}
		}else if(SU_MON_PROCESS.equalsIgnoreCase(objectType)){	 // 프로세스
			sb.append(hostname).append(" ");
			if(!(STATUS_ABNORMAL.equalsIgnoreCase(status))){
				sb.append(objectName).append(" normal");
			}else{
				sb.append(objectName).append(" stopped");
			}
		}else if(SU_MON_QMGR.equalsIgnoreCase(objectType)){	 // 큐관리자
			if(!(STATUS_ABNORMAL.equalsIgnoreCase(status))){
				sb.append(objectName).append(" Qmgr normal");
			}else{
				sb.append(objectName).append(" Qmgr disconnect");
			}

		}else if(SU_MON_QUEUE.equalsIgnoreCase(objectType)){	 // 큐
			sb.append(qmgrNm).append(" ");
			sb.append(objectName).append(" ");
			if(!(STATUS_ABNORMAL.equalsIgnoreCase(status))){
				sb.append("[").append(info1).append("] normal");
			}else{
				sb.append("[").append(info1).append("] depthOver");
			}
		}else if(SU_MON_CHANNEL.equalsIgnoreCase(objectType)){	 // 채널
			sb.append(qmgrNm).append(" ");
			if(!(STATUS_ABNORMAL.equalsIgnoreCase(status))){
				sb.append(objectName).append(" normal");
			}else{
				sb.append(objectName).append(" abnormal");
			}
		}else if(SU_MON_RESOURCE.equalsIgnoreCase(objectType)){	 // 리소스
			sb.append(hostname).append(" ");
			sb.append(objectName).append(" ");
			sb.append("[").append(info1).append("]");
			String info2 = (String)params.get("Info2");

			if(!(info2 == null || info2.length()<=0)){
				sb.append("[").append(info2).append("]");
			}
			if(!(STATUS_ABNORMAL.equalsIgnoreCase(status))){
				sb.append(" normal");
			}else{
				sb.append(" over");
			}
		}else{
			logger.warn(Util.join("abnormal ObjectType" , objectType));
			throw new Exception(Util.join("abnormal ObjectType" , objectType));
		}

		return sb.toString();
	}

	@Override
	public String getSubject(Map<String, ?> params) {
		return "NH SMS Header";
	}



	@Override
	public void sendSMS(Map<String, Object> params) throws Exception {
		EaiAPI eaiAPI = null;

		BFH sendBFH = null;
		byte[] sendUserData = null;

		BFH recvBFH = null;
		byte[] recvUserData = null;

		EAIMessage recvEAIMsg = null;

		try {
			// EaiAPI instance 생성
			eaiAPI = new EaiAPIImpl("jvrtsend");

			String sender = (String)params.get("sender");
			String recipients = (String)params.get("recipients");
			String contents = (String)params.get("contents");

			if(sender == null || sender.length()<1){
				sender = sendSmsAddress;
			}

			StringBuffer sb = new StringBuffer();
			sb.append(Util.rightPad(nhSmsServiceCd, 4, " "));  						// 서비스코드  4  882
			sb.append(Util.rightPad(Util.getFormatedDate("HHmmssSSS")+"A", 10," "));  	// key  10
			sb.append(Util.rightPad(nhSmsJungsanCd, 6, " "));  						// 정산사무코드  6		000999
			sb.append(Util.rightPad(nhSmsUserID,13," "));  						// 고객정보  13  1234567890
			sb.append(Util.rightPad(recipients,80," "));  							// 수신매체 고객정보  80
			sb.append(Util.rightPad(sender,80," "));  								// 송신매치 고객정보  80
			sb.append("3");  														// 메시지  타입         1
			sb.append(Util.rightPad("",40," "));  									// 제목 		40
			sb.append(contents);  													// 내용
			logger.debug("SendMsg["+sb.toString()+"]");


//			sendUserData = sb.toString().getBytes("eucKR");
			sendUserData = sb.toString().getBytes();

//			sendBFH = eaiAPI.createBFH( nhSmsIntfId , 0,"MQM_ECHO", null, "W", "T", 1, "R", null, null, null, 1);  // UMCMEART01
//			sendBFH = eaiAPI.createBFH( nhSmsIntfId , 0,"UMSP2511", null, "W", "T", 1, "R", null, null, null, 1);  // UMCMEART01

			sendBFH = eaiAPI.createBFH( nhSmsIntfId , 0,nhSmsServiceNm, null, "W", "T", 1, "R", null, null, null, 1);  // UMCMEART01
			recvEAIMsg = eaiAPI.mqMsgPut(sendBFH, sendUserData);

			// 수신된 BFH
			recvBFH = recvEAIMsg.getBFH();
			if(recvBFH != null){
				logger.debug("### 1 getResulstCode [" +  recvBFH.getResultCode()+ "]");
			}

			logger.debug(recvBFH.toString());

			// 수신된 사용자 데이터
			recvUserData = recvEAIMsg.getUserData();

			// 수신된 사용자 데이터 출력
			logger.debug( "======= Start Of RecvUserData ================== ");
			logger.debug( new String(recvUserData, "euc-kr") );
			logger.debug( "======= End   Of RecvUserData ================== ");
		} catch(EAIException ee) {
			// 예외상황출력
			logger.warn("ErrorCode:" + ee.getErrorCode());
			logger.warn("Message:" + ee.getMessage() , ee);
			throw ee;
		} catch(Exception e){
			logger.warn("Message:" + e.getMessage(), e);
			throw e;
		}

	}


	@Override
	public String getSendSMSAddress() {
		return sendSmsAddress;
	}



	@Override
	public boolean checkSendSMS(Map<String, ?> params) throws Exception {
		// 발송 대상 이면 true  // 발상 제한  false
		//
//		checkMap.put("smsId", sms.getSmsId());
//		checkMap.put("type", sms.getType());

//	    리소스 인겨 우  key3, 큐매니저는 key1 를  잠조한다.
		String qmgrId = (String)params.get("key1");	  //
		String serverId = (String)params.get("key2");  // 서버ID
		String type = (String)params.get("type");

		// ControlType 체크
		//NH Event Type Control checkValue P:Process/M:QManager/Q:Queue/C:Channel/I:Interface

		if(Sms.TYPE_TRACKING.equalsIgnoreCase(type)){
			if(eventControlType.indexOf("I")>=0){
				return false;
			}
		}else if(Sms.TYPE_QMGR.equalsIgnoreCase(type)){
			if(eventControlType.indexOf("M")>=0){
				return false;
			}
		}else if(Sms.TYPE_CHANNEL.equalsIgnoreCase(type)){
			if(eventControlType.indexOf("C")>=0){
				return false;
			}
		}else if(Sms.TYPE_QUEUE.equalsIgnoreCase(type)){
			if(eventControlType.indexOf("Q")>=0){
				return false;
			}
		}else if(Sms.TYPE_PROCESS.equalsIgnoreCase(type)){
			if(eventControlType.indexOf("P")>=0){
				return false;
			}
		}


		logger.debug("qmgrId ["+qmgrId+"]  severId=["+serverId+ "]  type=["+type+"]");
		if(Sms.TYPE_TRACKING.equals(type) || Sms.TYPE_GENERAL.equals(type)){
			return true;
		}




		if(Sms.TYPE_CHANNEL.equalsIgnoreCase(type) ||
				Sms.TYPE_QUEUE.equalsIgnoreCase(type)  ||
				Sms.TYPE_CHANNEL.equalsIgnoreCase(type)   ){

			if("N".equalsIgnoreCase(eventNotiQmgrMap.get(qmgrId))){
				return false;
			}else{
				return true;
			}
		}else if(Sms.TYPE_IIP_AGENT.equalsIgnoreCase(type) ||
				Sms.TYPE_MI_AGENT.equalsIgnoreCase(type) ||
				Sms.TYPE_MI_RUNNER.equalsIgnoreCase(type) ||
				Sms.TYPE_PROCESS.equalsIgnoreCase(type) ||
				Sms.TYPE_RESOURCE.equalsIgnoreCase(type) ){
			if("N".equalsIgnoreCase(eventNotiServerMap.get(serverId))){
				return false;
			}else{
				return true;
			}

		}else{
			return true;
		}
	}


	@Override
	public int getFromCheckTime() {
		// TODO Auto-generated method stub
		return -10;
	}
}
