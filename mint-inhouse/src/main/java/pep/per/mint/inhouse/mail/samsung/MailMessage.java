package pep.per.mint.inhouse.mail.samsung;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="objectType")
public class MailMessage implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 4339085098700770152L;

	@JsonProperty
	ResourceVO resourceVO;

	@JsonProperty
	SendMailVO sendMailVO;

	public ResourceVO getResourceVO() {
		return resourceVO;
	}

	public void setResourceVO(ResourceVO resourceVO) {
		this.resourceVO = resourceVO;
	}

	public SendMailVO getSendMailVO() {
		return sendMailVO;
	}

	public void setSendMailVO(SendMailVO sendMailVO) {
		this.sendMailVO = sendMailVO;
	}



}
