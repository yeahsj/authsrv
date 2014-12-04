package net.suntec.framework.exception;

/**
 * 
 * 
 * @项目名称: OauthSrv
 * @功能描述: QPID操作异常
 * @当前版本： 1.0
 * @创建时间: 2014年12月4日 下午5:46:34
 * @author: <a href="mailto:yeahsj@yahoo.com.cn">yeahsj</a>
 * @修改历史:
 */
public class ASQpidException extends ASBaseException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ASQpidException() {
		super();
	}

	public ASQpidException(String message) {
		super(message);
	}

	public ASQpidException(String message, Integer errCode) {
		super(message, errCode);
	}

	public ASQpidException(String message, Throwable cause) {
		super(message, cause);
	}

	public ASQpidException(Throwable cause) {
		super(cause);
	}

	public ASQpidException(Throwable cause, Integer errCode) {
		super(cause, errCode);
	}
}
