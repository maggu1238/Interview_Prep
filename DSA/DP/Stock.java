class Solution {
    public int maxProfit(int[] prices) {
        // Initialize variables
        int minBuy = prices[0]; // Minimum price seen so far
        int maxProfit = 0;      // Maximum profit seen so far
        int n = prices.length;  // Length of the prices array

        // Iterate through the array starting from the second day
        for (int i = 1; i < n; i++) {
            // Update the minimum price if the current price is lower
            if (prices[i] < minBuy) {
                minBuy = prices[i];
            }
            // Calculate and update the maximum profit
            maxProfit = Math.max(maxProfit, prices[i] - minBuy);
        }

        return maxProfit;
    }
}