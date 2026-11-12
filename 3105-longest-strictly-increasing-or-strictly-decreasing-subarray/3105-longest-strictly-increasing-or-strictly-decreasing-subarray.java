class Solution {
    public int longestMonotonicSubarray(int[] nums) {
        if (nums == null || nums.length == 0) return 0;
        
        int maxLength = 1;
        int incLen = 1;
        int decLen = 1;
        
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] > nums[i - 1]) {
                incLen++;
                decLen = 1;
            } else if (nums[i] < nums[i - 1]) {
                decLen++;
                incLen = 1;
            } else {
                incLen = 1;
                decLen = 1;
            }
            
            maxLength = Math.max(maxLength, Math.max(incLen, decLen));
        }
        
        return maxLength;
    }
}