package pep.per.mint.common.data.basic.dataset;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;


import pep.per.mint.common.data.CacheableObject;
import pep.per.mint.common.data.basic.User;
import pep.per.mint.common.util.Util;

@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")
public class DataSet extends CacheableObject {

	/**
	 *
	 */
	private static final long serialVersionUID = 425591532362329819L;

	public static final String DF_XML = "0";
	public static final String DF_JSON = "1";

	@JsonProperty
	String dataSetId = defaultStringValue;

	@JsonProperty
	String name1 = defaultStringValue;

	@JsonProperty
	String name2 = defaultStringValue;

	@JsonProperty
	String cd = defaultStringValue;

	@JsonProperty
	String dataFormat = defaultStringValue;

	@JsonProperty
	String dataFormatNm = defaultStringValue;

	@JsonProperty
	String encryptType = defaultStringValue;

	@JsonProperty
	String encryptTypeNm = defaultStringValue;

	@JsonProperty
	int length = -1;

	@JsonProperty
	String isStandard = "N";

	@JsonProperty
	String use = "N";

	@JsonProperty
	String isMapped = "N";

	@JsonProperty
	String isRoot = "Y";


	@JsonProperty
	//String recordDelimiter = System.lineSeparator(); //java-7 or higher
	String recordDelimiter = System.getProperty("line.separator");

	@JsonProperty
	String fieldDelimiter = ",";

	@JsonProperty
	List<DataField> dataFieldList = new ArrayList<DataField>();

	@JsonProperty
	Map<String, DataSet> complexTypeMap = new LinkedHashMap<String, DataSet>();

	@JsonProperty
    String comments = defaultStringValue;

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

    // TODO :: 삭제예정
    //@JsonProperty
    //DataSetInterfaceMap dataSetInterfaceMap;


	public String getDataSetId() {
		return dataSetId;
	}

	public void setDataSetId(String dataSetId) {
		this.dataSetId = dataSetId;
	}

	public String getName1() {
		return name1;
	}

	public void setName1(String name1) {
		this.name1 = name1;
	}

	public String getName2() {
		return name2;
	}

	public void setName2(String name2) {
		this.name2 = name2;
	}



	public String getCd() {
		return cd;
	}

	public void setCd(String cd) {
		this.cd = cd;
	}

	public String getDataFormat() {
		return dataFormat;
	}

	public void setDataFormat(String dataFormat) {
		this.dataFormat = dataFormat;
	}



	public String getDataFormatNm() {
		return dataFormatNm;
	}

	public void setDataFormatNm(String dataFormatNm) {
		this.dataFormatNm = dataFormatNm;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public String getEncryptType() {
		return encryptType;
	}

	public void setEncryptType(String encryptType) {
		this.encryptType = encryptType;
	}



	public String getEncryptTypeNm() {
		return encryptTypeNm;
	}

	public void setEncryptTypeNm(String encryptTypeNm) {
		this.encryptTypeNm = encryptTypeNm;
	}

	public String getIsStandard() {
		return isStandard;
	}

	public void setIsStandard(String isStandard) {
		this.isStandard = isStandard;
	}

	public String getUse() {
		return use;
	}

	public void setUse(String use) {
		this.use = use;
	}

	public String getIsMapped() {
		return isMapped;
	}

	public void setIsMapped(String isMapped) {
		this.isMapped = isMapped;
	}




	public String getIsRoot() {
		return isRoot;
	}

	public void setIsRoot(String isRoot) {
		this.isRoot = isRoot;
	}


	public List<DataField> getDataFieldList() {
		return dataFieldList;
	}

	public void setDataFieldList(List<DataField> dataFieldList) {
		this.dataFieldList = dataFieldList;
	}



	public Map<String, DataSet> getComplexTypeMap() {
		return complexTypeMap;
	}

	public void setComplexTypeMap(Map<String, DataSet> complexTypeMap) {
		this.complexTypeMap = complexTypeMap;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
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


// TODO :: 삭제할것
//	public DataSetInterfaceMap getDataSetInterfaceMap() {
//		return dataSetInterfaceMap;
//	}
//
//	public void setDataSetInterfaceMap(DataSetInterfaceMap dataSetInterfaceMap) {
//		this.dataSetInterfaceMap = dataSetInterfaceMap;
//	}



	/**
	 * @return the recordDelimiter
	 */
	public String getRecordDelimiter() {
		return recordDelimiter;
	}

	/**
	 * @param recordDelimiter the recordDelimiter to set
	 */
	public void setRecordDelimiter(String recordDelimiter) {
		this.recordDelimiter = recordDelimiter;
	}

	/**
	 * @return the fieldDelimiter
	 */
	public String getFieldDelimiter() {
		return fieldDelimiter;
	}

	/**
	 * @param fieldDelimiter the fieldDelimiter to set
	 */
	public void setFieldDelimiter(String fieldDelimiter) {
		this.fieldDelimiter = fieldDelimiter;
	}

	@Override
    public boolean equals(Object obj) {

    	DataSet compared = (DataSet)obj;

    	return (
    		name1.equals(compared.getName1()) &&
    		name2.equals(compared.getName2()) &&
    		dataFormat.equals(compared.getDataFormat()) &&
    		encryptType.equals(compared.getEncryptType()) &&
    		length == compared.getLength() &&
    		isStandard.equals(compared.getIsStandard()) &&
    		use.equals(compared.getUse()) &&
    		isMapped.equals(compared.getIsMapped())&&
    		isRoot.equals(compared.getIsRoot()) &&
    		comments.equals(compared.getComments()) &&
    		delYn.equals(compared.getDelYn()) &&
    		dataFieldList.equals(compared.getDataFieldList())
    	);
    }

}
