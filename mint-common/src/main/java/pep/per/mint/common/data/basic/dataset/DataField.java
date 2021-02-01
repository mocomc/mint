package pep.per.mint.common.data.basic.dataset;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import pep.per.mint.common.data.CacheableObject;
import pep.per.mint.common.util.Util;

@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")
public class DataField extends CacheableObject {

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
	String parentId = null;

	@JsonProperty
	String dataSetId = defaultStringValue;

	@JsonProperty
	String dataFieldId = defaultStringValue;

	@JsonProperty
	int seq;

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
	int offset = 0;

	@JsonProperty
	int length = -1;

	@JsonProperty
	String encryptType = defaultStringValue;

	@JsonProperty
	String encryptTypeNm = defaultStringValue;

	@JsonProperty
	String isLengthField = "N";

	@JsonProperty
	int repeatCount = 1;

	@JsonProperty
	String hasRepeatCountField = "N";

	@JsonProperty
	String repeatFieldId = defaultStringValue;

	@JsonProperty
	String repeatDataSetId = defaultStringValue;

	@JsonProperty
	String defaultValue = defaultStringValue;

	@JsonProperty
	String justify = JUSTIFY_L;

	@JsonProperty
	String justifyNm = defaultStringValue;


	@JsonProperty
	String padding = " ";

	@JsonProperty
	String fieldSetId = defaultStringValue;

	//상위필드 한글명
	@JsonProperty
	String parentNmKo = defaultStringValue;

	//상위필드 영문명
	@JsonProperty
	String parentNmEn = defaultStringValue;

	//필수 여부( Y|N )
	@JsonProperty
	String requiredYn = "Y";

	//스케일
	@JsonProperty
	int scale = 0;

	//한글여부 ( Y|N )
	@JsonProperty
	String koreanYn = "N";

	//메타시스템검색 여부 ( Y|N )
	@JsonProperty
	String metaYn = "Y";

	//소수점포함여부  ( Y|N )
	@JsonProperty
	String decimalPointYn = "Y";

	//마스킹여부  ( Y|N )
	@JsonProperty
	String maskingYn = "N";

	//마스킹패턴코드
	@JsonProperty
	String maskingPatternCd = defaultStringValue;

	//비고
	@JsonProperty
	String comments = defaultStringValue;


	@JsonProperty
	String refDataSetId = defaultStringValue;

	@JsonProperty
	String refDataFieldId = defaultStringValue;


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

    //metaCheck 결과등...
	@JsonProperty
    boolean valid = false;

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getDataSetId() {
		return dataSetId;
	}

	public void setDataSetId(String dataSetId) {
		this.dataSetId = dataSetId;
	}

	public String getDataFieldId() {
		return dataFieldId;
	}

	public void setDataFieldId(String dataFieldId) {
		this.dataFieldId = dataFieldId;
	}

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
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

	public String getCd() {
		return cd;
	}

	public void setCd(String cd) {
		this.cd = cd;
	}

