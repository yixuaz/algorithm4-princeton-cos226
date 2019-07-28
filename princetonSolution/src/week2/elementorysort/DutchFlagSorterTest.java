package week2.elementorysort;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class DutchFlagSorterTest {
    @Test
    public void sort() {
        int n = 5;
        DutchFlag flag = new DutchFlag(5);
        System.out.println(flag);
        DutchFlagSorter.sort(flag,5);
        System.out.println(flag);
    }
    @Test
    public void testSort() {
        int N = 1000;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < 10; j++) {
                DutchFlag flag = new DutchFlag(i);
                DutchFlagSorter.sort(flag,i);
                for (int k = 1; k < i; k++) {
                    Assert.assertTrue(flag.color(k - 1) <= flag.color(k));
                }
            }
        }
    }
}