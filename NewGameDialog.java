package com.chess.gui.dialog;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.JDialog;

import com.chess.Chess;
import com.chess.gui.bar.NewGameTitleBar;
import com.chess.gui.panel.NewGamePanel;

/**
 * NewGameDialog creates and controls the new game dialog window that allows a player to start a new game of chess.
 * @author Monroe Gordon
 * @since 6/7/2022
 */
public class NewGameDialog extends JDialog implements MouseMotionListener {

	/** NewGameDialog serial version ID value. */
	private static final long serialVersionUID = 7391903975629309326L;
	
	/** Default dialog width. */
	public static final int DEF_DIALOG_WIDTH = 600;
	/** Default dialog height. */
	public static final int DEF_DIALOG_HEIGHT = 600;
	
	/** New game title bar. */
	private NewGameTitleBar titlebar;
	/** New game panel. */
	private NewGamePanel ngpanel;
	/** Mouse cursor position. */
	private Point mousePos;
	
	/**
	 * Default constructor for NewGameDialog that creates the dialog for starting a new game of chess.
	 */
	public NewGameDialog() {
		//Initialize dialog
		setSize(new Dimension(DEF_DIALOG_WIDTH, DEF_DIALOG_HEIGHT));
		setLocationRelativeTo(Chess.getChessFrame());
		setModal(true);
		setResizable(false);
		setUndecorated(true);
		BorderLayout bl = new BorderLayout();
		bl.setVgap(0);
		setLayout(bl);
		
		//Create title bar
		titlebar = new NewGameTitleBar(this);
		
		//Create new game panel
		ngpanel = new NewGamePanel(this);
		
		//Add components
		add(titlebar, BorderLayout.NORTH);
		add(ngpanel, BorderLayout.CENTER);
		
		//Add listeners
		addMouseMotionListener(this);
		
		//If allowing glass board
		if(Chess.GLASS_BOARD_ALLOWED)
			//Make window 50% translucent
			setBackground(new Color(0, 0, 0, 127));
		//If using any other board
		else
			//Make window opaque
			setBackground(new Color(0, 0, 0, 255));
		
		//Show dialog
		setVisible(true);
	}

	//Handle mouse dragged
	@Override
	public void mouseDragged(MouseEvent e) {
		//Move the window
		this.setLocation(getLocation().x - (mousePos.x - e.getX()), getLocation().y - (mousePos.y - e.getY()));
	}

	//Handle mouse moved
	@Override
	public void mouseMoved(MouseEvent e) {
		//Update mouse position
		mousePos = e.getPoint();
	}
}
