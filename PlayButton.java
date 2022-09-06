package com.chess.gui.button;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;

import com.chess.gui.panel.NewGamePanel;

/**
 * PlayButton creates and controls the play button in the new game dialog. This button causes a new chess game to start.
 * @author Monroe Gordon
 * @since 6/9/2022
 */
public class PlayButton extends JButton implements MouseListener {

	/** ExitButton serial version ID value. */
	private static final long serialVersionUID = -7790390600464041763L;
	
	/** Exit button height value. */
	public static final int BUTTON_HEIGHT = 50;
	/** Exit button width value. */
	public static final int BUTTON_WIDTH = 150;
	
	/** Rollover flag. */
	private boolean rollover;
	/** Pressed flag. */
	private boolean pressed;

	/**
	 * Default constructor for the PlayButton that creates the exit button.
	 */
	public PlayButton() {
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
				//Create lighter translucent new game dialog colored background
				g2.setBackground(NewGamePanel.DEF_BG_COLOR.brighter().brighter());
				g2.clearRect(0, 0, BUTTON_WIDTH, BUTTON_HEIGHT);
				
				//Draw translucent Play string
				g2.setColor(new Color(255, 255, 255, 128));
				g2.setFont(new Font("Arial", Font.PLAIN, 24));
				FontMetrics metrics = g2.getFontMetrics();
				int strwidth = metrics.charsWidth("Play".toCharArray(), 0, "Play".length());
				int strheight = metrics.getHeight();
				g2.drawString("Play", (getWidth() / 2) - (strwidth / 2), getHeight() - (strheight / 2));
			}
			//If the button is not pressed
			else {
				//Create lighter new game dialog colored background
				g2.setBackground(NewGamePanel.DEF_BG_COLOR.brighter());
				g2.clearRect(0, 0, BUTTON_WIDTH, BUTTON_HEIGHT);
				
				//Draw Play string
				g2.setColor(new Color(255, 255, 255, 255));
				g2.setFont(new Font("Arial", Font.PLAIN, 24));
				FontMetrics metrics = g2.getFontMetrics();
				int strwidth = metrics.charsWidth("Play".toCharArray(), 0, "Play".length());
				int strheight = metrics.getHeight();
				g2.drawString("Play", (getWidth() / 2) - (strwidth / 2), getHeight() - (strheight / 2));
			}
		}
		//If the mouse is not over the button
		else {
			//Create new game dialog colored background
			g2.setBackground(NewGamePanel.DEF_BG_COLOR);
			g2.clearRect(0, 0, BUTTON_WIDTH, BUTTON_HEIGHT);
			
			//Draw Play string
			g2.setColor(new Color(255, 255, 255, 255));
			g2.setFont(new Font("Arial", Font.PLAIN, 24));
			FontMetrics metrics = g2.getFontMetrics();
			int strwidth = metrics.charsWidth("Play".toCharArray(), 0, "Play".length());
			int strheight = metrics.getHeight();
			g2.drawString("Play", (getWidth() / 2) - (strwidth / 2), getHeight() - (strheight / 2));
		}
		
		//Dispose of graphics object
		g2.dispose();
	}

	//Handle mouse clicked.
	@Override
	public void mouseClicked(MouseEvent e) {
		//Inform action listeners
		fireActionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null));
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
