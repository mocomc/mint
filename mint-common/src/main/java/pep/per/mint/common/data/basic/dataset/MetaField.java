package pep.per.mint.common.data.basic.dataset;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import pep.per.mint.common.data.CacheableObject;
import pep.per.mint.common.util.Util;

@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")
public class MetaField extends CacheableObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 495677075508055759L;

	/* 공통코드 AN 09
	 * 0 string		문자
	 * 1 number		숫자
	 * 2 boolean	진리값
	 * 3 binary		이진데이터
	 * 4 complex	복합유형
	 */
	public static final String TYPE_STRING  = "0";
	public static final String TYPE_NUMBER  = "1";
	public static final String TYPE_BOOLEAN = "2";	
	public static final String TYPE_BINARY  = "3";
	public static final String TYPE_COMPLEX = "4";

	public static final String JUSTIFY_R = "R";
	public static final String JUSTIFY_L = "L";

	@JsonProperty
	String fieldId = defaultStringValue;
	 
	
	@JsonProperty
	String name1 = defaultStringValue;
	
	@JsonProperty
	String name2 = defaultStringValue;
	

	@JsonProperty
	String cd = defaultStringValue;
	
	
	@JsonProperty
	String type = TYPE_STRING;
	
	
	@JsonProperty
	String typeNm = defaultStringValue;
	
	@JsonProperty
	int length = -1;
  
	@JsonProperty
	String encryptType = defaultStringValue;
	
	@JsonProperty
	String encryptTypeNm = defaultStringValue;
	
	@JsonProperty
	String defaultValue = defaultStringValue;
	
	@JsonProperty
	String justify = JUSTIFY_L;
	
	@JsonProperty
	String justifyNm = defaultStringValue;
	
	
	@JsonProperty
	String padding = " ";
	
	
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

	public String getFieldId() {
		return fieldId;
	}

	public void setFieldId(String fieldId) {
		this.fieldId = fieldId;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTypeNm() {
		return typeNm;
	}

	public void setTypeNm(String typeNm) {
		this.typeNm = typeNm;
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

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public String getJustify() {
		return justify;
	}

	public void setJustify(String justify) {
		this.justify = justify;
	}

	public String getJustifyNm() {
		return justifyNm;
	}

	public void setJustifyNm(String justifyNm) {
		this.justifyNm = justifyNm;
	}

	public String getPadding() {
		return padding;
	}

	public void setPadding(String padding) {
		this.padding = padding;
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

    
    
	
}
