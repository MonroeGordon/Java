package com.chess.gui.panel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import com.chess.Chess;
import com.chess.gui.frame.ChessSplashScreen;

/**
 * ChessSplashScreenPanel creates and handles the panel used inside the ChessSplashScreen frame. It contains the splash screen's image and the exit button.
 * @author Monroe Gordon
 * @since 6/1/2022
 */
public class ChessSplashScreenPanel extends JPanel {

	/** ChessSplashScreenPanel serial version ID value. */
	private static final long serialVersionUID = 8414854340551875657L;
	
	/** Default splash screen image height. */
	public static final int DEF_SPLASH_IMG_HEIGHT = 700;
	/** Default splash screen image width. */
	public static final int DEF_SPLASH_IMG_WIDTH = 700;
	
	/** Glass background color. */
	public static final Color GLASS_BG_COLOR = new Color(16, 32, 96, 128);
	/** Default background color. */
	public static final Color DEF_BG_COLOR = new Color(16, 32, 96, 255);
	
	/** Splash screen image. */
	private BufferedImage splashimg;

	/**
	 * Default constructor for the ChessSplashScreenPanel that creates the splash screen panel.
	 */
	public ChessSplashScreenPanel() {
		//Attempt to load splash screen image
		try {
			splashimg = ImageIO.read(new File(Chess.RESOURCES_DIR + Chess.SPLASH_IMG));
			Chess.makeTransparent(splashimg, 0xFFFFFFFF);
		//Display error and exit program if loading fails
		} catch (IOException e) {
			System.err.println("Failed to load splash screen image.");
			System.exit(1);
		}
		
		//Initialize panel
		setSize(new Dimension(ChessSplashScreen.DEF_SCREEN_WIDTH, ChessSplashScreen.DEF_SCREEN_HEIGHT - ChessSplashScreen.DEF_PROGRESS_BAR_HEIGHT));
		setLayout(null);
		
		//Show panel
		setVisible(true);
	}
	
	//Paint this panel.
	@Override
	public void paintComponent(Graphics g) {
		//Convert to 2D graphics
		Graphics2D g2 = (Graphics2D)g;
		
		//Set background based on GLASS_BOARD_ALLOWED
		if(Chess.GLASS_BOARD_ALLOWED) g2.setBackground(GLASS_BG_COLOR);
		else g2.setBackground(DEF_BG_COLOR);
		g2.clearRect(0, 0, getWidth(), getHeight());
		
		//Draw splash screen image
		g2.drawImage(splashimg, (getWidth() - DEF_SPLASH_IMG_WIDTH) / 2, (getHeight() - DEF_SPLASH_IMG_HEIGHT) / 2, DEF_SPLASH_IMG_WIDTH, DEF_SPLASH_IMG_HEIGHT, null);
		
		//Dispose of graphics object
		g2.dispose();
	}
}
