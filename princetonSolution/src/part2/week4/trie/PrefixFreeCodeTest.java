package part2.week4.trie;

import commonutil.Shuffler;
import edu.princeton.cs.algs4.Quick3string;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class PrefixFreeCodeTest {
    @Test
    public void basicTest() {
        Assert.assertTrue(PrefixFreeCode.isPrefixFree(new String[]{"01", "10", "0010", "1111"}));
        Assert.assertFalse(PrefixFreeCode.isPrefixFree(new String[]{"01", "10", "0010", "10100"}));
        Assert.assertFalse(PrefixFreeCode.isPrefixFree(new String[]{"10100", "01", "10", "0010"}));
    }

    @Test
    public void randomTest() {
        int N = 100;
        Random r = new Random();
        for (int i = 0; i <= 800; i++) {
            if (i % 100 == 99) N <<= 1;
            List<Integer> pre = Shuffler.getRandomList(1 + r.nextInt(N), 0, N);
            List<String> input = pre.stream().map((a) -> Integer.toBinaryString(a)).collect(Collectors.toList());
            String[] cur = input.toArray(new String[0]);
            Assert.assertEquals(expect(cur.clone()), PrefixFreeCode.isPrefixFree(cur));
        }
    }

    private boolean expect(String[] cur) {
        Quick3string.sort(cur);
        for (int i = 1; i < cur.length; i++) {
            if (cur[i].startsWith(cur[i - 1]))
                return false;
        }
        return true;
    }


}