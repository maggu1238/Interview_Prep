/*Given an array arr, find the XOR of all subset sums.

Key Observations:
Subset Representation:

A subset can be represented by a binary mask. For example, for arr=[a,b,c]:
Subset {a,b} corresponds to mask 110, where 1 indicates the element is included.
Subset Sum:

The sum of a subset is the sum of all elements where the binary mask has 1 for the corresponding indices.
Key Insight:

If an array contains an element x such that x appears in half of the subsets (due to the properties of binary masks), then the XOR of all subset sums depends only on whether x contributes to the XOR (odd times).

This simplifies to:

If n (the length of the array) is odd, the result is 0, because every element contributes an even number of times.
If n is even, the result is the XOR of all elements in the array.*/

public class SubsetXORSums {
    public static int xorOfSubsetSums(int[] arr) {
        int n = arr.length;
        
        // If the size of the array is odd
        if (n % 2 == 1) {
            int xor = 0;
            for (int num : arr) {
                xor ^= num;
            }
            return xor;
        } 
        
        // If the size of the array is even
        return 0; // XOR is zero because every element cancels out
    }

    public static void main(String[] args) {
        int[] arr1 = {1, 2, 3}; // Odd length
        int[] arr2 = {1, 2, 3, 4}; // Even length

        System.out.println("XOR of subset sums (arr1): " + xorOfSubsetSums(arr1)); // Output: 0
        System.out.println("XOR of subset sums (arr2): " + xorOfSubsetSums(arr2)); // Output: 0
    }
}