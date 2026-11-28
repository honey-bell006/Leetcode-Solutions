import java.util.Arrays;

class Solution {
    public int maximumSaleItems(int[][] items, int budget) {
        int n = items.length;
        int maxFactor = 0;
        long minPrice = Long.MAX_VALUE;

        for (int[] item : items) {
            if (item[0] > maxFactor) {
                maxFactor = item[0];
            }
            if (item[1] < minPrice) {
                minPrice = item[1];
            }
        }

        int[] factorCount = new int[maxFactor + 1];
        for (int[] item : items) {
            factorCount[item[0]]++;
        }

        int[] gain = new int[n];
        for (int i = 0; i < n; i++) {
            int f = items[i][0];
            int multiplesCount = 0;
            for (int mult = f; mult <= maxFactor; mult += f) {
                multiplesCount += factorCount[mult];
            }
            gain[i] = multiplesCount - 1;
        }

        long[][] pairs = new long[n][2];
        for (int i = 0; i < n; i++) {
            pairs[i][0] = items[i][1];
            pairs[i][1] = gain[i];
        }

        Arrays.sort(pairs, (a, b) -> Long.compare(a[0], b[0]));

        long totalItems = 0;
        long rem = budget;
        long doubleMin = 2L * minPrice;

        for (long[] pair : pairs) {
            long price = pair[0];
            long g = pair[1];
            if (g > 0 && price < doubleMin && rem >= price) {
                long take = Math.min(g, rem / price);
                totalItems += 2L * take;
                rem -= take * price;
            }
        }

        totalItems += rem / minPrice;
        return (int) totalItems;
    }
}