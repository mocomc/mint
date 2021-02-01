package pep.per.mint.batch.job.su;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pep.per.mint.batch.job.JobServiceCaller;
import pep.per.mint.batch.mapper.su.TSU0501JobMapper;
import pep.per.mint.common.data.basic.batch.UnknownInterfaceInfo;
@SuppressWarnings({ "rawtypes", "unchecked" })
@Service
public class TSU0501Service extends JobServiceCaller {

	@Autowired
	TSU0501JobMapper tsu0501JobMapper;
	
    @Transactional
	public void run(Map params) throws Exception {

		List<UnknownInterfaceInfo> infoList = tsu0501JobMapper.getUnknownInterfaceInfoList(params);
		if (infoList == null) return;
		
		for (UnknownInterfaceInfo info : infoList) {
			if (tsu0501JobMapper.getMatchingCount(info.getInterfaceId()) == 0) {
				tsu0501JobMapper.insertUnknownInterfaceInfo(info);
			} else {
				tsu0501JobMapper.updateUnknownInterfaceInfo(info);
			}
		}
	}

}
