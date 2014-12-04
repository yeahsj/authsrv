package net.suntec.oauthsrv.action.jsonresult;

import net.suntec.framework.dto.SpringJsonResult;

/**
 * 
 * 
 * @项目名称: OauthSrv
 * @功能描述:
 * @当前版本： 1.0
 * @创建时间: 2014年12月4日 下午5:50:31
 * @author: <a href="mailto:yeahsj@yahoo.com.cn">yeahsj</a>
 * @修改历史:
 */
public class AgreeBindStatusResult<T> extends SpringJsonResult<T> {
	boolean isBind = false;

	public int getCode() {
		return code;
	}

	public boolean getIsBind() {
		return isBind;
	}

	public void setIsBind(boolean isBind) {
		this.isBind = isBind;
	}
}
