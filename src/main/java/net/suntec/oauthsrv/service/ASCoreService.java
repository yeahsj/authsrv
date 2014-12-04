package net.suntec.oauthsrv.service;

import java.util.List;

import net.suntec.constant.AuthErrorCodeConstant;
import net.suntec.framework.exception.ASServiceException;
import net.suntec.oauthsrv.dao.AppDeviceDistributeMapper;
import net.suntec.oauthsrv.dao.AppIautoBindConfigMapper;
import net.suntec.oauthsrv.dao.AppIautoMapMapper;
import net.suntec.oauthsrv.dao.AppInfoMapper;
import net.suntec.oauthsrv.dao.AuthHistoryMapper;
import net.suntec.oauthsrv.dto.AppDeviceDistribute;
import net.suntec.oauthsrv.dto.AppIautoBindConfig;
import net.suntec.oauthsrv.dto.AppIautoMap;
import net.suntec.oauthsrv.dto.AppInfo;
import net.suntec.oauthsrv.dto.AuthHistory;
import net.suntec.oauthsrv.framework.OauthAppConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openjava.core.util.DateUtil;
import com.openjava.core.util.StrUtil;

/**
 * 
 * 
 * @项目名称: OauthSrv
 * @功能描述:
 * @当前版本： 1.0
 * @创建时间: 2014年12月4日 下午5:53:19
 * @author: <a href="mailto:yeahsj@yahoo.com.cn">yeahsj</a>
 * @修改历史:
 */
@Service
public class ASCoreService {
	@Autowired
	AppInfoMapper appInfoMapper;
	@Autowired
	AppDeviceDistributeMapper appDeviceDistributeMapper;
	@Autowired
	AuthHistoryMapper authHistoryMapper;
	@Autowired
	AppIautoMapMapper appIautoMapMapper;
	@Autowired
	AppIautoBindConfigMapper appIautoBindConfigMapper;

	public String selectLastestHistoryTime(String loginName) {
		AuthHistory params = new AuthHistory();
		params.setIautoUserId(loginName);
		List<AuthHistory> records = authHistoryMapper.selectLastestTime(params);
		if (records.size() > 0) {
			return records.get(0).getCreationDate();
		} else {
			return DateUtil.getCurDateString();
		}
	}

	/**
	 * 保存授权历史信息
	 * 
	 * @param appType
	 * @param loginName
	 * @param action
	 * @return
	 */
	public int saveAuthHistory(String appType, String loginName, Integer action) {
		AuthHistory authHistory = new AuthHistory();
		authHistory.setAppType(appType);
		authHistory.setIautoUserId(loginName);
		authHistory.setAction(action);
		return authHistoryMapper.insert(authHistory);
	}

