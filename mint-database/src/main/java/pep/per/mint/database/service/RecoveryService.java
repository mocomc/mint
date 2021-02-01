package pep.per.mint.database.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pep.per.mint.common.data.RecoveryFieldData;
import pep.per.mint.common.data.RecoveryFieldSet;
import pep.per.mint.common.data.RecoveryFieldSetDetail;
import pep.per.mint.common.data.RecoveryInfo;
import pep.per.mint.common.exception.NotFoundException;
import pep.per.mint.common.resource.ExceptionResource;
import pep.per.mint.database.mybatis.dao.RecoveryDao;
import pep.per.mint.database.mybatis.persistance.RecoveryMapper;

@Service
public class RecoveryService {
	
	@Autowired
	RecoveryMapper recoveryMapper;
	
	@Autowired
	RecoveryDao recoveryDao;
	
	
	/**
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public RecoveryFieldSet getRecoveryFieldSet(Object id) throws Exception {

		/**
		 * 01. RecoveryFieldSet 정보 추출
		 *     Table : TB_RUL_RECOVERY_FIELDSET 
		 */
		RecoveryFieldSet recoveryFieldSet = recoveryMapper.getRecoveryFieldSet(id);

		if( recoveryFieldSet == null ) {
			Object [] arguments = {getClass(), "getRecoveryFieldSet", RecoveryFieldSet.class, id};
			String errmsg = ExceptionResource.getString("DBE0001", arguments);
			throw new NotFoundException(errmsg);			
		}

		/**
		 * 02. RecoveryFieldSetDetail 정보 추출 
		 *    TB_RUL_RECOVERY_FIELDSET_DETAIL
		 */
		List<Map<String,Object>> recoveryFieldSetDetail = recoveryMapper.getRecoveryFieldSetDetail(id);
		
		recoveryFieldSet.setFieldPathList(recoveryFieldSetDetail);
		
		//
		
		
		return recoveryFieldSet;
	}
	
	public void insertRecoveryFieldSet(RecoveryFieldSet recoveryFieldSet) throws Exception {
		recoveryMapper.insertRecoveryFieldSet(recoveryFieldSet);
	}
	
	public void insertRecoveryFieldSetDetail(RecoveryFieldSetDetail recoveryFieldSetDetail) throws Exception {
		recoveryMapper.insertRecoveryFieldSetDetail(recoveryFieldSetDetail);
	}
	
	public void updateRecoveryFieldSet(RecoveryFieldSet recoveryFieldSet) throws Exception {
		recoveryMapper.updateRecoveryFieldSet(recoveryFieldSet);
	}	
	
	/**
	 * getRecoveryInfo
	 * @param recoverykey
	 * @return
	 * @throws Exception
	 */
	public RecoveryInfo getRecoveryInfo(Object recoverykey) throws Exception {
		RecoveryInfo recoveryInfo = recoveryMapper.getRecoveryInfo(recoverykey);
		
		if( recoveryInfo == null ) {
			Object [] arguments = {getClass(), "getRecoveryInfo", RecoveryInfo.class, recoverykey};
			String errmsg = ExceptionResource.getString("DBE0001", arguments);
			throw new NotFoundException(errmsg);
		}
	
		return recoveryInfo;
	}
	
	public void insertRecoveryInfo(RecoveryInfo recoveryInfo) throws Exception {
		recoveryMapper.insertRecoveryInfo(recoveryInfo);
	}
	
	public void deleteRecoveryInfo(RecoveryInfo recoveryInfo) throws Exception {
		recoveryMapper.deleteRecoveryInfo(recoveryInfo);
	}
	
	public void insertRecoveryFieldData(RecoveryFieldData recoveryFieldData) throws Exception {
		recoveryMapper.insertRecoveryFieldData(recoveryFieldData);
	}
	
	public void deleteRecoveryFieldData(RecoveryFieldData recoveryFieldData) throws Exception {
		recoveryMapper.deleteRecoveryFieldData(recoveryFieldData);
	}
	
//	public void insertRecoveryInfo(RecoveryInfo recoveryInfo) throws Exception{
//		SqlSession session = null;
//		try{
//			session = recoveryDao.openSession();
//			recoveryDao.insertRecoveryInfo(session, recoveryInfo);
//			session.commit();
//			//if(rollbackMode) session.rollback();
//			//else session.commit();
//		}catch(Exception e){
//			session.rollback();
//			throw e;
//		}finally{
//			if(session != null) session.close();
//		}
//	}
	
	


	

}
