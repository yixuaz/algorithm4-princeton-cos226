package part1.week1.unionfind;

public class SuccessorWithDelete {
    private UFWithMaxParent uf;
    private int n;
    public SuccessorWithDelete(int n) {
        this.n = n;
        uf = new UFWithMaxParent(n + 1);
    }
    public boolean remove(int x) {
        return uf.union(x,x+1);
    }
    public int find(int x) {
        int ret = uf.find(x);
        return ret >= n ? -1 : ret;
    }
}
