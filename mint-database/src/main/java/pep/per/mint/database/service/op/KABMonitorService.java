package pep.per.mint.database.service.op;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pep.per.mint.common.data.basic.MessageLog;
import pep.per.mint.common.data.basic.TRLogDetail;
import pep.per.mint.common.util.Util;
import pep.per.mint.database.mapper.op.KABMonitorMapper;

/**
 * 트래킹 로그 관련 서비스
 * @author noggenfogger
 *
 */

@Service
public class KABMonitorService {

	@Autowired
	KABMonitorMapper monitorMapper;

	/**
	 * 온라인전문이력 리스트 조회 - 전체 카운트
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public int getMessageLogListTotalCount(Map params) throws Exception {
		int count = monitorMapper.getMessageLogListTotalCount(params);
		return count;
	}

	/**
	 * 온라인전문이력 리스트 조회
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<MessageLog> getMessageLogList(Map params) throws Exception{

		List<MessageLog> messageLogList = monitorMapper.getMessageLogList(params);
		return messageLogList;
	}

	/**
	 * 온라인전문이력 상세 조회
	 * @param arg
	 * @return
	 * @throws Exception
	 */
	public MessageLog getMessageLogDetail(Map arg) throws Exception {

		MessageLog messageLogDetail = monitorMapper.getMessageLogDetail(arg);
		return messageLogDetail;
	}

	/**
	 * 트레킹 상세 조회
	 * @param arg
	 * @return
	 * @throws Exception
	 */
	public List<TRLogDetail> getTrackingLogDetail(Map arg) throws Exception {

		//--------------------------------------------------------------------
		// 트레킹 정보의 GROUP_ID, INTF_ID 를 조회한다
		//--------------------------------------------------------------------
		{
			String groupId = "";
			String intfId  = "";

			Map trackingInterfaceInfo = monitorMapper.getTrackingInterfaceInfo(arg);
			if( !Util.isEmpty(trackingInterfaceInfo) ) {
				groupId = Util.isEmpty(trackingInterfaceInfo.get("GROUP_ID")) ? "" : trackingInterfaceInfo.get("GROUP_ID").toString();
				intfId  = Util.isEmpty(trackingInterfaceInfo.get("INTF_ID")) ? "" : trackingInterfaceInfo.get("INTF_ID").toString();
			}

			arg.put("groupId", groupId);
			arg.put("intfId", intfId);
		}

		List<TRLogDetail> trackingLogDetailList = monitorMapper.getTrackingLogDetail(arg);
		return trackingLogDetailList;
	}

}
