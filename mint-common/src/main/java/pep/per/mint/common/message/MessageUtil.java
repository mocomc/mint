/*
 * Copyright 2013 Mocomsys, Inc.  All Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * Please visit www.mocomsys.com if you need additional information or
 * have any questions.
 */
package pep.per.mint.common.message;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.SerializationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pep.per.mint.common.data.FieldPath;
import pep.per.mint.common.data.map.MapPath;

/**
 * <blockquote>
 * <pre>
 * 파싱된 필드값의 논리구조를 표현한 Message 인스턴스의 트리구조를 탐색하거나 트리구조에 특정 필드 엘리먼트를 
 * 추가할수 있도록 하는 함수를 제공하는 유틸리티 클래스
 * </pre> 
 * </blockquote>
 * @author Solution TF
 *
 */
public class MessageUtil {

	public static final Logger logger = LoggerFactory.getLogger(MessageUtil.class);
	
	 
	/**
	 * <blockquote>
	 * <pre>
	 * 필드패스에 지정된 특정 인덱스의 Element 를 검색한다. 인덱스가 지정되지 않은 패스에 대해서는 인덱스값은 0으로 대체된다.
	 * 필드패스에 인덱스 지정 예)   
	 * FieldPath path = new FieldPath("a.b[1]","c[2]");//부모패스가 a.b[1]이고 현재 부모패스를 기준으로 패스가 c[2]인 FieldPath 객체
	 * 위의 path 객체에서 전체 패스는 "a.b[1].c[2]"이며 인덱스가 지정되지 않은 a의 인덱스 값은 0으로 지정되어 전체 패스 값은
	 * "a[0].b[1].c[2]"로 처리된다. 아래 데이터 트리는 a 에서 c까지의 트리패스를 도식화한 것이다.
	 * 붉은색으로 표시된 부분이 검색된 패스이다.
	 * <table>
	 * <tr><td align="center" colspan="6">[Message tree]</td></tr>
	 * <tr><td align="center" colspan="6" style="color:red">a(a[0])</td></tr>
	 * <tr><td align="center"  colspan="3">b[0]</td><td align="center"  colspan="3" style="color:red">b[1]</td></tr>
	 * <tr><td>c[0]</td><td>c[1]</td><td>c[2]</td><td>c[0]</td><td>c[1]</td><td style="color:red">c[2]</td></tr>
	 * </table>
	 * 
	 * <div style="background:#BDE12A">
	 * <B>[usage]</B>
	 * Message msg = ...;
	 * FieldPath fieldPath = new FieldPath("a.b[1].c[2]");
	 * Element element = MessageUtil.getElement(msg, fieldPath);//패스가 "a.b[1].c[2]"인 Element를 찾는다.
	 * </div>
	 * 
	 * </pre>
	 * </blockquote>
	 * @param msg 검색하고자하는 필드값을 가진 Message 객체
	 * @param fieldPath 검색하고자하는 필드값의 패스를 표현한 FieldPath 객체
	 * @return
	 * @throws Exception
	 */
	public static Element getElement(Message msg, FieldPath fieldPath) throws Exception{
		Element element = null;
		List<String> pathList = fieldPath.getPathList();
		
		Element rootElement = msg.getMsgElement();
		
		int currentPathIndex = 0;

		Path path = PathUtil.getPath(pathList.get(currentPathIndex));
		String name = path.getName();
		int index = path.getIndex();
		
		List<Element> children = rootElement.getChildList(name);
		if(children == null || children.size() == 0 || index > children.size() - 1) return null;
		
		Element child = children.get(index == Path.INDEX_ALL ? 0 : index);
		
		
		int pathSize = pathList.size();
		if(pathSize <= 1){
			element = child;
		}else{
			++ currentPathIndex;
			element = getElement(child, pathList, currentPathIndex); 
		}
		
		return element;
	}
	
	
	/**
	 * @param child
	 * @param pathList
	 * @param currentPathIndex
	 * @return
	 * @throws Exception 
	 */
	private static Element getElement(Element element, List<String> pathList, int currentPathIndex) throws Exception {
		Element child = null;
		String pathValue = pathList.get(currentPathIndex);
		Path path = PathUtil.getPath(pathValue);
		String name = path.getName();
		int index = path.getIndex();
		List<Element> children = element.getChildList(name);
		
		if(children == null || children.size() == 0 || index > children.size() - 1) return null;
		
		child = children.get(index == Path.INDEX_ALL ? 0 : index);
		
		
		int pathSize = pathList.size();
		if(pathSize <= 1){
			return child;
		}else{
			
		}
		
		
		if(pathSize - 1 <= currentPathIndex){
			 return child;
		}else{
			++ currentPathIndex;
			child = getElement(child, pathList, currentPathIndex); 
		}
		
		
		return child;
	}

