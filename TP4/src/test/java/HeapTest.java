import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class HeapTest {
    @Test
    void startAtZeroIndex() throws Exception {
        Heap<Integer> heap = new Heap<>();

        int value = 1;
        heap.push(value);

        ArrayList<Integer> innerData = getInnerData(heap);

        assertEquals(value, innerData.get(0));
    }

    @Test
    void canContainDuplicates() {
        Heap<Integer> heap = new Heap<>();

        heap.push(1);
        heap.push(1);

        assertEquals(2, heap.size());

        heap = new Heap<>(List.of(1,1));

        assertEquals(2, heap.size());
    }

    @Test
    void popAscending() throws Exception {
        ArrayList<Integer> mockHeap = IntStream.range(0, 7)
                .boxed()
                .collect(Collectors.toCollection(ArrayList::new));

        ArrayList<Integer> expected = new ArrayList<>(mockHeap);

        pop(expected, mockHeap, true);
    }

    @Test
    void popDescending() throws Exception {
        ArrayList<Integer> mockHeap = IntStream.range(0, 7)
                .boxed()
                .collect(Collectors.toCollection(ArrayList::new));
        Collections.reverse(mockHeap);

        ArrayList<Integer> expected = new ArrayList<>(mockHeap);

        pop(expected, mockHeap, false);
    }

    @Test
    void pushAscending() throws Exception {
        List<Integer> inputs = IntStream.range(0, 7)
                .boxed()
                .collect(Collectors.toList());

        push(inputs, true);
    }

    @Test
    void pushDescending() throws Exception {
        List<Integer> inputs = IntStream.range(0, 7)
                .boxed()
                .collect(Collectors.toList());
        Collections.reverse(inputs);

        push(inputs, false);
    }

    @Test
    void heapifyAscending() throws Exception {
        List<Integer> inputs = IntStream.range(0, 7)
                .boxed()
                .collect(Collectors.toList());
        Collections.reverse(inputs);

        heapify(inputs, true);
    }

    @Test
    void heapifyDescending() throws Exception {
        List<Integer> inputs = IntStream.range(0, 7)
                .boxed()
                .collect(Collectors.toList());

        heapify(inputs, false);
    }

    @Test
    void heapSortAscending() throws Exception {
        ArrayList<Integer> mockHeap = new ArrayList<>(List.of(
           0,2,1,3,5,6,4
        ));

        ArrayList<Integer> expected = new ArrayList<>(mockHeap);
        Collections.sort(expected);

        heapSort(expected, mockHeap, true);
    }

    @Test
    void heapSortDescending() throws Exception {
        ArrayList<Integer> mockHeap = new ArrayList<>(List.of(
                6,4,5,3,1,0,2
        ));

        ArrayList<Integer> expected = new ArrayList<>(mockHeap);
        Collections.sort(expected, Collections.reverseOrder());

        heapSort(expected, mockHeap, false);
    }

    @Test
    void testPopComplexityWithBarometer() throws Exception {
        int increaseRate = 100;
        int maxSize = 10000;

        ArrayList<Double> Xs = new ArrayList<>();
        ArrayList<Double> Ys = new ArrayList<>();

        Random rnd = new Random();

        for (int listSize = increaseRate; listSize < maxSize; listSize += increaseRate) {
            Heap<MockValue> heap = new Heap<>();

            ArrayList<MockValue> mockValues = rnd.ints(listSize, -listSize, listSize)
                    .boxed()
                    .sorted()
                    .map(MockValue::new)
                    .collect(Collectors.toCollection(ArrayList::new));

            mockInnerData(heap, new ArrayList<>(mockValues));

            for (int i = 0; i < listSize; ++i) heap.pop();

            // Count the barometer operation for complexity.
            int totalBarometer = 0;
            for (MockValue value : mockValues)
                totalBarometer += value.getBarometerCounter();

            Xs.add((double)listSize);
            Ys.add((double)totalBarometer);
        }

        // O(n log n) tends to O(n) when n is large, therefore we can use a linear test (R2)
        // Trend should be linear between input size and barometer => R2 ~= 1 => O(n)
        LinearRegression regression = new LinearRegression(Xs.toArray(new Double[0]), Ys.toArray(new Double[0]));
        assertEquals(1.0, regression.R2(), 0.01);
    }

    @Test
    void testPushComplexityWithBarometer() throws Exception {
        int increaseRate = 100;
        int maxSize = 10000;

        ArrayList<Double> Xs = new ArrayList<>();
        ArrayList<Double> Ys = new ArrayList<>();

        Random rnd = new Random();

        for (int listSize = increaseRate; listSize < maxSize; listSize += increaseRate) {
            Heap<MockValue> heap = new Heap<>();

            List<MockValue> mockValues = rnd.ints(listSize, -listSize, listSize)
                    .boxed()
                    .sorted(Comparator.reverseOrder())
                    .map(MockValue::new)
                    .collect(Collectors.toList());

            for (MockValue mock : mockValues) heap.push(mock);

            assertEquals(Collections.min(mockValues).getValue(), heap.peek().getValue());

            // Count the barometer operation for complexity.
            int totalBarometer = 0;
            for (MockValue value : getInnerData(heap))
                totalBarometer += value.getBarometerCounter();

            Xs.add((double)listSize);
            Ys.add((double)totalBarometer);
        }

        // O(n log n) tends to O(n) when n is large, therefore we can use a linear test (R2)
        // Trend should be linear between input size and barometer => R2 ~= 1 => O(n)
        LinearRegression regression = new LinearRegression(Xs.toArray(new Double[0]), Ys.toArray(new Double[0]));
        assertEquals(1.0, regression.R2(), 0.01);
    }

    @Test
    void testHeapifyComplexityWithBarometer() {
        int increaseRate = 100;
        int maxSize = 10000;

        ArrayList<Double> Xs = new ArrayList<>();
        ArrayList<Double> Ys = new ArrayList<>();

        Random rnd = new Random();

        for (int listSize = increaseRate; listSize < maxSize; listSize += increaseRate) {
            List<MockValue> mockValues = rnd.ints(listSize, -listSize, listSize)
                    .boxed()
                    .map(MockValue::new)
                    .collect(Collectors.toList());

            Heap<MockValue> heap = new Heap<>(new ArrayList<>(mockValues));

            assertEquals(Collections.min(mockValues).getValue(), heap.peek().getValue());

            int totalBarometer = 0;
            for (MockValue value : heap)
                totalBarometer += value.getBarometerCounter();

            Xs.add((double)listSize);
            Ys.add((double)totalBarometer);
        }

        // Trend should be linear between input size and barometer => R2 ~= 1 => O(n).
        LinearRegression regression = new LinearRegression(Xs.toArray(new Double[0]), Ys.toArray(new Double[0]));
        assertEquals(1.0, regression.R2(), 0.01);
    }

    void pop(ArrayList<Integer> expected, ArrayList<Integer> mockHeap, boolean isMinHeap) throws Exception {
        Heap<Integer> heap = new Heap<>(isMinHeap);
        mockInnerData(heap, mockHeap);

        int initialSize = expected.size();
        for (int i = 0; i < initialSize; ++i) {
            assertEquals(expected.get(i), heap.pop());

            if(i < initialSize - 1) {
                assertEquals(expected.get(i + 1), mockHeap.get(0));
                assertEquals(initialSize - i - 1, mockHeap.size());
            } else {
                assertTrue(mockHeap.isEmpty());
            }
        }
    }

    void push(List<Integer> inputs , boolean isMinHeap) throws Exception {
        Heap<Integer> heap = new Heap<>(isMinHeap);
        for(Integer input : inputs) heap.push(input);

        ArrayList<Integer> innerData = getInnerData(heap);
        assertEquals(inputs.size(), innerData.size());
        validateInnerData(innerData, isMinHeap);
    }

    void heapify(Collection<Integer> inputs, boolean isMinHeap) throws Exception {
        Heap<Integer> heap = new Heap<>(isMinHeap, inputs);

        ArrayList<Integer> innerData = getInnerData(heap);

        assertEquals(inputs.size(), innerData.size());

        validateInnerData(innerData, isMinHeap);
    }

    void heapSort(ArrayList<Integer> expected, ArrayList<Integer> mockHeap, boolean isMinHeap) throws Exception {
        Heap<Integer> heap = new Heap<>(isMinHeap);
        ArrayList<Integer> initialHeap = new ArrayList<>(mockHeap);

        mockInnerData(heap, mockHeap);

        ArrayList<Integer> sorted = heap.sort();

        assertEquals(expected.size(), sorted.size());
        for (int i = 0; i < expected.size(); ++i)
            assertEquals(expected.get(i), sorted.get(i));

        // Verify that all data is still within the heap
        ArrayList<Integer> innerData = getInnerData(heap);

        assertEquals(initialHeap.size(), innerData.size());
        for (int i = 0; i < initialHeap.size(); ++i)
            assertEquals(initialHeap.get(i), innerData.get(i));
    }

    <T extends Comparable<? super T>> ArrayList<T> getInnerData(Heap<T> heap) throws Exception {
        Field dataField = heap.getClass().getDeclaredField("data");
        dataField.setAccessible(true);
        return (ArrayList<T>) dataField.get(heap);
    }

    <T extends Comparable<? super T>> void mockInnerData(Heap<T> heap, ArrayList<T> mock) throws Exception {
        Field dataField = heap.getClass().getDeclaredField("data");
        dataField.setAccessible(true);
        dataField.set(heap, mock);
    }

    void validateInnerData(ArrayList<Integer> innerData, boolean isMinHeap) {
        if (innerData.size() != 7)
            throw new UnsupportedOperationException("validateinnerData only supports size of 7");

        // First level
        assertTrue(compare(innerData.get(0), innerData.get(1), isMinHeap));
        assertTrue(compare(innerData.get(0), innerData.get(2), isMinHeap));

        // Second level
        assertTrue(compare(innerData.get(1), innerData.get(3), isMinHeap));
        assertTrue(compare(innerData.get(1), innerData.get(4), isMinHeap));

        assertTrue(compare(innerData.get(2), innerData.get(5), isMinHeap));
        assertTrue(compare(innerData.get(2), innerData.get(6), isMinHeap));

    }

    boolean compare(Integer parent, Integer child, boolean isMinHeap){
        return isMinHeap ? parent <= child : parent >= child;
    }
}

class MockValue implements Comparable<MockValue> {
    private int barometerCounter = 0;
    private final Integer value;

    public MockValue(Integer value) {
        this.value = value;
    }

    public int getBarometerCounter() {
        return barometerCounter;
    }

    public Integer getValue() {
        return value;
    }

    @Override
    public int compareTo(MockValue o) {
        ++barometerCounter;
        return value.compareTo(o.value);
    }
}