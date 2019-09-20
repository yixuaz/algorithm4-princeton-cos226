package part2.week4.substringsearch;

import commonutil.RandomStringBuilder;
import org.junit.Assert;
import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.assertEquals;

public class TandemRepeatTest {
    @Test
    public void basicTest() {
        assertEquals(2, TandemRepeat.solve("abcab", "abcabcababcaba"));
        assertEquals(3, TandemRepeat.solve("abcab", "abcababcababcab"));
        assertEquals(0, TandemRepeat.solve("abcab", "b"));
        assertEquals(0, TandemRepeat.solve("abcab", "abcbab"));
        assertEquals(-1, TandemRepeat.solve("", "b"));
    }

    @Test
    public void randomTest() {
        Random r = new Random();
        for (int i = 100; i < 10000; i++) {
            int len = (int) Math.sqrt(i);
            String cur = RandomStringBuilder.randomStringLowerCase(len);
            StringBuilder sb = new StringBuilder();
            int expect = 1 + r.nextInt(len - 1);
            // build random string will not impact ans
            for (int j = 0; j < r.nextInt(expect); j++) {
                if (r.nextBoolean()) sb.append(RandomStringBuilder.randomStringLowerCase(len));
                else sb.append(cur);
            }
            sb.append(RandomStringBuilder.randomStringLowerCase(len));
            // build ans
            for (int j = 0; j < expect; j++)
                sb.append(cur);
            // build random string will not impact ans
            sb.append(RandomStringBuilder.randomStringLowerCase(len));
            for (int j = 0; j < r.nextInt(expect); j++) {
                if (r.nextBoolean()) sb.append(RandomStringBuilder.randomStringLowerCase(len));
                else sb.append(cur);
            }
            Assert.assertEquals(expect, TandemRepeat.solve(cur, sb.toString()));
        }
    }


}