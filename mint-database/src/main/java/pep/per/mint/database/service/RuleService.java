package pep.per.mint.database.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pep.per.mint.common.data.LogRule;
import pep.per.mint.common.data.MonitorRule;
import pep.per.mint.common.data.ParseSystemFieldRule;
import pep.per.mint.common.data.RestServiceUri;
import pep.per.mint.common.data.RestServiceUriMap;
import pep.per.mint.common.data.RouteByInterfaceRule;
import pep.per.mint.common.data.RouteInfo;
import pep.per.mint.common.data.RouteMap;
import pep.per.mint.common.data.RouteRule;
import pep.per.mint.common.data.Rule;
import pep.per.mint.common.data.RuleSet;
import pep.per.mint.common.data.RuleSetDetail;
import pep.per.mint.common.data.SourceSystemRule;
import pep.per.mint.common.data.UserDefineRule;
import pep.per.mint.common.exception.NotFoundException;
import pep.per.mint.common.exception.NotFoundRuleSetException;
import pep.per.mint.common.exception.NotImplementRuleException;
import pep.per.mint.common.util.Util;
import pep.per.mint.database.mybatis.dao.RuleDao;
import pep.per.mint.database.mybatis.persistance.RuleMapper;



@Service
public class RuleService {
	
	Logger logger = LoggerFactory.getLogger(RuleService.class);
	
	@Autowired
	RuleMapper ruleMapper;
	
	@Autowired
	RuleDao ruleDao;
	
	public RuleSet getRuleSet(Object id) throws NotFoundRuleSetException, NotImplementRuleException, NotFoundException{
		RuleSet rs = ruleMapper.getRuleSet(id);
		if(rs == null) throw new NotFoundRuleSetException(id);
		List<Rule> rules = rs.getRuleList(); 
		List<Rule> tmp = new ArrayList<Rule>();
		
		for(Rule rule : rules){
			//if(logger.isDebugEnabled())logger.debug(Util.join("rule info:-------------------------------"));
			//if(logger.isDebugEnabled())logger.debug(Util.join("rule:", Util.toJSONString(rule)));
			
			Rule r = null;
			switch(rule.getType()) {
			case Rule.RULE_TYPE_SYSTEM_FIELD_PARSING : 
				r = ruleMapper.getParseSystemFieldRule(rule.getId());
				if(r == null) throw new NotFoundException(Util.join("not found RULE_TYPE_SYSTEM_FIELD_PARSING ", " with ruleId[",rule.getId(), "]"));
				break;
			case Rule.RULE_TYPE_ROUTING : 
				r = ruleMapper.getRouteRule(rule.getId()); 
				if(r == null) throw new NotFoundException(Util.join("not found RULE_TYPE_ROUTING ", " with ruleId[",rule.getId(), "]"));
				break;
			case Rule.RULE_TYPE_ROUTING_BY_INT : 
				r = ruleMapper.getRouteByInterfaceRule(rule.getId()); 
				if(r == null) throw new NotFoundException(Util.join("not found RULE_TYPE_ROUTING_BY_INT ", " with ruleId[",rule.getId(), "]"));
				List<RouteInfo> routeInfos = ruleMapper.getRouteByInterfaceInfo(rule.getId());
				Map<String, String> routeMap = new LinkedHashMap<String, String>();
				for (RouteInfo routeInfo : routeInfos) {
					String key = (String)routeInfo.getId(); 
					String value = (String) routeInfo.getRouteId();
					routeMap.put(key, value);
				}
				((RouteByInterfaceRule)r).setRouteMap(routeMap);
				break;	
			case Rule.RULE_TYPE_USER_DEFINE : 
				r = ruleMapper.getUserDefineRule(rule.getId());
				if(r == null) throw new NotFoundException(Util.join("not found RULE_TYPE_USER_DEFINE ", " with ruleId[",rule.getId(), "]"));
				break;	
			case Rule.RULE_TYPE_MAPPING : 
				r = ruleMapper.getMapRule(rule.getId());
				if(r == null) throw new NotFoundException(Util.join("not found RULE_TYPE_MAPPING ", " with ruleId[",rule.getId(), "]"));
				break;	
			case Rule.RULE_TYPE_PARSING : 
				r = ruleMapper.getParseRule(rule.getId()); 
				if(r == null) throw new NotFoundException(Util.join("not found RULE_TYPE_PARSING ", " with ruleId[",rule.getId(), "]"));
				break;	
			case Rule.RULE_TYPE_RECOVERY : 
				r = ruleMapper.getRecoveryRule(rule.getId());
				if(r == null) throw new NotFoundException(Util.join("not found RULE_TYPE_RECOVERY ", " with ruleId[",rule.getId(), "]"));
				break;
			case Rule.RULE_TYPE_LOGGING : 
				r = ruleMapper.getLogRule(rule.getId()); 
				if(r == null) throw new NotFoundException(Util.join("not found RULE_TYPE_LOGGING ", " with ruleId[",rule.getId(), "]"));
				break;
			case Rule.RULE_TYPE_MONITORING : 
				r = ruleMapper.getMonitorRule(rule.getId()); 
				if(r == null) throw new NotFoundException(Util.join("not found RULE_TYPE_MONITORING ", " with ruleId[",rule.getId(), "]"));
				break;
			case Rule.RULE_TYPE_SOURCE_SYSTEM : 
				r = ruleMapper.getSourceSystemRule(rule.getId()); 
				if(r == null) throw new NotFoundException(Util.join("not found RULE_TYPE_SOURCE_SYSTEM ", " with ruleId[",rule.getId(), "]"));
				break;
			default :
				throw new NotImplementRuleException("rule[id("+ rule.getId() +"),type(" + rule.getType() + ")] is not implemented!");
			}
			
			//if(logger.isDebugEnabled())logger.debug(Util.join("2rule:", Util.toJSONString(rule)));
			//기존 룰정보에서 얻은 정보 세팅
			r.setType(rule.getType());
			r.setNextRuleId(rule.getNextRuleId());
			r.setNextRuleSetId(rule.getNextRuleSetId());
			r.setNextRuleSeq(rule.getNextRuleSeq());
			tmp.add(r);
			
			//if(logger.isDebugEnabled())logger.debug(Util.join("3rule:", Util.toJSONString(r)));
			//기존 룰정보에서 얻은 정보 세팅
		}
		
		rs.setRuleList(tmp);
		
		List<Rule> l = rs.getRuleList();
		for (Rule rule2 : l) {
			if(logger.isDebugEnabled())logger.debug(Util.join("4rule:", Util.toJSONString(rule2)));
		}
		return rs;
	}
	
