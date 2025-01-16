import strategies.RateLimiterStrategy;

public class RateLimiter {
    private RateLimiterStrategy strategy;

    public RateLimiter(RateLimiterStrategy strategy) {
        this.strategy = strategy;
    }

    public void setStrategy(RateLimiterStrategy strategy) {
        this.strategy = strategy;
    }

    public boolean isAllowed(String customerId) {
        return strategy.isAllowed(customerId);
    }
}