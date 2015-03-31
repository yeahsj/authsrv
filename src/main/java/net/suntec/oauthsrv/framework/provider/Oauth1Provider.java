package net.suntec.oauthsrv.framework.provider;

import net.suntec.framework.util.ASLogger;
import net.suntec.oauthsrv.dto.AppConfig;
import net.suntec.oauthsrv.framework.dto.OauthFlowStatus;

import org.scribe.model.OAuthRequest;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.model.Verifier;
import org.scribe.oauth.OAuthPocketServiceImpl;
import org.scribe.oauth.OAuthService;

/**
 * 
 * 
 * @项目名称: OauthSrv
 * @功能描述:
 * @当前版本： 1.0
 * @创建时间: 2014-5-27 下午12:27:44
 * @author: <a href="mailto:yeahsj@gmail.com">yeahsj</a>
 * @修改历史:
 */
public class Oauth1Provider extends AbstractOauthProvider {
	private final ASLogger logger = new ASLogger(Oauth1Provider.class);

	/**
	 * 
	 * @param user
	 */
	public void buildRedirectUrl(OauthFlowStatus user, OAuthService service) {
		Token token = service.getRequestToken();
		logger.info("requestToken: " + token);

		String redirect = service.getAuthorizationUrl(token);
		logger.info("Redirect URL: " + redirect);

		user.setOAuthToken(token.getToken());
		user.setOAuthSecret(token.getSecret());
		user.setOAuthRawResponse(token.getRawResponse());
		user.setOAuthAuthorizationUrl(redirect);
	}

	/**
	 * 
	 * @param user
	 */
	public void buildRedirectUrl(OauthFlowStatus user) {
		OAuthService service = getOAuthService(user);
		buildRedirectUrl(user, service);
	}

	public void obtainAccessToken(OauthFlowStatus user) {
		OAuthService service = getOAuthService(user);

		Verifier verifier = null;
		if (service instanceof OAuthPocketServiceImpl) {
			logger.info(" this is pocket service, not check ");
		} else {
			verifier = new Verifier(user.getOAuthVerifier());
		}
		Token token = new Token(user.getOAuthToken(), user.getOAuthSecret(),
				user.getOAuthRawResponse());
		Token accessToken = service.getAccessToken(token, verifier);
		user.setAccessToken(accessToken);
	}

	public String getAuthHeader(AppConfig appConfig, Token token,
			String requestUrl, String method) {
		OAuthService service = getOAuthService(appConfig);
		OAuthRequest request = new OAuthRequest(Verb.valueOf(method),
				requestUrl);
		return service.getOauthHeader(token, request);
	}

}
