/*
There is a fixed size array of integers. And we are given a number S. We need to pick a number x from integers (doesn't have to be in the array) and truncate each array integer to largest optimal number such that sum of array after truncations is storage limit S.
*/


/**
We need to find an integer x such that when each element in the array is truncated to 
⌊num/x⌋, the sum equals S.

This suggests using binary search on 𝑥, as increasing 𝑥 decreases the sum.
*/

/*
Truncation Rule: Each element 
num[i] is truncated as: truncated[i]=⌊num[i]/x⌋
Summation Condition: The total sum of truncated values should be exactly S.
Binary Search Range:
The smallest x can be 1 (producing the largest sum).
The largest x can be max(arr), where all elements become 1 or 0.
*/

#include <iostream>
#include <vector>
#include <algorithm>

using namespace std;

bool isValid(const vector<int>& nums, int x, int S) {
    long long sum = 0;
    for (int num : nums) {
        sum += num / x;  // Truncate each element
        if (sum > S) return false; // No need to continue if sum exceeds S
    }
    return sum >= S; // Ensure it's exactly S
}

int findOptimalX(vector<int>& nums, int S) {
    int left = 1, right = *max_element(nums.begin(), nums.end());
    int bestX = -1;

    while (left <= right) {
        int mid = left + (right - left) / 2; // Avoid overflow
        if (isValid(nums, mid, S)) {
            bestX = mid;  // Possible candidate
            left = mid + 1; // Try for a larger x
        } else {
            right = mid - 1; // Reduce x to satisfy sum condition
        }
    }
    return bestX;
}

int main() {
    vector<int> nums = {10, 20, 30}; 
    int S = 5;
    cout << "Optimal X: " << findOptimalX(nums, S) << endl;
    return 0;
}

