package week4.priorityqueue;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.*;

public class DynamicMedianTest {

    @Test
    public void randomTest() {
        List<Integer> expect = new ArrayList<>();
        DynamicMedian dynamicMedian = new DynamicMedian();
        Random r = new Random();
        int N = 100000;
        int max = 0;
        for (int i = 0; i < N; i++) {
            if (expect.isEmpty() || r.nextInt(100) >= 45) {
                int val = r.nextInt(N);
                dynamicMedian.insert(val);
                expect.add(val);
                Collections.sort(expect);
                max = Math.max(expect.size(),max);
            } else {
                Assert.assertEquals(expect.get((expect.size()-1)/2).intValue(),dynamicMedian.findMedian());
                expect.remove((expect.size()-1)/2);
                dynamicMedian.removeMedian();
            }
        }
        System.out.println(max);
    }

}