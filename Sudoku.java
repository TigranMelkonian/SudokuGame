import java.util.Scanner;

public class Sudoku {
	public static void main(String[] args) {
		@SuppressWarnings("resource")
		Scanner kbd = new Scanner(System.in);
		System.out.println("Sudoku Game ");
		Sudoku puzzle = new Sudoku();
		puzzle.initializePuzzle(puzzle); // initializing puzzle
		System.out.print("The puzzle is: \n" + puzzle);
		boolean done = false;
		while (!done) { // main game loop
			System.out.println("Instructions: Each time you're prompted, enter a Row, Column, and Value combination. ");
			System.out.println(
					"Enter a (C) to CLEAR puzzle board; (S) to SET an open slot;  (G) to GET possible values;  (Q) to QUIT ");
			String input = kbd.next(); // sets user input ( c s g q) to a
										// variable named input
			kbd.nextLine().toLowerCase(); // converts the user input to lower
											// cases, to avoid any errors 
			if (input.equals("q")) { // if statement for quit
				System.out.println("Thanks for playing.");
				done = true;
				break;

			} else if (input.equals("s")) { // if statement for setting a
											// specific slot
				System.out.println("Which row (1-9) and column (1-9) do you want to set?");
				System.out.println("Row: ");
				int row = kbd.nextInt() - 1;
				System.out.println("Column: ");
				int col = kbd.nextInt() - 1;
				System.out.println("value (1-9): ");
				int value = kbd.nextInt();
				puzzle.addGuess(row, col, value);

			} else if (input.equals("g")) { // if statement for getting allowed
											// values for each slot
				System.out.println("Which row (1-9) and column (1-9) do you want to get??");
				System.out.println("Row: ");
				int row = kbd.nextInt() - 1;
				System.out.println("Column: ");
				int col = kbd.nextInt() - 1;

				boolean valid[] = puzzle.getAllowedvalues(row, col);
				System.out.print("Allowed values are: ");
				for (int i = 0; i < 9; i++) {
					if (valid[i]) {
						System.out.print((i + 1) + " ");
					}
				}
				System.out.println();

			} else if (input.equals("c")) { // resets the puzzle to original
											// puzzle
				puzzle.reset(); // calls reset method on puzzle object
			}
			System.out.print("The puzzle is now: \n" + puzzle);
			} if (puzzle.isFull()) { // checks if puzzle is full by calling
											// isFull method on puzzle object
				System.out.println("Great job, you completed the puzzle!");
				System.out.println("Thanks for playing!");
				done = true;// ends the game
			}
		}
	

	private int board[][]; // initializing board array
	private int start[][]; // initializing start array

	public Sudoku() {
		start = new int[9][9]; // – a 9 x 9 array of boolean values that
								// indicate which squares in board are given
								// values that cannot be change

		board = new int[9][9]; // a 9x9 array of integers that represents the
								// current state
	}

	public String toString() { // creates the puzzle board
		String output = "";

		output += "=====================================\n";
		for (int i = 0; i < 9; i++) {
			output += "| ";
			for (int j = 0; j < 9; j++) {
				if (board[i][j] <= 0) {
					output += "  ";

				} else {
					output += board[i][j] + " ";

				}
				if (j % 3 == 2) {
					output += "| "; // adding a vertical delimiter for each 3X3
									// array

				} else {
					output += ": "; // adding a delimiter that will go
				} // in-between the numbers within the slots
			}
			if (i % 3 == 2) {
				output += "\n=====================================\n"; // adding
																		// a
																		// horizontal
																		// delimiter
																		// for
																		// each
																		// 3X3
																		// array
			} else {
				output += "\n-------------------------------------\n";
			}
		}

		return output;
	}

	public void addInitial(int row, int col, int input) {
		// sets the given square to the given value as an
		// initial value that cannot be changed by the puzzle solver
		start[row][col] = input;
		board[row][col] = input;
	}

	public void addGuess(int row, int col, int input) {
		// only allow addition of users guess if the initial value is 0
		if (col >= 0 && col <= 9 && row >= 0 && row <= 9 && input >= 1 && input <= 9 && start[row][col] == 0) {
			board[row][col] = input;
		}if(!checkPuzzle()){
			board[row][col] = 0;
			System.out.println("That was an invalid move " + input + " already exists in row: " + (row + 1) + " column: " + (col + 1)); // print error statement to the user 
		}
	}

	public int getValueIn(int row, int col) {
		// – returns the value in the given slot
		return board[row][col];
	}

	public void reset() {
		// changes all nonpermanent squares back to blanks
		for (int i = 0; i < 9; i++)
			for (int j = 0; j < 9; j++)
				board[i][j] = start[i][j]; // setting each slot of board equal
											// to that of the puzzle we start
											// out with
	}

