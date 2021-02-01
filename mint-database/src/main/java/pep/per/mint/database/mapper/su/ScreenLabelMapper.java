/*
 * Copyright 2013 ~ 2014 Mocomsys(dhkim, Solution TF), Inc.  All Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 * Please contact Mocomsys, Inc., NURITKUM SQUARE R&D TOWER, 11F DMC 1605, 
 * Sangam-Dong, Mapo-Gu, Seoul, 121-795 Korea or visit mocomsys.com 
 * if you need additional information or have any questions.
*/
package pep.per.mint.database.mapper.su;


import pep.per.mint.common.data.basic.ScreenLabel;

import java.util.List;
import java.util.Map;


/**
 * @author noggenfogger
 *
 */
public interface ScreenLabelMapper {
	
	public List<ScreenLabel> getScreenLabelList(Map params) throws Exception;
	
	public ScreenLabel getScreenLabelDetail(Map params) throws Exception;

	public int insertScreenLabel(ScreenLabel service) throws Exception;
	
	public int updateScreenLabel(ScreenLabel service) throws  Exception;

	public int deleteScreenLabel(Map params) throws Exception;
	
	public List<ScreenLabel> existScreenLabel(Map params) throws Exception;

}
