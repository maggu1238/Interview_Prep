/*
Given an array arr[] of n integers, find the maximum that maximizes the sum of the value of i*arr[i] where i varies from 0 to n-1.
*/

/*
next_val = curr_val - (cum_sum - arr[i-1]) + arr[i-1] * (n-1);

next_val = Value of ?i*arr[i] after one rotation.
curr_val = Current value of ?i*arr[i] 
cum_sum = Sum of all array elements, i.e., ?arr[i].
*/


// An efficient C++ program to compute
// maximum sum of i*arr[i]
#include<bits/stdc++.h>

using namespace std;

int maxSum(int arr[], int n)
{
	// Compute sum of all array elements
	int cum_sum = 0;
	for (int i=0; i<n; i++)
		cum_sum += arr[i];

	// Compute sum of i*arr[i] for initial
	// configuration.
	int curr_val = 0;
	for (int i=0; i<n; i++)
		curr_val += i*arr[i];

	// Initialize result
	int res = curr_val;

	// Compute values for other iterations
	for (int i=1; i<n; i++)
	{
		// Compute next value using previous 
		// value in O(1) time
		int next_val = curr_val - (cum_sum - arr[i-1])
					+ arr[i-1] * (n-1);

		// Update current value
		curr_val = next_val;

		// Update result if required
		res = max(res, next_val);
	}

	return res;
}

// Driver code
int main()
{
	int arr[] = {8, 3, 1, 2};
	int n = sizeof(arr)/sizeof(arr[0]);
	cout << maxSum(arr, n) << endl;
	return 0;
}