	public boolean isFull() {
		// returns true if every square has a value
		for (int i = 0; i < 9; i++)
			for (int j = 0; j < 9; j++)
				if (board[i][j] == 0) {
					return false;
				}
		return true;
	}

	public boolean checkPuzzle() {
		// returns true if the values in the puzzle do not violate the
		// restrictions
		boolean valid = true;
		for (int i = 0; i < 9; i++) {
			valid = valid && checkRow(i); // have created separate methods to
											// check row
			valid = valid && checkCol(i); // column
			valid = valid && checkSub(i); // diagonals for numbers that are
											// already in the selected slots
		}
		return valid;
	}

	public boolean checkSub(int sub) { // method to check diagonals , returns
										// true if no input on the diagonals are
										// alike
		int count[] = new int[10]; // initializing a one dimensional count array
									// with ten inputs
		int rowBase = (sub / 3) * 3;
		int colBase = (sub % 3) * 3;
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				count[board[rowBase + i][colBase + j]]++;
			}
		}

		boolean countIsValid = true;
		for (int i = 1; i <= 9; i++)
			countIsValid = countIsValid && (count[i] <= 1);
		return countIsValid;
	}

	public boolean checkCol(int col) { // method for checking columns, returns
										// true if no input on the specified
										// column are alike
		int count[] = new int[10]; // initializing a one dimensional count array
									// with ten inputs
		for (int row = 0; row < 9; row++) {
			count[board[row][col]]++;
		}
		boolean countIsValid = true;
		for (int i = 1; i <= 9; i++)
			countIsValid = countIsValid && (count[i] <= 1);
		return countIsValid;
	}

	public boolean checkRow(int row) {// method to check Rows , returns true if
										// no input on the specified row are
										// alike
		int count[] = new int[10]; // initializing a one dimensional count array
									// with ten inputs
		for (int col = 0; col < 9; col++) {
			count[board[row][col]]++;
		}

		boolean countIsValid = true;

		for (int i = 1; i <= 9; i++){
			countIsValid = countIsValid && (count[i] <= 1);
		}
		return countIsValid;

	}

	public boolean[] getAllowedvalues(int row, int col) {
		// returns a one-dimensional array of nine
		// Booleans, each of which corresponds to a digit, and is true if the
		// digit can
		// be placed in the given square without violating the restrictions

		int value = board[row][col];
		boolean result[] = new boolean[9];

		for (int val = 1; val <= 9; val++) {
			board[row][col] = val;
			result[val - 1] = checkPuzzle();
		}
		board[row][col] = value;

		return result;

	}

	public  void initializePuzzle(Sudoku puzzle) {
		// adding final values to the puzzle board to be solved
		puzzle.addInitial(0, 3, 2);// calling addInitial method on puzzle object
									// , this particular line sets row 1 column
									// 4 with value of 2
		puzzle.addInitial(0, 4, 6);
		puzzle.addInitial(0, 6, 7);
		puzzle.addInitial(0, 8, 1);
		puzzle.addInitial(1, 0, 6);
		puzzle.addInitial(1, 1, 8);
		puzzle.addInitial(1, 4, 7);
		puzzle.addInitial(1, 7, 9);
		puzzle.addInitial(2, 0, 1);
		puzzle.addInitial(2, 1, 9);
		puzzle.addInitial(2, 5, 4);
		puzzle.addInitial(2, 6, 5);
		puzzle.addInitial(3, 0, 8);
		puzzle.addInitial(3, 1, 2);
		puzzle.addInitial(3, 3, 1);
		puzzle.addInitial(3, 7, 4);
		puzzle.addInitial(4, 2, 4);
		puzzle.addInitial(4, 3, 6);
		puzzle.addInitial(4, 5, 2);
		puzzle.addInitial(4, 6, 9);
		puzzle.addInitial(5, 1, 5);
		puzzle.addInitial(5, 5, 3);
		puzzle.addInitial(5, 7, 2);
		puzzle.addInitial(5, 8, 8);
		puzzle.addInitial(6, 2, 9);
		puzzle.addInitial(6, 3, 3);
		puzzle.addInitial(6, 7, 7);
		puzzle.addInitial(6, 8, 4);
		puzzle.addInitial(7, 1, 4);
		puzzle.addInitial(7, 4, 5);
		puzzle.addInitial(7, 7, 3);
		puzzle.addInitial(7, 8, 6);
		puzzle.addInitial(8, 0, 7);
		puzzle.addInitial(8, 2, 3);
		puzzle.addInitial(8, 4, 1);
		puzzle.addInitial(8, 5, 8);
	}

}
