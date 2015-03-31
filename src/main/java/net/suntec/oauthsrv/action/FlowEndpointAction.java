package net.suntec.oauthsrv.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.suntec.constant.AuthErrorCodeConstant;
import net.suntec.framework.constant.AppConstant;
import net.suntec.framework.constant.MessageConstant;
import net.suntec.framework.dto.SpringDetailJsonResult;
import net.suntec.framework.dto.SpringErrorJsonResult;
import net.suntec.framework.dto.SpringJsonResult;
import net.suntec.framework.dto.SpringQueryJsonResult;
import net.suntec.framework.exception.ASBaseException;
import net.suntec.framework.exception.ASParamValidaterException;
import net.suntec.framework.util.ASLogger;
import net.suntec.oauthsrv.action.check.AccessTokenCheck;
import net.suntec.oauthsrv.action.check.ActionCheck;
import net.suntec.oauthsrv.action.check.ClientIdCheck;
import net.suntec.oauthsrv.action.jsonresult.AgreeBindStatusResult;
import net.suntec.oauthsrv.action.jsonresult.AuthStatusResult;
import net.suntec.oauthsrv.action.param.IautoDeviceParamDTO;
import net.suntec.oauthsrv.action.param.IautoPhoneParamDTO;
import net.suntec.oauthsrv.action.util.IautoDeviceUtil;
import net.suntec.oauthsrv.action.util.IautoPhoneUtil;
import net.suntec.oauthsrv.action.util.SpringResultUtil;
import net.suntec.oauthsrv.dto.AppBase;
import net.suntec.oauthsrv.dto.AppIautoMap;
import net.suntec.oauthsrv.service.ASCoreService;
import net.suntec.oauthsrv.service.ASDeviceService;
import net.suntec.oauthsrv.service.ASPhoneService;
import net.suntec.oauthsrv.service.MessageService;

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
 * @创建时间: 2014年12月4日 下午5:50:02
 * @author: <a href="mailto:yeahsj@yahoo.com.cn">yeahsj</a>
 * @修改历史:
 */
@Controller
@RequestMapping(value = "/api")
public class FlowEndpointAction {
	private final ASLogger logger = new ASLogger(FlowEndpointAction.class);

	@Autowired
	MessageService messageService;
	@Autowired
	ASPhoneService aSPhoneService;
	@Autowired
	ASCoreService aSCoreService;
	@Autowired
	ASDeviceService aSDeviceService;

	@Deprecated
	@RequestMapping(value = "/doLoginEndpoint")
	public @ResponseBody SpringJsonResult<String> doLoginEndpoint(
			HttpServletRequest req, HttpServletResponse res,
			@ModelAttribute IautoPhoneParamDTO iautoPhoneParamDTO) {
		String errMsg = null;
		SpringJsonResult<String> result;
		try {
			logger.debug("do login");
			String loginName = iautoPhoneParamDTO.getLoginName();
			if (StrUtil.isEmpty(loginName)) {
				loginName = IautoPhoneUtil
						.getIautoPhoneUserName(iautoPhoneParamDTO);
			}
			// String userName =
			// IautoPhoneUtil.getIautoPhoneUserName(iautoPhoneParamDTO);
			if (null == loginName) {
				errMsg = " user not exists or token expired";
			}
			// String userName = IautoActionUtil.getIautoPhoneUserName(req);
		} catch (ASBaseException ex) {
			errMsg = ex.getMessage();
			logger.error(ex.getMessage(), ex);
		} catch (Exception ex) {
			errMsg = ex.getMessage();
			logger.exception(errMsg);
		}

		if (!StrUtil.isEmpty(errMsg)) {
			result = new SpringErrorJsonResult<String>();
			result.setCode(AppConstant.ERROR_CODE);
			result.setErrMsg(errMsg);
		} else {
			result = new SpringDetailJsonResult<String>();
			result.setResult("success");
			result.setCode(AppConstant.SUCCESS_CODE);
		}
		return result;
	}

