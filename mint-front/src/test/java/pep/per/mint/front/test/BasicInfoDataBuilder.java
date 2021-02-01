/**
 * Copyright 2013 ~ 2015 Mocomsys's guys(Minhwoa Bak, Sanghoon Lim, Deahun Ham, dhkim, Solution TF), Inc.  All Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 * [주의!]
 * 본 코드가 하자없이 완벽할거라 믿었다간 엄청난 욕을 먹고 그로 인한 스트레스로 병들거나 심하면 살기 싫어질 수도 있으니
 * 부디 살얼음판을 걷듯이 주의하여 사용하기 바란다.
 * 사용상 받을지 모를 스트레스 및 기타 피해에 대한 책임은 사용자 본인에게 있음을 명시한다. 부디 행운을 빈다.
 * Please Don't contact Mocomsys, Inc., NURITKUM SQUARE R&D TOWER, 11F DMC 1605,
 * Sangam-Dong, Mapo-Gu, Seoul, 121-7105 Korea or visit mocomsys.com
 * if you need additional information or have any questions.
 */
package pep.per.mint.front.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pep.per.mint.common.data.basic.App;
import pep.per.mint.common.data.basic.BasicInfo;
import pep.per.mint.common.data.basic.Business;
import pep.per.mint.common.data.basic.Channel;
import pep.per.mint.common.data.basic.CommonCode;
import pep.per.mint.common.data.basic.Interface;
import pep.per.mint.common.data.basic.InterfaceTag;
import pep.per.mint.common.data.basic.Menu;
import pep.per.mint.common.data.basic.Requirement;
import pep.per.mint.common.data.basic.User;
import pep.per.mint.common.data.basic.System;
import pep.per.mint.common.util.Util;

/**
 * @author Solution TF
 *
 */
public class BasicInfoDataBuilder {

	private static final Logger logger = LoggerFactory.getLogger(BasicInfoDataBuilder.class);

