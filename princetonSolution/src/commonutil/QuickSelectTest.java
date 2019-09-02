package commonutil;

import edu.princeton.cs.algs4.Quick;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

public class QuickSelectTest {

    @Test
    public void findMedian() {
        List<Integer> input = Arrays.asList(3,2,4,5,1,6);
        Assert.assertEquals(3, QuickSelect.findMedian(input).intValue());
        for (int i = 0; i < 10; i++) {
            int N = (i+1) * 100;
            input = Shuffler.getRandomList(N, -N / 2, N / 2);
            List<Integer> expect = new ArrayList<>(input);
            Collections.sort(expect);
            Assert.assertEquals(expect.get((N - 1) / 2), QuickSelect.findMedian(input));
        }

    }

    @Test
    public void findKth() {
    }
}