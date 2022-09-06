package com.chess.gui.event;

import java.util.EventListener;

/**
 * StopwatchListener is an interface implemented by objects to receive stopwatch events.
 * @author Monroe Gordon
 * @since 6/9/2022
 */
public interface StopwatchListener extends EventListener {
	
	/**
	 * Informs listening objects that the stopwatch paused.
	 * @param e - the stopwatch event triggering this call
	 */
	public void stopwatchPaused(StopwatchEvent e);

	/**
	 * Informs listening objects that the stopwatch started.
	 * @param e - the stopwatch event triggering this call
	 */
	public void stopwatchStarted(StopwatchEvent e);
	
	/**
	 * Informs listening objects that the stopwatch ticked.
	 * @param e - the stopwatch event triggering this call
	 */
	public void stopwatchTicked(StopwatchEvent e);
	
	/**
	 * Informs listening objects that the stopwatch stopped.
	 * @param e - the stopwatch event triggering this call
	 */
	public void stopwatchStopped(StopwatchEvent e);
}
