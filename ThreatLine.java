package com.chess;

import java.awt.Point;
import java.util.BitSet;

import com.chess.pieces.ChessPiece;

/**
 * ThreatLine contains the piece that is threatening another piece and the spaces that can be used to block the threat.
 * @author Monroe Gordon
 * @since 6/3/2022
 */
public class ThreatLine {

	/** The piece creating the threat. */
	private ChessPiece threat;
	/** The spaces creating the threat line. */
	private BitSet line;
	
	/**
	 * Constructor for the ThreatLine that sets the threat to the specified piece and creates the threat line from the specified spaces.
	 * @param piece - the piece creating the threat
	 * @param spaces - the spaces creating the threat line
	 */
	public ThreatLine(ChessPiece piece, Point[] spaces) {
		//Initialize variables
		threat = piece;
		line = new BitSet(ChessBoard.BOARD_SPACES);
		
		for(int i = 0; i < spaces.length; ++i) {
			if(spaces[i].x < 0 || spaces[i].x > ChessBoard.BOARD_ROWS - 1 || spaces[i].y < 0 || spaces[i].y > ChessBoard.BOARD_COLS - 1)
				throw new IndexOutOfBoundsException("Spaces must be within the chess board's bounds");
			
			line.set(spaces[i].y + (spaces[i].x * ChessBoard.BOARD_COLS));
		}
	}
	
	/**
	 * Returns the piece creating the threat.
	 * @return the piece creating the threat
	 */
	public ChessPiece getThreat() { return threat; }
	
	/**
	 * Returns the threat line. These are positions on the chess board between the threat piece and the threatened piece that can be used to block the threat.
	 * @return the threat line
	 */
	public BitSet getLine() { return line; }
}
