package net.suntec.oauthsrv.framework.provider;

/**
 * 
 * 
 * @项目名称: OauthSrv
 * @功能描述:
 * @当前版本： 1.0
 * @创建时间: 2014-5-27 下午12:27:53
 * @author: <a href="mailto:yeahsj@gmail.com">yeahsj</a>
 * @修改历史:
 */
public class OauthProviderFactory {
	static Oauth1Provider oauth1Provider = null;
	static Oauth2Provider oauth2Provider = null;
	static OauthProviderFactory factory = null;
	static {
		oauth1Provider = new Oauth1Provider();
		oauth2Provider = new Oauth2Provider();
		factory = new OauthProviderFactory();
	}

	public static OauthProviderFactory getFactory() {
		return factory;
	}

	public AbstractOauthProvider getOauthProvider(int oauthVersion) {
		if (oauthVersion == 1) {
			return oauth1Provider;
		} else if (oauthVersion == 2) {
			return oauth2Provider;
		} else {
			return oauth2Provider;
		}
	}

	private OauthProviderFactory() {

	}

}
