package net.suntec.oauthsrv.dao;

import java.util.List;

import net.suntec.framework.PioneerDAO;
import net.suntec.oauthsrv.dto.AppInfo;

/**
 * 
 * @项目名称: oauthsrv
 * @功能描述: oauth srv架构
 * @修改历史: 1.0
 * @当前版本： 1.0
 * @创建时间: 2014-05-22 17:06:47
 * @author: <a href="mailto:yeahsj@gmail.com">sang jun</a>
 */
public interface AppInfoMapper extends PioneerDAO<AppInfo> {
	public List<AppInfo> selectClientId(String appType);
	
	public boolean checkClientIdValid(AppInfo appInfo);
	
	public AppInfo selectByClientId(AppInfo appInfo);
}
