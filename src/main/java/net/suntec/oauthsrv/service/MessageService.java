package net.suntec.oauthsrv.service;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.RequestContext;

@Service
public class MessageService {
	@Autowired
	ResourceBundleMessageSource messageSource;

	public String getMessage(HttpServletRequest req, String key) {
		return getMessage(req, key, new String[0]);
	}

	public String getMessage(HttpServletRequest req, String key, String... msgs) {
		RequestContext requestContext = new RequestContext(req);
		Locale myLocale = requestContext.getLocale();
		return messageSource.getMessage(key, msgs, myLocale);
	}

	public String getMessage(HttpServletRequest req, String key, int code) {
		String codestr = String.valueOf(code);
		return getMessage(req, key, codestr);
	}

	public String getMessage(HttpServletRequest req, String key, Object[] args) {
		RequestContext requestContext = new RequestContext(req);
		Locale myLocale = requestContext.getLocale();
		return messageSource.getMessage(key, args, myLocale);
	}

	public String getMessage(HttpServletRequest req, String key, Object[] args, String defaultVal) {
		RequestContext requestContext = new RequestContext(req);
		Locale myLocale = requestContext.getLocale();
		return messageSource.getMessage(key, args, defaultVal, myLocale);
	}
}
