package net.suntec.framework.util;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.support.RequestContext;

/**
 * 
 * 
 * @项目名称: OauthSrv
 * @功能描述:
 * @当前版本： 1.0
 * @创建时间: 2014年12月4日 下午5:49:37
 * @author: <a href="mailto:yeahsj@yahoo.com.cn">yeahsj</a>
 * @修改历史:
 */
public class AuthSrvHtmlUtil {
	static final String BIND_PROMPT = "请点击右边按钮进行授权绑定";
	static final String UNBIND_PROMPT = "应用已绑定,可点击右边按钮进行授权绑定解绑";

	private AuthSrvHtmlUtil() {
	}

	private static RequestContext getRequestContext(HttpServletRequest req) {
		return new RequestContext(req);
	}

	private static String getMessage(HttpServletRequest req, String code) {
		return getRequestContext(req).getMessageSource().getMessage(code,
				(new Object[0]), getRequestContext(req).getLocale());
	}

	public static String getPromptForBind(boolean isBind) {
		if (isBind) {
			return UNBIND_PROMPT;
		} else {
			return BIND_PROMPT;
		}
	}

	public static String getPromptForBind(HttpServletRequest req, boolean isBind) {
		if (isBind) {
			return getMessage(req, "unBindPrompt");
		} else {
			return getMessage(req, "bindPrompt");
		}
	}

	public static String getBtnClassForBind(boolean isBind) {
		if (isBind) {
			return "gear";
		} else {
			return "lock";
		}
	}

	public static String getClickActionForBind(boolean isBind) {
		if (isBind) {
			return "unbindAuth";
		} else {
			return "bindAuth";
		}
	}
}
