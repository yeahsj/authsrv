package net.suntec.framework.dto;

import java.util.List;

/**
 * 
 * 
 * @项目名称: OauthSrv
 * @功能描述: spring MVC 返回的Json对象
 * @当前版本： 1.0
 * @创建时间: 2014-6-16 上午09:32:16
 * @author: <a href="mailto:yeahsj@gmail.com">yeahsj</a>
 * @修改历史:
 */
public class SpringJsonResult<T> {
	protected int code = 200;
	protected String errMsg = null;
	protected List<T> lists;
	protected T result;
	protected String lastUpdateTime;

	public void setLastUpdateTime(String lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	public void setLists(List<T> lists) {
		this.lists = lists;
	}

	public void setResult(T result) {
		this.result = result;
	}
}
