package com.chess.gui.panel;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;

import com.chess.Chess;
import com.chess.Chess.BoardOption;
import com.chess.ChessBoard;
import com.chess.ChessBoard.GameState;
import com.chess.gui.frame.ChessFrame;
import com.chess.pieces.ChessPiece;

/**
 * ChessBoardPanel creates and handles the chess board GUI.
 * @author Monroe Gordon
 * @since 5/27/2022
 */
public class ChessBoardPanel extends JPanel implements MouseInputListener {

	/** ChessBoardPanel serial version ID value. */
	private static final long serialVersionUID = 1451849349499551835L;
	
	/** Glass background color. */
	private static final Color GLASS_BG_COLOR = new Color(196, 196, 255, 127);
	/** White glass color. */
	private static final Color GLASS_WHITE_COLOR = new Color(196, 196, 255, 0);
	/** Black glass color. */
	private static final Color GLASS_BLACK_COLOR = new Color(0, 0, 0, 196);
	/** Glass font color. */
	private static final Color GLASS_FONT_COLOR = new Color(196, 196, 255, 127);
	
	/** Wood background color. */
	private static final Color WOOD_BG_COLOR = new Color(160, 130, 90, 255);
	/** White glass color. */
	private static final Color WOOD_WHITE_COLOR = new Color(160, 130, 90, 255);
	/** Black glass color. */
	private static final Color WOOD_BLACK_COLOR = new Color(101, 67, 33, 255);
	/** Wood font color. */
	private static final Color WOOD_FONT_COLOR = new Color(210, 180, 140, 255);
	
	/** Black and white background color. */
	private static final Color BW_BG_COLOR = new Color(255, 255, 255, 255);
	/** White glass color. */
	private static final Color BW_WHITE_COLOR = new Color(255, 255, 255, 255);
	/** Black glass color. */
	private static final Color BW_BLACK_COLOR = new Color(32, 32, 32, 255);
	/** Black and white font color. */
	private static final Color BW_FONT_COLOR = new Color(255, 255, 255, 255);
	
	/** Space highlight color. */
	private static final Color HIGHLIGHT_COLOR = new Color(0, 255, 255, 255);
	/** Piece selection highlight color. */
	private static final Color SELECTION_COLOR = new Color(0, 192, 240, 255);
	/** Legal move highlight color. */
	private static final Color LEGAL_MOVE_COLOR = new Color(0, 131, 163, 128);
	
	/** Chess clock color. */
	private static final Color CLOCK_COLOR = new Color(32, 32, 32, 255);
	/** Chess clock frame color. */
	private static final Color CLOCK_FRAME_COLOR = new Color(128, 128, 128, 255);
	/** Chess clock face color. */
	private static final Color CLOCK_FACE_COLOR = new Color(196, 196, 196, 255);
	/** Chess clock black button color. */
	private static final Color CLOCK_BLACK_COLOR = new Color(64, 64, 64, 255);
	/** Chess clock white button color. */
	private static final Color CLOCK_WHITE_COLOR = new Color(160, 160, 160, 255);
	/** Chess clock font color. */
	private static final Color CLOCK_FONT_COLOR = new Color(64, 64, 64, 255);
	
	/** Board offset value. */
	private int boardOffset;
	/** Board square size. */
	private int squareSize;
	/** Board square font. */
	private Font squareFont;
	/** Chess pieces. */
	private ChessPiece[] piece;
	/** Current mouse cursor position. */
	private Point mousePos;
	/** Board square positions. */
	private Rectangle[] boardSquare;
	/** Highlighted board square. */
	private Point highlight;
	/** Selected chess piece. */
	private ChessPiece selected;
	/** Chess clock rectangle. */
	private Rectangle chessClock;
	/** Black clock's string. */
	private String blackClockStr;
	/** White clock's string. */
	private String whiteClockStr;

