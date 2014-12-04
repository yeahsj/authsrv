package net.suntec.framework.dto;

import java.util.List;

/**
 * 
 * 
 * @项目名称: OauthSrv
 * @功能描述: 支持Spring MVC的 Json 返回格式,用于返回列表
 * @当前版本： 1.0
 * @创建时间: 2014-6-12 下午04:47:09
 * @author: <a href="mailto:yeahsj@gmail.com">yeahsj</a>
 * @修改历史:
 */
public class SpringQueryJsonResult<T> extends SpringJsonResult<T> {

	public String getLastUpdateTime() {
		return lastUpdateTime;
	}

	public List<T> getLists() {
		return lists;
	}

	public int getCode() {
		return code;
	}

}
