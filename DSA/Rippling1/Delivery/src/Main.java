import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

// Driver entity
class Driver {
    private int driverId;
    private float hourlyRate;

    public Driver(int driverId, float hourlyRate) {
        this.driverId = driverId;
        this.hourlyRate = hourlyRate;
    }

    public int getDriverId() {
        return driverId;
    }

    public float getHourlyRate() {
        return hourlyRate;
    }

    public void setHourlyRate(float hourlyRate) {
        this.hourlyRate = hourlyRate;
    }
}

// Order entity
class Order {
    private int driverId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public Order(int driverId, LocalDateTime startTime, LocalDateTime endTime) {
        this.driverId = driverId;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public int getDriverId() {
        return driverId;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }
}

// Repository for drivers
interface DriverRepository {
    void addDriver(Driver driver);
    Driver getDriver(int driverId);
}

// Repository for orders
interface OrderRepository {
    void addOrder(Order order);
    Iterable<Order> getOrders();
}

// Implementation of DriverRepository
class InMemoryDriverRepository implements DriverRepository {
    private Map<Integer, Driver> drivers = new HashMap<>();

    @Override
    public void addDriver(Driver driver) {
        drivers.put(driver.getDriverId(), driver);
    }

    @Override
    public Driver getDriver(int driverId) {
        return drivers.get(driverId);
    }
}

// Implementation of OrderRepository
class InMemoryOrderRepository implements OrderRepository {
    private Map<Integer, Order> orders = new HashMap<>();
    private int orderIdCounter = 1;

    @Override
    public void addOrder(Order order) {
        orders.put(orderIdCounter++, order);
    }

    @Override
    public Iterable<Order> getOrders() {
        return orders.values();
    }
}

// Service for calculating delivery costs
class DeliveryCostCalculator {
    private DriverRepository driverRepository;
    private OrderRepository orderRepository;

    public DeliveryCostCalculator(DriverRepository driverRepository, OrderRepository orderRepository) {
        this.driverRepository = driverRepository;
        this.orderRepository = orderRepository;
    }

    public float calculateTotalCost() {
        float totalCost = 0;
        for (Order order : orderRepository.getOrders()) {
            Driver driver = driverRepository.getDriver(order.getDriverId());
            if (driver != null) {
                long minutesWorked = Duration.between(order.getStartTime(), order.getEndTime()).toMinutes();
                float hoursWorked = minutesWorked / 60.0f;
                totalCost += driver.getHourlyRate() * hoursWorked;
            }
        }
        return totalCost;
    }
}

// Main application class
public class Main {
    public static void main(String[] args) {
        DriverRepository driverRepo = new InMemoryDriverRepository();
        OrderRepository orderRepo = new InMemoryOrderRepository();
        DeliveryCostCalculator costCalculator = new DeliveryCostCalculator(driverRepo, orderRepo);

        Driver driver1 = new Driver(1, 20.0f);
        Driver driver2 = new Driver(2, 25.0f);

        driverRepo.addDriver(driver1);
        driverRepo.addDriver(driver2);

        Order order1 = new Order(1, LocalDateTime.of(2025, 3, 9, 10, 0), LocalDateTime.of(2025, 3, 9, 12, 0));
        Order order2 = new Order(2, LocalDateTime.of(2025, 3, 9, 14, 0), LocalDateTime.of(2025, 3, 9, 16, 30));

        orderRepo.addOrder(order1);
        orderRepo.addOrder(order2);

        System.out.println("Total Cost: $" + costCalculator.calculateTotalCost());
    }
}
