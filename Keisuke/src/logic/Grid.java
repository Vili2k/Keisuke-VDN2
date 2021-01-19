package logic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.TreeMap;

/**
 * @desc Logic for the grid.
 * @author vilip
 */
public class Grid {
	
	/**
	 * @desc Contains grid state.
	 */
	private char[][] g;
	
	/**
	 * @desc Creates a new grid of size r * c.
	 * @env GameState.ROWS, GameState.COLS
	 * @env this.g
	 */
	public Grid() {
		this.g = new char[GameState.ROWS][GameState.COLS];
		for (int y = 0; y < GameState.ROWS; y++) {
			for (int x = 0; x < GameState.COLS; x++) {
				this.g[y][x] = '-';
			}
		}
	}
	
	/**
	 * @desc Creates a new grid with passed grid.
	 * @param g - grid
	 * @env GameState.ROWS, GameState.COLS
	 * @env this.g
	 */
	public Grid(char[][] g) {
		this.g = new char[GameState.ROWS][GameState.COLS];
		for (int y = 0; y < GameState.ROWS; y++) {
			for (int x = 0; x < GameState.COLS; x++) {
				this.g[y][x] = g[y][x]; 
			}
		}
	}
	
	/**
	 * @desc Prints the grid.
	 * @env GameState.ROWS, GameState.COLS
	 * @env this.g
	 */
	public void print() {
		for (int y = 0; y < GameState.ROWS; y++) {
			for (int x = 0; x < GameState.COLS; x++) {
				System.out.print(this.g[y][x] + " "); 
			}
			System.out.println();
		}
		System.out.println();
	}
	
	/**
	 * @desc Fills grid with random black squares with passed percentage.
	 * @env GameState.BLACK_SQUARES_PERCENTAGE, GameState.ROWS, GameState.COLS
	 * @env this.g
	 */
	public void fill_black_squares() {
		int n = (int) (GameState.BLACK_SQUARES_PERCENTAGE * GameState.ROWS * GameState.COLS);
		int free = 0;
		for (int y = 0; y < GameState.ROWS; y++) {
			for (int x = 0; x < GameState.COLS; x++) {
				if (this.g[y][x] == '-') free++;
				else if (this.g[y][x] == '#') n--;
			}
		}
		if (free < n) {
			System.out.println("Not enough free space!");
			return;
		}
		for (int i = 0; i < n; i++) {
			int rand_y, rand_x;
			do {
				rand_y = (int) (Math.random() * GameState.ROWS);
				rand_x = (int) (Math.random() * GameState.COLS);
			} while (this.g[rand_y][rand_x] != '-');
			this.g[rand_y][rand_x] = '#';
		}
	}
	
	/**
	 * @desc Fills empty cells of the grid with random values 0-9.
	 * @env GameState.ROWS, GameState.COLS
	 * @env this.g
	 */
	public void fill_random() {
		for (int y = 0; y < GameState.ROWS; y++) {
			for (int x = 0; x < GameState.COLS; x++) {
				if (this.g[y][x] == '-') {
					int rand_val = (int) (Math.random() * 10);
					this.g[y][x] = (char) (rand_val + '0');
				}
			}
		}
	}
	
	/**
	 * @desc Expands grid in y and x direction.
	 * @env GameState.ROWS, GameState.COLS
	 * @env this.g
	 */
	public void expand() {
		char[][] new_g = new char[GameState.ROWS + 1][GameState.COLS + 1];
		for (int y = 0; y < GameState.ROWS; y++) {
			for (int x = 0; x < GameState.COLS; x++) {
				new_g[y][x] = g[y][x];
			}
		}
		this.g = new_g;
		GameState.ROWS++;
		GameState.COLS++;
	}
	
	/**
	 * @desc Returns current grid state.
	 * @return this.g;
	 * @env this.g
	 */
	public char[][] get() {
		return this.g;
	}
	
	/**
	 * @desc Set character at (x, y) to c.
	 * @param y - y coordinate
	 * @param x - x coordinate
	 * @param c - value
	 * @env this.g
	 */
	public void set(int y, int x, char c) {
		this.g[y][x] = c;
	}
	
	/**
	 * @desc Get across values sorted by length.
	 * @return across_values
	 * @env GameState.ROWS, GameState.COLS
	 * @env this.g
	 */
	public TreeMap<Integer, ArrayList<String>> get_across() {
		TreeMap<Integer, ArrayList<String>> across_values = new TreeMap<>();
		String val = "";
		for (int y = 0; y < GameState.ROWS; y++) {
			for (int x = 0; x < GameState.COLS; x++) {
				int key = val.length();
				if (g[y][x] == '-') {
					System.out.println("Grid not solved!");
					return null;
				} else if (g[y][x] == '#') {
					if (key > 0) {
						if (across_values.containsKey(key)) {
							ArrayList<String> values = across_values.get(key);
							values.add(val);
							across_values.replace(key, values);
						} else {
							ArrayList<String> values = new ArrayList<>();
							values.add(val);
							across_values.put(key, values);
						}
						val = "";
					}
				} else {
					val += g[y][x];
				}
			}
			int key = val.length();
			if (key > 0) {
				if (across_values.containsKey(key)) {
					ArrayList<String> values = across_values.get(key);
					values.add(val);
					across_values.replace(key, values);
				} else {
					ArrayList<String> values = new ArrayList<>();
					values.add(val);
					across_values.put(key, values);
				}
				val = "";
			}
		}
		for (int key : across_values.keySet()) {
			ArrayList<String> values = across_values.get(key);
			Collections.sort(across_values.get(key));
			across_values.replace(key, values);
			System.out.print(key + ": ");
			for (String s : values) {
				System.out.print(s + ", ");
			}
			System.out.println();
		}
		System.out.println();
		return across_values;
	}
	
	/**
	 * @desc Get down values sorted by length.
	 * @return down_values
	 * @env GameState.ROWS, GameState.COLS
	 * @env this.g
	 */
	public TreeMap<Integer, ArrayList<String>> get_down() {
		TreeMap<Integer, ArrayList<String>> down_values = new TreeMap<>();
		String val = "";
		for (int x = 0; x < GameState.COLS; x++) {
			for (int y = 0; y < GameState.ROWS; y++) {
				int key = val.length();
				if (g[y][x] == '-') {
					System.out.println("Grid not solved!");
					return null;
				} else if (g[y][x] == '#') {
					if (key > 0) {
						if (down_values.containsKey(key)) {
							ArrayList<String> values = down_values.get(key);
							values.add(val);
							down_values.replace(key, values);
						} else {
							ArrayList<String> values = new ArrayList<>();
							values.add(val);
							down_values.put(key, values);
						}
					}
					val = "";
				} else {
					val += g[y][x];
				}
			}
			int key = val.length();
			if (key > 0) {
				if (down_values.containsKey(key)) {
					ArrayList<String> values = down_values.get(key);
					values.add(val);
					down_values.replace(key, values);
				} else {
					ArrayList<String> values = new ArrayList<>();
					values.add(val);
					down_values.put(key, values);
				}
			}
			val = "";
		}
		for (int key : down_values.keySet()) {
			ArrayList<String> values = down_values.get(key);
			Collections.sort(down_values.get(key));
			down_values.replace(key, values);
			System.out.print(key + ": ");
			for (String s : values) {
				System.out.print(s + ", ");
			}
			System.out.println();
		}
		System.out.println();
		return down_values;
	}
	
}