	public List<Rule> selectRuleList(Object id) throws Exception{
		List<Rule> list = ruleMapper.selectRuleList(id);
		return list;
	}
	
	
	public void loadRuleSetData(
		List<RuleSet> ruleSets,
		List<RuleSetDetail> ruleSetDetails,
		List<Rule> rules,
		List<RouteMap> routeMaps,
		boolean rollbackMode
			) throws Exception{
		
		SqlSession session = null;
		try{
			session = ruleDao.openSession();
			
			
			ruleDao.upsertRuleSetList(session, ruleSets);
			for(Rule rule : rules){
				
				int type = rule.getType();
				switch(type){
				case Rule.RULE_TYPE_SYSTEM_FIELD_PARSING :
					ParseSystemFieldRule psfr = (ParseSystemFieldRule)rule;
					ruleDao.upsertParseSystemFieldRule(session, psfr);
					break;
				case Rule.RULE_TYPE_LOGGING :
					LogRule lr = (LogRule)rule;
					ruleDao.upsertLogRule(session, lr);
					break;
				case Rule.RULE_TYPE_MAPPING :
					throw new pep.per.mint.common.exception.UnsupportedTypeException("아직 지원하지 않는 룰타입(RULE_TYPE_MAPPING:"+ type +")입니다.");
				case Rule.RULE_TYPE_MONITORING :
					MonitorRule mr = (MonitorRule)rule;
					ruleDao.upsertMonitorRule(session, mr);
					break;
				case Rule.RULE_TYPE_PARSING:
					throw new pep.per.mint.common.exception.UnsupportedTypeException("아직 지원하지 않는 룰타입(RULE_TYPE_PARSING:"+ type +")입니다.");
				case Rule.RULE_TYPE_RECOVERY:
					throw new pep.per.mint.common.exception.UnsupportedTypeException("아직 지원하지 않는 룰타입(RULE_TYPE_RECOVERY:"+ type +")입니다.");
				case Rule.RULE_TYPE_ROUTING :
					RouteRule rr = (RouteRule)rule;
					ruleDao.upsertRouteRule(session, rr);
					break;
				case Rule.RULE_TYPE_ROUTING_BY_INT:
					RouteByInterfaceRule rbir = (RouteByInterfaceRule)rule;
					ruleDao.upsertRouteByInterfaceRule(session, rbir);
					break;
				case Rule.RULE_TYPE_SOURCE_SYSTEM:
					SourceSystemRule ssr = (SourceSystemRule)rule;
					ruleDao.upsertSourceSystemRule(session, ssr);
					break;
				case Rule.RULE_TYPE_USER_DEFINE:
					UserDefineRule udr = (UserDefineRule)rule;
					ruleDao.upsertUserDefineRule(session, udr);
					break;
				default :
					throw new pep.per.mint.common.exception.UnsupportedTypeException("지원하지 않는 룰타입("+ type +")입니다.");
				}
			}
			
			ruleDao.upsertRuleSetDetailList(session, ruleSetDetails);
			
			ruleDao.upsertRouteMapList(session, routeMaps);
			
			if(rollbackMode && session != null) {
				session.rollback();
			} else {
				session.commit();
			}
		}catch(Exception e){
			if(rollbackMode && session != null) {
				session.rollback();
			}
			throw e;
		}finally{
			if(session != null) session.close();
		}
		
	}
	
	
	public  MonitorRule getMonitorRule(String id) throws Exception{
		MonitorRule rule = ruleMapper.getMonitorRule(id);
		return rule;
	}

	
	
