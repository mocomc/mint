/**
 * Copyright 2018 mocomsys Inc. All Rights Reserved.
 */
package pep.per.mint.database.service.op;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pep.per.mint.common.util.Util;
import pep.per.mint.database.mapper.op.LocalCmdLogMapper;

/**
 * <pre>
 * pep.per.mint.database.service.op
 * LocalCmdLogService.java
 * </pre>
 * @author whoana
 * @date Dec 3, 2019
 */
@Service
public class LocalCmdLogService {

	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	LocalCmdLogMapper localCmdLogMapper;
	 
 
	/**
	 * @param logs
	 */
	public void logNetstat(String agentId, List<Map<String, String>> logs) throws Exception{
		
		for (Map<String, String> netstat : logs) {
			netstat.put("agentId", agentId);
			if(localCmdLogMapper.exist(netstat) > 0) {
				localCmdLogMapper.updateNetstatLog(netstat);
				logger.debug("netstatUpdate :" + Util.toJSONString(netstat));
			}else {
				localCmdLogMapper.insertNetstatLog(netstat);
				logger.debug("netstatInsert :" + Util.toJSONString(netstat));
			}
		}
		
	}
	
	/*
	public void logNetstat(String agentId, List<Map<String, String>> logs) throws Exception{
		 
		List<Map<String, String>> serverList = localCmdLogMapper.getServerIds();
		if(serverList != null) {
			
			String date = Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI); 
			
			for (Map<String, String> map : serverList) {
				
				String serverId = map.get("serverId");
				String serverIp = map.get("ip"); 
				
				logger.debug("-------------------------------------------------------");
				logger.debug("netstatCheck:("+serverId+","+serverIp + ")");
				logger.debug("-------------------------------------------------------");
				
				Map<String, String> netstat = null;
				
				if(logs != null) {
					for(Map<String, String> log : logs) {
						if(serverIp.equals(log.get("localAddress")) || serverIp.equals(log.get("foreignAddress"))){
							netstat = log;
							break;
						}
					}
				}
				
				if(netstat == null) {
					netstat = new HashMap<String,String>();
					netstat.put("protocol", 	 "TCP");
					netstat.put("recvQ", 		 "0");
					netstat.put("sendQ", 		 "0");
					netstat.put("localAddress",  "NONE");
					netstat.put("foreignAddress","NONE");
					netstat.put("state", 		 "NONE");
				} 				
				netstat.put("agentId", agentId);
				netstat.put("serverId", serverId);
				
				if(localCmdLogMapper.exist(netstat) > 0) {
					netstat.put("modDate", date);
					localCmdLogMapper.updateNetstatLog(netstat);
					logger.debug("netstatUpdate :" + Util.toJSONString(netstat));
				}else {
					netstat.put("regDate", date);
					netstat.put("modDate", date);
					localCmdLogMapper.insertNetstatLog(netstat);
					logger.debug("netstatInsert :" + Util.toJSONString(netstat));
				}
				if(logs != null) logs.remove(netstat);
			}
		}
	}
	*/

	/**
	 * @return
	 * @throws Exception 
	 */
	public List<Map<String,String>> getNetstatCheckList(String agentId, String agentCd) throws Exception {
		return localCmdLogMapper.getServerIds(agentId, agentCd);
	}
	 
}
