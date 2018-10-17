/**
 * Node class
 * 
 * @author Casey Ryane
 *
 * @param <T>
 */
public class Node<T extends Comparable<T>> {
	
	

	private T data;
	private Node<T> next;

	/**
	 * Constructor for objects of class Node
	 */
	public Node() {
		data = null;
		next = null;
	}

	public Node(T data) {
		this.data = data;
	}

	public T getData() {
		return data;
	}

	public void setData(T o) {
		data = o;
	}

	public Node getNext() {
		return next;
	}

	public void setNext(Node n) {
		next = n;
	}

	public String toString() {
		return "Node: " + getData().toString();
	}
}
