package net.suntec.oauthsrv.sample;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/sample")
public class SampleAction {
	private Logger logger = LoggerFactory.getLogger(getClass());

	@RequestMapping(value = "/twitter")
	public String twitter() {
		logger.debug("test twitter");
		return "/sample/twitter";
	}
}
