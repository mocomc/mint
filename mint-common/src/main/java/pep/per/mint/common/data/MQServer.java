package pep.per.mint.common.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")
public class MQServer extends Server{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -9031896008762175788L;

	@JsonProperty
	String qmgr;
	
	@JsonProperty
	String connectionString;

	@JsonProperty
	String queueName;
	
	public MQServer() {
		super();
	}
	
	/**
	 * @return the qmgr
	 */
	public String getQmgr() {
		return qmgr;
	}

	/**
	 * @param qmgr the qmgr to set
	 */
	public void setQmgr(String qmgr) {
		this.qmgr = qmgr;
	}

	/**
	 * @return the connectionString
	 */
	public String getConnectionString() {
		return connectionString;
	}

	/**
	 * @param connectionString the connectionString to set
	 */
	public void setConnectionString(String connectionString) {
		this.connectionString = connectionString;
	}

	/**
	 * @return the queueName
	 */
	public String getQueueName() {
		return queueName;
	}

	/**
	 * @param queueName the queueName to set
	 */
	public void setQueueName(String queueName) {
		this.queueName = queueName;
	}
	
	
	
}
