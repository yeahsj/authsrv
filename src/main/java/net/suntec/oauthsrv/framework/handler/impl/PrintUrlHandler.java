package net.suntec.oauthsrv.framework.handler.impl;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import net.suntec.oauthsrv.framework.handler.LogFilterHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PrintUrlHandler implements LogFilterHandler {

	Logger logger = LoggerFactory.getLogger(PrintUrlHandler.class);

	@Override
	public void execute(ServletRequest request, ServletResponse response) {
		HttpServletRequest req = (HttpServletRequest) request;
		String path = req.getRequestURI();
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
		logger.info("session id is :" + req.getSession().getId());
	}

}
