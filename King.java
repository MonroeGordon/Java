package com.chess.pieces;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.BitSet;

import com.chess.Chess;
import com.chess.ChessBoard;
import com.chess.ThreatLine;
import com.chess.ChessBoard.KingState;
import com.chess.nan.itf.event.NANActionEvent;

/**
 * King represents a black pawn chess piece and handles its movement and state in the game.
 * @author Monroe Gordon
 * @since 5/30/2022
 */
public class King extends ChessPiece {

	/**
	 * Default constructor for the King that calls the parent constructor to initialize variables.
	 */
	public King() {
		super();
	}
	
	/**
	 * Constructor for the King that calls the parent constructor to initialize variables based on the specified row and column position.
	 * @param clr - the color of this chess piece
	 * @param row - the row of the board (0 - 7)
	 * @param col - the column of the board (0 - 7)
	 */
	public King(boolean clr, int row, int col) {
		super(clr, row, col);
	}
	
	/**
	 * Add a threat line to the current list of threat lines for this king and update this king's state to checked.
	 * @param t - the threat line to add
	 */
	@Override
	public void addThreatLine(ThreatLine t) {
		if(t != null) 
			threat.add(t);
	}
	
	@Override
	public boolean checkSpace(ChessPiece[] pieces, int x, int y) {
		//Initialize variables
		BitSet space = new BitSet(ChessBoard.BOARD_SPACES);
		boolean blocked = false;
		boolean opponent = false;
		boolean threatened = false;
		boolean b = false;
		boolean o = false;
		boolean t = false;
		boolean k = false;
		int ix = Math.abs(x - getPositionPoint().x);
		int iy = Math.abs(y - getPositionPoint().y);
		int sx = (int)Math.signum(x - getPositionPoint().x);
		int sy = (int)Math.signum(y - getPositionPoint().y);
		boolean legal = false;
		
		//Set next space to check
		space.set(x + (y * ChessBoard.BOARD_COLS));
		
		//Check if space is blocked by another piece
		for(int n = 0; n < pieces.length; ++n) {
			blocked = space.intersects(pieces[n].getPosition());
			opponent = false;
			threatened = false;
			
			//Check if blocking piece is an opponent piece
			if(blocked)
				opponent = pieces[n].getColor() != color;
			
			//If blocking piece is an opponent
			if(opponent) {
				//Check if opponent piece is the king
				k = pieces[n].isKing();
				
				//Check if another threat line is being blocked by this opponent piece
				for(int m = 0; m < threat.size(); ++m)
					threatened = pieces[n].getPosition().intersects(threat.get(m).getLine());
				
				//If no threat line is being blocked by this opponent piece
				if(!threatened) {
					//Create a threat line and add it to the opponent piece
					Point[] spaces = new Point[((ix == iy) ? ix : (ix + iy))];
					
					//If space is diagonal from king
					if(ix == iy) {
						//Create diagonal threat line
						for(int j = 0; j < ix; ++j) {
							spaces[j] = new Point(getPositionPoint().x + (j * sx), getPositionPoint().y + (j * sy));
						}
					}
					//If space is not diagonal from king
					else {
						//Create horizontal threat line, if space is left/right of king
						for(int j = 0; j < ix; ++j) {
							spaces[j] = new Point(getPositionPoint().x, getPositionPoint().y + (j * sy));
						}
						
						//Create vertical threat line, if space is above/below the king
						for(int j = 0; j < iy; ++j) {
							spaces[j] = new Point(getPositionPoint().x + (j * sx), getPositionPoint().y);
						}
					}
				}
			}
			
			//Track if this space has been blocked, contains an opponent piece and/or is blocking a threat line
			b |= blocked;
			o |= opponent;
			t |= threatened;
		}
		
		//Check if space is not blocked by another piece or it is blocked by an opponent piece that is not blocking a threat line
		legal = !b || (b && o && !t && !k);
		
		//If space is legal, add to legal moves
		if(legal)
			legalMoves.set(x + (y * ChessBoard.BOARD_COLS));
		
		//Return legal
		return legal;
	}
	
