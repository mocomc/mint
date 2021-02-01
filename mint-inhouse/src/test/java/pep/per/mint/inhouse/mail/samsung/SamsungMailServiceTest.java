package pep.per.mint.inhouse.mail.samsung;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
		"classpath:/config/inhouse-context.xml",
		"classpath:/config/database-context.xml"})
public class SamsungMailServiceTest {

	Logger logger = LoggerFactory.getLogger(SamsungMailServiceTest.class);

	@Autowired
	SamsungMailService service;

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testSendMail() {

		try {


			Map params = new HashMap();

			String contents = service.buildContents(params);
			logger.debug(Util.join("build msg:", contents));

			params.put("subject", "S-Printing 이메일 연동 건-" + new Date());
			//params.put("contents", "<STRING><H1>추가된 사용자 계정으로 메일전송 테스트해봅니다.</H1></STRING><P>잘 받았는지 아래 주소로 답장 한번 주세요.</P><P><a href='mailto:whoana@gmail.com?Subject=주말 잘 보내세요.' target='_top'>Reply Mail</a></P>");

			params.put("contents", contents);
			List<String> recipients = new ArrayList<String>();
			recipients.add("whoana@gmail.com");
			params.put("recipients", recipients);

			service.sendMail(params);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
