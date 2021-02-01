package pep.per.mint.batch.manager.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.FrameworkServlet;

import pep.per.mint.batch.BatchManager;

import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;

/**
 * 
 * <pre>
 * pep.per.mint.batch.manager.listener
 * ResourceUnregisterListener.java
 * </pre>
 * 
 * @author whoana
 * @date Feb 22, 2019
 */
public class BatchResourceUnregisterListener implements ServletContextListener {

	private static final Logger logger = LoggerFactory.getLogger(BatchResourceUnregisterListener.class);

	@Override
	public void contextInitialized(ServletContextEvent sce) {
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {

		try {
			WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(sce.getServletContext(),
					FrameworkServlet.SERVLET_CONTEXT_PREFIX + "MintFrontWebAppServlet");
			BatchManager batchManager = (BatchManager) context.getBean("batchManager");
			batchManager.stopBachManager();
		} catch (Exception e) {
			logger.error("", e);
		}

		Enumeration<Driver> drivers = DriverManager.getDrivers();
		while (drivers.hasMoreElements()) {
			try {
				DriverManager.deregisterDriver(drivers.nextElement());
			} catch (SQLException e) {
				logger.error("", e);
			}
		}
	}

}
