import java.util.*;

class Solution {

    class Fenwick {
        long[] count, sum;
        int n;

        Fenwick(int n) {
            this.n = n;
            count = new long[n + 1];
            sum = new long[n + 1];
        }

        void update(int i, long c, long s) {
            while (i <= n) {
                count[i] += c;
                sum[i] += s;
                i += i & -i;
            }
        }

        long query(long[] tree, int i) {
            long ans = 0;
            while (i > 0) {
                ans += tree[i];
                i -= i & -i;
            }
            return ans;
        }

        int kth(int k) {
            int idx = 0;

            for (int bit = Integer.highestOneBit(n);
                 bit > 0; bit >>= 1) {

                int next = idx + bit;

                if (next <= n && count[next] < k) {
                    idx = next;
                    k -= count[next];
                }
            }

            return idx + 1;
        }
    }

    public long minOperations(int[] nums, int x, int k) {

        int n = nums.length;

        int[] sorted = nums.clone();
        Arrays.sort(sorted);

        int m = 0;

        for (int v : sorted) {
            if (m == 0 || sorted[m - 1] != v) {
                sorted[m++] = v;
            }
        }

        Fenwick bit = new Fenwick(m);

        long[] cost = new long[n - x + 1];

        long total = 0;

        for (int i = 0; i < n; i++) {

            int pos = Arrays.binarySearch(
                sorted, 0, m, nums[i]
            ) + 1;

            bit.update(pos, 1, nums[i]);
            total += nums[i];

            if (i >= x) {

                int old = Arrays.binarySearch(
                    sorted, 0, m, nums[i - x]
                ) + 1;

                bit.update(old, -1, -nums[i - x]);
                total -= nums[i - x];
            }

            if (i >= x - 1) {

                int medPos = bit.kth((x + 1) / 2);

                long med = sorted[medPos - 1];

                long leftCount = bit.query(bit.count, medPos);
                long leftSum = bit.query(bit.sum, medPos);

                cost[i - x + 1] =
                    med * leftCount - leftSum
                    + (total - leftSum)
                    - med * (x - leftCount);
            }
        }

        long INF = Long.MAX_VALUE / 4;

        long[] prev = new long[n + 1];
        long[] curr = new long[n + 1];

        for (int j = 1; j <= k; j++) {

            Arrays.fill(curr, INF);

            for (int i = 1; i <= n; i++) {

                curr[i] = curr[i - 1];

                if (i >= x && prev[i - x] != INF) {

                    curr[i] = Math.min(
                        curr[i],
                        prev[i - x] + cost[i - x]
                    );
                }
            }

            long[] temp = prev;
            prev = curr;
            curr = temp;
        }

        return prev[n];
    }
}