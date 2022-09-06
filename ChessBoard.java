package com.chess;

import java.awt.Point;
import java.util.ArrayList;
import java.util.BitSet;

import com.chess.pieces.Bishop;
import com.chess.pieces.ChessPiece;
import com.chess.pieces.King;
import com.chess.pieces.Knight;
import com.chess.pieces.Pawn;
import com.chess.pieces.Queen;
import com.chess.pieces.Rook;

/**
 * ChessBoard contains and handles the current state of the chess board and chess pieces.
 * @author Monroe Gordon
 * @since 5/30/2022
 */
public class ChessBoard {
	
	/**
	 * GameState enum contains the values of all possible game states during a game of chess (or none if no game is in session).
	 * @author Monroe Gordon
	 * @since 5/30/2022
	 */
	public enum GameState {
		/** No game in session. */
		NONE,
		/** Game in play. */
		PLAYING,
		/** Game paused. */
		PAUSED,
		/** Player wins. */
		PLAYER_WINS,
		/** NAN wins. */
		NAN_WINS,
		/** Game is a draw. */
		DRAW
	}
	
	/**
	 * KingState enum conatins the values of all possible king states during a game of chess.
	 * @author Monroe Gordon
	 * @since 5/30/2022
	 */
	public enum KingState {
		/** King is safe. */
		SAFE,
		/** King is in check. */
		CHECKED,
		/** King is checkmated. */
		CHECKMATED,
		/** King is stalemated. */
		STALEMATED
	}
	
	/** Boolean value representing black color. */
	public static final boolean BLACK = false;
	/** Boolean value representing white color. */
	public static final boolean WHITE = true;
	
	/** Number of spaces on the board. */
	public static final int BOARD_SPACES = 64;
	/** Number of rows on the board. */
	public static final int BOARD_ROWS = 8;
	/** Number of columns on the board. */
	public static final int BOARD_COLS = 8;
	
	/** No legal moves return value. */
	public static final int NO_LEGAL_MOVES = -2;
	/** No threat line index return index. */
	public static final int NO_THREAT_LINE_INDEX = -1;
	
	/** The player's color. */
	private boolean player;
	/** The NAN's color. */
	private boolean nan;
	/** The current player's turn flag. */
	private boolean turn;
	/** The current move number. */
	private int moveNum;
	/** The current game state. */
	private GameState gameState;
	/** The black king's state. */
	private KingState blackKingState;
	/** The white king's state. */
	private KingState whiteKingState;

	/** Black pawn pieces. */
	private Pawn blackPawn[];
	/** Black rook pieces. */
	private Rook blackRook[];
	/** Black knight pieces. */
	private Knight blackKnight[];
	/** Black bishop pieces. */
	private Bishop blackBishop[];
	/** Black queen piece. */
	private Queen blackQueen;
	/** Black king piece. */
	private King blackKing;
	
	/** White pawn pieces. */
	private Pawn whitePawn[];
	/** White rook pieces. */
	private Rook whiteRook[];
	/** White knight pieces. */
	private Knight whiteKnight[];
	/** White bishop pieces. */
	private Bishop whiteBishop[];
	/** White queen piece. */
	private Queen whiteQueen;
	/** White king piece. */
	private King whiteKing;
	
	/** All pieces. */
	private ChessPiece pieces[];
	
