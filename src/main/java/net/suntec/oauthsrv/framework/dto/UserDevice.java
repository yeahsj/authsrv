package net.suntec.oauthsrv.framework.dto;

/**
 * 
 * @项目名称: OauthSrv
 * @功能描述:
 * @当前版本： 1.0
 * @创建时间: 2014-5-30 下午12:32:01
 * @author: <a href="mailto:yeahsj@gmail.com">yeahsj</a>
 * @修改历史:
 */
public class UserDevice {
	long userId;
	long deviceId;
	String deviceNo;
	long platformId;
	String deviceAlias;
	String bandingStatus;
	String bandingStatusValue;
	String productId;

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public long getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(long deviceId) {
		this.deviceId = deviceId;
	}

	public String getDeviceNo() {
		return deviceNo;
	}

	public void setDeviceNo(String deviceNo) {
		this.deviceNo = deviceNo;
	}

	public long getPlatformId() {
		return platformId;
	}

	public void setPlatformId(long platformId) {
		this.platformId = platformId;
	}

	public String getDeviceAlias() {
		return deviceAlias;
	}

	public void setDeviceAlias(String deviceAlias) {
		this.deviceAlias = deviceAlias;
	}

	public String getBandingStatus() {
		return bandingStatus;
	}

	public void setBandingStatus(String bandingStatus) {
		this.bandingStatus = bandingStatus;
	}

	public String getBandingStatusValue() {
		return bandingStatusValue;
	}

	public void setBandingStatusValue(String bandingStatusValue) {
		this.bandingStatusValue = bandingStatusValue;
	}

}
