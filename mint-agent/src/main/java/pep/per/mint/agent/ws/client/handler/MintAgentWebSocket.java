package pep.per.mint.agent.ws.client.handler;

import pep.per.mint.common.data.basic.ComMessage;

public interface MintAgentWebSocket {

	public boolean isConnection();

	public void sendComMessage(ComMessage<?, ?> message);
}
