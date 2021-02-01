/**
 * Copyright 2020 Mocomsys Inc.  All Rights Reserved.
 */
package pep.per.mint.endpoint.service.deploy.data.description;


import java.io.File;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.CDATASection;

import pep.per.mint.common.data.basic.Business;
import pep.per.mint.common.data.basic.Interface;
import pep.per.mint.common.data.basic.dataset.DataMap;
import pep.per.mint.common.data.basic.dataset.DataSet;
import pep.per.mint.common.data.basic.runtime.AppModel;
import pep.per.mint.common.data.basic.runtime.AppModelAttribute;
import pep.per.mint.common.data.basic.runtime.InterfaceModel;
import pep.per.mint.common.data.basic.runtime.MappingModel;
import pep.per.mint.common.data.basic.runtime.MessageModel;
import pep.per.mint.common.util.Util;
import pep.per.mint.database.service.an.DataSetService;
import pep.per.mint.endpoint.Variables;
import pep.per.mint.endpoint.service.deploy.data.mapping.MappingDefinitionBuilder;
import pep.per.mint.endpoint.service.deploy.data.msg.MessageDefinitionBuilder;

/**
 * @author whoana
 * @since Aug 20, 2020
 */
public class ModelDescriptionXmlBuilder {

	Logger logger = LoggerFactory.getLogger(getClass());


	final static String CD_APP_ATTR_INPUT_TYPE_SENTENCE = "6";//속성 입력유형 문장

	Document document;

	Element model;

	Element id;

	Element name;

	Element stage;

	Element createDate;

	Element interfaze;

	Element apps;

	Element layouts;

	Element mappings;

	public ModelDescriptionXmlBuilder() throws ParserConfigurationException {
		initialize();
	}


	/**
	 * @throws ParserConfigurationException
	 *
	 */
	private void initialize() throws ParserConfigurationException {
		DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
    	DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
		document = docBuilder.newDocument();
        document.setXmlStandalone(true); // standalone="no" 를 없애준다.
        model = document.createElementNS(XMLConstants.W3C_XML_SCHEMA_INSTANCE_NS_URI, "model");
        document.appendChild(model);
	}


