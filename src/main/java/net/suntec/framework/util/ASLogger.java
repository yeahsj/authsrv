package net.suntec.framework.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ASLogger {
	Logger logger;

	public ASLogger(Class<?> clazz) {
		this.logger = LoggerFactory.getLogger(clazz);
	}

	public void info(String msg) {
		logger.info(msg);
	}

	public void info(String format, Object... arg) {
		logger.info(format, arg);
	}

	public void exception(String msg) {
		logger.error(msg);
	}

	public void exception(String format, Object... arg) {
		logger.error(format, arg);
	}
	
	public void exception(String format, Throwable arg) {
		logger.error(format, arg);
	}

	public void error(String msg) {
		logger.info("WM: " + msg);
	}

	public void error(String format, Object... arg) {
		logger.info("WM: " + format, arg);
	}

	public void warn(String msg) {
		logger.warn(msg);
	}

	public void warn(String format, Object... arg) {
		logger.warn(format, arg);
	}

	public void debug(String msg) {
		logger.debug(msg);
	}

	public void debug(String format, Object... arg) {
		logger.debug(format, arg);
	}

}
