package net.suntec.oauthsrv.framework.job;

import javax.servlet.ServletContext;

import net.suntec.framework.exception.ASBaseException;
import net.suntec.oauthsrv.dto.AppPathLogDetail;
import net.suntec.oauthsrv.service.PathLogService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.FrameworkServlet;

public class WritePathLogJob implements Runnable {
	private ServletContext servletContext = null;
	private AppPathLogDetail log;
	private final Logger logger = LoggerFactory
			.getLogger(WritePathLogJob.class);

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