	/**
	 * Default constructor for the ChessBoard that initializes the board with white on bottom and black on top.
	 */
	public ChessBoard() {
		//Initialize variables
		player = WHITE;
		nan = BLACK;
		turn = BLACK;
		moveNum = 0;
		gameState = GameState.NONE;
		blackKingState = KingState.SAFE;
		whiteKingState = KingState.SAFE;
		blackPawn = new Pawn[8];
		blackRook = new Rook[2];
		blackKnight = new Knight[2];
		blackBishop = new Bishop[2];
		blackQueen = new Queen(BLACK, 3, 0);
		blackKing = new King(BLACK, 4, 0);
		whitePawn = new Pawn[8];
		whiteRook = new Rook[2];
		whiteKnight = new Knight[2];
		whiteBishop = new Bishop[2];
		whiteQueen = new Queen(WHITE, 3, 7);
		whiteKing = new King(WHITE, 4, 7);
		pieces = new ChessPiece[32];
		
		for(int i = 0; i < 8; ++i) {
			blackPawn[i] = new Pawn(BLACK, i, 1, i + 1);
			whitePawn[i] = new Pawn(WHITE, i, 6, i + 1);
			
			if(i < 2) {
				blackRook[i] = new Rook(BLACK, (i * 7), 0, i + 1);
				blackKnight[i] = new Knight(BLACK, 1 + (i * 5), 0, i + 1);
				blackBishop[i] = new Bishop(BLACK, 2 + (i * 3), 0, i + 1);
				whiteRook[i] = new Rook(WHITE, (i * 7), 7, i + 1);
				whiteKnight[i] = new Knight(WHITE, 1 + (i * 5), 7, i + 1);
				whiteBishop[i] = new Bishop(WHITE, 2 + (i * 3), 7, i + 1);
			}
		}
		
		for(int i = 0; i < 8; ++i) {
			pieces[i] = blackPawn[i];
			pieces[i + 8] = whitePawn[i];
			
			if(i < 2) {
				pieces[i + 16] = blackRook[i];
				pieces[i + 18] = blackKnight[i];
				pieces[i + 20] = blackBishop[i];
				pieces[i + 22] = whiteRook[i];
				pieces[i + 24] = whiteKnight[i];
				pieces[i + 26] = whiteBishop[i];
			}
		}
		
		pieces[28] = blackQueen;
		pieces[29] = blackKing;
		pieces[30] = whiteQueen;
		pieces[31] = whiteKing;
	}
	
	/**
	 * Return a list of all the chess pieces.
	 * @return a list of all the chess pieces
	 */
	public ChessPiece[] getAllPieces() { return pieces; }
	
	/**
	 * This checks the diagonal paths (top-left, top-right, bottom-right, and bottom-left diagonal) from the position of the specified piece to locate legal moves based on
	 * the position of all other pieces.
	 * @param piece - the piece to check
	 */
	public void checkDiagonalPaths(ChessPiece piece) {
		//Initialize variables
		ChessPiece[] pieces = getAllPieces();
		int i = 0;
		boolean legal = true;
		
		//Check top-left diagonal
		i = 1;
		legal = true;
			
		while(legal && piece.getPositionPoint().y - i >= 0 && piece.getPositionPoint().x - i >= 0) {
			//Check next space
			legal = piece.checkSpace(pieces, piece.getPositionPoint().x - i, piece.getPositionPoint().y - i);
			if(legal) ++i;
		}
		
		//Check top-right diagonal
		i = 1;
		legal = true;
		
		while(legal && piece.getPositionPoint().y - i >= 0 && piece.getPositionPoint().x + i < ChessBoard.BOARD_COLS) {
			//Check next space
			legal = piece.checkSpace(pieces, piece.getPositionPoint().x + i, piece.getPositionPoint().y - i);
			if(legal) ++i;
		}
		
		//Check bottom-right diagonal
		i = 1;
		legal = true;
		
		while(legal && piece.getPositionPoint().y + i < ChessBoard.BOARD_ROWS && piece.getPositionPoint().x + i < ChessBoard.BOARD_COLS) {
			//Check next space
			legal = piece.checkSpace(pieces, piece.getPositionPoint().x + i, piece.getPositionPoint().y + i);
			if(legal) ++i;
		}
		
		//Check bottom-left diagonal
		i = 1;
		legal = true;
		
		while(legal && piece.getPositionPoint().y + i < ChessBoard.BOARD_ROWS && piece.getPositionPoint().x - i >= 0) {
			//Check next space
			legal = piece.checkSpace(pieces, piece.getPositionPoint().x - i, piece.getPositionPoint().y + i);
			if(legal) ++i;
		}
	}
	
