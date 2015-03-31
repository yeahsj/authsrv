package net.suntec.oauthsrv.framework.job;

import java.util.concurrent.TimeUnit;

import javax.servlet.ServletContext;

import net.suntec.framework.exception.ASBaseException;
import net.suntec.framework.util.ASLogger;
import net.suntec.oauthsrv.service.ASCoreService;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.FrameworkServlet;

/**
 * 
 * 
 * @项目名称: OauthSrv
 * @功能描述: 定期清理Access Token的JOB
 * @当前版本： 1.0
 * @创建时间: 2014-6-18 上午09:47:36
 * @author: <a href="mailto:yeahsj@gmail.com">yeahsj</a>
 * @修改历史:
 */
public class CleanAccessTokenJob implements Runnable {
	private ServletContext servletContext = null;
	private final ASLogger logger = new ASLogger(CleanAccessTokenJob.class);

	public static final long INITIALDELAY = 0;
	public static final long PERIOD = 24;
	public static final TimeUnit TIME_UNIT = TimeUnit.HOURS;

	public CleanAccessTokenJob(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

	@Override
	public void run() {
		logger.info("start run CleanAccessTokenJob .............. ");
		WebApplicationContext wac = WebApplicationContextUtils
				.getWebApplicationContext(servletContext,
						FrameworkServlet.SERVLET_CONTEXT_PREFIX + "mvc");
		if (null == wac) {
			throw new ASBaseException("wac is null");
		}
		try {
			ASCoreService aSCoreService = wac.getBean(ASCoreService.class);
			aSCoreService.deleteExpiredApps(59);
			logger.info(" run CleanAccessTokenJob success .............. ");
		} catch (ASBaseException ex) {
			logger.error("CleanAccessTokenJob error: " + ex.getMessage());
		} catch (Exception ex) {
			logger.exception("CleanAccessTokenJob error: " + ex.getMessage());
		}
	}

}
