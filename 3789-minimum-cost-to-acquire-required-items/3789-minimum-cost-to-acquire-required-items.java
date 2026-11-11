class Solution {
    public long minimumCost(int cost1, int cost2, int costBoth, int need1, int need2) {
        long c1 = cost1;
        long c2 = cost2;
        long cBoth = costBoth;
        long n1 = need1;
        long n2 = need2;
        
        long common = Math.min(n1, n2);
        long totalCost = common * Math.min(cBoth, c1 + c2);
        
        n1 -= common;
        n2 -= common;
        
        if (n1 > 0) {
            totalCost += n1 * Math.min(cBoth, c1);
        } else if (n2 > 0) {
            totalCost += n2 * Math.min(cBoth, c2);
        }
        
        return totalCost;
    }
}