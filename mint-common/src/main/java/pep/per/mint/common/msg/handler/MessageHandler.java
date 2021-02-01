package pep.per.mint.common.msg.handler;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;

import pep.per.mint.common.data.basic.ComMessage;

public class MessageHandler {

	ObjectMapper mapper = new ObjectMapper();
	SimpleModule module = new SimpleModule();

	public void setDeserializer(StdDeserializer<ComMessage> deserializer) {
		module.addDeserializer(ComMessage.class, deserializer);
		mapper.registerModule(module);
	}

	public ComMessage deserialize(String msg) throws JsonParseException, JsonMappingException, IOException{
		ComMessage readValue = mapper.readValue(msg, ComMessage.class);
		return readValue;
	}

	public String serialize(ComMessage msg) throws JsonProcessingException{
		String jsonInString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(msg);
		return jsonInString;
	}

	public String serialize(Object msg) throws JsonProcessingException{
		String jsonInString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(msg);
		return jsonInString;
	}

}
