package net.suntec.oauthsrv.framework.handler.impl;

import java.util.Enumeration;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import net.suntec.oauthsrv.framework.handler.LogFilterHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PrintHeaderHandler implements LogFilterHandler {
	Logger logger = LoggerFactory.getLogger(PrintHeaderHandler.class);

	@Override
	public void execute(ServletRequest request, ServletResponse response) {
		HttpServletRequest req = (HttpServletRequest) request;
		Enumeration<String> names = req.getHeaderNames();
		logger.info("print header start............................ ");
		while (names.hasMoreElements()) {
			String name = (String) names.nextElement();
			String value = req.getHeader(name);
			logger.info(name + "=" + value);
		}
		logger.info("print header success............................ ");
	}
}
