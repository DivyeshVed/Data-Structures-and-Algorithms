import java.util.NoSuchElementException;

/**
 * Your implementation of a non-circular DoublyLinkedList with a tail pointer.
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
public class DoublyLinkedList<T> {

    // Do not add new instance variables or modify existing ones.
    private DoublyLinkedListNode<T> head;
    private DoublyLinkedListNode<T> tail;
    private int size;

    // Do not add a constructor.

    /**
     * Adds the element to the specified index. Don't forget to consider whether
     * traversing the list from the head or tail is more efficient!
     *
     * Must be O(1) for indices 0 and size and O(n) for all other cases.
     *
     * @param index the index at which to add the new element
     * @param data  the data to add at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index > size
     * @throws java.lang.IllegalArgumentException  if data is null
     */
    public void addAtIndex(int index, T data) {
        // Throwing the exceptions incase the index was negative, or greater than the size of the list.
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("The index is either less than 0, "
                    + "or greater than the size of the list.");
        }
        // Throwing the exception if the data entered in null.
        if (data == null) {
            throw new IllegalArgumentException("The data entered is null, and thus invalid");
        }
        DoublyLinkedListNode<T> current = head;
        // Accounting for the case when the size of the list is 0
        if (size == 0) {
            DoublyLinkedListNode<T> temp = new DoublyLinkedListNode<T>(data, null, null);
            head = temp;
            tail = temp;
        } else if (index == 0) {
            DoublyLinkedListNode<T> temp = new DoublyLinkedListNode<T>(data, null, head);
            head.setPrevious(temp);
            head = temp;
        } else if (index == size) {
            for (int a = 0; a < size - 1; a++) {
                current = current.getNext();
            }
            DoublyLinkedListNode<T> temp = new DoublyLinkedListNode<T>(data, current, null);
            current.setNext(temp);
            tail = temp;
        } else {

            for (int b = 0; b < index - 1; b++) {
                current = current.getNext();
            }
            DoublyLinkedListNode<T> temp = new DoublyLinkedListNode<T>(data, current, current.getNext());
            current.getNext().setPrevious(temp);
            current.setNext(temp);
        }
        size++;
    }


    /**
     * Adds the element to the front of the list.
     *
     * Must be O(1).
     *
     * @param data the data to add to the front of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToFront(T data) {
        addAtIndex(0, data);
    }

    /**
     * Adds the element to the back of the list.
     *
     * Must be O(1).
     *
     * @param data the data to add to the back of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToBack(T data) {
        addAtIndex(size, data);
    }

    /**
     * Removes and returns the element at the specified index. Don't forget to
     * consider whether traversing the list from the head or tail is more
     * efficient!
     *
     * Must be O(1) for indices 0 and size - 1 and O(n) for all other cases.
     *
     * @param index the index of the element to remove
     * @return the data formerly located at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T removeAtIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("The index is either less than 0,"
                    + "or greater than the size of the list.");
        }
        T returnData;
        // Accounting for the situation where your list on has one node.
        if (size == 1) {
            // Collecting the data that was at the node
            returnData = head.getData();
            // Setting the head to null
            head = null;
            tail = null;
            // Decrementing the size of the list by 1
            size--;
        } else if (index == 0) {
            // Collecting the data that was at the node
            returnData = head.getData();
            head = head.getNext();
            head.setPrevious(null);
            // Decrementing the size of the list by 1
            size--;
        } else if (index == size - 1) {
            // Making a node to keep track of the current node
            DoublyLinkedListNode<T> current = head;
            // Looping through the DoublyLInkedList and keeping track of the number of nodes that we have crossed.
            for (int z = 0; z < size - 1; z++) {
                current = current.getNext();
            }
            returnData = current.getData();
            // Setting the tail the previous node
            tail = current.getPrevious();
            // Setting the next pointer of the tail to null
            tail.setNext(null);
            // Decrementing the size variable
            size--;
        } else {
            // Making a node to keep track of the current node
            DoublyLinkedListNode<T> current = head;
            // Looping through the DoublyLInkedList and keeping track of the number of nodes that we have crossed.
            for (int c = 0; c < index - 1; c++) {
                current = current.getNext();
            }
            // Getting the data form the next node
            returnData = current.getNext().getData();
            // Setting the next to next node's previous node to the current node
            current.getNext().getNext().setPrevious(current);
            // Setting the current node's next node to the next to next node of the current node
            current.setNext(current.getNext().getNext());
            // Decrementing the size variable
            size--;
        }
        return returnData;
    }


    /**
     * Removes and returns the first element of the list.
     *
     * Must be O(1).
     *
     * @return the data formerly located at the front of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromFront() {
        if (size == 0) {
            throw new NoSuchElementException("The list is empty. There is no such element in the list");
        }
        return removeAtIndex(0);
    }

    /**
     * Removes and returns the last element of the list.
     *
     * Must be O(1).
     *
     * @return the data formerly located at the back of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromBack() {
        if (size == 0) {
            throw new NoSuchElementException("The list is empty. There is no such element in the list.");
        }
        return removeAtIndex(size - 1);
    }

    /**
     * Returns the element at the specified index. Don't forget to consider
     * whether traversing the list from the head or tail is more efficient!
     *
     * Must be O(1) for indices 0 and size - 1 and O(n) for all other cases.
     *
     * @param index the index of the element to get
     * @return the data stored at the index in the list
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T get(int index) {
        // Throwing the exceptions
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("The index entered is less than 0,"
                    + "or greater than or equal to the size of the list.");
        }
        // Making a node to keep track of the current node
        DoublyLinkedListNode<T> current = head;
        // Looping through the list
        for (int d = 0; d < index; d++) {
            current = current.getNext();
        }
        return current.getData();
    }

    /**
     * Returns whether or not the list is empty.
     *
     * Must be O(1).
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return (size == 0);
    }

    /**
     * Clears the list.
     *
     * Clears all data and resets the size.
     *
     * Must be O(1).
     */
    public void clear() {
        head = null;
        tail = null;
        // Resetting the size of the list to zero.
        size = 0;
    }

    /**
     * Removes and returns the last copy of the given data from the list.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the list.
     *
     * Must be O(1) if data is in the tail and O(n) for all other cases.
     *
     * @param data the data to be removed from the list
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if data is not found
     */
    public T removeLastOccurrence(T data) {
        T removedData = null;
        if (data == null) {
            throw new IllegalArgumentException("The data entered is invalid. Please enter valid data.");
        } else if (size == 0) {
            throw new NoSuchElementException("This element doesn't exist in this list, as the list is empty.");
        }
        if (tail.getData().toString().equals(data)) {
            removedData = removeFromBack();
        } else {
            DoublyLinkedListNode<T> current = head;
            int lastOccurrence = -1;
            for (int f = 0; f < size - 1; f++) {
                if (current.getData().toString().equals(data)) {
                    lastOccurrence = f;
                }
                current = current.getNext();
            }
            if (lastOccurrence == -1){
                throw new NoSuchElementException("The data that you are looking for is not in this list.");
            } else {
                DoublyLinkedListNode<T> temp = head;
                for (int h = 0; h < size - 1; h++) {
                    if (h == lastOccurrence) {
                        removedData = removeAtIndex(h);
                    } else {
                        temp = temp.getNext();
                    }
                }
            }
        }
        return removedData;
    }


    /**
     * Returns an array representation of the linked list. If the list is
     * size 0, return an empty array.
     *
     * Must be O(n) for all cases.
     *
     * @return an array of length size holding all of the objects in the
     * list in the same order
     */
    public Object[] toArray() {
        Object[] arr;
        if (size == 0) {
            arr = new Object[0];
        } else {
            arr = new Object[size];
            DoublyLinkedListNode<T> current = head;
            for (int g = 0; g < size; g++) {
                arr[g] = current.getData();
                current = current.getNext();
            }
        }
        return arr;
    }

    /**
     * Returns the head node of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the node at the head of the list
     */
    public DoublyLinkedListNode<T> getHead() {
        // DO NOT MODIFY!
        return head;
    }

    /**
     * Returns the tail node of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the node at the tail of the list
     */
    public DoublyLinkedListNode<T> getTail() {
        // DO NOT MODIFY!
        return tail;
    }

    /**
     * Returns the size of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the list
     */
    public int size() {
        // DO NOT MODIFY!
        return size;
    }
}
