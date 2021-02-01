package pep.per.mint.common.data;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;



@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")
public class CommonCode extends CacheableObject{

	

	/**
	 * 
	 */
	private static final long serialVersionUID = 498574523451923314L;

	@JsonProperty
	String level1;
	
	@JsonProperty
	String level1Nm;
	
	@JsonProperty
	String level2;
	
	@JsonProperty
	String level2Nm;
	
	@JsonProperty
	String cd;
	
	@JsonProperty
	String nm;
	 
	@JsonProperty
	String description = "";
 	 
	@JsonProperty
	String regDate;
	
	@JsonProperty
	String regId;
	
	@JsonProperty
	String modDate;
	
	@JsonProperty
	String modId;

	/**
	 * @return the level1
	 */
	public String getLevel1() {
		return level1;
	}

	/**
	 * @param level1 the level1 to set
	 */
	public void setLevel1(String level1) {
		this.level1 = level1;
	}

	/**
	 * @return the level1Nm
	 */
	public String getLevel1Nm() {
		return level1Nm;
	}

	/**
	 * @param level1Nm the level1Nm to set
	 */
	public void setLevel1Nm(String level1Nm) {
		this.level1Nm = level1Nm;
	}

	/**
	 * @return the level2
	 */
	public String getLevel2() {
		return level2;
	}

	/**
	 * @param level2 the level2 to set
	 */
	public void setLevel2(String level2) {
		this.level2 = level2;
	}

	/**
	 * @return the level2Nm
	 */
	public String getLevel2Nm() {
		return level2Nm;
	}

	/**
	 * @param level2Nm the level2Nm to set
	 */
	public void setLevel2Nm(String level2Nm) {
		this.level2Nm = level2Nm;
	}

	/**
	 * @return the cd
	 */
	public String getCd() {
		return cd;
	}

	/**
	 * @param cd the cd to set
	 */
	public void setCd(String cd) {
		this.cd = cd;
	}

	/**
	 * @return the nm
	 */
	public String getNm() {
		return nm;
	}

	/**
	 * @param nm the nm to set
	 */
	public void setNm(String nm) {
		this.nm = nm;
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

	

}
