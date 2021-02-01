/**
 * Copyright 2020 Mocomsys Inc.  All Rights Reserved.
 */
package pep.per.mint.endpoint.service.deploy.data.msg;

import java.io.File;
import java.io.StringWriter;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import pep.per.mint.common.data.basic.dataset.DataSet;
import pep.per.mint.common.util.Util;
import pep.per.mint.endpoint.Variables;
import pep.per.mint.endpoint.service.deploy.data.msg.xsd.Annotation;
import pep.per.mint.endpoint.service.deploy.data.msg.xsd.AppInfo;
import pep.per.mint.endpoint.service.deploy.data.msg.xsd.ComplexType;
import pep.per.mint.endpoint.service.deploy.data.msg.xsd.DataSetElement;
import pep.per.mint.endpoint.service.deploy.data.msg.xsd.DataSetXsd;
import pep.per.mint.endpoint.service.deploy.data.msg.xsd.Documentation;
import pep.per.mint.endpoint.service.deploy.data.msg.xsd.Property;
import pep.per.mint.endpoint.service.deploy.data.msg.xsd.Restriction;
import pep.per.mint.endpoint.service.deploy.data.msg.xsd.RestrictionValue;

/**
 * <pre>
 * XSD builder
 * </pre>
 * @author whoana
 * @since Jul 31, 2020
 */
public class MessageDefinitionBuilder {

	Logger logger = LoggerFactory.getLogger(getClass());

	public final static String kAnnotation 	= "annotation";
	public final static String kAppinfo    	= "appinfo";
	public final static String kElement	 	= "element";
	public final static String kSimpleType	= "simpleType";
	public final static String kComplexType = "complexType";
	public final static String kRestriction = "restriction";
	public final static String kSequence	= "sequence";
	public final static String kPattern	 	= "pattern";
	public final static String kLength		= "length";
	public final static String kName 	  	= "name";
	public final static String kMinOccurs 	= "minOccurs";
	public final static String kMaxOccurs 	= "maxOccurs";
	public final static String kBase 		= "base";
	public final static String kValue 		= "value";
	public final static String kDefault 	= "default";
	public final static String kDocumentation = "documentation";
	public final static String kProperty = "property";
	public final static String kKey = "key";
	public final static String kProperties = "properties";
	public final static String NS_PREFIX = "xs:";

	public final static String NS_MINT = "http://mint.mocomsys.com/2020/schema";
	public final static String MI_PREFIX = "mx:";


	Document document;

	Element schemaRoot;

	public MessageDefinitionBuilder() throws Exception {


        initialize();
	}



	/**
	 * @throws Exception
	 *
	 */
	private void initialize() throws Exception {
		DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
    	DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
		document = docBuilder.newDocument();
        document.setXmlStandalone(true); // standalone="no" 를 없애준다.

        schemaRoot = document.createElementNS(XMLConstants.W3C_XML_SCHEMA_NS_URI, NS_PREFIX+"schema");

        document.appendChild(schemaRoot);
	}



	public String build(DataSet dataSet,Variables variables) throws Exception {


		DataSetXsd xsd = new DataSetXsd(dataSet, variables);

		//System.out.println("xsd jsonL\n" + Util.toJSONPrettyString(xsd));

		DataSetElement dataSetElement = xsd.getDataSetElement();


		Element dataSetRoot = createElementNS(kElement, dataSetElement.getName());
		schemaRoot.appendChild(dataSetRoot);

		buildAnnotation(dataSetRoot, dataSetElement.getAnnotation());

		buildDataSetDocument(dataSetRoot, xsd);





		TransformerFactory tFactory = TransformerFactory.newInstance();
		Transformer transformer = tFactory.newTransformer();
		transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		DOMSource domSource = new DOMSource(document);

		//to create a file use something like this:
		//transformer.transform(domSource, new StreamResult(new File(dataSet.getCd() + ".xsd")));

		//to print to console use this:
		//transformer.transform(domSource, new StreamResult(System.out));

		StringWriter sw = new StringWriter();
		transformer.transform(domSource, new StreamResult(sw));
		//System.out.println("xsd:\n"+sw.toString());


		return sw.toString();
	}
	/**
	 * @param dataSetRoot
	 * @param complexType
	 */
	private void buildDataSetDocument(Element dataSetRoot, DataSetXsd xsd) {
		// TODO Auto-generated method stub
		Element complexTypeElement = createElementNS(kComplexType);
		dataSetRoot.appendChild(complexTypeElement);

		Element sequenceElement = createElementNS(kSequence);
		complexTypeElement.appendChild(sequenceElement);

		Collection<DataSetElement> elements = xsd.getDataSetElement().getComplexType().getElements();
		for (DataSetElement dse : elements) {

			int kind = dse.getTypeKind();

			if(DataSetElement.COMPLEX_TYPE == kind) {
				buildComplexType(sequenceElement, dse, xsd);
			}else if(DataSetElement.SIMPLE_TYPE == kind) {

				buildSimpleType(sequenceElement, dse);
			}else{
				buildPrimitiveType(sequenceElement, dse);
			}
		}
	}

