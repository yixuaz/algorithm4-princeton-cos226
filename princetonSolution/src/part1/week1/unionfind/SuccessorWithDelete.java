package part1.week1.unionfind;

/**
 * use Q2 datasture, when we remove x, we need to union x and x+1,
 * then next time we check x, we can get the maxium in the remove x set unitl
 * find the max not removed element.
 */
public class SuccessorWithDelete {
    private UFWithMaxParent uf;
    private int n;

    public SuccessorWithDelete(int n) {
        this.n = n;
        uf = new UFWithMaxParent(n + 1);
    }

    public boolean remove(int x) {
        return uf.union(x, x + 1);
    }

    public int find(int x) {
        int ret = uf.find(x);
        return ret >= n ? -1 : ret;
    }
}
