package pep.per.mint.websocket.event;

import org.springframework.web.socket.WebSocketSession;

import pep.per.mint.common.data.basic.ComMessage;

/**
 *
 * <pre>
 * pep.per.mint.websocket.event
 * ServiceEvent.java
 * </pre>
 *
 * @author whoana
 * @date 2018. 7. 2.
 * @param <K>
 * @param <M>
 */
public class ServiceEvent<K, M> {

	public static final int TYPE_FRONT = 1;

	public static final int TYPE_FRONT_PUSH = 2;

	public static final int TYPE_AGENT = 3;

	String name;

	String id;

	int type = TYPE_FRONT;

	ComMessage<K, M> request;

	WebSocketSession session;
 

	public ServiceEvent(ComMessage<K, M> request, WebSocketSession session, int type) {
		super();
		this.request = request;
		this.session = session;
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public ComMessage<K, M> getRequest() {
		return request;
	}

	public void setRequest(ComMessage<K, M> request) {
		this.request = request;
	}

	public WebSocketSession getSession() {
		return session;
	}

	public void setSession(WebSocketSession session) {
		this.session = session;
	}

}
