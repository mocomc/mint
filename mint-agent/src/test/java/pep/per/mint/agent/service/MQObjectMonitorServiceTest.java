package pep.per.mint.agent.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import pep.per.mint.agent.exception.AgentException;
import pep.per.mint.common.data.basic.agent.IIPAgentInfo;
import pep.per.mint.common.data.basic.agent.MonitorItem;
import pep.per.mint.common.data.basic.agent.QmgrInfo;
import pep.per.mint.common.data.basic.qmgr.QmgrProperty;
import pep.per.mint.common.data.basic.qmgr.QueueProperty;

//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations={"classpath:/config/agent-context.xml"})
public class MQObjectMonitorServiceTest {
	@Autowired
	MQObjectMonitorService mqObjectMonitorService;
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	//@Test
	public void testGetMQList() {
		mqObjectMonitorService =  new MQObjectMonitorService();
		try {
			List<QmgrProperty> list =  mqObjectMonitorService.getMQList();
			for(QmgrProperty qmgr : list){
				System.out.println(qmgr.getName() + "    " + qmgr.getStatus());
			}

		} catch (AgentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testGetMQListForIIPAgentInfo() throws AgentException {
		mqObjectMonitorService =  new MQObjectMonitorService();
		IIPAgentInfo agentInfo = new IIPAgentInfo();

		MonitorItem  item1 = new MonitorItem();
		item1.setItemType(MonitorItem.ITEM_TYPE_QMGR);

		List<QmgrInfo> qmgrs = new ArrayList();
		QmgrInfo qmgr = new QmgrInfo();
		qmgr.setIp("127.0.0.1");
		qmgr.setPort("1414");
		qmgr.setQmgrNm("KDN");
		qmgr.setType(QmgrInfo.TYPE_MQ);
		qmgrs.add(qmgr);

		item1.setQmgrs(qmgrs);

		List<MonitorItem> monitorItems  = new ArrayList();
		monitorItems.add(item1);

		agentInfo.setMonitorItems(monitorItems);

		List<QmgrProperty> list =  mqObjectMonitorService.getMQList(agentInfo);
		for(QmgrProperty qmgrPrp : list){
			System.out.println(qmgrPrp.getName() + "    " + qmgrPrp.getStatus());
		}
	}


	@Test
	public void testGetQListForIIPAgentInfo() {
		mqObjectMonitorService =  new MQObjectMonitorService();

		IIPAgentInfo agentInfo = new IIPAgentInfo();

		MonitorItem  item1 = new MonitorItem();
		item1.setItemType(MonitorItem.ITEM_TYPE_QMGR);

		List<QmgrInfo> qmgrs = new ArrayList();
		QmgrInfo qmgr1 = new QmgrInfo();
		qmgr1.setIp("127.0.0.1");
		qmgr1.setPort("1414");
		qmgr1.setQmgrNm("KDN");
		qmgr1.setType(QmgrInfo.TYPE_MQ);
		qmgrs.add(qmgr1);

		item1.setQmgrs(qmgrs);

		List<MonitorItem> monitorItems  = new ArrayList();
		monitorItems.add(item1);

		agentInfo.setMonitorItems(monitorItems);

 		try {
 			List<QmgrProperty> list =  mqObjectMonitorService.getMQList(agentInfo);
			for(QmgrProperty qmgr : list){
				System.out.println("QgmrNm = " + qmgr.getName() + "    " + qmgr.getStatus());

				HashMap<String, String> agent = new HashMap<>();
				agent.put("qmgrNm",qmgr.getName());

				List<QueueProperty> listQ =  mqObjectMonitorService.getQList(agent, agentInfo);
				for(QueueProperty queue : listQ){
					System.out.println("\t"+queue.toString());
				}
			}

		} catch (AgentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	//@Test
	public void testGetQList() {
		mqObjectMonitorService =  new MQObjectMonitorService();
 		try {
 			List<QmgrProperty> list =  mqObjectMonitorService.getMQList();
			for(QmgrProperty qmgr : list){
				System.out.println("QgmrNm = " + qmgr.getName() + "    " + qmgr.getStatus());

				HashMap<String, String> agent = new HashMap<>();
				agent.put("qmgrNm",qmgr.getName());

				List<QueueProperty> listQ =  mqObjectMonitorService.getQList(agent);
				for(QueueProperty queue : listQ){
					System.out.println("\t"+queue.toString());
				}
			}

		} catch (AgentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
