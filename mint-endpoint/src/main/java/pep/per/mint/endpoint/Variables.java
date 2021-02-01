/**
 * Copyright 2020 Mocomsys Inc.  All Rights Reserved.
 */
package pep.per.mint.endpoint;

import java.util.HashMap;
import java.util.Map;

import pep.per.mint.common.data.basic.CommonCode;

/**
 * @author whoana
 * @since 2020. 12. 8.
 */
public class Variables {

	public final Map<String, String> xsdTypeMap = new HashMap<String, String>();
	public final Map<String, CommonCode> userTypeMap = new HashMap<String, CommonCode>();
	public static final String typeMapPackage   			= "layout";

	public static final String typeMapKeyString 			= "xsd.type.mapping.string";
	public static final String typeMapKeyByte 				= "xsd.type.mapping.byte";
	public static final String typeMapKeyDecimal 			= "xsd.type.mapping.decimal";
	public static final String typeMapKeyInt 				= "xsd.type.mapping.int";
	public static final String typeMapKeyInteger			= "xsd.type.mapping.integer";
	public static final String typeMapKeyLong 				= "xsd.type.mapping.long";
	public static final String typeMapKeyFloat 				= "xsd.type.mapping.float";
	public static final String typeMapKeyDouble 			= "xsd.type.mapping.double";
	public static final String typeMapKeyNegativeInteger 	= "xsd.type.mapping.negativeInteger";
	public static final String typeMapKeyNonNegativeInteger = "xsd.type.mapping.nonNegativeInteger";
	public static final String typeMapKeyPositiveInteger 	= "xsd.type.mapping.positiveInteger";
	public static final String typeMapKeyNonPositiveInteger = "xsd.type.mapping.nonPositiveInteger";
	public static final String typeMapKeyShort				= "xsd.type.mapping.short";
	public static final String typeMapKeyUnsignedLong 		= "xsd.type.mapping.unsignedLong";
	public static final String typeMapKeyUnsignedInt 		= "xsd.type.mapping.unsignedInt";
	public static final String typeMapKeyUnsignedShort 		= "xsd.type.mapping.unsignedShort";
	public static final String typeMapKeyUnsignedByte 		= "xsd.type.mapping.unsignedByte";
	public static final String typeMapKeyBoolean 			= "xsd.type.mapping.boolean";
	public static final String typeMapKeyBase64Binary		= "xsd.type.mapping.base64Binary";
	public static final String typeMapKeyHexBinary 			= "xsd.type.mapping.hexBinary";
	public static final String typeMapKeyComplex			= "xsd.type.mapping.complex";
	public static final String typeMapKeyDate 				= "xsd.type.mapping.date";
	public static final String typeMapKeyTime 				= "xsd.type.mapping.time";
	public static final String typeMapKeyDateTime 			= "xsd.type.mapping.dateTime";

	public static final String xsdTypeString 				= "xs:string";
	public static final String xsdTypeByte 					= "xs:byte";
	public static final String xsdTypeDecimal 				= "xs:decimal";
	public static final String xsdTypeInt 					= "xs:int";
	public static final String xsdTypeInteger				= "xs:integer";
	public static final String xsdTypeLong 					= "xs:long";
	public static final String xsdTypeFloat 				= "xs:float";
	public static final String xsdTypeDouble 				= "xs:double";
	public static final String xsdTypeNegativeInteger 		= "xs:negativeInteger";
	public static final String xsdTypeNonNegativeInteger 	= "xs:nonNegativeInteger";
	public static final String xsdTypePositiveInteger 		= "xs:positiveInteger";
	public static final String xsdTypeNonPositiveInteger 	= "xs:nonPositiveInteger";
	public static final String xsdTypeShort					= "xs:short";
	public static final String xsdTypeUnsignedLong 			= "xs:unsignedLong";
	public static final String xsdTypeUnsignedInt 			= "xs:unsignedInt";
	public static final String xsdTypeUnsignedShort 		= "xs:unsignedShort";
	public static final String xsdTypeUnsignedByte 			= "xs:unsignedByte";
	public static final String xsdTypeBoolean 				= "xs:boolean";
	public static final String xsdTypeBase64Binary			= "xs:base64Binary";
	public static final String xsdTypeHexBinary 			= "xs:hexBinary";
	public static final String xsdTypeComplex				= "xs:complex";
	public static final String xsdTypeDate 					= "xs:date";
	public static final String xsdTypeTime 					= "xs:time";
	public static final String xsdTypeDateTime 				= "xs:dateTime";


	public final static String FIELD_TYPE_STRING  = "0";
	public final static String FIELD_TYPE_NUMBER  = "1";
	public final static String FIELD_TYPE_BOOLEAN = "2";
	public final static String FIELD_TYPE_BINARY  = "3";
	public final static String FIELD_TYPE_COMPLEX = "4";

