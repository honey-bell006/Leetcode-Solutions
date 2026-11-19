import java.util.Arrays;

class Solution {
    public int minimizeSum(int[] nums) {
        Arrays.sort(nums);
        int n = nums.length;
        int op1 = nums[n - 3] - nums[0];
        int op2 = nums[n - 1] - nums[2];
        int op3 = nums[n - 2] - nums[1];
        return Math.min(op1, Math.min(op2, op3));
    }
}