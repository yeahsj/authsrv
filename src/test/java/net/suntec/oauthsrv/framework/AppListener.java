package net.suntec.oauthsrv.framework;

import java.util.Enumeration;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AppListener implements ServletContextListener {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		logger.info(" destroyed ............ ");
	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		// TODO Auto-generated method stub
		ServletContext sc = sce.getServletContext();
		Enumeration<String> e = sc.getAttributeNames();
		while (e.hasMoreElements()) {
			String name = e.nextElement();
			logger.info( name + " = " + sc.getAttribute(name) );
		}
		logger.info(" start init ............ " + sc.getServerInfo());
	}

}
