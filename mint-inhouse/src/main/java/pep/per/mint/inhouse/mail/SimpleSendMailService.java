/**
 * Copyright 2018 Mocomsys Inc.  All Rights Reserved.
 */
package pep.per.mint.inhouse.mail;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import pep.per.mint.common.data.basic.email.Email;
import pep.per.mint.common.util.Util;
import pep.per.mint.database.service.co.CommonService;

/**
 * <pre>
 * pep.per.mint.inhouse.mail
 * SimpleSendMailService.java
 * </pre>
 * @author whoana
 * @date Jan 9, 2019
 */
public class SimpleSendMailService implements SendMailService {



	Logger logger = LoggerFactory.getLogger(SimpleSendMailService.class);




	static final String STATUS_NORMAL 		= "1";
	static final String STATUS_ABNORMAL	    = "9";


	/*
	 * 한국 NSK에서 온 답변 입니다.
	 *
	 * 4. E-mail전송 관련 API 제공 및 환경 구성 정보 제공(NSK)
	 *	단순 JAVA mail api를 사용해서 SMTP 설정으로 메일 발송하고 있다고 합니다.
	 *	요청하신 SMTP설정 정보입니다.
	 *	private String SMTP_ip = "172.30.45.11";
	 *	private String SMTP_id = "N696513";
	 *	private String SMTP_pwd = "uAAvdjVa";
	 *
	 * 메일 프로토콜 관련 일반적인 정보
	 * Incoming server (IMAP): 993 port for SSL, 143 for TLS. Incoming server (POP3): 995 port for SSL, 110 for TLS.
	 * Outgoing server (SMTP): 465 port for SSL, 25/587 port for TLS.   --> gmail은 25, 587 모두 된다.
	 * Incoming server/outgoing server name: the name of the server your hosting account is located on.
	 *
	 */
	static String smtpHost = "smtp.gmail.com";
	static String smtpPort = "587";
	//static String smtpPort = "25";
	static String smtpUser = "mocomsysall";
	static String smtpPwd = "don'tworrybehappy19";
	static String from = "mocomsysall@gmail.com";
	static Properties props = new Properties();


	final static String CONTENT_TYPE_TEXT_PLAIN = "";
	final static String CONTENT_TYPE_TEXT_HTML = "text/html; charset=utf-8";


	static String templatePath 	= "/template/email.alarm.template.html";


	@Autowired
	CommonService commonService;

	public void init() throws Exception{
		smtpHost = commonService.getEnvironmentalValue("alarm","mail.smtp.host", smtpHost);
		smtpPort = commonService.getEnvironmentalValue("alarm","mail.smtp.port", smtpPort);
		smtpUser = commonService.getEnvironmentalValue("alarm","mail.smtp.user", smtpUser);
		smtpPwd  = commonService.getEnvironmentalValue("alarm","mail.smtp.pwd", smtpPwd);
		from     = commonService.getEnvironmentalValue("alarm","mail.from.address", from);
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", smtpHost);
		props.put("mail.smtp.port", smtpPort);
	}