	String kType = "type";

	/**
	 * @param sequenceElement
	 * @param dse
	 * @param xsd
	 */
	private void buildPrimitiveType(Element parent, DataSetElement dse) {
		//create element
		{
			Map<String, String> attributes = new LinkedHashMap<String, String>();
			attributes.put(kName, dse.getName());
			attributes.put(kType, dse.getType());
			attributes.put(kMinOccurs, dse.getMinOccurs());
			attributes.put(kMaxOccurs, dse.getMaxOccurs());
			if(dse.getDefaultValue() != null) attributes.put(kDefault, 	dse.getDefaultValue());
			Element element = createElementNS(kElement, attributes);
			parent.appendChild(element);
				//create annotation
				if(dse.getAnnotation() != null) buildAnnotation(element, dse.getAnnotation());
		}

	}



	/**
	 * @param sequenceElement
	 * @param dse
	 * @param xsd
	 */
	private void buildSimpleType(Element parent, DataSetElement dse) {
		//create element
		{

			Map<String, String> attributes = new LinkedHashMap<String, String>();
			attributes.put(kName, 		dse.getName());
			attributes.put(kMinOccurs, 	dse.getMinOccurs());
			attributes.put(kMaxOccurs, 	dse.getMaxOccurs());
			if(dse.getDefaultValue() != null) attributes.put(kDefault, 	dse.getDefaultValue());
			Element element = createElementNS(kElement, attributes);
			parent.appendChild(element);
			//create simpleType
			{
				Element simpleType = createElementNS(kSimpleType);
				element.appendChild(simpleType);
				//create annotation
				if(dse.getAnnotation() != null) buildAnnotation(simpleType, dse.getAnnotation());
				//create restriction
				Restriction restriction = dse.getSimpleType().getRestriction();
				if(restriction != null) buildRestriction(simpleType,  restriction);
			}
		}
	}



	/**
	 * @param simpleType
	 * @param restriction
	 */
	private void buildRestriction(Element parent, Restriction restriction) {

		Element restr = createElementNS(kRestriction);
		restr.setAttribute(kBase, restriction.getBase());
		parent.appendChild(restr);

		List<RestrictionValue> rvs = restriction.getValues();
		if(!Util.isEmpty(rvs)) {
			for (RestrictionValue rv : rvs) {
				String name = rv.getName();
				String value = rv.getValue();

				Element rve = createElementNS(name);
				rve.setAttribute(kValue, value);
				restr.appendChild(rve);
			}
		}

	}



	/**
	 * @param parent
	 * @param complexType
	 */
	private void buildComplexType(Element parent, DataSetElement pdse, DataSetXsd xsd) {
		String name = pdse.getName();

		String minOccurs = pdse.getMinOccurs();
		String maxOccurs = pdse.getMaxOccurs();
		Map<String, String> attributes = new LinkedHashMap<String, String>();
		attributes.put(kName, name);
		attributes.put(kMinOccurs, minOccurs);
		attributes.put(kMaxOccurs, maxOccurs);

		Element element = createElementNS(kElement, attributes);
		parent.appendChild(element);

		if(pdse.getAnnotation() != null) buildAnnotation(element, pdse.getAnnotation());


		ComplexType complexType = xsd.getComplexType(pdse.getType());
		Element complexTypeElement = createElementNS(kComplexType);
		element.appendChild(complexTypeElement);

		Element sequenceElement = createElementNS(kSequence);
		complexTypeElement.appendChild(sequenceElement);

		Collection<DataSetElement> elements = complexType.getElements();

		for (DataSetElement dse : elements) {
			int kind = dse.getTypeKind();
			if(DataSetElement.COMPLEX_TYPE == kind) {
				buildComplexType(sequenceElement, dse, xsd);
			}else if(DataSetElement.SIMPLE_TYPE == kind) {
				buildSimpleType(sequenceElement, dse);
			}else{
				buildPrimitiveType(sequenceElement, dse);
			}
		}

	}




