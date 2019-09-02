package part1.week1.unionfind;

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

    //if there are no time to connect all member return -1
    public static long earliestTimeToConnectAllMember(int n, List<Log> sortedTimeStampLog) {
        //TODO: ADD YOUR CODE HERE
        return -1;
    }
}
