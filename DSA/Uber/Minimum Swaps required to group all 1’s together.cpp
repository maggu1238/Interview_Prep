/*Given an array of 0’s and 1’s, we need to write a program to find the minimum number of swaps required to group all 1’s present in the array together.*/

import java.io.*;
import java.util.*;

class GFG{
	
static int minSwaps(int[] arr, int n)
{
	
	// To store total number of ones
	int totalCount = 0; 
	
	// Count total no of ones
	int i;
	for(i = 0; i < n; i++)
		totalCount += arr[i];

	int currCount = 0; // To store count of ones in current window
	int maxCount = 0; // To store maximum count ones out
					// of all windows
					
	// start of window
	i = 0; 
	
	// end of window
	int j = 0; 

	while (j < n) 
	{
		currCount += arr[j];

		// update maxCount when reach window size i.e.
		// total count of ones in array
		if ((j - i + 1) == totalCount) 
		{
			maxCount = Math.max(maxCount, currCount);
			if (arr[i] == 1)
				currCount--; // decrease current count
							// if first element of
							// window is 1
							
			// slide window
			i++; 
		}
		j++;
	}

	return totalCount - maxCount; // return total no of ones in array
								// - maximum count of ones out of
								// all windows
}

// Driver Code
public static void main(String args[])
	{
	int[] a = { 1, 0, 1, 0, 1, 1 };
	int n = a.length;

	System.out.println(minSwaps(a, n));
}
}

// This code is contributed by shivanisinghss2110
