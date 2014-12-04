package net.suntec.oauthsrv.action.jsonresult;

import net.suntec.framework.dto.SpringJsonResult;

/**
 * 
 * 
 * @项目名称: OauthSrv
 * @功能描述:
 * @当前版本： 1.0
 * @创建时间: 2014年12月4日 下午5:50:47
 * @author: <a href="mailto:yeahsj@yahoo.com.cn">yeahsj</a>
 * @修改历史:
 */
public class AuthStatusResult extends SpringJsonResult<String> {
	String lastUpdateTime;

	public String getLastUpdateTime() {
		return lastUpdateTime;
	}

	public void setLastUpdateTime(String lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public int getCode() {
		return code;
	}
}
