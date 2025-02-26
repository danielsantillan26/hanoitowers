package org.bishopireton.hanoi;

import java.awt.event.ActionListener;

/**
 * stores the information to create a button including 
 * its name and action listener
 * @author Mrs. Kelly
 */
public class ButtonInfo {

	/**
	 * text that will display on the button
	 */
	private String name;
	/**
	 * action taken when the button is clicked
	 */
	private ActionListener action;

	/**
	 * stores the button information
	 * @param name text that will be displayed on the button
	 * @param action action that will be taken when the button is clicked
	 */
	public ButtonInfo(String name, ActionListener action) {
		super();
		this.name = name;
		this.action = action;
	}

	/**
	 * standard getter
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * standard getter
	 * @return the action
	 */
	public ActionListener getAction() {
		return action;
	}
}
