/*
 * Copyright 2013 ~ 2014 Mocomsys(dhkim, Solution TF), Inc.  All Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 * Please contact Mocomsys, Inc., NURITKUM SQUARE R&D TOWER, 11F DMC 1605, 
 * Sangam-Dong, Mapo-Gu, Seoul, 121-795 Korea or visit mocomsys.com 
 * if you need additional information or have any questions.
*/
package pep.per.mint.database.service.op;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pep.per.mint.common.data.basic.Business;
import pep.per.mint.common.data.basic.Interface;
import pep.per.mint.common.data.basic.RelUser;
import pep.per.mint.common.data.basic.System;
import pep.per.mint.common.data.basic.monitor.ProblemAttachFile;
import pep.per.mint.common.data.basic.monitor.ProblemManagement;
import pep.per.mint.common.util.LogUtil;
import pep.per.mint.common.util.Util;
import pep.per.mint.database.mapper.co.CommonMapper;
import pep.per.mint.database.mapper.op.ProblemMapper;

/**
 * 트래킹 로그 관련 서비스
 * @author noggenfogger
 *
 */

@Service
public class ProblemService {
	
	private static final Logger logger = LoggerFactory.getLogger(ProblemService.class);
	
	@Autowired
	ProblemMapper problemMapper;
	
	@Autowired
	CommonMapper commonMapper;
	
	public List<ProblemManagement> getProblemList(Map arg) throws Exception{
		List<ProblemManagement> problemList = problemMapper.getProblemList(arg);
		return problemList;
	}
	
	public ProblemManagement getProblemInfo(Map arg) throws Exception{
		ProblemManagement problemInfo = problemMapper.getProblemInfo(arg);
		return problemInfo;
	}
	
	public Interface getProblemInterfaceInfo(Map arg) throws Exception{
		Interface interfaceInfo = problemMapper.getProblemInterfaceInfo(arg);
		return interfaceInfo;
	}
	