	public RestServiceUriMap getRestServiceUriMap()throws Exception{
		RestServiceUriMap map = new RestServiceUriMap();
		
		List<RestServiceUri> list = ruleMapper.getRestServiceUriList();
		for (RestServiceUri restServiceUri : list) {
			map.put(Util.toString(restServiceUri.getId()), restServiceUri);
		}
		return map;
	}
	
	
	 
	@Transactional
	public void insertRestServiceUriMap(RestServiceUriMap map) throws Exception{
		
		Map<String, RestServiceUri> m = map.getServiceUriMap();
		Collection<RestServiceUri> col = m.values();
	 
	    for (RestServiceUri uri : col) {
	    	ruleMapper.insertRestServiceUri(uri);
		}

	}
	
	 
	
	@Transactional
	public void insertRuleSet(RuleSet ruleSet) throws Exception{
		 
		ruleMapper.insertRuleSet(ruleSet);
		List<Rule> rules = ruleSet.getRuleList();
		
		for(int i  = 0 ; i < rules.size() ; i ++){
		//for(Rule rule : rules){
		
			Rule rule = rules.get(i);
			ruleMapper.insertRule(rule);
			
			int type = rule.getType();
			switch(type){
			case Rule.RULE_TYPE_SYSTEM_FIELD_PARSING :
				ParseSystemFieldRule psfr = (ParseSystemFieldRule)rule;
		
				ruleMapper.insertParseSystemFieldRule(psfr);
				break;
			case Rule.RULE_TYPE_LOGGING :
				LogRule lr = (LogRule)rule;
				ruleMapper.insertLogRule(lr);
				break;
			case Rule.RULE_TYPE_MAPPING :
				throw new pep.per.mint.common.exception.UnsupportedTypeException("아직 지원하지 않는 룰타입(RULE_TYPE_MAPPING:"+ type +")입니다.");
			case Rule.RULE_TYPE_MONITORING :
				MonitorRule mr = (MonitorRule)rule;
				ruleMapper.insertMonitorRule(mr);
				break;
			case Rule.RULE_TYPE_PARSING:
				throw new pep.per.mint.common.exception.UnsupportedTypeException("아직 지원하지 않는 룰타입(RULE_TYPE_PARSING:"+ type +")입니다.");
			case Rule.RULE_TYPE_RECOVERY:
				throw new pep.per.mint.common.exception.UnsupportedTypeException("아직 지원하지 않는 룰타입(RULE_TYPE_RECOVERY:"+ type +")입니다.");
			case Rule.RULE_TYPE_ROUTING :
				RouteRule rr = (RouteRule)rule;
				ruleMapper.insertRouteRule(rr);
				break;
			case Rule.RULE_TYPE_ROUTING_BY_INT:
				RouteByInterfaceRule rbir = (RouteByInterfaceRule)rule;
				ruleMapper.insertRouteByInterfaceRule(rbir);
				break;
			case Rule.RULE_TYPE_SOURCE_SYSTEM:
				SourceSystemRule ssr = (SourceSystemRule)rule;
				ruleMapper.insertSourceSystemRule(ssr);
				break;
			case Rule.RULE_TYPE_USER_DEFINE:
				UserDefineRule udr = (UserDefineRule)rule;
				ruleMapper.insertUserDefineRule(udr);
				break;
			default :
				throw new pep.per.mint.common.exception.UnsupportedTypeException("지원하지 않는 룰타입("+ type +")입니다.");
			}
			
			RuleSetDetail ruleSetDetail = new RuleSetDetail();
			ruleSetDetail.setRuleSetId(ruleSet.getId());
			ruleSetDetail.setRuleId(rule.getId());
			
			ruleSetDetail.setSeq(rule.getSeq());
			ruleSetDetail.setType(rule.getType());
			ruleSetDetail.setRegDate(rule.getRegDate());
			ruleSetDetail.setRegId(rule.getRegId());
			ruleSetDetail.setModDate(rule.getModDate());
			ruleSetDetail.setModId(rule.getModId());
			ruleSetDetail.setNextRuleSetId(ruleSet.getId());
			
			if (i < rules.size() - 1){
				Rule nextRule = rules.get(i+1);
				Object nextRuleId = nextRule.getId();
				int nextRuleSeq = nextRule.getSeq();
				ruleSetDetail.setNextRuleId(nextRuleId);
				ruleSetDetail.setNextRuleSeq(nextRuleSeq); 
			}else{
				 
			}
			ruleMapper.insertRuleSetDetail(ruleSetDetail);
		}
		
	}



	
}
