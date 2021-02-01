/**
 * 
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

import pep.per.mint.common.data.basic.Help;
import pep.per.mint.common.data.basic.Interface;
import pep.per.mint.common.data.basic.monitor.ProblemAttachFile;
import pep.per.mint.common.data.basic.monitor.ProblemLedger;
import pep.per.mint.common.data.basic.monitor.ProblemTemplate;
import pep.per.mint.common.util.LogUtil;
import pep.per.mint.common.util.Util;
import pep.per.mint.database.mapper.co.CommonMapper;
import pep.per.mint.database.mapper.op.ProblemLedgerMapper;

/**
 * 장애대장 관련 서비스
 * @author INSEONG
 *
 */

@Service
public class ProblemLedgerService {

	private static final Logger logger = LoggerFactory.getLogger(ProblemLedgerService.class);
	
	@Autowired
	ProblemLedgerMapper problemLedgerMapper;
	
	@Autowired
	CommonMapper commonMapper;
	
	
	public List<ProblemLedger> getProblemManagementList(Map param) throws Exception {
		return problemLedgerMapper.getProblemManagementList(param);
	}
	
	public List<Map> getProblemManagementListCount(Map param) throws Exception {
		return problemLedgerMapper.getProblemManagementListCount(param);
	}		
	
	
	
	public List<ProblemLedger> getProblemList(Map param) throws Exception {
		return problemLedgerMapper.getProblemList(param);
	}
	
	public List<Map> getProblemListCount(Map param) throws Exception {
		return problemLedgerMapper.getProblemListCount(param);
	}	
	
	public ProblemLedger getProblemInfo(Map param) throws Exception {
		return problemLedgerMapper.getProblemInfo(param);
	}
	
	public List<ProblemLedger> getProblemRegisterManagementList(Map param) throws Exception {
		return problemLedgerMapper.getProblemRegisterManagementList(param);
	}
	
	public List<Map> getProblemRegisterManagementListCount(Map param) throws Exception {
		return problemLedgerMapper.getProblemRegisterManagementListCount(param);
	}	
	
	public List<ProblemLedger> getToDoProblemList(Map param) throws Exception {
		return problemLedgerMapper.getToDoProblemList(param);
	}
	
	public List<ProblemLedger> getProblemListForTrackingDetail(Map param) throws Exception {
		return problemLedgerMapper.getProblemListForTrackingDetail(param);
	}
	
	public Interface getProblemInterfaceInfo(Map param) throws Exception {
		Interface interfaceInfo = problemLedgerMapper.getProblemInterfaceInfo(param);
		return interfaceInfo;
	}
	
