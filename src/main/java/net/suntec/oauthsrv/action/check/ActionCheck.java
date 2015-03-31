package net.suntec.oauthsrv.action.check;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.suntec.oauthsrv.service.MessageService;

public interface ActionCheck {
	public boolean execute(HttpServletRequest req, HttpServletResponse res,
			String provider, MessageService messageService);
}
