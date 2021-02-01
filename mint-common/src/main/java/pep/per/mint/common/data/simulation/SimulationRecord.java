/**
 * 
 */
package pep.per.mint.common.data.simulation;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import pep.per.mint.common.data.CacheableObject;

/**
 * @author Solution TF
 *
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")
public class SimulationRecord extends CacheableObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2884419943104386453L;

	//--------------------------------------
	//TABLE RECORD STRUCTURE
	//--------------------------------------
	//TB_SIM_TABLE_DS
	//--------------------------------------
	//RECORD_ID int primary key,
	//MSG       varchar(4000) not null,
	//SND_DATE  varchar(20) not null,
	//SND_STAT  char(1) not null default 'N',
	//ERR_MSG   varchar(2000),
	//TRY_CNT   int,
	//REG_DATE  varchar(20) not null
	
	/**
	 * 
	 */
	public SimulationRecord() {
		// TODO Auto-generated constructor stub
		super();
	}
	
	@JsonProperty
	String msg;
	
	@JsonProperty
	String sndDate;
	
	@JsonProperty
	String sndStat = "N";
	
	@JsonProperty
	String errMsg;
	
	@JsonProperty
	int tryCnt = 0 ;
	
	@JsonProperty
	String regDate;
	
	@JsonProperty
	String regId;

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
	 * @return the sndDate
	 */
	public String getSndDate() {
		return sndDate;
	}

	/**
	 * @param sndDate the sndDate to set
	 */
	public void setSndDate(String sndDate) {
		this.sndDate = sndDate;
	}

	/**
	 * @return the sndStat
	 */
	public String getSndStat() {
		return sndStat;
	}

	/**
	 * @param sndStat the sndStat to set
	 */
	public void setSndStat(String sndStat) {
		this.sndStat = sndStat;
	}

	/**
	 * @return the errMsg
	 */
	public String getErrMsg() {
		return errMsg;
	}

	/**
	 * @param errMsg the errMsg to set
	 */
	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	/**
	 * @return the tryCnt
	 */
	public int getTryCnt() {
		return tryCnt;
	}

	/**
	 * @param tryCnt the tryCnt to set
	 */
	public void setTryCnt(int tryCnt) {
		this.tryCnt = tryCnt;
	}

	/**
	 * @return the regDate
	 */
	public String getRegDate() {
		return regDate;
	}

	/**
	 * @param regDate the regDate to set
	 */
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}

	/**
	 * @return the regId
	 */
	public String getRegId() {
		return regId;
	}

	/**
	 * @param regId the regId to set
	 */
	public void setRegId(String regId) {
		this.regId = regId;
	}
	
	
	
	
	

}
