package net.suntec.oauthsrv.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.suntec.oauthsrv.dto.AppIautoMap;
import net.suntec.oauthsrv.util.ASLogger;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/page")
public class PageAction {
	private final ASLogger logger = new ASLogger(OauthAction.class);

	@RequestMapping(value = "/iAuto3rdBind")
	public String switchAgree(HttpServletRequest req, HttpServletResponse res,
			@ModelAttribute AppIautoMap record) {
		//
		String backurl = req.getParameter("backurl");
		logger.info(backurl);
		req.setAttribute("backurl", backurl);
		req.setAttribute("agreeParams", record);
		return "/flow/device/switchAgree";
	}
}
