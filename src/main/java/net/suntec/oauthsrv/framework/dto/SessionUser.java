package net.suntec.oauthsrv.framework.dto;

import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SessionUser implements HttpSessionBindingListener {
	private String iAutoLoginName;
	private String appstoreAccessToken;
	private String appstoreRefreshToken;
	private final Logger logger = LoggerFactory.getLogger(SessionUser.class);

	public String getAppstoreAccessToken() {
		return appstoreAccessToken;
	}

	public void setAppstoreAccessToken(String appstoreAccessToken) {
		this.appstoreAccessToken = appstoreAccessToken;
	}

	public String getAppstoreRefreshToken() {
		return appstoreRefreshToken;
	}

	public void setAppstoreRefreshToken(String appstoreRefreshToken) {
		this.appstoreRefreshToken = appstoreRefreshToken;
	}

	public String getIAutoLoginName() {
		return iAutoLoginName;
	}

	public void setIAutoLoginName(String autoLoginName) {
		iAutoLoginName = autoLoginName;
	}

	@Override
	public void valueBound(HttpSessionBindingEvent event) {
		logger.info(event.getName() + " valueBound ........... ");

	}

	@Override
	public void valueUnbound(HttpSessionBindingEvent event) {
		logger.info(event.getName() + " valueUnbound ........... ");
	}
}
