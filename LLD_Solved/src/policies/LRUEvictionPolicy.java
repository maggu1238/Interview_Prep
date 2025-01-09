package policies;

import exceptions.StorageFullException;

import java.util.LinkedHashMap;
import java.util.Map;

public class LRUEvictionPolicy<Key> implements EvictionPolicy<Key> {
    private final Map<Key, Long> accessOrder;
    private final int capacity;

    public LRUEvictionPolicy(int capacity) {
        this.capacity = capacity;
        // true for accessOrder to maintain the order of access (not insertion)
        this.accessOrder = new LinkedHashMap<>(capacity, 0.75f, true);
    }

    @Override
    public void keyAccessed(Key key) {
        // Insert or update the key's access timestamp
        accessOrder.put(key, System.nanoTime());

        // Check if the cache has exceeded the capacity and throw an exception
        if (accessOrder.size() > capacity) {
            throw new StorageFullException("Storage capacity exceeded. No key evicted.");
        }
    }

    @Override
    public Key evictKey() {
        long oldestTimestamp = Long.MAX_VALUE;
        Key oldestKey = null;

        for (Map.Entry<Key, Long> entry : accessOrder.entrySet()) {
            if (entry.getValue() < oldestTimestamp) {
                oldestTimestamp = entry.getValue();
                oldestKey = entry.getKey();
            }
        }

        accessOrder.remove(oldestKey);
        return oldestKey;    }


}