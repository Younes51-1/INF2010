import edu.princeton.cs.algs4.LinearRegression;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.lang.reflect.Field;
import java.time.Duration;
import java.util.*;
import java.util.stream.*;

class AvlTreeTest {
    static private AvlTree<Integer> tree;
    static private final int BALANCED_TREE_SIZE = 7;

    @BeforeEach
    private void setUp() {
        tree = new AvlTree<>();
    }
    /**
     *  Produces the following tree
     *               3
     *             /   \
     *           1      5
     *          / \    / \
     *         0   2  4   6
     */
    private static void addBalancedTree() {
        // Root
        tree.add(3);

        // First level
        tree.add(1);
        tree.add(5);

        // Second level
        tree.add(0);
        tree.add(2);
        tree.add(4);
        tree.add(6);
    }

    @Test
    void addShouldSetRoot() throws NoSuchFieldException, IllegalAccessException {
        tree.add(3);

        //Get the BinaryNode class since it is a private inner class
        Class<?> nodeClass = tree.getClass().getDeclaredClasses()[0];

        //Use reflection to access the root of the AVL
        Field rootField = tree.getClass().getDeclaredField("root");
        rootField.setAccessible(true);

        //Use reflection to access the value property of the root
        Field nodeValueField = nodeClass.getDeclaredField("value");
        nodeValueField.setAccessible(true);
        Integer value = (Integer) nodeValueField.get(rootField.get(tree));

        assertEquals(3, value);
    }

    @Test
    void addShouldPlaceOnAppropriateSide() throws NoSuchFieldException, IllegalAccessException {
        tree.add(2);
        tree.add(1);
        tree.add(3);

        //Get the BinaryNode class since it is a private inner class
        Class<?> nodeClass = tree.getClass().getDeclaredClasses()[0];

        //Use reflection to access private members of AVL
        Field rootField = tree.getClass().getDeclaredField("root");
        Field leftField = nodeClass.getDeclaredField("left");
        Field rightField = nodeClass.getDeclaredField("right");
        Field nodeValueField = nodeClass.getDeclaredField("value");

        //Allow access to the private members field
        rootField.setAccessible(true);
        leftField.setAccessible(true);
        rightField.setAccessible(true);
        nodeValueField.setAccessible(true);

        //Get the root and its left and right child
        Object rootNode = rootField.get(tree);
        Object leftNode = leftField.get(rootNode);
        Object rightNode = rightField.get(rootNode);

        //Get the values of the left and right child
        Integer leftValue = (Integer)nodeValueField.get(leftNode);
        Integer rightValue = (Integer)nodeValueField.get(rightNode);

        assertEquals(1, leftValue);
        assertEquals(3, rightValue);
    }

    @Test
    public void contains(){
        assertFalse(tree.contains(0));

        addBalancedTree();

        for (int value = 0; value < BALANCED_TREE_SIZE; ++value){
            assertTrue(tree.contains(value));
        }
    }

    @Test
    public void cannotContainDuplicates(){
        int duplicatedValue = 1;

        tree.add(duplicatedValue);
        tree.add(duplicatedValue);
        assertTrue(tree.contains(duplicatedValue));

        tree.remove(duplicatedValue);
        assertFalse(tree.contains(duplicatedValue));
    }

    @Test
    void getHeightOnEmptyTree() {
        final int emptyTreeHeight = -1;
        assertEquals(emptyTreeHeight, tree.getHeight());
    }

    @Test
    public void addUpdatesHeight() {
        // Empty
        final int emptyTreeHeight = -1;
        assertEquals(emptyTreeHeight, tree.getHeight());

        // Only root
        tree.add(3);
        final int onlyRootHeight = 0;
        assertEquals(onlyRootHeight, tree.getHeight());

        // First level
        tree.add(1);
        assertEquals(1, tree.getHeight());
        tree.add(5);
        assertEquals(1, tree.getHeight());

        // Second level
        tree.add(2);
        assertEquals(2, tree.getHeight());
        tree.add(4);
        assertEquals(2, tree.getHeight());
        tree.add(6);
        assertEquals(2, tree.getHeight());

        // Third level
        tree.add(7);
        assertEquals(3, tree.getHeight());
    }

    @Test
    public void removePerfectCase(){
        addBalancedTree();

        // Second level
        tree.remove(0);
        assertTrue(!tree.contains(0) && tree.contains(2));
        tree.remove(2);
        assertTrue(!tree.contains(2) && tree.contains(4));
        tree.remove(4);
        assertTrue(!tree.contains(4) && tree.contains(6));
        tree.remove(6);
        assertTrue(!tree.contains(6) && tree.contains(1));

        // First level
        tree.remove(1);
        assertTrue( !tree.contains(1) && tree.contains(5));
        tree.remove(5);
        assertTrue(!tree.contains(5) && tree.contains(3));

        // Root
        tree.remove(3);
        assertFalse(tree.contains(3));
    }

    @Test
    public void findMinOnEmptyTreeReturnsNull() {
        Integer minimumOnEmptyTree = tree.findMin();
        assertNull(minimumOnEmptyTree);
    }

    @Test
    public void findMin() {
        addBalancedTree();
        assertEquals(0, tree.findMin());
    }

    @Test
    public void findMaxOnEmptyTreeReturnsNull() {
        Integer maximumOnEmptyTree = tree.findMax();
        assertNull(maximumOnEmptyTree);
    }

    @Test
    public void findMax() {
        addBalancedTree();
        assertEquals(6, tree.findMax());
    }

