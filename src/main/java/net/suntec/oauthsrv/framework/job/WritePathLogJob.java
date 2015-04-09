package net.suntec.oauthsrv.framework.job;

import javax.servlet.ServletContext;

import net.suntec.framework.exception.ASBaseException;
import net.suntec.oauthsrv.dto.AppPathLogDetail;
import net.suntec.oauthsrv.service.PathLogService;
import net.suntec.oauthsrv.util.ASLogger;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.FrameworkServlet;

/**
 * 
 * 
 * @项目名称: OauthSrv
 * @功能描述: 记录访问路径历史
 * @当前版本： 1.0
 * @创建时间: 2014年12月4日 下午5:52:48
 * @author: <a href="mailto:yeahsj@yahoo.com.cn">yeahsj</a>
 * @修改历史:
 */
public class WritePathLogJob implements Runnable {
	private ServletContext servletContext = null;
	private AppPathLogDetail log;

	private final ASLogger logger = new ASLogger(WritePathLogJob.class);

	public WritePathLogJob(ServletContext servletContext, AppPathLogDetail log) {
		this.servletContext = servletContext;
		this.log = log;
	}

	@Override
	public void run() {
		WebApplicationContext wac = WebApplicationContextUtils
				.getWebApplicationContext(servletContext,
						FrameworkServlet.SERVLET_CONTEXT_PREFIX + "mvc");
		if (null == wac) {
			throw new ASBaseException("wac is null");
		}
		PathLogService pathLogService = wac.getBean(PathLogService.class);
		log.setCost(log.getEndtime() - log.getStarttime());
		pathLogService.saveLog(log);
		logger.info("write log success ............ ");
	}
}