	@Transactional
	public int insertProblem(ProblemLedger param) throws Exception {
		
		StringBuffer resultMsg = new StringBuffer();
		int resultCd = 0;
		try {
		
			if(logger.isDebugEnabled())
			{ 
				resultMsg = new StringBuffer();
				LogUtil.bar2(LogUtil.prefix(resultMsg, "장애등록프로세스[ProblemLedgerService.insertProblem(ProblemLedger) 처리시작]"));				
				try{ LogUtil.prefix(resultMsg, "params:",Util.toJSONString(param)); LogUtil.postbar(resultMsg);}catch(Exception e){}
			}
			
			//----------------------------------------------------------
			//1.장애 insert
			//----------------------------------------------------------
			{
				if(logger.isDebugEnabled()) {LogUtil.prefix(resultMsg,"Problem insert(장애등록) 처리:");}
				resultCd = problemLedgerMapper.insertProblem(param);
				if(resultCd != 1) {
					if(logger.isDebugEnabled()) {LogUtil.prefix(resultMsg,"Problem insert(장애등록) 처리 실패:problemLedgerMapper.insertProblem:result:",resultCd);}
					throw new Exception(Util.join("Exception:ProblemLedgerService.insertProblem:problemLedgerMapper.insertProblem:resultCd:(", resultCd, ")")); 					
				}
				if(logger.isDebugEnabled()) LogUtil.prefix(resultMsg,"Problem insert(장애등록) 처리 OK:problemId:",param.getProblemId());
			}
			
			//----------------------------------------------------------
			//2.List<ProblemAttatchFile>(장애 첨부파일 리스트) insert (TOP0303)
			//----------------------------------------------------------
			{
				if(logger.isDebugEnabled()) {LogUtil.prefix(resultMsg,"장애 첨부파일 리스트 등록 시작");}
				List<ProblemAttachFile> attatchFileList = param.getProblemAttachFile();
				//2.1.등록할 장애등록 첨부파일 리스트가 존재하면 
				if(attatchFileList != null && attatchFileList.size() != 0){
					int cnt = 0;
					for (ProblemAttachFile paf : attatchFileList) {
						paf.setProblemId(param.getProblemId());
						paf.setRegDate(param.getRegDate());
						paf.setRegUser(param.getRegUser());
						
						resultCd = problemLedgerMapper.insertProblemAttachFile(paf);
						if(resultCd != 1) {
							if(logger.isDebugEnabled()) {LogUtil.prefix(resultMsg,"장애 첨부파일 리스트 등록 처리 실패:problemLedgerMapper.insertProblemAttatchFile:result:", resultCd);}
							throw new Exception(Util.join("Exception:ProblemLedgerService.insertProblem:problemLedgerMapper.insertProblemAttatchFile:resultCd:(", resultCd, ")"));
						}
						cnt ++;
					}
					if(logger.isDebugEnabled()) {LogUtil.prefix(resultMsg,"장애 첨부파일 리스트 등록 OK(총 장애 첨부파일 등록 건수 : ",cnt,")");}
				}else{
					if(logger.isDebugEnabled()) {LogUtil.prefix(resultMsg,"장애 첨부파일 리스트 등록 -> 등록할 장애 첨부파일 리스트가 존재하지 않는다.");}
				}
			}
			
			//----------------------------------------------------------
			//3.interface정보 insert (TOP0310)
			//----------------------------------------------------------
			{
				if(logger.isDebugEnabled()) {LogUtil.prefix(resultMsg,"장애 인터페이스 리스트 등록 시작");}
				
				List<Map> problemInterfaceList = param.getProblemInterface();
				//Map<String, String> problemInterfaceMap = new HashMap<String, String>();
				if(problemInterfaceList != null && problemInterfaceList.size() != 0){
					int cnt = 0;
					for (Map<String, String> problemInterfaceMap : problemInterfaceList) {
						problemInterfaceMap.put("problemId", param.getProblemId());
						
						resultCd = problemLedgerMapper.insertProblemInterface(problemInterfaceMap);
						
						if(resultCd != 1) {
							if(logger.isDebugEnabled()) {LogUtil.prefix(resultMsg,"장애 인터페이스 리스트 등록 처리 실패:problemLedgerMapper.insertProblemInterface:result:", resultCd);}
							throw new Exception(Util.join("Exception:ProblemLedgerService.insertProblem:problemLedgerMapper.insertProblemInterface:resultCd:(", resultCd, ")"));
						}
						cnt++;
					}
					if(logger.isDebugEnabled()) {LogUtil.prefix(resultMsg,"장애 인터페이스 리스트 등록 OK(총 장애 인터페이스 등록 건수 : ",cnt,")");}
				}else{
					if(logger.isDebugEnabled()) {LogUtil.prefix(resultMsg,"장애 인터페이스 리스트 등록 -> 등록할 장애 인터페이스 리스트가 존재하지 않는다.");}
				}
			}
			
			
			return resultCd;
		}
		finally {
			if(logger.isDebugEnabled()) {
				resultMsg.append(LogUtil.bar(LogUtil.prefix("장애 등록 [ProblemLedgerService.insertProblem(ProblemLedger) 처리종료]")));
				logger.debug(resultMsg.toString());
			}
		}
	}
	
	
	public int deleteProblem(ProblemLedger param) throws Exception {
		return problemLedgerMapper.deleteProblem(param);
	}
	
