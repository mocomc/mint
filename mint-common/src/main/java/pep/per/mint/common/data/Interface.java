package pep.per.mint.common.data;
 

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;


@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")
public class Interface  extends CacheableObject{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3922605124868273683L;
	
	public static final int IF_TYPE_NONE = 0;
	
	public static final int IF_ROUTE_TYPE_RULESET = 0;
	
	public static final int IF_ROUTE_TYPE_INTERFACE = 1;
	
	
	@JsonProperty
	String name;
	
	@JsonProperty
	int type;
	
	@JsonProperty
	String typeNm;
	
	@JsonProperty
	String sendBizCd;
	
	@JsonProperty
	String sendBizNm;
	
	@JsonProperty
	String recvBizCd;
	
	@JsonProperty
	String recvBizNm;
	
	
	@JsonProperty
	int routeType;
	

	@JsonProperty
	String routeTypeNm;
	
	@JsonProperty
	String description;
	
	@JsonProperty
	String regDate;
	
	@JsonProperty
	String regId;
	
	@JsonProperty
	String modDate;
	
	@JsonProperty
	String modId;

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the type
	 */
	public int getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(int type) {
		this.type = type;
	}

	/**
	 * @return the sendBizCd
	 */
	public String getSendBizCd() {
		return sendBizCd;
	}

	/**
	 * @param sendBizCd the sendBizCd to set
	 */
	public void setSendBizCd(String sendBizCd) {
		this.sendBizCd = sendBizCd;
	}

	/**
	 * @return the recvBizCd
	 */
	public String getRecvBizCd() {
		return recvBizCd;
	}

	/**
	 * @param recvBizCd the recvBizCd to set
	 */
	public void setRecvBizCd(String recvBizCd) {
		this.recvBizCd = recvBizCd;
	}

	
	/**
	 * @return the routeType
	 */
	public int getRouteType() {
		return routeType;
	}

	/**
	 * @param routeType the routeType to set
	 */
	public void setRouteType(int routeType) {
		this.routeType = routeType;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
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

	/**
	 * @return the modDate
	 */
	public String getModDate() {
		return modDate;
	}

	/**
	 * @param modDate the modDate to set
	 */
	public void setModDate(String modDate) {
		this.modDate = modDate;
	}

	/**
	 * @return the modId
	 */
	public String getModId() {
		return modId;
	}

	/**
	 * @param modId the modId to set
	 */
	public void setModId(String modId) {
		this.modId = modId;
	}

	/**
	 * @return the typeNm
	 */
	public String getTypeNm() {
		return typeNm;
	}

	/**
	 * @param typeNm the typeNm to set
	 */
	public void setTypeNm(String typeNm) {
		this.typeNm = typeNm;
	}

	/**
	 * @return the sendBizNm
	 */
	public String getSendBizNm() {
		return sendBizNm;
	}

	/**
	 * @param sendBizNm the sendBizNm to set
	 */
	public void setSendBizNm(String sendBizNm) {
		this.sendBizNm = sendBizNm;
	}

	/**
	 * @return the recvBizNm
	 */
	public String getRecvBizNm() {
		return recvBizNm;
	}

	/**
	 * @param recvBizNm the recvBizNm to set
	 */
	public void setRecvBizNm(String recvBizNm) {
		this.recvBizNm = recvBizNm;
	}

	/**
	 * @return the routeTypeNm
	 */
	public String getRouteTypeNm() {
		return routeTypeNm;
	}

	/**
	 * @param routeTypeNm the routeTypeNm to set
	 */
	public void setRouteTypeNm(String routeTypeNm) {
		this.routeTypeNm = routeTypeNm;
	}
	
	
	
	
}
