package pep.per.mint.batch.mapper.op;

import java.util.List;
import java.util.Map;

import pep.per.mint.common.data.basic.sms.Sms;

public interface SendSmsJobMapper {
	public List<Sms> getNotYetSendSmses(Map params) throws Exception;

	public int updateSmsSendResult(Sms sms) throws Exception;

}
