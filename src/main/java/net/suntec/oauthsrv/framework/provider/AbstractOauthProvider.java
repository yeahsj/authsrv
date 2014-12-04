package net.suntec.oauthsrv.framework.provider;

import net.suntec.oauthsrv.dto.AppConfig;
import net.suntec.oauthsrv.framework.dto.OauthFlowStatus;

import org.scribe.builder.ServiceBuilder;
import org.scribe.model.ProviderUser;
import org.scribe.model.Token;
import org.scribe.oauth.OAuthService;

import com.openjava.core.util.StrUtil;

/**
 * 
 * 
 * @项目名称: OauthSrv
 * @功能描述: Oauth Provider
 * @当前版本： 1.0
 * @创建时间: 2014-5-27 下午12:27:39
 * @author: <a href="mailto:yeahsj@gmail.com">yeahsj</a>
 * @修改历史:
 */
public abstract class AbstractOauthProvider {
	public abstract String getAuthHeader(AppConfig appConfig, Token token,
			String requestUrl, String method);

	public abstract void buildRedirectUrl(OauthFlowStatus user);

	public abstract void obtainAccessToken(OauthFlowStatus user);

	public void prodUserProfile(OauthFlowStatus oauthFlowStatus) {
		OAuthService service = getOAuthService(oauthFlowStatus);
		ProviderUser providerUser = service.getProviderUser(oauthFlowStatus
				.getAccessToken(), oauthFlowStatus.getAppConfig()
				.isRequestForUserInfo());
		oauthFlowStatus.setProviderUser(providerUser);
	}

	protected OAuthService getOAuthService(OauthFlowStatus user) {
		return getOAuthService(user.getAppConfig());
	}

	/**
	 * 根据<code>AppConfig</code>构建<code>OAuthService</code>
	 * 
	 * @param appConfig
	 * @return
	 */
	protected OAuthService getOAuthService(AppConfig appConfig) {
		ServiceBuilder builder = new ServiceBuilder();
		builder.provider(appConfig.getClazz())
				.apiKey(appConfig.getAppKey())
				.apiSecret(appConfig.getAppSecret())
				.callback(
						appConfig.getLocalServer() + appConfig.getCallbackUrl());
		// builder.debugStream(System.out);
		if (!StrUtil.isEmpty(appConfig.getScope())) {
			builder.scope(appConfig.getScope());
		}
		return builder.build();
	}

}
