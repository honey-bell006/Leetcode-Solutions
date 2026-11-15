class Solution {
    public int numOfSubarrays(int[] arr) {
        int MOD = 1_000_000_007;
        int oddCount = 0;
        int evenCount = 1;
        int prefixSum = 0;
        int totalOdd = 0;
        for (int num : arr) {
            prefixSum += num;
            if (prefixSum % 2 != 0) {
                totalOdd = (totalOdd + evenCount) % MOD;
                oddCount++;
            } else {
                totalOdd = (totalOdd + oddCount) % MOD;
                evenCount++;
            }
        }
        return totalOdd;
    }
}