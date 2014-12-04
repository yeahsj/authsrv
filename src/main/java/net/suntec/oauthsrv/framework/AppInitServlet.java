package net.suntec.oauthsrv.framework;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import net.suntec.oauthsrv.framework.job.CleanAccessTokenJob;

import org.xml.sax.SAXException;

/**
 * 
 * @项目名称: OauthSrv
 * @功能描述: 加载oauth配置信息
 * @当前版本： 1.0
 * @创建时间: 2014-5-27 下午12:27:12
 * @author: <a href="mailto:yeahsj@gmail.com">yeahsj</a>
 * @修改历史:
 */
public class AppInitServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Logger logger = Logger.getLogger(AppInitServlet.class.getName());

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		AppTaskExecutors.getInstance().shudown();
		super.destroy(); // Just puts "destroy" string in log
	}

	/**
	 * Initialization of the servlet. <br>
	 * 
	 * @throws ServletException
	 *             if an error occurs
	 */
	@Override
	public void init(ServletConfig servletConfig) throws ServletException {
		super.init(servletConfig);
		initSysConfig();
		initAppConfig();

		doAutoJob();
	}

	/**
	 * 定时JOB
	 * 
	 * @throws ServletException
	 */
	private void doAutoJob() throws ServletException {
		logger.info("doAutoJob ................ ");
		try {
			AppTaskExecutors appTaskExecutors = AppTaskExecutors.getInstance();
			appTaskExecutors.addTask(
					new CleanAccessTokenJob(this.getServletContext()),
					CleanAccessTokenJob.INITIALDELAY,
					CleanAccessTokenJob.PERIOD, CleanAccessTokenJob.TIME_UNIT);
			logger.info("doAutoJob success  ........... ");
		} catch (Exception ex) {
			logger.warning(ex.getMessage());
			throw new ServletException(ex);
		}
	}

	private void initAppConfig() throws ServletException {
		try {
			logger.info("start initAppConfig  ........... ");
			OauthAppConfig oauthAppConfig = OauthAppConfig.getInstance();
			oauthAppConfig.reload(getServletContext());
			logger.info(" initAppConfig success ................... ");
		} catch (Exception ex) {
			logger.warning(ex.getMessage());
			throw new ServletException(ex);
		}
	}

	private void initSysConfig() throws ServletException {
		logger.info(" initSysConfig ................... ");
		ResourceConfig resourceConfig = ResourceConfig.getInstance();
		String systemConfigPath = getServletContext().getInitParameter(
				"systemConfigPath");
		logger.info("systemConfigPath: " + systemConfigPath);
		try {
			resourceConfig.init(systemConfigPath);
			logger.info(" initSysConfig success ................... ");
		} catch (SAXException e) {
			logger.warning(e.getMessage());
			throw new ServletException(e);
		} catch (IOException e) {
			logger.warning(e.getMessage());
			throw new ServletException(e);
		} catch (Exception ex) {
			logger.warning(ex.getMessage());
			throw new ServletException(ex);
		}
	}

}
