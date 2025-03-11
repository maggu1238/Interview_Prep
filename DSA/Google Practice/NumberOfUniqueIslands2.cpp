/**
Given an m × n binary grid where:

0 represents water.
1 represents land.
An island is a group of connected 1s (land) using 4-directional movement (up, down, left, right).

Two islands are considered the same if they are identical after translation, rotation (90°, 180°, 270°), or reflection (flipping left-right or top-bottom).

Goal: Count the number of distinct islands in the grid, considering rotations and reflections.
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

// Generate 8 transformations (rotations & reflections)
vector<vector<pair<int, int>>> generateTransformations(vector<pair<int, int>>& shape) {
    vector<vector<pair<int, int>>> transformations(8);
    
    for (auto [x, y] : shape) {
        transformations[0].push_back({x, y});   // Original
        transformations[1].push_back({y, -x});  // 90° Rotation
        transformations[2].push_back({-x, -y}); // 180° Rotation
        transformations[3].push_back({-y, x});  // 270° Rotation
        transformations[4].push_back({-x, y});  // Horizontal Reflection
        transformations[5].push_back({x, -y});  // Vertical Reflection
        transformations[6].push_back({y, x});   // Diagonal Reflection (↘)
        transformations[7].push_back({-y, -x}); // Diagonal Reflection (↙)
    }

    // Normalize each transformation
    for (auto& t : transformations) {
        sort(t.begin(), t.end());  // Sort for consistency
        int minX = t[0].first, minY = t[0].second;
        for (auto& p : t) p = {p.first - minX, p.second - minY};  // Normalize to (0,0)
    }
    
    return transformations;\
}

int numDistinctIslands2(vector<vector<int>>& grid) {
    int m = grid.size(), n = grid[0].size();
    set<vector<pair<int, int>>> uniqueIslands; // Store unique island shapes

    for (int i = 0; i < m; i++) {
        for (int j = 0; j < n; j++) {
            if (grid[i][j] == 1) {
                vector<pair<int, int>> shape;
                dfs(grid, i, j, i, j, shape);

                // Generate all transformations
                vector<vector<pair<int, int>>> transformations = generateTransformations(shape);

                // Store the smallest lexicographical shape
                uniqueIslands.insert(*min_element(transformations.begin(), transformations.end()));
            }
        }
    }
    return uniqueIslands.size();
}

int main() {
    vector<vector<int>> grid = {
        {1, 1, 0, 0, 0},
        {1, 0, 0, 0, 1},
        {0, 0, 1, 1, 1},
        {0, 0, 1, 1, 1}
    };

    cout << "Number of distinct islands: " << numDistinctIslands2(grid) << endl;
    return 0;
}


