// AvlTree class
//
// CONSTRUCTION: with no initializer
//
// ******************PUBLIC OPERATIONS*********************
// void insert( x )       --> Insert x
// void remove( x )       --> Remove x (unimplemented)
// boolean contains( x )  --> Return true if x is present
// boolean remove( x )    --> Return true if x was present
// Comparable findMin( )  --> Return smallest item
// Comparable findMax( )  --> Return largest item
// boolean isEmpty( )     --> Return true if empty; else false
// void makeEmpty( )      --> Remove all items
// void printTree( )      --> Print tree in sorted order
// ******************ERRORS********************************
// Throws UnderflowException as appropriate

/**
 * Implements an AVL tree.
 * Note that all "matching" is based on the compareTo method.
 */
public class AvlTree<AnyType extends Comparable<? super AnyType>> {
    /**
     * Construct the tree.
     */
    public AvlTree( ) {
        root = null;
    }

    /**
     * Insert into the tree; duplicates are ignored.
     * @param x the item to insert.
     */
    public void insert( AnyType x ) {
        root = insert( x, root );
    }

    /**
     * Remove from the tree. Nothing is done if x is not found.
     * @param x the item to remove.
     */
    public void remove( AnyType x ) {
        root = remove( x, root );
    }


    /**
     * Internal method to remove from a subtree.
     * @param x the item to remove.
     * @param t the node that roots the subtree.
     * @return the new root of the subtree.
     */
    private AvlNode<AnyType> remove( AnyType x, AvlNode<AnyType> t ) {
        AvlNode<AnyType> tempNode;
        tempNode = t;

        if(contains(x, t)) {                             // ensures x is in the tree
            while(x != tempNode.element) {               // finds the node to delete
                if(x.compareTo(tempNode.element) > 0) {
                    tempNode = tempNode.right;
                } else {
                    tempNode = tempNode.left;
                }
            }
            return removeHelp(t, tempNode);              // deletes the node and replaces
        } else {
            return t;
        }
    }

    private AvlNode<AnyType> removeHelp( AvlNode<AnyType> t, AvlNode<AnyType> delete ) {
        if(t == delete) {
            // case 0: leaf (no recursion)
            if(t.left == null && t.right == null) { t = null; }

            // case 1: only has right child (no recursion)
            else if(t.left == null) { t = t.right; }

            // case 2: only has left child (no recursion)
            else if(t.right == null) { t = t.left; }

            // case 3: has two children
            else {
                delete = findMin(t.left);    // change target to navigate to
                t.element = delete.element;  // update t.element

                // navigate to target
                removeHelp(t, delete);
            }
        } else {
            // navigate to next node that needs changing
            if(t.element.compareTo(delete.element) == 0) {
                t.right = removeHelp(t.right, delete);
            } else {
                t.left = removeHelp(t.left, delete);
            }
        }

        if(t != null) {
            t.height = Math.max(getHeight(t.right), getHeight(t.left));  // updates height
            t.balanceLevel = getHeight(t.right) - getHeight(t.left);     // updates balanceLevel
            if(Math.abs(t.balanceLevel) > 1) {
                t = balance(t);
            }
        }

        return t;
    }

    /**
     * Find the smallest item in the tree.
     * @return smallest item or null if empty.
     */
    public AnyType findMin( ) {
        if( isEmpty( ) )
            throw new UnderflowException( );
        return findMin( root ).element;
    }

    /**
     * Find the largest item in the tree.
     * @return the largest item of null if empty.
     */
    public AnyType findMax( ) {
        if( isEmpty( ) )
            throw new UnderflowException( );
        return findMax( root ).element;
    }

    /**
     * Find an item in the tree.
     * @param x the item to search for.
     * @return true if x is found.
     */
    public boolean contains( AnyType x ) {
        return contains( x, root );
    }

    /**
     * Make the tree logically empty.
     */
    public void makeEmpty( ) {
        root = null;
    }

    /**
     * Test if the tree is logically empty.
     * @return true if empty, false otherwise.
     */
    public boolean isEmpty( ) {
        return root == null;
    }

    /**
     * Print the tree contents in sorted order.
     */
    public void printTree( ) {
        if( isEmpty( ) )
            System.out.println( "Empty tree" );
        else
            printTree( root );
    }

    private static final int ALLOWED_IMBALANCE = 1;

