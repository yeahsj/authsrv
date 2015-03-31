package net.suntec.framework.exception;

/**
 * 
 * 
 * @项目名称: OauthSrv
 * @功能描述: 参数检验异常
 * @当前版本： 1.0
 * @创建时间: 2014年12月4日 下午5:46:21
 * @author: <a href="mailto:yeahsj@yahoo.com.cn">yeahsj</a>
 * @修改历史:
 */
public class ASActionCheckException extends ASBaseException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ASActionCheckException() {
		super();
	}

	public ASActionCheckException(String message) {
		super(message);
	}

	public ASActionCheckException(String message, Integer errCode) {
		super(message, errCode);
	}

	public ASActionCheckException(String message, Throwable cause) {
		super(message, cause);
	}

	public ASActionCheckException(Throwable cause) {
		super(cause);
	}

	public ASActionCheckException(Throwable cause, Integer errCode) {
		super(cause, errCode);
	}
}
