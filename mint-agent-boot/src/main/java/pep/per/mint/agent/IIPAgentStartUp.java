package pep.per.mint.agent;

import java.io.File;

import pep.per.mint.agent.util.RuntimeJarLoader;


public class IIPAgentStartUp {

	/**
	 *  프로세스 구동 Home 지정.
	 */
	private static String IIPAGENT_HOME = "";

	static{
		if(System.getProperty("IIPAGENT_HOME") == null){
			IIPAGENT_HOME ="";
		}else{
			IIPAGENT_HOME =System.getProperty("IIPAGENT_HOME");
		}
	}


	public static void main(String[] args) throws Exception {
		IIPAgentStartUp iip = new IIPAgentStartUp();
		try {
			iip.strart(args);
		} catch (Throwable e) {
			System.err.println("// IIP AGENT STARTUP FAIL");
			e.printStackTrace();
		}
	}

	private void strart(String[] args) throws Throwable {

		if("".equalsIgnoreCase(IIPAGENT_HOME)){
			System.err.println("$IIPAGENT_HOME System.property not defined.");
			return ;
		}

		String confPath =  IIPAGENT_HOME + File.separatorChar ;
		RuntimeJarLoader.addClassPath(confPath);

		String classesPath =  IIPAGENT_HOME + File.separatorChar  + "classes";
		RuntimeJarLoader.addClassPath(classesPath);

		String libPath =  IIPAGENT_HOME + File.separatorChar +"lib"+ File.separatorChar ;
		RuntimeJarLoader.loadJarIndDir(libPath);

		AgentLauncher agentLauncher = new AgentLauncher();
		agentLauncher.init(args);
	}




}
