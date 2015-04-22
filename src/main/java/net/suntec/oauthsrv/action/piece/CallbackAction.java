package net.suntec.oauthsrv.action.piece;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.suntec.constant.AuthErrorCodeConstant;
import net.suntec.framework.exception.ASBaseException;
import net.suntec.framework.exception.ASParamValidaterException;
import net.suntec.oauthsrv.constant.AppConstant;
import net.suntec.oauthsrv.constant.MessageConstant;
import net.suntec.oauthsrv.dto.AppIautoMap;
import net.suntec.oauthsrv.framework.dto.OauthFlowStatus;
import net.suntec.oauthsrv.framework.dto.OauthStatusParamDTO;
import net.suntec.oauthsrv.service.ASCoreService;
import net.suntec.oauthsrv.service.MessageService;
import net.suntec.oauthsrv.util.ASLogger;
import net.suntec.oauthsrv.util.OauthProviderService;
import net.suntec.oauthsrv.util.SessionUtil;

import org.springframework.web.bind.annotation.PathVariable;

import com.openjava.core.util.StrUtil;

/**
 * 
 * 
 * @项目名称: OauthSrv
 * @功能描述:
 * @当前版本： 1.0
 * @创建时间: 2015年4月17日 上午11:08:05
 * @author: <a href="mailto:yeahsj@yahoo.com.cn">sangjun</a>
 * @修改历史:
 */
public class CallbackAction {
	private final ASLogger logger = new ASLogger(CallbackAction.class);
	ASCoreService aSCoreService;
	MessageService messageService;

	public CallbackAction(ASCoreService aSCoreService,
			MessageService messageService) {
		super();
		this.aSCoreService = aSCoreService;
		this.messageService = messageService;
	}

	public CallbackCacheResult execute(HttpServletRequest req,
			HttpServletResponse res, @PathVariable("provider") String provider) {
		CallbackCacheResult result = null;
		String errMsg = null;
		int errCode = AppConstant.ERROR_CODE;
		String fromPhone = "";
		String errurl = null;
		String backurl = null;
		String code = req.getParameter("code");
		String oAuthVerifier = req.getParameter("oauth_verifier");
		String oauthToken = req.getParameter("oauth_token");
		HttpSession session = req.getSession();

		CallbackCacheKey key = new CallbackCacheKey(req.getSession().getId(),
				provider, code, oAuthVerifier, oauthToken);
		synchronized (session) {
			Object cacheResult = session.getAttribute(key.toString());
			if (null != cacheResult
					&& cacheResult instanceof CallbackCacheResult) {
				logger.info("get callback result from cahce");
				result = (CallbackCacheResult) cacheResult;
			} else {
				logger.info("new request, create result for callback");
				try {
					OauthFlowStatus oauthFlowStatus = SessionUtil
							.getSessionOauthStatus(req, provider);
					OauthStatusParamDTO oauthStatusParamDTO = SessionUtil
							.getSessionOauthParamsStatus(req, provider);
					errurl = oauthStatusParamDTO.getErrurl();
					fromPhone = StrUtil.nullToString(oauthStatusParamDTO
							.getFromPhone());
					backurl = oauthStatusParamDTO.getBackurl();
					if (StrUtil.isNotEmpty(code)) {
						oauthFlowStatus.setOAuthVerifier(code);
					} else {
						oauthFlowStatus.setOAuthVerifier(oAuthVerifier);
					}
					logger.info("obtain accessToken ................ ");
					OauthProviderService.obtainAccessToken(oauthFlowStatus);
					logger.info("prod user profile ................ ");
					OauthProviderService.prodUserProfile(oauthFlowStatus);
					logger.info("start save data (optional) and redirect  ................ ");
					agreeAndBindAppCheck(provider, oauthFlowStatus
							.getAppConfig().getAppKey(), oauthFlowStatus
							.getAccessToken().getToken());
					// 手机支持部分,保存每次认证成功后的信息.
					if ("true".equals(fromPhone)) {
						// || agent == Agent.ANDROID || agent == Agent.IPHONE
						AppIautoMap record = new AppIautoMap();
						record.setIautoUserId(oauthStatusParamDTO
								.getLoginName());
						record.setClientId(oauthFlowStatus.getAppConfig()
								.getAppKey());
						record.setAppType(oauthFlowStatus.getAppConfig()
								.getAppType());
						record.setApiUid(oauthFlowStatus.getProviderUser()
								.getUserId());
						record.setAccessToken(oauthFlowStatus.getAccessToken()
								.getToken());
						record.setRefreshToken(oauthFlowStatus.getAccessToken()
								.getSecret());
						aSCoreService.saveAuthStatus(record,
								AppConstant.AUTH_LOGIN_PHONE);
						if (StrUtil.isEmpty(oauthStatusParamDTO.getBackurl())) {
							backurl = AppConstant.INDEX_URL;
						} else {
							backurl = oauthStatusParamDTO.getBackurl();
						}
					} else {
						if (StrUtil.isNotEmpty(oauthStatusParamDTO
								.getLoginName())) {
							if (aSCoreService
									.hasAgreeBindConfig(oauthStatusParamDTO
											.getLoginName())) {
								AppIautoMap record = new AppIautoMap();
								record.setIautoUserId(oauthStatusParamDTO
										.getLoginName());
								record.setClientId(oauthFlowStatus
										.getAppConfig().getAppKey());
								record.setAppType(oauthFlowStatus
										.getAppConfig().getAppType());
								record.setApiUid(oauthFlowStatus
										.getProviderUser().getUserId());
								record.setAccessToken(oauthFlowStatus
										.getAccessToken().getToken());
								record.setRefreshToken(oauthFlowStatus
										.getAccessToken().getSecret());
								aSCoreService.saveAuthStatus(record,
										AppConstant.AUTH_LOGIN_DEVICE);
								backurl = conCallbackSuccessUrl(
										backurl,
										oauthFlowStatus.getAccessToken()
												.getToken(),
										StrUtil.nullToString(oauthFlowStatus
												.getAccessToken().getSecret()),
										StrUtil.nullToString(oauthFlowStatus
												.getProviderUser().getUserId()),
										oauthFlowStatus.getAppConfig()
												.getAppKey());
							} else {
								backurl = req.getContextPath()
										+ this.conAgreeUrl(backurl,
												oauthFlowStatus
														.getAccessToken()
														.getToken(),
												oauthFlowStatus
														.getAccessToken()
														.getSecret(),
												oauthFlowStatus
														.getProviderUser()
														.getUserId(),
												oauthFlowStatus.getAppConfig()
														.getAppKey(), provider,
												oauthStatusParamDTO
														.getLoginName());
								// return "/flow/device/switchAgree";
								// aSCoreService.saveAuthHistory(provider,
								// oauthStatusParamDTO.getLoginName(),
								// AppConstant.AUTH_LOGIN_DEVICE);
							}
						} else {
							backurl = conCallbackSuccessUrl(
									backurl,
									oauthFlowStatus.getAccessToken().getToken(),
									StrUtil.nullToString(oauthFlowStatus
											.getAccessToken().getSecret()),
									StrUtil.nullToString(oauthFlowStatus
											.getProviderUser().getUserId()),
									oauthFlowStatus.getAppConfig().getAppKey());
						}
					}
				} catch (ASBaseException e) {
					errCode = e.getErrCode();
					errMsg = messageService.getMessage(req,
							MessageConstant.MSG_AUTHENTICATE_FAILED, errCode);
					logger.error(errMsg, e.getMessage());
				} catch (Exception e) {
					errMsg = messageService.getMessage(req,
							MessageConstant.MSG_AUTHENTICATE_FAILED,
							AuthErrorCodeConstant.AUTH_CALLBACK_FAILED);
					logger.exception(errMsg, e);
				}
				result = new CallbackCacheResult();
				result.setBackurl(backurl);
				result.setCacheTime(Calendar.getInstance().getTimeInMillis());
				result.setErrCode(errCode);
				result.setErrMsg(errMsg);
				result.setFromPhone(fromPhone);
				result.setErrUrl(errurl);
				session.setAttribute(key.toString(), result);
			}
		}
		return result;
	}

