/*Given two sorted arrays of size m and n respectively, you are tasked with finding the element that would be at the kth position of the final sorted array.*/


/*
Range of x:

The maximum possible value of x: We want to build the left subarray of size k. So, the maximum possible value should be k. But if we are considering arr1[] of size n1 and n1 < k, in that case, the maximum possible value will be n1. So, after generalization, the maximum value will be min(k, n1), where n1 = the size of the smaller array. Here n1 = min(m, n).

The minimum possible value of x: Let’s understand this using an example, given arr1[] size i.e. m = 6, and arr2[] size i.e. n = 5 and k = 7. Now, the lowest value of x(i.e. The no. of elements we should take from arr1[]) should be 2. If we have to build an array of size, 7, and the maximum element we can take from arr2[] is 5, so, we have to take a minimum of 2 elements from arr1[].

So, the minimum possible value should be k-n2, where n2 = the size of the not-considered array i.e. the bigger array.

But if k < n2, the k-n2, will be negative. So, to handle this case, we will consider the minimum value as max(0, k-n2), where n2 = the size of the bigger array, and here, n2 = max(m, n).

The new range of x will be [max(0, k-n2), min(k, n1)], where n1 = the size of the smaller array, and n2 = the size of the bigger array. Here n1 = min(m, n) as we want to optimize the time complexity we will consider the array with a smaller length. And we will apply binary search in this new range.

*/

#include <bits/stdc++.h>
using namespace std;

int kthElement(vector<int> &a, vector<int>& b, int m, int n, int k) {
    if (m > n) return kthElement(b, a, n, m, k);

    int left = k; //length of left half

    //apply binary search:
    int low = max(0, k - n), high = min(k, m);
    while (low <= high) {
        int mid1 = (low + high) >> 1;
        int mid2 = left - mid1;
        //calculate l1, l2, r1 and r2;
        int l1 = INT_MIN, l2 = INT_MIN;
        int r1 = INT_MAX, r2 = INT_MAX;
        if (mid1 < m) r1 = a[mid1];
        if (mid2 < n) r2 = b[mid2];
        if (mid1 - 1 >= 0) l1 = a[mid1 - 1];
        if (mid2 - 1 >= 0) l2 = b[mid2 - 1];

        if (l1 <= r2 && l2 <= r1) {
            return max(l1, l2);
        }

        //eliminate the halves:
        else if (l1 > r2) high = mid1 - 1;
        else low = mid1 + 1;
    }
    return 0; //dummy statement

}