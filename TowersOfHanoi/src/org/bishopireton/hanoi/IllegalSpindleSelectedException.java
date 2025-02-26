package org.bishopireton.hanoi;

/**
 * An exception to indicate if spindles were improperly selected
 * @author Mrs. Kelly
 */
public class IllegalSpindleSelectedException extends Exception {

	/**
	 * version id
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * construct the illegal spindle selected exception
	 */
	public IllegalSpindleSelectedException() {
		this("Illegal Spindle Selected Exception");
	}
	
	/**
	 * construct the illegal spindle selected exception
	 * @param message message that can be retrieved when error is caught
	 */
	public IllegalSpindleSelectedException(String message) {
		super(message);
	}

}
