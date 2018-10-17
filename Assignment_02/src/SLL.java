/**
 * Singly linked list 
 * 
 * @author Casey Ryane
 *
 * @param <T>
 */
public class SLL<T extends Comparable<T>> {

	
	
	private Node<T> head;
	private Node<T> tail;
	private int size;

	public SLL() {
		head = null;
		size = 0;
	}

	public void add(T o) {
		Node<T> temp = new Node(o);
		Node<T> curr = head;
		if (head == null) {
			head = temp;
		} else {
			while (curr.getNext() != null) {
				curr = curr.getNext();
			}
			curr.setNext(temp);
		}
		size ++;
	}
	
	
	public void addByLength(T o) {
		Node<T> temp = new Node(o);
		Node<T> curr = head;
		if (head == null) {
			head = temp;
		} else {
			while (curr.getNext() != null) {
				curr = curr.getNext();
			}
			curr.setNext(temp);
		}
		size ++;
	}

	
	
	
	
	
	
	public T get(int index) {
		Node<T>  curr = head;
		for (int i = 0; i < index; i++) {
			curr = curr.getNext();
		}
		return curr.getData();
	}
 

	public int size() {
    return this.size;
	}
	


}
