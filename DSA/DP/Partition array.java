/**Given an integer array arr, partition the array into (contiguous) subarrays of length at most k. After partitioning, each subarray has its values changed to become the maximum value of that subarray.Given an integer array arr, partition the array into (contiguous) subarrays of length at most k. After partitioning, each subarray has its values changed to become the maximum value of that subarray. */
/** */

public class MaxSumAfterPartitioning {
    static int f(int ind, int[] num, int k, int[] dp) {
        int n = num.length;
        // Base case:
        if (ind == n) return 0;

        if (dp[ind] != -1) return dp[ind];
        int len = 0;
        int maxi = Integer.MIN_VALUE;
        int maxAns = Integer.MIN_VALUE;
        
        // Iterate through the next 'k' elements or remaining elements if less than 'k'.
        for (int j = ind; j < Math.min(ind + k, n); j++) {
            len++;
            maxi = Math.max(maxi, num[j]);
            int sum = len * maxi + f(j + 1, num, k, dp);
            maxAns = Math.max(maxAns, sum);
        }
        return dp[ind] = maxAns;
    }

    static int maxSumAfterPartitioning(int[] num, int k) {
        int n = num.length;
        int[] dp = new int[n];
        Arrays.fill(dp, -1);
        return f(0, num, k, dp);
    }

    public static void main(String[] args) {
        int[] num = {1, 15, 7, 9, 2, 5, 10};
        int k = 3;
        int maxSum = maxSumAfterPartitioning(num, k);
        System.out.println("The maximum sum is: " + maxSum);
    }
}

// Tabulation

public class MaxSumAfterPartitioning {
    static int maxSumAfterPartitioning(int[] num, int k) {
        int n = num.length;
        int[] dp = new int[n + 1];
        
        // Traverse the input array from right to left
        for (int ind = n - 1; ind >= 0; ind--) {
            int len = 0;
            int maxi = Integer.MIN_VALUE;
            int maxAns = Integer.MIN_VALUE;
            
            // Iterate through the next 'k' elements or remaining elements if less than 'k'.
            for (int j = ind; j < Math.min(ind + k, n); j++) {
                len++;
                maxi = Math.max(maxi, num[j]);
                int sum = len * maxi + dp[j + 1];
                maxAns = Math.max(maxAns, sum);
            }
            dp[ind] = maxAns;
        }
        return dp[0];
    }

    public static void main(String[] args) {
        int[] num = {1, 15, 7, 9, 2, 5, 10};
        int k = 3;
        int maxSum = maxSumAfterPartitioning(num, k);
        System.out.println("The maximum sum is: " + maxSum);
    }
}