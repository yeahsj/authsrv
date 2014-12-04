package net.suntec.oauthsrv.action;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.suntec.constant.AuthErrorCodeConstant;
import net.suntec.framework.constant.AppConstant;
import net.suntec.framework.constant.MessageConstant;
import net.suntec.framework.dto.SpringDetailJsonResult;
import net.suntec.framework.dto.SpringErrorJsonResult;
import net.suntec.framework.dto.SpringJsonResult;
import net.suntec.framework.exception.ASBaseException;
import net.suntec.framework.exception.ASParamValidaterException;
import net.suntec.framework.util.OauthProviderService;
import net.suntec.framework.util.ServerPathUtil;
import net.suntec.framework.util.SessionUtil;
import net.suntec.oauthsrv.action.jsonresult.AgreeBindStatusResult;
import net.suntec.oauthsrv.action.jsonresult.AppInfoResult;
import net.suntec.oauthsrv.action.jsonresult.TokenResult;
import net.suntec.oauthsrv.action.param.IautoDeviceParamDTO;
import net.suntec.oauthsrv.action.util.IautoDeviceUtil;
import net.suntec.oauthsrv.action.util.IautoPhoneUtil;
import net.suntec.oauthsrv.action.util.SpringResultUtil;
import net.suntec.oauthsrv.dto.AppConfig;
import net.suntec.oauthsrv.dto.AppIautoBindConfig;
import net.suntec.oauthsrv.dto.AppIautoMap;
import net.suntec.oauthsrv.framework.dto.OauthFlowStatus;
import net.suntec.oauthsrv.framework.dto.OauthStatusParamDTO;
import net.suntec.oauthsrv.service.ASCoreService;
import net.suntec.oauthsrv.service.ASDeviceService;
import net.suntec.oauthsrv.service.MessageService;

import org.scribe.model.Token;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.openjava.core.util.StrUtil;

/**
 * 
 * @项目名称: OauthSrv
 * @功能描述:
 * @当前版本： 1.0
 * @创建时间: 2014-5-27 下午12:26:31
 * @author: <a href="mailto:yeahsj@gmail.com">yeahsj</a>
 * @修改历史:
 */
@Controller
@RequestMapping(value = "/auth")
public final class OauthAction {
	@Autowired
	ASDeviceService aSDeviceService = null;
	@Autowired
	ASCoreService aSCoreService;
	@Autowired
	MessageService messageService;

	private final Logger logger = LoggerFactory.getLogger(OauthAction.class);

	/**
	 * 
	 * @param req
	 * @param res
	 * @param provider
	 * @return
	 */
	@RequestMapping(value = "/{provider}/agreeAndBindApp")
	public @ResponseBody SpringJsonResult<String> agreeAndBindApp(
			HttpServletRequest req, HttpServletResponse res,
			@PathVariable("provider") String provider,
			@ModelAttribute IautoDeviceParamDTO iautoDeviceParamDTO) {
		logger.info("start [OAUTH-1-3] ....... ");
		String errMsg = null;
		String clientId = req.getParameter("clientId");
		String accessToken = req.getParameter("accessToken");
		String refreshToken = req.getParameter("refreshToken");
		Integer errCode = AppConstant.ERROR_CODE;
		try {
			agreeAndBindAppCheck(clientId, accessToken);
			String loginName = IautoDeviceUtil.getIautoDeviceUserName(req,
					iautoDeviceParamDTO, messageService);
			AppIautoBindConfig agreeParam = new AppIautoBindConfig();
			agreeParam.setUserName(loginName);
			agreeParam.setAppType(provider);
			AppIautoMap record = new AppIautoMap();
			record.setIautoUserId(loginName);
			record.setClientId(clientId);
			record.setAppType(provider);
			record.setApiUid("");
			record.setAccessToken(accessToken);
			record.setRefreshToken(refreshToken);
			aSDeviceService.saveAgreeAndBindApp(record, agreeParam,
					AppConstant.AUTH_LOGIN_DEVICE);
		} catch (ASBaseException e) {
			errCode = e.getErrCode();
			errMsg = messageService.getMessage(req,
					MessageConstant.MSG_AGREE_AND_BIND_FAILED,
					e.getLocalizedMessage());
			logger.error(errMsg);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			errMsg = messageService.getMessage(req,
					MessageConstant.MSG_AGREE_AND_BIND_FAILED,
					e.getLocalizedMessage());
		}
		return SpringResultUtil.jsonResult(errMsg, errCode);
	}

