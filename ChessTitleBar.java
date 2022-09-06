package com.chess.gui.bar;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.chess.Chess;
import com.chess.Chess.BoardOption;
import com.chess.gui.button.ExitButton;
import com.chess.gui.button.MaximizeButton;
import com.chess.gui.button.MinimizeButton;
import com.chess.gui.frame.ChessFrame;

/**
 * ChessTitleBar creates and controls the title bar of the Chess window, including the minimize, maximize and exit buttons.
 * @author Monroe Gordon
 * @since 5/28/2022
 */
public class ChessTitleBar extends JPanel {

	/** ChessTitleBar serial version ID value. */
	private static final long serialVersionUID = 2653870673267292972L;
	
	/** Default title bar height. */
	public static final int DEF_TITLEBAR_HEIGHT = 25;
	
	/** Glass title bar background color. */
	public static final Color GLASS_BG_COLOR = new Color(64, 64, 196, 255);
	/** Wood title bar background color. */
	public static final Color WOOD_BG_COLOR = new Color(80, 65, 45, 255);
	/** Black and white title bar background color. */
	public static final Color BW_BG_COLOR = new Color(128, 128, 128, 255);
	
	/** Icon label. */
	private JLabel iconlbl;
	/** Title label. */
	private JLabel titlelbl;
	/** Minimize button. */
	private MinimizeButton minbtn;
	/** Maximize button. */
	private MaximizeButton maxbtn;
	/** Exit button. */
	private ExitButton exitbtn;

	/**
	 * Default constructor for the ChessTitleBar that creates the title bar.
	 */
	public ChessTitleBar() {
		//Initialize panel
		setPreferredSize(new Dimension(Chess.getFrameWidth(), DEF_TITLEBAR_HEIGHT));
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
		titlelbl = new JLabel("Chess");
		titlelbl.setForeground(Color.white);
		titlelbl.setBounds(22, 0, 50, DEF_TITLEBAR_HEIGHT);
		
		//Create minimize button
		minbtn = new MinimizeButton();
		minbtn.setBounds(Chess.getFrameWidth() - ExitButton.BUTTON_WIDTH - MaximizeButton.BUTTON_WIDTH - MinimizeButton.BUTTON_WIDTH - ChessFrame.FRAME_BORDER_SIZE, 
				ChessFrame.FRAME_BORDER_SIZE, MinimizeButton.BUTTON_WIDTH, MinimizeButton.BUTTON_HEIGHT);
		
		//Create maximize button
		maxbtn = new MaximizeButton();
		maxbtn.setBounds(Chess.getFrameWidth() - ExitButton.BUTTON_WIDTH - MaximizeButton.BUTTON_WIDTH - ChessFrame.FRAME_BORDER_SIZE, ChessFrame.FRAME_BORDER_SIZE, 
				MaximizeButton.BUTTON_WIDTH, MaximizeButton.BUTTON_HEIGHT);
		
		//Create exit button
		exitbtn = new ExitButton();
		exitbtn.setBounds(Chess.getFrameWidth()- ExitButton.BUTTON_WIDTH - ChessFrame.FRAME_BORDER_SIZE, ChessFrame.FRAME_BORDER_SIZE, 
				ExitButton.BUTTON_WIDTH, ExitButton.BUTTON_HEIGHT);
		
		//Add components
		add(iconlbl);
		add(titlelbl);
		add(minbtn);
		add(maxbtn);
		add(exitbtn);
		
		//Show panel
		setVisible(true);
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
		if(minbtn != null) {
			minbtn.setBounds(Chess.getFrameWidth() - ExitButton.BUTTON_WIDTH - MaximizeButton.BUTTON_WIDTH - MinimizeButton.BUTTON_WIDTH - ChessFrame.FRAME_BORDER_SIZE, 
					ChessFrame.FRAME_BORDER_SIZE, MinimizeButton.BUTTON_WIDTH, MinimizeButton.BUTTON_HEIGHT);
			minbtn.repaint();
		}
		if(maxbtn != null) {
			maxbtn.setBounds(Chess.getFrameWidth() - ExitButton.BUTTON_WIDTH - MaximizeButton.BUTTON_WIDTH - ChessFrame.FRAME_BORDER_SIZE, ChessFrame.FRAME_BORDER_SIZE, 
					MaximizeButton.BUTTON_WIDTH, MaximizeButton.BUTTON_HEIGHT);
			maxbtn.repaint();
		}
		if(exitbtn != null) {
			exitbtn.setBounds(Chess.getFrameWidth()- ExitButton.BUTTON_WIDTH - ChessFrame.FRAME_BORDER_SIZE, ChessFrame.FRAME_BORDER_SIZE, 
					ExitButton.BUTTON_WIDTH, ExitButton.BUTTON_HEIGHT);
			exitbtn.repaint();
		}
	}
}
