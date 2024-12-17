/**Given an integer array nums, return the number of longest increasing subsequences.

Notice that the sequence has to be strictly increasing. */

import java.util.*;

public class Main {
    public static int findNumberOfLIS(int[] nums) {
        int n = nums.length;
        int[] dp = new int[n];          // Stores the length of LIS ending at each index
        int[] numberOfLis = new int[n]; // Stores the count of LIS ending at each index
        Arrays.fill(dp, 1);             // Initialize LIS lengths to 1
        Arrays.fill(numberOfLis, 1);    // Initialize LIS counts to 1

        int lis = 1; // Length of the longest LIS
        int total = 0;

        // Build dp and numberOfLis arrays
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < i; j++) {
                if (nums[i] > nums[j]) {
                    if (dp[i] < dp[j] + 1) {
                        dp[i] = dp[j] + 1;
                        numberOfLis[i] = numberOfLis[j];
                    } else if (dp[i] == dp[j] + 1) {
                        numberOfLis[i] += numberOfLis[j];
                    }
                }
            }
            lis = Math.max(lis, dp[i]);
        }

        // Count the number of LIS
        int res = 0;
        for (int i = 0; i < n; i++) {
            if (dp[i] == lis) {
                res += numberOfLis[i];
            }
        }

        return res;
    }

    public static void main(String[] args) {
        int[] nums = {1, 3, 5, 4, 7};
        System.out.println(findNumberOfLIS(nums)); // Output: 2
    }
}