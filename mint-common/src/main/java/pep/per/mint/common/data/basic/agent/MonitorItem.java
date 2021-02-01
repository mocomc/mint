package pep.per.mint.common.data.basic.agent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
//import org.codehaus.jackson.annotate.JsonProperty;
//import org.codehaus.jackson.annotate.JsonTypeInfo;

import pep.per.mint.common.data.CacheableObject;


@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")
public class MonitorItem extends CacheableObject {

	public final static String ITEM_TYPE_RESOURCE = "0";
	public final static String ITEM_TYPE_PROCESS = "1";
	public final static String ITEM_TYPE_QMGR = "2";
	public final static String ITEM_TYPE_CHANNEL = "3";
	public final static String ITEM_TYPE_QUEUE = "4";

	/**
	 *
	 */
	private static final long serialVersionUID = -3770102662069006708L;

	@JsonProperty
	String itemId  = defaultStringValue;

	@JsonProperty
	String itemType  = defaultStringValue;

	@JsonProperty
	int seq;

	@JsonProperty
	List<ResourceInfo> resources;

	@JsonProperty
	List<ProcessInfo> processes;

	@JsonProperty
	List<QmgrInfo> qmgrs;

	@JsonInclude(Include.NON_NULL)
	@JsonProperty
	Map<String, QmgrInfo> qmgrInfoMap;

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getItemType() {
		return itemType;
	}

	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	public List<ResourceInfo> getResources() {
		return resources;
	}

	public void setResources(List<ResourceInfo> resources) {
		this.resources = resources;
	}

	public List<ProcessInfo> getProcesses() {
		return processes;
	}

	public void setProcesses(List<ProcessInfo> processes) {
		this.processes = processes;
	}

	public List<QmgrInfo> getQmgrs() {
		return qmgrs;
	}

	public void setQmgrs(List<QmgrInfo> qmgrs) {
		this.qmgrs = qmgrs;
	}

	public Map<String, QmgrInfo> getQmgrInfoMap() {
		return qmgrInfoMap;
	}

	public void setQmgrInfoMap(Map<String, QmgrInfo> qmgrInfoMap) {
		this.qmgrInfoMap = qmgrInfoMap;
	}







}
