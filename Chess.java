package com.chess;

import static java.awt.GraphicsDevice.WindowTranslucency.PERPIXEL_TRANSLUCENT;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import com.chess.ChessBoard.GameState;
import com.chess.gui.dialog.NewGameDialog;
import com.chess.gui.event.ProgressEvent;
import com.chess.gui.event.ProgressUpdateListener;
import com.chess.gui.frame.ChessFrame;
import com.chess.gui.frame.ChessSplashScreen;
import com.chess.nan.core.NAN;

/**
 * 
 * @author Monroe Gordon
 * @since 5/27/2022
 */
public class Chess {
	
	/** BoardOption enum */
	public enum BoardOption {
		BLACK_WHITE,
		WOODEN,
		GLASS
	}
	
	/** Working directory. */
	public static String WORKING_DIR = "C:/Synap/Projects/Java/Chess/";
	/** Resources directory. */
	public static String RESOURCES_DIR = WORKING_DIR + "resources/";
	
	/** Chess icon image. */ 
	public static ImageIcon ICON = new ImageIcon(Chess.RESOURCES_DIR + "chess_ico.png");
	
	/** Board option value */
	public static BoardOption BOARD_OPTION = BoardOption.GLASS;
	/** Glass board option allowed flag */
	public static boolean GLASS_BOARD_ALLOWED = true;
	
	/** Splash screen image file */
	public static String SPLASH_IMG = "chess_ico.png";
	
	/** Black/white image width. */
	public static final int BW_IMG_WIDTH = 320;
	/** Black/white image height. */
	public static final int BW_IMG_HEIGHT = 320;
	/** Black rook black/white image file. */
	public static String BLACK_ROOK_BW_IMG = "br1.png";
	/** Black pawn black/white image file. */
	public static String BLACK_PAWN_BW_IMG = "bp1.png";
	/** Black knight black/white image file. */
	public static String BLACK_KNIGHT_BW_IMG = "bn1.png";
	/** Black bishop black/white image file. */
	public static String BLACK_BISHOP_BW_IMG = "bb1.png";
	/** Black queen black/white image file. */
	public static String BLACK_QUEEN_BW_IMG = "bq1.png";
	/** Black king black/white image file */
	public static String BLACK_KING_BW_IMG = "bk1.png";
	/** White pawn black/white image file. */
	public static String WHITE_PAWN_BW_IMG = "wp1.png";
	/** White rook black/white image file. */
	public static String WHITE_ROOK_BW_IMG = "wr1.png";
	/** White knight black/white image file. */
	public static String WHITE_KNIGHT_BW_IMG = "wn1.png";
	/** White bishop black/white image file. */
	public static String WHITE_BISHOP_BW_IMG = "wb1.png";
	/** White queen black/white image file. */
	public static String WHITE_QUEEN_BW_IMG = "wq1.png";
	/** White king black/white image file */
	public static String WHITE_KING_BW_IMG = "wk1.png";
	
	/** Wood image width. */
	public static final int WOOD_IMG_WIDTH = 1100;
	/** Wood image height. */
	public static final int WOOD_IMG_HEIGHT = 1100;
	/** Black pawn wood image file. */
	public static String BLACK_PAWN_WOOD_IMG = "bp2.png";
	/** Black rook wood image file. */
	public static String BLACK_ROOK_WOOD_IMG = "br2.png";
	/** Black knight wood image file. */
	public static String BLACK_KNIGHT_WOOD_IMG = "bn2.png";
	/** Black bishop wood image file. */
	public static String BLACK_BISHOP_WOOD_IMG = "bb2.png";
	/** Black queen wood image file. */
	public static String BLACK_QUEEN_WOOD_IMG = "bq2.png";
	/** Black king wood image file */
	public static String BLACK_KING_WOOD_IMG = "bk2.png";
	/** White pawn wood image file. */
	public static String WHITE_PAWN_WOOD_IMG = "wp2.png";
	/** White rook wood image file. */
	public static String WHITE_ROOK_WOOD_IMG = "wr2.png";
	/** White knight wood image file. */
	public static String WHITE_KNIGHT_WOOD_IMG = "wn2.png";
	/** White bishop wood image file. */
	public static String WHITE_BISHOP_WOOD_IMG = "wb2.png";
	/** White queen wood image file. */
	public static String WHITE_QUEEN_WOOD_IMG = "wq2.png";
	/** White king wood image file */
	public static String WHITE_KING_WOOD_IMG = "wk2.png";
	