	@Override
	public BitSet findLegalMoves(ChessBoard board) {
		//Initialize variables
		ChessPiece[] pieces = Chess.getChessBoard().getAllPieces();
		
		//Reset legal moves
		legalMoves.clear();
		
		//Check all spaces immediately surrounding the king
		if(getPositionPoint().y - 1 > 0)
			checkSpace(pieces, getPositionPoint().x, getPositionPoint().y - 1);
		
		if(getPositionPoint().x - 1 > 0 && getPositionPoint().y - 1 > 0)
			checkSpace(pieces, getPositionPoint().x - 1, getPositionPoint().y - 1);
		
		if(getPositionPoint().x - 1 > 0)
			checkSpace(pieces, getPositionPoint().x - 1, getPositionPoint().y);
		
		if(getPositionPoint().x - 1 > 0 && getPositionPoint().y + 1 < ChessBoard.BOARD_COLS)
			checkSpace(pieces, getPositionPoint().x - 1, getPositionPoint().y + 1);
		
		if(getPositionPoint().y + 1 < ChessBoard.BOARD_COLS)
			checkSpace(pieces, getPositionPoint().x, getPositionPoint().y + 1);
		
		if(getPositionPoint().x + 1 < ChessBoard.BOARD_ROWS && getPositionPoint().y + 1 < ChessBoard.BOARD_COLS)
			checkSpace(pieces, getPositionPoint().x + 1, getPositionPoint().y + 1);
		
		if(getPositionPoint().x + 1 < ChessBoard.BOARD_ROWS)
			checkSpace(pieces, getPositionPoint().x + 1, getPositionPoint().y);
		
		if(getPositionPoint().x + 1 < ChessBoard.BOARD_ROWS && getPositionPoint().y - 1 > 0)
			checkSpace(pieces, getPositionPoint().x + 1, getPositionPoint().y - 1);
		
		//Check if the king has not moved and can castle with one of its rooks
		if(!hasMoved()) {
			//Initialize blocked
			boolean blocked = false;
			
			//If the left rook has not moved
			Rook left = color ? Chess.getChessBoard().getWhiteRook(0) : Chess.getChessBoard().getBlackRook(0);
			
			if(!left.hasMoved()) {
				//Check if spaces between the king and rook are empty and not threatened by any opponent pieces
				for(int i = getPositionPoint().x - 1; i > left.getPositionPoint().x; --i) {
					for(int j = 0; j < pieces.length; ++j) {
						blocked |= pieces[j].getPositionPoint().equals(new Point(i, getPositionPoint().y)) || 
								((pieces[j].getColor() != color) && pieces[j].legalMoves.get(getPositionPoint().y + (i * ChessBoard.BOARD_COLS)));
					}
				}
				
				//If space is not blocked, then add left rook's position to legal moves
				if(!blocked)
					legalMoves.set(left.getPosition().nextSetBit(0));
			}
			
			//Reset blocked
			blocked = false;
			
			//If the right rook has not moved
			Rook right = color ? Chess.getChessBoard().getWhiteRook(1) : Chess.getChessBoard().getBlackRook(1);
			
			if(right.hasMoved()) {
				//Check if spaces between the king and rook are empty and not threatened by any opponent pieces
				for(int i = getPositionPoint().x + 1; i < right.getPositionPoint().x; ++i) {
					for(int j = 0; j < pieces.length; ++j) {
						blocked |= pieces[j].getPositionPoint().equals(new Point(i, getPositionPoint().y)) || 
								((pieces[j].getColor() != color) && pieces[j].legalMoves.get(getPositionPoint().y + (i * ChessBoard.BOARD_COLS)));
					}
				}
				
				//If space is not blocked, then add right rook's position to legal moves
				if(!blocked)
					legalMoves.set(right.getPosition().nextSetBit(0));
			}
		}
		
		//Return legal moves
		return legalMoves;
	}
	
	@Override
	public BufferedImage getImage() { return color ? Chess.getWhiteKingImage() : Chess.getBlackKingImage(); }
	
	@Override
	public boolean isKing() { return true; }
	
	@Override
	public boolean isPawn() { return false; }
	
	@Override
	public void performAction(NANActionEvent e) {
		//Decode action
		int a = e.getAction().get();
		
		//If action is to move this king
		if((color && ((a & 0x00001000) == 0x00001000)) || (!color && ((a & 0x000002000) == 0x00002000))) {
			//Decode move position and move king to specified position
			int p = a & 0x000000FF;
			int i = p / ChessBoard.BOARD_ROWS;
			int j = p % ChessBoard.BOARD_COLS;
			move(i, j);
		}
	}
	
	/**
	 * Remove any threat lines caused by the specified piece and if no threats remain, set this king's state to safe.
	 * @param piece - the piece to check for
	 */
	@Override
	public void removeThreatLine(ChessPiece piece) {
		for(int i = threat.size() - 1; i >= 0; ++i) {
			if(threat.get(i).getThreat().equals(piece))
				threat.remove(i);
		}
		
		if(threat.size() == 0) {
			if(color)
				Chess.getChessBoard().setWhiteKingState(KingState.SAFE);
			else
				Chess.getChessBoard().setBlackKingState(KingState.SAFE);
		}
	}
	
	//Return name of piece
	@Override
	public String toString() { return (color ? "White " : "Black ") + "King"; }
}