    @Test
    public void infixOrder(){
        addBalancedTree();
        List<Integer> sortedList = IntStream.range(0, BALANCED_TREE_SIZE).boxed().collect(Collectors.toList());
        assertEquals(sortedList, tree.infixOrder());
    }

    @Test
    public void rotateRightPerfectCase(){
        tree.add(0);
        tree.add(1);
        tree.add(2);

       assertEquals(tree.getHeight(), 1);

        List<Integer> sortedList = IntStream.range(0, 3).boxed().collect(Collectors.toList());
        assertEquals(sortedList, tree.infixOrder());
    }

    @Test
    public void rotateLeftPerfectCase(){
        tree.add(2);
        tree.add(1);
        tree.add(0);

        assertEquals(tree.getHeight(), 1);

        List<Integer> sortedList = IntStream.range(0, 3).boxed().collect(Collectors.toList());
        assertEquals(sortedList, tree.infixOrder());
    }

    @Test
    public void doubleRotateOnRightPerfectCase(){
        tree.add(2);

        tree.add(1);
        tree.add(5);

        tree.add(0);
        tree.add(4);
        tree.add(6);

        tree.add(3);

        tree.remove(0);

        assertEquals(2, tree.getHeight());

        List<Integer> sortedList = IntStream.range(1, 7).boxed().collect(Collectors.toList());
        assertEquals(sortedList, tree.infixOrder());
    }

    @Test
    public void doubleRotateOnLeftPerfectCase(){
        tree.add(4);

        tree.add(1);
        tree.add(5);

        tree.add(0);
        tree.add(2);
        tree.add(6);

        tree.add(3);

        tree.remove(6);

        assertEquals(tree.getHeight(), 2);

        List<Integer> sortedList = IntStream.range(0, 6).boxed().collect(Collectors.toList());
        assertEquals(sortedList, tree.infixOrder());
    }

    @Test
    public void removeReassignsLeftAndRight(){
        Integer toRemove = 2;

        // Only root
        tree.add(5);

        // First level
        tree.add(toRemove);
        tree.add(8);

        // Second level
        tree.add(0);
        tree.add(4);
        tree.add(6);
        tree.add(10);

        // Third level
        tree.add(1);
        tree.add(3);
        tree.add(9);
        tree.add(7);

        tree.remove(toRemove);

        List<Integer> sortedList = IntStream.range(0, 11).boxed().filter(num -> !num.equals(toRemove)).collect(Collectors.toList());
        assertEquals(sortedList, tree.infixOrder());
    }

    @Test
    public void removeBalances(){
        tree.add(1);

        tree.add(0);
        tree.add(2);

        tree.add(3);

        tree.remove(0);

        assertEquals(1, tree.getHeight());

        List<Integer> sortedList = IntStream.range(1, 4).boxed().collect(Collectors.toList());
        assertEquals(sortedList, tree.infixOrder());
    }

    @Test
    public void completeBalancingTest(){
        int n = 10;
        int expectedHeight =  (int) Math.floor(Math.log(n) / Math.log(2)); // Numerically unstable calculation, won't work for all n values
        List<Integer> sortedList = IntStream.range(0, n).boxed().collect(Collectors.toList());

        // Insertion to the left
        for (int i = n - 1; i >= 0; --i){ tree.add(i); }

        assertEquals(expectedHeight, tree.getHeight());
        assertEquals(sortedList, tree.infixOrder());

        // Insertion to the right
        setUp();
        for (int i = 0; i < n; ++i) { tree.add(i); }

        assertEquals(expectedHeight, tree.getHeight());
        assertEquals(sortedList, tree.infixOrder());
    }

    @Test
    public void handleBigData() {
        final int increaseRate = 500000;
        final int maxSize = 6000000;

        ArrayList<Double> Xs = new ArrayList<>();
        ArrayList<Double> Ys = new ArrayList<>();

        assertTimeoutPreemptively(Duration.ofSeconds(35), () -> {
            for (int listSize = increaseRate; listSize < maxSize; listSize += increaseRate) {
                TreeSet<Integer> javaTree = new TreeSet<>();
                AvlTree<Integer> tree = new AvlTree<>();

                long startTime = System.currentTimeMillis();

                for (int i = 0; i < listSize; ++i) javaTree.add(i);
                for (int i = 0; i < listSize; ++i) assertTrue(javaTree.contains(i));
                for (int i = 0; i < listSize; ++i) javaTree.remove(i);

                double timeSpent =  System.currentTimeMillis() - startTime;
                Xs.add(timeSpent);

                startTime = System.currentTimeMillis();

                for (int i = 0; i < listSize; ++i) tree.add(i);
                for (int i = 0; i < listSize; ++i) tree.contains(i);
                for (int i = 0; i < listSize; ++i) tree.remove(i);

                timeSpent =  System.currentTimeMillis() - startTime;
                Ys.add(timeSpent);
            }
        }, "Votre algorithme n'est probablement pas en O(log n)");

        LinearRegression regression = new LinearRegression(Xs.toArray(Double[]::new), Ys.toArray(Double[]::new));
        System.out.println("R2 : " + regression.R2());
        System.out.println("Time spent JAVA : " + Xs.stream().mapToDouble(Double::doubleValue).sum() / 1000 + " seconds");
        System.out.println("Time spent : " + Ys.stream().mapToDouble(Double::doubleValue).sum() / 1000 + " seconds");

        assertEquals(1.0, regression.R2(), 0.1, "Votre algorithme n'est pas en O(log n)");
    }
}