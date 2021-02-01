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

import pep.per.mint.common.data.basic.GSTRLog;
import pep.per.mint.common.data.basic.GSTRLogDetail;



/**
 * @author noggenfogger
 *
 */
public interface GSSPMonitorMapper {

	public int getTrackingLogListTotalCount(Map arg) throws Exception;

	public List<GSTRLog> getTrackingLogList(Map arg) throws Exception;

	public List<GSTRLogDetail> getTrackingLogDetail(Map map) throws Exception;

}
