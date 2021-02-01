package pep.per.mint.common.exception;

import pep.per.mint.common.data.FieldDefinition;
import pep.per.mint.common.data.MessageSet;

public class ParserException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7086332145916161457L;
	
	String errorMsgId = "PAR9999";//예상치 못한 Exception에 해당하는 값

	MessageSet messageSet;
	
	FieldDefinition fieldDefinition;
	
	public ParserException() {
		// TODO Auto-generated constructor stub
		super();
	}

	public ParserException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public ParserException(String message, String errorMsgId) {
		this(message);
		// TODO Auto-generated constructor stub
		this.errorMsgId = errorMsgId;
	}

	
	public ParserException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	public ParserException(Throwable cause, String errorMsgId) {
		this(cause);
		// TODO Auto-generated constructor stub
		this.errorMsgId = errorMsgId;
	}
	
	public ParserException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}
	
	public ParserException(String message, Throwable cause, String errorMsgId) {
		this(message, cause);
		// TODO Auto-generated constructor stub
		this.errorMsgId = errorMsgId;
	}

	public void setMessageSet(MessageSet messageSet) {
		// TODO Auto-generated method stub
		this.messageSet = messageSet;
	}

	public void setFieldDefinition(FieldDefinition fieldDefinition) {
		// TODO Auto-generated method stub
		this.fieldDefinition = fieldDefinition;
	}

	/**
	 * @return the messageSet
	 */
	public MessageSet getMessageSet() {
		return messageSet;
	}

	/**
	 * @return the fieldDefinition
	 */
	public FieldDefinition getFieldDefinition() {
		return fieldDefinition;
	}

	/**
	 * @return the errorMsgId
	 */
	public String getErrorMsgId() {
		return errorMsgId;
	}
	
	

}
