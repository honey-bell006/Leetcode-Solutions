
class Solution {
    public int minimumChanges(String s, int k) {
        int n = s.length();
        int INF = 1000000000;

        int[][] cost = new int[n][n];

        for (int len = 2; len <= n; len++) {
            for (int start = 0; start + len <= n; start++) {
                int best = INF;

                for (int d = 1; d < len; d++) {
                    if (len % d != 0) continue;

                    int changes = 0;

                    for (int r = 0; r < d; r++) {
                        int left = r;
                        int right = len - d + r;

                        while (left < right) {
                            if (s.charAt(start + left) !=
                                s.charAt(start + right)) {
                                changes++;
                            }

                            left += d;
                            right -= d;
                        }
                    }

                    best = Math.min(best, changes);
                }

                cost[start][start + len - 1] = best;
            }
        }

        int[][] dp = new int[k + 1][n + 1];

        for (int[] row : dp) {
            java.util.Arrays.fill(row, INF);
        }

        dp[0][0] = 0;

        for (int parts = 1; parts <= k; parts++) {
            for (int end = parts * 2; end <= n; end++) {
                for (int cut = (parts - 1) * 2;
                     cut <= end - 2; cut++) {

                    if (dp[parts - 1][cut] == INF) {
                        continue;
                    }

                    dp[parts][end] = Math.min(
                        dp[parts][end],
                        dp[parts - 1][cut] + cost[cut][end - 1]
                    );
                }
            }
        }

        return dp[k][n];
    }
}