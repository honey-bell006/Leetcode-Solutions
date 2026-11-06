class Solution {
    public int[] countServers(int n, int[][] logs, int x, int[] queries) {
        int m = queries.length;
        Integer[] qIdx = new Integer[m];
        for (int i = 0; i < m; i++) {
            qIdx[i] = i;
        }
        Arrays.sort(qIdx, (a, b) -> Integer.compare(queries[a], queries[b]));
        Arrays.sort(logs, (a, b) -> Integer.compare(a[1], b[1]));
        int[] ans = new int[m];
        HashMap<Integer, Integer> map = new HashMap<>();
        int l = 0, r = 0;
        int total_l = logs.length;
        for (int i : qIdx) {
            int t = queries[i];
            int left = t - x;
            while (r < total_l && logs[r][1] <= t) {
                map.put(logs[r][0], map.getOrDefault(logs[r][0], 0) + 1);
                r++;
            }
            while (l < total_l && logs[l][1] < left) {
                int server = logs[l][0];
                map.put(server, map.get(server) - 1);
                if (map.get(server) == 0) {
                    map.remove(server);
                }
                l++;
            }   
            ans[i] = n - map.size();
        }      
        return ans;
    }
}