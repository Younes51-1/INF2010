import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class AvlTree<ValueType extends Comparable<? super ValueType> > {

    // Only node which has its parent to null
    private BinaryNode<ValueType> root;

    public AvlTree() { }

    /** TODO Worst case : O ( log n ) HAS TO BE ITERATIVE, NOT RECURSIVE
     *  TODO: What's the average case? Briefly explain
     *  Deux boucles parcourent chacune l'arbre en le divisant en deux parties, droite et gauche :
     *  boucle while #1 en O(logn) + boucle while #2 en O(logn) = O(logn) en average case
     * Adds value to the tree and keeps it as a balanced AVL Tree
     * Should call balance only if insertion succeeds
     * AVL Trees do not contain duplicates
     *
     * @param value value to add to the tree
     */
    public void add(ValueType value) {
        if (contains(value))
            return;
        BinaryNode<ValueType> node = this.root, parent = null;
        if (node == null) {
            this.root = new BinaryNode<>(value, null);
            return;
        }
        while (node != null) {
            parent = node;
            node = node.value.compareTo(value) > 0 ? node.left : node.right;
        }
        node = new BinaryNode<>(value, parent);
        if (parent.value.compareTo(node.value) > 0)
            parent.left = node;
        else
            parent.right = node;
        BinaryNode<ValueType> addedNode = node;
        while (node.parent != null) {
            if (BinaryNode.getHeight(node.parent) < BinaryNode.getHeight(node) + 1)
                node.parent.height = BinaryNode.getHeight(node) + 1;
            node = node.parent;
        }
        balance(addedNode);
    }

    /** TODO Worst case : O ( log n )
     *  TODO: What's the average case? Briefly explain
     *  Trois boucles parcourent chacune l'arbre en le divisant en deux parties, droite et gauche :
     *  boucle while #1 en O(logn) + boucle while #2 en O(logn) +  boucle while #3 en O(logn) = O(logn) en average case
     * Removes value from the tree and keeps it as a balanced AVL Tree
     * Should call balance only if removal succeeds
     *
     * @param value value to remove from the tree
     */
    public void remove(ValueType value) {
        if (!contains(value))
            return;
        BinaryNode<ValueType> node = root;
        while (!node.value.equals(value)) {
            node = node.value.compareTo(value) > 0 ? node.left : node.right;
        }
        if (node.right == node.left) {
            if (node.value.equals(root.value))
                root = null;
            else if (node.parent.value.compareTo(node.value) > 0)
                node.parent.left = null;
            else
                node.parent.right = null;
        }
        else if (node.right == null || node.left == null) {
            BinaryNode<ValueType> child = node.right == null ? node.left : node.right;
            if (node.value.equals(root.value))
                root = child;
            else if (node.parent.value.compareTo(node.value) > 0)
                node.parent.left = child;
            else
                node.parent.right = child;
        }
        else {
            BinaryNode<ValueType> min = node.right;
            while (min.left != null)
                min = min.left;
            node.value = min.value;
            min.parent.left = min.right;
            if (min.right != null)
                min.right.parent = min.parent;
        }
        BinaryNode<ValueType> removedNode = node;
        node = node.parent;
        while (node != null) {
            int lHeight = BinaryNode.getHeight(node.left);
            int rHeight = BinaryNode.getHeight(node.right);
            node.height = Math.max(lHeight,rHeight) + 1;
            node = node.parent;
        }
        balance(removedNode);
    }

    /** TODO Worst case : O ( log n ) HAS TO BE ITERATIVE, NOT RECURSIVE
     *  TODO: What's the average case? Briefly explain
     *  Une boucle parcourt l'arbre en le divisant en deux parties droite et gauche :
     *  boucle while en O(logn) = O(logn) en average case
     * Verifies if the tree contains value
     * @param value value to verify
     * @return if value already exists in the tree
     */
    public boolean contains(ValueType value) {
        BinaryNode<ValueType> node = root;
        while (node != null) {
            if (node.value.equals(value))
                return true;
            node = node.value.compareTo(value) > 0 ? node.left : node.right;
        }
        return false;
    }

    /** TODO Worst case : O( 1 )
     *  TODO: What's the average case? Briefly explain
     *  Appel de la fonction getHeight qui est en O(1) car constante, donc O(1) en average case
     * Returns the max level contained in our root tree
     * @return Max level contained in our root tree
     */
    public int getHeight() {
        return BinaryNode.getHeight(this.root);
    }

    /** TODO O( log n ) HAS TO BE ITERATIVE, NOT RECURSIVE
     *  TODO: What's the average case? Briefly explain
     *  Une boucle parcourt l'arbre en le divisant en deux parties droite et gauche et itère sur la moitié de gauche :
     *  boucle while en O(logn) = O(logn) en average case
     * Returns the node which has the minimal value contained in our root tree
     * @return Node which has the minimal value contained in our root tree
     */
    public ValueType findMin() {
        if (this.root == null)
            return null;
        BinaryNode<ValueType> node = this.root;
        while (node.left != null)
            node = node.left;
        return node.value;
    }

    /** TODO O( log n ) HAS TO BE ITERATIVE, NOT RECURSIVE
     *  TODO: What's the average case? Briefly explain
     *  Une boucle parcourt l'arbre en le divisant en deux parties droite et gauche et itère sur la moitié de droite :
     *  boucle while en O(logn) = O(logn) en average case
     * Returns the node which has the maximum value contained in our root tree
     * @return Node which has the maximum value contained in our root tree
     */
    public ValueType findMax() {
        if (this.root == null)
            return null;
        BinaryNode<ValueType> node = this.root;
        while (node.right != null)
            node = node.right;
        return node.value;
    }

    /** TODO Worst case : O( n ) HAS TO BE ITERATIVE, NOT RECURSIVE
     *  TODO: What's the average case? Briefly explain
     *  Deux boucles parcourent l'arbre divisé en droite et gauche de façon linéaire :
     *  boucle while #1 en O(n) droite en O(n) + boucle while #2 en O(n) gauche en O(n) = O(n) en average case
     * Returns all values contained in the root tree in ascending order
     * @return Values contained in the root tree in ascending order
     */
    public List<ValueType> infixOrder() {
        LinkedList<ValueType> list = new LinkedList<>();
        BinaryNode<ValueType> node = this.root;
        Stack<BinaryNode<ValueType>> stack = new Stack<>();
        while (node != null || !stack.empty()) {
            while (node != null) {
                stack.push(node);
                node = node.left;
            }
            node = stack.pop();
            list.add(node.value);
            node = node.right;
        }
        return list;
    }

    /** TODO Worst case : O( log n ) HAS TO BE ITERATIVE, NOT RECURSIVE
     *  TODO: What's the average case? Briefly explain
     *  Fonction récursive en O(logn) car appelle seulement le parent du noeud, donc O(logn) en average case
     * Balances the whole tree
     * @param node Node to balance all the way to root
     */
    private void balance(BinaryNode<ValueType> node) {
        if (node == null)
            return;
        if (BinaryNode.getHeight(node.left) - BinaryNode.getHeight(node.right) > 1) {
            if (BinaryNode.getHeight(node.left.left) <= BinaryNode.getHeight(node.left.right))
                rotateRight(node.left);
            rotateLeft(node);
        }
        else if (BinaryNode.getHeight(node.right) - BinaryNode.getHeight(node.left) > 1) {
            if (BinaryNode.getHeight(node.right.right) <= BinaryNode.getHeight(node.right.left))
                rotateLeft(node.right);
            rotateRight(node);
        }
        node.height = Math.max(BinaryNode.getHeight(node.left), BinaryNode.getHeight(node.right)) + 1;
        balance(node.parent);
    }

    /** TODO Worst case : O ( 1 )
     *
     * Single rotation to the left child, AVR Algorithm
     * @param node1 Node to become child of its left child
     */
    private void rotateLeft(BinaryNode<ValueType> node1) {
        BinaryNode<ValueType> node2 = node1.left;
        node2.parent = node1.parent;
        if (node2.parent == null)
            root = node2;
        else if (node2.value.compareTo(node2.parent.value) > 0)
            node2.parent.right = node2;
        else
            node2.parent.left = node2;
        node1.left = node2.right;
        if (node1.left != null)
            node1.left.parent = node1;
        node2.right = node1;
        node1.parent = node2;
        node1.height = Math.max(BinaryNode.getHeight(node1.left), BinaryNode.getHeight(node1.right)) + 1;
        node2.height = Math.max(BinaryNode.getHeight(node2.left), BinaryNode.getHeight(node2.right)) + 1;
    }

    /** TODO Worst case : O ( 1 )
     *
     * Single rotation to the right, AVR Algorithm
     * @param node1 Node to become child of its right child
     */
    private void rotateRight(BinaryNode<ValueType> node1){
        BinaryNode<ValueType> node2 = node1.right;
        node2.parent = node1.parent;
        if(node2.parent == null)
            root = node2;
        else if (node2.value.compareTo(node2.parent.value) > 0)
            node2.parent.right = node2;
        else
            node2.parent.left = node2;
        node1.right = node2.left;
        if (node1.right != null)
            node1.right.parent= node1;
        node2.left = node1;
        node1.parent = node2;
        node1.height = Math.max(BinaryNode.getHeight(node1.left), BinaryNode.getHeight(node1.right)) + 1;
        node2.height = Math.max(BinaryNode.getHeight(node2.left), BinaryNode.getHeight(node2.right)) + 1;
    }

    /** TODO O( n )
     *  TODO: What's the average case? Briefly explain
     *  Fonction récursive qui appelle seulement de façon linéaire la partie gauche de l'arbre en O(n) + la partie droite de l'arbre en O(n), donc O(n) en average case
     * Builds items which should contain all values within the root tree in ascending order
     * @param currentNode Node currently being accessed in this recursive method
     * @param items List being modified to contain all values in the root tree in ascending order
     */
    private void infixOrder(BinaryNode<ValueType> currentNode, List<ValueType> items){
        if (currentNode == null)
            return;
        infixOrder(currentNode.left, items);
        items.add(currentNode.value);
        infixOrder(currentNode.right, items);
    }

    static private class BinaryNode<ValueType> {
        ValueType value;

        BinaryNode<ValueType> parent; // Pointer to the node containing this node

        BinaryNode<ValueType> left = null; // Pointer to the node on the left which should contain a value below this.value
        BinaryNode<ValueType> right = null; // Pointer to the node on the right which should contain a value above this.value

        int height = 0;

        // Null-safe height accessor
        // Raw use of parameterized class is justified because we do not use any value of the BinaryNode, only the height of the parameter
        static public int getHeight(BinaryNode node) {
            return node != null ? node.height : -1;
        }

        BinaryNode(ValueType value, BinaryNode<ValueType> parent)
        {
            this.value = value;
            this.parent = parent;
        }
    }
}