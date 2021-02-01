/*
 * Copyright 2013 ~ 2014 Mocomsys(dhkim, Solution TF), Inc.  All Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 * Please contact Mocomsys, Inc., NURITKUM SQUARE R&D TOWER, 11F DMC 1605,
 * Sangam-Dong, Mapo-Gu, Seoul, 121-795 Korea or visit mocomsys.com
 * if you need additional information or have any questions.
*/
package pep.per.mint.database.mapper.op;


import java.util.List;
import java.util.Map;

import pep.per.mint.common.data.basic.TRLinkInfo;
import pep.per.mint.common.data.basic.TRLog;
import pep.per.mint.common.data.basic.TRLogDetail;
import pep.per.mint.common.data.basic.TRNodeInfo;
import pep.per.mint.common.data.basic.monitor.TrackingSystemInfo;



/**
 * @author noggenfogger
 *
 */
public interface VerMonitorMapper {

	public List<TRLog> getTrackingLogList(Map arg) throws Exception;

	public List<TRLog> getTrackingLogListType0(Map arg) throws Exception;

	public List<TRLog> getTrackingLogListType1(Map arg) throws Exception;

	public List<TRLog> getTrackingLogListType2(Map arg) throws Exception;

	public List<TRLogDetail> getTrackingLogDetail(Map map) throws Exception;

	public List<TrackingSystemInfo> getTrackingSystemInfo(Map map) throws Exception;

	public List<TRNodeInfo> getTrackingNodeInfo(Map arg) throws Exception;

	public List<TRLinkInfo> getTrackingLinkInfo(Map arg) throws Exception;

	public int getTrackingLogListTotalCount(Map arg) throws Exception;

}
