package pep.per.mint.database.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pep.per.mint.common.data.gateway.ControlMsg;
import pep.per.mint.database.mybatis.persistance.ControlMsgMapper;

@Service
public class ControlMsgService {
	
	final static Logger logger = LoggerFactory.getLogger(ControlMsgService.class);
	
	@Autowired
	ControlMsgMapper controlMsgMapper;
	
	public List<ControlMsg> getControlMsgList(String type, int cmd, String regId) throws Exception{
		List<ControlMsg> list = controlMsgMapper.getControlMsgList(type, cmd, regId);
		return list;
	}
	
	public List<ControlMsg> getControlMsgResultList(String id) throws Exception{
		List<ControlMsg> list = controlMsgMapper.getControlMsgResultList(id);
		return list;
	}
	
	public void insertControlMsg(ControlMsg msg) throws Exception{
		controlMsgMapper.insertControlMsg(msg);
	}
	
	public void insertControlMsgResult(ControlMsg msg) throws Exception{
		controlMsgMapper.insertControlMsgResult(msg);
	}
	
}
