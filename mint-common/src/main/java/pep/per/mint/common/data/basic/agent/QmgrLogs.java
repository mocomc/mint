package pep.per.mint.common.data.basic.agent;

import java.util.List;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
//import org.codehaus.jackson.annotate.JsonProperty;
//import org.codehaus.jackson.annotate.JsonTypeInfo;

import pep.per.mint.common.data.CacheableObject;
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")
public class QmgrLogs extends CacheableObject {
 

	/**
	 * 
	 */
	private static final long serialVersionUID = 5820047121199148530L;
	 
	@JsonProperty
	QmgrInfo qmgrInfo;
	
	@JsonProperty
	QmgrStatusLog qmgrStatusLog;
	
	@JsonProperty
	List<ChannelStatusLog> channelStatusLogs;
	
	
	@JsonProperty
	List<QueueStatusLog> queueStatusLogs;


	public QmgrInfo getQmgrInfo() {
		return qmgrInfo;
	}


	public void setQmgrInfo(QmgrInfo qmgrInfo) {
		this.qmgrInfo = qmgrInfo;
	}


	public QmgrStatusLog getQmgrStatusLog() {
		return qmgrStatusLog;
	}


	public void setQmgrStatusLog(QmgrStatusLog qmgrStatusLog) {
		this.qmgrStatusLog = qmgrStatusLog;
	}


	public List<ChannelStatusLog> getChannelStatusLogs() {
		return channelStatusLogs;
	}


	public void setChannelStatusLogs(List<ChannelStatusLog> channelStatusLogs) {
		this.channelStatusLogs = channelStatusLogs;
	}


	public List<QueueStatusLog> getQueueStatusLogs() {
		return queueStatusLogs;
	}


	public void setQueueStatusLogs(List<QueueStatusLog> queueStatusLogs) {
		this.queueStatusLogs = queueStatusLogs;
	}
	
	
}
