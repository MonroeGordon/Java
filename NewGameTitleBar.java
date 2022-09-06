package com.chess.gui.bar;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.chess.Chess;
import com.chess.Chess.BoardOption;
import com.chess.gui.button.CloseButton;
import com.chess.gui.dialog.NewGameDialog;
import com.chess.gui.frame.ChessFrame;

/**
 * NewGameTitleBar creates and controls the title bar of the new game dialog window, including the close button.
 * @author Monroe Gordon
 * @since 6/7/2022
 */
public class NewGameTitleBar extends JPanel implements ActionListener {

	/** ChessTitleBar serial version ID value. */
	private static final long serialVersionUID = 2653870673267292972L;
	
	/** Default title bar height. */
	public static final int DEF_TITLEBAR_HEIGHT = 25;
	
	/** Glass title bar background color */
	public static final Color GLASS_BG_COLOR = new Color(64, 64, 196, 255);
	/** Wood title bar background color. */
	public static final Color WOOD_BG_COLOR = new Color(80, 65, 45, 255);
	/** Black and white title bar background color. */
	public static final Color BW_BG_COLOR = new Color(128, 128, 128, 255);
	
	/** Parent window. */
	private Window parent;
	/** Icon label. */
	private JLabel iconlbl;
	/** Title label. */
	private JLabel titlelbl;
	/** Close button. */
	private CloseButton closebtn;

	/**
	 * Default constructor for the NewGameTitleBar that creates the title bar.
	 */
	public NewGameTitleBar(Window parent) {
		//Check parameters
		if(parent == null)
			throw new IllegalArgumentException("New game title bar must have a parent window.");
		
		//Initialize variables
		this.parent = parent;
		
		//Initialize panel
		setPreferredSize(new Dimension(NewGameDialog.DEF_DIALOG_WIDTH, DEF_TITLEBAR_HEIGHT));
		setLayout(null);
		if(Chess.BOARD_OPTION == BoardOption.GLASS)
			setBackground(GLASS_BG_COLOR);
		else if(Chess.BOARD_OPTION == BoardOption.WOODEN)
			setBackground(WOOD_BG_COLOR);
		else
			setBackground(BW_BG_COLOR);
		
		//Create icon label
		iconlbl = new JLabel(new ImageIcon(Chess.makeTransparent(Chess.toBufferedImage(Chess.ICON.getImage()), 0xFFFFFFFF).getScaledInstance(16, 16, Image.SCALE_SMOOTH)));
		iconlbl.setBounds(4, 4, 16, 16);
		
		//Create title label
		titlelbl = new JLabel("New Game");
		titlelbl.setForeground(Color.white);
		titlelbl.setBounds(22, 0, 100, DEF_TITLEBAR_HEIGHT);
		
		//Create close button
		closebtn = new CloseButton();
		closebtn.setBounds(NewGameDialog.DEF_DIALOG_WIDTH - CloseButton.BUTTON_WIDTH - ChessFrame.FRAME_BORDER_SIZE, ChessFrame.FRAME_BORDER_SIZE, 
				CloseButton.BUTTON_WIDTH, CloseButton.BUTTON_HEIGHT);
		closebtn.addActionListener(this);
		
		//Add components
		add(iconlbl);
		add(titlelbl);
		add(closebtn);
		
		//Show panel
		setVisible(true);
	}
	
	//Handle action events
	@Override
	public void actionPerformed(ActionEvent e) {
		//If the close button was pressed
		if(e.getSource().equals(closebtn)) 
			//Close the window
			parent.dispose();
	}
	
	//Repaint the title bar.
	@Override
	public void repaint() {
		super.repaint();
		
		//Repaint background to match board option
		if(Chess.BOARD_OPTION == BoardOption.GLASS)
			setBackground(GLASS_BG_COLOR);
		else if(Chess.BOARD_OPTION == BoardOption.WOODEN)
			setBackground(WOOD_BG_COLOR);
		else
			setBackground(BW_BG_COLOR);
		
		//Resize and repaint components
		if(closebtn != null) {
			closebtn.setBounds(NewGameDialog.DEF_DIALOG_WIDTH- CloseButton.BUTTON_WIDTH - ChessFrame.FRAME_BORDER_SIZE, ChessFrame.FRAME_BORDER_SIZE, 
					CloseButton.BUTTON_WIDTH, CloseButton.BUTTON_HEIGHT);
			closebtn.repaint();
		}
	}
}
