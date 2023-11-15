/**
 * A node class representing a node in a doubly linked list with a priority value for use in priority queues.
 * @param <T> The type of the data stored in the node.
 * @author Kevin Wu kwu347
 */
public class DLinkedNode<T> {
	private T dataItem;
	private double priority;
	private DLinkedNode<T> next;
	private DLinkedNode<T> prev;
	/**
	 * Creates a new DLinkedNode with the given data and priority values.
	 * @param data The data item stored in the node.
	 * @param prio The priority of the node.
	 */
	public DLinkedNode(T data, double prio) {
		dataItem = data;
		priority = prio;
	}
	/**
	 * Creates a new DLinkedNode with null data and zero priority.
	 */
	public DLinkedNode() {
		this(null, 0);
	}
	/**
	 * Getter methods
	 * 
	 */
	public double getPriority() {
		return priority;
	}

	public T getDataItem() {
		return dataItem;
	}

	public DLinkedNode<T> getNext() {
		return next;
	}

	public DLinkedNode<T> getPrev() {
		return prev;
	}

	/**
	 * Setter methods
	 * @param take in the required information (T data, DLinkedNode<T> nextNode, DLinkedNode<T> prevNode, double newPriority) 
	 */
	public void setData(T data) {
		dataItem = data;
	}

	public void setNext(DLinkedNode<T> nextNode) {
		next = nextNode;
	}

	public void setPrev(DLinkedNode<T> prevNode) {
		prev = prevNode;
	}
	public void setPriority(double newPriority) {
		priority = newPriority;
	}
}