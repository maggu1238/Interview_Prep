/**You are given n items whose weights and values are known, as well as a knapsack to carry these items. The knapsack cannot carry more than a certain maximum weight, known as its capacity.

You need to maximize the total value of the items in your knapsack, while ensuring that the sum of the weights of the selected items does not exceed the capacity of the knapsack.

If there is no combination of weights whose sum is within the capacity constraint, return 0. */

int FindMaxKnapsackProfitHelper(int capacity, const std::vector<int>& weights, const std::vector<int>& values, int n, std::vector<std::vector<int>>& dp) {
    // Base case
    if (n == 0 || capacity == 0) {
        return 0;
    }

    // If we have already solved this subproblem, fetch the result from memory
    if (dp[n][capacity] != -1) {
        return dp[n][capacity];
    }

    // Otherwise, we solve it and save the result in our look-up table
    if (weights[n - 1] <= capacity) {
        dp[n][capacity] = std::max(
            values[n - 1] + FindMaxKnapsackProfitHelper(capacity - weights[n - 1], weights, values, n - 1, dp),
            FindMaxKnapsackProfitHelper(capacity, weights, values, n - 1, dp));
        return dp[n][capacity];
    }

    dp[n][capacity] = FindMaxKnapsackProfitHelper(capacity, weights, values, n - 1, dp);
    return dp[n][capacity];
}

int FindMaxKnapsackProfit(int capacity, const std::vector<int>& weights, const std::vector<int>& values) {
    int n = weights.size();
    // Set up the dp table to store solutions to subproblems
    std::vector<std::vector<int>> dp(n + 1, std::vector<int>(capacity + 1, -1));
    return FindMaxKnapsackProfitHelper(capacity, weights, values, n, dp);
}

///////////////////////////////////////////////////////
////////////////////////////////////////////////////
//// Tabulation

int FindMaxKnapsackProfit(int capacity, const std::vector<int>& weights, const std::vector<int>& values) {
    int n = weights.size();

    // previous (i-1)th row which will be used to fill up the current ith row
    std::vector<int> dp(capacity + 1, 0);

    // current ith row that will use the values of the previous (i-1)th row to fill itself.
    std::vector<int> temp(capacity + 1, 0);

    for (int i = 1; i <= n; i++) {
        for (int j = 1; j <= capacity; j++) {
            if (weights[i - 1] <= j) {
                temp[j] = std::max(values[i - 1] + dp[j - weights[i - 1]], dp[j]);
            }
            else {
                temp[j] = dp[j];
            }
        }

        // Setting the (i-1)th row equal to the ith row
        dp = temp;
    }

    return dp[capacity];
}