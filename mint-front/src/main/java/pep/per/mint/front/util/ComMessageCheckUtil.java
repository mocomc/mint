package pep.per.mint.front.util;

import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import pep.per.mint.common.data.basic.*;
import pep.per.mint.common.data.basic.dataset.DataField;
import pep.per.mint.common.data.basic.dataset.DataSet;
import pep.per.mint.common.exception.RequiredFieldsException;
import pep.per.mint.common.util.RequiredFields;
import pep.per.mint.common.util.Util;
import pep.per.mint.front.exception.ControllerException;

/**
 * <pre>
 * pep.per.mint.front.util
 * ComMessageCheckUtil.java
 * </pre>
 * @author whoana
 * @date Jun 13, 2019
 */
@SuppressWarnings({ "rawtypes" })
public class ComMessageCheckUtil {

	private static final Logger logger = LoggerFactory.getLogger(ComMessageCheckUtil.class);

	/**
	 *
	 * @param serviceId
	 * @param requirement
	 * @param messageSource
	 * @param locale
	 * @throws ControllerException
	 */
	public static void check(String serviceId, ComMessage comMessage, ReloadableResourceBundleMessageSource messageSource,  Locale locale) throws ControllerException {
		String errorCd = "";
		String errorMsg = "";

		if(Util.isEmpty(comMessage.getAppId())){
			errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.commsg.serviceid.check.fail", locale);
			errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.commsg.serviceid.check.fail", null, locale);
			throw new ControllerException(errorCd, errorMsg);
		}

		if(!comMessage.getAppId().equals(serviceId)) {
			errorCd = MessageSourceUtil.getErrorCd(messageSource, "error.cd.commsg.serviceid.check.fail", locale);
			errorMsg = MessageSourceUtil.getErrorMsg(messageSource, "error.msg.commsg.serviceid.check.fail", null, locale);
			throw new ControllerException(errorCd, errorMsg);
		}


	}


}
