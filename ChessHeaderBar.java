package com.chess.gui.bar;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JPanel;

import com.chess.Chess;
import com.chess.Chess.BoardOption;
import com.chess.gui.frame.ChessFrame;

/**
 * 
 * @author Monroe Gordon
 * @since 5/18/2022
 */
public class ChessHeaderBar extends JPanel {

	/** ChessTitleBar serial version ID value. */
	private static final long serialVersionUID = 2653870673267292972L;
	
	/** Chess title bar. */
	private ChessTitleBar titlebar;
	/** Chess menu bar. */
	private ChessMenuBar menubar;

	/**
	 * Default constructor for ChessHeaderBar that creates the title and menu bar.
	 */
	public ChessHeaderBar() {
		//Initialize panel
		setSize(new Dimension(Chess.getFrameWidth(), ChessFrame.DEF_HEADER_HEIGHT));
		BorderLayout bl = new BorderLayout();
		bl.setVgap(0);
		setLayout(bl);
		if(Chess.BOARD_OPTION == BoardOption.GLASS)
			setBackground(ChessTitleBar.GLASS_BG_COLOR);
		else if(Chess.BOARD_OPTION == BoardOption.WOODEN)
			setBackground(ChessTitleBar.WOOD_BG_COLOR);
		else
			setBackground(ChessTitleBar.BW_BG_COLOR);
		
		//Create chess title bar
		titlebar = new ChessTitleBar();
		
		//Create chess menu bar
		menubar = new ChessMenuBar();
		
		//Add components
		add(titlebar, BorderLayout.NORTH);
		add(menubar, BorderLayout.SOUTH);
		
		//Show panel
		setVisible(true);
	}
	
	//Repaint the header bar.
	@Override
	public void repaint() {
		super.repaint();
		
		//Resize and repaint components
		if(titlebar != null) {
			titlebar.setSize(new Dimension(Chess.getFrameWidth(), 25));
			titlebar.repaint();
		}
		if(menubar != null) {
			menubar.setSize(new Dimension(Chess.getFrameWidth(), 20));
			menubar.repaint();
		}
	}
}