	/**
	 * 车机解绑App接口 ,这样下次用户需要重新走oauth流程
	 * 
	 * @param req
	 * @param res
	 * @param provider
	 * @return
	 * @throws IOException
	 */
	@Deprecated
	@RequestMapping(value = "/{provider}/unbind")
	public @ResponseBody SpringJsonResult<String> unbind(
			HttpServletRequest req, HttpServletResponse res,
			@PathVariable("provider") String provider,
			@ModelAttribute IautoDeviceParamDTO iautoDeviceParamDTO) {
		String errMsg = null;
		logger.debug("appType:" + provider);
		Integer errCode = AppConstant.ERROR_CODE;
		try {
			String loginName = IautoDeviceUtil.getIautoDeviceUserName(req,
					iautoDeviceParamDTO, messageService);
			AppIautoMap record = new AppIautoMap();
			record.setIautoUserId(loginName);
			record.setAppType(provider);
			aSDeviceService.saveLogoutApp(record);
		} catch (ASBaseException e) {
			errCode = e.getErrCode();
			errMsg = messageService.getMessage(req,
					MessageConstant.MSG_AGREE_AND_BIND_FAILED,
					e.getLocalizedMessage());
			logger.error(errMsg);
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			errMsg = ex.getLocalizedMessage();
		}
		return SpringResultUtil.jsonResult(errMsg, errCode);
	}

	/**
	 * 这个接口只支持device访问 获取Token,如果用户之前已经激活了该App,则直接从数据库中获取Token,如果之前用户未激活过Token,
	 * 则走oauth流程获取Token( /auth/twitter )
	 */
	@RequestMapping(value = "/{provider}/token")
	public @ResponseBody SpringJsonResult<TokenResult> fetchToken(
			HttpServletRequest req, HttpServletResponse res,
			@PathVariable("provider") String provider,
			@ModelAttribute IautoDeviceParamDTO iautoDeviceParamDTO) {
		logger.info("start [OAUTH-1-1] ...... ");
		SpringJsonResult<TokenResult> result = null;
		String errMsg = null;
		AppIautoMap record = null;
		boolean isBind = false;
		boolean noToken = true; // 标记服务器上有没有该iauto用户的对应的App信息
		Integer errCode = AppConstant.ERROR_CODE;
		try {
			/**
			 * 1: 首先从session中判断是否已存在loginName 2: 没有则从iauto获取 3: 返回loginName
			 */
			String loginName = IautoDeviceUtil.getIautoDeviceUserName(req,
					iautoDeviceParamDTO, messageService);
			record = aSDeviceService.saveFetchToken(iautoDeviceParamDTO,
					provider, loginName);
			if (null == record) {
				isBind = aSCoreService.hasAgreeBindConfig(loginName);
				noToken = true;
			} else {
				noToken = false;
			}
		} catch (ASBaseException e) {
			errCode = e.getErrCode();
			errMsg = messageService.getMessage(req,
					MessageConstant.MSG_AGREE_AND_BIND_FAILED,
					e.getLocalizedMessage());
			logger.error(errMsg);
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			errMsg = messageService.getMessage(req,
					MessageConstant.MSG_TOKEN_FAILED, ex.getLocalizedMessage());
		}

		if (!StrUtil.isEmpty(errMsg)) {
			result = new SpringErrorJsonResult<TokenResult>();
			result.setCode(errCode);
			result.setErrMsg(errMsg);
		} else {
			if (noToken) {
				AgreeBindStatusResult<TokenResult> result2 = new AgreeBindStatusResult<TokenResult>();
				result2.setCode(AppConstant.SUCCESS_CODE);
				result2.setIsBind(isBind);
				result = result2;
			} else {
				result = new SpringDetailJsonResult<TokenResult>();
				TokenResult tokenResult = new TokenResult();
				tokenResult.setAccessToken(record.getAccessToken());
				tokenResult.setRefreshToken(StrUtil.nullToString(record
						.getRefreshToken()));
				tokenResult.setClientId(record.getClientId());
				tokenResult.setUid(StrUtil.nullToString(record.getApiUid()));
				result.setCode(AppConstant.SUCCESS_CODE);
				result.setResult(tokenResult);
			}
		}
		return result;
	}

