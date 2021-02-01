package pep.per.mint.common.data.basic.dataset;


import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import pep.per.mint.common.util.Util;

public class DataSetTest {

	@Test
	public void test() {

		//----------------------------------------------------------------------------------
		// 시스템 헤더 영역 
		//----------------------------------------------------------------------------------
		
		DataSet systemHeaderFieldSet = new DataSet();
		systemHeaderFieldSet.setDataSetId("2");
		systemHeaderFieldSet.setName1("SystemHeader");
		systemHeaderFieldSet.setName2("시스템헤더");
		systemHeaderFieldSet.setDataFormat(DataSet.DF_XML);
		systemHeaderFieldSet.setRegId("iip");
		systemHeaderFieldSet.setIsRoot("N");
		systemHeaderFieldSet.setComments("시스템헤더 정의부");
		systemHeaderFieldSet.setLength(100);
		List<DataField> fieldList = new ArrayList<DataField>(); 
		systemHeaderFieldSet.setDataFieldList(fieldList);
		
		DataField f1 = new DataField();
		f1.setDataSetId("2");
		f1.setDataFieldId("1");
		f1.setName1("SND_DATE");
		f1.setName2("송신일시");
		f1.setType(DataField.TYPE_STRING);
		f1.setDefaultValue("{DATE}");
		f1.setLength(20);
		f1.setOffset(0);
		f1.setSeq(0);
		f1.setRegId("iip");
		
		DataField f2 = new DataField();
		f2.setDataSetId("2");
		f2.setDataFieldId("2");
		f2.setName1("GID");
		f2.setName2("글로벌ID");
		f2.setType(DataField.TYPE_STRING);
		f2.setDefaultValue("");
		f2.setLength(10);
		f2.setOffset(20);
		f2.setSeq(1);
		f2.setRegId("iip");
		
		DataField f3 = new DataField();
		f3.setDataSetId("2");
		f3.setDataFieldId("3");
		f3.setName1("SND_SYSTEM");
		f3.setName2("송신시스템");
		f3.setType(DataField.TYPE_STRING);
		f3.setDefaultValue("SEND SYSTEM");
		f3.setLength(10);
		f3.setOffset(30);
		f3.setSeq(2);
		f3.setRegId("iip");
		
		DataField f4 = new DataField();
		f4.setDataSetId("2");
		f4.setDataFieldId("4");
		f4.setName1("TTL");
		f4.setName2("TIME TO LIVE");
		f4.setType(DataField.TYPE_NUMBER);
		f4.setDefaultValue("0000000010");
		f4.setLength(10);
		f4.setOffset(40);
		f4.setSeq(3);
		f4.setPadding("0");
		f4.setJustify(DataField.JUSTIFY_R);
		f4.setRegId("iip");
		
		DataField f5 = new DataField();
		f5.setDataSetId("2");
		f5.setDataFieldId("5");
		f5.setName1("SND_APP");
		f5.setName2("송신APP");
		f5.setType(DataField.TYPE_STRING);
		f5.setDefaultValue("SNDAPP");
		f5.setLength(10);
		f5.setOffset(50);
		f5.setSeq(4);
		f5.setRegId("iip");
		
		DataField f6 = new DataField();
		f6.setDataSetId("2");
		f6.setDataFieldId("6");
		f6.setName1("RCV_APP");
		f6.setName2("응답APP");
		f6.setType(DataField.TYPE_STRING);
		f6.setDefaultValue("RCVAPP");
		f6.setLength(10);
		f6.setOffset(60);
		f6.setSeq(5);
		f6.setRegId("iip");
		
		DataField f7 = new DataField();
		f7.setDataSetId("2");
		f7.setDataFieldId("7");
		f7.setName1("RES_CD");
		f7.setName2("응답코드");
		f7.setType(DataField.TYPE_STRING);
		f7.setDefaultValue("0");
		f7.setLength(10);
		f7.setOffset(70);
		f7.setSeq(6);
		f7.setRegId("iip");
		
		DataField f8 = new DataField();
		f8.setDataSetId("2");
		f8.setDataFieldId("8");
		f8.setName1("RES_MSG");
		f8.setName2("응답메시지");
		f8.setType(DataField.TYPE_STRING);
		f8.setDefaultValue("");
		f8.setLength(20);
		f8.setOffset(80);
		f8.setSeq(7);
		f8.setRegId("iip");
		
		fieldList.add(f1);
		fieldList.add(f2);
		fieldList.add(f3);
		fieldList.add(f4);
		fieldList.add(f5);
		fieldList.add(f6);
		fieldList.add(f7);
		fieldList.add(f8);
	
		System.out.println("system header:\n" + Util.toJSONString(systemHeaderFieldSet));
		
		//----------------------------------------------------------------------------------
		// 사용자 데이터 영역 
		//----------------------------------------------------------------------------------
		
		DataSet userDataFieldSet = new DataSet();
		userDataFieldSet.setDataSetId("3");
		userDataFieldSet.setName1("UserData");
		userDataFieldSet.setName2("사용자데이터");
		userDataFieldSet.setDataFormat(DataSet.DF_XML);
		userDataFieldSet.setRegId("iip");
		userDataFieldSet.setIsRoot("N");
		userDataFieldSet.setComments("사용자데이터 정의부");
		userDataFieldSet.setLength(553);
		List<DataField> userFieldList = new ArrayList<DataField>(); 
		userDataFieldSet.setDataFieldList(userFieldList);
		
		DataField u1 = new DataField();
		u1.setDataSetId("3");
		u1.setDataFieldId("1");
		u1.setName1("LENGTH");
		u1.setName2("길이");
		u1.setType(DataField.TYPE_NUMBER);
		u1.setDefaultValue("0000000000");
		u1.setOffset(0);
		u1.setLength(10);
		u1.setPadding("0");
		u1.setJustify(DataField.JUSTIFY_R);
		u1.setSeq(0);
		u1.setRegId("iip");
		
		DataField u2 = new DataField();
		u2.setDataSetId("3");
		u2.setDataFieldId("2");
		u2.setName1("NAME");
		u2.setName2("이름");
		u2.setType(DataField.TYPE_STRING);
		u2.setDefaultValue("");
		u2.setOffset(10);
		u2.setLength(20);
		u2.setSeq(1);
		u2.setRegId("iip");
		
		DataField u3 = new DataField();
		u3.setDataSetId("3");
		u3.setDataFieldId("3");
		u3.setName1("AGE");
		u3.setName2("나이");
		u3.setType(DataField.TYPE_NUMBER);
		u3.setDefaultValue("0");
		u3.setOffset(20);
		u3.setLength(3);
		u1.setPadding("0");
		u1.setJustify(DataField.JUSTIFY_R);
		u3.setSeq(2);
		u3.setRegId("iip");
		
		 
		
		DataField u4 = new DataField();
		u4.setDataSetId("3");
		u4.setDataFieldId("4");
		u4.setName1("ADDRESS");
		u4.setName2("주소");
		u4.setType(DataField.TYPE_STRING);
		u4.setDefaultValue("");
		u4.setOffset(33);
		u4.setLength(500);
		u4.setSeq(3);
		u4.setRegId("iip");
		
		DataField u5 = new DataField();
		u5.setDataSetId("3");
		u5.setDataFieldId("5");
		u5.setName1("UID");
		u5.setName2("사용자ID");
		u5.setType(DataField.TYPE_STRING);
		u5.setDefaultValue("");
		u5.setOffset(533);
		u5.setLength(20);
		u5.setSeq(4);
		u5.setRegId("iip");
		
		 
		
		userFieldList.add(u1);
		userFieldList.add(u2);
		userFieldList.add(u3);
		userFieldList.add(u4);
		userFieldList.add(u5); 
	
		System.out.println("user data:\n" + Util.toJSONString(userDataFieldSet));
		
		
		//----------------------------------------------------------------------------------
		// 표준전문 구성 
		//----------------------------------------------------------------------------------
		
		
		DataSet standDataSet = new DataSet();
		standDataSet.setDataSetId("1");
		standDataSet.setName1("STDMSGV1.0");
		standDataSet.setName2("표준전문V1.0");
		standDataSet.setDataFormat(DataSet.DF_XML);
		standDataSet.setLength(systemHeaderFieldSet.getLength() + userDataFieldSet.getLength());
		standDataSet.setComments("표준전문 구조 등록");
		standDataSet.setRegId("iip");
		List dataFieldList = new ArrayList();
		
		standDataSet.setDataFieldList(dataFieldList);
		
		DataField systemHeader = new DataField();
		systemHeader.setDataSetId("1");
		systemHeader.setDataFieldId("1");
		systemHeader.setType(DataField.TYPE_COMPLEX);
		systemHeader.setFieldSetId(systemHeaderFieldSet.getDataSetId());
		systemHeader.setName1(systemHeaderFieldSet.getName1());
		systemHeader.setName2(systemHeaderFieldSet.getName2());
		systemHeader.setOffset(0);
		systemHeader.setLength(systemHeaderFieldSet.getLength());
		systemHeader.setSeq(0);
		systemHeader.setRegId("iip");
		
		
		DataField userData = new DataField();
		userData.setDataSetId("1");
		userData.setDataFieldId("2");
		userData.setType(DataField.TYPE_COMPLEX);
		userData.setFieldSetId(userDataFieldSet.getDataSetId());
		userData.setName1(userDataFieldSet.getName1());
		userData.setName2(userDataFieldSet.getName2());
		userData.setOffset(systemHeaderFieldSet.getLength());
		userData.setLength(userDataFieldSet.getLength());
		userData.setSeq(1);
		userData.setRegId("iip");
		
		dataFieldList.add(systemHeader);
		
		dataFieldList.add(userData);
		
		System.out.println("standard data:\n" + Util.toJSONString(standDataSet));
		
		
	}

}
