package pep.per.mint.common.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")
public class UserDefineRule extends Rule{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8309884006601242760L;
	
	@JsonProperty
	String implementClass;

	
	@JsonProperty
	String implementMethod;

	@JsonProperty
	String classPathFile;

	
	public UserDefineRule() {
		super();
	}
	
	public String getImplementClass() {
		return implementClass;
	}

	public void setImplementClass(String implementClass) {
		this.implementClass = implementClass;
	}

	/**
	 * @return the implementMethod
	 */
	public String getImplementMethod() {
		return implementMethod;
	}

	/**
	 * @param implementMethod the implementMethod to set
	 */
	public void setImplementMethod(String implementMethod) {
		this.implementMethod = implementMethod;
	}

	/**
	 * @return the classPathFile
	 */
	public String getClassPathFile() {
		return classPathFile;
	}

	/**
	 * @param classPathFile the classPathFile to set
	 */
	public void setClassPathFile(String classPathFile) {
		this.classPathFile = classPathFile;
	}
	
	
}
