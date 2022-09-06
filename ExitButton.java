package com.chess.gui.button;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;

import com.chess.Chess;
import com.chess.Chess.BoardOption;
import com.chess.gui.bar.ChessTitleBar;
import com.chess.gui.frame.ChessFrame;

/**
 * ExitButton creates and controls the exit button at the top right of the window. This button causes the program to exit.
 * @author Monroe Gordon
 * @since 5/28/2022
 */
public class ExitButton extends JButton implements MouseListener {

	/** ExitButton serial version ID value. */
	private static final long serialVersionUID = -7790390600464041763L;
	
	/** Exit button height value. */
	public static final int BUTTON_HEIGHT = 25 - ChessFrame.FRAME_BORDER_SIZE;
	/** Exit button width value. */
	public static final int BUTTON_WIDTH = 50;
	
	/** Rollover flag. */
	private boolean rollover;
	/** Pressed flag. */
	private boolean pressed;

	/**
	 * Default constructor for the ExitButton that creates the exit button.
	 */
	public ExitButton() {
		//Initialize variables
		rollover = false;
		pressed = false;
		
		//Initialize button
		setSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
		setBorder(BorderFactory.createEmptyBorder());
		
		//Add listeners
		addMouseListener(this);
		
		//Show button
		setVisible(true);
	}
	
	//Paint the button.
	@Override
	public void paintComponent(Graphics g) {
		//Convert to 2D graphics
		Graphics2D g2 = (Graphics2D)g;
		
		//If mouse is over the button
		if(rollover) {
			//If the button is pressed
			if(pressed) {
				//Create translucent red colored background
				g2.setBackground(new Color(255, 64, 64, 196));
				g2.clearRect(0, 0, BUTTON_WIDTH, BUTTON_HEIGHT);
				
				//Draw translucent white x
				g2.setColor(new Color(255, 255, 255, 128));
				g2.setStroke(new BasicStroke(2));
				g2.drawLine(21, 6, 30, 15);
				g2.drawLine(21, 15, 30, 6);
			}
			//If the button is not pressed
			else {
				//Create red colored background
				if(Chess.BOARD_OPTION == BoardOption.GLASS)
					g2.setBackground(ChessTitleBar.GLASS_BG_COLOR.brighter());
				else if(Chess.BOARD_OPTION == BoardOption.WOODEN)
					g2.setBackground(ChessTitleBar.WOOD_BG_COLOR.brighter());
				else
					g2.setBackground(ChessTitleBar.BW_BG_COLOR.brighter());
				g2.clearRect(0, 0, BUTTON_WIDTH, BUTTON_HEIGHT);
				
				//Draw white x
				g2.setColor(new Color(255, 255, 255, 255));
				g2.setStroke(new BasicStroke(2));
				g2.drawLine(21, 6, 30, 15);
				g2.drawLine(21, 15, 30, 6);
			}
		}
		//If the mouse is not over the button
		else {
			//Create title bar colored background
			if(Chess.BOARD_OPTION == BoardOption.GLASS)
				g2.setBackground(ChessTitleBar.GLASS_BG_COLOR);
			else if(Chess.BOARD_OPTION == BoardOption.WOODEN)
				g2.setBackground(ChessTitleBar.WOOD_BG_COLOR);
			else
				g2.setBackground(ChessTitleBar.BW_BG_COLOR);
			g2.clearRect(0, 0, BUTTON_WIDTH, BUTTON_HEIGHT);
			
			//Draw white x
			g2.setColor(new Color(255, 255, 255, 255));
			g2.setStroke(new BasicStroke(2));
			g2.drawLine(21, 6, 30, 15);
			g2.drawLine(21, 15, 30, 6);
		}
		
		//Dispose of graphics object
		g2.dispose();
	}

	//Handle mouse clicked.
	@Override
	public void mouseClicked(MouseEvent e) {
		//Shutdown the program
		Chess.shutdown();
	}

	//Handle mouse pressed.
	@Override
	public void mousePressed(MouseEvent e) {
		//Set pressed and repaint
		pressed = true;
		repaint();
	}

	//Handle mouse released.
	@Override
	public void mouseReleased(MouseEvent e) {
		//Reset pressed and repaint
		pressed = false;
		repaint();
	}

	//Handle mouse entered.
	@Override
	public void mouseEntered(MouseEvent e) {
		//Set rollover and repaint
		rollover = true;
		repaint();
	}

	@Override
	public void mouseExited(MouseEvent e) {
		//Reset rollover and repaint
		rollover = false;
		repaint();
	}
}
