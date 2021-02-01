package pep.per.mint.database.mapper.op;

import java.util.List;
import java.util.Map;

import pep.per.mint.common.data.basic.MessageLog;
import pep.per.mint.common.data.basic.TRLogDetail;

public interface KABMonitorMapper {

	public int getMessageLogListTotalCount(Map arg) throws Exception;

	public List<MessageLog> getMessageLogList(Map arg) throws Exception;

	public MessageLog getMessageLogDetail(Map map) throws Exception;

	public List<TRLogDetail> getTrackingLogDetail(Map map) throws Exception;

	public Map getTrackingInterfaceInfo(Map arg) throws Exception;

}
