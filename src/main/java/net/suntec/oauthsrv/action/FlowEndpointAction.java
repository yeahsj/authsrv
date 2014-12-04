package net.suntec.oauthsrv.action;

import java.io.IOException;
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
import net.suntec.framework.iauto.dto.result.IautoPhoneLoginResultDTO;
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
import net.suntec.oauthsrv.service.IautoApiService;
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

@Controller
@RequestMapping(value = "/api")
public class FlowEndpointAction {
	private final Logger logger = LoggerFactory.getLogger(FlowEndpointAction.class);
	@Autowired
	MessageService messageService;
	@Autowired
	ASPhoneService aSPhoneService;
	@Autowired
	ASCoreService aSCoreService;
	@Autowired
	ASDeviceService aSDeviceService;

	@RequestMapping(value = "/{provider}/sessionToken")
	public @ResponseBody
	SpringJsonResult<IautoPhoneLoginResultDTO> getSessionToken(@PathVariable("provider") String provider) {
		logger.info("start [TST-1] " + provider + " ....... ");
		SpringJsonResult<IautoPhoneLoginResultDTO> result = null;
		IautoApiService iautoApiService = new IautoApiService();
		try {
			IautoPhoneLoginResultDTO dto = null;
			if (provider.equals("phone")) {
				dto = iautoApiService.doPhoneLogin();
			} else {
				dto = iautoApiService.doDeviceLogin();
			}
			result = new SpringDetailJsonResult<IautoPhoneLoginResultDTO>();
			result.setResult(dto);
			result.setCode(AppConstant.SUCCESS_CODE);
		} catch (ASBaseException ex) {
			logger.error(ex.getMessage());
			result = new SpringErrorJsonResult<IautoPhoneLoginResultDTO>();
			result.setErrMsg(ex.getMessage());
			result.setCode(ex.getErrCode());
		}
		return result;
	}

