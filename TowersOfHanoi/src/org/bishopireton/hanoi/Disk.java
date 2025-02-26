/**
 * 
 */
package org.bishopireton.hanoi;

import java.awt.Color;
import java.awt.Graphics;
import org.bishopireton.gui.Colors;
/**
 * represents a disk for the towers of hanoi
 * @author Mrs. Kelly
 */
public class Disk {

	/**
	 * standard disk height
	 */
	public static final int HEIGHT = 10;
	/**
	 * diameter of the first bottom disk
	 */
	public static final int FIRST_DIAMETER = 80;
	/**
	 * how much each successive disk's size should change
	 */
	public static final int DIAMETER_CHANGE = 20;
	
	/**
	 * size of the disk
	 */
	private int width;
	/**
	 * color of the disk
	 */
	private Color color;
	
	/**
	 * using where the disk is created on the spindle, calculates
	 * its size then assigns it a random color
	 * @param d which number the disk is on the spindle
	 */
	public Disk (int d) {
		width = FIRST_DIAMETER + d * DIAMETER_CHANGE;
		color = Colors.getRandomColor();			
	}
	
	
	/**
	 * creates a specific disk
	 * @param width disk size
	 * @param color disk color
	 */
	public Disk(int width, Color color) {
		this.width = width;
		this.color = color;
	}

	/**
	 * standard getter
	 * @return the width
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * fills the rectangle for the disk
	 * @param g Graphics component
	 * @param x location of top left corner
	 * @param y location of top left corner
	 */
	public void fill(Graphics g, int x, int y) {
		g.setColor(color);
		g.fillRect(x - width/2 + Spindle.WIDTH/2, y, width,  HEIGHT);
	}
	
	/**
	 * outlines the rectangle for the disk
	 * @param g Graphics component
	 * @param x location of top left corner
	 * @param y location of top left corner
	 */
	public void draw(Graphics g, int x, int y) {
		g.setColor(color);
		g.drawRect(x - width/2 + Spindle.WIDTH/2, y, width,  HEIGHT);
	}
	
}
