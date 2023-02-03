import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * Your implementation of a MinHeap.
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
public class MinHeap<T extends Comparable<? super T>> {

    /**
     * The initial capacity of the MinHeap when created with the default
     * constructor.
     *
     * DO NOT MODIFY THIS VARIABLE!
     */
    public static final int INITIAL_CAPACITY = 13;

    // Do not add new instance variables or modify existing ones.
    private T[] backingArray;
    private int size;

    /**
     * Constructs a new MinHeap.
     *
     * The backing array should have an initial capacity of INITIAL_CAPACITY.
     * To initialize the backing array, create a Comparable array and then cast
     * it to a T array.
     */
    public MinHeap() {
        // Creating an array of Comparable type and casting it.
        backingArray = (T[]) new Comparable[INITIAL_CAPACITY];
        // Setting size variable to 1
        size = 0;
    }

    /**
     * Creates a properly ordered heap from a set of initial values.
     *
     * You must use the BuildHeap algorithm that was taught in lecture! Simply
     * adding the data one by one using the add method will not get any credit.
     * As a reminder, this is the algorithm that involves building the heap
     * from the bottom up by repeated use of downHeap operations.
     *
     * Before doing the algorithm, first copy over the data from the
     * ArrayList to the backingArray (leaving index 0 of the backingArray
     * empty). The data in the backingArray should be in the same order as it
     * appears in the passed in ArrayList before you start the BuildHeap
     * algorithm.
     *
     * The backingArray should have capacity 2n + 1 where n is the
     * size of the passed in ArrayList (not INITIAL_CAPACITY). Index 0 should
     * remain empty, indices 1 to n should contain the data in proper order, and
     * the rest of the indices should be empty.
     *
     * @param data a list of data to initialize the heap with
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public MinHeap(ArrayList<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("The data set you are trying to make to a heap is null.");
        } else {
            // Getting the size of the data
            size = data.size();
            // Getting the size of the backing array that needs to be made
            int arraySize = (2 * size) + 1;
            // Making the backing array
            backingArray = (T[]) new Comparable[arraySize];
            // Adding the data into the array
            for (int b = 0; b < size; b++) {
                if (data.get(b) == null) {
                    throw new IllegalArgumentException("The data trying to be added is null. Please enter valid data");
                } else {
                    backingArray[b + 1] = data.get(b);
                }
            }
            // Iterating through the parents of the heap made
            for (int c = (size / 2); c > 0; c--) {
                heapCreator(c);
            }
        }

    }

    /**
     * Adds an item to the heap. If the backing array is full (except for
     * index 0) and you're trying to add a new item, then double its capacity.
     * The order property of the heap must be maintained after adding. You can
     * assume that no duplicate data will be passed in.
     * 
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    // 12,45,11,87,98,34,54,67,99,10,110,111
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data entered is null. PLease enter valid data");
        } else if (size == backingArray.length - 1) {
            resize();
            backingArray[size + 1] = data;
            size++;
            addHelper(size);
        } else {
            backingArray[size + 1] = data;
            size++;
            addHelper(size);
        }
    }

    /**
     * Creating a resize method for the backing array.
     * @return a new backing array.
     */
    private T[] resize() {
        int newSize = 2 * backingArray.length;
        T[] newBackingArray = (T[]) new Comparable[newSize];
        newBackingArray[0] = null;
        for (int a = 1; a <= size; a++) {
            newBackingArray[a] = backingArray[a];
        }
        backingArray = newBackingArray;
        return backingArray;
    }

    /**
     * Making a helper method for the add operation.
     * @param num index value.
     */
    private void addHelper(int num) {
        while (num > 1) {
            int parentIndex = num / 2;
            if (backingArray[num].compareTo(backingArray[parentIndex]) < 0) {
                T parentData = backingArray[parentIndex];
                backingArray[parentIndex] = backingArray[num];
                backingArray[num] = parentData;
            }
            num = parentIndex;
        }
    }

    /**
     * Removes and returns the min item of the heap. As usual for array-backed
     * structures, be sure to null out spots as you remove. Do not decrease the
     * capacity of the backing array.
     * The order property of the heap must be maintained after removing.
     *
     * @return the data that was removed
     * @throws java.util.NoSuchElementException if the heap is empty
     */
    public T remove() {
        if (size == 0) {
            throw new NoSuchElementException("The heap you are trying to remove from is empty.");
        } else {
            T minData = backingArray[1];
            backingArray[1] = backingArray[size];
            backingArray[size] = null;
            size--;
            heapCreator(1);
            return minData;
        }
    }

    /**
     * Making a helper method to create the heap.
     * @param index index that you want to start at.
     */
    private void heapCreator(int index) {
        int rightChild = (2 * index) + 1;
        int leftChild = 2 * index;
        int currentIndex = index;
        if (leftChild <= size && (backingArray[leftChild].compareTo(backingArray[currentIndex]) < 0)) {
            currentIndex = leftChild;
        }
        if (rightChild <= size && (backingArray[rightChild].compareTo(backingArray[currentIndex]) < 0)) {
            currentIndex = rightChild;
        }
        if (currentIndex <= size && backingArray[currentIndex].compareTo(backingArray[index]) < 0) {
            T currentData = backingArray[index];
            backingArray[index] = backingArray[currentIndex];
            backingArray[currentIndex] = currentData;
            heapCreator(currentIndex);
            heapCreator(index);
        }
    }

    /**
     * Returns the minimum element in the heap.
     *
     * @return the minimum element
     * @throws java.util.NoSuchElementException if the heap is empty
     */
    public T getMin() {
        if (isEmpty()) {
            throw new NoSuchElementException("The heap you are trying to access is empty!");
        }
        return backingArray[1];
    }

    /**
     * Returns whether or not the heap is empty.
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return (backingArray[1] == null);
    }

    /**
     * Clears the heap.
     *
     * Resets the backing array to a new array of the initial capacity and
     * resets the size.
     */
    public void clear() {
        backingArray = (T[]) new Comparable[INITIAL_CAPACITY];
        size = 0;
    }

    /**
     * Returns the backing array of the heap.
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
     * Returns the size of the heap.
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
