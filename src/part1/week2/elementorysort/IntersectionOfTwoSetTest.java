package part1.week2.elementorysort;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class IntersectionOfTwoSetTest {
    @Test
    public void testFullIntersection() {
        int[] a = {1,2,3};
        int[] b = {2,3,1};
        Assert.assertEquals(3, IntersectionOfTwoSet.intersectionCount(a,b));
    }
    @Test
    public void testNoIntersection() {
        int[] a = {3,2,1,0};
        int[] b = {4,6,5,9,8};
        Assert.assertEquals(0,IntersectionOfTwoSet.intersectionCount(a,b));
    }
    @Test
    public void testOneIntersection() {
        int[] a = {3,2,1};
        int[] b = {4,3,5,6,7};
        Assert.assertEquals(1,IntersectionOfTwoSet.intersectionCount(a,b));
    }
    @Test
    public void testMulIntersection() {
        int[] a = {7,3};
        int[] b = {4,3,5,6,7};
        Assert.assertEquals(2,IntersectionOfTwoSet.intersectionCount(a,b));
    }
    @Test(timeout = 700)
    public void test() {
        Random r = new Random();
        for (int k = 0; k < 10; k++) {
            Set<Integer> s1 = new HashSet<>(), s2 = new HashSet<>();
            int N = 100000;
            for (int i = 0; i < N; i++) s1.add(r.nextInt(N*2));
            for (int i = 0; i < N; i++) s2.add(r.nextInt(N*2));
            int cnt = 0;
            int[] arr1 = new int[s1.size()];
            int[] arr2 = new int[s2.size()];
            int idx = 0;
            for (int i : s1) {
                arr1[idx++] = i;
                if (s2.contains(i)) cnt++;
            }
            idx = 0;
            for (int i : s2) {
                arr2[idx++] = i;
            }
            Assert.assertEquals(cnt,
                    IntersectionOfTwoSet.intersectionCount(arr1,arr2));
        }

    }

}