/*There are n cities. Some of them are connected, while some are not. If city a is connected directly with city b, and city b is connected directly with city c, then city a is connected indirectly with city c.

A province is a group of directly or indirectly connected cities and no other cities outside of the group.

You are given an n x n matrix isConnected where isConnected[i][j] = 1 if the ith city and the jth city are directly connected, and isConnected[i][j] = 0 otherwise.

Return the total number of provinces.*/

import java.util.*;

class Solution {
    // DFS Function
    private void dfs(int node, int[] visited, int[][] isConnected) {
        if (visited[node] == 1) {
            return;
        }
        visited[node] = 1;
        for (int i = 0; i < isConnected[node].length; i++) {
            if (isConnected[node][i] == 1 && node != i) {
                dfs(i, visited, isConnected);
            }
        }
    }

    // BFS Function
    private void bfs(int node, int[] visited, int[][] isConnected) {
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(node);

        while (!queue.isEmpty()) {
            int currentNode = queue.poll();
            visited[currentNode] = 1;

            for (int i = 0; i < isConnected[currentNode].length; i++) {
                if (isConnected[currentNode][i] == 1 && i != currentNode && visited[i] == 0) {
                    queue.offer(i);
                }
            }
        }
    }

    // Function to find the number of provinces
    public int findCircleNum(int[][] isConnected) {
        int n = isConnected.length;
        int[] visited = new int[n];
        int provinces = 0;

        for (int i = 0; i < n; i++) {
            if (visited[i] == 0) {
                provinces++;
                bfs(i, visited, isConnected); // You can switch to dfs(i, visited, isConnected) here
            }
        }

        return provinces;
    }
}
