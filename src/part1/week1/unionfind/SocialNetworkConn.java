package part1.week1.unionfind;

import java.util.List;

/**
 * Social network connectivity. Given a social network containing nn members and a log file containing
 * mm timestamps at which times pairs of members formed friendships, design an algorithm to determine
 * the earliest time at which all members are connected (i.e., every member is a friend of a friend
 * of a friend ... of a friend). Assume that the log file is sorted by timestamp and that friendship
 * is an equivalence relation. The running time of your algorithm should be mlogn or better
 * and use extra space proportional to nn.
 */
public class SocialNetworkConn {
    public static class Log {
        private final long timstamp;
        private final int peopleA;
        private final int peopleB;

        public Log(long timstamp, int peopleA, int peopleB) {
            this.timstamp = timstamp;
            this.peopleA = peopleA;
            this.peopleB = peopleB;
        }
    }

    //if there are no time to connect all member return -1
    public static long earliestTimeToConnectAllMember(int n, List<Log> sortedTimeStampLog) {
        //TODO: ADD YOUR CODE HERE
        return -1;
    }
}
