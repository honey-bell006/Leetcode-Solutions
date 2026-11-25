
import java.util.*;

class Solution {
    public int smallestUniqueSubarray(int[] nums) {
        int n = nums.length;
        int left = 1, right = n;
        int ans = n;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (check(nums, mid)) {
                ans = mid;
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }

        return ans;
    }

    private boolean check(int[] nums, int len) {
        long mod1 = 1000000007L;
        long mod2 = 1000000009L;
        long base1 = 911382323L;
        long base2 = 972663749L;

        int n = nums.length;

        long pow1 = 1, pow2 = 1;

        for (int i = 1; i < len; i++) {
            pow1 = pow1 * base1 % mod1;
            pow2 = pow2 * base2 % mod2;
        }

        long h1 = 0, h2 = 0;

        for (int i = 0; i < len; i++) {
            h1 = (h1 * base1 + nums[i]) % mod1;
            h2 = (h2 * base2 + nums[i]) % mod2;
        }

        HashMap<Long, Integer> map = new HashMap<>();

        add(map, h1, h2);

        for (int i = len; i < n; i++) {
            h1 = ((h1 - nums[i - len] * pow1 % mod1 + mod1)
                    * base1 + nums[i]) % mod1;

            h2 = ((h2 - nums[i - len] * pow2 % mod2 + mod2)
                    * base2 + nums[i]) % mod2;

            add(map, h1, h2);
        }

        for (int count : map.values()) {
            if (count == 1) {
                return true;
            }
        }

        return false;
    }

    private void add(HashMap<Long, Integer> map,
                     long h1, long h2) {
        long key = (h1 << 32) | h2;
        map.put(key, map.getOrDefault(key, 0) + 1);
    }
}