package pep.per.mint.batch.job;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pep.per.mint.batch.mapper.co.JobMapper;
import pep.per.mint.common.data.basic.batch.ZobResult;
import pep.per.mint.common.data.basic.batch.ZobSchedule;
import pep.per.mint.common.util.Util;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

/**
 * Created by Solution TF on 15. 11. 11..
 */
@Service
public abstract  class JobServiceCaller {

    public abstract void run(Map params) throws Exception;

    @Autowired
    JobMapper jobMapper;



    public void call(Map params) throws  Exception{

    	String implClass = (params == null || params.get("implClass") == null) ? this.getClass().getName() : (String) params.get("implClass");

        List<ZobSchedule> list = jobMapper.getJobScheduleListByClass(implClass);

        if(list == null || list.size() == 0) {
            run(params);
        }else{
            ZobSchedule schedule = list.get(0);
            ZobResult zobResult = new ZobResult();
            zobResult.setGroupId(schedule.getZob().getGroupId());
            zobResult.setProcessId(this.getClass().getName());
            zobResult.setJobId(schedule.getZob().getJobId());
            zobResult.setScheduleId(schedule.getScheduleId());
            zobResult.setScheduleValue(schedule.getValue());
            zobResult.setStartDate(Util.getFormatedDate());
            String caller = (String)params.get("caller");
            zobResult.setCaller(caller);

            try{
                run(params);
                zobResult.setResultCd(ZobResult.RESULT_CD_SUCCESS);
                zobResult.setProcessStatus(ZobResult.PROCESS_STATUS_END);
                zobResult.setEndDate(Util.getFormatedDate());
                jobMapper.insertJobResult(zobResult);
            }catch (Exception e){
                zobResult.setProcessStatus(ZobResult.PROCESS_STATUS_END);
                zobResult.setResultCd(ZobResult.RESULT_CD_ERROR);

                String errorDetail = "";
                PrintWriter pw = null;
                try {
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    pw = new PrintWriter(baos);
                    e.printStackTrace(pw);
                    if (pw != null) errorDetail = baos.toString();
                } finally {
                    if (pw != null) pw.close();
                }

                zobResult.setErrorMsg(errorDetail);
                zobResult.setEndDate(Util.getFormatedDate());
                jobMapper.insertJobResult(zobResult);
                throw e;
            }
        }
    }
}
