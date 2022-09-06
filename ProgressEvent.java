package com.chess.gui.event;

import java.awt.AWTEvent;

/**
 * ProgressEvent is an AWT event used to update a progress bar's value.
 * @author Monroe Gordon
 * @since 6/2/2022
 */
public class ProgressEvent extends AWTEvent {

	/** ProgressEvent serial version ID value. */
	private static final long serialVersionUID = 4116551976881796932L;
	
	/** Progress event mask ID value. */
	public static final int PROGRESS_EVENT_MASK = AWTEvent.RESERVED_ID_MAX + 0x00000001;
	
	/** The current progress value. */
	private int progressValue;

	/**
	 * Constructor for the ProgressEvent that creates the progress event.
	 * @param source - the source of the event
	 */
	public ProgressEvent(Object source, int progress) {
		//Call parent constructor
		super(source, PROGRESS_EVENT_MASK);
		
		//Check parameters
		if(progress < 0 || progress > 100)
			throw new IllegalArgumentException("Progress value must be between 0 and 100");
		
		//Initialize variables
		progressValue = progress;
	}

	/**
	 * Returns the current progress value set by this event.
	 * @return the current progress value
	 */
	public int getProgress() { return progressValue; }
}
