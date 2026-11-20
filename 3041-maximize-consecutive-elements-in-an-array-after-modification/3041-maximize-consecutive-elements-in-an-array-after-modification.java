import java.util.*;

class Solution {
    public int maxSelectedElements(int[] nums) {
        Arrays.sort(nums);
        Map<Integer, Integer> dp = new HashMap<>();
        int ans = 0;

        for (int num : nums) {
            int toPlusOne = dp.getOrDefault(num, 0) + 1;
            int toSame = dp.getOrDefault(num - 1, 0) + 1;

            dp.put(num + 1, Math.max(dp.getOrDefault(num + 1, 0), toPlusOne));
            dp.put(num, Math.max(dp.getOrDefault(num, 0), toSame));

            ans = Math.max(ans, Math.max(dp.get(num + 1), dp.get(num)));
        }

        return ans;
    }
}