	@RequestMapping(value = "/bindAppListEndpoint")
	public @ResponseBody SpringJsonResult<AppBase> bindAppListEndpoint(
			HttpServletRequest req,
			@ModelAttribute IautoPhoneParamDTO iautoPhoneParamDTO) {
		logger.info("start [OAUTH-2-9] ....... ");
		SpringJsonResult<AppBase> result;
		String lastUpdatetime = null;
		List<AppBase> appListResult = null;
		String errMsg = null;
		try {
			String userName = req.getParameter("loginName");
			if (StrUtil.isEmpty(userName)) {
				userName = IautoPhoneUtil
						.getIautoPhoneUserName(iautoPhoneParamDTO);
			}
			if (StrUtil.isEmpty(userName)) {
				errMsg = messageService.getMessage(req,
						MessageConstant.MSG_LIST_BINDS_FAILED,
						AuthErrorCodeConstant.APP_NO_LOGIN_NAME);
			} else {
				appListResult = aSPhoneService
						.selectPhoneBindAppListNew(userName);
//				lastUpdatetime = aSCoreService
//						.selectLastestHistoryTime(userName);
			}
			// appListResult = ApiActionConvert.connvert(datas);
		} catch (ASBaseException ex) {
			errMsg = messageService.getMessage(req,
					MessageConstant.MSG_LIST_BINDS_FAILED, ex.getErrCode());
			logger.error(errMsg);
		} catch (Exception ex) {
			errMsg = messageService.getMessage(req,
					MessageConstant.MSG_LIST_BINDS_FAILED,
					AuthErrorCodeConstant.API_FETCH_APP_LIST);
			logger.exception(ex.getMessage());
		}

		if (!StrUtil.isEmpty(errMsg)) {
			result = new SpringErrorJsonResult<AppBase>();
			result.setCode(AppConstant.ERROR_CODE);
			result.setErrMsg(errMsg);
		} else {
			result = new SpringQueryJsonResult<AppBase>();
			result.setLists(appListResult);
			result.setLastUpdateTime(lastUpdatetime);
			result.setCode(AppConstant.SUCCESS_CODE);
		}
		return result;
	}

	@RequestMapping(value = "/listBindAppEndpoint")
	public @ResponseBody SpringJsonResult<AppBase> listBindAppEndpoint(
			HttpServletRequest req,
			@ModelAttribute IautoPhoneParamDTO iautoPhoneParamDTO) {
		logger.info("start [OAUTH-2-4] ....... ");
		SpringJsonResult<AppBase> result;
		String lastUpdatetime = null;
		List<AppBase> appListResult = null;
		String errMsg = null;
		try {
			String userName = req.getParameter("loginName");
			if (StrUtil.isEmpty(userName)) {
				userName = IautoPhoneUtil
						.getIautoPhoneUserName(iautoPhoneParamDTO);
			}
			if (StrUtil.isEmpty(userName)) {
				errMsg = messageService.getMessage(req,
						MessageConstant.MSG_LIST_BINDS_FAILED,
						AuthErrorCodeConstant.APP_NO_LOGIN_NAME);
			} else {
				appListResult = aSPhoneService.selectPhoneBindAppList(userName);
				lastUpdatetime = aSCoreService
						.selectLastestHistoryTime(userName);
			}
			// appListResult = ApiActionConvert.connvert(datas);
		} catch (ASBaseException ex) {
			errMsg = messageService.getMessage(req,
					MessageConstant.MSG_LIST_BINDS_FAILED, ex.getErrCode());
			logger.error(errMsg);
		} catch (Exception ex) {
			errMsg = messageService.getMessage(req,
					MessageConstant.MSG_LIST_BINDS_FAILED,
					AuthErrorCodeConstant.API_FETCH_APP_LIST);
			logger.exception(errMsg);
		}

		if (!StrUtil.isEmpty(errMsg)) {
			result = new SpringErrorJsonResult<AppBase>();
			result.setCode(AppConstant.ERROR_CODE);
			result.setErrMsg(errMsg);
		} else {
			result = new SpringQueryJsonResult<AppBase>();
			result.setLists(appListResult);
			result.setLastUpdateTime(lastUpdatetime);
			result.setCode(AppConstant.SUCCESS_CODE);
		}
		return result;
	}

