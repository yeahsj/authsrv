package net.suntec.framework.exception;

/**
 * 
 * 
 * @项目名称: OauthSrv
 * @功能描述: 业务类异常
 * @当前版本： 1.0
 * @创建时间: 2014年12月4日 下午5:46:44
 * @author: <a href="mailto:yeahsj@yahoo.com.cn">yeahsj</a>
 * @修改历史:
 */
public class ASServiceException extends ASBaseException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ASServiceException() {
		super();
	}

	public ASServiceException(String message) {
		super(message);
	}

	public ASServiceException(String message, Integer errCode) {
		super(message, errCode);
	}

	public ASServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public ASServiceException(Throwable cause) {
		super(cause);
	}

	public ASServiceException(Throwable cause, Integer errCode) {
		super(cause, errCode);
	}

}
