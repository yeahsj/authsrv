package net.suntec.oauthsrv.framework.dto;

import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

import net.suntec.oauthsrv.dto.AppConfig;

import org.scribe.model.ProviderUser;
import org.scribe.model.Token;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * 
 * @项目名称: OauthSrv
 * @功能描述: 
 *        认证流程中需要在本服务器上保存到session中的变量.它和OauthStatusParamDTO都是在认证流程需要保持的状态，两者的区别在于
 *        : 它是流程中产生的一些状态,而OauthStatusParamDTO是在流程开启前由用户输入的参数.
 * @当前版本： 1.0
 * @创建时间: 2014-5-27 下午12:27:01
 * @author: <a href="mailto:yeahsj@gmail.com">yeahsj</a>
 * @修改历史:
 */
public class OauthFlowStatus implements HttpSessionBindingListener {

	Logger logger = LoggerFactory.getLogger(getClass());
	private Token accessToken;

	private AppConfig appConfig;
	private ProviderUser providerUser; // 获取token的同时,获取用户信息

	// parameter
	// private String providerUserId;
	// private String name;
	// private String nickname;
	// private String eMail;

	// communication parameters
	private String oAuthToken;
	private String oAuthSecret;
	private String oAuthRawResponse;
	private String oAuthVerifier;
	// result, 临时变量
	private String oAuthAuthorizationUrl;

	public Token getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(Token accessToken) {
		this.accessToken = accessToken;
	}

	public AppConfig getAppConfig() {
		return appConfig;
	}

	public void setAppConfig(AppConfig appConfig) {
		this.appConfig = appConfig;
	}

	public String getOAuthToken() {
		return oAuthToken;
	}

	public void setOAuthToken(String authToken) {
		oAuthToken = authToken;
	}

	public String getOAuthSecret() {
		return oAuthSecret;
	}

	public void setOAuthSecret(String authSecret) {
		oAuthSecret = authSecret;
	}

	public String getOAuthRawResponse() {
		return oAuthRawResponse;
	}

	public void setOAuthRawResponse(String authRawResponse) {
		oAuthRawResponse = authRawResponse;
	}

	public String getOAuthVerifier() {
		return oAuthVerifier;
	}

	public void setOAuthVerifier(String authVerifier) {
		oAuthVerifier = authVerifier;
	}

	public String getOAuthAuthorizationUrl() {
		return oAuthAuthorizationUrl;
	}

	public void setOAuthAuthorizationUrl(String authAuthorizationUrl) {
		oAuthAuthorizationUrl = authAuthorizationUrl;
	}

	public ProviderUser getProviderUser() {
		return providerUser;
	}

	public void setProviderUser(ProviderUser providerUser) {
		this.providerUser = providerUser;
	}

	@Override
	public void valueBound(HttpSessionBindingEvent event) {
		// TODO Auto-generated method stub
		logger.info(event.getName() + " valueBound ........... ");

	}

	@Override
	public void valueUnbound(HttpSessionBindingEvent event) {
		// TODO Auto-generated method stub
		logger.info(event.getName() + " valueUnbound ........... ");
	}
}