	/**
	 * 解绑 App ,这样下次用户需要重新走oauth流程, ajax方式
	 * 
	 * @param req
	 * @param res
	 * @param provider
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/{provider}/unbindEndpoint")
	public @ResponseBody SpringJsonResult<AppBase> unbindEndpoint(
			HttpServletRequest req, HttpServletResponse res,
			@PathVariable("provider") String provider,
			@ModelAttribute IautoPhoneParamDTO iautoPhoneParamDTO)
			throws IOException {
		logger.info("start [OAUTH-2-5] ....... ");
		String errMsg = null;
		String lastUpdatetime = null;
		SpringJsonResult<AppBase> result;
		AppBase appbase = null;
		logger.debug("Calling unbind .... ");
		logger.debug("appType:" + provider);

		try {
			AppIautoMap record = new AppIautoMap();
			String userName = req.getParameter("loginName");
			if (StrUtil.isEmpty(userName)) {
				userName = IautoPhoneUtil
						.getIautoPhoneUserName(iautoPhoneParamDTO);
			}
			if (StrUtil.isEmpty(userName)) {
				errMsg = messageService.getMessage(req,
						MessageConstant.MSG_LIST_BINDS_FAILED,
						AuthErrorCodeConstant.API_LOGIN_NAME_IS_REQUIRED);
			} else {
				record.setIautoUserId(userName);
				record.setAppType(provider);
				appbase = aSPhoneService.deletePhoneLogoutApp(record);
				lastUpdatetime = aSCoreService
						.selectLastestHistoryTime(userName);
			}
		} catch (ASBaseException ex) {
			errMsg = messageService.getMessage(req,
					MessageConstant.MSG_UN_BIND_FAILED, ex.getErrCode());
			logger.error(errMsg);
		} catch (Exception ex) {
			errMsg = messageService.getMessage(req,
					MessageConstant.MSG_UN_BIND_FAILED,
					AuthErrorCodeConstant.API_UNBIDN_FAILED);
			logger.exception(errMsg);
		}

		if (!StrUtil.isEmpty(errMsg)) {
			result = new SpringErrorJsonResult<AppBase>();
			result.setCode(AppConstant.ERROR_CODE);
			result.setErrMsg(errMsg);
		} else {
			result = new SpringDetailJsonResult<AppBase>();
			result.setResult(appbase);
			result.setLastUpdateTime(lastUpdatetime);
			result.setCode(AppConstant.SUCCESS_CODE);
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
	@Deprecated
	@RequestMapping(value = "/agreeSwitchForPhone")
	public @ResponseBody SpringJsonResult<String> agreeSwitchForPhone(
			HttpServletRequest req, HttpServletResponse res,
			@ModelAttribute IautoPhoneParamDTO iautoPhoneParamDTO) {
		logger.info("start [OAUTH-2-2] ....... ");
		String errMsg = null;
		try {
			String status = req.getParameter("status");
			String userName = req.getParameter("loginName");
			if (StrUtil.isEmpty(userName)) {
				userName = IautoPhoneUtil
						.getIautoPhoneUserName(iautoPhoneParamDTO);
			}
			aSCoreService.saveAgreeSwitch(userName, "", status);
		} catch (ASBaseException ex) {
			errMsg = messageService.getMessage(req,
					MessageConstant.MSG_AGREE_SWITCH_FAILED,
					ex.getLocalizedMessage());
			logger.error(errMsg);
		} catch (Exception ex) {
			errMsg = messageService.getMessage(req,
					MessageConstant.MSG_AGREE_SWITCH_FAILED,
					ex.getLocalizedMessage());
			logger.exception(errMsg);
		}
		return SpringResultUtil.jsonResult(errMsg);
	}

	/**
	 * 
	 * @param req
	 * @param res
	 * @param provider
	 * @return
	 */
	@RequestMapping(value = "/fetchAgreeSwitchForPhone")
	public @ResponseBody SpringJsonResult<String> fetchAgreeSwitchForPhone(
			HttpServletRequest req, HttpServletResponse res,
			@ModelAttribute IautoPhoneParamDTO iautoPhoneParamDTO) {
		logger.info("start [OAUTH-2-3] ....... ");
		String errMsg = null;
		SpringJsonResult<String> result = null;
		boolean isBind = false;
		try {
			String userName = req.getParameter("loginName");
			if (StrUtil.isEmpty(userName)) {
				userName = IautoPhoneUtil
						.getIautoPhoneUserName(iautoPhoneParamDTO);
			}
			isBind = aSCoreService.hasAgreeBindConfig(userName);
		} catch (ASBaseException ex) {
			errMsg = messageService.getMessage(req,
					MessageConstant.MSG_FETCH_AGREE_SWITCH_FAILED,
					ex.getLocalizedMessage());
			logger.error(errMsg);
		} catch (Exception ex) {
			errMsg = messageService.getMessage(req,
					MessageConstant.MSG_FETCH_AGREE_SWITCH_FAILED,
					ex.getLocalizedMessage());
			logger.exception(errMsg);
		}
		if (!StrUtil.isEmpty(errMsg)) {
			result = new SpringErrorJsonResult<String>();
			result.setCode(AppConstant.ERROR_CODE);
			result.setErrMsg(errMsg);
		} else {
			AgreeBindStatusResult<String> result2 = new AgreeBindStatusResult<String>();
			result2.setCode(AppConstant.SUCCESS_CODE);
			result2.setIsBind(isBind);
			result = result2;
		}
		return result;
	}

