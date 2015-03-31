package net.suntec.oauthsrv.action.util;

import com.openjava.core.util.StrUtil;

public final class UrlUtil {
	static String CON_PARAMS_FLAG = "&";
	static String CON_URL_FLAG = "?";

	public static String conUrl(String url, String params) {
		if (StrUtil.isEmpty(params)) {
			return url;
		}
		StringBuilder sb = new StringBuilder();
		sb.append(url);
		if (url.contains(CON_URL_FLAG)) {
			sb.append(CON_PARAMS_FLAG);
			sb.append(params);
		} else {
			sb.append(CON_URL_FLAG);
			sb.append(params);
		}

		return sb.toString();

	}
}
