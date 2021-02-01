package pep.per.mint.database.mybatis.persistance;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import pep.per.mint.common.data.gateway.ControlMsg;

public interface ControlMsgMapper {
	 
	public List<ControlMsg> getControlMsgList(@Param("type") String type, @Param("cmd") int cmd, @Param("regId") String regId);
	
	public List<ControlMsg> getControlMsgResultList(@Param("id") String id);
	
	public void insertControlMsg(ControlMsg msg) throws Exception;
	
	public void insertControlMsgResult(ControlMsg msg) throws Exception;
	
}
