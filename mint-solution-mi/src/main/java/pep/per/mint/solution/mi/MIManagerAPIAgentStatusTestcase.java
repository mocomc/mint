package pep.per.mint.solution.mi;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.List;

import com.mococo.mi.server.api.ConnectionManager;
import com.mococo.mi.server.api.MIManagerAPIException;
import com.mococo.mi.server.api.data.AgentMonitorObject;
import com.mococo.mi.server.api.data.BrokerMonitorObject;
import com.mococo.mi.server.api.data.HostMonitorObject;
import com.mococo.mi.server.api.data.UserObject;
import com.mococo.mi.server.api.handler.LoginHandler;
import com.mococo.mi.server.api.handler.SystemMonitorHandler;

/**
 * MI 시스템 상태정보를 가져오는 테스트케이스
 *
 */
public class MIManagerAPIAgentStatusTestcase {

	/**
	 * 메인 생성자
	 */
	public static void main(String[] args) {
		
		MIManagerAPIAgentStatusTestcase api = new MIManagerAPIAgentStatusTestcase();
        while(true){
            api.test();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
        
        
	}

	/**
	 * 테스트 진행
	 * 사내 구성위치는 다음과 같습니다.
	 * MI Manager : ip-10.10.1.145 port-8991
	 * MI Workbench : 10.10.1.145에 설치되어 있는 워크벤치 사용
	 * DTT DB :
	 */
	public void test() {
        
		System.setProperty("TCP_ENCODE", "true");
		
		
		// MI Manager 접속을 위한 커넥션매니저 생성
		ConnectionManager connMgr = ConnectionManager.getInstance();
		// 접속 정보 셋팅
		connMgr.setConnectionInfo("10.10.1.11", 8991);

		// 모든 작업 전에 로그인을 한다.
		LoginHandler loginHandler = new LoginHandler(null, null);
		String strUserName = "USER001";
		String strUserPw = "USER001$";

		try {
			UserObject userObj = loginHandler.checkUser(strUserName, strUserPw);

			// 시스템 상태 정보를 얻기 위한 핸들러 생성
			SystemMonitorHandler systemMonitorHandler = new SystemMonitorHandler(userObj.getUuid(), userObj.getName());
			
			System.out.println("sender info:" + systemMonitorHandler.m_strSenderName);
			

			// 시스템 상태정보는 LIST로 저장된다. 호스트 -> 에어전트 -> 브로커(런너) 순서로
			// 계층정(hierarchical)으로 구성되어 있다.
			// 각 List의 루프를 탐색하면서 MonotorObject를 통해 상태 정보를 얻는다.
			List systemList = systemMonitorHandler.getSystemMonitorInfo();

			// 호스트 내역 정보를 가져온다.
			for (Object hostObject : systemList) {
				if (hostObject == null)
					continue;

				// 호스트 내 에이전트 정보를 가져온다.
				HostMonitorObject hmo = (HostMonitorObject) hostObject;
				System.out.println("== HOST NAME : " + hmo.getHostName());
				List agentList = hmo.getAgentList();
				for (Object agentObject : agentList) {
					if (agentObject == null)
						continue;

					// 에이전트 내 브로커(런너)정보를 가져온다.
					// 에이전트 이름과 상태정보를 가져올 수 있다.
					AgentMonitorObject amo = (AgentMonitorObject) agentObject;
					System.out
							.println("===+ AGENT NAME : " + amo.getAgentName() + " (Online : " + amo.isBOnline() + ")");

					List brokerList = amo.getBrokerObject();
					for (Object brokerObject : brokerList) {
						if (brokerObject == null)
							continue;
						// 브로커(런너)의 이름과 상태정보를 가져올 수 있다.
						BrokerMonitorObject bmo = (BrokerMonitorObject) brokerObject;
						System.out.println(
								"=====+ RUNNER NAME : " + bmo.getBrokerName() + " (Online : " + bmo.isBOnline() + ")");
					}
				}
			}
			// TODO 각각의 에러처리를 한다.
		} catch (UnknownHostException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		} catch (MIManagerAPIException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}
}