	private void buildAnnotation(Element element, Annotation annotation) {

		Element annotationElement = createElementNS(kAnnotation);
		element.appendChild(annotationElement);

		Documentation documentation = annotation.getDocumentation();
		if(!Util.isEmpty(documentation)) {
			Element de = createElementNS(kDocumentation);
			de.setTextContent(documentation.getMsg());
			annotationElement.appendChild(de);
		}

		AppInfo appInfo = annotation.getAppInfo();
		if(!Util.isEmpty(appInfo)) {
			List<Property> properties = appInfo.getProperties();
			if(!Util.isEmpty(properties)) {
				Element appInfoElement = createElementNS(kAppinfo);
				annotationElement.appendChild(appInfoElement);

				Element fieldInfoElement = createElementMX(kProperties);
				appInfoElement.appendChild(fieldInfoElement);
				for (Property property : properties) {
					Map<String, String> params = new LinkedHashMap<String, String>();
					params.put(kKey, property.getKey());
					params.put(kValue, property.getValue());
					Element pe = createElementMX(kProperty, params);
					fieldInfoElement.appendChild(pe);
				}


			}
		}
	}




	final static String FIELD_TYPE_STRING  = "0";
	final static String FIELD_TYPE_NUMBER  = "1";
	final static String FIELD_TYPE_BOOLEAN = "2";
	final static String FIELD_TYPE_BINARY  = "3";
	final static String FIELD_TYPE_COMPLEX = "4";



    /**
	 * @return
	 */
	private Element getRoot() {
		// TODO Auto-generated method stub
		return schemaRoot;
	}

	/**
	 * @return
	 */
	private Document getDocument() {
		// TODO Auto-generated method stub
		return document;
	}

	public Element createElementMX(String elementName, Map<String, String> attributes) {
        Element element = document.createElementNS(NS_MINT, MI_PREFIX + elementName);
        if(attributes != null && attributes.size() > 0) {
        	Set<String> keys = attributes.keySet();
        	for (String key : keys) {
        		String value = attributes.get(key);
        		element.setAttribute(key, value);
			}
        }
        return element;
    }


	public Element createElementMX(String elementName, String nameAttrVal, String typeAttrVal) {
        Map<String, String> attributes = new HashMap<String, String>();
        if(nameAttrVal != null)
        	attributes.put("name", nameAttrVal);
        if(typeAttrVal != null)
        	attributes.put("type", typeAttrVal);
        return createElementMX(elementName, attributes);
    }

    public Element createElementMX(String elementName, String nameAttrVal) {
        return createElementMX(elementName, nameAttrVal, null);
    }

    public Element createElementMX(String elementName) {
        return createElementMX(elementName, null, null);
    }


	public Element createElementNS(String elementName, Map<String, String> attributes) {
        Element element = document.createElementNS(XMLConstants.W3C_XML_SCHEMA_NS_URI, NS_PREFIX + elementName);
        if(attributes != null && attributes.size() > 0) {
        	Set<String> keys = attributes.keySet();
        	for (String key : keys) {
        		String value = attributes.get(key);
        		element.setAttribute(key, value);
			}
        }
        return element;
    }


	public Element createElementNS(String elementName, String nameAttrVal, String typeAttrVal) {
        Map<String, String> attributes = new HashMap<String, String>();
        if(nameAttrVal != null)
        	attributes.put("name", nameAttrVal);
        if(typeAttrVal != null)
        	attributes.put("type", typeAttrVal);
        return createElementNS(elementName, attributes);
    }

    public Element createElementNS(String elementName, String nameAttrVal) {
        return createElementNS(elementName, nameAttrVal, null);
    }

    public Element createElementNS(String elementName) {
        return createElementNS(elementName, null, null);
    }


    public Element createElement(String elementName, Map<String, String> attributes) {
        Element element = document.createElement(elementName);
        if(attributes != null && attributes.size() > 0) {
        	Set<String> keys = attributes.keySet();
        	for (String key : keys) {
        		String value = attributes.get(key);
        		element.setAttribute(key, value);
			}
        }
        return element;
    }

	public Element createElement(String elementName, String nameAttrVal, String typeAttrVal) {
		Map<String, String> attributes = new HashMap<String, String>();
        if(nameAttrVal != null)
        	attributes.put("name", nameAttrVal);
        if(typeAttrVal != null)
        	attributes.put("type", typeAttrVal);
        return createElement(elementName, attributes);
    }

    public Element createElement(String elementName, String nameAttrVal) {
        return createElement(elementName, nameAttrVal, null);
    }

    public Element createElement(String elementName) {
        return createElement(elementName, null, null);
    }



}
