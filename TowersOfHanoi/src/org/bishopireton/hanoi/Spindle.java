/**
 * 
 */
package org.bishopireton.hanoi;

import java.awt.Color;
import org.bishopireton.gui.Colors;
import java.awt.Graphics;
import java.awt.Point;
import java.util.Stack;

/**
 * represents a spindle for the towers of hanoi as a 
 * stack of disks
 * @author Mrs. Kelly
 */
public class Spindle extends Stack<Disk> {

	/**
	 * default version id 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * width of the spindle in pixels
	 */
	public final static int WIDTH = 15;
	/**
	 * maximum number of disks allowed
	 */
	private final static int MAX_DISKS = 10;
	/**
	 * how many pixels between disks
	 */
	private final static int DISK_SPACING = 5;
	/**
	 * how far apart the spindles are drawn
	 */
	private final static int SPINDLE_DISTANCE = 300;
	/**
	 * How tall to draw the spindles based on maximum disks and disk height/spacing
	 */
	public final static int HEIGHT =  MAX_DISKS * (Disk.HEIGHT + DISK_SPACING) + 40;
	/**
	 * where the tops of the spindles should be drawn
	 */
	public final static int TOP = 220;
	
	/**
	 * where this specific spindle is drawn
	 */
	private Point location;
	/**
	 * the color of the spindle
	 */
	private Color color;
	/**
	 * the location of the disk on the top of the spindle
	 */
	private Point topDiskLocation;
	/**
	 * how many disks are on the spindle
	 */
	private int numDisks;
	
	/**
	 * creates a spindle with the correct number of disks 
	 * sets the color to a random color and creates the disks
	 * @param spindle spindle number ensures the spindle is located in the right place
	 *                with the correct number of disks
	 * @param numDisks number of disks the spindle should start with
 	 */
	public Spindle(int spindle, int numDisks) {
		this.numDisks = numDisks;
		color = Colors.getRandomColor();
		createDisks(numDisks);
		location = new Point(spindle * SPINDLE_DISTANCE+150, TOP);
		topDiskLocation = new Point(location.x, location.y + HEIGHT);
	}

	/**
	 * standard getter
	 * @return the location of the spindle
	 */
	public Point getLocation() {
		return location;
	}

	/**
	 * standard getter
	 * @return the location of the top disk on the spindle
	 */
	public Point getTopDiskLocation() {
		return topDiskLocation;
	}

	/**
	 * standard getter
	 * @return the number of disks
	 */
	public int getNumDisks() {
		return numDisks;
	}
	
	/**
	 * adds one to the number of disks
	 */
	public void increment() {
		numDisks++;
	}

	/**
	 * subtracts one from the number of disks
	 */
	public void decrement() {
		numDisks--;
	}
	
	/**
	 * pushes the appropriate number of disks onto the spindle
	 * @param numDisks	number of disks
	 */
	private void createDisks(int numDisks) {
		if (numDisks == 0) return;
		for (int d = numDisks; d > 0; d--) {
			this.push(new Disk(d));
		}
	}

	/**
	 * draws the spindle and the associated disks
	 * @param g Graphics environment
	 */
	public void draw(Graphics g) {
		g.setColor(color);
		g.fillRect(location.x, location.y, WIDTH, HEIGHT);
		drawDisks(g);		
	}

	/**
	 * draws the disks by copying the stack into an array without destroying the stack
	 * 
	 * @param g Graphics environment
	 */
	private void drawDisks(Graphics g) {
		Disk[] ds =  this.toArray(new Disk[0]);
		int x = location.x;
		int y = location.y + HEIGHT - Disk.HEIGHT;
		for (int o = 0; o < ds.length; o++){
			Disk d = (Disk) ds[o];
			d.fill(g, x, y);
			y -= (DISK_SPACING + Disk.HEIGHT);
		}
		y = (ds.length == 0) ? y + Disk.HEIGHT : y + (DISK_SPACING +Disk.HEIGHT); 
		topDiskLocation = new Point(x, y);
	}
	
}
