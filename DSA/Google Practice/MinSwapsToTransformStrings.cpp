/*Given two lists of Strings with the same set of elements and no duplicates within the list, find out the minimum number of contiguous swaps that are required to get from one list to another.
Example S = [B,C,A,D] and D = [C,D,A,B]*/




// O(n log n) optimal solution.

#include <bits/stdc++.h>
using namespace std;

// Function to merge two sorted halves and count the number of swaps needed
int mergeAndCount(vector<int>& arr, int l, int m, int r) {
    // Create temporary left and right subarrays
    vector<int> left(arr.begin() + l, arr.begin() + m + 1);
    vector<int> right(arr.begin() + m + 1, arr.begin() + r + 1);
    
    int i = 0, j = 0, k = l, swaps = 0;

    // Merge the two sorted halves while counting inversions (swaps needed)
    while (i < left.size() && j < right.size()) {
        if (left[i] <= right[j]) {
            // If left[i] is smaller, place it first (no swap needed)
            arr[k++] = left[i++];
        } else {
            // If left[i] > right[j], it means all remaining elements in left[] are also greater
            // So, all these elements contribute to swaps
            arr[k++] = right[j++];
            swaps += left.size() - i;  // Count inversions
        }
    }

    // Copy remaining elements from left[] (if any)
    while (i < left.size()) arr[k++] = left[i++];

    // Copy remaining elements from right[] (if any)
    while (j < right.size()) arr[k++] = right[j++];

    return swaps;
}

// Recursive function to count inversions using Merge Sort
int countInversions(vector<int>& arr, int l, int r) {
    if (l >= r) return 0;  // Base case: single element has no inversions

    int m = l + (r - l) / 2;  // Find middle index
    int swaps = 0;

    // Count inversions in left half
    swaps += countInversions(arr, l, m);

    // Count inversions in right half
    swaps += countInversions(arr, m + 1, r);

    // Count inversions while merging both halves
    swaps += mergeAndCount(arr, l, m, r);

    return swaps;
}

// Function to transform S to D and count the minimum swaps required
int minSwapsToTransform(vector<string>& S, vector<string>& D) {
    unordered_map<string, int> indexMap;

    // Store the index positions of elements in D
    for (int i = 0; i < D.size(); i++) {
        indexMap[D[i]] = i;
    }

    // Convert S into an array of indices based on the order in D
    vector<int> transformed;
    for (const string& s : S) {
        transformed.push_back(indexMap[s]);
    }

    // Count minimum adjacent swaps (inversions) needed to sort transformed[]
    return countInversions(transformed, 0, transformed.size() - 1);
}

int main() {
    vector<string> S = {"B", "C", "A", "D"};
    vector<string> D = {"C", "D", "A", "B"};
    
    cout << minSwapsToTransform(S, D) << endl;  // Output: 4

    return 0;
}