	/**
	 * <blockquote>
	 * <pre>
	 * 필드패스에 지정된 모든 Element의 리스트를 검색하여 리턴한다.
	 * 예를 들어 Message 내에서 검색하려는 필드 패스가 "a.b.c"라하고 메시지트리는 아래와 같다면,  
	 * <table>
	 * <tr><td align="center" colspan="6">[Message tree]</td></tr>
	 * <tr><td align="center" colspan="6">a(a[0])</td></tr>
	 * <tr><td align="center"  colspan="3">b[0]</td><td align="center"  colspan="3">b[1]</td></tr>
	 * <tr><td>c[0]</td><td>c[1]</td><td>c[2]</td><td>c[0]</td><td>c[1]</td><td>c[2]</td></tr>
	 * </table>
	 * 
	 * 아래와 같은 함수 호출 결과로 3개의 최하위 엘리먼트로 구성된 List를 결과값으로 얻게된다. 
	 * <div style="background:#BDE12A">
	 * <B>[usage]</B>
	 * Message msg = ...;
	 * FieldPath fieldPath = new FieldPath("a.b.c");
	 * List&lt;Element&gt; elements = MessageUtil.getElements(msg, fieldPath);
	 * System.out.println("The size of elements is " + elements.size());//This line print "The size of elements is 3"
	 * </div>
	 * </pre>
	 * </blockquote>
	 * 
	 * @param msg 검색하고자하는 필드값을 가진 Message 객체
	 * @param fieldPath 검색하고자하는 필드값의 패스를 표현한 FieldPath 객체
	 * @return
	 * @throws Exception
	 */
	public static List<Element> getElements(Message msg, FieldPath fieldPath) throws Exception{
		List<Element> list = null;
		
		List<String> pathList = fieldPath.getPathList();

		int pathSize = pathList.size();
		
		
		
		Element rootElement = msg.getMsgElement();
		
		if(pathSize == 1 && FieldPath.ROOT.equals(fieldPath.toString())){
			list = new ArrayList<Element>();
			list.add(rootElement);
			return list;
		} 
		
		int currentPathIndex = 0;
		 
		String pathValue = pathList.get(currentPathIndex);
		Path path = PathUtil.getPath(pathValue);
		String name = path.getName();
		int index = path.getIndex();
		
		List<Element> children = rootElement.getChildList(name);
		
		if(index == Path.INDEX_ALL){
			
		}else{
			List<Element> tmp = new ArrayList<Element>();
			Element child = children.get(index);
			tmp.add(child);
			children = tmp;
		}
		
		if(pathSize <= 1){
			list = children;
		}else{
			list = new ArrayList<Element>();
			++ currentPathIndex;
			for (Element child : children) {
				List<Element> childList = getElements(child, pathList, currentPathIndex); 
				if(childList != null) list.addAll(childList);
			}
		}
		
		return list;
	}
	 
	/**
	 * <blockquote>
	 * <pre>
	 * 필드패스에 지정된 모든 Element의 리스트를 검색하여 리턴한다.
	 * 예를 들어 Message 내에서 검색하려는 필드 패스가 "a.b.c"라하고 메시지트리는 아래와 같다면,  
	 * <table>
	 * <tr><td align="center" colspan="6">[Message tree]</td></tr>
	 * <tr><td align="center" colspan="6">a(a[0])</td></tr>
	 * <tr><td align="center"  colspan="3">b[0]</td><td align="center"  colspan="3">b[1]</td></tr>
	 * <tr><td>c[0]</td><td>c[1]</td><td>c[2]</td><td>c[0]</td><td>c[1]</td><td>c[2]</td></tr>
	 * </table>
	 * 아래와 같은 함수 호출 결과로 3개의 최 하위 엘리먼트로 구성된 List를 결과 값으로 얻게된다. 
	 * <div style="background:#BDE12A">
	 * <B>[usage]</B>
	 * Message msg = ...;
	 * String fieldPath = "a.b.c";
	 * List&lt;Element&gt; elements = MessageUtil.getElements(msg, fieldPath);
	 * System.out.println("The size of elements is " + elements.size());//This line print "The size of elements is 3"
	 * </div>
	 * </pre>
	 * </blockquote>
	 * 
	 * @param msg 검색하고자하는 필드값을 가진 Message 객체
	 * @param fieldPath 검색하고자하는 필드값의 패스를 표현한 String 객체
	 * @return
	 * @throws Exception
	 */
	public static List<Element> getElements(Message msg, String pathStr) throws Exception{
		FieldPath filePath = new FieldPath(pathStr);
		return getElements(msg,filePath);
	}

