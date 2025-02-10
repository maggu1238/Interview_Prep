/*
Count total 1s: Determine the number of 1s in the array (total_ones). This is the size of the window we need to consider.
Sliding Window: Instead of explicitly extending the array, use a modular index to wrap around when reaching the end of the array.
Track the minimum number of 0s in any valid window of size total_ones and return that as the answer.
*/

#include <iostream>
#include <vector>
#include <climits>

using namespace std;

int minSwaps(vector<int>& nums) {
    int n = nums.size();
    
    // Step 1: Count total 1s
    int total_ones = 0;
    for (int num : nums) {
        if (num == 1) total_ones++;
    }
    
    if (total_ones == 0) return 0; // No swaps needed if there are no 1s
    
    // Step 2: Sliding window to count zeros in first window
    int current_zeros = 0, min_swaps = INT_MAX;
    
    // Count zeros in the first window
    for (int i = 0; i < total_ones; i++) {
        if (nums[i] == 0) current_zeros++;
    }
    min_swaps = current_zeros;
    
    // Step 3: Slide the window across circular array
    for (int i = 1; i < n; i++) {
        // Remove the outgoing element from the previous window
        if (nums[i - 1] == 0) current_zeros--;
        
        // Add the new incoming element (using modular indexing for circular array)
        if (nums[(i + total_ones - 1) % n] == 0) current_zeros++;
        
        min_swaps = min(min_swaps, current_zeros);
    }
    
    return min_swaps;
}

int main() {
    vector<int> nums = {1, 0, 1, 0, 1, 0, 0, 1};
    cout << "Minimum swaps required: " << minSwaps(nums) << endl;
    return 0;
}
