package pep.per.mint.common.msg.handler;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import pep.per.mint.common.data.basic.ApplicationInfo;
import pep.per.mint.common.data.basic.ComMessage;
import pep.per.mint.common.data.basic.ConfigInfo;
import pep.per.mint.common.data.basic.Extension;
import pep.per.mint.common.data.basic.agent.IIPAgentInfo;
import pep.per.mint.common.data.basic.agent.ProcessStatusLog;
import pep.per.mint.common.data.basic.agent.QmgrLogs;
import pep.per.mint.common.data.basic.agent.ResourceUsageLog;
import pep.per.mint.common.data.basic.qmgr.QmgrProperty;
import pep.per.mint.common.data.basic.qmgr.QueueProperty;
import pep.per.mint.common.data.basic.test.InterfaceCallDetail;
import pep.per.mint.common.util.Util;

public class ItemDeserializer extends StdDeserializer<ComMessage> {

	public ItemDeserializer() {
		this(null);
	}
	public ItemDeserializer(Class<?> vc) {
		super(vc);
	}
	/**
	 *
	 */
	private static final long serialVersionUID = 4228364508859388790L;

	String getRequiredFieldAsText(JsonNode node, String field) throws IOException{
		JsonNode cn = node.get(field);
		if(cn == null) throw new IOException(Util.join("Exception:ComMessage field(",field,") value is required."));
		return cn.asText();
	}

	String getFieldAsText(JsonNode node, String field) {
		JsonNode cn = node.get(field);
		String value = cn == null ? "" : cn.asText();
		return value;
	}

	boolean getFieldAsBoolean(JsonNode node, String field) {
		JsonNode cn = node.get(field);
		boolean value = cn == null ? false : cn.asBoolean();
		return value;
	}


