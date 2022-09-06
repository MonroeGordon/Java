package com.chess;

import javax.swing.Timer;

/**
 * ChessClockStage handles the black and white timers for a single stage of the game. A stage is specified by the starting move number and ending move number. Between those two
 * move numbers, the timers, delay and increment values are applied to the chess clock. A stage can last the entire game by starting at move 0 and ending at move END_OF_GAME.
 * The default chess clock stage gives a time limit of 2 hours with no delay and no increment.
 * @author Monroe Gordon
 * @since 6/7/2022
 */
public class ChessClockStage {
	
	/** Default time limit (in seconds) value. */
	public static final int DEF_TIME_LIMIT = 7200;

	/** End of game move number value. */
	public static final int END_OF_GAME = Integer.MAX_VALUE;
	
	/** Move number that starts this stage. */
	private int moveStart;
	/** Move number that ends this stage. */
	private int moveEnd;
	/** Black player's clock. */
	private Stopwatch blackClock;
	/** White player's clock. */
	private Stopwatch whiteClock;
	/** Delay timer. */
	private Timer delay;
	/** Time increment. */
	private int increment;
	/** Move 1 flag. */
	private boolean moveOne;
	
	/**
	 * Default constructor for the ChessClockStage that initializes a default chess clock stage belonging to the chess clock.
	 * @param clock
	 */
	public ChessClockStage() {
		//Initialize variables
		moveStart = 0;
		moveEnd = END_OF_GAME;
		blackClock = new Stopwatch(DEF_TIME_LIMIT);
		whiteClock = new Stopwatch(DEF_TIME_LIMIT);
		delay = new Timer(0, Chess.getChessClock());
		delay.setRepeats(false);
		increment = 0;
		moveOne = false;
	}
	
	/**
	 * Constructor for the ChessClockStage that initializes the starting move, ending move, players' time limit, delay time, increment time and if the increment is applied on
	 * move one.
	 * @param start - the move number that starts this stage
	 * @param end - the move number that ends this stage
	 * @param timeLimit - the number of seconds each player has to complete the specified number or moves (or complete the game)
	 * @param delay - the delay time before starting the player's clock
	 * @param increment - the time increment to add at the end of each player's turn
	 * @oaram firstMove - apply increment on move 1
	 */
	public ChessClockStage(int start, int end, int timeLimit, int delay, int increment, boolean firstMove) {
		//Check parameters
		if(start < 0 || end < 0 || timeLimit < 0 || delay < 0 || increment < 0)
			throw new IllegalArgumentException("ChessClockStage input parameters cannot be negative");
		
		if(end <= start)
			throw new IllegalArgumentException("Ending move number must exceed starting move number");
		
		//Initialize variables
		moveStart = start;
		moveEnd = end;
		blackClock = new Stopwatch(timeLimit);
		blackClock.addStopwatchListener(Chess.getChessFrame());
		whiteClock = new Stopwatch(timeLimit);
		whiteClock.addStopwatchListener(Chess.getChessFrame());
		this.delay = new Timer(delay, Chess.getChessClock());
		this.delay.setRepeats(false);
		this.increment = increment;
		moveOne = firstMove;
	}
	
	/**
	 * This adds the specified length of time (in seconds) to the black clock's remaining time.
	 * @param time - the amount of time (in seconds) to add
	 */
	public void addBlackTimeLength(int time) { blackClock.setTimeLength(blackClock.getTime() + time); }
	
	/**
	 * This adds the specified length of time (in seconds) to the white clock's remaining time.
	 * @param time - the amount of time (in seconds) to add
	 */
	public void addWhiteTimeLength(int time) { whiteClock.setTimeLength(whiteClock.getTime() + time); }
	
	/**
	 * Returns the black clock;
	 * @return the black clock
	 */
	public Stopwatch getBlackClock() { return blackClock; }
	
	/**
	 * Returns the time remaining on the black player's clock.
	 * @return the time remaining on the black player's clock
	 */
	public int getBlackTime() { return blackClock.getTime(); }
	
	/**
	 * Returns the delay time length at the start of each player's turn before their clock starts.
	 * @return the delay time length
	 */
	public int getDelayTime() { return delay.getDelay(); }
	
	/**
	 * Returns the move number this clock stage ends on.
	 * @return the move number this clock stage ends on.
	 */
	public int getEndingMove() { return moveEnd; }
	
	/**
	 * Returns the time increment value that is earned and added to a player's clock after their turn.
	 * @return the time increment value
	 */
	public int getIncrement() { return increment; }
	
	/**
	 * Returns the move number this clock stage starts on.
	 * @return the move number this clock stage starts on
	 */
	public int getStartingMove() { return moveStart; }
	
	/**
	 * Returns the white clock;
	 * @return the white clock
	 */
	public Stopwatch getWhiteClock() { return whiteClock; }
	
	/**
	 * Returns the time remaining on the white player's clock.
	 * @return the time remaining on the white player's clock
	 */
	public int getWhiteTime() { return whiteClock.getTime(); }
	
	/**
	 * Returns if the time increment is applied on move 1.
	 * @return true if the time increment is applied on move 1, false otherwise
	 */
	public boolean incrementFirstMove() { return moveOne; }
	
	/**
	 * Pauses the black player's clock.
	 */
	public void pauseBlackClock() { blackClock.pause(); }
	
	/**
	 * Pauses the white player's clock.
	 */
	public void pauseWhiteClock() { whiteClock.pause(); }
	
	/**
	 * Starts the black player's clock. If the clock was paused, the clock continues from where it left off.
	 */
	public void startBlackClock() { blackClock.start(); }
	
	/**
	 * Starts the delay timer.
	 */
	public void startDelayTimer() { delay.start(); }
	
	/**
	 * Starts the white player's clock. If the clock was paused, the clock continues from where it left off.
	 */
	public void startWhiteClock() { whiteClock.start(); }
}
