package net.suntec.framework.util;

import javax.servlet.http.HttpServletRequest;

import net.suntec.oauthsrv.framework.ResourceConfig;
import net.suntec.oauthsrv.framework.dto.ServerConfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.openjava.core.util.StrUtil;

/**
 * 
 * 
 * @项目名称: OauthSrv
 * @功能描述: 获取当前服务器http路径
 * @当前版本： 1.0
 * @创建时间: 2014-6-11 上午09:48:27
 * @author: <a href="mailto:yeahsj@gmail.com">yeahsj</a>
 * @修改历史:
 */
public final class ServerPathUtil {
	static String LABEL_1 = ":";
	static String LABEL_2 = "//";
	static Logger logger = LoggerFactory.getLogger(ServerPathUtil.class
			.getName());

	private ServerPathUtil() {
	}

	public static String getRemoteCurrentServerRootPath(HttpServletRequest req) {
		ServerConfig serverConfig = ResourceConfig.getInstance()
				.getServerConfig();
		return serverConfig.getServerUrl();
	}

	public static String getCurrentServerRootPath(HttpServletRequest req) {
		// if (req.getServerName().contains("localhost")
		// || req.getServerName().contains("127.0.0.1")) {
		// return getLocalCurrentServerRootPath(req);
		// } else {
		// String configUrl = getRemoteCurrentServerRootPath(req);
		// if (StrUtil.isEmpty(configUrl)) {
		return getLocalCurrentServerRootPath(req);
		// } else {
		// return configUrl;
		// }
		// }
	}

	public static String getLocalCurrentServerRootPath(HttpServletRequest req) {
		StringBuilder localServer = new StringBuilder();
		ServerConfig serverConfig = ResourceConfig.getInstance()
				.getServerConfig();
		if (StrUtil.isEmpty(serverConfig.getProtol())) {
			localServer.append(req.getScheme());
		} else {
			localServer.append(serverConfig.getProtol());
		}
		localServer.append(LABEL_1);
		localServer.append(LABEL_2);
		localServer.append(req.getServerName());

		// localServer.append(req.getScheme());

		if (req.getServerPort() != 80 && req.getServerPort() != 443) {
			localServer.append(LABEL_1);
			localServer.append(req.getServerPort());
		}

		String path = req.getContextPath();
		if (!path.equals("/") && !path.equals("")) {
			localServer.append(path);
		}
		logger.info(localServer.toString());
		return localServer.toString();
	}
}
