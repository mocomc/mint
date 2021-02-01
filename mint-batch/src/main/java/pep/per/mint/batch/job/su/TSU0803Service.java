/*
 * Copyright (c) 2013 ~ 2015. Mocomsys's guys(Sanghoon Lim, Deahun Ham, dhkim, Solution TF), Inc.  All Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 * [주의!]
 * 본 코드가 하자없이 완벽할거라 믿었다간 엄청난 욕을 먹고 그로 인한 스트레스로 병들거나 심하면 살기 싫어질 수도 있으니 부디 살얼음판을 걷듯이 주의하여 사용하기 바란다.
 * 사용상 받을지 모를 스트레스 및 기타 피해에 대한 책임은 사용자 본인에게 있음을 명시한다. 부디 행운을 빈다.
 * Please Don't contact Mocomsys, Inc., NURITKUM SQUARE R&D TOWER, 11F DMC 1605,
 * Sangam-Dong, Mapo-Gu, Seoul, 121-795 Korea or visit mocomsys.com if you need additional information or have any questions.
 */

package pep.per.mint.batch.job.su;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pep.per.mint.batch.BatchManager;
import pep.per.mint.batch.job.JobServiceCaller;
import pep.per.mint.batch.mapper.su.TSU0803JobMapper;
import pep.per.mint.common.util.Util;

import java.net.InetAddress;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Solution TF on 15. 9. 30..
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
@Service
public class TSU0803Service extends JobServiceCaller{

	Logger log = LoggerFactory.getLogger(TSU0803Service.class);

    @Autowired
    TSU0803JobMapper tsu0803JobMapper;


    @Autowired
    BatchManager bm;

    public void run(Map params) throws Exception{



    	String fromDate = (String)params.get("fromDate");
    	String toDate = (String)params.get("toDate");

    	if(Util.isEmpty(fromDate) || fromDate.length() < 10) {
    		throw new Exception("parameter fromDate(format:yyyyMMddHH) is null or not valid.");
    	}

    	if(Util.isEmpty(toDate) || toDate.length() < 10) {
    		throw new Exception("parameter toDate(format:yyyyMMddHH) is null or not valid.");
    	}


	     SqlSession srcSqlSession = null;
	     SqlSession tagSqlSession = null;
	     try {
	     	//for IFM datasoruce
	         SqlSessionFactory srcSqlSessionFactory = bm.getIfmSqlSessionFactory();

	         //for IIP datasource
	         SqlSessionFactory tagSqlSessionFactory = bm.getSqlSessionFactory();

	         srcSqlSession = srcSqlSessionFactory.openSession(true);

	         tagSqlSession = tagSqlSessionFactory.openSession(ExecutorType.BATCH, false);



	         List<Map<String,String>> interfaceList = srcSqlSession.selectList("pep.per.mint.batch.mapper.ifm.IfmJobMapper.selectInterfaces", params);
	         for (Map<String, String> interfaze : interfaceList) {
					summarizeTSU0803(srcSqlSession, tagSqlSession, interfaze, params);
				}

	         insertZeroData(tagSqlSession, params);

	         srcSqlSession.commit();
	         tagSqlSession.commit();

	     } finally {
	     	if(srcSqlSession != null) { srcSqlSession.close(); }
	         if(tagSqlSession != null) { tagSqlSession.close(); }
	     }

    }



    private void insertZeroData(SqlSession tagSqlSession, Map<String,Object> params) {
		// TODO Auto-generated method stub

		Map<String, String> summaryParams = new HashMap<String, String>();

		String regApp = null;
		try{
			regApp = InetAddress.getLocalHost().getHostName();
		}catch(Exception e){
			regApp = Util.join("bthread-",Thread.currentThread().getName());
			//log.error("",e);
		}
		summaryParams.put("regApp", regApp);
		summaryParams.put("regDate",Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI));

