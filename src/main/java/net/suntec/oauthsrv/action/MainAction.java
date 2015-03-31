package net.suntec.oauthsrv.action;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.suntec.framework.constant.AppConstant;
import net.suntec.framework.exception.ASBaseException;
import net.suntec.framework.util.ASLogger;
import net.suntec.framework.util.OauthProviderService;
import net.suntec.framework.util.SessionUtil;
import net.suntec.oauthsrv.dto.AppIautoMap;
import net.suntec.oauthsrv.framework.dto.OauthFlowStatus;
import net.suntec.oauthsrv.framework.dto.OauthStatusParamDTO;
import net.suntec.oauthsrv.service.ASCoreService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.openjava.core.util.StrUtil;

/**
 * 
 * 
 * @项目名称: OauthSrv
 * @功能描述:
 * @当前版本： 1.0
 * @创建时间: 2014年12月4日 下午5:50:07
 * @author: <a href="mailto:yeahsj@yahoo.com.cn">yeahsj</a>
 * @修改历史:
 */
@Controller
public class MainAction {
	@Autowired
	ASCoreService aSCoreService = null;

//	private final Logger logger = LoggerFactory.getLogger(MainAction.class);
	private final ASLogger logger = new ASLogger(MainAction.class);

	@RequestMapping(value = "/")
	public String all(HttpServletRequest req, HttpServletResponse res,
			Model model) throws IOException {
		// res.sendRedirect(AppConstant.MAIN_URL);
		String code = req.getParameter("code");
		if (StrUtil.isEmpty(code)) {
			return "index";
		} else {
			return this.handleFeedly(req, res);
		}
		// return null;
	}
	 
	public String handleFeedly(HttpServletRequest req, HttpServletResponse res)
			throws IOException {
		String errMsg = null;
		OauthFlowStatus oauthFlowStatus = SessionUtil.getSessionOauthStatus(
				req, "");
		if (null == oauthFlowStatus) {
			res.sendRedirect(AppConstant.MAIN_URL);
			return null;
		}

		OauthStatusParamDTO oauthStatusParamDTO = SessionUtil
				.getSessionOauthParamsStatus(req, "");

		String code = req.getParameter("code");
		if (StrUtil.isNotEmpty(code)) {
			oauthFlowStatus.setOAuthVerifier(code);
		} else {
			String oAuthVerifier = req.getParameter("oauth_verifier");
			oauthFlowStatus.setOAuthVerifier(oAuthVerifier);
			logger.info("oAuthVerifier: " + oAuthVerifier);
		}

		try {
			OauthProviderService.obtainAccessToken(oauthFlowStatus);
			OauthProviderService.prodUserProfile(oauthFlowStatus);
			SessionUtil.removeSessionOauthStatus(req, "");
			String fromPhone = StrUtil.nullToString(oauthStatusParamDTO
					.getFromPhone());
			if ("true".equals(fromPhone)
					|| (oauthStatusParamDTO.isSave() && StrUtil
							.isNotEmpty(oauthStatusParamDTO.getLoginName()))) {
				AppIautoMap record = new AppIautoMap();
				record.setIautoUserId(oauthStatusParamDTO.getLoginName());
				record.setClientId(oauthFlowStatus.getAppConfig().getAppKey());
				record.setAppType(oauthFlowStatus.getAppConfig().getAppType());
				record.setApiUid(oauthFlowStatus.getProviderUser().getUserId());
				record.setAccessToken(oauthFlowStatus.getAccessToken()
						.getToken());
				record.setRefreshToken(oauthFlowStatus.getAccessToken()
						.getSecret());
				aSCoreService.saveAuthStatus(record,
						AppConstant.AUTH_LOGIN_PHONE);
			}

			if ("true".equals(fromPhone)
					&& StrUtil.isEmpty(oauthStatusParamDTO.getBackurl())) {
				res.sendRedirect(AppConstant.INDEX_URL);
			} else {
				StringBuilder redUrl = new StringBuilder();
				redUrl.append(oauthStatusParamDTO.getBackurl());
				redUrl.append("?accessToken=");
				redUrl.append(oauthFlowStatus.getAccessToken().getToken());
				redUrl.append("&refreshToken=");
				redUrl.append(oauthFlowStatus.getAccessToken().getSecret());
				redUrl.append("&uid=");
				redUrl.append(oauthFlowStatus.getProviderUser().getUserId());
				redUrl.append("&clientId=");
				redUrl.append(oauthFlowStatus.getAppConfig().getAppKey());
				res.sendRedirect(redUrl.toString());
			}

		} catch (ASBaseException ex) {
			logger.error(ex.getMessage());
			errMsg = ex.getMessage();
		}

		if (!StrUtil.isEmpty(errMsg)) {
			req.setAttribute("errMsg", errMsg);
			return AppConstant.ERROR_URL;
		} else {
			return null;
		}
	}

}
