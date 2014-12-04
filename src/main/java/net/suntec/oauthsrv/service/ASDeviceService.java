package net.suntec.oauthsrv.service;

import net.suntec.constant.AuthErrorCodeConstant;
import net.suntec.framework.exception.ASServiceException;
import net.suntec.oauthsrv.action.param.IautoDeviceParamDTO;
import net.suntec.oauthsrv.dao.AppBaseMapper;
import net.suntec.oauthsrv.dao.AppIautoBindConfigMapper;
import net.suntec.oauthsrv.dao.AppIautoMapMapper;
import net.suntec.oauthsrv.dto.AppIautoBindConfig;
import net.suntec.oauthsrv.dto.AppIautoMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openjava.core.util.StrUtil;

/**
 * 
 * 
 * @项目名称: OauthSrv
 * @功能描述:
 * @当前版本： 1.0
 * @创建时间: 2014年12月4日 下午5:53:24
 * @author: <a href="mailto:yeahsj@yahoo.com.cn">yeahsj</a>
 * @修改历史:
 */
@Service
public class ASDeviceService {
	@Autowired
	AppBaseMapper appBaseMapper;

	@Autowired
	ASCoreService aSCoreService;
	@Autowired
	AppIautoMapMapper appIautoMapMapper;
	@Autowired
	AppIautoBindConfigMapper appIautoBindConfigMapper;

	/**
	 * 同意并上传授权信息--车机功能
	 * 
	 * @param record
	 * @param param
	 */
	public void saveAgreeAndBindApp(AppIautoMap record,
			AppIautoBindConfig param, Integer action, boolean saveAuthHistory) {
		aSCoreService.saveAuthStatus(record, action, saveAuthHistory);
		aSCoreService.saveAgreeBindConfig(param);
	}

	public void saveAgreeAndBindApp(AppIautoMap record,
			AppIautoBindConfig param, Integer action) {
		saveAgreeAndBindApp(record, param, action, true);
	}

	/**
	 * 获取AccessToken
	 * 
	 * @param iautoDeviceParamDTO
	 * @param appType
	 * @param loginName
	 * @return
	 */
	public AppIautoMap saveFetchToken(IautoDeviceParamDTO iautoDeviceParamDTO,
			String appType, String loginName) {
		AppIautoMap params = new AppIautoMap();
		params.setIautoUserId(loginName);
		params.setAppType(appType);
		AppIautoMap record = appIautoMapMapper.selectByPrimaryKeys(params);
		if (null != record) {
			String selectClientId = aSCoreService.selectExistsClientId(appType,
					iautoDeviceParamDTO.getDeviceNo());
			if (StrUtil.isEmpty(selectClientId)) {
				aSCoreService
						.saveDeviceNoToClientId(appType,
								iautoDeviceParamDTO.getDeviceNo(),
								record.getClientId());
			}
		}
		return record;
	}

	/**
	 * 登出第三方App(车机)
	 * 
	 * @param record
	 * @param action
	 */
	public void saveLogout(AppIautoMap record, Integer action,
			boolean saveAuthHistory) {
		if (StrUtil.isEmpty(record.getIautoUserId())) {
			throw new ASServiceException("you should login first",
					AuthErrorCodeConstant.APP_NO_LOGIN_NAME);
		}
		if (StrUtil.isEmpty(record.getAppType())) {
			throw new ASServiceException("app type is required",
					AuthErrorCodeConstant.APP_NO_APP_TYPE);
		}
		if (saveAuthHistory) {
			aSCoreService.saveAuthHistory(record.getAppType(),
					record.getIautoUserId(), action);
		}
		appIautoMapMapper.deleteByPrimaryKeys(record);
	}

	public void saveLogout(AppIautoMap record, Integer action) {
		this.saveLogout(record, action, true);
	}

	@Deprecated
	public void saveLogoutApp(AppIautoMap record) {
		appIautoMapMapper.deleteByPrimaryKeys(record);
	}

}
