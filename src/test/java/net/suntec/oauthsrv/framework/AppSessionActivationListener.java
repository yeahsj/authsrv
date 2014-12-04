package net.suntec.oauthsrv.framework;

import javax.servlet.http.HttpSessionActivationListener;
import javax.servlet.http.HttpSessionEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AppSessionActivationListener implements HttpSessionActivationListener {
	Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public void sessionDidActivate(HttpSessionEvent se) {
		// TODO Auto-generated method stub
		logger.info( se.getSession().getId() + " is sessionDidActivate ................... " );
	}

	@Override
	public void sessionWillPassivate(HttpSessionEvent se) {
		// TODO Auto-generated method stub
		logger.info( se.getSession().getId() + " is sessionWillPassivate ................... " );
	}

}
