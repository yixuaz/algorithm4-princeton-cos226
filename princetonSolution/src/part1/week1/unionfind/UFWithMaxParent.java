package part1.week1.unionfind;

/**
 * Solution: add extra max array to record in the connected set, what is the max value. so we need to update it when
 * union. we should use max from two set's max number. to find it, first find the root for the set,
 * then get max value from the max array.
 */
public class UFWithMaxParent {
    private int[] parent;
    private int[] size;
    private int[] max;

    public UFWithMaxParent(int n) {
        parent = new int[n];
        size = new int[n];
        max = new int[n];
        for (int i = 0; i < n; i++) {
            parent[i] = i;
            max[i] = i;
            size[i] = 1;
        }
    }

    public int find(int p) {
        int par = findWithPathCompression(p);
        return max[par];
    }

    private int findWithPathCompression(int p) {
        if (parent[p] == p) {
            return p;
        }
        parent[p] = findWithPathCompression(parent[p]);
        return parent[p];
    }

    public boolean union(int i, int j) {
        int pi = findWithPathCompression(i), pj = findWithPathCompression(j);
        if (pi == pj) return false;
        if (size[pi] < size[pj]) {
            parent[pi] = pj;
            size[pj] += size[pi];
            max[pj] = Math.max(max[pj], max[pi]);
        } else {
            parent[pj] = pi;
            size[pi] += size[pj];
            max[pi] = Math.max(max[pj], max[pi]);
        }
        return true;
    }

    public boolean connected(int i, int j) {
        return find(i) == find(j);
    }

}
