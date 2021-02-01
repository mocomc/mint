/*
 * Copyright 2013 ~ 2014 Mocomsys(dhkim, Solution TF), Inc.  All Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 * Please contact Mocomsys, Inc., NURITKUM SQUARE R&D TOWER, 11F DMC 1605,
 * Sangam-Dong, Mapo-Gu, Seoul, 121-795 Korea or visit mocomsys.com
 * if you need additional information or have any questions.
*/
package pep.per.mint.database.mapper.su;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import pep.per.mint.common.data.basic.InterfaceAdditionalAttribute;



public interface InterfaceAttrMapper {

	public List<InterfaceAdditionalAttribute> getAttributeList(Map params) throws Exception;

	public int insertAttribute(InterfaceAdditionalAttribute service) throws Exception;

	public int updateAttribute(InterfaceAdditionalAttribute service) throws  Exception;

	public int deleteAttribute(Map params) throws Exception;

	public List<InterfaceAdditionalAttribute> existAttribute(Map params) throws Exception;

	public List<String> usedAttribute(@Param("attrId")String attrId) throws Exception;

}
