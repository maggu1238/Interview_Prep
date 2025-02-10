/*Given a sorted array with positive and negative values, sort them based on the square of its values. Also, print the squared values array.
- Expected O(N) working code and interviewer made me run the code with sample tests and compare output
- Sample: [-7, -2, -1, -1, 1, 2, 2, 2, 3, 5] => [-1, -1, 1, -2, 2, 2, 2, 3, 5, -7]
*/

/*how would you modify your code to find the kth smallest square element in the array. Expected O(LogN)
    ->  kth smallest element in union of two sorted arrays similar to median of two sorted arrays

*/    

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
