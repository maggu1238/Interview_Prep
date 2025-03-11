/*
Given a n x n matrix with 0s and 1s, given the min number of swaps (not necessary to be adjacent) required to created a y x y (y<=n)
 matrix of 1s inside.
*/

#include <iostream>
#include <vector>
#include <climits>

using namespace std;

// Function to compute min swaps with in-place DP
int minSwapsToCreateSquare(vector<vector<int>>& matrix, int y) {
    int n = matrix.size();
    if (y > n) return -1;

    // Step 1: Convert matrix into cumulative sum (In-Place DP)
    for (int i = 0; i < n; i++) {
        for (int j = 0; j < n; j++) {
            if (i > 0) matrix[i][j] += matrix[i-1][j];
            if (j > 0) matrix[i][j] += matrix[i][j-1];
            if (i > 0 && j > 0) matrix[i][j] -= matrix[i-1][j-1];
        }
    }

    // Step 2: Iterate over all possible y × y submatrices
    int min_swaps = INT_MAX;
    for (int i = y - 1; i < n; i++) {
        for (int j = y - 1; j < n; j++) {
            int ones = matrix[i][j];
            
            if (i >= y) ones -= matrix[i - y][j];
            if (j >= y) ones -= matrix[i][j - y];
            if (i >= y && j >= y) ones += matrix[i - y][j - y];

            int swaps = (y * y) - ones;
            min_swaps = min(min_swaps, swaps);
        }
    }

    return min_swaps;
}

int main() {
    vector<vector<int>> matrix = {
        {1, 0, 1, 1},
        {0, 1, 1, 0},
        {1, 1, 0, 1},
        {0, 1, 1, 1}
    };
    int y = 2;
    cout << "Minimum swaps required: " << minSwapsToCreateSquare(matrix, y) << endl;
    return 0;
}
