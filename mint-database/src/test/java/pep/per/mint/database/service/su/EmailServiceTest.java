package pep.per.mint.database.service.su;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import pep.per.mint.common.data.basic.email.Email;
import pep.per.mint.common.data.basic.email.EmailResourceForTracking;
import pep.per.mint.common.data.basic.email.Recipient;
import pep.per.mint.common.util.Util;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:/config/database-context.xml"})
public class EmailServiceTest {

	private static final Logger logger = LoggerFactory.getLogger(EmailServiceTest.class);

	@Autowired
	EmailService emailService;

	@Test
	public void testGetEmailResourceForTracking(){
		try{
			List<EmailResourceForTracking> resources = emailService.getEmailResourceForTracking();
			logger.debug(Util.join("email resources:", Util.toJSONString(resources)));
		}catch(Exception e){
			logger.error("",e);
		}
	}

	@Test
	public void testGetEmailRecipientsForTracking(){
		try{
			List<Recipient> res = emailService.getEmailRecipientsForTracking();
			logger.debug(Util.join("email recipients:", Util.toJSONString(res)));
		}catch(Exception e){
			logger.error("",e);
		}
	}

	@Test
	public void testLoadEmailsForTracking(){
		try{

			List<EmailResourceForTracking> resources = emailService.getEmailResourceForTracking();
			if(resources != null && resources.size() > 0){
				EmailResourceForTracking resource = resources.get(0);
				Email email = new Email();
				email.setResource(resource);
				email.setContents("<P>이메일 내용입니다.</P>");
				email.setRecipients("whoana@gmail.com");
				email.setSender("whoana@mocomsys.com");
				email.setRefers("whoana@mocomsys.com");
				email.setType(Email.TYPE_TRACKING);
				email.setSubject("이메일제목");
				email.setRegDate(Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI));
				email.setRegId("iip");

				List<Email> emails = new ArrayList<Email>();
				emails.add(email);
				emailService.loadEmailsForTracking(emails);
			}

		}catch(Exception e){
			logger.error("",e);
		}
	}

}
