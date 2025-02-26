/**
 * 
 */
package org.bishopireton.hanoi;

import javax.swing.JButton;
import javax.swing.SwingWorker;

/**
 * @author Mrs. Kelly
 */
public class Hanoi {

	private HanoiGraphics hanoiGraphics;
	/**
	 * Instantiates the game
	 */
	public Hanoi() {

		hanoiGraphics = new HanoiGraphics(this);
	}


	// TODO Fill in these java docs

	/**
	 * creates a swing worker to allow the graphics to display while the  
	 * hanoi method runs in the background
	 * 
	 */
	public void  createSwingWorker(int from, int to, int spare, int numDisks, JButton button) {
		SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {

			@Override
			protected Void doInBackground() throws Exception {
				// TODO UPDATE this call to include only the parameters that you need
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

	// TODO Fill in these java docs

	/**
	 * a recursive method that moves a tower of disks to the spare spindle
	 * then moves the remaining disk to the to spindle, imitating the
	 * towers of hanoi challenge
	 *
	 * @throws IllegalDiskMoveException 
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
