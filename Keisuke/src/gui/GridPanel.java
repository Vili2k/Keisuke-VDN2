package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import logic.GameState;
import logic.Grid;

/**
 * @desc Panel that holds the grid.
 * @author vilip
 */
public class GridPanel extends JPanel {

	/**
	 * @desc Randomly generated serial version UID.
	 */
	private static final long serialVersionUID = 2648153338425725382L;
	
	private Grid g;
	
	private JTextField[][] grid_cells;
	
	private int cell_size;
	
	private volatile int screen_x, screen_y;
	
	private int delta_x, delta_y;
	
	private volatile int pos_x, pos_y;
	
	private int min_x, min_y, max_x, max_y;
	
	private double zoom;
	
	private double min_zoom, max_zoom;
	
	public GridPanel(Grid g) {
		this.setBorder(new LineBorder(Color.BLACK, 1));
		this.setOpaque(false);
		this.setLayout(null);
		
		this.g = g;
		
		grid_cells = new JTextField[GameState.ROWS][GameState.COLS];
		for (int y = 0; y < GameState.ROWS; y++) {
			for (int x = 0; x < GameState.COLS; x++) {
				grid_cells[y][x] = new JTextField();
				grid_cells[y][x].setDocument(new JTextFieldLimit(1));
				if (g.get()[y][x] == '#') {
					grid_cells[y][x].setBackground(Color.BLACK);
					grid_cells[y][x].setEditable(false);
				} else if (g.get()[y][x] != '-') {
					grid_cells[y][x].setText(g.get()[y][x] + "");
				}
				grid_cells[y][x].setHorizontalAlignment(JTextField.CENTER);
				grid_cells[y][x].addMouseListener(new ComponentClickedListener());
				grid_cells[y][x].addMouseMotionListener(new ComponentDraggedListener());
				grid_cells[y][x].addMouseWheelListener(new ComponentZoomedListener());
				grid_cells[y][x].getDocument().addDocumentListener(new TextFieldChangedListener());
				this.add(grid_cells[y][x]);
			}
		}
	}
	
	public void reset() {
		for (int y = 0; y < GameState.ROWS; y++) {
			for (int x = 0; x < GameState.COLS; x++) {
				grid_cells[y][x].setText("");
				GameState.PLAYING_GRID.reset();
			}
		}
	}
	
	public void set_uneditable() {
		for (int y = 0; y < GameState.ROWS; y++) {
			for (int x = 0; x < GameState.COLS; x++) {
				grid_cells[y][x].setEditable(false);;
			}
		}
	}
	
	public void update_bounds() {	
		min_zoom = 1;
		max_zoom = (double) this.getHeight() * GameState.ROWS / this.getHeight();
		
		if (zoom < min_zoom) zoom = min_zoom;
		else if (zoom > max_zoom) zoom = max_zoom;
		
		cell_size = (int) (this.getHeight() / GameState.ROWS * zoom);
		
		int new_x = pos_x + delta_x;
		int new_y = pos_y + delta_y;
		
		min_x = this.getWidth() - cell_size * GameState.COLS -1;
		max_x = 1;
		if (cell_size * GameState.COLS < this.getWidth()) {
			min_x /= 2;
			max_x = min_x;
		}
		
		min_y = this.getHeight() - cell_size * GameState.ROWS -1;
		max_y = 1;
		
		if (new_x < min_x) new_x = min_x;
		else if (new_x > max_x) new_x = max_x;
		if (new_y < min_y) new_y = min_y;
		else if (new_y > max_y) new_y = max_y;
		
		for (int y = 0; y < GameState.ROWS; y++) {
			for (int x = 0; x < GameState.COLS; x++) {
				grid_cells[y][x].setBounds(new_x + x * cell_size, new_y + y * cell_size, cell_size, cell_size); 
				grid_cells[y][x].setFont(new Font("SansSerif", Font.PLAIN, cell_size / 2));
			}
		}
	}
	
