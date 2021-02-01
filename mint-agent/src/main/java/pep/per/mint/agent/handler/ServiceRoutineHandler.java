package pep.per.mint.agent.handler;

import pep.per.mint.agent.exception.AgentException;
import pep.per.mint.common.data.basic.ComMessage;

public interface ServiceRoutineHandler {

	public ComMessage<?,?> route(ComMessage<?,?> request) throws AgentException ;
}
