package pep.per.mint.agent.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;


import pep.per.mint.agent.controller.AgentController;

@Controller
public class ResourceCheckScheduler {
	@Autowired
	private AgentController agentController;

	@Scheduled(fixedDelay=1000)
	public void run(){
		agentController.consumeCpuUsageLog();
		agentController.consumeMemoryUsageLog();
		agentController.consumeDiskUsageLog();
		agentController.consumeProcessStatusLog();
	}
}