	/**
	 * 车机解绑App接口 ,这样下次用户需要重新走oauth流程
	 * 
	 * @param req
	 * @param res
	 * @param provider
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/{provider}/redirecturl")
	public @ResponseBody SpringJsonResult<String> getRedirectURL(
			HttpServletRequest req, HttpServletResponse res,
			@PathVariable("provider") String provider,
			@ModelAttribute OauthStatusParamDTO oauthParam) {
		logger.info("start [OAUTH-1-7] ...... ");
		clearSession(req, provider);
		String errMsg = null;
		String clientId = null;
		Integer errCode = AppConstant.ERROR_CODE;
		SpringJsonResult<String> result;
		String redirectUrl = null;
		logger.debug("appType:" + provider);
		try {
			String fromPhone = StrUtil.nullToString(oauthParam.getFromPhone());
			checkAuthParams(req, provider, oauthParam);
			if ("true".equals(fromPhone)) {
				clientId = aSCoreService.selectClientId(provider);
			} else {
				String deviceNo = req.getParameter("deviceNo");
				clientId = aSCoreService.saveAfterSelectClientId(provider,
						deviceNo);
			}
			// String clientId =
			// oauthProvider.saveSelectClientId(provider,deviceNo);
			if (StrUtil.isEmpty(clientId)) {
				errMsg = messageService.getMessage(req,
						MessageConstant.MSG_AUTHENTICATE_FAILED,
						AuthErrorCodeConstant.APP_NO_CLIENT_ID);
			} else {
				String localServer = ServerPathUtil
						.getCurrentServerRootPath(req);
				OauthFlowStatus oauthFlowStatus = OauthProviderService
						.initOauthFlowStatus(provider, clientId, localServer);

				OauthProviderService.buildRedirectUrl(oauthFlowStatus);
				SessionUtil.saveSessionOauthStatus(req, provider,
						oauthFlowStatus);
				redirectUrl = oauthFlowStatus.getOAuthAuthorizationUrl();
				if (provider.equals("pocket")) {
					redirectUrl += "&mobile=1";
				}
				logger.info("Redirect URL: " + redirectUrl);
				// res.sendRedirect(oauthFlowStatus.getOAuthAuthorizationUrl());
			}
		} catch (NullPointerException e) {
			errCode = AuthErrorCodeConstant.NULL_POINTER_ERROR;
			errMsg = messageService.getMessage(req,
					MessageConstant.MSG_AUTHENTICATE_FAILED, errCode);
			logger.error(errMsg);
		} catch (ASBaseException e) {
			errCode = e.getErrCode();
			errMsg = messageService.getMessage(req,
					MessageConstant.MSG_AUTHENTICATE_FAILED, errCode);
			logger.error(errMsg);
		} catch (Exception e) {
			errCode = AuthErrorCodeConstant.AUTH_FAILED;
			errMsg = messageService.getMessage(req,
					MessageConstant.MSG_AUTHENTICATE_FAILED, errCode);
			logger.error(errMsg);
		}
		if (!StrUtil.isEmpty(errMsg)) {
			String fromPhone = StrUtil.nullToString(oauthParam.getFromPhone());
			if (!"true".equals(fromPhone)) {
				SessionUtil.removeSessionOauthStatus(req, provider);
				SessionUtil.removeSessionOauthParamsStatus(req, provider);
			}
			result = new SpringErrorJsonResult<String>();
			result.setCode(errCode);
			result.setErrMsg(errMsg);
		} else {
			result = new SpringDetailJsonResult<String>();
			result.setResult(redirectUrl);
			result.setCode(AppConstant.SUCCESS_CODE);
		}
		return result;
	}

	/**
	 * oauth 流程起点
	 * 
	 * @param sessionToken
	 *            : Iauto 用户token
	 * @param backurl
	 *            required,当前服务器获取到Token后，返回到客户端的路径
	 * @param provider
	 *            required,app type ( twitter | facebook | soundcloud and so on
	 * @param saveFlag
	 *            optional,在获取到Token后，是否保存Token到数据库中的标记. true / 1
	 * @param fromPhone
	 *            optional,标记是否来自手机客户端
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/{provider}")
	public String authenticate(HttpServletRequest req, HttpServletResponse res,
			@ModelAttribute OauthStatusParamDTO oauthParam,
			@PathVariable("provider") String provider) throws IOException {
		clearSession(req, provider);
		logger.info("start [OAUTH-1-2] ...... ");
		logger.info("session id is :" + req.getSession().getId());
		String errMsg = null;
		String clientId = null;
		Integer errCode = AppConstant.ERROR_CODE;
		logger.debug("appType:" + provider);
		try {
			String fromPhone = StrUtil.nullToString(oauthParam.getFromPhone());
			checkAuthParams(req, provider, oauthParam);
			if ("true".equals(fromPhone)) {
				clientId = aSCoreService.selectClientId(provider);
			} else {
				String deviceNo = req.getParameter("deviceNo");
				clientId = aSCoreService.saveAfterSelectClientId(provider,
						deviceNo);
			}
			// String clientId =
			// oauthProvider.saveSelectClientId(provider,deviceNo);
			if (StrUtil.isEmpty(clientId)) {
				errMsg = messageService.getMessage(req,
						MessageConstant.MSG_AUTHENTICATE_FAILED,
						AuthErrorCodeConstant.APP_NO_CLIENT_ID);
			} else {
				String localServer = ServerPathUtil
						.getCurrentServerRootPath(req);
				OauthFlowStatus oauthFlowStatus = OauthProviderService
						.initOauthFlowStatus(provider, clientId, localServer);

				OauthProviderService.buildRedirectUrl(oauthFlowStatus);
				SessionUtil.saveSessionOauthStatus(req, provider,
						oauthFlowStatus);
				String redirectUrl = oauthFlowStatus.getOAuthAuthorizationUrl();
				if (provider.equals("pocket")) {
					redirectUrl += "&mobile=1";
				}
				logger.info("Redirect URL: " + redirectUrl);
				res.sendRedirect(redirectUrl);
			}
		} catch (NullPointerException e) {
			errCode = AuthErrorCodeConstant.NULL_POINTER_ERROR;
			errMsg = messageService.getMessage(req,
					MessageConstant.MSG_AUTHENTICATE_FAILED, errCode);
			// logger.error(errMsg);
			logger.error(errMsg, e);
		} catch (ASBaseException e) {
			errCode = e.getErrCode();
			errMsg = messageService.getMessage(req,
					MessageConstant.MSG_AUTHENTICATE_FAILED, errCode);
			logger.error(errMsg);
		} catch (Exception e) {
			errCode = AuthErrorCodeConstant.AUTH_FAILED;
			errMsg = messageService.getMessage(req,
					MessageConstant.MSG_AUTHENTICATE_FAILED, errCode);
			logger.error(errMsg);
		}
		if (!StrUtil.isEmpty(errMsg)) {
			clearSession(req, provider);
			String fromPhone = StrUtil.nullToString(oauthParam.getFromPhone());
			if ("true".equals(fromPhone)) {
				req.setAttribute("errMsg", errMsg);
				if (StrUtil.isEmpty(oauthParam.getErrurl())) {
					return AppConstant.ERROR_URL;
				} else {
					return handleErrorUrl(res, oauthParam.getErrurl(),
							"errMsg=" + errMsg + "&errCode=" + errCode);
				}
			} else {
				req.setAttribute("errMsg", errMsg);
				req.setAttribute("appType", provider);
				if (StrUtil.isEmpty(oauthParam.getErrurl())) {
					return AppConstant.DEVICE_ERROR_URL;
				} else {
					return handleErrorUrl(res, oauthParam.getErrurl(),
							"errMsg=" + errMsg + "&errCode=" + errCode);
				}
			}
		} else {
			return null;
		}
	}

	/**
	 * oauth 流程回调点
	 * 
	 * @param req
	 * @param res
	 * @param provider
	 * @param appId
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/{provider}/{sortNo}/callback")
	public String callback(HttpServletRequest req, HttpServletResponse res,
			@PathVariable("provider") String provider,
			@PathVariable("sortNo") String sortNo) throws IOException {
		logger.info("callback for [OAUTH-1-2] ...... ");
		logger.info("session id is :" + req.getSession().getId());
		String errMsg = null;
		int errCode = AppConstant.ERROR_CODE;
		String fromPhone = "";
		String errurl = null;
		boolean isResubmit = false;
		try {
			isResubmit = checkResubmit(res, req, provider);
		} catch (Exception ex) {
			isResubmit = true;
		}
		if (isResubmit) {
			errMsg = messageService.getMessage(req,
					MessageConstant.MSG_AUTHENTICATE_FAILED,
					AuthErrorCodeConstant.AUTH_CALLBACK_RESUBMIT);
			req.setAttribute("errMsg", errMsg);
		} else {
			OauthFlowStatus oauthFlowStatus = SessionUtil
					.getSessionOauthStatus(req, provider);
			OauthStatusParamDTO oauthStatusParamDTO = SessionUtil
					.getSessionOauthParamsStatus(req, provider);
			if (null == oauthFlowStatus) {
				errMsg = "oauthFlowStatus is null!";
				logger.error(errMsg);
			}
			if (null == oauthStatusParamDTO) {
				errMsg = "oauthStatusParamDTO is null!";
				logger.error(errMsg);
			}
			errurl = oauthStatusParamDTO.getErrurl();
			fromPhone = StrUtil
					.nullToString(oauthStatusParamDTO.getFromPhone());
			if (!provider.equals(oauthFlowStatus.getAppConfig().getAppType())) {
				errMsg = "appType not match!";
				logger.error(errMsg);
			} else {
				if (handleCancel(req, res, provider, fromPhone)) {
					return null;
				}
				try {
					String backurl = oauthStatusParamDTO.getBackurl();
					String code = req.getParameter("code");
					String oAuthVerifier = req.getParameter("oauth_verifier");
					checkCallBack(provider, fromPhone, backurl, code,
							oAuthVerifier);
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
					// 手机支持部分,保存每次认证成功后的信息.

					if ("true".equals(fromPhone)) {
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
							res.sendRedirect(AppConstant.INDEX_URL);
						} else {
							res.sendRedirect(oauthStatusParamDTO.getBackurl());
						}
					} else {
						if (StrUtil.isNotEmpty(oauthStatusParamDTO
								.getLoginName())
								&& aSCoreService
										.hasAgreeBindConfig(oauthStatusParamDTO
												.getLoginName())) {
							AppIautoMap record = new AppIautoMap();
							record.setIautoUserId(oauthStatusParamDTO
									.getLoginName());
							record.setClientId(oauthFlowStatus.getAppConfig()
									.getAppKey());
							record.setAppType(oauthFlowStatus.getAppConfig()
									.getAppType());
							record.setApiUid(oauthFlowStatus.getProviderUser()
									.getUserId());
							record.setAccessToken(oauthFlowStatus
									.getAccessToken().getToken());
							record.setRefreshToken(oauthFlowStatus
									.getAccessToken().getSecret());
							aSCoreService.saveAuthStatus(record,
									AppConstant.AUTH_LOGIN_DEVICE);
						}
						StringBuilder redUrl = new StringBuilder();
						redUrl.append(backurl);
						redUrl.append("?accessToken=");
						redUrl.append(oauthFlowStatus.getAccessToken()
								.getToken());
						redUrl.append("&refreshToken=");
						redUrl.append(StrUtil.nullToString(oauthFlowStatus
								.getAccessToken().getSecret()));
						redUrl.append("&uid=");
						redUrl.append(StrUtil.nullToString(oauthFlowStatus
								.getProviderUser().getUserId()));
						redUrl.append("&clientId=");
						redUrl.append(oauthFlowStatus.getAppConfig()
								.getAppKey());
						logger.info("redirect url: " + redUrl.toString());
						res.sendRedirect(redUrl.toString());
					}
				} catch (ASBaseException e) {
					errCode = e.getErrCode();
					errMsg = messageService.getMessage(req,
							MessageConstant.MSG_AUTHENTICATE_FAILED,
							e.getErrCode());
					logger.error(errMsg);
				} catch (Exception e) {
					errMsg = messageService.getMessage(req,
							MessageConstant.MSG_AUTHENTICATE_FAILED,
							AuthErrorCodeConstant.AUTH_CALLBACK_FAILED);
					logger.error(e.getMessage(), e);
				}
			}
		}

		if (!StrUtil.isEmpty(errMsg)) {
			if ("true".equals(fromPhone)) {
				req.setAttribute("errMsg", errMsg);
				if (StrUtil.isEmpty(errurl)) {
					return AppConstant.ERROR_URL;
				} else {
					return handleErrorUrl(res, errurl, "errMsg=" + errMsg
							+ "&errCode=" + errCode);
				}
			} else {
				req.setAttribute("errMsg", errMsg);
				req.setAttribute("appType", provider);
				if (StrUtil.isEmpty(errurl)) {
					return AppConstant.DEVICE_ERROR_URL;
				} else {
					return handleErrorUrl(res, errurl, "errMsg=" + errMsg
							+ "&errCode=" + errCode);
				}
			}
		} else {
			return null;
		}
	}

	@RequestMapping(value = "/{provider}/logout")
	public @ResponseBody SpringJsonResult<String> logout(
			HttpServletRequest req, HttpServletResponse res,
			@PathVariable("provider") String provider,
			@ModelAttribute IautoDeviceParamDTO iautoDeviceParamDTO) {
		logger.info("start [OAUTH-1-5] ...... ");
		String errMsg = null;
		Integer errCode = AppConstant.ERROR_CODE;
		try {
			String loginName = IautoDeviceUtil.getIautoDeviceUserName(req,
					iautoDeviceParamDTO, messageService);
			AppIautoMap appIautoMap = new AppIautoMap();
			appIautoMap.setIautoUserId(loginName);
			appIautoMap.setAppType(provider);
			clearSession(req, provider);
			aSDeviceService.saveLogout(appIautoMap,
					AppConstant.AUTH_LOGOUT_DEVICE);
		} catch (ASBaseException e) {
			errCode = e.getErrCode();
			errMsg = messageService.getMessage(req,
					MessageConstant.MSG_AGREE_AND_BIND_FAILED,
					e.getLocalizedMessage());
			logger.error(errMsg);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			errMsg = messageService.getMessage(req,
					MessageConstant.MSG_LOGOUT_FAILED, e.getLocalizedMessage());
		}

		return SpringResultUtil.jsonResult(errMsg, errCode);
	}

	/**
	 * <li>
	 * http://localhost:8080/auth/twitter/header?iautoLoginName=demo1&method=
	 * get&requestUrl=https://api.twitter.com/1.1/statuses/home_timeline.json</li>
	 * http://localhost:8080/auth/path/header?iautoLoginName=demo1&method=GET&
	 * requestUrl=https://partner.path.com/1/user/self
	 * 
	 * @param req
	 * @param res
	 * @param provider
	 * @return
	 */
	@RequestMapping(value = "/{provider}/header")
	public @ResponseBody SpringJsonResult<String> getHeader(
			HttpServletRequest req, HttpServletResponse res,
			@PathVariable("provider") String provider) {
		logger.info("start [OAUTH-1-6] ....... ");
		String requestUrl = req.getParameter("requestUrl");
		String method = req.getParameter("method");
		String clientId = req.getParameter("clientId");
		String accessToken = req.getParameter("accessToken");
		String refreshToken = req.getParameter("refreshToken");

		SpringJsonResult<String> result = null;
		String errMsg = null;
		Integer errCode = AppConstant.ERROR_CODE;
		String oauthHeader = null;
		try {
			this.getHeaderCheck(clientId, accessToken, requestUrl, method);
			// requestUrl = URLDecoder.decode(requestUrl, "UTF-8");
			logger.info("requestUrl: " + requestUrl);
			AppConfig appConfig = OauthProviderService.prodAppConfig(provider,
					clientId);
			String localServer = ServerPathUtil.getCurrentServerRootPath(req);
			appConfig.setLocalServer(localServer);
			Token token = new Token(accessToken, refreshToken);
			oauthHeader = OauthProviderService.prodAuthHeader(appConfig, token,
					requestUrl, method);
		} catch (ASBaseException e) {
			errCode = e.getErrCode();
			errMsg = messageService.getMessage(req,
					MessageConstant.MSG_HEADER_FAILED, e.getLocalizedMessage());
			logger.error(errMsg);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			errMsg = messageService.getMessage(req,
					MessageConstant.MSG_HEADER_FAILED, e.getLocalizedMessage());
		}

		if (!StrUtil.isEmpty(errMsg)) {
			result = new SpringErrorJsonResult<String>();
			result.setCode(errCode);
			result.setErrMsg(errMsg);
		} else {
			result = new SpringDetailJsonResult<String>();
			result.setCode(AppConstant.SUCCESS_CODE);
			result.setResult(oauthHeader);
		}
		return result;
	}