	/**
	 * Default constructor for the ChessBoardPanel that creates chess board GUI.
	 */
	public ChessBoardPanel() {
		//Initialize variables
		squareSize = ChessFrame.DEF_CHESS_BOARD_HEIGHT / 12;
		boardOffset = squareSize * 2;
		squareFont = new Font("Arial", Font.PLAIN, 18);
		piece = Chess.getChessBoard().getAllPieces();
		mousePos = new Point(0, 0);
		boardSquare = new Rectangle[ChessBoard.BOARD_SPACES];
		highlight = null;
		selected = null;
		chessClock = new Rectangle(squareSize * 4, 0, squareSize * 4, squareSize);
		blackClockStr = "00:00:00";
		whiteClockStr = "00:00:00";
		
		for(int i = 0; i < ChessBoard.BOARD_ROWS; ++i) {
			for(int j = 0; j < ChessBoard.BOARD_COLS; ++j) 
				boardSquare[j + (i * ChessBoard.BOARD_COLS)] = new Rectangle(boardOffset + (j * squareSize), boardOffset + (i * squareSize), squareSize, squareSize);
		}
		
		//Initialize panel
		setSize(new Dimension(ChessFrame.DEF_CHESS_BOARD_WIDTH, ChessFrame.DEF_CHESS_BOARD_HEIGHT));
		
		//Add listeners
		addMouseListener(this);
		addMouseMotionListener(this);
		
		//Show panel
		setVisible(true);
	}
	
	//Paint the panel.
	@Override
	public void paintComponent(Graphics g) {
		//Convert g to 2d
		Graphics2D g2 = (Graphics2D)g;
		
		//Recalculate square size
		squareSize = getHeight() / 12;
		
		//Recalculate board offset
		boardOffset = squareSize * 2;
		
		//Recalculate chess clock bounds
		chessClock.setBounds(squareSize * 4, 0, squareSize * 4, squareSize);
		
		//Draw glass board
		if(Chess.BOARD_OPTION == BoardOption.GLASS) {
			drawBoard(g2, GLASS_BLACK_COLOR, GLASS_WHITE_COLOR, GLASS_BG_COLOR, GLASS_FONT_COLOR, Chess.GLASS_IMG_WIDTH, Chess.GLASS_IMG_HEIGHT);
		}
		//Draw wooden board
		else if(Chess.BOARD_OPTION == BoardOption.WOODEN) {
			drawBoard(g2, WOOD_BLACK_COLOR, WOOD_WHITE_COLOR, WOOD_BG_COLOR, WOOD_FONT_COLOR, Chess.WOOD_IMG_WIDTH, Chess.WOOD_IMG_HEIGHT);
		}
		//Draw black and white board
		else {
			drawBoard(g2, BW_BLACK_COLOR, BW_WHITE_COLOR, BW_BG_COLOR, BW_FONT_COLOR, Chess.BW_IMG_WIDTH, Chess.BW_IMG_HEIGHT);
		}
		
		//Dispose of graphics object
		g2.dispose();
	}
	
