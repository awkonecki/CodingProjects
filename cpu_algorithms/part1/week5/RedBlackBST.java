import java.util.List;
import java.util.ArrayList;

public class RedBlackBST<Key extends Comparable<Key>, Value> {

    private static final boolean RED_LINK = true;
    private static final boolean BLACK_LINK = false;
    private Node<Key, Value> mRoot = null;

    private class Node<Key extends Comparable<Key>, Value> {
        // Left leaning node in a Red Black BST implementation.
        private Key mKey = null;
        private Node<Key, Value> mLeftChild = null;
        private Node<Key, Value> mRightChild = null;
        private Value mValue = null;
        private int mCount = 1;
        private boolean mLinkColor = RedBlackBST.RED_LINK;

        public Node(Key key, Value value) {
            this.mKey = key;
            this.mValue = value;
        }

        public String toString() {
            return "<" + this.mKey + ", " + this.mValue + "> :: Count : " + 
                Integer.toString(this.mCount) + " :: Link Colors <parent, left, right> : <" +
                Boolean.toString(this.mLinkColor) + ", " + Boolean.toString(this.getNodeLink(this.mLeftChild)) + ", " +
                Boolean.toString(this.getNodeLink(this.mRightChild)) + "> :: Keys <current, left, right> : <" +
                this.mKey + ", " + this.getNodeKey(this.mLeftChild) + ", " + 
                this.getNodeKey(this.mRightChild) + ">";
        }

        private Key getNodeKey(Node<Key, Value> node) {
            if (node == null) {
                return null;
            }

            return node.mKey;
        }

        private boolean getNodeLink(Node<Key, Value> node) {
            if (node == null) {
                return false;
            }

            return node.mLinkColor;
        }
    }

    public void insert(Key key, Value value) {
        if (key == null || value == null) {
            return;
        }

        // Update the root post insert.
        this.mRoot = this.insert(this.mRoot, key, value);
        this.mRoot.mLinkColor = RedBlackBST.BLACK_LINK;
    }

    public int size() {
        return this.size(this.mRoot);
    }

    private int size(Node<Key, Value> root) {
        if (root == null) {
            return 0;
        }

        return root.mCount;
    }

    private Node<Key, Value> insert(Node<Key, Value> root, Key key, Value value) {
        if (root == null) {
            // Not found create a new node.
            return new Node<Key, Value>(key, value);
        }

        if (root.mKey.compareTo(key) > 0) {
            // key is less than go left.
            root.mLeftChild = this.insert(root.mLeftChild, key, value);
            root.mCount = 1 + this.size(root.mRightChild) + this.size(root.mLeftChild);
        }
        else if (root.mKey.compareTo(key) < 0) {
            // key is greater than go right.
            root.mRightChild = this.insert(root.mRightChild, key, value);
            root.mCount = 1 + this.size(root.mRightChild) + this.size(root.mLeftChild);
        }
        else {
            // key is equal, update since not supporting duplicate keys.
            root.mValue = value;
        }
        
        // Apply upward invariant for Red Black Tree.

        // At this point the tree is collapsing upward.  Local sub-tree that was
        // traversed for the insert must have a correct invariant, otherwise bad
        // things will occur.

        // Two Node Consideration Case 1 - new node is left child with new link 
        // being red and to the left.
        // Do nothing.

        // Two Node Consideration Case 2 - new node is right child with new link
        // being red and to the right (invariant needs to be addressed).

        // Three Node Consideration Case 1
        // Three Node Consideration Case 2
        // Three Node Consideration Case 3

        if (root.mRightChild != null && root.mRightChild.mLinkColor) {
            root = this.rotateLeft(root);
        }

        if (root.mLeftChild != null && root.mRightChild != null && root.mLeftChild.mLinkColor && root.mRightChild.mLinkColor) {
            this.flip(root);
        }

        if (root.mLeftChild != null && root.mLeftChild.mLeftChild != null && root.mLeftChild.mLinkColor && root.mLeftChild.mLeftChild.mLinkColor) {
            // Temporary 4 Node.
            root = this.rotateRight(root);
            this.flip(root);
        }

        if (root.mLeftChild != null && root.mRightChild != null && root.mLinkColor && root.mRightChild.mLinkColor) {
            root = this.rotateLeft(root);
            this.flip(root);
        }

        this.isRedBlackTree(root);
        try {
            assert(this.isRedBlackTree(root));
            // System.out.println(root);
        }
        catch(java.lang.AssertionError e) {

            System.out.println(root);
            throw new java.lang.UnsupportedOperationException(e);
        }



        return root;
    }

