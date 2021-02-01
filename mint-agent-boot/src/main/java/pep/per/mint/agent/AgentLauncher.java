package pep.per.mint.agent;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;

import pep.per.mint.agent.common.ComMessage;
import pep.per.mint.agent.common.Extension;
import pep.per.mint.agent.common.MessageHandler;
import pep.per.mint.agent.util.Util;


@EnableAutoConfiguration
@RestController
public class AgentLauncher implements AgentLauncherInterface{

	/**
	 *
	 */
	private static final long serialVersionUID = -1409203604580561756L;

	final static int delay = 10*1000;

	static Logger logger = null;
	static String agentId;
	static String agentNm;
	static String agentCd;
	static AgentProperties properties = new AgentProperties();
	static String agentHome;

	static String webSocketURL;


//	static Map<String,CommandConsole> commandMap = new HashMap<String,CommandConsole>();

	ClassLoader classLoader;
	Class agentClass;
	Object agent;
	File repositoryPathFile;
	String deployCmdFile;
	String deployCmdData;

	static AgentLauncher launcher = null;
	static ApplicationContext spring_boot = null;
	String deployVersion ="3.0";

	static void initialize() throws Throwable{



		String agentMsg = null;
		launcher = new AgentLauncher();
		InputStream is = ClassLoader.getSystemResourceAsStream("config/agent.properties");
		properties.load(is);
		properties.list(System.out);
		webSocketURL = properties.readProperty("iip.server.websocket.protocol",true) +"://"
				+properties.readProperty("iip.server.websocket.ip",true) +":"
				+properties.readProperty("iip.server.websocket.port",true) +"/"
				+properties.readProperty("iip.server.websocket.contextroot",true) +"/"
				+properties.readProperty("iip.server.websocket.agentpath",true) +"/"
				+properties.readProperty("iip.server.websocket.websocketUrl",true);


		agentHome = properties.readProperty("iip.agent.home",true);
		System.setProperty("IIP_LOG_HOME", agentHome);
		logger = LoggerFactory.getLogger(AgentLauncher.class);

		logger.info("//--------------------------------------------");
		logger.info("// IIP AGENT 서비스를 위한 BASE URL 처리");
		logger.info("//--------------------------------------------");
		logger.info("iip.agent.home:" + agentHome);

		logger.info(" WebSocketURL:" + webSocketURL);

		logger.info("//--------------------------------------------");
		logger.info("// IIP AGENT 서비스를 위한 프로퍼티 읽기");
		logger.info("//--------------------------------------------");
		agentId = properties.readProperty("iip.agent.id",false);
		agentCd = properties.readProperty("iip.agent.nm",true);
		agentNm = properties.readProperty("iip.agent.cd",true);
		logger.info("property:iip.agent.id:" + agentId);
		logger.info("property:iip.agent.nm:" + agentNm);
		logger.info("property:iip.agent.cd:" + agentCd);

//		IIPAgentInfo req = new IIPAgentInfo();
//		req.setAgentCd(agentCd);
//		req.setAgentNm(agentNm);

		SpringApplication app = new SpringApplication();
		spring_boot = app.run(AgentLauncher.class);

	}

