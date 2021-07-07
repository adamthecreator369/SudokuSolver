/* Created by Adam Jost on 4/27/2021 */
// Description: Using recursion, this program finds and prints all solutions 
// for a 9x9 or 16x16 Sudoku puzzle.

package sudoku.solver;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class SudokuSolver {

	public static void main(String[] args) throws IOException {
		
		// Input and output streams.
		FileInputStream file_in = new FileInputStream("puzzle.txt");
		Scanner scanner = new Scanner(file_in);
		FileOutputStream file_out = new FileOutputStream("solutions.txt");
		PrintWriter writer = new PrintWriter(file_out);

		// Represents the Sudoku Puzzle.
		SudokuPuzzle puzzle;  
		
		// Holds the puzzle board attempting to be solved. 
		ArrayList<ArrayList<Character>> board = new ArrayList<ArrayList<Character>>(); 
		
		// Character to represent blank spaces in the Sudoku Puzzle.
		final Character BLANK = '.';
		
		// Valid Characters used for finding solutions
		
		// Characters to be used if Puzzle attempting to find solutions for is a 9x9 puzzle.
		ArrayList<Character> choices_9x9 = new ArrayList<>(
				Arrays.asList('1', '2', '3', '4', '5', '6', '7', '8', '9')); 
		
		// Characters to be used if Puzzle attempting to find solutions for is a 16x16 puzzle.
		ArrayList<Character> choices_16x16 = new ArrayList<>(
				Arrays.asList('0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 
						'd', 'e', 'f')); 
		
		while (scanner.hasNext()) {
			
			// Read in the next line representing the next row of the Puzzle board.
			String line = scanner.nextLine();
			
			// New row of the puzzle board.
			ArrayList<Character> row = new ArrayList<Character>();
			
			// Populate the row with Characters from the read-in and stored line.
			for (int j = 0; j < line.length(); j++) { row.add(line.charAt(j)); }
			board.add(row);

		}
		
		// If the Sudoku Puzzle attempting to be solved is a 16x16 or a 9x9 board, instantiate accordingly.
		if (board.size() == choices_16x16.size()) { puzzle = new SudokuPuzzle(board, BLANK, choices_16x16); }
		else  { puzzle = new SudokuPuzzle(board, BLANK, choices_9x9); }
		
		// Find and print all possible solutions to the puzzle, if any.
		puzzle.print_all_solutions(writer); 
	

		// Close streams
		writer.close(); 
		scanner.close();
		file_out.close();
		file_in.close();
		
	}

}
