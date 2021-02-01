/*
 * Copyright 2013 ~ 2014 Mocomsys(dhkim, Solution TF), Inc.  All Rights Reserved.
 *
 * Please contact Mocomsys, Inc., NURITKUM SQUARE R&D TOWER, 11F DMC 1605, 
 * Sangam-Dong, Mapo-Gu, Seoul, 121-795 Korea or visit mocomsys.com 
 * if you need additional information or have any questions.
 */
package pep.per.mint.database.mybatis.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;

import pep.per.mint.common.data.LogRule;
import pep.per.mint.common.data.MonitorRule;
import pep.per.mint.common.data.ParseSystemFieldRule;
import pep.per.mint.common.data.RouteByInterfaceRule;
import pep.per.mint.common.data.RouteMap;
import pep.per.mint.common.data.RouteRule;
import pep.per.mint.common.data.Rule;
import pep.per.mint.common.data.RuleSet;
import pep.per.mint.common.data.RuleSetDetail;
import pep.per.mint.common.data.SourceSystemRule;
import pep.per.mint.common.data.UserDefineRule;

@Component
public class RuleDao extends AbstractDao {

	
	public void insertRuleSetBatch(List<RuleSet> list) throws Exception {
		String sqlName = "pep.per.mint.database.mybatis.persistance.RuleMapper.insertRuleSet";
		insertBatch(list, sqlName);
	}

	public void insertRuleSetDetailBatch(List<RuleSetDetail> list ) throws Exception{
		String sqlName = "pep.per.mint.database.mybatis.persistance.RuleMapper.insertRuleSetDetail";
		insertBatch(list, sqlName);
	}
	
	public void insertParseSystemFieldRuleBatch(List<ParseSystemFieldRule> list ) throws Exception{
		String sqlName = "pep.per.mint.database.mybatis.persistance.RuleMapper.insertParseSystemFieldRule";
		insertBatch(list, sqlName);
	}
	
	public void insertRuleSet(SqlSession session, RuleSet ruleSet) throws Exception{
		String sqlName = "pep.per.mint.database.mybatis.persistance.RuleMapper.insertRuleSet";
		insert(session, ruleSet, sqlName);
	}
	
	public void insertRuleSetList(SqlSession session, List<RuleSet> list) throws Exception{
		String sqlName = "pep.per.mint.database.mybatis.persistance.RuleMapper.insertRuleSet";
		insertList(session, list, sqlName);
	}
	
	public void upsertRuleSet(SqlSession session, RuleSet ruleSet) throws Exception{
		String updateSqlName = "pep.per.mint.database.mybatis.persistance.RuleMapper.updateRuleSet";
		String insertSqlName = "pep.per.mint.database.mybatis.persistance.RuleMapper.insertRuleSet";
		upsert(session, ruleSet, updateSqlName, insertSqlName);
	}
	
	public void upsertRuleSetList(SqlSession session, List<RuleSet> list) throws Exception{
		String updateSqlName = "pep.per.mint.database.mybatis.persistance.RuleMapper.updateRuleSet";
		String insertSqlName = "pep.per.mint.database.mybatis.persistance.RuleMapper.insertRuleSet";
		upsertList(session, list, updateSqlName, insertSqlName);
	}
	
	public void insertRuleSetDetail(SqlSession session, RuleSetDetail ruleSetDetail) throws Exception{
		String sqlName = "pep.per.mint.database.mybatis.persistance.RuleMapper.insertRuleSetDetail";
		insert(session, ruleSetDetail, sqlName);
	}
	
	public void insertRuleSetDetailList(SqlSession session,List<RuleSetDetail> list ) throws Exception{
		String sqlName = "pep.per.mint.database.mybatis.persistance.RuleMapper.insertRuleSetDetail";
		insertList(session, list, sqlName);
	}
	
	public void upsertRuleSetDetail(SqlSession session, RuleSetDetail ruleSetDetail) throws Exception{
		String updateSqlName = "pep.per.mint.database.mybatis.persistance.RuleMapper.updateRuleSetDetail";
		String insertSqlName = "pep.per.mint.database.mybatis.persistance.RuleMapper.insertRuleSetDetail";
		upsert(session, ruleSetDetail, updateSqlName, insertSqlName);
	}
	
	public void upsertRuleSetDetailList(SqlSession session,List<RuleSetDetail> list ) throws Exception{
		String updateSqlName = "pep.per.mint.database.mybatis.persistance.RuleMapper.updateRuleSetDetail";
		String insertSqlName = "pep.per.mint.database.mybatis.persistance.RuleMapper.insertRuleSetDetail";
		upsertList(session, list, updateSqlName, insertSqlName);
	}
	
	
	public void insertRule(SqlSession session, Rule rule) throws Exception{
		String sqlName = "pep.per.mint.database.mybatis.persistance.RuleMapper.Rule";
		insert(session, rule, sqlName);
	}
	
	public void upsertParseRule(SqlSession session, Rule rule) throws Exception{
		String updateSqlName = "pep.per.mint.database.mybatis.persistance.RuleMapper.updateRule";
		String insertSqlName = "pep.per.mint.database.mybatis.persistance.RuleMapper.insertRule";
		upsert(session, rule, updateSqlName, insertSqlName);
	}
	
