package net.suntec.oauthsrv.framework.provider;

import net.suntec.oauthsrv.dto.AppConfig;
import net.suntec.oauthsrv.framework.dto.OauthFlowStatus;

import org.scribe.model.Token;
import org.scribe.model.Verifier;
import org.scribe.oauth.OAuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * 
 * @项目名称: OauthSrv
 * @功能描述:
 * @当前版本： 1.0
 * @创建时间: 2014-5-27 下午12:27:48
 * @author: <a href="mailto:yeahsj@gmail.com">yeahsj</a>
 * @修改历史:
 */
public class Oauth2Provider extends AbstractOauthProvider {
	private final Logger logger = LoggerFactory.getLogger(Oauth2Provider.class);

	/**
	 * 
	 * @param user
	 */
	public void buildRedirectUrl(OauthFlowStatus user, OAuthService service) {
		String redirect = service.getAuthorizationUrl(null);
		logger.info("Redirect URL: " + redirect);
		user.setOAuthAuthorizationUrl(redirect);
	}

	/**
	 * 
	 * @param user
	 */
	public void buildRedirectUrl(OauthFlowStatus user) {
		OAuthService service = getOAuthService(user);
		String redirect = service.getAuthorizationUrl(null);
		logger.info("Redirect URL: " + redirect);
		user.setOAuthAuthorizationUrl(redirect);
	}

	public void obtainAccessToken(OauthFlowStatus user) {
		OAuthService service = getOAuthService(user);
		Verifier verifier = new Verifier(user.getOAuthVerifier());
		Token accessToken = service.getAccessToken(null, verifier);
		user.setAccessToken(accessToken);
	}

	@Override
	public String getAuthHeader(AppConfig appConfig, Token token, String requestUrl, String method) {
		throw new UnsupportedOperationException("Unsupported operation");
	}

}
