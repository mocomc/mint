package pep.per.mint.common.parser;


import java.util.LinkedHashMap;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import pep.per.mint.common.data.FieldDefinition;
import pep.per.mint.common.data.FieldSetDefinition;
import pep.per.mint.common.data.FixedLengthFieldDefinition;
import pep.per.mint.common.data.MessageSet;
import pep.per.mint.common.message.Element;
import pep.per.mint.common.message.Message;
import pep.per.mint.common.message.PathInitializer;
import pep.per.mint.common.parser.FixedLengthParser;
import pep.per.mint.common.parser.Parser;
import pep.per.mint.common.util.Util;

public class FixedLengthParserTest {

	MessageSet stdMsgset = new MessageSet();
	byte[] testMsg = null;
	
	@Before
	public void setUp() throws Exception {
		
	}
	
	
	public void init() throws Exception {
		
		//====================================
		// MessageSet definition
		//------------------------------------
		LinkedHashMap<Object, FieldDefinition> fieldDefinitionMap = new LinkedHashMap<Object, FieldDefinition>();
		stdMsgset.setFieldDefinitionMap(fieldDefinitionMap);
		stdMsgset.setName("FixedLengthMessage");
		stdMsgset.setUsage(MessageSet.MSG_USAGE_COMMON);
		stdMsgset.setCcsid("UTF-8");
		//stdMsgset.setCcsid("EUC-KR");
		
		//----------------------------------------------------
		// field set definition : header
		// header infoset:
		// length[8]
		// interfaceId[30]
		// gid[20]
		// senderId[20]
		// senderTime[17]
		//----------------------------------------------------
		FieldSetDefinition header = new FieldSetDefinition();
		LinkedHashMap<Object, FieldDefinition> headerMap = new LinkedHashMap<Object, FieldDefinition>();
		header.setId("header");
		header.setName("header");
		header.setFieldSet(true);
		header.setRepeatCount(0);
		header.setRequired(true);
		header.setFieldDefinitionMap(headerMap);
		fieldDefinitionMap.put(header.getName(), header);
		//----------------------------------------------------
		// field definition : header.length
		//----------------------------------------------------
		FixedLengthFieldDefinition length = new FixedLengthFieldDefinition();
		length.setId("length");
		length.setName("length");
		length.setLength(8);
		length.setSeq(1);
		length.setRequired(true);
		length.setType(FieldDefinition.FIELD_TYPE_STR_INTEGER);
		length.setJustify(FieldDefinition.FIELD_JUSTIFY_RIGHT);
		length.setDefaultValue(0);
		length.setLengthFieldName(null);
		length.setRepeatCount(0);
		length.setRepeatFieldName(null);
		length.setPaddingValue("0");
		headerMap.put(length.getName(), length);
		//----------------------------------------------------
		// field definition : header.interfaceId
		//----------------------------------------------------
		FixedLengthFieldDefinition interfaceId = new FixedLengthFieldDefinition();
		interfaceId.setId("interfaceId");
		interfaceId.setName("interfaceId");
		interfaceId.setLength(30);
		interfaceId.setSeq(2);
		interfaceId.setRequired(true);
		interfaceId.setType(FieldDefinition.FIELD_TYPE_STRING);
		interfaceId.setJustify(FieldDefinition.FIELD_JUSTIFY_LEFT);
		interfaceId.setDefaultValue("");
		interfaceId.setLengthFieldName(null);
		interfaceId.setRepeatCount(0);
		interfaceId.setRepeatFieldName(null);
		interfaceId.setPaddingValue(" ");
		headerMap.put(interfaceId.getName(), interfaceId);
		//----------------------------------------------------
		// field definition : header.gid
		//----------------------------------------------------
		FixedLengthFieldDefinition gid = new FixedLengthFieldDefinition();
		gid.setId("gid");
		gid.setName("gid");
		gid.setLength(20);
		gid.setSeq(3);
		gid.setRequired(true);
		gid.setType(FieldDefinition.FIELD_TYPE_STRING);
		gid.setJustify(FieldDefinition.FIELD_JUSTIFY_LEFT);
		gid.setDefaultValue("");
		gid.setLengthFieldName(null);
		gid.setRepeatCount(0);
		gid.setRepeatFieldName(null);
		gid.setPaddingValue("0");
		headerMap.put(gid.getName(), gid);
		//----------------------------------------------------
		// field definition : header.senderId
		//----------------------------------------------------
		FixedLengthFieldDefinition senderId = new FixedLengthFieldDefinition();
		senderId.setId("senderId");
		senderId.setName("senderId");
		senderId.setLength(20);
		senderId.setSeq(4);
		senderId.setRequired(true);
		senderId.setType(FieldDefinition.FIELD_TYPE_STRING);
		senderId.setJustify(FieldDefinition.FIELD_JUSTIFY_LEFT);
		senderId.setDefaultValue("");
		senderId.setLengthFieldName(null);
		senderId.setRepeatCount(0);
		senderId.setRepeatFieldName(null);
		senderId.setPaddingValue(" ");
		headerMap.put(senderId.getName(), senderId);
		//----------------------------------------------------
		// field definition : header.sendTime
		//----------------------------------------------------
		FixedLengthFieldDefinition sendTime = new FixedLengthFieldDefinition();
		sendTime.setId("sendTime");
		sendTime.setName("sendTime");
		sendTime.setLength(17);
		sendTime.setSeq(5);
		sendTime.setRequired(true);
		sendTime.setType(FieldDefinition.FIELD_TYPE_STRING);
		sendTime.setJustify(FieldDefinition.FIELD_JUSTIFY_LEFT);
		sendTime.setDefaultValue("");
		sendTime.setLengthFieldName(null);
		sendTime.setRepeatCount(0);
		sendTime.setRepeatFieldName(null);
		sendTime.setPaddingValue(" ");
		headerMap.put(sendTime.getName(), sendTime);
		
		
		
		//----------------------------------------------------
		// data field set definition : data
		//----------------------------------------------------
		FieldSetDefinition data = new FieldSetDefinition();
		LinkedHashMap<Object, FieldDefinition> dataMap = new LinkedHashMap<Object, FieldDefinition>();
		data.setId("data");
		data.setName("data");
		data.setFieldSet(true);
		data.setRepeatCount(0);
		data.setRequired(true);
		data.setFieldDefinitionMap(dataMap);
		fieldDefinitionMap.put(data.getName(), data);
		
		//----------------------------------------------------
		// data field definition : data.repeatCount
		//----------------------------------------------------
		FixedLengthFieldDefinition repeatCount = new FixedLengthFieldDefinition();
		repeatCount.setId("repeatCount");
		repeatCount.setName("repeatCount");
		repeatCount.setLength(3);
		repeatCount.setSeq(6);
		repeatCount.setRequired(true);
		repeatCount.setType(FieldDefinition.FIELD_TYPE_STR_INTEGER);
		repeatCount.setJustify(FieldDefinition.FIELD_JUSTIFY_RIGHT);
		repeatCount.setDefaultValue(0);
		repeatCount.setLengthFieldName(null);
		repeatCount.setRepeatCount(0);
		repeatCount.setRepeatFieldName(null);
		repeatCount.setPaddingValue("0");
		dataMap.put(repeatCount.getName(), repeatCount);
		
		
		//----------------------------------------------------
		// data nested field set definition : people
		// field infoSet:
		// name[10]
		// age[3]
		// sex[1]
		// domestic[5] : true/false
		// address[200] : 
		// email[100]
		// phone[13]
		// cellphone[13]
		//----------------------------------------------------
		FieldSetDefinition people = new FieldSetDefinition();
		LinkedHashMap<Object, FieldDefinition> peopleMap = new LinkedHashMap<Object, FieldDefinition>();
		people.setId("people");
		people.setName("people");
		people.setFieldSet(true);
		people.setRepeatFieldName("FixedLengthMessage.data.repeatCount");
		people.setRepeatCount(0);
		people.setRequired(true);
		people.setFieldDefinitionMap(peopleMap);
		dataMap.put(people.getName(), people);
		//----------------------------------------------------
		// data field definition : data.people.name[10]
		//----------------------------------------------------
		FixedLengthFieldDefinition name = new FixedLengthFieldDefinition();
		name.setId("name");
		name.setName("name");
		name.setLength(10);
		name.setSeq(7);
		name.setRequired(true);
		name.setType(FieldDefinition.FIELD_TYPE_STRING);
		name.setJustify(FieldDefinition.FIELD_JUSTIFY_LEFT);
		name.setDefaultValue("");
		name.setLengthFieldName(null);
		name.setRepeatCount(0);
		name.setRepeatFieldName(null);
		name.setPaddingValue(" ");
		peopleMap.put(name.getName(), name);
		//----------------------------------------------------
		// data field definition : data.people.age[3]
		//----------------------------------------------------
		FixedLengthFieldDefinition age = new FixedLengthFieldDefinition();
		age.setId("age");
		age.setName("age");
		age.setLength(3);
		age.setSeq(8);
		age.setRequired(true);
		age.setType(FieldDefinition.FIELD_TYPE_STR_INTEGER);
		age.setJustify(FieldDefinition.FIELD_JUSTIFY_RIGHT);
		age.setDefaultValue(0);
		age.setLengthFieldName(null);
		age.setRepeatCount(0);
		age.setRepeatFieldName(null);
		age.setPaddingValue("0");
		peopleMap.put(age.getName(), age);
		//----------------------------------------------------
		// data field definition : data.people.sex[1]
		//----------------------------------------------------
		FixedLengthFieldDefinition sex = new FixedLengthFieldDefinition();
		sex.setId("sex");
		sex.setName("sex");
		sex.setLength(1);
		sex.setSeq(9);
		sex.setRequired(true);
		sex.setType(FieldDefinition.FIELD_TYPE_STRING);
		sex.setJustify(FieldDefinition.FIELD_JUSTIFY_LEFT);
		sex.setDefaultValue("");
		sex.setLengthFieldName(null);
		sex.setRepeatCount(0);
		sex.setRepeatFieldName(null);
		sex.setPaddingValue(" ");
		peopleMap.put(sex.getName(), sex);
		//----------------------------------------------------
		// data field definition : data.people.domestic[5]
		//----------------------------------------------------
		FixedLengthFieldDefinition domestic = new FixedLengthFieldDefinition();
		domestic.setId("domestic");
		domestic.setName("domestic");
		domestic.setLength(5);
		domestic.setSeq(9);
		domestic.setRequired(true);
		domestic.setType(FieldDefinition.FIELD_TYPE_BOOLEAN);
		domestic.setJustify(FieldDefinition.FIELD_JUSTIFY_LEFT);
		domestic.setDefaultValue(true);
		domestic.setLengthFieldName(null);
		domestic.setRepeatCount(0);
		domestic.setRepeatFieldName(null);
		domestic.setPaddingValue(" ");
		peopleMap.put(domestic.getName(), domestic);
		//----------------------------------------------------
		// data field definition : data.people.address[200]
		//----------------------------------------------------
		FixedLengthFieldDefinition address = new FixedLengthFieldDefinition();
		address.setId("address");
		address.setName("address");
		address.setLength(200);
		address.setSeq(10);
		address.setRequired(true);
		address.setType(FieldDefinition.FIELD_TYPE_STRING);
		address.setJustify(FieldDefinition.FIELD_JUSTIFY_LEFT);
		address.setDefaultValue("");
		address.setLengthFieldName(null);
		address.setRepeatCount(0);
		address.setRepeatFieldName(null);
		address.setPaddingValue(" ");
		peopleMap.put(address.getName(), address);
		//----------------------------------------------------
		// data field definition : data.people.email[100]
		//----------------------------------------------------
		FixedLengthFieldDefinition email = new FixedLengthFieldDefinition();
		email.setId("email");
		email.setName("email");
		email.setLength(100);
		email.setSeq(10);
		email.setRequired(true);
		email.setType(FieldDefinition.FIELD_TYPE_STRING);
		email.setJustify(FieldDefinition.FIELD_JUSTIFY_LEFT);
		email.setDefaultValue("");
		email.setLengthFieldName(null);
		email.setRepeatCount(0);
		email.setRepeatFieldName(null);
		email.setPaddingValue(" ");
		peopleMap.put(email.getName(), email);
		//----------------------------------------------------
		// data field definition : data.people.phone[13]
		//----------------------------------------------------
		FixedLengthFieldDefinition phone = new FixedLengthFieldDefinition();
		phone.setId("phone");
		phone.setName("phone");
		phone.setLength(13);
		phone.setSeq(11);
		phone.setRequired(true);
		phone.setType(FieldDefinition.FIELD_TYPE_STRING);
		phone.setJustify(FieldDefinition.FIELD_JUSTIFY_LEFT);
		phone.setDefaultValue("");
		phone.setLengthFieldName(null);
		phone.setRepeatCount(0);
		phone.setRepeatFieldName(null);
		phone.setPaddingValue(" ");
		peopleMap.put(phone.getName(), phone);
		//----------------------------------------------------
		// data field definition : data.people.cellphone[13]
		//----------------------------------------------------
		FixedLengthFieldDefinition cellphone = new FixedLengthFieldDefinition();
		cellphone.setId("cellphone");
		cellphone.setName("cellphone");
		cellphone.setLength(13);
		cellphone.setSeq(12);
		cellphone.setRequired(true);
		cellphone.setType(FieldDefinition.FIELD_TYPE_STRING);
		cellphone.setJustify(FieldDefinition.FIELD_JUSTIFY_LEFT);
		cellphone.setDefaultValue("");
		cellphone.setLengthFieldName(null);
		cellphone.setRepeatCount(0);
		cellphone.setRepeatFieldName(null);
		cellphone.setPaddingValue(" ");
		peopleMap.put(cellphone.getName(), cellphone);		
		//----------------------------------------------------
		// tail field definition : tail[1]
		//----------------------------------------------------
		FixedLengthFieldDefinition tail = new FixedLengthFieldDefinition();
		tail.setId("tail");
		tail.setName("tail");
		tail.setLength(1);
		tail.setSeq(13);
		tail.setRequired(true);
		tail.setType(FieldDefinition.FIELD_TYPE_STRING);
		tail.setJustify(FieldDefinition.FIELD_JUSTIFY_LEFT);
		tail.setDefaultValue("@");
		tail.setLengthFieldName(null);
		tail.setRepeatCount(0);
		tail.setRepeatFieldName(null);
		tail.setPaddingValue(" ");
		fieldDefinitionMap.put(tail.getName(), tail);
		
		PathInitializer.initializePath(stdMsgset, this, null);
		
		//----------------------------------------------------
		// field set definition : header
		// header infoset:
		// length[8]
		// interfaceId[30]
		// gid[20]
		// senderId[20]
		// senderTime[17]
		//----------------------------------------------------
		//----------------------------------------------------
		// data field definition : data.repeatCount
		//----------------------------------------------------
		//----------------------------------------------------
		// data nested field set definition : people
		// field infoSet:
		// name[10]
		// age[3]
		// sex[1]
		// domestic[5] : true/false
		// address[200] : 
		// email[100]
		// phone[13]
		// cellphone[13]
		//----------------------------------------------------
		
		//----------------------------------------------------
		// field set definition : header
		// header infoset:
		//----------------------------------------------------
		StringBuffer hBuffer = new StringBuffer();
		//hBuffer.append("00000000");//length[8]
		hBuffer.append(Util.rightPad("INTERFACE_A",30," "));//interfaceId[30]
		hBuffer.append(Util.rightPad("GID_A",20,"0"));//gid[20]
		hBuffer.append(Util.rightPad("SENDER_A",20,"0"));//senderId[20]
		hBuffer.append("20131118120000123");//senderTime[17]
		//----------------------------------------------------
		// data field definition : data.repeatCount
		//----------------------------------------------------
		StringBuffer dBuffer = new StringBuffer();
		dBuffer.append("004"); //repeatCount[3]
		//----------------------------------------------------
		// data nested field set definition : people
		// field infoSet:
		//----------------------------------------------------
		for(int i = 0 ; i < 5 ; i ++){
			dBuffer.append("1234567890");//name[10]
			dBuffer.append("019");//age[3]
			dBuffer.append("M");//sex[1]
			dBuffer.append("true ");//domestic[5]
			dBuffer.append(Util.rightPad("address",200," "));//address[200]
			dBuffer.append(Util.rightPad("Solution TF@mocomsys.com",100," "));//email[100]
			dBuffer.append(Util.rightPad("02-9113-4270",13," "));//phone[13]
			dBuffer.append(Util.rightPad("010-9113-4270",13," "));//cellphone[13]
		}
		
		String tailer = "@";
		
		StringBuffer totalBuffer = new StringBuffer();
		totalBuffer.append(hBuffer).append(dBuffer).append(tailer);
		int len = totalBuffer.toString().getBytes().length;
		
		String lenStr = Util.leftPad(len +"",8, "0");
		totalBuffer.insert(0, lenStr);
		
		testMsg = totalBuffer.toString().getBytes();
		
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public final void testParseAndExceptionHandle() {
	
		try{
			init();
			
			Parser<byte[], Message> parser = new FixedLengthParser(stdMsgset);
			
			//System.out.println("testMsg:[" +  Util.toString(testMsg) +"]");
			Message msg = null;
			long elapsed = System.currentTimeMillis();
			
			
			for(int i = 0 ; i < 1; i ++) msg = parser.parse(testMsg);
			
			
			//System.out.println("parsing elapsed:[" +  (System.currentTimeMillis() - elapsed) +"]");
			
			Element root = msg.getMsgElement();
			
			Element fixed = root.getChildAtFirst("FixedLengthMessage");
			if(!Util.isEmpty(fixed)){
				
				Element header = fixed.getChildAtFirst("header");
				if(Util.isEmpty(header)){
					throw new Exception("header is null.");
				}
				/**
				 * header infoset:
				 * length[8]
				 * interfaceId[30]
			     * gid[20]
				 * senderId[20]
				 * senderTime[17]
				 */
				Integer length = (Integer) header.getChildValueAtFirst("length");
				String interfaceId = (String) header.getChildValueAtFirst("interfaceId");
				String gid = (String) header.getChildValueAtFirst("gid");
				String senderId = (String) header.getChildValueAtFirst("senderId");
				String sendTime = (String) header.getChildValueAtFirst("sendTime");
				
				/*System.out.println("length:" + length);
				System.out.println("interfaceId:" + interfaceId);
				System.out.println("gid:" + gid);
				System.out.println("senderId:" + senderId);
				System.out.println("sendTime:" + sendTime);*/
				
				
				Element data = fixed.getChildAtFirst("data");
				if(Util.isEmpty(data)){
					throw new Exception("data is null.");
				}
				Integer repeatCount = (Integer) data.getChildValueAtFirst("repeatCount");
				//System.out.println("repeatCount:" + repeatCount);
				List<Element> peoples = data.getChildList("people");
				if(Util.isEmpty(peoples)){
					throw new Exception("data.people list is null.");
				}
				
				//----------------------------------------------------
				// data nested field set definition : people
				// field infoSet:
				// name[10]
				// age[3]
				// sex[1]
				// domestic[5] : true/false
				// address[200] : 
				// email[100]
				// phone[13]
				// cellphone[13]
				//----------------------------------------------------
				for (Element people : peoples) {
					String name = (String)people.getChildValueAtFirst("name");
					Integer age = (Integer)people.getChildValueAtFirst("age");
					String sex = (String)people.getChildValueAtFirst("sex");
					Boolean domestic = (Boolean)people.getChildValueAtFirst("domestic");
					String address = (String)people.getChildValueAtFirst("address");
					String email = (String)people.getChildValueAtFirst("email");
					String phone = (String)people.getChildValueAtFirst("phone");
					String cellphone = (String)people.getChildValueAtFirst("cellphone");
					
					/*System.out.println("name:" + name);
					System.out.println("age:" + age);
					System.out.println("sex:" + sex);
					System.out.println("domestic:" + domestic);
					System.out.println("address:" + address);
					System.out.println("email:" + email);
					System.out.println("phone:" + phone);
					System.out.println("cellphone:" + cellphone);*/
				}
				//System.out.println("--------------------------------------------------------");
				
				
				
				
				//build to serialize message;
				byte[] serializedData = parser.build(msg);
				
				System.out.println("serializedData[" + serializedData.length + "]:[" + new String(serializedData) + "]");
				
			}
		}catch(Throwable t){
			t.printStackTrace();
		}
	}

	
	//@Test
	/*public final void testParse() throws ParserException {

		long free = Runtime.getRuntime().freeMemory();
		long total = Runtime.getRuntime().totalMemory();
		long max = Runtime.getRuntime().maxMemory();
		System.out.format("Used  Memory : %6.2f MB%n", (double) (total - free) / (1024 * 1024));
		System.out.format("Total Memory : %6.2f MB%n", (double) total / (1024 * 1024));
		System.out.format("Free  Memory : %6.2f MB%n", (double) free / (1024 * 1024));
		System.out.format("Max   Memory : %6.2f MB%n", (double) max / (1024 * 1024));
		
		
		LinkedHashMap<Object, FieldDefinition> map = new LinkedHashMap<Object, FieldDefinition>();
		MessageSet messageSet = new MessageSet();
		messageSet.setName("people");
		messageSet.setFieldDefinitionMap(map);
		 
		FixedLengthFieldDefinition name = new FixedLengthFieldDefinition();
		name.setName("name");
		name.setLength(10);
		name.setType(FixedLengthFieldDefinition.FIELD_TYPE_STRING);
		map.put("name", name);
		
		FixedLengthFieldDefinition age = new FixedLengthFieldDefinition();
		age.setName("age");
		age.setLength(2);
		age.setType(FixedLengthFieldDefinition.FIELD_TYPE_STR_INTEGER);
		map.put("age", age);
		
		FixedLengthFieldDefinition sex = new FixedLengthFieldDefinition();
		sex.setName("sex");
		sex.setLength(1);
		sex.setType(FixedLengthFieldDefinition.FIELD_TYPE_STRING);
		map.put("sex", sex);
		
		FixedLengthFieldDefinition phone = new FixedLengthFieldDefinition();
		phone.setName("phone");
		phone.setLength(13);
		phone.setType(FixedLengthFieldDefinition.FIELD_TYPE_STRING);
		phone.setRepeatCount(1);
		map.put("phone", phone);
		
		FixedLengthFieldDefinition emailRepeatCount = new FixedLengthFieldDefinition();
		emailRepeatCount.setName("emailRepeatCount");
		emailRepeatCount.setLength(3);
		emailRepeatCount.setType(FixedLengthFieldDefinition.FIELD_TYPE_STR_INTEGER);
		map.put(emailRepeatCount.getName(), emailRepeatCount);
		
		

		FieldSetDefinition email = new FieldSetDefinition();
		email.setName("email");
//		email.setRepeatCount(1);
		email.setRepeatFieldName("emailRepeatCount");
		LinkedHashMap<Object, FieldDefinition> fieldDefinitionMap = new LinkedHashMap<Object, FieldDefinition>();
		email.setFieldDefinitionMap(fieldDefinitionMap);
		
		FixedLengthFieldDefinition email1 = new FixedLengthFieldDefinition();
		email1.setName("email1");
		email1.setLength(20);
		email1.setType(FixedLengthFieldDefinition.FIELD_TYPE_STRING);
		fieldDefinitionMap.put("email1", email1);
		
		FixedLengthFieldDefinition email2 = new FixedLengthFieldDefinition();
		email2.setName("email2");
		email2.setLength(20);
		email2.setType(FixedLengthFieldDefinition.FIELD_TYPE_STRING);
		fieldDefinitionMap.put("email2", email2);
		
		map.put("email", email);

		
		FixedLengthFieldDefinition dataLength = new FixedLengthFieldDefinition();
		dataLength.setName("dataLength");
		dataLength.setLength(2);
		dataLength.setType(FixedLengthFieldDefinition.FIELD_TYPE_STR_INTEGER);
		map.put("dataLength", dataLength);
		
		FixedLengthFieldDefinition dataBytes = new FixedLengthFieldDefinition();
		dataBytes.setName("dataBytes");
		dataBytes.setLengthFieldName("dataLength");
		dataBytes.setType(FixedLengthFieldDefinition.FIELD_TYPE_BINARY);
		map.put("dataBytes", dataBytes);
		
		
		FixedLengthFieldDefinition repeatCount = new FixedLengthFieldDefinition();
		repeatCount.setName("repeatCount");
		repeatCount.setLength(3);
		repeatCount.setType(FixedLengthFieldDefinition.FIELD_TYPE_STR_INTEGER);
		map.put(repeatCount.getName(), repeatCount);
		
		
		FixedLengthFieldDefinition repeatField = new FixedLengthFieldDefinition();
		repeatField.setName("repeatField");
		repeatField.setLength(10);
		repeatField.setRepeatFieldName(repeatCount.getName());
		repeatField.setType(FixedLengthFieldDefinition.FIELD_TYPE_STRING);
		map.put(repeatField.getName(), repeatField);
		
		for(int j = 0  ; j < 100 ; j ++){
			FixedLengthFieldDefinition f = new FixedLengthFieldDefinition();
			f.setName("f" + j);
			f.setLength(10);
			f.setType(FixedLengthFieldDefinition.FIELD_TYPE_STRING);
			map.put(f.getName(), f);
		}
		
		
		long elapsed = System.currentTimeMillis();
		FixedLengthParser parser = new FixedLengthParser(messageSet);
		
		
		
		
		
		
		System.out.println("elapsed1:"+(System.currentTimeMillis() - elapsed));
		try {
			byte[] data 
			= ("whoana123442m010-9113-4270010)9113-4270001whoan1@gmail.com    whoan1@naver.com    whoan2@gmail.com    whoan2@naver.com    101234567890" + 
			"099" + 
					Util.repeat("abcdefghij", 100))
					.getBytes();
			
			elapsed = System.currentTimeMillis();
			

			
			Message msg = null;
			for(int i = 0 ; i < 1 ; i++)
				msg = parser.parse(data);
			
			System.out.println("elapsed:"+(System.currentTimeMillis() - elapsed));
			
			Element ele = (Element) msg.getFixedLengthRootElement().getChildList("people").get(0);
			
			System.out.println("name:" + ele.getChildList("name").get(0));
			
			Element<Integer> ageElement = (Element)ele.getChildList("age").get(0);
			
			System.out.println("age:" + (ageElement.getValue() + 10));
			System.out.println("sex:" + ele.getChildList("sex").get(0));
			System.out.println("phone:" +ele.getChildList("phone").get(0));
			System.out.println("phone:" +ele.getChildList("phone").get(1));
			
			
			List<Element> emailElement = ele.getChildList("email");
			for (Element element : emailElement) {
				System.out.println(element.getChildList("email1").get(0));
				System.out.println(element.getChildList("email2").get(0));
			}
			System.out.println(msg.getFixedLengthElement("people/dataBytes", 0));
			
			
			System.out.println(ele.getChildList("email").get(0).getChildList("email1").get(0));
			System.out.println(ele.getChildList("email").get(0).getChildList("email2").get(0));
			System.out.println(ele.getChildList("email").get(0).getChildList("email1").get(0));
			System.out.println(ele.getChildList("email").get(0).getChildList("email2").get(0));
			
			free = Runtime.getRuntime().freeMemory();
			total = Runtime.getRuntime().totalMemory();
			max = Runtime.getRuntime().maxMemory();

			System.out.format("Used  Memory : %6.2f MB%n", (double) (total - free) / (1024 * 1024));
			System.out.format("Total Memory : %6.2f MB%n", (double) total / (1024 * 1024));
			System.out.format("Free  Memory : %6.2f MB%n", (double) free / (1024 * 1024));
			System.out.format("Max   Memory : %6.2f MB%n", (double) max / (1024 * 1024));
			
			byte [] serializedBytes = build(msg, messageSet);
			 
			System.out.println("origin bytes data to string :" + Util.toString(msg.toBytes()));
			
		} catch (UnsupportedFieldTypeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UndefinedFieldElementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UndefinedFieldValueException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	*/
	 

}
