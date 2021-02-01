package pep.per.mint.common.parser;

import java.nio.ByteOrder;



public interface TypeHandler<T> {

	/*public T deserialize(String value) throws Exception ;
	public T deserialize(byte[] value) throws Exception;
	public T deserialize(byte[] value, String ccsid) throws Exception;
	 */
	
	/**
	 * 문자열 또는 바이트 배열 입력데이타를 지정된 타입의 객체로 변환한다.
	 * @param type - 최종 변환 대상 타입 ()
	 * @param value
	 * @param ccsid
	 * @param defaultValue
	 * @param justify
	 * @param length
	 * @param padding
	 * @param order
	 * @return
	 * @throws Exception
	 */
	public T deserialize(int type, byte[] value, String ccsid, T defaultValue, int justify, int length, String padding, ByteOrder order) throws Exception;
	
	public byte[] serialize(int type, T value, String ccsid, T defaultValue , int justify, int length, String padding, ByteOrder order) throws Exception;
	
//	public byte[] serialize(T value, T defaultValue, ByteOrder order) throws Exception;

}
