package pep.per.mint.database.mybatis.persistance;

import java.util.List;
import java.util.Map;

import pep.per.mint.common.data.RecoveryFieldData;
import pep.per.mint.common.data.RecoveryFieldSet;
import pep.per.mint.common.data.RecoveryFieldSetDetail;
import pep.per.mint.common.data.RecoveryInfo;

public interface RecoveryMapper {
	
	public RecoveryFieldSet getRecoveryFieldSet(Object id);
	public List<Map<String,Object>> getRecoveryFieldSetDetail(Object id);
	public RecoveryInfo getRecoveryInfo(Object recoveryKey);
	
	public void insertRecoveryFieldSet(RecoveryFieldSet recoveryFieldSet);
	public void updateRecoveryFieldSet(RecoveryFieldSet recoveryFieldSet);
	public void deleteRecoveryFieldSet(RecoveryFieldSet recoveryFieldSet);
	
	public void insertRecoveryFieldSetDetail(RecoveryFieldSetDetail recoveryFieldSetDetail);
	public void updateRecoveryFieldSetDetail(RecoveryFieldSetDetail recoveryFieldSetDetail);
	public void deleterecoveryFieldSetDetail(RecoveryFieldSetDetail recoveryFieldSetDetail);
	
	public void insertRecoveryInfo(RecoveryInfo recoveryInfo);
	public void deleteRecoveryInfo(RecoveryInfo recoveryInfo);
	
	public void insertRecoveryFieldData(RecoveryFieldData recoveryFieldData);
	public void deleteRecoveryFieldData(RecoveryFieldData recoveryFieldData);
}
