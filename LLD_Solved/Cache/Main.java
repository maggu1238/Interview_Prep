import policies.EvictionPolicy;
import policies.LRUEvictionPolicy;
import storage.HashMapBasedStorage;
import storage.Storage;



//
// Links:  https://www.youtube.com/watch?v=B7iCXl_KSoM

//

public class Main {
    public static void main(String[] args) {
        // Example storage implementation
        Storage<String, String> storage = new HashMapBasedStorage<>(3);  // Assuming a capacity of 3
        EvictionPolicy<String> evictionPolicy = new LRUEvictionPolicy<>(3);  // LRU eviction policy with capacity 3

        // Create cache with eviction policy and storage
        com.uditagarwal.cache.Cache<String, String> cache = new com.uditagarwal.cache.Cache<>(evictionPolicy, storage);

        // Adding some key-value pairs
        cache.put("key1", "value1");
        cache.put("key2", "value2");
        cache.put("key3", "value3");

        // Access a key to change the eviction order
        cache.get("key1");  // Now "key1" is the most recently used

        // Adding another key, which should cause eviction of the least recently used key ("key2")
        cache.put("key4", "value4");

        // Fetch values to see the current state of cache
        System.out.println(cache.get("key1"));  // Should print "value1"
        System.out.println(cache.get("key2"));  // Should print null (evicted)
        System.out.println(cache.get("key3"));  // Should print "value3"
        System.out.println(cache.get("key4"));  // Should print "value4"
    }
}