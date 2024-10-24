/*
 * @file: BST.java
 * @description: This class implements a Binary Search Tree. It relies
 *               on the previously-defined Node.java class for its nodes.
 *               The class includes the following methods:
 *               clear, size, insert, remove, search, printBTS,
 *               iterator
 * @author: Elliott Lowman
 * @date: October 24, 2024
 */

import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Spliterator;
import java.util.function.Consumer;

class BST<T extends Comparable<T>> {
    // Implement the constructor
    private Node<T> root;
    private int nodeCount;

    BST() {
        root = null;
        nodeCount = 0;
    }

    // Implement the clear method
    public void clear() {
        root = null;
        nodeCount = 0;
    }

    // Implement the size method
    public int size() { return nodeCount; }

    // Implement the insert method
    public void insert(T obj) {
        boolean foundPlace = false;
        Node<T> compare = root;

        // base case: insert first object
        if(root == null) {
            root = new Node<>(obj);
            foundPlace = true;
        }

        while(!foundPlace) {
            // ensures no duplicates are put in BST
            if(compare.compareTo(obj) == 0) {
                return;

                // sorts items left of larger items
            } else if(compare.compareTo(obj) > 0) {
                // places leaf
                if(compare.getLeft() == null) {
                    compare.setLeft(obj);
                    foundPlace = true;
                } else {
                    compare = compare.getLeft();
                }

                // sorts items right of smaller items
            } else {
                // places leaf
                if(compare.getRight() == null) {
                    compare.setRight(obj);
                    foundPlace = true;
                } else {
                    compare = compare.getRight();
                }
            }
        }

        nodeCount++;
    }

    // Implement the remove method
    public Node<T> remove(T obj) {
        boolean pcRight = true;  // tracks the relation between parent and child
        // true: child is parent's right child
        // false: child is parent's left child
        boolean end = false;
        Node<T> node = root;
        Node<T> parent = null;   // parent of tracked node
        Node<T> temp = null;     // temporary

        // same as search method, but tracks parent node
        while(!end) {
            if(node.compareTo(obj) == 0) {
                end = true;
            } else if(node.compareTo(obj) > 0) {
                parent = node;
                node = node.getLeft();

                pcRight = false;
            } else {
                parent = node;
                node = node.getRight();

                pcRight = true;
            }

            if(node == null) {
                return null;
            }
        }

        end = false;

        while(!end) {
            // case 0: remove root
            if(node.compareTo(root.getElement()) == 0) {
                if(node.isLeaf()) {
                    root = null;
                    end = true;
                }
                else if(node.getLeft() == null && !(node.getRight() == null)) {
                    root = node.getRight();
                    end = true;
                }
                else if(node.getRight() == null && !(node.getLeft() == null)) {
                    root = node.getLeft();
                    end = true;
                }
                else {
                    while(!end) {
                        if(node.isLeaf()) {
                            if(pcRight) { parent.setRight(); }
                            else { parent.setLeft(); }
                            end = true;

                            // case 2: node has 1 right child
                        } else if(node.getLeft() == null && !(node.getRight() == null)) {
                            if(pcRight) {
                                parent.setRight(node.getRight());
                            } else {
                                parent.setLeft(node.getRight());
                            }
                            end = true;

                            // case 3: node has 1 left child
                        } else if(node.getRight() == null && !(node.getLeft() == null)) {
                            if (pcRight) {
                                parent.setRight(node.getLeft());
                            } else {
                                parent.setLeft(node.getLeft());
                            }
                            end = true;

                            // case 4: node has 2 children
                        } else {
                            parent = node;
                            temp = parent;
                            node = node.getLeft();

                            while(node.getRight() != null) {
                                parent = node;
                                node = node.getRight();
                            }

                            temp.setElement(node.getElement());
                        }
                    }
                }
                // case 1: node has 0 children
            } else if(node.isLeaf()) {
                if(pcRight) { parent.setRight(); }
                else { parent.setLeft(); }
                end = true;

                // case 2: node has 1 right child
            } else if(node.getLeft() == null && !(node.getRight() == null)) {
                if(pcRight) {
                    parent.setRight(node.getRight());
                } else {
                    parent.setLeft(node.getRight());
                }
                end = true;

                // case 3: node has 1 left child
            } else if(node.getRight() == null && !(node.getLeft() == null)) {
                if (pcRight) {
                    parent.setRight(node.getLeft());
                } else {
                    parent.setLeft(node.getLeft());
                }
                end = true;

                // case 4: node has 2 children
            } else {
                parent = node;
                temp = parent;
                node = node.getLeft();

                while(node.getRight() != null) {
                    parent = node;
                    node = node.getRight();
                }

                temp.setElement(node.getElement());
            }
        }

        nodeCount--;
        return node;
    }

    // Implement the search method
    public Node<T> search(T obj) {
        boolean found = false;
        Node<T> node = root;

        while(!found) {
            if(node.compareTo(obj) == 0) {
                found = true;
            } else if(node.compareTo(obj) > 0) {
                node = node.getLeft();
            } else {
                node = node.getRight();
            }

            // ensures node is present
            if(node == null) {
                return null;
            }
        }

        return node;
    }

    // Implement the iterator method
    public String printBST() {
        return iterator(root, "") + "End of BST\n";
    }

    public String iterator(Node<T> node, String returnString) {
        if(node == null) { return returnString; }

        // typical application of inorder traversal
        returnString = iterator(node.getLeft(), returnString);
        returnString = returnString + node.getElement() + "\n ";
        returnString = iterator(node.getRight(), returnString);

        return returnString;
    }
}