	private void addLogoffHook() {
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable(){
			public void run() {
				System.out.println("Context Shutdown");
				((ConfigurableApplicationContext) spring_boot).close();
				try {
					pause();
				} catch (Exception e) {
				}
			}
		}));
	}

	public static void main( String[] args ) {
		AgentLauncher al = new AgentLauncher();
		al.init(args);
    }

	public void init(String[] args){
		String agentMsg = "";
		try {
			initialize();
			launcher.launch();
			agentMsg = "success to launch iipagent";
		} catch (Throwable t) {
			if(logger == null){
				System.err.println("// IIP AGENT LAUNCHING FAIL:{agentNm:"+agentNm +")");
				t.printStackTrace();
			}else{
				logger.info("//--------------------------------------------");
				logger.info("// IIP AGENT LAUNCHING 실패:{agentNm:"+agentNm+")");
				logger.info("//--------------------------------------------");
				logger.info("//아래와 같은 이유로 에이전트실행을 실패했습니다. 문제해결 후 다시 실행해주세요.");
				logger.error("agent launcher error:",t);
			}
			String errorDetail = "";
			PrintWriter pw = null;
			try{
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				pw = new PrintWriter(baos);
				t.printStackTrace(pw);
				pw.flush();
				if(pw != null)  errorDetail = baos.toString();
			}finally{
				if(pw != null) pw.close();
			}
			agentMsg = "Agent "+agentNm +" fail to logon, the state of agent is logoff.(error detail:"+errorDetail+")";
			System.exit(-1);
		} finally{
		}
	}

	public void pause(long sleep) throws Exception{
//		((ConfigurableApplicationContext) spring_boot).close();
		Method stop = agentClass.getDeclaredMethod("stop", null);
		stop.invoke(agent, null);
	}

	public void reLaunch(long sleep) throws Exception{
		pause(sleep);

		((URLClassLoader)this.classLoader).close();

		this.classLoader = null;
		this.agentClass = null;
		this.agent = null;
		launch();
	}


	public void launch() throws Exception{
		try {
			while(true){
				try{
					logger.info("//--------------------------------------------");
					logger.info("// IIP AGENT repository 작업 ");
					logger.info("//--------------------------------------------");
					String repositoryPath = properties.readProperty("iip.agent.repository.path", true);
					logger.info("property:iip.agent.repository.path:" + repositoryPath);
					repositoryPathFile = new File(repositoryPath);
					if(!repositoryPathFile.exists()){
						repositoryPathFile.mkdirs();
					}else{
						File dataFile = new File(repositoryPathFile,CMD_DEPLOY_FILE_NAME);
						if(dataFile.exists()){
							 byte[] readData = readFile(dataFile);
							 deployCmdData = new String(readData, "UTF-8");
						}
					}
					logger.info("//--------------------------------------------");
					logger.info("// IIP AGENT 서비스를 위한 BASE URL 처리 ");
					logger.info("//--------------------------------------------");
					String baseUrl = properties.readProperty("iip.server.protocol",true) + "://" +
									 properties.readProperty("iip.server.ip",true) + ":" +
									 properties.readProperty("iip.server.port",true) + "/" +
									 properties.readProperty("iip.server.contextroot",true) + "/" +
									 properties.readProperty("iip.server.agentpath",true);
					logger.info("base.url:" + baseUrl);

					logger.info("//--------------------------------------------");
					logger.info("// IIP AGENT 서비스를 위한 서버연결사태 체크");
					logger.info("//--------------------------------------------");
					ping(new URL(baseUrl));

					try{
						deployVersion  = properties.readProperty("iip.server.deploy.version",false);
					}catch(Exception e){
					}

					if(deployVersion == null){
						deployVersion ="v3.0";
					}
					logger.info("//--------------------------------------------");
					logger.info("// IIP AGENT DeployVersion " + deployVersion);
					logger.info("//--------------------------------------------");


					logger.info("//--------------------------------------------");
					logger.info("// IIP AGENT 소스 배포 위치 조회 ");
					logger.info("//--------------------------------------------");
					String deployPath = baseUrl + "/deploy_" + deployVersion;

					logger.info("//--------------------------------------------");
					logger.info("// IIP AGENT 클래스 및 라이브러리 URL 리스트 생성 ");
					logger.info("//--------------------------------------------");
					List<URL> urlList = new ArrayList<URL>();

					logger.info("//--------------------------------------------");
					logger.info("// IIP AGENT Class 소스 위치 조회 및 URL 리스트 추가");
					logger.info("//--------------------------------------------");
//					String classesPath = deployPath + "/classes/";
//					URL classesUrl = new URL(classesPath);
//					urlList.add(classesUrl);
//					logger.info("classes path:" + classesPath);

//					logger.info("//--------------------------------------------");
//					logger.info("// IIP AGENT Common Class 소스 위치 조회 및 URL 리스트 추가");
//					logger.info("//--------------------------------------------");
//					String commonClassesPath = deployPath + "/common-classes/";
//					URL commonClassesUrl = new URL(commonClassesPath);
//					urlList.add(commonClassesUrl);
//					logger.info("common classes path:" + commonClassesUrl);


					logger.info("//--------------------------------------------");
					logger.info("// IIP AGENT Lib(jar) 리스트 조회 및 URL 추가 ");
					logger.info("//--------------------------------------------");
					URL libListUrl = new URL(deployPath + "/library.list");
					logger.info("library list:" + libListUrl.toString());
					byte [] data = readContents(libListUrl);
					String libList = new String(data);
					StringTokenizer sb = new StringTokenizer(libList,",");
					while(sb.hasMoreTokens()){
						String name = sb.nextToken().trim();
						URL url = new URL(deployPath + "/lib/" + name);
						urlList.add(url);
					}

					logger.info("//--------------------------------------------");
					logger.info("// IIP AGENT ext-context.xml replace 처리 ");
					logger.info("//--------------------------------------------");
					URL extContextUrl = new URL(deployPath + "/classes/config/ext-context.xml");
					byte [] extContextBytes = readContents(extContextUrl);
					if(extContextBytes != null && extContextBytes.length > 0)
						replaceContext(agentHome + File.separator + "classes" + File.separator + "config" + File.separator + "ext-context.xml", extContextBytes);

					logger.info("//--------------------------------------------");
					logger.info("// IIP AGENT agent-context.xml replace 처리    ");
					logger.info("//--------------------------------------------");
					URL agentContextUrl = new URL(deployPath + "/classes/config/agent-context.xml");
					byte [] agentContextBytes = readContents(agentContextUrl);
					if(extContextBytes != null && extContextBytes.length > 0)
						replaceContext(agentHome + File.separator + "classes" + File.separator + "config" + File.separator + "agent-context.xml", agentContextBytes);


					logger.info("//--------------------------------------------");
					logger.info("// IIP AGENT 실행을 위한 클래스 로더 생성 ");
					logger.info("//--------------------------------------------");
					URL[] params = new URL[urlList.size()];
					URL[] urls = urlList.toArray(params);
					for(int i = 0 ; i < urls.length ; i ++) logger.info("class loader url[" + i + "]:" + urls[i]);
					classLoader = new URLClassLoader(urls);


					logger.info("//--------------------------------------------");
					logger.info("// IIP AGENT 실행");
					logger.info("//--------------------------------------------");


					agentClass = classLoader.loadClass("pep.per.mint.agent.IIPAgent");
					agent = agentClass.newInstance();
					Class[] paramTypes = {ClassLoader.class, AgentLauncherInterface.class, ApplicationContext.class};

					Method start = agentClass.getDeclaredMethod("start", paramTypes);

					Object[] paramValues = {classLoader, this, spring_boot};

					start.invoke(agent, paramValues);

					logger.info("//--------------------------------------------");
					logger.info("// IIP AGENT 실행 OK!");
					logger.info("//--------------------------------------------");


					logger.info("//--------------------------------------------");
					logger.info("// IIP AGENT LAUNCHER 실행 OK!");
					logger.info("//--------------------------------------------");


					addLogoffHook();

					return;

				}catch(Throwable t){
					logger.info("//--------------------------------------------");
					logger.error("에이전트런처에러:IIP 에이전트 실행 과정중 다음과 같은 예외 발생으로 " + (delay/1000) + "sec 후 재시도합니다.",t);
					logger.info("//--------------------------------------------");
					Thread.sleep(delay);
				}

			}

		} finally{

		}
	}


	/**
	 * @param url
	 * @throws Throwable
	 */
	private void ping(URL url) throws Throwable {
		try{
			URLConnection con = url.openConnection();
			con.connect();
		}catch(Throwable t){
			throw new Exception("IIP 서버 Ping 예외:",t);
		}
	}

	public void setAgentId(String id) {
		this.agentId = id;
	}

	private void replaceContext(String file, byte[] contextBytes) throws IOException {
		// TODO Auto-generated method stub
		FileOutputStream fos = null;
		try{
			File old = new File(file);
			File dest = new File(file + ".bak");

			if(old.exists()){
				old.renameTo(dest);
			}

			fos = new FileOutputStream(old);

			fos.write(contextBytes);
			fos.flush();
		}finally{
			if(fos !=null) fos.close();
		}
	}

	private byte[] readContents(URL url) throws Exception{
		InputStream is = null;
		try{
			URLConnection con = url.openConnection();
			is = con.getInputStream();

			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			while(true){

				int b = is.read();
				if(b <= 0){
					break;
				}
				baos.write(b);
			}
			return baos.toByteArray();
		}finally{
			if(is != null) is.close();
		}

	}

	private byte[] readFile(File file) throws Exception{
		InputStream is = null;
		try{
			is = new FileInputStream(file);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			while(true){

				int b = is.read();
				if(b <= 0){
					break;
				}
				baos.write(b);
			}
			return baos.toByteArray();
		}finally{
			if(is != null) is.close();
		}

	}

