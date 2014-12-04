package net.suntec.framework.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.suntec.constant.AuthErrorCodeConstant;
import net.suntec.framework.constant.AppConstant;
import net.suntec.framework.exception.ASParamValidaterException;
import net.suntec.oauthsrv.framework.dto.OauthFlowStatus;
import net.suntec.oauthsrv.framework.dto.OauthStatusParamDTO;
import net.suntec.oauthsrv.framework.dto.SessionUser;

public final class SessionUtil {

	private SessionUtil() {
	}

	public static boolean checkSessionOauthParamsStatus(HttpServletRequest req, String appType) {
		return checkSessionOauthParamsStatus(req.getSession(), appType);
	}

	public static boolean checkSessionOauthParamsStatus(HttpSession session, String appType) {
		Object obj = session.getAttribute(AppConstant.SESSION_OAUTH_PARAMS_STATUS);
		return (null != obj);
	}

	public static void saveSessionOauthParamsStatus(HttpServletRequest req, String appType,
			OauthStatusParamDTO sessionUser) {
		saveSessionOauthParamsStatus(req.getSession(), appType, sessionUser);
	}

	public static OauthStatusParamDTO getSessionOauthParamsStatus(HttpServletRequest req, String appType) {
		return getSessionOauthParamsStatus(req.getSession(), appType);
	}

	public static void removeSessionOauthParamsStatus(HttpServletRequest req, String appType) {
		removeSessionOauthParamsStatus(req.getSession(), appType);
	}

	public static void saveSessionOauthParamsStatus(HttpSession session, String appType,
			OauthStatusParamDTO oauthFlowStatus) {
		session.setAttribute(AppConstant.SESSION_OAUTH_PARAMS_STATUS + "-" + appType, oauthFlowStatus);
	}

	public static OauthStatusParamDTO getSessionOauthParamsStatus(HttpSession session, String appType) {
		try {
			return (OauthStatusParamDTO) session.getAttribute(AppConstant.SESSION_OAUTH_PARAMS_STATUS + "-" + appType);
		} catch (Exception ex) {
			throw new ASParamValidaterException("OauthStatusParam is not exists",
					AuthErrorCodeConstant.SS_NO_PARAMS_OAUTHSTATUSPARAM);
		}
	}

	public static void removeSessionOauthParamsStatus(HttpSession session, String appType) {
		session.removeAttribute(AppConstant.SESSION_OAUTH_PARAMS_STATUS + "-" + appType);
	}

	public static void saveSessionOauthStatus(HttpSession session, String appType, OauthFlowStatus oauthFlowStatus) {
		session.setAttribute(AppConstant.SESSION_OAUTH_STATUS + "-" + appType, oauthFlowStatus);
	}

	public static OauthFlowStatus getSessionOauthStatus(HttpSession session, String appType) {
		try {
			return (OauthFlowStatus) session.getAttribute(AppConstant.SESSION_OAUTH_STATUS + "-" + appType);
		} catch (Exception ex) {
			throw new ASParamValidaterException("OauthFlowStatus is not exists",
					AuthErrorCodeConstant.SS_NO_PARAMS_OAUTHFLOWSTATUS);
		}
	}

	public static void removeSessionOauthStatus(HttpSession session, String appType) {
		session.removeAttribute(AppConstant.SESSION_OAUTH_STATUS + "-" + appType);
	}

	public static void saveSessionOauthStatus(HttpServletRequest req, String appType, OauthFlowStatus sessionUser) {
		saveSessionOauthStatus(req.getSession(), appType, sessionUser);
	}

	public static OauthFlowStatus getSessionOauthStatus(HttpServletRequest req, String appType) {
		return getSessionOauthStatus(req.getSession(), appType);
	}

	public static void removeSessionOauthStatus(HttpServletRequest req, String appType) {
		removeSessionOauthStatus(req.getSession(), appType);
	}

	public static void saveSessionUser(HttpSession session, SessionUser sessionUser) {
		session.setAttribute(AppConstant.SESSION_USER_ATTR, sessionUser);
	}

	public static SessionUser getSessionUser(HttpSession session) {
		Object obj = session.getAttribute(AppConstant.SESSION_USER_ATTR);
		if (null != obj) {
			return (SessionUser) obj;
		} else {
			throw new ASParamValidaterException("please login", AuthErrorCodeConstant.SS_NO_PARAMS_USERINFO);
		}
	}

	public static void removeSessionUser(HttpSession session) {
		session.removeAttribute(AppConstant.SESSION_USER_ATTR);
	}

	public static void saveSessionUser(HttpServletRequest req, SessionUser sessionUser) {
		saveSessionUser(req.getSession(), sessionUser);
	}

	public static SessionUser getSessionUser(HttpServletRequest req) {
		return getSessionUser(req.getSession());
	}

	public static void removeSessionUser(HttpServletRequest req) {
		removeSessionUser(req.getSession());
	}

	public static boolean checkSessionUser(HttpServletRequest req) {
		return checkSessionUser(req.getSession());
	}

	public static boolean checkSessionUser(HttpSession session) {
		Object obj = session.getAttribute(AppConstant.SESSION_USER_ATTR);
		boolean sessionExists = (null != obj);
		return sessionExists;
	}
}
