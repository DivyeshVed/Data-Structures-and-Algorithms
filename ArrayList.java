/**
 * Your implementation of an ArrayList.
 *
 * @author DIVYESH VED
 * @version 1.0
 * @userid DVED6
 * @GTID 903600373
 *
 * Collaborators: DIVYESH VED
 *
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */
public class ArrayList<T> {

    /**
     * The initial capacity of the ArrayList.
     *
     * DO NOT MODIFY THIS VARIABLE!
     */
    public static final int INITIAL_CAPACITY = 9;

    // Do not add new instance variables or modify existing ones.
    private T[] backingArray;
    private int size;

    /**
     * Constructs a new ArrayList.
     *
     * Java does not allow for regular generic array creation, so you will have
     * to cast an Object[] to a T[] to get the generic typing.
     */
    public ArrayList() {
        // Making the constructor. Making the backing array to hold any Object and casting it.
        backingArray = (T[]) new Object[INITIAL_CAPACITY];
        size = 0;
    }

    /**
     * Adds the element to the specified index.
     *
     * Remember that this add may require elements to be shifted.
     *
     * Must be amortized O(1) for index size and O(n) for all other cases.
     *
     * @param index the index at which to add the new element
     * @param data  the data to add at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index > size
     * @throws java.lang.IllegalArgumentException  if data is null
     */
    public void addAtIndex(int index, T data) {
        if (index < 0 || index > backingArray.length) {
            // Throwing the exception if the index is out of bounds.
            throw new IndexOutOfBoundsException("The index is either less than 0, or is greater than or equal to the size of the backing array.");
        } else if (data == null) {
            // Throwing the exception if the data entered is null.
            throw new IllegalArgumentException("Cannot insert null data into the data structure.");
        } else {
            // Checking if the size is greater than the backing array length. If it is, then you regrow the array, by creating a new backing array and copying the elements.
            // We add 1 to the size, due to indexing beginning at 0, and the size is 1 value greater than the number of elements in the structure.
            if (size + 1 > backingArray.length) {
                // Since we don't want to tamper with the original backing array, we store it in a temporary array.
                T[] temporaryArray = backingArray;
                // Creating the new backing array that is twice the size of the temporary array.
                backingArray = (T[]) new Object[temporaryArray.length * 2];
                // Copying all the elements from the temporary array to the new backing array.
                for (int a = 0; a < temporaryArray.length; a++) {
                    backingArray[a] = temporaryArray[a];
                }
            }
            // Looking at the three possibilities of the index. Starting with when the size  = 0.
            if (index == 0) {
                // Looping through the array and shifting the elements one to the right
                for (int b = size; b > 0; b--) {
                    backingArray[b] = backingArray[b - 1];
                }
                // Adding the data to the 0th index in the array
                backingArray[0] = data;
                // Incrementing the size variable.
                size++;
            } else if (index == size) {
                // Accounting for the situation when the index is equal to the size of the array. Simply add the data and increment size.
                backingArray[index] = data;
                // Incrementing the size variable.
                size++;
            } else {
                // Accounting for the rest of the situations, where the index is not the size of the array, and the not zero. Looping through the array and shifting the elements one to the right
                for (int c = size; c > index; c--) {
                    backingArray[c] = backingArray[c - 1];
                }
                // Adding the data to the structure.
                backingArray[index] = data;
                // Incrementing the size variable.
                size++;
            }
        }
    }

    /**
     * Adds the element to the front of the list.
     *
     * Remember that this add may require elements to be shifted.
     *
     * Must be O(n).
     *
     * @param data the data to add to the front of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToFront(T data) {
        // Simply using the addAtIndex but at the index = 0.
        addAtIndex(0,data);
    }

    /**
     * Adds the element to the back of the list.
     *
     * Must be amortized O(1).
     *
     * @param data the data to add to the back of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToBack(T data) {
        // Simply using the addAtIndex but at the index = size.
        addAtIndex(size,data);
    }

    /**
     * Removes and returns the element at the specified index.
     *
     * Remember that this remove may require elements to be shifted.
     *
     * Must be O(1) for index size - 1 and O(n) for all other cases.
     *
     * @param index the index of the element to remove
     * @return the data formerly located at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T removeAtIndex(int index) {
        // Throwing the exception if the index is out of bounds.
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("The index entered is either 0, or is greater than or equal to the size of the backing array.");
        } else if (index == size - 1) {
            // Accounting for the situation where you have to remove the last index.
            // Getting the data we want to remove and storing it.
            T removedData = backingArray[size - 1];
            // Making the last value null
            backingArray[index] = null;
            // Reducing the size
            size--;
            // Returning the removed value
            return removedData;
        }
        // Accounting the instance when the index is any other number.
        else {
            // Getting the removed data.
            T removedData = backingArray[index];
            // Shifting the data to the left.
            for (int d = index; d < size - 1; d++) {
                backingArray[d] = backingArray[d + 1];
            }
            // Decrementing the size
            size--;
            // Inputting a null value at the index.
            backingArray[size] = null;
            // Returning the removed value
            return removedData;
        }
    }

    /**
     * Removes and returns the first element of the list.
     *
     * Remember that this remove may require elements to be shifted.
     *
     * Must be O(n).
     *
     * @return the data formerly located at the front of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromFront() {
        // Simply using the removeAtIndex at index = 0
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
        // Simply using the removeAtIndex at index = size - 1
        return removeAtIndex(size-1);
    }

    /**
     * Returns the element at the specified index.
     *
     * Must be O(1).
     *
     * @param index the index of the element to get
     * @return the data stored at the index in the list
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T get(int index) {
        // Throwing the exception if the index is invalid
        if (index < 0 || index >= size){
            throw new IndexOutOfBoundsException("The index entered is either 0, or is greater than or equal to the size of the backing array.");
        } else {
            // Returning the data at the specific index
            return backingArray[index];
        }
    }

    /**
     * Returns whether or not the list is empty.
     *
     * Must be O(1).
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        // Checking the size of the array. If it is zero, then the array is empty.
        return (size == 0);
    }

    /**
     * Clears the list.
     *
     * Resets the backing array to a new array of the initial capacity and
     * resets the size.
     *
     * Must be O(1).
     */
    public void clear() {
        // Simply create a new backing array with the initial capacity size, and reset the size variable.
        backingArray = (T[]) new Object[INITIAL_CAPACITY];
        size = 0;
    }

    /**
     * Returns the backing array of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the backing array of the list
     */
    public T[] getBackingArray() {
        // DO NOT MODIFY THIS METHOD!
        return backingArray;
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
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}
