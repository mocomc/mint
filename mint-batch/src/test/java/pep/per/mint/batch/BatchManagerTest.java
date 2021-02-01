package pep.per.mint.batch;



import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


/**
 * Created by Solution TF on 15. 11. 5..
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:/config/batch-context.xml"})
public class BatchManagerTest {

    Logger logger = LoggerFactory.getLogger(BatchManagerTest.class);

    @Autowired
    BatchManager batchManager;


    @Test
    public void testLoadAndStartBatch()  {

        try{

        	logger.debug("IntegerMax:"+Double.MAX_VALUE);

            //batchManager.startBatchManager();
            Thread.sleep(Long.MAX_VALUE);

        }catch (Throwable t){
            logger.error("",t);
        }
    }

//    @Test
//    public void testTummy(){
//    	try{
//
//    		Calendar fromCal = new GregorianCalendar();
//    		fromCal.add(Calendar.HOUR_OF_DAY, -1);
//            long fromDate = fromCal.getTimeInMillis();
//
//            Calendar toCal = new GregorianCalendar();
//    		//toCal.add(Calendar.HOUR_OF_DAY, +1);
//            long toDate = toCal.getTimeInMillis();
//
//            Map<String,String> params = new HashMap<String,String>();
//            params.put("fromDate", Util.getFormatedDate(fromDate, "yyyyMMddHH"));
//            params.put("toDate", Util.getFormatedDate(toDate, "yyyyMMddHH"));
//
//            logger.debug(Util.toJSONString(params));
//            logger.debug(Util.getFormatedDate(toDate, "yyyyMMddHH").substring(0, 8));
//            logger.debug(Util.getFormatedDate(toDate, "yyyyMMddHH").substring(8));
//    	}catch(Exception e){
//    		e.printStackTrace();
//    	}
//    }

}
