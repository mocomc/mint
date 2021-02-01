package pep.per.mint.agent.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;


import pep.per.mint.agent.controller.AgentController;


@Controller
public class NetstatCheckScheduler {
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	public static String USE_YES = "Y";
	public static String USE_NO  = "N"; 
	
	@Value("${iip.agent.use.module.netstat:N}")  
	public String useNetstat = USE_NO;
	
	
	@Autowired
	private AgentController agentController;
	
	@Scheduled(fixedDelay=1000)
	public void run(){ 
		
		//===========================================================================
		//2019.11 추가 기능 
		//---------------------------------------------------------------------------
		//content : zeta 운영서버의 대외 채널 OS 레벨 네트워크 상태 체크 
		//request : 감정원 추가 요청 
		//date    : 2019.11
		//control : agent.properties 의 iip.agent.use.module.netstat 값으로 사용여부 제어 
		//---------------------------------------------------------------------------
		if(USE_YES.equalsIgnoreCase(useNetstat)) {
			
			agentController.getNetstatCheckList();
			
			agentController.consumeNetstatLog();
		}
	}
}
