package week4.priorityqueue;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static commonutil.PrimitiveArrayConverter.convertToLongArray;
import static org.junit.Assert.*;

public class TaxicabNumbersTest {
    @Test
    public void basicTest() {
        long[] res = TaxicabNumbers.findAllTaxicabNum(13);
        long[] res2 = TaxicabNumbers.findAllTaxicabNumLessSpace(13);
        Assert.assertArrayEquals(new long[]{1729},res);
        Assert.assertArrayEquals(new long[]{1729},res2);
    }

    @Test
    public void basicTest2() {
        for (int n = 100; n <= 1000; n += 100) {
            long[] res = TaxicabNumbers.findAllTaxicabNum(n);
            long[] res2 = TaxicabNumbers.findAllTaxicabNumLessSpace(n);
            long[] expect = expect(n);
            Arrays.sort(expect);
            Assert.assertArrayEquals(expect,res);
            Assert.assertArrayEquals(expect,res2);
        }
    }
    long[] expect(int n) {
        Set<Long> seen = new HashSet<Long>();
        Set<Long> result = new HashSet<Long>();
        for (int i = 1; i < n; i++) {
            for (int j = i; j < n; j++) {
                long cur = cal(i,j);
                if (!seen.add(cur)) {
                    result.add(cur);
                }
            }
        }
        return convertToLongArray(result);
    }
    private long cal(long i, long j) {
        return i * i * i + j * j * j;
    }


}