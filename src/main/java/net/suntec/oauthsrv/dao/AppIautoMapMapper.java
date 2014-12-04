package net.suntec.oauthsrv.dao;

import java.util.List;

import net.suntec.framework.PioneerDAO;
import net.suntec.oauthsrv.dto.AppIautoMap;

/**
 * 
 * @项目名称: oauthsrv
 * @功能描述: oauth srv架构
 * @修改历史: 1.0
 * @当前版本： 1.0
 * @创建时间: 2014-05-22 17:06:48
 * @author: <a href="mailto:yeahsj@gmail.com">sang jun</a>
 */
public interface AppIautoMapMapper extends PioneerDAO<AppIautoMap> {
	public void deleteByPrimaryKeys(AppIautoMap record);

	public void deleteExpiredApps(AppIautoMap record);

	public AppIautoMap selectByPrimaryKeys(AppIautoMap record);

	public List<AppIautoMap> selectExpiredApp(AppIautoMap record);
}
