/*
 * Copyright 2013 ~ 2014 Mocomsys(dhkim, Solution TF), Inc.  All Rights Reserved.
 *
 * Please contact Mocomsys, Inc., NURITKUM SQUARE R&D TOWER, 11F DMC 1605, 
 * Sangam-Dong, Mapo-Gu, Seoul, 121-795 Korea or visit mocomsys.com 
 * if you need additional information or have any questions.
 */
package pep.per.mint.common.util;

/**
 * @author mint
 *
 */
public class UniqueKeyContext {
	
	public final static String KEY_SYSTEM_ID = "systemId"; 
	public final static String DELIMITER = "-";
	
 
	static ThreadLocal<UniqueKey> context = new ThreadLocal<UniqueKey>(){
		protected UniqueKey initialValue() {
			String systemId = System.getProperty(KEY_SYSTEM_ID, "SID1");
//			Object [] externalKeyArray = {systemId, Thread.currentThread().getName() , Thread.currentThread().getId()};
			Object [] externalKeyArray = {systemId, Util.join("TID", Thread.currentThread().getId())};
			boolean circular = false;
			UniqueKey key = new UniqueKey(DELIMITER, circular, externalKeyArray);
			return key;
		};
	};
	
	public static String getNextKey() throws Exception{
		return context.get().getNextKey();
	}
	
	public static String getNextKey(int formatLength, String padStr, int justify) throws Exception{
		return context.get().getNextKey(formatLength, padStr, justify);
	}
	
}