	public final static String FIELD_USER_TYPE_GS = "5";
	public final static String FIELD_USER_TYPE_GM = "6";
	public final static String FIELD_USER_TYPE_GF = "7";



	public static final String kId 				= "id";
	public static final String kName 			= "name";
	public static final String kValue			= "value";
	public static final String kStage 			= "stage";
	public static final String kCreateDate 		= "createDate";
	public static final String kVersion 		= "version";
	public static final String kDescription 	= "description";
	public static final String kInterface 		= "interface";

	public static final String kBusiness 		= "business";

	public static final String kChannel 		= "channel";

	public static final String kDataPrDir 		= "dataPrDir";
	public static final String kDataPrMethod 	= "dataPrMethod";
	public static final String kAppPrMethod 	= "appPrMethod";
	public static final String kDataSeq   		= "dataSeq";
	public static final String kDataFrequency   = "dataFrequency";
	public static final String kSizePerTran   	= "sizePerTran";
	public static final String kCntPerFrequency = "cntPerFrequency";
	public static final String kCntPerDay 		= "cntPerDay";
	public static final String kQttPerDay 		= "qttPerDay";

	public static final String kDeployUserId 	= "deployUserId";
	public static final String kRegDate 		= "regDate";
	public static final String kRegUserId 		= "regUserId";
	public static final String kModDate 		= "modDate";
	public static final String kModUserId 		= "modUserId";
	public static final String kApps 			= "apps";
	public static final String kLayouts 		= "layouts";
	public static final String kMappings 		= "mappings";
	public static final String kApp 			= "app";
	public static final String kType 			= "type";
	public static final String kCd				= "cd";
	public static final String kSeq 			= "seq";
	public static final String kSystem 			= "system";
	public static final String kServer 			= "server";
	public static final String kProperties		= "properties";
	public static final String kProperty 		= "property";

	public static final String kLayout			= "layout";
	public static final String kRef 			= "ref";
	public static final String kMapping 		= "mapping";

	public static final String kLength	 		= "length";
	public static final String kPadding 		= "padding";
	public static final String kJustify 		= "justify";
	public static final String kScale 			= "scale";
	public static final String kRequired 		= "required";
	public static final String kIsKorean 		= "isKorean";
	public static final String kMeta 			= "meta";
	public static final String kDecimalPoint 	= "decimalPoint";
	public static final String kReatFieldName 	= "reatFieldName";

	public static final String kMaxLength  	 	= "maxLength";
	public static final String kTotalDigits	 	= "totalDigits";
	public static final String kMaxInclusive    = "maxInclusive";
	public static final String kFormat 			= "format";

	public static final String kRecordDelimiter = "recordDelimiter";
	public static final String kFieldDelimiter 	= "fieldDelimiter";

	public static final String kMaskingYn       = "maskingYn";
	public static final String kMaskingPatternCd= "maskingPatternCd";
	public static final String kComments		= "comments";
	public static final String kIOType 			= "ioType";

	public static final String vXml 		= "xml";
	public static final String vJson 		= "json";
	public static final String vDelimiter 	= "delimiter";
	public static final String vFixed 		= "fixed";

	public static final String vBusinessLabel 		= "업무";
	public static final String vChannelLabel  		= "연계방식";
	public static final String vDataPrDirLabel		= "DATA처리방향";
	public static final String vDataPrMethodLabel	= "DATA처리방식";
	public static final String vAppPrMethodLabel	= "애플리케이션처리방식";
	public static final String vDataSeqLabel		= "DATA순차보장여부";
	public static final String vDataFrequencyLabel  = "발생주기";
	public static final String vSizePerTranLabel 	= "건별사이즈";
	public static final String vCntPerFrequencyLabel= "주기별건수";
	public static final String vCntPerDayLabel		= "일일발생횟수";
	public static final String vQttPerDayLabel		= "일일총전송량";

	public static final String SPACE   		= "space";
	public static final String JUSTIFY_LEFT = "left";
	public static final String JUSTIFY_RIGHT= "right";
	public static final String TYPE_POSTFIX = "_TYPE";

	public final static String kUserDataType = "userDataType";
	public final static String vGS = "gs";
	public final static String vGM = "gm";
	public final static String vGF = "gf";


	public static final int BASE64 = 0;
	public static final int HEX = 1;


	/**
	 * 0:xml
	 * 1:json
	 * 2:delimiter
	 * 3:fixedlength
	 */
	public final static String FORMAT_XML 			= "0";
	public final static String FORMAT_JSON 		= "1";
	public final static String FORMAT_DELIMITER 	= "2";
	public final static String FORMAT_FIXEDLENGTH	= "3";

	public final static String DEFAULT_RECORD_DELIMITER  = System.lineSeparator();
	public final static String DEFAULT_FIELD_DELIMITER  = ",";
	public final static String Y = "Y";
	public final static String yes = "yes";
	public final static String no = "no";


}
