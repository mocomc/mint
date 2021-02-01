package pep.per.mint.common.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")
public class RestResult<T>{
	
	public static final int RESULT_FAIL    = 0;
	public static final int RESULT_SUCCESS = 1;

	@JsonProperty
	int result;
	
	@JsonProperty
	String msg = "";
 	
	@JsonProperty
	T value;
	
	@JsonProperty
	Exception exception;

	/**
	 * @return the result
	 */
	public int getResult() {
		return result;
	}

	/**
	 * @param result the result to set
	 */
	public void setResult(int result) {
		this.result = result;
	}

	/**
	 * @return the msg
	 */
	public String getMsg() {
		return msg;
	}

	/**
	 * @param msg the msg to set
	 */
	public void setMsg(String msg) {
		this.msg = msg;
	}

	/**
	 * @return the value
	 */
	public T getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(T value) {
		this.value = value;
	}

	/**
	 * @return the e
	 */
	public Exception getException() {
		return exception;
	}

	/**
	 * @param e the e to set
	 */
	public void setException(Exception e) {
		this.exception = e;
	}
	
	
}
