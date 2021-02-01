package pep.per.mint.agent.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;


import pep.per.mint.agent.controller.AgentController;

@Controller
public class AgentCheckScheduler {
	@Autowired
	private AgentController agentController;

//	@Scheduled(fixedDelay=1000)
	@Scheduled(cron = "${cron.expression}")
	public void run(){
		agentController.consumeVersionLog();
	}
}
