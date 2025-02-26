/**
 * 
 */
package org.bishopireton.hanoi;

import javax.swing.JButton;
import javax.swing.SwingWorker;

/**
 * The Hanoi class is the class that allows the Towers of Hanoi game to run.
 * 
 * @author Mrs. Kelly
 */
public class Hanoi {
	
	/**
	 * Object for the graphics of the game, used to move disks and lod the game
	 */
	private HanoiGraphics hanoiGraphics;
	/**
	 * Instantiates the game
	 */
	public Hanoi() {

		hanoiGraphics = new HanoiGraphics(this);
	}

	
	/**
	 * creates a swing worker to allow the graphics to display while the  
	 * hanoi method runs in the background
	 * 
	 * @param from			the spindle where all disks are originally at
	 * @param to			the target spindle where all disks should go
	 * @param spare			the spare spindle
	 * @param numDisks		the number of disks
	 * @param button		the JButton that calls this method
	 */
	public void  createSwingWorker(int from, int to, int spare, int numDisks, JButton button) {
		SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {

			@Override
			protected Void doInBackground() throws Exception {
				hanoi(from, to, spare, numDisks);
				return null;
			}

			@Override
			protected void done() {
				hanoiGraphics.setInAction(false);
				button.setEnabled(true);
			}
		};
		button.setEnabled(false);
		worker.execute();
	}



	/**
	 * a recursive method that moves a tower of disks to the spare spindle
	 * then moves the remaining disk to the to spindle, imitating the
	 * towers of hanoi challenge - uses a base case for an even and odd
	 * number of disks
	 *
	 * @param from			the spindle where all disks are originally at
	 * @param to			the target spindle where all disks should go
	 * @param spare			the spare spindle
	 * @param numDisks		the number of disks
	 * @throws IllegalDiskMoveException  ensures no illegal moves are made
	 */
	private void hanoi(int from, int to, int spare, int numDisks) throws IllegalDiskMoveException {
		if (numDisks == 1) {
			hanoiGraphics.moveDisk(from, to);
		}

		if (numDisks == 2) {
			hanoiGraphics.moveDisk(from, spare);
			hanoiGraphics.moveDisk(from, to);
			hanoiGraphics.moveDisk(spare, to);
		}

		else {
			hanoi(from, spare, to, numDisks - 1);
			hanoiGraphics.moveDisk(from, to);
			hanoi(spare, to, from, numDisks - 1);
		}


	}

	/**
	 * The main program for the Towers of Hanoi animation
	 * @param args unused
	 */
	public static void main(String[] args) {

		new Hanoi();
	}

}
