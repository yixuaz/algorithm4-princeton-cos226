package part2.week4.substringsearch;

import commonutil.RandomStringBuilder;
import org.junit.Assert;
import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.*;

public class CyclicRotationStringTest {
    @Test
    public void basicTest() {
        Assert.assertTrue(CyclicRotationString.isCyclicRotation("abc", "bca"));
        Assert.assertTrue(CyclicRotationString.isCyclicRotation("abc", "cab"));
        Assert.assertTrue(CyclicRotationString.isCyclicRotation("abcd", "cdab"));
        Assert.assertFalse(CyclicRotationString.isCyclicRotation("abc", "bac"));
        Assert.assertFalse(CyclicRotationString.isCyclicRotation("abc", "ab"));
    }
    @Test
    public void randomTest() {
        Random r = new Random();
        for (int i = 100; i < 100000; i++) {
            int len = (int) Math.sqrt(i);
            String cur = RandomStringBuilder.randomStringLowerCase(len);
            if (r.nextBoolean()) {
                Assert.assertTrue(CyclicRotationString.isCyclicRotation(cur, cur.substring(len / 2) + cur.substring(0,len / 2)));
            } else {
                Assert.assertFalse(CyclicRotationString.isCyclicRotation(cur, RandomStringBuilder.randomStringLowerCase(len)));
            }
        }

        Assert.assertTrue(CyclicRotationString.isCyclicRotation("abc", "cab"));
        Assert.assertTrue(CyclicRotationString.isCyclicRotation("abcd", "cdab"));
        Assert.assertFalse(CyclicRotationString.isCyclicRotation("abc", "bac"));
    }

}