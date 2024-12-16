/*Given an array of integers nums and an integer threshold, we will choose a positive integer divisor, divide all the array by it, and sum the division's result. Find the smallest divisor such that the result mentioned above is less than or equal to threshold.

Each result of the division is rounded to the nearest integer greater than or equal to that element. (For example: 7/3 = 3 and 10/2 = 5).

The test cases are generated so that there will be an answer. */

class Solution {
    long sum(int divisor, int[] nums){
        long sum = 0;

        for(int it : nums){
            if(it < divisor){
                sum +=1;
            }
            else{
                sum += (it/divisor);
                long rem = it%divisor != 0 ? 1 : 0;
                sum += rem;
            }
        }

        return sum;
    }

    public int smallestDivisor(int[] nums, int threshold) {

        int maxi  = Integer.MIN_VALUE;

        for(int num  : nums){
            if(num > maxi){
                maxi = num;
            }
        }

        int low = 1;
        int high = maxi;

        while(low <= high){
            int mid = low + (high-low)/2;

            if(sum(mid, nums) <= threshold){
                high = mid - 1;
            }
            else{
                low = mid + 1;
            }
        }
        return low;
    }
}