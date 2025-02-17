/*
Given a two dimensional array of positive integer values, find the minimum sum when you start from the top left corner traveling to the bottom right corner. You can only move in the direction of right and down.
*/

public class MinimumPathSum {

    public static int minPathSum(int[][] grid) {
        if (grid == null || grid.length == 0 || grid[0].length == 0) {
            return 0; // Handle edge cases
        }

        int rows = grid.length;
        int cols = grid[0].length;

        // Create a DP table
        int[][] dp = new int[rows][cols];

        // Initialize the top-left corner
        dp[0][0] = grid[0][0];

        // Fill the first row (can only come from the left)
        for (int j = 1; j < cols; j++) {
            dp[0][j] = dp[0][j - 1] + grid[0][j];
        }

        // Fill the first column (can only come from above)
        for (int i = 1; i < rows; i++) {
            dp[i][0] = dp[i - 1][0] + grid[i][0];
        }

        // Fill the rest of the DP table
        for (int i = 1; i < rows; i++) {
            for (int j = 1; j < cols; j++) {
                dp[i][j] = Math.min(dp[i - 1][j], dp[i][j - 1]) + grid[i][j];
            }
        }

        // The bottom-right corner contains the minimum path sum
        return dp[rows - 1][cols - 1];
    }

    public static void main(String[] args) {
        int[][] grid = {
            {1, 3, 1},
            {1, 5, 1},
            {4, 2, 1}
        };

        int result = minPathSum(grid);
        System.out.println("Minimum path sum: " + result);
    }
}