	/* (non-Javadoc)
	 * @see pep.per.mint.inhouse.mail.SendMailService#buildContents(java.util.Map)
	 */
	@Override
	public String buildContents(Map<String, ?> params) throws Exception {
		URL template = null;
		InputStream is = null;
		String contents = "";
		try{

			String status 			= (String)params.get("Status");
			String regTime 			= (String)params.get("RegTime");
			String qmgrNm 			= (String)params.get("QmgrName");
			String objectType 		= (String)params.get("ObjectType");
			String objectName 		= (String)params.get("ObjectName");
			String info1	 		= (String)params.get("Info1");
			String info2	 		= (String)params.get("Info2");
			String hostName	 		= (String)params.get("HostName");


			template = getClass().getResource(templatePath);


			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			is = template.openStream();
			while(true){
				byte[] b = new byte[1024];
				int res = is.read(b);
				if(res == -1) break;
				baos.write(b);
			}
			contents = new String(baos.toByteArray());

			/* replace template var
			    ${title}
			    ${alertDate}
				${hostName}
				${objectTypeName}
				${objectName}
				${statusName}
				${info1}
			*/
			String title = "";
			String objectTypeName = "";



			if(Email.TYPE_PROCESS.equalsIgnoreCase(objectType)) {
				title = "프로세스 알람";
				objectTypeName = "Process 명";
			}else if(Email.TYPE_CPU.equalsIgnoreCase(objectType)) {
				title = "CPU 알람";
				objectTypeName = "CPU";
				info1 = Util.join(info1,"(측정값/입계치)");
			}else if(Email.TYPE_MEM.equalsIgnoreCase(objectType)) {
				title = "MEMORY 알람";
				objectTypeName = "MEMORY";
				info1 = Util.join(info1,"(측정값/입계치)");
			}else if(Email.TYPE_DISK.equalsIgnoreCase(objectType)) {
				title = "DISK 알람";
				objectTypeName = "DISK";
				info1 = Util.join(info1,"(측정값/입계치), 위치:", info2);

			}else if(Email.TYPE_QMGR.equalsIgnoreCase(objectType)) {
				title = "큐매니저 알람";
				objectTypeName = "큐매니저 명";
			}else if(Email.TYPE_CHANNEL.equalsIgnoreCase(objectType)) {
				title = "채널 알람";
				objectTypeName = "채널 명";
				objectTypeName = Util.join(objectTypeName, "(큐매니저:",qmgrNm,")");
			}else if(Email.TYPE_QUEUE.equalsIgnoreCase(objectType)) {
				title = "큐 알람";
				objectTypeName = "큐 명";
				objectTypeName = Util.join(objectTypeName, "(큐매니저:",qmgrNm,")");
				info1 = Util.join(info1,"(측정값/입계치)");
			}else {
				throw new Exception(Util.join("Unsupported Alram Type:", objectType));
			}

			String statusName = "";
			if(STATUS_ABNORMAL.equalsIgnoreCase(status)) {
				statusName = "비정상";
			}else {
				statusName = "정상";
			}

			SimpleDateFormat dt = new SimpleDateFormat(Util.DEFAULT_DATE_FORMAT_MI);
			Date date = dt.parse(regTime);
			String alertDate =new SimpleDateFormat("MM-dd HH:mm:ss").format(date);

			contents = contents.replaceAll(Pattern.quote("${title}"), Util.isEmpty(title) ? "&nbsp;" : Matcher.quoteReplacement(title));
			contents = contents.replaceAll(Pattern.quote("${alertDate}"), Util.isEmpty(alertDate) ? "&nbsp;" : Matcher.quoteReplacement(alertDate));
			contents = contents.replaceAll(Pattern.quote("${hostName}"), Util.isEmpty(hostName) ? "&nbsp;" : Matcher.quoteReplacement(hostName));
			contents = contents.replaceAll(Pattern.quote("${objectTypeName}"), Util.isEmpty(objectTypeName) ? "&nbsp;" : Matcher.quoteReplacement(objectTypeName));
			contents = contents.replaceAll(Pattern.quote("${objectName}"), Util.isEmpty(objectName) ? "&nbsp;" : Matcher.quoteReplacement(objectName));
			contents = contents.replaceAll(Pattern.quote("${statusName}"), Util.isEmpty(statusName) ? "&nbsp;" : Matcher.quoteReplacement(statusName));
			contents = contents.replaceAll(Pattern.quote("${info1}"), Util.isEmpty(info1) ? "&nbsp;" : Matcher.quoteReplacement(info1));

			return contents;

		}finally{
			if(is != null) is.close();
		}
	}

	/* (non-Javadoc)
	 * @see pep.per.mint.inhouse.mail.SendMailService#sendMail(java.util.Map)
	 */
	@Override
	public void sendMail(Map<String, ?> params) throws Exception {
		String contents = (String)params.get("contents");
		String subject = (String)params.get("subject");
		String recipients = (String)params.get("recipients");
		send(from, recipients, subject, contents, CONTENT_TYPE_TEXT_HTML);
	}

