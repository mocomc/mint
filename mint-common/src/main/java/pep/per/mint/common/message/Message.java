package pep.per.mint.common.message;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import pep.per.mint.common.data.MessageSet;

/**
 * <blockquote>
 * <pre>
 * 트리구조의 필드들로 구성된 메시지 클래스.
 * 아래는 메시지 기본 트리구조를 보여준다.
 * Message는 최상위에 Element root를 하나 가진다.
 * root는 자식 엘리먼트로 Element 인스턴스인 bin, msg를 각각  하나씩 가진다.
 * bin는 자식 엘리먼트로 Element 인스턴스인 data를 가지며 data에는 값으로 바이너리 형태의 메시지 원본 데이터를 저장하게 된다.
 * msg는 자식 엘리먼트로 MessageSet에서 정의한 구조대로 메시지 필드 트리를 구성한 트리구조체를 자식 엘리먼트로 가지게 된다.
 * 
 * <B>[Message Tree]</B>
 * <table>
 * <tr style="color:blue"><td align="center" colspan="2">root<br>/\</td></tr>
 * <tr style="color:blue"><td><pre>bin
 * |</pre></td><td valign="top">msg</td></tr>
 * <tr style="color:blue"><td>data</td></tr>
 * </table>
 * 
 * 아래 트리구조는 Message 내의 root.msg 아래에 임의의 MessageSet 구조대로 필드 데이터를 구성한 예이다.
 * <B>[root.msg 하위 트리 구성 예]</B>
 * <table>
 * <tr style="color:blue"><td align="center" colspan="2">root<br>|</td></tr>
 * <tr style="color:blue"><td align="center" colspan="2">msg<br>|</td></tr>
 * <tr style="color:red"><td align="center" colspan="2">data<br>/\</td></tr>
 * <tr style="color:red"><td align="center">a<br>/\</td><td align="center" valign="top">b</td></tr>
 * <tr style="color:red"><td><table><tr style="color:red"><td>c</td><td>d</td></tr></table></td><td></td></tr>
 * </table>
 * msg 하위를 구성한 트리구조를 살펴보면, 하나의 복합 필드인 data 아래로 자식 필드 a,b를 구성하고 있다.
 * data는 사실상 메시지부의 루트로 메시지부에서 한번만 발생되도록 해야 한다. 
 * 다시 복합 필드인 a 는 자식필드 c,d로 구성되어 있다. 
 * </pre>
 * </blockquote>
 * @see MessageUtil
 * @author Solution TF
 *
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")
public class Message implements Serializable, Cloneable{
	
	/**
	 * ROOT 엘리먼트 이름
	 */
	public final static String ELEMENT_NAME_ROOT 		= "root";
	
	/**
	 * 바이너리 엘리먼트 이름
	 */
	public final static String ELEMENT_NAME_BINARY 		= "bin";
	
	/**
	 * 바이너리 데이터 엘리먼트 이름
	 */
	public final static String ELEMENT_NAME_BINARY_DATA	= "data";
	
	/**
	 * 메시지 엘리먼트 이름
	 */
	public final static String ELEMENT_NAME_MSG 		= "msg";
	
	/**
	 * 최상위 루트 엘리먼트로 메시지 내에 하나만 존재한다.
	 *  
	 */
	@JsonProperty
	Element root;
	
	/**
	 * <blockquote>
	 * <pre>
	 * 원본 바이너리 데이터를 저장하기위한 목적으로 사용되며 root 엘리먼트 밑에 하나만 존재한다.
	 * </pre>
	 * </blockquote>
	 */
	@JsonProperty
	Element bin;
	
	/**
	 * <blockquote>
	 * <pre>
	 * 원본 바이너리 메시지를 파싱 및 매핑후 반환되는 메시지 내에  트리구조의 필드 엘리먼트들을 
	 * 저장하기 위한 목적으로 사용되면 root 엘리먼트 밑에 하나만 존재한다.
	 * </pre>
	 * </blockquote>
	 */
	@JsonProperty
	Element msg;
	
	@JsonProperty
	MessageSet msgSet;
	
	/**
	 * <blockquote>
	 * <pre>
	 * Message 클래스 기본 생성자
	 * 기본 생성자를 통해 Message 인스턴스를 생성할 경우 root 및 root 의 자식 엘리먼트로 bin, msg 엘리먼트가 구성된다.
	 * </pre>
	 * </blockquote>
	 */
	public Message(){ 
		root = new Element(ELEMENT_NAME_ROOT);
		bin = new Element(ELEMENT_NAME_BINARY);
		msg = new Element(ELEMENT_NAME_MSG);
		msg.setRoot(true);
		root.addChild(bin);
		root.addChild(msg);
	}	
	
	/**
	 * <blockquote>
	 * <pre>
	 * Message 클래스 생성자
	 * 기본 생성자를 통해 Message 인스턴스를 생성할 경우 root 및 root 의 자식 엘리먼트로 bin, msg 엘리먼트가 구성된다.
	 * 입력으로 받은 문자열 형태의 메시지 원본 데이터를 root.bin.data 엘리먼트의 값으로 저장한다.
	 * 캐릭터셋은 시스템 기본값을 사용한다.
	 * </pre>
	 * @param msg 문자열 형태의 메시지 원본 데이터
	 */
	public Message(String msg) {
		this(msg.getBytes());
	}
	
	
	/**
	 * <blockquote>
	 * <pre>
	 * Message 클래스 생성자
	 * 기본 생성자를 통해 Message 인스턴스를 생성할 경우 root 및 root 의 자식 엘리먼트로 bin, msg 엘리먼트가 구성된다.
	 * 입력으로 받은 바이너리 배열(byte[]) 형태의 메시지 원본 데이터를 root.bin.data 엘리먼트의 값으로 저장한다.
	 * </pre>
	 * @param msg 바이너리 배열(byte[]) 형태의 메시지 원본 데이터
	 */
	public Message(byte[] msg) {
		this();
		Element<byte[]> binElement = new Element<byte[]>(ELEMENT_NAME_BINARY_DATA, msg);
		bin.addChild(binElement);
	}
	
	/**
	 * root.msg 엘리먼트를 리턴한다.
	 * @return
	 */
	public Element getMsgElement(){
		return msg;
	}
	
	/**
	 * Element[path=root.bin.data] 의 값인 바이트 어레이(byte[]) 값을 리턴한다. 
	 * @return Element[path=root.bin.data] 의 값인 바이트 어레이(byte[])
	 */
	public byte[] getBinaryData(){
		Element<byte[]> element = bin.getChildAtFirst(ELEMENT_NAME_BINARY_DATA);
		return element == null ? null : element.getValue();
	}
	
	public void setBinaryData(byte[] msg) {
		bin.getChildAtFirst(ELEMENT_NAME_BINARY_DATA).setValue(msg);
	}
	
	/**
	 * Element[path=root.bin.data] 의 값인 바이트 어레이(byte[]) 값을 리턴한다. 
	 * @return Element[path=root.bin.data] 의 값인 바이트 어레이(byte[])
	 */
	public byte[] toBytes(){
		return getBinaryData();
	}
	
	/**
	 * Element[path=root.bin.data] 의 값인 바이트 어레이(byte[]) 값을 String 값으로 변환하여 반환한다.
	 * 값이 존재하지 않을 경우 NULL을 리턴하도록 구현함.
	 */
	public String toString(){
		byte[] b = toBytes();
		return b == null ? null : new String(b);
	}
	
	/**
	 * Element[path=root.bin.data] 의 값인 바이트 어레이(byte[]) 값을 특정 ccsid를 적용한 String 값으로 변환하여 반환한다. 
	 * 없을 경우 NULL을 리턴하도록 구현함.
	 * @param charset byte[] 데이터를 문자열로 변환시 사용될 charset 값(예:UTF-8, EUC-KR)
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public String toString(String charset) throws UnsupportedEncodingException{
		byte[] b = toBytes();
		return b == null ? null : new String(b, charset);
	}
	 
	
}
