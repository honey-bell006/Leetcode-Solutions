import java.util.*;

class Solution {
    private List<List<Integer>> adj;
    private int[] in, out, head, parent, depth, heavy, pos, invPos;
    private char[] charArr;
    private int timer;
    private int[] tree;

    public List<Boolean> palindromePath(int n, int[][] edges, String s, String[] queries) {
        adj = new ArrayList<>();
        for (int i = 0; i < n; i++) adj.add(new ArrayList<>());
        for (int[] edge : edges) {
            adj.get(edge[0]).add(edge[1]);
            adj.get(edge[1]).add(edge[0]);
        }

        charArr = s.toCharArray();
        in = new int[n];
        out = new int[n];
        head = new int[n];
        parent = new int[n];
        depth = new int[n];
        heavy = new int[n];
        pos = new int[n];
        invPos = new int[n];
        Arrays.fill(heavy, -1);

        parent[0] = -1;
        depth[0] = 0;
        dfs1(0);
        timer = 0;
        dfs2(0, 0);

        tree = new int[4 * n];
        build(1, 0, n - 1);

        List<Boolean> ans = new ArrayList<>();

        for (String qStr : queries) {
            String[] parts = qStr.split(" ");
            if (parts[0].equals("update")) {
                int u = Integer.parseInt(parts[1]);
                char c = parts[2].charAt(0);
                charArr[u] = c;
                update(1, 0, n - 1, pos[u], 1 << (c - 'a'));
            } else {
                int u = Integer.parseInt(parts[1]);
                int v = Integer.parseInt(parts[2]);
                int pathMask = queryPath(u, v);
                if (pathMask == 0 || (pathMask & (pathMask - 1)) == 0) {
                    ans.add(true);
                } else {
                    ans.add(false);
                }
            }
        }

        return ans;
    }

    private int dfs1(int u) {
        int size = 1;
        int maxSubtree = 0;
        for (int v : adj.get(u)) {
            if (v != parent[u]) {
                parent[v] = u;
                depth[v] = depth[u] + 1;
                int subSize = dfs1(v);
                size += subSize;
                if (subSize > maxSubtree) {
                    maxSubtree = subSize;
                    heavy[u] = v;
                }
            }
        }
        return size;
    }

    private void dfs2(int u, int h) {
        head[u] = h;
        pos[u] = timer;
        invPos[timer++] = u;
        if (heavy[u] != -1) {
            dfs2(heavy[u], h);
        }
        for (int v : adj.get(u)) {
            if (v != parent[u] && v != heavy[u]) {
                dfs2(v, v);
            }
        }
    }

    private void build(int node, int l, int r) {
        if (l == r) {
            tree[node] = 1 << (charArr[invPos[l]] - 'a');
            return;
        }
        int mid = (l + r) / 2;
        build(2 * node, l, mid);
        build(2 * node + 1, mid + 1, r);
        tree[node] = tree[2 * node] ^ tree[2 * node + 1];
    }

    private void update(int node, int l, int r, int idx, int val) {
        if (l == r) {
            tree[node] = val;
            return;
        }
        int mid = (l + r) / 2;
        if (idx <= mid) {
            update(2 * node, l, mid, idx, val);
        } else {
            update(2 * node + 1, mid + 1, r, idx, val);
        }
        tree[node] = tree[2 * node] ^ tree[2 * node + 1];
    }

    private int query(int node, int l, int r, int ql, int qr) {
        if (ql > r || qr < l) return 0;
        if (ql <= l && r <= qr) return tree[node];
        int mid = (l + r) / 2;
        return query(2 * node, l, mid, ql, qr) ^ query(2 * node + 1, mid + 1, r, ql, qr);
    }

    private int queryPath(int u, int v) {
        int res = 0;
        while (head[u] != head[v]) {
            if (depth[head[u]] > depth[head[v]]) {
                int t = u; u = v; v = t;
            }
            res ^= query(1, 0, pos.length - 1, pos[head[v]], pos[v]);
            v = parent[head[v]];
        }
        if (depth[u] > depth[v]) {
            int t = u; u = v; v = t;
        }
        res ^= query(1, 0, pos.length - 1, pos[u], pos[v]);
        return res;
    }
}