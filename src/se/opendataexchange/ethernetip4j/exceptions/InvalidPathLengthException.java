package se.opendataexchange.ethernetip4j.exceptions;

public class InvalidPathLengthException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3022970314417199848L;

	/**
	 * 
	 */
	public InvalidPathLengthException() {
		super();
	}

	/**
	 * @param arg0
	 */
	public InvalidPathLengthException(String arg0) {
		super(arg0);
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public InvalidPathLengthException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	/**
	 * @param arg0
	 */
	public InvalidPathLengthException(Throwable arg0) {
		super(arg0);
	}
}
