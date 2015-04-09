package net.suntec.oauthsrv.util;

import javax.servlet.http.HttpSession;

/**
 * 
 * @项目名称: OauthSrv
 * @功能描述:
 * @当前版本： 1.0
 * @创建时间: 2014-10-15 下午03:45:42
 * @author: <a href="mailto:yeahsj@yahoo.com.cn">yeahsj</a>
 * @修改历史:
 */
public class SessionAttributeUtil {
	public static boolean checkSessionAttribute(HttpSession session,
			String attributeName) {
		Object obj = session.getAttribute(attributeName);
		return (null != obj);
	}

	public static <T> void saveSessionAttribute(HttpSession session,
			String attributeName, T t) {
		session.setAttribute(attributeName, t);
	}

	@SuppressWarnings("unchecked")
	public static <T> T getSessionAttribute(HttpSession session,
			String attributeName) {

		return (T) session.getAttribute(attributeName);
	}

	public static void removeSessionAttribute(HttpSession session,
			String attributeName) {
		session.removeAttribute(attributeName);
	}
}
