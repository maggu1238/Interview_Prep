import strategies.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws InterruptedException {
        // Define X requests and Y seconds
        int X = 5; // Maximum requests (X)
        int Y = 10; // Time window in seconds (Y)

        // Create different rate-limiting strategies
        RateLimiterStrategy fixedWindow = new FixedWindowStrategy(X, Y);
        RateLimiterStrategy slidingWindow = new SlidingWindowCounterStrategy(X, Y);
        RateLimiterStrategy tokenBucket = new TokenBucketStrategy(X, X / Y); // Refill rate calculated as X/Y
        RateLimiterStrategy leakyBucket = new LeakyBucketStrategy(X, X / Y); // Leak rate calculated as X/Y

        // Choose which strategy to test by changing the selectedStrategy
        RateLimiterStrategy selectedStrategy = slidingWindow; // Change to fixedWindow, tokenBucket, or leakyBucket

        // Create the rate limiter with the selected strategy
        RateLimiter rateLimiter = new RateLimiter(selectedStrategy);

        // Simulate multiple requests for the same customer
        String customerId = "customer1";
        System.out.println("Testing Rate Limiter with X = " + X + " requests and Y = " + Y + " seconds.\n");

        // Send multiple requests to observe rate limiting behavior
        for (int i = 0; i < 15; i++) { // 15 requests to test the limit
            boolean allowed = rateLimiter.isAllowed(customerId);
            System.out.println("Request " + (i + 1) + " allowed: " + allowed);

            // Wait 1 second between requests to observe rate limiting over time
            Thread.sleep(1000);
        }
    }
}