//	public void saveRepository(String name, String data) throws Exception {
//		logger.info("//--------------------------------------------");
//		logger.info(Util.join("// IIP AGENT resetRepository : name :", name, ", data:" + data));
//		logger.info("//--------------------------------------------");
//		commandMap.put(name, (CommandConsole)Util.jsonToObject(data,CommandConsole.class));
//	}
//
//
//
//	public void clearRepository(String name, String data) throws Exception {
//		logger.info("//--------------------------------------------");
//		logger.info(Util.join("// IIP AGENT clearRepository : name :", name, ", data:" + data));
//		logger.info("//--------------------------------------------");
//		commandMap.remove(name);
//	}
//
//
//	public void saveRepository(String name, CommandConsole data) throws Exception {
//		logger.info("//--------------------------------------------");
//		logger.info(Util.join("// IIP AGENT resetRepository : name :", name, ", data:" + data));
//		logger.info("//--------------------------------------------");
//		commandMap.put(name, data);
//	}
//
//
//
//	public void clearRepository(String name, CommandConsole data) throws Exception {
//		logger.info("//--------------------------------------------");
//		logger.info(Util.join("// IIP AGENT clearRepository : name :", name, ", data:" + Util.toJSONString(data)));
//		logger.info("//--------------------------------------------");
//		commandMap.remove(name);
//	}


