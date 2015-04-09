package net.suntec.oauthsrv.action.check;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.suntec.constant.AuthErrorCodeConstant;
import net.suntec.framework.exception.ASActionCheckException;
import net.suntec.oauthsrv.action.util.IautoPhoneUtil;
import net.suntec.oauthsrv.constant.MessageConstant;
import net.suntec.oauthsrv.service.MessageService;

import com.openjava.core.util.StrUtil;

/**
 * 
 * 
 * @项目名称: OauthSrv
 * @功能描述: check LoginName
 * @当前版本： 1.0
 * @创建时间: 2015年2月2日 下午3:48:48
 * @author: <a href="mailto:yeahsj@yahoo.com.cn">yeahsj</a>
 * @修改历史:
 */
public class LoginNameCheck implements ActionCheck {

	@Override
	public boolean execute(HttpServletRequest req, HttpServletResponse res,
			String provider, MessageService messageService) {
		String errMsg = null;
		String userName = req.getParameter("loginName");
		String sessionToken = req.getParameter("sessionToken");
		if (StrUtil.isEmpty(userName)) {
			userName = IautoPhoneUtil.getIautoPhoneUserName(sessionToken);
		}

		if (StrUtil.isEmpty(userName)) {
			errMsg = messageService.getMessage(req,
					MessageConstant.MSG_SYSTEM_FAILED,
					AuthErrorCodeConstant.API_LOGIN_NAME_IS_REQUIRED);
			throw new ASActionCheckException(errMsg);
		}
		return true;
	}

}