	/**
	 * Draw the chess board and pieces with the specified colors.
	 * @param g2 - the 2D graphics object
	 * @param black - the black board space color
	 * @param white - the white board space color
	 * @param background - the background color
	 * @param font - the font color
	 */
	private void drawBoard(Graphics2D g2, Color black, Color white, Color background, Color font, int imgWidth, int imgHeight) {
		//Clear panel
		g2.setBackground(background);
		g2.clearRect(0, 0, getWidth(), getHeight());
		
		//Draw top and bottom borders with letters
		for(int i = 0; i < 10; ++i) {
			g2.setColor(black);
			g2.fillRect(squareSize + (i * squareSize), squareSize, squareSize, squareSize);
			g2.fillRect(squareSize + (i * squareSize), squareSize * 10, squareSize, squareSize);
			
			if(i > 0 && i < 9) {
				g2.setColor(font);
				g2.setFont(squareFont);
				Rectangle2D fontSize = squareFont.getStringBounds("" + (char)('a' + i - 1) + "", g2.getFontRenderContext());
				g2.drawString("" + (char)('a' + i - 1) + "", squareSize + (i * squareSize) + (squareSize / 2) - (int)fontSize.getCenterX(), 
						squareSize  + (squareSize / 2) - (int)fontSize.getCenterY());
				g2.drawString("" + (char)('a' + i - 1) + "", squareSize + (i * squareSize) + (squareSize / 2) - (int)fontSize.getCenterX(), 
						squareSize * 10 + (squareSize / 2) - (int)fontSize.getCenterY());
			}
		}
		
		//Draw side borders with numbers and the board squares
		for(int i = 0; i < ChessBoard.BOARD_ROWS + 2; ++i) {
			for(int j = 0; j < ChessBoard.BOARD_COLS; ++j) {
				//Set size border color
				if(i == 0 || i == ChessBoard.BOARD_ROWS + 1) {
					g2.setColor(black);
				}
				//Set board square color based on position
				else if(i % 2 == 0) {
					if(j % 2 == 0)
						g2.setColor(black);
					else
						g2.setColor(white);
				}
				else {
					if(j % 2 == 1)
						g2.setColor(black);
					else
						g2.setColor(white);
				}
				
				//Fill square with current color
				g2.fillRect(squareSize + (i * squareSize), boardOffset + (j * squareSize), squareSize, squareSize);
				
				//Draw side border numbers
				if(i == 0 || i == ChessBoard.BOARD_ROWS + 1) {
					g2.setColor(font);
					g2.setFont(squareFont);
					Rectangle2D fontSize = squareFont.getStringBounds("" + (8 - j) + "", g2.getFontRenderContext());
					g2.drawString("" + (8 - j) + "", squareSize + (i * squareSize) + (squareSize / 2) - (int)fontSize.getCenterX(), 
							boardOffset + (j * squareSize) + (squareSize / 2) - (int)fontSize.getCenterY());
				}
			}
		}
		
		//Draw all chess pieces
		for(int i = 0; i < piece.length; ++i) {
			g2.drawImage(piece[i].getImage(), 
					piece[i].getPositionPoint().x * squareSize + boardOffset, piece[i].getPositionPoint().y * squareSize + boardOffset, 
					piece[i].getPositionPoint().x * squareSize + squareSize + boardOffset, piece[i].getPositionPoint().y * squareSize + squareSize + boardOffset, 
					0, 0, imgWidth, imgHeight, null);
		}
		
		//Draw the chess clock at the top of the board
		g2.setColor(CLOCK_COLOR);
		g2.fillRoundRect(squareSize * 4, squareSize / 3, squareSize * 4, squareSize - (squareSize / 3) - 2, squareSize / 8, squareSize / 8);
		g2.setColor(CLOCK_FRAME_COLOR);
		g2.fillRoundRect((squareSize * 4) + (squareSize / 8), (squareSize / 3) + (squareSize / 8), (squareSize * 2) - ((squareSize / 8) * 2), 
				squareSize - (squareSize / 3) - ((squareSize / 8) * 2) - 2, squareSize / 8, squareSize / 8);
		g2.fillRoundRect((squareSize * 6) + (squareSize / 8), (squareSize / 3) + (squareSize / 8), (squareSize * 2) - ((squareSize / 8) * 2), 
				squareSize - (squareSize / 3) - ((squareSize / 8) * 2) - 2, squareSize / 8, squareSize / 8);
		g2.setColor(CLOCK_FACE_COLOR);
		g2.fillRect((squareSize * 4) + ((squareSize / 8) * 2), (squareSize / 3) + (int)((squareSize / 8) * 1.5), (squareSize * 2) - ((squareSize / 8) * 4),
				squareSize - (squareSize / 3) - ((squareSize / 8) * 3) - 2);
		g2.fillRect((squareSize * 6) + ((squareSize / 8) * 2), (squareSize / 3) + (int)((squareSize / 8) * 1.5), (squareSize * 2) - ((squareSize / 8) * 4),
				squareSize - (squareSize / 3) - ((squareSize / 8) * 3) - 2);
		g2.setColor(CLOCK_FONT_COLOR);
		
		//Draw chess clock strings
		g2.setFont(new Font("Monospaced", Font.PLAIN, 12 + 
				((((double)getWidth() / (double)ChessFrame.DEF_SCREEN_WIDTH) > (int)(getWidth() / ChessFrame.DEF_SCREEN_WIDTH) &&
				((double)getWidth() / (double)ChessFrame.DEF_SCREEN_WIDTH) > 1.0) ?
				(int)(getWidth() / ChessFrame.DEF_SCREEN_WIDTH) + 1 : (int)(getWidth() / ChessFrame.DEF_SCREEN_WIDTH))));
		FontMetrics metrics = g2.getFontMetrics();
		int strlen = metrics.charsWidth(whiteClockStr.toCharArray(), 0, 8);
		int strheight = metrics.getHeight();
		g2.drawString(whiteClockStr, (squareSize * 4) + ((squareSize / 8) * 2) + (squareSize - ((squareSize / 8) * 2)) - (strlen / 2), 
				(squareSize / 3) + (int)((squareSize / 8) * 2) + (strheight / 2));
		g2.drawString(blackClockStr, (squareSize * 6) + ((squareSize / 8) * 2) + (squareSize - ((squareSize / 8) * 2)) - (strlen / 2), 
				(squareSize / 3) + (int)((squareSize / 8) * 2) + (strheight / 2));
		
		//If a game is not in session, draw both clock buttons in off state
		if(Chess.getChessBoard().getGameState() != GameState.PLAYING) {
			g2.setColor(CLOCK_WHITE_COLOR);
			g2.fillRect((squareSize * 5) - (squareSize / 2), 5, squareSize, (squareSize / 3) - 5);
			g2.setColor(CLOCK_BLACK_COLOR);
			g2.fillRect((squareSize * 7) - (squareSize / 2), 5, squareSize, (squareSize / 3) - 5);
		}
		//If a game is in session
		else {
			//If it's white's turn, draw white's clock button in on state
			if(Chess.getChessBoard().getTurn() == ChessBoard.WHITE) {
				g2.setColor(CLOCK_WHITE_COLOR);
				g2.fillRect((squareSize * 5) - (squareSize / 2), 5 + (squareSize / 6), squareSize, (squareSize / 6) - 5);
				g2.setColor(CLOCK_BLACK_COLOR);
				g2.fillRect((squareSize * 7) - (squareSize / 2), 5, squareSize, (squareSize / 3) - 5);
			}
			//If it's black's turn, draw black's clock button in on state
			else {
				g2.setColor(CLOCK_WHITE_COLOR);
				g2.fillRect((squareSize * 5) - (squareSize / 2), 5, squareSize, (squareSize / 3) - 5);
				g2.setColor(CLOCK_BLACK_COLOR);
				g2.fillRect((squareSize * 7) - (squareSize / 2), 5 + (squareSize / 6), squareSize, (squareSize / 6) - 5);
			}
			
			//If a square is highlighted
			if(highlight != null) {
				//Draw highlight around the square
				g2.setColor(HIGHLIGHT_COLOR);
				g2.setStroke(new BasicStroke((int)(squareSize * 0.1)));
				g2.drawRect(boardOffset + (highlight.x * squareSize), boardOffset + (highlight.y * squareSize), squareSize, squareSize);
			}
			
			//If a player's piece is selected on the player's turn
			if(Chess.getChessBoard().getTurn() == Chess.getChessBoard().getPlayerColor() && selected != null) {
				//Draw selection highlight around the square the piece is on
				g2.setColor(SELECTION_COLOR);
				g2.setStroke(new BasicStroke((int)(squareSize * 0.1)));
				g2.drawRect(boardOffset + (selected.getPositionPoint().x * squareSize), boardOffset + (selected.getPositionPoint().y * squareSize), squareSize, squareSize);
				
				//Highlight selected piece's legal moves
				ArrayList<Point> legalMoves = selected.getLegalMovesPoints();
				g2.setColor(LEGAL_MOVE_COLOR);
				
				for(int i = 0; i < legalMoves.size(); ++i) {
					g2.fillRect(boardOffset + (legalMoves.get(i).x * squareSize), boardOffset + (legalMoves.get(i).y * squareSize), squareSize, squareSize);
				}
			}
		}
	}