	@Transactional
	public int insertProblem(ProblemManagement param) throws Exception{
		
		StringBuffer resultMsg = new StringBuffer();
		int resultCd = 0;
		try{
			
			if(logger.isDebugEnabled())
			{ 
				resultMsg = new StringBuffer();
				LogUtil.bar2(LogUtil.prefix(resultMsg, "요건등록프로세스[ProblemService.insertProblem(problemManagerment) 처리시작]"));				
				try{ LogUtil.prefix(resultMsg, "params:",Util.toJSONString(param)); LogUtil.postbar(resultMsg);}catch(Exception e){}
				
			}
		 
			//----------------------------------------------------------
			//1.장애 insert
			//----------------------------------------------------------
			{
				if(logger.isDebugEnabled()) LogUtil.prefix(resultMsg,"Problem(장애등록) insert 처리:");
				resultCd = problemMapper.insertProblem(param);
				if(resultCd != 1) {
					if(logger.isDebugEnabled()) LogUtil.prefix(resultMsg,"Problem(장애등록) insert 처리 실패:problemMapper.insertProblem:result:",resultCd);
					throw new Exception(Util.join("Exception:ProblemService.createProblem:problemMapper.insertProblem:resultCd:(", resultCd, ")")); 					
				}
				if(logger.isDebugEnabled()) LogUtil.prefix(resultMsg,"Problem(장애등록) insert 처리 OK:problemId:",param.getProblemId());
			}
			
			//----------------------------------------------------------
			//2.List<ProblemAttatchFile>(장애 첨부파일 리스트) insert
			//----------------------------------------------------------
			{
				if(logger.isDebugEnabled()) LogUtil.prefix(resultMsg,"장애 첨부파일 리스트 등록 시작");
				List<ProblemAttachFile> attatchFileList = param.getProblemAttachFile();
				//3.1.등록할 요건첨부파일 리스트가 존재하면 
				if(attatchFileList != null && attatchFileList.size() != 0){
					int cnt = 0;
					for (ProblemAttachFile paf : attatchFileList) {
						paf.setProblemId(param.getProblemId());
						paf.setRegDate(param.getRegDate());
						paf.setRegUser(param.getRegUser());
						
						resultCd = problemMapper.insertProblemAttachFile(paf);
						if(resultCd != 1) {
							if(logger.isDebugEnabled()) LogUtil.prefix(resultMsg,"장애 첨부파일 리스트 등록 처리 실패:ProblemMapper.insertProblemAttatchFile:result:", resultCd);
							throw new Exception(Util.join("Exception:ProblemService.createProblem:ProblemMapper.insertProblemAttatchFile:resultCd:(", resultCd, ")"));
						}
						cnt ++;
					}
					if(logger.isDebugEnabled()) LogUtil.prefix(resultMsg,"장애 첨부파일 리스트 등록 OK(총 장애 첨부파일 등록 건수 : ",cnt,")");
				}else{
					if(logger.isDebugEnabled()) LogUtil.prefix(resultMsg,"장애 첨부파일 리스트 등록 -> 등록할 장애 첨부파일 리스트가 존재하지 않는다.");
				}
			}
			
			
			//----------------------------------------------------------
			//3.interface정보 insert
			//----------------------------------------------------------
			{
				if(param.getInterfaceId() != null && param.getInterfaceId() != "") {
					
					//----------------------------------------------------------
					//3-1.interface정보
					//----------------------------------------------------------
					Interface intf = commonMapper.getInterfaceDetail(param.getInterfaceId());
					
					
					//----------------------------------------------------------
					//3-2.장애 인터페이스 등록(top0304)
					//----------------------------------------------------------
					Map<String, String> problemInterfaceMap = new HashMap<String, String>();
					problemInterfaceMap.put("problemId", param.getProblemId());
					problemInterfaceMap.put("interfaceId", intf.getInterfaceId());
					problemInterfaceMap.put("interfaceNm", intf.getInterfaceNm());
					problemInterfaceMap.put("channelId", intf.getChannel().getChannelId());
					problemInterfaceMap.put("channelNm", intf.getChannel().getChannelNm());
					problemInterfaceMap.put("channelCd", intf.getChannel().getChannelCd());
					problemInterfaceMap.put("dataPrDir", intf.getDataPrDir());
					problemInterfaceMap.put("appPrMethod", intf.getAppPrMethod());
					problemInterfaceMap.put("dataPrMethod", intf.getDataPrMethod());
					
					problemMapper.insertProblemInterface(problemInterfaceMap);
					
					//----------------------------------------------------------
					//3-3.장애 인터페이스 시스템 정보 등록(top0305)
					//----------------------------------------------------------
					if(intf.getSystemList() != null) {
						
						List<System> list = intf.getSystemList();
						
						for(int i = 0; i < list.size(); i++) {
							
							Map<String, String> problemSystemMap = new HashMap<String, String>();
							problemSystemMap.put("problemId", param.getProblemId());
							problemSystemMap.put("interfaceId", intf.getInterfaceId());
							problemSystemMap.put("systemId", list.get(i).getSystemId());
							problemSystemMap.put("systemNm", list.get(i).getSystemNm());
							problemSystemMap.put("systemCd", list.get(i).getSystemCd());
							problemSystemMap.put("service", list.get(i).getService());
							problemSystemMap.put("resourceType", list.get(i).getResourceCd());
							problemSystemMap.put("nodeType", list.get(i).getNodeType());
							
							problemMapper.insertProblemInterfaceSystem(problemSystemMap);
						}
					}
					
					//----------------------------------------------------------
					//3-4.장애 인터페이스 시스템 정보 등록(top0306)
					//----------------------------------------------------------
					if(intf.getBusinessList() != null) {
						List<Business> businessList = intf.getBusinessList();
						
						for(int i = 0; i < businessList.size(); i++) {
							Map<String, String> problemBusinessMap = new HashMap<String, String>();
							
							problemBusinessMap.put("problemId", param.getProblemId());
							problemBusinessMap.put("interfaceId", intf.getInterfaceId());
							problemBusinessMap.put("businessId", businessList.get(i).getBusinessId());
							problemBusinessMap.put("businessNm", businessList.get(i).getBusinessNm());
							problemBusinessMap.put("businessCd", businessList.get(i).getBusinessCd());
							problemBusinessMap.put("nodeType", businessList.get(i).getNodeType());
							
							problemMapper.insertProblemBusiness(problemBusinessMap);
						}
					}
					
					//----------------------------------------------------------
					//3-5.장애 인터페이스 담당자 정보 등록(top0307)
					//----------------------------------------------------------
					if(intf.getRelUsers() != null) {
						List<RelUser> userList = intf.getRelUsers();
						
						for(int i = 0; i < userList.size(); i++) {
							Map<String, String> problemUserMap = new HashMap<String, String>();
							problemUserMap.put("problemId", param.getProblemId());
							problemUserMap.put("interfaceId", intf.getInterfaceId());
							problemUserMap.put("userId", userList.get(i).getUser().getUserId());
							problemUserMap.put("roleType", userList.get(i).getRoleType());
							
							problemMapper.insertProblemAdmin(problemUserMap);
						}
					}
				}
				
			}
			
			return resultCd;
		}finally{
			if(logger.isDebugEnabled()) {
				resultMsg.append(LogUtil.bar(LogUtil.prefix("장애 등록 [ProblemService.insertProblem(problemManager) 처리종료]")));
				logger.debug(resultMsg.toString());
			}
		}
	}
	