    // Localized flipping of the link colors.
    private void flip(Node<Key, Value> root) {
        // Should only execute this function in the pressence of a left and 
        // right child with red links.
        assert(!(root.mRightChild == null || root.mLeftChild == null));
        assert(this.isRightChildRed(root) && this.isLeftChildRed(root));

        root.mLeftChild.mLinkColor = !root.mLeftChild.mLinkColor;
        root.mRightChild.mLinkColor = !root.mRightChild.mLinkColor;
        root.mLinkColor = !root.mLinkColor;

        // Post condition checks.
        assert(!(this.isRightChildRed(root) || this.isLeftChildRed(root)));
        assert(root.mLinkColor);
    }

    // Localized rotation right (e.g. the left becomes the parent)
    private Node<Key, Value> rotateRight(Node<Key, Value> root) {
        // Never perform a rotate right if the left child is null.
        assert(!(root.mLeftChild == null));

        Node<Key, Value> oldRoot = root;
        Node<Key, Value> newRoot = root.mLeftChild;

        // Fix old root's left to be the right child of the old root's left
        // child.
        oldRoot.mLeftChild = newRoot.mRightChild;
        // The left child of the new root must now be set to the old root.
        newRoot.mRightChild = oldRoot;

        // Update link colors appropriately.
        newRoot.mLinkColor = oldRoot.mLinkColor;
        oldRoot.mLinkColor = RedBlackBST.RED_LINK;
        
        // Count Update
        oldRoot.mCount = 1 + this.size(oldRoot.mLeftChild) + this.size(oldRoot.mRightChild);
        newRoot.mCount = 1 + this.size(newRoot.mLeftChild) + this.size(newRoot.mRightChild);

        // Ensure that the old root's right child is not touched.
        assert(oldRoot.mRightChild == root.mRightChild);
        // Ensure that the new right child of the old root is not a red link.
        assert(!(this.isLeftChildRed(oldRoot)));

        // Return the new root.
        return newRoot;
    }

    // Localized rotation left (e.g. the right becomes the parent)
    private Node<Key, Value> rotateLeft(Node<Key, Value> root) {
        // Never perform a rotate left if the right child is null.
        assert(!(root.mRightChild == null));
        
        Node<Key, Value> oldRoot = root;
        Node<Key, Value> newRoot = root.mRightChild;
        int tempCount = 0;

        // Fix old root's right to be the left child of the old root's right
        // child.
        oldRoot.mRightChild = oldRoot.mRightChild.mLeftChild;
        // The left child of the new root must now be set to the old root.
        newRoot.mLeftChild = oldRoot;

        // Update link colors appropriately.
        newRoot.mLinkColor = oldRoot.mLinkColor;
        oldRoot.mLinkColor = RedBlackBST.RED_LINK;
        
        // Count Update
        oldRoot.mCount = 1 + this.size(oldRoot.mLeftChild) + this.size(oldRoot.mRightChild);
        newRoot.mCount = 1 + this.size(newRoot.mLeftChild) + this.size(newRoot.mRightChild);

        // Ensure that the old root's left child is not touched.
        assert(oldRoot.mLeftChild == root.mLeftChild);
        // Ensure that the new right child of the old root is not a red link.
        assert(!(this.isRightChildRed(oldRoot)));

        // Return the new root.
        return newRoot;
    }