	//Handle mouse clicked
	@Override
	public void mouseClicked(MouseEvent e) {
		//If left mouse button is clicked
		if(e.getButton() == MouseEvent.BUTTON1) {
			//Check if clock was clicked on
			if(chessClock.contains(mousePos)) {
				//Switch turns (or start game)
				if(Chess.getChessBoard().getGameState() == GameState.PLAYING) {
					Chess.getChessBoard().nextTurn();
				}
			}
			//Check if a board square was clicked on
			else {
				for(int i = 0; i < boardSquare.length; ++i) {
					//If board square was clicked on
					if(boardSquare[i].contains(mousePos)) {
						//If no piece is selected
						if(selected == null) {
							//If a player's piece is clicked on, set selected to that piece
							selected = Chess.getChessBoard().getPieceOnSquare(new Point(i % ChessBoard.BOARD_COLS, i / ChessBoard.BOARD_ROWS));
							
							if(selected != null && selected.getColor() != Chess.getChessBoard().getPlayerColor())
								selected = null;
						}
						//If a piece is selected
						else {
							//If the board square is a legal move for the selected piece
							ArrayList<Point> legal = selected.getLegalMovesPoints();
							Rectangle legalSquare = new Rectangle();
							
							for(int j = 0; j < legal.size(); ++j) {
								legalSquare.setBounds(boardOffset + (squareSize * legal.get(j).x), boardOffset + (squareSize * legal.get(j).y), squareSize, squareSize);
								
								if(boardSquare[i].contains(legalSquare)) {
									//Move the piece to that square
									selected.move(i % ChessBoard.BOARD_COLS, i / ChessBoard.BOARD_ROWS);
									selected = null;
								}
							}
						}
					}
				}
			}
		}
		//If the any other mouse button is clicked
		else {
			//Check if clock was clicked on
			if(chessClock.contains(mousePos)) {
				//If game is in session
				if(Chess.getChessBoard().getGameState() == GameState.PLAYING)
					//Pause the game
					Chess.getChessBoard().pauseGame();
				//If game is paused
				else if(Chess.getChessBoard().getGameState() == GameState.PAUSED)
					//Continue the game
					Chess.getChessBoard().resumeGame();
			}
			
			//Clear selected
			selected = null;
		}
		
		//Repaint
		repaint();
	}
	
