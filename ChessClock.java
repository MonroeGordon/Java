package com.chess;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * ChessClock handles the black and white game clock timers. Chess clock stages are added to the chess clock to tell the clock how much time to give each player, how long of a
 * delay to have before starting each timer each turn, and/or how much of a time increment is earned after each player's turn. The stages are started and stopped based on the
 * current move number in the game. Several clock presets are available from the Handbook, Section C.02.7.3.22.
 * @author Monroe Gordon
 * @since 6/6/2022
 */
public class ChessClock implements ActionListener {
	
	/** Preset chess clocks enum. */
	public enum ClockPreset {
		/** 40 moves in 100 minutes, 20 moves in 50 minutes, 15 minutes and 30 seconds per move from move 1. */
		CLK_40_100_20_50_15_30_1,
		/** 40 moves in 90 minutes, 30 minutes and 30 seconds per move from move 1. */
		CLK_40_90_30_30_1,
		/** Game in 90 minutes and 30 seconds per move from move 1. */
		CLK_G90_30_1,
		/** 40 moves in 120 minutes, 20 moves in 60 minutes, 15 minutes and 30 seconds per move from move 61. */
		CLK_40_120_20_60_15_30,
		/** 40 moves in 120 minutes, 20 moves in 60 minutes, 30 minutes. */
		CLK_40_120_20_60_30,
		/** 40 moves in 120 minutes, 30 minutes. */
		CLK_40_120_30,
		/** Game in 60 minutes. */
		CLK_G60,
		/** 40 moves in 120 minutes, 60 minutes. */
		CLK_40_120_60,
		/** Game in 15 minutes and 10 seconds per move from move 1. */
		CLK_G15_10,
		/** Game in 15 minutes and 5 seconds per move from move 1. */
		CLK_G15_5,
		/** Game in 25 minutes and 10 seconds per move from move 1. */
		CLK_G25_10,
		/** Game in 25 minutes. */
		CLK_G25,
		/** Game in 5 minutes. */
		CLK_G5,
		/** Game in 5 minutes and 3 seconds per move from move 1. */
		CLK_G5_3,
		/** Game in 5 minutes and 2 seconds per move from move 1. */
		CLK_G5_2,
		/** Game in 3 minutes and 2 seconds per move from move 1. */
		CLK_G3_2,
		/** Game in 2 minutes and 1 second per move from move 1. */
		CLK_G2_1,
		/** Game in 1 minute. */
		CLK_G1
	}

	/** Chess clock stages. */
	private ArrayList<ChessClockStage> stage;
	/** Current clock stage index. */
	private int currStage;
	
	/**
	 * Default constructor for the ChessClock that initializes and empty list of clock stages, meaning no chess clock is being used.
	 */
	public ChessClock() {
		//Initialize variables
		stage = new ArrayList<ChessClockStage>(0);
		currStage = 0;
	}
	
	public ChessClock(ClockPreset clock) {
		//Initialize variables based on clock
		stage = new ArrayList<ChessClockStage>(0);
		currStage = 0;
		setClock(clock);
	}
	
	//Respond to delay timer expiration
	@Override
	public void actionPerformed(ActionEvent e) {
		//If black's turn
		if(Chess.getChessBoard().getTurn() == ChessBoard.BLACK) {
			//Start black's clock
			stage.get(currStage).startBlackClock();
		}
		//If white's turn
		else {
			//Start white's clock
			stage.get(currStage).startBlackClock();
		}
	}
	
	/**
	 * Returns a list of descriptions for each clock preset available.
	 * @return a list of descriptions for each clock preset
	 */
	public static String[] clockDescriptions() {
		//Initialize variables
		String desc[] = new String[19];
		
		desc[0] = "No time limit";
		desc[1] = "40 moves in 100m; next 20 moves in 50m; 15m for rest of game; +30s per move from move 1";
		desc[2] = "40 moves in 90m; 30m for rest of game; +30s per move from move 1";
		desc[3] = "90m for game; +30s per move from move 1";
		desc[4] = "40 moves in 120m; next 20 moves in 60m; 15m for rest of game; +30s per move from move 61";
		desc[5] = "40 moves in 120m; next 20 moves in 60m; 30m for rest of game";
		desc[6] = "40 moves in 120m; 60 mins for rest of game";
		desc[7] = "40 moves in 120m; 30 mins for rest of game";
		desc[8] = "60m for game";
		desc[9] = "25m for game; +10s per move from move 1";
		desc[10] = "25m for game";
		desc[11] = "15m for game; +10s per move from move 1";
		desc[12] = "15m for game; +5s per move from move 1";
		desc[13] = "5m for game";
		desc[14] = "5m for game; +3s per move from move 1";
		desc[15] = "5m for game; +2s per move from move 1";
		desc[16] = "3m for game; +2s per move from move 1";
		desc[17] = "2m for game; +1s per move from move 1";
		desc[18] = "1m for game";
		
		//Return descriptions
		return desc;
	}
	
