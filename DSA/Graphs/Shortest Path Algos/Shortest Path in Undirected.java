/*You are given an Undirected Graph having unit weight of the edges, find the shortest path from src 
to all the vertex and if it is unreachable to reach any vertex, then return -1 for that vertex.*/

import java.util.*;

class Solution {

    public int[] shortestPath(int[][] edges, int N, int M, int src) {
        // Create adjacency list
        List<List<Integer>> adj = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            adj.add(new ArrayList<>());
        }

        // Populate the adjacency list
        for (int i = 0; i < edges.length; i++) {
            int u = edges[i][0];
            int v = edges[i][1];
            adj.get(u).add(v);
            adj.get(v).add(u);
        }

        // Initialize visited array, path length array, and queue
        boolean[] visited = new boolean[N];
        int[] pathLength = new int[N];
        Arrays.fill(pathLength, -1);

        // BFS initialization
        Queue<Integer> q = new LinkedList<>();
        q.add(src);
        visited[src] = true;
        pathLength[src] = 0;

        // BFS algorithm to calculate shortest path lengths
        int length = 0;
        int size = q.size();

        while (!q.isEmpty()) {
            int node = q.poll();
            pathLength[node] = length;
            size--;

            // Traverse the neighbors of the current node
            for (int neighbor : adj.get(node)) {
                if (!visited[neighbor]) {
                    q.add(neighbor);
                    visited[neighbor] = true;
                }
            }

            // If the level is processed, increase the length
            if (size == 0) {
                length++;
                size = q.size();
            }
        }

        return pathLength;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();

        // Example graph with 4 nodes and 4 edges
        int[][] edges = {
            {0, 1},
            {0, 2},
            {1, 2},
            {2, 3}
        };
        int N = 4;  // Number of nodes
        int M = 4;  // Number of edges
        int src = 0; // Starting node

        // Test the shortestPath function
        int[] result = solution.shortestPath(edges, N, M, src);

        // Print the shortest distance to each node from the source node
        System.out.println(Arrays.toString(result));
    }
}