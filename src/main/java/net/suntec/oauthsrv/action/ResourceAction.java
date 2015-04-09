package net.suntec.oauthsrv.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.suntec.oauthsrv.util.ASLogger;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/res")
public class ResourceAction {
	private final ASLogger logger = new ASLogger(OauthAction.class);

	@RequestMapping(value = "/{provider}/logo")
	public String switchAgree(HttpServletRequest req, HttpServletResponse res,
			@PathVariable("provider") String provider) throws IOException {
		logger.info("start [OAUTH-2-10]  ....... ");
		String png = "/css/images/app/" + provider + "-icon.png";
		logger.info("png path : " + png);
		try {
			req.getRequestDispatcher(png).forward(req, res);
		} catch (ServletException e) {
			logger.exception(e.getMessage());
		}
		return null;
	}
}
