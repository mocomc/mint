package hello;

import static org.junit.Assert.*;

import java.io.InputStream;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pep.per.mint.agent.AgentProperties;

public class IIPAgentTest {
	static AgentProperties properties = new AgentProperties();
	Logger logger =  LoggerFactory.getLogger(IIPAgentTest.class);
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void getHello() throws Exception {
		InputStream is = ClassLoader.getSystemResourceAsStream("config/agent.properties");
		properties.load(is);
		//properties.list(System.out);

		logger.error(properties.toString());


		Properties props = System.getProperties();
        Enumeration<Object> enumm = props.keys();
        while (enumm.hasMoreElements()) {
            String key = (String) enumm.nextElement();
            String value = (String) props.get(key);
            System.out.println("# " + key + " : " + value);
        }

	}
}
