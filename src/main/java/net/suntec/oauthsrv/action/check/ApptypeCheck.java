package net.suntec.oauthsrv.action.check;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.suntec.framework.exception.ASActionCheckException;
import net.suntec.oauthsrv.framework.dto.OauthFlowStatus;
import net.suntec.oauthsrv.service.MessageService;
import net.suntec.oauthsrv.util.SessionUtil;

/**
 * 
 * 
 * @项目名称: OauthSrv
 * @功能描述: check Apptype
 * @当前版本： 1.0
 * @创建时间: 2015年2月2日 下午3:48:48
 * @author: <a href="mailto:yeahsj@yahoo.com.cn">yeahsj</a>
 * @修改历史:
 */
public class ApptypeCheck implements ActionCheck {

	@Override
	public boolean execute(HttpServletRequest req, HttpServletResponse res,
			String provider, MessageService messageService) {
		String errMsg = null;
		OauthFlowStatus oauthFlowStatus = SessionUtil.getSessionOauthStatus(
				req, provider);
		if (!provider.equals(oauthFlowStatus.getAppConfig().getAppType())) {
			errMsg = "appType not match!";
			throw new ASActionCheckException(errMsg);
		}
		return true;
	}

}
