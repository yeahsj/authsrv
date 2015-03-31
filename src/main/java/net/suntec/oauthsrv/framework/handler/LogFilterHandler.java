package net.suntec.oauthsrv.framework.handler;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * 
 * @项目名称: ntsrv
 * @功能描述: 日志处理
 * @当前版本：1.0
 * @创建时间: 2015年2月12日 下午3:04:12
 * @author: yeahsj
 * @修改历史:
 */
public interface LogFilterHandler {
	public void execute(ServletRequest request, ServletResponse response);
}
