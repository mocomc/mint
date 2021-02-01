package pep.per.mint.common.data.basic.dashboard;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import pep.per.mint.common.data.CacheableObject;

@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")
public class EngineLimitCount extends CacheableObject{

	/**
	 *
	 */
	private static final long serialVersionUID = -5961516913804512852L;

	@JsonProperty("agentLimitCnt")
	int agentLimitCnt = 0;

	@JsonProperty("brokerLimitCnt")
	int brokerLimitCnt =0;

	@JsonProperty("cpuLimitCnt")
	int cpuLimitCnt =0;

	@JsonProperty("memoryLimitCnt")
	int memoryLimitCnt =0;

	@JsonProperty("diskLimitCnt")
	int diskLimitCnt =0;

	@JsonProperty("processLimitCnt")
	int processLimitCnt =0;

	@JsonProperty("qmgrLimitCnt")
	int qmgrLimitCnt =0;

	@JsonProperty("channelLimitCnt")
	int channelLimitCnt =0;

	@JsonProperty("queueLimitCnt")
	int queueLimitCnt =0;

	@JsonProperty("triggerLimitCnt")
	int triggerLimitCnt =0;

	@JsonProperty("iipAgentLimitCnt")
	int iipAgentLimitCnt =0;


	public int getAgentLimitCnt() {
		return agentLimitCnt;
	}

	public void setAgentLimitCnt(int agentLimitCnt) {
		this.agentLimitCnt = agentLimitCnt;
	}

	public int getBrokerLimitCnt() {
		return brokerLimitCnt;
	}

	public void setBrokerLimitCnt(int brokerLimitCnt) {
		this.brokerLimitCnt = brokerLimitCnt;
	}

	public int getCpuLimitCnt() {
		return cpuLimitCnt;
	}

	public void setCpuLimitCnt(int cpuLimitCnt) {
		this.cpuLimitCnt = cpuLimitCnt;
	}

	public int getMemoryLimitCnt() {
		return memoryLimitCnt;
	}

	public void setMemoryLimitCnt(int memoryLimitCnt) {
		this.memoryLimitCnt = memoryLimitCnt;
	}

	public int getDiskLimitCnt() {
		return diskLimitCnt;
	}

	public void setDiskLimitCnt(int diskLimitCnt) {
		this.diskLimitCnt = diskLimitCnt;
	}

	public int getProcessLimitCnt() {
		return processLimitCnt;
	}

	public void setProcessLimitCnt(int processLimitCnt) {
		this.processLimitCnt = processLimitCnt;
	}

	public int getQmgrLimitCnt() {
		return qmgrLimitCnt;
	}

	public void setQmgrLimitCnt(int qmgrLimitCnt) {
		this.qmgrLimitCnt = qmgrLimitCnt;
	}

	public int getChannelLimitCnt() {
		return channelLimitCnt;
	}

	public void setChannelLimitCnt(int channelLimitCnt) {
		this.channelLimitCnt = channelLimitCnt;
	}

	public int getQueueLimitCnt() {
		return queueLimitCnt;
	}

	public void setQueueLimitCnt(int queueLimitCnt) {
		this.queueLimitCnt = queueLimitCnt;
	}

	public int getIipAgentLimitCnt() {
		return iipAgentLimitCnt;
	}

	public void setIipAgentLimitCnt(int iipAgentLimitCnt) {
		this.iipAgentLimitCnt = iipAgentLimitCnt;
	}

	public int getTriggerLimitCnt() {
		return triggerLimitCnt;
	}

	public void setTriggerLimitCnt(int triggerLimitCnt) {
		this.triggerLimitCnt = triggerLimitCnt;
	}
}
