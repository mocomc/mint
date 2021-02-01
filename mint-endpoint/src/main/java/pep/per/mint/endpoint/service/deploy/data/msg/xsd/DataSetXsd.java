
/**
 * Copyright 2020 Mocomsys Inc.  All Rights Reserved.
 */
package pep.per.mint.endpoint.service.deploy.data.msg.xsd;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;



import pep.per.mint.common.data.basic.dataset.DataField;
import pep.per.mint.common.data.basic.dataset.DataSet;
import pep.per.mint.common.util.Util;
import pep.per.mint.endpoint.Variables;
 

/**
 * @author whoana
 * @since Aug 5, 2020
 */
public class DataSetXsd {
	 
	
	Map<String, ComplexType> complexTypeMapByFid = new LinkedHashMap<String, ComplexType>();
	Map<String, ComplexType> complexTypeMapByName = new LinkedHashMap<String, ComplexType>();
	
	DataSetElement dataSetRootElement;
	
	boolean useComplexTypeRef = false;

	int binaryOption = Variables.BASE64;
	
	Map<String, String> fieldPaths = new HashMap<String, String>();
	
	/**
	 * @return the useComplexTypeRef
	 */
	public boolean isUseComplexTypeRef() {
		return useComplexTypeRef;
	}


	/**
	 * @param useComplexTypeRef the useComplexTypeRef to set
	 */
	public void setUseComplexTypeRef(boolean useComplexTypeRef) {
		this.useComplexTypeRef = useComplexTypeRef;
	}


	 
	Variables variables;
	
