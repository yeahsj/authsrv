package net.suntec.oauthsrv.framework;

import java.io.IOException;
import java.util.Calendar;
import java.util.Enumeration;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import net.suntec.oauthsrv.dto.AppPathLogDetail;
import net.suntec.oauthsrv.framework.job.WritePathLogJob;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * 
 * @项目名称: OauthSrv
 * @功能描述: 记录日志
 * @当前版本： 1.0
 * @创建时间: 2014-8-26 下午02:35:38
 * @author: <a href="mailto:yeahsj@yahoo.com.cn">yeahsj</a>
 * @修改历史:
 */
public class PathLogFilter implements Filter {
	Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
	}

	private void saveLog(ServletContext servletContext, AppPathLogDetail log) {
		WritePathLogJob job = new WritePathLogJob(servletContext, log);
		AppTaskExecutors.getInstance().addLogTask(job);
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		String path = req.getRequestURI();
		// logger.info(req.getRequestURL().toString());

		if (path.equals("/") || path.indexOf("css") > -1
				|| path.indexOf("js") > -1) {
			chain.doFilter(request, response);
		} else if (path.indexOf("/api/authStatus") > -1) {
			chain.doFilter(request, response);
		} else {
			StringBuilder params = this.getParams(req);
			StringBuilder headers = this.getHeaders(req);
			long startTime = Calendar.getInstance().getTimeInMillis();
			String remoteAddr = req.getRemoteAddr();

			ServletContext servletContext = req.getSession()
					.getServletContext();
			try {
				beforeFilter(path);
				chain.doFilter(request, response);
				afterFilter(servletContext, path, params.toString(),
						headers.toString(), remoteAddr, startTime);
			} catch (Exception ex) {
				String errMsg = ex.getMessage();
				errorOnFilter(servletContext, path, params.toString(),
						headers.toString(), remoteAddr, startTime, errMsg);
			}
		}

	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
	}

	@SuppressWarnings("unchecked")
	private StringBuilder getParams(HttpServletRequest req) {
		Enumeration<String> names = req.getParameterNames();
		StringBuilder params = new StringBuilder();
		boolean isfirst = true;
		while (names.hasMoreElements()) {
			String name = (String) names.nextElement();
			String value = req.getParameter(name);
			if (isfirst) {
				isfirst = false;
			} else {
				params.append("&");
			}
			params.append(name);
			params.append("=");
			params.append(value);
		}
		return params;
	}

	@SuppressWarnings("unchecked")
	private StringBuilder getHeaders(HttpServletRequest req) {
		Enumeration<String> names = req.getHeaderNames();
		StringBuilder params = new StringBuilder();
		boolean isfirst = true;
		while (names.hasMoreElements()) {
			String name = (String) names.nextElement();
			String value = req.getHeader(name);
			if (isfirst) {
				isfirst = false;
			} else {
				params.append("&");
			}
			params.append(name);
			params.append("=");
			params.append(value);
		}
		return params;
	}

	private void beforeFilter(String path) {

		StringBuilder msg = new StringBuilder();
		msg.append("start");
		msg.append(" ");
		msg.append(path);
		msg.append(" ");
		msg.append("success");
		msg.append(" ");
		msg.append(".");
		msg.append(".");
		msg.append(".");
		msg.append(".");
		logger.info(msg.toString());
	}

	private void afterFilter(ServletContext servletContext, String path,
			String params, String headers, String remoteAddr, long startTime) {
		long endTime = Calendar.getInstance().getTimeInMillis();
		if (params.length() >= 255) {
			params = params.substring(0, 255);
		}
		if (headers.length() >= 255) {
			headers = headers.substring(0, 255);
		}
		AppPathLogDetail log = new AppPathLogDetail();
		log.setPath(path);
		log.setStarttime(startTime);
		log.setEndtime(endTime);
		log.setStatus(1);
		log.setParams(params);
		log.setHeaders(headers);
		log.setRemoteAddr(remoteAddr);
		saveLog(servletContext, log);
		// StringBuilder msg = new StringBuilder();
		// msg.append("end");
		// msg.append(" ");
		// msg.append(path);
		// msg.append(" ");
		// msg.append("success");
		// msg.append(" ");
		// msg.append(".");
		// msg.append(".");
		// msg.append(".");
		// msg.append(".");
		// msg.append(" ");
		// msg.append(",");
		// msg.append(" ");
		// msg.append("cost");
		// msg.append(endTime - startTime);
		// msg.append(" ");
		// msg.append("ms");
		// logger.info(msg.toString());
	}

	private void errorOnFilter(ServletContext servletContext, String path,
			String params, String headers, String remoteAddr, long startTime,
			String errMsg) {
		long endTime = Calendar.getInstance().getTimeInMillis();
		if (params.length() >= 255) {
			params = params.substring(0, 255);
		}
		if (errMsg.length() >= 255) {
			errMsg = errMsg.substring(0, 255);
		}
		if (headers.length() >= 255) {
			headers = headers.substring(0, 255);
		}
		AppPathLogDetail log = new AppPathLogDetail();
		log.setPath(path);
		log.setStarttime(startTime);
		log.setEndtime(endTime);
		log.setStatus(1);
		log.setParams(params);
		log.setMsg(errMsg);
		log.setHeaders(headers);
		log.setRemoteAddr(remoteAddr);
		saveLog(servletContext, log);
		// StringBuilder msg = new StringBuilder();
		// msg.append("end");
		// msg.append(" ");
		// msg.append(path);
		// msg.append(" ");
		// msg.append("failed");
		// msg.append(" ");
		// msg.append(".");
		// msg.append(".");
		// msg.append(".");
		// msg.append(".");
		// msg.append(" ");
		// msg.append(",");
		// msg.append(" ");
		// msg.append("cost");
		// msg.append(endTime - startTime);
		// msg.append(" ");
		// msg.append("ms");
		// msg.append(" ");
		// msg.append(".");
		// msg.append(errMsg);
		// logger.info(msg.toString());
	}
}