	/**
	 * @param element
	 * @param pathList
	 * @param currentPathIndex
	 * @return
	 */
	private static List<Element> getElements(Element element, List<String> pathList, int currentPathIndex) throws Exception {
		List<Element> list = null;
		 
		String pathValue = pathList.get(currentPathIndex);
		Path path = PathUtil.getPath(pathValue);
		String name = path.getName();
		int index = path.getIndex();
		
		
		List<Element> children = element.getChildList(name);
		if(index == Path.INDEX_ALL){
			
		}else{
			List<Element> tmp = new ArrayList<Element>();
			Element child = children.get(index);
			tmp.add(child);
			children = tmp;
		}

		int pathSize = pathList.size();
		if(pathSize - 1 <= currentPathIndex){
			list = children;
		}else{
			list = new ArrayList<Element>();
			++ currentPathIndex;
			for (Element child : children) {
				List<Element> childList = getElements(child, pathList, currentPathIndex); 
				if(childList != null) list.addAll(childList);
			}
		}
		return list;
	}
	
	/**
	 * <blockquote>
	 * <pre>
	 * Message 로 부터 검색되어 얻어진 Element 객체의 root로부터 패스를 얻어는다.
	 * <div style="background:#BDE12A">
	 * <B>[usage]</B>
	 * 
	 * Message msg = parser.parse(b, log);
	 * List&lt;Element&gt; as = MessageUtil.getElements(msg,"a.b.c");
	 * Element a = as.get(0);
	 * ...
	 * ... 
	 * FieldPath path = MessageUtil.getFieldPath(a);
	 * System.out.println("The path of element a:" + path.toString());//This line print "The path of element a:a.b.c";
	 * </div>
	 * </pre>
	 * </blockquote>
	 * 
	 * @param element Element 객체
	 * @return
	 */
	public static FieldPath getFieldPath(Element element){
		FieldPath fieldPath = new FieldPath();
		List<String>pathList = fieldPath.getPathList();
		pathList.add(element.getName());
		
		Element parent = element.getParent();
		if(parent != null && !parent.isRoot()){
			fieldPath = getFieldPath(parent, fieldPath);
		}
		return fieldPath;
	}

	/**
	 * @param parent
	 * @param fieldPath
	 * @return
	 */
	private static FieldPath getFieldPath(Element element, FieldPath fieldPath) {
		List<String> pathList = fieldPath.getPathList();
		pathList.add(0, element.getName());
		
		Element parent = element.getParent();
		if(parent != null && !parent.isRoot()){
			fieldPath = getFieldPath(parent, fieldPath);
		}
		return fieldPath;
	}
	
	
	public static String CURRENT = ".";
	
	/**
	 * <blockquote>
	 * <pre>
	 * 입력으로 주어진 부모 Element객체의 자식필드 Element중에 입력으로 주어진 상대 패스에 지정된 모든 Element의 리스트를 검색하여 리턴한다.
	 * 예를 들어 메시지트리가 아래와 같다면,  
	 * <table>
	 * <tr><td align="center" colspan="6">[Message tree]</td></tr>
	 * <tr><td align="center" colspan="6">a(a[0])</td></tr>
	 * <tr><td align="center"  colspan="3">b[0]</td><td align="center"  colspan="3">b[1]</td></tr>
	 * <tr><td>c[0]</td><td>c[1]</td><td>c[2]</td><td>c[0]</td><td>c[1]</td><td>c[2]</td></tr>
	 * </table>
	 * parentElement로 Element a[0]을 넘겨주고 parentElement a로부터 상대 패스로 "b.c"를 넘겨줄 경우 사용 예는 아래와 같다.
	 * 결과 값으로 6개의 모든 c 엘리먼트들을 포함한 리스트를 리턴하다. 
	 * <div style="background:#BDE12A">
	 * <B>[usage]</B>
	 * Message msg = ...;
	 * Element parentElement = MessageUtil.getElement(msg, "a[0]");
	 * FieldPath fieldPath = new FieldPath("b.c");
	 * List&lt;Element&gt; elements = MessageUtil.getElements(parentElement, fieldPath);
	 * System.out.println("The size of elements is " + elements.size());//This line print "The size of elements is 6"
	 * </div>
	 * </pre>
	 * </blockquote>
	 * 
	 * @param parentElement 검색하고자하는 필드값을 가진 부모 Element
	 * @param childPath 검색하고자 하는 필드의 parentElement로부터의 상대 패스를 표현한 FieldPath 객체 
	 * @return
	 * @throws Exception
	 */
	public static List<Element> getElements(Element parentElement, FieldPath childPath) throws Exception {
		List<Element> list = null;
		List<String> pathList = childPath.getPathList();
		 
		
		int currentPathIndex = 0;
		
		String pathValue = pathList.get(currentPathIndex);
		Path path = PathUtil.getPath(pathValue);
		String name = path.getName();
		int index = path.getIndex();
		 

		//if(CURRENT.equals(name))
		
		List<Element> children = parentElement.getChildList(name);
		if(index == Path.INDEX_ALL){
			
		}else{
			List<Element> tmp = new ArrayList<Element>();
			Element child = children.get(index);
			tmp.add(child);
			children = tmp;
		}

		int pathSize = pathList.size();
		if(pathSize <= 1){
			list = children;
		}else{
			list = new ArrayList<Element>();
			++ currentPathIndex;
			for (Element child : children) {
				List<Element> childList = getElements(child, pathList, currentPathIndex); 
				if(childList != null) list.addAll(childList);
			}
		}
		
		return list;
		
	}
	
