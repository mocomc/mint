package pep.per.mint.solution.mi.service;


import java.io.IOException;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mococo.mi.server.api.ClientSocket;
import com.mococo.mi.server.api.ConnectionManager;
import com.mococo.mi.server.api.MIManagerAPIException;
import com.mococo.mi.server.api.data.AgentMonitorObject;
import com.mococo.mi.server.api.data.BrokerMonitorObject;
import com.mococo.mi.server.api.data.HostMonitorObject;
import com.mococo.mi.server.api.data.UserObject;
import com.mococo.mi.server.api.handler.LoginHandler;
import com.mococo.mi.server.api.handler.SystemMonitorHandler;

import pep.per.mint.common.data.basic.Server;
import pep.per.mint.common.data.basic.miagent.MIAgentState;
import pep.per.mint.common.data.basic.miagent.MIRunnerState;
import pep.per.mint.common.util.Util;

@Service
public class MIMonitorService {

    Logger logger = LoggerFactory.getLogger(MIMonitorService.class);

    Map<String, Server> serverMap = new HashMap<String, Server>();
    Map<String, MIAgentState> agentStatusMap = new HashMap<String, MIAgentState>();
    Map<String, MIRunnerState> runnerStatusMap = new HashMap<String, MIRunnerState>();

    String managerIp;
    int managerPort = 8991;
    String userName;
    String password;

    public Server getServer(String serverCd) {
        return serverMap.get(serverCd);
    }

    public MIAgentState getAgentStatus(String agentName) {
        return agentStatusMap.get(agentName);
    }

    public MIRunnerState getBrokerStatus(String runnerName) {
        return runnerStatusMap.get(runnerName);
    }

    public void setConnectionInfo(String ip, int port, String userName, String password) {
        this.managerIp = ip;
        this.managerPort = port;
        this.userName = userName;
        this.password = password;
    }

//	public void monitor() throws UnknownHostException, IOException, MIManagerAPIException {
//		// TODO Auto-generated method stub
//		// MI Manager 접속을 위한 커넥션매니저 생성
//		ConnectionManager connMgr = ConnectionManager.getInstance();
//        
//		// 접속 정보 셋팅
//		connMgr.setConnectionInfo(managerIp, managerPort);
//
//		// 모든 작업 전에 로그인을 한다.
//		LoginHandler loginHandler = new LoginHandler(null, null);
//
//
//		UserObject userObj = loginHandler.checkUser(userName, password);
//
//		// 시스템 상태 정보를 얻기 위한 핸들러 생성
//		SystemMonitorHandler systemMonitorHandler = new SystemMonitorHandler(userObj.getUuid(), userObj.getName());
//
//		// 시스템 상태정보는 LIST로 저장된다. 호스트 -> 에어전트 -> 브로커(런너) 순서로
//		// 계층정(hierarchical)으로 구성되어 있다.
//		// 각 List의 루프를 탐색하면서 MonotorObject를 통해 상태 정보를 얻는다.
//		List systemList = systemMonitorHandler.getSystemMonitorInfo();
// 
//        
//        
//		// 호스트 내역 정보를 가져온다.
//		for (Object hostObject : systemList) {
//			if (hostObject == null)
//				continue;
//
//			// 호스트 내 에이전트 정보를 가져온다.
//			HostMonitorObject hmo = (HostMonitorObject) hostObject;
//			logger.debug("-----------------------------------------------");
//			logger.debug(Util.join("// HOST NAME : " + hmo.getHostName()));
//			logger.debug("-----------------------------------------------");
//
//			Server server = new Server();
//			server.setServerCd(hmo.getHostName());
//			serverMap.put(hmo.getHostName(), server);
//
//			List agentList = hmo.getAgentList();
//			for (Object agentObject : agentList) {
//				if (agentObject == null)
//					continue;
//
//				// 에이전트 내 브로커(런너)정보를 가져온다.
//				// 에이전트 이름과 상태정보를 가져올 수 있다.
//				AgentMonitorObject amo = (AgentMonitorObject) agentObject;
//				logger.debug("-----------------------------------------------");
//				logger.debug(Util.join("// AGENT NAME : " , amo.getAgentName(), " (Online : " ,amo.isBOnline() , ")"));
//				logger.debug("-----------------------------------------------");
//
//				MIAgentState agentState = new MIAgentState();
//				agentState.setGetDate(Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI));
//				agentState.setRegApp("mint-batch");
//				agentState.setRegDate(Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI));
//				String status = MIAgentState.STAT_NOT_READY;
//				String msg = "";
//				if(amo.isBOnline()){
//					status = MIAgentState.STAT_ONLINE;
//					msg = "online";
//				}else{
//					status = MIAgentState.STAT_OFFLINE;
//					msg = "offline";
//				}
//				agentState.setStatus(status);
//				agentStatusMap.put(Util.join(server.getServerCd() ,"@",amo.getAgentName()), agentState);
//
//
//				List brokerList = amo.getBrokerObject();
//				for (Object brokerObject : brokerList) {
//					if (brokerObject == null)
//						continue;
//					// 브로커(런너)의 이름과 상태정보를 가져올 수 있다.
//					BrokerMonitorObject bmo = (BrokerMonitorObject) brokerObject;
//					logger.debug("-----------------------------------------------");
//					logger.debug(Util.join("// RUNNER NAME : " , bmo.getBrokerName() , " (Online : " ,bmo.isBOnline() , ")"));
//					logger.debug("-----------------------------------------------");
//
//					MIRunnerState runnerState = new MIRunnerState();
//					runnerState.setGetDate(Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI));
//					runnerState.setRegApp("mint-batch");
//					runnerState.setRegDate(Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI));
//					status = MIRunnerState.STAT_NOT_READY;
//					msg = "";
//					if(bmo.isBOnline()){
//						status = MIRunnerState.STAT_ONLINE;
//						msg = "online";
//					}else{
//						status = MIRunnerState.STAT_OFFLINE;
//						msg = "offline";
//					}
//					runnerState.setStatus(status);
//					runnerStatusMap.put(Util.join(server.getServerCd() ,"@",amo.getAgentName(),"@", bmo.getBrokerName()), runnerState);
//				}
//			}
//		}
//		// TODO 각각의 에러처리를 한다.
//	}
    ConnectionManager connMgr = null;
    LoginHandler loginHandler = null;
    SystemMonitorHandler systemMonitorHandler = null;

