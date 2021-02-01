package pep.per.mint.batch.job.su;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pep.per.mint.batch.job.JobServiceCaller;
import pep.per.mint.batch.mapper.su.TSU0502JobMapper;
import pep.per.mint.common.data.basic.batch.InterfaceCountSummary;
@SuppressWarnings({ "rawtypes", "unchecked" })
@Service
public class TSU0502Service extends JobServiceCaller {

	@Autowired
	TSU0502JobMapper tsu0502JobMapper;
	
    @Transactional
	public void run(Map params) throws Exception {

		List<InterfaceCountSummary> summaryList = tsu0502JobMapper.selectSummary(params);
		if (summaryList == null) return;
		String curHour = new SimpleDateFormat("HH").format(new Date());
		
		for (InterfaceCountSummary ics : summaryList) {
			
			int hFactor = 0, dFactor = 0;
			float hDelta = ics.getTodayCurHour() - ics.getYesterdayCurHour();
			float dDelta = ics.getTodayTotal() - ics.getYesterdayTotal();
			
			if (hDelta > 0 && ics.getYesterdayCurHour() > 0) {
				hFactor = (int)(hDelta / ics.getYesterdayCurHour() * 100);
			}
			if (dDelta > 0 && ics.getYesterdayTotal() > 0) {
				dFactor = (int)(dDelta / ics.getYesterdayTotal() * 100);
			}

			String subject = ics.getIntegrationId();
			String content = ics.getRequirementName();
			if (hFactor >= 30 && dFactor < 30) {
				subject = subject + " 동시간대 처리량 " + hFactor + "% 증가";
				content = content + " " + curHour + "시 기준 (어제 " + ics.getYesterdayCurHour() + "건 / 오늘 " + ics.getTodayCurHour() + "건)";
			} else if (dFactor >= 30 && hFactor < 30) {
				subject = subject + " 전체 처리량 " + hFactor + "% 증가";
				content = content + " " + curHour + "시 까지 (어제 " + ics.getYesterdayTotal() + "건 / 오늘 " + ics.getTodayTotal() + "건)";
			} else if (hFactor >= 30 && dFactor >= 30) {
				subject = subject + " 처리량 증가";
				content = content + " " + curHour + "시 동시간대 (어제 " 
						+ ics.getYesterdayCurHour() + "건 / 오늘 " + ics.getTodayCurHour() + "건), "
						+ curHour + "까지 전체 (어제 " + ics.getYesterdayTotal() + "건 / 오늘 " + ics.getTodayTotal() + "건)";
			} else {
				continue;
			}
			ics.setSubject(subject);
			ics.setContent(content);
			
			tsu0502JobMapper.insertTSU0502(ics);
			
		}
		
	}

}
