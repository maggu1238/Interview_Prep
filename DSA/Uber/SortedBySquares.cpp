/*Given a sorted array containing both positive and negative numbers, return an array sorted based on the square of its values, but instead of squared values, keep the original numbers.*/
#include <iostream>
#include <vector>
#include <algorithm>

using namespace std;

vector<int> sortedBySquares(vector<int>& nums) {
    int n = nums.size();
    vector<int> result(n);
    int left = 0, right = n - 1;
    int index = n - 1;

    while (left <= right) {
        int leftSquare = nums[left] * nums[left];
        int rightSquare = nums[right] * nums[right];

        if (leftSquare > rightSquare) {
            result[index] = nums[left];  // Store the original element
            left++;
        } else {
            result[index] = nums[right]; // Store the original element
            right--;
        }
        index--;
    }

    return result;
}

int main() {
    vector<int> arr = {-7, -3, 0, 2, 5};
    
    // Get sorted array based on squares
    vector<int> sortedArr = sortedBySquares(arr);

    // Print result
    cout << "Sorted Squared Array (Original Elements): ";
    for (int num : sortedArr) {
        cout << num << " ";
    }
    cout << endl;

    return 0;
}
