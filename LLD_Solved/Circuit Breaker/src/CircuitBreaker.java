import java.time.Duration;
import java.time.Instant;

// Enum for Circuit State Types
enum CircuitStateType {
    CLOSED, OPEN, HALF_OPEN
}

// State Factory
class CircuitStateFactory {

    public CircuitStateBehavior getState(CircuitStateType type) {
        switch (type) {
            case CLOSED:
                return new ClosedState();
            case OPEN:
                return new OpenState();
            case HALF_OPEN:
                return new HalfOpenState();
            default:
                throw new IllegalArgumentException("Invalid Circuit State Type");
        }
    }
}

// State Interface
interface CircuitStateBehavior {
    String execute(CircuitBreaker context, RPCService rpcService) throws Exception;
}

// CLOSED State
class ClosedState implements CircuitStateBehavior {
    @Override
    public String execute(CircuitBreaker context, RPCService rpcService) throws Exception {
        context.resetWindowIfNeeded();

        try {
            String response = rpcService.call();
            context.recordSuccess();
            return response;
        } catch (Exception e) {
            context.recordFailure();
            if (context.shouldOpenCircuit()) {
                context.setState(CircuitStateType.OPEN);
            }
            throw e;
        }
    }
}

// OPEN State
class OpenState implements CircuitStateBehavior {
    @Override
    public String execute(CircuitBreaker context, RPCService rpcService) throws Exception {
        if (Duration.between(context.getCircuitOpenTime(), Instant.now()).getSeconds() >= context.getCircuitCloseTimeSec()) {
            context.setState(CircuitStateType.HALF_OPEN);
            return context.getState().execute(context, rpcService);
        } else {
            throw new RuntimeException("Circuit is open");
        }
    }
}

// HALF_OPEN State
class HalfOpenState implements CircuitStateBehavior {
    private boolean testExecuted = false;

    @Override
    public String execute(CircuitBreaker context, RPCService rpcService) throws Exception {
        if (testExecuted) {
            throw new RuntimeException("Circuit is half-open; no more test requests allowed");
        }

        testExecuted = true;
        try {
            String response = rpcService.call();
            context.recordSuccess();
            context.setState(CircuitStateType.CLOSED);
            return response;
        } catch (Exception e) {
            context.recordFailure();
            context.setState(CircuitStateType.OPEN);
            throw e;
        }
    }
}

// CircuitBreaker Class
public class CircuitBreaker {
    private final int timeWindowSec;
    private final double failureRatioThreshold;
    private final int circuitCloseTimeSec;
    private final int minRequests;

    private int successCount = 0;
    private int failureCount = 0;
    private Instant windowStart = Instant.now();
    private Instant circuitOpenTime = null;
    private  CircuitStateFactory circuitStateFactory;

    private CircuitStateBehavior state;

    public CircuitBreaker(int timeWindowSec, double failureRatioThreshold, int circuitCloseTimeSec, int minRequests, CircuitStateFactory circuitStateFactory) {
        this.timeWindowSec = timeWindowSec;
        this.failureRatioThreshold = failureRatioThreshold;
        this.circuitCloseTimeSec = circuitCloseTimeSec;
        this.minRequests = minRequests;
        this.circuitStateFactory = circuitStateFactory;
        this.state = this.getStateOfCircuitBreaker(CircuitStateType.CLOSED);
    }

    private CircuitStateBehavior getStateOfCircuitBreaker(CircuitStateType circuitStateType) {
        return circuitStateFactory.getState(circuitStateType);
    }

    public synchronized String execute(RPCService rpcService) throws Exception {
        return state.execute(this, rpcService);
    }

    // State Management
    public synchronized void setState(CircuitStateType newStateType) {
        this.state = this.getStateOfCircuitBreaker(newStateType);
        if (newStateType == CircuitStateType.OPEN) {
            this.circuitOpenTime = Instant.now();
        }
    }

    public synchronized CircuitStateBehavior getState() {
        return state;
    }

    // Utility Methods
    public synchronized void resetWindowIfNeeded() {
        if (Duration.between(windowStart, Instant.now()).getSeconds() >= timeWindowSec) {
            windowStart = Instant.now();
            successCount = 0;
            failureCount = 0;
        }
    }

    public synchronized void recordSuccess() {
        successCount++;
    }

    public synchronized void recordFailure() {
        failureCount++;
    }

    public synchronized boolean shouldOpenCircuit() {
        int totalRequests = successCount + failureCount;
        if (totalRequests < minRequests) {
            return false;
        }
        double failureRatio = (double) failureCount / totalRequests;
        return failureRatio >= failureRatioThreshold;
    }

    public synchronized Instant getCircuitOpenTime() {
        return circuitOpenTime;
    }

    public int getCircuitCloseTimeSec() {
        return circuitCloseTimeSec;
    }
}

// RPCService Interface
@FunctionalInterface
interface RPCService {
    String call() throws Exception;
}

// Example Usage
class Main {
    public static void main(String[] args) {
        CircuitStateFactory circuitStateFactory =  new CircuitStateFactory();

        CircuitBreaker circuitBreaker = new CircuitBreaker(10, 0.5, 5, 10, circuitStateFactory);

        for (int i = 0; i < 20; i++) {
            try {
                String response = circuitBreaker.execute(() -> {
                    if (Math.random() > 0.7) {
                        throw new RuntimeException("RPC Failure");
                    }
                    return "RPC Success";
                });
                System.out.println("Response: " + response);
            } catch (Exception e) {
                System.out.println("Exception: " + e.getMessage());
            }

            try {
                Thread.sleep(500); // Simulate delay
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
