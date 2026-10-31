class Solution {
    public int uniquePaths(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        int MOD = 1000000007;

        int[][][] jump = new int[m][n][2];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                jump[i][j][0] = getLanding(grid, m, n, i, j, 0, 1);
                jump[i][j][1] = getLanding(grid, m, n, i, j, 1, 0);
            }
        }

        long[][] dp = new long[m][n];
        dp[0][0] = 1;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (dp[i][j] == 0) continue;

                int targetRight = jump[i][j][0];
                if (targetRight != -1) {
                    int nr = targetRight / n;
                    int nc = targetRight % n;
                    dp[nr][nc] = (dp[nr][nc] + dp[i][j]) % MOD;
                }

                int targetDown = jump[i][j][1];
                if (targetDown != -1) {
                    int nr = targetDown / n;
                    int nc = targetDown % n;
                    dp[nr][nc] = (dp[nr][nc] + dp[i][j]) % MOD;
                }
            }
        }

        return (int) dp[m - 1][n - 1];
    }

    private int getLanding(int[][] grid, int m, int n, int r, int c, int dr, int dc) {
        int currR = r;
        int currC = c;
        int currDr = dr;
        int currDc = dc;

        while (true) {
            int nextR = currR + currDr;
            int nextC = currC + currDc;

            if (nextR < 0 || nextR >= m || nextC < 0 || nextC >= n) {
                return -1;
            }

            if (grid[nextR][nextC] == 0) {
                return nextR * n + nextC;
            }

            if (currDc != 0) {
                currDr = 1;
                currDc = 0;
            } else {
                currDr = 0;
                currDc = 1;
            }
            currR = nextR;
            currC = nextC;
        }
    }
}