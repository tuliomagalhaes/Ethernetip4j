package se.opendataexchange.ethernetip4j.exceptions;

public class ItemNotFoundException extends Exception{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 836415960111867971L;

	/**
	 * 
	 */
	public ItemNotFoundException() {
		super();
	}

	/**
	 * @param arg0
	 */
	public ItemNotFoundException(String arg0) {
		super(arg0);
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public ItemNotFoundException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	/**
	 * @param arg0
	 */
	public ItemNotFoundException(Throwable arg0) {
		super(arg0);
	}
}
