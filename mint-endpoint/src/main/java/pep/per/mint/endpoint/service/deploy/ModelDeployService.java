/**
 * Copyright 2020 Mocomsys Inc.  All Rights Reserved.
 */
package pep.per.mint.endpoint.service.deploy;

import java.io.BufferedWriter;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import org.springframework.web.client.RestTemplate;


import pep.per.mint.common.data.basic.ComMessage;
import pep.per.mint.common.data.basic.DisplaySystemInfo;
import pep.per.mint.common.data.basic.Interface;
import pep.per.mint.common.data.basic.dataset.DataMap;
import pep.per.mint.common.data.basic.dataset.DataSet;
import pep.per.mint.common.data.basic.runtime.DeploymentInfo;
import pep.per.mint.common.data.basic.runtime.InterfaceModel;
import pep.per.mint.common.data.basic.runtime.ModelDeployment;
import pep.per.mint.common.util.Util;
import pep.per.mint.database.service.an.DataSetService;
import pep.per.mint.database.service.co.CommonService;
import pep.per.mint.database.service.rt.ModelService;
import pep.per.mint.endpoint.Variables;
import pep.per.mint.endpoint.service.deploy.data.description.Layout;
import pep.per.mint.endpoint.service.deploy.data.description.Mapping;
import pep.per.mint.endpoint.service.deploy.data.description.ModelDescription;
import pep.per.mint.endpoint.service.deploy.data.description.ModelDescriptionBuilder;
import pep.per.mint.endpoint.service.deploy.data.description.ModelDescriptionXmlBuilder;
import pep.per.mint.endpoint.service.deploy.data.mapping.MappingDefinitionBuilder;
import pep.per.mint.endpoint.service.deploy.data.msg.MessageDefinitionBuilder;

/**
 * <pre>
 * 모델 정보를 파일 및 서비스를 통해 내보내기 하기 위한 서비스
 * </pre>
 *
 * @author whoana
 * @since Jul 30, 2020
 */
@Service
public class ModelDeployService {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	DataSetService dataSetService;

	@Autowired
	ModelService modelService;

	@Autowired
	CommonService commonService;

	@Autowired
	Variables variables;

	/**
	 *
	 */
	final static String STAGE_DEV  = "0";

	/**
	 *
	 */
	final static String STAGE_TEST = "1";

	/**
	 *
	 */
	final static String STAGE_REAL = "2";

	/**
	 * <pre>
	 * 인터페이스 모델 정보를 xml 하나의 포멧으로 내보내기 한다.
	 * xml 정의서는 model.xsd 를 참고하라.
	 * 메시지 레이아웃 및 매핑 정의서는 cdata 형태로 포함한다.
	 *
	 * [내보내기 위치 및 파일]
	 * 	{배포 HOME:dest}/{STAGE:deve,test,real}/{INTERFACE_ID}/{MODEL_ID}/model.xml
	 * </pre>
	 *
	 * @param dest
	 * @param interfaceMid
	 * @throws Exception
	 */
	public void deployModelToXmlFile(String dest, String interfaceMid, String deployUserId) throws Exception{
		ModelDescriptionXmlBuilder mdxb = new ModelDescriptionXmlBuilder();
		InterfaceModel model = modelService.getInterfaceModel(interfaceMid);
		Interface interfaze = commonService.getInterfaceDetail(model.getInterfaceId());

		String contents = mdxb.build(model, dataSetService, interfaze, variables, deployUserId);



		String interfaceId = model.getIntegrationId();
		String modelId = model.getMid();
		Path destPath = null;
		String stageCd = model.getStage();
		String state;
		if(STAGE_DEV.equals(stageCd)){
			state = "deve";
		}else if(STAGE_TEST.equals(stageCd)){
			state = "test";
		}else if(STAGE_REAL.equals(stageCd)){
			state = "real";
		}else {
			state = "real";
		}

		destPath = Paths.get(dest, state, interfaceId,  modelId);
		//destPath = Paths.get(dest, interfaceId, modelId);

		Files.createDirectories(destPath);
		Path modelFile = Paths.get(destPath.toString(),  "model.xml");

		Files.deleteIfExists(modelFile);
		Files.createFile(modelFile);
		Charset cs = null;
		OpenOption []oos = null;
		BufferedWriter bw = Files.newBufferedWriter(modelFile, cs, oos);
		bw.write(contents);
		bw.flush();

	}

