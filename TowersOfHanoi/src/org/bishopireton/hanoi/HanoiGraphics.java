/**
 * 
 */
package org.bishopireton.hanoi;

import java.awt.BorderLayout;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.EmptyStackException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingWorker;
import javax.swing.Timer;

import org.bishopireton.gui.Components;
import org.bishopireton.gui.MyFrame;
/**
 * displays the Towers of Hanoi Graphics and allows user to choose 
 * starting spindle and number of disks
 * 
 * @author Mrs. Kelly
 */

public class HanoiGraphics extends JPanel {

	/**
	 * default version id
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * how much the disk should move up/down when animated
	 */
	private static final int DELTA_Y = 25;
	/**
	 * used as indices into the Spindles array
	 */
	private static final int FROM = 0, TO = 1, SPARE = 2; 
	/**
	 * the game defaults to this specific number of disks
	 */
	public final static int DEFAULT_NUMBER_OF_DISKS = 4;
	/**
	 * when the user chooses to animate, there is a timer which uses this
	 * for its timer delay
	 */

	private final static int ANIMATION_TIME = 100;
	/**
	 * when animation is running, the program needs to wait to 
	 * display the new location for the disk that is moving. 
	 * This is how long the program pauses (in milliseconds)
	 */
	private final static int PAUSE_TIME = 2500;
	/**
	 * the three spindles for the game
	 */
	private Spindle[] spindles;
	/**
	 * the JPanel with all of the components for the panel
	 */
	private Footer footer;
	/**
	 * when solving hanoi it is important to not allow the user to
	 * move disks around. inAction is true when hanoi is being solved
	 * 
	 */
	private boolean inAction;
	
	/**
	 * a reference to the methods that run the game
	 */
	private Hanoi hanoi; 

	/**
	 * instantiates the GUI for the Towers game 
	 */
	public HanoiGraphics(Hanoi hanoi) {
		this.hanoi = hanoi;
		inAction = false;
		createSpindles(DEFAULT_NUMBER_OF_DISKS);
		MyFrame frame = setupFrame();
		frame.setVisible(true);

	}
	
	/**
	 * allows the hanoi class to setInAction to false when the animation is complete
	 * 
	 * @param inAction whether the animation is running or not
	 */

	public void setInAction(boolean inAction) {
		this.inAction = inAction;
	}



	/**
	 * accesses the radio buttons in the footer to get which spindle is which
	 * (From/To). Creates an array of these indices
	 * @return an array of the spindles in from, to, spare order
	 */
	private int[] getSpindles() {
		int[] sp = new int[3];
		try {
			sp[FROM] = Integer.parseInt(footer.getFrom().getSelection().getActionCommand());
			sp[TO] = Integer.parseInt(footer.getTo().getSelection().getActionCommand());	
			sp[SPARE] = unselectedSpindle(sp[FROM], sp[TO]);
		} catch (IllegalSpindleSelectedException exc) {
			JOptionPane.showMessageDialog(null, "You selected the same spindle for from and to", 
					"Spindle Error", JOptionPane.ERROR_MESSAGE);
			return null;
		} catch (NullPointerException exc) {
			return new int[] {0, 2, 1}; // using default of from = 1, to = 2, spare = 3
		}
		return sp;
	}

	/**
	 * takes in the number of desired disks and creates the three spindles
	 * Uses the footer radio buttons to determine which spindle to put the 
	 * disks on. Returns the array of from, to, spare spindle numbers in case
	 * the calling method needs it
	 * 
	 * if the user has not yet selected the from/to spindles, from is the 1st spindle and
	 * to is the 3rd spindle
	 * 
	 * @param disks the number of disks that should be on the from spindle
	 * @return the array of spindles in from, to, spare order
	 */
	private int[] createSpindles(int disks) {

		int[] sp = getSpindles();
		if (sp == null) {
			sp = new int[] {0, 2, 1}; // make the default spindles from=1, to=3, spare= 2


		}
		if (spindles == null)
			spindles = new Spindle[3];

		spindles[sp[0]] = new Spindle(sp[0], disks); // from
		spindles[sp[1]] = new Spindle(sp[1], 0);	// to
		spindles[sp[2]] = new Spindle(sp[2], 0);	// other (spare)
		return sp;
	}

	/**
	 * sets up the JFrame for the game to include creating the footer
	 * with its components
	 * @return the JFrame completely set up, but not yet visible
	 */
	private MyFrame setupFrame() {
		MyFrame frame = new MyFrame("Towers of Hanoi");
		frame.setLocation(400, 200);
		frame.add(this);
		footer = createFooter();
		frame.add(footer, BorderLayout.PAGE_END);
		frame.setSize(930, 580);

		return frame;
	}

/**
	 * creates a JPanel Footer with required components.
	 * The information for the buttons complete with action listeners is
	 * found in the info[] array
	 * @return JPanel with the required footer components
	 */

