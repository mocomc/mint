/*
 * Copyright 2013 Mocomsys, Inc.  All Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * Please visit www.mocomsys.com if you need additional information or
 * have any questions.
 */
package pep.per.mint.common.cache;

import java.io.Serializable;

/**
 * cache 메모리에 저장될 데이터 객체가 구현해야할 인터페이스 정의
 * 
 * @author Solution TF
 *
 */
public interface Cacheable extends Serializable {
	
	/**
	 * cache 메모리에 저장될때 키값으로 사용될 값을 리턴한다.
	 * @return
	 */
	public Object getIdentifier();
	
	/**
	 * cache 메모리에서 삭제되어도 될지 여부를 리턴한다.
	 * @return
	 */
	public boolean isExpired();
}
