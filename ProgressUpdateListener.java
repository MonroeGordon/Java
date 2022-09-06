package com.chess.gui.event;

import java.util.EventListener;

/**
 * ProgressUpdateListener is an interface implemented by objects to receive updates to the progress bar's current value.
 * @author MonroeGordon
 * @since 6/2/2022
 */
public interface ProgressUpdateListener extends EventListener {

	/**
	 * Update the progress bar based on the value specified in the progress event.
	 * @param e - the progress event that called for the update
	 */
	public void updateProgress(ProgressEvent e);
}
