import java.util.*;
import java.io.FileNotFoundException;

/**
 * This class runs the maze solving program.
 * A file name is taken from the user and validated. 
 * A new Maze object is created using the given file name.
 * The status of the maze at a given point in time is printed to the screen\
 * All exceptions resulting in the program being unable to run are 
 * displayed on the screen for the user with a reasoning behind termination.
 * 
 * @author Casey Ryane
 *
 */
public class UserInterface {

	private Scanner sc = new Scanner(System.in);
	private Maze maze;
	private static UserInterface UI;

	/**
	 * Main access point for the Payroll program
	 * 
	 * @param args
	 *            unused
	 * @throws FileNotFoundException
	 */
	public static void main(String[] args) {
		UI = new UserInterface();
		UI.run();

	}

	/**
	 * The maze solving program is run.
	 * A file name is take from the user and maze object created using
	 * said file name.
	 * The maze is loaded into the 2d Maze array
	 * Neighbours are assigned for every mazeCell
	 * The status of the maze is displayed to the screen
	 * The maze is solved.
	 * if invalid files or invalid content within files is presented a detailed
	 * error message is printed out and program terminated.
	 * 
	 */
	private void run() {

		String fileName = "";
		char status = ' ';
		try {
			System.out.print("Please Enter File name: ");
			fileName = getFileName();
			maze = new Maze(fileName);

			try {
				maze.getColAndRow();

				try {
					status = maze.loadArray();
					boolean validInput = checkStatus(status);

					if (validInput) {
						maze.addNeighbours();
						printMaze();
						maze.solveMaze();
					}
				} catch (NullPointerException e) {
					System.out
							.println("Number of columns/rows do not match column/row decleration. Program Terminated");
				}
			} catch (InputMismatchException e) {
				System.out.println("Invalid value in row/column decleration: Line 1. Program Terminated.");

			}
		} catch (FileNotFoundException e) {
			System.out.println("Invalid File Name: \"" + fileName + "\" Program Terminated.");
		}
	}

	/**
	 * The status of the validity of the maze given is checked.
	 * A message is displayed if..
	 * No starting or end position is available in the maze.
	 * An unknown character has been found in the given file
	 * 
	 * @param status
	 * @return
	 */
	private boolean checkStatus(char status) {
		boolean validFile = false;
		if (status == 'Z') {
			System.out.println("Invalid Character present in maze file. Program Terminated");

		} else if (status == 'C') {
			System.out.println("No end point found in maze file. Program Terminated");

		} else if (status == 'M') {
			System.out.println("No Starting point found in maze file. Program Terminated");

		} else {
			validFile = true;
		}
		return validFile;
	}

	
	/**
	 * Prints the state of the 2D maze to the screen mazed on the 
	 * input from the Maze class.
	 */
	private void printMaze() {
		System.out.print(maze.compileMaze());
	}

	/**
	 * Retrieves the name of the file the user wishes to use
	 * 
	 * @return Maze file name.
	 */
	private String getFileName() {
		System.out.print("Input file name: ");
		return sc.next();
	}

}