	/**
	 * Check if the specified piece is unable to move due to its king having unblocked threat lines, or if the piece can only move along the threat line it alone is blocking
	 * for its king.
	 * @param piece - the piece to check
	 * @return NO_LEGAL_MOVES if the king has multiple threat lines blocked by the piece, or if one of the king's threat lines is unblocked; NO_THREAT_INDEX if the piece is
	 * not blocking a threat line alone or if the king has no threat lines; the index of the threat line that the piece is blocking alone
	 */
	public int checkKing(ChessPiece piece) {
		//Initialize variables
		ChessPiece[] pieces = Chess.getChessBoard().getAllPieces();
		King king = piece.getColor() ? Chess.getChessBoard().getWhiteKing() : Chess.getChessBoard().getBlackKing();
		boolean intersects = false;
		boolean blocking = false;
		boolean blocked = false;
		int i = 0;
		int blockers = 0;
		ArrayList<Integer> blocks = new ArrayList<Integer>(0);
		
		//If this pawn's king has multiple threat lines
		if(king.getThreatCount() > 1) {
			//Check if the threat lines are blocked by other pieces
			do {
				//Reset blocking, blocked and blockers
				blocking = false;
				blocked = false;
				blockers = 0;
				
				//Check if a piece's position intersects with the current threat line
				for(int j = 0; j < pieces.length; ++j) {
					intersects = pieces[j].getPosition().intersects(king.getThreatLine(i).getLine());
					blocked |= intersects;
					
					//If this threat line is intersected by another piece
					if(intersects) {
						//Increment number of blockers
						blockers++;
						
						//If the only intersecting piece is this pawn then set blocking
						blocking = pieces[j].getPosition().equals(piece.getPosition()) && (blockers == 1);
					}
				}
				
				//If this pawn is the only piece blocking this threat line
				if(blocking)
					//Add current threat line index to the list of blocks
					blocks.add(i);
				
				//Increment i
				++i;
			} while(blocked && i < king.getThreatCount());
			
			//If a threat line is not blocked or if this pawn is the only piece blocking multiple threat lines, return no legal moves
			if(!blocked || blocks.size() > 1)
				return NO_LEGAL_MOVES;
		}
		
		//If the piece is blocking a threat line, return the threat line's index, else return no threat line index
		return (blocks.size() > 0) ? blocks.get(0) : NO_THREAT_LINE_INDEX;
	}
	
	/**
	 * Checks both kings' legal moves to prevent overlapping legal moves, meaning moves that would cause a king to check a king.
	 */
	public void checkKingVsKing() {
		//Create a set that contains the same legal moves from the kings, if any
		BitSet kingsMoves = (BitSet)whiteKing.getLegalMoves().clone();
		kingsMoves.and(blackKing.getLegalMoves());
		
		//Remove any moves that overlap with the set of same legal moves
		whiteKing.getLegalMoves().xor(kingsMoves);
		blackKing.getLegalMoves().xor(kingsMoves);
	}
	
	/**
	 * This checks the straight paths (top, right, bottom, and left) from the position of the specified piece to locate legal moves based on the position of all other pieces.
	 * @param piece - the piece to check
	 */
	public void checkStraightPaths(ChessPiece piece) {
		//Initialize variables
		ChessPiece[] pieces = getAllPieces();
		int i = 0;
		boolean legal = true;
		
		//Check top path
		i = 1;
		legal = true;
			
		while(legal && piece.getPositionPoint().y - i >= 0) {
			//Check next space
			legal = piece.checkSpace(pieces, piece.getPositionPoint().x, piece.getPositionPoint().y - i);
			if(legal) ++i;
		}
		
		//Check right path
		i = 1;
		legal = true;
		
		while(legal && piece.getPositionPoint().x + i < ChessBoard.BOARD_COLS) {
			//Check next space
			legal = piece.checkSpace(pieces, piece.getPositionPoint().x + i, piece.getPositionPoint().y);
			if(legal) ++i;
		}
		
		//Check bottom path
		i = 1;
		legal = true;
		
		while(legal && piece.getPositionPoint().y + i < ChessBoard.BOARD_ROWS) {
			//Check next space
			legal = piece.checkSpace(pieces, piece.getPositionPoint().x, piece.getPositionPoint().y + i);
			if(legal) ++i;
		}
		
		//Check left path
		i = 1;
		legal = true;
		
		while(legal && piece.getPositionPoint().x - i >= 0) {
			//Check next space
			legal = piece.checkSpace(pieces, piece.getPositionPoint().x - i, piece.getPositionPoint().y);
			if(legal) ++i;
		}
	}
	
	/**
	 * Return the specified black bishop piece.
	 * @param index - the index of the black bishop
	 * @return the black bishop at the specified index
	 */
	public Bishop getBlackBishop(int index) { 
		if(index < 0 || index > 1)
			throw new IndexOutOfBoundsException("Black bishop index must be 0 or 1");
		return blackBishop[index]; 
	}
	
	/**
	 * Return the black king piece.
	 * @return the black king piece
	 */
	public King getBlackKing() { return blackKing; }
	
	/**
	 * Return the current state of the black king.
	 * @return the black king's state
	 */
	public KingState getBlackKingState() { return blackKingState; }
	
