
/**Given an integer array nums, return true if you can partition the array into two subsets such that the sum of the elements in both subsets is equal or false otherwise. */

class Solution {

    boolean partition(int[] nums, int[][] dp, int sum, int ind){
        if(sum == 0){
            return true;
        }

        if(ind == 0){
            return nums[ind] == sum;
        }

        if(dp[ind][sum] != -1){
            return dp[ind][sum] == 1 ? true : false;
        }

        boolean taken = false;
        boolean notTaken = partition(nums, dp, sum, ind-1);

        if(nums[ind] <= sum)
            taken = partition(nums, dp, sum - nums[ind], ind-1);
        
        dp[ind][sum] = taken || notTaken ? 1 : 0;
        return taken || notTaken;
    }

    public boolean canPartition(int[] nums) {
        int sum = 0;
        for(int it : nums){
            sum+=it;
        }
        if(sum %2 != 0 || nums.length == 1){
            return false;
        }

        sum = sum/2;
        int size = nums.length;

        int[][] dp = new int[size][sum + 1];

        for( int i =0; i < size; i++){
            Arrays.fill(dp[i], -1);
        }

        return partition(nums, dp, sum, size - 1);
    }
}