	//Handle mouse dragged
	@Override
	public void mouseDragged(MouseEvent e) {
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

	//Handle mouse preseed
	@Override
	public void mousePressed(MouseEvent e) {
	}

	//Handle mouse released
	@Override
	public void mouseReleased(MouseEvent e) {
	}

	//Handle mouse moved
	@Override
	public void mouseMoved(MouseEvent e) {
		//Update mouse position
		mousePos = e.getPoint();
		
		//Set mouse cursor to default cursor
		setCursor(Cursor.getDefaultCursor());
		
		//Highlight board space if mouse is over it
		highlight = null;
		
		for(int i = 0; i < boardSquare.length; ++i) {
			if(boardSquare[i].contains(mousePos)) 
				highlight = new Point(i % ChessBoard.BOARD_COLS, i / ChessBoard.BOARD_ROWS);
		}
		
		//Repaint
		repaint();
	}
	
	/**
	 * Updates the black clock's string with the black clock's current time in HH:MM:SS format.
	 * @param time - black clock's current time
	 */
	public void updateBlackClock(int time) {
		int hours = time / 3600;
		int mins = (time - (hours * 3600)) / 60;
		int secs = (time - (hours * 3600) - (mins * 60));
		
		synchronized(blackClockStr) {
			blackClockStr = String.format("%02d:%02d:%02d", hours, mins, secs);
		}
		
		repaint();
	}
	
	/**
	 * Updates the white clock's string with the white clock's current time in HH:MM:SS format.
	 * @param time - white clock's current time
	 */
	public void updateWhiteClock(int time) {
		int hours = time / 3600;
		int mins = (time - (hours * 3600)) / 60;
		int secs = (time - (hours * 3600) - (mins * 60));
		
		synchronized(whiteClockStr) {
			whiteClockStr = String.format("%02d:%02d:%02d", hours, mins, secs);
		}
		
		repaint();
	}
}