	/**
	 * Return the specified black knight piece.
	 * @param index - the index of the black knight
	 * @return the black knight at the specified index
	 */
	public Knight getBlackKnight(int index) { 
		if(index < 0 || index > 1)
			throw new IndexOutOfBoundsException("Black knight index must be 0 or 1");
		return blackKnight[index]; 
	}
	
	/**
	 * Return the specified black pawn piece.
	 * @param index - the index of the black pawn
	 * @return the black pawn at the specified index
	 */
	public Pawn getBlackPawn(int index) { 
		if(index < 0 || index > 7)
			throw new IndexOutOfBoundsException("Black knight index must be between 0 and 7");
		return blackPawn[index]; 
	}
	
	/**
	 * Return the black queen piece.
	 * @return the black queen piece
	 */
	public Queen getBlackQueen() { return blackQueen; }
	
	/**
	 * Return the specified black rook piece.
	 * @param index - the index of the black rook
	 * @return the black rook at the specified index
	 */
	public Rook getBlackRook(int index) { 
		if(index < 0 || index > 1)
			throw new IndexOutOfBoundsException("Black rook index must be between 0 and 1");
		return blackRook[index]; 
	}
	
	/**
	 * Return the current game state.
	 * @return the game state
	 */
	public GameState getGameState() { return gameState; }
	
	/**
	 * Return the current move number.
	 * @return the current move number
	 */
	public int getMoveNumber() { return moveNum; }
	
	/**
	 * Return the color of the NAN's pieces.
	 * @return the color of the NAN's pieces
	 */
	public boolean getNANColor() { return nan; }
	
	/**
	 * Returns the piece that is on the specified board square, or null if the board square is empty.
	 * @param square - the board square to check
	 * @return the piece of the specified board square, or null if the board square is empty
	 */
	public ChessPiece getPieceOnSquare(Point square) {
		for(int i = 0; i < pieces.length; ++i) {
			if(pieces[i].getPositionPoint().equals(square)) return pieces[i];
		}
		
		return null;
	}
	
	/**
	 * Return the color of the player's pieces.
	 * @return the color of the player's pieces
	 */
	public boolean getPlayerColor() { return player; }
	
	/**
	 * Return the color of the current turn.
	 * @return the color of the current turn
	 */
	public boolean getTurn() { return turn; }
	
	/**
	 * Return the specified white bishop piece.
	 * @param index - the index of the white bishop
	 * @return the white bishop at the specified index
	 */
	public Bishop getWhiteBishop(int index) { 
		if(index < 0 || index > 1)
			throw new IndexOutOfBoundsException("	 bishop index must be 0 or 1");
		return whiteBishop[index]; 
	}
	
	/**
	 * Return the white king piece.
	 * @return the white king piece
	 */
	public King getWhiteKing() { return whiteKing; }
	
	/**
	 * Return the current state of the white king.
	 * @return the white king's state
	 */
	public KingState getWhiteKingState() { return whiteKingState; }
	
	/**
	 * Return the specified white knight piece.
	 * @param index - the index of the white knight
	 * @return the white knight at the specified index
	 */
	public Knight getWhiteKnight(int index) { 
		if(index < 0 || index > 1)
			throw new IndexOutOfBoundsException("White knight index must be 0 or 1");
		return whiteKnight[index]; 
	}
	
	/**
	 * Return the specified white pawn piece.
	 * @param index - the index of the white pawn
	 * @return the white pawn at the specified index
	 */
	public Pawn getWhitePawn(int index) { 
		if(index < 0 || index > 7)
			throw new IndexOutOfBoundsException("White knight index must be between 0 and 7");
		return whitePawn[index]; 
	}
	
	/**
	 * Return the white queen piece.
	 * @return the white queen piece
	 */
	public Queen getWhiteQueen() { return whiteQueen; }
	
	/**
	 * Return the specified white rook piece.
	 * @param index - the index of the white rook
	 * @return the white rook at the specified index
	 */
	public Rook getWhiteRook(int index) { 
		if(index < 0 || index > 1)
			throw new IndexOutOfBoundsException("White rook index must be between 0 and 1");
		return whiteRook[index]; 
	}
	
	/**
	 * Starts and runs a new game of chess.
	 * @param playerColor - the human player's piece color for this game.
	 */
	public void newGame(boolean playerColor) {
		//Set player colors
		player = playerColor;
		nan = !playerColor;
		
		//Reset the board
		resetBoard();
		
		//Set game state to PAUSED
		gameState = GameState.PAUSED;
		
		//Setup first turn
		nextTurn();
	}
	
