package pep.per.mint.front.service.im;



import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import pep.per.mint.common.data.basic.ComMessage;
import pep.per.mint.common.data.basic.PageInfo;
import pep.per.mint.common.data.basic.Requirement;
import pep.per.mint.common.util.Util;
import pep.per.mint.database.service.an.RequirementService;
import pep.per.mint.front.env.FrontEnvironments;


@Service
public class InterfaceTransferService {

	final static String TARGET_SYSTEM_TEST = "1";

	final static String TARGET_SYSTEM_REAL = "2";

	@Autowired
	RequirementService rs;

	@Autowired
	@Qualifier(value="restTemplate")
	RestTemplate rest;

	final static String TRANSFER_OK = "0";

	/*
	 * G0	분석	요건상태	개발완료
	 * H0	분석	요건상태	테스트완료
	 * I0	분석	요건상태	이행완료
	 */

	/**
	 *
	 * @param targetSystem
	 * @param requirementIds
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void send(String targetSystem, String requirementId, String userId) throws Exception{

		ComMessage<Requirement, Requirement> comMessage = new ComMessage<Requirement, Requirement>();
		comMessage.setAppId(InterfaceTransferService.class.getName());
		comMessage.setCheckSession(false);
		String sysdate = Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI);
		comMessage.setStartTime(sysdate);
		comMessage.setUserId(userId);

		String transferUrl = "";
		String status = "";
		String modDate = sysdate;
		String modId = userId;
		String finYmd = Util.getFormatedDate("yyyymmdd");
		if(TARGET_SYSTEM_REAL.equals(targetSystem) ){
			transferUrl = FrontEnvironments.interfaceTransferRealUrl ;
			status = "I0"; //운영이관
		}else{
			transferUrl = FrontEnvironments.interfaceTransferTestUrl ;
			status = "G0";//개발완료
		}

		if(Util.isEmpty(transferUrl)){
			throw new Exception("인터페이스정보이관실패: 전송할 목적지 서버 정보가 IIP환경설정에 등록되어있지 않습니다.");
		}


		Requirement requirement = rs.getRequirementDetail(requirementId);
		comMessage.setRequestObject(requirement);
		transferUrl = Util.join(transferUrl , "/mint/im/interfaces/transfer/receive?method=POST");

		ComMessage<Requirement, Requirement> response = rest.postForObject(transferUrl, comMessage, comMessage.getClass());
		String errorCd = response.getErrorCd();
		if(!TRANSFER_OK.equals(errorCd)){
			throw new Exception(Util.join("인터페이스정보이관실패, errorMsg:",response.getErrorMsg(), ", errorDetail:" , response.getErrorDetail()));
		}

		rs.updateRequirementDevelopmentStatus(status, modDate, modId, requirementId, finYmd);

	}

	public Requirement receive(Requirement requirement) throws Exception {
		int res = rs.createRequirement(requirement);
		return requirement;
	}
	/*
	@Bean
	public RestTemplate getRestTemplate() {
	    return new RestTemplate();
	}
	*/

}
