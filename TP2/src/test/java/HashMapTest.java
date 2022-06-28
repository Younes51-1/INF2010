import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.stream.IntStream;

class HashMapTest {
    @Test
    void nodeIteratorConstructor() {
        HashMap.Node<Integer, Integer> node = new HashMap.Node<>(0, 0);
        assertTrue(node.iterator().hasNext());
        assertSame(node, node.iterator().next());
    }

    @Test
    void nodeIteratorNextWithNoNextThrows() {
        HashMap.Node<Integer, Integer> node = new HashMap.Node<>(0, 0);

        Iterator<HashMap.Node<Integer, Integer>> it = node.iterator();
        it.next();

        assertThrows(NoSuchElementException.class, it::next);
    }

    @Test
    void nodeIteratorTraversesEverything() {
        HashMap.Node<Integer, Integer> head = new HashMap.Node<>(0, 0);

        int n = 5;
        HashMap.Node<Integer, Integer> currentNode = head;
        for (int i = 1; i < n; i++) {
            currentNode.next = new HashMap.Node<>(i, i);
            currentNode = currentNode.next;
        }

        Iterator<HashMap.Node<Integer, Integer>> it = head.iterator();
        for (int i = 0; i < n; i++)
            assertEquals(i, it.next().key);

        assertFalse(it.hasNext());
    }

    @Test
    void ctorRefusesInvalidCapacity() {
        HashMap<String, Integer> map = new HashMap<>(-1);

        // Capacity should be set to DEFAULT_CAPACITY in that case
        assertEquals(23, map.capacity());

        map = new HashMap<>(0);
        assertEquals(23, map.capacity());

    }

    @Test
    void ctorCapacityIsPrime() {
        HashMap<String, Integer> map = new HashMap<>(11);
        assertEquals(11, map.capacity());

        map = new HashMap<>(10);
        assertEquals(11, map.capacity());
    }

    @Test
    void getReturnsNullIfNotThere() {
        HashMap<KeyMock, Integer> map = new HashMap<>();

        KeyMock firstKey = new KeyMock(1, true);

        assertNull(map.get(firstKey));
    }

    @Test
    void getReturnsValue() throws Exception {
        HashMap<KeyMock, Integer> map = new HashMap<>(11);

        Integer value = 1;
        KeyMock firstKey = new KeyMock(1, true);

        // Create a fake array of nodes for testing
        HashMap.Node<KeyMock, Integer>[] mockMap = new HashMap.Node[11];
        mockMap[1] = new HashMap.Node<>(firstKey, value);

        // Use reflection to access the size
        Field sizeField = map.getClass().getDeclaredField("size");
        sizeField.setAccessible(true);

        // Since we added an entry, size should now be 1
        sizeField.set(map, 1);

        // Use reflection to access private field
        // Allows to test get() before put() is implemented
        Field mapField = map.getClass().getDeclaredField("map");
        mapField.setAccessible(true);
        mapField.set(map, mockMap);

        assertEquals(value, map.get(firstKey));
    }

    @Test
    void getHandlesCollisions() throws Exception {
        HashMap<KeyMock, Integer> map = new HashMap<>(23);

        int n = 9;

        // Place the first node at position 1 since hashcode is forced at 1
        HashMap.Node<KeyMock, Integer>[] mockMap = new HashMap.Node[23];
        mockMap[1] = new HashMap.Node<>(new KeyMock(0), 0);

        // Place subsequent nodes in linked list of first node
        HashMap.Node<KeyMock, Integer> currentNode = mockMap[1];
        for (int i = 1; i < n; i++) {
            currentNode.next = new HashMap.Node<>(new KeyMock(i), i);
            currentNode = currentNode.next;
        }

        // Use reflection to access the size
        Field sizeField = map.getClass().getDeclaredField("size");
        sizeField.setAccessible(true);

        // Since we added 9 entries, size should now be 9
        sizeField.set(map, 9);

        // Use reflection to access private field
        // Allows to test get() before put() is implemented
        Field mapField = map.getClass().getDeclaredField("map");
        mapField.setAccessible(true);
        mapField.set(map, mockMap);

        for (int i = 0; i < n; i++) {
            assertEquals(i, map.get(new KeyMock(i)));
        }
    }

