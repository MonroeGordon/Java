package com.chess.pieces;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.BitSet;

import com.chess.Chess;
import com.chess.ChessBoard;
import com.chess.ThreatLine;
import com.chess.nan.itf.event.listener.NANActionListener;

/**
 * ChessPiece is the base class for all chess pieces.
 * @author Monroe Gordon
 * @since 5/30/2022
 */
public abstract class ChessPiece implements NANActionListener {

	/** The color of the chess piece. */
	protected boolean color;
	/** The chess piece's board position. */
	protected BitSet position;
	/** Chess piece move counter. */
	protected int moveCount;
	/** Spaces the chess piece moved last turn. */
	protected int spacesMoved;
	/** The total number of spaces this chess piece has moved throughout the game currently. */
	protected int totalSpacesMoved;
	/** The chess piece's legal moves. */
	protected BitSet legalMoves;
	/** Threat lines that are threatening the chess piece. */
	protected ArrayList<ThreatLine> threat;
	
	/**
	 * Default constructor for the ChessPiece that initializes class variables.
	 */
	public ChessPiece() {
		//Initialize variables
		color = ChessBoard.WHITE;
		position = new BitSet(ChessBoard.BOARD_SPACES);
		moveCount = 0;
		spacesMoved = 0;
		totalSpacesMoved = 0;
		legalMoves = new BitSet(ChessBoard.BOARD_SPACES);
		threat = new ArrayList<ThreatLine>(0);
	}
	
	/**
	 * Constructor for the ChessPiece that initializes class variables and sets position based on the specified y and column of the board.
	 * @param clr - the color of this chess piece
	 * @param x - the x position of the board (0 - 7)
	 * @param y = the y position of the board (0 - 7)
	 */
	public ChessPiece(boolean clr, int x, int y) {
		//Check parameters
		if(y < 0 || y >= ChessBoard.BOARD_ROWS || x < 0 || x >= ChessBoard.BOARD_COLS)
			throw new IllegalArgumentException("ChessPiece @ setPosition: y and x must be a value between 0 and 7.");
		
		//Initialize variables
		color = clr;
		position = new BitSet(ChessBoard.BOARD_SPACES);
		moveCount = 0;
		spacesMoved = 0;
		totalSpacesMoved = 0;
		legalMoves = new BitSet(ChessBoard.BOARD_SPACES);
		threat = new ArrayList<ThreatLine>(0);
		
		position.set(x + (y * ChessBoard.BOARD_COLS));
	}
	
	/**
	 * Add a threat line to the current list of threat lines for this chess piece.
	 * @param t - the threat line to add
	 */
	public void addThreatLine(ThreatLine t) {
		if(t != null) threat.add(t);
	}
	
	/**
	 * Checks the space at the specified x and y coordinates to see if the piece can legally move there.
	 * @param pieces - all the chess pieces
	 * @param y - the y board space coordinate
	 * @param x - the x board space coordinate
	 * @return true if the space checked is inaccessible to this piece, false otherwise
	 */
	public abstract boolean checkSpace(ChessPiece[] pieces, int x, int y);
	
	/**
	 * Removes all threat lines from this chess piece. This is performed at the start of each player's turn before legal moves and new threat lines are re-evaluated.
	 */
	public void clearThreatLines() { threat.clear(); }
	
	/**
	 * Find the legal moves this chess piece can make based on the current chess board.
	 * @param board - the current chess board
	 * @return the legal moves this chess piece can make
	 */
	public abstract BitSet findLegalMoves(ChessBoard board);
	
	/**
	 * Return the color of this chess piece (black or white).
	 * @return the color of this chess piece
	 */
	public boolean getColor() { return color; }
	
	/**
	 * Return the image of this chess piece.
	 * @return the image of this chess piece
	 */
	public abstract BufferedImage getImage();
	
	/**
	 * Return the board position of this chess piece.
	 * @return the board position of this chess piece
	 */
	public BitSet getPosition() { return position; }
	
	/**
	 * Return the board position of this chess piece as a point on the board.
	 * @return the position of this chess piece as a point on the board
	 */
	public Point getPositionPoint() {
		//Get index of set position bit
		int pIndex = position.nextSetBit(0);
		
		//Convert index value to a point on the chess board
		return new Point(pIndex % ChessBoard.BOARD_COLS, pIndex / ChessBoard.BOARD_ROWS);
	}
	
	/**
	 * Return the legal moves this chess piece can make.
	 * @return the legal moves this chess piece can make
	 */
	public BitSet getLegalMoves() { return legalMoves; }
	
	/**
	 * Returns legal moves as a list of points on the board.
	 * @return legal moves as a list of points on the board
	 */
	public ArrayList<Point> getLegalMovesPoints() {
		//Initialize variables
		ArrayList<Point> moves = new ArrayList<Point>(0);
		int i = 0;
		
		//Convert all legal moves to board spaces
		while(i < ChessBoard.BOARD_SPACES && i != -1) {
			i = legalMoves.nextSetBit(i);
			
			if(i != -1) {
				moves.add(new Point(i % ChessBoard.BOARD_COLS, i / ChessBoard.BOARD_ROWS));
				i++;
			}
		}
		
		//Return moves
		return moves;
	}
	
