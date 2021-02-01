package pep.per.mint.database.mybatis.persistance;

import java.util.List;

import pep.per.mint.common.data.LogRule;
import pep.per.mint.common.data.MapRule;
import pep.per.mint.common.data.MonitorRule;
import pep.per.mint.common.data.ParseRule;
import pep.per.mint.common.data.ParseSystemFieldRule;
import pep.per.mint.common.data.RecoveryRule;
import pep.per.mint.common.data.RestServiceUri;
import pep.per.mint.common.data.RouteByInterfaceRule;
import pep.per.mint.common.data.RouteInfo;
import pep.per.mint.common.data.RouteMap;
import pep.per.mint.common.data.RouteRule;
import pep.per.mint.common.data.Rule;
import pep.per.mint.common.data.RuleSet;
import pep.per.mint.common.data.RuleSetDetail;
import pep.per.mint.common.data.SourceSystemRule;
import pep.per.mint.common.data.UserDefineRule;

public interface RuleMapper {
	
	public RuleSet getRuleSet(Object id);
	
	public List<Rule> selectRuleList(Object id);
	
	public ParseSystemFieldRule getParseSystemFieldRule(Object id);
	
	public ParseRule getParseRule(Object id);
	
	public RouteRule getRouteRule(Object id);
	
	public RouteByInterfaceRule getRouteByInterfaceRule(Object id);
	
	public List<RouteInfo> getRouteByInterfaceInfo(Object id);

	public UserDefineRule getUserDefineRule(Object id);

	public LogRule getLogRule(Object id);

	public MonitorRule getMonitorRule(Object id);

	public SourceSystemRule getSourceSystemRule(Object id);
	
	public RecoveryRule getRecoveryRule(Object id);
	
	public MapRule getMapRule(Object id);
	
	public void insertRuleSet(RuleSet ruleSet) throws Exception;
	
	public void insertRuleSetDetail(RuleSetDetail ruleSetDetail) throws Exception;
	
	public void insertRule(Rule rule) throws Exception;
	
	public void insertParseSystemFieldRule(ParseSystemFieldRule rule) throws Exception;
	
	public void insertRouteRule(RouteRule rule) throws Exception;
	
	public void insertRouteByInterfaceRule(RouteByInterfaceRule rule) throws Exception;
	
	public void insertRouteMap(RouteMap routeMap) throws Exception;
	
	public void insertUserDefineRule(UserDefineRule rule) throws Exception;
	
	public void insertLogRule(LogRule rule) throws Exception;
	
	public void insertMonitorRule(MonitorRule rule) throws Exception;
	
	public void insertSourceSystemRule(SourceSystemRule rule) throws Exception;
	
	public void insertRecoveryRule(RecoveryRule rule) throws Exception;
	
	public void insertMapRule(MapRule rule) throws Exception;
	
	public List<RestServiceUri> getRestServiceUriList() throws Exception;
	
	public void insertRestServiceUri(RestServiceUri uri) throws Exception;
	
	public void deleteAllRestServiceUri() throws Exception;
	
}
