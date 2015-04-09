package net.suntec.oauthsrv.action.check;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.suntec.constant.AuthErrorCodeConstant;
import net.suntec.framework.exception.ASActionCheckException;
import net.suntec.oauthsrv.constant.MessageConstant;
import net.suntec.oauthsrv.service.MessageService;

import com.openjava.core.util.StrUtil;

public class AccessTokenCheck implements ActionCheck {

	@Override
	public boolean execute(HttpServletRequest req, HttpServletResponse res,
			String provider, MessageService messageService) {
		String accessToken = req.getParameter("accessToken");
		if (StrUtil.isEmpty(accessToken)) {
			throw new ASActionCheckException(MessageConstant.MSG_SYSTEM_FAILED,
					AuthErrorCodeConstant.APP_NO_ACCESS_TOKEN);
		}
		return true;
	}

}
