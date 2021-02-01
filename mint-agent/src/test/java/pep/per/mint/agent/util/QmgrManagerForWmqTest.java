package pep.per.mint.agent.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ibm.mq.MQMessage;
import com.ibm.mq.constants.MQConstants;
import com.ibm.mq.headers.MQDataException;
import com.ibm.mq.headers.pcf.MQCFIL;
import com.ibm.mq.headers.pcf.PCFException;
import com.ibm.mq.headers.pcf.PCFMessage;
import com.ibm.mq.headers.pcf.PCFParameter;

import pep.per.mint.common.data.basic.agent.ChannelInfo;
import pep.per.mint.common.data.basic.agent.QmgrInfo;
import pep.per.mint.common.data.basic.agent.QmgrLogs;
import pep.per.mint.common.data.basic.agent.QueueInfo;
import pep.per.mint.common.util.Util;

import com.ibm.mq.headers.pcf.PCFMessageAgent;

public class QmgrManagerForWmqTest {

	static Logger logger = LoggerFactory.getLogger(QmgrManagerForWmqTest.class);

	@Test
	public void testQmgrManagerForWmq(){
		try{

			QmgrManagerForWmq qmfw = new QmgrManagerForWmq();
			String agentId = "AG00000001";
			List<QmgrInfo> qmgrInfos = new ArrayList<QmgrInfo>();

			QmgrInfo qmgrInfo = new QmgrInfo();
			qmgrInfos.add(qmgrInfo);
			qmgrInfo.setQmgrNm("TEST80");
			qmgrInfo.setPort("8484");
			qmgrInfo.setIp("10.10.0.42");
			//채널 체크
			List<ChannelInfo> channels = new ArrayList<ChannelInfo>();
			qmgrInfo.setChannels(channels);
			ChannelInfo channelInfo1 = new ChannelInfo();
			channelInfo1.setChannelNm("TEST.SND.CH");
			channels.add(channelInfo1);

			ChannelInfo channelInfo2 = new ChannelInfo();
			channelInfo2.setChannelNm("SYSTEM.DEF.SVRCONN");
			channels.add(channelInfo2);

			//큐 체크
			List<QueueInfo> queues = new ArrayList<QueueInfo>();
			qmgrInfo.setQueues(queues);
			QueueInfo qInfo1 = new QueueInfo();
			qInfo1.setQueueNm("TEST.LQ");
			queues.add(qInfo1);
			QueueInfo qInfo2 = new QueueInfo();
			qInfo2.setQueueNm("TEST.TQ");
			queues.add(qInfo2);


			List<QmgrLogs> list = qmfw.getQmgrLogs(agentId, qmgrInfos);
			logger.debug(Util.join("logs:",Util.toJSONString(list)));

		}catch(Exception e){
			logger.error("",e);
		}
	}

