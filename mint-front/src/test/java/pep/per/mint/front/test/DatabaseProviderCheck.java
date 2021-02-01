/**
 * Copyright 2013 ~ 2015 Mocomsys's guys(Minhwoa Bak, Sanghoon Lim, Deahun Ham, dhkim, Solution TF), Inc.  All Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 * [주의!]
 * 본 코드가 하자없이 완벽할거라 믿었다간 엄청난 욕을 먹고 그로 인한 스트레스로 병들거나 심하면 살기 싫어질 수도 있으니
 * 부디 살얼음판을 걷듯이 주의하여 사용하기 바란다.
 * 사용상 받을지 모를 스트레스 및 기타 피해에 대한 책임은 사용자 본인에게 있음을 명시한다. 부디 행운을 빈다.
 * Please Don't contact Mocomsys, Inc., NURITKUM SQUARE R&D TOWER, 11F DMC 1605,
 * Sangam-Dong, Mapo-Gu, Seoul, 121-795 Korea or visit mocomsys.com
 * if you need additional information or have any questions.
 */
package pep.per.mint.front.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

import pep.per.mint.common.data.basic.ComMessage;
import pep.per.mint.common.util.Util;
import pep.per.mint.front.controller.an.RequirementControllerTest;

/**
 * @author Solution TF
 *
 */
public class DatabaseProviderCheck {

	 // JDBC driver name and database URL
	   static final String JDBC_DRIVER = "com.tmax.tibero.jdbc.TbDriver";
	   static final String DB_URL = "jdbc:tibero:thin:@10.10.1.55:8629:tibero";

	   //  Database credentials
	   static final String USER = "iip";
	   static final String PASS = "iip";

	private static final Logger logger = LoggerFactory.getLogger(DatabaseProviderCheck.class);


	public static void main(String[] args){
		Connection conn = null;
		try{
			 //STEP 2: Register JDBC driver
		      Class.forName(JDBC_DRIVER);

		      //STEP 3: Open a connection
		      System.out.println("Connecting to database...");
		      conn = DriverManager.getConnection(DB_URL,USER,PASS);

		      System.out.println(conn.getMetaData().getDatabaseProductName());

		}catch(Exception e){
			e.printStackTrace();
		}

	}

}
