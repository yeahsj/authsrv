package net.suntec.oauthsrv.action.piece;

public class CallbackCacheResult {
	//最后的转向路径
	String backurl ;
	//错误信息
	String errMsg;
	
	String errUrl;
	
	int errCode;
	//缓存发生的时间
	long cacheTime;
	
	String fromPhone;
	
	public String getFromPhone() {
		return fromPhone;
	}
	public void setFromPhone(String fromPhone) {
		this.fromPhone = fromPhone;
	}
	public String getErrUrl() {
		return errUrl;
	}
	public void setErrUrl(String errUrl) {
		this.errUrl = errUrl;
	}
	public String getBackurl() {
		return backurl;
	}
	public void setBackurl(String backurl) {
		this.backurl = backurl;
	}
	public String getErrMsg() {
		return errMsg;
	}
	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}
	public long getCacheTime() {
		return cacheTime;
	}
	public void setCacheTime(long cacheTime) {
		this.cacheTime = cacheTime;
	}
	public int getErrCode() {
		return errCode;
	}
	public void setErrCode(int errCode) {
		this.errCode = errCode;
	}
}
