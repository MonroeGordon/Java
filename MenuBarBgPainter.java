package com.chess.gui.painter;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics2D;

import javax.swing.Painter;

import com.chess.Chess;
import com.chess.Chess.BoardOption;

/**
 * MenuBarBgPainter paints the colors for the foreground of the menu bar used in the Chess program.
 * @author Monroe Gordon
 * @since 6/2/2022
 */
public class MenuBarBgPainter implements Painter<Component> {
	
	/** Glass background color. */
	private static final Color GLASS_BG_COLOR = new Color(196, 196, 255, 255);
	/** Wood background color. */
	private static final Color WOOD_BG_COLOR = new Color(160, 130, 90, 255);
	/** Black and white background color. */
	private static final Color BW_BG_COLOR = new Color(255, 255, 255, 255);
	/** Glass border color */
	private static final Color GLASS_BORDER_COLOR = new Color(16, 16, 32, 255);
	/** Wood border color */
	private static final Color WOOD_BORDER_COLOR = new Color(101, 67, 33, 255);
	/** Black and white border color */
	private static final Color BW_BORDER_COLOR = new Color(0, 0, 0, 255);

	@Override
	public void paint(Graphics2D g, Component object, int width, int height) {
		//Use chess board's background color
		if(Chess.BOARD_OPTION == BoardOption.GLASS) {
			g.setColor(GLASS_BG_COLOR);
			g.fillRect(0, 0, width, height);
			g.setColor(GLASS_BORDER_COLOR);
			g.drawLine(0, height - 1, width, height - 1);
		}
		else if(Chess.BOARD_OPTION == BoardOption.WOODEN) {
			g.setColor(WOOD_BG_COLOR);
			g.fillRect(0, 0, width, height);
			g.setColor(WOOD_BORDER_COLOR);
			g.drawLine(0, height - 1, width, height - 1);
		}
		else {
			g.setColor(BW_BG_COLOR);
			g.fillRect(0, 0, width, height);
			g.setColor(BW_BORDER_COLOR);
			g.drawLine(0, height - 1, width, height - 1);
		}

		g.dispose();
	}
}