	/** Glass image width. */
	public static final int GLASS_IMG_WIDTH = 600;
	/** Glass image height. */
	public static final int GLASS_IMG_HEIGHT = 600;
	/** Black pawn glass image file. */
	public static String BLACK_PAWN_GLASS_IMG = "bp3.png";
	/** Black rook glass image file. */
	public static String BLACK_ROOK_GLASS_IMG = "br3.png";
	/** Black knight glass image file. */
	public static String BLACK_KNIGHT_GLASS_IMG = "bn3.png";
	/** Black bishop glass image file. */
	public static String BLACK_BISHOP_GLASS_IMG = "bb3.png";
	/** Black queen glass image file. */
	public static String BLACK_QUEEN_GLASS_IMG = "bq3.png";
	/** Black king glass image file */
	public static String BLACK_KING_GLASS_IMG = "bk3.png";
	/** White pawn glass image file. */
	public static String WHITE_PAWN_GLASS_IMG = "wp3.png";
	/** White rook glass image file. */
	public static String WHITE_ROOK_GLASS_IMG = "wr3.png";
	/** White knight glass image file. */
	public static String WHITE_KNIGHT_GLASS_IMG = "wn3.png";
	/** White bishop glass image file. */
	public static String WHITE_BISHOP_GLASS_IMG = "wb3.png";
	/** White queen glass image file. */
	public static String WHITE_QUEEN_GLASS_IMG = "wq3.png";
	/** White king glass image file */
	public static String WHITE_KING_GLASS_IMG = "wk3.png";
	
	/** Chess splash screen. */
	private static ChessSplashScreen css;
	/** Chess progress bar update listener. */
	private static ProgressUpdateListener progressListener;
	/** Chess window. */
	private static ChessFrame cf;
	/** Current chess window location. */
	private static Point wndloc;
	/** Current chess window size. */
	private static Dimension wndsize;
	/** Current chess board. */
	private static ChessBoard board;
	/** The chess clock. */
	private static ChessClock clock;
	/** Black pawn image. */
	private static BufferedImage bpimg;
	/** Black rook image. */
	private static BufferedImage brimg;
	/** Black knight image. */
	private static BufferedImage bnimg;
	/** Black bishop image. */
	private static BufferedImage bbimg;
	/** Black queen image. */
	private static BufferedImage bqimg;
	/** Black king image. */
	private static BufferedImage bkimg;
	/** White pawn image. */
	private static BufferedImage wpimg;
	/** White rook image. */
	private static BufferedImage wrimg;
	/** White knight image. */
	private static BufferedImage wnimg;
	/** White wishop image. */
	private static BufferedImage wbimg;
	/** White queen image. */
	private static BufferedImage wqimg;
	/** White king image. */
	private static BufferedImage wkimg;