	/**
	 * <blockquote>
	 * <pre>
	 * 입력으로 주어진 부모 Element객체의 자식필드 Element중에 입력으로 주어진 상대 패스에 지정된 모든 Element의 리스트를 검색하여 리턴한다.
	 * 예를 들어 메시지트리가 아래와 같다면,  
	 * <table>
	 * <tr><td align="center" colspan="6">[Message tree]</td></tr>
	 * <tr><td align="center" colspan="6">a(a[0])</td></tr>
	 * <tr><td align="center"  colspan="3">b[0]</td><td align="center"  colspan="3">b[1]</td></tr>
	 * <tr><td>c[0]</td><td>c[1]</td><td>c[2]</td><td>c[0]</td><td>c[1]</td><td>c[2]</td></tr>
	 * </table>
	 * parentElement로 Element a[0]을 넘겨주고 parentElement a로부터 상대 패스로 "b.c"를 넘겨줄 경우 사용 예는 아래와 같다.
	 * 결과 값으로 6개의 모든 c 엘리먼트들을 포함한 리스트를 리턴하다. 
	 * <div style="background:#BDE12A">
	 * <B>[usage]</B>
	 * Message msg = ...;
	 * Element parentElement = MessageUtil.getElement(msg, "a[0]");
	 * String fieldPath = "b.c";
	 * List&lt;Element&gt; elements = MessageUtil.getElements(parentElement, fieldPath);
	 * System.out.println("The size of elements is " + elements.size());//This line print "The size of elements is 6"
	 * </div>
	 * </pre>
	 * </blockquote>
	 * 
	 * @param parentElement 검색하고자하는 필드값을 가진 부모 Element
	 * @param childPath 검색하고자 하는 필드의 parentElement로부터의 상대 패스를 표현한 String 객체 
	 * @return
	 * @throws Exception
	 */
	public static List<Element> getElements(Element parentElement, String childPath) throws Exception {
		return getElements(parentElement, new FieldPath(childPath));
	}
	
	
	/**
	 * <blockquote>
	 * <pre>
	 * 필드패스에 지정된 Element의 첫번째를 검색하여 리턴한다. 필드패스에는 인덱스를 지정하지 말아야 한다. 
	 * 예를 들어 Message 내에서 검색하려는 필드 패스로 "a.b.c"를 주면 결과적으로는 a[0].b[0].c[0]의 Element를 반환한다.  
	 * <table>
	 * <tr><td align="center" colspan="6">[Message tree]</td></tr>
	 * <tr><td align="center" colspan="6">a(a[0])</td></tr>
	 * <tr><td align="center"  colspan="3">b[0]</td><td align="center"  colspan="3">b[1]</td></tr>
	 * <tr><td>c[0]</td><td>c[1]</td><td>c[2]</td><td>c[0]</td><td>c[1]</td><td>c[2]</td></tr>
	 * </table>
	 * 
	 * <div style="background:#BDE12A">
	 * <B>[usage]</B>
	 * 
	 * Message msg = ...;
	 * FieldPath fieldPath = new FieldPath("a.b.c");
	 * Element element = MessageUtil.getElementFirst(msg, fieldPath);//a[0].b[0].c[0]의 Element를 반환한다.
	 * 
	 * </pre>
	 * </blockquote>
	 * 
	 * @param msg 검색하고자하는 필드값을 가진 Message 객체
	 * @param fieldPath 검색하고자하는 필드값의 패스를 표현한 FieldPath 객체, 필드패스에는 인덱스를 지정하지 말아야 한다.
	 * @return
	 * @throws Exception
	 */
	public static Element getElementFirst(Message msg, FieldPath fieldPath) throws Exception{
		List<String> pathList = fieldPath.getPathList();
		Element current = msg.getMsgElement();
		if(current == null) return null;
		for (String path : pathList) {
			List<Element> list = current.getChildList(path);
			if(list == null) return null;
			current = list.get(0);
			if(current == null) return null;
		}
		return current;
	}
	
	/**
	 * <blockquote>
	 * <pre>
	 * 필드패스에 지정된 Element의 첫번째를 검색하여 리턴한다. 필드패스에는 인덱스를 지정하지 말아야 한다. 
	 * 예를 들어 Message 내에서 검색하려는 필드 패스로 "a.b.c"를 주면 결과적으로는 a[0].b[0].c[0]의 Element를 반환한다.  
	 * <table>
	 * <tr><td align="center" colspan="6">[Message tree]</td></tr>
	 * <tr><td align="center" colspan="6">a(a[0])</td></tr>
	 * <tr><td align="center"  colspan="3">b[0]</td><td align="center"  colspan="3">b[1]</td></tr>
	 * <tr><td>c[0]</td><td>c[1]</td><td>c[2]</td><td>c[0]</td><td>c[1]</td><td>c[2]</td></tr>
	 * </table>
	 * 
	 * <div style="background:#BDE12A">
	 * <B>[usage]</B>
	 * 
	 * Message msg = ...;
	 * String fieldPath = "a.b.c";
	 * Element element = MessageUtil.getElementFirst(msg, fieldPath);//a[0].b[0].c[0]의 Element를 반환한다.
	 * 
	 * </pre>
	 * </blockquote>
	 * 
	 * @param msg 검색하고자하는 필드값을 가진 Message 객체
	 * @param fieldPath 검색하고자하는 필드값의 패스를 표현한 String 객체, 필드패스에는 인덱스를 지정하지 말아야 한다.
	 * @return
	 * @throws Exception
	 */
	public static Element getElementFirst(Message msg, String fieldPath) throws Exception{
		return getElementFirst(msg, new FieldPath(fieldPath));
	}
	