    @Test
    void putReturnsOldValue() {
        HashMap<String, Integer> map = new HashMap<>();

        Integer oldValue = 1;
        map.put("myKey", oldValue);

        Integer returnedOldValue = map.put("myKey", 2);

        assertEquals(oldValue, returnedOldValue);
    }

    @Test
    void putReturnsNullIfNoOldValue() {
        HashMap<String, Integer> map = new HashMap<>();
        Integer value = map.put("myKey", 1);

        assertNull(value);
    }

    @Test
    void putReplacesValue() throws Exception{
        HashMap<KeyMock, Integer> map = new HashMap<>();

        // Forcing a hash code so we know where to manually look for the data
        KeyMock key = new KeyMock(4, true);

        Integer value = 2;
        Integer oldValueNull = map.put(key, 1);
        Integer oldValue1 = map.put(key, value);

        assertNull(oldValueNull);
        assertEquals(1, oldValue1);

        // Uses reflection to access private fields
        // Allows to test put() without having implemented get()
        Field mapField = map.getClass().getDeclaredField("map");
        mapField.setAccessible(true);
        HashMap.Node<KeyMock, Integer>[] nodeArray = (HashMap.Node<KeyMock, Integer>[]) mapField.get(map);

        assertEquals(value, nodeArray[1].data);
        assertEquals(1, map.size());
    }

    @Test
    void putHandlesCollisions() throws Exception{
        HashMap<KeyMock, Integer> map = new HashMap<>(23);

        int n = 9;

        for (int i = 0; i < n; ++i) {
            map.put(new KeyMock(i), i);
        }

        // Uses reflection to access private fields
        // Allows to test put() without having implemented get()
        Field mapField = map.getClass().getDeclaredField("map");
        mapField.setAccessible(true);
        HashMap.Node<KeyMock, Integer>[] nodeArray = (HashMap.Node<KeyMock, Integer>[]) mapField.get(map);

        // We know values are at position 1 since we forced the hash to 1
        HashMap.Node<KeyMock, Integer> currentNode = nodeArray[1];

        for(int i = 0; i < n && currentNode != null; i++) {
            assertEquals(i, currentNode.key.key);
            currentNode = currentNode.next;
        }
    }

    @Test
    void putIfAbsentReturnsCurrentValueWhenFound() {
        HashMap<String, Integer> map = new HashMap<>();

        Integer currentValue = 1;
        map.putIfAbsent("myKey", currentValue);

        IntStream.range(0, 5).forEach(i -> {
                    assertEquals(currentValue, map.putIfAbsent("myKey", i));
                    assertEquals(1, map.size());
                });
    }

    @Test
    void putIfAbsentReturnsNullWhenAbsent() {
        HashMap<String, Integer> map = new HashMap<>();

        Integer currentValue = 1;
        Integer shouldBeNull = map.putIfAbsent("myKey", currentValue);

        assertEquals(1, map.size());
        assertNull(shouldBeNull);
    }

    @Test
    void putIfAbsentHandlesCollisions() throws Exception{
        HashMap<KeyMock, Integer> map = new HashMap<>(23);

        int n = 9;

        for (int i = 0; i < n; ++i) {
            map.putIfAbsent(new KeyMock(i), i);
            map.putIfAbsent(new KeyMock(i), i+100);
        }

        // Uses reflection to access private fields
        // Allows to test put() without having implemented get()
        Field mapField = map.getClass().getDeclaredField("map");
        mapField.setAccessible(true);
        HashMap.Node<KeyMock, Integer>[] nodeArray = (HashMap.Node<KeyMock, Integer>[]) mapField.get(map);

        // We know values are at position 1 since we forced the hash to 1
        HashMap.Node<KeyMock, Integer> currentNode = nodeArray[1];

        for(int i = 0; i < n && currentNode != null; i++) {
            assertEquals(i, currentNode.key.key);
            currentNode = currentNode.next;
        }
        assertEquals(n, map.size());
    }

    // All tests below assume `get` and `put` are well implemented

