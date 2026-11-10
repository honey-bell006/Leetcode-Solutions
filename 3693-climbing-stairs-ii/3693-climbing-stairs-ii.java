class Solution {
    public int climbStairs(int n, int[] costs) {
        long[] dp = new long[n + 1];
        dp[0] = 0;
        
        for (int j = 1; j <= n; j++) {
            long minCost = Long.MAX_VALUE;
            for (int k = 1; k <= 3; k++) {
                int i = j - k;
                if (i >= 0) {
                    long cost = dp[i] + costs[j - 1] + (long) (j - i) * (j - i);
                    minCost = Math.min(minCost, cost);
                }
            }
            dp[j] = minCost;
        }
        
        return (int) dp[n];
    }
}