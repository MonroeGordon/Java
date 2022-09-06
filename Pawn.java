package com.chess.pieces;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.BitSet;

import com.chess.Chess;
import com.chess.ChessBoard;
import com.chess.ThreatLine;
import com.chess.nan.itf.event.NANActionEvent;

/**
 * Pawn represents a black pawn chess piece and handles its movement and state in the game.
 * @author Monroe Gordon
 * @since 5/30/2022
 */
public class Pawn extends ChessPiece {
	
	/** En passant move flag. */
	private boolean enpassant;
	/** Pawn number. */
	private int number;

	/**
	 * Default constructor for the Pawn that calls the parent constructor to initialize variables.
	 */
	public Pawn() {
		super();
		enpassant = false;
		number = 1;
	}
	
	/**
	 * Constructor for the Pawn that calls the parent constructor to initialize variables based on the specified row and column position.
	 * @param clr - the color of this chess piece
	 * @param row - the row of the board (0 - 7)
	 * @param col - the column of the board (0 - 7)
	 * @param num - the pawn number (1 - 8)
	 */
	public Pawn(boolean clr, int row, int col, int num) {
		super(clr, row, col);
		
		if(num < 1 || num > 8)
			throw new IllegalArgumentException("Pawn: number must be between 1 - 8");
		
		enpassant = false;
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
		int ix = Math.abs(x - getPositionPoint().x);
		int iy = Math.abs(y - getPositionPoint().y);
		int sx = (int)Math.signum(x - getPositionPoint().x);
		int sy = (int)Math.signum(y - getPositionPoint().y);
		boolean legal = false;
		
		//Return if space is invalid
		if(y < 0 || y >= ChessBoard.BOARD_ROWS || x < 0 || x >= ChessBoard.BOARD_COLS) return legal;
		
		//Set next space to check
		space.set(x + (y * ChessBoard.BOARD_COLS));
		
		//Check if space is blocked by another piece
		for(int n = 0; n < pieces.length; ++n) {
			//Space is blocked if another piece is there or if it's diagonal from the pawn
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
				
				//If space is diagonal from pawn
				if(ix == iy) {
					//Create diagonal threat line
					for(int j = 0; j < ix; ++j) {
						spaces[j] = new Point(getPositionPoint().x + (j * sx), getPositionPoint().y + (j * sy));
					}
				}
				//If space left/right of pawn
				else if(iy > 0) {
					//Check for en passant attack
					if(pieces[n].isPawn() && pieces[n].getMoveCount() == 1 && pieces[n].getTotalSpacesMoved() == 2) {
						//Create a threat line to the opponent's pawn
						spaces[0].x = getPositionPoint().x;
						spaces[0].y = getPositionPoint().y;
						
						ThreatLine threat = new ThreatLine(this, spaces);
						pieces[n].addThreatLine(threat);
					}
				}
			}
			
			//Track if this space has been blocked and/or if it is blocked by an opponent
			b |= blocked;
			o |= opponent;
		}
		
		//Check if space is not blocked by another piece and is not diagonal to the pawn or it is blocked by an opponent piece and is diagonal to the pawn
		legal = (!b && (ix != iy)) || (b && o && !k && (ix == iy));
		
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
		ChessPiece[] pieces = Chess.getChessBoard().getAllPieces();
		boolean legal = false;
		
		//Reset legal moves
		legalMoves.clear();
		
		//If pawn is not on the board or no legal moves was returned, return no legal moves
		if(getPosition().cardinality() == 0 || blocks == ChessBoard.NO_LEGAL_MOVES)
			return legalMoves;
		
		//If this is the pawn's first move
		if(!hasMoved()) {
			//Check two spaces in front of the pawn
			legal = checkSpace(pieces,  getPositionPoint().x, getPositionPoint().y - 1);
			if(legal) checkSpace(pieces,  getPositionPoint().x, getPositionPoint().y - 2);
			
			//Check if pawn can attack an opponent in the upper-left/right space
			checkSpace(pieces,  getPositionPoint().x - 1, getPositionPoint().y - 1);
			checkSpace(pieces,  getPositionPoint().x + 1, getPositionPoint().y - 1);
		}
		//If this is not the pawn's first move
		else {
			//Check space in front of the pawn
			checkSpace(pieces,  getPositionPoint().x, getPositionPoint().y - 1);
			
			//Check if pawn can attack an opponent in the upper-left/right space
			checkSpace(pieces,  getPositionPoint().x - 1, getPositionPoint().y - 1);
			checkSpace(pieces,  getPositionPoint().x + 1, getPositionPoint().y - 1);
			
			//Check for en passant attack on the left/right
			checkSpace(pieces,  getPositionPoint().x - 1, getPositionPoint().y);
			checkSpace(pieces,  getPositionPoint().x + 1, getPositionPoint().y);
		}
		
		//If this pawn is the only piece blocking a threat line
		if(blocks > ChessBoard.NO_THREAT_LINE_INDEX) {
			//Only allow legal moves that are along the threat line
			legalMoves.and(king.getThreatLine(blocks).getLine());
		}
		
		//Return legal moves
		return legalMoves;
	}
	
	/**
	 * Returns if this pawn's previous move was an en passant attack.
	 * @return true if this pawn just made an en passant attack, false otherwise
	 */
	public boolean enPassant() { return enpassant; }
	
	@Override
	public BufferedImage getImage() { return color ? Chess.getWhitePawnImage() : Chess.getBlackPawnImage(); }
	
	@Override
	public boolean isKing() { return false; }
	
	@Override
	public boolean isPawn() { return true; }
	
	@Override
	public void performAction(NANActionEvent e) {
		//Decode action
		int a = e.getAction().get();
		
		//If action is to move this pawn
		if((color && ((a & (0x00000F00 & (number << 2))) == (a & 0x00000F00))) || (!color && ((a & (0x000001F00 & (number << 2))) == (a & 0x00001F00)))) {
			//Decode move position and move pawn to specified position
			int p = a & 0x000000FF;
			int i = p / ChessBoard.BOARD_ROWS;
			int j = p % ChessBoard.BOARD_COLS;
			move(i, j);
		}
	}
	
	//Return name of piece
	@Override
	public String toString() { return (color ? "White " : "Black ") + "Pawn"; }
}