	public String build(InterfaceModel interfaceModel, DataSetService dataSetService, Interface interfaceInfo, Variables variables, String deployUserId) throws Exception {
		String xml = null;
		//--------------------------------------------------------------------------------------------------
		//id
		//--------------------------------------------------------------------------------------------------
		{
			id = document.createElement(Variables.kId);
			id.setTextContent(interfaceModel.getMid());
	        model.appendChild(id);
		}
		//--------------------------------------------------------------------------------------------------
		//name
		//--------------------------------------------------------------------------------------------------
		{
			name = document.createElement(Variables.kName);
			name.setTextContent(interfaceModel.getName());
	        model.appendChild(name);
		}
		//--------------------------------------------------------------------------------------------------
		//stage
		//--------------------------------------------------------------------------------------------------
		{
			Element stage = document.createElement(Variables.kStage);
			stage.setTextContent(interfaceModel.getStage());
	        model.appendChild(stage);
		}
		//--------------------------------------------------------------------------------------------------
		//createDate
		//--------------------------------------------------------------------------------------------------
		{
			Element createDate = document.createElement(Variables.kCreateDate);
			createDate.setTextContent(Util.getFormatedDate());
	        model.appendChild(createDate);
		}
		//--------------------------------------------------------------------------------------------------
		//version
		//--------------------------------------------------------------------------------------------------
		{
			Element version = document.createElement(Variables.kVersion);
			version.setTextContent(interfaceModel.getVersion());
	        model.appendChild(version);
		}
		//--------------------------------------------------------------------------------------------------
		//description
		//--------------------------------------------------------------------------------------------------
		{
			Element description = document.createElement(Variables.kDescription);
			description.setTextContent(Util.isEmpty(interfaceModel.getComments()) ? interfaceModel.getName() : interfaceModel.getComments());
	        model.appendChild(description);
		}
		//--------------------------------------------------------------------------------------------------
		//interface
		//--------------------------------------------------------------------------------------------------
		{
			Element interfaze = document.createElement(Variables.kInterface);
			interfaze.setAttribute(Variables.kId, interfaceModel.getIntegrationId());
			interfaze.setAttribute(Variables.kName, interfaceInfo.getInterfaceNm());

			//--------------------------------------------------------------------------------------------------
			//interface.business
			//--------------------------------------------------------------------------------------------------
			if(!Util.isEmpty(interfaceInfo.getBusinessList())) {
				Business businessInfo = interfaceInfo.getBusinessList().get(0);
				Element business = document.createElement(Variables.kBusiness);
				business.setAttribute(Variables.kCd,   businessInfo.getBusinessCd());
				business.setAttribute(Variables.kName, businessInfo.getBusinessNm());
				business.setAttribute(Variables.kDescription, Variables.vBusinessLabel);
				interfaze.appendChild(business);
			}

			//--------------------------------------------------------------------------------------------------
			//interface.channel
			//--------------------------------------------------------------------------------------------------
			if(interfaceInfo.getChannel() != null) {
				Element channel = document.createElement(Variables.kChannel);
				channel.setAttribute(Variables.kCd,   interfaceInfo.getChannel().getChannelCd());
				channel.setAttribute(Variables.kName, interfaceInfo.getChannel().getChannelNm());
				channel.setAttribute(Variables.kDescription, Variables.vChannelLabel);
				interfaze.appendChild(channel);
			}

			//--------------------------------------------------------------------------------------------------
			//interface.dataPrDir
			//--------------------------------------------------------------------------------------------------
			{
				Element dataPrDir = document.createElement(Variables.kDataPrDir);
				dataPrDir.setAttribute(Variables.kCd,   interfaceInfo.getDataPrDir());
				dataPrDir.setAttribute(Variables.kName, interfaceInfo.getDataPrDirNm());
				dataPrDir.setAttribute(Variables.kDescription, Variables.vDataPrDirLabel);
				interfaze.appendChild(dataPrDir);
			}

			//--------------------------------------------------------------------------------------------------
			//interface.dataPrMethod
			//--------------------------------------------------------------------------------------------------
			{
				Element dataPrMethod = document.createElement(Variables.kDataPrMethod);
				dataPrMethod.setAttribute(Variables.kCd,   interfaceInfo.getDataPrMethod());
				dataPrMethod.setAttribute(Variables.kName, interfaceInfo.getDataPrMethodNm());
				dataPrMethod.setAttribute(Variables.kDescription, Variables.vDataPrMethodLabel);
				interfaze.appendChild(dataPrMethod);
			}

			//--------------------------------------------------------------------------------------------------
			//interface.appPrMethod
			//--------------------------------------------------------------------------------------------------
			{
				Element appPrMethod = document.createElement(Variables.kAppPrMethod);
				appPrMethod.setAttribute(Variables.kCd,   interfaceInfo.getAppPrMethod());
				appPrMethod.setAttribute(Variables.kName, interfaceInfo.getAppPrMethodNm());
				appPrMethod.setAttribute(Variables.kDescription, Variables.vAppPrMethodLabel);
				interfaze.appendChild(appPrMethod);
			}

			//--------------------------------------------------------------------------------------------------
			//interface.dataSeq
			//--------------------------------------------------------------------------------------------------
			{
				Element dataSeq = document.createElement(Variables.kDataSeq);
				dataSeq.setAttribute(Variables.kValue,   interfaceInfo.getDataSeqYn());
				dataSeq.setAttribute(Variables.kDescription, Variables.vDataSeqLabel);
				interfaze.appendChild(dataSeq);
			}

			//--------------------------------------------------------------------------------------------------
			//interface.dataFrequency
			//--------------------------------------------------------------------------------------------------
			{
				Element dataFrequency = document.createElement(Variables.kDataFrequency);
				dataFrequency.setAttribute(Variables.kCd,   interfaceInfo.getDataFrequency());
				dataFrequency.setAttribute(Variables.kName, interfaceInfo.getDataFrequencyNm());
				dataFrequency.setAttribute(Variables.kValue,interfaceInfo.getDataFrequencyInput());
				dataFrequency.setAttribute(Variables.kDescription, Variables.vDataFrequencyLabel);
				interfaze.appendChild(dataFrequency);
			}

			//--------------------------------------------------------------------------------------------------
			//interface.sizePerTran
			//--------------------------------------------------------------------------------------------------
			{
				Element sizePerTran = document.createElement(Variables.kSizePerTran);
				sizePerTran.setAttribute(Variables.kValue,   interfaceInfo.getSizePerTran() + "");
				sizePerTran.setAttribute(Variables.kDescription, Variables.vSizePerTranLabel);
				interfaze.appendChild(sizePerTran);
			}

			//--------------------------------------------------------------------------------------------------
			//interface.cntPerFrequency
			//--------------------------------------------------------------------------------------------------
			{
				Element cntPerFrequency = document.createElement(Variables.kCntPerFrequency);
				cntPerFrequency.setAttribute(Variables.kValue,   interfaceInfo.getCntPerFrequency() + "");
				cntPerFrequency.setAttribute(Variables.kDescription, Variables.vCntPerFrequencyLabel);
				interfaze.appendChild(cntPerFrequency);
			}

			//--------------------------------------------------------------------------------------------------
			//interface.cntPerDay
			//--------------------------------------------------------------------------------------------------
			{
				Element cntPerDay = document.createElement(Variables.kCntPerDay);
				cntPerDay.setAttribute(Variables.kValue,   interfaceInfo.getCntPerDay() + "");
				cntPerDay.setAttribute(Variables.kDescription, Variables.vCntPerDayLabel);
				interfaze.appendChild(cntPerDay);
			}

			//--------------------------------------------------------------------------------------------------
			//interface.qttPerDay
			//--------------------------------------------------------------------------------------------------
			{
				Element qttPerDay = document.createElement(Variables.kQttPerDay);
				qttPerDay.setAttribute(Variables.kValue,   interfaceInfo.getQttPerDay() + "");
				qttPerDay.setAttribute(Variables.kDescription, Variables.vQttPerDayLabel);
				interfaze.appendChild(qttPerDay);
			}

	        model.appendChild(interfaze);
		}
		//--------------------------------------------------------------------------------------------------
		//deployUser
		//--------------------------------------------------------------------------------------------------
		{
			Element deployUser = document.createElement(Variables.kDeployUserId);
			deployUser.setTextContent(deployUserId);
	        model.appendChild(deployUser);
		}
		//--------------------------------------------------------------------------------------------------
		//regDate
		//--------------------------------------------------------------------------------------------------
		{
			Element regDate = document.createElement(Variables.kRegDate);
			regDate.setTextContent(interfaceModel.getRegDate());
	        model.appendChild(regDate);
		}
		//--------------------------------------------------------------------------------------------------
		//regUserId
		//--------------------------------------------------------------------------------------------------
		{
			Element regUserId = document.createElement(Variables.kRegUserId);
			regUserId.setTextContent(interfaceModel.getRegId());
	        model.appendChild(regUserId);
		}
		//--------------------------------------------------------------------------------------------------
		//modDate
		//--------------------------------------------------------------------------------------------------
		{
			Element modDate = document.createElement(Variables.kModDate);
			modDate.setTextContent(interfaceModel.getModDate());
	        model.appendChild(modDate);
		}
		//--------------------------------------------------------------------------------------------------
		//modUserId
		//--------------------------------------------------------------------------------------------------
		{
			Element modUserId = document.createElement(Variables.kModUserId);
			modUserId.setTextContent(interfaceModel.getModId());
	        model.appendChild(modUserId);
		}
		//--------------------------------------------------------------------------------------------------
		//apps
		//--------------------------------------------------------------------------------------------------
		{
			apps = document.createElement(Variables.kApps);
			model.appendChild(apps);
		}

		//--------------------------------------------------------------------------------------------------
		//layouts
		//--------------------------------------------------------------------------------------------------
		{
			layouts = document.createElement(Variables.kLayouts);
			model.appendChild(layouts);
		}


		//--------------------------------------------------------------------------------------------------
		//mappings
		//--------------------------------------------------------------------------------------------------
		{
			mappings = document.createElement(Variables.kMappings);
			model.appendChild(mappings);
		}

		List<String> layoutDupCheck = new ArrayList<String>();
		List<String> mappingDupCheck = new ArrayList<String>();

		//--------------------------------------------------------------------------------------------------
		//apps detail
		//--------------------------------------------------------------------------------------------------
		{
			List<AppModel> appModels = interfaceModel.getAppModels();
			if(!Util.isEmpty(appModels)) {


				for (AppModel appModel : appModels) {
					//--------------------------------------------------------------------------------------
					//app
					//--------------------------------------------------------------------------------------
					Element app = document.createElement(Variables.kApp);
					app.setAttribute(Variables.kId, appModel.getMid());
					app.setAttribute(Variables.kName, appModel.getName());
					apps.appendChild(app);
					//--------------------------------------------------------------------------------------
					//type
					//--------------------------------------------------------------------------------------
					{
						Element type = document.createElement(Variables.kType);
						type.setAttribute(Variables.kCd, appModel.getType());
						type.setAttribute(Variables.kName, appModel.getTypeName());
						app.appendChild(type);
					}
					//--------------------------------------------------------------------------------------
					//system
					//--------------------------------------------------------------------------------------
					{
						Element system = document.createElement(Variables.kSystem);
						system.setAttribute(Variables.kCd, appModel.getSystemCd());
						system.setAttribute(Variables.kName, appModel.getSystemName());
						system.setAttribute(Variables.kType, appModel.getSystemType());
						system.setAttribute(Variables.kSeq, appModel.getSystemSeq() + "");
						app.appendChild(system);

					}
					//--------------------------------------------------------------------------------------
					//server
					//--------------------------------------------------------------------------------------
					{
						Element server = document.createElement(Variables.kServer);
						server.setAttribute(Variables.kCd, appModel.getServerCd());
						server.setAttribute(Variables.kName, appModel.getServerName());
						app.appendChild(server);
					}
					//--------------------------------------------------------------------------------------
					//properties
					//--------------------------------------------------------------------------------------
					{
						Collection<List<AppModelAttribute>> attributes = appModel.getAttributes().values();
						if(attributes != null && attributes.size() > 0) {

							Element properties = document.createElement(Variables.kProperties);
							app.appendChild(properties);

							for (List<AppModelAttribute> array : attributes) {

								if(array.size() == 1) {
									AppModelAttribute attr = array.get(0);

									Element property = document.createElement(Variables.kProperty);
									property.setAttribute(Variables.kName, attr.getCd());
									if(CD_APP_ATTR_INPUT_TYPE_SENTENCE.equals(attr.getInputType())){
										//앱속성 input type = '6'(문장입력)에 대한 처리
										CDATASection cdata = document.createCDATASection(attr.getVal());
										property.appendChild(cdata);
									}else {
										property.setTextContent(attr.getVal());
									}
									properties.appendChild(property);
								}else if(array.size() > 1){
									for (AppModelAttribute attr : array) {
										Element property = document.createElement(Variables.kProperty);
										property.setAttribute(Variables.kName, attr.getCd());
										property.setTextContent(attr.getVal());
										properties.appendChild(property);
									}
								}
							}
						}
					}
					//--------------------------------------------------------------------------------------
					//layouts reference
					//--------------------------------------------------------------------------------------
					{
						List<MessageModel> msgModels = appModel.getMsgs();
						if(!Util.isEmpty(msgModels)) {

							Element layoutsRef = document.createElement(Variables.kLayouts);
							app.appendChild(layoutsRef);



							for (MessageModel msgModel : msgModels) {
								Element layout = document.createElement(Variables.kLayout);
								layout.setAttribute(Variables.kRef, msgModel.getCd());
								layout.setAttribute(Variables.kType, msgModel.getType());
								layoutsRef.appendChild(layout);

								//layout reference
								if(!layoutDupCheck.contains(msgModel.getDataSetId())) {
									buildLayoutReference(dataSetService.getSimpleDataSet(msgModel.getDataSetId(), "N"), variables);
									layoutDupCheck.add(msgModel.getDataSetId());
								}
							}

						}
					}
					//--------------------------------------------------------------------------------------
					//mappings reference
					//--------------------------------------------------------------------------------------
					{
						List<MappingModel> mappingModels = appModel.getMappings();
						if(!Util.isEmpty(mappingModels)){
							for (MappingModel mappingModel : mappingModels) {
								//layout reference
								if(!mappingDupCheck.contains(mappingModel.getMapId())) {
									Map<String, Object> res  = dataSetService.getSimpleDataMap(mappingModel.getMapId() , "N");
									if(res != null && res.size() > 0) {
										DataMap map = (DataMap) res.get("mapData");
										DataSet tar = (DataSet) res.get("tagDataSet");
										DataSet src = (DataSet) res.get("srcDataSet");
										buildMappingReference(mappingModel, Arrays.asList(src,tar), map);
										mappingDupCheck.add(mappingModel.getMapId());
									}

								}
							}
						}
					}
				}
			}
		}



		TransformerFactory tFactory = TransformerFactory.newInstance();
		Transformer transformer = tFactory.newTransformer();
		transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
		DOMSource domSource = new DOMSource(document);

		//to create a file use something like this:
		//transformer.transform(domSource, new StreamResult(new File(dataMap.getCd() + ".xml")));

		//to print to console use this:
		//transformer.transform(domSource, new StreamResult(System.out));

		StringWriter sw = new StringWriter();

		transformer.transform(domSource, new StreamResult(sw));
		//System.out.println("xml:\n"+sw.toString());




		return sw.toString();


	}


