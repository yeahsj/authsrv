package net.suntec.oauthsrv.framework;

import java.util.Enumeration;
import java.util.Locale;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LocaleRequestWrapper extends HttpServletRequestWrapper {
	Logger logger = LoggerFactory.getLogger(LocaleRequestWrapper.class);

	public LocaleRequestWrapper(HttpServletRequest request) {
		super(request);
	}

	@Override
	public Enumeration<Locale> getLocales() {
		Vector<Locale> v = new Vector<Locale>(1);
		v.add(getLocale());
		return v.elements();
	}

	public Locale getLocale() {
		Object localeObj = ((HttpServletRequest) getRequest()).getSession()
				.getAttribute("locale");
		if (null != localeObj) {
			String localeStr = localeObj.toString();
			logger.debug("add locale " + localeStr);
			Locale locale = new Locale(localeStr);
			return locale;
		} else {
			logger.debug("request locale " + getRequest().getLocale() );
			return getRequest().getLocale();
		}

	}
}