package pep.per.mint.inhouse.sms.nh;

import static org.junit.Assert.*;

import java.util.HashMap;
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



//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations={
//		"classpath:/config/inhouse-context.xml",
//		"classpath:/config/database-context.xml"})
public class NHSMSServiceTest {

	Logger logger = LoggerFactory.getLogger(NHSMSServiceTest.class);

//	@Autowired
	NHSMSService nhsms;

	@Before
	public void setUp() throws Exception {
		nhsms = new NHSMSService();
		nhsms.sendSmsAddress ="01111111";
		nhsms.nhSmsIntfId ="UMCMEART01";
		nhsms.nhSmsUserID ="1234567890";
		nhsms.nhSmsServiceCd ="882";
		nhsms.nhSmsJungsanCd ="000999";
		nhsms.sendSmsAddress ="01111111";
//		nhsms.init();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testBuildContents() {
		// 0 인터페이스
		System.out.println(" 인터페이스 공지 ");
		System.out.println("================================================");
		{
			Map params = new HashMap();
			params.put("Status", NHSMSService.STATUS_ABNORMAL);
			params.put("ObjectType", NHSMSService.SU_MON_INTF);
			params.put("RegTime", Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI));
			params.put("QmgrName", "NHEATB001");
			params.put("ObjectName", "UMCMEATR99");
			params.put("Info1", "10/50");
			params.put("HostName", "BILL-T450");

			String smsbody;
			try {
				smsbody = nhsms.buildContents(params);
				System.out.println(smsbody);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("================================================");

		System.out.println(" 에이전트 ");
		System.out.println("================================================");
		{
			Map params = new HashMap();
			params.put("Status", NHSMSService.STATUS_NORMAL);
			params.put("ObjectType", NHSMSService.SU_MON_IIPAGENT);
			params.put("RegTime", Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI));
			params.put("QmgrName", "");
			params.put("ObjectName", "NH_IIP_AGT1");
			params.put("Info1", "");
			params.put("HostName", "BILL-T450");

			String smsbody;
			try {
				smsbody = nhsms.buildContents(params);
				System.out.println(smsbody);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		{
			Map params = new HashMap();
			params.put("Status", NHSMSService.STATUS_ABNORMAL);
			params.put("ObjectType", NHSMSService.SU_MON_IIPAGENT);
			params.put("RegTime", Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI));
			params.put("QmgrName", "");
			params.put("ObjectName", "NH_IIP_AGT1");
			params.put("Info1", "");
			params.put("HostName", "BILL-T450");

			String smsbody;
			try {
				smsbody = nhsms.buildContents(params);
				System.out.println(smsbody);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("================================================");


		System.out.println(" 프로세스 ");
		System.out.println("================================================");
		{
			Map params = new HashMap();
			params.put("Status", NHSMSService.STATUS_NORMAL);
			params.put("ObjectType", NHSMSService.SU_MON_PROCESS);
			params.put("RegTime", Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI));
			params.put("QmgrName", "");
			params.put("ObjectName", "tpAdt");
			params.put("Info1", "");
			params.put("HostName", "BILL-T450");

			String smsbody;
			try {
				smsbody = nhsms.buildContents(params);
				System.out.println(smsbody);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		{
			Map params = new HashMap();
			params.put("Status", NHSMSService.STATUS_ABNORMAL);
			params.put("ObjectType", NHSMSService.SU_MON_PROCESS);
			params.put("RegTime", Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI));
			params.put("QmgrName", "");
			params.put("ObjectName", "tpAdt");
			params.put("Info1", "");
			params.put("HostName", "BILL-T450");

			String smsbody;
			try {
				smsbody = nhsms.buildContents(params);
				System.out.println(smsbody);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("================================================");

		System.out.println(" 큐관리자 ");
		System.out.println("================================================");
		{
			Map params = new HashMap();
			params.put("Status", NHSMSService.STATUS_NORMAL);
			params.put("ObjectType", NHSMSService.SU_MON_QMGR);
			params.put("RegTime", Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI));
			params.put("QmgrName", "EABT1");
			params.put("ObjectName", "EABT1");
			params.put("Info1", "");
			params.put("HostName", "BILL-T450");

			String smsbody;
			try {
				smsbody = nhsms.buildContents(params);
				System.out.println(smsbody);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		{
			Map params = new HashMap();
			params.put("Status", NHSMSService.STATUS_ABNORMAL);
			params.put("ObjectType", NHSMSService.SU_MON_QMGR);
			params.put("RegTime", Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI));
			params.put("QmgrName", "EABT1");
			params.put("ObjectName", "EABT1");
			params.put("Info1", "");
			params.put("HostName", "BILL-T450");

			String smsbody;
			try {
				smsbody = nhsms.buildContents(params);
				System.out.println(smsbody);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("================================================");



		System.out.println(" 큐 ");
		System.out.println("================================================");
		{
			Map params = new HashMap();
			params.put("Status", NHSMSService.STATUS_ABNORMAL);
			params.put("ObjectType", NHSMSService.SU_MON_QUEUE);
			params.put("RegTime", Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI));
			params.put("QmgrName", "EABT1");
			params.put("ObjectName", "RV.UX.01.LQ");
			params.put("Info1", "45699/5000");
			params.put("HostName", "BILL-T450");

			String smsbody;
			try {
				smsbody = nhsms.buildContents(params);
				System.out.println(smsbody);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		{
			Map params = new HashMap();
			params.put("Status", NHSMSService.STATUS_NORMAL);
			params.put("ObjectType", NHSMSService.SU_MON_QUEUE);
			params.put("RegTime", Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI));
			params.put("QmgrName", "EABT1");
			params.put("ObjectName", "RV.UX.01.LQ");
			params.put("Info1", "456/5000");
			params.put("HostName", "BILL-T450");

			String smsbody;
			try {
				smsbody = nhsms.buildContents(params);
				System.out.println(smsbody);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("================================================");




		System.out.println(" 채널 ");
		System.out.println("================================================");
		{
			Map params = new HashMap();
			params.put("Status", NHSMSService.STATUS_NORMAL);
			params.put("ObjectType", NHSMSService.SU_MON_CHANNEL);
			params.put("RegTime", Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI));
			params.put("QmgrName", "EABT1");
			params.put("ObjectName", "TO.EAAP01.CH");
			params.put("Info1", "");
			params.put("HostName", "BILL-T450");

			String smsbody;
			try {
				smsbody = nhsms.buildContents(params);
				System.out.println(smsbody);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		{
			Map params = new HashMap();
			params.put("Status", NHSMSService.STATUS_ABNORMAL);
			params.put("ObjectType", NHSMSService.SU_MON_CHANNEL);
			params.put("RegTime", Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI));
			params.put("QmgrName", "EABT1");
			params.put("ObjectName", "TO.EAAP01.CH");
			params.put("Info1", "");
			params.put("HostName", "BILL-T450");

			String smsbody;
			try {
				smsbody = nhsms.buildContents(params);
				System.out.println(smsbody);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("================================================");



		System.out.println(" 리소스 - CPU");
		System.out.println("================================================");
		{
			Map params = new HashMap();
			params.put("Status", NHSMSService.STATUS_NORMAL);
			params.put("ObjectType", NHSMSService.SU_MON_RESOURCE);
			params.put("RegTime", Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI));
			params.put("QmgrName", "");
			params.put("ObjectName", "CPU");
			params.put("Info1", "91/90");
			params.put("HostName", "BILL-T450");

			String smsbody;
			try {
				smsbody = nhsms.buildContents(params);
				System.out.println(smsbody);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		{
			Map params = new HashMap();
			params.put("Status", NHSMSService.STATUS_ABNORMAL);
			params.put("ObjectType", NHSMSService.SU_MON_RESOURCE);
			params.put("RegTime", Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI));
			params.put("QmgrName", "");
			params.put("ObjectName", "CPU");
			params.put("Info1", "80/90");
			params.put("HostName", "BILL-T450");

			String smsbody;
			try {
				smsbody = nhsms.buildContents(params);
				System.out.println(smsbody);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("================================================");



		System.out.println(" 리소스 - 메모리");
		System.out.println("================================================");
		{
			Map params = new HashMap();
			params.put("Status", NHSMSService.STATUS_ABNORMAL);
			params.put("ObjectType", NHSMSService.SU_MON_RESOURCE);
			params.put("RegTime", Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI));
			params.put("QmgrName", "");
			params.put("ObjectName", "MEM");
			params.put("Info1", "95/80");
			params.put("HostName", "BILL-T450");

			String smsbody;
			try {
				smsbody = nhsms.buildContents(params);
				System.out.println(smsbody);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		{
			Map params = new HashMap();
			params.put("Status", NHSMSService.STATUS_NORMAL);
			params.put("ObjectType", NHSMSService.SU_MON_RESOURCE);
			params.put("RegTime", Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI));
			params.put("QmgrName", "");
			params.put("ObjectName", "MEM");
			params.put("Info1", "60/80");
			params.put("HostName", "BILL-T450");

			String smsbody;
			try {
				smsbody = nhsms.buildContents(params);
				System.out.println(smsbody);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("================================================");


		System.out.println(" 리소스 - 디스크");
		System.out.println("================================================");
		{
			Map params = new HashMap();
			params.put("Status", NHSMSService.STATUS_ABNORMAL);
			params.put("ObjectType", NHSMSService.SU_MON_RESOURCE);
			params.put("RegTime", Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI));
			params.put("QmgrName", "");
			params.put("ObjectName", "DISK");
			params.put("Info1", "95/80");
			params.put("Info2", "C:/");
			params.put("HostName", "BILL-T450");

			String smsbody;
			try {
				smsbody = nhsms.buildContents(params);
				System.out.println(smsbody);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		{
			Map params = new HashMap();
			params.put("Status", NHSMSService.STATUS_NORMAL);
			params.put("ObjectType", NHSMSService.SU_MON_RESOURCE);
			params.put("RegTime", Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI));
			params.put("QmgrName", "");
			params.put("ObjectName", "DISK");
			params.put("Info1", "60/80");
			params.put("Info2", "C:/");
			params.put("HostName", "BILL-T450");

			String smsbody;
			try {
				smsbody = nhsms.buildContents(params);
				System.out.println(smsbody);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("================================================");
	}

	@Test
	public void testGetSubject() {
		Map params = new HashMap();
		params.put("ID", "bill");
		String resTitle = nhsms.getSubject(params);

		System.out.println(resTitle);
	}

	@Test
	public void testSendSMS() {
		StringBuffer sb = new StringBuffer();
		sb.append(Util.rightPad("882", 4, " "));  						// 서비스코드  4  882
		sb.append(Util.rightPad(Util.getFormatedDate("HHmmssSSS")+"E", 10," "));  	// key  10
		sb.append(Util.rightPad("000999", 6, " "));  						// 정산사무코드  6		000999
		sb.append(Util.rightPad("1234567890",13," "));  						// 고객정보  13  1234567890
		sb.append(Util.rightPad("10222222",80," "));  								// 수신매체 고객정보  80
		sb.append(Util.rightPad("01063123728",80," "));  							// 송신매치 고객정보  80
		sb.append("3");  														// 메시지  타입         1
		sb.append(Util.rightPad("",40," "));  									// 제목 		40
		sb.append("된장.");  													// 내용
		logger.debug("SendMsg["+sb.toString()+"]");
	}

	@Test
	public void testGetSendSMSAddress() {
//		assertEquals("", "0220593112", nhsms.getSendSMSAddress());
	}

}
