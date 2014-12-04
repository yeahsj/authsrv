package net.suntec.oauthsrv.dao;

import java.util.List;

import net.suntec.framework.PioneerDAO;
import net.suntec.oauthsrv.dto.AppDeviceDistribute;

/**
 * 
 * @项目名称: oauthsrv
 * @功能描述: oauth srv架构
 * @修改历史: 1.0
 * @当前版本： 1.0
 * @创建时间: 2014-09-05 16:54:36
 * @author: <a href="mailto:yeahsj@yahoo.com.cn">sang jun</a>
 */
public interface AppDeviceDistributeMapper extends PioneerDAO<AppDeviceDistribute> {
	public List<AppDeviceDistribute> selectClientIdByDeviceNo(AppDeviceDistribute param);
}
