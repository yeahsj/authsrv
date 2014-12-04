package net.suntec.framework.exception;

/**
 * 
 * @项目名称: OauthSrv
 * @功能描述:
 * @当前版本： 1.0
 * @创建时间: 2014-11-7 下午03:42:27
 * @author: <a href="mailto:yeahsj@yahoo.com.cn">yeahsj</a>
 * @修改历史:
 */
public class ASBaseException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String message = null;
	Integer errCode = 0;

	public ASBaseException() {
		super();
	}

	public ASBaseException(String message) {
		super(message);
		this.message = message;
	}

	public ASBaseException(String message, Integer errCode) {
		super(message);
		this.message = message;
		this.errCode = errCode;
	}

	public ASBaseException(String message, Throwable cause) {
		super(message, cause);
	}

	public ASBaseException(Throwable cause) {
		super(cause);
	}

	public ASBaseException(Throwable cause, Integer errCode) {
		super(cause);
		this.errCode = errCode;
	}

	public String getMessage() {
		return message;
	}

	public Integer getErrCode() {
		return errCode;
	}
}