	/**
	 * Returns the number of clock presets that are available.
	 * @return the number of clock presets
	 */
	public static int clockPresetCount() { return ClockPreset.values().length + 1; }
	
	/**
	 * Returns a list of chess clock presets.
	 * @return a list of chess clock presets
	 */
	public static String[] clockPresets() {
		//Initialize variables
		String preset[] = new String[19];
		
		preset[0] = "Leisure";
		preset[1] = "World Championship Match";
		preset[2] = "World Cup";
		preset[3] = "Classic";
		preset[4] = "Classic XL Incremented";
		preset[5] = "Classic XL";
		preset[6] = "Classic L+";
		preset[7] = "Classic L";
		preset[8] = "Rapid";
		preset[9] = "Rapid Fast Incremented";
		preset[10] = "Rapid Fast";
		preset[11] = "World Rapid Championships";
		preset[12] = "World Rapid +5";
		preset[13] = "Blitz";
		preset[14] = "Blitz +3";
		preset[15] = "Blitz +2";
		preset[16] = "World Blitz Championships";
		preset[17] = "Bullet";
		preset[18] = "Speeding Bullet";
		
		//Return clock presets
		return preset;
	}
	
	/**
	 * Returns the black clock currently in use.
	 * @return the black clock
	 */
	public Stopwatch getBlackClock() { return stage.get(currStage).getBlackClock(); }
	
	/**
	 * Returns the ClockPreset value at the specified index.
	 * @param index - the index of the ClockPreset value to return
	 * @return the ClockPreset value at index
	 */
	public static ClockPreset getClockPreset(int index) { 
		ClockPreset preset[] = ClockPreset.values();
		
		if(index < 0 || index >= preset.length)
			throw new IndexOutOfBoundsException("Clock preset index out of bounds");
		
		return preset[index]; 
	}
	
	/**
	 * Returns the white clock currently in use.
	 * @return the white clock
	 */
	public Stopwatch getWhiteClock() { return stage.get(currStage).getWhiteClock(); }
	
	/**
	 * Pauses both black and white clocks.
	 */
	public void pauseClocks() {
		stage.get(currStage).pauseBlackClock();
		stage.get(currStage).pauseWhiteClock();
	}
	
	/**
	 * Resumes the player's clock that was running before the game was paused.
	 */
	public void resumeClocks() {
		//If it's white's turn, start white's clock
		if(Chess.getChessBoard().getTurn() == ChessBoard.WHITE)
			stage.get(currStage).startWhiteClock();
		//If it's black's turn, start black's clock
		else
			stage.get(currStage).startBlackClock();
	}
	