	/**
	 * @param dataSet
	 * @throws Exception 
	 */
	public DataSetXsd(DataSet dataSet, Variables variables) throws Exception {
		this.variables = variables;
		
		{
			String dataSetId = dataSet.getDataSetId();
			String desc		 = dataSet.getName1();
			String name		 = dataSet.getCd();//dataSet.getName2(); 전문레이아웃 element name 값을 코드(CD) 값으로 변경 요청, 2020.11.12, dhkim
			String cd		 = dataSet.getCd();		
			/*
				0:xml
				1:json
				2:delimiter
				3:fixedlength
				4:derecord
				5:etc
			 */
			String dataFormat= dataSet.getDataFormat();
			
			dataSetRootElement = new DataSetElement();
			dataSetRootElement.setName(name);
			dataSetRootElement.setTypeKind(DataSetElement.COMPLEX_TYPE);
			Annotation annotation = new  Annotation();
			annotation.setDocumentation(new Documentation(desc));
			AppInfo appInfo = new AppInfo();
			
			if(Variables.FORMAT_XML.equals(dataFormat)) {
				appInfo.addProperty(Variables.kFormat, Variables.vXml);
			}else if(Variables.FORMAT_JSON.equals(dataFormat)) {
				appInfo.addProperty(Variables.kFormat, Variables.vJson);
			}else if(Variables.FORMAT_DELIMITER.equals(dataFormat)) {
				appInfo.addProperty(Variables.kFormat, Variables.vDelimiter);
				appInfo.addProperty(Variables.kRecordDelimiter, Util.isEmpty(dataSet.getRecordDelimiter()) ? Variables.DEFAULT_RECORD_DELIMITER : dataSet.getRecordDelimiter());	
				appInfo.addProperty(Variables.kFieldDelimiter , Util.isEmpty(dataSet.getFieldDelimiter())  ? Variables. DEFAULT_FIELD_DELIMITER : dataSet.getFieldDelimiter());	
			}else if(Variables.FORMAT_FIXEDLENGTH.equals(dataFormat)) {
				appInfo.addProperty(Variables.kFormat, Variables.vFixed);
			}else {
				
			}
			annotation.setAppInfo(appInfo);
			dataSetRootElement.setAnnotation(annotation);
		}
		
		
		List<DataField> fields = dataSet.getDataFieldList();
		//------------------------------------------------
		//pick up complexType 
		//------------------------------------------------
		Map<String, DataField> fieldMap = new HashMap<String, DataField>(); 
		for (DataField dataField : fields) {
			
			fieldMap.put(dataField.getDataFieldId(), dataField);
			
			String type = dataField.getType();
			if(Variables.FIELD_TYPE_COMPLEX.equals(type)|| Variables.FIELD_USER_TYPE_GS.equals(type) || Variables.FIELD_USER_TYPE_GM.equals(type) || Variables.FIELD_USER_TYPE_GF.equals(type)) {
				String fid  = dataField.getDataFieldId();
				String fcd  = dataField.getCd();
				String minOccurs = (dataField.getRepeatCount() == 0 ? 1 : dataField.getRepeatCount()) + "";	
				String maxOccurs = minOccurs;
				String name = fcd + Variables.TYPE_POSTFIX;
				ComplexType complexType = new ComplexType(name);
				
				Sequence sequence = new Sequence();
				sequence.setMaxOccurs(maxOccurs);
				sequence.setMinOccurs(minOccurs);
				complexType.setSequence(sequence);

				complexTypeMapByFid.put(fid, complexType);
				complexTypeMapByName.put(name, complexType);
			} 
		}
		
		//------------------------------------------------
		//build up complexType 
		//------------------------------------------------
		for (DataField dataField : fields) {
			String fcd  = dataField.getCd();
			String pid  = dataField.getParentId();
			String type = dataField.getType();
			String fname = dataField.getCd();
			 
			String minOccurs = (dataField.getRepeatCount() == 0 ? 1 : dataField.getRepeatCount()) + "";
			
			if(!Util.isEmpty(pid)) {
				ComplexType complexType = complexTypeMapByFid.get(pid);
				
				DataSetElement element = new DataSetElement();
				element.setName(fname);
				if(!Util.isEmpty(dataField.getDefaultValue())) element.setDefaultValue(dataField.getDefaultValue());
				
				Annotation annotation = new  Annotation();
				element.setAnnotation(annotation);
				annotation.setDocumentation(new Documentation(dataField.getName1()));
				AppInfo appInfo = new AppInfo();
				annotation.setAppInfo(appInfo);				

				if(Variables.FIELD_TYPE_COMPLEX.equals(type)) {
					element.setType(fcd + Variables.TYPE_POSTFIX);	
					element.setTypeKind(DataSetElement.COMPLEX_TYPE);
					
					if(Variables.Y.equalsIgnoreCase(dataField.getHasRepeatCountField())){
						String repeatFieldId = dataField.getRepeatFieldId();
						DataField repeatField = fieldMap.get(repeatFieldId);
						String repeatFieldCd = repeatField.getCd();
						appInfo.addProperty(Variables.kReatFieldName, repeatFieldCd);
					}
					appInfo.addProperty(Variables.kComments, Util.isEmpty(dataField.getComments()) ? "" : dataField.getComments());
					
				}else if(Variables.FIELD_USER_TYPE_GS.equals(type) || Variables.FIELD_USER_TYPE_GM.equals(type) || Variables.FIELD_USER_TYPE_GF.equals(type)) {
					element.setType(fcd + Variables.TYPE_POSTFIX);	
					element.setTypeKind(DataSetElement.COMPLEX_TYPE);
					
					if(Variables.Y.equalsIgnoreCase(dataField.getHasRepeatCountField())){
						String repeatFieldId = dataField.getRepeatFieldId();
						DataField repeatField = fieldMap.get(repeatFieldId);
						String repeatFieldCd = repeatField.getCd();
						appInfo.addProperty(Variables.kReatFieldName, repeatFieldCd);
					}
					
					String userDataType;
					if(Variables.FIELD_USER_TYPE_GS.equals(type)) {
						userDataType = Variables.vGS;
					}else if(Variables.FIELD_USER_TYPE_GM.equals(type)) {
						userDataType = Variables.vGM;
					}else{
						userDataType = Variables.vGF;
					}
					appInfo.addProperty(Variables.kUserDataType, userDataType);
					appInfo.addProperty(Variables.kComments, Util.isEmpty(dataField.getComments()) ? "" : dataField.getComments());
				}else { 
				
					String dataFormat= dataSet.getDataFormat();	
					element.setSimpleType(createSimpleType(type, dataField));
					element.setTypeKind(DataSetElement.SIMPLE_TYPE);
					
					
					//---------------------------------------------------------------------
					//공통 필드 프로퍼티 설정 
					//---------------------------------------------------------------------
					{
						appInfo.addProperty(Variables.kUserDataType, 	variables.userTypeMap.get(type) == null ? "" : variables.userTypeMap.get(type).getNm());
						appInfo.addProperty(Variables.kLength, 			dataField.getLength() == 0 ? "" : dataField.getLength() + "");
						appInfo.addProperty(Variables.kJustify, 		(!Util.isEmpty(dataField.getJustify())    && dataField.getJustify().equals("L"))    ? Variables.JUSTIFY_LEFT : Variables.JUSTIFY_RIGHT);
						appInfo.addProperty(Variables.kPadding, 		(!Util.isEmpty(dataField.getPadding())    && dataField.getPadding().equals(" "))    ? Variables.SPACE : dataField.getPadding());
						appInfo.addProperty(Variables.kRequired,		(!Util.isEmpty(dataField.getRequiredYn()) && dataField.getRequiredYn().equals("N")) ? Variables.no : Variables.yes); 
						appInfo.addProperty(Variables.kMeta,    		(!Util.isEmpty(dataField.getMetaYn())     && dataField.getMetaYn().equals("N"))     ? Variables.no : Variables.yes);
						appInfo.addProperty(Variables.kScale, 			dataField.getScale() + "");
						appInfo.addProperty(Variables.kDecimalPoint, 	(!Util.isEmpty(dataField.getDecimalPointYn()) && dataField.getDecimalPointYn().equals("N")) ? Variables.no : Variables.yes);
						appInfo.addProperty(Variables.kIsKorean, 		(!Util.isEmpty(dataField.getKoreanYn()) && dataField.getKoreanYn().equals("N")) ? Variables.no : Variables.yes);						
						appInfo.addProperty(Variables.kMaskingYn, 		(!Util.isEmpty(dataField.getMaskingYn()) && dataField.getMaskingYn().equals("Y")) ? Variables.yes : Variables.no);
						appInfo.addProperty(Variables.kMaskingPatternCd,Util.isEmpty(dataField.getMaskingPatternCd()) ? "" : dataField.getMaskingPatternCd());
						appInfo.addProperty(Variables.kComments, 		Util.isEmpty(dataField.getComments()) ? "" : dataField.getComments());
						
						if(Variables.Y.equalsIgnoreCase(dataField.getHasRepeatCountField())){
							String repeatFieldId = dataField.getRepeatFieldId();
							DataField repeatField = fieldMap.get(repeatFieldId);
							String repeatFieldCd = repeatField.getCd();
							appInfo.addProperty(Variables.kReatFieldName, repeatFieldCd);
						}
					}
					
					if(Variables.FORMAT_XML.equals(dataFormat)) {
						//---------------------------------------------------------------------
						//xml 포멧 로직  
						//---------------------------------------------------------------------
					}else if(Variables.FORMAT_JSON.equals(dataFormat)) {
						//---------------------------------------------------------------------
						//json 포멧 로직  
						//---------------------------------------------------------------------
					}else if(Variables.FORMAT_DELIMITER.equals(dataFormat)) {
						//---------------------------------------------------------------------
						//delimiter 포멧 로직  
						//---------------------------------------------------------------------
											
					}else if(Variables.FORMAT_FIXEDLENGTH.equals(dataFormat)) {
						//---------------------------------------------------------------------
						//fixed 포멧 로직  
						//--------------------------------------------------------------------- 
					}else {		
						throw new Exception("UnsupportedFormat(dataFormat:" + dataFormat + ")");
					}
				}
							
				element.setMinOccurs(minOccurs);
				element.setMaxOccurs(minOccurs);
				complexType.addElement(element);
			} 
		}
		
		//------------------------------------------------
		//build up element 
		//------------------------------------------------
		{
			ComplexType complexType = new ComplexType();
			dataSetRootElement.setComplexType(complexType);
			Sequence sequence = new Sequence();
			complexType.setSequence(sequence);
			
			 
			for (DataField dataField : fields) {
				String pid  = dataField.getParentId();
				if(Util.isEmpty(pid)) {
					String type = dataField.getType();
					String fcd  = dataField.getCd();
					
					String minOccurs = (dataField.getRepeatCount() == 0 ? 1 : dataField.getRepeatCount()) + "";
					String maxOccurs = minOccurs;
					DataSetElement element = new DataSetElement();
					element.setName(fcd);
					element.setMaxOccurs(maxOccurs);
					element.setMinOccurs(minOccurs);							
					
					if(!Util.isEmpty(dataField.getDefaultValue())) element.setDefaultValue(dataField.getDefaultValue());
					
					Annotation annotation = new  Annotation();
					element.setAnnotation(annotation);
					annotation.setDocumentation(new Documentation(dataField.getName1()));
					AppInfo appInfo = new AppInfo();
					annotation.setAppInfo(appInfo);
					
					if(Variables.FIELD_TYPE_COMPLEX.equals(type)) {
						element.setType(fcd + Variables.TYPE_POSTFIX);	
						element.setTypeKind(DataSetElement.COMPLEX_TYPE);
						
						if(Variables.Y.equalsIgnoreCase(dataField.getHasRepeatCountField())){
							String repeatFieldId = dataField.getRepeatFieldId();
							DataField repeatField = fieldMap.get(repeatFieldId);
							String repeatFieldCd = repeatField.getCd();
							appInfo.addProperty(Variables.kReatFieldName , repeatFieldCd);
						}
						
						appInfo.addProperty(Variables.kComments, Util.isEmpty(dataField.getComments()) ? "" : dataField.getComments());
						
					}else if(Variables.FIELD_USER_TYPE_GS.equals(type) || Variables.FIELD_USER_TYPE_GM.equals(type) || Variables.FIELD_USER_TYPE_GF.equals(type)) {
						element.setType(fcd + Variables.TYPE_POSTFIX);	
						element.setTypeKind(DataSetElement.COMPLEX_TYPE);
						
						if(Variables.Y.equalsIgnoreCase(dataField.getHasRepeatCountField())){
							String repeatFieldId = dataField.getRepeatFieldId();
							DataField repeatField = fieldMap.get(repeatFieldId);
							String repeatFieldCd = repeatField.getCd();
							appInfo.addProperty(Variables.kReatFieldName, repeatFieldCd);
						}
						
						String userDataType;
						if(Variables.FIELD_USER_TYPE_GS.equals(type)) {
							userDataType = Variables.vGS;
						}else if(Variables.FIELD_USER_TYPE_GM.equals(type)) {
							userDataType = Variables.vGM;
						}else{
							userDataType = Variables.vGF;
						}
						appInfo.addProperty(Variables.kUserDataType, userDataType);
						 
						appInfo.addProperty(Variables.kComments, Util.isEmpty(dataField.getComments()) ? "" : dataField.getComments());
						
					} else {
						 
						String dataFormat= dataSet.getDataFormat();	
						element.setSimpleType(createSimpleType(type, dataField));
						element.setTypeKind(DataSetElement.SIMPLE_TYPE);
						
						
						//---------------------------------------------------------------------
						//공통 필드 프로퍼티 설정 
						//---------------------------------------------------------------------
						{
							appInfo.addProperty(Variables.kUserDataType, 	variables.userTypeMap.get(type) == null ? "" : variables.userTypeMap.get(type).getNm());
							appInfo.addProperty(Variables.kLength, 			dataField.getLength() == 0 ? "" : dataField.getLength() + "");
							appInfo.addProperty(Variables.kJustify, 		(!Util.isEmpty(dataField.getJustify())    && dataField.getJustify().equals("L"))    ? Variables.JUSTIFY_LEFT : Variables.JUSTIFY_RIGHT);
							appInfo.addProperty(Variables.kPadding, 		(!Util.isEmpty(dataField.getPadding())    && dataField.getPadding().equals(" "))    ? Variables.SPACE : dataField.getPadding());
							appInfo.addProperty(Variables.kRequired,		(!Util.isEmpty(dataField.getRequiredYn()) && dataField.getRequiredYn().equals("N")) ? Variables.no : Variables.yes); 
							appInfo.addProperty(Variables.kMeta,    		(!Util.isEmpty(dataField.getMetaYn())     && dataField.getMetaYn().equals("N"))     ? Variables.no : Variables.yes);
							appInfo.addProperty(Variables.kScale, 			dataField.getScale() + "");
							appInfo.addProperty(Variables.kDecimalPoint, 	(!Util.isEmpty(dataField.getDecimalPointYn()) && dataField.getDecimalPointYn().equals("N")) ? Variables.no : Variables.yes);
							appInfo.addProperty(Variables.kIsKorean, 		(!Util.isEmpty(dataField.getKoreanYn()) && dataField.getKoreanYn().equals("N")) ? Variables.no : Variables.yes);
							appInfo.addProperty(Variables.kMaskingYn, 		(!Util.isEmpty(dataField.getMaskingYn()) && dataField.getMaskingYn().equals("Y")) ? Variables.yes : Variables.no);
							appInfo.addProperty(Variables.kMaskingPatternCd,Util.isEmpty(dataField.getMaskingPatternCd()) ? "" : dataField.getMaskingPatternCd());
							appInfo.addProperty(Variables.kComments, 		Util.isEmpty(dataField.getComments()) ? "" : dataField.getComments());

							if(Variables.Y.equalsIgnoreCase(dataField.getHasRepeatCountField())){
								String repeatFieldId = dataField.getRepeatFieldId();
								DataField repeatField = fieldMap.get(repeatFieldId);
								String repeatFieldCd = repeatField.getCd();
								appInfo.addProperty(Variables.kReatFieldName, repeatFieldCd);
							}
						}
						
						
						if(Variables.FORMAT_XML.equals(dataFormat)) {
							//---------------------------------------------------------------------
							//xml 포멧 로직  
							//---------------------------------------------------------------------
						}else if(Variables.FORMAT_JSON.equals(dataFormat)) {
							//---------------------------------------------------------------------
							//json 포멧 로직  
							//---------------------------------------------------------------------
						}else if(Variables.FORMAT_DELIMITER.equals(dataFormat)) {
							//---------------------------------------------------------------------
							//delimiter 포멧 로직  
							//---------------------------------------------------------------------
						}else if(Variables.FORMAT_FIXEDLENGTH.equals(dataFormat)) {
							//---------------------------------------------------------------------
							//fixed 포멧 로직  
							//--------------------------------------------------------------------- 
						}else {		
							throw new Exception("UnsupportedFormat(dataFormat:" + dataFormat + ")");
						}
					}
					complexType.addElement(element); 
				}
			}
		}	 

	}
	
	

