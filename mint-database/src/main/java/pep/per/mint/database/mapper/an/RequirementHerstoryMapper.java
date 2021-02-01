/*
 * Copyright 2013 ~ 2014 Mocomsys(dhkim, Solution TF), Inc.  All Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 * Please contact Mocomsys, Inc., NURITKUM SQUARE R&D TOWER, 11F DMC 1605,
 * Sangam-Dong, Mapo-Gu, Seoul, 121-795 Korea or visit mocomsys.com
 * if you need additional information or have any questions.
*/
package pep.per.mint.database.mapper.an;

import org.apache.ibatis.annotations.Param;
import pep.per.mint.common.data.basic.*;
import pep.per.mint.common.data.basic.System;
import pep.per.mint.common.data.basic.herstory.ColumnHerstory;
import pep.per.mint.common.data.basic.herstory.InterfaceColumnHerstory;
import pep.per.mint.common.data.basic.herstory.RequirementColumnHerstory;
import pep.per.mint.common.data.basic.herstory.RequirementColumnHistory;

import java.util.List;
import java.util.Map;


/**
 * @author Solution TF
 *
 */
public interface RequirementHerstoryMapper {


	/**
	 * @param columnHerstory
	 * @return
	 */
	public int insertRequirementColumnHerstory(ColumnHerstory columnHerstory)throws Exception;

	/**
	 * 현재 요건의의 최고 버전을 리턴한다. 없으면 0을 리턴한다.
	 * @param requirementId
	 * @return
	 */
	public int selectCurrentHerstoryVersion(@Param("requirementId") String requirementId) throws Exception;

	/**
	 *
	 * @param requirementId
	 * @param columnId
	 * @return
	 * @throws Exception
	 */
	public ColumnHerstory getLastRequirementColumnHerstory(@Param("requirementId")  String requirementId, @Param("columnId")String columnId) throws Exception;

	/**
	 *
	 * @param requirementId
	 * @param modDate
	 * @param modId
	 * @return
	 */
	public int insertRequirementCommentHerstory(@Param("requirementId") String requirementId, @Param("version") String version, @Param("modDate") String modDate, @Param("modId") String modId);

	/**
	 *
	 * @param requirementId
	 * @param modDate
	 * @param modId
	 * @return
	 */
	public int insertRequirementAttatchFileHerstory(@Param("requirementId") String requirementId, @Param("version") String version, @Param("modDate") String modDate, @Param("modId") String modId);


	/**
	 *
	 * @param requirementId
	 * @param interfaceId
	 * @param columnId
	 * @return
	 * @throws Exception
	 */
	public ColumnHerstory getLastInterfaceColumnHerstory(@Param("requirementId")  String requirementId,@Param("interfaceId")  String interfaceId, @Param("columnId")String columnId) throws Exception;




	/**
	 * @param columnHerstory
	 * @return
	 */
	public int insertInterfaceColumnHerstory(ColumnHerstory columnHerstory);



	/**
	 *
	 * @param interfaceId
	 * @param modDate
	 * @param modId
	 * @return
	 */
	public int insertInterfaceSystemMapHerstory(@Param("requirementId") String requirementId, @Param("version") String version, @Param("interfaceId") String interfaceId, @Param("modDate") String modDate, @Param("modId") String modId);

	/**
	 *
	 * @param interfaceId
	 * @param modDate
	 * @param modId
	 * @return
	 */
	public int insertInterfaceBusinessMapHerstory(@Param("requirementId") String requirementId, @Param("version") String version, @Param("interfaceId") String interfaceId, @Param("modDate") String modDate, @Param("modId") String modId);

	/**
	 *
	 * @param interfaceId
	 * @param modDate
	 * @param modId
	 * @return
	 */
	public int insertInterfaceRelUserHerstory(@Param("requirementId") String requirementId, @Param("version") String version, @Param("interfaceId") String interfaceId, @Param("modDate") String modDate, @Param("modId") String modId);


	/**
	 *
	 * @param interfaceId
	 * @param modDate
	 * @param modId
	 * @return
	 */
	public int insertB2BiInterfaceMetaDataHistory(@Param("requirementId") String requirementId, @Param("version") String version, @Param("interfaceId") String interfaceId, @Param("modDate") String modDate, @Param("modId") String modId);




