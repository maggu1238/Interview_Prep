import java.util.*;
import java.util.Map;

interface EvictionStrategy {
    void keyAccessed(String key);
    String evictKey();
}


class LRUEvictionStrategy implements EvictionStrategy {
    private final LinkedHashMap<String, Boolean> order = new LinkedHashMap<>();

    @Override
    public void keyAccessed(String key) {
        if (order.containsKey(key)) {
            order.remove(key);
        }
        order.put(key, true);
    }

    @Override
    public String evictKey() {
        if (order.isEmpty()) {
            throw new IllegalStateException("No keys to evict!");
        }
        return order.keySet().iterator().next();
    }
}



class Cache {
    private final Map<String, String> cache = new HashMap<>();
    private final EvictionStrategy evictionStrategy;
    private final int capacity;

    public Cache(int capacity, EvictionStrategy evictionStrategy) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be greater than 0");
        }
        this.capacity = capacity;
        this.evictionStrategy = evictionStrategy;
    }

    public String get(String key) {
        if (!cache.containsKey(key)) {
            return null;
        }
        evictionStrategy.keyAccessed(key);
        return cache.get(key);
    }

    public void put(String key, String value) {
        if (cache.size() >= capacity && !cache.containsKey(key)) {
            String evictedKey = evictionStrategy.evictKey();
            cache.remove(evictedKey);
        }
        cache.put(key, value);
        evictionStrategy.keyAccessed(key);
    }

    public void displayCache() {
        System.out.println("Cache Contents: " + cache);
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
        cache.displayCache(); // Output: {a=value1, b=value2, c=value3}

        // Access 'a' to make it recently used
        cache.get("a");
        cache.put("d", "value4"); // 'b' should be evicted
        cache.displayCache(); // Output: {a=value1, c=value3, d=value4}
    }
}