	@Deprecated
	@RequestMapping(value = "/doLoginEndpoint")
	public @ResponseBody
	SpringJsonResult<String> doLoginEndpoint(HttpServletRequest req, HttpServletResponse res,
			@ModelAttribute IautoPhoneParamDTO iautoPhoneParamDTO) {
		String errMsg = null;
		SpringJsonResult<String> result;
		try {
			logger.debug("do login");
			String loginName = iautoPhoneParamDTO.getLoginName();
			if (StrUtil.isEmpty(loginName)) {
				loginName = IautoPhoneUtil.getIautoPhoneUserName(iautoPhoneParamDTO);
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
			logger.error(errMsg);
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

	@RequestMapping(value = "/listBindAppEndpoint")
	public @ResponseBody
	SpringJsonResult<AppBase> listBindAppEndpoint(HttpServletRequest req,
			@ModelAttribute IautoPhoneParamDTO iautoPhoneParamDTO) {
		logger.info("start [OAUTH-2-4] ....... ");
		SpringJsonResult<AppBase> result;
		String lastUpdatetime = null;
		List<AppBase> appListResult = null;
		String errMsg = null;
		try {
			String userName = req.getParameter("loginName");
			if (StrUtil.isEmpty(userName)) {
				userName = IautoPhoneUtil.getIautoPhoneUserName(iautoPhoneParamDTO);
			}
			if (StrUtil.isEmpty(userName)) {
				errMsg = messageService.getMessage(req, MessageConstant.MSG_LIST_BINDS_FAILED,
						AuthErrorCodeConstant.APP_NO_LOGIN_NAME);
			} else {
				appListResult = aSPhoneService.selectPhoneBindAppList(userName);
				lastUpdatetime = aSCoreService.selectLastestHistoryTime(userName);
			}
			// appListResult = ApiActionConvert.connvert(datas);
		} catch (ASBaseException ex) {
			errMsg = messageService.getMessage(req, MessageConstant.MSG_LIST_BINDS_FAILED, ex.getErrCode());
			logger.error(errMsg);
		} catch (Exception ex) {
			errMsg = messageService.getMessage(req, MessageConstant.MSG_LIST_BINDS_FAILED,
					AuthErrorCodeConstant.API_FETCH_APP_LIST);
			logger.error(errMsg);
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
	public @ResponseBody
	SpringJsonResult<AppBase> unbindEndpoint(HttpServletRequest req, HttpServletResponse res,
			@PathVariable("provider") String provider, @ModelAttribute IautoPhoneParamDTO iautoPhoneParamDTO)
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
				userName = IautoPhoneUtil.getIautoPhoneUserName(iautoPhoneParamDTO);
			}
			if (StrUtil.isEmpty(userName)) {
				errMsg = messageService.getMessage(req, MessageConstant.MSG_LIST_BINDS_FAILED,
						AuthErrorCodeConstant.API_LOGIN_NAME_IS_REQUIRED);
			} else {
				record.setIautoUserId(userName);
				record.setAppType(provider);
				appbase = aSPhoneService.deletePhoneLogoutApp(record);
				lastUpdatetime = aSCoreService.selectLastestHistoryTime(userName);
			}
		} catch (ASBaseException ex) {
			errMsg = messageService.getMessage(req, MessageConstant.MSG_UN_BIND_FAILED, ex.getErrCode());
			logger.error(errMsg);
		} catch (Exception ex) {
			errMsg = messageService.getMessage(req, MessageConstant.MSG_UN_BIND_FAILED,
					AuthErrorCodeConstant.API_UNBIDN_FAILED);
			logger.error(errMsg);
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
	public @ResponseBody
	SpringJsonResult<String> agreeSwitchForPhone(HttpServletRequest req, HttpServletResponse res,
			@ModelAttribute IautoPhoneParamDTO iautoPhoneParamDTO) {
		logger.info("start [OAUTH-2-2] ....... ");
		String errMsg = null;
		try {
			String status = req.getParameter("status");
			String userName = req.getParameter("loginName");
			if (StrUtil.isEmpty(userName)) {
				userName = IautoPhoneUtil.getIautoPhoneUserName(iautoPhoneParamDTO);
			}
			aSCoreService.saveAgreeSwitch(userName, "", status);
		} catch (ASBaseException ex) {
			errMsg = messageService.getMessage(req, MessageConstant.MSG_AGREE_SWITCH_FAILED, ex.getLocalizedMessage());
			logger.error(errMsg);
		} catch (Exception ex) {
			errMsg = messageService.getMessage(req, MessageConstant.MSG_AGREE_SWITCH_FAILED, ex.getLocalizedMessage());
			logger.error(errMsg);
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
	public @ResponseBody
	SpringJsonResult<String> fetchAgreeSwitchForPhone(HttpServletRequest req, HttpServletResponse res,
			@ModelAttribute IautoPhoneParamDTO iautoPhoneParamDTO) {
		logger.info("start [OAUTH-2-3] ....... ");
		String errMsg = null;
		SpringJsonResult<String> result = null;
		boolean isBind = false;
		try {
			String userName = req.getParameter("loginName");
			if (StrUtil.isEmpty(userName)) {
				userName = IautoPhoneUtil.getIautoPhoneUserName(iautoPhoneParamDTO);
			}
			isBind = aSCoreService.hasAgreeBindConfig(userName);
		} catch (ASBaseException ex) {
			errMsg = messageService.getMessage(req, MessageConstant.MSG_FETCH_AGREE_SWITCH_FAILED, ex
					.getLocalizedMessage());
			logger.error(errMsg);
		} catch (Exception ex) {
			errMsg = messageService.getMessage(req, MessageConstant.MSG_FETCH_AGREE_SWITCH_FAILED, ex
					.getLocalizedMessage());
			logger.error(errMsg);
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
	public @ResponseBody
	SpringJsonResult<String> agreeSwitchForDevice(HttpServletRequest req, HttpServletResponse res,
			@ModelAttribute IautoDeviceParamDTO iautoDeviceParamDTO) {
		logger.info("start [OAUTH-1-4] ....... ");
		String errMsg = null;
		try {
			String status = req.getParameter("status");
			String loginName = IautoDeviceUtil.getIautoDeviceUserName(req, iautoDeviceParamDTO, messageService);
			aSCoreService.saveAgreeSwitch(loginName, "", status);
		} catch (ASBaseException ex) {
			errMsg = messageService.getMessage(req, MessageConstant.MSG_AGREE_SWITCH_FAILED, ex.getLocalizedMessage());
			logger.error(errMsg);
		} catch (Exception ex) {
			errMsg = messageService.getMessage(req, MessageConstant.MSG_AGREE_SWITCH_FAILED, ex.getLocalizedMessage());
			logger.error(errMsg);
		}
		return SpringResultUtil.jsonResult(errMsg);
	}

	@RequestMapping(value = "/authStatus")
	public @ResponseBody
	SpringJsonResult<String> authStatus(HttpServletRequest req, HttpServletResponse res,
			@ModelAttribute IautoPhoneParamDTO iautoPhoneParamDTO) {
		logger.info("start [OAUTH-2-7] ....... ");
		String errMsg = null;
		String lastUpdateTime = null;
		try {
			String loginName = req.getParameter("loginName");
			if (StrUtil.isEmpty(loginName)) {
				loginName = IautoPhoneUtil.getIautoPhoneUserName(iautoPhoneParamDTO);
			}
			lastUpdateTime = aSCoreService.selectLastestHistoryTime(loginName);
		} catch (ASBaseException ex) {
			errMsg = messageService.getMessage(req, MessageConstant.MSG_SYSTEM_FAILED, ex.getLocalizedMessage());
			logger.error(errMsg);
		} catch (Exception ex) {
			errMsg = messageService.getMessage(req, MessageConstant.MSG_SYSTEM_FAILED, ex.getLocalizedMessage());
			logger.error(errMsg);
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

	@RequestMapping(value = "/message")
	public String message() throws IOException {
		logger.info("start [OAUTH-2-6] ....... ");
		return "/config/message";
	}

}
