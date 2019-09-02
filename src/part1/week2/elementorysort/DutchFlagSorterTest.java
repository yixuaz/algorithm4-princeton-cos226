package part1.week2.elementorysort;

import org.junit.Assert;
import org.junit.Test;

public class DutchFlagSorterTest {
    @Test
    public void sort() {
        int n = 5;
        DutchFlag flag = new DutchFlag(5);
        System.out.println(flag);
        DutchFlagSorter.sort(flag,5);
        System.out.println(flag);
        Assert.assertTrue(flag.isSort());
    }
    @Test(timeout = 200)
    public void testSort() {
        int N = 1000;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < 10; j++) {
                DutchFlag flag = new DutchFlag(i);
                DutchFlagSorter.sort(flag,i);
                Assert.assertTrue(flag.isSort());
            }
        }
    }
}