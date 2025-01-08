/**
 * Given a string S, you need to count how many distinct subsequences can be formed from it.
 * 
 * Recurrence Relation:
For each character S[i], we can either include it in the subsequence or exclude it.

If we exclude S[i], the number of subsequences remains the same as dp[i−1].
If we include S[i], it doubles the number of subsequences since it can form a new subsequence with all the existing subsequences.
To ensure distinct subsequences, we need to consider the last occurrence of the character S[i]. If S[i] has appeared before, we need to subtract the subsequences that would have been counted twice due to this repetition.
 */

import java.util.HashMap;

public class DistinctSubsequences {
    public static int countDistinctSubsequences(String S) {
        int n = S.length();
        // dp[i] will store the number of distinct subsequences of the first i characters.
        int[] dp = new int[n + 1];
        
        // Initialize dp[0] = 1 (empty subsequence)
        dp[0] = 1;

        // HashMap to store the last occurrence index of each character
        HashMap<Character, Integer> lastOccurrence = new HashMap<>();

        // Fill the dp array
        for (int i = 1; i <= n; i++) {
            char c = S.charAt(i - 1);

            // Double the previous result (i.e., include/exclude the current character)
            dp[i] = 2 * dp[i - 1];

            // If the character c has appeared before, subtract the subsequences
            // formed by the previous occurrence to avoid duplicates.
            if (lastOccurrence.containsKey(c)) {
                dp[i] -= dp[lastOccurrence.get(c) - 1];
            }

            // Update the last occurrence of the current character
            lastOccurrence.put(c, i);
        }

        // The result is dp[n] - 1 (to exclude the empty subsequence)
        return dp[n] - 1;
    }

    public static void main(String[] args) {
        String S = "aab";
        System.out.println("Number of distinct subsequences: " + countDistinctSubsequences(S));  // Output: 6
    }
}