	private Footer createFooter() {
		// MOVE BUTTON
		ButtonInfo[] info = new ButtonInfo[2];
		info[0] = new ButtonInfo("Move Disk", new ActionListener() {

			/**
			 * Determines which spindle the user wants to move a disk
			 * from and to.
			 * 
			 * @param exc Event information, unused
			 */

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					if (inAction) return;
					int sp[] = getSpindles();

					moveDisk(sp[0], sp[1]);
				} catch (IllegalDiskMoveException exc) {
					JOptionPane.showMessageDialog(null, 
							"Trying to move a large disk on top of a smaller disk", 
							"Disk Error", JOptionPane.ERROR_MESSAGE);
				} catch (NullPointerException exc) {
					// should only happen if the getSpindles errors and the user receives an error there
				}
			}
		});

		// SOLVE BUTTON
		info[1] = new ButtonInfo("Solve", new ActionListener() {

			/**
			 * Determines which spindle the user wants to move the stack of disks
			 * from and to. Calls the unused spindle "spare"
			 * 
			 * Creates the specified number of disks on the from spindle before starting
			 * 
			 * @param e Event information, used to get the specific JPutton clicked
			 * 
			 * 
			 */

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					if (inAction) return;
					inAction = true;
					// recreate the spindles and disks before  starting.
					int sp[] = createSpindles(footer.getNumDisks());
					repaint();
					
					int from = sp[FROM], to = sp[TO], spare = sp[SPARE];

					hanoi.createSwingWorker(from, to, spare, footer.getNumDisks(), (JButton) e.getSource());
				} catch (NullPointerException exc) {
					inAction = false;
					// user should have already received a message
				}
			}
		});

		// determines when the cursor leaves the textfield that holds the
		// number of disk information. When the focus moves out of that textfield
		// The spindles are recreated with the number of disks on the appropriate
		// spindle

		FocusListener fl = new FocusListener() {

			@Override
			public void focusGained(FocusEvent e) {}

			@Override
			public void focusLost(FocusEvent e) {
				createSpindles(footer.getNumDisks());
				repaint();
			}
		};


		return  new Footer(info, fl);
	}



	/**
	 * Given the spindles selected for from/to returns the other spindle index
	 * @param from index of the spindle selected as from
	 * @param to index of the spindle selected as to
	 * @return the other index
	 * @throws IllegalSpindleSelectedException if the from and to spindles are the same
	 */
	private int unselectedSpindle(int from, int to) throws IllegalSpindleSelectedException {
		switch (from) {
		case 0:
			switch (to) {
			case 0 : throw new IllegalSpindleSelectedException(); 
			case 1 : return 2;
			case 2 : return 1;
			}
			break;
		case 1:
			switch (to) {
			case 0 : return 2;
			case 1 : throw new IllegalSpindleSelectedException();
			case 2 : return 0;
			}
			break;
		case 2: 
			switch (to) {
			case 0 : return 1;
			case 1 : return 0;
			case 2 : throw new IllegalSpindleSelectedException();
			}
		}
		throw new IllegalSpindleSelectedException();
	}

	/**
	 * Paints the background, draws the spindles and then the table
	 * The spindles are responsible for drawing themselves
	 * 
	 * @param g the graphics environment
	 */
	public void paintComponent(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(0,  0,  getWidth(),  getHeight());
		for (Spindle spindle : spindles)
			spindle.draw(g);
		drawTable(g);
	}

	/**
	 * draws the table under the spindles
	 * @param g graphics environment
	 */
	private void drawTable(Graphics g) {
		g.setColor(new Color(178, 137, 101));
		Point p = spindles[0].getLocation();
		int y = p.y + Spindle.HEIGHT;
		g.fillRect(0, y, getWidth(), 20);
	}

	/**
	 * ensures that the from disk is not bigger than the two disk
	 * @param diskFrom disk being moved
	 * @param diskTo disk being covered
	 * @return true of the moving disk is smaller than the covered disk or if there 
	 * 			are no disks on the spindle
	 */
	private boolean legalMove(Disk diskFrom, Disk diskTo) {
		return diskFrom.getWidth() <= diskTo.getWidth();
	}

	/**
	 * moves a disk from one spindle to another
	 * @param spindleFrom the spindle the disk should move from
	 * @param spindleTo the spindle the disk should move to
	 * @return boolean if the move works
	 * @throws IllegalDiskMoveException if the move is not legal or there are 
	 *                                  no disks on the from spindle
	 */
	public boolean moveDisk(int spindleFrom, int spindleTo) throws IllegalDiskMoveException {
		//	System.out.printf("Actual Move: %d to %d\n",  spindleFrom, spindleTo);
		Disk diskFrom, diskTo = null;
		try {
			diskFrom = spindles[spindleFrom].peek();
			if (! spindles[spindleTo].isEmpty())
				diskTo = spindles[spindleTo].peek();
			if (diskTo != null && (! legalMove(diskFrom, diskTo))) {
				throw new IllegalDiskMoveException();
			}
		} catch (EmptyStackException e) {
			JOptionPane.showMessageDialog(this, "There are no disks on the from spindle", 
					"Spindle Error", JOptionPane.ERROR_MESSAGE);
			throw new IllegalDiskMoveException("There are no disks on the from spindle");
		}

		if (footer.getAnimate()) {
			// Get the from point and the to point for each disk
			Point fromLoc = spindles[spindleFrom].getTopDiskLocation();
			Point toLoc = spindles[spindleTo].getTopDiskLocation();

			animateMoveUp(diskFrom, fromLoc, toLoc);

			// only sleep if completing hanoi, not if just a simple move
			if (inAction) { 
				try {
					Thread.sleep(PAUSE_TIME);

				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		// this removes the disk from one spindle and puts it on the other
		spindles[spindleTo].push(spindles[spindleFrom].pop());

		// this helps keep track of how many disks are on what spindle
		// which is required for the graphics to draw properly

		spindles[spindleTo].increment();
		spindles[spindleFrom].decrement();

		// if the action is not animated, the screen must be repainted
		// if it is being animated, it is repainted during the animation

		if (! footer.getAnimate()) {
			repaint();
		}
		return true;
	}

	/**
	 * moves the disk straight up then calls animateMoveOver
	 * uses the timer delay ANIMATION_TIME
	 * 
	 * @param disk the disk to move 
	 * @param from the point that it will move from
	 * @param to the point that it will move to
	 */
	
	private void animateMoveUp(Disk disk, Point from, Point to) {
		
			Timer t = new Timer(ANIMATION_TIME, new ActionListener() {
			
			int y = from.y;
			Disk blankDisk = new Disk(disk.getWidth(), Components.backgroundColor);
			int blankY = y;

			@Override
			public void actionPerformed(ActionEvent e) {
				y -= DELTA_Y;
					// allows you to draw on the JPanel without erasing what is already there
				Graphics g = getGraphics(); 

				blankDisk.draw(g, from.x, blankY); // erases the disk as it "moves"
				blankY = y;
				disk.draw(g, from.x,  y); // draw the disk as it moves (not filled)

				// continue moving until the disk is slightly above the spindle
				// then stop and call the animateMoveOver after creating a new
				// from point with the actual point where the disk stopped moving up
				if (y < Spindle.TOP - 50) {
					((Timer)e.getSource()).stop();
					animateMoveOver(disk, new Point(from.x, y), to);
				}
			}

		});
		t.start();
	}

	/**
	 * @param disk the disk to move 
	 * @param from the point that it will move from
	 * @param to the point that it will move to
	 */
	private void animateMoveOver(Disk disk, Point from, Point to) {
		boolean right = to.x > from.x; // are we moving left or right

		Timer t = new Timer(ANIMATION_TIME, new ActionListener() {

			int deltaX = right ? 2*DELTA_Y : -2*DELTA_Y; // move right or left

			int x = from.x;
			int y = from.y;

			// allows the disk to be erased as it is redrawn
			Disk blankDisk = new Disk(disk.getWidth(), Components.backgroundColor);
			int blankX = x;

			@Override
			public void actionPerformed(ActionEvent e) {
				x += deltaX;
				Graphics g = getGraphics();
				blankDisk.draw(g, blankX, y);
				blankX = x;
				disk.draw(g, x,  y);
				// stop when the disk is over the correct spindle
				if ((right && (x > to.x - disk.getWidth()/4)) || 
						((! right) && x < to.x + disk.getWidth()/4)) {
					((Timer)e.getSource()).stop();
					// call the method to move the disk down
					animateMoveDown(disk, new Point(x, y), to);
				}
			}
		})	;
		t.start();
	}

	/**
	 * @param disk the disk to move 
	 * @param from the point that it will move from
	 * @param to the point that it will move to
	 */
	private void animateMoveDown(Disk disk, Point from, Point to) {

		Timer t = new Timer(ANIMATION_TIME, new ActionListener() {

			int y = from.y;
			Disk blankDisk = new Disk(disk.getWidth(), Components.backgroundColor);
			int blankY = y;

			@Override
			public void actionPerformed(ActionEvent e) {
				y += DELTA_Y;
				Graphics g = getGraphics();
				blankDisk.draw(g, from.x, blankY);
				blankY = y;
				disk.draw(g, from.x,  y);
				if (y >= to.y - Disk.HEIGHT*2) {
					((Timer)e.getSource()).stop();
					repaint();
				}
			}
		})	;
		t.start();
	}

}
