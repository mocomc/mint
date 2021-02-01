/*
 * Copyright 2013 ~ 2014 Mocomsys(dhkim, Solution TF), Inc.  All Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 * Please contact Mocomsys, Inc., NURITKUM SQUARE R&D TOWER, 11F DMC 1605, 
 * Sangam-Dong, Mapo-Gu, Seoul, 121-795 Korea or visit mocomsys.com 
 * if you need additional information or have any questions.
*/
package pep.per.mint.common.message;

import java.util.Map;

import pep.per.mint.common.data.FieldDefinition;
import pep.per.mint.common.data.FieldPath;
import pep.per.mint.common.data.MessageSet;

/**
 * <blockquote>
 * <pre>
 * MessageSet의 특정 필드정의를 id, name, path 값을 키로 조회하는 함수를 제공하는 유틸리티 클래스
 * </pre> 
 * </blockquote>
 * @author Solution TF
 *
 */
public class MessageSetUtil {
	
	/**
	 * <blockquote>
	 * <pre>
	 * 필드패스에 해당하는 필드정의(FieldDefinition) 객체를 조회한다.
	 * <div style="background:#BDE12A">
	 * <B>[usage]</B>
	 * MessageSet msgSet = ...;
	 * String fieldPath = "a.b.c";
	 * FieldDefinition fieldDefinition = MessageSetUtil.getFieldDefinition(msgSet, fieldPath);//패스가 "a.b.c"인 FieldDefinition을 찾는다.
	 * System.out.println("This field name is " + fieldDefinition.getName());
	 * </div>
	 * </pre>
	 * </blockquote>
	 * @param msgSet {@link MessageSet} 객체
	 * @param path 조회할 필드정의 패스 String 값
	 * @return
	 * @throws Exception
	 */
	public static FieldDefinition getFieldDefinition(MessageSet msgSet, String path) throws Exception{
		FieldDefinition fd = null;
		Map<Object, FieldDefinition> fdm = msgSet.getFieldDefinitionPathMap();
		fd = fdm.get(path);
		return fd;
	}
	

	/**
	 * <blockquote>
	 * <pre>
	 * 필드패스에 해당하는 필드정의(FieldDefinition) 객체를 조회한다.
	 * <div style="background:#BDE12A">
	 * <B>[usage]</B>
	 * MessageSet msgSet = ...;
	 * FieldPath fieldPath = new FieldPath("a.b.c");
	 * FieldDefinition fieldDefinition = MessageSetUtil.getFieldDefinition(msgSet, fieldPath);//패스가 "a.b.c"인 FieldDefinition을 찾는다.
	 * System.out.println("This field name is " + fieldDefinition.getName());
	 * </div>
	 * </pre>
	 * </blockquote>
	 * @param msgSet {@link MessageSet} 객체
	 * @param path 조회할 필드정의 패스 {@link FieldPath} 객체
	 * @return
	 * @throws Exception
	 */
	public static FieldDefinition getFieldDefinition(MessageSet msgSet, FieldPath path) throws Exception{
		return getFieldDefinition(msgSet, path.toString());
	}
	
	
	
	
}
