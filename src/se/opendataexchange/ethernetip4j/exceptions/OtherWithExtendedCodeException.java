package se.opendataexchange.ethernetip4j.exceptions;

public class OtherWithExtendedCodeException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 613337229968392859L;

	/**
	 * 
	 */
	public OtherWithExtendedCodeException() {
		super();
	}

	/**
	 * @param arg0
	 */
	public OtherWithExtendedCodeException(String arg0) {
		super(arg0);
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public OtherWithExtendedCodeException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	/**
	 * @param arg0
	 */
	public OtherWithExtendedCodeException(Throwable arg0) {
		super(arg0);
	}
}
