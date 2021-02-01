/*
 * Copyright 2013 ~ 2014 Mocomsys(dhkim, Solution TF), Inc.  All Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 * Please contact Mocomsys, Inc., NURITKUM SQUARE R&D TOWER, 11F DMC 1605, 
 * Sangam-Dong, Mapo-Gu, Seoul, 121-795 Korea or visit mocomsys.com 
 * if you need additional information or have any questions.
*/
package pep.per.mint.common.message;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pep.per.mint.common.data.FieldPath;
import pep.per.mint.common.util.Util;

/**
 * @author Solution TF
 *
 */
public class MessageUtilTest {

	static Logger logger = LoggerFactory.getLogger(MessageUtilTest.class);

	public Message getMessage(){
		Message msg = new Message();
		
		Element root = msg.getMsgElement();
		
		Element body = new Element("body");
		root.addChild(body);
		
		Element company1 = new Element("company");
		body.addChild(company1);
		

		Element customers1 = new Element("customers");
		company1.addChild(customers1);
		
		Element customer1 = new Element("customer");
		customer1.addChild(new Element<String>("name", "customer1"));
		customer1.addChild(new Element<Integer>("age", 100));
		customers1.addChild(customer1);
		
		Element customer2 = new Element("customer");
		customer2.addChild(new Element<String>("name", "customer2"));
		customer2.addChild(new Element<Integer>("age", 90));
		customers1.addChild(customer2);
		
		
		
		Element company2 = new Element("company");
		body.addChild(company2);
		

		Element customers2 = new Element("customers");
		company2.addChild(customers2);
		
		Element customer3 = new Element("customer");
		customer3.addChild(new Element<String>("name", "customer3"));
		customer3.addChild(new Element<Integer>("age", 300));
		customers2.addChild(customer3);
		
		Element customer4 = new Element("customer");
		customer4.addChild(new Element<String>("name", "customer4"));
		customer4.addChild(new Element<Integer>("age", 400));
		customers2.addChild(customer4);
		
		
		
		System.out.println(Util.toJSONString(msg));
		return msg;
	}
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * Test method for {@link pep.per.mint.common.message.MessageUtil#getElement(pep.per.mint.common.message.Message, pep.per.mint.common.data.FieldPath)}.
	 */
	@Test
	public void testGetElement() {
		Message msg = getMessage();
		try {
	
			logger.debug(Util.join("element:",Util.toJSONString(MessageUtil.getElement(msg, new FieldPath("body.company[0].customers[0].customer[0]")))));
			logger.debug(Util.join("element:",Util.toJSONString(MessageUtil.getElement(msg, new FieldPath("body.company[0].customers[0].customer[1]")))));
			logger.debug(Util.join("element:",Util.toJSONString(MessageUtil.getElement(msg, new FieldPath("body.company[1].customers[0].customer[0]")))));
			logger.debug(Util.join("element:",Util.toJSONString(MessageUtil.getElement(msg, new FieldPath("body.company[1].customers[0].customer[1]")))));
		
			List<Element> elements = MessageUtil.getElements(msg, new FieldPath("body.company.customers"));
			logger.debug(Util.join("element:",Util.toJSONString(MessageUtil.getElements(elements.get(0), new FieldPath("customer.name")))));
			
			
		} catch (Exception e) {
			logger.error("",e);
		}
		
	}
	
	
	@Test
	public void testGetElementsByPathIndex (){
		Message msg = getMessage();
		try {
			List<Element> elements = MessageUtil.getElements(msg, new FieldPath("body.company[1].customers.customer[0]"));
			for (Element element : elements) {
				logger.debug(Util.join("element:",Util.toJSONString(element)));
			}
		} catch (Exception e) {
			logger.error("",e);
		}
	}
	
	@Test
	public void testCreateElement() {
		Message msg = getMessage();
		try {
			/*Element element = MessageUtil.createElement(msg, "body.company", false);
			logger.debug(Util.join("element(appendMode=false:",Util.toJSONString(element)));
			logger.debug(Util.join("msg(appendMode=false:",Util.toJSONString(msg)));*/
			
			Element element = MessageUtil.createElement(msg, "body.company.customers.customer", true);
			logger.debug(Util.join("element(appendMode=true:",Util.toJSONString(msg)));
			
			
			/*MessageUtil.createElement(element, "employees.employee", false);
			logger.debug(Util.join("msg:",Util.toJSONString(msg)));*/

		} catch (Exception e) {
			logger.error("",e);
		}
		
	}
	
	@Test
	public void testMain() {
		try{
			Message msg = new Message();
			
			Element root = msg.getMsgElement();
			
			Element body = new Element("body");
			root.addChild(body);
			
			Element company1 = new Element("company");
			body.addChild(company1);
			
	
			Element customers1 = new Element("customers");
			company1.addChild(customers1);
			
			Element customer1 = new Element("customer");
			customer1.addChild(new Element<String>("name", "customer1"));
			customer1.addChild(new Element<Integer>("age", 100));
			customers1.addChild(customer1);
			
			Element customer2 = new Element("customer");
			customer2.addChild(new Element<String>("name", "customer2"));
			customer2.addChild(new Element<Integer>("age", 90));
			customers1.addChild(customer2);
			
			
			
			Element company2 = new Element("company");
			body.addChild(company2);
			
	
			Element customers2 = new Element("customers");
			company2.addChild(customers2);
			
			Element customer3 = new Element("customer");
			customer3.addChild(new Element<String>("name", "customer3"));
			customer3.addChild(new Element<Integer>("age", 300));
			customers2.addChild(customer3);
			
			Element customer4 = new Element("customer");
			customer4.addChild(new Element<String>("name", "customer4"));
			customer4.addChild(new Element<Integer>("age", 400));
			customers2.addChild(customer4);
			
			
			
			System.out.println(Util.toJSONString(msg));
	
			long elapsed = System.currentTimeMillis();
			/*
			FieldPath path = new FieldPath("body.company.customers");
			 
			
			Element getCustomers = null;
			try {
				getCustomers = msg.getMsgElementFirst(path);
				List<Element> list  = getCustomers.getChildList("customer");
				for (Element customer : list) {
					System.out.println(Util.join("elapsed:",(System.currentTimeMillis() - elapsed),", ", customer.getName(),", name:",customer.getChildValueAtFirst("name"),", age:",customer.getChildValueAtFirst("age")));
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
			
			List<Element> customerList = MessageUtil.getElements(msg, "body.company.customers.customer");
			System.out.println(Util.join("getElement - elapsed:",(System.currentTimeMillis() - elapsed)));
			for (Element element : customerList) {
				System.out.println("customer:" + Util.toJSONString(element));
			}
			
			
			Element firstCustomer = MessageUtil.getElementFirst(msg,"body.company.customers.customer");
			if(firstCustomer != null){
				System.out.println("customer:" + Util.toJSONString(firstCustomer));
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	@Test
	public final void testGetFieldPath(){
			
		Message msg = getMessage();
		try {
			List<Element> elements = MessageUtil.getElements(msg, new FieldPath("body.company[1].customers.customer[0]"));
			for (Element element : elements) {
				logger.debug(Util.join("element:",Util.toJSONString(element)));
				FieldPath path = MessageUtil.getFieldPath(element);
				logger.debug(Util.join("path:",Util.toJSONString(path)));
				logger.debug(Util.join("path:",Util.toJSONString(element.getFieldPath())));
			}
		} catch (Exception e) {
			logger.error("",e);
		}
			
	}
	
	
	

}
