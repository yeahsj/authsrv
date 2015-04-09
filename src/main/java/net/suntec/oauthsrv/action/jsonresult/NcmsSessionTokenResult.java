package net.suntec.oauthsrv.action.jsonresult;

import net.suntec.framework.springmvc.json.dto.SpringJsonResult;

/**
 * 
 * 
 * @项目名称: OauthSrv
 * @功能描述: 长连接通讯用Token
 * @当前版本： 1.0
 * @创建时间: 2014年12月4日 下午5:50:51
 * @author: <a href="mailto:yeahsj@yahoo.com.cn">yeahsj</a>
 * @修改历史:
 */
public class NcmsSessionTokenResult extends SpringJsonResult<String> {
	private String sessionToken;

	public String getSessionToken() {
		return sessionToken;
	}

	public void setSessionToken(String sessionToken) {
		this.sessionToken = sessionToken;
	}

	public int getCode() {
		return code;
	}
}
