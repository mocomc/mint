/*
 * Copyright 2013 ~ 2014 Mocomsys(dhkim, Solution TF), Inc.  All Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 * Please contact Mocomsys, Inc., NURITKUM SQUARE R&D TOWER, 11F DMC 1605, 
 * Sangam-Dong, Mapo-Gu, Seoul, 121-795 Korea or visit mocomsys.com 
 * if you need additional information or have any questions.
*/
package pep.per.mint.common.monitor;


//import org.codehaus.jackson.type.TypeReference;
import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.core.type.TypeReference;

import pep.per.mint.common.data.ParseRule;
import pep.per.mint.common.data.RouteRule;
import pep.per.mint.common.util.Util;

/**
 * @author Solution TF
 *
 */
public class ApplicationEventTest {

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test()  {
		
		try{
			
		 	String eventKey = "1";
			MonitorApplication source = new RuleHandlerNodeApplication("RuleHandleNode", "RuleHandleNode");
			ApplicationData<RouteRule> data = new ApplicationData<RouteRule>();
			RouteRule rr = new RouteRule();
			rr.setId("routeRule");
			rr.setName("RouteRule123456789");
			data.setUserData(rr);
			long eventTime = System.currentTimeMillis();
			ApplicationEvent<RouteRule> ae = new ApplicationEvent<RouteRule>(eventKey, source, data, eventTime);
			
			System.out.println(Util.join("source name:", ae.getSource().getName()));
			String json = Util.toJSONString(ae);
			System.out.println(Util.join("ApplicationEvent:", json));
			
			TypeReference<ApplicationEvent<RouteRule>> type = new TypeReference<ApplicationEvent<RouteRule>>() {};
			
			ApplicationEvent<RouteRule> e = Util.jsonToObject(json, type);
			
			RouteRule r = e.getData().getUserData();
			System.out.println(Util.join("User Data class:", r.getClass()));
			System.out.println(Util.join("Route name:", r.getName()));
			
			
			ParseRule pr = new ParseRule();
			pr.setName("parsing");
			String js = Util.toJSONString(pr);
			System.out.println(Util.join("parsing rule:", js));
			ParseRule pr2 = Util.jsonToObject(js, ParseRule.class);
			System.out.println(Util.join("Parsing rule name:", pr2.getName()));
			
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