	@RequestMapping(value = "/{provider}/clientid")
	public @ResponseBody SpringJsonResult<AppInfoResult> clientid(
			HttpServletRequest req, HttpServletResponse res,
			@PathVariable("provider") String provider) {
		logger.info("start [OAUTH-1-7] ....... ");
		String clientId = null;
		AppInfoResult appInfoResult = null;
		SpringJsonResult<AppInfoResult> result = null;
		String errMsg = null;
		Integer errCode = AppConstant.ERROR_CODE;
		String fromPhone = req.getParameter("fromPhone");
		try {
			if ("true".equals(fromPhone)) {
				clientId = aSCoreService.selectClientId(provider);
				appInfoResult = OauthProviderService.prodAppInfoResult(
						provider, clientId);
			} else {
				String deviceNo = req.getParameter("deviceNo");
				if (StrUtil.isEmpty(deviceNo)) {
					errCode = AuthErrorCodeConstant.APP_NO_DEVICE_NO;
					errMsg = messageService.getMessage(req,
							MessageConstant.MSG_GET_CLIENTID, errCode);
				} else {
					clientId = aSCoreService.saveAfterSelectClientId(provider,
							deviceNo);
					appInfoResult = OauthProviderService.prodAppInfoResult(
							provider, clientId);
				}
			}
		} catch (ASBaseException e) {
			errCode = e.getErrCode();
			errMsg = messageService.getMessage(req,
					MessageConstant.MSG_GET_CLIENTID, e.getLocalizedMessage());
			logger.error(errMsg);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			errMsg = messageService.getMessage(req,
					MessageConstant.MSG_GET_CLIENTID, e.getLocalizedMessage());
		}

		if (!StrUtil.isEmpty(errMsg)) {
			result = new SpringErrorJsonResult<AppInfoResult>();
			result.setCode(errCode);
			result.setErrMsg(errMsg);
		} else {
			result = new SpringDetailJsonResult<AppInfoResult>();
			result.setCode(AppConstant.SUCCESS_CODE);
			result.setResult(appInfoResult);
		}
		return result;
	}

