import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.PriorityQueue;


/**
 * Your implementation of various sorting algorithms.
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
public class Sorting {

    /**
     * Implement insertion sort.
     *
     * It should be:
     * in-place
     * stable
     * adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n)
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void insertionSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("Either the array entered or the comparator used in null");
        } else {
            int arrayLength = arr.length;
            for (int a = 0; a < arrayLength; a++) {
                int temp = a;
                while ((temp > 0) && (comparator.compare(arr[temp - 1], arr[temp]) > 0)) {
                    T tempData = arr[temp - 1];
                    arr[temp - 1] = arr[temp];
                    arr[temp] = tempData;
                    temp = temp - 1;
                }
            }
        }
    }

    /**
     * Implement cocktail sort.
     *
     * It should be:
     * in-place
     * stable
     * adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n)
     *
     * NOTE: See pdf for last swapped optimization for cocktail sort. You
     * MUST implement cocktail sort with this optimization
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void cocktailSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("Either the data or the comparator used is null");
        } else {
            int start = 0;
            int end = arr.length - 1;
            int swapped = 0;
            while (end > start) {
                swapped = start;
                for (int i = start; i < end; i++) {
                    if (comparator.compare(arr[i], arr[i + 1]) > 0) {
                        T tempData = arr[i + 1];
                        arr[i + 1] = arr[i];
                        arr[i] = tempData;
                        swapped = i;
                    }
                }
                end = swapped;
                for (int j = end; j > start; j--) {
                    if (comparator.compare(arr[j], arr[j - 1]) < 0) {
                        T tempData1 = arr[j - 1];
                        arr[j - 1] = arr[j];
                        arr[j] = tempData1;
                        swapped = j;
                    }
                }
                start = swapped;
            }
        }
    }

    /**
     * Implement merge sort.
     *
     * It should be:
     * out-of-place
     * stable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n log n)
     *
     * And a best case running time of:
     * O(n log n)
     *
     * You can create more arrays to run merge sort, but at the end, everything
     * should be merged back into the original T[] which was passed in.
     *
     * When splitting the array, if there is an odd number of elements, put the
     * extra data on the right side.
     *
     * Hint: If two data are equal when merging, think about which subarray
     * you should pull from first
     *
     * @param <T>        data type to sort
     * @param arr        the array to be sorted
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void mergeSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("Either the array or the comparator is null");
        } else if (arr.length == 0 || arr.length == 1) {
            return;
        } else {
            int arrayLength = arr.length;
            int midIndex = arrayLength / 2;
            T[] leftArray = (T[]) new Object[midIndex];
            for (int a = 0; a < midIndex; a++) {
                leftArray[a] = arr[a];
            }
            T[] rightArray = (T[]) new Object[arrayLength - midIndex];
            for (int b = midIndex; b < arrayLength; b++) {
                rightArray[b - midIndex] = arr[b];
            }
            mergeSort(leftArray, comparator);
            mergeSort(rightArray, comparator);

            int leftIndex = 0;
            int rightIndex = 0;
            while ((leftIndex < leftArray.length) && (rightIndex < rightArray.length)) {
                if (comparator.compare(leftArray[leftIndex], rightArray[rightIndex]) <= 0) {
                    arr[leftIndex + rightIndex] = leftArray[leftIndex++];
                } else {
                    arr[leftIndex + rightIndex] = rightArray[rightIndex++];
                }
            }
            while (leftIndex < leftArray.length) {
                arr[leftIndex + rightIndex] = leftArray[leftIndex++];
            }
            while (rightIndex < rightArray.length) {
                arr[rightIndex + leftIndex] = rightArray[rightIndex++];
            }
        }
    }

    /**
     * Implement quick sort.
     *
     * Use the provided random object to select your pivots. For example if you
     * need a pivot between a (inclusive) and b (exclusive) where b > a, use
     * the following code:
     *
     * int pivotIndex = rand.nextInt(b - a) + a;
     *
     * If your recursion uses an inclusive b instead of an exclusive one,
     * the formula changes by adding 1 to the nextInt() call:
     *
     * int pivotIndex = rand.nextInt(b - a + 1) + a;
     *
     * It should be:
     * in-place
     * unstable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n log n)
     *
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm and you may not receive
     * credit if you do not use the one we have taught you!
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @param rand       the Random object used to select pivots
     * @throws java.lang.IllegalArgumentException if the array or comparator or
     *                                            rand is null
     */
    public static <T> void quickSort(T[] arr, Comparator<T> comparator, Random rand) {
        if (arr == null || comparator == null || rand == null) {
            throw new IllegalArgumentException("Either the array, the comparator or the random vaule enerted is null");
        } else {
            int start = 0;
            int end = arr.length - 1;
            helperQuickSort(arr, comparator, rand, start, end);
        }
    }

    /**
     * Helper method for quick sort.
     * @param arr this is the initial array inputted.
     * @param comparator this is the comparator used to compare the values.
     * @param rand this is the random number generator used to pick the pivot index.
     * @param start this is the start index.
     * @param end this is the end index.
     * @param <T> this is the array values once sorted.
     */
    public static <T> void helperQuickSort(T[] arr, Comparator<T> comparator, Random rand, int start, int end) {
        if (end - start < 1) {
            return;
        }
        int pivotIndex = rand.nextInt(end - start + 1) + start;
        T pivot = arr[pivotIndex];
        arr[pivotIndex] = arr[start];
        arr[start] = pivot;
        int i = start + 1;
        int j = end;
        while (j - i >= 0) {
            while (j - i >= 0 && comparator.compare(arr[i], pivot) <= 0) {
                i++;
            }
            while (j - i >= 0 && comparator.compare(arr[j], pivot) >= 0) {
                j--;
            }
            if (j - i >= 0) {
                T temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
                i++;
                j--;
            }
        }
        T temp1 = arr[start];
        arr[start] = arr[j];
        arr[j] = temp1;
        helperQuickSort(arr, comparator, rand, start, j - 1);
        helperQuickSort(arr, comparator, rand, j + 1, end);
    }

    /**
     * Implement LSD (least significant digit) radix sort.
     *
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm and you may not get full
     * credit if you do not implement the one we have taught you!
     *
     * Remember you CANNOT convert the ints to strings at any point in your
     * code! Doing so may result in a 0 for the implementation.
     *
     * It should be:
     * out-of-place
     * stable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(kn)
     *
     * And a best case running time of:
     * O(kn)
     *
     * You are allowed to make an initial O(n) passthrough of the array to
     * determine the number of iterations you need. The number of iterations
     * can be determined using the number with the largest magnitude.
     *
     * At no point should you find yourself needing a way to exponentiate a
     * number; any such method would be non-O(1). Think about how how you can
     * get each power of BASE naturally and efficiently as the algorithm
     * progresses through each digit.
     *
     * Refer to the PDF for more information on LSD Radix Sort.
     *
     * You may use ArrayList or LinkedList if you wish, but it may only be
     * used inside radix sort and any radix sort helpers. Do NOT use these
     * classes with other sorts. However, be sure the List implementation you
     * choose allows for stability while being as efficient as possible.
     *
     * Do NOT use anything from the Math class except Math.abs().
     *
     * @param arr the array to be sorted
     * @throws java.lang.IllegalArgumentException if the array is null
     */
    public static void lsdRadixSort(int[] arr) {
        if (arr == null) {
            throw new IllegalArgumentException("The array is null");
        }
        if (arr.length == 0) {
            return;
        }
        int maximumNumber = arr[0];
        for (int i = 0; i < arr.length; i++) {
            int num = 0;
            if (arr[i] == Integer.MIN_VALUE) {
                num = Integer.MAX_VALUE;
            } else {
                num = Math.abs(arr[i]);
            }
            if (num > maximumNumber) {
                maximumNumber = num;
            }
        }
        int k = 1;
        while (maximumNumber >= 10) {
            k++;
            maximumNumber = maximumNumber / 10;
        }

        int divNumber = 1;
        ArrayList<Integer>[] buckets = new ArrayList[19];
        for (int i = 0; i < buckets.length; i++) {
            buckets[i] = new ArrayList<>();
        }

        for (int i = 0; i < k; i++) {
            for (int j = 0; j < arr.length; j++) {
                int bucket = (arr[j] / divNumber) % 10 + 9;
                buckets[bucket].add(arr[j]);
            }
            divNumber *= 10;

            int index = 0;
            for (int bucket = 0; bucket < buckets.length; bucket++) {
                while (buckets[bucket].size() != 0) {
                    arr[index] = buckets[bucket].remove(0);
                    index++;
                }
            }
        }
    }

    /**
     * Implement heap sort.
     *
     * It should be:
     * out-of-place
     * unstable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n log n)
     *
     * And a best case running time of:
     * O(n log n)
     *
     * Use java.util.PriorityQueue as the heap. Note that in this
     * PriorityQueue implementation, elements are removed from smallest
     * element to largest element.
     *
     * Initialize the PriorityQueue using its build heap constructor (look at
     * the different constructors of java.util.PriorityQueue).
     *
     * Return an int array with a capacity equal to the size of the list. The
     * returned array should have the elements in the list in sorted order.
     *
     * @param data the data to sort
     * @return the array with length equal to the size of the input list that
     * holds the elements from the list is sorted order
     * @throws java.lang.IllegalArgumentException if the data is null
     */
    public static int[] heapSort(List<Integer> data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null.");
        }
        PriorityQueue<Integer> queue = new PriorityQueue<>();
        for (int dataItem : data) {
            queue.add(dataItem);
        }
        int[] arr = new int[data.size()];
        for (int i = 0; i < data.size(); i++) {
            arr[i] = queue.remove();
        }
        return arr;
    }
}
