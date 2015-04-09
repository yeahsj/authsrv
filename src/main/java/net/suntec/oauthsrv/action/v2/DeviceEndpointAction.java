package net.suntec.oauthsrv.action.v2;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.suntec.framework.exception.ASBaseException;
import net.suntec.framework.springmvc.json.dto.SpringDetailJsonResult;
import net.suntec.framework.springmvc.json.dto.SpringErrorJsonResult;
import net.suntec.framework.springmvc.json.dto.SpringJsonResult;
import net.suntec.oauthsrv.action.jsonresult.AgreeBindStatusResult;
import net.suntec.oauthsrv.action.jsonresult.TokenResult;
import net.suntec.oauthsrv.action.param.IautoDeviceParamDTO;
import net.suntec.oauthsrv.action.util.IautoDeviceUtil;
import net.suntec.oauthsrv.constant.AppConstant;
import net.suntec.oauthsrv.constant.MessageConstant;
import net.suntec.oauthsrv.dto.AppIautoMap;
import net.suntec.oauthsrv.service.ASCoreService;
import net.suntec.oauthsrv.service.ASDeviceService;
import net.suntec.oauthsrv.service.MessageService;
import net.suntec.oauthsrv.util.ASLogger;

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
@RequestMapping(value = "/v2/device")
public final class DeviceEndpointAction {
	@Autowired
	ASDeviceService aSDeviceService = null;
	@Autowired
	ASCoreService aSCoreService;
	@Autowired
	MessageService messageService;

	private final ASLogger logger = new ASLogger(DeviceEndpointAction.class);

	/**
	 * 这个接口只支持device访问 获取Token,如果用户之前已经激活了该App,则直接从数据库中获取Token,如果之前用户未激活过Token,
	 * 则走oauth流程获取Token( /auth/twitter )
	 */
	@RequestMapping(value = "/{provider}/token")
	public @ResponseBody SpringJsonResult<TokenResult> fetchToken(
			HttpServletRequest req, HttpServletResponse res,
			@PathVariable("provider") String provider,
			@ModelAttribute IautoDeviceParamDTO iautoDeviceParamDTO) {
		logger.info("start [OAUTH-V2-1-1] ...... ");
		logger.info("session id is :" + req.getSession().getId());
		SpringJsonResult<TokenResult> result = null;
		String errMsg = null;
		AppIautoMap record = null;
		boolean isBind = false;
		boolean noToken = true; // 标记服务器上有没有该iauto用户的对应的App信息
		Integer errCode = AppConstant.ERROR_CODE;
		String appVersion = req.getParameter("appVersion");
		try {
			/**
			 * 1: 首先从session中判断是否已存在loginName 2: 没有则从iauto获取 3: 返回loginName
			 */
			String loginName = IautoDeviceUtil.getIautoDeviceUserName(req,
					iautoDeviceParamDTO, messageService);
			record = aSDeviceService.saveFetchToken(iautoDeviceParamDTO,
					provider, loginName, appVersion);
			if (null == record) {
				isBind = aSCoreService.hasAgreeBindConfig(loginName);
				noToken = true;
			} else {
				noToken = false;
			}
		} catch (ASBaseException e) {
			errCode = e.getErrCode();
			errMsg = messageService.getMessage(req,
					MessageConstant.MSG_TOKEN_FAILED, errCode);
			logger.error(errMsg, e.getMessage());
		} catch (Exception ex) {
			errMsg = messageService.getMessage(req,
					MessageConstant.MSG_TOKEN_FAILED, ex.getLocalizedMessage());
			logger.exception(errMsg, ex);
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

}
