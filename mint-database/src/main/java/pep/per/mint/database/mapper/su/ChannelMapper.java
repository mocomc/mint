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

import pep.per.mint.common.data.basic.Channel;




public interface ChannelMapper {

	public List<Channel> getChannelList(Map params) throws Exception;

	public int insertChannel(Channel channel) throws Exception;

	public int updateChannel(Channel channel) throws  Exception;

	public int deleteChannel(Map params) throws Exception;

	public List<Channel> existChannel(Map params) throws Exception;

	public List<String> usedChannel(@Param("channelId")String channelId) throws Exception;
	
}