	@RequestMapping(value = "/agreeSwitchForDevice")
	@Deprecated
	public @ResponseBody SpringJsonResult<String> agreeSwitchForDevice(
			HttpServletRequest req, HttpServletResponse res,
			@ModelAttribute IautoDeviceParamDTO iautoDeviceParamDTO) {
		logger.info("start [OAUTH-1-4] ....... ");
		String errMsg = null;
		try {
			String status = req.getParameter("status");
			String loginName = IautoDeviceUtil.getIautoDeviceUserName(req,
					iautoDeviceParamDTO, messageService);
			aSCoreService.saveAgreeSwitch(loginName, "", status);
		} catch (ASBaseException ex) {
			errMsg = messageService.getMessage(req,
					MessageConstant.MSG_AGREE_SWITCH_FAILED,
					ex.getLocalizedMessage());
			logger.error(errMsg);
		} catch (Exception ex) {
			errMsg = messageService.getMessage(req,
					MessageConstant.MSG_AGREE_SWITCH_FAILED,
					ex.getLocalizedMessage());
			logger.exception(errMsg);
		}
		return SpringResultUtil.jsonResult(errMsg);
	}

	@RequestMapping(value = "/authStatus")
	public @ResponseBody SpringJsonResult<String> authStatus(
			HttpServletRequest req, HttpServletResponse res,
			@ModelAttribute IautoPhoneParamDTO iautoPhoneParamDTO) {
		logger.info("start [OAUTH-2-7] ....... ");
		String errMsg = null;
		String lastUpdateTime = null;
		try {
			String loginName = req.getParameter("loginName");
			if (StrUtil.isEmpty(loginName)) {
				loginName = IautoPhoneUtil
						.getIautoPhoneUserName(iautoPhoneParamDTO);
			}
			lastUpdateTime = aSCoreService.selectLastestHistoryTime(loginName);
		} catch (ASBaseException ex) {
			errMsg = messageService
					.getMessage(req, MessageConstant.MSG_SYSTEM_FAILED,
							ex.getLocalizedMessage());
			logger.error(errMsg);
		} catch (Exception ex) {
			errMsg = messageService
					.getMessage(req, MessageConstant.MSG_SYSTEM_FAILED,
							ex.getLocalizedMessage());
			logger.exception(errMsg);
		}
		if (!StrUtil.isEmpty(errMsg)) {
			return SpringResultUtil.jsonResult(errMsg);
		} else {
			AuthStatusResult result = new AuthStatusResult();
			result.setCode(AppConstant.SUCCESS_CODE);
			result.setLastUpdateTime(lastUpdateTime);
			return result;
		}
	}

