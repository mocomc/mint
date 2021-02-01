/**
 * Copyright 2020 Mocomsys Inc.  All Rights Reserved.
 */
package pep.per.mint.endpoint;


import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import org.springframework.context.annotation.Configuration;

import pep.per.mint.common.data.basic.CommonCode;
import pep.per.mint.common.util.Util;
import pep.per.mint.database.service.co.CommonService;

/**
 * @author whoana
 * @since Sep 16, 2020
 */
@Configuration
//@ComponentScan({
//	"pep.per.mint.endpoint",
//	"pep.per.mint.endpoint.tester",
//	"pep.per.mint.endpoint.service.deploy"
//})
public class EndpointConfig {
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired 
	CommonService commonService;
	
	@Bean
	public Variables getVariables() {
		Variables var = new Variables();
		
		try {
			List<CommonCode> commonCodes = commonService.getCommonCodeList("AN", "09");
			if(!Util.isEmpty(commonCodes)) {
				for (CommonCode commonCode : commonCodes) var.userTypeMap.put(commonCode.getCd(), commonCode);
			}
		} catch (Exception e) {
			logger.error("", e);
		}
		
		//string
		try {
			List<String> types = commonService.getEnvironmentalValueList(Variables.typeMapPackage, Variables.typeMapKeyString);
			if(!Util.isEmpty(types)) {
				for (String value : types) var.xsdTypeMap.put(value, Variables.xsdTypeString);
			}else{
				var.xsdTypeMap.put(Variables.FIELD_TYPE_STRING, Variables.xsdTypeString);
			}
		} catch (Exception e) {
			logger.error("", e);
		}
		//byte
		try {
			List<String> types = commonService.getEnvironmentalValueList(Variables.typeMapPackage, Variables.typeMapKeyByte);
			if(!Util.isEmpty(types)) {
				for (String value : types) var.xsdTypeMap.put(value, Variables.xsdTypeByte);
			}else {
				
			}
		} catch (Exception e) {
			logger.error("", e);
		}
		//deciaml
		try {
			List<String> types = commonService.getEnvironmentalValueList(Variables.typeMapPackage, Variables.typeMapKeyDecimal);
			if(!Util.isEmpty(types)) {
				for (String value : types) var.xsdTypeMap.put(value, Variables.xsdTypeDecimal);
			}else {
				var.xsdTypeMap.put(Variables.FIELD_TYPE_NUMBER, Variables.xsdTypeDecimal);
			}
		} catch (Exception e) {
			logger.error("", e);
		}
		//int
		try {
			List<String> types = commonService.getEnvironmentalValueList(Variables.typeMapPackage, Variables.typeMapKeyInt);
			if(!Util.isEmpty(types)) {
				for (String value : types) var.xsdTypeMap.put(value, Variables.xsdTypeInt);
			}else {
				
			}
		} catch (Exception e) {
			logger.error("", e);
		}
		//integer
		try {
			List<String> types = commonService.getEnvironmentalValueList(Variables.typeMapPackage, Variables.typeMapKeyInteger);
			if(!Util.isEmpty(types)) {
				for (String value : types) var.xsdTypeMap.put(value, Variables.xsdTypeInteger);
			}else {
				
			}
		} catch (Exception e) {
			logger.error("", e);
		}
		//long
		try {
			List<String> types = commonService.getEnvironmentalValueList(Variables.typeMapPackage, Variables.typeMapKeyLong);
			if(!Util.isEmpty(types)) {
				for (String value : types) var.xsdTypeMap.put(value, Variables.xsdTypeLong);
			}else {
				
			}
		} catch (Exception e) {
			logger.error("", e);
		}
		//negativeInteger
		try {
			List<String> types = commonService.getEnvironmentalValueList(Variables.typeMapPackage, Variables.typeMapKeyNegativeInteger);
			if(!Util.isEmpty(types)) {
				for (String value : types) var.xsdTypeMap.put(value, Variables.xsdTypeNegativeInteger);
			}else {
				
			}
		} catch (Exception e) {
			logger.error("", e);
		}
		//nonNegativeInteger
		try {
			List<String> types = commonService.getEnvironmentalValueList(Variables.typeMapPackage, Variables.typeMapKeyNonNegativeInteger);
			if(!Util.isEmpty(types)) {
				for (String value : types) var.xsdTypeMap.put(value, Variables.xsdTypeNonNegativeInteger);
			}else {
				
			}
		} catch (Exception e) {
			logger.error("", e);
		}
		//positiveInteger
		try {
			List<String> types = commonService.getEnvironmentalValueList(Variables.typeMapPackage, Variables.typeMapKeyPositiveInteger);
			if(!Util.isEmpty(types)) {
				for (String value : types)var.xsdTypeMap.put(value, Variables.xsdTypePositiveInteger);
			}else {
				
			}
		} catch (Exception e) {
			logger.error("", e);
		}
		//nonPositiveInteger
		try {
			List<String> types = commonService.getEnvironmentalValueList(Variables.typeMapPackage, Variables.typeMapKeyNonPositiveInteger);
			if(!Util.isEmpty(types)) {
				for (String value : types) var.xsdTypeMap.put(value, Variables.xsdTypeNonPositiveInteger);
			}else {
				
			}
		} catch (Exception e) {
			logger.error("", e);
		}
		
		//short
		try {
			List<String> types = commonService.getEnvironmentalValueList(Variables.typeMapPackage, Variables.typeMapKeyShort);
			if(!Util.isEmpty(types)) {
				for (String value : types)var.xsdTypeMap.put(value, Variables.xsdTypeShort);
			}else {
				
			}
		} catch (Exception e) {
			logger.error("", e);
		}
		//unsignedLong	
		try {
			List<String> types = commonService.getEnvironmentalValueList(Variables.typeMapPackage, Variables.typeMapKeyUnsignedLong);
			if(!Util.isEmpty(types)) {
				for (String value : types)var.xsdTypeMap.put(value, Variables.xsdTypeUnsignedLong);
			}else {
				
			}
		} catch (Exception e) {
			logger.error("", e);
		}
		//unsignedInt	
		try {
			List<String> types = commonService.getEnvironmentalValueList(Variables.typeMapPackage, Variables.typeMapKeyUnsignedInt);
			if(!Util.isEmpty(types)) {
				for (String value : types) var.xsdTypeMap.put(value, Variables.xsdTypeUnsignedInt);
			}else {
				
			}
		} catch (Exception e) {
			logger.error("", e);
		}
		//unsignedShort	
		try {
			List<String> types = commonService.getEnvironmentalValueList(Variables.typeMapPackage, Variables.typeMapKeyUnsignedShort);
			if(!Util.isEmpty(types)) {
				for (String value : types)var.xsdTypeMap.put(value, Variables.xsdTypeUnsignedShort);
			}else {
				
			}
		} catch (Exception e) {
			logger.error("", e);
		}
		
		//unsignedByte
		try {
			List<String> types = commonService.getEnvironmentalValueList(Variables.typeMapPackage, Variables.typeMapKeyUnsignedByte);
			if(!Util.isEmpty(types)) {
				for (String value : types) var.xsdTypeMap.put(value, Variables.xsdTypeUnsignedByte);
			}else {
				
			}
		} catch (Exception e) {
			logger.error("", e);
		}
		
		//Boolean
		try {
			List<String> types = commonService.getEnvironmentalValueList(Variables.typeMapPackage, Variables.typeMapKeyBoolean);
			if(!Util.isEmpty(types)) {
				for (String value : types)var.xsdTypeMap.put(value, Variables.xsdTypeBoolean);
			}else {
				var.xsdTypeMap.put(Variables.FIELD_TYPE_BOOLEAN, Variables.xsdTypeBoolean);
			}
		} catch (Exception e) {
			logger.error("", e);
		}
		
		//base64Binary
		try {
			List<String> types = commonService.getEnvironmentalValueList(Variables.typeMapPackage, Variables.typeMapKeyBase64Binary);
			if(!Util.isEmpty(types)) {
				for (String value : types)var.xsdTypeMap.put(value, Variables.xsdTypeBase64Binary);
			}else {
				var.xsdTypeMap.put(Variables.FIELD_TYPE_BINARY, Variables.xsdTypeBase64Binary);
			}
		} catch (Exception e) {
			logger.error("", e);
		}
		
		
		//HexBinary
		try {
			List<String> types = commonService.getEnvironmentalValueList(Variables.typeMapPackage, Variables.typeMapKeyHexBinary);
			if(!Util.isEmpty(types)) {
				for (String value : types) var.xsdTypeMap.put(value, Variables.xsdTypeHexBinary);
			}else {
				
			}
		} catch (Exception e) {
			logger.error("", e);
		}
		
		//complex
		try {
			List<String> types = commonService.getEnvironmentalValueList(Variables.typeMapPackage, Variables.typeMapKeyComplex);
			if(!Util.isEmpty(types)) {
				for (String value : types)var.xsdTypeMap.put(value, Variables.xsdTypeComplex);
			}else {
				var.xsdTypeMap.put(Variables.FIELD_TYPE_COMPLEX, Variables.xsdTypeComplex);
				var.xsdTypeMap.put(Variables.FIELD_USER_TYPE_GS, Variables.xsdTypeComplex);
				var.xsdTypeMap.put(Variables.FIELD_USER_TYPE_GM, Variables.xsdTypeComplex);
				var.xsdTypeMap.put(Variables.FIELD_USER_TYPE_GF, Variables.xsdTypeComplex);
			}
		} catch (Exception e) {
			logger.error("", e);
		}
		
		//date
		try {
			List<String> types = commonService.getEnvironmentalValueList(Variables.typeMapPackage, Variables.typeMapKeyDate);
			if(!Util.isEmpty(types)) {
				for (String value : types) var.xsdTypeMap.put(value, Variables.xsdTypeDate);
			}else {
				
			}
		} catch (Exception e) {
			logger.error("", e);
		}
		
		//time
		try {
			List<String> types = commonService.getEnvironmentalValueList(Variables.typeMapPackage, Variables.typeMapKeyTime);
			if(!Util.isEmpty(types)) {
				for (String value : types)var.xsdTypeMap.put(value, Variables.xsdTypeTime);
			}else {
				
			}
		} catch (Exception e) {
			logger.error("", e);
		}
		
		//datetime
		try {
			List<String> types = commonService.getEnvironmentalValueList(Variables.typeMapPackage, Variables.typeMapKeyDateTime);
			if(!Util.isEmpty(types)) {
				for (String value : types)var.xsdTypeMap.put(value, Variables.xsdTypeDateTime);
			}else {
				
			}
		} catch (Exception e) {
			logger.error("", e);
		}
		
		
		return var;
	}
	
}
