/**
 * Copyright 2018 Mocomsys Inc.  All Rights Reserved.
 */
package pep.per.mint.websocket.handler.routing;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * <pre>
 * pep.per.mint.websocket.handler.routing
 * Router.java
 * </pre>
 * @author whoana
 * @date 2018. 8. 10.
 */
public class Router {

	String serviceCd;

	Method method;

	Object owner;

	int priority = 0;

	boolean runAfterException = true;

	public String getServiceCd() {
		return serviceCd;
	}

	public void setServiceCd(String serviceCd) {
		this.serviceCd = serviceCd;
	}

	public Method getMethod() {
		return method;
	}

	public void setMethod(Method method) {
		this.method = method;
	}

	public Object getOwner() {
		return owner;
	}

	public void setOwner(Object owner) {
		this.owner = owner;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public boolean isRunAfterException() {
		return runAfterException;
	}

	public void setRunAfterException(boolean runAfterException) {
		this.runAfterException = runAfterException;
	}

	/**
	 * @param params
	 * @throws InvocationTargetException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public void route(Object[] params) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		method.invoke(owner, params);
	}



}