	@Transactional
	public int updateProblem(ProblemLedger param) throws Exception {
		StringBuffer resultMsg = new StringBuffer();
		int resultCd = 0;
		try {
			
			if(logger.isDebugEnabled())
			{ 
				resultMsg = new StringBuffer();
				LogUtil.bar2(LogUtil.prefix(resultMsg, "요건등록프로세스[ProblemLedgerService.updateProblem(ProblemLedger) 처리시작]"));				
				try{ LogUtil.prefix(resultMsg, "params:",Util.toJSONString(param)); LogUtil.postbar(resultMsg);}catch(Exception e){}
				
			}
			
			//----------------------------------------------------------
			//1.장애 update
			//----------------------------------------------------------
			{
				if(logger.isDebugEnabled()) LogUtil.prefix(resultMsg,"Problem update(장애수정) 처리:");
				resultCd = problemLedgerMapper.updateProblem(param);
				if(resultCd != 1) {
					if(logger.isDebugEnabled()) LogUtil.prefix(resultMsg,"Problem update(장애수정) 처리 실패:problemLedgerMapper.updateProblem:result:",resultCd);
					throw new Exception(Util.join("Exception:ProblemLedgerService.updateProblem:problemLedgerMapper.updateProblem:resultCd:(", resultCd, ")")); 					
				}
				if(logger.isDebugEnabled()) LogUtil.prefix(resultMsg,"Problem(장애등록) update 처리 OK:problemId:", param.getProblemId());
			}
			
			//----------------------------------------------------------
			//2.List<ProblemAttatchFile>(장애 첨부파일 리스트) update
			//----------------------------------------------------------
			{
				if(logger.isDebugEnabled()) LogUtil.prefix(resultMsg,"장애 첨부파일 리스트 등록 시작");
				List<ProblemAttachFile> attatchFileList = param.getProblemAttachFile();
				//2.1.수정할 요건첨부파일 리스트가 존재하면 
				if(attatchFileList != null && attatchFileList.size() != 0){
					int cnt = 0;
					for (ProblemAttachFile paf : attatchFileList) {
						paf.setProblemId(param.getProblemId());
						paf.setRegDate(param.getRegDate());
						paf.setRegUser(param.getRegUser());
						
						if ("C".equals(paf.getFlag())) {
							resultCd = problemLedgerMapper.insertProblemAttachFile(paf);
						}
						else if ("D".equals(paf.getFlag())) {
							resultCd = problemLedgerMapper.deleteProblemAttachFile(paf);
						}
						
						if(resultCd != 1) {
							if(logger.isDebugEnabled()) LogUtil.prefix(resultMsg,"장애 첨부파일 리스트 등록 처리 실패:ProblemLedgerMapper.insertProblemAttatchFile:result:", resultCd);
							throw new Exception(Util.join("Exception:ProblemLedgerService.updateProblem:ProblemLedgerMapper.insertProblemAttatchFile:resultCd:(", resultCd, ")"));
						}
						cnt ++;
					}
					if(logger.isDebugEnabled()) LogUtil.prefix(resultMsg,"장애 첨부파일 리스트 등록 OK(총 장애 첨부파일 등록 건수 : ",cnt,")");
				}else{
					if(logger.isDebugEnabled()) LogUtil.prefix(resultMsg,"장애 첨부파일 리스트 등록 -> 등록할 장애 첨부파일 리스트가 존재하지 않는다.");
				}
			}
			
			//----------------------------------------------------------
			//3.List<Map> (장애 interface 목록) update
			//----------------------------------------------------------
			{
				if(logger.isDebugEnabled()) {LogUtil.prefix(resultMsg,"장애 인터페이스 리스트 수정 시작");}
				
				List<Map> problemInterfaceList = param.getProblemInterface();
				//Map<String, String> problemInterfaceMap = new HashMap<String, String>();
				if(problemInterfaceList != null && problemInterfaceList.size() != 0){
					int cnt = 0;
					for (Map<String, String> problemInterfaceMap : problemInterfaceList) {
						problemInterfaceMap.put("problemId", param.getProblemId());
						
						if ("C".equals((String) problemInterfaceMap.get("flag"))) {
							resultCd = problemLedgerMapper.insertProblemInterface(problemInterfaceMap);
						}
						else if ("D".equals((String) problemInterfaceMap.get("flag"))) {
							resultCd = problemLedgerMapper.deleteProblemInterface(problemInterfaceMap);
						}
						
						if(resultCd != 1) {
							if(logger.isDebugEnabled()) {LogUtil.prefix(resultMsg,"장애 인터페이스 리스트 등록 처리 실패:problemLedgerMapper.insertProblemInterface:result:", resultCd);}
							throw new Exception(Util.join("Exception:ProblemLedgerService.insertProblem:problemLedgerMapper.insertProblemInterface:resultCd:(", resultCd, ")"));
						}
						cnt++;
					}
					if(logger.isDebugEnabled()) {LogUtil.prefix(resultMsg,"장애 인터페이스 리스트 등록 OK(총 장애 인터페이스 등록 건수 : ",cnt,")");}
				}else{
					if(logger.isDebugEnabled()) {LogUtil.prefix(resultMsg,"장애 인터페이스 리스트 등록 -> 등록할 장애 인터페이스 리스트가 존재하지 않는다.");}
				}
			}
			
			
			return resultCd;
		}
		finally {
			if(logger.isDebugEnabled()) {
				resultMsg.append(LogUtil.bar(LogUtil.prefix("장애 등록 [ProblemLedgerService.updateProblem(problemLedger) 처리종료]")));
				logger.debug(resultMsg.toString());
			}
		}
	}
	
	@Transactional
	public int updateProblemStatus(String status, String modDate, String modId,String problemId) throws Exception {
		return problemLedgerMapper.updateProblemStatus(status, modDate, modId, problemId);
	}
	
	public List<Map> getProblemTemplateList(Map param) throws Exception {
		return problemLedgerMapper.getProblemTemplateList(param);
	}
	
	public List<Map> getProblemTemplateDetail(Map param) throws Exception {
		return problemLedgerMapper.getProblemTemplateDetail(param);
	}
	
	@Transactional
	public int insertProblemTemplate(ProblemTemplate param) throws Exception{
		return problemLedgerMapper.insertProblemTemplate(param);
	}
	
	@Transactional
	public int updateProblemTemplate(ProblemTemplate param) throws Exception{
		return problemLedgerMapper.updateProblemTemplate(param);
	}
	
	@Transactional
	public int deleteProblemTemplate(ProblemTemplate param) throws Exception{
		return problemLedgerMapper.deleteProblemTemplate(param);
	}		
}
