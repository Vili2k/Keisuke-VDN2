package gui;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;

/**
 * @desc Frame that displays the menu.
 * @author vilip
 */
public class MenuFrame extends JFrame {

	/**
	 * @desc Randomly generated serial version UID.
	 */
	private static final long serialVersionUID = 5469304007304180616L;
	
	/**
	 * @desc Buttons.
	 */
	private JButton new_button, load_button, help_button, exit_button;
	
	/**
	 * @desc Preparing and adding components to menu frame.
	 * @env this.buttons_panel
	 * @env this.new_button, this.load_button, this.help_button, this.exit_button.
	 */
	public MenuFrame() {
		this.setTitle("Keisuke - Menu");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(420, 420);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setLayout(new GridLayout(4, 1, 0, 2));
		
		new_button = new JButton("New");
		load_button = new JButton("Load");
		help_button = new JButton("Help");
		exit_button = new JButton("Exit");
		
		new_button.setFocusable(false);
		load_button.setFocusable(false);
		help_button.setFocusable(false);
		exit_button.setFocusable(false);
		
		new_button.addActionListener(e -> {
			this.dispose();
			new SetupFrame();
		});
		exit_button.addActionListener(e -> {
			this.dispose();
			System.exit(0);
		});
		
		this.add(new_button);
		this.add(load_button);
		this.add(help_button);
		this.add(exit_button);
		
		this.setVisible(true);
	}
	
}
