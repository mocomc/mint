package pep.per.mint.agent;


import java.io.InputStream;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import pep.per.mint.agent.controller.AgentController;
import pep.per.mint.agent.event.QueueListenerImpl;
import pep.per.mint.agent.exception.AgentException;
import pep.per.mint.common.data.basic.agent.CommandConsole;
import pep.per.mint.common.data.basic.agent.IIPAgentInfo;
import pep.per.mint.common.util.Util;


public class IIPAgent implements AgentInfoChanggeListener{

	public final Logger logger = LoggerFactory.getLogger(IIPAgent.class);

	ClassLoader userClassLoader;

	AgentLauncherInterface launcher;

	CommandConsole deployCmd;

	ApplicationContext ac = null;

	IIPAgentInfo agentInfo = null;

	// AgentLuncher
	static String agentId;
	static String agentNm;
	static String agentCd;


	static String agentHome;
	static AgentProperties properties = new AgentProperties();

	public IIPAgent() {
	}

	AgentController agentController = null;
	QueueListenerImpl queueListener = null;
	ApplicationContext parent = null;

	private String URL = "";

	void run() {
		try {
			String[] cls = {"classpath:/config/agent-context.xml","classpath:/config/ext-context.xml"};

			ac = new ClassPathXmlApplicationContext(cls, parent){
				@Override
				protected void initBeanDefinitionReader(XmlBeanDefinitionReader reader) {
					super.initBeanDefinitionReader(reader);
//					reader.setValidationMode(XmlBeanDefinitionReader.VALIDATION_NONE);
		            reader.setBeanClassLoader(userClassLoader);
		            setClassLoader(userClassLoader);
				}

				@Override
				public void close() {
					agentController.logoff();
					super.close();
					logger.info("//--------------------------------------------");
					logger.info("// IIP AGENT(Version.3.0.0-181022.1) STOPPED!");
					logger.info("//--------------------------------------------");
				}
			};


			agentController = ac.getBean("agentController", pep.per.mint.agent.controller.AgentController.class);
			agentController.setAgentLauncherInterface(this.launcher);


			agentInfo = new IIPAgentInfo();
			agentInfo.setAgentCd(agentCd);
			agentInfo.setAgentNm(agentNm);

			logger.info("//--------------------------------------------");
			logger.info("Agent Info :: "+agentInfo.getAgentNm());
			logger.info("//--------------------------------------------");

			queueListener = ac.getBean("queueListener", pep.per.mint.agent.event.QueueListenerImpl.class);
			queueListener.setUrl(URL);
			queueListener.start();

			logger.info("//--------------------------------------------");
			logger.info("//	IIP AGENT(Version.3.0.0-181022.1) STARTED!");
			logger.info("//--------------------------------------------");


		} catch (Exception e) {
			logger.error("",e);
		} catch (Throwable e) {
			logger.error("",e);
		} finally{

		}
	}

	// ??????
	public void deploy(CommandConsole deployCmd) throws Throwable{
		String repositoryData = Util.toJSONString(deployCmd);
		launcher.saveRepository(AgentLauncherInterface.CMD_DEPLOY_FILE_NAME, repositoryData);
		launcher.deploy();
	}


	public void stop(){
		// logoff 를 전달 한다.
		agentController.logoff();

		((ClassPathXmlApplicationContext) ac).close();
		queueListener.setStopRun();
	}

	public void onChanged(IIPAgentInfo info) {
		launcher.setAgentInfo(info);
		this.agentInfo = info;
	}

	public void changeInfo(){
		agentController.updateAgentInfoByCmd();
	}

	/**
	 * @param userClassLoader
	 * @param agrs
	 * @param startup
	 * @throws Throwable
	 * @Deprecated
	 */
	public void runStart(ClassLoader userClassLoader, String[] agrs, ApplicationContext ctx) throws Throwable {

		this.userClassLoader = userClassLoader;


		logger.info("IIP Agent Start");

		this.parent = ctx;
		this.initialize();

		this.run();

	}

	public void start(ClassLoader userClassLoader, AgentLauncherInterface launcher, ApplicationContext ctx ) throws AgentException{
		this.userClassLoader = userClassLoader;
		this.launcher = launcher;
		this.parent  = ctx;
		if(launcher == null){
			logger.debug("//--------------------------------------------");
			logger.debug("// IIP AGENT launcher is null.");
			logger.debug("//--------------------------------------------");
			throw new AgentException("IIP AGENT launcher is null.");
		}
		try {
			this.initialize();
		} catch (Throwable e) {
			throw new AgentException("IIP AGENT launcher is init error.");
		}
		run();
	}


	private void initialize() throws Throwable{
//		String agentStatus = IIPAgentInfo.AGENT_STAT_LOGOFF;
//		String agentMsg = null;
		InputStream is = ClassLoader.getSystemResourceAsStream("config/agent.properties");
		properties.load(is);
//		properties.list(System.out);
		logger.info(properties.toString());

		agentHome = properties.readProperty("iip.agent.home",true);
		System.setProperty("IIP_LOG_HOME", agentHome);
		//
		URL = Util.join(properties.readProperty("iip.server.websocket.protocol",true) +"://"
				+properties.readProperty("iip.server.websocket.ip",true) +":"
				+properties.readProperty("iip.server.websocket.port",true) +"/"
				+properties.readProperty("iip.server.websocket.contextroot",true) +"/"
				+properties.readProperty("iip.server.websocket.agentpath",true) +"/"
				+properties.readProperty("iip.server.websocket.websocketUrl",true));

		logger.info("//--------------------------------------------");
		logger.info("// IIP AGENT 서비스를 위한 BASE URL 처리");
		logger.info("//--------------------------------------------");
		logger.info("iip.agent.home:" + agentHome);

		logger.info("//--------------------------------------------");
		logger.info("// IIP AGENT 서비스를 위한 프로퍼티 읽기");
		logger.info("//--------------------------------------------");
		agentId = properties.readProperty("iip.agent.id",false);
		agentCd = properties.readProperty("iip.agent.nm",true);
		agentNm = properties.readProperty("iip.agent.cd",true);
		logger.info("property:iip.agent.id:" + agentId);
		logger.info("property:iip.agent.nm:" + agentNm);
		logger.info("property:iip.agent.cd:" + agentCd);

	}

}
