
/**Given a wooden stick of length n units. The stick is labelled from 0 to n. For example, a stick of length 6 is labelled as follows:


Given an integer array cuts where cuts[i] denotes a position you should perform a cut at.

You should perform the cuts in order, you can change the order of the cuts as you wish.

The cost of one cut is the length of the stick to be cut, the total cost is the sum of costs of all cuts. When you cut a stick, it will be split into two smaller sticks (i.e. the sum of their lengths is the length of the stick before the cut). Please refer to the first example for a better explanation.

Return the minimum total cost of the cuts. */
class Solution {
    int findValue(int i, int j,  List<Integer> cuts, int[][] dp){
        if(i > j)
            return 0;
        int sum = 0;
        int mini = Integer.MAX_VALUE;

        if(dp[i][j] != -1){
            return dp[i][j];
        }

        for( int ind = i; ind <= j; ind++){
            sum = findValue(i,ind - 1,cuts,dp) + findValue(ind + 1, j,cuts,dp) + (cuts.get(j+1) - cuts.get(i-1));
            mini = Math.min(mini, sum);
        }
        dp[i][j] = mini;

        return dp[i][j];

    }
    public int minCost(int n, int[] cuts) {
        int c = cuts.length;

        List<Integer> list = new ArrayList<>();
        for(int it : cuts){
            list.add(it);
        }
        list.add(n);
        list.add(0);

        Collections.sort(list);

        
        int[][] dp = new int[c+1][c+1];

        for (int i = 0; i < dp.length; i++) {
            for (int j = 0; j < dp[i].length; j++){
                dp[i][j] = -1;
            }
        }

        return findValue(1,c,list, dp);
    }
}