	/**
	 * <blockquote>
	 * <pre>
	 * serializable 객체를 참조가 아닌 value copy할 필요가 있을 경우 사용한다.
 	 * 
	 * <div style="background:#BDE12A">
	 * <B>[usage]</B>
	 * 
	 * Message msg = ...;
	 * String fieldPath = "a.b.c";
	 * Element element = MessageUtil.getElementFirst(msg, fieldPath);//a[0].b[0].c[0]의 Element를 반환한다.
	 * Object value = element.getValue();//엘리먼트의 값을 얻어온다.
	 * Object copy = MessageUtil.copy(value);//엘리먼트의 원래 값을 레퍼런스하지 않고 새로운 값으로 복사한다.
	 * ...
	 * ... 
	 * ...
	 * </pre>
	 * </blockquote>
	 * @param serializable copy할 대상 serializable 객체(Serializable 인터페이스를 implement한 객체여야한다.)
	 * @return copy된 결과 객체 값
	 * @throws Exception
	 */
	public static <T> T copy(Serializable serializable) throws Exception{
		ObjectOutputStream oos = null;
		ObjectInputStream ois = null;
		try{
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(baos);
			oos.writeObject(serializable);
			
			byte[] b = baos.toByteArray();
			ois = new ObjectInputStream(new ByteArrayInputStream(b));
			return (T) ois.readObject();
		}finally{
			try{if(oos != null) oos.close();}catch(IOException e){}
			try{if(ois != null) ois.close();}catch(IOException e){}
		}
		
	}
	
	/**
	 * <blockquote>
	 * <pre>
	 * serializable 객체를 참조가 아닌 value copy할 필요가 있을 경우 사용한다.
	 * 아파치의 SerializationUtils 라이브러를 사용한 함수이다.
 	 * 
	 * <div style="background:#BDE12A">
	 * <B>[usage]</B>
	 * 
	 * Message msg = ...;
	 * String fieldPath = "a.b.c";
	 * Element element = MessageUtil.getElementFirst(msg, fieldPath);//a[0].b[0].c[0]의 Element를 반환한다.
	 * Object value = element.getValue();//엘리먼트의 값을 얻어온다.
	 * Object copy = MessageUtil.copy2(value);//엘리먼트의 원래 값을 레퍼런스하지 않고 새로운 값으로 복사한다.
	 * ...
	 * ... 
	 * ...
	 * </pre>
	 * </blockquote>
	 * @param serializable copy할 대상 serializable 객체(Serializable 인터페이스를 implement한 객체여야한다.)
	 * @return copy된 결과 객체 값
	 * @throws Exception
	 */
	public static <T> T  copy2(Serializable serializable)throws Exception{
		return (T) SerializationUtils.clone(serializable);
	}


	/**
	 * <blockquote>
	 * <pre>
	 * 입력으로 받은 부모 엘리먼트의 바로 하위 자식 엘리먼트 리스트를 검색하여 리턴한다.
	 * 예를 들어 메시지 트리가 아래와 같을 경우  
	 * <table>
	 * <tr><td align="center" colspan="6">[Message tree]</td></tr>
	 * <tr><td align="center" colspan="6">a(a[0])</td></tr>
	 * <tr><td align="center"  colspan="3">b[0]</td><td align="center"  colspan="3">b[1]</td></tr>
	 * <tr><td>c[0]</td><td>c[1]</td><td>c[2]</td><td>c[0]</td><td>c[1]</td><td>c[2]</td></tr>
	 * </table>
	 * 
	 * 엘리먼트 "b[1]"을 부모 엘리먼트로하고 자식 엘리먼트 이름으로 "c"를 주어 검색한다면, 
	 * <div style="background:#BDE12A">
	 * <B>[usage]</B>
	 * Element parent = ...;//엘리먼트 "b[1]"
	 * List&lt;Element&gt children = MessageUtil.getChildren(parent,"c");
	 * System.out.println("The size of children is " + children.size());//This line print "The size of children is 3"
	 * </div>
	 * </pre>
	 * </blockquote>
	 * 
	 * @param parentElement 검색하고자하는 필드 엘리먼트를 가진 부모 엘리먼트 객체
	 * @param name 검색하고자하는 자식 엘리먼트 이름
	 * @return
	 * @throws Exception
	 */
	public static List<Element> getChildren(Element parentElement, String name) throws Exception{
		List<Element> elements = parentElement.getChildList(name);
		return elements;
	}


