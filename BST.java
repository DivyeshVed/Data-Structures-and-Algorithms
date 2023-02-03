import java.util.Collection;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.NoSuchElementException;

/**
 * Your implementation of a BST.
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
public class BST<T extends Comparable<? super T>> {

    /*
     * Do not add new instance variables or modify existing ones.
     */
    private BSTNode<T> root;
    private int size;

    /**
     * Constructs a new BST.
     *
     * This constructor should initialize an empty BST.
     *
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public BST() {
        root = null;
        size = 0;
    }

    /**
     * Constructs a new BST.
     *
     * This constructor should initialize the BST with the data in the
     * Collection. The data should be added in the same order it is in the
     * Collection.
     *
     * Hint: Not all Collections are indexable like Lists, so a regular for loop
     * will not work here. However, all Collections are Iterable, so what type
     * of loop would work?
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public BST(Collection<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("The entered is invalid, please enter valid data!");
        }
        for (T dataItem : data) {
            add(dataItem);
        }
    }

    /**
     * Adds the data to the tree.
     *
     * This must be done recursively.
     *
     * The data becomes a leaf in the tree.
     *
     * Traverse the tree to find the appropriate location. If the data is
     * already in the tree, then nothing should be done (the duplicate
     * shouldn't get added, and size should not be incremented).
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data entered is invalid. Please enter valid data!");
        } else {
            root = helperAdd(root, data);
        }
    }

    /**
     * Making a helper method for the add function.
     * @param currentNode our current node
     * @param data the data we want to add
     * @return desired node
     */
    private BSTNode<T> helperAdd(BSTNode<T> currentNode, T data) {
        if (currentNode == null) {
            BSTNode<T> node = new BSTNode<>(data);
            size++;
            return node;
        } else {
            if (data.compareTo(currentNode.getData()) > 0) {
                currentNode.setRight(helperAdd(currentNode.getRight(), data));
            } else if (data.compareTo(currentNode.getData()) < 0) {
                currentNode.setLeft(helperAdd(currentNode.getLeft(), data));
            }
            return currentNode;
        }
    }

    /**
     * Removes and returns the data from the tree matching the given parameter.
     *
     * This must be done recursively.
     *
     * There are 3 cases to consider:
     * 1: The node containing the data is a leaf (no children). In this case,
     * simply remove it.
     * 2: The node containing the data has one child. In this case, simply
     * replace it with its child.
     * 3: The node containing the data has 2 children. Use the successor to
     * replace the data. You MUST use recursion to find and remove the
     * successor (you will likely need an additional helper method to
     * handle this case efficiently).
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to remove
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data cannot be removed from the tree!");
        }
        BSTNode<T> dummyNode = new BSTNode<>(null);
        root = helperRemove(data, root, dummyNode);
        size--;
        return dummyNode.getData();
    }

    /**
     * Helper method for the remove function.
     * @param data data we want to remove
     * @param currentNode the node we want to remove from
     * @param dummyNode a dummy node
     * @return desired node
     */
    private BSTNode<T> helperRemove(T data, BSTNode<T> currentNode, BSTNode<T> dummyNode) {
        if (data.compareTo(currentNode.getData()) < 0) {
            currentNode.setLeft(helperRemove(data, currentNode.getLeft(), dummyNode));
        } else if (data.compareTo(currentNode.getData()) > 0) {
            currentNode.setRight(helperRemove(data, currentNode.getRight(), dummyNode));
        } else {
            if (dummyNode.getData() == null) {
                dummyNode.setData(currentNode.getData());
            }
            if (currentNode.getLeft() == null) {
                return currentNode.getRight();
            } else if (currentNode.getRight() == null) {
                return currentNode.getLeft();
            }
            currentNode.setData(successorFunction(currentNode.getRight()));
            currentNode.setRight(helperRemove(currentNode.getData(), currentNode.getRight(), dummyNode));
        }
        return currentNode;
    }

    /**
     * Successor Method as a helper method.
     * @param node the desired node
     * @return T data of any type
     */
    private T successorFunction(BSTNode<T> node) {
        T data = node.getData();
        while (node.getLeft() != null) {
            data = node.getLeft().getData();
            node = node.getLeft();
        }
        return data;
    }
    /**
     * Returns the data from the tree matching the given parameter.
     *
     * This must be done recursively.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to search for
     * @return the data in the tree equal to the parameter
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("We cannot pick out null data form the tree");
        } else {
            BSTNode<T> node1 = recursionFunction(root, data);
            return node1.getData();
        }
    }

    /**
     * Helper function for recursion.
     * @param node1 the deisred node
     * @param data the desired data
     * @return BSTNode the node we want
     */
    private BSTNode<T> recursionFunction(BSTNode<T> node1, T data) {
        if (node1 == null) {
            throw new NoSuchElementException("The data you are looking for is not found");
        } else if (data.compareTo(node1.getData()) > 0) {
            return recursionFunction(node1.getRight(), data);
        } else if (data.compareTo(node1.getData()) < 0) {
            return recursionFunction(node1.getLeft(), data);
        } else {
            return node1;
        }
    }

    /**
     * Returns whether or not data matching the given parameter is contained
     * within the tree.
     *
     * This must be done recursively.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to search for
     * @return true if the parameter is contained within the tree, false
     * otherwise
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("We cannot get null data from the tree");
        } else {
            return recursionFunction1(data, root);
        }
    }

    /**
     * Helper function for recursion.
     * @param data data we want to check for.
     * @param node2 node that we are checking at.
     * @return boolean value.
     */
    private boolean recursionFunction1(T data, BSTNode<T> node2) {
        if (node2 != null) {
            if (node2.getData().equals(data)) {
                return true;
            } else if (data.compareTo(node2.getData()) > 0) {
                return recursionFunction1(data, node2.getRight());
            } else if (data.compareTo(node2.getData()) < 0) {
                return recursionFunction1(data, node2.getLeft());
            }
        }
        return false;
    }

    /**
     * Generate a pre-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the pre-order traversal of the tree
     */
    public List<T> preorder() {
        List<T> list = new ArrayList<>();
        helperPreOrder(root, list);
        return list;
    }

    /**
     * Helper function for travel.
     * @param node node we want.
     * @param list the list we want to add to.
     */
    private void helperPreOrder(BSTNode<T> node, List<T> list) {
        if (node == null) {
            return;
        } else {
            list.add(node.getData());
            helperPreOrder(node.getLeft(), list);
            helperPreOrder(node.getRight(), list);
        }
    }

    /**
     * Generate an in-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the in-order traversal of the tree
     */
    public List<T> inorder() {
        List<T> list = new ArrayList<>();
        return helperInOrder(root, list);
    }

    /**
     * Helper function.
     * @param node the node we want.
     * @param list the list we add to.
     * @return returning a list.
     */
    private List<T> helperInOrder(BSTNode<T> node, List<T> list) {
        if (node != null) {
            helperInOrder(node.getLeft(), list);
            list.add(node.getData());
            helperInOrder(node.getRight(), list);
        }
        return list;
    }

    /**
     * Generate a post-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the post-order traversal of the tree
     */
    public List<T> postorder() {
        List<T> list = new ArrayList<>();
        return helperPostOrder(root, list);
    }

    /**
     * Helper function for post order.
     * @param node the node wew are looking at.
     * @param list the list we are adding to.
     * @return returning a list.
     */
    private List<T> helperPostOrder(BSTNode<T> node, List<T> list) {
        if (node != null) {
            helperPostOrder(node.getLeft(), list);
            helperPostOrder(node.getRight(), list);
            list.add(node.getData());
        }
        return list;
    }

    /**
     * Generate a level-order traversal of the tree.
     *
     * This does not need to be done recursively.
     *
     * Hint: You will need to use a queue of nodes. Think about what initial
     * node you should add to the queue and what loop / loop conditions you
     * should use.
     *
     * Must be O(n).
     *
     * @return the level-order traversal of the tree
     */
    public List<T> levelorder() {
        List<T> list = new ArrayList<>();
        Queue<BSTNode<T>> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            BSTNode<T> currentNode = queue.peek();
            if (currentNode == null) {
                return list;
            }
            if (currentNode.getLeft() != null && currentNode.getRight() != null) {
                queue.add(currentNode.getLeft());
                queue.add(currentNode.getRight());
                list.add(currentNode.getData());
            } else if (currentNode.getRight() != null) {
                queue.add(currentNode.getRight());
                list.add(currentNode.getData());
            } else if (currentNode.getLeft() != null) {
                queue.add(currentNode.getLeft());
                list.add(currentNode.getData());
            } else {
                list.add(currentNode.getData());
            }
            queue.remove();
        }
        return list;
    }

    /**
     * Returns the height of the root of the tree.
     *
     * This must be done recursively.
     *
     * A node's height is defined as max(left.height, right.height) + 1. A
     * leaf node has a height of 0 and a null child has a height of -1.
     *
     * Must be O(n).
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        if (root == null) {
            return -1;
        } else {
            return heightHelper(root) - 1;
        }
    }

    /**
     * Helper funtion for height method.
     * @param currentNode the node we are working with.
     * @return int value.
     */
    private int heightHelper(BSTNode<T> currentNode) {
        if (currentNode == null) {
            return 0;
        } else {
            int left = heightHelper(currentNode.getLeft());
            int right = heightHelper(currentNode.getRight());
            if (left > right) {
                return left + 1;
            } else {
                return right + 1;
            }
        }
    }

    /**
     * Clears the tree.
     *
     * Clears all data and resets the size.
     *
     * Must be O(1).
     */
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * Generates a list of the max data per level from the top to the bottom
     * of the tree. (Another way to think about this is to get the right most
     * data per level from top to bottom.)
     * 
     * This must be done recursively.
     *
     * This list should contain the last node of each level.
     *
     * If the tree is empty, an empty list should be returned.
     *
     * Ex:
     * Given the following BST composed of Integers
     *      2
     *    /   \
     *   1     4
     *  /     / \
     * 0     3   5
     * getMaxDataPerLevel() should return the list [2, 4, 5] - 2 is the max
     * data of level 0, 4 is the max data of level 1, and 5 is the max data of
     * level 2
     *
     * Ex:
     * Given the following BST composed of Integers
     *               50
     *           /        \
     *         25         75
     *       /    \
     *      12    37
     *     /  \    \
     *   11   15   40
     *  /
     * 10
     * getMaxDataPerLevel() should return the list [50, 75, 37, 40, 10] - 50 is
     * the max data of level 0, 75 is the max data of level 1, 37 is the
     * max data of level 2, etc.
     *
     * Must be O(n)
     *
     * @return the list containing the max data of each level
     */
    public List<T> getMaxDataPerLevel() {
        List<T> returnList = new ArrayList<>();
        getMaxDataPerLevelHelper(root, returnList, 0);
        return returnList;
    }

    /**
     * Helper function for recursion.
     * @param currentNode the node we are wokring with.
     * @param list the list we want to add to.
     * @param level the level of the list we are looking at.
     */
    private void getMaxDataPerLevelHelper(BSTNode<T> currentNode, List<T> list, int level) {
        if (currentNode != null) {
            if (level == list.size()) {
                list.add(currentNode.getData());
            }
            getMaxDataPerLevelHelper(currentNode.getRight(), list, level + 1);
            getMaxDataPerLevelHelper(currentNode.getLeft(), list, level + 1);
        }
    }


    /**
     * Returns the root of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the root of the tree
     */
    public BSTNode<T> getRoot() {
        // DO NOT MODIFY THIS METHOD!
        return root;
    }

    /**
     * Returns the size of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the tree
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}
