package net.suntec.oauthsrv.action.v2;

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
import net.suntec.framework.util.NcmsUtil;
import net.suntec.framework.util.OauthProviderService;
import net.suntec.framework.util.ServerPathUtil;
import net.suntec.framework.util.SessionUtil;
import net.suntec.oauthsrv.action.jsonresult.AgreeBindStatusResult;
import net.suntec.oauthsrv.action.jsonresult.AppInfoResult;
import net.suntec.oauthsrv.action.jsonresult.TokenResult;
import net.suntec.oauthsrv.action.param.IautoDeviceParamDTO;
import net.suntec.oauthsrv.action.util.IautoDeviceUtil;
import net.suntec.oauthsrv.action.util.SpringResultUtil;
import net.suntec.oauthsrv.dto.AppConfig;
import net.suntec.oauthsrv.dto.AppIautoBindConfig;
import net.suntec.oauthsrv.dto.AppIautoMap;
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
@RequestMapping(value = "/v2/api/device")
public final class DeviceEndpointAction {
	@Autowired
	ASDeviceService aSDeviceService = null;
	@Autowired
	ASCoreService aSCoreService;
	@Autowired
	MessageService messageService;
	@Autowired
	NcmsUtil ncmsUtil;

	private final Logger logger = LoggerFactory
			.getLogger(DeviceEndpointAction.class);

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
			aSDeviceService
					.saveAgreeAndBindApp(record, agreeParam,
							AppConstant.AUTH_LOGIN_DEVICE,
							V2Constant.SAVE_AUTH_HISTORY);
			ncmsUtil.send(iautoDeviceParamDTO.getSessionToken());
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
	 * 
	 * @param req
	 * @param res
	 * @param provider
	 * @param iautoDeviceParamDTO
	 * @return
	 */
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
					AppConstant.AUTH_LOGOUT_DEVICE,
					V2Constant.SAVE_AUTH_HISTORY);
			ncmsUtil.send(iautoDeviceParamDTO.getSessionToken());
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
