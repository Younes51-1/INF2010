import java.util.*;

public class Heap<ValueType extends Comparable<? super ValueType>> implements Iterable<ValueType> {
    private static final boolean DEFAULT_IS_MIN_HEAP = true;

    private ArrayList<ValueType> data;

    /**
     * Decides if the heap is a MinHeap (True) or a MaxHeap (False)
     */
    private final boolean isMinHeap;

    public Heap() {
        this(DEFAULT_IS_MIN_HEAP);
    }

    /**
     * @param data Unordered list of elements
     */
    public Heap(Collection<ValueType> data) {
        this(DEFAULT_IS_MIN_HEAP, data);
    }

    /**
     * @param isMinHeap
     */
    public Heap(boolean isMinHeap) {
        this.isMinHeap = isMinHeap;
        data = new ArrayList<>();
    }

    /**
     * @param isMinHeap
     * @param data Unordered list of elements
     */
    public Heap(boolean isMinHeap, Collection<ValueType> data) {
        this.isMinHeap = isMinHeap;
        this.data = new ArrayList<>(data);
        heapify();
    }

    /**
     * @return Number of elements within `heap`
     */
    public int size() {
        return data.size();
    }

    /**
     * @return Min/Max depending on `isMinHeap`
     */
    public ValueType peek() {
        return data.get(0);
    }

    /** TODO Worst Case O(1)
     * @param childIndex Index associated to child of the parent that will be returned
     * @return Index of the parent of `childIndex`
     */
    private int parent(int childIndex) {
        return (childIndex - 1) / 2;
    }

    /** TODO Worst Case O(1)
     * @param parentIndex Index associated to the parent of the left child that will be returned
     * @return Index of the left child of `parentIndex`
     */
    private int left(int parentIndex) {
        return 2 * parentIndex + 1;
    }

    /** TODO Worst Case O(1)
     * @param parentIndex Index associated to the parent of the right child that will be returned
     * @return Index of the right child of `parentIndex`
     */
    private int right(int parentIndex) {
        return 2 * (parentIndex + 1);
    }

    /** TODO Worst Case O(1)
     * Swap value at `firstIndex` and `secondIndex`
     * Value initially at `firstIndex` will now be at `secondIndex`
     * Value initially at `secondIndex` will now be at `firstIndex`
     *
     * @param firstIndex Index of the first element to be swapped
     * @param secondIndex Index of the second element to be swapped
     */
    private void swap(int firstIndex, int secondIndex) {
        ValueType temp = this.data.get(firstIndex);
        this.data.set(firstIndex, this.data.get(secondIndex));
        this.data.set(secondIndex, temp);
    }

    /** TODO Worst Case O(n)
     *   See here for complexity explanation :
     *   https://stackoverflow.com/questions/9755721/how-can-building-a-heap-be-on-time-complexity/18295327#18295327
     *
     * Rearrange elements within `data` to respect either MinHeap
     * or MaxHeap format depending on `isMinHeap`
     */
    private void heapify() {
        for (int i = this.size() - 1; i >= 0 ; i--) {
            if (this.isMinHeap)
                this.percolateDown(i);
            else
                this.percolateUp(i);
        }
    }

    /** TODO Worst Case O(log n)
     *   HAS TO BE RECURSIVE
     * Move the value at `childIndex` towards the root (index 0) until
     * it respects either MinHeap or MaxHeap format depending on `isMinHeap`
     *
     * @param childIndex
     */
    private void percolateUp(int childIndex) {
        if (!isMinHeap && childIndex > 0 && this.data.get(parent(childIndex)).compareTo(this.data.get(childIndex)) < 0) {
            this.swap(childIndex, this.parent(childIndex));
            this.percolateUp(this.parent(childIndex));
        }
        else if (isMinHeap && childIndex > 0 && this.data.get(parent(childIndex)).compareTo(this.data.get(childIndex)) > 0) {
            this.swap(childIndex, this.parent(childIndex));
            this.percolateUp(this.parent(childIndex));
        }
    }

    /** TODO Worst Case O(log n)
     *   HAS TO BE RECURSIVE
     * Move the value at `parentIndex` towards the leaves prioritizing the left until
     * it respects either MinHeap or MaxHeap format depending on `isMinHeap`
     *
     * @param parentIndex
     */
    private void percolateDown(int parentIndex) {
        int left = this.left(parentIndex);
        int right = this.right(parentIndex);
        int index = parentIndex;
        if (isMinHeap) {
            if (left < this.size() && this.data.get(left).compareTo(this.data.get(parentIndex)) < 0)
                index = left;
            if (right < this.size() && this.data.get(right).compareTo(this.data.get(index)) < 0)
                index = right;
        }
        else {
            if (left < this.size() && this.data.get(left).compareTo(this.data.get(parentIndex)) > 0)
                index = left;
            if (right < this.size() && this.data.get(right).compareTo(this.data.get(index)) > 0)
                index = right;
        }
        if (index != parentIndex) {
            this.swap(parentIndex, index);
            this.percolateDown(index);
        }
    }

    /** TODO Worst Case O(log n)
     * Add `element` to `heap` while making sure `heap` still respects
     * either MinHeap or MaxHeap format depending on `isMinHeap`
     *
     * @param element Value to add within `heap`
     */
    public void push(ValueType element) {
        if (element != null) {
            this.data.add(element);
            this.percolateUp(this.size() - 1);
        }
    }

    /** TODO Worst Case O(log n)
     * Remove the Min/Max from `heap` while making sure `heap` still respects
     * either MinHeap or MaxHeap format depending on `isMinHeap`
     *
     * @return Min/Max depending on `isMinHeap`
     */
    public ValueType pop() {
        ValueType removed = null;
        if (this.data.size() > 0) {
            removed = this.data.remove(0);
            this.percolateDown(0);
        }
        return removed;
    }

    /** TODO Worst Case O(n log n)
     * Sort elements with a heap sort
     * Elements will be in ascending order if it is a MinHeap
     * Elements will be in descending order if it is a MaxHeap
     *
     * @return Sorted elements
     */
    public ArrayList<ValueType> sort() {
        ArrayList<ValueType> dataCopy = new ArrayList<>(this.data);
        ArrayList<ValueType> sortedList = new ArrayList<>(this.data.size());
        while (this.data.size() != 0) {
            ValueType firstEntry = this.pop();
            sortedList.add(firstEntry);
        }
        this.data = dataCopy;
        return sortedList;
    }

    @Override
    public Iterator<ValueType> iterator() {
        return data.iterator();
    }
}
