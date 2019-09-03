package part1.week1.unionfind;

import org.junit.Assert;
import org.junit.Test;

import java.util.Random;
import java.util.TreeSet;

public class SuccessorWithDeleteTest {
    @Test
    public void basicTest1() {
        SuccessorWithDelete swd = new SuccessorWithDelete(10);
        for (int i = 0; i < 9; i++) {
            Assert.assertTrue(swd.remove(i));
            Assert.assertEquals(swd.find(i), i + 1);
            if (i > 0)
                Assert.assertEquals(swd.find(i - 1), i + 1);
        }
    }

    @Test
    public void basicTest2() {
        SuccessorWithDelete swd = new SuccessorWithDelete(10);
        for (int i = 9; i >= 0; i--) {
            Assert.assertTrue(swd.remove(i));
            Assert.assertEquals(swd.find(i), -1);
            if (i > 0)
                Assert.assertEquals(swd.find(i - 1), i - 1);
        }
    }

    @Test(timeout = 200)
    public void randomTest() {
        TreeSet<Integer> set = new TreeSet<>();
        int N = 100000;
        for (int i = 0; i < N; i++) set.add(i);
        SuccessorWithDelete swd = new SuccessorWithDelete(N);
        Random r = new Random();
        for (int i = 0; i < N / 2; i++) {
            int k = 1 + r.nextInt(N - 1);
            Assert.assertEquals(swd.remove(k), set.remove(k));
            Integer expect = set.ceiling(k - 1);
            Assert.assertEquals(swd.find(k - 1), expect == null ? -1 : expect.intValue());
        }
        for (int i = 0; i < N / 4; i++) {
            int k = r.nextInt(N);
            Integer expect = set.ceiling(k);
            Assert.assertEquals(swd.find(k), expect == null ? -1 : expect.intValue());
        }

    }

}