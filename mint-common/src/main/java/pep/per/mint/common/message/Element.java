package pep.per.mint.common.message;
 
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List; 

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import pep.per.mint.common.data.FieldPath;
import pep.per.mint.common.util.Util;

/**
 * <blockquote>
 * <pre>
 * 메시지 트리 구조를 구성하는 필드 엘리먼트 클래스.
 * 아래 메시지 트리에서 root, root.bin, root.bin.data, root.msg 들이  Element에 해당하며
 * 부모/자식 엘리먼트를 가지며, 엘리먼트는 Serializable을 구현한 값을 가질 수도 있다.
 * 엘리먼트가 가지는 값은 Serializable을 구현한 어떤한 유형도 가능하다.
 *  
 * <B>[Message Tree]</B>
 * <table>
 * <tr><td align="center" colspan="2">root<br>/\</td></tr>
 * <tr><td><pre>bin
 * |</pre></td><td valign="top">msg</td></tr>
 * <tr><td>data</td></tr>
 * </table>
 * 
 * </pre>
 * </blockquote>
 * @author Solution TF
 *
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")
public class Element<T extends Serializable> implements Serializable, Cloneable{
	
	/**
	 * <blockquote>
	 * <pre>
	 * 엘리먼트가 가지는 값
	 * 반드시 Serializable 인터페이스를 implement해야만 한다.
	 * </pre>
	 * </blockquote>
	 */
	@JsonProperty
	private T value;
	
	/**
	 * <blockquote>
	 * <pre>
	 * 엘리먼트 이름
	 * 엘리먼트 이름은 메시지 트리 내에서 루트부터 특정 엘리먼트까지의 위치를 표현한 필드패스를 표현한때 사용된다.
	 * 아래 메시지 트리내에서 이름 "a"인 엘리먼트에 접근하기위한 msg 필드 패스는 "data.a.c"가 된다.
	 * <B>[Message Tree]</B>
	 * <table>
	 * <tr><td align="center" colspan="2">root<br>|</td></tr>
	 * <tr><td align="center" colspan="2">msg<br>|</td></tr>
	 * <tr><td align="center" colspan="2">data<br>/\</td></tr>
	 * <tr><td align="center">a<br>/\</td><td align="center" valign="top">b</td></tr>
	 * <tr><td><table><tr><td>c</td><td>d</td></tr></table></td><td></td></tr>
	 * </table>
	 * </pre>
	 * </blockquote>
	 */
	@JsonProperty
	private String name;
	
	/**
	 * <blockquote>
	 * <pre>
	 * 현재 Element가 "root.msg" 즉 메시지 루트인지 판단하는 구분값
	 * </pre>
	 * </blockquote>
	 * 
	 */
	@JsonProperty
	private boolean isRoot = false;
	
	/**
	 * <blockquote>
	 * <pre>
	 * 현재 Element의 부모 Element
	 * </pre>
	 */
	@JsonIgnore
	private Element parent;
	
	/**
	 * <blockquote>
	 * <pre>
	 * 현재 Element의 자식 Element Map
	 * 자식 엘리먼트들은 Map collection 저장되며, Map에는 엘리먼트 이름을 key로 동일이름의 엘리먼트들의 리스트를 value로 저장한다.
	 * 
	 * </pre>
	 */
	@JsonProperty
	LinkedHashMap<String, List<Element>> childrenMap = new LinkedHashMap<String, List<Element>>();

	/**
	 * <blockquote>
	 * <pre>
	 * Element 클래스 생성자
	 * 인수로 받은 이름으로 엘리먼트 인스턴스를 생성한다.
	 * </pre>
	 * </blockquote>
	 * @param name 엘리먼트 이름
	 */
	public Element(String name) {
		super();
		this.name = name;
	}

	/**
	 * Element 클래스 생성자
	 * 인수로 받은 이름 및 값으로 엘리먼트 인스턴스를 생성한다.
	 * @param name 엘리먼트 이름
	 * @param value 엘리먼트 value
	 */
	public Element(String name, T value) {
		this(name);
		this.value = value;
	}
	
	/**
	 * 엘리먼트의 값을 리턴한다.
	 * @return 
	 */
	public T getValue() {
		return value;
	}

	/**
	 * 엘리먼트의 값을 세팅한다.
	 * @param value
	 */
	public void setValue(T value) {
		this.value = value;
	}

	/**
	 * 엘리먼트의 이름을 리턴한다.
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * 엘리먼트의 이름을 세팅한다.
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * 엘리먼트의 자식 엘리먼트 Map 내에 특정이름의 자식 엘리먼트를 삭제한다.
	 * @param childName
	 */
	public void resetChildren(String childName){
		childrenMap.remove(childName);
	}
	
	/**
	 * 자식엘리먼트를 자식 엘리먼트 Map에 추가한다.
	 * @param child 추가할 자식 Element
	 * @return 추가된 자식 Element
	 */
	public Element<T> addChild(Element child){
		List<Element> children = childrenMap.get(child.getName());
		
		if(children == null) {
			children = new ArrayList<Element>(); 
			childrenMap.put(child.getName(), children);
		}
		children.add(child);
		child.setParent(this);
		
		return this;
	}
	 
	/**
	 * 자식 엘리먼트 Map에서 이름에 해당하는 자식 엘리먼트 리스트를 얻어 리턴한다.
	 * @param name 얻어오려는 자식 엘리먼트 이름
	 * @return 자식 엘리먼트 리스트 Element
	 */
	public List<Element> getChildList(String name){
		return childrenMap.get(name);
	}
	
	/**
	 * 자식 엘리먼트 Map에서 이름에 해당하는 자식 엘리먼트 리스트중 첫번재 엘리먼트를 얻어 리턴한다.
	 * 해당 이름의 자식 엘리먼트가 존재하지 않으면 null을 리턴하므로 호출자는 null 체크를 해주어야 한다.
	 * @param name 얻어오려는 자식 엘리먼트 이름
	 * @return 자식 엘리먼트
	 */
	public Element getChildAtFirst(String name){
		List<Element> children = childrenMap.get(name);
		if(Util.isEmpty(children)) return null;
		return children.get(0);
	}
	  
	/**
	 * 자식 엘리먼트 Map에서 이름에 해당하는 자식 엘리먼트 리스트중 첫번재 엘리먼트의 값을 얻어 리턴한다.
	 * 해당 이름의 자식 엘리먼트가 존재하지 않으면 null을 리턴하므로 호출자는 null 체크를 해주어야 한다.
	 * @param name 얻어오려는 자식 엘리먼트 이름
	 * @return 자식 엘리먼트의 값
	 */
	public Object getChildValueAtFirst(String name){
		List<Element> children = childrenMap.get(name);
		if(Util.isEmpty(children)) return null;
		Element ch = children.get(0);
		if(Util.isEmpty(ch)) return null;
		return ch.getValue();
	}
	 
	/**
	 * 현재 엘리먼트의 부모 엘리먼트를 리턴한다.
	 * @return
	 */
	public Element getParent() {
		return parent;
	}

	/**
	 * 현재 엘리먼트의 부모 엘리먼트를 세팅한다.
	 * @param parent
	 */
	public void setParent(Element parent) {
		this.parent = parent;
	}

	/**
	 * 현재 Element의 자식 Element Map collection을 리턴한다.
	 * @return
	 */
	public LinkedHashMap<String, List<Element>> getChildrenMap() {
		return childrenMap;
	}

	/**
	 * 현재 Element의 자식 Element Map collection을 세팅한다.
	 * @param childrenMap
	 */
	public void setChildrenMap(LinkedHashMap<String, List<Element>> childrenMap) {
		this.childrenMap = childrenMap;
	}

	/**
	 * 현재 Element가 "root.msg" 즉 메시지 루트인지 판단하는 구분값을 리턴한다.
	 * @return
	 */
	public boolean isRoot() {
		return isRoot;
	}

	/**
	 * 현재 Element가 "root.msg" 즉 메시지 루트인지 판단하는 구분값을 세팅한다.
	 * @return
	 */
	public void setRoot(boolean isRoot) {
		this.isRoot = isRoot;
	}
	
	/**
	 * <blockquote>
	 * <pre>
	 * 현재 엘리먼트의 이름 및 값을 문자열로 리턴한다.
	 * 리턴 형태는 {name:x,value:y}
	 * </pre>
	 * </blockquote>
	 * @return
	 */
	public String toString(){
		StringBuffer sb = new StringBuffer();
		sb.append("{name:").append(name).append(",value:").append(Util.toString(value)).append("}");
		return sb.toString();
	}
	
	/**
	 * <blockquote>
	 * <pre>
	 * root 로부터 현재 엘리먼트까지의 필드패스를 얻어온다.
	 * </pre>
	 * </blockquote>
	 * @return
	 */
	public FieldPath getFieldPath(){
		FieldPath fieldPath = new FieldPath();
		List<String>pathList = fieldPath.getPathList();
		pathList.add(name);
		
		if(parent != null && !parent.isRoot()){
			fieldPath = getFieldPath(parent, fieldPath);
		}
		return fieldPath;
	}

	private FieldPath getFieldPath(Element element, FieldPath fieldPath) {
		List<String> pathList = fieldPath.getPathList();
		pathList.add(0, element.getName());
		
		Element parent = element.getParent();
		if(parent != null && !parent.isRoot()){
			fieldPath = getFieldPath(parent, fieldPath);
		}
		return fieldPath;
	}
	
	
	/* (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	/*@Override
	public Object clone() throws CloneNotSupportedException {
		try{
			Element cloned = (Element)super.clone();
			Element p = (Element)cloned.getParent();
			if(p != null) cloned.setParent((Element)p.clone());
			
			cloned.setChildrenMap((LinkedHashMap<String, List<Element>>)cloned.getChildrenMap().clone());
			
			Serializable v = cloned.getValue();
			
			if(v != null) {
				T copyValue = MessageUtil.copy(v);
				cloned.setValue(copyValue);
			}
			
			return cloned;
		}catch(Exception e){
			e.printStackTrace();
			throw new CloneNotSupportedException(e.getMessage());
		}
	}*/
	
	
	/*T copy(Serializable serializable) throws IOException, ClassNotFoundException{
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
		
	}*/
	
}
