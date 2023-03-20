package View;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import java.io.*;
import java.util.*;

public class View extends JFrame{
	private final int BUTTON_SIZE = 50;
	private final int WORD_SEARCH_TITLE_SIZE = 20;
	private final int BIG_FONT_SIZE = 16;

	private final Icon RESTING_BUTTON = new ImageIcon("button_blank.png");
	private final Icon PRESSED_BUTTON = new ImageIcon("button_pressed.png");
	private final Icon USED_BUTTON = new ImageIcon("button_letterused.png");
	private final Icon CURRENT_BUTTON = new ImageIcon("button_current.png");
	private final Icon DONE_BUTTON = new ImageIcon("button_done.png");

	private final String BLANK_SPACE = " ";
        
        private JButton [][] letterBoardButtons;
	private JLabel currentScore;
	private JLabel gameState;
	private int[][] buttonStates;
	private int maxScore;
	private int score;

}
