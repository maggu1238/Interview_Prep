import models.*;

import java.time.LocalTime;
import java.util.*;

class RailwayStation {
    List<Platform> platforms;
    PriorityQueue<Train> waitingQueue;
    Map<String, Train> trainRegistry; // Keep track of all trains by their ID

    public RailwayStation(int platformCount) {
        platforms = new ArrayList<>();
        for (int i = 1; i <= platformCount; i++) {
            platforms.add(new Platform(i));
        }
        waitingQueue = new PriorityQueue<>(Comparator.comparing(train -> train.arrivalTime));
        trainRegistry = new HashMap<>();
    }

    // Allocate a platform to a train
    public boolean allocatePlatform(Train train) {
        for (Platform platform : platforms) {
            if (platform.isAvailable(train.arrivalTime)) {
                platform.allocateTrain(train);
                return true;
            }
        }
        return false; // No platform available
    }

    // Handle waiting queue
    private void processWaitingQueue() {
        Iterator<Train> iterator = waitingQueue.iterator();
        while (iterator.hasNext()) {
            Train train = iterator.next();
            if (allocatePlatform(train)) {
                iterator.remove();
            }
        }
    }

    // Add train to the station
    public void addTrain(Train train) {
        trainRegistry.put(train.trainId, train);
        if (!allocatePlatform(train)) {
            System.out.println("No platform available for Train " + train.trainId + ". Adding to waiting queue.");
            waitingQueue.add(train);
        }
        processWaitingQueue();
    }

    // Update train arrival time
    public void updateTrainArrivalTime(String trainId, LocalTime newArrivalTime) {
        Train train = trainRegistry.get(trainId);
        if (train == null) {
            System.out.println("Train with ID " + trainId + " not found!");
            return;
        }

        System.out.println("Updating arrival time of Train " + trainId + " to " + newArrivalTime);
        train.updateArrivalTime(newArrivalTime);

        // If the train is in the waiting queue, update its position
        if (waitingQueue.remove(train)) {
            waitingQueue.add(train);
        }

        // Reprocess the waiting queue to check for new allocations
        processWaitingQueue();
    }

    // Schedule trains
    public void scheduleTrains(List<Train> trains) {
        trains.sort(Comparator.comparing(train -> train.arrivalTime));
        for (Train train : trains) {
            addTrain(train);
        }
    }
}
