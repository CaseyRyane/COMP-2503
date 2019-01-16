import java.io.*;
import java.util.*;

/*
 *  The maze class facilitates the loading and validating of input from
 * 	a text file into an array and then solving said maze while displaying each step
 * 	as prompted by the user.
 */
public class Maze {
	private MazeCell[][] maze2DArray;
	private String fileName;
	private int columns;
	private int rows;
	private Stack<MazeCell> s1;
	private File mazeFile;
	private Scanner fileReader;

	/**
	 * Constructor for the MAze class that takes in a file name and initializes a
	 * fileReader to pars through a given file. Stack that takes in mazeCells is
	 * initialized.
	 * 
	 * @param fileName
	 *            THe file to be loaded and sued as a maze.
	 * @throws FileNotFoundException
	 */
	public Maze(String fileName) throws FileNotFoundException {
		this.fileName = fileName;
		mazeFile = new File(fileName);
		fileReader = new Scanner(mazeFile);
		s1 = new Stack<MazeCell>();

	}

	/**
	 * This method loads the 2d array that contains the state of the maze as a whole
	 * by parsing through the given file and creating mazeCell objects based on the
	 * letter value given. Checks are performed to ensure the maze contains both a
	 * start and end value and does not contain any characters that are unexpected
	 * 
	 * @return A char value indicating if there is no Start or end to the maze given
	 *         in the maze file.
	 * @throws FileNotFoundException
	 */
	public char loadArray() throws FileNotFoundException {

		fileReader.nextLine();
		maze2DArray = new MazeCell[rows + 2][columns + 2];

		boolean start = false;
		boolean end = false;
		boolean extraChar = false;
		for (int r = 1; r < rows + 1; r++) {
			String nextLine = fileReader.nextLine();
			StringTokenizer st = new StringTokenizer(nextLine);
			int c = 1;
			while (st.hasMoreTokens() && c < columns + 1) {

				String input = st.nextToken();
				char cellType = 'a';
				if (input.equals("W")) {
					cellType = 'W';
				} else if (input.equals("M")) {
					cellType = 'M';
					start = true;
				} else if (input.equals("C")) {
					cellType = 'C';
					end = true;
				} else if (input.equals("O")) {
					cellType = 'O';
				} else if (input.equals(" ")) {
					cellType = ' ';
				} else if (input.equals("S")) {
					cellType = 'S';
				} else if (input.equals("X")) {
					cellType = 'X';
				} else {
					extraChar = true;
				}
				MazeCell mazeCell = new MazeCell(cellType);
				maze2DArray[r][c] = mazeCell;
				c++;
			}
		}
		fileReader.close();
		char status = ' ';
		if (extraChar) {
			status = 'Z';
		} else if (!end) {
			status = 'C';
		} else if (!start) {
			status = 'M';
		}
		return status;
	}

	/**
	 * Retrieves the number of columns and rows stated in a given file and assigns
	 * those values to the rows and columns variables.
	 */
	public void getColAndRow() {
		rows = fileReader.nextInt();
		columns = fileReader.nextInt();
	}

	/**
	 * Adds mazeCells as neighbours for all mazeCells contained within the 2D
	 * MazeCell array. THe neighbours in position 1 and 4 are assigned first
	 * (neighbours to the immediate left and right of a current mazeCells position).
	 * Neighbours in positions 5 and 0 are assigned next, (Neighbours above the
	 * current cell) Neighbours in position 3 and 2 are assigned last. (Neighbours
	 * below current cell) Each neighbour above or below accounts for offset when
	 * assigned neighbours.
	 */
	public void addNeighbours() {
		for (int r = 1; r < rows + 1; r++) {
			for (int c = 1; c < columns + 1; c++) {

				int offset = 1 - r % 2;
				maze2DArray[r][c].setNeighbours(maze2DArray[r][c + 1], 1);
				maze2DArray[r][c].setNeighbours(maze2DArray[r][c - 1], 4);
				maze2DArray[r][c].setNeighbours(maze2DArray[r - 1][c + (-1 + offset)], 5);
				maze2DArray[r][c].setNeighbours(maze2DArray[r - 1][c + (0 + offset)], 0);
				maze2DArray[r][c].setNeighbours(maze2DArray[r + 1][c + (-1 + offset)], 3);
				maze2DArray[r][c].setNeighbours(maze2DArray[r + 1][c + (0 + offset)], 2);
			}
		}
	}

