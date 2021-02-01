package pep.per.mint.common.data.basic.dataset;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import pep.per.mint.common.data.CacheableObject;
import pep.per.mint.common.data.basic.User;
import pep.per.mint.common.util.Util;

@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")
public class DataSetHistory extends CacheableObject {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3786710521072083680L;

	@JsonProperty
    String dataSetId = defaultStringValue;

    @JsonProperty
    String version = defaultStringValue;

    @JsonProperty
    String contents = defaultStringValue;

    @JsonProperty
    String delYn = "N";

    @JsonProperty
    String regDate = Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI);

    @JsonProperty
    String regId = defaultStringValue;

    @JsonProperty
    String modDate = defaultStringValue;

    @JsonProperty
    String modId = defaultStringValue;
    
    @JsonProperty
    User regUser;
    
    @JsonProperty
    User modUser;
    
    

	public String getDataSetId() {
		return dataSetId;
	}

	public void setDataSetId(String dataSetId) {
		this.dataSetId = dataSetId;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public String getDelYn() {
		return delYn;
	}

	public void setDelYn(String delYn) {
		this.delYn = delYn;
	}

	public String getRegDate() {
		return regDate;
	}

	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}

	public String getRegId() {
		return regId;
	}

	public void setRegId(String regId) {
		this.regId = regId;
	}

	public String getModDate() {
		return modDate;
	}

	public void setModDate(String modDate) {
		this.modDate = modDate;
	}

	public String getModId() {
		return modId;
	}

	public void setModId(String modId) {
		this.modId = modId;
	}

	public User getRegUser() {
		return regUser;
	}

	public void setRegUser(User regUser) {
		this.regUser = regUser;
	}

	public User getModUser() {
		return modUser;
	}

	public void setModUser(User modUser) {
		this.modUser = modUser;
	}
    
    
    
}
