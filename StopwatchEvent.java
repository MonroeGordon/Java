package com.chess.gui.event;

import java.awt.AWTEvent;

import com.chess.Stopwatch;

/**
 * StopwatchEvent is sent to StopwatchListeners to inform them of a change to a stopwatch.
 * @author Monroe Gordon
 * @since 6/9/2022
 */
public class StopwatchEvent extends AWTEvent{

	/** StopwatchEvent serial version ID value. */
	private static final long serialVersionUID = 8872832116083979995L;
	
	/** Stopwatch event mask ID value. */
	public static final int STOPWATCH_EVENT_MASK = AWTEvent.RESERVED_ID_MAX + 0x00000002;
	
	/**
	 * Constructor for StopwatchEvent that creates a stopwatch event.
	 * @param source - the stopwatch creating this event
	 */
	public StopwatchEvent(Stopwatch source) {
		//Call parent constructor
		super(source, STOPWATCH_EVENT_MASK);
	}
	
	/**
	 * Returns the time remaining on the stopwatch attached to this event.
	 * @return the time remaining of this event's stopwatch
	 */
	public int getTime() { return ((Stopwatch)source).getTime(); }
}
