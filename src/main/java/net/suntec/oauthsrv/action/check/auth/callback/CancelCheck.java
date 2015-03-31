package net.suntec.oauthsrv.action.check.auth.callback;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.openjava.core.util.StrUtil;

import net.suntec.framework.exception.ASActionCheckException;
import net.suntec.framework.util.ServerPathUtil;
import net.suntec.framework.util.SessionUtil;
import net.suntec.oauthsrv.action.check.ActionCheck;
import net.suntec.oauthsrv.framework.dto.OauthStatusParamDTO;
import net.suntec.oauthsrv.service.MessageService;

/**
 * 
 * 
 * @项目名称: OauthSrv
 * @功能描述: check step 4
 * @当前版本： 1.0
 * @创建时间: 2015年2月2日 下午3:49:04
 * @author: <a href="mailto:yeahsj@yahoo.com.cn">yeahsj</a>
 * @修改历史:
 */
public class CancelCheck implements ActionCheck {

	@Override
	public boolean execute(HttpServletRequest req, HttpServletResponse res,
			String provider, MessageService messageService) {
		boolean isHandle = false;
		OauthStatusParamDTO oauthStatusParamDTO = SessionUtil
				.getSessionOauthParamsStatus(req, provider);
		String fromPhone = StrUtil.nullToString(oauthStatusParamDTO
				.getFromPhone());
		boolean fromPhoneBl = "true".equals(fromPhone);
		String localServer = ServerPathUtil.getCurrentServerRootPath(req);
		if ("twitter".equals(provider)) {
			String denied = req.getParameter("denied");
			if (!StrUtil.isEmpty(denied) && fromPhoneBl) {
				if (fromPhoneBl) {
					isHandle = true;
					try {
						res.sendRedirect(localServer + "/redirect.html");
					} catch (IOException e) {
						throw new ASActionCheckException(e);
					}
				}
			}
		}
		if ("soundcloud".equals(provider)) {
			String error = req.getParameter("error");
			if (!StrUtil.isEmpty(error) && fromPhoneBl) {
				if (fromPhoneBl) {
					isHandle = true;
					try {
						res.sendRedirect(localServer + "/redirect.html");
					} catch (IOException e) {
						throw new ASActionCheckException(e);
					}
				}
			}
		}
		return !isHandle;
	}
}
