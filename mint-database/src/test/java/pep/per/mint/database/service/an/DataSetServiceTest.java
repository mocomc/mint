package pep.per.mint.database.service.an;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fasterxml.jackson.annotation.JsonTypeName;

import pep.per.mint.common.data.basic.dataset.DataField;
import pep.per.mint.common.data.basic.dataset.DataSet;
import pep.per.mint.common.data.basic.dataset.DataSetList;
import pep.per.mint.common.data.basic.dataset.DataSetTemp;
import pep.per.mint.common.data.basic.dataset.MetaField;
import pep.per.mint.common.util.Util;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
		//"file:./src/test/resources/com/mocomsys/mint/database/config/database-context-test.xml"})
		"classpath:/config/database-context.xml"})
		//"file:./src/test/resources/config/database-context-test.xml"})
public class DataSetServiceTest {

	@Autowired
	DataSetService dataSetService;
	
	
	
	@Test
	public void testCreateDataSet() throws Throwable {
 
		//DataSet standardDataSet = (DataSet) Util.readObjectFromJson(new File("src/test/data/an/standardDataSet.json"), DataSet.class, "UTF-8");
		//dataSetService.createDataSet(standardDataSet);
	
		//----------------------------------------------------------------------------------
		// 시스템 헤더 영역 
		//----------------------------------------------------------------------------------
		
		DataSet systemHeaderFieldSet = new DataSet();
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
		f1.setName1("SND_DATE");
		f1.setName2("송신일시");
		f1.setType(DataField.TYPE_STRING);
		f1.setDefaultValue("{DATE}");
		f1.setLength(20);
		f1.setOffset(0);
		f1.setSeq(0);
		f1.setRegId("iip");
		
		DataField f2 = new DataField();
		f2.setName1("GID");
		f2.setName2("글로벌ID");
		f2.setType(DataField.TYPE_STRING);
		f2.setDefaultValue("");
		f2.setLength(10);
		f2.setOffset(20);
		f2.setSeq(1);
		f2.setRegId("iip");
		
		DataField f3 = new DataField();
		f3.setName1("SND_SYSTEM");
		f3.setName2("송신시스템");
		f3.setType(DataField.TYPE_STRING);
		f3.setDefaultValue("SEND SYSTEM");
		f3.setLength(10);
		f3.setOffset(30);
		f3.setSeq(2);
		f3.setRegId("iip");
		
		DataField f4 = new DataField();
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
		f5.setName1("SND_APP");
		f5.setName2("송신APP");
		f5.setType(DataField.TYPE_STRING);
		f5.setDefaultValue("SNDAPP");
		f5.setLength(10);
		f5.setOffset(50);
		f5.setSeq(4);
		f5.setRegId("iip");
		
		DataField f6 = new DataField();
		f6.setName1("RCV_APP");
		f6.setName2("응답APP");
		f6.setType(DataField.TYPE_STRING);
		f6.setDefaultValue("RCVAPP");
		f6.setLength(10);
		f6.setOffset(60);
		f6.setSeq(5);
		f6.setRegId("iip");
		
		DataField f7 = new DataField();
		f7.setName1("RES_CD");
		f7.setName2("응답코드");
		f7.setType(DataField.TYPE_STRING);
		f7.setDefaultValue("0");
		f7.setLength(10);
		f7.setOffset(70);
		f7.setSeq(6);
		f7.setRegId("iip");
		
		DataField f8 = new DataField();
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
	
		dataSetService.createDataSetWithHistory(systemHeaderFieldSet);
		
		System.out.println("system header:\n" + Util.toJSONString(systemHeaderFieldSet));
				
		//----------------------------------------------------------------------------------
		// 사용자 데이터 영역 
		//----------------------------------------------------------------------------------
		
		DataSet userDataFieldSet = new DataSet();
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
		u2.setName1("NAME");
		u2.setName2("이름");
		u2.setType(DataField.TYPE_STRING);
		u2.setDefaultValue("");
		u2.setOffset(10);
		u2.setLength(20);
		u2.setSeq(1);
		u2.setRegId("iip");
		
		DataField u3 = new DataField();
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
		u4.setName1("ADDRESS");
		u4.setName2("주소");
		u4.setType(DataField.TYPE_STRING);
		u4.setDefaultValue("");
		u4.setOffset(33);
		u4.setLength(500);
		u4.setSeq(3);
		u4.setRegId("iip");
		
		DataField u5 = new DataField();
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
			
		dataSetService.createDataSetWithHistory(userDataFieldSet);
		
		System.out.println("user data:\n" + Util.toJSONString(userDataFieldSet));


		//----------------------------------------------------------------------------------
		// 표준전문 구성 
		//----------------------------------------------------------------------------------
		DataSet standDataSet = new DataSet();
		standDataSet.setName1("STDMSGV1.0");
		standDataSet.setName2("표준전문V1.0");
		standDataSet.setDataFormat(DataSet.DF_XML);
		standDataSet.setLength(systemHeaderFieldSet.getLength() + userDataFieldSet.getLength());
		standDataSet.setComments("표준전문 구조 등록");
		standDataSet.setRegId("iip");
		List<DataField> dataFieldList = new ArrayList<DataField>();
		
		standDataSet.setDataFieldList(dataFieldList);
		
		DataField sh = new DataField();
		sh.setType(DataField.TYPE_COMPLEX);
		sh.setFieldSetId(systemHeaderFieldSet.getDataSetId());
		sh.setName1(systemHeaderFieldSet.getName1());
		sh.setName2(systemHeaderFieldSet.getName2());
		sh.setOffset(0);
		sh.setLength(systemHeaderFieldSet.getLength());
		sh.setSeq(0);
		sh.setRegId("iip");
		
		
		DataField ud = new DataField();
		ud.setType(DataField.TYPE_COMPLEX);
		ud.setFieldSetId(userDataFieldSet.getDataSetId());
		ud.setName1(userDataFieldSet.getName1());
		ud.setName2(userDataFieldSet.getName2());
		ud.setOffset(systemHeaderFieldSet.getLength());
		ud.setLength(userDataFieldSet.getLength());
		ud.setSeq(1);
		ud.setRegId("iip");
		
		dataFieldList.add(sh);
		
		dataFieldList.add(ud);
		
		dataSetService.createDataSetWithHistory(standDataSet);
		System.out.println("standard data:\n" + Util.toJSONString(standDataSet));
		
				
	}
	
