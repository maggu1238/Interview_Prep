import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class SlidingLog {
    private final int requestLimit; // Maximum number of requests allowed in the sliding window
    private final int windowSizeInSeconds; // The duration of the sliding window in seconds
    private final Map<String, LinkedList<Long>> customerLogs; // Stores logs of timestamps for each customer

    public SlidingLog(int requestLimit, int windowSizeInSeconds) {
        this.requestLimit = requestLimit;
        this.windowSizeInSeconds = windowSizeInSeconds;
        this.customerLogs = new HashMap<>();
    }

    // Method to check if the customer can make a request
    public synchronized boolean isAllowed(String customerId) {
        long currentTime = System.currentTimeMillis();

        // Retrieve the request log for the customer, or initialize it if the customer doesn't exist yet
        LinkedList<Long> log = customerLogs.getOrDefault(customerId, new LinkedList<>());

        // Remove requests from the log that are older than the sliding window (windowSizeInSeconds)
        long windowStartTime = currentTime - windowSizeInSeconds * 1000;
        while (!log.isEmpty() && log.peek() < windowStartTime) {
            log.poll(); // Remove old requests
        }

        // Check if the customer has made fewer than the allowed number of requests in the current window
        if (log.size() < requestLimit) {
            log.add(currentTime); // Allow the request and add the timestamp to the log
            customerLogs.put(customerId, log); // Update the customer's log
            return true; // Allow the request
        } else {
            return false; // Reject the request if the limit is exceeded
        }
    }

    // Method to get the number of requests a customer has made in the last window period
    public synchronized int getRequestCount(String customerId) {
        long currentTime = System.currentTimeMillis();

        // Retrieve the request log for the customer
        LinkedList<Long> log = customerLogs.getOrDefault(customerId, new LinkedList<>());

        // Remove old requests that are no longer in the sliding window
        long windowStartTime = currentTime - windowSizeInSeconds * 1000;
        while (!log.isEmpty() && log.peek() < windowStartTime) {
            log.poll(); // Remove old requests
        }

        return log.size(); // Return the number of requests within the current window
    }
}
