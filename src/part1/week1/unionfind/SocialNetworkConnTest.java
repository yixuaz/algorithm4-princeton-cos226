package part1.week1.unionfind;


import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;


public class SocialNetworkConnTest {

    @Test
    public void testEmptyLog() {
        List<SocialNetworkConn.Log> logs = new ArrayList<>();
        Assert.assertEquals(0,
                SocialNetworkConn.earliestTimeToConnectAllMember(1, logs));
        for (int i = 2; i < 10; i++) {
            Assert.assertEquals(-1,
                    SocialNetworkConn.earliestTimeToConnectAllMember(i, logs));
        }
    }

    @Test
    public void testFailedLog() {
        List<SocialNetworkConn.Log> logs = new ArrayList<>();
        logs.add(new SocialNetworkConn.Log(1, 1, 2));
        Assert.assertEquals(-1,
                SocialNetworkConn.earliestTimeToConnectAllMember(3, logs));
    }

    @Test
    public void testSuccessLogBeforeFinal() {
        List<SocialNetworkConn.Log> logs = new ArrayList<>();
        logs.add(new SocialNetworkConn.Log(3, 1, 2));
        logs.add(new SocialNetworkConn.Log(4, 0, 2));
        logs.add(new SocialNetworkConn.Log(5, 0, 1));
        Assert.assertEquals(4,
                SocialNetworkConn.earliestTimeToConnectAllMember(3, logs));
    }

    @Test
    public void testSuccessLogAtFinal() {
        List<SocialNetworkConn.Log> logs = new ArrayList<>();
        logs.add(new SocialNetworkConn.Log(3, 1, 2));
        logs.add(new SocialNetworkConn.Log(4, 2, 2));
        logs.add(new SocialNetworkConn.Log(5, 0, 2));
        Assert.assertEquals(5,
                SocialNetworkConn.earliestTimeToConnectAllMember(3, logs));
    }

    @Test
    public void testDuplicateLog() {
        List<SocialNetworkConn.Log> logs = new ArrayList<>();
        logs.add(new SocialNetworkConn.Log(3, 1, 2));
        logs.add(new SocialNetworkConn.Log(3, 1, 2));
        logs.add(new SocialNetworkConn.Log(3, 1, 2));
        logs.add(new SocialNetworkConn.Log(4, 2, 1));
        logs.add(new SocialNetworkConn.Log(4, 2, 1));
        logs.add(new SocialNetworkConn.Log(5, 0, 2));
        logs.add(new SocialNetworkConn.Log(5, 0, 2));
        Assert.assertEquals(5,
                SocialNetworkConn.earliestTimeToConnectAllMember(3, logs));
    }


}
