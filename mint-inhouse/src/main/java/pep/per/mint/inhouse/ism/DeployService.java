package pep.per.mint.inhouse.ism;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import pep.per.mint.common.data.basic.ComMessage;
import pep.per.mint.common.data.basic.Deployment;
import pep.per.mint.common.data.basic.DisplaySystemInfo;
import pep.per.mint.common.data.basic.Interface;
import pep.per.mint.common.data.basic.InterfaceProperties;
import pep.per.mint.common.data.basic.User;
import pep.per.mint.common.util.Util;
import pep.per.mint.database.Environments;
import pep.per.mint.database.exception.NotFoundDataException;
import pep.per.mint.database.mapper.im.InfraMapper;
import pep.per.mint.database.mapper.op.DeployMapper;
import pep.per.mint.database.service.co.CommonService;

@Service
public class DeployService {

	Logger logger = LoggerFactory.getLogger(DeployService.class);

	public final static String DEPLOY_SUCCESS = "0";


	@Autowired
	@Qualifier(value="restTemplate")
	RestTemplate restTemplate;

	@Autowired
	//CommonService commonService;
	InfraMapper infraMapper;


	@Autowired
	CommonService commonService;

	@Autowired
	DeployMapper deployMapper;

	public List<Deployment> getDeployInterfaceResults(Map<String, String> params) throws Exception{
		return deployMapper.getDeployInterfaceResults(params);
	}


	public Deployment getDeployInterfaceResult(String reqDate, String interfaceId, int seq) throws Exception{
		return deployMapper.getDeployInterfaceResult(reqDate, interfaceId, seq);
	}

	@Transactional
	@SuppressWarnings("unchecked")
	public Deployment deployInterface(Map<String, String> params) throws Exception{

		String interfaceId = params.get("interfaceId");
		String appId = params.get("appId");
		String userId = params.get("userId");
		String targetType = params.get("targetType");

		String version = deployMapper.getInterfaceLastVersion(interfaceId);

		Interface interfaze = commonService.getInterfaceDetail(interfaceId);

		if(interfaze == null){
			throw new NotFoundDataException(Util.join("배포할 인터페이스 정보를 찾을 수 없습니다.(interfaceId:",interfaceId,")"));
		}

		Deployment deployment = new Deployment();

		deployment.setInterface(interfaze);
		deployment.setRequesterId(userId);
		User requester = commonService.getUser(userId);
		deployment.setRequesterNm(requester.getUserNm());
		deployment.setRequestKey(UUID.randomUUID().toString());
		deployment.setTargetType(targetType);
		deployment.setVersion(version);

		ComMessage<Deployment,Object> request = new ComMessage<Deployment,Object>();
		request.setAppId(appId);
		request.setCheckSession(false);
		request.setRequestObject(deployment);
		request.setStartTime(Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI));
		request.setUserId(params.get("userId"));


		ComMessage<Deployment,Object> response = deployInterface(request, Environments.deployInterfaceUrl);

		deployment = (Deployment)response.getRequestObject();

		deployMapper.insertDeployment(deployment);

