
/*
Given a matrix of size n*m with tower heights as input. For each tower you are allowed to jump to other neighbour tower if height of current tower < height of neighbour tower. 
Find the maximum number of towers you can jump.

you can start from any tower in array
*/

/*
Modified Approach
dp[i][j] stores the maximum number of jumps possible from (i, j).
Base Case: If no valid move exists, dp[i][j] = 0.
Recurrence:𝑑𝑝[𝑖][𝑗]=max⁡(1+𝑑𝑝[𝑛𝑖][𝑛𝑗]) for all valid moves to (𝑛𝑖,𝑛𝑗)
*/

#include <bits/stdc++.h>
using namespace std;

class Solution {
public:
    int n, m;
    vector<vector<int>> dp;
    vector<vector<int>> directions = {{0,1}, {0,-1}, {1,0}, {-1,0}}; // Right, Left, Down, Up

    int dfs(vector<vector<int>>& matrix, int i, int j) {
        if (dp[i][j] != -1) return dp[i][j]; // Return memoized result
        
        int maxJumps = 0;  // If no moves are possible, stays 0
        for (auto &dir : directions) {
            int ni = i + dir[0], nj = j + dir[1];
            if (ni >= 0 && ni < n && nj >= 0 && nj < m && matrix[ni][nj] > matrix[i][j]) {
                maxJumps = max(maxJumps, 1 + dfs(matrix, ni, nj));
            }
        }
        
        return dp[i][j] = maxJumps; // Store and return the result
    }

    int maxJumps(vector<vector<int>>& matrix) {
        if (matrix.empty()) return 0;
        n = matrix.size();
        m = matrix[0].size();
        dp.assign(n, vector<int>(m, -1)); // Initialize DP table
        
        int maxJumps = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                maxJumps = max(maxJumps, dfs(matrix, i, j));
            }
        }
        return maxJumps;
    }
};

int main() {
    vector<vector<int>> matrix = {
        {1, 2, 3},
        {6, 5, 4},
        {7, 8, 9}
    };
    
    Solution sol;
    cout << sol.maxJumps(matrix) << endl; // Output: 8
    return 0;
}
