/*
 * Copyright 2013 ~ 2014 Mocomsys(dhkim, Solution TF), Inc.  All Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 * Please contact Mocomsys, Inc., NURITKUM SQUARE R&D TOWER, 11F DMC 1605, 
 * Sangam-Dong, Mapo-Gu, Seoul, 121-795 Korea or visit mocomsys.com 
 * if you need additional information or have any questions.
*/
package pep.per.mint.common.message;

import java.util.List;

import pep.per.mint.common.data.FieldPath;
import pep.per.mint.common.util.Util;

/**
 * <blockquote>
 * <pre>
 * 메시지의 필드와 관련하여 Path 및 FieldPath와 관련된 유틸리티 함수를 가지는 클래스
 * </div>
 * </pre>
 * </blockquote>
 * @author Solution TF
 *
 */
public class PathUtil {

	 
	public static final String CURRENT = ".";
	
	public static final String DEFAULT_DELIM = ".";

	/**
	 * <blockquote>
	 * <pre>
	 * 
	 * 패스에 포함된 배열 인덱스 표현식을 파싱후 Path 인스턴스를 리턴한다.
	 * 
	 * 아래 메시지 트리에서 엘리먼트 c[2] 즉 Element[path=a[0].b[1].c[2]] 의 전체 필드패스는 아래와 같다.
	 * 
	 * 	<B>"a[0].b[1].c[2]"</B>
	 * 
	 * 필드패스 내에서 각각의 엘리먼트 이름인 a[0], b[1], c[2]는 배열의 인덱스 값을 가지고 있다.
	 * 메시지 파싱 또는 매핑 등 메시지 트리내의 특정 엘리먼트를 다루는 과정에서 엘리먼트 리스트의 
	 * 특정 인덱스에 해당하는 엘리먼트에 접근하기 위해서는 이러한 인덱스 반드시 값을 알아야만 한다.
	 * 이때 PathUtil.getPath("a[2]") 함수를 사용하여 a[2] 라는 값을 넘겨주면 필드 이름 "a" 와 인덱스 "2"를 
	 * 멤버 변수로 하는 Path를 얻을 수 있다.  Path를 통해서 이제 이름과 인덱스를 얻어올 수도 있다. 
	 * 
	 * <table>
	 * <tr><td align="center" colspan="6">[Message tree]</td></tr>
	 * <tr><td align="center" colspan="6" style="color:red">a(a[0])</td></tr>
	 * <tr><td align="center"  colspan="3">b[0]</td><td align="center"  colspan="3" style="color:red">b[1]</td></tr>
	 * <tr><td>c[0]</td><td>c[1]</td><td>c[2]</td><td>c[0]</td><td>c[1]</td><td style="color:red">c[2]</td></tr>
	 * </table>
	 * <div style="background:#BDE12A">
	 * <B>[usage]</B>
	 * FieldPath fieldPath = new FieldPath("a.b[1]");
	 * List<String> pathList = fieldPath.getPathList();
	 * String pathString = pathList.get(1);
	 * <B style="color:red">Path path = PathUtil.getPath(pathString);</B>
	 * System.out.println("path's name:" + path.getName());//This line print "path's name:b"
	 * System.out.println("path's index:" + path.getIndex()); //This line print "path's index:1"
	 * </div>
	 * </pre>
	 * </blockquote>
	 * @param 이름과 인덱스로 구성된 패스 문자열(예:"a[0]")
	 * @return pathValue 이름과 인덱스를 멤버로 가지는 Path 객체
	 */
	public static Path getPath(String pathValue) throws Exception {
		Path path = null;
		int beginIndex = pathValue.indexOf('[');
		int endIndex   = pathValue.indexOf(']');
		String name = pathValue;
		int index = Path.INDEX_ALL;
		if(beginIndex == -1 || endIndex  == -1){
		}else{
			name = pathValue.substring(0, beginIndex);
			index = Integer.parseInt(pathValue.substring(beginIndex + 1, endIndex));
		}
		path = new Path(name,index);
		 
		return path;
	}

	/**
	 * <blockquote>
	 * <pre>
	 * 필드패스에서 배열 인덱스값을 제거한 패스값을 리턴한다.
	 * 예를 들어 필드 패스 "a.b[0].c[3]"를 입력으로 주면
	 * 각 엘리먼트의 패스에 포함된 인덱스를 제거한 "a.b.c"를 리턴해준다.
	 * <div style="background:#BDE12A">
	 * <B>[usage]</B>
	 * FieldPath fieldPath = new FieldPath("a.b[1].c[4]");
	 * <B style="color:red">String pathString = PathUtil.getPathString(fieldPath);</B>
	 * System.out.println("pathString:" + pathString);//This line print "pathString:a.b.c"
	 * </div>
	 * </pre>
	 * </blockquote>
	 * 
	 * @param fieldPath
	 * @return
	 * @throws Exception
	 */
	public static String getPathString(FieldPath fieldPath) throws Exception{
		List<String> pathList = fieldPath.getPathList();
		StringBuffer buffer = new StringBuffer();
		
		for (int i = 0 ; i < pathList.size() ; i ++){
			String pathString = pathList.get(i);
			Path path = getPath(pathString);
			if(i == 0) {
				buffer.append(path.getName());
				continue;
			}
			buffer.append(Path.CURRENT).append(path.getName());
		}
		return buffer.toString();
	}
	
