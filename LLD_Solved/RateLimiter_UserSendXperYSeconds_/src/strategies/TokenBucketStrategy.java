package strategies;

import java.util.HashMap;
import java.util.Map;

public class TokenBucketStrategy implements RateLimiterStrategy {
    private final int maxTokens; // Maximum number of tokens the bucket can hold (X requests).
    private final int refillRatePerSecond; // Rate at which tokens are added (refill rate).
    private final Map<String, TokenBucket> customerMap = new HashMap<>(); // Map to track tokens for each customer.

    // Constructor to initialize maxTokens and refillRatePerSecond.
    public TokenBucketStrategy(int maxTokens, int refillRatePerSecond) {
        this.maxTokens = maxTokens;
        this.refillRatePerSecond = refillRatePerSecond;
    }

    @Override
    public synchronized boolean isAllowed(String customerId) {
        long currentTime = System.currentTimeMillis(); // Get the current system time in milliseconds.

        // Retrieve or initialize the token bucket for the customer.
        TokenBucket bucket = customerMap.getOrDefault(customerId, new TokenBucket(maxTokens, currentTime));

        // Calculate the time elapsed since the last refill.
        long elapsedTime = (currentTime - bucket.lastRefillTime) / 1000; // Convert milliseconds to seconds.

        // Refill tokens based on the elapsed time and refill rate.
        bucket.tokens = Math.min(maxTokens, bucket.tokens + elapsedTime * refillRatePerSecond);

        // Update the last refill time to the current time.
        bucket.lastRefillTime = currentTime;

        // Check if the bucket has enough tokens for the request.
        if (bucket.tokens > 0) {
            bucket.tokens--; // Deduct a token for the request.
            customerMap.put(customerId, bucket); // Update the customer's bucket in the map.
            return true; // Allow the request.
        }

        // If no tokens are available, reject the request.
        return false;
    }

    // Inner class to represent the state of a customer's token bucket.
    private static class TokenBucket {
        long tokens; // Current number of tokens in the bucket.
        long lastRefillTime; // Timestamp of the last token refill.

        // Constructor to initialize tokens and lastRefillTime.
        TokenBucket(long tokens, long lastRefillTime) {
            this.tokens = tokens;
            this.lastRefillTime = lastRefillTime;
        }
    }
}
