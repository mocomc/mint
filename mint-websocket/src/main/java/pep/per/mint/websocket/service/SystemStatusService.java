/**
 * Copyright 2018 Mocomsys Inc.  All Rights Reserved.
 */
package pep.per.mint.websocket.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

import pep.per.mint.common.data.basic.status.SystemStatus;

/**
 * <pre>
 * pep.per.mint.websocket.service
 * SystemStatusService.java
 * </pre>
 * @author whoana
 * @date 2018. 8. 16.
 */
@Service
public class SystemStatusService {

	Map<String, SystemStatus> systemStatusMap = new ConcurrentHashMap<String,SystemStatus>();

	public void updateStatus(SystemStatus status) {
		systemStatusMap.put(status.getSystemId(), status);
	}

	public Map<String, SystemStatus> getStatusMap() {
		return systemStatusMap;
	}

	/**
	 * @param systemId
	 * @return
	 */
	public SystemStatus getStatus(String systemId) {
		return systemStatusMap.get(systemId);
	}

}