	/**
	 * authenticate 的参数检查
	 * 
	 * @param req
	 * @param oauthParam
	 */
	public void checkAuthParams(HttpServletRequest req, String appType,
			OauthStatusParamDTO oauthParam) {
		String fromPhone = StrUtil.nullToString(oauthParam.getFromPhone());
		String loginName = "";
		if ("true".equals(fromPhone)) {
			loginName = oauthParam.getLoginName();
			if (StrUtil.isEmpty(loginName)) {
				loginName = IautoPhoneUtil.getIautoPhoneUserName(oauthParam
						.getSessionToken());
			}
			if (StrUtil.isEmpty(loginName)) {
				throw new ASBaseException(
						MessageConstant.MSG_AUTHENTICATE_FAILED,
						AuthErrorCodeConstant.APP_NO_LOGIN_NAME);
			} else {
				oauthParam.setLoginName(loginName);
			}
		} else {
			if (StrUtil.isEmpty(oauthParam.getBackurl())) {
				throw new ASBaseException(
						MessageConstant.MSG_AUTHENTICATE_FAILED,
						AuthErrorCodeConstant.APP_NO_BACKURL);
			}

			String sessionToken = req.getParameter("sessionToken");
			if (StrUtil.isNotEmpty(sessionToken)) {
				IautoDeviceParamDTO iautoDeviceParamDTO = new IautoDeviceParamDTO();
				iautoDeviceParamDTO.setSessionToken(sessionToken);
				iautoDeviceParamDTO.setDeviceNo(req.getParameter("deviceNo"));
				iautoDeviceParamDTO.setPlatformVersion(req
						.getParameter("platformVersion"));
				iautoDeviceParamDTO.setIautoClientId(req
						.getParameter("iautoClientId"));
				loginName = IautoDeviceUtil.getIautoDeviceUserName(req,
						iautoDeviceParamDTO, messageService);
				oauthParam.setLoginName(loginName);
				oauthParam.setSessionToken(sessionToken);
			}
		}
		SessionUtil.saveSessionOauthParamsStatus(req, appType, oauthParam);
	}

