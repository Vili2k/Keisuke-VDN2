package gui;

import java.awt.Dimension;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;

import logic.GameState;

/**
 * @desc Frame that displays the setup menu for the game.
 * @author vilip
 * @date 7.1.2021
 */
public class SetupFrame extends JFrame {

	/**
	 * @desc Randomly generated serial version UID
	 */
	private static final long serialVersionUID = 4120194782201821752L;
	
	/**
	 * @desc Labels.
	 */
	private JLabel rows_label, cols_label;
	
	/**
	 * @desc Buttons for navigation.
	 */
	private JButton back_button, continue_button;
	
	/**
	 * @desc Combo boxes containing possible grid sizes.
	 */
	private JComboBox<Integer> rows_combobox, cols_combobox;

	/**
	 * @desc Preparing and adding components to menu frame.
	 * @env this.rows_label, this.cols_label
	 * @env this.rows_combobox, this.cols_combobox
	 * @env this.back_button, this.continue_button
	 */
	public SetupFrame() {
		this.setTitle("Keisuke - Setup");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(null);
		this.setSize(400, 200);
		this.setResizable(true);
		this.setLocationRelativeTo(null);
		this.getRootPane().addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                resize_components();
            }
        });
		
		rows_label = new JLabel("Rows: ");
		cols_label = new JLabel("Columns: ");
		
		Integer[] rows_values = new Integer[GameState.MAX_ROWS-GameState.MIN_ROWS+1];
		for (int i = 0; i < rows_values.length; i++) {
			rows_values[i] = i + GameState.MIN_ROWS;
		}		
		Integer[] cols_values = new Integer[GameState.MAX_COLS-GameState.MIN_COLS+1];
		for (int i = 0; i < cols_values.length; i++) {
			cols_values[i] = i + GameState.MIN_COLS;
		}

		rows_combobox = new JComboBox<Integer>(rows_values);
		cols_combobox = new JComboBox<Integer>(cols_values);
		
		back_button = new JButton("Back");
		continue_button = new JButton("Continue");
		
		back_button.setFocusable(false);
		continue_button.setFocusable(false);
		
		back_button.addActionListener(e -> {
			this.dispose();
			new MenuFrame();
		});
		continue_button.addActionListener(e -> {
			this.dispose();
		});
		
		this.getContentPane().add(rows_label);
		this.getContentPane().add(rows_combobox);
		this.getContentPane().add(cols_label);
		this.getContentPane().add(cols_combobox);
		this.getContentPane().add(back_button);
		this.getContentPane().add(continue_button);
		
		this.setVisible(true);
	}
	
	/**
	 * @desc Resizing components on frame resize.
	 * @evn GameState.FRAME_SIZE
	 * @env this.rows_label, this.cols_label
	 * @env this.rows_combobox, this.cols_combobox
	 * @env this.back_button, this.continue_button
	 */
	private void resize_components() {
		Dimension content_pane_size = new Dimension(this.getContentPane().getSize());
		int border_offset = 10;
		int labels_width = (int) (content_pane_size.getWidth() * 0.4);
		int labels_height = 20;
		int combobox_width = (int) (content_pane_size.getWidth() * 0.4);
		int combobox_height = 20;
		int button_width = (int) (content_pane_size.getWidth() * 0.25);
		int button_height = (int) (content_pane_size.getWidth() * 0.075);
		int top_margin = (int) (content_pane_size.getHeight() * 0.01);
		rows_label.setBounds(border_offset, border_offset, labels_width, labels_height);
		cols_label.setBounds((int) content_pane_size.getWidth() - labels_width - border_offset,
				border_offset, labels_width, labels_height);
		rows_combobox.setBounds(border_offset, border_offset + labels_height + top_margin,
				combobox_width, combobox_height);
		cols_combobox.setBounds((int) content_pane_size.getWidth() - combobox_width - border_offset,
				border_offset + labels_height + top_margin,
				combobox_width, combobox_height);
		back_button.setBounds(border_offset,
				(int) content_pane_size.getHeight() - button_height - border_offset,
				button_width, button_height);
		continue_button.setBounds((int) content_pane_size.getWidth() - button_width - border_offset,
				(int) content_pane_size.getHeight() - button_height - border_offset,
				button_width, button_height);
	}
	
}
