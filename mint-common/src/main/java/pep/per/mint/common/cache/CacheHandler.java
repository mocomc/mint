/*
 * Copyright 2013 Mocomsys, Inc.  All Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * Please visit www.mocomsys.com if you need additional information or
 * have any questions.
 */
package pep.per.mint.common.cache;

import java.util.Properties;


/**
 * runtime 환경에서 주요 데이터를 메모리상에 cache하기 위한 인터페이스 명세
 * 
 * @author Solution TF
 * @since mint 0.0.1
 *
 */
public interface CacheHandler {
	
	public static final String PK_LOCAL_IS_SAVE = "isSaveToLocal";
	
	public static final String PK_LOCAL_SAVE_HOME = "saveToLocalHome";
	
	public Properties getProperties();
	
	/**
	 * cache 메모리에서 데이터를  key 값을 통해 조회하여 리턴한다. 
	 * 
	 * @param key
	 * @return
	 */
	public Object getData(Object key);
	
	/**
	 * cache 메모리에 데이터를 저장한다.
	 *  
	 * @param data 저장 객체는 Cachable 인터페이스를 구현하여야 한다.
	 */
	public void putData(Cacheable data);
	
	/**
	 * cache 메모리에 저장된 데이터를 삭제한다.
	 * @param data
	 */
	public void removeData(Cacheable data);
	
	
	/**
	 * cache 메모리에 저장된 데이터를 key값으로 조회하여 삭제한다.
	 * @param key
	 */
	public void evict(Object key);
	 
	
}
