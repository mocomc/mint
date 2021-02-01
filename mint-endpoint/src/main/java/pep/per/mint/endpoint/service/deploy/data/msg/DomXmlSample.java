/**
 * Copyright 2020 Mocomsys Inc.  All Rights Reserved.
 */
package pep.per.mint.endpoint.service.deploy.data.msg;

import java.io.File;
import java.io.FileOutputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * @author whoana
 * @since Jul 31, 2020
 */
public class DomXmlSample {

	public static void main(String[] args) {

		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

			// book 엘리먼트
			Document doc = docBuilder.newDocument();
			
			doc.setXmlStandalone(true); // standalone="no" 를 없애준다.
			String namespaceURI = "http://www.w3.org/2001/XMLSchema";
			String prefix = "xs";
			Element book = doc.createElementNS(namespaceURI, "schema");
			book.setPrefix(prefix);
			doc.appendChild(book);

			// code 엘리먼트
			Element code = doc.createElement("code");
			book.appendChild(code);

			// 속성값 정의 (id:1)
			code.setAttribute("id", "1");
			// code.setAttribute("type", "novel");

			// name 엘리먼트
			Element name = doc.createElement("name");
			name.appendChild(doc.createTextNode("사람은 무엇으로 사는가?"));
			code.appendChild(name);

			// writer 엘리먼트
			Element writer = doc.createElement("writer");
			writer.appendChild(doc.createTextNode("톨스토이"));
			code.appendChild(writer);

			// price 엘리먼트
			Element price = doc.createElement("price");
			price.appendChild(doc.createTextNode("100"));
			code.appendChild(price);

			code = doc.createElement("code");
			book.appendChild(code);

			// 속성값 정의 (id:2)
			code.setAttribute("id", "2");
			code.setAttribute("type", "novel");

			// name 엘리먼트
			name = doc.createElement("name");
			name.appendChild(doc.createTextNode("홍길동 전"));
			code.appendChild(name);

			// writer 엘리먼트
			writer = doc.createElement("writer");
			writer.appendChild(doc.createTextNode("허균"));
			code.appendChild(writer);

			// price 엘리먼트
			price = doc.createElement("price");
			price.appendChild(doc.createTextNode("300"));
			code.appendChild(price);

			// XML 파일로 쓰기
			TransformerFactory transformerFactory = TransformerFactory.newInstance();

			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4"); // 정렬 스페이스4칸
			transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
			transformer.setOutputProperty(OutputKeys.INDENT, "yes"); // 들여쓰기
			transformer.setOutputProperty(OutputKeys.DOCTYPE_PUBLIC, "yes"); // doc.setXmlStandalone(true); 했을때 붙어서
																				// 출력되는부분 개행

			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new FileOutputStream(new File("./book.xml")));

			transformer.transform(source, result);

			System.out.println("=========END=========");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
