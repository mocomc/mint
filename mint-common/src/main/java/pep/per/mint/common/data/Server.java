package pep.per.mint.common.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")
@JsonSubTypes({
        @JsonSubTypes.Type(value=MQServer.class),
        @JsonSubTypes.Type(value=DatabaseServer.class),
        @JsonSubTypes.Type(value=SocketServer.class),
        @JsonSubTypes.Type(value=HTTPServer.class)
})
public class Server extends CacheableObject{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1644974957458376064L;

	public static final int SERVER_TYPE_HTTP = 0;
	public static final int SERVER_TYPE_SOCKET = 1;
	public static final int SERVER_TYPE_DATABASE = 2;
	public static final int SERVER_TYPE_MQ = 3;
	
	@JsonProperty
	String name;
	
	@JsonProperty
	int type;
	
	@JsonProperty
	String description;
	
	@JsonProperty
	String regDate;
	
	@JsonProperty
	String regId;
	
	@JsonProperty
	String modDate;
	
	@JsonProperty
	String modId;

	public Server() {
		
		if(HTTPServer.class == this.getClass()){
			type = SERVER_TYPE_HTTP;
		}else if(SocketServer.class == this.getClass()){
			type = SERVER_TYPE_SOCKET;
		}else if(MQServer.class == this.getClass()){
			type = SERVER_TYPE_MQ;
		}else if(DatabaseServer.class == this.getClass()){
			type = SERVER_TYPE_DATABASE;
		}
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getRegDate() {
		return regDate;
	}

	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}

	public String getRegId() {
		return regId;
	}

	public void setRegId(String regId) {
		this.regId = regId;
	}

	public String getModDate() {
		return modDate;
	}

	public void setModDate(String modDate) {
		this.modDate = modDate;
	}

	public String getModId() {
		return modId;
	}

	public void setModId(String modId) {
		this.modId = modId;
	}
	
	@Override
	public String toString() {
		return "{id:" + id + ",type:" + type + "}";
	}
	
}
