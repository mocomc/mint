/**
 * Copyright 2018 Mocomsys Inc.  All Rights Reserved.
 */
package pep.per.mint.batch.mapper.op;

import java.util.List;
import java.util.Map;

import pep.per.mint.common.data.basic.email.Email;

/**
 * <pre>
 * pep.per.mint.batch.mapper.op
 * SimpleSendMailMapper.java
 * </pre>
 * @author whoana
 * @date Jan 10, 2019
 */
public interface SimpleSendMailMapper {

	public List<Email> getNotYetSendEmails(Map params) throws Exception;

	public int updateEmail(Email email) throws Exception;
}
