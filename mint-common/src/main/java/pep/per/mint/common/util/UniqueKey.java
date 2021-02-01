/*
 * Copyright 2013 ~ 2014 Mocomsys(dhkim, Solution TF), Inc.  All Rights Reserved.
 *
 * Please contact Mocomsys, Inc., NURITKUM SQUARE R&D TOWER, 11F DMC 1605, 
 * Sangam-Dong, Mapo-Gu, Seoul, 121-795 Korea or visit mocomsys.com 
 * if you need additional information or have any questions.
 */
package pep.per.mint.common.util;

import java.util.concurrent.atomic.AtomicLong;


/**
 * @author mint
 *
 */
public class UniqueKey {
	
	Object[] externalKeyArray;
	String externalKey = "";
	AtomicLong uniqueKey = new AtomicLong(0);
	String delim = "-";
	String key;
	boolean isCircular;
	 
	public UniqueKey(String delim, boolean isCircular, Object...externalKeyArray) {
		this.isCircular = isCircular;
		this.delim = delim;
		if(externalKeyArray != null){
			this.externalKeyArray = externalKeyArray;
		
			for (int i = 0 ; i < externalKeyArray.length ; i ++) {
				if(i == 0){
					this.externalKey = Util.join(this.externalKey, externalKeyArray[i]);
					continue;
				}
				this.externalKey = Util.join(this.externalKey, this.delim, externalKeyArray[i]);
			}
			
		} 
		this.key = Util.join(this.externalKey, this.delim, uniqueKey.addAndGet(1) );
	}
	 
	
	public String getKey(){
		return key;
	}
	
	public String getNextKey() throws Exception{
		if(Long.MAX_VALUE <= uniqueKey.get()){
			if(!isCircular) throw new Exception(Util.join("UniqueKey.getNextKey() reached max value[", Long.MAX_VALUE ,"]"));
			uniqueKey.set(1);
		}
		
		key = Util.isEmpty(externalKey) ?  Util.join(externalKey, uniqueKey.getAndAdd(1)):
				Util.join(externalKey, delim, uniqueKey.getAndAdd(1)) ;
		return key;
	}
	
	public final static int JUSTIFY_LEFT = 0;
	public final static int JUSTIFY_RIGHT = 1;
	public String getNextKey(int formatLength, String padStr, int justify)throws Exception{
		String rs = getNextKey();
		if(justify == JUSTIFY_LEFT){
			rs = Util.rightPad(rs, formatLength, padStr);
		}else{
			rs = Util.leftPad(rs, formatLength, padStr);
		}
		return rs;
	}
	
	public String getKey(int formatLength, String padStr, int justify){
		String rs = "";
		if(justify == JUSTIFY_LEFT){
			rs = Util.rightPad(key, formatLength, padStr);
		}else{
			rs = Util.leftPad(key, formatLength, padStr);
		}
		return rs;
	}
	
	@Override
	public String toString() {
		return key;
	}
	
	
}