	private void buildMappingReference(MappingModel mappingModel, List<DataSet> dataSets, DataMap dataMap) throws Exception {
		// TODO Auto-generated method stub
		MappingDefinitionBuilder mpdb = new MappingDefinitionBuilder();
		String contents = mpdb.build(dataSets, dataMap);
		Element mapping = document.createElement(Variables.kMapping);
		mapping.setAttribute(Variables.kIOType, mappingModel.getType());
		mapping.setAttribute(Variables.kId, dataMap.getCd());
		mapping.setAttribute(Variables.kName, dataMap.getName());
		mapping.setAttribute(Variables.kRegDate, dataMap.getRegDate());
		mapping.setAttribute(Variables.kRegUserId, dataMap.getRegId());
		mapping.setAttribute(Variables.kModDate, dataMap.getModDate());
		mapping.setAttribute(Variables.kModUserId, dataMap.getModId());
		CDATASection mappingContents = document.createCDATASection(contents);
		mapping.appendChild(mappingContents);
		mappings.appendChild(mapping);
	}




	/**
	 * @param dataSetId
	 * @throws Exception
	 */
	private void buildLayoutReference(DataSet dataSet, Variables variables) throws Exception {
		MessageDefinitionBuilder mdb = new MessageDefinitionBuilder();
		String contents = mdb.build(dataSet, variables);
		Element layout = document.createElement(Variables.kLayout);
		layout.setAttribute(Variables.kId, dataSet.getCd());
		layout.setAttribute(Variables.kName, dataSet.getName1());
		layout.setAttribute(Variables.kRegDate, dataSet.getRegDate());
		layout.setAttribute(Variables.kRegUserId, dataSet.getRegId());
		layout.setAttribute(Variables.kModDate, dataSet.getModDate());
		layout.setAttribute(Variables.kModUserId, dataSet.getModId());
		CDATASection layoutContents = document.createCDATASection(contents);
		layout.appendChild(layoutContents);
		layouts.appendChild(layout);
	}
}
