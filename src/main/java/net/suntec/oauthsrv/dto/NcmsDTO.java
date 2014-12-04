package net.suntec.oauthsrv.dto;

/**
 * 
 * 
 * @项目名称: OauthSrv
 * @功能描述: 
 * @当前版本： 1.0
 * @创建时间: 2014年11月25日 上午11:28:33
 * @author: <a href="mailto:yeahsj@yahoo.com.cn">yeahsj</a>
 * @修改历史:
 */
public class NcmsDTO {
	String body;
	String msgId;
	String marker;
	String deviceInfo;

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}

	public String getMarker() {
		return marker;
	}

	public void setMarker(String marker) {
		this.marker = marker;
	}

	public String getDeviceInfo() {
		return deviceInfo;
	}

	public void setDeviceInfo(String deviceInfo) {
		this.deviceInfo = deviceInfo;
	}

}
