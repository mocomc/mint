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

import pep.per.mint.common.data.basic.NHTRLog;
import pep.per.mint.common.data.basic.NHTRLogDetail;
import pep.per.mint.common.data.basic.TRLinkInfo;
import pep.per.mint.common.data.basic.TRNodeInfo;



/**
 * @author noggenfogger
 *
 */
public interface NHMonitorMapper {

	public int getTrackingLogListTotalCount(Map arg) throws Exception;

	public List<NHTRLog> getTrackingLogList(Map arg) throws Exception;

	public List<NHTRLogDetail> getTrackingLogDetail(Map map) throws Exception;

	public List<NHTRLogDetail> getTrackingLogDetailError(Map map) throws Exception;

	public List<TRLinkInfo> getTrackingLinkInfo(Map arg) throws Exception;

	public List<TRNodeInfo> getTrackingNodeInfo(Map arg) throws Exception;

}
