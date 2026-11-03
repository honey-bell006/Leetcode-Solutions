class Solution {
    public int minMoves(int[] nums) {
        int max = nums[0];
        for(int num : nums){
            if(num > max){
                max = num;
            }
        }
        int moves = 0;
        for(int num : nums){
            moves += (max - num);
        }
        return moves;
    }
}