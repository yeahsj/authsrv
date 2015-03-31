package net.suntec.oauthsrv.action.util;

import javax.servlet.http.HttpServletRequest;

import net.suntec.constant.AuthErrorCodeConstant;
import net.suntec.framework.constant.MessageConstant;
import net.suntec.framework.exception.ASBaseException;
import net.suntec.framework.exception.ASIautoException;
import net.suntec.framework.iauto.dto.IautoConfigDTO;
import net.suntec.framework.iauto.dto.IautoUserDTO;
import net.suntec.oauthsrv.action.param.IautoDeviceParamDTO;
import net.suntec.oauthsrv.framework.IautoUserCacheMap;
import net.suntec.oauthsrv.framework.ResourceConfig;
import net.suntec.oauthsrv.service.IautoApiService;
import net.suntec.oauthsrv.service.MessageService;

import com.openjava.core.util.StrUtil;

/**
 * 
 * 
 * @项目名称: OauthSrv
 * @功能描述: 处理Iauto Device的信息获取
 *        <p>
 *        1.根据sessionToken 获取loginName <br>
 *        2.
 *        </p>
 * @当前版本： 1.0
 * @创建时间: 2014-8-18 上午11:46:00
 * @author: <a href="mailto:yeahsj@yahoo.com.cn">yeahsj</a>
 * @修改历史:
 */
public final class IautoDeviceUtil {

	/**
	 * 获取 iauto_login_name
	 * <p>
	 * 首先从IautoUserCacheMap中查找，如果没有，则从服务器获取
	 * </p>
	 * 
	 * @param req
	 * @param iautoDeviceParamDTO
	 * @return
	 */
	public static String getIautoDeviceUserName(HttpServletRequest req,
			IautoDeviceParamDTO iautoDeviceParamDTO,
			MessageService messageService) {
		String loginName = null;
		IautoConfigDTO iautoConfigDTO = ResourceConfig.getInstance()
				.getIautoConfigDTO();
		// 是否模拟测试
		if (iautoConfigDTO.getIsDemo()) {
			loginName = iautoConfigDTO.getDemoName();
		} else {
			String sessionToken = iautoDeviceParamDTO.getSessionToken();
			if (IautoUserCacheMap.getInstance().containsKey(sessionToken)) {
				loginName = IautoUserCacheMap.getInstance().get(sessionToken);
			} else {
				loginName = getIautoDeviceUserNameFromIautoServer(iautoDeviceParamDTO);
			}
		}

		if (StrUtil.isEmpty(loginName)) {
			throw new ASBaseException(messageService.getMessage(req,
					MessageConstant.MSG_AUTHENTICATE_FAILED,
					AuthErrorCodeConstant.APP_NO_LOGIN_NAME),
					AuthErrorCodeConstant.APP_NO_LOGIN_NAME);
		}
		return loginName;
	}

	private static String getIautoDeviceUserNameFromIautoServer(
			IautoDeviceParamDTO iautoDeviceParamDTO) {
		return getIautoDeviceUserNameFromIautoServer(iautoDeviceParamDTO, true);
	}

	/**
	 * 从iauto 服务器获取 iauto_login_name
	 * 
	 * @param iautoDeviceParamDTO
	 * @return
	 */
	private static String getIautoDeviceUserNameFromIautoServer(
			IautoDeviceParamDTO iautoDeviceParamDTO, boolean cache) {
		String iautoSessionToken = iautoDeviceParamDTO.getSessionToken();
		IautoUserDTO user = getIautoDeviceUser(iautoDeviceParamDTO);
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
			// 将sessionToken和userName对应关系设置到IautoUserCacheMap中
			IautoUserCacheMap.getInstance().put(iautoSessionToken, userName);
		}
		return userName;
	}

	/**
	 * 从iauto 服务器获取 iauto_login_name
	 * 
	 * @param iautoDeviceParamDTO
	 * @return
	 */
	private static IautoUserDTO getIautoDeviceUser(
			IautoDeviceParamDTO iautoDeviceParamDTO) {
		String iautoSessionToken = iautoDeviceParamDTO.getSessionToken();
		// String platformVersion = iautoDeviceParamDTO.getPlatformVersion();
		// String deviceNo = iautoDeviceParamDTO.getDeviceNo();
		if (StrUtil.isEmpty(iautoSessionToken)) {
			throw new ASIautoException(" sessionToken is required  ",
					AuthErrorCodeConstant.IT_GUI_ERR_NO_SESSIONTOKEN);
		}
		// if (StrUtil.isEmpty(platformVersion)) {
		// throw new ASIautoException(" platformVersion is required  ",
		// AuthErrorCodeConstant.IT_GUI_DEVICE_ERR_NO_IF);
		// }
		// if (StrUtil.isEmpty(deviceNo)) {
		// throw new ASIautoException(" deviceNo is required  ",
		// AuthErrorCodeConstant.IT_GUI_DEVICE_ERR_NO_DEVICENO);
		// }
		String iautoClientId = iautoDeviceParamDTO.getIautoClientId();
		String languageCode = "";
		IautoApiService iautoApiService = new IautoApiService();
		return iautoApiService.doGetWebUserInfo(iautoSessionToken,
				iautoClientId, languageCode);
	}
}
