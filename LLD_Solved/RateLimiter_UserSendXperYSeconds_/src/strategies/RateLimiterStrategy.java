package strategies;

public interface RateLimiterStrategy {
    boolean isAllowed(String customerId);
}