	/**
	 * @param type
	 * @param dataField
	 * @return
	 * @throws Exception 
	 */
	private SimpleType createSimpleType(String type, DataField dataField) throws Exception {
		SimpleType simpleType = new SimpleType();
		
		String xsdType = variables.xsdTypeMap.get(type);
		if(Util.isEmpty(xsdType)) throw new Exception("NotMappingXsdType(code:" + type + ")");
		
		Restriction restriction = null;
		if(xsdType.equals(Variables.xsdTypeString)) {
			restriction = dataField.getLength() == 0 ? 
					new Restriction(Variables.xsdTypeString) : 
					new Restriction(Variables.xsdTypeString, new RestrictionValue(Variables.kMaxLength, dataField.getLength() + ""));
		}else if(xsdType.equals(Variables.xsdTypeByte)) {
			restriction = dataField.getLength() == 0 ? 
					new Restriction(Variables.xsdTypeByte) : 
					new Restriction(Variables.xsdTypeByte, new RestrictionValue(Variables.kTotalDigits, dataField.getLength() + ""));
		}else if(xsdType.equals(Variables.xsdTypeDecimal)) {
			restriction = dataField.getLength() == 0 ? 
					new Restriction(Variables.xsdTypeDecimal) : 
					new Restriction(Variables.xsdTypeDecimal, new RestrictionValue(Variables.kTotalDigits, dataField.getLength() + ""));
		}else if(xsdType.equals(Variables.xsdTypeInt)) {
			restriction = dataField.getLength() == 0 ? 
					new Restriction(Variables.xsdTypeInt) : 
					new Restriction(Variables.xsdTypeInt, new RestrictionValue(Variables.kTotalDigits, dataField.getLength() + ""));
		}else if(xsdType.equals(Variables.xsdTypeInteger)) {
			restriction = dataField.getLength() == 0 ? 
					new Restriction(Variables.xsdTypeInteger) : 
					new Restriction(Variables.xsdTypeInteger, new RestrictionValue(Variables.kTotalDigits, dataField.getLength() + ""));
		}else if(xsdType.equals(Variables.xsdTypeLong)) {
			restriction = dataField.getLength() == 0 ? 
					new Restriction(Variables.xsdTypeLong) : 
					new Restriction(Variables.xsdTypeLong, new RestrictionValue(Variables.kTotalDigits, dataField.getLength() + ""));
		}else if(xsdType.equals(Variables.xsdTypeFloat)) {
//			float 유형에 대한 길이 정의는 totalDigits 표현이 불가하며  maxInclusive 로 표현할 경우 최고값으로 제한하는 것으로 화면의 길이 입력 취지와 맞지 않으므로 float 유형 데이터 입력란을 상세하게 표현하면 
//			이후 어떻게 할지 고민해 보자.
//			restriction = dataField.getLength() == 0 ? 
//					new Restriction(Variables.xsdTypeFloat) : 
//					new Restriction(Variables.xsdTypeFloat, new RestrictionValue(Variables.kMaxInclusive, dataField.getLength() + ""));			
			restriction = new Restriction(Variables.xsdTypeFloat);	
		}else if(xsdType.equals(Variables.xsdTypeDouble)) {
			//double 유형에 대한 길이 정의는 totalDigits 표현이 불가능함 , maxInclusive 로 대체 
			restriction = dataField.getLength() == 0 ? 
					new Restriction(Variables.xsdTypeDouble) : 
					new Restriction(Variables.xsdTypeDouble, new RestrictionValue(Variables.kMaxInclusive, dataField.getLength() + ""));			
		}else if(xsdType.equals(Variables.xsdTypeNegativeInteger)) {
			restriction = dataField.getLength() == 0 ? 
					new Restriction(Variables.xsdTypeNegativeInteger) : 
					new Restriction(Variables.xsdTypeNegativeInteger, new RestrictionValue(Variables.kTotalDigits, dataField.getLength() + ""));
		}else if(xsdType.equals(Variables.xsdTypeNonNegativeInteger)) {
			restriction = dataField.getLength() == 0 ? 
					new Restriction(Variables.xsdTypeNonNegativeInteger) : 
					new Restriction(Variables.xsdTypeNonNegativeInteger, new RestrictionValue(Variables.kTotalDigits, dataField.getLength() + ""));
		}else if(xsdType.equals(Variables.xsdTypePositiveInteger)) {
			restriction = dataField.getLength() == 0 ? 
					new Restriction(Variables.xsdTypePositiveInteger) : 
					new Restriction(Variables.xsdTypePositiveInteger, new RestrictionValue(Variables.kTotalDigits, dataField.getLength() + ""));
		}else if(xsdType.equals(Variables.xsdTypeNonPositiveInteger)) {
			restriction = dataField.getLength() == 0 ? 
					new Restriction(Variables.xsdTypeNonPositiveInteger) : 
					new Restriction(Variables.xsdTypeNonPositiveInteger, new RestrictionValue(Variables.kTotalDigits, dataField.getLength() + ""));
		}else if(xsdType.equals(Variables.xsdTypeShort)) {
			restriction = dataField.getLength() == 0 ? 
					new Restriction(Variables.xsdTypeShort) : 
					new Restriction(Variables.xsdTypeShort, new RestrictionValue(Variables.kTotalDigits, dataField.getLength() + ""));
		}else if(xsdType.equals(Variables.xsdTypeUnsignedLong)) {
			restriction = dataField.getLength() == 0 ? 
					new Restriction(Variables.xsdTypeUnsignedLong) : 
					new Restriction(Variables.xsdTypeUnsignedLong, new RestrictionValue(Variables.kTotalDigits, dataField.getLength() + ""));
		}else if(xsdType.equals(Variables.xsdTypeUnsignedInt)) {
			restriction = dataField.getLength() == 0 ? 
					new Restriction(Variables.xsdTypeUnsignedInt) : 
					new Restriction(Variables.xsdTypeUnsignedInt, new RestrictionValue(Variables.kTotalDigits, dataField.getLength() + ""));
		}else if(xsdType.equals(Variables.xsdTypeUnsignedShort)) {
			restriction = dataField.getLength() == 0 ? 
					new Restriction(Variables.xsdTypeUnsignedShort) : 
					new Restriction(Variables.xsdTypeUnsignedShort, new RestrictionValue(Variables.kTotalDigits, dataField.getLength() + ""));
		}else if(xsdType.equals(Variables.xsdTypeBoolean)) {
			restriction = new Restriction(Variables.xsdTypeBoolean);
		}else if(xsdType.equals(Variables.xsdTypeBase64Binary)) {
			restriction = dataField.getLength() == 0 ? 
					new Restriction(Variables.xsdTypeBase64Binary) : 
					new Restriction(Variables.xsdTypeBase64Binary, new RestrictionValue(Variables.kMaxLength, dataField.getLength() + ""));
		}else if(xsdType.equals(Variables.xsdTypeHexBinary)) {
			restriction = dataField.getLength() == 0 ? 
					new Restriction(Variables.xsdTypeHexBinary) : 
					new Restriction(Variables.xsdTypeHexBinary, new RestrictionValue(Variables.kMaxLength, dataField.getLength() + ""));
		}else if(xsdType.equals(Variables.xsdTypeDate)) {
			restriction = new Restriction(Variables.xsdTypeDate);
		}else if(xsdType.equals(Variables.xsdTypeTime)) {
			restriction = new Restriction(Variables.xsdTypeTime);
		}else if(xsdType.equals(Variables.xsdTypeDateTime)) {
			restriction = new Restriction(Variables.xsdTypeDateTime);
		}else {
			throw new Exception("UnsupportedTypeDefinition(xsdType:" + xsdType + ")");
		}
		simpleType.setRestriction(restriction);
		 
		return simpleType;
	}
	
	public ComplexType getComplexType(String name) {
		return complexTypeMapByName.get(name);
	}
	
	public Collection<ComplexType> getComplexTypes(){
		return complexTypeMapByName.values();
	}
	
	/**
	 * @return the dataSetElement
	 */
	public DataSetElement getDataSetElement() {
		return dataSetRootElement;
	}

	/**
	 * @param dataSetElement the dataSetElement to set
	 */
	public void setDataSetElement(DataSetElement dataSetElement) {
		this.dataSetRootElement = dataSetElement;
	}
	 
	
	
	
	

}
