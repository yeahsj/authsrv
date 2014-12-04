package net.suntec.framework.exception;

/**
 * 
 * 
 * @项目名称: OauthSrv
 * @功能描述: iauto异常
 * @当前版本： 1.0
 * @创建时间: 2014年12月4日 下午5:46:09
 * @author: <a href="mailto:yeahsj@yahoo.com.cn">yeahsj</a>
 * @修改历史:
 */
public class ASIautoException extends ASBaseException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ASIautoException() {
		super();
	}

	public ASIautoException(String message) {
		super(message);
	}

	public ASIautoException(String message, Integer errCode) {
		super(message, errCode);
	}

	public ASIautoException(String message, Throwable cause) {
		super(message, cause);
	}

	public ASIautoException(Throwable cause) {
		super(cause);
	}

	public ASIautoException(Throwable cause, Integer errCode) {
		super(cause, errCode);
	}
}
