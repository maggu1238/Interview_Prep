import controllers.IVehicleController;

public class CabManagementSystem {
    private static CabManagementSystem instance;

    private Map<String, City> cities;
    private IVehicleController vehicleManager;
    private IBookingManager bookingManager;
    private IInsightsManager insightsManager;

    private CabManagementSystem() {
        cities = new HashMap<>();
        vehicleManager = new VehicleManager();
        bookingManager = new BookingManager();
        insightsManager = new InsightsManager();
    }

    public static CabManagementSystem getInstance() {
        if (instance == null) {
            instance = new CabManagementSystem();
        }
        return instance;
    }

    // Methods for managing cities
    public City addCity(String id, String name) {
        City city = new City(id, name);
        cities.put(id, city);
        return city;
    }

    public City getCity(String id) {
        return cities.get(id);
    }

    // Methods for managing vehicles
    public Vehicle registerVehicle(String id, String cityId, String type, String vehicleType) {
        City city = getCity(cityId);
        if (city == null) throw new IllegalArgumentException("City not found");
        return vehicleManager.registerVehicle(id, city, type, vehicleType);
    }

    // Methods for booking vehicles
    public Vehicle bookVehicle(String cityId) {
        City city = getCity(cityId);
        if (city == null) throw new IllegalArgumentException("City not found");
        return bookingManager.bookVehicle(city);
    }

    // Methods for insights
    public long getIdleTime(Vehicle vehicle, long start, long end) {
        return insightsManager.getIdleTime(vehicle, start, end);
    }

    public Map<City, Integer> getDemandData() {
        return insightsManager.getDemandData(new ArrayList<>(cities.values()));
    }
}
