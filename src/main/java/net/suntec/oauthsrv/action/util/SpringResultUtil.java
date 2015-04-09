package net.suntec.oauthsrv.action.util;

import net.suntec.framework.springmvc.json.dto.SpringDetailJsonResult;
import net.suntec.framework.springmvc.json.dto.SpringErrorJsonResult;
import net.suntec.framework.springmvc.json.dto.SpringJsonResult;
import net.suntec.oauthsrv.constant.AppConstant;

import com.openjava.core.util.StrUtil;

/**
 * 
 * 
 * @项目名称: OauthSrv
 * @功能描述:
 * @当前版本： 1.0
 * @创建时间: 2014年12月4日 下午5:51:13
 * @author: <a href="mailto:yeahsj@yahoo.com.cn">yeahsj</a>
 * @修改历史:
 */
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
