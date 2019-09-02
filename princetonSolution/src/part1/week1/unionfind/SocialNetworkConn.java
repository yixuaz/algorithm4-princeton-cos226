package part1.week1.unionfind;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

import java.util.List;

public class SocialNetworkConn {
    public static class Log {
        long timstamp;
        int peopleA;
        int peopleB;
        public Log(long timstamp, int peopleA, int peopleB) {
            this.timstamp = timstamp;
            this.peopleA = peopleA;
            this.peopleB = peopleB;
        }
    }
    public static long earliestTimeToConnectAllMember(int n, List<Log> sortedTimeStampLog) {
        if (n < 1) throw new IllegalArgumentException("n should >= 1");
        if (n == 1)
            return 0;
        WeightedQuickUnionUF uf = new WeightedQuickUnionUF(n);
        for (Log log : sortedTimeStampLog) {
            uf.union(log.peopleA,log.peopleB);
            if (uf.count() == 1)
                return log.timstamp;
        }
        return -1;
    }
}