	@Override
	public ComMessage deserialize(JsonParser jp, DeserializationContext ctxt)
			throws IOException, JsonProcessingException {
		JsonNode node = jp.getCodec().readTree(jp);


		String id = getFieldAsText(node, "id");
		String startTime = node.get("startTime").asText();
		String endTime = getFieldAsText(node, "endTime");
		String errorCd = getFieldAsText(node, "errorCd");
		String errorMsg = getFieldAsText(node, "errorMsg");
		String errorDetail = getFieldAsText(node, "errorDetail");
		String userId = node.get("userId").asText();
		String appId = getFieldAsText(node, "appId");
		boolean checkSession = getFieldAsBoolean(node, "checkSession");
		JsonNode exNode = node.get("extension") ;
		//---------------------------------------------------------------------------
		//node.get("extension").toString() --> "null" 값이 나와서 수정 함
		//---------------------------------------------------------------------------
		//String extension = (exNode == null) ? "": node.get("extension").toString();
		String extensionValue = (exNode == null || exNode.toString().equals("null")) ? "":exNode.toString();


		ComMessage msg = new ComMessage();

		String msgType = "";
		String serviceCd = "";

		if(extensionValue != null && extensionValue.length()>0){
			ObjectMapper mapper = new ObjectMapper();
			Extension readValue = mapper.readValue(extensionValue, Extension.class);
			msg.setExtension(readValue);
			msgType = msg.getExtension().getMsgType();
			serviceCd =  msg.getExtension().getServiceCd();
		}


		if(ServiceCodeConstant.WS0025.equalsIgnoreCase(serviceCd)) // 에이전트 로그인
		{
			ObjectMapper mapper = new ObjectMapper();
			JsonNode node1 = node.get("responseObject");
			if(node1 != null){
				IIPAgentInfo readValue = mapper.readValue(node1.toString(), IIPAgentInfo.class);
				msg.setResponseObject(readValue);
			}
		}else if(ServiceCodeConstant.WS0020.equalsIgnoreCase(serviceCd)){	// 에이전트 정보 리로드
			ObjectMapper mapper = new ObjectMapper();
			JsonNode node1 = node.get("requestObject");
			if(node1 != null){
				IIPAgentInfo readValue = mapper.readValue(node1.toString(), IIPAgentInfo.class);
				msg.setRequestObject(readValue);
			}

		}else if(ServiceCodeConstant.WS0027.equalsIgnoreCase(serviceCd) ||
				ServiceCodeConstant.WS0028.equalsIgnoreCase(serviceCd) ||
				ServiceCodeConstant.WS0029.equalsIgnoreCase(serviceCd)
				){  // 에이전트 PUSH – CPU LOG / Memory  DISK
			ObjectMapper mapper = new ObjectMapper();
			JsonNode node1 = node.get("requestObject");
			if(node1 != null){
				TypeReference<List<ResourceUsageLog>> mapType = new TypeReference<List<ResourceUsageLog>>() {};
				List<ResourceUsageLog> readValue = mapper.readValue(node1.toString(), mapType);
				msg.setRequestObject(readValue);
			}

		}else if(ServiceCodeConstant.WS0030.equalsIgnoreCase(serviceCd)){ // 프로세스
			ObjectMapper mapper = new ObjectMapper();
			JsonNode node1 = node.get("requestObject");
			if(node1 != null){
				TypeReference<List<ProcessStatusLog>> mapType = new TypeReference<List<ProcessStatusLog>>() {};
				List<ProcessStatusLog> readValue = mapper.readValue(node1.toString(), mapType);
				msg.setRequestObject(readValue);
			}

		}else if(ServiceCodeConstant.WS0031.equalsIgnoreCase(serviceCd)){  // 큐관리자
			ObjectMapper mapper = new ObjectMapper();
			JsonNode node1 = node.get("requestObject");
			if(node1 != null){
				TypeReference<List<QmgrLogs>> mapType = new TypeReference<List<QmgrLogs>>() {};
				List<QmgrLogs> readValue = mapper.readValue(node1.toString(), mapType);
				msg.setRequestObject(readValue);
			}
		}else if(ServiceCodeConstant.WS0034.equalsIgnoreCase(serviceCd)){  // 인터페이스 테스트결과 (IIP 서버, 에이전트 모두 사용)
			ObjectMapper mapper = new ObjectMapper();

			JsonNode requestObjectNode = node.get("requestObject");
			if(requestObjectNode != null){
				//TypeReference<InterfaceCallDetail> mapType = new TypeReference<InterfaceCallDetail>() {};
				InterfaceCallDetail readValue = mapper.readValue(requestObjectNode.toString(), InterfaceCallDetail.class);
				msg.setRequestObject(readValue);
			}

			JsonNode responseObjectNode = node.get("responseObject");
			if(responseObjectNode != null){
				//TypeReference<InterfaceCallDetail> mapType = new TypeReference<InterfaceCallDetail>() {};
				InterfaceCallDetail readValue = mapper.readValue(responseObjectNode.toString(), InterfaceCallDetail.class);
				msg.setResponseObject(readValue);
			}

		 

		}else if(ServiceCodeConstant.WS0050.equalsIgnoreCase(serviceCd)){  // 버전정보
			ObjectMapper mapper = new ObjectMapper();
			JsonNode node1 = node.get("requestObject");
			if(node1 != null){
				TypeReference<List<ApplicationInfo>> mapType = new TypeReference<List<ApplicationInfo>>() {};
				List<QmgrLogs> readValue = mapper.readValue(node1.toString(), mapType);
				msg.setRequestObject(readValue);
			}
		}else if(ServiceCodeConstant.WS0051.equalsIgnoreCase(serviceCd)) {
			ObjectMapper mapper = new ObjectMapper();

			JsonNode requestObjNode = node.get("requestObject");
			if(requestObjNode != null){
				Map readValue = mapper.readValue(requestObjNode.toString(), Map.class);
				msg.setRequestObject(readValue);
			}

			JsonNode node1 = node.get("responseObject");
			if(node1 != null){
				TypeReference<List<QmgrProperty>> mapType = new TypeReference<List<QmgrProperty>>() {};
				List<QmgrProperty> readValue = mapper.readValue(node1.toString(), mapType);
				msg.setResponseObject(readValue);
			}

		}else if(ServiceCodeConstant.WS0052.equalsIgnoreCase(serviceCd)) {
			ObjectMapper mapper = new ObjectMapper();

			JsonNode requestObjNode = node.get("requestObject");
			if(requestObjNode != null){
				Map readValue = mapper.readValue(requestObjNode.toString(), Map.class);
				msg.setRequestObject(readValue);
			}

			JsonNode node1 = node.get("responseObject");
			if(node1 != null){
				TypeReference<List<QueueProperty>> mapType = new TypeReference<List<QueueProperty>>() {};
				List<QueueProperty> readValue = mapper.readValue(node1.toString(), mapType);
				msg.setResponseObject(readValue);
			}
		}else if(ServiceCodeConstant.WS0053.equalsIgnoreCase(serviceCd)) {
			ObjectMapper mapper = new ObjectMapper();

			JsonNode requestObjNode = node.get("requestObject");
			if(requestObjNode != null){
				Map readValue = mapper.readValue(requestObjNode.toString(), Map.class);
				msg.setRequestObject(readValue);
			}

			JsonNode node1 = node.get("responseObject");
			if(node1 != null){
				TypeReference<QueueProperty> mapType = new TypeReference<QueueProperty>() {};
				QueueProperty readValue = mapper.readValue(node1.toString(), mapType);
				msg.setResponseObject(readValue);
			}
		}else if(ServiceCodeConstant.WS0054.equalsIgnoreCase(serviceCd)) {
			ObjectMapper mapper = new ObjectMapper();

			JsonNode requestObjNode = node.get("requestObject");
			if(requestObjNode != null){
				ConfigInfo readValue = mapper.readValue(requestObjNode.toString(), ConfigInfo.class);
				msg.setRequestObject(readValue);
			}

			JsonNode node1 = node.get("responseObject");
			if(node1 != null){
				ConfigInfo readValue = mapper.readValue(node1.toString(), ConfigInfo.class);
				msg.setResponseObject(readValue);
			}
		}else{
			ObjectMapper mapper = new ObjectMapper();

			JsonNode requestObject = node.get("requestObject");
			if(requestObject != null){
				Object readValue = mapper.readValue(requestObject.toString(), Object.class);
				msg.setRequestObject(readValue);
			}
			
			JsonNode responseObject = node.get("responseObject");
			if(responseObject != null){
				Object readValue = mapper.readValue(responseObject.toString(), Object.class);
				msg.setResponseObject(readValue);
			}
			
		}

		msg.setId(id);
		msg.setStartTime(startTime);
		msg.setEndTime(endTime);
		msg.setErrorCd(errorCd);
		msg.setErrorMsg(errorMsg);
		msg.setErrorDetail(errorDetail);
		msg.setUserId(userId);
		msg.setAppId(appId);
		msg.setCheckSession(checkSession);

		return msg;
	}

}
