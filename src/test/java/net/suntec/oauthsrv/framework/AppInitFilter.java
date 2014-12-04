package net.suntec.oauthsrv.framework;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AppInitFilter implements Filter {

	private boolean isFirst = true;
	Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
			ServletException {
		if (isFirst) {
			logger.info("getProtocol: " + request.getProtocol());
			logger.info("getScheme: " + request.getScheme());
			logger.info("ServerName: " + request.getServerName());
			logger.info("ServerPort: " + request.getServerPort());
			String localServer = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
			ResourceConfig.getInstance().setLocalServer(localServer);
			isFirst = false;
		}
		chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
