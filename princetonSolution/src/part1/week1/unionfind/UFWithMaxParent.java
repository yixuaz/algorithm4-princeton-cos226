package part1.week1.unionfind;

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
        if (parent[p] == p) return p;
        return parent[p] = findWithPathCompression(parent[p]);
    }

    public boolean union(int i, int j) {
        int pi = findWithPathCompression(i), pj = findWithPathCompression(j);
        if (pi == pj) return false;
        if (size[pi] < size[pj]) {
            parent[pi] = pj;
            size[pj] += size[pi];
            max[pj] = Math.max(max[pj],max[pi]);
        } else {
            parent[pj] = pi;
            size[pi] += size[pj];
            max[pi] = Math.max(max[pj],max[pi]);
        }
        return true;
    }

    public boolean connected(int i, int j) {
        return find(i) == find(j);
    }

}