//	public void setAgentInfo(IIPAgentInfo agentInfo) {
//		this.agentInfo = agentInfo;
//
//	}

	public void deploy() throws Exception {
		reLaunch(1000L);
	}

	public void pause() throws Exception {
		logger.info("//--------------------------------------------");
		logger.info("// IIP AGENT PAUSE");
		logger.info("//--------------------------------------------");
		Method stop = agentClass.getDeclaredMethod("stop", null);
		stop.invoke(agent, null);
		logger.info("//--------------------------------------------");
		logger.info("// IIP AGENT PAUSE OK!");
		logger.info("//--------------------------------------------");
	}

	public void resume() throws Exception {
		logger.info("//--------------------------------------------");
		logger.info("// IIP AGENT 실행");
		logger.info("//--------------------------------------------");
		agentClass = classLoader.loadClass("pep.per.mint.agent.IIPAgent");
		//Runnable agent = (Runnable)agentClass.newInstance();
		agent = agentClass.newInstance();
		Class[] paramTypes = {ClassLoader.class, AgentLauncherInterface.class};

		Method start = agentClass.getDeclaredMethod("start", paramTypes);

		Object[] paramValues = {classLoader, this};

		start.invoke(agent, paramValues);

		logger.info("//--------------------------------------------");
		logger.info("// IIP AGENT 실행 OK!");
		logger.info("//--------------------------------------------");
	}

	/* (non-Javadoc)
	 * @see pep.per.mint.agent.AgentLauncherInterface#checkAlive()
	 * @deprecated
	 */
	public void checkAlive() throws Exception {
		logger.info("//--------------------------------------------");
		logger.info("// IIP AGENT checkAlive()");
		logger.info("//--------------------------------------------");
		String msg = "I'm alive";
//		sendAgentLog(agentId, status, msg);  ///???
		logger.info("//--------------------------------------------");
		logger.info("// IIP AGENT checkAlive() finish.");
		logger.info("//--------------------------------------------");
	}



	public void changeInfo() throws Exception {
		logger.info("//--------------------------------------------");
		logger.info("// IIP AGENT changeInfo");
		logger.info("//--------------------------------------------");
		Method changeInfo = agentClass.getDeclaredMethod("changeInfo", null);
		changeInfo.invoke(agent, null);
		logger.info("//--------------------------------------------");
		logger.info("// IIP AGENT changeInfo finish.");
		logger.info("//--------------------------------------------");
	}

	public void exit() {
		try{
			pause();
			logger.info("//--------------------------------------------");
			logger.info("// IIP AGENT STOP & EXIT OK!");
			logger.info("//--------------------------------------------");
		}catch(Exception e){
			logger.error("", e);
		}finally{
			System.exit(1);
		}
	}

//	public void sendAgentStateLog(IIPAgentLog log) {
//		logger.trace("IIPAgent 3.0 not used.");
//	}

	@RequestMapping(value="/console/alive",
			method=RequestMethod.POST,
			headers="content-type=application/json")
	public  @ResponseBody ComMessage<?, ?>  alive(HttpSession  httpSession,
			@RequestBody ComMessage<?, ?> comMessage) throws JsonProcessingException{

		MessageHandler mh = new MessageHandler();
		logger.debug(mh.serialize(comMessage));
		ComMessage msg = comMessage;
		boolean retValue = true;
		msg.setResponseObject(retValue);
		msg.setEndTime(Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI));
		msg.setErrorCd("0");

		Extension ext = new Extension();
		ext.setMsgType(Extension.MSG_TYPE_RES);
		ext.setServiceCd("WS0033");
		msg.setExtension(ext);

		return msg;
	}

	@RequestMapping(value="/console/shutdown",
	method=RequestMethod.POST,
	headers="content-type=application/json")
	public  @ResponseBody ComMessage<?, ?>  shutdown(HttpSession  httpSession,
	@RequestBody ComMessage<?, ?> comMessage) throws Exception{

		MessageHandler mh = new MessageHandler();
		logger.debug(mh.serialize(comMessage));
		ComMessage<?, ?> msg = comMessage;
		msg.setEndTime(Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI));
		msg.setErrorCd("0");

		Extension ext = new Extension();
		ext.setMsgType(Extension.MSG_TYPE_RES);
		ext.setServiceCd("WS0032");
		msg.setExtension(ext);

		(new Thread(new Runnable(){
			   public void run(){
				   try {
						((ConfigurableApplicationContext) spring_boot).close();
						Thread.sleep(10*1000);
						exit();
				   } catch (InterruptedException e) {
				   }

				   System.exit(-9);
			   }
			})).start();

		return msg;
	}

	public void saveRepository(String name, String data) throws Exception {
		// TODO Auto-generated method stub

	}

	public void clearRepository(String name, String data) throws Exception {
		// TODO Auto-generated method stub

	}



}
