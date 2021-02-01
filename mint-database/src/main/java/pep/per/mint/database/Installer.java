/*
 * Copyright 2013 ~ 2014 Mocomsys(dhkim, Solution TF), Inc.  All Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 * Please contact Mocomsys, Inc., NURITKUM SQUARE R&D TOWER, 11F DMC 1605, 
 * Sangam-Dong, Mapo-Gu, Seoul, 121-795 Korea or visit mocomsys.com 
 * if you need additional information or have any questions.
*/
package pep.per.mint.database;

import java.util.Enumeration;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import pep.per.mint.common.accessory.LogVariables;
import pep.per.mint.common.util.Util;
import pep.per.mint.database.service.InstallService;

/**
 * @author Solution TF
 *
 */
public class Installer {

	public static final Logger logger = LoggerFactory.getLogger(Installer.class);

	/**
	 * 
	 */
	public Installer() {
		// TODO Auto-generated constructor stub
	}
	
	public static void main(String args[]){
		
		if(logger.isInfoEnabled()) logger.info(LogVariables.logSerperator2);
		if(logger.isInfoEnabled()) logger.info(Util.join(LogVariables.logPrefix, "mint-database Installer start"));
		if(logger.isInfoEnabled()) logger.info(LogVariables.logSerperator1);
		
		Properties systemProperties = System.getProperties();
		Enumeration<?> enumer = systemProperties.keys();
		
		if(logger.isInfoEnabled()) logger.info(LogVariables.logSerperator1);
		if(logger.isInfoEnabled()) logger.info(Util.join(LogVariables.logPrefix, "SYSTEM PROPERTIES"));
		if(logger.isInfoEnabled()) logger.info(LogVariables.logSerperator1);
		
		while(enumer.hasMoreElements()) {
			String key = (String)enumer.nextElement();
			String val = systemProperties.getProperty(key);
			if(logger.isInfoEnabled()) logger.info(Util.join(LogVariables.logPrefix, key , ":" , val));
		}
		
		if(logger.isInfoEnabled()) logger.info(LogVariables.logSerperator1);
		
		ConfigurableApplicationContext context = null;
		try{

			context = new ClassPathXmlApplicationContext("config/database-context.xml");
			InstallService installer = context.getBean(InstallService.class);
			installer.install();
			 
			
		}catch (Throwable t) {
			if(logger.isErrorEnabled()) logger.error(LogVariables.logSerperator1);
			if(logger.isErrorEnabled()) logger.error(Util.join(LogVariables.logPrefix, "exception trace"));
			if(logger.isErrorEnabled()) logger.error(LogVariables.logSerperator1);
			if(logger.isErrorEnabled()) logger.error(LogVariables.logPrefix, t);
			if(logger.isErrorEnabled()) logger.error(LogVariables.logSerperator1);
			if(logger.isErrorEnabled()) logger.error(Util.join(LogVariables.logPrefix, "에러나면 무조건 종료되게 해둔다...."));
			
			System.exit(-1);
			
		}finally{
			if(logger.isInfoEnabled()) logger.info(LogVariables.logSerperator1);
			if(logger.isInfoEnabled()) logger.info(Util.join(LogVariables.logPrefix, "mint-database Installer Class was reached finally block."));
		}
	}
}
