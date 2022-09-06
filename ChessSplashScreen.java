package com.chess.gui.frame;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JProgressBar;
import javax.swing.UIDefaults;

import com.chess.Chess;
import com.chess.gui.event.ProgressEvent;
import com.chess.gui.event.ProgressUpdateListener;
import com.chess.gui.painter.ProgressBarFgPainter;
import com.chess.gui.panel.ChessSplashScreenPanel;

/**
 * ChessSplashScreen creates and controls the splash screen that is displayed when the Chess program is launched.
 * @author Monroe Gordon
 * @since 6/1/2022
 */
public class ChessSplashScreen extends JFrame implements ProgressUpdateListener {

	/** ChessSplashScreen serial version ID value. */
	private static final long serialVersionUID = 8270882333920704552L;
	
	/** Default screen height value. */
	public static final int DEF_SCREEN_HEIGHT = 768;
	/** Default screen width value. */
	public static final int DEF_SCREEN_WIDTH = 1024;
	/** Default progress bar height value. */
	public static final int DEF_PROGRESS_BAR_HEIGHT = 10;
	
	/** Chess splash screen panel. */
	private ChessSplashScreenPanel csspanel;
	/** Chess progress bar. */
	private JProgressBar progressbar;

	/**
	 * Default constructor for ChessSplashScreen that creates the splash screen.
	 */
	public ChessSplashScreen() {
		//Initialize frame
		setSize(new Dimension(DEF_SCREEN_WIDTH, DEF_SCREEN_HEIGHT));
		setLocationRelativeTo(null);
		setResizable(false);
		setUndecorated(true);
		setLayout(null);
		
		//Create splash screen panel
		csspanel = new ChessSplashScreenPanel();
		csspanel.setBounds(0, 0, DEF_SCREEN_WIDTH, DEF_SCREEN_HEIGHT - DEF_PROGRESS_BAR_HEIGHT);
		
		//Create progress bar
		progressbar = new JProgressBar();
		progressbar.setBounds(0, DEF_SCREEN_HEIGHT - DEF_PROGRESS_BAR_HEIGHT, DEF_SCREEN_WIDTH, DEF_PROGRESS_BAR_HEIGHT);
		progressbar.setMinimum(0);
		progressbar.setMaximum(100);
	    UIDefaults overrides = new UIDefaults();
	    overrides.put("ProgressBar[Enabled].foregroundPainter", new ProgressBarFgPainter());
	    overrides.put("ProgressBar[Enabled+Finished].foregroundPainter", new ProgressBarFgPainter());
	    progressbar.putClientProperty("Nimbus.Overrides", overrides);
	    progressbar.putClientProperty("Nimbus.Overrides.InheritDefaults", false);
		
		//Add components
		add(csspanel);
		add(progressbar);
		
		//If glass is allowed
		if(Chess.GLASS_BOARD_ALLOWED) {
			//Make window 50% translucent
			getContentPane().setBackground(new Color(0, 0, 0, 127));
			setBackground(new Color(0, 0, 0, 127));
		}
		//If using any other board
		else {
			getContentPane().setBackground(new Color(0, 0, 0, 255));
			setBackground(new Color(0, 0, 0, 255));
		}
		
		//Set Chess progress update listener
		Chess.setProgressUpdateListener(this);
		
		//Show frame
		setVisible(true);
	}

	//Update the progress bar
	@Override
	public void updateProgress(ProgressEvent e) {
		progressbar.setValue(e.getProgress());
	}
}
