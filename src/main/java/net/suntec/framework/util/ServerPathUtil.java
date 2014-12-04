package net.suntec.framework.util;

import javax.servlet.http.HttpServletRequest;

import com.openjava.core.util.StrUtil;

import net.suntec.oauthsrv.framework.ResourceConfig;
import net.suntec.oauthsrv.framework.dto.ServerConfig;

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

	private ServerPathUtil() {
	}

	public static String getCurrentServerRootPath(HttpServletRequest req) {
		StringBuilder localServer = new StringBuilder();
		ServerConfig serverConfig = ResourceConfig.getInstance().getServerConfig();
		if( StrUtil.isEmpty(  serverConfig.getProtol() )){
			localServer.append(req.getScheme());
		}else{
			localServer.append(serverConfig.getProtol());
		} 
		localServer.append(LABEL_1);
		localServer.append(LABEL_2);
		localServer.append(req.getServerName());
		// localServer.append(req.getScheme());

		if (req.getServerPort() != 80 && req.getServerPort() != 443 ) {
			localServer.append(LABEL_1);
			localServer.append(req.getServerPort());
		}
		
		String path = req.getContextPath();
		if( !path.equals("/") && !path.equals("")){
			localServer.append( path );
		}
		return localServer.toString();
	}
}
