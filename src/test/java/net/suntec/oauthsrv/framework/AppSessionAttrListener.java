package net.suntec.oauthsrv.framework;

import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AppSessionAttrListener implements HttpSessionAttributeListener {
	Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public void attributeAdded(HttpSessionBindingEvent se) {
		// TODO Auto-generated method stub
		logger.info( se.getName() + " is attributeAdded ................... " );
	}

	@Override
	public void attributeRemoved(HttpSessionBindingEvent se) {
		// TODO Auto-generated method stub
		logger.info( se.getName() + " is attributeRemoved ............... " );
	}

	@Override
	public void attributeReplaced(HttpSessionBindingEvent se) {
		// TODO Auto-generated method stub
		logger.info( se.getName() + " is attributeReplaced ................... " );
	}

}
