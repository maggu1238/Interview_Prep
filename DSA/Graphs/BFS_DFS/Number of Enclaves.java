/*You are given an m x n binary matrix grid, where 0 represents a sea cell and 1 represents a land cell.

A move consists of walking from one land cell to another adjacent (4-directionally) land cell or walking off the boundary of the grid.

Return the number of land cells in grid for which we cannot walk off the boundary of the grid in any number of moves.*/

import java.util.*;

class Solution {
    public int numEnclaves(int[][] grid) {
        Queue<int[]> queue = new LinkedList<>();
        int rows = grid.length;
        int cols = grid[0].length;

        // Process the boundary cells
        for (int i = 0; i < rows; i++) {
            if (grid[i][0] == 1) {
                grid[i][0] = -1;
                queue.offer(new int[]{i, 0});
            }
            if (grid[i][cols - 1] == 1) {
                grid[i][cols - 1] = -1;
                queue.offer(new int[]{i, cols - 1});
            }
        }

        for (int i = 0; i < cols; i++) {
            if (grid[0][i] == 1) {
                grid[0][i] = -1;
                queue.offer(new int[]{0, i});
            }
            if (grid[rows - 1][i] == 1) {
                grid[rows - 1][i] = -1;
                queue.offer(new int[]{rows - 1, i});
            }
        }

        // Directions for moving in the grid
        int[] dx = {0, 0, 1, -1};
        int[] dy = {1, -1, 0, 0};

        // BFS to mark connected land cells
        while (!queue.isEmpty()) {
            int[] node = queue.poll();
            int x = node[0];
            int y = node[1];

            for (int i = 0; i < 4; i++) {
                int newX = x + dx[i];
                int newY = y + dy[i];

                if (newX >= 0 && newX < rows && newY >= 0 && newY < cols && grid[newX][newY] == 1) {
                    grid[newX][newY] = -1;
                    queue.offer(new int[]{newX, newY});
                }
            }
        }

        // Count the remaining land cells
        int result = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (grid[i][j] == 1) {
                    result++;
                }
            }
        }

        return result;
    }
}