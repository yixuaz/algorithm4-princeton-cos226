package part1.week1.analysis;

public class EggDropBuilding {
    private int n; // have an nn-story building (with floors 1 through n)
    private int T; // An egg breaks if it is dropped from floor TT or higher and does not break otherwise

    public EggDropBuilding(int n, int t) {
        if (t > n)
            throw new IllegalArgumentException("n should larger equal than t");
        this.n = n;
        T = t;
    }

    public int getHeight() {
        return n;
    }

    public int getT() {
        return T;
    }

    public boolean isBreak(int i) {
        return i >= T;
    }

    public boolean guess(int i) {
        return i == T;
    }
}
