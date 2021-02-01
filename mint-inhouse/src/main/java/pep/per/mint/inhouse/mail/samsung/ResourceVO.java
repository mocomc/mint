package pep.per.mint.inhouse.mail.samsung;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")
/**
 *    "resourceVO":{
 *    	"email":"inah.yoo@samsungsquare.com",
 *      "localeStr":"ko_KR",
 *      "encoding":"utf-8",
 *      "timeZone": "GMT+9"
 *    }
 * @author whoana
 *
 */
public class ResourceVO implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -802905909545028306L;

	@JsonProperty
	String email = "";

	@JsonProperty
	String localeStr = "ko_KR";

	@JsonProperty
	String encoding = "utf-8";

	@JsonProperty
	String timeZone = "GMT+9";

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLocaleStr() {
		return localeStr;
	}

	public void setLocaleStr(String localeStr) {
		this.localeStr = localeStr;
	}

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public String getTimeZone() {
		return timeZone;
	}

	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}


}