	/**
	 * <blockquote>
	 * <pre>
	 * 입력으로 받은 부모 엘리먼트의 바로 하위 자식 엘리먼트 리스트를 검색하여 지정된 인덱스값의 엘리먼트를 리턴한다.
	 * 예를 들어 메시지 트리가 아래와 같을 경우  
	 * <table>
	 * <tr><td align="center" colspan="6">[Message tree]</td></tr>
	 * <tr><td align="center" colspan="6">a(a[0])</td></tr>
	 * <tr><td align="center"  colspan="3">b[0]</td><td align="center"  colspan="3">b[1]</td></tr>
	 * <tr><td>c[0]</td><td>c[1]</td><td>c[2]</td><td>c[0]</td><td>c[1]</td><td>c[2]</td></tr>
	 * </table>
	 * 
	 * 엘리먼트 "b[1]"을 부모 엘리먼트로하고 자식 엘리먼트 이름으로 "c"를 주어 검색한다면, 
	 * <div style="background:#BDE12A">
	 * <B>[usage]</B>
	 * Element parent = ...;//엘리먼트 "b[1]"
	 * Element child = MessageUtil.getChildAt(parent,"c",1);//엘리먼트 "c[1]"을 반환한다.
	 * </div>
	 * </pre>
	 * </blockquote>
	 * 
	 * @param parentElement 검색하고자하는 필드 엘리먼트를 가진 부모 Element 객체
	 * @param childName 검색하고자하는 자식 엘리먼트 이름
	 * @param index
	 * @return
	 * @throws Exception
	 */
	public static Element getChildAt(Element parentElement, String childName, int index) throws Exception{
		List<Element> children = getChildren(parentElement, childName);
		if(children == null || children.size() - 1 < index) return null;
		return children.get(index);
	}



	/**
	 * <blockquote>
	 * <pre>
	 * 메시지에 특정 패스에 해당하는 엘리먼트를 신규로 추가 생성한다.
	 * <table>
	 * <tr><td align="center" colspan="6">[Message tree]</td></tr>
	 * <tr><td align="center" colspan="6">a(a[0])</td></tr>
	 * <tr><td align="center"  colspan="3">b[0]</td><td align="center"  colspan="3">b[1]</td></tr>
	 * <tr><td>c[0]</td><td>c[1]</td><td>c[2]</td><td>c[0]</td><td>c[1]</td><td>c[2]</td></tr>
	 * </table>
	 * 위의 메시지 트리에서 "a.b[1].c[3]" 엘리먼트를 신규 생성할 경우 아래와 같다.
	 * <div style="background:#BDE12A">
	 * <B>[usage]</B>
	 * Message msg = ...;
	 * String fieldPath = "a.b.c";
	 * boolean createMode = true;
	 * Element c3 = MessageUtil.createElement(msg,fieldPath, createMode);//엘리먼트 "c[3]"을 신규 생성하여 반환한다.
	 * 
	 * 아래는 신규 엘리먼트 a[0].b[1].c[3] 추가된 메시지 트리 결과이다.
	 * <table>
	 * <tr><td align="center" colspan="7">[Result Message tree]</td></tr>
	 * <tr><td align="center" colspan="7">a(a[0])</td></tr>
	 * <tr><td align="center"  colspan="3">b[0]</td><td align="center"  colspan="4">b[1]</td></tr>
	 * <tr><td>c[0]</td><td>c[1]</td><td>c[2]</td><td>c[0]</td><td>c[1]</td><td>c[2]</td><td style="color:red">c[3]</td></tr>
	 * </table>
	 * 
	 * </div>
	 * </pre>
	 * </blockquote>
	 * 
	 * @param msg 필드 엘리먼트를 추가할 Message 객체
	 * @param fieldPath 추가할 필드 엘리먼트의 패스 String 객체
	 * @param createMode 신규로 추가할 엘리먼트가 존재하면 true - 엘리먼트 새로 생성, false - 기존 리스트의 최하위 엘리먼트를 리턴
	 * @return
	 * @throws Exception
	 */
	public static Element createElement(Message msg, String fieldPath, boolean createMode) throws Exception{
		Element newElement = null; 
		newElement = createElement(msg, new FieldPath(fieldPath), createMode);
		return newElement;
	}
	
