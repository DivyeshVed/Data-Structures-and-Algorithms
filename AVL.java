import java.util.Collection;
import java.util.NoSuchElementException;

/**
 * Your implementation of an AVL.
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
public class AVL<T extends Comparable<? super T>> {

    // Do not add new instance variables or modify existing ones.
    private AVLNode<T> root;
    private int size;

    /**
     * Constructs a new AVL.
     *
     * This constructor should initialize an empty AVL.
     *
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public AVL() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Constructs a new AVL.
     *
     * This constructor should initialize the AVL with the data in the
     * Collection. The data should be added in the same order it is in the
     * Collection.
     *
     * @param data the data to add to the tree
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public AVL(Collection<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("The data entered is null and invalid!");
        }
        for (T dataItem: data) {
            add(dataItem);
        }
    }

    /**
     * Adds the element to the tree.
     *
     * Start by adding it as a leaf like in a regular BST and then rotate the
     * tree as necessary.
     *
     * If the data is already in the tree, then nothing should be done (the
     * duplicate shouldn't get added, and size should not be incremented).
     *
     * Remember to recalculate heights and balance factors while going back
     * up the tree after adding the element, making sure to rebalance if
     * necessary.
     * 
     * Hint: Should you use value equality or reference equality?
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data you are trying to add is null!");
        }
        root = addHelper(root, data);
    }

    /**
     * Helper add method.
     * @param node the current node.
     * @param data the data we want to add.
     * @return root node.
     */
    private AVLNode<T> addHelper(AVLNode<T> node, T data) {
        if (node == null) {
            size++;
            return new AVLNode<>(data);
        } else if (data.compareTo(node.getData()) < 0) {
            node.setLeft(addHelper(node.getLeft(), data));
        } else if (data.compareTo(node.getData()) > 0) {
            node.setRight(addHelper(node.getRight(), data));
        }
        updateNode(node);
        if (Math.abs(node.getBalanceFactor()) > 1) {
            node = balance(node);
        }
        return node;
    }

    /**
     * Update method that updates the height and BF.
     * @param node the node whose height we want to update.
     */
    private void updateNode(AVLNode<T> node) {
        int left = nodeHeight(node.getLeft());
        int right = nodeHeight(node.getRight());
        node.setBalanceFactor(left - right);
        node.setHeight(1 + Math.max(left, right));
    }

    /**
     * Getting the height of the node.
     * @param node node whose height we want.
     * @return returning the height.
     */
    private int nodeHeight(AVLNode<T> node) {
        if (node == null) {
            return -1;
        }
        return node.getHeight();
    }

    /**
     * Method to balance the BF and the height of the node.
     * @param node node we want to balance.
     * @return returning the balanced node.
     */
    private AVLNode<T> balance(AVLNode<T> node) {
        AVLNode<T> returnNode = node;
        if (node.getBalanceFactor() > 0) {
            //left heavy - rotate right
            if (node.getLeft().getBalanceFactor() < 0) {
                //left-right rotation
                node.setLeft(rotateLeft(node.getLeft()));
                returnNode = rotateRight(node);
            } else {
                //right rotation
                returnNode = rotateRight(node);
            }
        } else if (node.getBalanceFactor() < 0) {
            //right heavy - rotate left
            if (node.getRight().getBalanceFactor() > 0) {
                //right-left rotation
                node.setRight(rotateRight(node.getRight()));
                returnNode = rotateLeft(node);
            } else {
                //left rotation
                returnNode = rotateLeft(node);
            }
        }
        return returnNode;
    }

    /**
     * Rotating tree left.
     * @param node node we want to rotate.
     * @return returns the node.
     */
    private AVLNode<T> rotateLeft(AVLNode<T> node) {
        AVLNode<T> rightNode = node.getRight();
        node.setRight(rightNode.getLeft());
        rightNode.setLeft(node);
        updateNode(node);
        updateNode(rightNode);
        return rightNode;
    }

    /**
     * Rotates the tree right.
     * @param node the node that you want to rotate.
     * @return the node returned after the node is rotated.
     */
    private AVLNode<T> rotateRight(AVLNode<T> node) {
        AVLNode<T> leftNode = node.getLeft();
        node.setLeft(leftNode.getRight());
        leftNode.setRight(node);
        updateNode(node);
        updateNode(leftNode);
        return leftNode;
    }

    /**
     * Removes and returns the element from the tree matching the given
     * parameter.
     *
     * There are 3 cases to consider:
     * 1: The node containing the data is a leaf (no children). In this case,
     * simply remove it.
     * 2: The node containing the data has one child. In this case, simply
     * replace it with its child.
     * 3: The node containing the data has 2 children. Use the predecessor to
     * replace the data, NOT successor. As a reminder, rotations can occur
     * after removing the predecessor node.
     *
     * Remember to recalculate heights and balance factors while going back
     * up the tree after removing the element, making sure to rebalance if
     * necessary.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * @param data the data to remove
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not found
     */
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot remove null data.");
        }
        //create dummy node reference to return data that was removed
        AVLNode<T> removeRef = new AVLNode(null);
        root = removeHelper(data, root, removeRef);
        size--;
        return removeRef.getData();
    }

    /**
     * Helper method for remove method.
     * @param data the data we want to remove.
     * @param node the node we want to remove from.
     * @param removeRef dummyNode.
     * @return the root node without the data.
     */
    private AVLNode<T> removeHelper(T data, AVLNode<T> node,
                                    AVLNode<T> removeRef) {
        if (node == null) {
            throw new NoSuchElementException(data + " was not "
                    + "found in the tree.");
        }
        //recurse until you reach the node
        if (data.compareTo(node.getData()) < 0) {
            node.setLeft(removeHelper(data, node.getLeft(), removeRef));
        } else if (data.compareTo(node.getData()) > 0) {
            node.setRight(removeHelper(data, node.getRight(), removeRef));
        } else {
            //if data is the same as node's data, delete this node
            //case 1 and 2: no child or one child
            if (removeRef.getData() == null) {
                //ensures dummy node's data is only changed once
                removeRef.setData(node.getData());
            }
            if (node.getLeft() == null) {
                return node.getRight();
            } else if (node.getRight() == null) {
                return node.getLeft();
            }
            //case 3: two children
            // get successor, swap data, delete old successor node
            node.setData(predecessor(node));
            node.setLeft(removeHelper(node.getData(), node.getLeft(),
                    removeRef));
        }
        updateNode(node);
        if (Math.abs(node.getBalanceFactor()) > 1) {
            node = balance(node);
        }
        return node;
    }

    /**
     * Predessor method that is a helper method.
     * @param node this is teh node that you want to look at.
     * @return this is the data that is returned.
     */
    private T predecessor(AVLNode<T> node) {
        node = node.getLeft();
        T data = node.getData();
        while (node.getRight() != null) {
            data = node.getRight().getData();
            node = node.getRight();
        }
        return data;
    }
    /**
     * Returns the element from the tree matching the given parameter.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * @param data the data to search for in the tree
     * @return the data in the tree equal to the parameter
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot retrieve null data.");
        }
        return getHelper(data, root);
    }

    /**
     * Helper method for the get method.
     * @param data this is the data that is retrieved.
     * @param node this is the node that we want to retrieve the data from.
     * @return this is teh returned data.
     */
    private T getHelper(T data, AVLNode<T> node) {
        if (node == null) {
            throw new NoSuchElementException(data
                    + " was not found in the AVL.");
        }
        if (node.getData().equals(data)) {
            return node.getData();
        } else {
            if (data.compareTo(node.getData()) > 0) {
                return getHelper(data, node.getRight());
            } else {
                return getHelper(data, node.getLeft());
            }
        }
    }

    /**
     * Returns whether or not data matching the given parameter is contained
     * within the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * @param data the data to search for in the tree.
     * @return true if the parameter is contained within the tree, false
     * otherwise
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot check "
                    + "contains on null data.");
        }
        return containsHelper(data, root);
    }

    /**
     * Helper method for the contains method.
     * @param data this is the data that we are looking for.
     * @param node this is the node that we are looking at.
     * @return this is the return value, whether the data is contained or not.
     */
    private boolean containsHelper(T data, AVLNode<T> node) {
        if (node == null) {
            //reached end of the BST without reaching the node
            return false;
        } else if (node.getData().equals(data)) {
            return true;
        } else {
            if (data.compareTo(node.getData()) > 0) {
                //move right
                return containsHelper(data, node.getRight());
            } else {
                //move left
                return containsHelper(data, node.getLeft());
            }
        }
    }

    /**
     * Returns the height of the root of the tree.
     * 
     * Should be O(1). 
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        return nodeHeight(root);
    }

    /**
     * Clears the tree.
     *
     * Clears all data and resets the size.
     */
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * Returns the data in the deepest node. If there is more than one node
     * with the same deepest depth, return the rightmost (i.e. largest) node with 
     * the deepest depth. 
     * 
     * Should be recursive. 
     *
     * Must run in O(log n) for all cases.
     *
     * Example
     * Tree:
     *           2
     *        /    \
     *       0      3
     *        \
     *         1
     * Max Deepest Node:
     * 1 because it is the deepest node
     *
     * Example
     * Tree:
     *           2
     *        /    \
     *       0      4
     *        \    /
     *         1  3
     * Max Deepest Node:
     * 3 because it is the maximum deepest node (1 has the same depth but 3 > 1)
     *
     * @return the data in the maximum deepest node or null if the tree is empty
     */
    public T maxDeepestNode() {
        if (size == 0) {
            return null;
        }
        return deepestNodeHelper(root);
    }

    /**
     * Helper method for the deepestMax Method.
     * @param node the node we are looking for.
     * @return the data from the node.
     */
    private T deepestNodeHelper(AVLNode<T> node) {
        if (node.getLeft() == null && node.getRight() == null) {
            return node.getData();
        } else {
            int left = nodeHeight(node.getLeft());
            int right = nodeHeight(node.getRight());
            if (left > right) {
                return deepestNodeHelper(node.getLeft());
            } else {
                return deepestNodeHelper(node.getRight());
            }
        }
    }

    /**
     * In BSTs, you learned about the concept of the successor: the
     * smallest data that is larger than the current data. However, you only
     * saw it in the context of the 2-child remove case.
     *
     * This method should retrieve (but not remove) the successor of the data
     * passed in. There are 2 cases to consider:
     * 1: The right subtree is non-empty. In this case, the successor is the
     * leftmost node of the right subtree.
     * 2: The right subtree is empty. In this case, the successor is the lowest
     * ancestor of the node whose left subtree contains the data. 
     * 
     * The second case means the successor node will be one of the node(s) we 
     * traversed left from to find data. Since the successor is the SMALLEST element 
     * greater than data, the successor node is the lowest/last node 
     * we traversed left from on the path to the data node.
     *
     * This should NOT be used in the remove method.
     * 
     * Should be recursive. 
     *
     * Ex:
     * Given the following AVL composed of Integers
     *                    76
     *                  /    \
     *                34      90
     *                  \    /
     *                  40  81
     * successor(76) should return 81
     * successor(81) should return 90
     * successor(40) should return 76
     *
     * @param data the data to find the successor of
     * @return the successor of data. If there is no larger data than the
     * one given, return null.
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T successor(T data) {
        return null;
    }

    /**
     * Returns the root of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the root of the tree
     */
    public AVLNode<T> getRoot() {
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
