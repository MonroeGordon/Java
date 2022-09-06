package com.chess.gui.frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.chess.Chess;
import com.chess.gui.bar.ChessHeaderBar;
import com.chess.gui.event.StopwatchEvent;
import com.chess.gui.event.StopwatchListener;
import com.chess.gui.panel.ChessBoardPanel;

/**
 * ChessFrame creates the main window frame for the Chess program and handles window events.
 * @author Monroe Gordon
 * @since 5/27/2022
 */
public class ChessFrame extends JFrame implements KeyListener, MouseListener, MouseMotionListener, StopwatchListener, WindowListener {

	/** ChessFrame serial version ID value */
	private static final long serialVersionUID = 6703227112963779595L;
	
	/** Default screen height value. */
	public static final int DEF_SCREEN_HEIGHT = 768;
	/** Default screen width value. */
	public static final int DEF_SCREEN_WIDTH = 1024;
	/** Minimum screen height value. */
	public static final int MIN_SCREEN_HEIGHT = 480;
	/** Minimum screen width value. */
	public static final double MIN_SCREEN_WIDTH_RATIO = 1.333333;
	/** Frame border size value. */
	public static final int FRAME_BORDER_SIZE = 2;
	/** Default chess header bar height value. */
	public static final int DEF_HEADER_HEIGHT = 45;
	/** Default chess board panel width value. */
	public static final int DEF_CHESS_BOARD_WIDTH = DEF_SCREEN_WIDTH - (FRAME_BORDER_SIZE * 2);
	/** Default chess board panel height value. */
	public static final int DEF_CHESS_BOARD_HEIGHT = DEF_SCREEN_HEIGHT - DEF_HEADER_HEIGHT - FRAME_BORDER_SIZE;
	
	/** North direction value. */
	public static final int NORTH_DIR = 0x00000001;
	/** East direction value. */
	public static final int EAST_DIR = 	0x00000002;
	/** South direction value. */
	public static final int SOUTH_DIR = 0x00000004;
	/** West direction value. */
	public static final int WEST_DIR = 	0x00000008;
	/** Northeast direction value. */
	public static final int NORTHEAST_DIR = NORTH_DIR | EAST_DIR;
	/** Southeast direction value. */
	public static final int SOUTHEAST_DIR = SOUTH_DIR | EAST_DIR;
	/** Southwest direction value. */
	public static final int SOUTHWEST_DIR = SOUTH_DIR | WEST_DIR;
	/** Northwest direction value. */
	public static final int NORTHWEST_DIR = NORTH_DIR | WEST_DIR;
	
	/** Chess title bar. */
	private ChessHeaderBar headerbar;
	/** Chess frame panel. */
	private JPanel fpanel;
	/** Chess board panel. */
	private ChessBoardPanel cbpanel;
	
	/** Mouse coordinates. */
	private Point mousePos;
	/** Mouse on border flag. */
	private boolean onBorder;
	/** Resize direction value. */
	private int resizeDir;

	/**
	 * Default constructor for the ChessFrame that creates the window for the program.
	 */
	public ChessFrame() {
		//Initialize variables
		onBorder = false;
		resizeDir = 0;
		
		//Initialize frame
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setSize(new Dimension(DEF_SCREEN_WIDTH, DEF_SCREEN_HEIGHT));
		setTitle("Chess");
		setUndecorated(true);
		
		//Set icon image
		setIconImage(Chess.makeTransparent(Chess.toBufferedImage(Chess.ICON.getImage()), 0xFFFFFFFF).getScaledInstance(16, 16, Image.SCALE_SMOOTH));
		
		//Create chess header bar
		headerbar = new ChessHeaderBar();
		
		//Create frame panel
		fpanel = new JPanel();
		fpanel.setSize(new Dimension(DEF_SCREEN_WIDTH, DEF_SCREEN_HEIGHT - DEF_HEADER_HEIGHT));
		fpanel.setBackground(headerbar.getBackground());
		fpanel.setLayout(null);
		
		//Create chess board panel
		cbpanel = new ChessBoardPanel();
		cbpanel.setBounds(FRAME_BORDER_SIZE, 0, fpanel.getWidth() - (FRAME_BORDER_SIZE * 2), fpanel.getHeight() - FRAME_BORDER_SIZE);
		fpanel.add(cbpanel);
		
		//Add components
		add(headerbar, BorderLayout.NORTH);
		add(fpanel, BorderLayout.CENTER);
		
		//Add listeners
		addKeyListener(this);
		addMouseMotionListener(this);
		addWindowListener(this);
		
		//Set frame's location
		setLocationRelativeTo(null);
		
		//If allowing glass board
		if(Chess.GLASS_BOARD_ALLOWED) {
			//Make window 50% translucent
			getContentPane().setBackground(new Color(0, 0, 0, 127));
			setBackground(new Color(0, 0, 0, 127));
		}
		//If using any other board
		else {
			//Make window opaque
			getContentPane().setBackground(new Color(0, 0, 0, 255));
			setBackground(new Color(0, 0, 0, 255));
		}
		
		//Show frame
		setVisible(true);
	}
	
