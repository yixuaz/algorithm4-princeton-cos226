package part2.week3.radixsort.suffixarray;

import commonutil.RandomStringBuilder;
import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.assertArrayEquals;

public class SuffixArrayTest {
    @Test
    public void randomTest() {
        for (int i = 10; i <= 1000; i++) {
            String cur = RandomStringBuilder.randomStringBase62(i);
            SuffixArray expect = new NaiveSuffixArray();
            SuffixArray test = new ManberMyerSuffixArray();
            assertArrayEquals(expect.getSuffixArray(cur), test.getSuffixArray(cur));
        }
    }

    @Test
    public void performanceTest() {
        long time1 = 0, time2 = 0;
        SuffixArray n2 = new NaiveSuffixArray();
        SuffixArray nlgn = new ManberMyerSuffixArray();
        for (int i = 100; i <= 5000; i <<= 1) {
            String cur = RandomStringBuilder.randomStringBase62(i);
            long st = System.nanoTime();
            n2.getSuffixArray(cur);
            time1 = System.nanoTime() - st;
            st = System.nanoTime();
            nlgn.getSuffixArray(cur);
            time2 = System.nanoTime() - st;
            System.out.println(i + " ,n^2 :" + time1 + ", nlgn :" + time2);
        }
    }


}
