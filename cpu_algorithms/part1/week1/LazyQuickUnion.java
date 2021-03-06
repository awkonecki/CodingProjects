public class LazyQuickUnion {
    private int[] mN;

    // Each integer shall be first initialized to their respective index.
    // Index 0 has value 0, 1 has 1 etc.
    public LazyQuickUnion(int N) {
        mN = new int [N];
        for (int index = 0; index < mN.length; index++) {
            mN[index] = index;
        }
    }

    void union(int p, int q) {
        this.checkRange(p);
        this.checkRange(q);

        // Union occurs that convertes q to the p.
        // O(N) due to the fact that a tree can be unbalanced.
        int rootOfP = rootOfNode(p);
        int rootOfQ = rootOfNode(q);
        
        mN[rootOfQ] = mN[rootOfP];
    }

    int rootOfNode(int node) {
        // Performance is tied directly to the height of the tree.
        // O(H) where H is the height.
        if (mN[node] != node)
            return rootOfNode(mN[node]);
        return node;
    }

    boolean isConnected(int p, int q) {
        this.checkRange(p);
        this.checkRange(q);

        return rootOfNode(p) == rootOfNode(q);
    }

    void checkRange(int value) {
        if (value > mN.length - 1 || value < 0) {
            throw new UnsupportedOperationException("p or q is greater than size of array.");
        }
    }

    void printArray() {
        for (int index = 0; index < mN.length; index++) {
            System.out.println("Index " 
                + Integer.toString(index) 
                + " has value " 
                + Integer.toString(mN[index]));
        }
    }

    void printIsConnected(int p, int q) {
        boolean value = this.isConnected(p, q);

        System.out.println("Is p " 
            + Integer.toString(p) 
            + " connected to q " 
            + Integer.toString(q)
            + " " 
            + Boolean.toString(value));
    }

    public static void main(String[] args) {
        LazyQuickUnion myUF = new LazyQuickUnion(10);
        myUF.union(4, 3);
        myUF.union(9, 4);
        myUF.union(9, 2);
        myUF.union(6, 5);
        myUF.printArray();
        return;
    }
}