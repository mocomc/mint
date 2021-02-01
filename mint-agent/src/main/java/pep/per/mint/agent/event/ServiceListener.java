package pep.per.mint.agent.event;

import pep.per.mint.agent.exception.AgentException;

public interface ServiceListener {


	public void requestService(ServiceEvent se) throws AgentException;
}
