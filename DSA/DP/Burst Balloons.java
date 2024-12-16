/**You are given n balloons, indexed from 0 to n - 1. Each balloon is painted with a number on it represented by an array nums. You are asked to burst all the balloons.

If you burst the ith balloon, you will get nums[i - 1] * nums[i] * nums[i + 1] coins. If i - 1 or i + 1 goes out of bounds of the array, then treat it as if there is a balloon with a 1 painted on it.

Return the maximum coins you can collect by bursting the balloons wisely. */

class Solution {

    int burst(int i , int j, List<Integer> nums, int[][] dp){

        if(i > j)
            return 0;

        if(dp[i][j] != -1){
            return dp[i][j];
        }

        int maxi = Integer.MIN_VALUE;

        for( int k  = i; k <= j; k++){
                // assume that this is the last burst balloon not the first
            int coins = burst(i,k-1,nums,dp) + burst(k+1,j,nums,dp) + nums.get(i-1)*nums.get(k)*nums.get(j+1);

            maxi = Math.max(coins, maxi);
        }
        dp[i][j] = maxi;

        return dp[i][j];
    }

    public int maxCoins(int[] nums) {
        List<Integer> list = new ArrayList<>();
        int size = nums.length;
        for(int num : nums){
            list.add(num);
        }
        list.add(1);
        list.add(0,1);

        int[][] dp = new int[size + 1][size + 1];
        
        for (int i = 0; i < dp.length; i++) {
            Arrays.fill(dp[i], -1);
        }

        return burst(1,size,list, dp);
    }   
}


// tabulation
class Solution {

    public int maxCoins(int[] nums) {
        List<Integer> list = new ArrayList<>();
        int size = nums.length;
        for(int num : nums){
            list.add(num);
        }
        list.add(1);
        list.add(0,1);

        int[][] dp = new int[size + 2][size + 2];
        
        for (int i = 0; i < dp.length; i++) {
            Arrays.fill(dp[i], 0);
        }

        for( int i = size; i >=1; i--){
            for( int j = 1; j <=size; j++){
                if(i > j)
                    dp[i][j] = 0;
                else{
                    int maxi = Integer.MIN_VALUE;

                    for( int k  = i; k <= j; k++){
                            // assume that this is the last burst balloon not the first
                        int coins = dp[i][k-1] + dp[k+1][j] + list.get(i-1)*list.get(k)*list.get(j+1);

                        maxi = Math.max(coins, maxi);
                    }
                    dp[i][j] = maxi;
                }
            }
        }

        return dp[1][size];
    }   
}