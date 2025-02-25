/*
You are given an m × n binary grid where:

0 represents water.
1 represents land.
An island is a group of connected 1s (land) using 4-directional movement (up, down, left, right).

Two islands are considered the same if they have the same shape, even if they appear in different locations.

Goal: Find the number of distinct islands in the grid.
*/

#include <iostream>
#include <vector>
#include <set>
using namespace std;

void dfs(vector<vector<int>>& grid, int x, int y, int baseX, int baseY, vector<pair<int, int>>& shape) {
    int m = grid.size(), n = grid[0].size();
    if (x < 0 || y < 0 || x >= m || y >= n || grid[x][y] == 0) return;

    grid[x][y] = 0;  // Mark as visited
    shape.push_back({x - baseX, y - baseY}); // Store relative position

    // Explore all 4 directions
    dfs(grid, x + 1, y, baseX, baseY, shape);
    dfs(grid, x - 1, y, baseX, baseY, shape);
    dfs(grid, x, y + 1, baseX, baseY, shape);
    dfs(grid, x, y - 1, baseX, baseY, shape);
}

int numDistinctIslands(vector<vector<int>>& grid) {
    int m = grid.size(), n = grid[0].size();
    set<vector<pair<int, int>>> uniqueIslands; // Store unique island shapes

    for (int i = 0; i < m; i++) {
        for (int j = 0; j < n; j++) {
            if (grid[i][j] == 1) {
                vector<pair<int, int>> shape;
                dfs(grid, i, j, i, j, shape);
                uniqueIslands.insert(shape);
            }
        }
    }
    return uniqueIslands.size();
}

int main() {
    vector<vector<int>> grid = {
        {1, 1, 0, 0, 0},
        {1, 1, 0, 0, 0},
        {0, 0, 0, 1, 1},
        {0, 0, 0, 1, 1}
    };

    cout << "Number of distinct islands: " << numDistinctIslands(grid) << endl;
    return 0;
}