	/**
	 * <pre>
	 * 인터페이스 모델 컨텐츠를 조회하여 리턴한다.
	 * </pre>
	 * @param dest
	 * @param interfaceMid
	 * @return
	 * @throws Exception
	 */
	public String getDeployModelXml(String interfaceMid, String deployUser) throws Exception{
		ModelDescriptionXmlBuilder mdxb = new ModelDescriptionXmlBuilder();
		InterfaceModel model = modelService.getInterfaceModel(interfaceMid);
		if(model == null) throw new Exception(Util.join("not found interface model [interfaceMid:",interfaceMid,"]"));
		Interface interfaze = commonService.getInterfaceDetail(model.getInterfaceId());
		String contents = mdxb.build(model, dataSetService, interfaze, variables, deployUser);
		return contents;
	}


	/**
	 * <pre>
	 * 인터페이스 모델 컨텐츠를 조회하여 리턴한다.
	 * </pre>
	 * @param dest
	 * @param interfaceMid
	 * @return
	 * @throws Exception
	 */

	@Autowired
	RestTemplate restTemplate;
	final static String SUCCESS_CD = "success";
	final static String ERROR_CD = "error";
	/**
	 * <pre>
	 * 인터페이스 모델 컨텐츠를 배포시스템에 배포한다.
	 * update
	 * 2021.01.12
	 * ModelDeployment - 추가내용
	 * 1) integrationId
	 * 2) interfaceNm
	 * 3) businessCd(업무코드)
	 * 4) 송신시스템코드
	 * 5) 송신시스템명
	 * 6) 수신시스템코드
	 * 7) 수신시스템명
	 * 2021.01.18
	 * ModelDeployment - 추가내용
	 * 8) 채널코드
	 * 9) 채널명
	 * </pre>
	 * @param md
	 * @throws Exception
	 */
	public ComMessage<ModelDeployment, ModelDeployment> deploy(ComMessage<ModelDeployment, ModelDeployment> request) throws Exception{

		ModelDeployment md = request.getRequestObject();
		try {
			md.setDeployDate(Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT));
			//md.setContents(getDeployModelXml(md.getInterfaceMid(), md.getDeployUser()));

			String interfaceMid = md.getInterfaceMid();
			ModelDescriptionXmlBuilder mdxb = new ModelDescriptionXmlBuilder();
			InterfaceModel model = modelService.getInterfaceModel(interfaceMid);
			if(model == null) throw new Exception(Util.join("not found interface model [interfaceMid:",interfaceMid,"]"));
			Interface interfaze = commonService.getInterfaceDetail(model.getInterfaceId());
			String contents = mdxb.build(model, dataSetService, interfaze, variables, md.getDeployUser());
			md.setContents(contents);

			DeploymentInfo di = new DeploymentInfo();
			if(!Util.isEmpty(interfaze.getBusinessList())) di.setBusinessCd(interfaze.getBusinessList().get(0).getBusinessCd());
			di.setIntegrationId(interfaze.getIntegrationId());
			di.setInterfaceNm(interfaze.getInterfaceNm());

			if(!Util.isEmpty(interfaze.getSenderSystemInfoList())) {
				DisplaySystemInfo senderSystem = interfaze.getSenderSystemInfoList().get(0);
				di.setSenderSystemCd(senderSystem.getSystemCd());
				di.setSenderSystemNm(senderSystem.getSystemNm());
			}
			if(!Util.isEmpty(interfaze.getReceiverSystemInfoList())) {
				DisplaySystemInfo receiverSystem = interfaze.getReceiverSystemInfoList().get(0);
				di.setReceiverSystemCd(receiverSystem.getSystemCd());
				di.setReceiverSystemNm(receiverSystem.getSystemNm());
			}
			if(interfaze.getChannel() != null) {
				di.setChannelCd(interfaze.getChannel().getChannelCd());
				di.setChannelNm(interfaze.getChannel().getChannelNm());
			}
			md.setDeploymentInfo(di);


			String url = commonService.getEnvironmentalValue("system", "model.deploy.service.url", "http://localhost:8080/mint-bridge-apps/deployments");
			ComMessage<ModelDeployment, ModelDeployment> response = restTemplate.postForObject(url, request, request.getClass());

			if(response == null) {
				response = request;
				response.setEndTime(Util.getFormatedDate(Util.DEFAULT_DATE_FORMAT_MI));
				response.setResponseObject(md);
				md.setResultCd(ERROR_CD);
				md.setResultMsg("have no response from the deployment system.");
			}

			return response;

		}catch(Exception e) {
			md.setResultCd(ERROR_CD);
			md.setResultMsg(e.getMessage());
			throw e;
		}finally {
			modelService.createModelDeployment(md);
		}
	}



	/**
	 * <pre>
	 * 내보내기 위치
	 * 	{배포 HOME:dest}/{STAGE:deve,test,real}/{INTERFACE_ID}/{MODEL_ID}
	 * 내보내기 파일 리스트
	 * 	model.json
	 * 	layout.xsd(여러개 가능)
	 * 	mapping.xml(여러개 가능)
	 * </pre>
	 * @param dest
	 * @param interfaceMid
	 * @throws Exception
	 */
	public void deployModelToJsonFile(String dest, String interfaceMid) throws Exception{

		//Files.createDirectories(Paths.get(dest));

		ModelDescription md = buildModelDescription(interfaceMid);

		String interfaceId = md.getInterfaceId();
		String modelId = md.getId();
		Path destPath = null;
		String stageCd = md.getStage();
		String state;
		if(STAGE_DEV.equals(stageCd)){
			state = "deve";
		}else if(STAGE_TEST.equals(stageCd)){
			state = "test";
		}else if(STAGE_REAL.equals(stageCd)){
			state = "real";
		}else {
			state = "real";
		}

		destPath = Paths.get(dest, state, interfaceId,  modelId);
		//destPath = Paths.get(dest, interfaceId, modelId);

		Files.createDirectories(destPath);
		Path modelFile = Paths.get(destPath.toString(),  "model.json");

		Files.deleteIfExists(modelFile);
		Files.createFile(modelFile);
		Charset cs = null;
		OpenOption []oos = null;
		BufferedWriter bw = Files.newBufferedWriter(modelFile, cs, oos);
		bw.write(Util.toJSONPrettyString(md));
		bw.flush();


		if(md.getLayouts().size() > 0) {
			Collection<Layout> layouts = md.getLayouts().values();
			for (Layout layout : layouts) {
				String id = layout.getId();
				String contents = buildMessageLayout(id);

				Path layoutFile = Paths.get(destPath.toString(), layout.getDefinition());

				Files.deleteIfExists(layoutFile);
				Files.createFile(layoutFile);

				BufferedWriter w = Files.newBufferedWriter(layoutFile, cs, oos);
				w.write(contents);
				w.flush();

			}
		}



		if(md.getMappings().size() > 0) {
			Collection<Mapping> mappings = md.getMappings().values();
			for (Mapping mapping : mappings) {
				String id = mapping.getId();
				String contents = buildMapping(id);

				Path mappingFile = Paths.get(destPath.toString(), mapping.getDefinition());

				Files.deleteIfExists(mappingFile);
				Files.createFile(mappingFile);
				BufferedWriter w = Files.newBufferedWriter(mappingFile, cs, oos);
				w.write(contents);
				w.flush();
			}
		}

		URL mappingXsd = getClass().getResource("/config/map.xsd");
		Files.deleteIfExists(Paths.get(destPath.toString(), "map.xsd"));
		Files.copy(Paths.get(mappingXsd.getFile()), Paths.get(destPath.toString(), "map.xsd"));

		URL readme = getClass().getResource("/config/readme.txt");
		Files.deleteIfExists(Paths.get(destPath.toString(), "readme.txt"));
		Files.copy(Paths.get(readme.getFile()), Paths.get(destPath.toString(), "readme.txt"));


	}


	/**
	 * <pre>
	 * mapping 정의서 생성
	 * </pre>
	 * @param dataMapId
	 * @return
	 * @throws Exception
	 */
	public String buildMapping(String dataMapId) throws Exception {
		Map<String, Object> res  = dataSetService.getSimpleDataMap(dataMapId , "N");

		DataMap map = (DataMap) res.get("mapData");
		DataSet tar = (DataSet) res.get("tagDataSet");
		DataSet src = (DataSet) res.get("srcDataSet");
		MappingDefinitionBuilder mdb = new MappingDefinitionBuilder();
		return mdb.build(Arrays.asList(src, tar), map);
	}

	/**
	 *
	 * @param dataSetId
	 * @return
	 * @throws Exception
	 */
	public String buildMessageLayout(String dataSetId) throws Exception {
		MessageDefinitionBuilder xsdBuilder = new MessageDefinitionBuilder();
		DataSet dataSet = dataSetService.getSimpleDataSet(dataSetId, "N");
		return xsdBuilder.build(dataSet, variables);
	}

	/**
	 * <pre>
	 *
	 * </pre>
	 * @param interfaceMid
	 * @return
	 * @throws Exception
	 */
	public ModelDescription buildModelDescription(String interfaceMid)throws Exception {

		InterfaceModel model = modelService.getInterfaceModel(interfaceMid);
		System.out.println("mode:" + Util.toJSONPrettyString(model));
		ModelDescriptionBuilder mdBuilder = new ModelDescriptionBuilder();
		ModelDescription md = mdBuilder.build(model);
		return md;
	}
}
