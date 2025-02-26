/**
 * 
 */
package org.bishopireton.hanoi;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.FocusListener;

import org.bishopireton.gui.Components;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

/**
 * A JPanel for the Towers of Hanoi animation. Creates all of 
 * the components required for the game
 */
public class Footer extends JPanel {

	/**
	 * default serial id
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * the footer has several JPanels on it and this allows them
	 * to be identically sized
	 */
	private static Dimension panelSize = new Dimension(80, 100);

	/**
	 * used to create the Radio Buttons for the spindle selections
	 */
	private final static String[] options = {"1", "2", "3"};
	/**
	 * allows the radio buttons to be grouped and to determine which
	 * radio button is selected
	 */
	private ButtonGroup from = new ButtonGroup(), to= new ButtonGroup();
	/**
	 * allows user to specify whether the movements should be animated
	 */
	private JCheckBox animate;
	/**
	 * allows the user to specify the number of disks to start with
	 */
	private JTextField numDisks;


	/**
	 * brings in the button information and the focus listener from the 
	 * frame, allowing that code to be specified there
	 * 
	 * creates all of the components needed for the user to modify the 
	 * animate of the towers of hanoi
	 * 
	 * @param information information for the buttons including action listeners
	 * @param focusListener focus listener for the text field of the number of disks
	 */
	public Footer(ButtonInfo[] information, FocusListener focusListener) {
		setBackground(Color.BLACK);
		setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));


		// setup objects
		JPanel toPanel = createSelection("To", to);
		JPanel fromPanel = createSelection("From", from);
		Components.setSize(fromPanel,  panelSize);
		Components.setSize(toPanel, panelSize);
		JPanel optionsPanel = createOptions(focusListener);

		// Add panels to JPanel
		add(Box.createHorizontalGlue());
		add(optionsPanel);
		add(Box.createRigidArea(new Dimension(30, 100)));
		add(fromPanel);
		add(toPanel);

		// add Buttons to JPanel
		for (ButtonInfo info : information) {
			add(createButton(info));
			add(Box.createRigidArea(new Dimension(30, 100)));
		}
		add(Box.createHorizontalGlue());
	}


	/**
	 * getter for the animation checkbox
	 * @return if the user has chosen to animate
	 */
	public boolean getAnimate() {
		return animate.isSelected();
	}

	/**
	 * getter for the from spindle
	 * @return the from spindle selected
	 */
	public ButtonGroup getFrom() {
		return from;
	}

	/**
	 * getter for the to spindle
	 * @return the to spindle selected
	 */
	public ButtonGroup getTo() {
		return to;
	}
	
	/**
	 * getter for number of disks
	 * @return the number of disks the user entered as an integer
	 */
	public int getNumDisks() {
		try {
		return Integer.parseInt(numDisks.getText());
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(null, "Invalid value for number of disks", "Number of Disks", JOptionPane.ERROR_MESSAGE);
			return 0;
		}
		
	}

	/**
	 * creates a JPanel of options including whether the movements should be
	 * animated and how many disks should make up the tower
	 * 
	 * @param focusListener
	 * @return the panel of options 
	 */
	private JPanel createOptions(FocusListener focusListener) {
	
		// create the components and set their properties
		animate = Components.createCheckBox("Animate",  false);
		JLabel label = Components.createLabel("Disks");
		numDisks = Components.createTextField(5, ""+HanoiGraphics.DEFAULT_NUMBER_OF_DISKS);
		numDisks.setMaximumSize(numDisks.getPreferredSize());
		numDisks.setHorizontalAlignment(JTextField.CENTER);
		numDisks.addFocusListener(focusListener);
		
		// create the field panel with the label and text field
		JPanel fieldPanel = Components.createPanel();
		fieldPanel.setLayout(new BoxLayout(fieldPanel, BoxLayout.LINE_AXIS));
		
		fieldPanel.add(Box.createVerticalGlue());
		fieldPanel.add(label);
		fieldPanel.add(Box.createRigidArea(new Dimension(10,0)));
		fieldPanel.add(numDisks);
		fieldPanel.add(Box.createVerticalGlue());
		
		// create the full options panel and add the components to it
		JPanel panel = Components.createPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

		panel.add(animate);
		panel.add(fieldPanel);

		// set the properties for the panel
		Dimension panelSize = new Dimension(120, 100);
		Components.setSize(panel, panelSize);

		return panel;
	}


	/**
	 * creates a button from the provided button info, which includes
	 * the name and the action listener
	 * @param info specific button information
	 * @return the resulting JButton
	 */
	private JButton createButton(ButtonInfo info) {
		JButton button = new JButton(info.getName());
		button.addActionListener(info.getAction());
		return button;
	}

	/**
	 * creates the JRadio Buttons and ButtonGroup for the spindles
	 * @param label specifies either from or to
	 * @param group the appropriate button group to add the radio buttons to00
	 * @return the JPanel with the label and the radio buttons
	 */
	private JPanel createSelection(String label, ButtonGroup group) {
		JPanel panel = Components.createPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		panel.add(Components.createLabel(label));
		for (String s : options) {
			JRadioButton button = Components.createRadioButton(s);
			button.setActionCommand(Integer.parseInt(button.getText())-1+""); // spindle numbers are actually 0, 1, 2
			// Select the correct radio buttons (from first spindle to third spindle)
			if (label.equals("From") && s.equals("1"))
				button.setSelected(true);
			else if (label.equals("To") && s.equals("3"))
				button.setSelected(true);
			
			group.add(button);
			panel.add(button);
		}
		return panel;

	}

}
