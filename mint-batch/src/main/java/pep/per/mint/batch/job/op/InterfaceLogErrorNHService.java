/*
 * Copyright (c) 2013 ~ 2015. Mocomsys's guys(Sanghoon Lim, Deahun Ham, dhkim, whoana.j), Inc.  All Rights Reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 * [주의!]
 * 본 코드가 하자없이 완벽할거라 믿었다간 엄청난 욕을 먹고 그로 인한 스트레스로 병들거나 심하면 살기 싫어질 수도 있으니 부디 살얼음판을 걷듯이 주의하여 사용하기 바란다.
 * 사용상 받을지 모를 스트레스 및 기타 피해에 대한 책임은 사용자 본인에게 있음을 명시한다. 부디 행운을 빈다.
 * Please Don't contact Mocomsys, Inc., NURITKUM SQUARE R&D TOWER, 11F DMC 1605,
 * Sangam-Dong, Mapo-Gu, Seoul, 121-795 Korea or visit mocomsys.com if you need additional information or have any questions.
 */

package pep.per.mint.batch.job.op;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pep.per.mint.batch.job.JobServiceCaller;
import pep.per.mint.batch.mapper.op.DetailLogErrorJobMapper;
import pep.per.mint.batch.mapper.op.InterfaceLogErrorNHJobMapper;
import pep.per.mint.common.util.Util;
import pep.per.mint.inhouse.sms.SendSMSService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by whoana on 15. 9. 30..
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
@Service
public class InterfaceLogErrorNHService extends JobServiceCaller{

	@Autowired
	InterfaceLogErrorNHJobMapper interfaceLogErrorNHJobMapper;


	@Transactional
	public void run(Map params) throws Exception{
		List<Map> list = interfaceLogErrorNHJobMapper.selectErrorTrLog(params);
		for (Map map : list) {
			Double totalCnt = ((Integer)map.get("totalCnt")).doubleValue();
			Double errorCnt = ((Integer)map.get("errCnt")).doubleValue();

			if( ((errorCnt / totalCnt) * 100 ) > 10){

				String hostId 	= (String)map.get("hostId");
				String groupId 	= (String)map.get("groupId");
				String intfId 	= (String)map.get("intfId");

//				Map buildParams = new HashMap();
//
//				buildParams.put("Status", "9");
//				buildParams.put("ObjectType", "1");
//				buildParams.put("RegTime", Util.getFormatedDate());
//				buildParams.put("QmgrName", "");
//				buildParams.put("ObjectName", intfId);
//				buildParams.put("Info1", Util.join(errorCnt, "/", totalCnt));
//				buildParams.put("HostName", hostId);

				StringBuffer sb = new StringBuffer();
				SimpleDateFormat dt1 = new SimpleDateFormat("MM-dd HH:mm:ss");
				String newRegTime =dt1.format(new Date());
				sb.append("[").append(newRegTime).append("] ");
				sb.append(intfId).append(" ");
				sb.append("[").append(Util.join(errorCnt, "/", totalCnt)).append("] alert");

				String contents = sb.toString();
				map.put("smsId", UUID.randomUUID().toString());
				map.put("type", "0");  // 포털 설정 확인
				map.put("subject", "InterfaceErrAlarm");
				map.put("contents", contents);

				map.put("sender", "11111");
				map.put("recipients", "222222");
				map.put("format", "0");
				map.put("sndReqDate", Util.getFormatedDate());
				map.put("sndYn", "N");
				map.put("regDate", Util.getFormatedDate());
				map.put("regUser", "iip");
				interfaceLogErrorNHJobMapper.insertInterfaceErrLogSms(map);
			}else{
				System.out.println("Not bad");
			}
		}

	}

}
