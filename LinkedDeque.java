import java.util.NoSuchElementException;

/**
 * Your implementation of a LinkedDeque.
 *
 * @author DIVYESH VED
 * @version 1.0
 * @userid dved6
 * @GTID 903600373
 *
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 *
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */
public class LinkedDeque<T> {

    // Do not add new instance variables or modify existing ones.
    private LinkedNode<T> head;
    private LinkedNode<T> tail;
    private int size;

    // Do not add a constructor.

    /**
     * Adds the element to the front of the deque.
     *
     * Must be O(1).
     *
     * @param data the data to add to the front of the deque
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addFirst(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data entered is invalid. Please enter valid data");
        }
        if (size == 0) {
            LinkedNode<T> newNode = new LinkedNode<>(data,null,null);
            head = newNode;
            tail = newNode;
            size = 1;
        }else {
            LinkedNode<T> newNode = new LinkedNode<>(data,null,head);
            head.setPrevious(newNode);
            head = newNode;
            size++;
        }
    }

    /**
     * Adds the element to the back of the deque.
     *
     * Must be O(1).
     *
     * @param data the data to add to the back of the deque
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addLast(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data entered is invalid. Please enter valid data.");
        }
        if (size == 0){
            LinkedNode<T> newNode = new LinkedNode<>(data,null,null);
            head = newNode;
            tail = newNode;
            size = 1;
        }else {
            LinkedNode<T> newNode = new LinkedNode<>(data,tail,null);
            tail.setNext(newNode);
            tail = newNode;
            size++;
        }
    }

    /**
     * Removes and returns the first element of the deque.
     *
     * Must be O(1).
     *
     * @return the data formerly located at the front of the deque
     * @throws java.util.NoSuchElementException if the deque is empty
     */
    public T removeFirst() {
        T returnData;
        if (size == 0) {
            throw new NoSuchElementException("The deque is empty. Fill it!");
        } else if (size == 1){
            returnData = head.getData();
            head = null;
            tail = null;
            size = 0;
        }else {
            returnData = head.getData();
            head = head.getNext();
            head.setPrevious(null);
            size--;
        }
        return returnData;
    }

    /**
     * Removes and returns the last element of the deque.
     *
     * Must be O(1).
     *
     * @return the data formerly located at the back of the deque
     * @throws java.util.NoSuchElementException if the deque is empty
     */
    public T removeLast() {
        T returnData;
        if (size == 0) {
            throw new NoSuchElementException("The deque is empty. Fill it!");
        } else if (size == 1) {
            returnData = tail.getData();
            tail = null;
            head = null;
            size = 0;
        }
        else {
            returnData = tail.getData();
            tail = tail.getPrevious();
            tail.setNext(null);
            size--;
        }
        return returnData;
    }

    /**
     * Returns the first data of the deque without removing it.
     *
     * Must be O(1).
     *
     * @return the data located at the front of the deque
     * @throws java.util.NoSuchElementException if the deque is empty
     */
    public T getFirst() {
        if (size == 0) {
            throw new NoSuchElementException("The deque is empty. Fill it!");
        } else {
            return head.getData();
        }
    }

    /**
     * Returns the last data of the deque without removing it.
     *
     * Must be O(1).
     *
     * @return the data located at the back of the deque
     * @throws java.util.NoSuchElementException if the deque is empty
     */
    public T getLast() {
        if (size == 0) {
            throw new NoSuchElementException("The deque is empty. Fill it!");
        } else {
            return tail.getData();
        }
    }

    /**
     * Returns the head node of the deque.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return node at the head of the deque
     */
    public LinkedNode<T> getHead() {
        // DO NOT MODIFY THIS METHOD!
        return head;
    }

    /**
     * Returns the tail node of the deque.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return node at the head of the deque
     */
    public LinkedNode<T> getTail() {
        // DO NOT MODIFY THIS METHOD!
        return tail;
    }

    /**
     * Returns the size of the deque.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the deque
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}
