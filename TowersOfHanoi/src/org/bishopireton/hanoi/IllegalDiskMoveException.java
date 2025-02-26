package org.bishopireton.hanoi;

/**
 * An exception to indicate if a disk cannot be properly moved
 * @author Mrs. Kelly
 */
public class IllegalDiskMoveException extends Exception {

	/**
	 * version 1
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * construct the illegal disk move exception
	 */
	public IllegalDiskMoveException() {
		this("Illegal Disk Exception");
	}
	

	/**
	 * construct the illegal disk move exception
	 * @param message message that can be retrieved when error is caught
	 */
	public IllegalDiskMoveException(String message) {
		super(message);
	}
}
