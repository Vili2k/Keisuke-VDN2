package logic;

import java.util.ArrayList;
import java.util.TreeMap;

/**
 * @desc Game state of the program.
 * @author vilip
 */
public abstract class GameState {
	
	/**
	 * @desc Defines minimum and maximum number of grid rows and columns.
	 */
	public static int MIN_ROWS, MIN_COLS, MAX_ROWS, MAX_COLS;
	
	/**
	 * @desc Contains the rows and columns of grid. 
	 */
	public static int ROWS, COLS;
	
	/**
	 * @desc Contains the percentage of grid black squares.
	 */
	public static double BLACK_SQUARES_PERCENTAGE;
	
	/**
	 * @desc Defines if the game mode is endless or not.
	 */
	public static boolean ENDLESS;
	
	/**
	 * @desc Contains playing grid and solved grid states.
	 */
	public static Grid PLAYING_GRID, SOLVED_GRID;
	
	/**
	 * @desc Contains across and down values for the grid.
	 */
	public static TreeMap<Integer, ArrayList<String>> ACROSS_VALUES, DOWN_VALUES;
	
}
