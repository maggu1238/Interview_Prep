/*Given a Directed Acyclic Graph of V vertices from 0 to n-1 and a 2D Integer array(or vector) edges[ ][ ] of length E, where there is a directed edge from edge[i][0] to edge[i][1] with a distance of edge[i][2] for all i.

Find the shortest path from src(0) vertex to all the vertices and if it is impossible to reach any vertex, then return -1 for that vertex.*/

import java.util.*;

class Solution {

    // DFS helper function
    public void dfs(int node, boolean[] visited, int[] minDist, List<List<Pair>> adj) {
        for (int i = 0; i < adj.get(node).size(); i++) {
            int cost = adj.get(node).get(i).second;
            int childNode = adj.get(node).get(i).first;
            
            if (!visited[childNode]) {
                minDist[childNode] = minDist[node] + cost;
                visited[childNode] = true;
            } else if (minDist[childNode] > minDist[node] + cost) {
                minDist[childNode] = minDist[node] + cost;
            } else {
                continue;
            }
            dfs(childNode, visited, minDist, adj);
        }
    }
    
    // Function to find the shortest path
    public int[] shortestPath(int V, int E, int[][] edges) {
        // Create adjacency list for the graph
        List<List<Pair>> adj = new ArrayList<>();
        for (int i = 0; i < V; i++) {
            adj.add(new ArrayList<>());
        }

        // Populate the adjacency list
        for (int i = 0; i < edges.length; i++) {
            adj.get(edges[i][0]).add(new Pair(edges[i][1], edges[i][2]));
        }

        // Initialize visited array and minDist array
        boolean[] visited = new boolean[V];
        int[] minDist = new int[V];
        Arrays.fill(minDist, -1);
        minDist[0] = 0;  // Starting node has a distance of 0
        visited[0] = true;

        // Perform DFS to compute shortest paths
        dfs(0, visited, minDist, adj);

        return minDist;
    }

    // Pair class to store the (node, cost) as a pair
    class Pair {
        int first, second;
        
        Pair(int first, int second) {
            this.first = first;
            this.second = second;
        }
    }
    
    // Main method for testing
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        // Example graph with 5 vertices and edges (node1, node2, cost)
        int[][] edges = {
            {0, 1, 10},
            {0, 2, 20},
            {1, 3, 30},
            {2, 3, 10},
            {3, 4, 40}
        };

        // Test shortestPath function
        int[] result = solution.shortestPath(5, 5, edges);
        
        // Print the shortest distance to each node from node 0
        System.out.println(Arrays.toString(result));
    }
}
