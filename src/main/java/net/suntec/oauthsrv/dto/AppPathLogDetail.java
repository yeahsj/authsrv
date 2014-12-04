package net.suntec.oauthsrv.dto;

import java.io.Serializable;

import net.suntec.framework.PioneerDTO;

/**
 * 
 * @项目名称: oauthsrv
 * @功能描述: oauth srv架构
 * @修改历史: 1.0
 * @当前版本： 1.0
 * @创建时间: 2014-08-26 15:04:41
 * @author: <a href="mailto:yeahsj@yahoo.com.cn">sang jun</a>
 */
public class AppPathLogDetail extends PioneerDTO {
	private String path;
	private Long starttime;
	private Long endtime;
	private Integer status;
	private String msg;
	private String params;
	private Long cost;

	private String headers;
	private String remoteAddr;

	public String getHeaders() {
		return headers;
	}

	public void setHeaders(String headers) {
		this.headers = headers;
	}

	public String getRemoteAddr() {
		return remoteAddr;
	}

	public void setRemoteAddr(String remoteAddr) {
		this.remoteAddr = remoteAddr;
	}

	public Long getCost() {
		return cost;
	}

	public void setCost(Long cost) {
		this.cost = cost;
	}

	public String getPath() {
		return this.path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Long getStarttime() {
		return this.starttime;
	}

	public void setStarttime(Long starttime) {
		this.starttime = starttime;
	}

	public Long getEndtime() {
		return this.endtime;
	}

	public void setEndtime(Long endtime) {
		this.endtime = endtime;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getMsg() {
		return this.msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getParams() {
		return this.params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public Serializable getPrimaryKey() {
		return path;
	}

	public void setPrimaryKey(Serializable primaryKey) {
		this.path = (String) primaryKey;
	}
}