		return deployment;

	}

	final static String TYPE_INTF_FILE_GET = "FileGet";
	final static String TYPE_INTF_FILE_PUT = "FilePut";
	final static String VAR_FILE_PATH_USE = "Y";
	String defaultFilePathValue = "./";
	String filePathUseAttributeCd = "";
	String filePathUseAttributeValue = "";

	public ComMessage<Deployment, Object> deployInterface(ComMessage<Deployment, Object> request, String url) throws Exception {

		Deployment deployment = (Deployment)request.getRequestObject();

		String targetType = deployment.getTargetType();
		String requesterId = deployment.getRequesterId();
		String requesterNm = deployment.getRequesterNm();
		String requestKey = deployment.getRequestKey();
		String version = deployment.getVersion();

		Interface interfaze = deployment.getInterface();

		logger.debug(Util.join("interface data:[",Util.toJSONString(interfaze),"]"));

		String interfaceId = interfaze.getIntegrationId(); // IF ID
		String appPrMethod = interfaze.getAppPrMethod();   // 동기,비동기
		String interfaceNm = interfaze.getInterfaceNm();   // I/F 이름
		String dataPrMethod = interfaze.getDataPrMethod(); // 온라인, 배치, 디퍼드 => I/F 유형
		String dataPrDir = interfaze.getDataPrDir();       // 데이터 처리방향 (조회,등록)
		String if_Use = interfaze.getImportance();         // IF 사용여부
		//============================================================================================
		//현대해상 추가 :
		//인터페이스 사용유무 추가
		//기존 importance 속성 사용
		//--------------------------------------------------------------------------------------------
		//고영선과장 요청
		//20170524
		String useYn = Util.isEmpty(interfaze.getImportance()) ? "Y" : interfaze.getImportance() ;


		List<InterfaceProperties> props = interfaze.getProperties();
		logger.debug("properties info : "+Util.toJSONString(props));


		String receiverResourceCd = null;
		String receiverSystemCd = null;
		String receiverService = null;
		String receiverServiceNm = null;
		//String receiverServiceDesc = null;
		String senderResourceCd = null;
		String senderSystemCd = null;
		String senderService = null;
		String senderServiceNm = null;


		//============================================================================================
		//현대해상 시스템CD 2단계 표시 요청 으로 인한 소스 변경
		//--------------------------------------------------------------------------------------------
		//고영선과장 요청
		//20170339
		{
			List<DisplaySystemInfo>sendSystems = interfaze.getSenderSystemInfoList();
			for(int i = 0 ; i < sendSystems.size() ; i++){
				DisplaySystemInfo system = sendSystems.get(i);
				//String systemId = system.getSystemId();
				String systemCd = system.getSystemCd();
				system.setSystemCd(Util.join(systemCd));

				senderResourceCd = system.getResourceCd();  // 송신리소스 코드(ex:DB, HTTP, SAP ...)
				senderSystemCd = Util.join(systemCd);       // 송신시스템 코드
				senderService = system.getService();        // 요청전문객체
				senderServiceNm = system.getServiceDesc();  // 송신호출ID명

			}

			List<DisplaySystemInfo>recvSystems = interfaze.getReceiverSystemInfoList();
			for(int i = 0 ; i < recvSystems.size() ; i++){
				DisplaySystemInfo system = recvSystems.get(i);
				//String systemId = system.getSystemId();
				String systemCd = system.getSystemCd();
				system.setSystemCd(Util.join(systemCd));

				receiverResourceCd = system.getResourceCd();   // 수신리소스 코드(ex:DB, HTTP, SAP ...)
				receiverSystemCd = Util.join(systemCd);        // 수신시스템 코드
				receiverService = system.getService();         // 응답전문객체
				receiverServiceNm = system.getServiceDesc(); // 수신서비스 ID명
			}
		}
		//--------------------------------------------------------------------------------------------

		DeploymentInfo info = new DeploymentInfo();
		info.setAppPrMethod(appPrMethod);
		info.setInterfaceId(interfaceId);
		info.setInterfaceNm(interfaceNm);
		info.setDataPrMethod(dataPrMethod);
		info.setDataPrDir(dataPrDir);
		info.setTargetType(targetType);
		info.setVersion(version);

		info.setRequesterId(requesterId);
		info.setRequesterNm(requesterNm);
		info.setRequestKey(requestKey);

		info.setReceiverResourceCd(receiverResourceCd);
		info.setReceiverService(receiverService);
		info.setReceiverServiceNm(receiverServiceNm);
		info.setReceiverSystemCd(receiverSystemCd);

		info.setSenderResourceCd(senderResourceCd);
		info.setSenderService(senderService);
		info.setSenderServiceNm(senderServiceNm);
		info.setSenderSystemCd(senderSystemCd);
		info.setIf_Use(if_Use);
		info.setUseYn(useYn);


		List<InterfacePropertiesInfo> pList = new ArrayList<InterfacePropertiesInfo>();

		{
		    try{
		    	Map<String,List<String>> envm = commonService.getEnvironmentalValues("operation", "interface.file.path.attr.cd");
				if(envm != null){
					List<String> envs = envm.get("operation.interface.file.path.attr.cd");
					if(!Util.isEmpty(envs)){
						filePathUseAttributeCd = envs.get(0);
					}
				}
			}catch(Exception e){logger.error("ignorableException:", e);}
			logger.debug(Util.join("The env [operation.interface.file.path.attr.cd]:",filePathUseAttributeCd));
		}

		for(InterfaceProperties property : props){
			InterfacePropertiesInfo pInfo = new InterfacePropertiesInfo();
			pInfo.setAttrCd(property.getAttrCd());
			pInfo.setInterfaceId(property.getAttrId());
			pInfo.setAttrNm(property.getAttrNm());
			pInfo.setAttrValue(property.getAttrValue());
			pInfo.setChannelId(property.getChannelId());
			pInfo.setIdx(property.getIdx());
			pInfo.setInterfaceId(property.getInterfaceId());
			pList.add(pInfo);

			logger.debug(Util.join("property:" , Util.toJSONString(property)));

			if(property.getAttrCd().equalsIgnoreCase(filePathUseAttributeCd)){
				filePathUseAttributeValue = property.getAttrValue();
			}
		}
		info.setInterfaceProperties(pList);

		//filePathUseAttributeValue 값이 "N" 이거나 null 이면 senderServiceNm 및 setReceiverServiceNm 를 "./" 로 보낸다.
		{
			String interfaceTypeNm = interfaze.getHasDataMapNm();//코드명으로 비교 , 현대해상 인터페이스 유형은 공통코드 'AN', '14'

			logger.debug(Util.join("#filePathUseAttributeValue:[",filePathUseAttributeValue,"]"));
			logger.debug(Util.join("#interfaceType:[",interfaceTypeNm,"]"));
			if(TYPE_INTF_FILE_GET.equalsIgnoreCase(interfaceTypeNm) || TYPE_INTF_FILE_PUT.equalsIgnoreCase(interfaceTypeNm)){
				if(Util.isEmpty(filePathUseAttributeValue) || !VAR_FILE_PATH_USE.equalsIgnoreCase(filePathUseAttributeValue)){
					info.setSenderService(defaultFilePathValue);
					info.setReceiverService(defaultFilePathValue);
				}
			}
		}



		if(logger.isDebugEnabled()){
			logger.debug("==========================================================================");
			logger.debug("== THE DEPLOY INTERFACE REQUEST INFO (요청 데이터 정보)");
			logger.debug("--------------------------------------------------------------------------");
			//logger.debug(Util.join("request msg :", Util.toJSONString(request)));
			logger.debug(Util.join("request msg :", Util.toJSONString(info)));
			logger.debug("--------------------------------------------------------------------------");
		}

		@SuppressWarnings("unchecked")

		//Map<String, String> response = restTemplate.postForObject(url, request, Map.class);
		Map<String, String> response = restTemplate.postForObject(url, info, Map.class);

		if(logger.isDebugEnabled()){
			logger.debug("==========================================================================");
			logger.debug("== THE DEPLOY INTERFACE RESPONSE INFO  (응답 데이터 정보)");
			logger.debug("--------------------------------------------------------------------------");
			logger.debug(Util.join("response msg :", Util.toJSONString(response)));
		}

		String errorCd = (String)response.get("errorCd");
		if(DEPLOY_SUCCESS.equals(errorCd)){
			deployment.setResultCd("0");
			deployment.setResultMsg("배포성공");
		}else{
			String errorMsg =  (String)response.get(errorCd);
			deployment.setResultCd(errorCd);
			deployment.setResultMsg(errorMsg);
		}
		deployment.setResDate(Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI));

		return request;
	}


}