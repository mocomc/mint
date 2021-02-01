package pep.per.mint.inhouse.mail.samsung;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")
/**
 *      "recipients":[
 *        {"email":"whoana@gmail.com","recipientType":"TO"},
 *        {"email":"inah.yoo@samsungsquare.com","recipientType":"TO"}
 *  	]
 * @author whoana
 *
 */
public class RecipientInfo implements Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = -2985123370953431679L;

	@JsonProperty
	String email = "";

	@JsonProperty
	String recipientType = "TO";

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRecipientType() {
		return recipientType;
	}

	public void setRecipientType(String recipientType) {
		this.recipientType = recipientType;
	}


}
