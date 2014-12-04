package net.suntec.framework.exception;

/**
 * 
 * 
 * @项目名称: OauthSrv
 * @功能描述: 登录iauto错误
 * @当前版本： 1.0
 * @创建时间: 2014-11-7 下午03:46:00
 * @author: <a href="mailto:yeahsj@yahoo.com.cn">yeahsj</a>
 * @修改历史:
 */
public class ASIautoLoginException extends ASIautoException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ASIautoLoginException() {
		super();
	}

	public ASIautoLoginException(String message) {
		super(message);
	}

	public ASIautoLoginException(String message, Integer errCode) {
		super(message, errCode);
	}

	public ASIautoLoginException(String message, Throwable cause) {
		super(message, cause);
	}

	public ASIautoLoginException(Throwable cause) {
		super(cause);
	}

	public ASIautoLoginException(Throwable cause, Integer errCode) {
		super(cause, errCode);
	}
}
