//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
// Online Java Compiler
// Use this editor to write, compile and run your Java code online
import java.util.*;
interface RateLimiterStrategy{
    boolean isAllowed(String customerId);
}

class FixedWindowStrategy implements  RateLimiterStrategy{

    private Integer maxWindowRequests;
    private Integer maxWindowSize;

    private Map<String, CustomerWindow> CustomerWindowMap;

    public FixedWindowStrategy(Integer maxWindowRequests, Integer maxWindowSize){
        this.maxWindowRequests = maxWindowRequests;
        this.maxWindowSize = maxWindowSize;
        CustomerWindowMap = new HashMap<>();
    }

    @Override
    public boolean isAllowed(String customerId){
            long windowId = System.currentTimeMillis() / (maxWindowSize * 1000);

            CustomerWindow window = CustomerWindowMap.getOrDefault(customerId, new CustomerWindow(windowId));

            if(window.windowId != windowId){
                window.windowId = windowId;
                window.windowReqs = 0;
                CustomerWindowMap.put(customerId, window);
            }

            if(window.windowReqs < maxWindowRequests){
                window.windowReqs++;
                CustomerWindowMap.put(customerId, window);
                return true;
            }

            return false;
    }

    private class CustomerWindow{
        long windowId;
        Integer windowReqs;

        private  CustomerWindow(long windowId){
            this.windowId = windowId;
        }
    }
}

class SlidingLogStrategy implements RateLimiterStrategy{

     private int maxRequests;
     private int maxWindowSize;

     private Map<String, LinkedList<Long>> customerTimeStampMap;

     public SlidingLogStrategy(int maxWindowSize, int maxRequests){
         this.maxRequests = maxRequests;
         this.maxWindowSize = maxWindowSize;
         customerTimeStampMap = new HashMap<>();
     }

     @Override
     public boolean isAllowed(String customerId){
        long currTime = System.currentTimeMillis();

        LinkedList<Long> customerLogs = customerTimeStampMap.get(customerId);

        while(!customerLogs.isEmpty() && currTime - customerLogs.peek() > maxWindowSize * 1000){
            customerLogs.poll();
        }

        if(customerLogs.size() < maxWindowSize){
            customerLogs.add(currTime);
            customerTimeStampMap.put(customerId, customerLogs);
            return true;
        }

        return false;
     }
}

class TokenBucketStrategy implements  RateLimiterStrategy{

    private Map<String, TockenBucket> customerBucketMap;
    private Integer maxTokens;
    private long tokenRefillRate;

    public TokenBucketStrategy(Integer maxTokens, Integer tokenRefillRate){
        this.maxTokens = maxTokens;
        this.tokenRefillRate = tokenRefillRate;
        this.customerBucketMap = new HashMap<>();
    }

    @Override
    public boolean isAllowed(String customerId){

        long currTime = System.currentTimeMillis();
        TockenBucket tockenBucket = customerBucketMap.getOrDefault(customerId, new TockenBucket(maxTokens, currTime));

        long elapsedTime = (currTime - tockenBucket.lastRefillTime)/1000;

        long tokensAfterElapsedTime = tockenBucket.tokens + elapsedTime * tokenRefillRate;

        tockenBucket.tokens = Math.max(maxTokens, tokensAfterElapsedTime);
        tockenBucket.lastRefillTime = currTime;

        if(tockenBucket.tokens > 0){
            tockenBucket.tokens--;
            customerBucketMap.put(customerId, tockenBucket);
            return true;
        }

        return false;
    }

    private class TockenBucket{
        long lastRefillTime;
        long tokens;

        TockenBucket(long maxTokens, long lastRefillTime){
            this.lastRefillTime = lastRefillTime;
            this.tokens = maxTokens;
        }
    }
}

class SlidingWindowStrategy implements RateLimiterStrategy{

    private Integer maxRequests;
    private Integer maxWindowSize;
    private Map<String, CustomerWindow> customerWindowMap;

    public SlidingWindowStrategy(Integer maxRequests, Integer maxWindowSize){
            this.maxRequests = maxRequests;
            this.maxWindowSize = maxWindowSize;
        customerWindowMap = new HashMap<>();
    }

    @Override
    public boolean isAllowed(String customerId){
        long currTime = System.currentTimeMillis();

        CustomerWindow customerWindow = customerWindowMap.getOrDefault(customerId, new CustomerWindow(currTime));

        long elapsedTime = (currTime - customerWindow.startTime)/1000;

        if(elapsedTime > maxWindowSize){
            customerWindow.prevCount = customerWindow.currCount;
            customerWindow.currCount = 1;
            customerWindow.startTime =  currTime;
            customerWindowMap.put(customerId, customerWindow);
            return true;
        }
        else{
            long estimatedCount = (1-(elapsedTime/maxWindowSize))* customerWindow.prevCount + customerWindow.currCount;
            if(estimatedCount > maxRequests){
                return false;
            }
            customerWindow.currCount++;
            customerWindowMap.put(customerId, customerWindow);
        }

        return true;
    }

    private static class CustomerWindow{
        int prevCount;
        int currCount;
        long startTime;

        CustomerWindow(long startTime){
            this.startTime = startTime;
            currCount =1;
            prevCount =0;
        }

    }
}
class RateLimiter{

    private RateLimiterStrategy strategy;

    RateLimiter(RateLimiterStrategy strategy){
        this.strategy = strategy;
    }

    boolean isAllowed(String customerId){
        return strategy.isAllowed(customerId);
    }
}
public class Main {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("Try programiz.pro");

        SlidingWindowStrategy slidingWindowStrategy = new SlidingWindowStrategy(5,10);
        RateLimiter limiter = new RateLimiter(slidingWindowStrategy); // 5 requests per 10 seconds
        String user = "user1";

        // Simulating requests
        for (int i = 0; i < 10; i++) {
            System.out.println("Request " + (i + 1) + ": " + limiter.isAllowed(user));
            Thread.sleep(1500); // 1.5 sec delay
        }
    }
}