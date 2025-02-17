/*
Given two inputs,

First input is the location map, a 2D array
| O | E | E | E | X |
| E | O | X | X | X |
| E | E | E | E | E |
| X | E | O | E | E |
| X | E | X | E | X |

O = Robot, E = Empty, X = blocker

Second input is the *query. It’s a 1D array consisting of distance to the closest blocker in the order from **left, **top, *bottom and right
[2, 2, 4, 1]

This means distance of 2 to the left blocker, 2 to the top blocker, 4 to the bottom blocker and 1 to the right blocker

Note: The location map boundary is also considered blocker, meaning if the robot hits the boundary it also means it’s hitting the blocker.
Return the coordinates of all robots who can satisfy move given by second input.
*/

#include <iostream>
#include <vector>

using namespace std;

vector<pair<int, int>> findValidRobots(vector<vector<char>>& grid, vector<int>& query) {
    int rows = grid.size(), cols = grid[0].size();
    
    // Initialize distance matrices
    vector<vector<int>> left(rows, vector<int>(cols, 0));
    vector<vector<int>> right(rows, vector<int>(cols, 0));
    vector<vector<int>> top(rows, vector<int>(cols, 0));
    vector<vector<int>> bottom(rows, vector<int>(cols, 0));

    // Precompute Left distances
    for (int r = 0; r < rows; r++) {
        int dist = 0;
        for (int c = 0; c < cols; c++) {
            if (grid[r][c] == 'X') dist = 0;
            else if (c == 0) dist = 0;  // Boundary is a blocker
            else dist++;
            left[r][c] = dist;
        }
    }

    // Precompute Right distances
    for (int r = 0; r < rows; r++) {
        int dist = 0;
        for (int c = cols - 1; c >= 0; c--) {
            if (grid[r][c] == 'X') dist = 0;
            else if (c == cols - 1) dist = 0;
            else dist++;
            right[r][c] = dist;
        }
    }

    // Precompute Top distances
    for (int c = 0; c < cols; c++) {
        int dist = 0;
        for (int r = 0; r < rows; r++) {
            if (grid[r][c] == 'X') dist = 0;
            else if (r == 0) dist = 0;
            else dist++;
            top[r][c] = dist;
        }
    }

    // Precompute Bottom distances
    for (int c = 0; c < cols; c++) {
        int dist = 0;
        for (int r = rows - 1; r >= 0; r--) {
            if (grid[r][c] == 'X') dist = 0;
            else if (r == rows - 1) dist = 0;
            else dist++;
            bottom[r][c] = dist;
        }
    }

    // Find valid robots
    vector<pair<int, int>> validRobots;
    for (int r = 0; r < rows; r++) {
        for (int c = 0; c < cols; c++) {
            if (grid[r][c] == 'O' && 
                left[r][c] == query[0] &&
                top[r][c] == query[1] &&
                bottom[r][c] == query[2] &&
                right[r][c] == query[3]) {
                validRobots.push_back({r, c});
            }
        }
    }

    return validRobots;
}

int main() {
    vector<vector<char>> grid = {
        {'O', 'E', 'E', 'E', 'X'},
        {'E', 'O', 'X', 'X', 'X'},
        {'E', 'E', 'E', 'E', 'E'},
        {'X', 'E', 'O', 'E', 'E'},
        {'X', 'E', 'X', 'E', 'X'}
    };

    vector<int> query = {2, 2, 4, 1};
    
    vector<pair<int, int>> result = findValidRobots(grid, query);

    cout << "Valid Robot Coordinates: ";
    for (auto& [r, c] : result) {
        cout << "(" << r << ", " << c << ") ";
    }
    cout << endl;

    return 0;
}


///////////////////////Sedcond approach

public class Solution {
    public static List<int[]> findMatchingRobots(char[][] array, int[] blocker) {
        List<int[]> robots = new ArrayList<>();
        for(int i=0; i<array.length; i++) {
            for(int j=0; j<array[0].length; j++) {
                if(array[i][j] == 'O') robots.add(new int[]{i,j});
            }
        }

        List<int[]> result = new ArrayList<>();
        int[][] dirs = new int[][]{{0,-1}, {-1,0}, {1,0}, {0,1}}; // left, top, bottom, right
        for(int[] pos : robots) {
            if(nearestBlockerPos(pos[0], pos[1], array, dirs[0], blocker[0]) &&
                nearestBlockerPos(pos[0], pos[1], array, dirs[1], blocker[1]) &&
                nearestBlockerPos(pos[0], pos[1], array, dirs[2], blocker[2]) &&
                nearestBlockerPos(pos[0], pos[1], array, dirs[3], blocker[3])) {
                result.add(pos.clone());
            }
        }

        return result;
    }

    private static boolean nearestBlockerPos(int x, int y, char[][] array, int[] dir, int val) {
        int distance = 0;
        while(x >= 0 && x < array.length && y >= 0 && y < array[0].length && array[x][y] != 'X') {
            x += dir[0];
            y += dir[1];
            distance++;
            if(distance > val) return false;
        }
        return distance == val;
    }

    public static void main(String[] args) {
        char[][] array = new char[][]{{'O','E','E','E','X'},
                {'E','O','X','X','X'},
                {'E','E','E','E','E'},
                {'X','E','O','E','E'},
                {'X','E','X','E','X'}};

        int[] blockers = new int[]{2,2,4,1};

        List<int[]> result = findMatchingRobots(array, blockers);
        for(int[] r : result) {
            System.out.println(r[0] + "," + r[1]);
        }
    }
}