	private final void agreeAndBindAppCheck(String clientId, String accessToken) {
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
	}

	private final void getHeaderCheck(String clientId, String accessToken,
			String requestUrl, String method) {
		if (StrUtil.isEmpty(clientId)) {
			throw new ASParamValidaterException(
					MessageConstant.MSG_HEADER_FAILED,
					AuthErrorCodeConstant.APP_NO_CLIENT_ID);
		}

		if (StrUtil.isEmpty(accessToken)) {
			throw new ASParamValidaterException(
					MessageConstant.MSG_HEADER_FAILED,
					AuthErrorCodeConstant.APP_NO_ACCESS_TOKEN);
		}

		if (StrUtil.isEmpty(requestUrl)) {
			throw new ASParamValidaterException(
					MessageConstant.MSG_HEADER_FAILED,
					AuthErrorCodeConstant.APP_NO_BACKURL);
		}

		if (StrUtil.isEmpty(method)) {
			throw new ASParamValidaterException(
					MessageConstant.MSG_HEADER_FAILED,
					AuthErrorCodeConstant.APP_NO_METHOD);
		}
	}

	private void checkCallBack(String appType, String fromPhone,
			String backurl, String code, String oAuthVerifier) {
		if ("true".equals(fromPhone)) {
			if (StrUtil.isEmpty(backurl)) {
				throw new ASParamValidaterException(
						MessageConstant.MSG_AUTHENTICATE_FAILED,
						AuthErrorCodeConstant.APP_NO_BACKURL);
			}
			if (StrUtil.isEmpty(code) && StrUtil.isEmpty(oAuthVerifier)
					&& !appType.equals("pocket")) {
				throw new ASParamValidaterException(
						MessageConstant.MSG_AUTHENTICATE_FAILED,
						AuthErrorCodeConstant.APP_NO_CODE_OR_VERIFY);
			}
		} else {
			if (StrUtil.isEmpty(code) && StrUtil.isEmpty(oAuthVerifier)
					&& !appType.equals("pocket")) {
				throw new ASParamValidaterException(
						MessageConstant.MSG_AUTHENTICATE_FAILED,
						AuthErrorCodeConstant.APP_NO_CODE_OR_VERIFY);
			}
		}
	}

