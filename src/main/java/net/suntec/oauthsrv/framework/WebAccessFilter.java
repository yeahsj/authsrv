package net.suntec.oauthsrv.framework;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.openjava.core.util.ArrUtil;
import com.openjava.core.util.StrUtil;

public class WebAccessFilter implements Filter {

	String exludePath = "";
	private String[] exceptURLs = null;
	private String[] exceptServletArr = null;
	private String[] exceptJSPArr = null;
	Logger logger = null;

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
			ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		String reDirectURL; // 需要保留以便重定向的URL
		String authenticateURL; // 需要验证权限的URL
		reDirectURL = req.getRequestURI();
		authenticateURL = reDirectURL;

		Object auth = req.getSession().getAttribute("user");
		if (null != auth) {
			chain.doFilter(request, response);
		} else {
			if (needPriviValidate(authenticateURL)) {
				logger.info(authenticateURL + "is needPriviValidate ");
				req.getRequestDispatcher("/flow/login").forward(request, response);
			} else {
				chain.doFilter(request, response);
			}
		}
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		logger = LoggerFactory.getLogger(WebAccessFilter.class);
		exludePath = filterConfig.getInitParameter("exludePath");
		String exceptions = filterConfig.getInitParameter("exceptions");
		String exceptServlets = filterConfig.getInitParameter("exceptServlets");
		String exceptJSPs = filterConfig.getInitParameter("exceptJSPs");
		if (!StrUtil.isEmpty(exceptions)) {
			this.exceptURLs = StrUtil.splitStr(exceptions);
		}
		if (!StrUtil.isEmpty(exceptServlets)) {
			this.exceptServletArr = StrUtil.splitStr(exceptServlets);
		}
		if (!StrUtil.isEmpty(exceptJSPs)) {
			this.exceptJSPArr = StrUtil.splitStr(exceptJSPs);
		}
	}

	/**
	 * 功能：检查当前URL是否需要权限验证。
	 * 
	 * @param authenticateURL
	 *            String
	 * @return boolean
	 */
	private boolean needPriviValidate(String authenticateURL) {
		if (authenticateURL.startsWith("/auth") || authenticateURL.startsWith("/config")) {
			return false;
		}
		boolean needValidate = true;
		String singleURL = getSingleURL(authenticateURL);
		if (StrUtil.endsWith(authenticateURL, exceptJSPArr)) {
			needValidate = false;
		} else if (ArrUtil.contains(exceptServletArr, singleURL)) {
			needValidate = false;
		}

		// 判断请求的URL的后缀是不是属于不检查权限的后缀
		if (exceptURLs != null && exceptURLs.length > 0 && needValidate) {
			int index = authenticateURL.lastIndexOf(".");
			if (index == -1) {
				index = authenticateURL.lastIndexOf("/");
			}
			String urlExtName = authenticateURL.substring(index + 1);
			needValidate = !ArrUtil.contains(exceptURLs, urlExtName, true);
		}
		return needValidate;
	}

	/**
	 * 功能：获取URL串中的具体文件
	 * 
	 * @param authenticateURL
	 *            String
	 * @return String
	 */
	private String getSingleURL(String authenticateURL) {
		String singleURL = authenticateURL;
		int servletIndex = authenticateURL.indexOf("/servlet");
		int jspIndex = authenticateURL.indexOf(".jsp");
		if (servletIndex > -1 || jspIndex > -1) {
			int index = authenticateURL.lastIndexOf("?");
			if (index != -1) {
				singleURL = authenticateURL.substring(0, index);
			}
			if (servletIndex > -1) {
				index = singleURL.lastIndexOf(".");
				if (index == -1) {
					index = singleURL.lastIndexOf("/");
				}
				singleURL = singleURL.substring(index + 1);
			} else {
				index = singleURL.lastIndexOf("/");
				singleURL = singleURL.substring(index + 1);
			}
		}
		return singleURL;
	}

}
