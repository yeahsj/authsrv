package net.suntec.oauthsrv.service;

import net.suntec.oauthsrv.dao.AppPathLogDetailMapper;
import net.suntec.oauthsrv.dto.AppPathLogDetail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * 
 * @项目名称: OauthSrv
 * @功能描述:
 * @当前版本： 1.0
 * @创建时间: 2014年12月4日 下午5:53:57
 * @author: <a href="mailto:yeahsj@yahoo.com.cn">yeahsj</a>
 * @修改历史:
 */
@Service
public class PathLogService {
	@Autowired
	AppPathLogDetailMapper appPathLogDetailMapper;

	public void saveLog(AppPathLogDetail param) {
		appPathLogDetailMapper.insert(param);
	}
}