	private boolean checkResubmit(HttpServletResponse res,
			HttpServletRequest req, String provider) {
		int count = 0;
		Object cntStr = req.getSession().getAttribute("CALLBACK-" + provider);
		if (StrUtil.isNotEmpty(cntStr)) {
			count = (Integer) cntStr;
		}
		if (count > 0) {
			return true;
		} else {
			req.getSession().setAttribute("CALLBACK-" + provider, count + 1);
			return false;
		}
	}

	/**
	 * 处理第三方app取消后的操作 目前做法是希望第三方页面取消后，都转向到列表页
	 * 
	 * @important
	 * @param req
	 * @param res
	 * @param provider
	 * @param fromPhone
	 * @return
	 * @throws IOException
	 */
	private boolean handleCancel(HttpServletRequest req,
			HttpServletResponse res, String provider, String fromPhone)
			throws IOException {
		// TODO
		boolean isHandle = false;
		boolean fromPhoneBl = "true".equals(fromPhone);
		String localServer = ServerPathUtil.getCurrentServerRootPath(req);
		if ("twitter".equals(provider)) {
			String denied = req.getParameter("denied");
			if (!StrUtil.isEmpty(denied) && fromPhoneBl) {
				if (fromPhoneBl) {
					isHandle = true;
					res.sendRedirect(localServer + "/redirect.html");
				}
			}
		}
		if ("soundcloud".equals(provider)) {
			String error = req.getParameter("error");
			if (!StrUtil.isEmpty(error) && fromPhoneBl) {
				if (fromPhoneBl) {
					isHandle = true;
					res.sendRedirect(localServer + "/redirect.html");
				}
			}
		}
		return isHandle;
	}

	public String handleErrorUrl(HttpServletResponse res, String errurl,
			String params) throws IOException {
		res.sendRedirect(errurl + "?" + params);
		return null;
	}

	private void clearSession(HttpServletRequest req, String provider) {
		req.getSession().setAttribute("CALLBACK-" + provider, 0);
		SessionUtil.removeSessionOauthStatus(req, provider);
		SessionUtil.removeSessionOauthParamsStatus(req, provider);
	}
}
