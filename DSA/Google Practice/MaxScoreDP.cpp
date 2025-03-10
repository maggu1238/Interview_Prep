/**
Given an array of positive elements, you can start at any element,
 at every element you have a choice to take it or skip it.
If you take it the score will be increased by arr[i] and your next position will be i + arr[i],
 we have to find the max score that we can get
*/

/*
Time Complexity: 
O(N) due to memoization.

Space Complexity: 
O(N) for recursion stack & dp array.
*/

#include <iostream>
#include <vector>
using namespace std;

int dfs(int i, vector<int>& arr, vector<int>& dp) {
    if (i >= arr.size()) return 0;
    if (dp[i] != -1) return dp[i];

    int take = arr[i] + ((i + arr[i] < arr.size()) ? dfs(i + arr[i], arr, dp) : 0);
    int skip = dfs(i + 1, arr, dp);

    return dp[i] = max(take, skip);
}

int maxScore(vector<int>& arr) {
    int n = arr.size();
    vector<int> dp(n, -1);
    int result = 0;

    for (int i = 0; i < n; i++) {
        result = max(result, dfs(i, arr, dp));
    }
    
    return result;
}

int main() {
    vector<int> arr = {3, 2, 5, 1, 1, 2};
    cout << "Max Score: " << maxScore(arr) << endl;
    return 0;
}



// Bottom Up approack

#include <iostream>
#include <vector>
#include <algorithm>
using namespace std;

int maxScore(vector<int>& arr) {
    int n = arr.size();
    vector<int> dp(n + 1, 0);

    // Process from right to left
    for (int i = n - 1; i >= 0; --i) {
        int take = arr[i] + ((i + arr[i] < n) ? dp[i + arr[i]] : 0);
        int skip = (i + 1 < n) ? dp[i + 1] : 0;
        dp[i] = max(take, skip);
    }

    // The max score can start from any index
    return *max_element(dp.begin(), dp.end());
}

int main() {
    vector<int> arr = {3, 2, 5, 1, 1, 2};
    cout << "Max Score: " << maxScore(arr) << endl;
    return 0;
}