	/**
	 * <blockquote>
	 * <pre>
	 * 메시지에 특정 패스에 해당하는 엘리먼트를 신규로 추가 생성한다.
	 * <table>
	 * <tr><td align="center" colspan="6">[Message tree]</td></tr>
	 * <tr><td align="center" colspan="6">a(a[0])</td></tr>
	 * <tr><td align="center"  colspan="3">b[0]</td><td align="center"  colspan="3">b[1]</td></tr>
	 * <tr><td>c[0]</td><td>c[1]</td><td>c[2]</td><td>c[0]</td><td>c[1]</td><td>c[2]</td></tr>
	 * </table>
	 * 위의 메시지 트리에서 "a.b[1].c[3]" 엘리먼트를 신규 생성할 경우 아래와 같다.
	 * <div style="background:#BDE12A">
	 * <B>[usage]</B>
	 * Message msg = ...;
	 * FieldPath fieldPath = new FieldPath("a.b.c");
	 * boolean createMode = true;
	 * Element c3 = MessageUtil.createElement(msg,fieldPath, createMode);//엘리먼트 "c[3]"을 신규 생성하여 반환한다.
	 * 
	 * 아래는 신규 엘리먼트 a[0].b[1].c[3] 추가된 메시지 트리 결과이다.
	 * <table>
	 * <tr><td align="center" colspan="7">[Result Message tree]</td></tr>
	 * <tr><td align="center" colspan="7">a(a[0])</td></tr>
	 * <tr><td align="center"  colspan="3">b[0]</td><td align="center"  colspan="4">b[1]</td></tr>
	 * <tr><td>c[0]</td><td>c[1]</td><td>c[2]</td><td>c[0]</td><td>c[1]</td><td>c[2]</td><td style="color:red">c[3]</td></tr>
	 * </table>
	 * 
	 * </div>
	 * </pre>
	 * </blockquote>
	 * 
	 * @param msg 필드 엘리먼트를 추가할 Message 객체
	 * @param fieldPath 추가할 필드 엘리먼트의 패스 FieldPath 객체
	 * @param createMode 신규로 추가할 엘리먼트가 존재하면 true - 엘리먼트 새로 생성, false - 기존 리스트의 최하위 엘리먼트를 리턴
	 * @return
	 * @throws Exception
	 */
	public static Element createElement(Message msg, FieldPath fieldPath, boolean createMode) throws Exception{
		Element newElement = null; 
		 
		int size = fieldPath.size();
		List<String> pathList = fieldPath.getPathList();
		Element root = msg.getMsgElement();
		int first = 0;
		if(size == 1){
			List<Element> elements = root.getChildList(pathList.get(first));
			if(elements == null || elements.size() == 0){
				newElement = new Element(pathList.get(0));
				root.addChild(newElement);
			}else{
				
				if(createMode){
					//-------------------------------------------------------------------
					//신규생성 모드라면 엘리먼트를 생성하고 기존 리스트에 마지막 엘리먼트를 추가 후 내어준다.
					//-------------------------------------------------------------------
					newElement = new Element(pathList.get(first));
					root.addChild(newElement);
				}else{
					//-------------------------------------------------------------------
					//신규생성 모드가 아니라면 기존 리스트에 마지막 엘리먼트를 내어준다.
					//-------------------------------------------------------------------
					newElement = elements.get(elements.size() - 1);
				}
			}
			
		}else if(size > 1){
			Element current = root;
			for(int i = 0 ; i < size ; i ++){
				String path = pathList.get(i);
				List<Element> elements = current.getChildList(path);
				if(elements == null || elements.size() == 0){
					newElement = new Element(path);
					current.addChild(newElement);
				}else{
					if(createMode){
						newElement = new Element(path);
						current.addChild(newElement);
					}else{
						newElement = elements.get(elements.size() - 1);
					}
				}
				current = newElement;
			}
		}
		return newElement;
	}
	

	/**
	 * <blockquote>
	 * <pre>
	 * 부모 엘리먼트에 특정 패스에 해당하는 엘리먼트를 신규로 추가 생성한다.
	 * <table>
	 * <tr><td align="center" colspan="6">[Message tree]</td></tr>
	 * <tr><td align="center" colspan="6">a(a[0])</td></tr>
	 * <tr><td align="center"  colspan="3">b[0]</td><td align="center"  colspan="3">b[1]</td></tr>
	 * <tr><td>c[0]</td><td>c[1]</td><td>c[2]</td><td>c[0]</td><td>c[1]</td><td>c[2]</td></tr>
	 * </table>
	 * 위의 메시지 트리에서 부모엘리먼트 "a.b[1]"에 "c[3]" 엘리먼트를 신규 생성할 경우 아래와 같다.
	 * <div style="background:#BDE12A">
	 * <B>[usage]</B>
	 * Element parent = ...;//엘리먼트 "a.b[1]"
	 * String fieldPath = "c";
	 * boolean createMode = true;
	 * Element c3 = MessageUtil.createElement(parent, fieldPath, createMode);//엘리먼트 "c[3]"을 신규 생성하여 반환한다.
	 * 
	 * 아래는 신규 엘리먼트 a[0].b[1].c[3] 추가된 메시지 트리 결과이다.
	 * <table>
	 * <tr><td align="center" colspan="7">[Result Message tree]</td></tr>
	 * <tr><td align="center" colspan="7">a(a[0])</td></tr>
	 * <tr><td align="center"  colspan="3">b[0]</td><td align="center"  colspan="4">b[1]</td></tr>
	 * <tr><td>c[0]</td><td>c[1]</td><td>c[2]</td><td>c[0]</td><td>c[1]</td><td>c[2]</td><td style="color:red">c[3]</td></tr>
	 * </table>
	 * </div>
	 * </pre>
	 * </blockquote>
	 * 
	 * @param parentElement 필드 엘리먼트를 추가할 부모 Element 객체
	 * @param currentPath 추가할 필드 엘리먼트의 패스 String 객체
	 * @param createMode 신규로 추가할 엘리먼트가 존재하면 true - 엘리먼트 새로 생성, false - 기존 리스트의 최하위 엘리먼트를 리턴
	 * @return
	 * @throws Exception
	 */
	public static Element createElement(Element parentElement, String currentPath, boolean createMode) throws Exception{
		Element newElement = createElement(parentElement, new FieldPath(currentPath), createMode); 
		return newElement;
	}
	
