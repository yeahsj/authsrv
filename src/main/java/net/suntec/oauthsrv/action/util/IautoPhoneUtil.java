package net.suntec.oauthsrv.action.util;

import net.suntec.constant.AuthErrorCodeConstant;
import net.suntec.framework.exception.ASIautoException;
import net.suntec.framework.iauto.dto.IautoUserDTO;
import net.suntec.oauthsrv.action.param.IautoPhoneParamDTO;
import net.suntec.oauthsrv.framework.IautoUserCacheMap;
import net.suntec.oauthsrv.service.IautoApiService;

import com.openjava.core.util.StrUtil;

/**
 * 
 * 
 * @项目名称: OauthSrv
 * @功能描述: 根据sessionToken 获取loginName
 * @当前版本： 1.0
 * @创建时间: 2014-8-21 上午10:00:20
 * @author: <a href="mailto:yeahsj@yahoo.com.cn">yeahsj</a>
 * @修改历史:
 */
public final class IautoPhoneUtil {

	public static String getIautoPhoneUserName(String sessionToken) {
		IautoPhoneParamDTO iautoPhoneParamDTO = new IautoPhoneParamDTO();
		iautoPhoneParamDTO.setSessionToken(sessionToken);
		return getIautoPhoneUserName(iautoPhoneParamDTO);
	}

	public static String getIautoPhoneUserName(
			IautoPhoneParamDTO iautoPhoneParamDTO) {
		String sessionToken = iautoPhoneParamDTO.getSessionToken();
		if (IautoUserCacheMap.getInstance().containsKey(sessionToken)) {
			return IautoUserCacheMap.getInstance().get(sessionToken);
		} else {
			return getIautoPhoneUserNameFromIautoServer(iautoPhoneParamDTO);
		}
	}

	public static String getIautoPhoneUserNameFromIautoServer(
			IautoPhoneParamDTO iautoPhoneParamDTO) {
		return getIautoPhoneUserNameFromIautoServer(iautoPhoneParamDTO, true);
	}

	public static String getIautoPhoneUserNameFromIautoServer(
			IautoPhoneParamDTO iautoPhoneParamDTO, boolean cache) {
		IautoUserDTO user = getIautoPhoneUser(iautoPhoneParamDTO);
		if (null == user) {
			throw new ASIautoException(
					" user not found or iauto session expired ",
					AuthErrorCodeConstant.APP_INVALID_ACCESS_TOKEN);
		}
		String userName = user.getUserName();
		if (StrUtil.isEmpty(userName)) {
			throw new ASIautoException(
					" iauto loginName not exists iauto sessiontoken expired ",
					AuthErrorCodeConstant.APP_NO_LOGIN_NAME);
		}
		if (cache) {
			IautoUserCacheMap.getInstance().put(
					iautoPhoneParamDTO.getSessionToken(), userName);
		}
		return userName;
	}

	public static IautoUserDTO getIautoPhoneUser(
			IautoPhoneParamDTO iautoPhoneParamDTO) {
		IautoApiService iautoApiService = new IautoApiService();
		return iautoApiService.doGetPhoneUserInfo(
				iautoPhoneParamDTO.getSessionToken(), null, null);
	}

}
