package net.suntec.oauthsrv.action.check.auth.callback;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.suntec.constant.AuthErrorCodeConstant;
import net.suntec.framework.exception.ASActionCheckException;
import net.suntec.oauthsrv.action.check.ActionCheck;
import net.suntec.oauthsrv.constant.MessageConstant;
import net.suntec.oauthsrv.framework.dto.OauthFlowStatus;
import net.suntec.oauthsrv.framework.dto.OauthStatusParamDTO;
import net.suntec.oauthsrv.service.MessageService;
import net.suntec.oauthsrv.util.SessionUtil;

/**
 * 
 * 
 * @项目名称: OauthSrv
 * @功能描述: check step 2
 * @当前版本： 1.0
 * @创建时间: 2015年2月2日 下午3:48:35
 * @author: <a href="mailto:yeahsj@yahoo.com.cn">yeahsj</a>
 * @修改历史:
 */
public class SessionObjectCheck implements ActionCheck {

	@Override
	public boolean execute(HttpServletRequest req, HttpServletResponse res,
			String provider, MessageService messageService) {
		String errMsg = null;
		OauthFlowStatus oauthFlowStatus = SessionUtil.getSessionOauthStatus(
				req, provider);
		OauthStatusParamDTO oauthStatusParamDTO = SessionUtil
				.getSessionOauthParamsStatus(req, provider);
		if (null == oauthFlowStatus) {
			errMsg = messageService.getMessage(req,
					MessageConstant.MSG_AUTHENTICATE_FAILED,
					AuthErrorCodeConstant.SS_NO_PARAMS_OAUTHFLOWSTATUS);
			throw new ASActionCheckException(errMsg,
					AuthErrorCodeConstant.SS_NO_PARAMS_OAUTHFLOWSTATUS);
		}

		if (null == oauthStatusParamDTO) {
			errMsg = messageService.getMessage(req,
					MessageConstant.MSG_AUTHENTICATE_FAILED,
					AuthErrorCodeConstant.SS_NO_PARAMS_OAUTHSTATUSPARAM);
			throw new ASActionCheckException(errMsg,
					AuthErrorCodeConstant.SS_NO_PARAMS_OAUTHSTATUSPARAM);
		}

		return true;
	}

}