	public void insertParseSystemFieldRule(SqlSession session, ParseSystemFieldRule rule) throws Exception{
		String sqlName = "pep.per.mint.database.mybatis.persistance.RuleMapper.insertParseSystemFieldRule";
		insert(session, rule, sqlName);
	}

	public void upsertParseSystemFieldRule(SqlSession session, ParseSystemFieldRule rule) throws Exception{
		String updateSqlName = "pep.per.mint.database.mybatis.persistance.RuleMapper.updateParseSystemFieldRule";
		String insertSqlName = "pep.per.mint.database.mybatis.persistance.RuleMapper.insertParseSystemFieldRule";
		upsert(session, rule, updateSqlName, insertSqlName);
	}

	
	public void insertRouteRule(SqlSession session, RouteRule rule) throws Exception{
		String sqlName = "pep.per.mint.database.mybatis.persistance.RuleMapper.insertRouteRule";
		insert(session, rule, sqlName);
	}

	public void upsertRouteRule(SqlSession session, RouteRule rule) throws Exception{
		String updateSqlName = "pep.per.mint.database.mybatis.persistance.RuleMapper.updateRouteRule";
		String insertSqlName = "pep.per.mint.database.mybatis.persistance.RuleMapper.insertRouteRule";
		upsert(session, rule, updateSqlName, insertSqlName);
	}

	
	public void insertRouteByInterfaceRule(SqlSession session, RouteByInterfaceRule rule) throws Exception{
		String sqlName = "pep.per.mint.database.mybatis.persistance.RuleMapper.insertRouteByInterfaceRule";
		insert(session, rule, sqlName);
	}

	public void upsertRouteByInterfaceRule(SqlSession session, RouteByInterfaceRule rule) throws Exception{
		String updateSqlName = "pep.per.mint.database.mybatis.persistance.RuleMapper.updateRouteByInterfaceRule";
		String insertSqlName = "pep.per.mint.database.mybatis.persistance.RuleMapper.insertRouteByInterfaceRule";
		upsert(session, rule, updateSqlName, insertSqlName);
	}

	
	public void insertRouteMapList(SqlSession session, List<RouteMap> list) throws Exception{
		String sqlName = "pep.per.mint.database.mybatis.persistance.RuleMapper.insertRouteMap";
		insertList(session, list, sqlName);
	}
	
	public void upsertRouteMapList(SqlSession session, List<RouteMap> list) throws Exception{
		String updateSqlName = "pep.per.mint.database.mybatis.persistance.RuleMapper.updateRouteMap";
		String insertSqlName = "pep.per.mint.database.mybatis.persistance.RuleMapper.insertRouteMap";
		upsertList(session, list, updateSqlName, insertSqlName);
	}

	
	public void insertUserDefineRule(SqlSession session, UserDefineRule rule) throws Exception{
		String sqlName = "pep.per.mint.database.mybatis.persistance.RuleMapper.insertUserDefineRule";
		insert(session, rule, sqlName);
	}
	
	public void upsertUserDefineRule(SqlSession session, UserDefineRule rule) throws Exception{
		String updateSqlName = "pep.per.mint.database.mybatis.persistance.RuleMapper.updateUserDefineRule";
		String insertSqlName = "pep.per.mint.database.mybatis.persistance.RuleMapper.insertUserDefineRule";
		upsert(session, rule, updateSqlName, insertSqlName);
	}

	
	public void insertLogRule(SqlSession session, LogRule rule) throws Exception{
		String sqlName = "pep.per.mint.database.mybatis.persistance.RuleMapper.insertLogRule";
		insert(session, rule, sqlName);
	}
	
	public void upsertLogRule(SqlSession session, LogRule rule) throws Exception{
		String updateSqlName = "pep.per.mint.database.mybatis.persistance.RuleMapper.updateLogRule";
		String insertSqlName = "pep.per.mint.database.mybatis.persistance.RuleMapper.insertLogRule";
		upsert(session, rule, updateSqlName, insertSqlName);
	}
	
	public void insertMonitorRule(SqlSession session, MonitorRule rule) throws Exception{
		String sqlName = "pep.per.mint.database.mybatis.persistance.RuleMapper.insertMonitorRule";
		insert(session, rule, sqlName);
	}

	public void upsertMonitorRule(SqlSession session, MonitorRule rule) throws Exception{
		String updateSqlName = "pep.per.mint.database.mybatis.persistance.RuleMapper.updateMonitorRule";
		String insertSqlName = "pep.per.mint.database.mybatis.persistance.RuleMapper.insertMonitorRule";
		upsert(session, rule, updateSqlName, insertSqlName);
	}

	public void insertSourceSystemRule(SqlSession session, SourceSystemRule rule) throws Exception{
		String sqlName = "pep.per.mint.database.mybatis.persistance.RuleMapper.insertSourceSystemRule";
		insert(session, rule, sqlName);
	}
	
	public void upsertSourceSystemRule(SqlSession session, SourceSystemRule rule) throws Exception{
		String updateSqlName = "pep.per.mint.database.mybatis.persistance.RuleMapper.updateSourceSystemRule";
		String insertSqlName = "pep.per.mint.database.mybatis.persistance.RuleMapper.insertSourceSystemRule";
		upsert(session, rule, updateSqlName, insertSqlName);
	}
	
}
