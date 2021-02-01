/**
 * 
 */
package pep.per.mint.common.data;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Properties;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * @author mint
 *
 */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")
public class MessageSet extends CacheableObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8303260193065993789L;

	public static final int MSG_FORMAT_TYPE_FIXED = 0;
	public static final int MSG_FORMAT_TYPE_JSON = 1;
	public static final int MSG_FORMAT_TYPE_XML = 2;
	public static final int MSG_FORMAT_TYPE_DELIM = 3;
	
	public static final int MSG_USAGE_NORMAL = 0;
	public static final int MSG_USAGE_COMMON = 1;
	
	
	@JsonProperty
	String name;
	
	@JsonProperty
	int usage;
	
	@JsonProperty
	int type;
	
	@JsonProperty
	String ccsid;
	
	@JsonProperty
	String name2;
	
	@JsonProperty
	Properties properties;
	
	@JsonProperty
	String regDate;
	
	@JsonProperty
	String regId;
	
	@JsonProperty
	String modDate;
	
	@JsonProperty
	String modId;
	
	@JsonProperty
	LinkedHashMap<Object, FieldDefinition> fieldDefinitionMap = new LinkedHashMap<Object, FieldDefinition>();

	@JsonProperty
	LinkedHashMap<Object, FieldDefinition> fieldDefinitionPathMap = new LinkedHashMap<Object, FieldDefinition>();

	@JsonProperty
	LinkedHashMap<Object, FieldDefinition> fieldDefinitionIdMap = new LinkedHashMap<Object, FieldDefinition>();
	
	@JsonProperty
	LinkedHashMap<Integer, List<SystemField>> systemFieldMap = new LinkedHashMap<Integer, List<SystemField>>();

	
	public List<SystemField> getSystemField(Integer key){
		return systemFieldMap.get(key);
	}
	
	public SystemField getSystemFieldAtFirst(Integer key){
		List<SystemField> list = systemFieldMap.get(key);
		return list == null ? null : list.get(0);
	}
	
	public LinkedHashMap<Integer, List<SystemField>> getSystemFieldMap(){
		return systemFieldMap;
	}
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the usage
	 */
	public int getUsage() {
		return usage;
	}

	/**
	 * @param usage the usage to set
	 */
	public void setUsage(int usage) {
		this.usage = usage;
	}

	/**
	 * @return the type
	 */
	public int getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(int type) {
		this.type = type;
	}

	/**
	 * @return the ccsid
	 */
	public String getCcsid() {
		return ccsid;
	}

	/**
	 * @param ccsid the ccsid to set
	 */
	public void setCcsid(String ccsid) {
		this.ccsid = ccsid;
	}
	
	
	
	/**
	 * @return the name2
	 */
	public String getName2() {
		return name2;
	}

	/**
	 * @param name2 the name2 to set
	 */
	public void setName2(String name2) {
		this.name2 = name2;
	}

	/**
	 * @return the regDate
	 */
	public String getRegDate() {
		return regDate;
	}

	/**
	 * @param regDate the regDate to set
	 */
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}

	/**
	 * @return the regId
	 */
	public String getRegId() {
		return regId;
	}

	/**
	 * @param regId the regId to set
	 */
	public void setRegId(String regId) {
		this.regId = regId;
	}

	/**
	 * @return the modDate
	 */
	public String getModDate() {
		return modDate;
	}

	/**
	 * @param modDate the modDate to set
	 */
	public void setModDate(String modDate) {
		this.modDate = modDate;
	}

	/**
	 * @return the modId
	 */
	public String getModId() {
		return modId;
	}

	/**
	 * @param modId the modId to set
	 */
	public void setModId(String modId) {
		this.modId = modId;
	}

	/**
	 * @return the fieldDefinitionMap
	 */
	public LinkedHashMap<Object, FieldDefinition> getFieldDefinitionMap() {
		return fieldDefinitionMap;
	}

	/**
	 * @param fieldDefinitionMap the fieldDefinitionMap to set
	 */
	public void setFieldDefinitionMap(
			LinkedHashMap<Object, FieldDefinition> fieldDefinitionMap) {
		this.fieldDefinitionMap = fieldDefinitionMap;
	}

	/**
	 * @return the properties
	 */
	public Properties getProperties() {
		return properties;
	}

	/**
	 * @param properties the properties to set
	 */
	public void setProperties(Properties properties) {
		this.properties = properties;
	}

	/**
	 * @return the fieldDefinitionPathMap
	 */
	public LinkedHashMap<Object, FieldDefinition> getFieldDefinitionPathMap() {
		return fieldDefinitionPathMap;
	}

	/**
	 * @param fieldDefinitionPathMap the fieldDefinitionPathMap to set
	 */
	public void setFieldDefinitionPathMap(
			LinkedHashMap<Object, FieldDefinition> fieldDefinitionPathMap) {
		this.fieldDefinitionPathMap = fieldDefinitionPathMap;
	}

	/**
	 * @return the fieldDefinitionIdMap
	 */
	public LinkedHashMap<Object, FieldDefinition> getFieldDefinitionIdMap() {
		return fieldDefinitionIdMap;
	}

	/**
	 * @param fieldDefinitionIdMap the fieldDefinitionIdMap to set
	 */
	public void setFieldDefinitionIdMap(
			LinkedHashMap<Object, FieldDefinition> fieldDefinitionIdMap) {
		this.fieldDefinitionIdMap = fieldDefinitionIdMap;
	}
	
	
	
	
	
}
