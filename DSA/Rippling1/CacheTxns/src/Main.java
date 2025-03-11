import java.util.*;

class Cache {
    private Map<String, String> cache = new HashMap<>();
    private Deque<Map<String, String>> transactionStack = new ArrayDeque<>();

    // Start a new transaction
    public void begin() {
        transactionStack.push(new HashMap<>());
    }

    // Commit the latest transaction (merge changes into outer transaction if nested)
    public void commit() {
        if (transactionStack.isEmpty()) return;

        Map<String, String> lastTxn = transactionStack.pop();
        if (!transactionStack.isEmpty()) {
            transactionStack.peek().putAll(lastTxn);
        }
    }

    // Rollback the latest transaction (restore only modified keys)
    public void rollback() {
        if (transactionStack.isEmpty()) return;

        Map<String, String> lastTxn = transactionStack.pop();
        for (Map.Entry<String, String> entry : lastTxn.entrySet()) {
            if (entry.getValue() == null) {
                cache.remove(entry.getKey());  // Key was newly added, so remove it
            } else {
                cache.put(entry.getKey(), entry.getValue());  // Restore old value
            }
        }
    }

    // Add or update a key-value pair
    public void add(String key, String value) {
        if (!transactionStack.isEmpty() && !transactionStack.peek().containsKey(key)) {
            transactionStack.peek().put(key, cache.containsKey(key) ? cache.get(key) : null);
        }
        cache.put(key, value);
    }

    // Get a value by key
    public String get(String key) {
        return cache.getOrDefault(key, "Key not found");
    }

    // Remove a key from the cache
    public void remove(String key) {
        if (!transactionStack.isEmpty() && !transactionStack.peek().containsKey(key)) {
            transactionStack.peek().put(key, cache.containsKey(key) ? cache.get(key) : null);
        }
        cache.remove(key);
    }

    // Display the current cache state
    public void display() {
        cache.forEach((key, value) -> System.out.println(key + " : " + value));
    }

    public static void main(String[] args) {
        Cache myCache = new Cache();
        myCache.add("name", "Shubham");
        myCache.add("company", "Microsoft");

        System.out.println("Before transaction:");
        myCache.display();

        myCache.begin();  // Transaction 1 starts
        myCache.add("role", "Engineer");  // New key
        myCache.remove("company");        // Key already exists

        System.out.println("After Transaction 1 changes:");
        myCache.display();

        myCache.begin();  // Nested Transaction 2 starts
        myCache.add("location", "India");  // New key
        myCache.remove("role");            // Remove key added in Transaction 1

        System.out.println("After Nested Transaction 2 changes:");
        myCache.display();

        myCache.rollback();  // Rolls back only Nested Transaction 2

        System.out.println("After rolling back Nested Transaction 2:");
        myCache.display();

        myCache.commit();  // Commits Transaction 1

        System.out.println("After committing Transaction 1:");
        myCache.display();
    }
}
