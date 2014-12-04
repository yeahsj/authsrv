package net.suntec.oauthsrv.framework;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AppSessionListener implements HttpSessionListener {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public void sessionCreated(HttpSessionEvent se) {
		// TODO Auto-generated method stub
		
		logger.info("create session " + se.getSession().getId() + " , time is " + se.getSession().getMaxInactiveInterval() );
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		// TODO Auto-generated method stub
		logger.info("session destory " + se.getSession().getId());
	}

}
