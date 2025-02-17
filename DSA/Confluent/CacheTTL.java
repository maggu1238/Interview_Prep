import java.util.*;

class RunningAverageTTL {
    private static class Node {
        int key, value;
        long expiryTime;
        Node prev, next;

        Node(int key, int value, long expiryTime) {
            this.key = key;
            this.value = value;
            this.expiryTime = expiryTime;
        }
    }

    private final Map<Integer, Node> map = new HashMap<>();
    private final Node head = new Node(-1, -1, Long.MIN_VALUE); // Dummy head
    private final Node tail = new Node(-1, -1, Long.MAX_VALUE); // Dummy tail
    private int sum = 0, count = 0;

    public RunningAverageTTL() {
        head.next = tail;
        tail.prev = head;
    }

    /** Inserts or updates a key with a TTL */
    public void put(int key, int value, long ttlMillis) {
        long expiryTime = System.currentTimeMillis() + ttlMillis;

        // Remove old entry if it exists
        if (map.containsKey(key)) {
            Node oldNode = map.get(key);
            removeNode(oldNode);
            sum -= oldNode.value;
        } else {
            count++; // New key, increase count
        }

        // Add new entry
        Node newNode = new Node(key, value, expiryTime);
        map.put(key, newNode);
        insertNode(newNode);
        sum += value;
    }

    /** Gets a value if it has not expired */
    public Integer get(int key) {
        cleanup();
        Node node = map.get(key);
        return (node != null && node.expiryTime > System.currentTimeMillis()) ? node.value : null;
    }

    /** Returns the running average of non-expired values */
    public double getAverage() {
        cleanup();
        return count == 0 ? 0.0 : (double) sum / count;
    }

    /** Cleans up expired entries */
    private void cleanup() {
        long now = System.currentTimeMillis();
        while (head.next != tail && head.next.expiryTime <= now) {
            Node expired = head.next;
            removeNode(expired);
            map.remove(expired.key);
            sum -= expired.value;
            count--;
        }
    }

    /** Removes a node from the DLL */
    private void removeNode(Node node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    /** Inserts a node into the DLL (sorted by expiry time) */
    private void insertNode(Node node) {
        Node curr = tail.prev;
        while (curr != head && curr.expiryTime > node.expiryTime) {
            curr = curr.prev;
        }
        node.next = curr.next;
        node.prev = curr;
        curr.next.prev = node;
        curr.next = node;
    }

    public static void main(String[] args) throws InterruptedException {
        RunningAverageTTL avgTTL = new RunningAverageTTL();
        avgTTL.put(1, 10, 3000); // Key 1 -> 10, expires in 3s
        avgTTL.put(2, 20, 5000); // Key 2 -> 20, expires in 5s

        System.out.println(avgTTL.get(1));      // 10
        System.out.println(avgTTL.getAverage()); // 15.0

        Thread.sleep(4000); // Wait 4s

        System.out.println(avgTTL.get(1));      // null (expired)
        System.out.println(avgTTL.get(2));      // 20
        System.out.println(avgTTL.getAverage()); // 20.0

        Thread.sleep(2000); // Wait 2 more seconds (total 6s passed)

        System.out.println(avgTTL.get(2));      // null (expired)
        System.out.println(avgTTL.getAverage()); // 0.0 (all expired)
    }
}
