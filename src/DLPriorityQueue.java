/**
 * This class implements the PriorityQueueADT interface using a doubly linked list data structure.
 * The elements in the list are sorted according to their priority value, with the smallest
 * priority value being at the front of the list.
 * @author Kevin Wu kwu347
 * @param <T> The type of elements that are stored in the priority queue.
 */
public class DLPriorityQueue<T> implements PriorityQueueADT<T> {
	private DLinkedNode<T> front;
	private DLinkedNode<T> rear;
	private int count;

	public DLPriorityQueue() {
		front = null;
		rear = null;
		count = 0;
	}
	/**
	 * Adds an element to the priority queue with the specified priority.
	 * If the queue is empty, the element becomes the first and last element of the queue.
	 * If the priority is less than the priority of the current front element, the element becomes
	 * the new front element of the queue.
	 * Otherwise, the element is inserted at the appropriate position in the queue.
	 * @param data The data element to be added to the queue.
	 * @param priority The priority of the data element.
	 */
	public void add (T data, double priority) {
		DLinkedNode<T> newNode = new DLinkedNode<T>(data, priority);
		if (isEmpty()) {
			front = newNode;
			rear = newNode;
		} else if (priority < front.getPriority()) {
			newNode.setNext(front);
			front.setPrev(newNode);
			front = newNode;
		} else {
			DLinkedNode<T> current = front;
			while (current.getNext() != null && priority >= current.getNext().getPriority()) {
				current = current.getNext();
			}
			newNode.setNext(current.getNext());
			if (current.getNext() != null) {
				current.getNext().setPrev(newNode);
			} else {
				rear = newNode;
			}
			current.setNext(newNode);
			newNode.setPrev(current);
		}
		count++;
	}
	/**
	 * Removes and returns the element with the smallest priority from the priority queue.
	 * If the queue is empty, an EmptyPriorityQueueException is thrown.
	 * @return The data element with the smallest priority in the queue.
	 * @throws EmptyPriorityQueueException If the priority queue is empty.
	 */
	public T removeMin() throws EmptyPriorityQueueException {
		if (isEmpty()) {
			throw new EmptyPriorityQueueException("The priority queue is empty.");
		}
		DLinkedNode<T> node = front;
		if (front == rear) {
			front = null;
			rear = null;
		}
		else {
			front = node.getNext();
		}
		count--;
		return node.getDataItem();
	}

	/**
	 * Updates the priority of an element in the queue.
	 * If the element is not in the queue, an InvalidElementException is thrown.
	 * @param dataitem The data element whose priority is to be updated.
	 * @param newPriority The new priority of the data element.
	 * @throws InvalidElementException If the data element is not in the queue.
	 */
	public void updatePriority(T dataitem, double newPriority) throws InvalidElementException {
		if (isEmpty()) {
			throw new InvalidElementException("The priority queue is empty.");
		}
		DLinkedNode<T> current = front;
		while (current != null && !current.getDataItem().equals(dataitem)) {
			current = current.getNext();
		}

		if(current == null){
			throw new InvalidElementException("item not in priority queue");
		} 
		if (current == front){
			if (count > 1){
				current.getNext().setPrev(null);
			}
			front = current.getNext();
		} 
		else {
			current.getPrev().setNext(current.getNext());
		}
		if (current == rear){
			if (count > 1){
				current.getPrev().setNext(null);
			}
			rear = current.getPrev();
		}
		else {
			current.getNext().setPrev(current.getPrev());
		}
		count--;
		add(dataitem, newPriority);
	}


	/**
	 * Checks of the Priority Queue is empty or not
	 * @return True or False based on if it is empty
	 */
	public boolean isEmpty() {
		return count == 0;
	}
	/**
	 * gives the size of the Priority Queue
	 * @return a number with the size of the queue
	 */
	public int size() {
		return count;
	}
	/**
	 * Converts a priority queue into a string
	 * @return a String of the priority queue in it
	 */
	public String toString() {
		String sentence = "";
		DLinkedNode<T> currentNode = front;
		while (currentNode != null) {
			sentence += currentNode.getDataItem();
			currentNode = currentNode.getNext();
		}
		return sentence;
	}
	/**
	 * getter method for the Priority Queue
	 * @return the rear node of a priorityQueue
	 */
	public DLinkedNode<T> getRear() {
		return rear;
	}

}    