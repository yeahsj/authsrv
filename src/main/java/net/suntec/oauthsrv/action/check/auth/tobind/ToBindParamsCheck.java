package net.suntec.oauthsrv.action.check.auth.tobind;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.suntec.constant.AuthErrorCodeConstant;
import net.suntec.framework.exception.ASActionCheckException;
import net.suntec.oauthsrv.action.check.AccessTokenCheck;
import net.suntec.oauthsrv.action.check.ActionCheck;
import net.suntec.oauthsrv.action.check.BackurlCheck;
import net.suntec.oauthsrv.action.check.ClientIdCheck;
import net.suntec.oauthsrv.constant.MessageConstant;
import net.suntec.oauthsrv.service.MessageService;

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
public class ToBindParamsCheck implements ActionCheck {
	static List<ActionCheck> toBindParamsChecks = new ArrayList<ActionCheck>();
	static {
		toBindParamsChecks.add(new ClientIdCheck());
		toBindParamsChecks.add(new AccessTokenCheck());
		toBindParamsChecks.add(new BackurlCheck());
	}

	@Override
	public boolean execute(HttpServletRequest req, HttpServletResponse res,
			String provider, MessageService messageService) {
		boolean check = false;
//		String errMsg = null;
		for (ActionCheck actionCheck : toBindParamsChecks) {
			check = actionCheck.execute(req, res, provider, messageService);
			if (!check) {
//				errMsg = "check failed";
				throw new ASActionCheckException(
						MessageConstant.MSG_SYSTEM_FAILED,
						AuthErrorCodeConstant.SYSTEM_ERROR_UNEXCEPTED);
			}
		}
		return check;
		// String loginName = req.getParameter("loginName");
//		String clientId = req.getParameter("clientId");
//		String accessToken = req.getParameter("accessToken");
//		String backurl = req.getParameter("backurl");
//		if (StrUtil.isEmpty(backurl)) {
//			throw new ASParamValidaterException(
//					MessageConstant.MSG_AUTHENTICATE_FAILED,
//					AuthErrorCodeConstant.APP_NO_BACKURL);
//		}
//		// if (StrUtil.isEmpty(loginName)) {
//		// throw new ASParamValidaterException(
//		// MessageConstant.MSG_AUTHENTICATE_FAILED,
//		// AuthErrorCodeConstant.APP_NO_LOGIN_NAME);
//		// }
//		if (StrUtil.isEmpty(clientId)) {
//			throw new ASParamValidaterException(
//					MessageConstant.MSG_AUTHENTICATE_FAILED,
//					AuthErrorCodeConstant.APP_NO_CLIENT_ID);
//		}
//		if (StrUtil.isEmpty(accessToken)) {
//			throw new ASParamValidaterException(
//					MessageConstant.MSG_AUTHENTICATE_FAILED,
//					AuthErrorCodeConstant.APP_NO_ACCESS_TOKEN);
//		}
//		return true;
	}

}