	//Repaint the window.
	@Override
	public void repaint() {
		super.repaint();
		
		//Resize and repaint the header bar
		if(headerbar != null) {
			headerbar.setSize(new Dimension(Chess.getFrameWidth(), DEF_HEADER_HEIGHT));
			headerbar.repaint();
		}
		
		//Resize and repaint the frame and board panel
		fpanel.setSize(new Dimension(Chess.getFrameWidth(), Chess.getFrameHeight() - DEF_HEADER_HEIGHT));
		fpanel.repaint();
		cbpanel.setBounds(FRAME_BORDER_SIZE, 0, fpanel.getWidth() - (FRAME_BORDER_SIZE * 2), fpanel.getHeight() - FRAME_BORDER_SIZE);
		cbpanel.repaint();
	}
	
	//Handle key typed.
	@Override
	public void keyTyped(KeyEvent e) {
	}

	//Handle key pressed.
	@Override
	public void keyPressed(KeyEvent e) {
		//If escape is pressed
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			//Shutdown the program
			Chess.shutdown();
		}
	}

	//Handle key released.
	@Override
	public void keyReleased(KeyEvent e) {
	}
	
	//Handle mouse clicked
	@Override
	public void mouseClicked(MouseEvent e) {
	}
	
	//Handle mouse dragged
	@Override
	public void mouseDragged(MouseEvent e) {
		//If the mouse is on a border
		if(onBorder) {
			//Resize the window in the set direction
			switch(resizeDir) {
			case NORTH_DIR:
				setLocation(getLocation().x, getLocation().y - (mousePos.y - e.getY()));
				setSize(getWidth(), getHeight() + (mousePos.y - e.getY()));
				cbpanel.setBounds(FRAME_BORDER_SIZE, 0, fpanel.getWidth() - (FRAME_BORDER_SIZE * 2), fpanel.getHeight() - FRAME_BORDER_SIZE);
				break;
			case NORTHEAST_DIR:
				setLocation(getLocation().x, getLocation().y - (mousePos.y - e.getY()));
				setSize(e.getXOnScreen() - getLocation().x, getHeight() + (mousePos.y - e.getY()));
				cbpanel.setBounds(FRAME_BORDER_SIZE, 0, fpanel.getWidth() - (FRAME_BORDER_SIZE * 2), fpanel.getHeight() - FRAME_BORDER_SIZE);
				break;
			case EAST_DIR:
				setSize(e.getXOnScreen() - getLocation().x, getHeight());
				cbpanel.setBounds(FRAME_BORDER_SIZE, 0, fpanel.getWidth() - (FRAME_BORDER_SIZE * 2), fpanel.getHeight() - FRAME_BORDER_SIZE);
				break;
			case SOUTHEAST_DIR:
				setSize(e.getXOnScreen() - getLocation().x, e.getYOnScreen() - getLocation().y);
				cbpanel.setBounds(FRAME_BORDER_SIZE, 0, fpanel.getWidth() - (FRAME_BORDER_SIZE * 2), fpanel.getHeight() - FRAME_BORDER_SIZE);
				break;
			case SOUTH_DIR:
				setSize(getWidth(), e.getYOnScreen() - getLocation().y);
				cbpanel.setBounds(FRAME_BORDER_SIZE, 0, fpanel.getWidth() - (FRAME_BORDER_SIZE * 2), fpanel.getHeight() - FRAME_BORDER_SIZE);
				break;
			case SOUTHWEST_DIR:
				setLocation(getLocation().x - (mousePos.x - e.getX()), getLocation().y);
				setSize(getWidth() + (mousePos.x - e.getX()), e.getYOnScreen() - getLocation().y);
				cbpanel.setBounds(FRAME_BORDER_SIZE, 0, fpanel.getWidth() - (FRAME_BORDER_SIZE * 2), fpanel.getHeight() - FRAME_BORDER_SIZE);
				break;
			case WEST_DIR:
				setLocation(getLocation().x - (mousePos.x - e.getX()), getLocation().y);
				setSize(getWidth() + (mousePos.x - e.getX()), getHeight());
				cbpanel.setBounds(FRAME_BORDER_SIZE, 0, fpanel.getWidth() - (FRAME_BORDER_SIZE * 2), fpanel.getHeight() - FRAME_BORDER_SIZE);
				break;
			case NORTHWEST_DIR:
				setLocation(getLocation().x - (mousePos.x - e.getX()), getLocation().y - (mousePos.y - e.getY()));
				setSize(getWidth() + (mousePos.x - e.getX()), getHeight() + (mousePos.y - e.getY()));
				cbpanel.setBounds(FRAME_BORDER_SIZE, 0, fpanel.getWidth() - (FRAME_BORDER_SIZE * 2), fpanel.getHeight() - FRAME_BORDER_SIZE);
				break;
			}
			
			//Maintain required aspect ratio (1.333333 : 1) and minimum required height
			if(getHeight() < MIN_SCREEN_HEIGHT)
				setSize(getWidth(), MIN_SCREEN_HEIGHT);
			
			if(getWidth() < (int)(getHeight() * MIN_SCREEN_WIDTH_RATIO))
				setSize((int)(getHeight() * MIN_SCREEN_WIDTH_RATIO), getHeight());
			
			cbpanel.setBounds(FRAME_BORDER_SIZE, 0, fpanel.getWidth() - (FRAME_BORDER_SIZE * 2), fpanel.getHeight() - FRAME_BORDER_SIZE);
		}
		//If the mouse isn't on a border
		else {
			//Move the window
			setLocation(getLocation().x - (mousePos.x - e.getX()), getLocation().y - (mousePos.y - e.getY()));
		}
	}
	
	//Handle mouse entered
	@Override
	public void mouseEntered(MouseEvent e) {
		//Set mouse cursor to default cursor
		setCursor(Cursor.getDefaultCursor());
	}

	//Handle mouse exited
	@Override
	public void mouseExited(MouseEvent e) {
		//Set mouse cursor to default cursor
		setCursor(Cursor.getDefaultCursor());
	}

	//Handle mouse pressed
	@Override
	public void mousePressed(MouseEvent e) {
	}

	//Handle mouse released
	@Override
	public void mouseReleased(MouseEvent e) {
	}

	//Handle mouse moves
	@Override
	public void mouseMoved(MouseEvent e) {
		//Update mouse position
		mousePos = e.getPoint();
		
		//Set mouse cursor to default cursor
		setCursor(Cursor.getDefaultCursor());
		
		//Reset onBorder and resizeDir
		onBorder = false;
		resizeDir = 0;
		
		//If mouse is on north border
		if(mousePos.y <= FRAME_BORDER_SIZE) {
			//And if mouse is on west border
			if(mousePos.x <= FRAME_BORDER_SIZE) {
				//Set mouse cursor to NW resize cursor
			    setCursor(Cursor.getPredefinedCursor(Cursor.NW_RESIZE_CURSOR));
			    resizeDir = NORTHWEST_DIR;
			}
			//And if mouse is on east border
			else if(mousePos.x >= getWidth() - FRAME_BORDER_SIZE) {
				//Set mouse cursor to NE resize cursor
			    setCursor(Cursor.getPredefinedCursor(Cursor.NE_RESIZE_CURSOR));
			    resizeDir = NORTHEAST_DIR;
			}
			//And if mouse isn't on any other border
			else {
				//Set mouse cursor to N resize cursor
			    setCursor(Cursor.getPredefinedCursor(Cursor.N_RESIZE_CURSOR));
			    resizeDir = NORTH_DIR;
			}
			
			//Set onBorder
			onBorder = true;
		}
		//If mouse is on south border
		else if(mousePos.y >= getHeight() - FRAME_BORDER_SIZE) {
			//And if mouse is on west border
			if(mousePos.x <= FRAME_BORDER_SIZE) {
				//Set mouse cursor to SW resize cursor
			    setCursor(Cursor.getPredefinedCursor(Cursor.SW_RESIZE_CURSOR));
			    resizeDir = SOUTHWEST_DIR;
			}
			//And if mouse is on east border
			else if(mousePos.x >= getWidth() - FRAME_BORDER_SIZE) {
				//Set mouse cursor to SE resize cursor
			    setCursor(Cursor.getPredefinedCursor(Cursor.SE_RESIZE_CURSOR));
			    resizeDir = SOUTHEAST_DIR;
			}
			//And if mouse isn't on any other border
			else {
				//Set mouse cursor to S resize cursor
			    setCursor(Cursor.getPredefinedCursor(Cursor.S_RESIZE_CURSOR));
			    resizeDir = SOUTH_DIR;
			}
			
			//Set onBorder
			onBorder = true;
		}
		//If mouse is only on west border
		else if(mousePos.x <= FRAME_BORDER_SIZE) {
			//Set mouse cursor to W resize cursor
		    setCursor(Cursor.getPredefinedCursor(Cursor.W_RESIZE_CURSOR));
		    
		    //Set onBorder and resizeDir
		    onBorder = true;
		    resizeDir = WEST_DIR;
		}
		//If mouse is only on east border
		else if(mousePos.x >= getWidth() - FRAME_BORDER_SIZE) {
			//Set mouse cursor to E resize cursor
		    setCursor(Cursor.getPredefinedCursor(Cursor.E_RESIZE_CURSOR));
		    
		    //Set onBorder and resizeDir
		    onBorder = true;
		    resizeDir = EAST_DIR;
		}
		//If mouse isn't on a border
		else {
			//Set mouse cursor to default cursor
			setCursor(Cursor.getDefaultCursor());
			
			//Reset onBorder and resizeDir
			onBorder = false;
			resizeDir = 0;
		}
	}
	
	//Handle stopwatch paused
	@Override
	public void stopwatchPaused(StopwatchEvent e) {
		
	}
	
	//Handle stopwatch started
	@Override
	public void stopwatchStarted(StopwatchEvent e) {
		//If event was triggered by the black clock
		if(e.getSource().equals(Chess.getChessClock().getBlackClock())) {
			//Update the black clock with the current time
			cbpanel.updateBlackClock(Chess.getChessClock().getBlackClock().getTime());
		}
		//If event was triggered by the white clock
		else {
			//Update the white clock with the current time
			cbpanel.updateWhiteClock(Chess.getChessClock().getWhiteClock().getTime());
		}
	}

	//Handle stopwatch ticked
	@Override
	public void stopwatchTicked(StopwatchEvent e) {
		//If event was triggered by the black clock
		if(e.getSource().equals(Chess.getChessClock().getBlackClock())) {
			//Update the black clock with the current time
			cbpanel.updateBlackClock(Chess.getChessClock().getBlackClock().getTime());
		}
		//If event was triggered by the white clock
		else {
			//Update the white clock with the current time
			cbpanel.updateWhiteClock(Chess.getChessClock().getWhiteClock().getTime());
		}
	}

	//Handle stopwatch stopped
	@Override
	public void stopwatchStopped(StopwatchEvent e) {
		
	}

	//Handle window opened.
	@Override
	public void windowOpened(WindowEvent e) {
	}

	//Handle window closing.
	@Override
	public void windowClosing(WindowEvent e) {
		//Shutdown the program
		Chess.shutdown();
	}

	//Handle window closed.
	@Override
	public void windowClosed(WindowEvent e) {
	}

	//Handle window iconified.
	@Override
	public void windowIconified(WindowEvent e) {
	}

	//Handle window deiconified.
	@Override
	public void windowDeiconified(WindowEvent e) {
	}

	//Handle window activated.
	@Override
	public void windowActivated(WindowEvent e) {
	}

	//Handle window deactivated.
	@Override
	public void windowDeactivated(WindowEvent e) {
	}
}