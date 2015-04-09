package net.suntec.oauthsrv.action.check.auth.callback;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.suntec.constant.AuthErrorCodeConstant;
import net.suntec.framework.exception.ASParamValidaterException;
import net.suntec.oauthsrv.action.check.ActionCheck;
import net.suntec.oauthsrv.constant.MessageConstant;
import net.suntec.oauthsrv.framework.dto.OauthStatusParamDTO;
import net.suntec.oauthsrv.service.MessageService;
import net.suntec.oauthsrv.util.SessionUtil;

import com.openjava.core.util.StrUtil;

/**
 * 
 * 
 * @项目名称: OauthSrv
 * @功能描述: check step 5
 * @当前版本： 1.0
 * @创建时间: 2015年2月2日 下午3:49:13
 * @author: <a href="mailto:yeahsj@yahoo.com.cn">yeahsj</a>
 * @修改历史:
 */
public class ParamsCheck implements ActionCheck {

	@Override
	public boolean execute(HttpServletRequest req, HttpServletResponse res,
			String provider, MessageService messageService) {
		OauthStatusParamDTO oauthStatusParamDTO = SessionUtil
				.getSessionOauthParamsStatus(req, provider);
		String backurl = oauthStatusParamDTO.getBackurl();
		String fromPhone = StrUtil.nullToString(oauthStatusParamDTO
				.getFromPhone());
		String code = req.getParameter("code");
		String oAuthVerifier = req.getParameter("oauth_verifier");
		if ("true".equals(fromPhone)) {
			if (StrUtil.isEmpty(backurl)) {
				throw new ASParamValidaterException(
						MessageConstant.MSG_AUTHENTICATE_FAILED,
						AuthErrorCodeConstant.APP_NO_BACKURL);
			}
			if (StrUtil.isEmpty(code) && StrUtil.isEmpty(oAuthVerifier)
					&& !provider.equals("pocket")) {
				throw new ASParamValidaterException(
						MessageConstant.MSG_AUTHENTICATE_FAILED,
						AuthErrorCodeConstant.APP_NO_CODE_OR_VERIFY);
			}
		} else {
			if (StrUtil.isEmpty(code) && StrUtil.isEmpty(oAuthVerifier)
					&& !provider.equals("pocket")) {
				throw new ASParamValidaterException(
						MessageConstant.MSG_AUTHENTICATE_FAILED,
						AuthErrorCodeConstant.APP_NO_CODE_OR_VERIFY);
			}
		}

		return true;
	}

}
