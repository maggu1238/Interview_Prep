package strategies;

import java.util.HashMap;
import java.util.Map;

public class SlidingWindowCounterStrategy implements RateLimiterStrategy {
    private final int maxRequests; // Maximum requests allowed within the window.
    private final int windowSizeInSeconds; // Duration of the sliding window in seconds.
    private final Map<String, CustomerBucket> customerMap = new HashMap<>(); // Map to track counters for each customer.

    // Constructor to initialize maxRequests and windowSizeInSeconds.
    public SlidingWindowCounterStrategy(int maxRequests, int windowSizeInSeconds) {
        this.maxRequests = maxRequests;
        this.windowSizeInSeconds = windowSizeInSeconds;
    }

    @Override
    public synchronized boolean isAllowed(String customerId) {
        long currentTime = System.currentTimeMillis(); // Get the current time in milliseconds.

        // Retrieve or initialize the counter bucket for the customer.
        CustomerBucket bucket = customerMap.getOrDefault(customerId, new CustomerBucket(0, currentTime));

        // Calculate the time elapsed since the last request.
        long elapsedTime = (currentTime - bucket.lastRequestTime) / 1000; // Convert milliseconds to seconds.

        // Calculate refill rate based on time elapsed and maximum requests allowed.
        double refillRate = (double) maxRequests / windowSizeInSeconds;

        // Refill tokens based on the elapsed time.
        bucket.tokens = Math.min(maxRequests, bucket.tokens + elapsedTime * refillRate);

        // Update the last request time to the current time.
        bucket.lastRequestTime = currentTime;

        // Check if there are enough tokens for the request.
        if (bucket.tokens >= 1) {
            bucket.tokens--; // Deduct a token for the request.
            customerMap.put(customerId, bucket); // Update the customer's bucket in the map.
            return true; // Allow the request.
        }

        // If no tokens are available, reject the request.
        return false;
    }

    // Inner class to represent the state of a customer's sliding window.
    private static class CustomerBucket {
        double tokens; // Current token count in the bucket.
        long lastRequestTime; // Timestamp of the last processed request.

        // Constructor to initialize tokens and lastRequestTime.
        CustomerBucket(double tokens, long lastRequestTime) {
            this.tokens = tokens;
            this.lastRequestTime = lastRequestTime;
        }
    }
}
