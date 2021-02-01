package pep.per.mint.agent.event;

import org.springframework.web.socket.WebSocketSession;

import pep.per.mint.common.data.basic.ComMessage;

public class ServiceEvent {

	String id = "";
	String name ="";
	String type = "";
	String serviceCd = "";
	ComMessage<?,?> request= null;
	WebSocketSession session = null;


	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public ComMessage getRequest() {
		return request;
	}
	public void setRequest(ComMessage request) {
		this.request = request;
	}
	public WebSocketSession getSession() {
		return session;
	}
	public void setSession(WebSocketSession session) {
		this.session = session;
	}
	public String getServiceCd() {
		return serviceCd;
	}
	public void setServiceCd(String serviceCd) {
		this.serviceCd = serviceCd;
	}

}