	/**
	 *
	 * @param requirementId
	 * @return
	 * @throws Exception
	 */
	public List<Map> getRequirementHerstoryList(@Param("requirementId") String requirementId) throws Exception;

	/**
	 *
	 * @param requirementId
	 * @param version
	 * @return
	 * @throws Exception
	 */
	public RequirementColumnHerstory getRequirementColumnHerstory(@Param("requirementId") String requirementId, @Param("version") String version) throws Exception;



	/**
	 *
	 * @param interfaceId
	 * @param version
	 * @return
	 * @throws Exception
	 */
	public InterfaceColumnHerstory getInterfaceColumnHerstory(@Param("requirementId") String requirementId, @Param("interfaceId") String interfaceId, @Param("version") String version) throws Exception;





	/**
	 *
	 * @param requirementId
	 * @param version
	 * @return
	 * @throws Exception
	 */
	public List<Map> getRequirementCommentListHerstory(@Param("requirementId") String requirementId, @Param("version") String version) throws Exception;

	/**
	 *
	 * @param requirementId
	 * @return
	 * @throws Exception
	 */
	public List<Map> getCurrentRequirementCommentList(@Param("requirementId")String requirementId) throws Exception;



	/**
	 *
	 * @param requirementId
	 * @param version
	 * @return
	 * @throws Exception
	 */
	public List<Map> getRequirementAttachFileListHerstory(@Param("requirementId") String requirementId, @Param("version") String version) throws Exception;

	/**
	 *
	 * @param requirementId
	 * @return
	 * @throws Exception
	 */
	public List<Map> getCurrentRequirementAttachFileList(@Param("requirementId")String requirementId) throws Exception;


	/**
	 *
	 * @param requirementId
	 * @param version
	 * @param interfaceId
	 * @return
	 * @throws Exception
	 */
	public List<Map> getInterfaceSystemMapListHerstory(@Param("requirementId") String requirementId, @Param("version") String version, @Param("interfaceId") String interfaceId) throws Exception;

	/**
	 *
	 * @param interfaceId
	 * @return
	 * @throws Exception
	 */
	public List<Map> getCurrentInterfaceSystemMapList(@Param("interfaceId")String interfaceId) throws Exception;


	/**
	 *
	 * @param requirementId
	 * @param version
	 * @param interfaceId
	 * @return
	 * @throws Exception
	 */
	public List<Map> getInterfaceBusinessMapListHerstory(@Param("requirementId") String requirementId, @Param("version") String version, @Param("interfaceId") String interfaceId) throws Exception;

	/**
	 *
	 * @param interfaceId
	 * @return
	 * @throws Exception
	 */
	public List<Map> getCurrentInterfaceBusinessMapList(@Param("interfaceId")String interfaceId) throws Exception;



	/**
	 *
	 * @param requirementId
	 * @param version
	 * @param interfaceId
	 * @return
	 * @throws Exception
	 */
	public List<Map> getInterfaceRelUserListHerstory(@Param("requirementId") String requirementId, @Param("version") String version, @Param("interfaceId") String interfaceId) throws Exception;


	/**
	 *
	 * @param interfaceId
	 * @return
	 * @throws Exception
	 */
	public List<Map> getCurrentInterfaceRelUserList(@Param("interfaceId")String interfaceId) throws Exception;



	/**
	 *
	 * @param requirementId
	 * @param version
	 * @param interfaceId
	 * @return
	 * @throws Exception
	 */
	public Map getInterfaceB2biInfoListHerstory(@Param("requirementId") String requirementId, @Param("version") String version, @Param("interfaceId") String interfaceId) throws Exception;


	/**
	 *
	 * @param interfaceId
	 * @return
	 * @throws Exception
	 */
	public Map getCurrentInterfaceB2biInfoList(@Param("interfaceId")String interfaceId) throws Exception;


	public RequirementHistory getLastVersionRequirementHistory(String requimentId);

	public void addRequirementHistory(RequirementHistory requirementHistory);

	public List<Map> getRequirementHistoryList(String requirementId);

	public RequirementHistory getRequirementHistory(@Param("requirementId")String requirementId, @Param("version")String version);

	public String getRequirementHistoryStatusNm(@Param("status")String status);


}
