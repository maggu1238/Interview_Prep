package strategies;

import java.util.HashMap;
import java.util.Map;

public class LeakyBucketStrategy implements RateLimiterStrategy {
    private final int capacity; // Maximum capacity of the bucket.
    private final int leakRatePerSecond; // Rate at which requests "leak" from the bucket.
    private final Map<String, LeakyBucket> customerMap = new HashMap<>(); // Map to track buckets for each customer.

    // Constructor to initialize capacity and leakRatePerSecond.
    public LeakyBucketStrategy(int capacity, int leakRatePerSecond) {
        this.capacity = capacity;
        this.leakRatePerSecond = leakRatePerSecond;
    }

    @Override
    public synchronized boolean isAllowed(String customerId) {
        long currentTime = System.currentTimeMillis(); // Get the current time in milliseconds.

        // Retrieve or initialize the leaky bucket for the customer.
        LeakyBucket bucket = customerMap.getOrDefault(customerId, new LeakyBucket(0, currentTime));

        // Calculate the time elapsed since the last request.
        long elapsedTime = (currentTime - bucket.lastLeakTime) / 1000; // Convert milliseconds to seconds.

        // Leak tokens from the bucket based on the elapsed time.
        bucket.tokens = Math.max(0, bucket.tokens - elapsedTime * leakRatePerSecond);

        // Update the last leak time to the current time.
        bucket.lastLeakTime = currentTime;

        // Check if there is space in the bucket for the request.
        if (bucket.tokens < capacity) {
            bucket.tokens++; // Add a token to the bucket for the current request.
            customerMap.put(customerId, bucket); // Update the customer's bucket in the map.
            return true; // Allow the request.
        }

        // If the bucket is full, reject the request.
        return false;
    }

    // Inner class to represent the state of a customer's leaky bucket.
    private static class LeakyBucket {
        long tokens; // Current token count in the bucket.
        long lastLeakTime; // Timestamp of the last token leak.

        // Constructor to initialize tokens and lastLeakTime.
        LeakyBucket(long tokens, long lastLeakTime) {
            this.tokens = tokens;
            this.lastLeakTime = lastLeakTime;
        }
    }
}
