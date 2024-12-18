/*Given an n x n binary matrix grid, return the length of the shortest clear path in the matrix. If there is no clear path, return -1.

A clear path in a binary matrix is a path from the top-left cell (i.e., (0, 0)) to the bottom-right cell (i.e., (n - 1, n - 1)) such that:

All the visited cells of the path are 0.
All the adjacent cells of the path are 8-directionally connected (i.e., they are different and they share an edge or a corner).
The length of a clear path is the number of visited cells of this path.*/

import java.util.*;

class Solution {
    
    // Custom Pair class to hold x and y coordinates
    static class Pair {
        int x, y;
        Pair(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
    
    public int shortestPathBinaryMatrix(int[][] grid) {
        // Directions for 8 possible moves (right, left, down, up, and diagonal)
        int[] xdir = {0, 0, 1, -1, -1, -1, 1, 1};
        int[] ydir = {1, -1, 0, 0, -1, 1, -1, 1};
        
        int m = grid.length;
        
        // If the start or the end is blocked, return -1
        if (grid[0][0] == 1 || grid[m - 1][m - 1] == 1) {
            return -1;
        }

        // Queue for BFS, holds Pair objects which contain (x, y)
        Queue<Pair> queue = new LinkedList<>();
        queue.add(new Pair(0, 0));
        
        // Mark the starting point as visited (with a distance of 1)
        grid[0][0] = 1;
        
        while (!queue.isEmpty()) {
            Pair p = queue.poll();
            int x = p.x;
            int y = p.y;
            
            // If we reach the bottom-right corner, return the distance
            if (x == m - 1 && y == m - 1) {
                return grid[x][y];
            }
            
            // Explore all 8 possible directions
            for (int i = 0; i < 8; i++) {
                int newX = x + xdir[i];
                int newY = y + ydir[i];
                
                // Check if the new position is within bounds and unvisited
                if (newX >= 0 && newX < m && newY >= 0 && newY < m && grid[newX][newY] == 0) {
                    // Mark the new position as visited by assigning the distance
                    grid[newX][newY] = grid[x][y] + 1;
                    queue.add(new Pair(newX, newY));
                }
            }
        }

        // If there is no path to the bottom-right corner
        return -1;
    }

    // Main method for testing
    public static void main(String[] args) {
        Solution solution = new Solution();
        
        int[][] grid = {
            {0, 1, 0},
            {1, 0, 1},
            {0, 0, 0}
        };
        
        int result = solution.shortestPathBinaryMatrix(grid);
        System.out.println(result);  // Output should be 4
    }
}
