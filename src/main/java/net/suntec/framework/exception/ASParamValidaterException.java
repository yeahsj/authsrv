package net.suntec.framework.exception;

public class ASParamValidaterException extends ASBaseException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ASParamValidaterException() {
		super();
	}

	public ASParamValidaterException(String message) {
		super(message);
	}

	public ASParamValidaterException(String message, Integer errCode) {
		super(message, errCode);
	}

	public ASParamValidaterException(String message, Throwable cause) {
		super(message, cause);
	}

	public ASParamValidaterException(Throwable cause) {
		super(cause);
	}

	public ASParamValidaterException(Throwable cause, Integer errCode) {
		super(cause, errCode);
	}
}
