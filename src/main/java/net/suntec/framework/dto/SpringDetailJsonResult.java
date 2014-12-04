package net.suntec.framework.dto;

/**
 * 
 * 
 * @项目名称: OauthSrv
 * @功能描述: 支持Spring MVC的 Json 返回格式,用于返回操作结果
 * @当前版本： 1.0
 * @创建时间: 2014-6-12 下午04:46:37
 * @author: <a href="mailto:yeahsj@gmail.com">yeahsj</a>
 * @修改历史:
 */
public class SpringDetailJsonResult<T> extends SpringJsonResult<T> {

	public T getResult() {
		return result;
	}

	public int getCode() {
		return code;
	}

	public String getLastUpdateTime() {
		return lastUpdateTime;
	}

}
