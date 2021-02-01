package pep.per.mint.common.data.basic;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

import pep.per.mint.common.data.CacheableObject;

@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")
public class TRLogDetailError  extends CacheableObject{

	/**
	 * 
	 */
	private static final long serialVersionUID = 180026811274790133L;
	
	String messageId;

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}
}