	/**
	 * Program entry point method that launches the Chess program.
	 * @param args - unused
	 */
	public static void main(String[] args) {
		//Determine if the GraphicsDevice supports translucency
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();

        //If translucent windows aren't supported, disable glass board option
        if (!gd.isWindowTranslucencySupported(PERPIXEL_TRANSLUCENT)) {
            GLASS_BOARD_ALLOWED = false;
        }
        
        //Attempt to use Nimbus look and feel
        try {
            for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        //If Nimbus is unavailable
        } catch (Exception e) {
        	//Attempt to use a cross-platform look and feel
        	try {
				UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
			//If a cross-platform look and feel is unavailable
			} catch (Exception e1) {
				//Display error message and exit
				System.err.println("Failed to set a cross-platform look and feel.");
				System.exit(1);
			}
        }

        //Create the GUI on the event-dispatching thread
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
            	//Create splash screen
            	css = new ChessSplashScreen();
            	
            	//Wait for progress update listener to be set
            	synchronized(progressListener) {
	            	while(progressListener == null) {
	                	//Wait 100 milliseconds
		                try {
							Thread.sleep(100);
						} catch (InterruptedException e) {
							JOptionPane.showMessageDialog(null, "Thread interrupted exception occurred.", "Error", JOptionPane.ERROR_MESSAGE);
							System.exit(1);
						}
	            	}
            	}
            	
            	//Create loading thread
            	Thread loadThread = new Thread() {
            		@Override
            		public void run() {
		            	//Set board option to wood if glass isn't allowed
		            	if(!GLASS_BOARD_ALLOWED) BOARD_OPTION = BoardOption.WOODEN;
		            	
		            	//Update progress bar
		            	if(progressListener != null) progressListener.updateProgress(new ProgressEvent(this, 25));
		            	
		            	//Load images
		            	loadImages();
		            	
		            	//Update progress bar
		            	if(progressListener != null) progressListener.updateProgress(new ProgressEvent(this, 50));
		            	
		                //Create new chess board
		                board = new ChessBoard();
		                
		                //Create new chess clock
		                clock = new ChessClock();
		                
		            	//Update progress bar
		            	if(progressListener != null) progressListener.updateProgress(new ProgressEvent(this, 75));
		            	
		            	//Initialize the NAN
		            	initializeNAN();
		            	
		            	//Update progress bar
		            	if(progressListener != null) progressListener.updateProgress(new ProgressEvent(this, 100));
		                
		                //Wait 1 second
		                try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
							JOptionPane.showMessageDialog(null, "Thread interrupted exception occurred.", "Error", JOptionPane.ERROR_MESSAGE);
							System.exit(1);
						}
		                
		                //Dispose of the splash screen
		                css.dispose();
		            	
		            	//Create new chess window
		                cf = new ChessFrame();
            		}
            	};
            	