	/**
	 * Compiles the contents of the 2D maze array into a formatted readable string
	 * using characters to represent each maze cell type.
	 * 
	 * @return formatted string of 2d maze cell
	 * 
	 */
	public String compileMaze() {
		String maze = String.format("%n");

		for (int r = 1; r < rows + 1; r++) {
			for (int c = 1; c < columns + 1; c++) {
				maze += String.format(maze2DArray[r][c].toString());
			}
			maze += String.format("%n");
			int offset = 1 - r % 2;
			if (offset < 1) {
				maze += String.format(" ");
			}
		}
		maze += String.format("%n");
		return maze;
	}

	/**
	 * Prints a visualization of the 2D maze to the screen.
	 */
	private void printMaze() {
		System.out.print(compileMaze());
	}

	/**
	 * Solves the 2D maze utilizing the CellStack and showing the current path and rejected paths as the
	 * "mouse" progresses fromx the starting position to the end.
	 * Each progression is shown after prompting and receiving input from user.
	 */
	public void solveMaze() {

		MazeCell startingPoint = getStartingPoint();
		s1.push(startingPoint);
		boolean endFound = false;
		int numSteps = 0;
		MazeCell currentCell;

		while (!s1.isEmpty() && !endFound) {
			currentCell = s1.peek();
			MazeCell neighbourCell;

			if (currentCell.getType() == 'C' || currentCell.getEnd()) {
				currentCell.setType('S');
				endFound = true;
			} else {
				promptAndDisplayStep(currentCell);
				numSteps++;
			}
		}
		if(endFound){
		printMaze();
		System.out.println("Maze has been solved in " + numSteps + " steps.");
		}else{
			printMaze();
			System.out.println( numSteps + " steps performed. End point has been deemed unreachable.");
		}
	}

	/**
	 * Gets the next neighbour needed before moving on to the next step
	 * in solving the maze. prompts the user to continue and prints the current
	 * state of the maze
	 * @param currentCell
	 */
	private void promptAndDisplayStep(MazeCell currentCell) {
		getNeighbourCell(currentCell);
		promptUser();
		printMaze();

	}

	/**
	 * Finds the next valid mazeCell neighbour and adds it to the stack.
	 * IF the next neighbour is the end point the status of the maze cell
	 * is updated to "end".
	 * If there are no available neighbours the status of the current cell is
	 * rejected and removed from the stack and current path.
	 * 
	 * @param currentCell the current cell in the maze being processed.
	 */
	private void getNeighbourCell(MazeCell currentCell) {

		MazeCell neighbourCell;
		boolean availableNeighbour = false;
		for (int i = 0; i <= 5; i++) {
			try {
				neighbourCell = currentCell.getNeighbour(i);
				if (validNeighbour(neighbourCell)) {
					s1.push(neighbourCell);
					
					if (neighbourCell.getType() == 'C') {
						neighbourCell.setEnd(true);
					}
					
					neighbourCell.setType(' ');
					availableNeighbour = true;
					i = 6;
				}
			} catch (NullPointerException e) {

			}
		}
		if (!availableNeighbour) {
			s1.peek().setType('X');
			s1.pop();
		}
	}

	/**
	 * Validates the neighbour, ensuring it  is a valid
	 * target to progress to (Open or end).
	 * 
	 * @param cell to analyze.
	 * @return boolean value for validity of neighbour.
	 */
	private boolean validNeighbour(MazeCell cell) {
		char cellType = cell.getType();
		if (cellType != 'W' && cellType != 'M' && cellType != ' ' && cellType != 'X' && cell != null) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Prompts the user and wait for input.
	 * USed to ensure user wants to move on to the next step in 
	 * solving the maze.
	 */
	private void promptUser() {
		System.out.print("Press \"ENTER\" to continue...");
		Scanner scanner = new Scanner(System.in);
		scanner.nextLine();
	}

	/**
	 * Retrieves the starting point within the 2D maze array.
	 * (Cell type value "M")/
	 * 
	 * @return the mazeCell that is the starting position
	 * 
	 */
	private MazeCell getStartingPoint() {
		MazeCell starting = null;
		for (int r = 1; r < rows + 1; r++) {
			for (int c = 1; c < columns + 1; c++) {
				MazeCell currCell = maze2DArray[r][c];
				if (currCell.getType() == 'M') {
					starting = currCell;
				}
			}
		}
		return starting;
	}
}
