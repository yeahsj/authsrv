package net.suntec.oauthsrv.framework;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import net.suntec.framework.exception.ASBaseException;
import net.suntec.oauthsrv.dto.AppBase;
import net.suntec.oauthsrv.dto.AppConfig;
import net.suntec.oauthsrv.dto.AppInfo;
import net.suntec.oauthsrv.service.ConfigService;

import org.apache.commons.beanutils.BeanUtils;
import org.scribe.builder.api.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.FrameworkServlet;

/**
 * 
 * 
 * @项目名称: OauthSrv
 * @功能描述: 存储OAuth 配置信息
 * @当前版本： 1.0
 * @创建时间: 2014-5-27 下午12:27:19
 * @author: <a href="mailto:yeahsj@gmail.com">yeahsj</a>
 * @修改历史:
 */
public class OauthAppConfig {
	private static OauthAppConfig instance = null;
	private List<AppInfo> appInfos = null;
	private List<AppBase> appBases = null;

	Map<String, AppConfig> appConfigMap = null;

	private final Logger logger = LoggerFactory.getLogger(OauthAppConfig.class);

	public void init() {
		try {
			initAppConfigMap();
		} catch (IllegalAccessException e) {
			logger.error(e.getMessage());
			throw new ASBaseException(e);
		} catch (InvocationTargetException e) {
			logger.error(e.getMessage());
			throw new ASBaseException(e);
		} catch (ClassNotFoundException e) {
			logger.error(e.getMessage());
			throw new ASBaseException(e);
		}
	}

	public void reload(ServletContext servletContext) {
		WebApplicationContext wac = WebApplicationContextUtils
				.getWebApplicationContext(servletContext,
						FrameworkServlet.SERVLET_CONTEXT_PREFIX + "mvc");
		if (null == wac) {
			throw new ASBaseException("wac is null");
		}
		ConfigService configService = wac.getBean(ConfigService.class);
		configService.getOauthConfig();
	}

	public void initAppConfigMap() throws IllegalAccessException,
			InvocationTargetException, ClassNotFoundException {
		appConfigMap = new HashMap<String, AppConfig>();

		StringBuilder callbackUrl = null;
		boolean isInitBase = false;
		AppConfig appConfig = null;
		for (AppInfo appInfo : appInfos) {
			isInitBase = false;
			appConfig = new AppConfig();
			appConfig.setAppId(appInfo.getAppId());
			appConfig.setAppKey(appInfo.getAppClientid());
			appConfig.setAppSecret(appInfo.getAppSecret());
			appConfig.setAppType(appInfo.getAppType());
			for (AppBase appBase : appBases) {
				if (appInfo.getAppType().equals(appBase.getAppType())) {
					Class<Api> clazz = (Class<Api>) Class.forName(appBase
							.getClazzName());
					appConfig.setOauthVersion(appBase.getOauthVersion());
					appConfig.setRequestForUserInfo(appBase
							.getRequestUserInfo() == 1);
					appConfig.setScope(appBase.getScope());
					appConfig.setClazz(clazz);
					BeanUtils.copyProperties(appConfig, appBase);
					isInitBase = true;
					break;
				}
			}
			if (isInitBase) {
				callbackUrl = new StringBuilder();
				// if (appInfo.getAppType().equals("feedly")) {
				// } else {
				callbackUrl.append("/auth/").append(appInfo.getAppType())
						.append("/").append(appInfo.getSortNo())
						.append("/callback");
				// }
				appConfig.setCallbackUrl(callbackUrl.toString());
				appConfigMap.put(
						appConfig.getAppType() + "-" + appConfig.getAppKey(),
						appConfig);
			} else {
				throw new ASBaseException(appInfo.getAppType()
						+ "is not exist in AppBase");
			}
		}
	}

	public List<AppInfo> getAppInfos() {
		return appInfos;
	}

	public void setAppInfos(List<AppInfo> appInfos) {
		this.appInfos = appInfos;
	}

	public List<AppBase> getAppBases() {
		return appBases;
	}

	public Map<String, AppConfig> getAppConfigMap() {
		return appConfigMap;
	}

	public void setAppBases(List<AppBase> appBases) {
		this.appBases = appBases;
	}

	public static OauthAppConfig getInstance() {
		return instance;
	}

	static {
		instance = new OauthAppConfig();
	}
}
