package net.suntec.oauthsrv.action.util;

import net.suntec.framework.constant.AppConstant;
import net.suntec.framework.dto.SpringDetailJsonResult;
import net.suntec.framework.dto.SpringErrorJsonResult;
import net.suntec.framework.dto.SpringJsonResult;

import com.openjava.core.util.StrUtil;

public final class SpringResultUtil {
	/**
	 * 
	 * @param errMsg
	 * @return
	 */
	public static SpringJsonResult<String> jsonResult(String errMsg) {
		SpringJsonResult<String> result = null;
		if (!StrUtil.isEmpty(errMsg)) {
			result = new SpringErrorJsonResult<String>();
			result.setCode(AppConstant.ERROR_CODE);
			result.setErrMsg(errMsg);
		} else {
			result = new SpringDetailJsonResult<String>();
			result.setCode(AppConstant.SUCCESS_CODE);
			result.setResult("success");
		}
		return result;
	}

	/**
	 * 
	 * @param errMsg
	 * @return
	 */
	public static SpringJsonResult<String> jsonResult(String errMsg, int errCode) {
		SpringJsonResult<String> result = null;
		if (!StrUtil.isEmpty(errMsg)) {
			result = new SpringErrorJsonResult<String>();
			result.setCode(errCode);
			result.setErrMsg(errMsg);
		} else {
			result = new SpringDetailJsonResult<String>();
			result.setCode(AppConstant.SUCCESS_CODE);
			result.setResult("success");
		}
		return result;
	}
}
