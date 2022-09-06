package com.chess.gui.panel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

import com.chess.Chess;
import com.chess.ChessBoard;
import com.chess.ChessClock;
import com.chess.gui.bar.NewGameTitleBar;
import com.chess.gui.button.CancelButton;
import com.chess.gui.button.PlayButton;
import com.chess.gui.dialog.NewGameDialog;

/**
 * NewGamePanel creates and controls the panel of the new game dialog window.
 * @author Monroe Gordon
 * @since 6/7/2022
 */
public class NewGamePanel extends JPanel implements ActionListener, ItemListener, PopupMenuListener {

	/** NewGamePanel serial version ID value. */
	private static final long serialVersionUID = 2452467393417542525L;
	
	/** Glass background color. */
	public static final Color GLASS_BG_COLOR = new Color(16, 32, 96, 128);
	/** Default background color. */
	public static final Color DEF_BG_COLOR = new Color(16, 32, 96, 255);
	
	/** Parent window. */
	private Window parent;
	/** Player color label. */
	private JLabel pclbl;
	/** Player color button group. */
	private ButtonGroup pcgroup;
	/** Player color button panel */
	private JPanel pcpanel;
	/** Black button. */
	private JRadioButton blackbtn;
	/** White button. */
	private JRadioButton whitebtn;
	/** Clock settings label. */
	private JLabel cslbl;
	/** Clock settings combo box. */
	private JComboBox<String> cscombo;
	/** Clock settings description label. */
	private JLabel csdlbl;
	/** Play button. */
	private PlayButton playbtn;
	/** Cancel button. */
	private CancelButton cancelbtn;
	/** Selected player color. */
	private boolean player;

	/**
	 * Default constructor for NewGamePanel that creates the panel.
	 */
	public NewGamePanel(Window parent) {
		//Check parameters
		if(parent == null)
			throw new IllegalArgumentException("New game title bar must have a parent window.");
		
		//Initialize variables
		this.parent = parent;
		player = ChessBoard.BLACK;
		
		//Initialize panel
		setSize(new Dimension(NewGameDialog.DEF_DIALOG_HEIGHT - NewGameTitleBar.DEF_TITLEBAR_HEIGHT, NewGameDialog.DEF_DIALOG_WIDTH));
		setLayout(null);
		if(Chess.GLASS_BOARD_ALLOWED) setBackground(GLASS_BG_COLOR);
		else setBackground(DEF_BG_COLOR);
		
		//Create player color label
		pclbl = new JLabel("Player Color");
		pclbl.setBackground(Chess.GLASS_BOARD_ALLOWED ? new Color(0, 0, 0, 0) : DEF_BG_COLOR);
		pclbl.setForeground(Color.white);
		pclbl.setFont(new Font("Arial", Font.PLAIN, 24));
		pclbl.setHorizontalAlignment(SwingConstants.CENTER);
		pclbl.setBounds((NewGameDialog.DEF_DIALOG_WIDTH / 2) - 150, 5, 300, 50);
		
		//Create color button group
		pcgroup = new ButtonGroup();
		
		//Create player color button panel
		pcpanel = new JPanel();
		pcpanel.setLayout(null);
		pcpanel.setBackground(DEF_BG_COLOR);
		pcpanel.setBounds((NewGameDialog.DEF_DIALOG_WIDTH / 2) - 150, 60, 300, 50);
		
		//Create black radio button
		blackbtn = new JRadioButton("Black");
		blackbtn.setForeground(Color.white);
		blackbtn.setFont(new Font("Arial", Font.PLAIN, 18));
		blackbtn.setHorizontalAlignment(SwingConstants.CENTER);
		blackbtn.setBounds(0, 0, 150, 50);
		blackbtn.setSelected(true);
		blackbtn.addActionListener(this);
		
		//Create white radio button
		whitebtn = new JRadioButton("White");
		whitebtn.setForeground(Color.white);
		whitebtn.setFont(new Font("Arial", Font.PLAIN, 18));
		whitebtn.setHorizontalAlignment(SwingConstants.CENTER);
		whitebtn.setBounds(150, 0, 150, 50);
		whitebtn.addActionListener(this);
		
		//Add black and white buttons to player color button group
		pcgroup.add(blackbtn);
		pcgroup.add(whitebtn);
		
		//Add black and white buttons to player color button panel
		pcpanel.add(blackbtn);
		pcpanel.add(whitebtn);
		
		//Create clock settings label
		cslbl = new JLabel("Clock Settings");
		cslbl.setBackground(Chess.GLASS_BOARD_ALLOWED ? new Color(0, 0, 0, 0) : DEF_BG_COLOR);
		cslbl.setForeground(Color.white);
		cslbl.setFont(new Font("Arial", Font.PLAIN, 24));
		cslbl.setHorizontalAlignment(SwingConstants.CENTER);
		cslbl.setBounds((NewGameDialog.DEF_DIALOG_WIDTH / 2) - 150, 120, 300, 50);
		
		//Create clock settings combo box
		cscombo = new JComboBox<String>(ChessClock.clockPresets());
		cscombo.setBackground(DEF_BG_COLOR);
		cscombo.setForeground(Color.white);
		cscombo.setBounds((NewGameDialog.DEF_DIALOG_WIDTH / 2) - 150, 175, 300, 25);
		cscombo.addItemListener(this);
		cscombo.addPopupMenuListener(this);
		
		//Create clock settings description label
		csdlbl = new JLabel(ChessClock.clockDescriptions()[cscombo.getSelectedIndex()]);
		csdlbl.setBackground(Chess.GLASS_BOARD_ALLOWED ? new Color(0, 0, 0, 0) : DEF_BG_COLOR);
		csdlbl.setForeground(Color.white);
		csdlbl.setFont(new Font("Arial", Font.PLAIN, 12));
		csdlbl.setHorizontalAlignment(SwingConstants.CENTER);
		csdlbl.setBounds((NewGameDialog.DEF_DIALOG_WIDTH / 2) - 275, 205, 550, 20);
		
		//Create play button
		playbtn = new PlayButton();
		playbtn.setBounds((NewGameDialog.DEF_DIALOG_WIDTH / 2) - (PlayButton.BUTTON_WIDTH + 5), NewGameDialog.DEF_DIALOG_HEIGHT - PlayButton.BUTTON_HEIGHT - 50,
				PlayButton.BUTTON_WIDTH, PlayButton.BUTTON_HEIGHT);
		playbtn.addActionListener(this);
		
		//Create cancel button
		cancelbtn = new CancelButton();
		cancelbtn.setBounds((NewGameDialog.DEF_DIALOG_WIDTH / 2) + 5, NewGameDialog.DEF_DIALOG_HEIGHT - CancelButton.BUTTON_HEIGHT - 50,
				CancelButton.BUTTON_WIDTH, CancelButton.BUTTON_HEIGHT);
		cancelbtn.addActionListener(this);
		
		//Add components
		add(pclbl);
		add(pcpanel);
		add(cslbl);
		add(cscombo);
		add(csdlbl);
		add(playbtn);
		add(cancelbtn);
		
		//Show panel
		setVisible(true);
	}