	@Test
	public void build(){

		BasicInfo basicInfo = new BasicInfo();

		int addCnt = 1;

		//------------------------------------------
		//메뉴리스트
		//------------------------------------------
		List<Menu> menuList = new ArrayList<Menu>();
		//메뉴1 : 라이프사이클 관리
		Menu menu1 = new Menu();
		menu1.setId("1");
		menu1.setMenuId("1");
		menu1.setMenuNm("라이프사이클 관리");
		menuList.add(menu1);

		//메뉴2 : 요 건관리
		Menu menu2 = new Menu();
		menu2.setId("2");
		menu2.setMenuId("2");
		menu2.setMenuNm("요건 관리");
		menuList.add(menu2);

		//메뉴3 : 인터페이스 관리
		Menu menu3 = new Menu();
		menu3.setId("3");
		menu3.setMenuId("3");
		menu3.setMenuNm("인터페이스 관리");
		menuList.add(menu3);


		//메뉴4 : 운영 관리
		Menu menu4 = new Menu();
		menu4.setId("4");
		menu4.setMenuId("4");
		menu4.setMenuNm("운영 관리");
		menuList.add(menu4);

		//메뉴5 : 인프라
		Menu menu5 = new Menu();
		menu5.setId("5");
		menu5.setMenuId("5");
		menu5.setMenuNm("인프라");
		menuList.add(menu5);

		//메뉴6 : 모니터링
		Menu menu6 = new Menu();
		menu6.setMenuId("6");
		menu6.setMenuNm("모니터링");
		menuList.add(menu6);

		//메뉴7 : Data Tracking
		Menu menu7 = new Menu();
		menu7.setMenuId("7");
		menu7.setMenuNm("Data Tracking");
		menuList.add(menu7);

		//메뉴8 : 대시보드
		Menu menu8 = new Menu();
		menu8.setMenuId("8");
		menu8.setMenuNm("대시보드");
		menuList.add(menu8);

		//메뉴10 :  자원모니터링
		Menu menu10 = new Menu();
		menu10.setMenuId("10");
		menu10.setMenuNm("자원 모니터링");
		menuList.add(menu10);

		//메뉴11 : 장애 관리
		Menu menu11 = new Menu();
		menu11.setMenuId("11");
		menu11.setMenuNm("장애 관리");
		menuList.add(menu11);


		//메뉴12 : 통계/KPI
		Menu menu12 = new Menu();
		menu12.setMenuId("12");
		menu12.setMenuNm("통계/KPI");
		menuList.add(menu12);

		basicInfo.setMenuList(menuList);

		//------------------------------------------
		//사용자 세팅
		//------------------------------------------
		User user = new User();
		user.setId("iip");
		user.setPassword("****");
		user.setUserNm("iip admin");
		basicInfo.setUser(user);


		//------------------------------------------
		//어플리케이션세팅
		//------------------------------------------
		//요건관리-어플리케이션-요건조회
		App app1 = new App();
		app1.setAppId("app1");
		app1.setAppNm("요건조회");
		app1.setAppUri("/view/sub-an/AN-01-00-001");
		app1.setCmdString("C,R,U,D,P");

		//요건관리-어플리케이션-요건등록
		App app2 = new App();
		app2.setAppId("app2");
		app2.setAppNm("요건등록");
		app2.setAppUri("/view/sub-an/AN-01-00-002");
		app2.setCmdString("C,R,U,D,P");

		//요건관리-어플리케이션-요건 히스토리
		App app3 = new App();
		app3.setAppId("app3");
		app3.setAppNm("요건 히스토리");
		app3.setAppUri("/view/sub-an/AN-01-00-003");
		app3.setCmdString("C,R,U,D,P");

		List<App> appList1 = new ArrayList<App>();
		appList1.add(app1);
		appList1.add(app2);
		appList1.add(app3);
		//menu2.setAppList(appList1);


		//인터페이스관리-어플리케이션-인터페이스조회
		App app11 = new App();
		app11.setAppId("app11");
		app11.setAppNm("인터페이스 조회");
		app11.setAppUri("/view/sub-an/AN-02-00-001");
		app11.setCmdString("C,R,U,D,P");

		//인터페이스관리-어플리케이션-인터페이스등록
		App app12 = new App();
		app12.setAppId("app12");
		app12.setAppNm("인터페이스 등록");
		app12.setAppUri("/view/sub-an/AN-02-00-002");
		app12.setCmdString("C,R,U,D,P");

		//인터페이스관리-어플리케이션-인터페이스 히스토리
		App app13 = new App();
		app13.setAppId("app13");
		app13.setAppNm("인터페이스 히스토리");
		app13.setAppUri("/view/sub-an/AN-02-00-003");
		app13.setCmdString("C,R,U,D,P");

		List<App> appList11 = new ArrayList<App>();
		appList11.add(app11);
		appList11.add(app12);
		appList11.add(app13);
		//menu3.setAppList(appList11);



		//인프라-어플리케이션-시스템(그룹)
		App app21 = new App();
		app21.setAppId("app21");
		app21.setAppNm("시스템(그룹)");
		app21.setAppUri("/view/sub-im/IM-01-01-001");
		app21.setCmdString("C,R,U,D,P");

		//인프라-어플리케이션-서버
		App app22 = new App();
		app22.setAppId("app22");
		app22.setAppNm("서버");
		app22.setAppUri("/view/sub-im/IM-01-02-001");
		app22.setCmdString("C,R,U,D,P");

		//인프라-어플리케이션-업무(그룹)
		App app23 = new App();
		app23.setAppId("app23");
		app23.setAppNm("업무(그룹)");
		app23.setAppUri("/view/sub-im/IM-01-03-001");
		app23.setCmdString("C,R,U,D,P");

		List<App> appList21 = new ArrayList<App>();
		appList21.add(app21);
		appList21.add(app22);
		appList21.add(app23);
		//menu5.setAppList(appList21);

		//Data Tracking-어플리케이션-Data Tracking
		App app31 = new App();
		app31.setAppId("app31");
		app31.setAppNm("Data Tracking");
		app31.setAppUri("/view/sub-op/OP-01-01-001");
		app31.setCmdString("C,R,U,D,P");

		List<App> appList31 = new ArrayList<App>();
		appList31.add(app31);
		//menu7.setAppList(appList31);



		//businessList
		List<Business> businessList = new ArrayList<Business>();
		addCnt = 1;
		for(int i = 0 ; i < addCnt; i ++){
			/*Business bz01 = new Business();
			bz01.setBusinessId("BIZ000000001");
			bz01.setBusinessCd("TE");
			bz01.setBusinessNm("TEST");
			businessList.add(bz01);
			Business bz02 = new Business();
			bz02.setBusinessId("BIZ000000002");
			bz02.setBusinessCd("SLM");
			bz02.setBusinessNm("솔멘");
			businessList.add(bz02);*/
			Business bz03 = new Business();
			bz03.setBusinessId("BIZ000000003");
			bz03.setBusinessCd("ERP-LMS");
			bz03.setBusinessNm("물류");
			businessList.add(bz03);
			Business bz04 = new Business();
			bz04.setBusinessId("BIZ000000004");
			bz04.setBusinessCd("GHUB");
			bz04.setBusinessNm("첼로");
			businessList.add(bz04);
			Business bz05 = new Business();
			bz05.setBusinessId("BIZ000000005");
			bz05.setBusinessCd("ERP-SD");
			bz05.setBusinessNm("영업");
			businessList.add(bz05);
			Business bz06 = new Business();
			bz06.setBusinessId("BIZ000000006");
			bz06.setBusinessCd("ERP-MM");
			bz06.setBusinessNm("구매");
			businessList.add(bz06);
			Business bz07 = new Business();
			bz07.setBusinessId("BIZ000000007");
			bz07.setBusinessCd("ERP-CO");
			bz07.setBusinessNm("원가");
			businessList.add(bz07);
			/*Business bz08 = new Business();
			bz08.setBusinessId("BIZ000000008");
			bz08.setBusinessCd("ENG");
			bz08.setBusinessNm("에너지시스템");
			businessList.add(bz08);*/
			Business bz09 = new Business();
			bz09.setBusinessId("BIZ000000009");
			bz09.setBusinessCd("ERP-FI");
			bz09.setBusinessNm("재무");
			businessList.add(bz09);
/*			Business bz10 = new Business();
			bz10.setBusinessId("BIZ000000010");
			bz10.setBusinessCd("WMS");
			bz10.setBusinessNm("WMS");
			businessList.add(bz10);*/
	/*		Business bz11 = new Business();
			bz11.setBusinessId("BIZ000000011");
			bz11.setBusinessCd("IDOC");
			bz11.setBusinessNm("IDOC");
			businessList.add(bz11);*/
			Business bz12 = new Business();
			bz12.setBusinessId("BIZ000000012");
			bz12.setBusinessCd("MOB");
			bz12.setBusinessNm("모바일시스템");
			businessList.add(bz12);
			Business bz13 = new Business();
			bz13.setBusinessId("BIZ000000013");
			bz13.setBusinessCd("ELF");
			bz13.setBusinessNm("통제물류");
			businessList.add(bz13);
/*			Business bz14 = new Business();
			bz14.setBusinessId("BIZ000000061");
			bz14.setBusinessCd("BI");
			bz14.setBusinessNm("BI");
			businessList.add(bz14);*/
			Business bz15 = new Business();
			bz15.setBusinessId("BIZ000000062");
			bz15.setBusinessCd("EP");
			bz15.setBusinessNm("업무포탈");
			businessList.add(bz15);
			Business bz16 = new Business();
			bz16.setBusinessId("BIZ000000063");
			bz16.setBusinessCd("SD");
			bz16.setBusinessNm("영업관리(SD)");
			businessList.add(bz16);
			Business bz17 = new Business();
			bz17.setBusinessId("BIZ000000064");
			bz17.setBusinessCd("QM");
			bz17.setBusinessNm("품질관리(QM)");
			businessList.add(bz17);
			Business bz18 = new Business();
			bz18.setBusinessId("BIZ000000065");
			bz18.setBusinessCd("PP");
			bz18.setBusinessNm("생산관리(PP)");
			businessList.add(bz18);
			Business bz19 = new Business();
			bz19.setBusinessId("BIZ000000066");
			bz19.setBusinessCd("MM");
			bz19.setBusinessNm("구매관리(MM)");
			businessList.add(bz19);
			Business bz20 = new Business();
			bz20.setBusinessId("BIZ000000067");
			bz20.setBusinessCd("LE");
			bz20.setBusinessNm("물류관리(LE)");
			businessList.add(bz20);
			Business bz21 = new Business();
			bz21.setBusinessId("BIZ000000068");
			bz21.setBusinessCd("FI");
			bz21.setBusinessNm("재무회계(FI)");
			businessList.add(bz21);
			Business bz22 = new Business();
			bz22.setBusinessId("BIZ000000069");
			bz22.setBusinessCd("CO");
			bz22.setBusinessNm("관리회계(CO)");
			businessList.add(bz22);
			Business bz23 = new Business();
			bz23.setBusinessId("BIZ000000070");
			bz23.setBusinessCd("TR");
			bz23.setBusinessNm("자금회계(TR)");
			businessList.add(bz23);
			Business bz24 = new Business();
			bz24.setBusinessId("BIZ000000071");
			bz24.setBusinessCd("EIS");
			bz24.setBusinessNm("Global-EIS");
			businessList.add(bz24);
			Business bz25 = new Business();
			bz25.setBusinessId("BIZ000000072");
			bz25.setBusinessCd("ACT");
			bz25.setBusinessNm("부문별 Action");
			businessList.add(bz25);
			Business bz26 = new Business();
			bz26.setBusinessId("BIZ000000073");
			bz26.setBusinessCd("TCE");
			bz26.setBusinessNm("팀센터(기구설계)");
			businessList.add(bz26);
		}

		basicInfo.setBusinessCdList(businessList);

		//requirementList
		List<Requirement> requirementList = new ArrayList<Requirement>();
		addCnt = 1;
		for(int i = 0 ; i < addCnt; i ++){
			Requirement rq01 = new Requirement();
			rq01.setRequirementId("REQ_PO_0001");
			rq01.setRequirementNm("싱글메일");
			rq01.setStatus("0");
			rq01.setStatusNm("등록");
			requirementList.add(rq01);
			Requirement rq02 = new Requirement();
			rq02.setRequirementId("REQ_PO_0002");
			rq02.setRequirementNm("결재상신");
			rq02.setStatus("0");
			rq02.setStatusNm("등록");
			requirementList.add(rq02);
			Requirement rq03 = new Requirement();
			rq03.setRequirementId("REQ_PO_0003");
			rq03.setRequirementNm("결재상황조회");
			rq03.setStatus("0");
			rq03.setStatusNm("등록");
			requirementList.add(rq03);
			Requirement rq04 = new Requirement();
			rq04.setRequirementId("REQ_PO_0004");
			rq04.setRequirementNm("진행상태전송");
			rq04.setStatus("0");
			rq04.setStatusNm("등록");
			requirementList.add(rq04);
			Requirement rq05 = new Requirement();
			rq05.setRequirementId("REQ_PO_0005");
			rq05.setRequirementNm("변경ID 수신에 대한 회신(reflect)");
			rq05.setStatus("0");
			rq05.setStatusNm("등록");
			requirementList.add(rq05);
			Requirement rq06 = new Requirement();
			rq06.setRequirementId("REQ_PO_0006");
			rq06.setRequirementNm("변경요청 조회");
			rq06.setStatus("0");
			rq06.setStatusNm("등록");
			requirementList.add(rq06);
			Requirement rq07 = new Requirement();
			rq07.setRequirementId("REQ_PO_0007");
			rq07.setRequirementNm("변경ID 상세조회");
			rq07.setStatus("0");
			rq07.setStatusNm("등록");
			requirementList.add(rq07);
			Requirement rq08 = new Requirement();
			rq08.setRequirementId("REQ_PO_0008");
			rq08.setRequirementNm("G-HUB로 보내주는 ACK Answer");
			rq08.setStatus("0");
			rq08.setStatusNm("등록");
			requirementList.add(rq08);
			Requirement rq09 = new Requirement();
			rq09.setRequirementId("REQ_PO_0009");
			rq09.setRequirementNm("ERP로 보내주는 ACK Answer");
			rq09.setStatus("0");
			rq09.setStatusNm("등록");
			requirementList.add(rq09);
			Requirement rq10 = new Requirement();
			rq10.setRequirementId("REQ_PO_0010");
			rq10.setRequirementNm("G-HUB로 보내주는 ACK Answer");
			rq10.setStatus("0");
			rq10.setStatusNm("등록");
			requirementList.add(rq10);
			Requirement rq11 = new Requirement();
			rq11.setRequirementId("REQ_PO_0011");
			rq11.setRequirementNm("ERP로 보내주는 ACK Answer");
			rq11.setStatus("0");
			rq11.setStatusNm("등록");
			requirementList.add(rq11);
			Requirement rq12 = new Requirement();
			rq12.setRequirementId("REQ_PO_0012");
			rq12.setRequirementNm("BPO ERP로 보내주는 ACK Answer");
			rq12.setStatus("0");
			rq12.setStatusNm("등록");
			requirementList.add(rq12);
			Requirement rq13 = new Requirement();
			rq13.setRequirementId("REQ_DIMS_0001");
			rq13.setRequirementNm("고객 수신");
			rq13.setStatus("0");
			rq13.setStatusNm("등록");
			requirementList.add(rq13);
			Requirement rq14 = new Requirement();
			rq14.setRequirementId("REQ_DIMS_0002");
			rq14.setRequirementNm("샘플 요청 (LCR, DM)");
			rq14.setStatus("0");
			rq14.setStatusNm("등록");
			requirementList.add(rq14);
			Requirement rq15 = new Requirement();
			rq15.setRequirementId("REQ_DIMS_0003");
			rq15.setRequirementNm("샘플 요청 현황 수신 (LCR, DM)");
			rq15.setStatus("0");
			rq15.setStatusNm("등록");
			requirementList.add(rq15);
			Requirement rq16 = new Requirement();
			rq16.setRequirementId("REQ_DIMS_0004");
			rq16.setRequirementNm("개발과제 발의 (LCR, DM)");
			rq16.setStatus("0");
			rq16.setStatusNm("등록");
			requirementList.add(rq16);
			Requirement rq17 = new Requirement();
			rq17.setRequirementId("REQ_DIMS_0005");
			rq17.setRequirementNm("개발과제 진행정보 수신 (LCR, DM)");
			rq17.setStatus("0");
			rq17.setStatusNm("등록");
			requirementList.add(rq17);
			Requirement rq18 = new Requirement();
			rq18.setRequirementId("REQ_DIMS_0006");
			rq18.setRequirementNm("샘플반출 현황 수신 (LCR, DM)");
			rq18.setStatus("0");
			rq18.setStatusNm("등록");
			requirementList.add(rq18);
			Requirement rq19 = new Requirement();
			rq19.setRequirementId("REQ_DIMS_0007");
			rq19.setRequirementNm("샘플요청 현황 수신 (ACI)");
			rq19.setStatus("0");
			rq19.setStatusNm("등록");
			requirementList.add(rq19);
			Requirement rq20 = new Requirement();
			rq20.setRequirementId("REQ_DIMS_0008");
			rq20.setRequirementNm("매출계획(가격/물량/일정) ");
			rq20.setStatus("0");
			rq20.setStatusNm("등록");
			requirementList.add(rq20);
		}
		basicInfo.setRequirementCdList(requirementList);

		List<Interface> interfaceCdList = new ArrayList<Interface>();
		addCnt = 1;
		for(int i = 0 ; i < addCnt; i ++){
			Interface intf01 = new Interface();
			intf01.setInterfaceId("A0CM0001_SO");
			intf01.setInterfaceNm("싱글메일");
			interfaceCdList.add(intf01);
			Interface intf02 = new Interface();
			intf02.setInterfaceId("A0CM0003_SO");
			intf02.setInterfaceNm("결재상신");
			interfaceCdList.add(intf02);
			Interface intf03 = new Interface();
			intf03.setInterfaceId("A0CM0004_SO");
			intf03.setInterfaceNm("결재상황조회");
			interfaceCdList.add(intf03);
			Interface intf04 = new Interface();
			intf04.setInterfaceId("A0CM1006_SO");
			intf04.setInterfaceNm("진행상태전송");
			interfaceCdList.add(intf04);
			Interface intf05 = new Interface();
			intf05.setInterfaceId("A0CM1007_AO");
			intf05.setInterfaceNm("변경ID 수신에 대한 회신(reflect)");
			interfaceCdList.add(intf05);
			Interface intf06 = new Interface();
			intf06.setInterfaceId("A0CM1008_SO");
			intf06.setInterfaceNm("변경요청 조회");
			interfaceCdList.add(intf06);
			Interface intf07 = new Interface();
			intf07.setInterfaceId("A0CM1009_SO");
			intf07.setInterfaceNm("변경ID 상세조회");
			interfaceCdList.add(intf07);
			Interface intf08 = new Interface();
			intf08.setInterfaceId("ACKANS1_AO");
			intf08.setInterfaceNm("G-HUB로 보내주는 ACK Answer");
			interfaceCdList.add(intf08);
			Interface intf09 = new Interface();
			intf09.setInterfaceId("ACKANS2_AO");
			intf09.setInterfaceNm("ERP로 보내주는 ACK Answer");
			interfaceCdList.add(intf09);
			Interface intf10 = new Interface();
			intf10.setInterfaceId("ACKANS3_AO");
			intf10.setInterfaceNm("G-HUB로 보내주는 ACK Answer");
			interfaceCdList.add(intf10);
			Interface intf11 = new Interface();
			intf11.setInterfaceId("ACKANS4_AO");
			intf11.setInterfaceNm("ERP로 보내주는 ACK Answer");
			interfaceCdList.add(intf11);
			Interface intf12 = new Interface();
			intf12.setInterfaceId("ACKANS_RECV_AO");
			intf12.setInterfaceNm("BPO ERP로 보내주는 ACK Answer");
			interfaceCdList.add(intf12);
			Interface intf13 = new Interface();
			intf13.setInterfaceId("ISD1R0002");
			intf13.setInterfaceNm("고객 수신");
			interfaceCdList.add(intf13);
			Interface intf14 = new Interface();
			intf14.setInterfaceId("IDIMD0001");
			intf14.setInterfaceNm("샘플 요청 (LCR, DM)");
			interfaceCdList.add(intf14);
			Interface intf15 = new Interface();
			intf15.setInterfaceId("ISPLD0001");
			intf15.setInterfaceNm("샘플 요청 현황 수신 (LCR, DM)");
			interfaceCdList.add(intf15);
			Interface intf16 = new Interface();
			intf16.setInterfaceId("IDIMD0002");
			intf16.setInterfaceNm("개발과제 발의 (LCR, DM)");
			interfaceCdList.add(intf16);
			Interface intf17 = new Interface();
			intf17.setInterfaceId("IPMSD0002");
			intf17.setInterfaceNm("개발과제 진행정보 수신 (LCR, DM)");
			interfaceCdList.add(intf17);
			Interface intf18 = new Interface();
			intf18.setInterfaceId("ISPLD0002");
			intf18.setInterfaceNm("샘플반출 현황 수신 (LCR, DM)");
			interfaceCdList.add(intf18);
			Interface intf19 = new Interface();
			intf19.setInterfaceId("IASMD0001");
			intf19.setInterfaceNm("샘플요청 현황 수신 (ACI)");
			interfaceCdList.add(intf19);
			Interface intf20 = new Interface();
			intf20.setInterfaceId("IDIMD0004");
			intf20.setInterfaceNm("매출계획(가격/물량/일정) ");
			interfaceCdList.add(intf20);
		}
		basicInfo.setInterfaceCdList(interfaceCdList);




		//서비스 리스트
		String[] services = {
			"GHUB_PRD01.jsp", "ECC_GSP01.RFC", "A0130_P01.PROC", "HKS_PRD01.c",
			"GHUB_PRD02.jsp", "ECC_GSP02.RFC", "A0230_P02.PROC", "HKS_PRD02.c",
			"GHUB_PRD03.jsp", "ECC_GSP03.RFC", "A0330_P03.PROC", "HKS_PRD03.c",
			"GHUB_PRD04.jsp", "ECC_GSP04.RFC", "A0430_P04.PROC", "HKS_PRD04.c"
		};
		List<String> serviceList = Arrays.asList(services);
		basicInfo.setServiceList(serviceList);


		//시스템 코드 리스트
		List<System> systemCdList = new ArrayList<System>();
		addCnt = 1;
		for(int i = 0 ; i < addCnt ; i ++){
			System sys01 = new System();sys01.setSystemCd("A0001_P");sys01.setSystemNm("마이싱글1");sys01.setSystemId("SYS000000001");systemCdList.add(sys01);
			System sys02 = new System();sys02.setSystemCd("A0130_P");sys02.setSystemNm("ITSM");sys02.setSystemId("SYS000000002");systemCdList.add(sys02);
			System sys03 = new System();sys03.setSystemCd("APS_RPD");sys03.setSystemNm("활동비관리시스템");sys03.setSystemId("SYS000000003");systemCdList.add(sys03);
			System sys04 = new System();sys04.setSystemCd("CMS_PRD");sys04.setSystemNm("CMS");sys04.setSystemId("SYS000000004");systemCdList.add(sys04);
			System sys05 = new System();sys05.setSystemCd("DCP_PRD");sys05.setSystemNm("DC&P");sys05.setSystemId("SYS000000005");systemCdList.add(sys05);
			System sys06 = new System();sys06.setSystemCd("ECC_GSP");sys06.setSystemNm("G-ERP");sys06.setSystemId("SYS000000006");systemCdList.add(sys06);
			System sys07 = new System();sys07.setSystemCd("EDI_PRD");sys07.setSystemNm("EDI");sys07.setSystemId("SYS000000007");systemCdList.add(sys07);
			System sys08 = new System();sys08.setSystemCd("EHR_PRD");sys08.setSystemNm("인사시스템2");sys08.setSystemId("SYS000000008");systemCdList.add(sys08);
			System sys09 = new System();sys09.setSystemCd("ELF_PRD");sys09.setSystemNm("통제물류");sys09.setSystemId("SYS000000009");systemCdList.add(sys09);
			System sys10 = new System();sys10.setSystemCd("ENG_PRD");sys10.setSystemNm("에너지시스템");sys10.setSystemId("SYS000000010");systemCdList.add(sys10);
			System sys11 = new System();sys11.setSystemCd("EQP_PRD");sys11.setSystemNm("설비생애");sys11.setSystemId("SYS000000011");systemCdList.add(sys11);
			System sys12 = new System();sys12.setSystemCd("GHR_PRD");sys12.setSystemNm("인사시스템(해외)");sys12.setSystemId("SYS000000012");systemCdList.add(sys12);
			System sys13 = new System();sys13.setSystemCd("GHUB_PRD");sys13.setSystemNm("첼로");sys13.setSystemId("SYS000000013");systemCdList.add(sys13);
			System sys14 = new System();sys14.setSystemCd("GLN_PRD");sys14.setSystemNm("글로넷");sys14.setSystemId("SYS000000014");systemCdList.add(sys14);
			System sys15 = new System();sys15.setSystemCd("HKS_PRD");sys15.setSystemNm("HKS");sys15.setSystemId("SYS000000015");systemCdList.add(sys15);
			System sys16 = new System();sys16.setSystemCd("HLP_PRD");sys16.setSystemNm("PC HELPDESK");sys16.setSystemId("SYS000000016");systemCdList.add(sys16);
			System sys17 = new System();sys17.setSystemCd("ICC_PRD");sys17.setSystemNm("IC카드");sys17.setSystemId("SYS000000017");systemCdList.add(sys17);
			System sys18 = new System();sys18.setSystemCd("BI");sys18.setSystemNm("BI");sys18.setSystemId("SYS000000050");systemCdList.add(sys18);
			System sys19 = new System();sys19.setSystemCd("EP");sys19.setSystemNm("업무포탈");sys19.setSystemId("SYS000000051");systemCdList.add(sys19);
			System sys20 = new System();sys20.setSystemCd("SD");sys20.setSystemNm("영업관리(SD)");sys20.setSystemId("SYS000000052");systemCdList.add(sys20);
			System sys21 = new System();sys21.setSystemCd("QM");sys21.setSystemNm("품질관리(QM)");sys21.setSystemId("SYS000000053");systemCdList.add(sys21);
			System sys22 = new System();sys22.setSystemCd("PP");sys22.setSystemNm("생산관리(PP)");sys22.setSystemId("SYS000000054");systemCdList.add(sys22);
			System sys23 = new System();sys23.setSystemCd("MM");sys23.setSystemNm("구매관리(MM)");sys23.setSystemId("SYS000000055");systemCdList.add(sys23);
			System sys24 = new System();sys24.setSystemCd("LE");sys24.setSystemNm("물류관리(LE)");sys24.setSystemId("SYS000000056");systemCdList.add(sys24);
			System sys25 = new System();sys25.setSystemCd("FI");sys25.setSystemNm("재무회계(FI)");sys25.setSystemId("SYS000000057");systemCdList.add(sys25);
			System sys26 = new System();sys26.setSystemCd("CO");sys26.setSystemNm("관리회계(CO)");sys26.setSystemId("SYS000000058");systemCdList.add(sys26);
			System sys27 = new System();sys27.setSystemCd("TR");sys27.setSystemNm("자금회계(TR)");sys27.setSystemId("SYS000000059");systemCdList.add(sys27);
		}
		basicInfo.setSystemCdList(systemCdList);

		Map<String, List<CommonCode>> cds = new HashMap<String,List<CommonCode>>();

		basicInfo.setCds(cds);


		//코드검색-리소스
		List<CommonCode> cdop01 = new ArrayList<CommonCode>();
		CommonCode cdop001 = new CommonCode();
		cdop001.setCd("00");
		cdop001.setNm("정상");
		CommonCode cdop002 = new CommonCode();
		cdop002.setCd("01");
		cdop002.setNm("처리중");
		CommonCode cdop003 = new CommonCode();
		cdop003.setCd("99");
		cdop003.setNm("오류");
		cdop01.add(cdop001);
		cdop01.add(cdop002);
		cdop01.add(cdop003);
		cds.put("OP01", cdop01);

		//코드검색-리소스
		List<CommonCode> cdim04 = new ArrayList<CommonCode>();
		CommonCode cdim010 = new CommonCode();
		cdim010.setCd("0");
		cdim010.setNm("DB");
		CommonCode cdim011 = new CommonCode();
		cdim011.setCd("1");
		cdim011.setNm("FILE");
		CommonCode cdim012 = new CommonCode();
		cdim012.setCd("2");
		cdim012.setNm("RFC");

		CommonCode cdim013 = new CommonCode();
		cdim013.setCd("3");
		cdim013.setNm("Proxy");

		CommonCode cdim014 = new CommonCode();
		cdim014.setCd("4");
		cdim014.setNm("WS");

		CommonCode cdim015 = new CommonCode();
		cdim015.setCd("5");
		cdim015.setNm("API");


		cdim04.add(cdim010);
		cdim04.add(cdim011);
		cdim04.add(cdim012);

		cdim04.add(cdim013);
		cdim04.add(cdim014);
		cdim04.add(cdim015);

		cds.put("IM04", cdim04);

		//공통코드-노드유형
		List<CommonCode> cdim08 = new ArrayList<CommonCode>();
		CommonCode cdim0801 = new CommonCode();
		cdim0801.setCd("0");
		cdim0801.setNm("송신");
		CommonCode cdim0802 = new CommonCode();
		cdim0802.setCd("1");
		cdim0802.setNm("HUB");
		CommonCode cdim0803 = new CommonCode();
		cdim0803.setCd("2");
		cdim0803.setNm("수신");
		cdim08.add(cdim0801);
		cdim08.add(cdim0802);
		cdim08.add(cdim0803);
		cds.put("IM08", cdim08);


		//------------------------------------------
		//코드-값 list
		//------------------------------------------
		//코드검색-요건상태
		CommonCode cdan010 = new CommonCode();
		cdan010.setCd("0");
		cdan010.setNm("등록");
		CommonCode cdan011 = new CommonCode();
		cdan011.setCd("1");
		cdan011.setNm("검토");
		CommonCode cdan012 = new CommonCode();
		cdan012.setCd("2");
		cdan012.setNm("결재");
		CommonCode cdan013 = new CommonCode();
		cdan013.setCd("3");
		cdan013.setNm("결재완료");
		CommonCode cdan014 = new CommonCode();
		cdan014.setCd("4");
		cdan014.setNm("반려");

		List<CommonCode> cdan01 = new ArrayList<CommonCode>();
		cdan01.add(cdan010);
		cdan01.add(cdan011);
		cdan01.add(cdan012);
		cdan01.add(cdan013);
		cdan01.add(cdan014);
		cds.put("AN01", cdan01);

		/*
		IIB
		"SAP PO"
		"Data Stage"
		BizTalk
		*/
		Channel channel01 = new Channel();
		channel01.setChannelId("1");
		channel01.setChannelNm("IIB");

		Channel channel02 = new Channel();
		channel02.setChannelId("2");
		channel02.setChannelNm("SAP PO");

		Channel channel03 = new Channel();
		channel03.setChannelId("3");
		channel03.setChannelNm("Data Stage");

		List<Channel> channelList = new ArrayList<Channel>();
		channelList.add(channel01);
		channelList.add(channel02);
		channelList.add(channel03);
		basicInfo.setChannelList(channelList);



		//InterfaceTag 세팅
		List<InterfaceTag> tagList = new ArrayList<InterfaceTag>();
		InterfaceTag tag01 = new InterfaceTag();
		tag01.setInterfaceId("A0CM0001_S");
		tag01.setTag("매출정보");
		tagList.add(tag01);

		InterfaceTag tag02 = new InterfaceTag();
		tag02.setInterfaceId("A0CM0003_SO");
		tag02.setTag("고객");
		tagList.add(tag02);

		InterfaceTag tag03 = new InterfaceTag();
		tag03.setInterfaceId("A0CM0004_SO");
		tag03.setTag("판매");
		tagList.add(tag03);

		InterfaceTag tag04 = new InterfaceTag();
		tag04.setInterfaceId("A0CM1006_SO");
		tag04.setTag("고객관리");
		tagList.add(tag04);

		InterfaceTag tag05 = new InterfaceTag();
		tag05.setInterfaceId("A0CM1007_AO");
		tag05.setTag("GERP연계");
		tagList.add(tag05);

		basicInfo.setInterfaceTagList(tagList);



		//------------------------------------------
		//JSON DATA Building
		//------------------------------------------
		//String jsonString = Util.toJSONString(basicInfo);
		//logger.debug(Util.join("data:\n",jsonString));
		String fileName = "REST-S01-CO-00-00-003.json";
		String path = "./src/main/webapp/WEB-INF/test-data/co";
		DataBuilderUtil.saveToLocal(path,fileName, basicInfo);

		logger.debug(Util.join("build success BasicInfoData:",path,"/",fileName));


	}



}
