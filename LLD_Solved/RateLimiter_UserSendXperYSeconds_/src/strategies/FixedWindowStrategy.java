package strategies;
import java.util.HashMap;
import java.util.Map;

public class FixedWindowStrategy implements RateLimiterStrategy {
    private final int maxRequests;
    private final int windowSizeInSeconds;
    private final Map<String, CustomerWindow> customerMap = new HashMap<>();

    public FixedWindowStrategy(int maxRequests, int windowSizeInSeconds) {
        this.maxRequests = maxRequests;
        this.windowSizeInSeconds = windowSizeInSeconds;
    }

    @Override
    public synchronized boolean isAllowed(String customerId) {
        long currentWindow = System.currentTimeMillis() / (windowSizeInSeconds * 1000);
        CustomerWindow window = customerMap.getOrDefault(customerId, new CustomerWindow(currentWindow, 0));

        if (window.windowId != currentWindow) {
            window.windowId = currentWindow;
            window.requestCount = 0;
        }

        if (window.requestCount < maxRequests) {
            window.requestCount++;
            customerMap.put(customerId, window);
            return true;
        }

        return false;
    }

    private static class CustomerWindow {
        long windowId;
        int requestCount;

        CustomerWindow(long windowId, int requestCount) {
            this.windowId = windowId;
            this.requestCount = requestCount;
        }
    }
}
