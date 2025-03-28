import java.util.*;

class Cache {
    private Map<String, String> cache = new HashMap<>();
    private Deque<Map<String, String>> transactionStack = new ArrayDeque<>();

    // Start a new transaction
    public void begin() {
        transactionStack.push(new HashMap<>());
        System.out.println("BEGIN TRANSACTION");
    }

    // Commit the latest transaction (merge changes into outer transaction if nested)
    public void commit() {
        if (transactionStack.isEmpty()) {
            System.out.println("No active transaction to commit.");
            return;
        }

        Map<String, String> lastTxn = transactionStack.pop();
        if (!transactionStack.isEmpty()) {
            // Merge changes into the previous transaction manually
            Map<String, String> prevTxn = transactionStack.peek();
            for (Map.Entry<String, String> entry : lastTxn.entrySet()) {
                prevTxn.put(entry.getKey(), entry.getValue());
            }
        } else {
            // Apply changes to the main cache
            for (Map.Entry<String, String> entry : lastTxn.entrySet()) {
                if (entry.getValue() == null) {
                    cache.remove(entry.getKey());
                } else {
                    cache.put(entry.getKey(), entry.getValue());
                }
            }
        }

        System.out.println("COMMITTED TRANSACTION");
    }

    //  public void commit() {
    //     if (transactionStack.isEmpty()) return;

    //     Map<String, String> lastTxn = transactionStack.pop();
    //     for (Map.Entry<String, String> entry : lastTxn.entrySet()) {
    //         if (entry.getValue() == null) {
    //             cache.remove(entry.getKey());  // Remove the key from the main cache
    //         } else {
    //             cache.put(entry.getKey(), entry.getValue());  // Apply changes
    //         }
    //     }
    // }

    // Rollback the latest transaction (discard changes)
    public void rollback() {
        if (transactionStack.isEmpty()) {
            System.out.println("No active transaction to rollback.");
            return;
        }
        transactionStack.pop(); // Simply discard the latest transaction
        System.out.println("ROLLED BACK TRANSACTION");
    }

    // Add or update a key-value pair
    public void add(String key, String value) {
        if (!transactionStack.isEmpty()) {
            transactionStack.peek().put(key, value); // Store changes in the transaction
        } else {
            cache.put(key, value); // No active transaction, modify main cache
        }
        System.out.println("SET " + key + " = " + value);
    }

    // Get a value by key
    public String get(String key) {
        if (!transactionStack.isEmpty() && transactionStack.peek().containsKey(key)) {
            return transactionStack.peek().getOrDefault(key, "Key not found");
        }
        return cache.getOrDefault(key, "Key not found");
    }

    // Remove a key from the cache
    public void remove(String key) {
        if (!transactionStack.isEmpty()) {
            transactionStack.peek().put(key, null); // Mark as deleted in transaction
        } else {
            cache.remove(key);
        }
        System.out.println("REMOVED " + key);
    }

    // Display the current cache state
    public void display() {
        Map<String, String> snapshot = new HashMap<>(cache);

        // Apply transaction changes in order
        for (Map<String, String> txn : transactionStack) {
            for (Map.Entry<String, String> entry : txn.entrySet()) {
                if (entry.getValue() == null) {
                    snapshot.remove(entry.getKey()); // Simulate deletion
                } else {
                    snapshot.put(entry.getKey(), entry.getValue()); // Apply transaction changes
                }
            }
        }

        System.out.println("CURRENT CACHE STATE:");
        snapshot.forEach((key, value) -> System.out.println(key + " : " + value));
        System.out.println("---------------------");
    }


    public static void main(String[] args) {
        Cache myCache = new Cache();

        myCache.begin();  // txn1 starts
        myCache.add("name", "Alice");  // txn1: change "name"

        myCache.begin();  // txn2 starts inside txn1
        myCache.add("city", "Seattle");  // txn2: change "city"
        myCache.display();

        myCache.commit();  // txn2 commits (changes merge into txn1)
        myCache.display();

        myCache.rollback();  // txn1 rolls back (removes both "name" & "city")
        myCache.display();
    }
}
