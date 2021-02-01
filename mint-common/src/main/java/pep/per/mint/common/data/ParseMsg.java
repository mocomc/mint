/**
 * 
 */
package pep.per.mint.common.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import pep.per.mint.common.message.Message;

/**
 * @author Solution TF
 *
 */

@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")
public class ParseMsg {

	@JsonProperty
	String msgSetId;
	
	 @JsonProperty
	 Message message;
	 
	 @JsonProperty
	 String stringData;
	 
	 @JsonProperty
	 String parseLog;

	/**
	 * @return the message
	 */
	public Message getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(Message message) {
		this.message = message;
	}

	 

	/**
	 * @return the parseLog
	 */
	public String getParseLog() {
		return parseLog;
	}

	/**
	 * @param parseLog the parseLog to set
	 */
	public void setParseLog(String parseLog) {
		this.parseLog = parseLog;
	}

	/**
	 * @return the msgSetId
	 */
	public String getMsgSetId() {
		return msgSetId;
	}

	/**
	 * @param msgSetId the msgSetId to set
	 */
	public void setMsgSetId(String msgSetId) {
		this.msgSetId = msgSetId;
	}

	/**
	 * @return the stringData
	 */
	public String getStringData() {
		return stringData;
	}

	/**
	 * @param stringData the stringData to set
	 */
	public void setStringData(String stringData) {
		this.stringData = stringData;
	}
	 
	 
	
	 
	 

}
