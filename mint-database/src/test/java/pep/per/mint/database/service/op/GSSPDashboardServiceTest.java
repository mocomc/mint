package pep.per.mint.database.service.op;
 
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
 
import pep.per.mint.common.util.Util;



@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
		//"file:./src/test/resources/com/mocomsys/mint/database/config/database-context-test.xml"})
		"classpath:/config/database-context.xml"})
		//"file:./src/test/resources/config/database-context-test.xml"}
public class GSSPDashboardServiceTest extends DashboardService {

	@Autowired
	GSSPDashboardService service;

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetDelayListTop() throws Exception {
        Map params = new HashMap();
        params.put("checkTime", "10");
        params.put("userId", "iip");
		List<Map> list = service.getDelayListTop(params);
		System.out.println(Util.toJSONString(list));
	}

    @Test
    public void testGetDeadPosCount() throws Exception{
        Integer count = service.getDeadPosCount();
        System.out.println("dead pos cnt:" + count);
    }
    
    @Test
    public void testGetDeadPosList() throws Exception{
        List<Map> list = service.getDeadPosList();
        System.out.println(Util.toJSONString(list));
    }
    
    @Test
    public void testGetNoTransactionPosCount() throws Exception{
        Integer count = service.getNoTransactionPosCount();
        System.out.println("count of pos that have no transaction :" + count);
    }
    
    @Test
    public void testGetNoTransactionPosList() throws Exception{
        List<Map> list = service.getNoTransactionPosList();
        System.out.println(Util.toJSONString(list));
    }
    
    @Test
    public void testGetTotalPosTransactionCount() throws Exception{
        Map params = new HashMap();
        params.put("startDate", "201711291540");
        params.put("endDate", "201711291550");
        Integer count = service.getTotalPosTransactionCount(params);
        System.out.println("count of pos that have no transaction :" + count);
    }
    
     @Test
    public void testGetRealTimeFavoriteCount() throws Exception{
        Map params = new HashMap();
        params.put("userId", "iip"); 
        List<Map> res = service.getRealTimeFavoriteCount(params);
        System.out.println("reslut :" + Util.toJSONString(res));
    }

}
