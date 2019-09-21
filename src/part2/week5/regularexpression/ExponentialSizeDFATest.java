package part2.week5.regularexpression;

import org.junit.Assert;
import org.junit.Test;

import java.util.Random;

public class ExponentialSizeDFATest {
    @Test
    public void test() {
        Random r = new Random();
        for (int i = 0; i < 1000; i++) {
            String s = Integer.toBinaryString(r.nextInt(Integer.MAX_VALUE));
            Assert.assertTrue(ExponentialSizeDFA.match("0" + s));
            Assert.assertFalse(ExponentialSizeDFA.match("1" + s));
        }
    }

}