/*Given a grid of size NxM (N is the number of rows and M is the number of columns in the grid) consisting of '0's (Water) and ‘1's(Land). Find the number of islands.*/


import java.util.*;

class Solution {
    private void bfs(int row, int col, boolean[][] visited, char[][] grid) {
        // Mark it visited
        visited[row][col] = true;
        Queue<int[]> queue = new LinkedList<>();
        // Push the node in queue
        queue.offer(new int[]{row, col});
        int n = grid.length;
        int m = grid[0].length;

        // Directions for traversing neighbors
        int[] delRow = {-1, 0, 1, 0};
        int[] delCol = {0, 1, 0, -1};

        // Until the queue becomes empty
        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int curRow = current[0];
            int curCol = current[1];

            // Traverse in the neighbors and mark them if it's land
            for (int i = 0; i < 4; i++) {
                int newRow = curRow + delRow[i];
                int newCol = curCol + delCol[i];

                // Check if the neighbor is valid, is unvisited land
                if (newRow >= 0 && newRow < n && newCol >= 0 && newCol < m 
                        && grid[newRow][newCol] == '1' && !visited[newRow][newCol]) {
                    visited[newRow][newCol] = true;
                    queue.offer(new int[]{newRow, newCol});
                }
            }
        }
    }

    public int numIslands(char[][] grid) {
        int n = grid.length;
        int m = grid[0].length;
        // Create visited array and initialize to false
        boolean[][] visited = new boolean[n][m];
        int count = 0;

        for (int row = 0; row < n; row++) {
            for (int col = 0; col < m; col++) {
                // If not visited and is a land
                if (!visited[row][col] && grid[row][col] == '1') {
                    count++;
                    bfs(row, col, visited, grid);
                }
            }
        }
        return count;
    }
}