            	loadThread.start();
            }
        });
        
        long x = 0;
        
        for(int i = 0; i < 2048 - 128; ++i) {
        	for(int j = i; j >= 0; --j) {
        		x += j;
        	}
        }
        
        System.out.println(x);
    }
	
	/**
	 * This causes the current game to end where it is at. If a player has not officially won, a draw is concluded.
	 */
	public static void endGame() {
		//If a player didn't win or a stalemate didn't occur
		if(board.getGameState() != GameState.PLAYER_WINS && board.getGameState() != GameState.NAN_WINS && board.getGameState() != GameState.PAUSED &&
				board.getGameState() != GameState.NONE)
			//Set game to a draw
			board.setGameState(GameState.DRAW);
	}
	
	/**
	 * Returns the current chess board.
	 * @return the current chess board
	 */
	public static ChessBoard getChessBoard() { return board; }
	
	/**
	 * Returns the chess clock.
	 * @return the chess clock
	 */
	public static ChessClock getChessClock() { return clock; }
	
	/**
	 * Returns the chess frame window.
	 * @return the chess frame window
	 */
	public static ChessFrame getChessFrame() { return cf; }
	
	/**
	 * Returns the current height of the chess window.
	 * @return the current height of the chess window
	 */
	public static int getFrameHeight() {
		return (cf == null) ? ChessFrame.DEF_SCREEN_HEIGHT : cf.getHeight();
	}
	
	/**
	 * Returns the current width of the chess window.
	 * @return the current width of the chess window
	 */
	public static int getFrameWidth() {
		return (cf == null) ? ChessFrame.DEF_SCREEN_WIDTH : cf.getWidth();
	}
	
	/**
	 * Return the image of the black bishop.
	 * @return the image of the black bishop
	 */
	public static BufferedImage getBlackBishopImage() { return bbimg; }
	
	/**
	 * Return the image of the black king.
	 * @return the image of the black king
	 */
	public static BufferedImage getBlackKingImage() { return bkimg; }
	
	/**
	 * Return the image of the black knight.
	 * @return the image of the black knight
	 */
	public static BufferedImage getBlackKnightImage() { return bnimg; }
	
	/**
	 * Return the image of the black pawn.
	 * @return the image of the black pawn
	 */
	public static BufferedImage getBlackPawnImage() { return bpimg; }
	
	/**
	 * Return the image of the black queen.
	 * @return the image of the black queen
	 */
	public static BufferedImage getBlackQueenImage() { return bqimg; }
	
	/**
	 * Return the image of the black rook.
	 * @return the image of the black rook
	 */
	public static BufferedImage getBlackRookImage() { return brimg; }
	
	/**
	 * Return the image of the white bishop.
	 * @return the image of the white bishop
	 */
	public static BufferedImage getWhiteBishopImage() { return wbimg; }
	
	/**
	 * Return the image of the white king.
	 * @return the image of the white king
	 */
	public static BufferedImage getWhiteKingImage() { return wkimg; }
	
	/**
	 * Return the image of the white knight.
	 * @return the image of the white knight
	 */
	public static BufferedImage getWhiteKnightImage() { return wnimg; }
	
	/**
	 * Return the image of the white pawn.
	 * @return the image of the white pawn
	 */
	public static BufferedImage getWhitePawnImage() { return wpimg; }
	
	/**
	 * Return the image of the white queen.
	 * @return the image of the white queen
	 */
	public static BufferedImage getWhiteQueenImage() { return wqimg; }
	
	/**
	 * Return the image of the white rook.
	 * @return the image of the white rook
	 */
	public static BufferedImage getWhiteRookImage() { return wrimg; }
	
	/**
	 * Initializes the NAN to play chess.
	 */
	private static void initializeNAN() {
		Thread NANThread = new Thread(NAN.getInstance());
		NANThread.start();
	}
	
	/**
	 * Load image files based on the PIECE_OPTION value.
	 */
	private static synchronized void loadImages() {
		//Load option 3 images (glass board, plastic pieces)
		if(Chess.BOARD_OPTION == BoardOption.GLASS) {
			try {
				bpimg = ImageIO.read(new File(Chess.RESOURCES_DIR + Chess.BLACK_PAWN_GLASS_IMG));
				brimg = ImageIO.read(new File(Chess.RESOURCES_DIR + Chess.BLACK_ROOK_GLASS_IMG));
				bnimg = ImageIO.read(new File(Chess.RESOURCES_DIR + Chess.BLACK_KNIGHT_GLASS_IMG));
				bbimg = ImageIO.read(new File(Chess.RESOURCES_DIR + Chess.BLACK_BISHOP_GLASS_IMG));
				bqimg = ImageIO.read(new File(Chess.RESOURCES_DIR + Chess.BLACK_QUEEN_GLASS_IMG));
				bkimg = ImageIO.read(new File(Chess.RESOURCES_DIR + Chess.BLACK_KING_GLASS_IMG));
				wpimg = ImageIO.read(new File(Chess.RESOURCES_DIR + Chess.WHITE_PAWN_GLASS_IMG));
				wrimg = ImageIO.read(new File(Chess.RESOURCES_DIR + Chess.WHITE_ROOK_GLASS_IMG));
				wnimg = ImageIO.read(new File(Chess.RESOURCES_DIR + Chess.WHITE_KNIGHT_GLASS_IMG));
				wbimg = ImageIO.read(new File(Chess.RESOURCES_DIR + Chess.WHITE_BISHOP_GLASS_IMG));
				wqimg = ImageIO.read(new File(Chess.RESOURCES_DIR + Chess.WHITE_QUEEN_GLASS_IMG));
				wkimg = ImageIO.read(new File(Chess.RESOURCES_DIR + Chess.WHITE_KING_GLASS_IMG));
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "Failed to load image resources.", "Error", JOptionPane.ERROR_MESSAGE);
				System.exit(1);
			}
		}
		//Load option 2 images (wooden board and pieces)
		else if(Chess.BOARD_OPTION == BoardOption.WOODEN) {
			try {
				bpimg = ImageIO.read(new File(Chess.RESOURCES_DIR + Chess.BLACK_PAWN_WOOD_IMG));
				brimg = ImageIO.read(new File(Chess.RESOURCES_DIR + Chess.BLACK_ROOK_WOOD_IMG));
				bnimg = ImageIO.read(new File(Chess.RESOURCES_DIR + Chess.BLACK_KNIGHT_WOOD_IMG));
				bbimg = ImageIO.read(new File(Chess.RESOURCES_DIR + Chess.BLACK_BISHOP_WOOD_IMG));
				bqimg = ImageIO.read(new File(Chess.RESOURCES_DIR + Chess.BLACK_QUEEN_WOOD_IMG));
				bkimg = ImageIO.read(new File(Chess.RESOURCES_DIR + Chess.BLACK_KING_WOOD_IMG));
				wpimg = ImageIO.read(new File(Chess.RESOURCES_DIR + Chess.WHITE_PAWN_WOOD_IMG));
				wrimg = ImageIO.read(new File(Chess.RESOURCES_DIR + Chess.WHITE_ROOK_WOOD_IMG));
				wnimg = ImageIO.read(new File(Chess.RESOURCES_DIR + Chess.WHITE_KNIGHT_WOOD_IMG));
				wbimg = ImageIO.read(new File(Chess.RESOURCES_DIR + Chess.WHITE_BISHOP_WOOD_IMG));
				wqimg = ImageIO.read(new File(Chess.RESOURCES_DIR + Chess.WHITE_QUEEN_WOOD_IMG));
				wkimg = ImageIO.read(new File(Chess.RESOURCES_DIR + Chess.WHITE_KING_WOOD_IMG));
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "Failed to load image resources.", "Error", JOptionPane.ERROR_MESSAGE);
				System.exit(1);
			}
		}
		//Load option 1 images (default) (black and white board and pieces)
		else {
			try {
				bpimg = ImageIO.read(new File(Chess.RESOURCES_DIR + Chess.BLACK_PAWN_BW_IMG));
				brimg = ImageIO.read(new File(Chess.RESOURCES_DIR + Chess.BLACK_ROOK_BW_IMG));
				bnimg = ImageIO.read(new File(Chess.RESOURCES_DIR + Chess.BLACK_KNIGHT_BW_IMG));
				bbimg = ImageIO.read(new File(Chess.RESOURCES_DIR + Chess.BLACK_BISHOP_BW_IMG));
				bqimg = ImageIO.read(new File(Chess.RESOURCES_DIR + Chess.BLACK_QUEEN_BW_IMG));
				bkimg = ImageIO.read(new File(Chess.RESOURCES_DIR + Chess.BLACK_KING_BW_IMG));
				wpimg = ImageIO.read(new File(Chess.RESOURCES_DIR + Chess.WHITE_PAWN_BW_IMG));
				wrimg = ImageIO.read(new File(Chess.RESOURCES_DIR + Chess.WHITE_ROOK_BW_IMG));
				wnimg = ImageIO.read(new File(Chess.RESOURCES_DIR + Chess.WHITE_KNIGHT_BW_IMG));
				wbimg = ImageIO.read(new File(Chess.RESOURCES_DIR + Chess.WHITE_BISHOP_BW_IMG));
				wqimg = ImageIO.read(new File(Chess.RESOURCES_DIR + Chess.WHITE_QUEEN_BW_IMG));
				wkimg = ImageIO.read(new File(Chess.RESOURCES_DIR + Chess.WHITE_KING_BW_IMG));
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "Failed to load image resources.", "Error", JOptionPane.ERROR_MESSAGE);
				System.exit(1);
			}
		}
		
		//Make image backgrounds transparent
		makeTransparent(bpimg, 0xFF00FF00);
		makeTransparent(brimg, 0xFF00FF00);
		makeTransparent(bnimg, 0xFF00FF00);
		makeTransparent(bbimg, 0xFF00FF00);
		makeTransparent(bqimg, 0xFF00FF00);
		makeTransparent(bkimg, 0xFF00FF00);
		makeTransparent(wpimg, 0xFF00FF00);
		makeTransparent(wrimg, 0xFF00FF00);
		makeTransparent(wnimg, 0xFF00FF00);
		makeTransparent(wbimg, 0xFF00FF00);
		makeTransparent(wqimg, 0xFF00FF00);
		makeTransparent(wkimg, 0xFF00FF00);
	}
	
	/**
	 * Makes any pixels that match the specified transparency color in the specified image transparent.
	 */
	public static BufferedImage makeTransparent(BufferedImage img, int transparencyColor) {
		for(int i = 0; i < img.getWidth(); ++i) {
			for(int j = 0; j < img.getHeight(); ++j) {
				if(img.getRGB(i, j) == transparencyColor) {
					img.setRGB(i, j, 0x00000000);
				}
			}
		}
		
		return img;
	}
	
	/**
	 * Maximize or restore the chess window.
	 */
	public static void maximize() {
		//Maximize the window
		if((cf.getExtendedState() & Frame.MAXIMIZED_BOTH) != Frame.MAXIMIZED_BOTH) {
			wndloc = cf.getLocation();
			wndsize = cf.getSize();
			cf.setExtendedState(cf.getExtendedState() | Frame.MAXIMIZED_BOTH);
		}
		//Restore to previous size and location
		else {
			cf.setExtendedState(Frame.NORMAL);
			cf.setLocation(wndloc);
			cf.setSize(wndsize);
		}
		
		cf.repaint();
	}
	
	/**
	 * Minimize the chess window.
	 */
	public static void minimize() {
		//Maximize the window
		if((cf.getState() & Frame.ICONIFIED) != Frame.ICONIFIED) 
			cf.setState(cf.getState() | Frame.ICONIFIED);
		
		cf.repaint();
	}
	
	/**
	 * Starts a new game of chess. Player may select their piece color and time control options and then the board is reset and a new game begins.
	 */
	public static void newGame() {
		new NewGameDialog();
	}
	
	/**
	 * Repaints the main window.
	 */
	public static void repaint() { cf.repaint(); }
	
	/**
	 * Sets the board option being used. The board can either be a black and white set, wooden set, or glass set (if supported).
	 * @param option - the board option to use
	 */
	public static synchronized void setBoardOption(BoardOption option) {
		BOARD_OPTION = option;
		loadImages();
		cf.repaint();
	}
	
	/**
	 * Sets the progress update listener.
	 * @param listener - the progress update listener
	 */
	public static synchronized void setProgressUpdateListener(ProgressUpdateListener listener) {
		progressListener = listener;
	}
	
	/**
	 * Shutdown the Chess program.
	 */
	public static void shutdown() {
		//Exit program
		System.exit(0);
	}
	
	/**
	 * Convert the specified image to a buffered image.
	 * @param img - the image to convert
	 * @return image converted to a buffered image
	 */
	public static BufferedImage toBufferedImage(Image img)
	{
	    if (img instanceof BufferedImage)
	        return (BufferedImage) img;

	    //Create a buffered image with transparency
	    BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

	    //Draw the image on to the buffered image
	    Graphics2D g2 = bimage.createGraphics();
	    g2.drawImage(img, 0, 0, null);
	    g2.dispose();

	    //Return the buffered image
	    return bimage;
	}
}