    public void open() throws IOException, UnknownHostException, MIManagerAPIException {
        connMgr = ConnectionManager.getInstance();
        connMgr.setConnectionInfo(managerIp, managerPort);

        // 모든 작업 전에 로그인을 한다.
        loginHandler = new LoginHandler(null, null);
        UserObject userObj = loginHandler.checkUser(userName, password);
        systemMonitorHandler = new SystemMonitorHandler(userObj.getUuid(), userObj.getName());
    }

    public void monitor() throws UnknownHostException, IOException, MIManagerAPIException {

        try {

            // 시스템 상태정보는 LIST로 저장된다. 호스트 -> 에어전트 -> 브로커(런너) 순서로
            // 계층정(hierarchical)으로 구성되어 있다.
            // 각 List의 루프를 탐색하면서 MonotorObject를 통해 상태 정보를 얻는다.
            List systemList = systemMonitorHandler.getSystemMonitorInfo();

            // 호스트 내역 정보를 가져온다.
            for (Object hostObject : systemList) {
                if (hostObject == null) {
                    continue;
                }

                // 호스트 내 에이전트 정보를 가져온다.
                HostMonitorObject hmo = (HostMonitorObject) hostObject;
                logger.debug("-----------------------------------------------");
                logger.debug(Util.join("// HOST NAME : " + hmo.getHostName()));
                logger.debug("-----------------------------------------------");

                Server server = new Server();
                server.setServerCd(hmo.getHostName());
                serverMap.put(hmo.getHostName(), server);

                List agentList = hmo.getAgentList();
                for (Object agentObject : agentList) {
                    if (agentObject == null) {
                        continue;
                    }

                    // 에이전트 내 브로커(런너)정보를 가져온다.
                    // 에이전트 이름과 상태정보를 가져올 수 있다.
                    AgentMonitorObject amo = (AgentMonitorObject) agentObject;
                    logger.debug("-----------------------------------------------");
                    logger.debug(Util.join("// AGENT NAME : ", amo.getAgentName(), " (Online : ", amo.isBOnline(), ")"));
                    logger.debug("-----------------------------------------------");

                    MIAgentState agentState = new MIAgentState();
                    agentState.setGetDate(Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI));
                    agentState.setRegApp("mint-batch");
                    agentState.setRegDate(Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI));
                    String status = MIAgentState.STAT_NOT_READY;
                    String msg = "";
                    if (amo.isBOnline()) {
                        status = MIAgentState.STAT_ONLINE;
                        msg = "online";
                    } else {
                        status = MIAgentState.STAT_OFFLINE;
                        msg = "offline";
                    }
                    agentState.setStatus(status);
                    agentStatusMap.put(Util.join(server.getServerCd(), "@", amo.getAgentName()), agentState);

                    List brokerList = amo.getBrokerObject();
                    for (Object brokerObject : brokerList) {
                        if (brokerObject == null) {
                            continue;
                        }
                        // 브로커(런너)의 이름과 상태정보를 가져올 수 있다.
                        BrokerMonitorObject bmo = (BrokerMonitorObject) brokerObject;
                        logger.debug("-----------------------------------------------");
                        logger.debug(Util.join("// RUNNER NAME : ", bmo.getBrokerName(), " (Online : ", bmo.isBOnline(), ")"));
                        logger.debug("-----------------------------------------------");

                        MIRunnerState runnerState = new MIRunnerState();
                        runnerState.setGetDate(Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI));
                        runnerState.setRegApp("mint-batch");
                        runnerState.setRegDate(Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI));
                        status = MIRunnerState.STAT_NOT_READY;
                        msg = "";
                        if (bmo.isBOnline()) {
                            status = MIRunnerState.STAT_ONLINE;
                            msg = "online";
                        } else {
                            status = MIRunnerState.STAT_OFFLINE;
                            msg = "offline";
                        }
                        runnerState.setStatus(status);
                        runnerStatusMap.put(Util.join(server.getServerCd(), "@", amo.getAgentName(), "@", bmo.getBrokerName()), runnerState);
                    }
                }
            }
            // TODO 각각의 에러처리를 한다.
        } finally {
// 막아도 될까 .        	
//            if(connMgr != null) {
//                try{
//                    ClientSocket cs = connMgr.getConnection();
//                    logger.debug("987234028485-ConnectionManager.ClientSocket.close()-MIAgent 접속 매니저 리소스 해제함");
//                    if(cs != null) cs.close();
//                }catch(Exception e){
//                    logger.error("987234028485-ConnectionManager.ClientSocket.close()-MIAgent 접속 매니저 리소스 해제 예외 발생", e);
//                }
//            } 
        }
    }

    public Map<String, Server> getServerMap() {
        return serverMap;
    }

    public Map<String, MIAgentState> getAgentStateMap() {
        return agentStatusMap;
    }

    public Map<String, MIRunnerState> getRunnerStateMap() {
        return runnerStatusMap;
    }

}
