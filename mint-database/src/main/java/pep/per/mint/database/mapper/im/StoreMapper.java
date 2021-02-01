/*
 * Copyright 2013 ~ 2014 Mocomsys(dhkim, Solution TF), Inc.  All Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 * Please contact Mocomsys, Inc., NURITKUM SQUARE R&D TOWER, 11F DMC 1605, 
 * Sangam-Dong, Mapo-Gu, Seoul, 121-795 Korea or visit mocomsys.com 
 * if you need additional information or have any questions.
*/
package pep.per.mint.database.mapper.im;


import pep.per.mint.common.data.basic.Store;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;


/**
 * @author noggenfogger
 *
 */
public interface StoreMapper {
	
	public List<Store> getStoreList(Map params) throws Exception;

	public int insertStore(@Param("storeObj") Map service) throws Exception;
	
	public int updateStore(@Param("storeObj") Map service) throws  Exception;

	public int deleteStore(@Param("storeObj") Map service) throws Exception;

}
