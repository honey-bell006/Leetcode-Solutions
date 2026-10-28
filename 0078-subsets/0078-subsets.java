class Solution {
    public List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();
        backtrack(0, nums, new ArrayList<>(), result);
        return result;
    }
    
    private void backtrack(int start, int[] nums, List<Integer> currentSubset, List<List<Integer>> result) {
        result.add(new ArrayList<>(currentSubset));
        
        for (int i = start; i < nums.length; i++) {
            currentSubset.add(nums[i]);
            backtrack(i + 1, nums, currentSubset, result);
            currentSubset.remove(currentSubset.size() - 1);
        }
    }
}