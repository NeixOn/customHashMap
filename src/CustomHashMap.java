public class CustomHashMap<K, V> {
    private static final int DEFAULT_CAPACITY = 16;
    private static final float DEFAULT_LOAD_FACTOR = 0.75f;

    private Node<K, V>[] table;
    private int size;
    private final float loadFactor;

    static class Node<K, V> {
        final K key;
        V value;
        Node<K, V> next;
        final int hash;

        Node(int hash, K key, V value, Node<K, V> next) {
            this.hash = hash;
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }

    public CustomHashMap() {
        this(DEFAULT_CAPACITY, DEFAULT_LOAD_FACTOR);
    }

    @SuppressWarnings("unchecked")
    public CustomHashMap(int capacity, float loadFactor) {
        this.table = (Node<K, V>[]) new Node[capacity];
        this.loadFactor = loadFactor;
        this.size = 0;
    }

    public V put(K key, V value) {
        return putVal(hash(key), key, value);
    }

    public V get(K key) {
        Node<K, V> node = getNode(hash(key), key);
        return node == null ? null : node.value;
    }

    public V remove(K key) {
        Node<K, V> node = removeNode(hash(key), key);
        return node == null ? null : node.value;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }


    private int hash(K key) {
        return key == null ? 0 : key.hashCode() & 0x7fffffff;
    }

    private V putVal(int hash, K key, V value) {
        int index = (table.length - 1) & hash;
        Node<K, V> first = table[index];

        for (Node<K, V> node = first; node != null; node = node.next) {
            if (node.hash == hash &&
                    (node.key == key || (key != null && key.equals(node.key)))) {
                V oldValue = node.value;
                node.value = value;
                return oldValue;
            }
        }

        table[index] = new Node<>(hash, key, value, first);
        size++;

        if (size > table.length * loadFactor) {
            resize();
        }

        return null;
    }

    private Node<K, V> getNode(int hash, K key) {
        int index = (table.length - 1) & hash;
        Node<K, V> node = table[index];

        while (node != null) {
            if (node.hash == hash &&
                    (node.key == key || (key != null && key.equals(node.key)))) {
                return node;
            }
            node = node.next;
        }

        return null;
    }

    private Node<K, V> removeNode(int hash, K key) {
        int index = (table.length - 1) & hash;
        Node<K, V> node = table[index];
        Node<K, V> prev = null;

        while (node != null) {
            if (node.hash == hash &&
                    (node.key == key || (key != null && key.equals(node.key)))) {

                if (prev == null) {
                    table[index] = node.next;
                } else {
                    prev.next = node.next;
                }
                size--;
                return node;
            }
            prev = node;
            node = node.next;
        }

        return null;
    }

    @SuppressWarnings("unchecked")
    private void resize() {
        Node<K, V>[] oldTable = table;
        int newCapacity = table.length * 2;
        table = (Node<K, V>[]) new Node[newCapacity];

        for (Node<K, V> head : oldTable) {
            while (head != null) {
                Node<K, V> next = head.next;
                int newIndex = (newCapacity - 1) & head.hash;
                head.next = table[newIndex];
                table[newIndex] = head;
                head = next;
            }
        }
    }
}