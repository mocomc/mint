package pep.per.mint.common.msg.handler;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;

import pep.per.mint.common.data.basic.ComMessage;
import pep.per.mint.common.data.basic.Extension;
import pep.per.mint.common.data.basic.agent.IIPAgentInfo;
import pep.per.mint.common.data.basic.agent.ResourceUsageLog;
import pep.per.mint.common.util.Util;

public class MessageHandlerTest {

	MessageHandler messageHandler;

	@Before
	public void setUp() throws Exception {
		messageHandler = new MessageHandler();
		ItemDeserializer deserializer = new ItemDeserializer();
		messageHandler.setDeserializer(deserializer);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testDeserialize() {
		fail("Not yet implemented");
	}

	@Test
	public void testSerialize() {



		ComMessage  msg = new ComMessage();
		msg.setId(UUID.randomUUID());
		msg.setStartTime(Util.getFormatedDate());

		IIPAgentInfo agentInfo = new IIPAgentInfo();
		agentInfo.setAgentId(UUID.randomUUID().toString());
		agentInfo.setAgentCd("11");
		agentInfo.setDelYn("N");

		msg.setResponseObject(agentInfo);
		msg.setUserId("AG000001");
		msg.setAppId("WS0020");
		Extension extension = new Extension();
		extension.setServiceCd("WS0020");
		extension.setMsgType(Extension.MSG_TYPE_REQ);

		msg.setExtension(extension);

		String str="";
		try {
			str = messageHandler.serialize(msg);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		System.out.println(str);


		try {
			ComMessage<?, ?> cmMsg =  messageHandler.deserialize(str);

			System.out.println(cmMsg.toString());
			System.out.println(cmMsg.getId());
			System.out.println(cmMsg.getAppId());

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	@Test
	public void testSerialize1_WS_AS_RS_001() {

		ComMessage  msg = new ComMessage();
		msg.setId(UUID.randomUUID());
		msg.setStartTime(Util.getFormatedDate());
		ArrayList list = new ArrayList<ResourceUsageLog>();

		ResourceUsageLog r1 = new ResourceUsageLog();
		r1.setId(UUID.randomUUID().toString());
		r1.setMsg("2");
		ResourceUsageLog r2 = new ResourceUsageLog();
		r2.setId(UUID.randomUUID().toString());
		r2.setMsg("3");
		ResourceUsageLog r3 = new ResourceUsageLog();
		r3.setId(UUID.randomUUID().toString());
		r3.setMsg("5");
		list.add(r1);
		list.add(r2);
		list.add(r3);

		msg.setRequestObject(list);




		msg.setUserId("AG000001");
		msg.setAppId("WS0020");
		String str="";
		try {
			str = messageHandler.serialize(msg);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(str);


		try {
			ComMessage<?, ?> cmMsg =  messageHandler.deserialize(str);

			System.out.println(cmMsg.toString());
			System.out.println(cmMsg.getId());
			System.out.println(cmMsg.getAppId());

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}



	@Test
	public void testSerialize1_WS_AS_RS_001_org() {

		ComMessage  msg = new ComMessage();
		msg.setId(UUID.randomUUID());
		msg.setStartTime(Util.getFormatedDate());
		ArrayList list = new ArrayList<ResourceUsageLog>();

		ResourceUsageLog r1 = new ResourceUsageLog();
		r1.setId(UUID.randomUUID().toString());
		r1.setMsg("2");
		ResourceUsageLog r2 = new ResourceUsageLog();
		r2.setId(UUID.randomUUID().toString());
		r2.setMsg("3");
		ResourceUsageLog r3 = new ResourceUsageLog();
		r3.setId(UUID.randomUUID().toString());
		r3.setMsg("5");
		list.add(r1);
		list.add(r2);
		list.add(r3);

		msg.setRequestObject(list);




		msg.setUserId("AG000001");
		msg.setAppId("WS0020");
		String str="";
		try {
			str = messageHandler.serialize(msg);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(str);


		try {
			ComMessage cmMsg =  messageHandler.deserialize(str);

			System.out.println(cmMsg.toString());
			System.out.println(cmMsg.getId());
			System.out.println(cmMsg.getAppId());

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