	/**
	 * <blockquote>
	 * <pre>
	 * 부모 엘리먼트에 특정 패스에 해당하는 엘리먼트를 신규로 추가 생성한다.
	 * <table>
	 * <tr><td align="center" colspan="6">[Message tree]</td></tr>
	 * <tr><td align="center" colspan="6">a(a[0])</td></tr>
	 * <tr><td align="center"  colspan="3">b[0]</td><td align="center"  colspan="3">b[1]</td></tr>
	 * <tr><td>c[0]</td><td>c[1]</td><td>c[2]</td><td>c[0]</td><td>c[1]</td><td>c[2]</td></tr>
	 * </table>
	 * 위의 메시지 트리에서 부모엘리먼트 "a.b[1]"에 "c[3]" 엘리먼트를 신규 생성할 경우 아래와 같다.
	 * <div style="background:#BDE12A">
	 * <B>[usage]</B>
	 * Element parent = ...;//엘리먼트 "a.b[1]"
	 * FieldPath fieldPath = new FieldPath("c");
	 * boolean createMode = true;
	 * Element c3 = MessageUtil.createElement(parent, fieldPath, createMode);//엘리먼트 "c[3]"을 신규 생성하여 반환한다.
	 * 
	 * 아래는 신규 엘리먼트 a[0].b[1].c[3] 추가된 메시지 트리 결과이다.
	 * <table>
	 * <tr><td align="center" colspan="7">[Result Message tree]</td></tr>
	 * <tr><td align="center" colspan="7">a(a[0])</td></tr>
	 * <tr><td align="center"  colspan="3">b[0]</td><td align="center"  colspan="4">b[1]</td></tr>
	 * <tr><td>c[0]</td><td>c[1]</td><td>c[2]</td><td>c[0]</td><td>c[1]</td><td>c[2]</td><td style="color:red">c[3]</td></tr>
	 * </table>
	 * </div>
	 * </pre>
	 * </blockquote>
	 * 
	 * @param parentElement 필드 엘리먼트를 추가할 부모 Element 객체
	 * @param currentPath 추가할 필드 엘리먼트의 패스 FieldPath 객체
	 * @param createMode 신규로 추가할 엘리먼트가 존재하면 true - 엘리먼트 새로 생성, false - 기존 리스트의 최하위 엘리먼트를 리턴
	 * @return
	 * @throws Exception
	 */
	public static Element createElement(Element parentElement, FieldPath currentPath, boolean createMode) throws Exception{
		
		if(MapPath.PATH_CHAR_CURRENT.equals(currentPath.toString())){
			return parentElement;
		}
		
		Element newElement = null; 
		 
		int size = currentPath.size();
		 
		
		if(size == 1){
			List<Element> elements = parentElement.getChildList(currentPath.toString());
			if(elements == null || elements.size() == 0){
				newElement = new Element(currentPath.toString());
				parentElement.addChild(newElement);
			}else{
				if(createMode){
					newElement = new Element(currentPath.toString());
					parentElement.addChild(newElement);
				}else{
					newElement = elements.get(elements.size() - 1);
				}
			}
			
		}else if(size > 1){
			List<String> pathList = currentPath.getPathList();
			Element current = parentElement;
			for(int i = 0 ; i < size ; i ++){
				String path = pathList.get(i);
				List<Element> elements = current.getChildList(path);
				if(elements == null || elements.size() == 0){
					newElement = new Element(path);
					current.addChild(newElement);
				}else{
					if(createMode){
						newElement = new Element(path);
						current.addChild(newElement);
					}else{
						newElement = elements.get(elements.size() - 1);
					}
				}
				current = newElement;
			}
		}
		return newElement;
	}
	
	public static Element addMsgElement(Message msg, Element element){
		Element msgRoot = msg.getMsgElement();
		msgRoot.addChild(element);
		return element;
	}
	
}
