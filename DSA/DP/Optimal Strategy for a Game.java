/**Two players take turns picking numbers from either end of an array. The goal is to maximize the sum of the numbers collected. Assuming both players play optimally, find the maximum score the first player can achieve.
 
 Problem Analysis
Two players: Player 1 (the first player) and Player 2 take turns picking numbers from either end of the array.
Goal: Player 1 wants to maximize their score, assuming Player 2 also plays optimally to maximize their own score.
Array: The array contains integers, which can be positive, negative, or zero.


Dynamic Programming Idea
The DP state incorporates the idea of minimizing the opponent's score during the next turn. Here's how:

State Definition:

Let 
dp[i][j] represent the maximum score Player 1 can achieve when it is their turn to pick from the subarray 𝑎𝑟𝑟[𝑖…𝑗].
Recurrence Relation:
If Player 1 chooses 𝑎𝑟𝑟[𝑖](leftmost), then Player 2 will play optimally on the subarray 𝑎𝑟𝑟[𝑖+1…𝑗]. Player 1’s total score becomes:𝑎𝑟𝑟[𝑖]+(sum of remaining subarray)−𝑑𝑝[𝑖+1][𝑗] 
Here, dp[i+1][j] is the maximum score Player 2 can achieve from arr[i+1…j], and Player 1 gets the complement of this score.

If Player 1 chooses arr[j] (rightmost), then Player 2 will play optimally on the subarray arr[i…j−1]. Player 1’s total score becomes: arr[j]+(sum of remaining subarray)−dp[i][j−1]
Therefore:
dp[i][j]=max(arr[i]+(sum of remaining subarray)−dp[i+1][j],arr[j]+(sum of remaining subarray)−dp[i][j−1])
Base Case:

If i==j, the subarray contains only one number. Player 1 takes this number: dp[i][i]=arr[i]
 */


public class OptimalGameStrategy {
    public static int maxScore(int[] arr) {
        int n = arr.length;

        // DP table
        int[][] dp = new int[n][n];

        // Prefix sum to calculate the sum of any subarray
        int[] prefixSum = new int[n];
        prefixSum[0] = arr[0];
        for (int i = 1; i < n; i++) {
            prefixSum[i] = prefixSum[i - 1] + arr[i];
        }

        // Sum of subarray arr[i...j]
        int sum(int i, int j) {
            return prefixSum[j] - (i > 0 ? prefixSum[i - 1] : 0);
        }

        // Fill the DP table
        for (int length = 1; length <= n; length++) {
            for (int i = 0; i <= n - length; i++) {
                int j = i + length - 1;
                if (i == j) {
                    dp[i][j] = arr[i];
                } else {
                    dp[i][j] = Math.max(
                        arr[i] + sum(i + 1, j) - dp[i + 1][j],
                        arr[j] + sum(i, j - 1) - dp[i][j - 1]
                    );
                }
            }
        }

        return dp[0][n - 1];
    }

    public static void main(String[] args) {
        int[] arr = {1, 5, 233, 7};
        System.out.println("Maximum score Player 1 can achieve: " + maxScore(arr));
    }
}
