package net.suntec.oauthsrv.action.check;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.suntec.constant.AuthErrorCodeConstant;
import net.suntec.framework.constant.MessageConstant;
import net.suntec.framework.exception.ASParamValidaterException;
import net.suntec.oauthsrv.service.MessageService;

import com.openjava.core.util.StrUtil;

/**
 * 
 * 
 * @项目名称: OauthSrv
 * @功能描述: check Backurl
 * @当前版本： 1.0
 * @创建时间: 2015年2月2日 下午3:48:48
 * @author: <a href="mailto:yeahsj@yahoo.com.cn">yeahsj</a>
 * @修改历史:
 */
public class BackurlCheck implements ActionCheck {

	@Override
	public boolean execute(HttpServletRequest req, HttpServletResponse res,
			String provider, MessageService messageService) {
		String backurl = req.getParameter("backurl");
		if (StrUtil.isEmpty(backurl)) {
			throw new ASParamValidaterException(
					MessageConstant.MSG_SYSTEM_FAILED,
					AuthErrorCodeConstant.APP_NO_BACKURL);
		}
		return true;
	}

}
