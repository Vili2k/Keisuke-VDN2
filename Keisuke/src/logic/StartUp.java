package logic;

import gui.MenuFrame;

import java.awt.Dimension;
import java.awt.Toolkit;

public class StartUp {

	/**
	 * @desc Preparing GameState and starting the program.
	 * @param args
	 */
	public static void main(String[] args) {
		GameState.SCREEN_SIZE = Toolkit.getDefaultToolkit().getScreenSize();
		GameState.FRAME_SIZE = new Dimension(400, 400);
		GameState.MIN_ROWS = 4;
		GameState.MIN_COLS = 4;
		GameState.MAX_ROWS = 20;
		GameState.MAX_COLS = 20;
		new MenuFrame();
	}
	
}
