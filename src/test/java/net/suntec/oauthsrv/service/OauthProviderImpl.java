package net.suntec.oauthsrv.service;

import java.util.HashMap;
import java.util.Map;

import net.suntec.oauthsrv.dto.AppConfig;
import net.suntec.oauthsrv.framework.OauthAppConfig;
import net.suntec.oauthsrv.framework.ResourceConfig;
import net.suntec.oauthsrv.framework.dto.OauthFlowStatus;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.FacebookApi;
import org.scribe.builder.api.GoogleApi;
import org.scribe.builder.api.InstagramApi;
import org.scribe.builder.api.TwitterApi;
import org.scribe.model.ProviderUser;
import org.scribe.model.Token;
import org.scribe.model.Verifier;
import org.scribe.oauth.OAuthService;

import com.openjava.core.util.StrUtil;

/**
 * 
 * 
 * @项目名称: OauthSrv
 * @功能描述: 
 * @当前版本： 1.0
 * @创建时间: 2014-5-27 下午12:28:12
 * @author: <a href="mailto:yeahsj@gmail.com">yeahsj</a>
 * @修改历史:
 */
@Deprecated
public class OauthProviderImpl {
	private Log log = LogFactory.getLog(getClass());

	public OauthFlowStatus initOauthFlowStatus(String appType, String deviceType) {
		OauthFlowStatus oauthFlowStatus = new OauthFlowStatus();
		AppConfig appConfig = this.getAppConfig(appType, deviceType);
		oauthFlowStatus.setAppConfig(appConfig);
		return oauthFlowStatus;
	}

	public void buildRedirectUrl(OauthFlowStatus user) {
		OAuthService service = getOAuthService(user);
		if (service.getVersion().equals("1.0")) {
			this.buildRedirectUrlForOauth1(user, service);
		} else {
			this.buildRedirectUrlForOauth2(user, service);
		}
	}

	/**
	 * 
	 * @param user
	 */
	public void buildRedirectUrlForOauth1(OauthFlowStatus user, OAuthService service) {
		Token token = service.getRequestToken();
		log.info("requestToken: " + token);

		String redirect = service.getAuthorizationUrl(token);
		log.info("Redirect URL: " + redirect);

		user.setOAuthToken(token.getToken());
		user.setOAuthSecret(token.getSecret());
		user.setOAuthRawResponse(token.getRawResponse());
		user.setOAuthAuthorizationUrl(redirect);
	}

	/**
	 * 
	 * @param user
	 */
	public void buildRedirectUrlForOauth2(OauthFlowStatus user, OAuthService service) {
		String redirect = service.getAuthorizationUrl(null);
		log.info("Redirect URL: " + redirect);
		user.setOAuthAuthorizationUrl(redirect);
	}

	public void obtainAccessTokenForOauth1(OauthFlowStatus user) {
		OAuthService service = getOAuthService(user);

		Verifier verifier = new Verifier(user.getOAuthVerifier());
		Token token = new Token(user.getOAuthToken(), user.getOAuthSecret(), user.getOAuthRawResponse());
		Token accessToken = service.getAccessToken(token, verifier);
		user.setAccessToken(accessToken);
	}

	public void obtainAccessTokenForOauth2(OauthFlowStatus user) {
		OAuthService service = getOAuthService(user);
		Verifier verifier = new Verifier(user.getOAuthVerifier());
		Token accessToken = service.getAccessToken(null, verifier);
		user.setAccessToken(accessToken);
	}

	public void prodUserProfile(OauthFlowStatus oauthFlowStatus) {
		OAuthService service = getOAuthService(oauthFlowStatus);
		ProviderUser providerUser = service.getProviderUser(oauthFlowStatus.getAccessToken(), oauthFlowStatus
				.getAppConfig().isRequestForUserInfo());
		oauthFlowStatus.setProviderUser(providerUser);
	}

	public AppConfig getAppConfig(String appType, String deviceType) {
		return this.getOauthConfig().get(appType + "-" + deviceType);
	}

	private Map<String, AppConfig> getOauthConfig() {
		ResourceConfig config = ResourceConfig.getInstance();

//		String curServer = "http://" + config.getServerConfig().getHost() + ":" + config.getServerConfig().getPort();
		String curServer = "http://localhost";
		Map<String, AppConfig> map = new HashMap<String, AppConfig>();
		AppConfig appConfig = new AppConfig();
		appConfig.setAppId(1);

		// GBBzdY50VngbUETKF7N7jQ
		// Dw4rwdrF11EidKa9eqDKls2eNR3xXYDAc3xiL1U
		appConfig.setAppKey("6UdgojawFvkyUnD8AUUCBt8BS");
		appConfig.setAppSecret("IXqJno9h8kPOpLnHumYtO327Qz7E5YQRg7Oa0aRbs9obzC8qk3");
		appConfig.setAppType("twitter");
		appConfig.setCallbackUrl(curServer + "/auth/twitter/callback");
		appConfig.setClazz(TwitterApi.class);
		map.put(appConfig.getAppType() + "-" + appConfig.getAppKey(), appConfig);

		appConfig = new AppConfig();
		appConfig.setAppId(2);
		appConfig.setAppKey("1515303475363532");
		appConfig.setAppSecret("437ded394ca45ab28075e03106e1e652");
		appConfig.setAppType("facebook");
		appConfig.setCallbackUrl("http://192.168.0.104:8080/auth/facebook/callback");
		appConfig.setScope("basic_info publish_actions read_stream user_notes user_photos user_videos");
		appConfig.setClazz(FacebookApi.class);
		map.put(appConfig.getAppType() + "-" + appConfig.getAppKey(), appConfig);

		appConfig = new AppConfig();
		appConfig.setAppId(3);
		appConfig.setAppKey("f41370f060f54da497da4c7ae2f6385e");
		appConfig.setAppSecret("01b27fbe76894326840b74fe62cad289");
		appConfig.setAppType("instagram");
		appConfig.setRequestForUserInfo(false);
		appConfig.setCallbackUrl(curServer + "/auth/instagram/callback");
		appConfig.setClazz(InstagramApi.class);
		map.put(appConfig.getAppType() + "-" + appConfig.getAppKey(), appConfig);

		appConfig = new AppConfig();
		appConfig.setAppId(4);
		appConfig.setAppKey("883753072662-fmmmp4gbo7ltsf79bm0ionvgtg4mgk2e.apps.googleusercontent.com");
		appConfig.setAppSecret("Z3X6IZlErnUyhl-LRzuPRFiw");
		appConfig.setAppType("google");
		appConfig.setScope("email profile");

		appConfig.setRequestForUserInfo(false);
		appConfig.setCallbackUrl(curServer + "/auth/google/callback");
		appConfig.setClazz(GoogleApi.class);
		map.put(appConfig.getAppType() + "-" + appConfig.getAppKey(), appConfig);

		return map;
	}

	private OAuthService getOAuthService(OauthFlowStatus user) {
		AppConfig appConfig = user.getAppConfig();
		ServiceBuilder builder = new ServiceBuilder();

		builder.provider(appConfig.getClazz()).apiKey(appConfig.getAppKey()).apiSecret(appConfig.getAppSecret())
				.callback(appConfig.getCallbackUrl());

		if (!StrUtil.isEmpty(appConfig.getScope())) {
			builder.scope(appConfig.getScope());
		}
		return builder.build();
	}

	private Map<String, AppConfig> getOauthConfigFromDB() {
		Map<String, AppConfig> map = OauthAppConfig.getInstance().getAppConfigMap();
		return map;
	}
}