	/**
	 * 
	 * @param req
	 * @param res
	 * @param provider
	 * @param iautoDeviceParamDTO
	 * @return
	 */
	@RequestMapping(value = "/{provider}/bindAppEndpoint")
	public @ResponseBody SpringJsonResult<String> bindApp(
			HttpServletRequest req, HttpServletResponse res,
			@PathVariable("provider") String provider,
			@ModelAttribute IautoDeviceParamDTO iautoDeviceParamDTO) {
		logger.info("start [OAUTH-2-11] ....... ");
		String errMsg = null;

		String sessionToken = req.getParameter("sessionToken");
		String clientId = req.getParameter("clientId");
		String accessToken = req.getParameter("accessToken");
		String refreshToken = req.getParameter("refreshToken");
		String loginName = req.getParameter("loginName");
		String uid = StrUtil.nullToString(req.getParameter("uid"));

		if (provider.equals("vine")) {
			clientId = "vine";
		}
		Integer errCode = AppConstant.ERROR_CODE;
		try {
			boolean isContinue = false;
			for (ActionCheck actionCheck : bindAppChecks) {
				isContinue = actionCheck.execute(req, res, provider,
						messageService);
				if (!isContinue) {
					errMsg = "check failed";
					break;
				}
			}
			checkClientIdValid(provider, clientId, accessToken);
			if (StrUtil.isEmpty(loginName)) {
				loginName = IautoPhoneUtil.getIautoPhoneUserName(sessionToken);
			}

			AppIautoMap record = new AppIautoMap();
			record.setIautoUserId(loginName);
			record.setClientId(clientId);
			record.setAppType(provider);
			record.setApiUid(uid);
			record.setAccessToken(accessToken);
			record.setRefreshToken(refreshToken);
			aSCoreService.saveAuthStatus(record, AppConstant.AUTH_LOGIN_DEVICE);
		} catch (ASBaseException e) {
			errCode = e.getErrCode();
			errMsg = messageService.getMessage(req,
					MessageConstant.MSG_BIND_FAILED, errCode);
			logger.error(errMsg, e.getMessage());
		} catch (Exception e) {
			errMsg = messageService.getMessage(req,
					MessageConstant.MSG_BIND_FAILED, e.getLocalizedMessage());
			logger.exception(errMsg, e);
		}
		return SpringResultUtil.jsonResult(errMsg, errCode);
	}

	private final void checkClientIdValid(String appType, String clientId,
			String accessToken) {
		if (StrUtil.isEmpty(clientId)) {
			throw new ASParamValidaterException(
					MessageConstant.MSG_BIND_FAILED,
					AuthErrorCodeConstant.APP_NO_CLIENT_ID);
		}

		if (StrUtil.isEmpty(accessToken)) {
			throw new ASParamValidaterException(
					MessageConstant.MSG_BIND_FAILED,
					AuthErrorCodeConstant.APP_NO_ACCESS_TOKEN);
		}

		if (!aSCoreService.checkClientIdValid(appType, clientId)) {
			throw new ASParamValidaterException(
					MessageConstant.MSG_BIND_FAILED,
					AuthErrorCodeConstant.APP_CLIENT_ID_INVALID);
		}
	}

	@RequestMapping(value = "/message")
	public String message() throws IOException {
		logger.info("start [OAUTH-2-6] ....... ");
		return "/config/message";
	}

	static List<ActionCheck> bindAppChecks = new ArrayList<ActionCheck>();
	static {
		bindAppChecks.add(new ClientIdCheck());
		bindAppChecks.add(new AccessTokenCheck());
	}

}
