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
import java.util.List;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pep.per.mint.common.data.basic.TRLogDetail;
import pep.per.mint.common.util.Util;

/**
 * @author Solution TF
 *
 */
public class TRLogDetailDataBuilder {

	private static final Logger logger = LoggerFactory.getLogger(TRLogDetailDataBuilder.class);

	@Test
	public void build(){

		List<TRLogDetail> detailList = new ArrayList<TRLogDetail>();

		int addCnt = 1;

		TRLogDetail detail = new TRLogDetail();
		TRLogDetail detail1 = new TRLogDetail();

		detail.setLogKey1("1");
		detail.setLogKey2("20150724130102000000");
		detail.setLogKey3("1");
		detail.setNodeId("");
		detail.setProcessTime("20150724130102");
		detail.setStatus("00");
		detail.setHopCnt(0);
		detail.setDataSize(6144);
		detail.setErrorCd("");
		detail.setErrorMsg("");
		detail.setProcessMode("SNDR");
		detail.setHostId("solman1");
		detail.setCompressYn("N");

		detailList.add(detail);

		detail1.setLogKey1("1");
		detail1.setLogKey2("20150724130102000000");
		detail1.setLogKey3("3");
		detail1.setNodeId("");
		detail1.setProcessTime("20150724130104");
		detail1.setStatus("99");
		detail1.setHopCnt(0);
		detail1.setDataSize(6144);
		detail1.setErrorCd("");
		detail1.setErrorMsg("");
		detail1.setProcessMode("RCVR");
		detail1.setHostId("itmsap1");
		detail1.setCompressYn("N");

		detailList.add(detail1);

		//------------------------------------------
		//JSON DATA Building
		//------------------------------------------
		//String jsonString = Util.toJSONString(basicInfo);
		//logger.debug(Util.join("data:\n",jsonString));

		String fileName = "REST-R02-OP-01-01-002.json";
		String path = "./src/main/webapp/WEB-INF/test-data/op";
		DataBuilderUtil.saveToLocal(path,fileName, detailList);

		logger.debug(Util.join("build success BasicInfoData:",path,"/",fileName));


	}


}
