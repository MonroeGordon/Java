package com.chess.pieces;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.BitSet;

import com.chess.Chess;
import com.chess.ChessBoard;
import com.chess.ThreatLine;
import com.chess.nan.itf.event.NANActionEvent;

/**
 * Knight represents a black pawn chess piece and handles its movement and state in the game.
 * @author Monroe Gordon
 * @since 5/30/2022
 */
public class Knight extends ChessPiece {
	
	/** Knight number. */
	int number;

	/**
	 * Default constructor for the Knight that calls the parent constructor to initialize variables.
	 */
	public Knight() {
		super();
		
		number = 1;
	}
	
	/**
	 * Constructor for the Knight that calls the parent constructor to initialize variables based on the specified row and column position.
	 * @param clr - the color of this chess piece
	 * @param row - the row of the board (0 - 7)
	 * @param col - the column of the board (0 - 7)
	 * @param num - the knight number
	 */
	public Knight(boolean clr, int row, int col, int num) {
		super(clr, row, col);
		
		if(num < 1 || num > 2)
			throw new IllegalArgumentException("Knight: number must be between 1 - 2");
		
		number = num;
	}
	
	@Override
	public boolean checkSpace(ChessPiece[] pieces, int x, int y) {
		//Initialize variables
		BitSet space = new BitSet(ChessBoard.BOARD_SPACES);
		boolean blocked = false;
		boolean opponent = false;
		boolean b = false;
		boolean o = false;
		boolean k = false;
		boolean legal = false;
		
		//Check specified space
		if(x >= 0 && x < ChessBoard.BOARD_COLS && y >= 0 && y < ChessBoard.BOARD_ROWS) {
			space.set(x + (y * ChessBoard.BOARD_COLS));
			
			for(int i = 0; i < pieces.length; ++i) {
				blocked = space.intersects(pieces[i].getPosition());
				opponent = false;
				
				//Check if blocking piece is an opponent piece
				if(blocked)
					opponent = pieces[i].getColor() != color;
				
				//If blocking piece is an opponent
				if(opponent) {
					//Check if opponent piece is the king
					k = pieces[i].isKing();
					
					//Create a threat line and add it to the opponent piece
					Point[] spaces = new Point[1];
					
					spaces[0] = new Point(getPositionPoint().x, getPositionPoint().y);
					
					ThreatLine threat = new ThreatLine(this, spaces);
					pieces[i].addThreatLine(threat);
				}
				
				//Track if this space has been blocked and/or if it is blocked by an opponent
				b |= blocked;
				o |= opponent;
			}
		
			//Check if space is not blocked by another piece or it is blocked by an opponent piece
			legal = (!b || (b && o && !k));
			
			//If space is legal, add to legal moves
			if(legal)
				legalMoves.set(x + (y * ChessBoard.BOARD_COLS));
		}
		
		//Return legal
		return legal;
	}
	
	@Override
	public BitSet findLegalMoves(ChessBoard board) {
		//Initialize variables
		int blocks = Chess.getChessBoard().checkKing(this);
		King king = color ? Chess.getChessBoard().getWhiteKing() : Chess.getChessBoard().getBlackKing();
		ChessPiece[] pieces = Chess.getChessBoard().getAllPieces();
		
		//Reset legal moves
		legalMoves.clear();
		
		//If knight is not on the board or no legal moves was returned, return no legal moves
		if(getPosition().cardinality() == 0 || blocks == ChessBoard.NO_LEGAL_MOVES)
			return legalMoves;
		
		//Check knight's upper-left L space
		checkSpace(pieces, getPositionPoint().x - 1, getPositionPoint().y - 2);
		
		//Check knight's left-upper L space
		checkSpace(pieces, getPositionPoint().x - 2, getPositionPoint().y - 1);
		
		//Check knight's left-bottom L space
		checkSpace(pieces, getPositionPoint().x - 2, getPositionPoint().y + 1);
		
		//Check knight's bottom-left L space
		checkSpace(pieces, getPositionPoint().x - 1, getPositionPoint().y + 2);
		
		//Check knight's bottom-right L space
		checkSpace(pieces, getPositionPoint().x + 1, getPositionPoint().y + 2);
		
		//Check knight's right-bottom L space
		checkSpace(pieces, getPositionPoint().x + 2, getPositionPoint().y + 1);
		
		//Check knight's right-upper L space
		checkSpace(pieces, getPositionPoint().x + 2, getPositionPoint().y - 1);
		
		//Check knight's upper-right L space
		checkSpace(pieces, getPositionPoint().x + 1, getPositionPoint().y - 2);
		
		//If this knight is the only piece blocking a threat line
		if(blocks > ChessBoard.NO_THREAT_LINE_INDEX) {
			//Only allow legal moves that are along the threat line
			legalMoves.and(king.getThreatLine(blocks).getLine());
		}
		
		//Return legal moves
		return legalMoves;
	}
	
	@Override
	public BufferedImage getImage() { return color ? Chess.getWhiteKnightImage() : Chess.getBlackKnightImage(); }
	
	@Override
	public boolean isKing() { return false; }
	
	@Override
	public boolean isPawn() { return false; }
	
	@Override
	public void performAction(NANActionEvent e) {
		//Decode action
		int a = e.getAction().get();
		
		//If action is to move this knight
		if((color && ((a & (0x00000F00 & ((number + 10) << 2))) == (a & 0x00000F00))) || (!color && ((a & (0x000001F00 & ((number + 10) << 2))) == (a & 0x00001F00)))) {
			//Decode move position and move knight to specified position
			int p = a & 0x000000FF;
			int i = p / ChessBoard.BOARD_ROWS;
			int j = p % ChessBoard.BOARD_COLS;
			move(i, j);
		}
	}
	
	//Return name of piece
	@Override
	public String toString() { return (color ? "White " : "Black ") + "Knight"; }
}
