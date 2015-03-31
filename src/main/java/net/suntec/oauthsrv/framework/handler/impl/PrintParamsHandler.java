package net.suntec.oauthsrv.framework.handler.impl;

import java.util.Enumeration;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import net.suntec.oauthsrv.framework.handler.LogFilterHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PrintParamsHandler implements LogFilterHandler {

	Logger logger = LoggerFactory.getLogger(PrintParamsHandler.class);

	@Override
	public void execute(ServletRequest request, ServletResponse response) {
		HttpServletRequest req = (HttpServletRequest) request;
		Enumeration<String> names = req.getParameterNames();
		logger.info("print params start............................ ");
		while (names.hasMoreElements()) {
			String name = (String) names.nextElement();
			String value = req.getParameter(name);
			logger.info(name + "=" + value);
		}
		logger.info("print params success............................ ");
	}

}