    private boolean isLeftChildRed(Node<Key, Value> root) {
        if (root == null || root.mLeftChild == null) {
            return false;
        }

        return root.mLeftChild.mLinkColor;
    }

    private boolean isRightChildRed(Node<Key, Value> root) {
        if (root == null || root.mRightChild == null) {
            return false;
        }

        return root.mRightChild.mLinkColor;
    }

    public Value delete(Key key) {
        return null;
    }

    public Value find(Key key) {
        Node<Key, Value> node = null;

        if (key == null || this.mRoot == null) {
            return null;
        }

        node = this.search(this.mRoot, key);

        if (node == null) {
            return null;
        }

        return node.mValue;
    }

    public String getNodeDetails(Key key) {
        Node<Key, Value> node = null;

        if (key == null || this.mRoot == null) {
            return null;
        }

        node = this.search(this.mRoot, key);

        if (node == null) {
            return null;
        }

        return node.toString();
    }

    private Node<Key, Value> search(Node<Key, Value> root, Key key) {
        if (root == null) {
            return null;
        }

        if (root.mKey.compareTo(key) > 0) {
            return this.search(root.mLeftChild, key);
        }
        else if (root.mKey.compareTo(key) < 0) {
            return this.search(root.mRightChild, key);
        }
        else {
            return root;
        }
    }

    public int rank(Key key) {
        return 0;
    }

    public int ceil(Key key) {
        return 0;
    }

    public int floor(Key key) {
        return 0;
    }

    public Iterable<Key> keys() {
        List<Key> keys = new ArrayList<Key>();

        this.inOrderTraversal(keys, this.mRoot);

        return keys;
    }

    public boolean isRedBlackTree() {
        return this.isRedBlackTree(this.mRoot);
    }

    private boolean isRedBlackTree(Node<Key, Value> root) {
        if (root == null) {
            return true;
        }

        // Right link is red.
        if (root.mRightChild != null && root.mRightChild.mLinkColor) {
            assert(false);
            // return false;
        }

        // Multiple red links in a row (more than a 2/3 red tree).
        // This is only true when the 4-Node case does not apply
        // if (root.mLeftChild != null && root.mLeftChild.mLinkColor && root.mLinkColor) {
            // assert(false);
        //    return false;
        // }

        // Keys are not in order.
        if (root.mLeftChild != null && root.mKey.compareTo(root.mLeftChild.mKey) <= 0) {
            assert(false);
            // return false;
        }

        if (root.mRightChild != null && root.mKey.compareTo(root.mRightChild.mKey) >= 0) {
            assert(false);
            // return false;
        }

        // Counts of children must be equal 
        // This is only true when the 4-Node case does not apply
        //if (root.mLeftChild != null && !root.mLeftChild.mLinkColor && (root.mLeftChild.mCount != root.mRightChild.mCount)) {
        //    assert(false);
            // return false;
        //}

        return true;
    }

    private void inOrderTraversal(List<Key> keys, Node<Key, Value> root) {
        if (root == null) {
            return;
        }

        this.inOrderTraversal(keys, root.mLeftChild);
        keys.add(root.mKey);
        this.inOrderTraversal(keys, root.mRightChild);

        return;
    }

    public static void main(String [] args) {
        RedBlackBST<Integer, Integer> bst = new RedBlackBST<Integer, Integer>();

        bst.insert(10, 10);
        bst.insert(9, 10);
        bst.insert(0, 10);
        bst.insert(1, 10);
        bst.insert(8, 10);
        bst.insert(7, 10);
        bst.insert(2, 10);
        bst.insert(3, 10);
        bst.insert(6, 10);
        bst.insert(4, 10);
        bst.insert(5, 10);

        for (Integer key : bst.keys()) {
            System.out.println(bst.getNodeDetails(key));
        }

        System.out.println("");
        System.out.println(bst.size());
    }
}