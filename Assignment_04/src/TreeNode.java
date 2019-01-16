/**
 * The TreeNode class consists of a single date element that holds a char value.
 * and can hold a left and right node. The TreeNode class is used to build the
 * expression tree.
 *  
 * @author Casey Ryane
 *
 */
public class TreeNode {

	private char data;
	private TreeNode leftNode;
	private TreeNode rightNode;

	/**
	 * A constructor for the TreeNode class that takes in a char value to populate 
	 * the data variable and sets the left and right nodes to null
	 * 
	 * @param data a char value
	 */
	public TreeNode(char data) {
		this.data = data;
		leftNode = null;
		rightNode = null;
	}

	/**
	 * A constructor for the TreeNode class that takes in a char value, and two TreeNodes
	 * To populate the data value, and left and right nodes.
	 * 
	 * @param data a char value
	 * @param leftNode a TreeNode
	 * @param rightNode a TreeNode
	 */
	public TreeNode(char data, TreeNode leftNode, TreeNode rightNode) {
		this.data = data;
		this.leftNode = leftNode;
		this.rightNode = rightNode;
	}

	/**
	 * Setter for the data value, 
	 * 
	 * @param data a char value
	 */
	public void setData(char data) {
		this.data = data;
	}

	/**
	 * Setter for the left node
	 * 
	 * @param leftNode A TreeNode 
	 */
	public void setLeft(TreeNode leftNode) {
		this.leftNode = leftNode;
	}

	/**
	 * Setter for the right node 
	 * 
	 * @param rightNode A TreeNode
	 */
	public void setRight(TreeNode rightNode) {
		this.rightNode = rightNode;
	}

	/**
	 * Getter for the data held within the TreeNode
	 * 
	 * @return a char value for  data
	 */
	public char getData() {
		return data;
	}

	/**
	 * Getter for the left node within the treeNode
	 * 
	 * @return a TreeNode 
	 */
	public TreeNode getLeft() {
		return leftNode;
	}

	/**
	 * Getter for the right node within the treeNode
	 * 
	 * @return a TreeNode 
	 */
	public TreeNode getRight() {
		return rightNode;
	}
}