	/**
	 * <blockquote>
	 * <pre>
	 * 필드패스에서 가장 마지막 패스를 문자열 값으로 리턴한다.
	 * 
	 * 예를 들어 필드 패스 "a.b[0].c[3]"를 입력으로 주면
	 * 각 엘리먼트의 패스에 포함된 인덱스를 제거한 "a.b.c"를 리턴해준다.
	 * <div style="background:#BDE12A">
	 * <B>[usage]</B>
	 * FieldPath fieldPath = new FieldPath("a.b.c");
	 * <B style="color:red">String lastPath = PathUtil.getLastPathName(fieldPath);</B>
	 * System.out.println("lastPath:" + lastPath);//This line print "lastPath:c"
	 * </div>
	 * </pre>
	 * </blockquote>
	 * 
	 * @param fieldPath
	 * @return
	 * @throws Exception
	 */
	public static String getLastPathName(FieldPath fieldPath) throws Exception{
		String name = "";
		List<String> paths = fieldPath.getPathList();
		name = paths.get(paths.size() - 1);
		return name;
	}
	
	/**
	 * <blockquote>
	 * <pre>
	 * 필드패스에서 가장 마지막 패스를 문자열 값으로 리턴한다.
	 * 
	 * 예를 들어 필드 패스 "a.b[0].c[3]"를 입력으로 주면
	 * 각 엘리먼트의 패스에 포함된 인덱스를 제거한 "a.b.c"를 리턴해준다.
	 * <div style="background:#BDE12A">
	 * <B>[usage]</B>
	 * String fieldPath = "a.b.c";
	 * <B style="color:red">String lastPath = PathUtil.getLastPathName(fieldPath);</B>
	 * System.out.println("lastPath:" + lastPath);//This line print "lastPath:c"
	 * </div>
	 * </pre>
	 * </blockquote>
	 * 
	 * @param fieldPath
	 * @return
	 * @throws Exception
	 */
	public static String getLastPathName(String fieldPath) throws Exception{
		return getLastPathName(new FieldPath(fieldPath));
	}
	
	/**
	 * <blockquote>
	 * <pre>
	 * 현재 필드패스의 부모 필드패스를 리턴한다.
	 * 
	 * 예를 들어 필드패스 "a.b.c"를 입력으로 주면
	 * 한단계위의 부모 필드패스인 "a.b"를 리턴해준다.
	 * <div style="background:#BDE12A">
	 * <B>[usage]</B>
	 * FieldPath fieldPath = new FieldPath("a.b.c");
	 * <B style="color:red">FieldPath parentPath = PathUtil.getParentFieldPath(fieldPath);</B>
	 * System.out.println("parentPath:" + parentPath.toString());//This line print "parentPath:a.b"
	 * </div>
	 * </pre>
	 * </blockquote>
	 * 
	 * @param fieldPath
	 * @return
	 * @throws Exception
	 */
	public static FieldPath getParentFieldPath(FieldPath fieldPath) throws Exception{
		FieldPath parent = null;
		if(fieldPath.size() == 1) parent = FieldPath.ROOT;
		else{
			parent = new FieldPath();
			List<String> pathList = fieldPath.getPathList();
			for(int i = 0 ; i < fieldPath.size() - 1 ; i ++){
				parent.addPath(pathList.get(i));
			}
		}
		return parent;
	}
	

	
	/**
	 * <blockquote>
	 * <pre>
	 * 입력된 부모 패스값과 자식 패스값을 합쳐서 전체패스를 구성하여 문자열로 리턴한다.
	 * 이때 자식 패스값이 현재위치("." 또는 "") 이면 부모 패스값을 전체 패스값으로 사용한다.
	 * 주로 매핑작업을 수행하는 Controller 클래스내에서 사용된다.
	 * <div style="background:#BDE12A">
	 * <B>[usage]</B>
	 * String parent = "a.b.c";
	 * String current = "d";
	 * <B style="color:red">String fullPathString = PathUtil.getFullPathString(parent,".",current);</B>
	 * System.out.println("fullPathString:" + fullPathString);//This line print "fullPathString:a.b.c.d"
	 * </div>
	 * </pre>
	 * </blockquote>
	 *  
	 * @param parent 부모 필드패스 문자열 값
	 * @param delim 필드패스 delimiter 문자(예: ".")
	 * @param current 자식(현재) 필드패스 문자열 값
	 * @return
	 */
	public static String getFullPathString(String parent, String delim, String current) {
		return current == null || current.trim().length() == 0 || CURRENT.equals(current) ? parent : Util.join(parent, delim, current);
	}
	
	/**
	 * <blockquote>
	 * <pre>
	 * 입력된 부모 패스값과 자식 패스값을 합쳐서 전체패스를 구성하여 문자열로 리턴한다.
	 * 이때 자식 패스값이 현재위치("." 또는 "") 이면 부모 패스값을 전체 패스값으로 사용한다.
	 * 주로 매핑작업을 수행하는 Controller 클래스내에서 사용된다.
	 * <div style="background:#BDE12A">
	 * <B>[usage]</B>
	 * String parent = "a.b.c";
	 * String current = "d";
	 * <B style="color:red">String fullPathString = PathUtil.getFullPathString(parent, current);</B>
	 * System.out.println("fullPathString:" + fullPathString);//This line print "fullPathString:a.b.c.d"
	 * </div>
	 * </pre>
	 * </blockquote>
	 *  
	 * @param parent 부모 필드패스 문자열 값
	 * @param current 자식(현재) 필드패스 문자열 값
	 * @return
	 */
	public static String getFullPathString(String parent, String current) {
		return getFullPathString(parent, DEFAULT_DELIM, current);
	}
	 
}