	@Transactional
	public int updateProblem(ProblemManagement param) throws Exception{
		StringBuffer resultMsg = new StringBuffer();
		int resultCd = 0;
		try{
			
			if(logger.isDebugEnabled())
			{ 
				resultMsg = new StringBuffer();
				LogUtil.bar2(LogUtil.prefix(resultMsg, "요건등록프로세스[ProblemService.updateProblem(problemManagerment) 처리시작]"));				
				try{ LogUtil.prefix(resultMsg, "params:",Util.toJSONString(param)); LogUtil.postbar(resultMsg);}catch(Exception e){}
				
			}
		 
			//----------------------------------------------------------
			//1.장애 update
			//----------------------------------------------------------
			{
				if(logger.isDebugEnabled()) LogUtil.prefix(resultMsg,"Problem(장애등록) update 처리:");
				resultCd = problemMapper.updateProblem(param);
				if(resultCd != 1) {
					if(logger.isDebugEnabled()) LogUtil.prefix(resultMsg,"Problem(장애등록) update 처리 실패:problemMapper.updateProblem:result:",resultCd);
					throw new Exception(Util.join("Exception:ProblemService.createProblem:problemMapper.updateProblem:resultCd:(", resultCd, ")")); 					
				}
				if(logger.isDebugEnabled()) LogUtil.prefix(resultMsg,"Problem(장애등록) update 처리 OK:problemId:",param.getProblemId());
			}
			
			//----------------------------------------------------------
			//2.List<ProblemAttatchFile>(장애 첨부파일 리스트) insert
			//----------------------------------------------------------
			{
				if(logger.isDebugEnabled()) LogUtil.prefix(resultMsg,"장애 첨부파일 리스트 등록 시작");
				List<ProblemAttachFile> attatchFileList = param.getProblemAttachFile();
				//3.1.등록할 요건첨부파일 리스트가 존재하면 
				if(attatchFileList != null && attatchFileList.size() != 0){
					int cnt = 0;
					for (ProblemAttachFile paf : attatchFileList) {
						paf.setProblemId(param.getProblemId());
						paf.setRegDate(param.getRegDate());
						paf.setRegUser(param.getRegUser());
						
						if("C".equals(paf.getFlag())) {
							resultCd = problemMapper.insertProblemAttachFile(paf);
						} else if("D".equals(paf.getFlag())) {
							resultCd = problemMapper.deleteProblemAttachFile(paf);
						}
						
						if(resultCd != 1) {
							if(logger.isDebugEnabled()) LogUtil.prefix(resultMsg,"장애 첨부파일 리스트 등록 처리 실패:ProblemMapper.insertProblemAttatchFile:result:", resultCd);
							throw new Exception(Util.join("Exception:ProblemService.createProblem:ProblemMapper.insertProblemAttatchFile:resultCd:(", resultCd, ")"));
						}
						cnt ++;
					}
					if(logger.isDebugEnabled()) LogUtil.prefix(resultMsg,"장애 첨부파일 리스트 등록 OK(총 장애 첨부파일 등록 건수 : ",cnt,")");
				}else{
					if(logger.isDebugEnabled()) LogUtil.prefix(resultMsg,"장애 첨부파일 리스트 등록 -> 등록할 장애 첨부파일 리스트가 존재하지 않는다.");
				}
			}
			
			
			//----------------------------------------------------------
			//3.interface정보 insert
			//----------------------------------------------------------
			{
				//----------------------------------------------------------
				//3-1.등록된 interface정보 삭제
				//----------------------------------------------------------
				Map<String, String> deleteMap = new HashMap<String, String>();
				deleteMap.put("problemId", param.getProblemId());
				
				problemMapper.deleteProblemAdmin(deleteMap);
				problemMapper.deleteProblemBusiness(deleteMap);
				problemMapper.deleteProblemSystem(deleteMap);
				problemMapper.deleteProblemInterface(deleteMap);
				
				
				
				if(param.getInterfaceId() != null && param.getInterfaceId() != "") {
					
					//----------------------------------------------------------
					//3-2.interface정보
					//----------------------------------------------------------
					Interface intf = commonMapper.getInterfaceDetail(param.getInterfaceId());
					
					
					//----------------------------------------------------------
					//3-3.장애 인터페이스 등록(top0304)
					//----------------------------------------------------------
					Map<String, String> problemInterfaceMap = new HashMap<String, String>();
					problemInterfaceMap.put("problemId", param.getProblemId());
					problemInterfaceMap.put("interfaceId", intf.getInterfaceId());
					problemInterfaceMap.put("interfaceNm", intf.getInterfaceNm());
					problemInterfaceMap.put("channelId", intf.getChannel().getChannelId());
					problemInterfaceMap.put("channelNm", intf.getChannel().getChannelNm());
					problemInterfaceMap.put("channelCd", intf.getChannel().getChannelCd());
					problemInterfaceMap.put("dataPrDir", intf.getDataPrDir());
					problemInterfaceMap.put("appPrMethod", intf.getAppPrMethod());
					problemInterfaceMap.put("dataPrMethod", intf.getDataPrMethod());
					
					problemMapper.insertProblemInterface(problemInterfaceMap);
					
					//----------------------------------------------------------
					//3-4.장애 인터페이스 시스템 정보 등록(top0305)
					//----------------------------------------------------------
					if(intf.getSystemList() != null) {
						
						List<System> list = intf.getSystemList();
						
						for(int i = 0; i < list.size(); i++) {
							
							Map<String, String> problemSystemMap = new HashMap<String, String>();
							problemSystemMap.put("problemId", param.getProblemId());
							problemSystemMap.put("interfaceId", intf.getInterfaceId());
							problemSystemMap.put("systemId", list.get(i).getSystemId());
							problemSystemMap.put("systemNm", list.get(i).getSystemNm());
							problemSystemMap.put("systemCd", list.get(i).getSystemCd());
							problemSystemMap.put("service", list.get(i).getService());
							problemSystemMap.put("resourceType", list.get(i).getResourceCd());
							problemSystemMap.put("nodeType", list.get(i).getNodeType());
							
							problemMapper.insertProblemInterfaceSystem(problemSystemMap);
						}
					}
					
					//----------------------------------------------------------
					//3-5.장애 인터페이스 시스템 정보 등록(top0306)
					//----------------------------------------------------------
					if(intf.getBusinessList() != null) {
						List<Business> businessList = intf.getBusinessList();
						
						for(int i = 0; i < businessList.size(); i++) {
							Map<String, String> problemBusinessMap = new HashMap<String, String>();
							
							problemBusinessMap.put("problemId", param.getProblemId());
							problemBusinessMap.put("interfaceId", intf.getInterfaceId());
							problemBusinessMap.put("businessId", businessList.get(i).getBusinessId());
							problemBusinessMap.put("businessNm", businessList.get(i).getBusinessNm());
							problemBusinessMap.put("businessCd", businessList.get(i).getBusinessCd());
							problemBusinessMap.put("nodeType", businessList.get(i).getNodeType());
							
							problemMapper.insertProblemBusiness(problemBusinessMap);
						}
					}
					
					//----------------------------------------------------------
					//3-6.장애 인터페이스 담당자 정보 등록(top0307)
					//----------------------------------------------------------
					if(intf.getRelUsers() != null) {
						List<RelUser> userList = intf.getRelUsers();
						
						for(int i = 0; i < userList.size(); i++) {
							Map<String, String> problemUserMap = new HashMap<String, String>();
							problemUserMap.put("problemId", param.getProblemId());
							problemUserMap.put("interfaceId", intf.getInterfaceId());
							problemUserMap.put("userId", userList.get(i).getUser().getUserId());
							problemUserMap.put("roleType", userList.get(i).getRoleType());
							
							problemMapper.insertProblemAdmin(problemUserMap);
						}
					}
				}
			}
			
			return resultCd;
		}finally{
			if(logger.isDebugEnabled()) {
				resultMsg.append(LogUtil.bar(LogUtil.prefix("장애 등록 [ProblemService.insertProblem(problemManager) 처리종료]")));
				logger.debug(resultMsg.toString());
			}
		}
		
		
		
		//return problemMapper.updateProblem(param);
	}
	
	public int deleteProblem(ProblemManagement param) throws Exception{
		return problemMapper.deleteProblem(param);
	}
	
	public int insertProblemAttachFile(ProblemAttachFile param) throws Exception{
		return problemMapper.insertProblemAttachFile(param);
	}
	
	public List<ProblemManagement> getToDoProblemList(Map arg) throws Exception{
		return problemMapper.getToDoProblemList(arg);
	}
	
	@Transactional
	public int updateProblemStatus(String status, String modDate, String modId,String problemId) throws Exception {
		return problemMapper.updateProblemStatus(status, modDate, modId, problemId);
	}
}
