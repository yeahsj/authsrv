package net.suntec.framework.exception;

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
