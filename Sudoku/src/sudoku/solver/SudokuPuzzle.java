/* Created by Adam Jost on 4/27/2021 */

package sudoku.solver;

import java.io.PrintWriter;
import java.util.ArrayList;

public class SudokuPuzzle {

	// Data fields
	private ArrayList<ArrayList<Character>> puzzle_board; 
	private final int BOARD_SIZE;   // The size of the puzzle board (9 for 9x9 or 16 for 16x16)
	private final int SECTION_SIZE; // The size of a board subsection (3 or 4)
	private final Character BLANK;  // Character representing blank spaces on the puzzle board.
	private final ArrayList<Character> CHOICES; // List of available and valid choices to be placed in blank spaces.
	private int solution_count;  // The number of solutions found. Used for the printed heading of each solution in output file.

	// Constructor

	SudokuPuzzle(ArrayList<ArrayList<Character>> puzzle, Character blank_character, ArrayList<Character> possible_choices) {
		puzzle_board = puzzle;
		BOARD_SIZE = puzzle.size(); 
		SECTION_SIZE = (int) Math.sqrt(puzzle.size()); 
		BLANK = blank_character;  
		CHOICES = possible_choices; 
		solution_count = 1; 
	}
	

	// Class-member methods

	/**
	 * Checks if an attempt to place a character in a specific column violates Sudoku constraints.
	 * @param board: The current SudokuPuzzle board.
	 * @param column: The column where the character is attempting to be placed.
	 * @param possible_choice: The character being checked.
	 * @returns: {true} if placement of the character in the position of that column is valid, {false} if not.
	 */
	private boolean validate_column(int column, Character possible_choice) {

		for (ArrayList<Character> row : puzzle_board) { // Iterate through the column searching for the Character.
			if (row.get(column) == possible_choice) { // If found,
				return false; // It cannot be placed in this column.
			}
		}
		return true; // If not found, it can be placed in the column.
	}

	/**
	 * Checks if an attempt to place a character in a specific row violates Sudoku constraints.
	 * @param board: The current SudokuPuzzle board.
	 * @param row: The row where the character is attempting to be placed.
	 * @param possible_choice: The character being checked.
	 * @returns: {true} if placement of the character in the position of that row is valid, {false} if not.
	 */
	private boolean validate_row(int row, Character possible_choice) {

		for (int i = 0; i < BOARD_SIZE; i++) { // Iterate through row checking for character.
			if (puzzle_board.get(row).get(i) == possible_choice) {  // If found, 
				return false;  // it cannot be placed in the row.
			}
		}
		return true; // If not found, it is valid and can be placed in the row.
	}

	/**
	 * Checks if an attempt to place a character in a specific board section violates Sudoku constraints.
	 * @param board: The current SudokuPuzzle board.
	 * @param section: The section where the character is attempting to be placed.
	 * @param possible_choice: The character being checked.
	 * @returns: {true} if placement of the character in the position of the current section is valid, {false} if not.
	 */
	private boolean validate_section(int row, int col, Character possible_choice) {

		for (int i = 0; i < BOARD_SIZE; i++) { // Search board section looking for the character.
			if (puzzle_board.get(SECTION_SIZE * (row / SECTION_SIZE) + i / SECTION_SIZE)
					.get(SECTION_SIZE * (col / SECTION_SIZE) + i % SECTION_SIZE) == possible_choice) {
				return false; // If found, it cannot be placed in the section.
			}
		}
		return true; // If not found, it can be placed in the section.
	}

	/**
	 * Checks if an attempt to place a character on the Sudoku board violate any Sudoku constraints.
	 * @param board: The current SudokuPuzzle board.
	 * @param row: The row where the Character is attempting to be placed.
	 * @param column: The column where the Character is attempting to be placed.
	 * @param possible_choice: The Character being checked.
	 * @returns: {true} if placement of the Character in the current position on the board is valid, {false} if not.
	 */
	private boolean validate_choice(int row, int column, Character possible_choice) {
		
		// Search section, row, and column for the Character. If found it cannot be placed here. 
		// If not found, it is safe to place the Character in the position.
		return validate_row(row, possible_choice) && validate_column(column, possible_choice)
				&& validate_section(row, column, possible_choice);
	}
	
	
	/**
	 * Attempts to find and print each and every possible solution to a Sudoku puzzle.  
	 * @param board: The Sudoku puzzle board.
	 * @param writer: The PrintWriter being used to print a solution if and when one is found.
	 * @returns: {true} if current attempt finds a valid solution, {false} otherwise.
	 */
	public boolean print_all_solutions(PrintWriter writer) {
		
		// Iterate through all board positions.
		for (int i = 0; i < BOARD_SIZE; i++) {
			for (int j = 0; j < BOARD_SIZE; j++) {
				// If a blank spot is found.
				if (puzzle_board.get(i).get(j) == BLANK) {
					// Iterate through all possible Characters.
					for (Character choice : CHOICES) {
						// If a valid choice is found.
						if (validate_choice(i, j, choice)) {
							// Place the Character in place on the board.
							puzzle_board.get(i).set(j, choice);
							// Go to the next blank space and complete the process again.
							// This continues until a valid solution is found or 
							// proven to not be possible.
							print_all_solutions(writer);
						}
						// If the choice is not valid then leave it blank.
						puzzle_board.get(i).set(j, BLANK);
					}
					// A blank space remains with no valid item to place in that position.
					// Therefore we must exit since this attempt will not result in a valid solution.
					return false; 
				}
			}
		}
		// A valid solution was found, so we now write the solution to the output file.
		print_solution(writer);
		return true; 
	}

	/**
	 * Prints a Sudoku puzzle board.
	 * @param board: The Sudoku board to be printed.
	 * @param writer: The PrintWriter used to write the board to the output file.
	 */
	private void print_solution(PrintWriter writer) {
		
		// Write solution heading to the output file.
		writer.println("Solution " + solution_count++ + "\n");
		
		// Iterate through solution Characters printing each to the output file.
		for (int i = 0; i < BOARD_SIZE; i++) {
			for (int j = 0; j < BOARD_SIZE; j++) {
				writer.print(puzzle_board.get(i).get(j));
			}
			writer.println();
		}
		// Add a blank line between solutions.
		writer.println(); 
	}

}
