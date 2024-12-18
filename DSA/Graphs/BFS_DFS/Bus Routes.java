/*You are given an array routes representing bus routes where routes[i] is a bus route that the ith bus repeats forever.

For example, if routes[0] = [1, 5, 7], this means that the 0th bus travels in the sequence 1 -> 5 -> 7 -> 1 -> 5 -> 7 -> 1 -> ... forever.
You will start at the bus stop source (You are not on any bus initially), and you want to go to the bus stop target. You can travel between bus stops by buses only.

Return the least number of buses you must take to travel from source to target. Return -1 if it is not possible.*/

import java.util.*;

class Solution {
    public int minimumBuses(int[][] routes, int src, int dest) {
        // Map to store which buses pass through each station
        Map<Integer, List<Integer>> adjList = new HashMap<>();
        for (int i = 0; i < routes.length; i++) {
            for (int station : routes[i]) {
                adjList.putIfAbsent(station, new ArrayList<>());
                adjList.get(station).add(i);
            }
        }

        // Queue for BFS: stores the current station and buses taken so far
        Deque<int[]> queue = new ArrayDeque<>();
        queue.offer(new int[]{src, 0});

        // Set to track visited buses
        Set<Integer> visitedBuses = new HashSet<>();

        // BFS
        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int station = current[0];
            int busesTaken = current[1];

            // If we reach the destination, return the number of buses taken
            if (station == dest) {
                return busesTaken;
            }

            // Check the buses passing through this station
            if (adjList.containsKey(station)) {
                for (int bus : adjList.get(station)) {
                    if (!visitedBuses.contains(bus)) {
                        // Add all stations of this bus route to the queue
                        for (int nextStation : routes[bus]) {
                            queue.offer(new int[]{nextStation, busesTaken + 1});
                        }
                        // Mark the bus as visited
                        visitedBuses.add(bus);
                    }
                }
            }
        }

        // If destination is unreachable, return -1
        return -1;
    }
}