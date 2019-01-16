/**
 * The MazeCell class holds data pertaining to each
 * individual cell within an array that make up a maze
 * 
 * @author Casey Ryane
 *
 */
public class MazeCell {

		private char type;
		private boolean end;
		private MazeCell[] neighbours = new MazeCell[6];
		
		
		/*
		 * Constructor for the MazeCell that takes in a "Type" value and 
		 * initializes the type to given type and the "end value" to false;
		 */
		public MazeCell(char type){
			this.type = type;		
			end = false;
		}
		
		/**
		 * A Neighbour at position 0 - 5 is set with a given mazeCell value
		 * 
		 * @param neighbour MazeCell that will be set as a neighbour
		 * @param i index of where in the array the neighbour is placed.
		 */
		public void setNeighbours(MazeCell neighbour, int i){
			this.neighbours[i] = neighbour;
		}
		
		/**
		 * Returns the neighbour when given the position in 
		 * relation to this mazeCell
		 * @param i index in the neighbour array
		 * @return
		 */
		public MazeCell getNeighbour(int i){
			return neighbours[i];	
		} 
			
	
		/**
		 * Sets the type of a neighbour 
		 * 
		 * @param type a char value correlating to a mazeCell type
		 */
		public void setType(char type){
			this.type = type;
		}
		
		/**
		 * Sets the end parameter to true or false based on boolean value
		 * 
		 * @param end
		 */
		public void setEnd(boolean end){
			this.end = end;
		}
		
		public char getType(){
			return type;
		}
		
		/**
		 * Returns a boolean value for whether the mazeCell is the end cell.
		 * @return A boolean correlating with the end value.
		 */
		public boolean getEnd(){
			return end;
		}
		
		/**
		 * Returns the "type" the current cell is as a string
		 */
		public String toString(){
			return type + " ";
		}
		
	}
	