	public void game_update() {
		for (int y = 0; y < GameState.ROWS; y++) {
			for (int x = 0; x < GameState.COLS; x++) {
				if (GameState.PLAYING_GRID.get()[y][x] == '#') continue; 
				else if (grid_cells[y][x].getText().length() > 0) {
					GameState.PLAYING_GRID.set(y, x, grid_cells[y][x].getText().charAt(0));
				} else {
					GameState.PLAYING_GRID.set(y, x, '-');
				}
			}
		}
		if (GameState.PLAYING_GRID.compare(GameState.SOLVED_GRID.get())) {
			if (GameState.ENDLESS) {
				GameState.ROWS = GameState.ROWS + 1;
				GameState.COLS = GameState.COLS + 1;
				GameState.PLAYING_GRID.expand();
				GameState.PLAYING_GRID.fill_black_squares();
				GameState.SOLVED_GRID = new Grid(GameState.PLAYING_GRID.get());
				GameState.SOLVED_GRID.fill_random();
				GameState.ACROSS_VALUES = GameState.SOLVED_GRID.get_across();
				GameState.ACROSS_VALUES = GameState.SOLVED_GRID.get_down();
				
				GameFrame game_frame = (GameFrame) SwingUtilities.getRoot(this);
				game_frame.setup_values_panel();
				
				this.removeAll();
				grid_cells = new JTextField[GameState.ROWS][GameState.COLS];
				for (int y = 0; y < GameState.ROWS; y++) {
					for (int x = 0; x < GameState.COLS; x++) {
						grid_cells[y][x] = new JTextField();
						grid_cells[y][x].setDocument(new JTextFieldLimit(1));
						if (g.get()[y][x] == '#') {
							grid_cells[y][x].setBackground(Color.BLACK);
							grid_cells[y][x].setEditable(false);
						} else if (g.get()[y][x] != '-') {
							grid_cells[y][x].setText(g.get()[y][x] + "");
						}
						grid_cells[y][x].setHorizontalAlignment(JTextField.CENTER);
						grid_cells[y][x].addMouseListener(new ComponentClickedListener());
						grid_cells[y][x].addMouseMotionListener(new ComponentDraggedListener());
						grid_cells[y][x].addMouseWheelListener(new ComponentZoomedListener());
						grid_cells[y][x].getDocument().addDocumentListener(new TextFieldChangedListener());
						this.add(grid_cells[y][x]);
					}
				}
				update_bounds();
				game_update();
			} else {
				System.out.println("gj you win");
				set_uneditable();
			}
		}
	}
	
	private class ComponentClickedListener implements MouseListener {
    	public void mousePressed(MouseEvent e) {
    		screen_x = e.getXOnScreen();
    		screen_y = e.getYOnScreen();
    		
    		pos_x = grid_cells[0][0].getX();
    		pos_y = grid_cells[0][0].getY();
    	} 	
    	public void mouseClicked(MouseEvent e) {}
    	public void mouseReleased(MouseEvent e) {}
    	public void mouseEntered(MouseEvent e) {}
    	public void mouseExited(MouseEvent e) {}   	
	}
	
	private class ComponentDraggedListener implements MouseMotionListener {
        public void mouseDragged(MouseEvent e) {
        	delta_x = e.getXOnScreen() - screen_x;
            delta_y = e.getYOnScreen() - screen_y;
        	update_bounds();
    	}
        public void mouseMoved(MouseEvent e) { }
	}
	
	private class ComponentZoomedListener implements MouseWheelListener {
		public void mouseWheelMoved(MouseWheelEvent e) {
			zoom += (e.getWheelRotation() < 0) ? 0.1 : -0.1;
			update_bounds();
		}
	}
	
	private class TextFieldChangedListener implements DocumentListener {
		public void insertUpdate(DocumentEvent e) {game_update();}
		public void removeUpdate(DocumentEvent e) {game_update();}
		public void changedUpdate(DocumentEvent e) {game_update();}
	}
	
}
