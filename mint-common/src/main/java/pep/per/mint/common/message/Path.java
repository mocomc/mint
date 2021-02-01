/*
 * Copyright 2013 ~ 2014 Mocomsys(dhkim, Solution TF), Inc.  All Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 * Please contact Mocomsys, Inc., NURITKUM SQUARE R&D TOWER, 11F DMC 1605, 
 * Sangam-Dong, Mapo-Gu, Seoul, 121-795 Korea or visit mocomsys.com 
 * if you need additional information or have any questions.
*/
package pep.per.mint.common.message;

/**
 * <blockquote>
 * <pre>
 * 필드패스를 구성하는 각 엘리먼트의 이름에 해당하는 값에 배열 인덱스 표현식을 처리하기위한 클래스
 * 아래 메시지 트리에서 엘리먼트 c[2] 즉 Element[path=a[0].b[1].c[2]] 의 전체 필드패스는 아래와 같이 표현하고.
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
 * Message msg = ...;
 * FieldPath fieldPath = new FieldPath("a.b[1]");
 * List<String> pathList = fieldPath.getPathList();
 * String pathString = pathList.get(1);
 * Path path = PathUtil.getPath(pathString);
 * System.out.println("path's name:" + path.getName());//This line print "path's name:b"
 * System.out.println("path's index:" + path.getIndex()); //This line print "path's index:1"
 * 
 * 
 * </div>
 * </pre>
 * </blockquote>
 * @author Solution TF
 *
 */
public class Path {
	
	public static final String CURRENT = ".";

	public static int INDEX_ALL = -1;
	
	int index;
	
	String name;

	/**
	 * 생성자
	 * @param name
	 * @param index
	 */
	public Path(String name, int index) {
		this.name = name;
		this.index = index;
	}

	/**
	 * 패스의 인덱스값을 리턴한다.
	 * @return
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * 패스의 인덱스를 세팅한다.
	 * @param index
	 */
	public void setIndex(int index) {
		this.index = index;
	}

	/**
	 * 패스의 이름을 리턴한다.
	 * @return
	 */
	public String getName() {
		return name;
	}


	/**
	 * 패스의 이름을 세팅한다.
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	

}
