package pep.per.mint.database.service.op;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pep.per.mint.common.data.basic.TrackingLog;
import pep.per.mint.common.data.basic.TrackingLogDetail;
import pep.per.mint.database.mapper.op.TrackingMapper;

/**
 * 트래킹 로그 관련 서비스
 * @author noggenfogger
 *
 */

@Service
public class TrackingService {

	@Autowired
	TrackingMapper trackingMapper;

	/**
	 * 트레킹 리스트 조회 - 전체 카운트
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public int getTrackingLogListTotalCount(Map params) throws Exception {
		int count = trackingMapper.getTrackingLogListTotalCount(params);
		return count;
	}

	/**
	 * 트례킹 리스트 조회
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public List<TrackingLog> getTrackingLogList(Map params) throws Exception{

		List<TrackingLog> trackingLogList = null;

		trackingLogList = trackingMapper.getTrackingLogList(params);

		return trackingLogList;
	}

	/**
	 * 트레킹 상세 조회
	 * @param arg
	 * @return
	 * @throws Exception
	 */
	public List<TrackingLogDetail> getTrackingLogDetail(Map arg) throws Exception {

		List<TrackingLogDetail> trackingLogDetailList = trackingMapper.getTrackingLogDetail(arg);
		return trackingLogDetailList;
	}
}
