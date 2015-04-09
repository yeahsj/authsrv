package net.suntec.oauthsrv.framework.provider;

import net.suntec.constant.AuthErrorCodeConstant;
import net.suntec.framework.exception.ASServiceException;
import net.suntec.oauthsrv.dto.AppConfig;
import net.suntec.oauthsrv.framework.dto.OauthFlowStatus;
import net.suntec.oauthsrv.util.ASLogger;

import org.json.JSONException;
import org.scribe.model.Token;
import org.scribe.model.Verifier;
import org.scribe.oauth.OAuthService;

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
	private final ASLogger logger = new ASLogger(Oauth2Provider.class);

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
		try {
			Token accessToken = service.getAccessToken(null, verifier);
			user.setAccessToken(accessToken);
		} catch (JSONException ex) {
			throw new ASServiceException("parse json error",
					AuthErrorCodeConstant.JSON_PARSE_ERROR);
		}
	}

	@Override
	public String getAuthHeader(AppConfig appConfig, Token token,
			String requestUrl, String method) {
		throw new UnsupportedOperationException("Unsupported operation");
	}

}
