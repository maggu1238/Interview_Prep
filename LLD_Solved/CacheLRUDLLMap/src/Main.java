import java.util.*;

class DoublyLinkedNode {
    String key;
    String value;
    DoublyLinkedNode prev;
    DoublyLinkedNode next;

    DoublyLinkedNode(String key, String value) {
        this.key = key;
        this.value = value;
    }
}

interface EvictionStrategy {
    void keyAccessed(DoublyLinkedNode node);
    DoublyLinkedNode evictKey();
}

class LRUEvictionStrategy implements EvictionStrategy {
    private final DoublyLinkedNode head;
    private final DoublyLinkedNode tail;

    public LRUEvictionStrategy() {
        head = new DoublyLinkedNode(null, null); // Dummy head
        tail = new DoublyLinkedNode(null, null); // Dummy tail
        head.next = tail;
        tail.prev = head;
    }

    @Override
    public void keyAccessed(DoublyLinkedNode node) {
        // Only remove the node if it is already part of the list
        if (node.prev != null && node.next != null) {
            removeNode(node);
        }
        addToTail(node);
    }

    @Override
    public DoublyLinkedNode evictKey() {
        if (head.next == tail) {
            throw new IllegalStateException("No keys to evict!");
        }
        DoublyLinkedNode node = head.next; // The least recently used node
        removeNode(node);
        return node;
    }

    private void addToTail(DoublyLinkedNode node) {
        DoublyLinkedNode prev = tail.prev;
        prev.next = node;
        node.prev = prev;
        node.next = tail;
        tail.prev = node;
    }

    private void removeNode(DoublyLinkedNode node) {
        DoublyLinkedNode prev = node.prev;
        DoublyLinkedNode next = node.next;
        prev.next = next;
        next.prev = prev;
    }

    public DoublyLinkedNode getHead(){
        return head;
    }

    public DoublyLinkedNode getTail(){
        return tail;
    }
}

class FIFOEvictionStrategy implements EvictionStrategy {
    private final DoublyLinkedNode head;
    private final DoublyLinkedNode tail;

    public FIFOEvictionStrategy() {
        head = new DoublyLinkedNode(null, null); // Dummy head
        tail = new DoublyLinkedNode(null, null); // Dummy tail
        head.next = tail;
        tail.prev = head;
    }

    @Override
    public void keyAccessed(DoublyLinkedNode node) {
        // For FIFO, we only care about adding new nodes, not reordering
        if (node.prev == null && node.next == null) { // If node is not already in the list
            addToTail(node);
        }
    }

    @Override
    public DoublyLinkedNode evictKey() {
        if (head.next == tail) {
            throw new IllegalStateException("No keys to evict!");
        }
        DoublyLinkedNode node = head.next; // The first (oldest) node
        removeNode(node);
        return node;
    }

    private void addToTail(DoublyLinkedNode node) {
        DoublyLinkedNode prev = tail.prev;
        prev.next = node;
        node.prev = prev;
        node.next = tail;
        tail.prev = node;
    }

    private void removeNode(DoublyLinkedNode node) {
        DoublyLinkedNode prev = node.prev;
        DoublyLinkedNode next = node.next;
        prev.next = next;
        next.prev = prev;
    }

    public DoublyLinkedNode getHead() {
        return head;
    }

    public DoublyLinkedNode getTail() {
        return tail;
    }
}



class Cache {
    private final Map<String, DoublyLinkedNode> map;
    private final EvictionStrategy evictionStrategy;
    private final int capacity;

    public Cache(int capacity, EvictionStrategy evictionStrategy) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be greater than 0");
        }
        this.capacity = capacity;
        this.evictionStrategy = evictionStrategy;
        this.map = new HashMap<>();
    }

    public String get(String key) {
        if (!map.containsKey(key)) {
            return null;
        }
        DoublyLinkedNode node = map.get(key);
        evictionStrategy.keyAccessed(node);
        return node.value;
    }

    public void put(String key, String value) {
        if (map.containsKey(key)) {
            DoublyLinkedNode node = map.get(key);
            node.value = value;
            evictionStrategy.keyAccessed(node);
        } else {
            if (map.size() >= capacity) {
                DoublyLinkedNode evictedNode = evictionStrategy.evictKey();
                map.remove(evictedNode.key);
            }
            DoublyLinkedNode newNode = new DoublyLinkedNode(key, value);
            map.put(key, newNode);
            evictionStrategy.keyAccessed(newNode);
        }
    }

    public void displayCache() {
        System.out.print("Cache Contents: ");
        DoublyLinkedNode current = ((LRUEvictionStrategy) evictionStrategy).getHead().next;
        while (current != ((LRUEvictionStrategy) evictionStrategy).getTail()) {
            System.out.print("{" + current.key + "=" + current.value + "} ");
            current = current.next;
        }
        System.out.println();
    }
}


//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        // Initialize LRU Cache with capacity 3
        EvictionStrategy lruStrategy = new LRUEvictionStrategy();
        Cache cache = new Cache(3, lruStrategy);

        // Test LRU behavior
        cache.put("a", "value1");
        cache.put("b", "value2");
        cache.put("c", "value3");
        cache.displayCache(); // Output: {a=value1} {b=value2} {c=value3}

        // Access 'a' to make it recently used
        cache.get("a");
        cache.put("d", "value4"); // 'b' should be evicted
        cache.displayCache(); // Output: {c=value3} {a=value1} {d=value4}

        // Add another key, 'e', to evict 'c'
        cache.put("e", "value5");
        cache.displayCache(); // Output: {a=value1} {d=value4} {e=value5}
    }
}