    @Test
    void containsKey() {
        HashMap<String, Integer> map = new HashMap<>();

        String key = new String("myKey");
        String keyClone = new String("myKey");

        assertFalse(map.containsKey(key));

        map.put(key, 1);

        assertTrue(map.containsKey(keyClone));
        assertFalse(map.containsKey("unknownKey"));
    }

    @Test
    void containsKeyHandlesCollisions() {
        HashMap<KeyMock, Integer> map = new HashMap<>(23);
        int n = 9;

        for (int i = 0; i < n; ++i)
            map.put(new KeyMock(i), i);

        for (int i = 0; i < n; ++i)
            assertTrue(map.containsKey(new KeyMock(i)));
    }

    @Test
    void containsValue() {
        HashMap<String, String> map = new HashMap<>();

        String value = String.valueOf(123);
        String sameValueAnotherObj = String.valueOf(123);

        assertFalse(map.containsValue(value));

        map.put("myKey", value);

        assertTrue(map.containsValue(sameValueAnotherObj));

        assertFalse(map.containsValue("unknownValue"));
    }

    @Test
    void containsValueHandlesCollisions() {
        HashMap<KeyMock, String> map = new HashMap<>(23);
        int n = 9;

        for (int i = 0; i < n; ++i)
            map.put(new KeyMock(i), String.valueOf(i));

        for (int i = 0; i < n; ++i)
            assertTrue(map.containsValue(String.valueOf(i)));
    }

    @Test
    void removeErasesValue() {
        HashMap<String, Integer> map = new HashMap<>();
        String key = "myKey";

        map.put(key, 1);
        map.remove(key);
        assertNull(map.get(key));
    }

    @Test
    void removeReturnsRemovedValue() {
        HashMap<String, Integer> map = new HashMap<>();

        Integer value = 1;
        String key = "myKey";

        map.put(key, value);
        Integer removedValue = map.remove(key);

        assertEquals(value, removedValue);
    }

    @Test
    void removeReturnsNullIfNotExist() {
        HashMap<String, Integer> map = new HashMap<>();

        Integer removedValue = map.remove("myKey");

        assertNull(removedValue);
    }

    @Test
    void removeHandlesCollisions() {
        HashMap<KeyMock, Integer> map = new HashMap<>(23);
        int n = 9;

        for (int i = 0; i < n; ++i) {
            map.put(new KeyMock(i), i);
        }

        // Did not want tests to have any randomness, so the array is manually created
        // Does not dynamically change with n
        int[] staticRandomOrderNums = {0, 4, 2, 6, 3, 1, 7, 8, 5};

        for (int i = 0; i < n; ++i) {
            Integer removedValue = map.remove(new KeyMock(staticRandomOrderNums[i]));
            assertNotNull(removedValue);
            assertEquals(removedValue, staticRandomOrderNums[i]);
        }
    }

    @Test
    void sizeIncrementsAndDecrements() {
        HashMap<String, Integer> map = new HashMap<>();
        assertTrue(map.isEmpty());

        map.put("myKey", 1);
        assertEquals(1, map.size());

        map.remove("myKey");
        assertTrue(map.isEmpty());
    }

    @Test
    void clear() {
        HashMap<String, Integer> map = new HashMap<>();
        int n = 9;

        for (int i = 0; i < n; ++i) {
            String index = String.valueOf(i);
            map.put("myKey" + index, i);
        }

        map.clear();
        assertTrue(map.isEmpty());
    }

    @Test
    void isEmpty() {
        HashMap<String, Integer> map = new HashMap<>();
        assertTrue(map.isEmpty());

        map.put("myKey", 1);
        assertFalse(map.isEmpty());

        map.remove("myKey");
        assertTrue(map.isEmpty());
    }

    @Test
    void capacityIncreasesWithLoadFactor() {
        HashMap<String, Integer> map = new HashMap<>(11, 0.5f);

        int n = 6;
        for (int i = 0; i < n; ++i) {
            String index = String.valueOf(i);
            map.put("myKey" + index, i);
        }

        // Clear should not put capacity back to its initial value
        map.clear();

        assertEquals(23, map.capacity());
    }

