package net.suntec.oauthsrv.action;

import java.util.Calendar;

import net.suntec.framework.springmvc.json.dto.SpringDetailJsonResult;
import net.suntec.framework.springmvc.json.dto.SpringJsonResult;
import net.suntec.oauthsrv.constant.AppConstant;
import net.suntec.oauthsrv.util.ASLogger;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/pub")
public class PubAction {
	private final ASLogger logger = new ASLogger(OauthAction.class);

	@RequestMapping(value = "/utctime")
	public @ResponseBody SpringJsonResult<Long> getUtctime() {
		logger.info("start [PUB-1-1] ....... ");
		SpringJsonResult<Long> result = new SpringDetailJsonResult<Long>();
		long time = Calendar.getInstance().getTimeInMillis();
		result = new SpringDetailJsonResult<Long>();
		result.setResult(time);
		result.setCode(AppConstant.SUCCESS_CODE);
		return result;
	}
}
