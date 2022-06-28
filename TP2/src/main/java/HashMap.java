import java.util.Iterator;
import java.util.NoSuchElementException;

public class HashMap<KeyType, DataType>  {

    // DEFAULT_CAPACITY has to be a prime number
    private static final int DEFAULT_CAPACITY = 23;
    private static final float DEFAULT_LOAD_FACTOR = 0.5f;
    private static final int CAPACITY_INCREASE_FACTOR = 2;

    private Node<KeyType, DataType>[] map;
    private int size = 0;
    private int capacity;
    private final float loadFactor; // Compression factor

    public HashMap() { this(DEFAULT_CAPACITY, DEFAULT_LOAD_FACTOR); }

    public HashMap(int initialCapacity) {
        this(initialCapacity, DEFAULT_LOAD_FACTOR);
    }

    /** TODO
     * Define capacity, loadFactor and map
     */
    public HashMap(int initialCapacity, float loadFactor) {
        this.loadFactor = Math.abs(loadFactor);
        this.capacity = nextPrime(initialCapacity> 0 ? initialCapacity : DEFAULT_CAPACITY);
        clear();
    }

    /**
     * Finds the index attached to a particular key
     * This is the hashing function ("Fonction de dispersement")
     * @param key Value used to access to a particular instance of a DataType
     * @return Index value where this key should be placed in `map`
     */
    private int hash(KeyType key){
        int keyHash = key.hashCode() % capacity;
        return Math.abs(keyHash);
    }

    /**
     * @return if it should be rehashed
     */
    private boolean needRehash() {
        return size > capacity * loadFactor;
    }

    /**
     * @return Number of elements
     */
    public int size() {
        return size;
    }

    /**
     * @return Current reserved space
     */
    public int capacity(){
        return capacity;
    }

    /**
     * @return if it is empty
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /** TODO
     * Finds the next prime
     * @param number number to start the search to the next prime
     * @return Next closest prime
     */
    private boolean isPrime(int number) {
        if (number == 2 || number == 3)
            return true;
        if (number == 1 || number % 2 == 0)
            return false;
        for (int i = 3; i * i <= number; i += 2 )
            if (number % i == 0)
                return false;
        return true;
    }
    private int nextPrime(int number) {
        if (number % 2 == 0)
            number++;
        while (!isPrime(number))
            number += 2;
        return number;
    }

    /** TODO Worst Case : O(m + n)
     * m = Capacity of the hashmap
     * n = number of elements in the hashmap
     * Increases capacity to the next prime number after capacity * CAPACITY_INCREASE_FACTOR and
     * reassigns all contained values
     */
    private void rehash() {
        Node<KeyType, DataType>[] mapToChange = this.map;
        this.capacity = nextPrime(this.capacity * CAPACITY_INCREASE_FACTOR);
        clear();
        for (var node : mapToChange) {
            while (node != null) {
                put(node.key, node.data);
                node = node.next;
            }
        }
    }

    /** TODO Average Case : O(1)
     * Finds if the key is already assigned
     * @param key Key which we want to know if exists already
     * @return if key is already used
     */
    public boolean containsKey(KeyType key) {
        return get(key) != null;
    }

    /** TODO Worst Case : O(m + n)
     * m = Capacity of the hashmap
     * n = number of elements in the hashmap
     * Finds if the value is already present
     * @param value Value which we want to know if exists already
     * @return if value is already present
     */
    public boolean containsValue(DataType value) {
        for (var node : this.map)
            while (node != null) {
                if (node.data.equals(value))
                    return true;
                node = node.next;
            }
        return false;
    }

    /** TODO Average Case : O(1)
     * Finds the value attached to a key
     * @param key Key which we want to have its value
     * @return DataType instance attached to key (null if not found)
     */
    public DataType get(KeyType key) {
        Node<KeyType, DataType> node = map[hash(key)];
        while (node != null) {
            if (node.key.equals(key))
                return node.data;
            node = node.next;
        }
        return null;
    }

    /** TODO Average Case : O(1) , Worst case : O(n)
     * Assigns a value to a key
     * @param key Key which will have its value assigned or reassigned
     * @return Old DataType instance at key (null if none existed)
     */
    public DataType put(KeyType key, DataType value) {
        DataType oldData = remove(key);
        Node<KeyType, DataType> node = map[hash(key)];
        size++;
        if (node == null) {
            node = new Node<>(key,value);
            map[hash(key)] = node;
        }
        else {
            while (node.next != null)
                node = node.next;
            node.next = new Node<>(key,value);
        }
        if (needRehash())
            rehash();
        return oldData;
    }

    /** TODO Average Case : O(1) , Worst case : O(n)
     * Assigns a value to a key if it's absent
     * @param key Key which will have its value assigned if absent
     * @return Current DataType instance at key (null if absent)
     */
    public DataType putIfAbsent(KeyType key, DataType value) {
        if (!containsKey(key)) {
            put(key, value);
            return null;
        }
        Node<KeyType, DataType> node = map[hash(key)];
        if (node.data == null) {
            put(key, value);
            return null;
        }
        else
            return node.data;
    }

    /** TODO Average Case : O(1)
     * Removes the node attached to a key
     * @param key Key which is contained in the node to remove
     * @return Old DataType instance at key (null if none existed)
     */
    public DataType remove(KeyType key) {
        if (!containsKey(key))
            return null;
        Node<KeyType, DataType> node = map[hash(key)];
        size--;
        if (node.key.equals(key)) {
            DataType oldData = node.data;
            map[hash(key)] = node.next;
            return oldData;
        }
        while (true) {
            if (node.next.key.equals(key)) {
                DataType oldData = node.next.data;
                node.next = node.next.next;
                return oldData;
            }
            node = node.next;
        }
    }

    /** TODO Worst Case : O(1)
     * Removes all nodes
     */
    public void clear() {
        this.map = new Node[capacity];
        this.size = 0;
    }

    static class Node<KeyType, DataType> implements Iterable<Node<KeyType, DataType>> {
        final KeyType key;
        DataType data;
        Node<KeyType, DataType> next; // Pointer to the next node within a Linked List

        Node(KeyType key, DataType data)
        {
            this.key = key;
            this.data = data;
            next = null;
        }

        @Override
        public Iterator<Node<KeyType, DataType>> iterator() {
            return new NodeIterator(this);
        }
        private class NodeIterator implements Iterator<Node<KeyType, DataType>> {
            private Node<KeyType, DataType> curr;

            /** TODO
             * Set curr to the current node
             */
            NodeIterator(Node<KeyType, DataType> node) {
                this.curr = node;
            }

            /** TODO
             * check if `curr` has a next element
             * @return if the current node has a next element
             */
            @Override
            public boolean hasNext() {
                return this.curr != null;
            }

            /** TODO
             * Defines `curr` to next element (null if none)
             * @return Old curr Node
             */
            @Override
            public Node<KeyType, DataType> next() {
                Node<KeyType, DataType> result = this.curr;
                if (!hasNext())
                    throw new NoSuchElementException();
                this.curr = this.curr.next;
                return result;
            }
        }
    }
}