	@Test
	public void testDisplayActiveLocalQueues(){
		//showQmgrAttributes();
		PCFMessageAgent agent = null;
		try {
			//agent = new PCFMessageAgent ("10.10.1.168", 1415, "SYSTEM.DEF.SVRCONN");
			agent = new PCFMessageAgent ("10.10.0.42", 8484, "SYSTEM.DEF.SVRCONN");


			/*MQEnvironment.hostname = "10.10.0.42";
			MQEnvironment.port = 8484;
			MQEnvironment.channel = "SYSTEM.DEF.SVRCONN";
			MQQueueManager qmgr = new MQQueueManager("TEST80");


			agent = new PCFMessageAgent(qmgr);
			agent.setCharacterSet(949);
			MQMessage msg = new MQMessage();
			msg.writeString("Are you ok?");
			qmgr.put("TEST.LQ", msg);*/

			displayActiveLocalQueues(agent, "*");
			//displayActiveLocalQueues(agent, "TEST.LQ");
			//displayActiveLocalQueues(agent, "MINT.EQ");
			//displayActiveLocalQueues(agent, "MINT3.EQ");

		} catch (MQDataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(agent != null){
				try {
					agent.disconnect();
				} catch (MQDataException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	 /**
	   * DisplayActiveLocalQueues uses the PCF command 'MQCMD_INQUIRE_Q' to gather information about all
	   * local Queues (using the '*' wildcard to denote 'all queues') contained within the given Queue
	   * Manager. The search is refined using a filter property to return just those queues that contain
	   * one or more messages. The information is displayed in a tabular form on the console.<br>
	   * For more information on the Inquire Queue command, please read the "Programmable Command
	   * Formats and Administration Interface" section within the Websphere MQ documentation.
	   *
	   * @param agent Object used to hold common objects used by the PCF samples.
	   * @throws IOException
	   * @throws MQDataException
	   * @throws PCFException
	   */
	  public static void displayActiveLocalQueues(PCFMessageAgent agent, String queueName) throws Exception,
	      MQDataException, IOException {
	    // Create the PCF message type for the inquire.
	    PCFMessage pcfCmd = new PCFMessage(MQConstants.MQCMD_INQUIRE_Q);

	    // Add the inquire rules.
	    // Queue name = wildcard.
	    pcfCmd.addParameter(MQConstants.MQCA_Q_NAME, queueName);

	    // Queue type = LOCAL.
	    pcfCmd.addParameter(MQConstants.MQIA_Q_TYPE, MQConstants.MQQT_ALL);

	    // Queue depth filter = "WHERE depth > 0".
	    //pcfCmd.addFilterParameter(MQConstants.MQIA_CURRENT_Q_DEPTH, MQConstants.MQCFOP_GREATER, 0);

	    // Execute the command. The returned object is an array of PCF messages.
	    PCFMessage[] pcfResponse = agent.send(pcfCmd);

	    // For each returned message, extract the message from the array and display the
	    // required information.
	    System.out.println("+-----+------------------------------------------------+-----+");
	    System.out.println("|Index|                    Queue Name                  |Depth|");
	    System.out.println("+-----+------------------------------------------------+-----+");

	    char[] space = new char[64];
	    Arrays.fill(space, 0, space.length, ' ');
	    String padding = new String(space);

	    for (int index = 0; index < pcfResponse.length; index++) {
	      PCFMessage response = pcfResponse[index];

	      System.out.println("|"
	          + (index + padding).substring(0, 5)
	          + "|"
	          + (response.getParameterValue(MQConstants.MQCA_Q_NAME) + padding).substring(0, 48)
	          + "|"
	          + (response.getParameterValue(MQConstants.MQIA_CURRENT_Q_DEPTH) + padding)
	              .substring(0, 5) + "|");
	    }

	    System.out.println("+-----+------------------------------------------------+-----+");
	    return;
	  }

	@Test
	public void testForChannelStatus(){
		//showQmgrAttributes();
		PCFMessageAgent agent = null;
		try {
			/*agent = new PCFMessageAgent ("10.10.1.168", 1415, "SYSTEM.DEF.SVRCONN");
			getChannelStatus(agent, true, "TEST.*");
			getChannelStatus(agent, true, "SYSTEM.DEF.SVRCONN");
			getChannelStatus(agent, true, "SYSTEM.DEF.*");*/
			agent = new PCFMessageAgent ("10.10.0.42", 8484, "SYSTEM.DEF.SVRCONN");
			getChannelStatus(agent, true, "*");
		} catch (MQDataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(agent != null){
				try {
					agent.disconnect();
				} catch (MQDataException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	static void showQmgrAttributes(){
		PCFMessageAgent agent = null;
		try{

			String channel = "SYSTEM.DEF.SVRCONN";
			agent = new PCFMessageAgent ("10.10.1.42", 8484, channel);
			/*
			int[] pcfParmAttrs = {MQConstants.MQIACF_ALL};
			PCFParameter[] pcfParameters = {new MQCFIL(MQConstants.MQIACF_Q_MGR_ATTRS, pcfParmAttrs)};
			MQMessage[] mqResponse = agent.send(MQConstants.MQCMD_INQUIRE_Q_MGR, pcfParameters);
			*/
			int[] pcfParmAttrs = {MQConstants.MQIACF_Q_MGR_STATUS_ATTRS};
			PCFParameter[] pcfParameters = {new MQCFIL(MQConstants.MQIACF_Q_MGR_STATUS_ATTRS, pcfParmAttrs)};
			MQMessage[] mqResponse = agent.send(MQConstants.MQCMD_INQUIRE_Q_MGR_STATUS, pcfParameters);

			logger.debug(Util.join("res:",Util.join(Util.toJSONString(mqResponse))));

/*
			MQCFH mqCFH = new MQCFH(mqResponse[0]);
			char[] space = new char[64];
		    Arrays.fill(space, 0, space.length, ' ');
		    String padding = new String(space);
			PCFParameter pcfParam;

		    if (mqCFH.getReason() == 0) {
		      logger.debug("Queue manager attributes:");
		      logger.debug("+--------------------------------+----------------------------------------------------------------+");
		      logger.debug("|Attribute Name                  |                            Value                               |");
		      logger.debug("+--------------------------------+----------------------------------------------------------------+");

		      for (int index = 0; index < mqCFH.getParameterCount(); index++) {
		        pcfParam = PCFParameter.nextParameter(mqResponse[0]);

		        logger.debug("|" + (pcfParam.getParameterName() + padding).substring(0, 32) + "|" + (pcfParam.getValue().toString().length() < 64 ? (pcfParam.getValue() + padding).substring(0, 64) : (pcfParam.getValue() + padding).substring(0, 61) + "...") + "|");
		      }

		      logger.debug("+--------------------------------+----------------------------------------------------------------+");
		    }
		    else {
		      logger.debug(" PCF error:\n" + mqCFH);

		      // Walk through the returned parameters describing the error.
		      for (int index = 0; index < mqCFH.getParameterCount(); index++) {
		        logger.debug(PCFParameter.nextParameter(mqResponse[0]).toString());
		      }
		    }
*/

		}catch(Exception e){
			logger.error("",e);
		}finally{
			if(agent != null) {
				try {
					agent.disconnect();
				} catch (MQDataException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}



	/**
	 * ChannelStatus uses the PCF command 'MQCMD_INQUIRE_CHANNEL_STATUS' to read
	 * a channel's status on the Queue Manager. Mandatory PCF parameters must
	 * appear first. Failure to define these parameters first will result in a
	 * 3015 (MQRCCF_PARM_SEQUENCE_ERROR) error. <br>
	 * For more information on the Inquire Channel Status command, please read
	 * the "Programmable Command Formats and Administration Interface" section
	 * within the Websphere MQ documentation.
	 *
	 * @param agent
	 *            Object used to hold common objects used by the PCF samples.
	 * @throws IOException
	 * @throws MQDataException
	 */
	public static void getChannelStatus(PCFMessageAgent agent, boolean mode, String channelName) throws MQDataException, IOException {
		// Create the PCF message type for the inquire channel.
		PCFMessage pcfCmd = new PCFMessage(MQConstants.MQCMD_INQUIRE_CHANNEL_STATUS);

		// Add the start channel mandatory parameters.
		// Channel name.
		pcfCmd.addParameter(MQConstants.MQCACH_CHANNEL_NAME, channelName);

		// Execute the command. The returned object is an array of PCF messages.
		// If no object
		// can be returned, then catch the exception as this may not be an
		// error.
		try {
			PCFMessage[] pcfResponse = agent.send(pcfCmd);

			logger.debug(Util.join("res:",Util.toJSONString(pcfResponse)));

			int chStatus = ((Integer) (pcfResponse[0].getParameterValue(MQConstants.MQIACH_CHANNEL_STATUS))).intValue();

			String[] chStatusText = { "", "MQCHS_BINDING", "MQCHS_STARTING", "MQCHS_RUNNING", "MQCHS_STOPPING",
					"MQCHS_RETRYING", "MQCHS_STOPPED", "MQCHS_REQUESTING", "MQCHS_PAUSED", "", "", "", "",
					"MQCHS_INITIALIZING" };

			System.out.println("Channel status is " + chStatusText[chStatus]);
		} catch (PCFException pcfe) {
			// If the channel type is MQCHT_RECEIVER, MQCHT_SVRCONN or
			// MQCHT_CLUSRCVR, then the
			// only action is to enable the channel, not start it.
			if (pcfe.reasonCode != MQConstants.MQRCCF_CHL_STATUS_NOT_FOUND) {
				throw pcfe;
			}

			System.out.println(
					"Either the queue manager \"" + agent.getQManagerName() + "\" does not exist or the channel \""
							+ channelName + "\" does not exist on the queue manager.");

		}
		return;
	}


	@Test
	public void testForDisplayActiveLocalChannels(){
		PCFMessageAgent agent = null;
		try {
			//agent = new PCFMessageAgent ("10.10.1.168", 1415, "SYSTEM.DEF.SVRCONN");
			agent = new PCFMessageAgent ("10.10.0.42", 8484, "SYSTEM.DEF.SVRCONN");
			displayActiveLocalChannels(agent);
		} catch (MQDataException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(agent != null){
				try {
					agent.disconnect();
				} catch (MQDataException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	 /**
	   * DisplayActiveLocalChannels uses the PCF command 'MQCMD_INQUIRE_CHANNEL_NAMES' to find all
	   * Channels (using the '*' wildcard) of all types (using the 'MQCHT_ALL' type) on the Queue
	   * Manager. Mandatory parameters must appear first. Failure to define these parameters first will
	   * result in a 3015 (MQRCCF_PARM_SEQUENCE_ERROR) error.<br>
	   * For more information on the Delete Queue command, please read the "Programmable Command Formats
	   * and Administration Interface" section within the Websphere MQ documentation.
	   *
	   * @param agent Object used to hold common objects used by the PCF samples.
	   * @throws IOException
	   * @throws MQDataException
	   * @throws PCFException
	   */
	  public static void displayActiveLocalChannels(PCFMessageAgent agent) throws PCFException,
	      MQDataException, IOException {
	    // Create the PCF message type for the channel names inquire.
	    PCFMessage pcfCmd = new PCFMessage(MQConstants.MQCMD_INQUIRE_CHANNEL_NAMES);

	    // Add the inquire rules.
	    // Queue name = wildcard.
	    pcfCmd.addParameter(MQConstants.MQCACH_CHANNEL_NAME, "*");

	    // Channel type = ALL.
	    pcfCmd.addParameter(MQConstants.MQIACH_CHANNEL_TYPE, MQConstants.MQCHT_ALL);

	    // Execute the command. The returned object is an array of PCF messages.
	    PCFMessage[] pcfResponse = agent.send(pcfCmd);

	    // For each returned message, extract the message from the array and display the
	    // required information.
	    System.out.println("+-----+------------------------------------------------+----------+");
	    System.out.println("|Index|                  Channel Name                  |   Type   |");
	    System.out.println("+-----+------------------------------------------------+----------+");


		char[] space = new char[64];
	    Arrays.fill(space, 0, space.length, ' ');
	    String padding = new String(space);

	    // The Channel information is held in some array element of the response object (the
	    // contents of the response object is defined in the documentation).
	    for (int responseNumber = 0; responseNumber < pcfResponse.length; responseNumber++) {
	      String[] names = (String[]) pcfResponse[responseNumber]
	          .getParameterValue(MQConstants.MQCACH_CHANNEL_NAMES);

	      // There might not be any names, so test this first before attempting to parse the object.
	      if (names != null) {
	        int[] types = (int[]) pcfResponse[responseNumber]
	            .getParameterValue(MQConstants.MQIACH_CHANNEL_TYPES);
	        String[] channelTypes = {"", "SDR", "SVR", "RCVR", "RQSTR", "", "CLTCN", "SVRCN",
	            "CLUSRCVR", "CLUSSDR", ""};
	        for (int index = 0; index < names.length; index++) {
	          System.out.println("|" + (index + padding).substring(0, 5) + "|"
	              + (names[index] + padding).substring(0, 48) + "|"
	              + (channelTypes[types[index]] + padding).substring(0, 10) + "|");
	        }

	        System.out.println("+-----+------------------------------------------------+----------+");
	        break;
	      }
	    }
	    return;
	  }

}
