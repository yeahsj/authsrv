package net.suntec.oauthsrv.action;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.suntec.framework.constant.AppConstant;
import net.suntec.framework.dto.SpringDetailJsonResult;
import net.suntec.framework.dto.SpringErrorJsonResult;
import net.suntec.framework.dto.SpringJsonResult;
import net.suntec.framework.exception.ASBaseException;
import net.suntec.framework.iauto.dto.result.IautoPhoneLoginResultDTO;
import net.suntec.framework.util.ASLogger;
import net.suntec.oauthsrv.service.IautoApiService;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/dev")
public class DeveloperAction {
	private final ASLogger logger = new ASLogger(DeveloperAction.class);

	@RequestMapping(value = "/{provider}/sessionToken")
	public @ResponseBody SpringJsonResult<IautoPhoneLoginResultDTO> getSessionToken(
			@PathVariable("provider") String provider) {
		logger.info("start [TST-1] " + provider + " ....... ");
		SpringJsonResult<IautoPhoneLoginResultDTO> result = null;
		IautoApiService iautoApiService = new IautoApiService();
		try {
			IautoPhoneLoginResultDTO dto = null;
			if (provider.equals("phone")) {
				dto = iautoApiService.doPhoneLogin();
			} else {
				dto = iautoApiService.doDeviceLogin();
			}
			result = new SpringDetailJsonResult<IautoPhoneLoginResultDTO>();
			result.setResult(dto);
			result.setCode(AppConstant.SUCCESS_CODE);
		} catch (ASBaseException ex) {
			logger.error(ex.getMessage());
			result = new SpringErrorJsonResult<IautoPhoneLoginResultDTO>();
			result.setErrMsg(ex.getMessage());
			result.setCode(ex.getErrCode());
		}
		return result;
	}
	
	@RequestMapping(value = "/switchAgree")
	public String switchAgree(HttpServletRequest req, HttpServletResponse res) throws IOException {
		logger.info("start [TST-2]  ....... ");
//		res.sendRedirect(  "/flow/device/debugSwitchAgree.jsp" );
//		return null;
		return "/flow/device/debugSwitchAgree";
	}
}