	private final void agreeAndBindAppCheck(String appType, String clientId,
			String accessToken) {
		if (StrUtil.isEmpty(clientId)) {
			throw new ASParamValidaterException(
					MessageConstant.MSG_AGREE_AND_BIND_FAILED,
					AuthErrorCodeConstant.APP_NO_CLIENT_ID);
		}

		if (StrUtil.isEmpty(accessToken)) {
			throw new ASParamValidaterException(
					MessageConstant.MSG_AGREE_AND_BIND_FAILED,
					AuthErrorCodeConstant.APP_NO_ACCESS_TOKEN);
		}

		if (!aSCoreService.checkClientIdValid(appType, clientId)) {
			throw new ASParamValidaterException(
					MessageConstant.MSG_AGREE_AND_BIND_FAILED,
					AuthErrorCodeConstant.APP_CLIENT_ID_INVALID);
		}
	}

	private String conAgreeUrl(String backurl, String accessToken,
			String refreshToken, String uid, String clientId, String appType,
			String loginName) throws UnsupportedEncodingException {
		StringBuilder params = new StringBuilder();
		params.append("/page/iAuto3rdBind?");
		params.append("clientId=");
		params.append(clientId);
		params.append("&accessToken=");
		params.append(accessToken);
		params.append("&refreshToken=");
		params.append(refreshToken);
		params.append("&iautoUserId=");
		params.append(loginName);
		params.append("&uid=");
		params.append(uid);
		params.append("&appType=");
		params.append(appType);
		params.append("&st=");
		params.append(Math.random());
		params.append("&backurl=");
		params.append(URLEncoder.encode(backurl, "UTF-8"));
		return params.toString();
	}

	private String conCallbackSuccessUrl(String backurl, String accessToken,
			String refreshToken, String uid, String clientId) {
		StringBuilder redUrl = new StringBuilder();
		redUrl.append(backurl);
		redUrl.append("?accessToken=");
		redUrl.append(accessToken);
		redUrl.append("&refreshToken=");
		redUrl.append(refreshToken);
		redUrl.append("&uid=");
		redUrl.append(uid);
		redUrl.append("&clientId=");
		redUrl.append(clientId);
		logger.info("redirect url: " + redUrl.toString());
		return redUrl.toString();
	}
}