	/* (non-Javadoc)
	 * @see pep.per.mint.inhouse.mail.SendMailService#getSendMailAddress()
	 */
	@Override
	public String getSendMailAddress() {
		return from;
	}

	/* (non-Javadoc)
	 * @see pep.per.mint.inhouse.mail.SendMailService#getSubject(java.util.Map)
	 */
	@Override
	public String getSubject(Map<String, ?> params) {

		String status 			= (String)params.get("Status");
		//String regTime 			= (String)params.get("RegTime");
		//String qmgrNm 			= (String)params.get("QmgrName");
		String objectType 		= (String)params.get("ObjectType");
		String objectName 		= (String)params.get("ObjectName");
		//String info1	 		= (String)params.get("Info1");
		String hostName	 		= (String)params.get("HostName");


		String subject = "모니터링 알람";
		if(Email.TYPE_PROCESS.equalsIgnoreCase(objectType)) {
			subject = Util.join("프로세스 알람[Process명:",objectName, ", 상태:", STATUS_ABNORMAL.equals(status) ? "비정상" : "정상", "]");
		}else if(Email.TYPE_QMGR.equalsIgnoreCase(objectType)) {
			subject = Util.join("큐매니저 알람[큐매니저 명:",objectName, ", 상태:", STATUS_ABNORMAL.equals(status) ? "비정상" : "정상", "]");
		}else if(Email.TYPE_CHANNEL.equalsIgnoreCase(objectType)) {
			subject = Util.join("채널 알람[채널 명:",objectName, ", 상태:", STATUS_ABNORMAL.equals(status) ? "비정상" : "정상", "]");
		}else if(Email.TYPE_QUEUE.equalsIgnoreCase(objectType)) {
			subject = Util.join("큐 알람[큐 명:",objectName, ", 상태:", STATUS_ABNORMAL.equals(status) ? "비정상" : "정상", "]");
		}else if(Email.TYPE_CPU.equalsIgnoreCase(objectType)) {
			subject = Util.join("CPU 알람[호스트명:",hostName, ", 상태:", STATUS_ABNORMAL.equals(status) ? "비정상" : "정상", "]");
		}else if(Email.TYPE_MEM.equalsIgnoreCase(objectType)) {
			subject = Util.join("MEMORY 알람[호스트명:",hostName, ", 상태:", STATUS_ABNORMAL.equals(status) ? "비정상" : "정상", "]");
		}else if(Email.TYPE_DISK.equalsIgnoreCase(objectType)) {
			subject = Util.join("DISK 알람[호스트명:",hostName, ", 상태:", STATUS_ABNORMAL.equals(status) ? "비정상" : "정상", "]");
		}

		return subject;
	}


	public void send(String from, String to, String subject, String contents, String type) throws AddressException, MessagingException {
		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(smtpUser, smtpPwd);
			}
		});


		Message message = new MimeMessage(session);
		message.setFrom(new InternetAddress(from));
		System.out.println("send mail to:" + to);
		message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
		message.setSubject(subject);
		if(CONTENT_TYPE_TEXT_HTML.equalsIgnoreCase(type)) {
			message.setContent(contents, type);
		}else {
			message.setText(contents);
		}
		Transport.send(message);
		System.out.println("Sent message successfully....");


	}

	/**
	 * 이메일 서버 메일 전송 테스트 코드
	 * @param args
	 */
	public static void main(String args[]) {
		try {

			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.host", smtpHost);
			props.put("mail.smtp.port", smtpPort);

			SimpleSendMailService ssms = new SimpleSendMailService();
			String subject = "자바메일을 통해서 보냅니다.";
			String to = "whoana@naver.com,whoana@daum.net,whoana@mocomsys.com,mocomsysall@gmail.com";// change accordingly
			String contents = "<html><body><h1>가나다라마바사아자차카타파하, 1234567890, Hello, this is sample for to check send " + "email using JavaMailAPI </h1></body></html>";

			ssms.send(from, to, subject, contents, CONTENT_TYPE_TEXT_HTML);

		}catch(Exception e) {
			e.printStackTrace();
		}
	}



}
