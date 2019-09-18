package part2.week3.radixsort;

import commonutil.RandomStringBuilder;
import org.junit.Assert;
import org.junit.Test;

import java.util.Random;

public class CyclicRotationsTest {
    @Test
    public void basicTest() {
        String[] input = {"algorithms", "polynomial", "sortsuffix", "boyermoore", "structures", "minimumcut",
                "suffixsort", "stackstack", "binaryheap", "digraphdfs", "stringsort", "digraphbfs"};
        Assert.assertTrue(CyclicRotations.solve(input));
        input = new String[]{"algorithms", "polynomial", "boyermoore", "structures", "minimumcut",
                "suffixsort", "stackstack", "binaryheap", "digraphdfs", "stringsort", "digraphbfs"};
        Assert.assertEquals(false, CyclicRotations.solve(input));
    }

    @Test(timeout = 1500)
    public void randomTest() {
        int N = 3;
        Random r = new Random();
        for (int strLen = 5; strLen < 100; strLen++) {
            if (strLen % 10 == 9) N <<= 1;
            String[] input = new String[N];
            for (int i = 0; i < N; i++) {
                input[i] = RandomStringBuilder.randomStringBase62(strLen);
            }
            if (r.nextBoolean()) {
                int k = r.nextInt(strLen);
                int idx = 1 + r.nextInt(N - 1);
                input[idx] = input[0].substring(k) + input[0].substring(0, k);
                Assert.assertTrue(CyclicRotations.solve(input));
            } else {
                Assert.assertFalse(CyclicRotations.solve(input));
            }
        }
    }

}