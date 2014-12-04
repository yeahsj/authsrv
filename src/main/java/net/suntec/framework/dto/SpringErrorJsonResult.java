package net.suntec.framework.dto;

/**
 * 
 * 
 * @项目名称: OauthSrv
 * @功能描述: Spring MVC返回Error的Json对象
 * @当前版本： 1.0
 * @创建时间: 2014-6-16 上午09:33:18
 * @author: <a href="mailto:yeahsj@gmail.com">yeahsj</a>
 * @修改历史:
 */
public class SpringErrorJsonResult<T> extends SpringJsonResult<T> {

	public int getCode() {
		return code;
	}

	public String getErrMsg() {
		return errMsg;
	}
}