	@Test
	public void testGetDataSetList() throws Throwable {
		
		Map<String, String> params = new HashMap<String, String>();
		
		DataSetList list = dataSetService.getDataSetList(params);
		System.out.println(Util.toJSONString(list));
	}
	
	@Test
	public void testGetDataSet() throws Throwable {
		try{
			DataSet dataSet = dataSetService.getDataSet("1",null);
			System.out.println(Util.toJSONString(dataSet));
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Test
	public void testModifyDataSet() throws Exception{
		
		DataSet dataSet = dataSetService.getDataSet("3", null);
		String name1 = dataSet.getName1();
		System.out.println("name1 before changing:" + name1);
		String changedName1 = "STDMSGV-changed-4.0";
		dataSet.setName1(changedName1);
		dataSet.setModDate(Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI));
		dataSet.setModId("iip");
		int res = dataSetService.modifyDataSetWithHistory(dataSet);
		System.out.println("name1 changed:" + (res == 1 ? "success" : "fail"));
		dataSet = dataSetService.getDataSet("3", null);
		System.out.println("name1 after changing:" + dataSet.getName1());
		
	}
	
	
	@Test
	public void testGetCompareDataSet() throws Exception{
		List<String> params = Arrays.asList("1","2","3");
		Map<String, DataSet> res = dataSetService.getCompareDataSet(params);
		System.out.println(Util.toJSONString(res));
	}
	
	
	@Test
	public void testGetMetaFieldList() throws Exception{
		Map<String, String> params = new HashMap<String,String>();		
		List<MetaField> list = dataSetService.getMetaFieldList(params);
		System.out.println(Util.toJSONString(list));
	}
	
	
	@Test
	public void testApprovalDataSet() throws Throwable{
		//int res = dataSetService.approvalDataSetWithHistory("1", "iip", Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI));
		//int res = dataSetService.approvalDataSetWithHistory("2", "iip", Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI));
		int res = dataSetService.approvalDataSetWithHistory("3", "iip", Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI));
		    
		assertEquals(1,res);
	}
	
	
	@Test
	public void testDeleteDataSet() throws Exception{
		int res = dataSetService.deleteDataSetWithHistory("3", Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI), "iip");
		System.out.println("name1 changed:" + (res == 1 ? "success" : "fail"));
	}
	
	@Test
	public void testSaveTemporary() throws Throwable {
 
		DataSet systemHeaderFieldSet = new DataSet();
		systemHeaderFieldSet.setName1("SystemHeader2");
		systemHeaderFieldSet.setName2("시스템헤더2");
		systemHeaderFieldSet.setDataFormat(DataSet.DF_XML);
		systemHeaderFieldSet.setRegId("iip");
		systemHeaderFieldSet.setIsRoot("N");
		systemHeaderFieldSet.setComments("시스템헤더 정의부");
		systemHeaderFieldSet.setLength(100);
		List<DataField> fieldList = new ArrayList<DataField>(); 
		systemHeaderFieldSet.setDataFieldList(fieldList);
		
		DataField f1 = new DataField();
		f1.setName1("SND_DATE");
		f1.setName2("송신일시");
		f1.setType(DataField.TYPE_STRING);
		f1.setDefaultValue("{DATE}");
		f1.setLength(20);
		f1.setOffset(0);
		f1.setSeq(0);
		f1.setRegId("iip");
		
		DataField f2 = new DataField();
		f2.setName1("GID");
		f2.setName2("글로벌ID");
		f2.setType(DataField.TYPE_STRING);
		f2.setDefaultValue("");
		f2.setLength(10);
		f2.setOffset(20);
		f2.setSeq(1);
		f2.setRegId("iip");
		
		DataField f3 = new DataField();
		f3.setName1("SND_SYSTEM");
		f3.setName2("송신시스템");
		f3.setType(DataField.TYPE_STRING);
		f3.setDefaultValue("SEND SYSTEM");
		f3.setLength(10);
		f3.setOffset(30);
		f3.setSeq(2);
		f3.setRegId("iip");
		
		DataField f4 = new DataField();
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
		f5.setName1("SND_APP");
		f5.setName2("송신APP");
		f5.setType(DataField.TYPE_STRING);
		f5.setDefaultValue("SNDAPP");
		f5.setLength(10);
		f5.setOffset(50);
		f5.setSeq(4);
		f5.setRegId("iip");
		
		DataField f6 = new DataField();
		f6.setName1("RCV_APP");
		f6.setName2("응답APP");
		f6.setType(DataField.TYPE_STRING);
		f6.setDefaultValue("RCVAPP");
		f6.setLength(10);
		f6.setOffset(60);
		f6.setSeq(5);
		f6.setRegId("iip");
		
		DataField f7 = new DataField();
		f7.setName1("RES_CD");
		f7.setName2("응답코드");
		f7.setType(DataField.TYPE_STRING);
		f7.setDefaultValue("0");
		f7.setLength(10);
		f7.setOffset(70);
		f7.setSeq(6);
		f7.setRegId("iip");
		
		DataField f8 = new DataField();
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
	
		DataSetTemp dataSetTemp = new DataSetTemp();
		dataSetTemp.setContents(Util.toJSONString(systemHeaderFieldSet));
		dataSetTemp.setRegId("iip");
		dataSetTemp.setUserId("iip");
		dataSetService.saveTemporary(dataSetTemp);
		DataSet dataSet = dataSetService.getLastTempSavedDataSet("iip");
		System.out.println(Util.toJSONString(dataSet));
	}
	 
	@Test
	public void testGetLastTempSavedDataSet() throws Throwable {
		DataSet dataSet = dataSetService.getLastTempSavedDataSet("iip");
		System.out.println(Util.toJSONString(dataSet));
	}
	
	@Test
	public void testGetDataSetVersionList() throws Exception{
		List<String> versions = dataSetService.getDataSetVersionList("3");
		System.out.println(Util.toJSONString(versions));
	}
	
	@Test
	public void testGetDataSetByVersion() throws Exception{
		DataSet dataSet = dataSetService.getDataSetByVersion("3", "1");
		System.out.println(Util.toJSONString(dataSet));
	}
	
	@Test 
	public void testDataSetMapByVersion() throws Exception{
		Map<String, DataSet> map = dataSetService.getDataSetMapByVersion("3");
		System.out.println(Util.toJSONString(map));
	}
	
}
