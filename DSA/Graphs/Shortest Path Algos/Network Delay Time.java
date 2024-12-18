/*You are given a network of n nodes, labeled from 1 to n. You are also given times, a list of travel times as directed edges times[i] = (ui, vi, wi), where ui is the source node, vi is the target node, and wi is the time it takes for a signal to travel from source to target.

We will send a signal from a given node k. Return the minimum time it takes for all the n nodes to receive the signal. If it is impossible for all the n nodes to receive the signal, return -1.*/
import java.util.*;

class Solution {
    // Custom Pair class to hold distance and node information
    static class Pair {
        int dist;
        int node;

        // Constructor for Pair
        public Pair(int dist, int node) {
            this.dist = dist;
            this.node = node;
        }
    }

    // Comparator to sort Pair based on the dist (distance)
    static class PairComparator implements Comparator<Pair> {
        @Override
        public int compare(Pair p1, Pair p2) {
            return Integer.compare(p1.dist, p2.dist);
        }
    }

    public int networkDelayTime(int[][] times, int n, int k) {
        // Adjacency list to store graph
        Map<Integer, List<int[]>> adj = new HashMap<>();
        
        // Build the adjacency list
        for (int i = 0; i < times.length; i++) {
            int src = times[i][0];
            int dest = times[i][1];
            int weight = times[i][2];
            adj.putIfAbsent(src, new ArrayList<>());
            adj.get(src).add(new int[]{dest, weight});
        }

        // Priority queue for Dijkstra's algorithm, using the custom comparator
        PriorityQueue<Pair> pq = new PriorityQueue<>(new PairComparator());
        int[] minTime = new int[n + 1];
        
        // Initialize all times to a large value (representing infinity)
        Arrays.fill(minTime, Integer.MAX_VALUE);
        
        // Start from the node k
        pq.offer(new Pair(0, k));
        minTime[k] = 0;
        
        // Number of nodes remaining to process
        int nodesLeft = n;

        // Run Dijkstra's algorithm
        while (!pq.isEmpty()) {
            Pair current = pq.poll();
            int dist = current.dist;
            int node = current.node;

            // If the node is already visited with a shorter time, skip it
            if (dist > minTime[node]) continue;

            for (int[] neighbor : adj.getOrDefault(node, new ArrayList<>())) {
                int child = neighbor[0];
                int weight = neighbor[1];
                if (minTime[child] > dist + weight) {
                    if (minTime[child] == Integer.MAX_VALUE) nodesLeft--;
                    minTime[child] = dist + weight;
                    pq.offer(new Pair(minTime[child], child));
                }
            }
        }

        // If there are still nodes that were not reached, return -1
        if (nodesLeft > 0) return -1;

        // Find the maximum time to reach any node
        int res = Integer.MIN_VALUE;
        for (int i = 1; i <= n; i++) {
            res = Math.max(res, minTime[i]);
        }
        return res;
    }

    // Main method for testing
    public static void main(String[] args) {
        Solution solution = new Solution();
        int[][] times = {
            {2, 1, 1},
            {2, 3, 1},
            {3, 4, 1}
        };
        int n = 4;
        int k = 2;

        int result = solution.networkDelayTime(times, n, k);
        System.out.println(result); // Output should be 2
    }
}
