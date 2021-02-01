package pep.per.mint.database.mybatis.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;

import pep.per.mint.common.data.FieldSetDefinition;
import pep.per.mint.common.data.FieldSetDetail;
import pep.per.mint.common.data.FixedLengthFieldDefinition;
import pep.per.mint.common.data.MessageSet;
import pep.per.mint.common.data.MessageSetDetail;
import pep.per.mint.common.data.SystemField;

@Component
public class MessageDao extends AbstractDao {

	
	public void insertMessageSet(SqlSession session, MessageSet messageSet) throws Exception{
		String sqlName = "pep.per.mint.database.mybatis.persistance.MessageMapper.insertMessageSet";
		insert(session, messageSet, sqlName);
	}

	
	public void insertMessageSetList(SqlSession session, List<MessageSet> list) throws Exception{
		String sqlName = "pep.per.mint.database.mybatis.persistance.MessageMapper.insertMessageSet";
		insertList(session, list, sqlName);
	}

	public void upsertMessageSet(SqlSession session, MessageSet messageSet) throws Exception{
		String updateSqlName = "pep.per.mint.database.mybatis.persistance.MessageMapper.updateMessageSet";
		String insertSqlName = "pep.per.mint.database.mybatis.persistance.MessageMapper.insertMessageSet";
		upsert(session, messageSet, updateSqlName, insertSqlName);
	}

	
	public void upsertMessageSetList(SqlSession session, List<MessageSet> list) throws Exception{
		String updateSqlName = "pep.per.mint.database.mybatis.persistance.MessageMapper.updateMessageSet";
		String insertSqlName = "pep.per.mint.database.mybatis.persistance.MessageMapper.insertMessageSet";
		upsertList(session, list, updateSqlName, insertSqlName);
	}

	
	public void insertMessageSetDetailList(SqlSession session, List<MessageSetDetail> list) throws Exception{
		String sqlName = "pep.per.mint.database.mybatis.persistance.MessageMapper.insertMessageSetDetail";
		insertList(session, list, sqlName);
	}
	
	public void upsertMessageSetDetailList(SqlSession session, List<MessageSetDetail> list) throws Exception{
		String updateSqlName = "pep.per.mint.database.mybatis.persistance.MessageMapper.updateMessageSetDetail";
		String insertSqlName = "pep.per.mint.database.mybatis.persistance.MessageMapper.insertMessageSetDetail";
		upsertList(session, list, updateSqlName, insertSqlName);
	}

	public void insertFieldSetDefinitionList(SqlSession session, List<FieldSetDefinition> list) throws Exception{
		String sqlName = "pep.per.mint.database.mybatis.persistance.MessageMapper.insertFieldSetDefinition";
		insertList(session, list, sqlName);
	}
	
	
	public void upsertFieldSetDefinitionList(SqlSession session, List<FieldSetDefinition> list) throws Exception{
		String updateSqlName = "pep.per.mint.database.mybatis.persistance.MessageMapper.updateFieldSetDefinition";
		String insertSqlName = "pep.per.mint.database.mybatis.persistance.MessageMapper.insertFieldSetDefinition";
		upsertList(session, list, updateSqlName, insertSqlName);
	}
	
	public void insertFieldSetDetailList(SqlSession session, List<FieldSetDetail> list) throws Exception{
		String sqlName = "pep.per.mint.database.mybatis.persistance.MessageMapper.insertFieldSetDetail";
		insertList(session, list, sqlName);
	}
	
	public void upsertFieldSetDetailList(SqlSession session, List<FieldSetDetail> list) throws Exception{
		String updateSqlName = "pep.per.mint.database.mybatis.persistance.MessageMapper.updateFieldSetDetail";
		String insertSqlName = "pep.per.mint.database.mybatis.persistance.MessageMapper.insertFieldSetDetail";
		upsertList(session, list, updateSqlName, insertSqlName);
	}
	
	public void insertFixedLengthFieldDefinitionList(SqlSession session, List<FixedLengthFieldDefinition> list) throws Exception{
		String sqlNameOfFieldDefinition = "pep.per.mint.database.mybatis.persistance.MessageMapper.insertFieldDefinition";
		insertList(session, list, sqlNameOfFieldDefinition);
		String sqlNameOfFixedLengthFieldDefinition = "pep.per.mint.database.mybatis.persistance.MessageMapper.insertFixedLengthFieldDefinition";
		insertList(session, list, sqlNameOfFixedLengthFieldDefinition);
	}
	
	public void upsertFixedLengthFieldDefinitionList(SqlSession session, List<FixedLengthFieldDefinition> list) throws Exception{
		String updateSqlNameOfFieldDefinition = "pep.per.mint.database.mybatis.persistance.MessageMapper.updateFieldDefinition";
		String insertSqlNameOfFieldDefinition = "pep.per.mint.database.mybatis.persistance.MessageMapper.insertFieldDefinition";
		upsertList(session, list, updateSqlNameOfFieldDefinition, insertSqlNameOfFieldDefinition);
		
		String updateSqlNameOfFixedLengthFieldDefinition = "pep.per.mint.database.mybatis.persistance.MessageMapper.updateFixedLengthFieldDefinition";
		String insertSqlNameOfFixedLengthFieldDefinition = "pep.per.mint.database.mybatis.persistance.MessageMapper.insertFixedLengthFieldDefinition";
		upsertList(session, list, updateSqlNameOfFixedLengthFieldDefinition, insertSqlNameOfFixedLengthFieldDefinition);
	}
	
	public void insertSystemFieldList(SqlSession session, List<SystemField> list) throws Exception{
		String sqlName = "pep.per.mint.database.mybatis.persistance.MessageMapper.insertSystemField";
		insertList(session, list, sqlName);
	}
	
	public void upsertSystemFieldList(SqlSession session, List<SystemField> list) throws Exception{
		String updateSqlName = "pep.per.mint.database.mybatis.persistance.MessageMapper.updateSystemField";
		String insertSqlName = "pep.per.mint.database.mybatis.persistance.MessageMapper.insertSystemField";
		upsertList(session, list, updateSqlName, insertSqlName);
	}
	
}
