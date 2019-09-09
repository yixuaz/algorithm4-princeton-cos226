package part1.week4.symboltable;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class WebTrackingTest {
    @Test
    public void randomTest() {
        Map<Integer, Map<Integer, Integer>> expect = new HashMap<>();
        Random r = new Random();
        int N = 100000;
        int users = r.nextInt((int) Math.sqrt(N)), websites = r.nextInt((int) Math.sqrt(N));
        WebTracking webTracking = new WebTracking(websites, users);
        for (int i = 0; i < N; i++) {
            if (r.nextInt(2) == 0) {
                int uid = r.nextInt(users);
                int webid = r.nextInt(websites);
                webTracking.visit(uid, webid);
                expect.putIfAbsent(uid, new HashMap<>());
                expect.get(uid).put(webid, expect.get(uid).getOrDefault(webid, 0) + 1);
                Assert.assertEquals(expect.get(uid).get(webid).intValue(), webTracking.count(uid, webid));
            } else {
                int uid = r.nextInt(users);
                int webid = r.nextInt(websites);
                expect.putIfAbsent(uid, new HashMap<>());
                Assert.assertEquals(expect.get(uid).getOrDefault(webid, 0).intValue(), webTracking.count(uid, webid));
            }
        }
    }


}