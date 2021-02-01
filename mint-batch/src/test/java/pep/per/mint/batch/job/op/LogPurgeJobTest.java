package pep.per.mint.batch.job.op;

import static org.junit.Assert.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
"classpath:/config/batch-context.xml"})
public class LogPurgeJobTest {

	@Autowired
	LogPurgeService logPurgeService;



	@Autowired
	@Qualifier("sqlSessionFactiory")
	SqlSessionFactory sqlSessionFactory;


	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Test
	public void test() {

		SqlSession  sqlSession = sqlSessionFactory.openSession(false);

		 ResultSet restEmail;
		 ResultSet restSMS;
		try {
			String SCHEMA = sqlSession.getConnection().getMetaData().getUserName();
			System.out.println(SCHEMA);
			restEmail = sqlSession.getConnection().getMetaData().getTables(null, SCHEMA, "TOP0901",  new String[] { "TABLE" });

			System.out.println(restEmail.getRow());
			while (restEmail.next()) {
			       	 System.out.println( restEmail.getString("TABLE_CAT"));
			       	System.out.println( restEmail.getString("TABLE_SCHEM"));
			       	System.out.println( restEmail.getString("TABLE_NAME"));
			       	System.out.println( restEmail.getString("TABLE_TYPE"));
			       	System.out.println( restEmail.getString("REMARKS"));
//			       	System.out.println( restEmail.getString("TYPE_CAT"));
//			       	System.out.println( restEmail.getString("TYPE_SCHEM"));
//			       	System.out.println( restEmail.getString("TYPE_NAME"));
//			       	System.out.println( restEmail.getString("SELF_REFERENCING_COL_NAME"));
//			       	System.out.println( restEmail.getString("REF_GENERATION"));
			        }
//


	         restSMS = sqlSession.getConnection().getMetaData().getTables(null, SCHEMA, "TOP0904",  new String[] { "TABLE" });

			System.out.println(restSMS.getRow());
			while (restSMS.next()){
				 System.out.println( restSMS.getString("TABLE_NAME"));
	         }
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

//		Map params = new HashMap();
//		Calendar toCal = new GregorianCalendar();
//		toCal.add(Calendar.DAY_OF_MONTH, -10);
//		long toHour = toCal.getTimeInMillis();
//		params.put("toDate", Util.getFormatedDate(toHour, "yyyyMMdd"));
//		try {
//			logPurgeService.run(params);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}

}
