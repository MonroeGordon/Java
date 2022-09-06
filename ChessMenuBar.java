package com.chess.gui.bar;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.ButtonGroup;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.UIDefaults;

import com.chess.Chess;
import com.chess.Chess.BoardOption;
import com.chess.gui.painter.MenuBarBgPainter;

/**
 * ChessMenuBar creates and controls the chess menu bar, menus and menu items.
 * @author Monroe Gordon
 * @since 5/28/2022
 */
public class ChessMenuBar extends JMenuBar implements ActionListener {

	/** ChessMenu serial version ID value. */
	private static final long serialVersionUID = -4106354543242462020L;
	
	/** File menu. */
	private JMenu fileMenu;
	/** New Game menu item. */
	private JMenuItem newGameItem;
	/** Open Game menu item. */
	private JMenuItem openGameItem;
	/** Save Game menu item. */
	private JMenuItem saveGameItem;
	/** Save Game As menu item. */
	private JMenuItem saveGameAsItem;
	/** Exit menu item. */
	private JMenuItem exitItem;
	
	/** Options menu. */
	private JMenu optionsMenu;
	/** View menu button group. */
	private ButtonGroup optionsGroup;
	/** Black and white board set radio menu button. */
	private JRadioButtonMenuItem bwbMenuButton;
	/** Wooden board set radio menu button. */
	private JRadioButtonMenuItem wbMenuButton;
	/** Glass board set radio menu button. */
	private JRadioButtonMenuItem gbMenuButton;

	/**
	 * Default constructor for ChessMenuBar that creates the menu bar.
	 */
	public ChessMenuBar() {
		//Initialize menu bar
		setPreferredSize(new Dimension(Chess.getFrameWidth(), 20));
	    UIDefaults overrides = new UIDefaults();
	    overrides.put("MenuBar[Enabled].backgroundPainter", new MenuBarBgPainter());
	    overrides.put("MenuBar[Enabled+Finished].backgroundPainter", new MenuBarBgPainter());
	    putClientProperty("Nimbus.Overrides", overrides);
	    putClientProperty("Nimbus.Overrides.InheritDefaults", false);
		
		//Create file menu
		fileMenu = new JMenu("File");
		fileMenu.setMnemonic(KeyEvent.VK_F);
		newGameItem = new JMenuItem("New Game");
		newGameItem.setMnemonic(KeyEvent.VK_N);
		fileMenu.add(newGameItem);
		openGameItem = new JMenuItem("Open Game");
		openGameItem.setMnemonic(KeyEvent.VK_O);
		fileMenu.add(openGameItem);
		fileMenu.addSeparator();
		saveGameItem = new JMenuItem("Save Game");
		saveGameItem.setMnemonic(KeyEvent.VK_S);
		fileMenu.add(saveGameItem);
		saveGameAsItem = new JMenuItem("Save Game As");
		saveGameAsItem.setMnemonic(KeyEvent.VK_A);
		fileMenu.add(saveGameAsItem);
		fileMenu.addSeparator();
		exitItem = new JMenuItem("Exit");
		exitItem.setMnemonic(KeyEvent.VK_X);
		fileMenu.add(exitItem);
		
		//Create options menu
		optionsMenu = new JMenu("Options");
		optionsMenu.setMnemonic(KeyEvent.VK_P);
		optionsGroup = new ButtonGroup();
		bwbMenuButton = new JRadioButtonMenuItem("Black & White Set");
		optionsGroup.add(bwbMenuButton);
		optionsMenu.add(bwbMenuButton);
		wbMenuButton = new JRadioButtonMenuItem("Wooden Set", !Chess.GLASS_BOARD_ALLOWED);
		optionsGroup.add(wbMenuButton);
		optionsMenu.add(wbMenuButton);
		gbMenuButton = null;
		if(Chess.GLASS_BOARD_ALLOWED) {
			gbMenuButton = new JRadioButtonMenuItem("Glass Set", Chess.GLASS_BOARD_ALLOWED);
			optionsGroup.add(gbMenuButton);
			optionsMenu.add(gbMenuButton);
		}
		
		//Add menus
		add(fileMenu);
		add(optionsMenu);
		
		//Add listeners
		newGameItem.addActionListener(this);
		openGameItem.addActionListener(this);
		saveGameItem.addActionListener(this);
		saveGameAsItem.addActionListener(this);
		exitItem.addActionListener(this);
		bwbMenuButton.addActionListener(this);
		wbMenuButton.addActionListener(this);
		gbMenuButton.addActionListener(this);
		
		//Show menu bar
		setVisible(true);
	}
	
	/**
	 * Handle menu action events.
	 * @param e - the menu action event
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		//Handle new game menu item
		if(e.getSource().equals(newGameItem)) {
			//Start new game
			Chess.newGame();
		}
		//Handle open game menu item
		else if(e.getSource().equals(openGameItem)) {
			
		}
		//Handle save game menu item
		else if(e.getSource().equals(saveGameItem)) {
			
		}
		//Handle save game as menu item
		else if(e.getSource().equals(saveGameAsItem)) {
			
		}
		//Handle exit menu item
		else if(e.getSource().equals(exitItem)) {
			//Shutdown the program
			Chess.shutdown();
		}
		//Handle black & white menu button
		else if(e.getSource().equals(bwbMenuButton)) {
			//Change board to black and white
			Chess.setBoardOption(BoardOption.BLACK_WHITE);
		}
		//Handle wooden menu button
		else if(e.getSource().equals(wbMenuButton)) {
			//Change board to wooden
			Chess.setBoardOption(BoardOption.WOODEN);
		}
		//Handle glass menu button
		else if(e.getSource().equals(gbMenuButton)) {
			//Change board to glass
			Chess.setBoardOption(BoardOption.GLASS);
		}
	}
	
	
}