		String fromDate = (String)params.get("fromDate");
		String toDate = (String)params.get("toDate");
		String trDateYmd = fromDate.substring(0, 8);
		int from = Integer.parseInt(fromDate.substring(8));
		int to   = Integer.parseInt(toDate.substring(8));
		log.debug("start=======================from:"+from+", to:" + to);
		for(int i = from ; i <= to ; i ++){
			String hh = Util.leftPad(new Integer(i).toString(), 2, "0");
			String trDate = Util.join(trDateYmd, hh);
			summaryParams.put("trDate", trDate);
			log.debug("insertZeroDataTSU0803.params:"+Util.toJSONString(summaryParams));
			int res = tagSqlSession.update("pep.per.mint.batch.mapper.su.TSU0803JobMapper.insertZeroDataTSU0803", summaryParams);
		}
	}

	private void summarizeTSU0803(SqlSession srcSqlSession, SqlSession tagSqlSession, Map interfaze, Map<String, Object> params) throws Exception {
		// TODO Auto-generated method stub
		Integer masterLogId = new Integer(interfaze.get("masterLogId").toString());
		String integrationId = (String)interfaze.get("integrationId");
		String groupId = (String)interfaze.get("groupId");
		params.put("masterLogId", masterLogId);
		List<Map> summaries = selectSummary(srcSqlSession, params);

		interfaze.put("delimeter", "_");
		Map<String, String> interfaceInfo = tagSqlSession.selectOne("pep.per.mint.batch.mapper.su.TSU0803JobMapper.selectInterfaceInfo", interfaze);

		if(interfaceInfo == null){
			log.info(Util.join("summarizeTSU0803:integraionId[",integrationId,"] 에 해당하는 인터페이스를 찾을 수 없어서 인터페이스별집계(시간별)을 수횅하지 않습니다." ));
			return;
		}

		String interfacePrDr = interfaceInfo.get("interfacePrDr");
		String interfaceId = interfaceInfo.get("interfaceId");
		for(int i = 0 ; i < summaries.size() ; i ++){

			Map summary = summaries.get(i);

			summary.put("interfaceId",interfaceId);
			summary.put("regDate",Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI));

			String regApp = null;
			try{
				regApp = InetAddress.getLocalHost().getHostName();
			}catch(Exception e){
				regApp = Util.join("bthread-",Thread.currentThread().getName());
				//log.error("",e);
			}
			summary.put("regApp", regApp);


			Map<String, Double> dataSizes = selectDataSize(srcSqlSession, params);

			double dataSize = 0;

			double sndDataSize = Double.parseDouble(dataSizes.get("sndDataSize").toString());
			double rcvDataSize = Double.parseDouble(dataSizes.get("rcvDataSize").toString());
			if(Util.isEmpty(interfacePrDr) || "0".equals(interfacePrDr)){//정보가 존재하지 않을 경우 단방향 처리한다.(기존 로직)
				dataSize = rcvDataSize;
			}else{
				dataSize = sndDataSize + rcvDataSize;
			}


			summary.put("dataSize", dataSize);

			int upsertRes = upsertSummary(tagSqlSession,summary);
			//if(upsertRes != 1){
				//throw new BatchException(Util.join("exception:","summarizeTSU0803 fail:upsertRes(",upsertRes,")"));
			//}
		}

	}

	private int upsertSummary(SqlSession tagSqlSession, Map params) throws Exception {
		// TODO Auto-generated method stub
		int upsertRes = 0;
		log.debug("params:"+Util.toJSONString(params));
		if(existInterfaceSummary(tagSqlSession, params)){
			upsertRes = tagSqlSession.update("pep.per.mint.batch.mapper.su.TSU0803JobMapper.updateTSU0803", params);
		}else{
			upsertRes = tagSqlSession.insert("pep.per.mint.batch.mapper.su.TSU0803JobMapper.insertTSU0803", params);

		}
		return upsertRes;
	}

	private boolean existInterfaceSummary(SqlSession tagSqlSession, Map params) {
		// TODO Auto-generated method stub
		int exist = (Integer)tagSqlSession.selectOne("pep.per.mint.batch.mapper.su.TSU0803JobMapper.selectOneTSU0803",params);
		return exist >= 1 ? true : false;
	}

	private Map<String, Double> selectDataSize(SqlSession srcSqlSession, Map<String, Object> params) throws Exception {
		// TODO Auto-generated method stub
		Map<String, Double> sizes = srcSqlSession.selectOne("pep.per.mint.batch.mapper.ifm.IfmJobMapper.selectDataSize", params);
		return sizes;
	}

	private List<Map> selectSummary(SqlSession srcSqlSession, Map<String, Object> params) throws Exception {
		// TODO Auto-generated method stub
		List<Map> res = srcSqlSession.selectList("pep.per.mint.batch.mapper.ifm.IfmJobMapper.selectInterfaceTransactionSummary", params);
		return res;
	}



}
