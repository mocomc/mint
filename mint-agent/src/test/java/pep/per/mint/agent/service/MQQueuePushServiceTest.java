package pep.per.mint.agent.service;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import pep.per.mint.agent.exception.AgentException;
import pep.per.mint.common.data.basic.qmgr.QueueProperty;

public class MQQueuePushServiceTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetQStatus() {
		MQQueuePushService mq = new MQQueuePushService("","",1, null,"111");
		HashMap<String, String> agent = new HashMap<>();
		agent.put("qmgrNm","KDN");
		agent.put("queueNm","KDN.LQ");

		while(true){
			 List<QueueProperty> list;
			try {
				list = mq.getQStatus(agent);
				for(QueueProperty queue : list){
					System.out.println(queue.toString());
				}
			} catch (AgentException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}


			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
