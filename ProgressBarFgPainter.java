package com.chess.gui.painter;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics2D;

import javax.swing.Painter;

/**
 * ProgressBarFgPainter paints the colors for the foreground of the progress bar used in the Chess program.
 * @author Monroe Gordon
 * @since 6/2/2022
 */
public class ProgressBarFgPainter implements Painter<Component> {

	@Override
	public void paint(Graphics2D g, Component object, int width, int height) {
		//Paint blue highlighted bar
		g.setColor(new Color(0, 0x83, 0xA3, 255));
		g.fillRect(0, 0, width, (int)(height * 0.2));
		g.setColor(new Color(0, 0xC0, 0xF0, 255));
		g.fillRect(0, (int)(height * 0.2), width, (int)(height * 0.3));
		g.setColor(new Color(0, 0x83, 0xA3, 255));
		g.fillRect(0, (int)(height * 0.5), width, (int)(height * 0.3));
		g.setColor(new Color(0, 0x50, 0x63, 255));
		g.fillRect(0, (int)(height * 0.8), width, (int)(height * 0.2));
		g.dispose();
	}
}
