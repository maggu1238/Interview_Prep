/*
The Shortest Bridge problem involves finding the minimum number of flips (changing 0 to 1) 
required to connect two islands in a binary grid.

Optimized Approach (BFS + DFS)
Find the first island using DFS and mark it with 2 while adding its boundary to a queue.
Use BFS from the first island's boundary to reach the second island with the fewest flips.
*/

#include <iostream>
#include <vector>
#include <queue>

using namespace std;

class Solution {
public:
    int n;
    vector<pair<int, int>> directions = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};

    bool isOnBoundary(int x,int y, vector<vector<int>> grid){
        if (r < 0 || c < 0 || r >= n || c >= n){
            return ;
        }
    }    
    // DFS to find and mark the first island
    void dfs(vector<vector<int>>& grid, int r, int c, queue<pair<int, int>>& q) {
        if (r < 0 || c < 0 || r >= n || c >= n || grid[r][c] != 1) return;
        grid[r][c] = 2;  // Mark as visited
        if(isOnBoundary(r,c)){
            q.push({r, c});
        }
          // Add boundary for BFS
        for (auto [dr, dc] : directions) 
            dfs(grid, r + dr, c + dc, q);
    }

    // BFS to find the shortest bridge
    int bfs(vector<vector<int>>& grid, queue<pair<int, int>>& q) {
        int flips = 0;
        while (!q.empty()) {
            int sz = q.size();
            while (sz--) {
                auto [r, c] = q.front(); q.pop();
                for (auto [dr, dc] : directions) {
                    int nr = r + dr, nc = c + dc;
                    if (nr >= 0 && nc >= 0 && nr < n && nc < n) {
                        if (grid[nr][nc] == 1) return flips;  // Reached second island
                        if (grid[nr][nc] == 0) {
                            grid[nr][nc] = 2;
                            q.push({nr, nc});
                        }
                    }
                }
            }
            flips++;
        }
        return -1;
    }

    // Main function to find the shortest bridge
    int shortestBridge(vector<vector<int>>& grid) {
        n = grid.size();
        queue<pair<int, int>> q;
        bool found = false;

        // Find the first island and mark it
        for (int i = 0; i < n && !found; i++) {
            for (int j = 0; j < n && !found; j++) {
                if (grid[i][j] == 1) {
                    dfs(grid, i, j, q);
                    found = true;
                }
            }
        }

        // BFS to find the shortest bridge
        return bfs(grid, q);
    }
};

// Driver code
int main() {
    vector<vector<int>> grid = {
        {0, 1, 0, 0, 0},
        {0, 1, 0, 0, 1},
        {0, 1, 1, 1, 1},
        {0, 0, 0, 0, 1},
        {0, 0, 0, 0, 1}
    };
    
    Solution sol;
    cout << sol.shortestBridge(grid) << endl;
    return 0;
}