	public void setName2(String name2) {
		this.name2 = name2;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
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

	public String getIsLengthField() {
		return isLengthField;
	}

	public void setIsLengthField(String isLengthField) {
		this.isLengthField = isLengthField;
	}

	public int getRepeatCount() {
		return repeatCount;
	}

	public void setRepeatCount(int repeatCount) {
		this.repeatCount = repeatCount;
	}

	public String getHasRepeatCountField() {
		return hasRepeatCountField;
	}

	public void setHasRepeatCountField(String hasRepeatCountField) {
		this.hasRepeatCountField = hasRepeatCountField;
	}

	public String getRepeatFieldId() {
		return repeatFieldId;
	}

	public void setRepeatFieldId(String repeatFieldId) {
		this.repeatFieldId = repeatFieldId;
	}

	public String getRepeatDataSetId() {
		return repeatDataSetId;
	}

	public void setRepeatDataSetId(String repeatDataSetId) {
		this.repeatDataSetId = repeatDataSetId;
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



	public String getTypeNm() {
		return typeNm;
	}

	public void setTypeNm(String typeNm) {
		this.typeNm = typeNm;
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



	public String getFieldSetId() {
		return fieldSetId;
	}

	public void setFieldSetId(String fieldSetId) {
		this.fieldSetId = fieldSetId;
	}



	/**
	 * @return the refDataSetId
	 */
	public String getRefDataSetId() {
		return refDataSetId;
	}

	/**
	 * @param refDataSetId the refDataSetId to set
	 */
	public void setRefDataSetId(String refDataSetId) {
		this.refDataSetId = refDataSetId;
	}

	/**
	 * @return the refDataFieldId
	 */
	public String getRefDataFieldId() {
		return refDataFieldId;
	}

	/**
	 * @param refDataFieldId the refDataFieldId to set
	 */
	public void setRefDataFieldId(String refDataFieldId) {
		this.refDataFieldId = refDataFieldId;
	}





	/**
	 * @return the parentNmKo
	 */
	public String getParentNmKo() {
		return parentNmKo;
	}

	/**
	 * @param parentNmKo the parentNmKo to set
	 */
	public void setParentNmKo(String parentNmKo) {
		this.parentNmKo = parentNmKo;
	}

	/**
	 * @return the parentNmEn
	 */
	public String getParentNmEn() {
		return parentNmEn;
	}

	/**
	 * @param parentNmEn the parentNmEn to set
	 */
	public void setParentNmEn(String parentNmEn) {
		this.parentNmEn = parentNmEn;
	}

	/**
	 * @return the requiredYn
	 */
	public String getRequiredYn() {
		return requiredYn;
	}

	/**
	 * @param requiredYn the requiredYn to set
	 */
	public void setRequiredYn(String requiredYn) {
		this.requiredYn = requiredYn;
	}

	/**
	 * @return the scale
	 */
	public int getScale() {
		return scale;
	}

	/**
	 * @param scale the scale to set
	 */
	public void setScale(int scale) {
		this.scale = scale;
	}

	/**
	 * @return the koreanYn
	 */
	public String getKoreanYn() {
		return koreanYn;
	}

	/**
	 * @param koreanYn the koreanYn to set
	 */
	public void setKoreanYn(String koreanYn) {
		this.koreanYn = koreanYn;
	}

	/**
	 * @return the metaYn
	 */
	public String getMetaYn() {
		return metaYn;
	}

	/**
	 * @param metaYn the metaYn to set
	 */
	public void setMetaYn(String metaYn) {
		this.metaYn = metaYn;
	}

	/**
	 * @return the decimalPointYn
	 */
	public String getDecimalPointYn() {
		return decimalPointYn;
	}

	/**
	 * @param decimalPointYn the decimalPointYn to set
	 */
	public void setDecimalPointYn(String decimalPointYn) {
		this.decimalPointYn = decimalPointYn;
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

    public boolean isValid() {
		return valid;
	}

	public void setValid(boolean isValid) {
		this.valid = isValid;
	}

	public String getMaskingYn() {
		return maskingYn;
	}

	public void setMaskingYn(String maskingYn) {
		this.maskingYn = maskingYn;
	}

	public String getMaskingPatternCd() {
		return maskingPatternCd;
	}

	public void setMaskingPatternCd(String maskingPatternCd) {
		this.maskingPatternCd = maskingPatternCd;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	@Override
    public boolean equals(Object obj) {
    	DataField compared = (DataField)obj;

    	return (
    		name1.equals(compared.getName1()) &&
    		name2.equals(compared.getName2()) &&
    		type.equals(compared.getType()) &&
    		offset == compared.getOffset() &&
    		length == compared.getLength() &&
    		encryptType.equals(compared.getEncryptType()) &&
    		isLengthField.equals(compared.getIsLengthField()) &&
    		repeatCount == compared.getRepeatCount() &&
    		hasRepeatCountField.equals(compared.getHasRepeatCountField()) &&
    		repeatFieldId.equals(compared.getRepeatFieldId()) &&
    		repeatDataSetId.equals(compared.getRepeatDataSetId()) &&
    		defaultValue.equals(compared.getDefaultValue()) &&
    		justify.equals(compared.getJustify()) &&
    		padding.equals(compared.getPadding()) &&
    		delYn.equals(compared.getDelYn())
    	);
    }


}
