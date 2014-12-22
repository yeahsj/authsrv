package net.suntec.oauthsrv.framework.dto;

import java.io.Serializable;

import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * 
 * @项目名称: OauthSrv
 * @功能描述:
 * @当前版本： 1.0
 * @创建时间: 2014年12月4日 下午5:52:31
 * @author: <a href="mailto:yeahsj@yahoo.com.cn">yeahsj</a>
 * @修改历史:
 */
public class SessionUser implements HttpSessionBindingListener,Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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
