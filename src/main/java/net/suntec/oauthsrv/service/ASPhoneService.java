package net.suntec.oauthsrv.service;

import java.util.List;

import net.suntec.constant.AuthErrorCodeConstant;
import net.suntec.framework.exception.ASServiceException;
import net.suntec.oauthsrv.constant.AppConstant;
import net.suntec.oauthsrv.dao.AppBaseMapper;
import net.suntec.oauthsrv.dao.AppIautoMapMapper;
import net.suntec.oauthsrv.dto.AppBase;
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
 * @创建时间: 2014-11-7 下午05:30:37
 * @author: <a href="mailto:yeahsj@yahoo.com.cn">yeahsj</a>
 * @修改历史:
 */
@Service
public class ASPhoneService {
	@Autowired
	AppBaseMapper appBaseMapper;

	@Autowired
	ASCoreService aSCoreService;
	@Autowired
	AppIautoMapMapper appIautoMapMapper;

	/**
	 * 
	 * @param userFlag
	 * @return
	 */
	public List<AppBase> selectPhoneBindAppList(String userFlag) {
		AppIautoMap aim = new AppIautoMap();
		aim.setIautoUserId(userFlag);
		return appBaseMapper.listBindApp(aim);
	}
	
	/**
	 * 
	 * @param userFlag
	 * @return
	 */
	public List<AppBase> selectPhoneBindAppListNew(String userFlag) {
		AppIautoMap aim = new AppIautoMap();
		aim.setIautoUserId(userFlag);
		return appBaseMapper.bindAppList(aim);
	}

	/**
	 * 登出第三方App(手机)
	 * 
	 * @param record
	 * @return
	 */
	public AppBase deletePhoneLogoutApp(AppIautoMap record) {
		return this.deletePhoneLogoutApp(record, true);
	}

	/**
	 * 登出第三方App(手机)
	 * 
	 * @param record
	 * @param saveAuthHistory
	 * @return
	 */
	public AppBase deletePhoneLogoutApp(AppIautoMap record,
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
					record.getIautoUserId(), AppConstant.AUTH_LOGOUT_PHONE);
		}
		appIautoMapMapper.deleteByPrimaryKeys(record);
		return appBaseMapper.selectByPrimaryKey(record.getAppType());
	}

}
