import java.util.ArrayList;

/**
 * The cellStack class utilizes an ArrayList to create a stack of
 * MazeCell objects. The stack works of a FILO model and keeps
 * track of its size at all times;
 *  
 * @author Casey
 *
 * @param <MazeCell>
 */
public class CellStack<MazeCell> {
	
	private ArrayList<MazeCell> cellStack;
	private int size;
	
	/**
	 * Default constructor for the Cellstack, initializes the arrayList and 
	 * set the stack size to 0
	 */
	public CellStack(){
		cellStack = new ArrayList<>();
		size = 0;
	}
	
	/**
	 * Returns the object at the top of the stack
	 * 
	 * @return The mazeCell on the top of the stack
	 */
	public MazeCell peek(){
		return cellStack.get(0);
	}
	
	public MazeCell pop(){
		MazeCell cell = cellStack.get(0);
		cellStack.remove(0);
		size --;
		return cell;
	}
	
	/**
	 * Adds a new MazeCell object to the top of the stack
	 * 
	 * @param cell THe mazeCell to be added to the stack
	 */
	public void push(MazeCell cell){
		cellStack.add(0, cell);
		size ++;
	}
	
	/**
	 * Returns true if the stack contains no elements
	 * False if the stack contains at least one element.
	 * 
	 * @return Boolean value for the state of the stack
	 */
	public boolean isEmpty(){
		if(size > 0){
			return false;
		}else{
		return true;	
		}
	}
	
	/**
	 * Gives the size of the ArrayList used in the stack.
	 * (How many objects are currently located on the stack).
	 * 
	 * @return an int value for the size of the stack.
	 */
	public int size(){
		return size;
	}	
}
