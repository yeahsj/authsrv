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
import net.suntec.framework.util.OauthProviderService;
import net.suntec.framework.util.ServerPathUtil;
import net.suntec.framework.util.SessionUtil;
import net.suntec.oauthsrv.action.param.IautoDeviceParamDTO;
import net.suntec.oauthsrv.action.util.IautoDeviceUtil;
import net.suntec.oauthsrv.action.util.IautoPhoneUtil;
import net.suntec.oauthsrv.framework.dto.OauthFlowStatus;
import net.suntec.oauthsrv.framework.dto.OauthStatusParamDTO;
import net.suntec.oauthsrv.service.ASCoreService;
import net.suntec.oauthsrv.service.ASDeviceService;
import net.suntec.oauthsrv.service.MessageService;

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
 * 
 * @项目名称: OauthSrv
 * @功能描述:
 * @当前版本： 1.0
 * @创建时间: 2014年11月25日 下午1:26:05
 * @author: <a href="mailto:yeahsj@yahoo.com.cn">yeahsj</a>
 * @修改历史:
 */

@Controller
@RequestMapping(value = "/v2/api/auth")
public class CommonEndpointAction {

	@Autowired
	ASDeviceService aSDeviceService = null;
	@Autowired
	ASCoreService aSCoreService;
	@Autowired
	MessageService messageService;

	private final Logger logger = LoggerFactory
			.getLogger(DeviceEndpointAction.class);

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

	private String handleErrorUrl(HttpServletResponse res, String errurl,
			String params) throws IOException {
		res.sendRedirect(errurl + "?" + params);
		return null;
	}

	public static void clearSession(HttpServletRequest req, String provider) {
		req.getSession().setAttribute("CALLBACK-" + provider, 0);
		SessionUtil.removeSessionOauthStatus(req, provider);
		SessionUtil.removeSessionOauthParamsStatus(req, provider);
	}

	/**
	 * authenticate 的参数检查
	 * 
	 * @param req
	 * @param oauthParam
	 */
	private void checkAuthParams(HttpServletRequest req, String appType,
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

}
