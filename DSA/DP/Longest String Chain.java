/**You are given an array of words where each word consists of lowercase English letters.

wordA is a predecessor of wordB if and only if we can insert exactly one letter anywhere in wordA without changing the order of the other characters to make it equal to wordB.

For example, "abc" is a predecessor of "abac", while "cba" is not a predecessor of "bcad".
A word chain is a sequence of words [word1, word2, ..., wordk] with k >= 1, where word1 is a predecessor of word2, word2 is a predecessor of word3, and so on. A single word is trivially a word chain with k == 1.

Return the length of the longest possible word chain with words chosen from the given list of words. */

import java.util.*;

class Solution {
    // Helper function to check if `s2` is a predecessor of `s1`
    private boolean compare(String s1, String s2) {
        // If the size difference is not 1, `s2` cannot be a predecessor of `s1`
        if (s1.length() != s2.length() + 1) {
            return false;
        }

        int first = 0, second = 0;

        // Use two pointers to compare characters
        while (first < s1.length()) {
            if (second < s2.length() && s1.charAt(first) == s2.charAt(second)) {
                first++;
                second++;
            } else {
                first++;
            }
        }

        // Ensure all characters of `s2` were matched
        return first == s1.length() && second == s2.length();
    }

    // Custom comparator to sort strings by length
    private static boolean comp(String s1, String s2) {
        return s1.length() < s2.length();
    }

    public int longestStrChain(String[] words) {
        int n = words.length;

        // Sort words by their length
        Arrays.sort(words, Comparator.comparingInt(String::length));

        // Initialize dp array to store the longest chain ending at each word
        int[] dp = new int[n];
        Arrays.fill(dp, 1);

        int res = 1;

        // Iterate through the words to compute the longest chain
        for (int i = 1; i < n; i++) {
            for (int j = 0; j < i; j++) {
                if (compare(words[i], words[j])) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
            res = Math.max(res, dp[i]);
        }

        return res;
    }
}