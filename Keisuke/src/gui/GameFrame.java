package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.Timer;

import logic.GameState;
import logic.Grid;

/**
 * @desc Frame that displays the game.
 * @author vilip
 */
public class GameFrame extends JFrame {

	/**
	 * @desc Randomly generated serial version UID.
	 */
	private static final long serialVersionUID = -7927156597267134363L;
	
	/**
	 * @desc Playing grid panel.
	 */
	private GridPanel grid_panel;
	
	/**
	 * @desc Contains values for the grid.
	 */
	private JPanel values_panel;
	
	/**
	 * @desc Contains buttons for the game.
	 */
	private JPanel header_panel;
	
	/**
	 * @desc Labels.
	 */
	private JLabel across_label, down_label, across_values_label, down_values_label, time_label;
	
	/**
	 * @desc Combo boxes for displaying values of selected length.
	 */
	private JComboBox<Integer> across_combobox, down_combobox;
	
	/**
	 * @desc Buttons.
	 */
	private JButton menu_button, save_button, hint_button, solution_button, reset_button;

	/**
	 * @desc Preparing and adding components to game frame.
	 * @env this.grid_panel, this.values_panel, this.buttons_panel
	 */
	public GameFrame() {
		this.setTitle("Keisuke - Game");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(500, 500);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setResizable(true);
		this.setLocationRelativeTo(null);
		this.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
	            grid_panel.update_bounds();
	        }
		});
		
		grid_panel = new GridPanel(GameState.PLAYING_GRID);
		setup_values_panel();
		setup_header_panel();
		
		this.add(grid_panel, BorderLayout.CENTER);
		this.add(new JScrollPane(values_panel, 
				JScrollPane.VERTICAL_SCROLLBAR_NEVER, 
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED), 
				BorderLayout.SOUTH);
		this.add(header_panel, BorderLayout.NORTH);
		
		this.setVisible(true);
	}
	
	/**
	 * @desc Set-up values panel.
	 * @env GameState.ACROSS_VALUES, GameState.DOWN_VALUES
	 * @env this.values_panel
	 * @env this.across_label, this.down_label, this.across_values_label, this.down_values_label
	 * @env this.across_combobox, this.down_combobox
	 */
	public void setup_values_panel() {
		values_panel = new JPanel();
		values_panel.setLayout(new BoxLayout(values_panel, BoxLayout.Y_AXIS));
		values_panel.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		across_label = new JLabel("Across >>>    Length: ");
		down_label = new JLabel("Down: vvv    Length: ");
		
		Integer[] across_lengths = new Integer[GameState.ACROSS_VALUES.size()];
		int across_i = 0;
		for (Integer key : GameState.ACROSS_VALUES.keySet()) {
			across_lengths[across_i++] = key;
		}
		across_combobox = new JComboBox<Integer>(across_lengths);
		across_combobox.addActionListener(e -> {
			@SuppressWarnings("unchecked")
			JComboBox<Integer> source = (JComboBox<Integer>) e.getSource();
			int index = (int) (source.getSelectedIndex());
			ArrayList<String> across_values_at_length 
					= GameState.ACROSS_VALUES.get(across_lengths[index]);
			String across_values_string = "  ";
			for (String val : across_values_at_length) {
				across_values_string += val + ", ";
			}
			across_values_label.setText(
					across_values_string.substring(0, across_values_string.length()-2));
		});
		
		Integer[] down_lengths = new Integer[GameState.DOWN_VALUES.size()];
		int down_i = 0;
		for (Integer key : GameState.DOWN_VALUES.keySet()) {
			down_lengths[down_i++] = key;
		}
		down_combobox = new JComboBox<Integer>(down_lengths);
		down_combobox.addActionListener(e -> {
			@SuppressWarnings("unchecked")
			JComboBox<Integer> source = (JComboBox<Integer>) e.getSource();
			int index = (int) (source.getSelectedIndex());
			ArrayList<String> down_values_at_length 
					= GameState.DOWN_VALUES.get(down_lengths[index]);
			String down_values_string = "  ";
			for (String val : down_values_at_length) {
				down_values_string += val + ", ";
			}
			down_values_label.setText(
					down_values_string.substring(0, down_values_string.length()-2));
		});
		
		JPanel across_header_panel = new JPanel();
		across_header_panel.setLayout(new FlowLayout(FlowLayout.LEFT));
		across_header_panel.add(across_label);
		across_header_panel.add(across_combobox);
		
		JPanel down_header_panel = new JPanel();
		down_header_panel.setLayout(new FlowLayout(FlowLayout.LEFT));
		down_header_panel.add(down_label);
		down_header_panel.add(down_combobox);
		
		if (across_lengths.length > 0) {
			ArrayList<String> across_values_at_length 
					= GameState.ACROSS_VALUES.get(across_lengths[0]);
			String across_values_string = "  ";
			for (String val : across_values_at_length) {
				across_values_string += val + ", ";
			}
			across_values_label = new JLabel(
					across_values_string.substring(0, across_values_string.length()-2));
		} else {
			across_values_label = new JLabel(" ");
		}
		
		if (down_lengths.length > 0) {
			ArrayList<String> down_values_at_length 
					= GameState.DOWN_VALUES.get(down_lengths[0]);
			String down_values_string = "  ";
			for (String val : down_values_at_length) {
				down_values_string += val + ", ";
			}
			down_values_label = new JLabel(
					down_values_string.substring(0, down_values_string.length()-2));
		} else {
			down_values_label = new JLabel(" ");
		}
		
		across_header_panel.setAlignmentX(Component.LEFT_ALIGNMENT);
		across_values_label.setAlignmentX(Component.LEFT_ALIGNMENT);
		down_header_panel.setAlignmentX(Component.LEFT_ALIGNMENT);
		down_values_label.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		values_panel.add(across_header_panel);
		values_panel.add(across_values_label);
		values_panel.add(down_header_panel);
		values_panel.add(down_values_label);
		values_panel.add(new JLabel(" "));
	}
	
	/**
	 * @desc Set-up header panel.
	 * @env this.header_panel
	 * @env this.time_label
	 * @env this.menu_button, this.save_button, this.hint_button, this.solution_button, this.reset_button
	 */
	private void setup_header_panel() {
		JPanel buttons_panel = new JPanel();
		buttons_panel.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		menu_button = new JButton("Menu");
		save_button = new JButton("Save");
		reset_button = new JButton("Reset");
		hint_button = new JButton("Hint");
		solution_button = new JButton("Solution");
		
		menu_button.setFocusable(false);
		save_button.setFocusable(false);
		reset_button.setFocusable(false);
		hint_button.setFocusable(false);
		solution_button.setFocusable(false);
		
		menu_button.addActionListener(e -> {
			this.dispose();
			new MenuFrame();
		});
		reset_button.addActionListener(e -> {
			grid_panel.reset();
		});
		solution_button.addActionListener(e -> {
			new SolutionFrame();
		});
		
		buttons_panel.add(menu_button);
		buttons_panel.add(save_button);
		buttons_panel.add(reset_button);
		buttons_panel.add(hint_button);
		buttons_panel.add(solution_button);
		
		time_label = new JLabel();
		Timer t = new Timer(1000, e -> {
			GameState.TIME++;
			int hours = GameState.TIME / 3600;
			int minutes = (GameState.TIME - hours * 3600) / 60;
			int seconds = GameState.TIME - hours * 3600 - minutes * 60;
			time_label.setText(((hours > 0) ? (hours + ":") : "") + ((minutes > 0) ? (minutes + ":") : "") + seconds + " ");
		});
		t.start();
		
		header_panel = new JPanel();
		header_panel.setLayout(new BorderLayout());
		
		header_panel.add(buttons_panel);
		header_panel.add(time_label, BorderLayout.EAST);
	}
	
}
