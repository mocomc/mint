package pep.per.mint.batch.job.an;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pep.per.mint.batch.job.JobServiceCaller;
import pep.per.mint.batch.mapper.an.TAN0201JobMapper;
import pep.per.mint.common.data.basic.batch.TransactionLogScaleInfo;
@SuppressWarnings({ "rawtypes", "unchecked" })
@Service
public class TAN0201Service extends JobServiceCaller {

	@Autowired
	TAN0201JobMapper tan0201JobMapper;
	
    @Transactional
	public void run(Map params) throws Exception {
		List<TransactionLogScaleInfo> infoList = tan0201JobMapper.getTxLogScaleInfoList(params);
		for (TransactionLogScaleInfo info : infoList) {
			tan0201JobMapper.updateTxLogScaleInfo(info);
		}
	}

}
