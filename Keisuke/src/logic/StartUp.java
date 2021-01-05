package logic;

import gui.MenuFrame;
import java.awt.Toolkit;

public class StartUp {

	public static void main(String[] args) {
		GameState.screen_size = Toolkit.getDefaultToolkit().getScreenSize();
		new MenuFrame();
	}
	
}
