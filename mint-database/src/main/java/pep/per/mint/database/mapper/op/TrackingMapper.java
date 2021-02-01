package pep.per.mint.database.mapper.op;


import java.util.List;
import java.util.Map;

import pep.per.mint.common.data.basic.TrackingLog;
import pep.per.mint.common.data.basic.TrackingLogDetail;

/**
 * @author noggenfogger
 *
 */
public interface TrackingMapper {

	public int getTrackingLogListTotalCount(Map arg) throws Exception;

	public List<TrackingLog> getTrackingLogList(Map arg) throws Exception;

	public List<TrackingLogDetail> getTrackingLogDetail(Map map) throws Exception;

}