	/**
	 * 保存认证信息 -- 手机/车机功能
	 * 
	 * @param record
	 * @param action
	 * @return
	 */
	public int saveAuthStatus(AppIautoMap record, Integer action,
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
			saveAuthHistory(record.getAppType(), record.getIautoUserId(),
					action);
		}
		appIautoMapMapper.deleteByPrimaryKeys(record);
		return appIautoMapMapper.insert(record);
	}

	/**
	 * 保存认证信息 -- 手机/车机功能
	 * 
	 * @param record
	 * @param action
	 * @return
	 */
	public int saveAuthStatus(AppIautoMap record, Integer action) {
		return this.saveAuthStatus(record, action, true);
	}

	/************** agree *******************/
	/**
	 * 同意上传标识切换
	 * 
	 * @param loginName
	 * @param provider
	 * @param status
	 */
	@Deprecated
	public void saveAgreeSwitch(String loginName, String provider, String status) {
		if (StrUtil.isEmpty(loginName)) {
			throw new ASServiceException("you should login first",
					AuthErrorCodeConstant.APP_NO_LOGIN_NAME);
		}
		// if (StrUtil.isEmpty(provider)) {
		// throw new ASServiceException("app type is required",
		// AuthErrorCodeConstant.APP_NO_APP_TYPE);
		// }
		AppIautoBindConfig agreeParam = new AppIautoBindConfig();
		agreeParam.setUserName(loginName);
		agreeParam.setAppType(provider);

		if (null != status
				&& (status.equals("true") || status.equals("Y") || status
						.equals("1"))) {
			this.saveAgreeBindConfig(agreeParam);
		} else {
			this.cancelAgreeBindConfig(agreeParam);
		}
	}

	/**
	 * 同意标识
	 * 
	 * @param param
	 */
	public void saveAgreeBindConfig(AppIautoBindConfig param) {
		appIautoBindConfigMapper.deleteByPrimaryKey(param);
		appIautoBindConfigMapper.insert(param);
	}

	/**
	 * 不同意标识
	 * 
	 * @param param
	 */
	@Deprecated
	private void cancelAgreeBindConfig(AppIautoBindConfig param) {
		appIautoBindConfigMapper.deleteByPrimaryKey(param);
	}

	/**
	 * 是否同意上传用户和App信息的标记
	 * 
	 * @param param
	 * @return
	 */
	public boolean hasAgreeBindConfig(String loginName) {
		if (StrUtil.isEmpty(loginName)) {
			throw new ASServiceException("you should login first",
					AuthErrorCodeConstant.APP_NO_LOGIN_NAME);
		}
		AppIautoBindConfig param = new AppIautoBindConfig();
		param.setUserName(loginName);
		return hasAgreeBindConfig(param);
	}

	/**
	 * 是否同意上传用户和App信息的标记
	 * 
	 * @param param
	 * @return
	 */
	private boolean hasAgreeBindConfig(AppIautoBindConfig param) {
		if (StrUtil.isEmpty(param.getUserName())) {
			throw new ASServiceException("you should login first",
					AuthErrorCodeConstant.APP_NO_LOGIN_NAME);
		}
		AppIautoBindConfig record = appIautoBindConfigMapper
				.selectByPrimaryKey(param);
		if (null == record) {
			return false;
		} else {
			return true;
		}
	}

	public void deleteExpiredApps(int expireTime) {
		AppIautoMap record = new AppIautoMap();
		record.setExpireTime(expireTime);
		appIautoMapMapper.deleteExpiredApps(record);
	}

	/**
	 * 选择client Id
	 * 
	 * @param appType
	 * @return
	 */
	public String selectClientId(String appType) {
		List<AppInfo> appInfos = appInfoMapper.selectClientId(appType);
		if (null != appInfos && appInfos.size() > 0) {
			return appInfos.get(0).getAppClientid();
		} else {
			return selectDefaultClientId(appType);
		}
	}

	public String saveAfterSelectClientId(String appType, String deviceNo) {
		String clientId = selectExistsClientId(appType, deviceNo);
		if (StrUtil.isEmpty(clientId)) {
			clientId = selectClientId(appType);
			if (StrUtil.isNotEmpty(deviceNo)) {
				this.saveDeviceNoToClientId(appType, deviceNo, clientId);
			}
		}
		return clientId;
	}

	/**
	 * 获取已经绑定deviceNo的clientId
	 * 
	 * @param appType
	 * @param deviceNo
	 * @return
	 */
	public String selectExistsClientId(String appType, String deviceNo) {
		AppDeviceDistribute param = new AppDeviceDistribute();
		param.setAppType(appType);
		param.setDeviceNo(deviceNo);
		List<AppDeviceDistribute> records = appDeviceDistributeMapper
				.selectClientIdByDeviceNo(param);
		if (null != records & records.size() > 0) {
			return records.get(0).getClientId();
		} else {
			return null;
		}
	}

	public void saveDeviceNoToClientId(String appType, String deviceNo,
			String clientId) {
		AppDeviceDistribute param = new AppDeviceDistribute();
		param.setAppType(appType);
		param.setDeviceNo(deviceNo);
		param.setClientId(clientId);
		appDeviceDistributeMapper.insert(param);
	}

	public String selectDefaultClientId(String appType) {
		List<AppInfo> appInfos = OauthAppConfig.getInstance().getAppInfos();
		for (AppInfo appInfo : appInfos) {
			if (appInfo.getAppType().equals(appType)) {
				return appInfo.getAppClientid();
			}
		}
		return null;
	}

}
