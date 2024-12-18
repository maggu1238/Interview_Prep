/*You are given an m x n grid where each cell can have one of three values:

0 representing an empty cell,
1 representing a fresh orange, or
2 representing a rotten orange.
Every minute, any fresh orange that is 4-directionally adjacent to a rotten orange becomes rotten.

Return the minimum number of minutes that must elapse until no cell has a fresh orange. If this is impossible, return -1.*/

import java.util.*;

class Solution {
    public int orangesRotting(int[][] grid) {
        Queue<int[]> q = new LinkedList<>();
        int freshOranges = 0;
        
        // Initialize the queue with rotten oranges and count fresh oranges
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == 2) {
                    q.offer(new int[]{i, j});
                }
                if (grid[i][j] == 1) {
                    freshOranges++;
                }
            }
        }
        
        // Directions for moving up, down, left, right
        int[] x = {0, 0, 1, -1};
        int[] y = {1, -1, 0, 0};
        
        int steps = 0;
        int size = q.size();
        
        // BFS to spread the rot
        while (!q.isEmpty()) {
            int[] p = q.poll();
            size--;
            
            for (int i = 0; i < 4; i++) {
                int newX = p[0] + x[i];
                int newY = p[1] + y[i];
                
                // If the new position is within bounds and is a fresh orange
                if (newX >= 0 && newX < grid.length && newY >= 0 && newY < grid[0].length 
                    && grid[newX][newY] == 1) {
                    freshOranges--;
                    grid[newX][newY] = 2;
                    q.offer(new int[]{newX, newY});
                }
            }
            
            // If we've processed one level of BFS and there are still elements in the queue
            if (size == 0 && !q.isEmpty()) {
                steps++;
                size = q.size();
            }
        }
        
        // If there are still fresh oranges left
        if (freshOranges > 0) {
            return -1;
        }
        
        return steps;
    }
}
