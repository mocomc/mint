/*
 * Copyright 2013 Mocomsys, Inc.  All Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * Please visit www.mocomsys.com if you need additional information or
 * have any questions.
 */
package pep.per.mint.common.rest;

import java.util.List;

import pep.per.mint.common.cache.CacheHandler;
import pep.per.mint.common.data.CacheableObject;
import pep.per.mint.common.data.Foo;
import pep.per.mint.common.data.Interface;
import pep.per.mint.common.data.MessageSet;
import pep.per.mint.common.data.RuleSet;
import pep.per.mint.common.data.SystemInfo;
import pep.per.mint.common.data.broker.ListenerGroup;
import pep.per.mint.common.data.broker.ServiceGroup;

/**
 * REST 서비스 클라이언트 구현을 위한 함수 정의부를 제공한다.  
 * @author Solution TF
 *
 */
public interface RestClient {
	
	public CacheHandler getCacheHandler();
	
	/**
	 * <blockquote>
	 * REST 서비스를 통해 원격객체를 조회한다.
	 * </blockquote>
	 * @param key
	 * @param restServiceUrl
	 * @param resultClass
	 * @return
	 */
	public <T extends CacheableObject> T getObject(Object key, String restServiceUrl, Class<T> resultClass) throws Exception; 
	
	/**
	 * <blockquote>
	 * REST 서비스를 통해 원격객체를 조회한다.
	 * </blockquote>
	 * @param restServiceUrl
	 * @param resultClass
	 * @return
	 */
	public <T extends CacheableObject> T getObject(String restServiceUrl, Class<T> resultClass); 
	
	
	/**
	 * <blockquote>
	 * caching된 object를 삭제후 REST서비스를 호출하여 조회 후 caching한다.
	 * </blockquote>
	 * @param restServiceUrl
	 * @param key
	 * @param resultClass
	 * @return
	 */
	public <T extends CacheableObject> T refreshObject(String restServiceUrl, Object key, Class<T> resultClass);
	
	/**
	 * <blockquote>
	 * getObject의 concrete wrapper 함수로 RuleSet object를 조회한다.
	 * </blockquote>
	 * @param ruleSetId
	 * @param restServiceUrl
	 * @return
	 */
	public RuleSet getRuleSet(Object ruleSetId, String restServiceUrl) throws Exception;
	
	/**
	 * <blockquote>
	 * getObject의 concrete wrapper 함수로 SystemInfo object를 조회한다.
	 * </blockquote>
	 * @param systemId
	 * @param restServiceUrl
	 * @return
	 */
	public SystemInfo getSystemInfo(Object systemId, String restServiceUrl) throws Exception;
	
	/**
	 * <blockquote>
	 * getObject의 concrete wrapper 함수로 getMessageSet object를 조회한다.
	 * </blockquote>
	 * @param msgSetId
	 * @param restServiceUrl
	 * @return
	 */
	public MessageSet getMessageSet(Object msgSetId, String restServiceUrl) throws Exception;
	
	/**
	 * <blockquote>
	 * getObject의 concrete wrapper 함수로 Interface object를 조회한다.
	 * </blockquote>
	 * @param interfaceId
	 * @param restServiceUrl
	 * @return
	 */
	public Interface getInterface(Object interfaceId, String restServiceUrl) throws Exception;
	
	/**
	 * <blockquote>
	 * Interface 리스트를 restServiceUrl에 포함된 인터페이스 이름으로 like 조회한다.
	 * </blockquote>
	 * @param restServiceUrl
	 * @return
	 */
	public List<Interface> getInterfaceListByName(String restServiceUrl) throws Exception;
	
	/**
	 * <blockquote>
	 * getObject의 concrete wrapper 함수로 ServiceGroup object를 조회한다.
	 * </blockquote>
	 * @param serviceGroupId
	 * @param restServiceUrl
	 * @return
	 */
	public ServiceGroup getServiceGroup(Object serviceGroupId, String restServiceUrl) throws Exception;
	
	/**
	 * <blockquote>
	 * getObject의 concrete wrapper 함수로 ListenerGroup object를 조회한다.
	 * </blockquote>
	 * @param listenerGroupId
	 * @param restServiceUrl
	 * @return
	 */
	public ListenerGroup getListenerGroup(Object listenerGroupId, String restServiceUrl) throws Exception;
	
	/**
	 * <blockquote>
	 * getObject의 concrete wrapper 함수로 Foo object를 조회한다.
	 * </blockquote>
	 * @param id
	 * @param restServiceUrl
	 * @return
	 */
	public Foo getFoo(String id, String restServiceUrl) throws Exception;
	
	
	
	/**
	 * 
	 * <blockquote>
	 * 원격객체를 레파지토리에 저장한다.
	 * </blockquote>
	 * @param restServiceUrl
	 * @param requestObject
	 * @param urlVariables
	 * @throws Exception
	 */
	public void updateObject(String restServiceUrl, Object requestObject, Object... urlVariables) throws Exception;
	
	
}
