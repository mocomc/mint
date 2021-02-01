package pep.per.mint.inhouse.mail.samsung;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")
/**
 * "from":{"email":"inah.yoo@samsungsquare.com"},
 * @author whoana
 *
 */
public class FromInfo implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 3844119293311480402L;

	@JsonProperty
	String email = "";

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}



}
