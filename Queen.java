package com.chess.pieces;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.BitSet;

import com.chess.Chess;
import com.chess.ChessBoard;
import com.chess.ThreatLine;
import com.chess.nan.itf.event.NANActionEvent;

/**
 * Queen represents a black pawn chess piece and handles its movement and state in the game.
 * @author Monroe Gordon
 * @since 5/30/2022
 */
public class Queen extends ChessPiece {

	/**
	 * Default constructor for the Queen that calls the parent constructor to initialize variables.
	 */
	public Queen() {
		super();
	}
	
	/**
	 * Constructor for the Queen that calls the parent constructor to initialize variables based on the specified row and column position.
	 * @param clr - the color of this chess piece
	 * @param row - the row of the board (0 - 7)
	 * @param col - the column of the board (0 - 7)
	 */
	public Queen(boolean clr, int row, int col) {
		super(clr, row, col);
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
			
			//Check if blocking piece is an opponent piece
			if(blocked)
				opponent = pieces[n].getColor() != color;
			
			//If blocking piece is an opponent
			if(opponent) {
				//Check if opponent piece is the king
				k = pieces[n].isKing();
				
				//Create a threat line and add it to the opponent piece
				Point[] spaces = new Point[((ix == iy) ? ix : (ix + iy))];
				
				//If space is diagonal from queen
				if(ix == iy) {
					//Create diagonal threat line
					for(int j = 0; j < ix; ++j) {
						spaces[j] = new Point(getPositionPoint().x + (j * sx), getPositionPoint().y + (j * sy));
					}
				}
				//If space is not diagonal from queen
				else {
					//Create horizontal threat line, if space is left/right of queen
					for(int j = 0; j < ix; ++j) {
						spaces[j] = new Point(getPositionPoint().x, getPositionPoint().y + (j * sy));
					}
					
					//Create vertical threat line, if space is above/below the queen
					for(int j = 0; j < iy; ++j) {
						spaces[j] = new Point(getPositionPoint().x + (j * sx), getPositionPoint().y);
					}
				}
				
				ThreatLine threat = new ThreatLine(this, spaces);
				pieces[n].addThreatLine(threat);
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
		
		//Return legal
		return legal;
	}
	
	@Override
	public BitSet findLegalMoves(ChessBoard board) {
		//Initialize variables
		int blocks = Chess.getChessBoard().checkKing(this);
		King king = color ? Chess.getChessBoard().getWhiteKing() : Chess.getChessBoard().getBlackKing();
		
		//Reset legal moves
		legalMoves.clear();
		
		//If queen is not on the board or no legal moves was returned, return no legal moves
		if(getPosition().cardinality() == 0 || blocks == ChessBoard.NO_LEGAL_MOVES)
			return legalMoves;
		
		//Check diagonal and straight paths
		Chess.getChessBoard().checkDiagonalPaths(this);
		Chess.getChessBoard().checkStraightPaths(this);
		
		//If this queen is the only piece blocking a threat line
		if(blocks > ChessBoard.NO_THREAT_LINE_INDEX) {
			//Only allow legal moves that are along the threat line
			legalMoves.and(king.getThreatLine(blocks).getLine());
		}
		
		//Return legal moves
		return legalMoves;
	}
	
	@Override
	public BufferedImage getImage() { return color ? Chess.getWhiteQueenImage() : Chess.getBlackQueenImage(); }
	
	@Override
	public boolean isKing() { return false; }
	
	@Override
	public boolean isPawn() { return false; }
	
	@Override
	public void performAction(NANActionEvent e) {
		//Decode action
		int a = e.getAction().get();
		
		//If action is to move this queen
		if((color && ((a & 0x00000F00) == 0x00000F00)) || (!color && ((a & 0x000001F00) == 0x00001F00))) {
			//Decode move position and move pawn to specified position
			int p = a & 0x000000FF;
			int i = p / ChessBoard.BOARD_ROWS;
			int j = p % ChessBoard.BOARD_COLS;
			move(i, j);
		}
	}
	
	//Return name of piece
	@Override
	public String toString() { return (color ? "White " : "Black ") + "Queen"; }
}