    // Assume t is either balanced or within one of being balanced
    private AvlNode<AnyType> balance( AvlNode<AnyType> t ) {
        if(t.balanceLevel > 0) {
            if(getBalanceLevel(t.right) >= 0) {
                t = rotateWithRightChild(t);
            } else {
                t = doubleWithRightChild(t);
            }
        } else {
            if(getBalanceLevel(t.left) <= 0) {
                t = rotateWithLeftChild(t);
            } else {
                t = doubleWithLeftChild(t);
            }
        }

        return t;
    }

    public void checkBalance( ) {
        checkBalance( root );
    }

    private int checkBalance( AvlNode<AnyType> t ) {
        if( t == null )
            return -1;

        if( t != null ) {
            int hl = checkBalance( t.left );
            int hr = checkBalance( t.right );
            if( Math.abs( height( t.left ) - height( t.right ) ) > 1 ||
                    height( t.left ) != hl || height( t.right ) != hr )
                System.out.println( "OOPS!!" );
        }

        return height( t );
    }


    /**
     * Internal method to insert into a subtree.
     * @param x the item to insert.
     * @param t the node that roots the subtree.
     * @return the new root of the subtree.
     */
    private AvlNode<AnyType> insert( AnyType x, AvlNode<AnyType> t ) {
        int comparison;

        if(root == null) {                        // if tree is empty
            root = new AvlNode<>(x);
            return root;
        } else if(t == null) {                    // if subtree is empty
            t = new AvlNode<>(x);
        } else {
            comparison = x.compareTo(t.element);
            if(comparison == 0) {                 // check for duplicates
                return t;
            } else if(comparison > 0) {           // insert to right subtree
                t.right = insert(x, t.right);
                t.height = Math.max(getHeight(t.right), getHeight(t.left)) + 1;  // change height
                t.balanceLevel = getHeight(t.right) - getHeight(t.left);         // change balanceLevel
                if(Math.abs(t.balanceLevel) > ALLOWED_IMBALANCE) {
                    t = balance(t);
                }
            } else {                              // insert to left subtree
                t.left = insert(x, t.left);
                t.height = Math.max(getHeight(t.right), getHeight(t.left)) + 1;  // change height
                t.balanceLevel = getHeight(t.right) - getHeight(t.left);         // change balanceLevel
                if(Math.abs(t.balanceLevel) > 1) {
                    t = balance(t);
                }
            }
        }

        return t;
    }

    // retrieves height and accounts for null nodes
    private int getHeight(AvlNode<AnyType> t) {
        if(t == null) return -1;
        else return t.height;
    }

    /**
     * Internal method to find the smallest item in a subtree.
     * @param t the node that roots the tree.
     * @return node containing the smallest item.
     */
    private AvlNode<AnyType> findMin( AvlNode<AnyType> t ) {
        while(t.left != null) {
            t = t.left;
        }
        return t;
    }

    /**
     * Internal method to find the largest item in a subtree.
     * @param t the node that roots the tree.
     * @return node containing the largest item.
     */
    private AvlNode<AnyType> findMax( AvlNode<AnyType> t ) {
        while(t.right != null) {
            t = t.right;
        }
        return t;
    }

    /**
     * Internal method to find an item in a subtree.
     * @param x is item to search for.
     * @param t the node that roots the tree.
     * @return true if x is found in subtree.
     */
    private boolean contains( AnyType x, AvlNode<AnyType> t ) {

        // checks if there is no matching node
        if(t == null) { return false; }

        // checks if there is a matching node
        if(x.compareTo(t.element) == 0) { return true; }

        // searches appropriate subtree
        else if(x.compareTo(t.element) > 0) { return contains(x, t.right); }
        else { return contains(x, t.left); }
    }

    /**
     * Internal method to print a subtree in (sorted) order.
     * @param t the node that roots the tree.
     */
    private void printTree( AvlNode<AnyType> t ) {
        if(t != null) {
            printTree(t.left);
            System.out.println(t.element.toString());
            printTree(t.right);
        }
    }

    /**
     * Return the height of node t, or -1, if null.
     */
    private int height( AvlNode<AnyType> t ) {
        return t == null ? -1 : t.height;
    }

    /**
     * Return the balanceLevel of node t, or -1, if null.
     */
    private int getBalanceLevel( AvlNode<AnyType> t) {
        return t == null ? -1 : t.balanceLevel;
    }