	//Handle action events
	@Override
	public void actionPerformed(ActionEvent e) {
		//If the black button is pressed
		if(e.getSource().equals(blackbtn)) {
			//Set player's color to black
			player = ChessBoard.BLACK;
		}
		//If the white button is pressed
		else if(e.getSource().equals(whitebtn)) {
			//Set player's color to white
			player = ChessBoard.WHITE;
		}
		//If the play button is pressed
		else if(e.getSource().equals(playbtn)) {
			//Start a new game using the selected chess clock and player color
			Chess.getChessClock().setClock(ChessClock.getClockPreset(cscombo.getSelectedIndex()));
			Chess.getChessBoard().newGame(player);
			
			//Close the window
			parent.dispose();
		}
		//If the cancel button is pressed
		else if(e.getSource().equals(cancelbtn)) {
			//Close the window
			parent.dispose();
		}
 	}

	//Handle item state changes
	@Override
	public void itemStateChanged(ItemEvent e) {
		//If clock settings combo box selection changed
		if(e.getSource().equals(cscombo)) {
			//Update description label
			csdlbl.setText(ChessClock.clockDescriptions()[cscombo.getSelectedIndex()]);
		}
	}

	//Handle showing popup menu
	@Override
	public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
		//Update description label position
		csdlbl.setBounds((NewGameDialog.DEF_DIALOG_WIDTH / 2) - 275, 375, 550, 20);
		repaint();
	}

	//Handle hiding popup menu
	@Override
	public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
		//Update description label position
		csdlbl.setBounds((NewGameDialog.DEF_DIALOG_WIDTH / 2) - 275, 205, 550, 20);
		repaint();
	}

	//Handle canceling popup menu
	@Override
	public void popupMenuCanceled(PopupMenuEvent e) {
		//Update description label position
		csdlbl.setBounds((NewGameDialog.DEF_DIALOG_WIDTH / 2) - 275, 205, 550, 20);
		repaint();
	}
}
