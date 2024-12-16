/**Given a string s, partition s such that every substring of the partition is a palindrome.
Return the minimum cuts needed for a palindrome partitioning of s. */

class Solution {
    boolean isPalindrome(int start, int end, String s){

        while(start <= end){
            if(s.charAt(start) != s.charAt(end)){
                return false;
            }
            start++;
            end--;
        }

        return true;
    }

    int partitions(int i , int n, String s, int[] dp){

        if(i == n){
            return 0;
        }
        if(dp[i]!=-1){
            return dp[i];
        }

        int value = 0;
        int mini = Integer.MAX_VALUE;

        for( int k = i; k < n; k++){
            if(isPalindrome(i, k, s)){
                value = 1 + partitions(k+1,n,s,dp);
                mini = Math.min(mini, value);
            }
        }
        if(mini == Integer.MAX_VALUE){
            return 0;
        }
        dp[i] = mini;

        return dp[i];
    }
    public int minCut(String s) {
        
        int[] dp = new int[s.length()];
        Arrays.fill(dp,-1);

        return partitions(0,s.length(), s,dp) - 1;
    }
}



// Tabulation

class Solution {
    boolean isPalindrome(int start, int end, String s){

        while(start <= end){
            if(s.charAt(start) != s.charAt(end)){
                return false;
            }
            start++;
            end--;
        }

        return true;
    }

    public int minCut(String s) {
        
        int[] dp = new int[s.length()+1];
        Arrays.fill(dp,0);

        for(int i = s.length() -1; i >=0 ;i--){
            int value = 0;
            int mini = Integer.MAX_VALUE;

            for( int k = i; k < s.length(); k++){
                if(isPalindrome(i, k, s)){
                    value = 1 + dp[k+1];
                    mini = Math.min(mini, value);
                }
            }

            if(mini == Integer.MAX_VALUE){
                dp[i] =  0;
            }
            else{
                dp[i] = mini;
            }
        }
        return dp[0] - 1;
    }
}