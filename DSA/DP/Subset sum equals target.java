/**Given an array of positive integers, arr[] and a value, target, determine if there is a subset of the given set with sum equal to given target.  */

class Solution {

    static Boolean isPossible(int arr[], int target, int n, int[][] dp){
        if(target == 0){
            return true;
        }
        
        if(n == 0){
            return arr[n] == target;
        }
        
        if(dp[n][target] != -1)
            return dp[n][target] == 1 ? true : false;
            
        
        Boolean notTaken = isPossible(arr, target, n-1, dp);
        Boolean taken = false;
        
        if (arr[n] <= target)
            taken = isPossible(arr, target - arr[n], n-1, dp);
            
        dp[n][target] = taken || notTaken == true ? 1 : 0;
        
        return dp[n][target] == 1 ? true : false ;
        
    }
    
    static Boolean isSubsetSum(int arr[], int target) {
        // code here
        
        int sum = 0;
        int size = arr.length;

        for(int it : arr){
            sum+=it;
        }
        
        int[][] dp = new int[size][target + 1];
        for(int i =0; i < size; i++){
            Arrays.fill(dp[i], -1);
        }
        
        return isPossible(arr, target, size - 1, dp);
    }
}


// Tabulation
class Solution {
    
    static Boolean isSubsetSum(int arr[], int target) {
        // code here
        
        int size = arr.length;

        boolean[][] dp = new boolean[size][target + 1];
        
        if(arr[0] <= target){
            dp[0][arr[0]] = true;
        }
        
        for( int i =0 ; i < size; i++){
            dp[i][0] = true;
        }
        
        for( int i = 1; i < size; i++){
            for( int j = 1; j < target +1; j++){
                
                Boolean notTaken = dp[i-1][j];
                Boolean taken = false;
        
                if (arr[i] <= j)
                    taken = dp[i-1][j - arr[i]];
            
                dp[i][j] = taken || notTaken;
            }

        }
        
        return dp[size-1][target];
    }
}

// Tabulation

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

        boolean[][] dp = new boolean[size][sum + 1];

        for( int i =0; i < size; i++){
            dp[i][0] = true;
        }

        if(nums[0] <= sum){
            dp[0][nums[0]] = true;
        }

        for( int i =1; i  <size; i++){
            for( int j = 1; j  <= sum; j++){
                boolean taken = false;
                boolean notTaken = dp[i-1][j];

                if(nums[i] <= j){
                    taken = dp[i - 1][j - nums[i]];
                }
                dp[i][j] = taken || notTaken;
            }
        }

        return dp[size-1][sum];
    }