package net.suntec.framework.util;

import java.util.Map;

import net.suntec.framework.exception.ASBaseException;
import net.suntec.oauthsrv.action.jsonresult.AppInfoResult;
import net.suntec.oauthsrv.dto.AppConfig;
import net.suntec.oauthsrv.framework.OauthAppConfig;
import net.suntec.oauthsrv.framework.dto.OauthFlowStatus;
import net.suntec.oauthsrv.framework.provider.AbstractOauthProvider;
import net.suntec.oauthsrv.framework.provider.OauthProviderFactory;

import org.scribe.model.Token;

/**
 * 
 * 
 * @项目名称: OauthSrv
 * @功能描述: 处理oauth 认证flow
 * @当前版本： 1.0
 * @创建时间: 2014-5-27 下午12:28:17
 * @author: <a href="mailto:yeahsj@gmail.com">yeahsj</a>
 * @修改历史:
 */
public final class OauthProviderService {

	public static String prodAuthHeader(AppConfig appConfig, Token accessToken,
			String requestUrl, String method) {
		AbstractOauthProvider provider = OauthProviderFactory.getFactory()
				.getOauthProvider(1);
		return provider.getAuthHeader(appConfig, accessToken, requestUrl,
				method.toUpperCase());
	}

	public static void buildRedirectUrl(OauthFlowStatus oauthFlowStatus) {
		AbstractOauthProvider provider = OauthProviderFactory.getFactory()
				.getOauthProvider(
						oauthFlowStatus.getAppConfig().getOauthVersion());
		provider.buildRedirectUrl(oauthFlowStatus);
	}

	public static void obtainAccessToken(OauthFlowStatus oauthFlowStatus) {
		AbstractOauthProvider provider = OauthProviderFactory.getFactory()
				.getOauthProvider(
						oauthFlowStatus.getAppConfig().getOauthVersion());
		provider.obtainAccessToken(oauthFlowStatus);
	}

	public static void prodUserProfile(OauthFlowStatus oauthFlowStatus) {
		AbstractOauthProvider provider = OauthProviderFactory.getFactory()
				.getOauthProvider(
						oauthFlowStatus.getAppConfig().getOauthVersion());
		provider.prodUserProfile(oauthFlowStatus);
	}

	public static OauthFlowStatus initOauthFlowStatus(String appType,
			String clientId, String localServer) {
		OauthFlowStatus oauthFlowStatus = new OauthFlowStatus();
		AppConfig appConfig = prodAppConfig(appType, clientId);
		appConfig.setLocalServer(localServer);
		oauthFlowStatus.setAppConfig(appConfig);
		return oauthFlowStatus;
	}

	public static AppConfig prodAppConfig(String appType, String clientId) {
		return prodOauthConfigFromDB().get(appType + "-" + clientId);
	}

	public static AppInfoResult prodAppInfoResult(String appType,
			String clientId) {
		AppConfig appConfig = OauthProviderService.prodAppConfig(appType,
				clientId);
		if (null == appConfig) {
			throw new ASBaseException(appType + "not exists in AppConfig");
		}
		AppInfoResult appInfoResult = new AppInfoResult();
		appInfoResult.setAppKey(AesUtil.encrypt(appConfig.getAppKey()));
		appInfoResult.setAppSecret(AesUtil.encrypt(appConfig.getAppSecret()));
		return appInfoResult;
	}

	private static Map<String, AppConfig> prodOauthConfigFromDB() {
		Map<String, AppConfig> map = OauthAppConfig.getInstance()
				.getAppConfigMap();
		return map;
	}
}