    @Test
    void rehash() {
        HashMap<String, Integer> map = new HashMap<>(10);
        int nextPrimeCapacity = 23;
        int n = 9;

        // Add data to trigger rehash
        for (int i = 0; i < n; ++i) {
            String index = String.valueOf(i);
            map.put("myKey" + index, i);
        }

        // Check we properly added all the data
        assertEquals(n, map.size());

        // Check capacity was increased
        assertEquals(nextPrimeCapacity, map.capacity());

        // Check all previous data is still present
        for (int i = 0; i < n; ++i) {
            String index = String.valueOf(i);
            Integer value = map.get("myKey" + index);
            assertEquals(i, value);
        }
    }

    @Test
    void rehashHandlesCollisions() throws Exception{
        HashMap<KeyMock, Integer> map = new HashMap<>(11);

        // Use reflection to access private field
        Field mapField = map.getClass().getDeclaredField("map");
        mapField.setAccessible(true);

        // Put value and for key hash code to 1
        map.put(new KeyMock(1), 1);

        // We know where the node we juste added is since its hashcode is forced to 1
        HashMap.Node<KeyMock, Integer> node = ((HashMap.Node<KeyMock, Integer>[]) mapField.get(map))[1];

        // Add a node in linked list that should not be there after rehash
        node.next = new HashMap.Node<>(new KeyMock(0, false), 0);

        // Use reflection to access the size
        Field sizeField = map.getClass().getDeclaredField("size");
        sizeField.setAccessible(true);

        // Since we added an entry, size should now be 2
        sizeField.set(map, 2);

        // We shouldn't be able to get node as it is not in the right spot
        assertNull(map.get(new KeyMock(0, false)));

        int n = 9;

        // Add nodes to trigger rehash
        for (int i = 2; i < n; i++)
            map.put(new KeyMock(i, false), i);

        // After rehash, we should be able to find the node
        assertNotNull(map.get(new KeyMock(0, false)));
    }

    // We verify that the get/put/containsKey functions are O(1), we test by adding N items and making sure
    // that the number of the most common operation (barometer) follows a trend that is linear.
    @Test
    void testComplexityWithBarometerOperation() {
        int increaseRate = 10;
        int previousTotalBarometer = 2;
        double totalBarometerRate = 0.0;
        int maxSize = 1000000;
        int totalLoops = 0;

        for (int listSize = increaseRate; listSize < maxSize; listSize *= increaseRate) {
            ++totalLoops;

            HashMap<KeyMock, Integer> map = new HashMap<>(listSize * 2);

            ArrayList<KeyMock> keys = new ArrayList<>(listSize*2);
            for (int i = 0; i < listSize; ++i) keys.add(new KeyMock(i, false));

            for (int i = 0; i < listSize; ++i) map.put(keys.get(i), i);
            for (int i = 0; i < listSize; ++i) assertTrue(map.containsKey(keys.get(i)));
            for (int i = 0; i < listSize; ++i) assertEquals(map.get(keys.get(i)), i);

            // Count the barometer operation for complexity.
            int totalBarometer = 0;
            for (KeyMock key : keys)
                totalBarometer += key.getBarometerCounter();

            totalBarometerRate += (double) totalBarometer / previousTotalBarometer;
            previousTotalBarometer = totalBarometer;
        }

        // The rate should be around the increaseRate because the complexity is O(n).
        assertEquals(increaseRate, totalBarometerRate / totalLoops, 1.0);
    }

    static class KeyMock {
        private int barometerCounter;
        private final Integer key;
        private final boolean forceHashCode;

        public KeyMock(Integer key) {
            this(key, true);
        }

        public KeyMock(Integer key, boolean forceHashCode) {
            this.key = key;
            this.forceHashCode = forceHashCode;
        }

        public int getBarometerCounter() {
            return barometerCounter;
        }

        @Override
        public int hashCode() {
            return forceHashCode ? 1 : key.hashCode();
        }

        @Override
        public boolean equals(Object other) {
            ++barometerCounter;

            if (other instanceof HashMapTest.KeyMock) {
                HashMapTest.KeyMock otherKey = (HashMapTest.KeyMock) other;
                return key.equals(otherKey.key);
            }

            return false;
        }
    }
}
