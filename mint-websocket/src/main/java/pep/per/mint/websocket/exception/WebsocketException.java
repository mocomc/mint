/**
 * Copyright 2018 Mocomsys Inc.  All Rights Reserved.
 */
package pep.per.mint.websocket.exception;

/**
 * <pre>
 * pep.per.mint.websocket.exception
 * WebsocketException.java
 * </pre>
 * @author whoana
 * @date 2018. 7. 3.
 */
public class WebsocketException extends Exception{


	public WebsocketException(Exception e) {
		super(e);
	}


	public WebsocketException(String msg) {
		super(msg);
	}

	/**
	 *
	 */
	public WebsocketException(String msg, Exception e) {
		super(msg, e);
	}

}
