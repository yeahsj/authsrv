package net.suntec.oauthsrv.action.check.auth.callback;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.suntec.constant.AuthErrorCodeConstant;
import net.suntec.framework.constant.MessageConstant;
import net.suntec.framework.exception.ASActionCheckException;
import net.suntec.framework.util.ASLogger;
import net.suntec.oauthsrv.action.check.ActionCheck;
import net.suntec.oauthsrv.service.MessageService;

import com.openjava.core.util.StrUtil;

/**
 * 
 * 
 * @项目名称: OauthSrv
 * @功能描述: check step 1
 * @当前版本： 1.0
 * @创建时间: 2015年2月2日 下午2:25:39
 * @author: <a href="mailto:yeahsj@yahoo.com.cn">yeahsj</a>
 * @修改历史:
 */
public class ResubmitActionCheck implements ActionCheck {

	private final ASLogger logger = new ASLogger(ResubmitActionCheck.class);

	@Override
	public boolean execute(HttpServletRequest req, HttpServletResponse res,
			String provider, MessageService messageService) {
		boolean isResubmit = false;
		String errMsg = null;
		try {
			isResubmit = checkResubmit(res, req, provider);
		} catch (Exception ex) {
			logger.error(ex.getMessage());
			isResubmit = true;
		}
		if (isResubmit) {
			errMsg = messageService.getMessage(req,
					MessageConstant.MSG_AUTHENTICATE_FAILED,
					AuthErrorCodeConstant.AUTH_CALLBACK_RESUBMIT);
			throw new ASActionCheckException(errMsg,
					AuthErrorCodeConstant.AUTH_CALLBACK_RESUBMIT);
		}

		return true;
	}

	private boolean checkResubmit(HttpServletResponse res,
			HttpServletRequest req, String provider) {
		int count = 0;
		Object cntStr = req.getSession().getAttribute("CALLBACK-" + provider);
		if (StrUtil.isNotEmpty(cntStr)) {
			count = (Integer) cntStr;
		}
		if (count > 0) {
			return true;
		} else {
			req.getSession().setAttribute("CALLBACK-" + provider, count + 1);
			return false;
		}
	}
}