    /**
     * Rotate binary tree node with left child.
     * For AVL trees, this is a single rotation for case 1.
     * Update heights, then return new root.
     */
    private AvlNode<AnyType> rotateWithLeftChild( AvlNode<AnyType> k2 ) {
        // declaration
        AvlNode<AnyType> aNode;
        AvlNode<AnyType> bNode;

        // definition
        aNode = k2;
        bNode = aNode.left;

        // rotation
        aNode.left = bNode.right;
        bNode.right = aNode;

        // calculation
        aNode.height = Math.max(getHeight(aNode.left), getHeight(aNode.right));
        bNode.height = Math.max(getHeight(bNode.left), getHeight(bNode.right));

        aNode.balanceLevel = getHeight(aNode.right) - getHeight(aNode.left);
        bNode.balanceLevel = getHeight(bNode.right) - getHeight(bNode.left);

        return bNode;
    }

    /**
     * Rotate binary tree node with right child.
     * For AVL trees, this is a single rotation for case 4.
     * Update heights, then return new root.
     */
    private AvlNode<AnyType> rotateWithRightChild( AvlNode<AnyType> k1 ) {
        // declaration
        AvlNode<AnyType> aNode;
        AvlNode<AnyType> bNode;

        // definition
        aNode = k1;
        bNode = aNode.right;

        // rotation
        aNode.right = bNode.left;
        bNode.left = aNode;

        // calculation
        aNode.height = Math.max(getHeight(aNode.left), getHeight(aNode.right));
        bNode.height = Math.max(getHeight(bNode.left), getHeight(bNode.right));

        aNode.balanceLevel = getHeight(aNode.right) - getHeight(aNode.left);
        bNode.balanceLevel = getHeight(bNode.right) - getHeight(bNode.left);

        return bNode;
    }

    /**
     * Double rotate binary tree node: first left child
     * with its right child; then node k3 with new left child.
     * For AVL trees, this is a double rotation for case 2.
     * Update heights, then return new root.
     */
    private AvlNode<AnyType> doubleWithLeftChild( AvlNode<AnyType> k3 ) {
        // declaration
        AvlNode<AnyType> aNode;
        AvlNode<AnyType> bNode;
        AvlNode<AnyType> cNode;

        // definition
        aNode = k3;
        bNode = aNode.left;

        // rotation
        aNode.left = rotateWithRightChild(bNode);
        cNode = rotateWithLeftChild(aNode);

        // Calculation
        aNode.height = Math.max(getHeight(aNode.left), getHeight(aNode.right));
        bNode.height = Math.max(getHeight(bNode.left), getHeight(bNode.right));
        cNode.height = Math.max(getHeight(cNode.left), getHeight(cNode.right));

        aNode.balanceLevel = getHeight(aNode.right) - getHeight(aNode.left);
        bNode.balanceLevel = getHeight(bNode.right) - getHeight(bNode.left);
        cNode.balanceLevel = getHeight(cNode.right) - getHeight(cNode.left);

        return cNode;
    }

    /**
     * Double rotate binary tree node: first right child
     * with its left child; then node k1 with new right child.
     * For AVL trees, this is a double rotation for case 3.
     * Update heights, then return new root.
     */
    private AvlNode<AnyType> doubleWithRightChild( AvlNode<AnyType> k1 ) {
        // declaration
        AvlNode<AnyType> aNode;
        AvlNode<AnyType> bNode;
        AvlNode<AnyType> cNode;

        // definition
        aNode = k1;
        bNode = aNode.right;

        // rotation
        aNode.right = rotateWithLeftChild(bNode);
        cNode = rotateWithRightChild(aNode);

        // Calculation
        aNode.height = Math.max(getHeight(aNode.left), getHeight(aNode.right));
        bNode.height = Math.max(getHeight(bNode.left), getHeight(bNode.right));
        cNode.height = Math.max(getHeight(cNode.left), getHeight(cNode.right));

        aNode.balanceLevel = getHeight(aNode.right) - getHeight(aNode.left);
        bNode.balanceLevel = getHeight(bNode.right) - getHeight(bNode.left);
        cNode.balanceLevel = getHeight(cNode.right) - getHeight(cNode.left);

        return cNode;
    }

    private static class AvlNode<AnyType> {
        // Constructors
        AvlNode( AnyType theElement ) {
            this( theElement, null, null );
        }

        AvlNode( AnyType theElement, AvlNode<AnyType> lt, AvlNode<AnyType> rt ) {
            element      = theElement;
            left         = lt;
            right        = rt;
            height       = 0;
            balanceLevel = 0;
        }

        AnyType           element;      // The data in the node
        AvlNode<AnyType>  left;         // Left child
        AvlNode<AnyType>  right;        // Right child
        int               height;       // Height
        int               balanceLevel; // How balanced a node is
    }

    /** The tree root. */
    private AvlNode<AnyType> root;
}
