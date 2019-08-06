package week5.bst;

import edu.princeton.cs.algs4.In;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.*;

public class GeneralizedQueueTest {
    @Test
    public void randomTest() {
        int N = 10000;
        Random r = new Random();
        List<Integer> expect = new ArrayList<>();
        GeneralizedQueue<Integer> queue = new GeneralizedQueue<>();
        for (int i = 0; i < N; i++) {
            if (expect.isEmpty() || r.nextInt(3) > 0) {
                int val = r.nextInt(N);
                expect.add(val);
                queue.append(val);
            } else {
                if (r.nextBoolean())
                    Assert.assertEquals(queue.removeFront(),expect.remove(0));
                else {
                    int idx = r.nextInt(expect.size());
                    int val = r.nextInt(expect.size());
                    Assert.assertEquals(expect.remove(val),queue.remove(val));
                    for (int j = 0; j < expect.size(); j++)
                        Assert.assertEquals(queue.get(j),expect.get(j));
                }
            }
        }

    }

}