	/**
	 * Sets the clock preset the chess clock will use
	 * @param clock - the clock preset to use
	 */
	public void setClock(ClockPreset clock) {
		switch(clock) {
		//40 moves in 100 minutes, 20 moves in 50 minutes, 15 minutes and 30 seconds per move from move 1
		case CLK_40_100_20_50_15_30_1:
			stage.add(new ChessClockStage(0, 40, 6000, 0, 30, true));
			stage.add(new ChessClockStage(41, 60, 3000, 0, 30, true));
			stage.add(new ChessClockStage(61, ChessClockStage.END_OF_GAME, 900, 0, 30, true));
			break;
		//40 moves in 90 minutes, 30 minutes and 30 seconds per move from move 1
		case CLK_40_90_30_30_1:
			stage.add(new ChessClockStage(0, 40, 5400, 0, 30, true));
			stage.add(new ChessClockStage(41, ChessClockStage.END_OF_GAME, 1800, 0, 30, true));
			break;
		//Game in 90 minutes and 30 seconds per move from move 1
		case CLK_G90_30_1:
			stage.add(new ChessClockStage(0, ChessClockStage.END_OF_GAME, 5400, 0, 30, true));
			break;
		//40 moves in 120 minutes, 20 moves in 60 minutes, 15 minutes and 30 seconds per move from move 61
		case CLK_40_120_20_60_15_30:
			stage.add(new ChessClockStage(0, 40, 6000, 0, 0, false));
			stage.add(new ChessClockStage(41, 60, 3600, 0, 0, false));
			stage.add(new ChessClockStage(61, ChessClockStage.END_OF_GAME, 900, 0, 30, false));
			break;
		//40 moves in 120 minutes, 20 moves in 60 minutes, 30 minutes
		case CLK_40_120_20_60_30:
			stage.add(new ChessClockStage(0, 40, 7200, 0, 0, false));
			stage.add(new ChessClockStage(41, 60, 3600, 0, 0, false));
			stage.add(new ChessClockStage(61, ChessClockStage.END_OF_GAME, 1800, 0, 0, false));
			break;
		//40 moves in 120 minutes, 30 minutes
		case CLK_40_120_30:
			stage.add(new ChessClockStage(0, 40, 7200, 0, 0, false));
			stage.add(new ChessClockStage(41, ChessClockStage.END_OF_GAME, 1800, 0, 0, false));
			break;
		//40 moves in 120 minutes, 60 minutes
		case CLK_G60:
			stage.add(new ChessClockStage(0, ChessClockStage.END_OF_GAME, 3600, 0, 0, false));
			break;
		//40 moves in 120 minutes, 60 minutes
		case CLK_40_120_60:
			stage.add(new ChessClockStage(0, 40, 7200, 0, 0, false));
			stage.add(new ChessClockStage(41, ChessClockStage.END_OF_GAME, 3600, 0, 0, false));
			break;
		//Game in 15 minutes and 10 seconds per move from move 1
		case CLK_G15_10:
			stage.add(new ChessClockStage(0, ChessClockStage.END_OF_GAME, 900, 0, 10, true));
			break;
		//Game in 25 minutes and 10 seconds per move from move 1
		case CLK_G15_5:
			stage.add(new ChessClockStage(0, ChessClockStage.END_OF_GAME, 900, 0, 5, true));
			break;
		//Game in 25 minutes and 10 seconds per move from move 1
		case CLK_G25_10:
			stage.add(new ChessClockStage(0, ChessClockStage.END_OF_GAME, 1500, 0, 10, true));
			break;
		//Game in 25 minutes
		case CLK_G25:
			stage.add(new ChessClockStage(0, ChessClockStage.END_OF_GAME, 1500, 0, 0, false));
			break;
		//Game in 5 minutes
		case CLK_G5:
			stage.add(new ChessClockStage(0, ChessClockStage.END_OF_GAME, 300, 0, 0, false));
			break;
		//Game in 5 minutes and 3 seconds per move from move 1
		case CLK_G5_3:
			stage.add(new ChessClockStage(0, ChessClockStage.END_OF_GAME, 300, 0, 3, true));
			break;
		//Game in 5 minutes and 2 seconds per move from move 1
		case CLK_G5_2:
			stage.add(new ChessClockStage(0, ChessClockStage.END_OF_GAME, 300, 0, 2, true));
			break;
		//Game in 3 minutes and 2 seconds per move from move 1
		case CLK_G3_2:
			stage.add(new ChessClockStage(0, ChessClockStage.END_OF_GAME, 180, 0, 2, true));
			break;
		//Game in 2 minutes and 1 second per move from move 1
		case CLK_G2_1:
			stage.add(new ChessClockStage(0, ChessClockStage.END_OF_GAME, 120, 0, 1, true));
			break;
		//Game in 1 minute
		case CLK_G1:
			stage.add(new ChessClockStage(0, ChessClockStage.END_OF_GAME, 60, 0, 0, false));
			break;
		//No clock
		default:
		}
	}
	
	/**
	 * This switches which clock is currently counting down. It also handles changing clock stages as needed and determining if the end of the game is reached due to the
	 * current move number.
	 */
	public void switchTurn() {
		//If it's white's turn
		if(Chess.getChessBoard().getTurn() == ChessBoard.WHITE) {
			//Pause black's clock
			stage.get(currStage).pauseBlackClock();
			
			//If move number is the end of the current stage
			if(Chess.getChessBoard().getMoveNumber() > stage.get(currStage).getEndingMove()) {
				//Move to next stage, if there is one
				if(currStage + 1 < stage.size()) {
					currStage++;
					
					//Add time remaining from last stage to this stage for both players
					stage.get(currStage).addBlackTimeLength(stage.get(currStage - 1).getBlackTime());
					stage.get(currStage).addWhiteTimeLength(stage.get(currStage - 1).getWhiteTime());
					
					//If black moved last turn, add time increment to black's clock
					if(Chess.getChessBoard().getMoveNumber() > 0 || stage.get(currStage).incrementFirstMove())
						stage.get(currStage).addBlackTimeLength(stage.get(currStage).getIncrement());
					
					//Start the delay timer
					stage.get(currStage).startDelayTimer();
				}
				//If there is not a next stage then end the game
				else {
					Chess.endGame();
				}
			}
			//If move number is not at the end of the current stage
			else {
				//Start the delay timer
				stage.get(currStage).startDelayTimer();
			}
		}
		//If it's black's turn
		else {
			//Pause white's clock
			stage.get(currStage).pauseWhiteClock();
			
			//If white moved last turn, add time increment to white's clock
			if(Chess.getChessBoard().getMoveNumber() > 0 || stage.get(currStage).incrementFirstMove())
				stage.get(currStage).addWhiteTimeLength(stage.get(currStage).getIncrement());
			
			//Start the delay timer
			stage.get(currStage).startDelayTimer();
		}
	}
}
