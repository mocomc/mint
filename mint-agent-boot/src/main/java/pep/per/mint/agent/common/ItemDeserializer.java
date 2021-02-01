package pep.per.mint.agent.common;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import pep.per.mint.agent.util.Util;

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



			ObjectMapper mapper = new ObjectMapper();
			JsonNode node1 = node.get("requestObject");
			if(node1 != null){
				Object readValue = mapper.readValue(node1.toString(), Object.class);
				msg.setRequestObject(readValue);
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
