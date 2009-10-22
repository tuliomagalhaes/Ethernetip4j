package se.opendataexchange.ethernetip4j.exceptions;

public class InsufficientCommandException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = -715378980384695918L;
	
	/**
	 * 
	 */
	public InsufficientCommandException() {
		super();
	}

	/**
	 * @param arg0
	 */
	public InsufficientCommandException(String arg0) {
		super(arg0);
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public InsufficientCommandException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	/**
	 * @param arg0
	 */
	public InsufficientCommandException(Throwable arg0) {
		super(arg0);
	}

}
