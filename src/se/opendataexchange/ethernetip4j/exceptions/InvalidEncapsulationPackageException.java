/**
 * 
 */
package se.opendataexchange.ethernetip4j.exceptions;

/**
 * @author Mattias
 *
 */
public class InvalidEncapsulationPackageException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 56971699489447983L;

	/**
	 * 
	 */
	public InvalidEncapsulationPackageException() {
		super();
	}

	/**
	 * @param arg0
	 */
	public InvalidEncapsulationPackageException(String arg0) {
		super(arg0);
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public InvalidEncapsulationPackageException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	/**
	 * @param arg0
	 */
	public InvalidEncapsulationPackageException(Throwable arg0) {
		super(arg0);
	}

}
