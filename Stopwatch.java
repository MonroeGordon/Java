package com.chess;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Timer;

import com.chess.gui.event.StopwatchEvent;
import com.chess.gui.event.StopwatchListener;

/**
 * StopWatch creates a stopwatch timer that updates per second until the specified time period has expired.
 * @author MonroeGordon
 * @since 6/8/2022
 */
public class Stopwatch implements ActionListener {

	/** Time period length. */
	private int length;
	/** Current time on the stopwatch. */
	private int time;
	/** One second timer. */
	private Timer secondtimer;
	/** Stopwatch stopped flag. */
	private boolean stopped;
	/** Stopwatch listeners. */
	private ArrayList<StopwatchListener> listener;
	
	/**
	 * Default constructor for Stopwatch that initializes all values to 0 and initializes the second timer to a 1000 millisecond delay timer.
	 */
	public Stopwatch() {
		//Initialize variables
		length = 0;
		time = 0;
		secondtimer = new Timer(1000, this);
		secondtimer.setRepeats(true);
		stopped = false;
		listener = new ArrayList<StopwatchListener>(0);
	}
	
	/**
	 * Constructor for Stopwatch that initializes the stopwatch's time length to the specified time length and sets the remaining time to the specified time length. All other
	 * variables are initialized to their default values.
	 * @param timeLength - the new time length in seconds
	 */
	public Stopwatch(int timeLength) {
		//Check parameters
		if(timeLength < 0)
			throw new IllegalArgumentException("Time length cannot be less than 0");
		
		//Initialize variables
		length = timeLength;
		time = length;
		secondtimer = new Timer(1000, this);
		secondtimer.setRepeats(true);
		stopped = false;
		listener = new ArrayList<StopwatchListener>(0);
	}

	//Handle action events
	@Override
	public void actionPerformed(ActionEvent e) {
		//Update current time remaining
		time--;
		
		//If time has expired
		if(time == 0) {
			//Stop the second timer
			secondtimer.stop();
			
			//Set stopped
			stopped = true;
			
			//Inform listeners
			for(int i = 0; i < listener.size(); ++i) listener.get(i).stopwatchStopped(new StopwatchEvent(this));
		}
		//If time has not expired
		else {
			//Inform listeners of current tick
			for(int i = 0; i < listener.size(); ++i) listener.get(i).stopwatchTicked(new StopwatchEvent(this));
		}
	}
	
	/**
	 * Adds a stopwatch listener to the list of stopwatch listeners.
	 * @param l - the stopwatch listener to add
	 */
	public void addStopwatchListener(StopwatchListener l) {
		if(l != null) listener.add(l);
	}
	
	/**
	 * Returns the number of seconds this stopwatch will time.
	 * @return the number of seconds this stopwatch will time
	 */
	public int getTimeLength() { return length; }
	
	/**
	 * Returns the current time remaining on this stopwatch.
	 * @return the current time remaining
	 */
	public int getTime() { return time; }
	
	/**
	 * Pauses the stop watch.
	 */
	public void pause() {
		//Stop the second timer
		secondtimer.stop();
		
		//Inform listeners
		for(int i = 0; i < listener.size(); ++i) listener.get(i).stopwatchPaused(new StopwatchEvent(this));
	}
	
	/**
	 * Resets the stopwatch by resetting the stopped flag.
	 */
	public void reset() {
		//Set stopped to false
		stopped = false;
	}
	
	/**
	 * Sets the time length of the stopwatch. This also sets the current remaining time to the new time length.
	 * @param timeLength - the new time length
	 */
	public void setTimeLength(int timeLength) {
		if(timeLength < 0)
			throw new IllegalArgumentException("Time length cannot be less than 0");
		
		length = timeLength;
		time = length;
	}
	
	/**
	 * Starts the stopwatch.
	 */
	public void start() {
		//If the stopwatch is not stopped
		if(!stopped) {
			//Start the second timer
			secondtimer.start();
			
			//Inform listeners
			for(int i = 0; i < listener.size(); ++i) listener.get(i).stopwatchStarted(new StopwatchEvent(this));
		}
	}
}
