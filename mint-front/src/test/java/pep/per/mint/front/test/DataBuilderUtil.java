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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pep.per.mint.common.data.CacheableObject;
import pep.per.mint.common.util.Util;

/**
 * @author Solution TF
 *
 */
public class DataBuilderUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(DataBuilderUtil.class); 
	 
	
	static final String DEFAULT_LOCAL_HOME = "./src/main/webapp/WEB-INF/test-data";
		
	public static void saveToLocal(String saveToLocalHome, String fileName, Object obj){
		FileOutputStream fos = null;
		try{
			String json = Util.toJSONString(obj);
			File root = new File(saveToLocalHome);
			if(!root.exists()){
				root.mkdirs();
			}
			
			File current = new File(root,fileName);
			fos = new FileOutputStream(current, false);
			fos.write(json.getBytes());
			
		}catch(Exception e){
			if(logger.isErrorEnabled()) logger.error("",e);
		}finally{
			if(fos != null)
				try {
					fos.close();
				} catch (IOException e) {
					if(logger.isErrorEnabled()) logger.error("",e);
				}
		}
	}
	
 
	
}
