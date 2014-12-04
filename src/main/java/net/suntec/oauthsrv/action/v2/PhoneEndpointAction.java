package net.suntec.oauthsrv.action.v2;

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
import net.suntec.framework.util.NcmsUtil;
import net.suntec.oauthsrv.action.param.IautoPhoneParamDTO;
import net.suntec.oauthsrv.action.util.IautoPhoneUtil;
import net.suntec.oauthsrv.dto.AppBase;
import net.suntec.oauthsrv.dto.AppIautoMap;
import net.suntec.oauthsrv.service.ASCoreService;
import net.suntec.oauthsrv.service.ASDeviceService;
import net.suntec.oauthsrv.service.ASPhoneService;
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
 * @创建时间: 2014年12月4日 下午5:51:26
 * @author: <a href="mailto:yeahsj@yahoo.com.cn">yeahsj</a>
 * @修改历史:
 */
@Controller
@RequestMapping(value = "/v2/api/phone")
public class PhoneEndpointAction {
	private final Logger logger = LoggerFactory
			.getLogger(PhoneEndpointAction.class);
	@Autowired
	MessageService messageService;
	@Autowired
	ASPhoneService aSPhoneService;
	@Autowired
	ASCoreService aSCoreService;
	@Autowired
	ASDeviceService aSDeviceService;
	@Autowired
	NcmsUtil ncmsUtil;

	@RequestMapping(value = "/listBindAppEndpoint")
	public @ResponseBody SpringJsonResult<AppBase> listBindAppEndpoint(
			HttpServletRequest req,
			@ModelAttribute IautoPhoneParamDTO iautoPhoneParamDTO) {
		logger.info("start [OAUTH-2-4] ....... ");
		SpringJsonResult<AppBase> result;
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
			}
		} catch (ASBaseException ex) {
			errMsg = messageService.getMessage(req,
					MessageConstant.MSG_LIST_BINDS_FAILED, ex.getErrCode());
			logger.error(errMsg);
		} catch (Exception ex) {
			errMsg = messageService.getMessage(req,
					MessageConstant.MSG_LIST_BINDS_FAILED,
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
				appbase = aSPhoneService.deletePhoneLogoutApp(record,
						V2Constant.SAVE_AUTH_HISTORY);
				ncmsUtil.send(userName);
			}
		} catch (ASBaseException ex) {
			errMsg = messageService.getMessage(req,
					MessageConstant.MSG_UN_BIND_FAILED, ex.getErrCode());
			logger.error(errMsg);
		} catch (Exception ex) {
			errMsg = messageService.getMessage(req,
					MessageConstant.MSG_UN_BIND_FAILED,
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
			result.setCode(AppConstant.SUCCESS_CODE);
		}
		return result;
	}

	@RequestMapping(value = "/message")
	public String message() throws IOException {
		logger.info("start [OAUTH-2-6] ....... ");
		return "/config/message";
	}

}
