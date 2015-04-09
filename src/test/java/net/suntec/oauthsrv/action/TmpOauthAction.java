package net.suntec.oauthsrv.action;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.suntec.constant.AuthErrorCodeConstant;
import net.suntec.framework.exception.ASBaseException;
import net.suntec.framework.exception.ASParamValidaterException;
import net.suntec.framework.springmvc.json.dto.SpringDetailJsonResult;
import net.suntec.framework.springmvc.json.dto.SpringErrorJsonResult;
import net.suntec.framework.springmvc.json.dto.SpringJsonResult;
import net.suntec.oauthsrv.action.util.DianPingSignUtil;
import net.suntec.oauthsrv.constant.AppConstant;
import net.suntec.oauthsrv.constant.MessageConstant;
import net.suntec.oauthsrv.dto.AppConfig;
import net.suntec.oauthsrv.service.ASCoreService;
import net.suntec.oauthsrv.service.ASDeviceService;
import net.suntec.oauthsrv.service.MessageService;
import net.suntec.oauthsrv.util.OauthProviderService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
public final class TmpOauthAction {
	@Autowired
	ASDeviceService aSDeviceService = null;
	@Autowired
	ASCoreService aSCoreService;
	@Autowired
	MessageService messageService;

	private final Logger logger = LoggerFactory.getLogger(OauthAction.class);

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
	@RequestMapping(value = "/{provider}/sign")
	public @ResponseBody SpringJsonResult<String> sign(HttpServletRequest req,
			HttpServletResponse res, @PathVariable("provider") String provider) {
		logger.info("start [OAUTH-1-9] ....... ");
		String requestUrl = req.getParameter("_appUrl");
		String method = req.getParameter("_method");
		String clientId = null;
		SpringJsonResult<String> result = null;
		String errMsg = null;
		Integer errCode = AppConstant.ERROR_CODE;
		String signUrl = null;
		try {
			clientId = aSCoreService.selectClientId(provider);
			this.signCheck(clientId, requestUrl, method);
			// requestUrl = URLDecoder.decode(requestUrl, "UTF-8");
			logger.info("requestUrl: " + requestUrl);
			AppConfig appConfig = OauthProviderService.prodAppConfig(provider,
					clientId);
			Map<String, String[]> requestMap = req.getParameterMap();
			String signParams = DianPingSignUtil.signParams(req, requestMap,
					appConfig.getAppKey(), appConfig.getAppSecret());
			signUrl = requestUrl + "?" + signParams;
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
			result.setResult(signUrl);
		}
		return result;
	}

	private final void signCheck(String clientId, String requestUrl,
			String method) {
		if (StrUtil.isEmpty(clientId)) {
			throw new ASParamValidaterException(
					MessageConstant.MSG_HEADER_FAILED,
					AuthErrorCodeConstant.APP_NO_CLIENT_ID);
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

}
