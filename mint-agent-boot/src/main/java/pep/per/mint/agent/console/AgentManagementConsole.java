package pep.per.mint.agent.console;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

import pep.per.mint.agent.AgentProperties;
import pep.per.mint.agent.common.ComMessage;
import pep.per.mint.agent.common.Extension;
import pep.per.mint.agent.common.MessageHandler;
import pep.per.mint.agent.util.RuntimeJarLoader;
import pep.per.mint.agent.util.Util;

public class AgentManagementConsole implements Runnable {


	final static String INFO =  "INFO";
	final static String DEBUG = "DEBUG";
	final static String ERROR = "ERROR";

	String cmd = null;
	String charset = "UTF-8";
	String ip = "127.0.0.1";
	int port = 4885;
	String contextpath ="/application";

	String uri = "http://"+ ip +":" + port + contextpath;

	static AgentProperties properties = new AgentProperties();

	String passwd = "";
	String agentNm = "";

	public Logger logger = null;

	static {
		try {
			properties.load(ClassLoader.getSystemResourceAsStream("config/agent.properties"));
			System.setProperty("IIP_LOG_HOME", properties.readProperty("iip.agent.home",true));
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	public AgentManagementConsole() {
		try{
					ip =  properties.getProperty("iip.agent.console.ip","127.0.0.1");
					port = Integer.parseInt(properties.getProperty("iip.agent.console.port", "4885"));
					contextpath = properties.getProperty("iip.agent.console.path","/application");
			//
			//		agentNm = properties.getProperty("iip.agent.nm","");
			//		passwd = properties.getProperty("iip.agent.passwd","");
			//
					uri = "http://"+ ip +":" + port + contextpath;

			String confPath =  properties.readProperty("iip.agent.home",true) + File.separatorChar ;
			RuntimeJarLoader.addClassPath(confPath);

			String libPath =  properties.readProperty("iip.agent.home",true) + File.separatorChar +"lib"+ File.separatorChar ;
			RuntimeJarLoader.loadJarIndDir(libPath);

			logger = LoggerFactory.getLogger(AgentManagementConsole.class);
		}catch(Throwable e){
			logger.warn(e.getMessage(),e);
		}


	}

	private String parseCmd(String[] args) throws Exception {
		//help();
		System.out.println("-----------------------------------------");
		if(args.length == 0) {
			help();
			String msg = "AgentManagementConsole has no args.";
			throw new Exception(msg);
		}


		for(int i = 0 ; i < args.length ; i ++){
			if("-cmd".equalsIgnoreCase(args[i])){
				try{
					cmd = args[i + 1];
					break;
				}catch(ArrayIndexOutOfBoundsException e){
					help();
					String msg = "AgentManagementConsole has no command value.";
					throw new Exception(msg);
				}
			}
		}

		if(Util.isEmpty(cmd)){
			help();
			String msg = "AgentManagementConsole has no command value.";
			throw new Exception(msg);
		}

		String checkCmd = Util.join(
				"(CHK)|" ,
				"(EXT)");
		if(!cmd.matches(checkCmd)){
			help();
			String msg = "입령한 명령값이 올바르지 않습니다.";
			throw new Exception(msg);
		}

		System.out.println(Util.join("input command:", cmd));
		return cmd;
	}



	public String getCmd() {
		return cmd;
	}

	public static void main(String[] args){
		AgentManagementConsole console = new AgentManagementConsole();;
		try {

			String cmdJsonString = console.parseCmd(args);
			console.writeCommand(cmdJsonString);
		} catch (ConnectException e){
			System.out.println(Util.join("IIPAgent에 접속할 수 없습니다. iip.agent.console.port[",console.getPort(),"] 가 올바르지 않거나 IIPAgent가 기동 상태인지 확인하십시요."));
			console.warn(Util.join("IIPAgent에 접속할 수 없습니다. iip.agent.console.port[",console.getPort(),"] 가 올바르지 않거나 IIPAgent가 기동 상태인지 확인하십시요."),e);
		} catch (UnknownHostException e) {
			console.warn (e.getMessage(),e);
		} catch (IOException e) {
			console.warn (e.getMessage(),e);
		} catch (Exception e) {
			console.warn (e.getMessage(),e);
		} finally{
			console.close();
		}
	}


	private void warn(String message, Exception e) {
		logger.warn(message, e);
	}

	private int getPort() {
		return port;
	}

	private  void close() {

	}

	private void writeCommand(String cmd) throws UnsupportedEncodingException, IOException {

		RestTemplate restTemplate = new RestTemplate();
		ComMessage  result = new ComMessage () ;

		MessageHandler mh = new MessageHandler();

		if("CHK".equalsIgnoreCase(cmd)){
			uri = Util.join(uri,"/alive");
			ComMessage request = new ComMessage () ;
			request.setId(UUID.randomUUID().toString());
			request.setStartTime(Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI));
			request.setUserId(agentNm);
			request.setCheckSession(false);


			HashMap<String, String> map = new HashMap<String, String>();
			map.put("password", passwd);

			request.setRequestObject(map);

			Extension ext = new Extension();
			ext.setMsgType(Extension.MSG_TYPE_REQ);
			ext.setServiceCd("WS0033");
			request.setExtension(ext);


			ComMessage response = restTemplate.postForObject(uri, request, request.getClass());

			System.out.println(mh.serialize(response));



		}else if("EXT".equalsIgnoreCase(cmd)){
			uri = Util.join(uri,"/shutdown");
			ComMessage request = new ComMessage () ;
			request.setId(UUID.randomUUID().toString());
			request.setStartTime(Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI));
			request.setUserId(agentNm);
			request.setCheckSession(false);


			HashMap<String, String> map = new HashMap<String, String>();
			map.put("password", passwd);

			request.setRequestObject(map);

			Extension ext = new Extension();
			ext.setMsgType(Extension.MSG_TYPE_REQ);
			ext.setServiceCd("WS0032");
			request.setExtension(ext);

			ComMessage response = restTemplate.postForObject(uri, request, request.getClass());

			System.out.println(mh.serialize(response));

		}

	}

	private static void help() {
		System.out.println("*usage:");
		System.out.println("\t-cmd [명령]");
		System.out.println("*명령값:");
		System.out.println("\tCHK-얼라이브체크");
		System.out.println("\tEXT-에이전트종료(JVM종료)");
	}
	//
	public void run() {
	}

}