	/**
	 * Processes the NAN's turn.
	 */
	public void NANTurn() {
		
	}
	
	/**
	 * Switches the players' turns and re-evaluates legal moves and game state.
	 */
	public void nextTurn() {
		//Switch turns
		turn = !turn;
		
		//If it's white's turn again, increment move number
		if(turn == WHITE) moveNum++;
		
		//Find pieces current legal moves for this turn
		for(int i = 0; i < pieces.length; ++i)
			pieces[i].findLegalMoves(this);
		
		//Check for king vs king move possibility
		checkKingVsKing();
		
		//Repaint the chess board
		Chess.repaint();
		
		//If it's the NAN's turn
		if(turn == nan)
			NANTurn();
	}
	
	/**
	 * Informs the chess board that a piece was moved and it's time to switch turns.
	 */
	public void pieceMoved() {
		//Switch clocks and start next move
		Chess.getChessClock().switchTurn();
		nextTurn();
	}
	
	/**
	 * Pauses the current game if one is in session by changing the game state from playing to paused.
	 */
	public void pauseGame() {
		//If a game is in session
		if(gameState == GameState.PLAYING) {
			//Set game state to paused
			gameState = GameState.PAUSED;
			
			//Pause clocks
			Chess.getChessClock().pauseClocks();
			
			//Repaint the board
			Chess.repaint();
		}
	}
	
	/**
	 * Resets the chess board to the start of a new game, with the human player's pieces positioned at the bottom of the board.
	 */
	private void resetBoard() {
		//If player is white, put white pieces at the bottom and black at the top
		if(player == WHITE) {
			blackQueen.setPosition(3, 0);
			blackKing.setPosition(4, 0);
			whiteQueen.setPosition(3, 7);
			whiteKing.setPosition(4, 7);
			
			for(int i = 0; i < 8; ++i) {
				blackPawn[i].setPosition(i, 1);
				whitePawn[i].setPosition(i, 6);
				
				if(i < 2) {
					blackRook[i].setPosition((i * 7), 0);
					blackKnight[i].setPosition(1 + (i * 5), 0);
					blackBishop[i].setPosition(2 + (i * 3), 0);
					whiteRook[i].setPosition((i * 7), 7);
					whiteKnight[i].setPosition(1 + (i * 5), 7);
					whiteBishop[i].setPosition(2 + (i * 3), 7);
				}
			}
		}
		//If player is black, put black pieces at the bottom and white at the top
		else {
			whiteQueen.setPosition(3, 0);
			whiteKing.setPosition(4, 0);
			blackQueen.setPosition(3, 7);
			blackKing.setPosition(4, 7);
			
			for(int i = 0; i < 8; ++i) {
				whitePawn[i].setPosition(i, 1);
				blackPawn[i].setPosition(i, 6);
				
				if(i < 2) {
					whiteRook[i].setPosition((i * 7), 0);
					whiteKnight[i].setPosition(1 + (i * 5), 0);
					whiteBishop[i].setPosition(2 + (i * 3), 0);
					blackRook[i].setPosition((i * 7), 7);
					blackKnight[i].setPosition(1 + (i * 5), 7);
					blackBishop[i].setPosition(2 + (i * 3), 7);
				}
			}
		}
	}
	
	/**
	 * Resumes the current game by changing the state from paused to playing.
	 */
	public void resumeGame() {
		//If a game is paused
		if(gameState == GameState.PAUSED) {
			//Set game state to playing
			gameState = GameState.PLAYING;
			
			//Resume clocks
			Chess.getChessClock().resumeClocks();
			
			//Repaint the board
			Chess.repaint();
		}
	}
	
	/**
	 * Set the current state of the black king.
	 * @param state - the black king's state
	 */
	public void setBlackKingState(KingState state) {
		if(state != null) blackKingState = state;
	}
	
	/**
	 * Sets the current game state.
	 * @param state - the new game state
	 */
	public void setGameState(GameState state) { gameState = state; }
	
	/**
	 * Sets the player's piece color and sets the NAN's piece color to the opposite.
	 * @param color - the player's piece color
	 */
	public void setPlayerColor(boolean color) {
		player = color;
		nan = !player;
	}
	
	/**
	 * Set the current state of the white king.
	 * @param state - the white king's state
	 */
	public void setWhiteKingState(KingState state) {
		if(state != null) whiteKingState = state;
	}
}