	/**
	 * Returns the number of moves this chess piece has made in the current game.
	 * @return the number of moves this chess piece has made
	 */
	public int getMoveCount() { return moveCount; }
	
	/**
	 * Returns the number of spaces this chess piece moved last turn.
	 * @return the number of spaces this chess piece moved last turn
	 */
	public int getSpacesMoved() { return spacesMoved; }
	
	/**
	 * Returns the number of threat lines this chess piece has.
	 * @return the number of threat lines
	 */
	public int getThreatCount() { return threat.size(); }
	
	/**
	 * Return the threat line at the specified index.
	 * @param index - the index of the threat line to return
	 * @return the threat line at the specified index
	 */
	public ThreatLine getThreatLine(int index) {
		if(index < 0 || index >= threat.size())
			throw new IndexOutOfBoundsException("Threat line index out of bounds");
		
		return threat.get(index);
	}
	
	/**
	 * Returns the current list of threat lines.
	 * @return the list of threat lines
	 */
	public ArrayList<ThreatLine> getThreatLines() { return threat; }
	
	/**
	 * Returns the total number of spaces this chess piece has moved throughout the game currently.
	 * @return the total number of spaces this chess piece has moved throughout the game currently
	 */
	public int getTotalSpacesMoved() { return totalSpacesMoved; }
	
	/**
	 * Returns if this chess piece has ever moved yet in the current game (moveCount > 0).
	 * @return true if moveCount > 0, otherwise false
	 */
	public boolean hasMoved() { return moveCount > 0; }
	
	/**
	 * Returns if this chess piece is a king.
	 * @return true if this chess piece is a king, false otherwise
	 */
	public abstract boolean isKing();
	
	/**
	 * Returns if this chess piece is a pawn.
	 * @return true if this chess piece is a pawn, false otherwise
	 */
	public abstract boolean isPawn();
	
	/**
	 * Move this chess piece to the specified new position, if it is a legal move.
	 * @param x - the x position of the new position of this chess piece (0 - 7)
	 * @param y - the y position of the new position of this chess piece (0 - 7)
	 * @return true if the move is legal, otherwise false
	 */
	public boolean move(int x, int y) {
		//Check parameters
		if(y < 0 || y >= ChessBoard.BOARD_ROWS || x < 0 || x >= ChessBoard.BOARD_COLS)
			throw new IllegalArgumentException("ChessPiece @ setPosition: y and x must be a value between 0 and 7.");
		
		//Convert y and x to a position bitset
		BitSet newPos = new BitSet(ChessBoard.BOARD_SPACES);
		newPos.set(x + (y * ChessBoard.BOARD_COLS));
		
		//If newPos is a legal move
		if(newPos.intersects(legalMoves)) {
			//Set position to newPos, increment moveCount, and return true
			position = newPos;
			moveCount++;
			
			//Check if move captures an opponent's piece
			ChessPiece pieces[] = Chess.getChessBoard().getAllPieces();
			
			for(int i = 0; i < pieces.length; ++i) {
				if(pieces[i].getColor() != color && position.intersects(pieces[i].getPosition())) {
					//Remove opponent piece from board
					pieces[i].position.clear();
					
					//Remove any threat lines the opponent piece caused other pieces
					for(int j = 0; j < pieces.length; ++j) {
						if(j != i) removeThreatLine(pieces[i]);
					}
				}
			}
			
			//Inform the board that a piece has moved
			Chess.getChessBoard().pieceMoved();
			
			//Return true
			return true;
		}
		
		//Return false if newPos is an illegal move
		return false;
	}
	
	/**
	 * Remove any threat lines caused by the specified piece.
	 * @param piece - the piece to check for
	 */
	public void removeThreatLine(ChessPiece piece) {
		for(int i = threat.size() - 1; i >= 0; ++i) {
			if(threat.get(i).getThreat().equals(piece))
				threat.remove(i);
		}
	}
	
	/**
	 * Set this chess piece's position on the board based on the specified row and column of the board.
	 * @param x - the x position of the board (0 - 7)
	 * @param y = the y position of the board (0 - 7)
	 */
	public void setPosition(int x, int y) {
		if(y < 0 || y >= ChessBoard.BOARD_ROWS || x < 0 || x >= ChessBoard.BOARD_COLS)
			throw new IllegalArgumentException("ChessPiece @ setPosition: x and y must be a value between 0 and 7.");
		position.clear();
		position.set(x + (y * ChessBoard.BOARD_COLS));
	}
	
	/**
	 * Returns the name of this chess piece.
	 * @return the name of this chess piece
	 */
	@Override
	public abstract String toString();
}
